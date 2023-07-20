package dungeon;

import java.util.List;

/**
 * A location in the grid represents a location in the dungeon where a player can
 * explore and can be connected to at most four (4) other locations: one to the north,
 * one to the east, one to the south, and one to the west. Some dungeon locations may
 * "wrap" to the one on the other side of the grid.
 */
public interface Location {

  /**
   * Returns the position of the location in the grid space.
   *
   * @return position as coordinates
   */
  Position getPosition();

  /**
   * Returns whether the current location has any treasure or not.
   *
   * @return true if it has treasure, else false
   */
  boolean hasTreasure();

  /**
   * Returns a list of all treasures in the location, if any or null/empty list.
   *
   * @return list of treasures
   */
  List<Treasure> getTreasures();

  /**
   * Adds the passed treasure to the location.
   *
   * @param treasure treasure to be added
   */
  boolean addTreasure(Treasure treasure);

  /**
   * Returns whether the current location has a monster or not.
   *
   * @return true if it has monster, else false
   */
  boolean hasMonster();

  /**
   * Adds the passed monster to the location.
   *
   * @param monster monster to be added
   */
  boolean addMonster(Monster monster);

  /**
   * Returns the monster at the location.
   */
  Monster getMonster();

  /**
   * Removes the monster at the location.
   */
  void removeMonster();

  /**
   * Returns whether the current location has a moving monster or not.
   *
   * @return true if it has monster, else false
   */
  boolean hasMovingMonster();

  /**
   * Adds the passed moving monster to the location.
   *
   * @param monster monster to be added
   */
  boolean addMovingMonster(Monster monster);

  /**
   * Returns the moving monster at the location.
   */
  Monster getMovingMonster();

  /**
   * Removes the moving monster at the location.
   */
  void removeMovingMonster();

  /**
   * Returns the health of the monster at this location.
   *
   * @return monster health
   */
  int getMHealth();

  /**
   * Returns the health of the moving monster at this location.
   *
   * @return moving monster health
   */
  int getMmHealth();

  /**
   * Hit the monster located at this location.
   *
   * @param hitPoints hit points
   */
  void hit(int hitPoints);

  /**
   * Returns whether the current location has any weapon or not.
   *
   * @return true if it has weapon, else false
   */
  boolean hasWeapon();

  /**
   * Returns a list of all weapons in the location, if any or null/empty list.
   *
   * @return list of weapons
   */
  List<Weapon> getWeapons();

  /**
   * Adds the passed weapon to the location.
   *
   * @param weapon weapon to be added
   */
  boolean addWeapon(Weapon weapon);

  /**
   * Returns a list of all valid actions that the player can take from this location.
   *
   * @return a list of valid actions
   */
  List<Action> getValidActions();

  /**
   * Returns whether the location has a pit.
   *
   * @return whether it has a pit
   */
  boolean hasPit();

  /**
   * Adds the passed pit to the location.
   *
   * @param pit passed pit
   * @return whether it was added successfully
   */
  boolean addPit(Pit pit);

  /**
   * Returns whether the location has a thief.
   *
   * @return whether it has thief
   */
  boolean hasThief();

  /**
   * Adds the passed thief to the location.
   *
   * @param thief thief
   * @return whether it was added successfully
   */
  boolean addThief(Thief thief);

  /**
   * Returns the pit type at this location.
   *
   * @return pit type
   */
  Pit getPit();

  /**
   * Returns the thief at this location.
   *
   * @return thief
   */
  Thief getThief();
}
