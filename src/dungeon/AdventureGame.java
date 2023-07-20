package dungeon;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * Driver class demonstrating the use of Dungeon for Adventure game.
 */
public class AdventureGame {

  /**
   * The starting main method of the dungeon model game.
   *
    * @param args N/A
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      // GUI based gameplay
      Model model = new Dungeon(8, 9, 72, true,
              15, 5, 15,
              5, 5, 5);
      ReadOnlyModel readOnlyModel = model;
      View view = new GuiView(readOnlyModel);
      EnhancedController controller = new GuiController(view, model);
      controller.playGame();
    } else {
      // Command line based gameplay
      if (args.length != 7) {
        throw new IllegalArgumentException("Invalid number of arguments!");
      }
      try {
        int row = Integer.parseInt(args[0]);
        int col = Integer.parseInt(args[1]);
        int interconnectivity = Integer.parseInt(args[2]);
        boolean isWrapping = false;
        if (args[3].toLowerCase(Locale.getDefault()).equals("y")) {
          isWrapping = true;
        } else if (!args[3].toLowerCase(Locale.getDefault()).equals("n")) {
          throw new NumberFormatException();
        }
        int percentageTreasure = Integer.parseInt(args[4]);
        int percentageMonster = Integer.parseInt(args[5]);
        int percentageWeapon = Integer.parseInt(args[6]);

        int percentagePit = Integer.parseInt(args[4]);
        int percentageThief = Integer.parseInt(args[5]);
        int percentageMovingMonster = Integer.parseInt(args[6]);
        Model model = new Dungeon(row, col, interconnectivity, isWrapping,
                percentageTreasure, percentageMonster, percentageWeapon,
                percentagePit, percentageThief, percentageMovingMonster);

        Controller controller = new ConsoleController(
                new InputStreamReader(System.in), System.out, model);
        controller.playGame();
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid argument!");
      }
    }
  }
}
