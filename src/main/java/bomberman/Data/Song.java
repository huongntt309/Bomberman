package bomberman.Data;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Song {
    private int songID;
    private String songName;
    private String songLink;

    public Song(Song song) {
        this.songID = song.songID;
        this.songName = song.songName;
        this.songLink = song.songLink;
    }
}
