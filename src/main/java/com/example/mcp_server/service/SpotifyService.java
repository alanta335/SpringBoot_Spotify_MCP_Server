package com.example.mcp_server.service;

import com.example.mcp_server.enumeration.PlayBackState;
import com.example.mcp_server.enumeration.TimeRange;
import com.example.mcp_server.mapper.SpotifyMapper;
import com.example.mcp_server.model.context.UserThreadLocalHolder;
import com.example.mcp_server.model.domain.CurrentSongDetail;
import com.example.mcp_server.model.domain.SongBasicDetailRecord;
import com.example.mcp_server.model.response.CurrentSongDetailRecordDto;
import com.example.mcp_server.model.response.TopTrackResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyService {
    private final RestClient restClient;
    private final SpotifyMapper spotifyMapper;

    @Tool(name = "get_user_top_songs", description = "Retrieve a user's top songs from Spotify within a specified time range.")
    public List<SongBasicDetailRecord> getSongs(@ToolParam(description = "Time range for top tracks (e.g., short_term, medium_term, long_term)") TimeRange timeRange,
                                                @ToolParam(description = "Number of tracks to retrieve") String limit,
                                                @ToolParam(description = "Pagination offset") String offset) {
        TopTrackResponseDto topTrackResponseDto = restClient.get()
                .uri("v1/me/top/tracks?time_range=" + timeRange.getValue() + "&limit=" + limit + "&offset=" + offset)
                .header(HttpHeaders.AUTHORIZATION, getAPIKeyFromThreadLocal())
                .retrieve()
                .body(TopTrackResponseDto.class);
        if (Objects.nonNull(topTrackResponseDto)) {
            return spotifyMapper.mapToSongBasicDetailRecord(topTrackResponseDto.items());
        }
        return null;
    }

    @Tool(name = "playback_controller", description = "Control playback of the current song tracks.")
    public String controlPlayBack(@ToolParam(description = "Playback state to apply, Can have value of play or pause") PlayBackState playBackState) {
        String action = playBackState.getState().toLowerCase();
        String uri = UriComponentsBuilder.fromPath("/v1/me/player/{action}")
                .buildAndExpand(action)
                .toString();
        try {
            ResponseEntity<Void> response = restClient.put()
                    .uri(uri)
                    .header(HttpHeaders.AUTHORIZATION, getAPIKeyFromThreadLocal())
                    .retrieve()
                    .toBodilessEntity();

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Playback '{}' executed successfully", action);
                return "Playback " + action + " command sent successfully.";
            } else {
                log.warn("Spotify API returned non-success: {}", response.getStatusCode());
                return "Spotify API returned: " + response.getStatusCode();
            }

        } catch (HttpStatusCodeException ex) {
            log.error("Spotify API error: {}", ex.getStatusCode(), ex);
            return "error occurred: " + ex.getMessage();
        } catch (Exception ex) {
            log.error("Unexpected error during playback '{}': {}", action, ex.getMessage(), ex);
            return "Failed to control playback: " + ex.getMessage();
        }
    }

    private String getAPIKeyFromThreadLocal() {
        return "Bearer " + UserThreadLocalHolder.get().apiKey();
    }

    @Tool(name = "get_current_song_details", description = "Fetch details about the currently playing song on the user's Spotify account.")
    public CurrentSongDetail getCurrentPlayingSongDetails() {
        try {
            ResponseEntity<CurrentSongDetailRecordDto> response = restClient.get()
                    .uri("/v1/me/player/currently-playing")
                    .header(HttpHeaders.AUTHORIZATION, getAPIKeyFromThreadLocal())
                    .retrieve()
                    .toEntity(CurrentSongDetailRecordDto.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return spotifyMapper.mapCurrentSongDetailRecordDtoToCurrentSongDetail(response.getBody());
            } else {
                log.warn("Spotify API returned non-success: {}", response.getStatusCode());
                return null;
            }

        } catch (HttpStatusCodeException ex) {
            log.error("Spotify API error: {}", ex.getStatusCode(), ex);
            return null;
        } catch (Exception ex) {
            log.error("Unexpected error while fetching current song details: {}", ex.getMessage(), ex);
            return null;
        }
    }
}
