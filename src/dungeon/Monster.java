package dungeon;

/**
 * Enum for having the only type of monster.
 */
public enum Monster {
  OTYUGH(2), BEHOLDER(1);

  private final int healthPoints;

  Monster(int healthPoints) {
    this.healthPoints = healthPoints;
  }

  int getHealthPoints() {
    return healthPoints;
  }
}
