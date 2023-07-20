package dungeon;

/**
 * A dungeon is represented by an m x n two-dimensional grid. Dungeons
 * are generated at random following some set of constraints resulting
 * in a different network each time the game begins. This interface is
 * a read-write version of the same model currently in use.
 */
public interface Model extends ReadOnlyModel {

  /**
   * Returns whether there exists a legal path from and to the passed locations.
   *
   * @param l1 location from
   * @param l2 location to
   * @return whether the path exists
   */
  boolean hasLegalPath(Location l1, Location l2);

  /**
   * Randomly adds treasures to the passed percentage of caves.
   *
   * @param percentage percentage of caves
   */
  void addTreasure(int percentage);

  /**
   * Randomly the passed number of monsters type to the caves.
   *
   * @param percentage percentage of caves
   */
  void addMonster(int percentage);

  /**
   * Randomly adds weapons to the passed percentage of caves.
   *
   * @param percentage percentage of caves
   */
  void addWeapon(int percentage);

  /**
   * Returns a copy of the current game state.
   *
   * @return current game state
   */
  GameState getCurrentGameState();

  /**
   * Returns a copy of the pit percentage.
   *
   * @param pitPercentage pit percentage
   */
  void addPit(int pitPercentage);

  /**
   * Returns a copy of the pit percentage.
   *
   * @param thiefPercentage thief percentage
   */
  void addThief(int thiefPercentage);

  /**
   * Returns a copy of the moving monsters percentage.
   *
   * @param movingMonsterPercentage moving monster percentage
   */
  void addMovingMonster(int movingMonsterPercentage);

  /**
   * Adds the passed moving monster to the location.
   *
   * @param monster monster to be added
   * @return Returns whether the add was successful or not.
   */
  boolean addMovingMonster(Position position, Monster monster);

  /**
   * Returns a generated start and end position that follows the requirements of >5 length.
   *
   * @return a two value array of start and end positions
   */
  Position[] generateStartAndEnd();

  /**
   * Allows the player to pick up a treasure from index.
   *
   * @param index treasure position
   * @return Returns whether the pickup was successful or not.
   */
  boolean pickTreasure(int index);

  /**
   * Allows the player to pick up a weapon from index.
   *
   * @param index weapon position
   * @return Returns whether the pickup was successful or not.
   */
  boolean pickWeapon(int index);

  /**
   * Removes the moving monster at the location.
   */
  void removeMovingMonster(Position position);

  /**
   * Returns whether the passed start and end positions are illegal.
   *
   * @param start start position
   * @param end end position
   * @return whether the positions are legal
   */
  boolean isIllegalStartAndEnd(Position start, Position end);

  /**
   * Returns a created player for the game and enters it at the start position.
   *
   */
  void createPlayer();

  /**
   * Returns the message containing information about what happens when a weapon is fired.
   *
   * @param weapon weapon that is fired
   * @param power power of the shot
   * @param direction direction of the shot
   * @return message status
   */
  StringBuilder shoot(Weapon weapon, int power, Direction direction);
}
