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

class WeaponPanel extends JPanel {

  private final JLabel[] weapons;
  private final ReadOnlyModel model;

  WeaponPanel(ReadOnlyModel model) {
    this.model = model;
    weapons = new JLabel[12];
    setBackground(Color.BLACK);
    setLayout(new GridLayout(3, 4, 12, 12));
    setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(3, 3, 3, 3)
    ));
    for (int i = 0; i < 12; i++) {
      JLabel w = new JLabel(new ImageIcon(new ImageIcon(
              LiMappings.getLocationImage("White Arrow")).getImage()
              .getScaledInstance(25, 25,  Image.SCALE_SMOOTH)));
      weapons[i] = w;
      add(w);
    }
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    if (model.hasWeapon()) {
      List<Weapon> weaponList = model.getWeapons();
      weaponList.sort(Comparator.comparing(Enum::name));
      for (int i = 0; i < 4; i++) {
        if (weaponList.contains(Weapon.CROOKED_ARROW)) {
          weaponList.remove(Weapon.CROOKED_ARROW);
        } else {
          updateWeaponIcons(g2d, i);
        }
      }
      for (int i = 4; i < 8; i++) {
        if (weaponList.contains(Weapon.CROOKED_ARROW)) {
          weaponList.remove(Weapon.CROOKED_ARROW);
        } else {
          updateWeaponIcons(g2d, i);
        }
      }
      for (int i = 8; i < 12; i++) {
        if (weaponList.contains(Weapon.CROOKED_ARROW)) {
          weaponList.remove(Weapon.CROOKED_ARROW);
        } else {
          updateWeaponIcons(g2d, i);
        }
      }
    } else {
      for (int i = 0; i < 12; i++) {
        updateWeaponIcons(g2d, i);
      }
    }
  }

  private void updateWeaponIcons(Graphics2D g2d, int i) {
    try {
      Point p = weapons[i].getLocation();
      g2d.drawImage(ImageIO.read(new File(LiMappings.getLocationImage("Blank")))
              .getScaledInstance(26, 35, Image.SCALE_SMOOTH), p.x, p.y, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
