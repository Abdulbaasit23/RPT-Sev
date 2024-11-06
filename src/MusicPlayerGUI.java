import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class MusicPlayerGUI extends JFrame {
    // Color configuration
    public static final Color FRAME_COLOR = Color.BLACK;
    public static final Color TEXT_COLOR = Color.WHITE;

    private final MusicPlayer musicPlayer;
    private final JFileChooser jfileChooser;
    private JLabel songTitle, songArtist;
    private JPanel playBackBtns;

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
                jfileChooser.showOpenDialog(MusicPlayerGUI.this);
                File selectedFile = jfileChooser.getSelectedFile();

                if (selectedFile != null) {
                    Song song = new Song(selectedFile.getPath());
                    musicPlayer.loadSong(song);
                    // Update song title and artist
                    updateSongTitleAndArtist(song);
                    enablePauseButtonDisablePlayButton();
                }
            }
        });
        songMenu.add(loadSong);

        JMenu playlistMenu = new JMenu("Playlist");
        menuBar.add(playlistMenu);

        // Add things to the playlist menu
        JMenuItem createPlaylist = new JMenuItem("Create Playlist");
        playlistMenu.add(createPlaylist);

        JMenuItem loadPlaylist = new JMenuItem("Load Playlist");
        playlistMenu.add(loadPlaylist);

        add(toolbar);
    }

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
        playBackBtns.add(playButton);

        JButton pauseButton = new JButton(loadImage("src/assets/pause.png"));
        pauseButton.setBorderPainted(false);
        pauseButton.setVisible(false);
        pauseButton.setBackground(null);
        playBackBtns.add(pauseButton);

        JButton nextButton = new JButton(loadImage("src/assets/next.png"));
        nextButton.setBorderPainted(false);
        nextButton.setBackground(null);
        playBackBtns.add(nextButton);

        add(playBackBtns);
    }

    private void updateSongTitleAndArtist(Song song) {
        songTitle.setText(song.getSongTitle());
        songArtist.setText(song.getSongArtist());
    }

    private void enablePauseButtonDisablePlayButton() {
        JButton playButton = (JButton) playBackBtns.getComponent(1);
        JButton pauseButton = (JButton) playBackBtns.getComponent(2);

        // Turn off play button
        playButton.setVisible(false);
        playButton.setEnabled(false);

        pauseButton.setVisible(true);
        pauseButton.setEnabled(true);
    }

    private void enablePlayButtonDisablePlayButton() {
        JButton playButton = (JButton) playBackBtns.getComponent(1);
        JButton pauseButton = (JButton) playBackBtns.getComponent(2);

        // Turn on play button
        playButton.setVisible(true);
        playButton.setEnabled(true);

        pauseButton.setVisible(false);
        pauseButton.setEnabled(false);
    }

    private ImageIcon loadImage(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            return new ImageIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MusicPlayerGUI().setVisible(true);
            }
        });
    }
}
