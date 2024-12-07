import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MusicPlayerTest {

    private MusicPlayer musicPlayer;
    private Song song;

    @BeforeEach
    void setUp() {
        musicPlayer = new MusicPlayer();
        song = new Song("src/assets/test_song.mp3"); // Path should be valid for testing
    }

    @Test
    void testLoadSong_ShouldSetCurrentSongAndStartPlayback() {
        // Arrange
        musicPlayer.loadSong(song);

        // Act
        musicPlayer.playCurrentSong();

        // Assert
        assertEquals(song, musicPlayer.getCurrentsong());
        // Since there's no mocking or player logic to assert, we would have to visually confirm that playback started.
    }

    @Test
    void testPauseSong_ShouldPausePlayback() {
        // Arrange
        musicPlayer.loadSong(song);
        musicPlayer.playCurrentSong();

        // Act
        musicPlayer.pauseSong();

        // Assert
        assertTrue(musicPlayer.isPaused);  // We check if the player is paused.
        assertNull(musicPlayer.getAdvancedPlayer()); // Assuming that the player stops when paused
    }

    @Test
    void testStopSong_ShouldStopPlayback() {
        // Arrange
        musicPlayer.loadSong(song);
        musicPlayer.playCurrentSong();

        // Act
        musicPlayer.stopSong();

        // Assert
        assertNull(musicPlayer.getAdvancedPlayer()); // Ensure the player is stopped and closed.
    }

    @Test
    void testPlayCurrentSong_ShouldPlayMusic() {
        // Arrange
        musicPlayer.loadSong(song);

        // Act
        musicPlayer.playCurrentSong();

        // Assert
        // For now, we cannot test external music play functionality in unit tests
        // But you can check if the song is loaded and the advancedPlayer is initialized.
        assertNotNull(musicPlayer.getAdvancedPlayer());
    }
}
