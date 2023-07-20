package dungeon;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

class SettingsPanel extends JPanel {

  SettingsPanel(ReadOnlyModel readOnlyModel) {
    setBackground(Color.BLACK);
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

    JPanel dungeonStateBasicPanel = new JPanel();
    dungeonStateBasicPanel.setBackground(Color.BLACK);
    dungeonStateBasicPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    JLabel rows = new JLabel("Rows : "
            + readOnlyModel.getRows());
    rows.setForeground(Color.WHITE);
    rows.setFont(new Font("Press Start 2P", Font.PLAIN, 13));
    rows.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(3, 3, 3, 3)
    ));
    gbc.gridx = 0;
    gbc.gridy = 0;
    dungeonStateBasicPanel.add(rows, gbc);

    JLabel cols = new JLabel("Columns : "
            + readOnlyModel.getColumns());
    cols.setForeground(Color.WHITE);
    cols.setFont(new Font("Press Start 2P", Font.PLAIN, 13));
    cols.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(3, 3, 3, 3)
    ));
    gbc.gridx = 0;
    gbc.gridy = 1;
    dungeonStateBasicPanel.add(cols);

    JLabel interconnectivity = new JLabel("Interconnectivity : "
            + readOnlyModel.getInterconnectivity());
    interconnectivity.setForeground(Color.WHITE);
    interconnectivity.setFont(new Font("Press Start 2P", Font.PLAIN, 13));
    interconnectivity.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(3, 3, 3, 3)
    ));
    gbc.gridx = 0;
    gbc.gridy = 2;
    dungeonStateBasicPanel.add(interconnectivity);

    JLabel isWrapping = new JLabel("Wrapping : "
            + readOnlyModel.getIsWrapping());
    isWrapping.setForeground(Color.WHITE);
    isWrapping.setFont(new Font("Press Start 2P", Font.PLAIN, 13));
    isWrapping.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(3, 3, 3, 3)
    ));
    gbc.gridx = 0;
    gbc.gridy = 3;
    dungeonStateBasicPanel.add(isWrapping);

    JPanel dungeonStateAdvancedPanel = new JPanel();
    dungeonStateAdvancedPanel.setBackground(Color.BLACK);
    dungeonStateAdvancedPanel.setLayout(new GridBagLayout());

    JLabel treasurePercentage = new JLabel("Treasures in ~ "
            + readOnlyModel.getTreasurePercentage() + "%");
    treasurePercentage.setForeground(Color.WHITE);
    treasurePercentage.setFont(new Font("Press Start 2P", Font.PLAIN, 13));
    treasurePercentage.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(3, 3, 3, 3)
    ));
    gbc.gridx = 0;
    gbc.gridy = 0;
    dungeonStateAdvancedPanel.add(treasurePercentage);

    JLabel monsterPercentage = new JLabel("Monsters in ~ "
            + readOnlyModel.getMonsterPercentage() + "%");
    monsterPercentage.setForeground(Color.WHITE);
    monsterPercentage.setFont(new Font("Press Start 2P", Font.PLAIN, 13));
    monsterPercentage.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(3, 3, 3, 3)
    ));
    gbc.gridx = 0;
    gbc.gridy = 1;
    dungeonStateAdvancedPanel.add(monsterPercentage);

    JLabel weaponPercentage = new JLabel("Weapons in ~ "
            + readOnlyModel.getWeaponsPercentage() + "%");
    weaponPercentage.setForeground(Color.WHITE);
    weaponPercentage.setFont(new Font("Press Start 2P", Font.PLAIN, 13));
    weaponPercentage.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(3, 3, 3, 3)
    ));
    gbc.gridx = 0;
    gbc.gridy = 1;
    dungeonStateAdvancedPanel.add(weaponPercentage);

    add(dungeonStateBasicPanel, BorderLayout.NORTH);
    add(dungeonStateAdvancedPanel, BorderLayout.SOUTH);
  }
}
