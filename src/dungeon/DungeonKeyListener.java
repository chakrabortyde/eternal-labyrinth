package dungeon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

class DungeonKeyListener extends KeyAdapter {

  private int previousKey;
  private final EnhancedController listener;

  DungeonKeyListener(GuiController listener) {
    this.listener = listener;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    super.keyPressed(e);
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        if (!listener.isPlayerInWait()) {
          try {
            if (previousKey == KeyEvent.VK_S) {
              listener.shoot(Weapon.CROOKED_ARROW, getPower(), Direction.NORTH);
            } else {
              listener.handlePlayerMove(Direction.NORTH);
            }
          } finally {
            previousKey = -1;
          }
        }
        break;
      case KeyEvent.VK_DOWN:
        if (!listener.isPlayerInWait()) {
          try {
            if (previousKey == KeyEvent.VK_S) {
              listener.shoot(Weapon.CROOKED_ARROW, getPower(), Direction.SOUTH);
            } else {
              listener.handlePlayerMove(Direction.SOUTH);
            }
          } finally {
            previousKey = -1;
          }
        }
        break;
      case KeyEvent.VK_LEFT:
        if (!listener.isPlayerInWait()) {
          try {
            if (previousKey == KeyEvent.VK_S) {
              listener.shoot(Weapon.CROOKED_ARROW, getPower(), Direction.WEST);
            } else {
              listener.handlePlayerMove(Direction.WEST);
            }
          } finally {
            previousKey = -1;
          }
        }
        break;
      case KeyEvent.VK_RIGHT:
        if (!listener.isPlayerInWait()) {
          try {
            if (previousKey == KeyEvent.VK_S) {
              listener.shoot(Weapon.CROOKED_ARROW, getPower(), Direction.EAST);
            } else {
              listener.handlePlayerMove(Direction.EAST);
            }
          } finally {
            previousKey = -1;
          }
        }
        break;
      case KeyEvent.VK_P:
        if (!listener.isPlayerInWait()) {
          previousKey = KeyEvent.VK_P;
        }
        break;
      case KeyEvent.VK_T:
        if (!listener.isPlayerInWait()) {
          if (previousKey == KeyEvent.VK_P) {
            listener.pickTreasure(0);
          }
          previousKey = -1;
        }
        break;
      case KeyEvent.VK_W:
        if (!listener.isPlayerInWait()) {
          if (previousKey == KeyEvent.VK_P) {
            listener.pickWeapon(0);
          }
          previousKey = -1;
        }
        break;
      case KeyEvent.VK_S:
        if (!listener.isPlayerInWait()) {
          previousKey = KeyEvent.VK_S;
        }
        break;
      case KeyEvent.VK_R:
        listener.restartPlayer();
        break;
      default:
        // Not needed
        break;
    }
  }

  private int getPower() throws IllegalStateException {
    final JOptionPane optionPane = new JOptionPane();
    JSlider slider = new JSlider();
    slider.setMajorTickSpacing(1);
    slider.setMinimum(1);
    slider.setMaximum(5);
    slider.setValue(1);
    optionPane.setInputValue(slider.getValue());
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.addChangeListener(changeEvent -> {
      JSlider theSlider = (JSlider) changeEvent.getSource();
      if (!theSlider.getValueIsAdjusting()) {
        optionPane.setInputValue(theSlider.getValue());
      }
    });
    optionPane.setMessage(new Object[] {"Shoot power: ", slider});
    optionPane.setMessageType(JOptionPane.PLAIN_MESSAGE);
    optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);
    JDialog dialog = optionPane.createDialog("Set power");
    dialog.pack();
    dialog.setVisible(true);
    return (int) optionPane.getInputValue();
  }
}
