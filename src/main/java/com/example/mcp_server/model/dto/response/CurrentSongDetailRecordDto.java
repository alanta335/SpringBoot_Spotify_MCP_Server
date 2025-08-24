package com.example.mcp_server.model.dto.response;

import java.util.List;
import java.util.Map;

public record CurrentSongDetailRecordDto(
        long timestamp,
        Context context,
        int progress_ms,
        Track item,
        String currently_playing_type,
        Actions actions,
        boolean is_playing
) {
    public record Context(
            Map<String, String> external_urls,
            String href,
            String type,
            String uri
    ) {
    }

    public record Track(
            Album album,
            List<Artist> artists,
            List<String> available_markets,
            int disc_number,
            int duration_ms,
            boolean explicit,
            ExternalIds external_ids,
            Map<String, String> external_urls,
            String href,
            String id,
            boolean is_local,
            String name,
            int popularity,
            String preview_url,
            int track_number,
            String type,
            String uri
    ) {
        public record Album(
                String album_type,
                List<Artist> artists,
                List<String> available_markets,
                Map<String, String> external_urls,
                String href,
                String id,
                List<Image> images,
                String name,
                String release_date,
                String release_date_precision,
                int total_tracks,
                String type,
                String uri
        ) {
        }

        public record Artist(
                Map<String, String> external_urls,
                String href,
                String id,
                String name,
                String type,
                String uri
        ) {
        }
    }


    public record Image(
            int height,
            String url,
            int width
    ) {
    }

    public record ExternalIds(
            String isrc
    ) {
    }

    public record Actions(
            Disallows disallows
    ) {
    }

    public record Disallows(
            boolean resuming
    ) {
    }
}



