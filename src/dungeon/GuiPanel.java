package dungeon;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

class GuiPanel extends JPanel {

  private final JLabel[][] cells;
  private final ReadOnlyModel model;

  GuiPanel(ReadOnlyModel readOnlyModel) {
    this.model = readOnlyModel;
    int rows = readOnlyModel.getRows();
    int cols = readOnlyModel.getColumns();
    cells = new JLabel[rows][cols];
    setLayout(new GridLayout(rows, cols, 0, 0));
    setSize(rows * 64, cols * 64);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        String directions = model.getGrid()
                .getLocation(i, j)
                .getValidActions()
                .stream()
                .map(Action::getDirection)
                .sorted(Comparator.comparing(Enum::name))
                .collect(Collectors.toList()).toString();
        cells[i][j] = new JLabel(new ImageIcon(LiMappings.getLocationImage(directions)));
        add(cells[i][j]);
      }
    }
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    for (int i = 0; i < model.getRows(); i++) {
      for (int j = 0; j < model.getColumns(); j++) {
        Point p = cells[i][j].getLocation();
        try {
          int player_x = model.getPlayerPosition().getX();
          int player_y = model.getPlayerPosition().getY();
          List<Position> validDirections = model.getValidPositions();
          if (i == player_x && j == player_y) {
            g2d.drawImage(ImageIO.read(
                    new File(LiMappings.getLocationImage("Player"))), p.x + 8, p.y + 8, null);
            if (model.isSmellingPetrichorNearby()) {
              g2d.drawImage(ImageIO.read(
                      new File(LiMappings.getLocationImage("Petrichor"))), p.x, p.y, null);
            }
            if (model.isSmellingMorePungentNearby()) {
              g2d.drawImage(ImageIO.read(
                      new File(LiMappings.getLocationImage("More Stench"))), p.x, p.y, null);
            } else if (model.isSmellingLessPungentNearby()) {
              g2d.drawImage(ImageIO.read(
                      new File(LiMappings.getLocationImage("Less Stench"))), p.x, p.y, null);
            }
          } else if (validDirections.contains(new Position(i, j))) {
            if (model.getVisitedPositions().contains(new Position(i, j))) {
              g2d.drawImage(ImageIO.read(
                      new File(LiMappings.getLocationImage("Valid Visited"))), p.x, p.y, null);
            } else {
              g2d.drawImage(ImageIO.read(
                      new File(LiMappings.getLocationImage("Valid Unvisited"))), p.x, p.y, null);
            }
          } else {
            if (!model.getVisitedPositions().contains(new Position(i, j))) {
              g2d.drawImage(ImageIO.read(
                      new File(LiMappings.getLocationImage("Blank"))), p.x, p.y, null);
            }
          }
          if (model.getRemovedMonsters().contains(new Position(i, j))
                  && model.getVisitedPositions().contains(new Position(i, j))) {
            g2d.drawImage(ImageIO.read(
                    new File(LiMappings.getLocationImage("Blood"))), p.x + 8, p.y + 8, null);
          }
          if (model.hasPit(new Position(i, j))
                  && model.getVisitedPositions().contains(new Position(i, j))) {
            g2d.drawImage(ImageIO.read(
                    new File(LiMappings.getLocationImage("Pit"))), p.x, p.y, null);
          }
          if (model.hasMonster(new Position(i, j))
                  && model.getVisitedPositions().contains(new Position(i, j))) {
            g2d.drawImage(ImageIO.read(
                    new File(LiMappings.getLocationImage("Otyugh"))), p.x + 8, p.y + 15, null);
          }
          if (model.hasThief(new Position(i, j))
                  && model.getVisitedPositions().contains(new Position(i, j))) {
            g2d.drawImage(ImageIO.read(
                    new File(LiMappings.getLocationImage("Thief"))), p.x + 8, p.y + 8, null);
          }
          if (model.hasMovingMonster(new Position(i, j))
                  && model.getVisitedPositions().contains(new Position(i, j))) {
            g2d.drawImage(ImageIO.read(
                    new File(LiMappings.getLocationImage("Beholder"))), p.x + 8, p.y + 8, null);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
