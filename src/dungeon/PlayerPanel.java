package dungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

class PlayerPanel extends JPanel {

  private final ReadOnlyModel model;
  private final JLabel[] playerStats;

  PlayerPanel(ReadOnlyModel model) {
    this.model = model;
    playerStats = new JLabel[12];
    setBackground(Color.BLACK);
    setLayout(new GridLayout(3, 4, 12, 12));
    setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(3, 3, 3, 3)
    ));
    for (int i = 0; i < 4; i++) {
      JLabel t = new JLabel(new ImageIcon(new ImageIcon(
              LiMappings.getLocationImage("Health")).getImage()
              .getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
      playerStats[i] = t;
      add(t);
    }
    for (int i = 4; i < 8; i++) {
      JLabel t = new JLabel(new ImageIcon(new ImageIcon(
              LiMappings.getLocationImage("Blank")).getImage()
              .getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
      playerStats[i] = t;
      add(t);
    }
    for (int i = 8; i < 12; i++) {
      JLabel t = new JLabel(new ImageIcon(new ImageIcon(
              LiMappings.getLocationImage("Blank")).getImage()
              .getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
      playerStats[i] = t;
      add(t);
    }
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    for (int i = model.getPlayerHealth(); i < 4; i++) {
      updateTreasureIcons(g2d, i, "Blank");
    }
    int treasureTopFour = 4;
    for (Treasure t : model.getPlayerTreasure()) {
      switch (t) {
        case DIAMOND:
          updateTreasureIcons(g2d, treasureTopFour, "Diamond");
          break;
        case RUBY:
          updateTreasureIcons(g2d, treasureTopFour, "Ruby");
          break;
        case SAPPHIRE:
          updateTreasureIcons(g2d, treasureTopFour, "Sapphire");
          break;
        default:
          // Not required
          break;
      }
      treasureTopFour++;
      if (treasureTopFour > 7) {
        break;
      }
    }
    int weaponTopFour = 8;
    for (Weapon w : model.getPlayerWeapon()) {
      if (w == Weapon.CROOKED_ARROW) {
        updateTreasureIcons(g2d, weaponTopFour, "White Arrow");
      }
      weaponTopFour++;
      if (weaponTopFour > 11) {
        break;
      }
    }
  }

  private void updateTreasureIcons(Graphics2D g2d, int i, String icon) {
    try {
      Point p = playerStats[i].getLocation();
      g2d.drawImage(ImageIO.read(new File(LiMappings.getLocationImage(icon)))
              .getScaledInstance(28, 28, Image.SCALE_SMOOTH), p.x, p.y, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
