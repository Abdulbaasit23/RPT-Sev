import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * A graphical user interface (GUI) for a music player application.
 * Allows the user to load songs, control playback, and manage playlists.
 * Features include play, pause, next, and previous song controls.
 */
public class MusicPlayerGUI extends JFrame {

    /**
     * The background color of the frame.
     */
    public static final Color FRAME_COLOR = Color.BLACK;

    /**
     * The color used for text in the GUI.
     */
    public static final Color TEXT_COLOR = Color.WHITE;

    private final MusicPlayer musicPlayer;
    private final JFileChooser jfileChooser;
    private JLabel songTitle, songArtist;
    private JPanel playBackBtns;

    /**
     * Constructs a MusicPlayerGUI instance, initializing the music player
     * and GUI components.
     */
    public MusicPlayerGUI() {
        super("Music Player");

        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(FRAME_COLOR);

        musicPlayer = new MusicPlayer();
        jfileChooser = new JFileChooser();
        jfileChooser.setCurrentDirectory(new File("src/assets"));
        jfileChooser.setFileFilter(new FileNameExtensionFilter("Music Files", "mp3"));

        addGuicomponent();
    }

    /**
     * Adds GUI components to the frame, including toolbar, playback buttons, and song details.
     */
    private void addGuicomponent() {
        addToolbar();

        JLabel songimage = new JLabel(loadImage("src/assets/record.png"));
        songimage.setBounds(0, 50, getWidth() - 20, 225);
        add(songimage);

        songTitle = new JLabel("Song Title");
        songTitle.setBounds(0, 285, getWidth() - 10, 30);
        songTitle.setFont(new Font("Dialog", Font.BOLD, 24));
        songTitle.setForeground(TEXT_COLOR);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);

        songArtist = new JLabel("Artist");
        songArtist.setBounds(0, 315, getWidth() - 10, 30);
        songArtist.setFont(new Font("Dialog", Font.PLAIN, 24));
        songArtist.setForeground(TEXT_COLOR);
        songArtist.setHorizontalAlignment(SwingConstants.CENTER);
        add(songArtist);

        JSlider playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        playbackSlider.setBounds(getWidth() / 2 - 300 / 2, 365, 300, 40);
        playbackSlider.setBackground(null);
        add(playbackSlider);

        addPlayBackBtns();
    }

    /**
     * Adds a toolbar with menus for loading songs and managing playlists.
     */
    private void addToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setBounds(0, 0, getWidth(), 20);
        toolbar.setFloatable(false);

        JMenuBar menuBar = new JMenuBar();
        toolbar.add(menuBar);

        JMenu songMenu = new JMenu("Song");
        menuBar.add(songMenu);

        JMenuItem loadSong = new JMenuItem("Load Song");
        loadSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = jfileChooser.showOpenDialog(MusicPlayerGUI.this);
                File selectedFile = jfileChooser.getSelectedFile();

                if (result == JFileChooser.APPROVE_OPTION && selectedFile != null) {
                    Song song = new Song(selectedFile.getPath());
                    musicPlayer.loadSong(song);
                    updateSongTitleAndArtist(song);
                    enablePauseButtonDisablePlayButton();
                }
            }
        });
        songMenu.add(loadSong);

        JMenu playlistMenu = new JMenu("Playlist");
        menuBar.add(playlistMenu);

        JMenuItem createPlaylist = new JMenuItem("Create Playlist");
        playlistMenu.add(createPlaylist);

        JMenuItem loadPlaylist = new JMenuItem("Load Playlist");
        playlistMenu.add(loadPlaylist);

        add(toolbar);
    }

    /**
     * Adds playback control buttons to the frame, including play, pause, next, and previous buttons.
     */
    private void addPlayBackBtns() {
        playBackBtns = new JPanel();
        playBackBtns.setBounds(0, 435, getWidth() - 10, 80);
        playBackBtns.setBackground(null);

        JButton prevButton = new JButton(loadImage("src/assets/previous.png"));
        prevButton.setBorderPainted(false);
        prevButton.setBackground(null);
        playBackBtns.add(prevButton);

        JButton playButton = new JButton(loadImage("src/assets/play.png"));
        playButton.setBorderPainted(false);
        playButton.setBackground(null);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enablePauseButtonDisablePlayButton();
                musicPlayer.playCurrentSong();
            }
        });
        playBackBtns.add(playButton);

        JButton pauseButton = new JButton(loadImage("src/assets/pause.png"));
        pauseButton.setBorderPainted(false);
        pauseButton.setVisible(false);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enablePlayButtonDisablePauseButton();
                musicPlayer.pauseSong();
            }
        });
        pauseButton.setBackground(null);
        playBackBtns.add(pauseButton);

        JButton nextButton = new JButton(loadImage("src/assets/next.png"));
        nextButton.setBorderPainted(false);
        nextButton.setBackground(null);
        playBackBtns.add(nextButton);

        add(playBackBtns);
    }

    /**
     * Updates the song title and artist details displayed in the GUI.
     *
     * @param song the song to update details from
     */
    private void updateSongTitleAndArtist(Song song) {
        songTitle.setText(song.getSongTitle());
        songArtist.setText(song.getSongArtist());
    }

    /**
     * Enables the play button and disables the pause button.
     */
    private void enablePlayButtonDisablePauseButton() {
        JButton playButton = (JButton) playBackBtns.getComponent(1);
        JButton pauseButton = (JButton) playBackBtns.getComponent(2);

        playButton.setVisible(true);
        playButton.setEnabled(true);

        pauseButton.setVisible(false);
        pauseButton.setEnabled(false);
    }

    /**
     * Enables the pause button and disables the play button.
     */
    private void enablePauseButtonDisablePlayButton() {
        JButton playButton = (JButton) playBackBtns.getComponent(1);
        JButton pauseButton = (JButton) playBackBtns.getComponent(2);

        playButton.setVisible(false);
        playButton.setEnabled(false);

        pauseButton.setVisible(true);
        pauseButton.setEnabled(true);
    }

    /**
     * Loads an image from the specified file path and returns an ImageIcon.
     *
     * @param imagePath the path to the image file
     * @return the ImageIcon object for the loaded image
     */
    private ImageIcon loadImage(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            return new ImageIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * The main method to launch the MusicPlayerGUI application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MusicPlayerGUI().setVisible(true);
            }
        });
    }
}
