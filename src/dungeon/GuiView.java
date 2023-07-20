package dungeon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.io.File;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * This class enables to play the game using an interactive graphical user interface (GUI).
 * This GUI:
 * <ui>
 *   <li>exposes all game settings including the size of the dungeon, the interconnectivity,
 *   whether it is wrapping or not, the percentage of caves that have treasure, and the number
 *   of Otyughs through one or more items on a JMenu</li>
 *   <li>provides an option for quitting the game, restarting the game as a new game with a new
 *   dungeon or reusing the same dungeon through one or more items on a JMenu</li>
 *   <li>displays the dungeon to the screen using graphical representation. The view should begin
 *   with a mostly blank screen and display only the pieces of the maze that have been revealed
 *   by the user's exploration of the caves and tunnels. Dungeons that are bigger than the area
 *   allocated it to the screen should provide the ability to scroll the view.</li>
 *   <li>allows the player to move through the dungeon using a mouse click on the screen in
 *   addition to the keyboard arrow keys. A click on an invalid space in the game would not
 *   advance the player.</li>
 *   <li>displays the details of a dungeon location to the screen. For instance, does it have
 *   treasure, does it have an arrow, does it smell.</li>
 *   <li>provides an option to get the player's description</li>
 *   <li>provides an option for the player to pick up a treasure or an arrow through pressing
 *   a key on the keyboard.</li>
 *   <li>provides an option for the player to shoot an arrow by pressing a key on the keyboard
 *   followed by an arrow key to indicate the direction.</li>
 *   <li>provides a clear indication of the results of each action a player takes.</li>
 * </ui>
 */
public class GuiView extends JFrame implements View {

  // Declare a new font
  static {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    try {
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./res/fonts/8_bit.ttf")));
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
    }
  }

  private final GuiPanel panel;
  private final ReadOnlyModel model;
  private final SettingsPanel settingsPanel;
  private final GameStatePanel gameStatePanel;
  private DungeonKeyListener dungeonKeyListener;
  private DungeonMouseListener dungeonMouseListener;

  /**
   * Constructor for initializing the game GUI.
   *
   * @param readOnlyModel a read-only model
   */
  public GuiView(ReadOnlyModel readOnlyModel) {
    super("Eternal Labyrinth");
    this.model = readOnlyModel;
    setResizable(false);
    setSize(773, 612);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    setJMenuBar(new MenuBar().getMenuBar());

    panel = new GuiPanel(readOnlyModel);
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setPreferredSize(new Dimension(500, 500));
    scrollPane.add(panel);
    gameStatePanel = new GameStatePanel(readOnlyModel);
    settingsPanel = new SettingsPanel(readOnlyModel);

    getContentPane().removeAll();
    add(scrollPane, BorderLayout.CENTER);
    add(gameStatePanel, BorderLayout.EAST);
    add(settingsPanel, BorderLayout.SOUTH);
    repaint();
    pack();
    panel.requestFocus();
  }

  @Override
  public void addAllListeners(GuiController listener) {
    dungeonKeyListener = new DungeonKeyListener(listener);
    dungeonMouseListener = new DungeonMouseListener(listener);
    panel.addMouseListener(dungeonMouseListener);
    panel.addKeyListener(dungeonKeyListener);
    panel.setFocusable(true);
    panel.setRequestFocusEnabled(true);
  }

  @Override
  public void removeAllListeners() {
    panel.removeMouseListener(dungeonMouseListener);
    panel.removeKeyListener(dungeonKeyListener);
    panel.setFocusable(false);
    panel.setRequestFocusEnabled(false);
  }

