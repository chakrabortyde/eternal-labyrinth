import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import dungeon.Dungeon;
import dungeon.Location;
import dungeon.Monster;
import dungeon.Position;
import dungeon.Treasure;
import dungeon.Weapon;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for testing demonstration of the Dungeon gameplay.
 */
public class DungeonTest {

  private Dungeon dungeon;

  /**
   * Sets up a 6 x 8 wrapping dungeon with 48 interconnectivity.
   */
  @Before
  public void setUp() {
    dungeon = new Dungeon(6, 8, 48, true);
  }

  /**
   * Tests if the Dungeon constructor is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalDungeonNegativeRows() {
    new Dungeon(-1, 8, 0, true);
    fail("Exception should have occurred!");
  }

  /**
   * Tests if the Dungeon constructor is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalDungeonNegativeCols() {
    new Dungeon(6, -1, 0, true);
    fail("Exception should have occurred!");
  }

  /**
   * Tests if the Dungeon constructor is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalDungeonNegativeInterconnectivity() {
    new Dungeon(6, 8, -1, true);
    fail("Exception should have occurred!");
  }

  /**
   * Tests if the Dungeon constructor is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalDungeonTooLessRows() {
    new Dungeon(3, 8, 0, true);
    fail("Exception should have occurred!");
  }

  /**
   * Tests if the Dungeon constructor is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalDungeonTooLessCols() {
    new Dungeon(6, 3, 0, true);
    fail("Exception should have occurred!");
  }

  /**
   * Tests if the Dungeon interconnectivity is working correctly.
   */
  @Test
  public void testLegalInterconnectivity() {
    Dungeon dungeon = new Dungeon(6, 8, 192, true);
    boolean interconnected = true;
    for (int i = 0; i < 6; i++ ) {
      for (int j = 0; j < 8; j++) {
        interconnected = dungeon.getMap()[i][j].getValidActions().size() == 4;
      }
    }
    assertTrue(interconnected);
  }

  /**
   * Tests if the Dungeon wrapping behaviour is working correctly.
   */
  @Test
  public void testWrappingDungeonWrappingBehaviour() {
    Dungeon dungeon = new Dungeon(6, 8, 48, true);
    Location[][] dungeonMap = dungeon.getMap();
    assertTrue(dungeon.hasLegalPath(dungeonMap[0][0], dungeonMap[5][0]));
    assertFalse(dungeon.hasLegalPath(dungeonMap[0][0], dungeonMap[5][5]));
  }

  /**
   * Tests if the Dungeon wrapping behaviour is working correctly.
   */
  @Test
  public void testNonWrappingDungeonNonWrappingBehaviour() {
    Dungeon dungeon = new Dungeon(6, 8, 0, false);
    Location[][] dungeonMap = dungeon.getMap();
    assertFalse(dungeon.hasLegalPath(dungeonMap[0][0], dungeonMap[5][0]));
  }

  /**
   * Tests if the Dungeon wrapping behaviour is working correctly.
   */
  @Test
  public void testWrappingDungeonNonWrappingBehaviour() {
    Dungeon dungeon = new Dungeon(6, 8, 48, true);
    Location[][] dungeonMap = dungeon.getMap();
    assertTrue(dungeon.hasLegalPath(dungeonMap[0][0], dungeonMap[0][7]));
  }

  /**
   * Tests if the Dungeon wrapping behaviour is working correctly.
   */
  @Test
  public void testNonWrappingDungeonWrappingBehaviour() {
    Dungeon dungeon = new Dungeon(6, 8, 0, false);
    Location[][] dungeonMap = dungeon.getMap();
    assertFalse(dungeon.hasLegalPath(dungeonMap[0][0], dungeonMap[0][7]));
  }

  /**
   * Tests if the start and end position generation is working correctly.
   */
  @Test
  public void testStartAndEndPositionsEqualityWrapping() {
    Dungeon dungeon = new Dungeon(6, 8, 48, true);
    Position[] positions = dungeon.generateStartAndEnd();
    assertNotEquals(positions[0], positions[1]);
  }

  /**
   * Tests if the start and end position generation is working correctly.
   */
  @Test
  public void testStartAndEndPositionsEqualityNonWrapping() {
    Dungeon dungeon = new Dungeon(8, 8, 0, false);
    Position[] positions = dungeon.generateStartAndEnd();
    assertNotEquals(positions[0], positions[1]);
  }

  /**
   * Tests if the start and end position generation is working correctly.
   */
  @Test
  public void testStartAndEndPositionsPathLengthWrapping() {
    Position[] positions = dungeon.generateStartAndEnd();
    assertTrue(Math.abs(
            Math.hypot(
                    positions[0].getX() - positions[1].getX(),
                    positions[0].getY() - positions[1].getY())) > 5);
  }

