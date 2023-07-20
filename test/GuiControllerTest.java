import static org.junit.Assert.assertEquals;

import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.EnhancedController;
import dungeon.GuiController;
import dungeon.MockView;
import dungeon.Model;
import dungeon.View;
import dungeon.Weapon;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class to test the GUI of the application by mocking a View.
 */
public class GuiControllerTest {

  private StringBuilder log;
  private EnhancedController controller;

  /**
   * Initialises the Mock View and sends it to the controller.
   */
  @Before
  public void setUp() {
    log = new StringBuilder();
    View view = new MockView(log);
    Model model = new Dungeon(8, 8, 64, false,
            15, 5, 15,
            5, 5, 5);
    controller = new GuiController(view, model);
    controller.playGame();
  }

  /**
   * Tests whether the playerInWait is working correctly.
   */
  @Test
  public void isPlayerInWait() {
    controller.isPlayerInWait();
    assertEquals("GuiController added!\n"
            + "Made visible!\n", log.toString());
  }

  /**
   * Tests whether the handlePlayerMove is working correctly.
   */
  @Test
  public void handlePlayerMove1() {
    controller.handlePlayerMove(0, 0);
    assertEquals("GuiController added!\n"
            + "Made visible!\n"
            + "Refresh!\n", log.toString());
  }

  /**
   * Tests whether the handlePlayerMove is working correctly.
   */
  @Test
  public void handlePlayerMove2() {
    controller.handlePlayerMove(Direction.NORTH);
    assertEquals("GuiController added!\n"
            + "Made visible!\n"
            + "Refresh!\n", log.toString());
  }

  /**
   * Tests whether the pickTreasure is working correctly.
   */
  @Test
  public void pickTreasure() {
    controller.pickTreasure(0);
    assertEquals("GuiController added!\n"
            + "Made visible!\n"
            + "Refresh!\n", log.toString());
  }

  /**
   * Tests whether the pickWeapon is working correctly.
   */
  @Test
  public void pickWeapon() {
    controller.pickWeapon(0);
    assertEquals("GuiController added!\n"
            + "Made visible!\n"
            + "Refresh!\n", log.toString());
  }

  /**
   * Tests whether the shoot is working correctly.
   */
  @Test
  public void shoot() {
    controller.shoot(Weapon.CROOKED_ARROW, 1, Direction.NORTH);
    assertEquals("GuiController added!\n"
            + "Made visible!\n"
            + "Refresh!\n", log.toString());
  }

  /**
   * Tests whether the restartPlayer is working correctly.
   */
  @Test
  public void restartPlayer() {
    controller.restartPlayer();
    assertEquals("GuiController added!\n"
            + "Made visible!\n"
            + "Refresh!\n", log.toString());
  }

  /**
   * Tests whether the gameplay is working correctly.
   */
  @Test
  public void testGameplay() {
    controller.handlePlayerMove(Direction.NORTH);
    controller.pickTreasure(0);
    controller.pickWeapon(0);
    controller.shoot(Weapon.CROOKED_ARROW, 1, Direction.NORTH);
    controller.restartPlayer();
    assertEquals("GuiController added!\n"
            + "Made visible!\n"
            + "Refresh!\n"
            + "Refresh!\n"
            + "Refresh!\n"
            + "Refresh!\n"
            + "Refresh!\n", log.toString());
  }
}
