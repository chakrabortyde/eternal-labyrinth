package dungeon;

import java.util.Objects;

class Action {

  private final Position position;
  private final Direction direction;

  Action(Position position, Direction direction) {
    if (position == null) {
      throw new IllegalArgumentException("Invalid position!");
    }
    if (direction == null) {
      throw new IllegalArgumentException("Invalid direction!");
    }
    this.position = position;
    this.direction = direction;
  }

  public Position getPosition() {
    return position;
  }

  public Direction getDirection() {
    return direction;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Action)) {
      return false;
    }
    Action action = (Action) o;
    return Objects.equals(position, action.position) && direction == action.direction;
  }

  @Override
  public int hashCode() {
    return Objects.hash(position, direction);
  }

  @Override
  public String toString() {
    return String.format("(%s, %s)", position, direction);
  }
}
