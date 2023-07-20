package dungeon;

/**
 * Enum for having all the three types of treasure.
 */
public enum Treasure {
  RUBY(1), SAPPHIRE(5), DIAMOND(10);

  Treasure(int value) {
    if (value < 0) {
      throw new IllegalArgumentException("Invalid value!");
    }
  }
}
