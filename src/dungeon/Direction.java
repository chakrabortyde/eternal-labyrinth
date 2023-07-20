package dungeon;

/**
 * Enum for having all the four types of directions.
 */
public enum Direction {
  NORTH, SOUTH, EAST, WEST;

  Direction reverse() {
    switch (this) {
      case NORTH:
        return SOUTH;
      case SOUTH:
        return NORTH;
      case EAST:
        return WEST;
      case WEST:
        return EAST;
      default:
        //Not required
    }
    throw new IllegalStateException("Unexpected state!");
  }
}
