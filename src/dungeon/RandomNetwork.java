package dungeon;

import java.util.Random;

/**
 * Helper class for generating random number in range.
 * Other methods can be added later by extending this class.
 */
public class RandomNetwork {

  private static final Random random = new Random();

  /**
   * Returns a random integer generated within the bounds.
   *
   * @param bound bound limit
   * @return random integer
   */
  public static int nextInt(int bound) {
    if (bound <= 0) {
      throw new IllegalArgumentException("Invalid bounds!");
    }
    return random.nextInt(bound);
  }
}
