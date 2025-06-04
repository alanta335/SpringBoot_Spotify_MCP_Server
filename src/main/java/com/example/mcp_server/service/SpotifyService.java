package com.example.mcp_server.service;

import com.example.mcp_server.enumeration.TimeRange;
import com.example.mcp_server.mapper.SpotifyMapper;
import com.example.mcp_server.model.domain.SongBasicDetailRecord;
import com.example.mcp_server.model.response.TopTrackResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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
                                                @ToolParam(description = "Pagination offset") String offset,
                                                @ToolParam(description = "API key for authentication can be found inside the <API_key> tag") String apiKey) {
        TopTrackResponseDto topTrackResponseDto = restClient.get()
                .uri("v1/me/top/tracks?time_range=" + timeRange.getValue() + "&limit=" + limit + "&offset=" + offset)
                .header("Authorization", "Bearer " + apiKey)
                .retrieve()
                .body(TopTrackResponseDto.class);
        if (Objects.nonNull(topTrackResponseDto)) {
            return spotifyMapper.mapToSongBasicDetailRecord(topTrackResponseDto.items());
        }
        return null;
    }
}