  /**
   * Tests if the start and end position generation is working correctly.
   */
  @Test
  public void testStartAndEndPositionsPathLengthNonWrapping() {
    Position[] positions = dungeon.generateStartAndEnd();
    assertTrue(Math.abs(
            Math.hypot(
                    positions[0].getX() - positions[1].getX(),
                    positions[0].getY() - positions[1].getY())) > 5);
  }

  /**
   * Tests if the adding treasure process is working correctly.
   */
  @Test
  public void testAddingTreasure25() {
    dungeon.addTreasure(25);
    double numCavesHaveTreasure = 0;
    double numCavesShouldHaveTreasure = 0.25 * dungeon.getGrid().getTotalCaves();
    for (int i = 0; i < 6; i++ ) {
      for (int j = 0; j < 8; j++) {
        if (dungeon.getMap()[i][j].hasTreasure()) {
          numCavesHaveTreasure++;
        }
      }
    }
    assertEquals(numCavesHaveTreasure, numCavesShouldHaveTreasure, 1);
  }

  /**
   * Tests if the adding treasure process is working correctly.
   */
  @Test
  public void testAddingTreasure50() {
    dungeon.addTreasure(50);
    double numCavesHaveTreasure = 0;
    double numCavesShouldHaveTreasure = 0.5 * dungeon.getGrid().getTotalCaves();
    for (int i = 0; i < 6; i++ ) {
      for (int j = 0; j < 8; j++) {
        if (dungeon.getMap()[i][j].hasTreasure()) {
          numCavesHaveTreasure++;
        }
      }
    }
    assertEquals(numCavesHaveTreasure, numCavesShouldHaveTreasure, 1);
  }

  /**
   * Tests if the adding treasure process is working correctly.
   */
  @Test
  public void testAddingTreasure100() {
    dungeon.addTreasure(100);
    double numCavesHaveTreasure = 0;
    double numCavesShouldHaveTreasure = dungeon.getGrid().getTotalCaves();
    for (int i = 0; i < 6; i++ ) {
      for (int j = 0; j < 8; j++) {
        if (dungeon.getMap()[i][j].hasTreasure()) {
          numCavesHaveTreasure++;
        }
      }
    }
    assertEquals(numCavesHaveTreasure, numCavesShouldHaveTreasure, 1);
  }

  /**
   * Tests if the adding treasure percentage is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingTreasureNegative() {
    dungeon.addTreasure(-1);
    fail("Exception should have occurred!");
  }

  /**
   * Tests if the adding treasure percentage is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingTreasureOverHundred() {
    dungeon.addTreasure(101);
    fail("Exception should have occurred!");
  }

  /**
   * Tests if the adding treasure manually is working correctly.
   */
  @Test
  public void testAddingTreasureToLocation() {
    Location location11 = dungeon.getMap()[1][1];
    location11.addTreasure(Treasure.DIAMOND);
    assertTrue(location11.getTreasures().contains(Treasure.DIAMOND));
  }

  /**
   * Tests if the adding treasure manually is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingNullTreasureToLocation() {
    Location location11 = dungeon.getMap()[1][1];
    location11.addTreasure(null);
    fail("Exception should have occurred!");
  }

  /**
   * Tests if the adding treasure process is working correctly.
   */
  @Test
  public void testAddingMonster25() {
    dungeon = new Dungeon(8, 8, 64, false);
    dungeon.generateStartAndEnd();
    dungeon.addMonster(25);
    double numCavesHaveMonsters = 0;
    double numCavesShouldHaveMonsters = 0.25 * dungeon.getGrid().getTotalCaves();
    for (int i = 0; i < 8; i++ ) {
      for (int j = 0; j < 8; j++) {
        if (dungeon.getMap()[i][j].hasMonster()) {
          numCavesHaveMonsters++;
        }
      }
    }
    assertEquals(numCavesHaveMonsters, numCavesShouldHaveMonsters, 1);
  }

  /**
   * Tests if the adding treasure process is working correctly.
   */
  @Test
  public void testAddingMonster50() {
    dungeon = new Dungeon(8, 8, 64, false);
    dungeon.generateStartAndEnd();
    dungeon.addMonster(50);
    double numCavesHaveMonsters = 0;
    double numCavesShouldHaveMonsters = 0.5 * dungeon.getGrid().getTotalCaves();
    for (int i = 0; i < 8; i++ ) {
      for (int j = 0; j < 8; j++) {
        if (dungeon.getMap()[i][j].hasMonster()) {
          numCavesHaveMonsters++;
        }
      }
    }
    assertEquals(numCavesHaveMonsters, numCavesShouldHaveMonsters, 1);
  }

