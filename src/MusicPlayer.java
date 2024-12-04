import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * Handles playback of MP3 songs using the JLayer library.
 * Provides functionality to play, pause, and stop songs, as well as manage song playback state.
 */
public class MusicPlayer extends PlaybackListener {

    /**
     * The currently loaded song.
     */
    private Song currentsong;

    /**
     * The JLayer AdvancedPlayer instance used for playback.
     */
    private AdvancedPlayer advancedPlayer;

    /**
     * Indicates whether the song is paused.
     */
    private boolean isPaused;

    /**
     * The current frame number, used to resume playback from a paused state.
     */
    private int currentFrame;

    /**
     * Constructor for the MusicPlayer class.
     */
    public void MusicPlayerGUI() {
        // Empty constructor for initialization
    }

    /**
     * Loads a song and starts playback immediately if a valid song is provided.
     *
     * @param song the song to be loaded
     */
    public void loadSong(Song song) {
        currentsong = song;

        if (currentsong != null) {
            playCurrentSong();
        }
    }

    /**
     * Pauses the currently playing song.
     * Stops playback and marks the player as paused.
     */
    public void pauseSong() {
        if (advancedPlayer != null) {
            isPaused = true;
            stopSong();
        }
    }

    /**
     * Stops the currently playing song.
     * Terminates the AdvancedPlayer instance and resets it.
     */
    public void stopSong() {
        if (advancedPlayer != null) {
            advancedPlayer.stop();
            advancedPlayer.close();
            advancedPlayer = null;
        }
    }

    /**
     * Plays the currently loaded song from the beginning or resumes it from a paused state.
     */
    public void playCurrentSong() {
        try {
            FileInputStream fileInputStream = new FileInputStream(currentsong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            advancedPlayer = new AdvancedPlayer(bufferedInputStream);
            advancedPlayer.setPlayBackListener(this);
            startMusicThread();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a new thread to handle music playback.
     * Resumes playback from the current frame if paused, otherwise starts from the beginning.
     */
    private void startMusicThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isPaused) {
                        advancedPlayer.play(currentFrame, Integer.MAX_VALUE);
                    } else {
                        advancedPlayer.play();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Called when playback starts. Logs a message indicating that playback has started.
     *
     * @param evt the playback event
     */
    @Override
    public void playbackStarted(PlaybackEvent evt) {
        System.out.println("playback started");
    }

    /**
     * Called when playback finishes. Logs a message indicating that playback has finished.
     *
     * @param evt the playback event
     */
    @Override
    public void playbackFinished(PlaybackEvent evt) {
        System.out.println("playback finished");
    }
}
