package dungeon;

import java.io.IOException;

/**
 * The controller implements a text-based adventure game. It is able to:
 * <ul>
 *   <li>use the input from the user to</li>
 *   <ul>
 *     <li>navigate the player through the dungeon</li>
 *     <li>pick up treasure and/or arrows if they are found in the same location as the player</li>
 *     <li>shoot an arrow in a given direction</li>
 *   </ul>
 *   <li>Output clues about the nearby caves and other relevant aspects of the
 *   current game state without dumping the dungeon map to the screen.</li>
 * </ul>
 */
public interface Controller {

  /**
   * Launches the controller from a driver. Also needed to specify the properties of our game like:
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
  void playGame() throws IOException;
}
