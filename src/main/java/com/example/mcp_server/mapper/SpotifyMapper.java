package com.example.mcp_server.mapper;

import com.example.mcp_server.model.domain.SongBasicDetailRecord;
import com.example.mcp_server.model.response.TopTrackResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SpotifyMapper {
    @Named("mapArtistNames")
    static List<String> mapArtistNames(final List<TopTrackResponseDto.Artist> artists) {
        return artists == null ? null : artists.stream().map(TopTrackResponseDto.Artist::name).collect(Collectors.toList());
    }

    List<SongBasicDetailRecord> mapToSongBasicDetailRecord(List<TopTrackResponseDto.Song> song);

    @Mapping(target = "artistName", source = "artists", qualifiedByName = "mapArtistNames")
    @Mapping(target = "songName", source = "name")
    @Mapping(target = "songDuration", expression = "java(new Time(song.duration_ms()))")
    @Mapping(target = "albumName", source = "album.name")
    @Mapping(target = "albumReleaseDate", source = "album.release_date")
    @Mapping(target = "songId", source = "id")
    @Mapping(target = "albumId", source = "album.id")
    SongBasicDetailRecord songToSongBasicDetailRecord(TopTrackResponseDto.Song song);
}
