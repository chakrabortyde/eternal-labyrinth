package dungeon;

import java.util.List;

/**
 * Keeps a state of the current game being played starting all required positions,
 * states, locations, treasure, weapons, pits, thieved, monsters and the player.
 */
public interface GameState {

  /**
   * Returns whether the game is over based on certain predefined conditions.
   *
   * @return true, if game is over, else false
   */
  boolean isGameOver();

  /**
   * Returns the current player of the game.
   *
   * @return player
   */
  Player getPlayer();

  /**
   * Returns the location of the player at the current game state.
   *
   * @return current location
   */
  Location getPlayerLocation();

  /**
   * Returns a list of all monsters that have been killed.
   *
   * @return list of removed monsters
   */
  List<Position> getRemovedMonsters();

  /**
   * Returns the location at the passed position.
   *
   * @param position position
   * @return location
   */
  Location getLocation(Position position);

  /**
   * Picks the treasure present at the passed index at the location.
   *
   * @param index index starting at 1
   * @return true if pickup is successful, else false.
   */
  boolean pickTreasure(int index);

  /**
   * Picks the weapon present at the passed index at the location.
   *
   * @param index index starting at 1
   * @return true if pickup is successful, else false.
   */
  boolean pickWeapon(int index);

  /**
   * Returns a list of all the treasures at the location in the current game state.
   *
   * @return list of treasures
   */
  List<Treasure> getPlayerTreasures();

  /**
   * Moves the player one position in the direction passed.
   *
   * @param direction direction
   */
  void movePlayer(Direction direction);

  /**
   * Returns a list of valid actions at the location in the current game state.
   *
   * @return list of actions
   */
  List<Action> getValidActions();

  /**
   * Returns whether it smells less pungent in the current location.
   *
   * @return true, if it smells less pungent, else false
   */
  boolean isSmellingLessPungentNearby();

  /**
   * Returns whether it smells more pungent in the current location.
   *
   * @return true, if it smells more pungent, else false
   */
  boolean isSmellingMorePungentNearby();

  /**
   * Returns whether it is smelling petrichor nearby.
   *
   * @return is smelling petrichor
   */
  boolean isSmellingPetrichorNearby();

  /**
   * Returns the message containing information about what happens when a weapon is fired.
   *
   * @param weapon weapon that is fired
   * @param power power of the shot
   * @param direction direction of the shot
   * @return message status
   */
  StringBuilder shoot(Weapon weapon, int power, Direction direction);

  /**
   * Returns a list of visited positions by the player.
   *
   * @return lits of visited positions
   */
  List<Position> getVisitedPositions();

  /**
   * Adds the location to the state list containing all monsters.
   *
   * @param location monster location
   */
  void addMovingMonsterState(Location location);

  /**
   * Removes the moving monster state.
   */
  void removeMovingMonsterState();

  /**
   * Restarts the game from the start position.
   */
  void resetPlayer();
}
