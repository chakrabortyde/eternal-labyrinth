package dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A dungeon is represented by an m x n two-dimensional grid. Dungeons
 * are generated at random following some set of constraints resulting
 * in a different network each time the game begins.
 * <ul>
 *   <li>There is a path from every cave in the dungeon to every other cave in the dungeon.</li>
 *   <li>Each dungeon is constructed with a degree of interconnectivity.</li>
 *   <li>Increasing the interconnectivity increases the number of paths between caves.</li>
 *   <li>Not all dungeons "wrap" from one side to the other.</li>
 * </ul>
 */
public class Dungeon implements Model {

  private Position end;
  private Position start;
  private final Grid grid;
  private final Location[][] map;
  private final int row;
  private final int col;
  private Player player;
  private int pitPercentage;
  private int thiefPercentage;
  private int weaponPercentage;
  private int monsterPercentage;
  private int treasurePercentage;
  private final boolean isWrapping;
  private GameState currentGameState;
  private final int interconnectivity;
  private int movingMonsterPercentage;

  /**
   * Constructor for inititating a dungeon in the provided dimensions
   * and its interconnectivity, and whether it is wrapping or not.
   *
   * @param row height of the dungeon
   * @param col width of the dungeon
   * @param interconnectivity degree of interconnectivity
   * @param isWrapping whether is wrapping
   */
  public Dungeon(int row, int col, int interconnectivity, boolean isWrapping) {
    if (row < 1) {
      throw new IllegalArgumentException("Illegal dungeon row value!");
    }
    if (col < 1) {
      throw new IllegalArgumentException("Illegal dungeon col value!");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Illegal dungeon interconnectivity value!");
    }
    if (row < 6) {
      throw new IllegalArgumentException("Dungeon row value should be more than 5!");
    }
    if (col < 6) {
      throw new IllegalArgumentException("Dungeon col value should be more than 5!");
    }
    this.row = row;
    this.col = col;
    this.interconnectivity = interconnectivity;
    this.isWrapping = isWrapping;
    grid = new Grid2D(row, col);
    map = grid.generateMaze(interconnectivity, isWrapping);
  }

  /**
   * Constructor for inititating a dungeon in the provided dimensions
   * and its interconnectivity, and whether it is wrapping or not.
   *
   * @param row height of the dungeon
   * @param col width of the dungeon
   * @param interconnectivity degree of interconnectivity
   * @param isWrapping whether is wrapping
   * @param treasurePercentage percentage of treasure
   * @param monsterPercentage percentage of monster
   * @param weaponPercentage percentage of weapons
   * @param pitPercentage percentage of pits
   * @param thiefPercentage percentage of thief
   * @param movingMonsterPercentage percentage of moving monsters
   **/
  public Dungeon(int row, int col, int interconnectivity, boolean isWrapping,
                 int treasurePercentage, int monsterPercentage, int weaponPercentage,
                 int pitPercentage, int thiefPercentage, int movingMonsterPercentage) {
    this(row, col, interconnectivity, isWrapping);
    generateStartAndEnd();
    createPlayer();
    addPit(pitPercentage);
    addThief(thiefPercentage);
    addWeapon(weaponPercentage);
    addMonster(monsterPercentage);
    addTreasure(treasurePercentage);
    currentGameState = new CurrentGameState(player, this, end);
    currentGameState.getVisitedPositions().add(start);
    addMovingMonster(movingMonsterPercentage);
  }

  @Override
  public Grid getGrid() {
    return grid;
  }

  @Override
  public int getRows() {
    return row;
  }

  @Override
  public int getColumns() {
    return col;
  }

  @Override
  public int getInterconnectivity() {
    return interconnectivity;
  }

  @Override
  public boolean getIsWrapping() {
    return isWrapping;
  }

  @Override
  public int getTreasurePercentage() {
    return treasurePercentage;
  }

  @Override
  public int getMonsterPercentage() {
    return monsterPercentage;
  }

  @Override
  public int getWeaponsPercentage() {
    return weaponPercentage;
  }

  @Override
  public List<Position> getRemovedMonsters() {
    return currentGameState.getRemovedMonsters();
  }

  @Override
  public Location[][] getMap() {
    return map;
  }

  @Override
  public Position getStart() {
    return start;
  }

  @Override
  public Position getEnd() {
    return end;
  }

  @Override
  public Position getPlayerPosition() {
    return currentGameState.getPlayerLocation().getPosition();
  }

  @Override
  public List<Position> getValidPositions() {
    return currentGameState.getValidActions()
            .stream().map(Action::getPosition).collect(Collectors.toList());
  }

  @Override
  public List<Direction> getValidDirections() {
    return currentGameState.getValidActions()
            .stream().map(Action::getDirection).collect(Collectors.toList());
  }

  @Override
  public List<Action> getValidActions() {
    return currentGameState.getValidActions();
  }

  @Override
  public boolean hasTreasure() {
    return currentGameState.getPlayerLocation().hasTreasure();
  }

