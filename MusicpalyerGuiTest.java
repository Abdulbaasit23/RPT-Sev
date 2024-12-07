import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MusicPlayerGUITest {

    private MusicPlayerGUI musicPlayerGUI;
    private MusicPlayer musicPlayer;

    @BeforeEach
    void setUp() {
        musicPlayerGUI = new MusicPlayerGUI();
        musicPlayer = new MusicPlayer();
        musicPlayerGUI.musicPlayer = musicPlayer;  // Injecting real MusicPlayer into GUI
    }

    @Test
    void testGUIComponentsInitialized() {
        // Check if the JFrame is initialized
        assertNotNull(musicPlayerGUI);

        // Check if the songTitle and songArtist labels are initialized
        assertNotNull(musicPlayerGUI.songTitle);
        assertNotNull(musicPlayerGUI.songArtist);

        // Check if the playback buttons (play, pause, next) are initialized
        JPanel playBackBtns = musicPlayerGUI.playBackBtns;
        assertNotNull(playBackBtns);
        assertEquals(4, playBackBtns.getComponentCount());  // Including 4 buttons (prev, play, pause, next)
    }

    @Test
    void testLoadSong() {
        // Create a song object (use a real file path for testing purposes)
        File songFile = new File("src/assets/test_song.mp3");
        Song song = new Song(songFile.getPath());

        // Simulate loading the song
        musicPlayerGUI.updateSongTitleAndArtist(song);

        // Verify that the song title and artist are updated on the GUI
        assertEquals(song.getSongTitle(), musicPlayerGUI.songTitle.getText());
        assertEquals(song.getSongArtist(), musicPlayerGUI.songArtist.getText());
    }

    @Test
    void testPlayButtonAction() {
        // Simulate clicking on the play button
        JButton playButton = (JButton) musicPlayerGUI.playBackBtns.getComponent(1); // Play button
        playButton.doClick(); // Trigger the action

        // Check if the playCurrentSong() method of the MusicPlayer was called
        assertTrue(playButton.isVisible());
    }

    @Test
    void testPauseButtonAction() {
        // Simulate clicking on the play button first to make the pause button visible
        JButton playButton = (JButton) musicPlayerGUI.playBackBtns.getComponent(1); // Play button
        playButton.doClick(); // Trigger the action (start playing)

        // Simulate clicking on the pause button
        JButton pauseButton = (JButton) musicPlayerGUI.playBackBtns.getComponent(2); // Pause button
        pauseButton.doClick(); // Trigger the action

        // Verify that the play button is enabled again and the pause button is hidden
        JButton playButtonAfterPause = (JButton) musicPlayerGUI.playBackBtns.getComponent(1);
        assertTrue(playButtonAfterPause.isVisible());
        assertFalse(pauseButton.isVisible());
    }

    @Test
    void testSongTitleAndArtistUpdate() {
        // Create a song object with test data
        Song song = new Song("src/assets/test_song.mp3");

        // Update the GUI with song title and artist
        musicPlayerGUI.updateSongTitleAndArtist(song);

        // Verify that the GUI label values are updated correctly
        assertEquals(song.getSongTitle(), musicPlayerGUI.songTitle.getText());
        assertEquals(song.getSongArtist(), musicPlayerGUI.songArtist.getText());
    }

    @Test
    void testEnablePauseButtonAndDisablePlayButton() {
        // Create a song object (simulating the user loading a song)
        Song song = new Song("src/assets/test_song.mp3");
        musicPlayerGUI.updateSongTitleAndArtist(song);

        // Simulate clicking the play button to start playing the song
        JButton playButton = (JButton) musicPlayerGUI.playBackBtns.getComponent(1); // Play button
        playButton.doClick(); // Trigger the action (play the song)

        // Verify that the pause button is now visible and play button is hidden
        JButton pauseButton = (JButton) musicPlayerGUI.playBackBtns.getComponent(2);
        assertTrue(pauseButton.isVisible());
        assertFalse(playButton.isVisible());
    }

    @Test
    void testEnablePlayButtonAndDisablePauseButton() {
        // Create a song object and simulate playing it
        Song song = new Song("src/assets/test_song.mp3");
        musicPlayerGUI.updateSongTitleAndArtist(song);

        // Simulate play button click
        JButton playButton = (JButton) musicPlayerGUI.playBackBtns.getComponent(1);
        playButton.doClick(); // Start playing the song

        // Simulate pause button action
        JButton pauseButton = (JButton) musicPlayerGUI.playBackBtns.getComponent(2);
        pauseButton.doClick(); // Pause the song

        // Verify that the play button is visible and pause button is hidden
        JButton playButtonAfterPause = (JButton) musicPlayerGUI.playBackBtns.getComponent(1);
        assertTrue(playButtonAfterPause.isVisible());
        assertFalse(pauseButton.isVisible());
    }
}
