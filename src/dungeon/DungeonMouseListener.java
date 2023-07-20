package dungeon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class DungeonMouseListener extends MouseAdapter {

  private final GuiController listener;

  DungeonMouseListener(GuiController listener) {
    this.listener = listener;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);
    listener.handlePlayerMove(Math.floorDiv(e.getY(), 64), Math.floorDiv(e.getX(), 64));
  }
}
