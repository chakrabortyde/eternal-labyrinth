package dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A controller class for the Player interface. It provides all the necessary
 * implementations required for it to function as a player in the adventure game.
 */
public class PlayerController implements Player {

  private int health;
  private Position position;
  private final List<Weapon> weapons;
  private final List<Treasure> treasures;

  /**
   * Initialises the player.
   *
   * @param position start position
   */
  public PlayerController(Position position) {
    this.health = 3;
    this.position = position;
    weapons = new ArrayList<>();
    treasures = new ArrayList<>();
    weapons.add(Weapon.CROOKED_ARROW);
    weapons.add(Weapon.CROOKED_ARROW);
    weapons.add(Weapon.CROOKED_ARROW);
  }

  /**
   * Initialises the player.
   *
   * @param x start X
   * @param y start y
   */
  public PlayerController(int x, int y) {
    this(new Position(x, y));
  }

  @Override
  public void move(Position position) {
    if (position == null) {
      throw new IllegalArgumentException("Invalid position!");
    }
    this.position = position;
  }

  @Override
  public Position getPosition() {
    return position;
  }

  @Override
  public List<Treasure> getTreasures() {
    return treasures;
  }

  @Override
  public int getCrookedArrowsLeft() {
    return (int) weapons.stream().filter(w -> (w == Weapon.CROOKED_ARROW)).count();
  }

  @Override
  public void removeWeapon() {
    if (!weapons.isEmpty()) {
      weapons.remove(0);
    }
  }

  @Override
  public List<Weapon> getWeapons() {
    return new ArrayList<>(weapons);
  }

  @Override
  public void pickTreasure(Treasure treasure) {
    treasures.add(treasure);
  }

  @Override
  public void pickWeapon(Weapon weapon) {
    weapons.add(weapon);
  }

  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public void useHealth() {
    if (health == 0) {
      throw new IllegalStateException("No more tries left!");
    }
    health--;
  }

  @Override
  public String toString() {
    return String.format("\nPlayer is in position %s with treasures %s\n", position, treasures);
  }
}
