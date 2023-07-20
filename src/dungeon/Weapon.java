package dungeon;

/**
 * Enum for having the only type of weapon.
 */
public enum Weapon {
  CROOKED_ARROW(5, 1);

  private final int distance;
  private final int hitPoints;

  Weapon(int distance, int hitPoints) {
    this.distance = distance;
    this.hitPoints = hitPoints;
  }

  public int getDistance() {
    return distance;
  }

  public int getHitPoints() {
    return hitPoints;
  }
}