  @Override
  public void refresh() {
    panel.repaint();
    gameStatePanel.repaint();
    settingsPanel.repaint();
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  private class MenuBar {

    private final JMenuBar menuBar;

    MenuBar() {
      menuBar = new JMenuBar();

      JMenu file = new JMenu("File");

      JMenuItem newGame = new JMenuItem("New default game...");
      newGame.addActionListener(e -> handleNewGame(8, 9, 72, true,
              15, 5, 15,
              5, 5, 5));
      file.add(newGame);

      JMenuItem reset = new JMenu("Reset game");
      JMenuItem newOptions = new JMenuItem("with new options...");
      newOptions.addActionListener(e -> {
        final JPanel gameSettingsPanel = new JPanel(new GridLayout(11, 2));
        final JTextField row = new JTextField();
        final JTextField col = new JTextField();
        final JTextField icv = new JTextField();
        final ButtonGroup wrp = new ButtonGroup();
        final JRadioButton wrpTrue = new JRadioButton("True");
        final JRadioButton wrpFalse = new JRadioButton("False");
        wrp.add(wrpTrue);
        wrp.add(wrpFalse);
        final JSlider trs = makeJSlider(100, 25, 15, true);
        final JSlider mns = makeJSlider(100, 25, 2, true);
        final JSlider wpn = makeJSlider(100, 25, 15, true);
        final JSlider pit = makeJSlider(100, 25, 5, true);
        final JSlider thf = makeJSlider(100, 25, 5, true);
        final JSlider mms = makeJSlider(100, 25, 5, true);
        final JSlider difficulty = makeJSlider(10, 1, 3, true);
        difficulty.addChangeListener(changeEvent -> {
          JSlider theSlider = (JSlider) changeEvent.getSource();
          if (!theSlider.getValueIsAdjusting()) {
            trs.setValue((int) (Math.sin(Math.toRadians(difficulty.getValue() * 18)) * 80));
            wpn.setValue((int) (Math.sin(Math.toRadians(difficulty.getValue() * 18)) * 80));
            pit.setValue(difficulty.getValue() * 8);
            thf.setValue(difficulty.getValue() * 8);
            mns.setValue((int) (Math.pow(difficulty.getValue(), 2) * 0.8));
            mms.setValue((int) (Math.pow(difficulty.getValue(), 2) * 0.8));
            // mns.setValue((int) (Math.exp(difficulty.getValue() / 2.171d) * 0.8));
            // mms.setValue((int) (Math.exp(difficulty.getValue() / 2.171d) * 0.8));
          }
        });
        gameSettingsPanel.add(new JLabel("Rows", SwingConstants.CENTER));
        gameSettingsPanel.add(row);
        gameSettingsPanel.add(new JLabel("Columns", SwingConstants.CENTER));
        gameSettingsPanel.add(col);
        gameSettingsPanel.add(new JLabel("Interconnectivity", SwingConstants.CENTER));
        gameSettingsPanel.add(icv);
        gameSettingsPanel.add(new JLabel("Wrapping?", SwingConstants.CENTER));
        JPanel wrpPanel = new JPanel();
        wrpPanel.setLayout(new FlowLayout());
        wrpPanel.add(wrpTrue);
        wrpPanel.add(wrpFalse);
        gameSettingsPanel.add(wrpPanel);
        gameSettingsPanel.add(new JLabel("Difficulty", SwingConstants.CENTER));
        gameSettingsPanel.add(difficulty);
        gameSettingsPanel.add(new JLabel("Treasure percentage", SwingConstants.CENTER));
        gameSettingsPanel.add(trs);
        gameSettingsPanel.add(new JLabel("Monster percentage", SwingConstants.CENTER));
        gameSettingsPanel.add(mns);
        gameSettingsPanel.add(new JLabel("Weapon percentage", SwingConstants.CENTER));
        gameSettingsPanel.add(wpn);
        gameSettingsPanel.add(new JLabel("Pit percentage", SwingConstants.CENTER));
        gameSettingsPanel.add(pit);
        gameSettingsPanel.add(new JLabel("Thief percentage", SwingConstants.CENTER));
        gameSettingsPanel.add(thf);
        gameSettingsPanel.add(new JLabel("Moving monster percentage", SwingConstants.CENTER));
        gameSettingsPanel.add(mms);
        JOptionPane.showConfirmDialog(GuiView.this, gameSettingsPanel,
                "Game Settings", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        try {
          handleNewGame(Integer.parseInt(row.getText()), Integer.parseInt(col.getText()),
                  Integer.parseInt(icv.getText()), wrpTrue.isSelected(),
                  trs.getValue(), mns.getValue(), wpn.getValue(),
                  pit.getValue(), thf.getValue(), mms.getValue());
        } catch (NumberFormatException numberFormatException) {
          // Intentionally left blank
        }
      });
      reset.add(newOptions);
      JMenuItem currentOptions = new JMenuItem("with current options...");
      currentOptions.addActionListener(e -> handleNewGame(model.getRows(), model.getColumns(),
              model.getInterconnectivity(), model.getIsWrapping(), model.getTreasurePercentage(),
              model.getMonsterPercentage(), model.getWeaponsPercentage(), model.getPitPercentage(),
              model.getThiefPercentage(), model.getMovingMonsterPercentage()));
      reset.add(currentOptions);
      file.add(reset);

      file.addSeparator();

      JMenuItem exit = new JMenuItem("Exit");
      exit.addActionListener(e -> System.exit(1));
      file.add(exit);

      menuBar.add(file);
    }

    public JMenuBar getMenuBar() {
      return menuBar;
    }

    private void handleNewGame(int row, int col, int interconnectivity,
                               boolean isWrapping, int treasurePercentage,
                               int monsterPercentage, int weaponPercentage,
                               int pitPercentage, int thiefPercentage,
                               int movingMonsterPercentage) {
      Model model = new Dungeon(row, col, interconnectivity, isWrapping,
              treasurePercentage, monsterPercentage, weaponPercentage,
              pitPercentage, thiefPercentage, movingMonsterPercentage);
      ReadOnlyModel readOnlyModel = model;
      View view = new GuiView(readOnlyModel);
      GuiController controller = new GuiController(view, model);
      controller.playGame();
      setVisible(false);
      dispose();
    }

    private JSlider makeJSlider(int max, int tick, int initialValue, boolean enabled) {
      JSlider slider = new JSlider();
      slider.setMinimum(1);
      slider.setMaximum(max);
      slider.setEnabled(enabled);
      slider.setValue(initialValue);
      slider.setMajorTickSpacing(tick);
      return slider;
    }
  }
}
