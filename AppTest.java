import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    void testSongInitialization() {
        // Provide the path to the MP3 file
        String filePath = "src/assets/Wind Riders - Asher Fulero.mp3";

        // Initialize the Song object
        Song song = new Song(filePath);

        // Validate the song title and artist
        assertNotNull(song.getSongTitle(), "Song title should not be null");
        assertNotNull(song.getSongArtist(), "Song artist should not be null");
        assertEquals("Wind Riders", song.getSongTitle(), "Song title mismatch");
        assertEquals("Asher Fulero", song.getSongArtist(), "Song artist mismatch");
    }
}
