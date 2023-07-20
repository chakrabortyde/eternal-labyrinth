package dungeon;

import java.util.List;

/**
 * Player represents the player that enters the dungeon at the start
 * position. A player can perform particular actions at a location
 * like move from one location to another and pick treasures.
 */
public interface Player {

  /**
   * Moves the player from its current position to the passed position.
   *
   * @param position the position to be moved to
   */
  void move(Position position);

  /**
   * Returns the current position of the player.
   *
   * @return current position
   */
  Position getPosition();

  /**
   * Returns all the treasures currently in possession of the player.
   *
   * @return list of treasures
   */
  List<Treasure> getTreasures();

  /**
   * Returns the number of crooked arrows left in the stash.
   *
   * @return number of crooked arrows
   */
  int getCrookedArrowsLeft();

  /**
   * Removes a crooked arrows from the stash.
   */
  void removeWeapon();

  /**
   * Returns a copy of the list of weapons held by the player.
   *
   * @return list of weapons
   */
  List<Weapon> getWeapons();

  /** Picks the passed treasure and adds it to the chest of the player.
   *
   * @param treasure the treasure to be picked
   */
  void pickTreasure(Treasure treasure);

  /**
   * Picks the passed weapon and adds it to the chest of the player.
   *
   * @param weapon the weapon to be picked
   */
  void pickWeapon(Weapon weapon);

  /**
   * Returns the health of the player.
   *
   * @return health
   */
  int getHealth();

  /**
   * Enables the user to use a health point from the player to reset.
   */
  void useHealth();
}
