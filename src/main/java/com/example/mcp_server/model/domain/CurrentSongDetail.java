package com.example.mcp_server.model.domain;

import java.util.List;

public record CurrentSongDetail(
        Track item,
        boolean is_playing
) {
    public record Track(
            Album album,
            List<Artist> artists,
            int disc_number,
            String id,
            String name,
            int popularity,
            int track_number,
            String uri
    ) {
        public record Album(
                List<Artist> artists,
                String id,
                String name,
                String release_date,
                String release_date_precision,
                int total_tracks,
                String uri
        ) {
        }

        public record Artist(
                String id,
                String name,
                String uri
        ) {
        }
    }
}
