package com.example.mcp_server.model.dto.response;

import java.util.List;

public record TopTrackResponseDto(
        List<Song> items
) {
    public record Song(
            Album album,
            List<Artist> artists,
            List<String> available_markets,
            int disc_number,
            int duration_ms,
            boolean explicit,
            ExternalId external_ids,
            ExternalUrl external_urls,
            String href,
            String id,
            boolean is_local,
            boolean is_playable,
            String name,
            int popularity,
            String preview_url,
            int track_number,
            String type,
            String uri
    ) {
    }

    public record Album(
            String album_type,
            List<Artist> artists,
            List<String> available_markets,
            ExternalUrl external_urls,
            String href,
            String id,
            List<Image> images,
            boolean is_playable,
            String name,
            String release_date,
            String release_date_precision,
            int total_tracks,
            String type,
            String uri
    ) {
    }

    public record Artist(
            ExternalUrl external_urls,
            String href,
            String id,
            String name,
            String type,
            String uri
    ) {
    }

    public record ExternalUrl(
            String spotify
    ) {
    }

    public record ExternalId(
            String isrc
    ) {
    }

    public record Image(
            int height,
            String url,
            int width
    ) {
    }
}

