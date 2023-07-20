package dungeon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

class GameStatePanel extends JPanel {

  GameStatePanel(ReadOnlyModel readOnlyModel) {
    setBackground(Color.BLACK);
    setLayout(new GridLayout(3, 1, 0, 0));

    JPanel player = new JPanel();
    player.setLayout(new BorderLayout());
    player.setBorder(new EmptyBorder(10, 10, 10, 10));
    player.setBackground(Color.BLACK);
    JLabel playerLabel = new JLabel("Player", SwingConstants.CENTER);
    playerLabel.setForeground(Color.WHITE);
    playerLabel.setAlignmentX(CENTER_ALIGNMENT);
    playerLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 13));
    playerLabel.setBorder(new EmptyBorder(0, 0, 3, 0));
    player.add(playerLabel, BorderLayout.NORTH);
    player.add(new PlayerPanel(readOnlyModel), BorderLayout.CENTER);
    add(player);

    JPanel treasure = new JPanel();
    treasure.setLayout(new BorderLayout());
    treasure.setBorder(new EmptyBorder(0, 10, 10, 10));
    treasure.setBackground(Color.BLACK);
    JLabel treasureLabel = new JLabel("Treasures", SwingConstants.CENTER);
    treasureLabel.setForeground(Color.WHITE);
    treasureLabel.setAlignmentX(CENTER_ALIGNMENT);
    treasureLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 13));
    treasureLabel.setBorder(new EmptyBorder(0, 0, 3, 0));
    treasure.add(treasureLabel, BorderLayout.NORTH);
    treasure.add(new TreasurePanel(readOnlyModel), BorderLayout.CENTER);
    add(treasure);

    JPanel weapon = new JPanel();
    weapon.setLayout(new BorderLayout());
    weapon.setBorder(new EmptyBorder(0, 10, 0, 10));
    weapon.setBackground(Color.BLACK);
    JLabel weaponLabel = new JLabel("Weapons", SwingConstants.CENTER);
    weaponLabel.setForeground(Color.WHITE);
    weaponLabel.setAlignmentX(CENTER_ALIGNMENT);
    weaponLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 13));
    weaponLabel.setBorder(new EmptyBorder(0, 0, 3, 0));
    weapon.add(weaponLabel, BorderLayout.NORTH);
    weapon.add(new WeaponPanel(readOnlyModel), BorderLayout.CENTER);
    add(weapon);
  }
}
