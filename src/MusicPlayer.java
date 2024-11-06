import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class MusicPlayer extends PlaybackListener {

    private Song currentsong;

    private AdvancedPlayer advancedPlayer;

    private boolean isPaused;

    public void MusicPlayerGUI(){

    }

    public void loadSong(Song song){
        currentsong = song;

        if (currentsong !=null){
            playCurrentSong();
        }
    }


    public void pauseSong(){
        if(advancedPlayer != null){
            isPaused = true;

            stopSong();
        }

    }

    public void stopSong(){
        if(advancedPlayer != null){
            advancedPlayer.stop();
            advancedPlayer.close();
            advancedPlayer = null;
        }
    }

      public void playCurrentSong(){
        try {

            FileInputStream fileInputStream = new FileInputStream(currentsong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            advancedPlayer = new AdvancedPlayer(bufferedInputStream);
            advancedPlayer.setPlayBackListener(this);
            startMusicThread();

        }catch (Exception e){
            e.printStackTrace();
        }
      }

      private void startMusicThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                   advancedPlayer.play();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
      }

    @Override
    public void playbackStarted(PlaybackEvent evt) {
       System.out.println("playback started");
    }

    @Override
    public void playbackFinished(PlaybackEvent evt) {
        System.out.println("playback finished");
    }
}
