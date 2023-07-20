package dungeon;

import java.util.List;
import java.util.Objects;

/**
 * A tunnel is a location with only 2 entrances.
 * A cave cannot have any treasure.
 */
public class Tunnel extends Cave {

  Tunnel(Position position, List<Action> validDirections) {
    super(position, validDirections);
  }

  @Override
  public boolean hasTreasure() {
    return false;
  }

  @Override
  public boolean addTreasure(Treasure treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("Invalid treasure!");
    }
    return false;
  }

  @Override
  public boolean hasMonster() {
    return false;
  }

  @Override
  public boolean addMonster(Monster monster) {
    if (monster == null) {
      throw new IllegalArgumentException("Invalid monster!");
    }
    return false;
  }

  @Override
  public Monster getMonster() {
    return null;
  }

  @Override
  public boolean hasWeapon() {
    return false;
  }

  @Override
  public boolean addWeapon(Weapon weapon) {
    if (weapon == null) {
      throw new IllegalArgumentException("Invalid weapon!");
    }
    return false;
  }

  @Override
  public boolean hasPit() {
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Location)) {
      return false;
    }
    Location location = (Location) o;
    return Objects.equals(getPosition(), location.getPosition());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPosition());
  }

  @Override
  public String toString() {
    return "Tunnel";
  }
}
