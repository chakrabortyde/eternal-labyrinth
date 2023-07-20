package dungeon;

/**
 * An enhanced version of the Controller interface that allows for GUI.
 */
public interface EnhancedController extends Controller {

  /**
   * Moves the player to the position passed.
   * Calls the movePlayer method from GameState.
   */
  void handlePlayerMove(int x, int y);

  /**
   * Moves the player in the direction passed.
   * Calls the movePlayer method from GameState.
   */
  void handlePlayerMove(Direction direction);

  /**
   * Picks the treasure present at the passed index at the location.
   * Calls the pickTreasure method from GameState.
   *
   * @param index index starting at 1
   */
  void pickTreasure(int index);

  /**
   * Picks the weapon present at the passed index at the location.
   * Calls the pickWeapon method from GameState.
   *
   * @param index index starting at 1
   */
  void pickWeapon(int index);

  /**
   * Returns the message containing information about what happens when a weapon is fired.
   * Calls the shoot method from GameState.
   *
   * @param weapon weapon that is fired
   * @param power power of the shot
   * @param direction direction of the shot
   */
  void shoot(Weapon weapon, int power, Direction direction);

  /**
   * Restarts the game from the start position.
   */
  void restartPlayer();

  /**
   * Checks whether the player is in a wait state after being killed.
   * If yes, the player can reset to the start.
   *
   * @return whether the player can wait
   */
  boolean isPlayerInWait();

  /**
   * Launches the controller from a driver.
   * Also needed to specify the properties of our game like:
   * <ul>
   *   <li>size of the dungeon</li>
   *   <li>interconnectivity</li>
   *   <li>whether it is wrapping or not</li>
   *   <li>the percentage of caves that have treasure</li>
   *   <li>difficulty (the number of Otyugh)</li>
   *   <li></li>
   *   <li></li>
   * </ul>
   */
  void playGame();
}
