package dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A cave is a location with 1, 3 or 4 entrances.
 * A cave can have more than one treasure.
 */
class Cave implements Location {

  private Pit pit;
  private Thief thief;
  private int mHealth;
  private int mmHealth;
  private Monster monster;
  private Monster movingMonster;
  private final Position position;
  private final List<Weapon> weapons;
  private final List<Treasure> treasures;
  private final List<Action> validActions;

  Cave(Position position, List<Action> validActions) {
    if (position == null) {
      throw new IllegalArgumentException("Invalid position!");
    }
    if (validActions == null) {
      throw new IllegalArgumentException("Invalid valid actions!");
    }
    this.position = position;
    this.validActions = validActions;
    weapons = new ArrayList<>();
    treasures = new ArrayList<>();
  }

  @Override
  public Position getPosition() {
    return position;
  }

  @Override
  public boolean hasTreasure() {
    return !treasures.isEmpty();
  }

  @Override
  public boolean addTreasure(Treasure treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("Invalid treasure!");
    }
    return treasures.add(treasure);
  }

  @Override
  public boolean hasMonster() {
    return (monster != null);
  }

  @Override
  public boolean addMonster(Monster monster) {
    if (monster == null) {
      throw new IllegalArgumentException("Invalid monster!");
    }
    this.monster = monster;
    this.mHealth = monster.getHealthPoints();
    return true;
  }

  @Override
  public Monster getMonster() {
    return monster;
  }

  @Override
  public void removeMonster() {
    mHealth = 0;
    monster = null;
  }

  @Override
  public boolean hasMovingMonster() {
    return (movingMonster != null);
  }

  @Override
  public boolean addMovingMonster(Monster monster) {
    if (monster == null) {
      throw new IllegalArgumentException("Invalid monster!");
    }
    this.movingMonster = monster;
    this.mmHealth = monster.getHealthPoints();
    return true;
  }

  @Override
  public Monster getMovingMonster() {
    return movingMonster;
  }

  @Override
  public void removeMovingMonster() {
    mmHealth = 0;
    movingMonster = null;
  }

  @Override
  public int getMHealth() {
    return mHealth;
  }

  @Override
  public int getMmHealth() {
    return mmHealth;
  }

  @Override
  public void hit(int hitPoints) {
    mHealth -= hitPoints;
  }

  @Override
  public boolean hasWeapon() {
    return !weapons.isEmpty();
  }

  @Override
  public List<Weapon> getWeapons() {
    return weapons;
  }

  @Override
  public boolean addWeapon(Weapon weapon) {
    if (weapon == null) {
      throw new IllegalArgumentException("Invalid weapon!");
    }
    return weapons.add(weapon);
  }

  @Override
  public List<Treasure> getTreasures() {
    return treasures;
  }

  @Override
  public List<Action> getValidActions() {
    return validActions;
  }

  @Override
  public boolean hasPit() {
    return pit != null;
  }

  @Override
  public boolean addPit(Pit pit) {
    if (pit == null) {
      throw new IllegalArgumentException("Invalid pit!");
    }
    this.pit = pit;
    return true;
  }

  @Override
  public boolean hasThief() {
    return thief != null;
  }

  @Override
  public boolean addThief(Thief thief) {
    if (thief == null) {
      throw new IllegalArgumentException("Invalid pit!");
    }
    this.thief = thief;
    return true;
  }

  @Override
  public Pit getPit() {
    return pit;
  }

  @Override
  public Thief getThief() {
    return thief;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Location)) {
      return false;
    }
    Location location = (Location) o;
    return Objects.equals(position, location.getPosition());
  }

  @Override
  public int hashCode() {
    return Objects.hash(position);
  }

  @Override
  public String toString() {
    return "Cave";
  }
}
