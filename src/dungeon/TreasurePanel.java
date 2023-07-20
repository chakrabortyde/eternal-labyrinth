package dungeon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

class TreasurePanel extends JPanel {

  private final ReadOnlyModel model;
  private final JLabel[] treasures;

  TreasurePanel(ReadOnlyModel model) {
    this.model = model;
    treasures = new JLabel[12];
    setBackground(Color.BLACK);
    setLayout(new GridLayout(3, 4, 12, 12));
    setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(3, 3, 3, 3)
    ));
    for (int i = 0; i < 4; i++) {
      JLabel t = new JLabel(new ImageIcon(new ImageIcon(
              LiMappings.getLocationImage("Diamond")).getImage()
              .getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
      treasures[i] = t;
      add(t);
    }
    for (int i = 4; i < 8; i++) {
      JLabel t = new JLabel(new ImageIcon(new ImageIcon(
              LiMappings.getLocationImage("Ruby")).getImage()
              .getScaledInstance(20, 25,  Image.SCALE_SMOOTH)));
      treasures[i] = t;
      add(t);
    }
    for (int i = 8; i < 12; i++) {
      JLabel t = new JLabel(new ImageIcon(new ImageIcon(
              LiMappings.getLocationImage("Sapphire")).getImage()
              .getScaledInstance(20, 25,  Image.SCALE_SMOOTH)));
      treasures[i] = t;
      add(t);
    }
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    if (model.hasTreasure()) {
      List<Treasure> treasureList = model.getTreasures();
      treasureList.sort(Comparator.comparing(Enum::name));
      for (int i = 0; i < 4; i++) {
        if (treasureList.contains(Treasure.DIAMOND)) {
          treasureList.remove(Treasure.DIAMOND);
        } else {
          updateTreasureIcons(g2d, i);
        }
      }
      for (int i = 4; i < 8; i++) {
        if (treasureList.contains(Treasure.RUBY)) {
          treasureList.remove(Treasure.RUBY);
        } else {
          updateTreasureIcons(g2d, i);
        }
      }
      for (int i = 8; i < 12; i++) {
        if (treasureList.contains(Treasure.SAPPHIRE)) {
          treasureList.remove(Treasure.SAPPHIRE);
        } else {
          updateTreasureIcons(g2d, i);
        }
      }
    } else {
      for (int i = 0; i < 12; i++) {
        updateTreasureIcons(g2d, i);
      }
    }
  }

  private void updateTreasureIcons(Graphics2D g2d, int i) {
    try {
      Point p = treasures[i].getLocation();
      g2d.drawImage(ImageIO.read(new File(LiMappings.getLocationImage("Blank")))
              .getScaledInstance(23, 29, Image.SCALE_SMOOTH), p.x, p.y, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
