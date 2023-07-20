package dungeon;

import java.util.Objects;

/**
 * Position represents a coordinate in the 2-D grid space. A position is comparable
 * to another position, and used in cases like comparing edge preference.
 */
public class Position implements Comparable<Position> {

  private final int x;
  private final int y;

  Position(int x, int y) {
    if (x < 0) {
      throw new IllegalArgumentException("Invalid x");
    }
    if (y < 0) {
      throw new IllegalArgumentException("Invalid y");
    }
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Position)) {
      return false;
    }
    Position position = (Position) o;
    return x == position.x && y == position.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public int compareTo(Position o) {
    if (x < o.x && y <= o.y) {
      return -1;
    } else if (x > o.x && y >= o.y) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public String toString() {
    return String.format("(%d, %d)", x, y);
  }
}