  /**
   * Tests if the adding treasure process is working correctly.
   */
  @Test
  public void testAddingMonster100() {
    dungeon = new Dungeon(8, 8, 64, false);
    dungeon.generateStartAndEnd();
    dungeon.addMonster(100);
    double numCavesHaveMonsters = 0;
    double numCavesShouldHaveMonsters = dungeon.getGrid().getTotalCaves();
    for (int i = 0; i < 8; i++ ) {
      for (int j = 0; j < 8; j++) {
        if (dungeon.getMap()[i][j].hasMonster()) {
          numCavesHaveMonsters++;
        }
      }
    }
    assertEquals(numCavesHaveMonsters, numCavesShouldHaveMonsters, 1);
  }

  /**
   * Tests if the adding treasure percentage is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingMonsterNegative() {
    dungeon.addMonster(-1);
    fail("Exception should have occurred!");
  }

  /**
   * Tests if the adding treasure percentage is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingMonsterOverHundred() {
    dungeon.addMonster(101);
    fail("Exception should have occurred!");
  }

  /**
   * Tests if the adding treasure manually is working correctly.
   */
  @Test
  public void testAddingMonsterToLocation() {
    dungeon = new Dungeon(8, 8, 64, false);
    Location location11 = dungeon.getMap()[1][1];
    location11.addMonster(Monster.OTYUGH);
    assertEquals(Monster.OTYUGH, location11.getMonster());
  }

  /**
   * Tests if the adding treasure manually is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingNullMonsterToLocation() {
    Location location11 = dungeon.getMap()[1][1];
    location11.addMonster(null);
    fail("Exception should have occurred!");
  }

  /**
   * Tests if the adding treasure process is working correctly.
   */
  @Test
  public void testAddingWeapon25() {
    dungeon = new Dungeon(8, 8, 64, false);
    dungeon.generateStartAndEnd();
    dungeon.addWeapon(25);
    double numCavesHaveWeapons = 0;
    double numCavesShouldHaveWeapons = 0.25 * dungeon.getGrid().getTotalCaves();
    for (int i = 0; i < 8; i++ ) {
      for (int j = 0; j < 8; j++) {
        if (dungeon.getMap()[i][j].hasWeapon()) {
          numCavesHaveWeapons += dungeon.getMap()[i][j].getWeapons().size();
        }
      }
    }
    assertEquals(numCavesHaveWeapons, numCavesShouldHaveWeapons, 1);
  }

  /**
   * Tests if the adding treasure process is working correctly.
   */
  @Test
  public void testAddingWeapon50() {
    dungeon = new Dungeon(8, 8, 64, false);
    dungeon.generateStartAndEnd();
    dungeon.addWeapon(50);
    double numCavesHaveWeapons = 0;
    double numCavesShouldHaveWeapons = 0.5 * dungeon.getGrid().getTotalCaves();
    for (int i = 0; i < 8; i++ ) {
      for (int j = 0; j < 8; j++) {
        if (dungeon.getMap()[i][j].hasWeapon()) {
          numCavesHaveWeapons += dungeon.getMap()[i][j].getWeapons().size();
        }
      }
    }
    assertEquals(numCavesHaveWeapons, numCavesShouldHaveWeapons, 1);
  }

  /**
   * Tests if the adding treasure process is working correctly.
   */
  @Test
  public void testAddingWeapon100() {
    dungeon = new Dungeon(8, 8, 64, false);
    dungeon.generateStartAndEnd();
    dungeon.addWeapon(100);
    double numCavesHaveWeapons = 0;
    double numCavesShouldHaveWeapons = dungeon.getGrid().getTotalCaves();
    for (int i = 0; i < 8; i++ ) {
      for (int j = 0; j < 8; j++) {
        if (dungeon.getMap()[i][j].hasWeapon()) {
          numCavesHaveWeapons += dungeon.getMap()[i][j].getWeapons().size();
        }
      }
    }
    assertEquals(numCavesHaveWeapons, numCavesShouldHaveWeapons, 1);
  }

  /**
   * Tests if the adding treasure percentage is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingWeaponNegative() {
    dungeon.addWeapon(-1);
    fail("Exception should have occurred!");
  }

  /**
   * Tests if the adding treasure percentage is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingWeaponOverHundred() {
    dungeon.addWeapon(101);
    fail("Exception should have occurred!");
  }

  /**
   * Tests if the adding treasure manually is working correctly.
   */
  @Test
  public void testAddingWeaponToLocation() {
    dungeon = new Dungeon(8, 8, 64, false);
    Location location11 = dungeon.getMap()[1][1];
    location11.addWeapon(Weapon.CROOKED_ARROW);
    assertEquals(Weapon.CROOKED_ARROW, location11.getWeapons().get(0));
  }

  /**
   * Tests if the adding treasure manually is working correctly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingNullWeaponToLocation() {
    Location location11 = dungeon.getMap()[1][1];
    location11.addWeapon(null);
    fail("Exception should have occurred!");
  }
}