  @Override
  public List<Treasure> getTreasures() {
    return new ArrayList<>(currentGameState.getPlayerLocation().getTreasures());
  }

  @Override
  public boolean hasMonster() {
    return currentGameState.getPlayerLocation().hasMonster();
  }

  @Override
  public boolean hasMonster(Position position) {
    return currentGameState.getLocation(position).hasMonster();
  }

  @Override
  public Monster getMonster() {
    return currentGameState.getPlayerLocation().getMonster();
  }

  @Override
  public boolean hasWeapon() {
    return currentGameState.getPlayerLocation().hasWeapon();
  }

  @Override
  public List<Weapon> getWeapons() {
    return new ArrayList<>(currentGameState.getPlayerLocation().getWeapons());
  }

  @Override
  public List<Treasure> getPlayerTreasure() {
    return new ArrayList<>(player.getTreasures());
  }

  @Override
  public List<Weapon> getPlayerWeapon() {
    return new ArrayList<>(player.getWeapons());
  }

  @Override
  public int getPlayerHealth() {
    return player.getHealth();
  }

  @Override
  public List<Position> getVisitedPositions() {
    return new ArrayList<>(currentGameState.getVisitedPositions());
  }

  @Override
  public boolean isSmellingLessPungentNearby() {
    return currentGameState.isSmellingLessPungentNearby();
  }

  @Override
  public boolean isSmellingMorePungentNearby() {
    return currentGameState.isSmellingMorePungentNearby();
  }

  @Override
  public boolean isSmellingPetrichorNearby() {
    return currentGameState.isSmellingPetrichorNearby();
  }

  @Override
  public boolean hasPit(Position position) {
    return currentGameState.getLocation(position).hasPit();
  }

  @Override
  public boolean hasThief(Position position) {
    return currentGameState.getLocation(position).hasThief();
  }

  @Override
  public Pit getPit(Position position) {
    return currentGameState.getLocation(position).getPit();
  }

  @Override
  public Thief getThief(Position position) {
    return currentGameState.getLocation(position).getThief();
  }

  @Override
  public Monster getMovingMonster(Position position) {
    return currentGameState.getLocation(position).getMovingMonster();
  }

  @Override
  public boolean hasMovingMonster(Position position) {
    return currentGameState.getLocation(position).hasMovingMonster();
  }

  @Override
  public int getPitPercentage() {
    return pitPercentage;
  }

  @Override
  public int getThiefPercentage() {
    return thiefPercentage;
  }

  @Override
  public int getMovingMonsterPercentage() {
    return movingMonsterPercentage;
  }

  @Override
  public boolean addMovingMonster(Position position, Monster monster) {
    return currentGameState.getLocation(position).addMovingMonster(monster);
  }

  @Override
  public void addMovingMonster(int percentage) {
    movingMonsterPercentage = percentage;
    if (percentage < 1 || percentage > 100) {
      throw new IllegalArgumentException("Invalid percentage!");
    }
    int timeout = 1000;
    int totalLocationsWithMovingMonster =
            percentage * (grid.getTotalCaves() + grid.getTotalTunnel()) / 100;
    while (totalLocationsWithMovingMonster > 0 && timeout > 0) {
      Location location = map[RandomNetwork.nextInt(grid.getRow())]
              [RandomNetwork.nextInt(grid.getCol())];
      if (!location.hasMovingMonster()
              && !location.getPosition().equals(start) && !location.getPosition().equals(end)) {
        if (location.addMovingMonster(Monster.BEHOLDER)) {
          currentGameState.addMovingMonsterState(location);
          totalLocationsWithMovingMonster--;
        }
      }
      timeout--;
    }
  }

  @Override
  public void removeMovingMonster(Position position) {
    currentGameState.getLocation(position).removeMovingMonster();
  }

  @Override
  public GameState getCurrentGameState() {
    return currentGameState;
  }

  @Override
  public boolean hasLegalPath(Location l1, Location l2) {
    if (l1 == null) {
      throw new IllegalArgumentException("Invalid location!");
    }
    if (l2 == null) {
      throw new IllegalArgumentException("Invalid location!");
    }
    return l1.getValidActions().stream().anyMatch(a -> (a.getPosition().equals(l2.getPosition())));
  }

  @Override
  public void addTreasure(int percentage) {
    treasurePercentage = percentage;
    if (percentage < 1 || percentage > 100) {
      throw new IllegalArgumentException("Invalid percentage!");
    }
    int totalCavesWithTreasure = percentage * grid.getTotalCaves() / 100;
    while (totalCavesWithTreasure != 0) {
      Location location = map[RandomNetwork.nextInt(grid.getRow())]
                                 [RandomNetwork.nextInt(grid.getCol())];
      int randomTreasureIndex = RandomNetwork.nextInt(Treasure.values().length);
      Treasure randomTreasure = Treasure.values()[randomTreasureIndex];
      if (location.getTreasures() != null && location.getTreasures().size() == 0) {
        if (location.addTreasure(randomTreasure)) {
          totalCavesWithTreasure--;
        }
      }
    }
  }

