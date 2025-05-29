package com.example.mcp_server.model.domain;

import java.sql.Time;
import java.util.List;

public record SongBasicDetailRecord(List<String> artistName,
                                    String songName,
                                    Time songDuration,
                                    String albumName,
                                    String albumReleaseDate,
                                    String songId,
                                    String albumId) {
}
