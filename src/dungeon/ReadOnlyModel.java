package dungeon;

import java.util.List;

/**
 * A dungeon is represented by an m x n two-dimensional grid. Dungeons
 * are generated at random following some set of constraints resulting
 * in a different network each time the game begins. This interface is
 * a read-only version of the same model currently in use.
 */
public interface ReadOnlyModel {

  /**
   * Returns the 2-D grid used to generate the dungeon.
   *
   * @return 2-D grid
   */
  Grid getGrid();

  /**
   * Returns the number of rows in the dungeon.
   *
   * @return number of rows
   */
  int getRows();

  /**
   * Returns the number of columns in the dungeon.
   *
   * @return number of columns
   */
  int getColumns();

  /**
   * Returns the interconnectivity of the dungeon.
   *
   * @return interconnectivity
   */
  int getInterconnectivity();

  /**
   * Returns whether the dungeon is wrapping.
   *
   * @return is wrapped
   */
  boolean getIsWrapping();

  /**
   * Returns the treasure percentage in the dungeon.
   *
   * @return percentage of treasure
   */
  int getTreasurePercentage();

  /**
   * Returns the monster percentage in the dungeon.
   *
   * @return percentage of monster
   */
  int getMonsterPercentage();

  /**
   * Returns the weapons= percentage in the dungeon.
   *
   * @return percentage of weapons
   */
  int getWeaponsPercentage();

  /**
   * Returns a copy of all removed monster positions from the dungeon.
   *
   * @return list of removed monster positions
   */
  List<Position> getRemovedMonsters();

  /**
   * Returns the dungeon map in the form of a 2-D location array.
   *
   * @return 2-D location grid
   */
  Location[][] getMap();

  /**
   * Returns the start position.
   *
   * @return start
   */
  Position getStart();

  /**
   * Returns the end position.
   *
   * @return end
   */
  Position getEnd();

  /**
   * Returns the current player position.
   *
   * @return player position
   */
  Position getPlayerPosition();

  /**
   * Returns a list of all valid positions possible at this location.
   *
   * @return valid positions
   */
  List<Position> getValidPositions();

  /**
   * Returns a list of all valid directions possible at this location.
   *
   * @return valid directions
   */
  List<Direction> getValidDirections();

  /**
   * Returns a list of all valid actions possible at this location.
   *
   * @return valid actions
   */
  List<Action> getValidActions();

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
   * Returns whether the current location has a monster or not.
   *
   * @return true if it has monster, else false
   */
  boolean hasMonster();

  /**
   * Returns whether the passed position has a monster or not.
   *
   * @param position position
   * @return true if it has monster, else false
   */
  boolean hasMonster(Position position);

  /**
   * Returns the monster at the location.
   */
  Monster getMonster();

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
   * Returns a list of treasures contained by the player.
   *
   * @return list of treasures
   */
  List<Treasure> getPlayerTreasure();

  /**
   * Returns a list of weapons contained by the player.
   *
   * @return list of weapons
   */
  List<Weapon> getPlayerWeapon();

  /**
   * Returns the player's current health.
   *
   * @return health
   */
  int getPlayerHealth();

  /**
   * Returns a list of visited positions by the player.
   *
   * @return lits of visited positions
   */
  List<Position> getVisitedPositions();

  /**
   * Returns whether it is smelling less pungent nearby.
   *
   * @return is smelling less pungent
   */
  boolean isSmellingLessPungentNearby();

  /**
   * Returns whether it is smelling more pungent nearby.
   *
   * @return is smelling more pungent
   */
  boolean isSmellingMorePungentNearby();

  /**
   * Returns whether it is smelling petrichor nearby.
   *
   * @return is smelling petrichor
   */
  boolean isSmellingPetrichorNearby();

  /**
   * Returns whether the passed position has a pit.
   *
   * @return is having pit
   */
  boolean hasPit(Position position);

  /**
   * Returns whether the passed position has a thief or not.
   *
   * @return is having thief
   */
  boolean hasThief(Position position);

  /**
   * Returns the pit type from the passed position.
   *
   * @return pit type
   */
  Pit getPit(Position position);

  /**
   * Returns the theif from the passed position.
   *
   * @return thief
   */
  Thief getThief(Position position);

  /**
   * Returns the moving monster at the location.
   */
  Monster getMovingMonster(Position position);

  /**
   * Returns whether the current location has a moving monster or not.
   *
   * @return true if it has monster, else false
   */
  boolean hasMovingMonster(Position position);

  /**
   * Returns the percentage of caves that have a pit.
   *
   * @return percentage of pit
   */
  int getPitPercentage();

  /**
   * Returns the percentage of locations that have a thief.
   *
   * @return percentage of thief
   */
  int getThiefPercentage();

  /**
   * Returns the percentage of locations that have a moving monsters.
   *
   * @return percentage of moving monsters
   */
  int getMovingMonsterPercentage();
}
