package dungeon;

import java.util.ArrayList;
import java.util.List;

class CurrentGameState implements GameState {

  private final Player player;
  private final Model dungeon;
  private final Position goalState;
  private final List<Position> removedMonsters;
  private final List<Position> visitedPositions;
  private List<Location> movingMonsterState;

  CurrentGameState(Player player, Model dungeon, Position goalState) {
    this.player = player;
    this.dungeon = dungeon;
    this.goalState = goalState;
    removedMonsters = new ArrayList<>();
    visitedPositions = new ArrayList<>();
    movingMonsterState = new ArrayList<>();
  }

  @Override
  public boolean isGameOver() {
    return player.getPosition().equals(goalState);
  }

  @Override
  public Player getPlayer() {
    return player;
  }

  @Override
  public Location getPlayerLocation() {
    return dungeon.getGrid().getLocation(player.getPosition().getX(), player.getPosition().getY());
  }

  @Override
  public List<Position> getRemovedMonsters() {
    return new ArrayList<>(removedMonsters);
  }

  @Override
  public Location getLocation(Position position) {
    return dungeon.getGrid().getLocation(position.getX(), position.getY());
  }

  @Override
  public boolean pickTreasure(int index) {
    if (getPlayerLocation().hasTreasure()) {
      Treasure treasure = getPlayerLocation().getTreasures().get(index);
      player.pickTreasure(treasure);
      return getPlayerLocation().getTreasures().remove(treasure);
    }
    return false;
  }

  @Override
  public boolean pickWeapon(int index) {
    if (getPlayerLocation().hasWeapon()) {
      Weapon weapon = getPlayerLocation().getWeapons().get(index);
      player.pickWeapon(weapon);
      return getPlayerLocation().getWeapons().remove(weapon);
    }
    return false;
  }

  @Override
  public List<Treasure> getPlayerTreasures() {
    return player.getTreasures();
  }

  @Override
  public void movePlayer(Direction direction) {
    getValidActions().stream()
            .filter(a -> a.getDirection().equals(direction))
            .findFirst()
            .ifPresent(a -> {
              visitedPositions.add(a.getPosition());
              player.move(a.getPosition());
            });
    List<Location> tmp = new ArrayList<>();
    for (Location state : movingMonsterState) {
      List<Action> actions = state.getValidActions();
      Action a = actions.get(RandomNetwork.nextInt(actions.size()));
      Location l = getLocation(a.getPosition());
      l.addMovingMonster(Monster.BEHOLDER);
      state.removeMovingMonster();
      tmp.add(l);
    }
    movingMonsterState = new ArrayList<>(tmp);
  }

  @Override
  public List<Action> getValidActions() {
    return dungeon.getGrid().getLocation(
            player.getPosition().getX(), player.getPosition().getY()).getValidActions();
  }

  private List<Action> getValidActions(Location location) {
    return dungeon.getGrid().getLocation(
            location.getPosition().getX(), location.getPosition().getY()).getValidActions();
  }

  private Position getNextPositionInDirection(Location location, Direction direction) {
    for (Action action : getValidActions(location)) {
      if (action.getDirection().equals(direction)) {
        return action.getPosition();
      }
    }
    return null;
  }

  @Override
  public boolean isSmellingLessPungentNearby() {
    int monsterCount = 0;
    for (Action a1 : getValidActions()) {
      for (Action a2 : getValidActions(getLocation(a1.getPosition()))) {
        if (getLocation(a2.getPosition()).hasMonster()) {
          monsterCount++;
        }
      }
    }
    return monsterCount == 1;
  }

  @Override
  public boolean isSmellingMorePungentNearby() {
    int innerMonsterCount = 0;
    int outerMonsterCount = 0;
    for (Action a1 : getValidActions()) {
      if (getLocation(a1.getPosition()).hasMonster()) {
        innerMonsterCount++;
      }
      for (Action a2 : getValidActions(getLocation(a1.getPosition()))) {
        if (getLocation(a2.getPosition()).hasMonster()) {
          outerMonsterCount++;
        }
      }
    }
    return innerMonsterCount > 0 || outerMonsterCount > 1;
  }

  @Override
  public boolean isSmellingPetrichorNearby() {
    for (Action a : getValidActions()) {
      if (getLocation(a.getPosition()).hasPit()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public StringBuilder shoot(Weapon weapon, int power, Direction direction) {
    if (power < 1 || power > weapon.getDistance()) {
      throw new IllegalArgumentException("Invalid input!");
    }
    if (player.getCrookedArrowsLeft() > 0) {
      player.removeWeapon();
      Position position = getPlayerLocation().getPosition();
      while (position != null && power > 0) {
        List<Action> validActions = getValidActions(getLocation(position));
        if (validActions.size() == 2) {
          for (Action a : validActions) {
            if (!a.getDirection().reverse().equals(direction)) {
              position = getNextPositionInDirection(getLocation(position), a.getDirection());
              direction = a.getDirection();
              break;
            }
          }
        } else {
          boolean nextExists = false;
          for (Action a : validActions) {
            if (a.getDirection().equals(direction)) {
              position = getNextPositionInDirection(getLocation(position), a.getDirection());
              nextExists = true;
              break;
            }
          }
          if (!nextExists) {
            Location location = getLocation(position);
            if (location.hasMonster()) {
              if (location.getMHealth() == 2) {
                location.hit(weapon.getHitPoints());
                return new StringBuilder("You hear a small ouch in the distance"
                        + " and the arrow hitting a wall\n");
              } else {
                removedMonsters.add(position);
                location.removeMonster();
                return new StringBuilder("You hear a great howl in the distance"
                        + " and the arrow hitting a wall\n");
              }
            } else {
              return new StringBuilder("You hear the arrow hitting a wall\n");
            }
          }
        }
        power--;
      }
      if (position != null && getLocation(position).hasMonster()) {
        Location location = getLocation(position);
        if (location.getMHealth() == 2) {
          location.hit(weapon.getHitPoints());
          return new StringBuilder("You hear a small ouch in the distance"
                  + " and the arrow hitting the ground\n");
        } else {
          removedMonsters.add(position);
          location.removeMonster();
          return new StringBuilder("You hear a great howl in the distance"
                  + " and the arrow hitting the ground\n");
        }
      } else {
        return new StringBuilder("You hear the arrow hitting the ground\n");
      }
    } else {
      return new StringBuilder("You are out of arrows, explore to find more\n");
    }
  }

  @Override
  public List<Position> getVisitedPositions() {
    return visitedPositions;
  }

  @Override
  public void addMovingMonsterState(Location location) {
    movingMonsterState.add(location);
  }

  @Override
  public void removeMovingMonsterState() {
    movingMonsterState.remove(getPlayerLocation());
  }

  @Override
  public void resetPlayer() {
    player.move(dungeon.getStart());
  }
}
