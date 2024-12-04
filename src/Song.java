import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;

/**
 * Represents a song file and extracts metadata such as title, artist, and file path.
 * Uses the JAudioTagger library to retrieve song metadata from audio files.
 */
public class Song {
    /**
     * The title of the song, extracted from the metadata.
     */
    private String songTitle;

    /**
     * The artist of the song, extracted from the metadata.
     */
    private String songArtist;

    /**
     * The length of the song in a readable format (currently not implemented).
     */
    private String songLength;

    /**
     * The file path to the song file.
     */
    private String filePath;

    /**
     * Constructs a Song object and initializes its metadata by reading the file at the given path.
     *
     * @param filePath the file path of the song
     */
    public Song(String filePath) {
        this.filePath = filePath;
        try {
            AudioFile audioFile = AudioFileIO.read(new File(filePath));
            Tag tag = audioFile.getTag();
            if (tag != null) {
                songTitle = tag.getFirst(FieldKey.TITLE);
                songArtist = tag.getFirst(FieldKey.ARTIST);
            } else {
                songTitle = "N/A";
                songArtist = "N/A";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the title of the song.
     *
     * @return the song title as a string
     */
    public String getSongTitle() {
        return songTitle;
    }

    /**
     * Returns the artist of the song.
     *
     * @return the song artist as a string
     */
    public String getSongArtist() {
        return songArtist;
    }

    /**
     * Returns the length of the song. Currently not implemented and returns null.
     *
     * @return the song length as a string
     */
    public String getSongLength() {
        return songLength;
    }

    /**
     * Returns the file path to the song file.
     *
     * @return the file path as a string
     */
    public String getFilePath() {
        return filePath;
    }
}
