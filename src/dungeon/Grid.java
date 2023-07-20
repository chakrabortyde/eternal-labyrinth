package dungeon;

import java.util.List;

/**
 * The dungeon is represented on a 2-D grid. Supports all the necessary implementation
 * for operations on the dungeon, such as creation of game space, generation of the
 * dungeon maze, calculation of possible paths and routes, etc.
 */
public interface Grid {

  /**
   * Returns a copy of the grid space.
   *
   * @return a copy of 2-D grid
   */
  Grid copy();

  /**
   * Returns the total number of the rows in the 2-D grid.
   *
   * @return total rows
   */
  int getRow();

  /**
   * Returns the total number of columns in the 2-D grid.
   *
   * @return total columns
   */
  int getCol();

  /**
   * Returns the total number of cave location in the 2-D grid.
   *
   * @return total caves
   */
  int getTotalCaves();

  /**
   * Returns the total number of tunnel locations in the 2-D grid.
   *
   * @return total tunnels
   */
  int getTotalTunnel();

  /**
   * Returns the location present at the passed x and y coordinates.
   *
   * @param x row
   * @param y column
   * @return location at coordinate
   */
  Location getLocation(int x, int y);

  /**
   * Returns the location present at the passed position.
   *
   * @param position position
   * @return location at position
   */
  Location getLocation(Position position);

  /**
   * Returns the generated dungeon maze with all the locations from the
   * 2-D grid. The map is created with the level of interconnectivity
   * passed and also, whether the dungeon is wrapping or not.
   *
   * @param interconnectivity degree of interconnectivity
   * @param isWrapping whether wrapping
   * @return randomly generated dungeon map
   */
  Location[][] generateMaze(int interconnectivity, boolean isWrapping);

  /**
   * Returns the set of actions required to be taken to move
   * from start position to end position, along with the
   * method to be used in order to calculate the path.
   *
   * @param start start position
   * @param end end position
   * @param method algorithm to be used
   * @return list of action
   */
  List<Action> calculatePath(Position start, Position end, String method);
}