  @Override
  public void addMonster(int percentage) {
    monsterPercentage = percentage;
    if (percentage < 1 || percentage > 100) {
      throw new IllegalArgumentException("Invalid percentage!");
    }
    int timeout = 1000;
    int totalCavesWithMonster = percentage * grid.getTotalCaves() / 100;
    map[end.getX()][end.getY()].addMonster(Monster.OTYUGH);
    totalCavesWithMonster--;
    while (totalCavesWithMonster > 0 && timeout > 0) {
      Location location = map[RandomNetwork.nextInt(grid.getRow())]
              [RandomNetwork.nextInt(grid.getCol())];
      if (!location.hasMonster() && !location.getPosition().equals(start)) {
        if (location.addMonster(Monster.OTYUGH)) {
          totalCavesWithMonster--;
        }
      }
      timeout--;
    }
  }

  @Override
  public void addWeapon(int percentage) {
    weaponPercentage = percentage;
    if (percentage < 1 || percentage > 100) {
      throw new IllegalArgumentException("Invalid percentage!");
    }
    int totalCavesWithWeapons = percentage * grid.getTotalCaves() / 100;
    while (totalCavesWithWeapons > 0) {
      Location location = map[RandomNetwork.nextInt(grid.getRow())]
              [RandomNetwork.nextInt(grid.getCol())];
      int randomWeaponIndex = RandomNetwork.nextInt(Weapon.values().length);
      Weapon randomWeapon = Weapon.values()[randomWeaponIndex];
      if (location.getWeapons() != null
              && !location.getPosition().equals(start) && !location.getPosition().equals(end)) {
        if (location.addWeapon(randomWeapon)) {
          totalCavesWithWeapons--;
        }
      }
    }
  }

  @Override
  public void addPit(int percentage) {
    pitPercentage = percentage;
    if (percentage < 1 || percentage > 100) {
      throw new IllegalArgumentException("Invalid percentage!");
    }
    int timeout = 1000;
    int totalCavesWithPits = percentage * grid.getTotalCaves() / 100;
    while (totalCavesWithPits > 0 && timeout > 0) {
      Location location = map[RandomNetwork.nextInt(grid.getRow())]
              [RandomNetwork.nextInt(grid.getCol())];
      if (!location.hasPit()
              && !location.getPosition().equals(start) && !location.getPosition().equals(end)) {
        if (location.addPit(Pit.DEEP_PIT)) {
          totalCavesWithPits--;
        }
      }
      timeout--;
    }
  }

  @Override
  public void addThief(int percentage) {
    thiefPercentage = percentage;
    if (percentage < 1 || percentage > 100) {
      throw new IllegalArgumentException("Invalid percentage!");
    }
    int timeout = 1000;
    int totalLocationsWithThieves =
            percentage * (grid.getTotalCaves() + grid.getTotalTunnel()) / 100;
    while (totalLocationsWithThieves > 0 && timeout > 0) {
      Location location = map[RandomNetwork.nextInt(grid.getRow())]
              [RandomNetwork.nextInt(grid.getCol())];
      if (!location.hasThief()
              && !location.getPosition().equals(start) && !location.getPosition().equals(end)) {
        if (location.addThief(Thief.TREASURE_THIEF)) {
          totalLocationsWithThieves--;
        }
      }
      timeout--;
    }
  }

  @Override
  public Position[] generateStartAndEnd() {
    int timer = 1000;
    do {
      if (timer == 0) {
        throw new IllegalStateException("Start end make failed!");
      }
      start = getRandomPosition();
      end = getRandomPosition();
      timer--;
    }
    while (isIllegalStartAndEnd(start, end));
    return new Position[]{start, end};
  }

  @Override
  public boolean pickTreasure(int index) {
    return player.getTreasures().size() < 4 && currentGameState.pickTreasure(index);
  }

  @Override
  public boolean pickWeapon(int index) {
    return player.getWeapons().size() < 4 && currentGameState.pickWeapon(index);
  }

  @Override
  public boolean isIllegalStartAndEnd(Position start, Position end) {
    if (start == null) {
      throw new IllegalArgumentException("Invalid start!");
    }
    if (end == null) {
      throw new IllegalArgumentException("Invalid end!");
    }
    return grid.getLocation(start) instanceof Tunnel
              || grid.getLocation(end) instanceof Tunnel
              || Math.abs(start.getX() - end.getX()) < 5
              || Math.abs(start.getY() - end.getY()) < 5;
  }

  private Position getRandomPosition() {
    return new Position(RandomNetwork.nextInt(grid.getRow()), RandomNetwork.nextInt(grid.getCol()));
  }

  @Override
  public void createPlayer() {
    player = new PlayerController(start);
  }

  @Override
  public StringBuilder shoot(Weapon weapon, int power, Direction direction) {
    return currentGameState.shoot(weapon, power, direction);
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (Location[] row : map) {
      for (Location col : row) {
        stringBuilder.append(String.format("%-10s", col));
      }
      stringBuilder.append("\n");
    }
    return stringBuilder.toString();
  }
}
