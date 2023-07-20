package dungeon;

import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A simple text-based controller that can be used to play the game. Also,
 * adding enhancements to the dungeon to make the game more interactive and fun.
 */
public class ConsoleController implements Controller {

  private final Scanner in;
  private final Appendable out;
  private final Model dungeon;

  /**
   * Constructor to initialize the controller with the ability to specify that
   * the input will be from System.in and the output will be to System.out.
   *
   * @param in readable input
   * @param out appendable output
   */
  public ConsoleController(Readable in, Appendable out, Model dungeon) {
    if (in == null) {
      throw new IllegalArgumentException("Invalid Readable!");
    }
    if (out == null) {
      throw new IllegalArgumentException("Invalid Appendable!");
    }
    if (dungeon == null) {
      throw new IllegalArgumentException("Invalid Dungeon model!");
    }
    this.out = out;
    this.dungeon = dungeon;
    this.in = new Scanner(in);
    try {
      out.append("\nInitializing dungeon...\n");
      out.append(String.format("Start: %s -> End: %s\n\n", dungeon.getStart(), dungeon.getEnd()));
      out.append("Starting game...\n");
    } catch (IOException e) {
      throw new IllegalStateException("Illegal state!");
    }
  }

  @Override
  public void playGame() throws IOException {
    GameState currentGameState = dungeon.getCurrentGameState();
    if (currentGameState == null) {
      throw new IllegalStateException("Invalid state!");
    }
    while (!currentGameState.isGameOver()) {
      out.append(String.format("\nYou are in a %s\n", currentGameState.getPlayerLocation()));
      if (currentGameState.getPlayerLocation().hasTreasure()) {
        out.append(String.format("Treasures available: %s\n",
                currentGameState.getPlayerLocation().getTreasures()));
      }
      if (currentGameState.getPlayerLocation().hasWeapon()) {
        out.append(String.format("Weapons available: %s\n",
                currentGameState.getPlayerLocation().getWeapons()));
      }
      if (currentGameState.isSmellingMorePungentNearby()) {
        out.append("You smell something very pungent nearby\n");
      } else {
        if (currentGameState.isSmellingLessPungentNearby()) {
          out.append("You smell something slightly pungent nearby\n");
        }
      }
      if (currentGameState.getPlayerLocation().hasMonster()) {
        Monster monster = currentGameState.getPlayerLocation().getMonster();
        if (monster.getHealthPoints() == 2) {
          out.append("Chomp, chomp, chomp, you are eaten by an Otyugh!\n");
          out.append("Better luck next time\n");
          break;
        } else {
          int chanceToEscape = RandomNetwork.nextInt(2);
          if (chanceToEscape == 0) {
            out.append("Chomp, chomp, chomp, you are eaten by an Otyugh!\n");
            out.append("Better luck next time\n");
            break;
          } else {
            continue;
          }
        }
      }
      out.append(String.format("Doors lead to %s\n",
              currentGameState.getValidActions()
                      .stream().map(Action::getDirection).collect(Collectors.toList())));
      out.append("Do you decide to move, pickup or shoot (M/P/S)? ");
      switch (in.next()) {
        case "m":
        case "M":
          out.append("Which direction do you choose (N/E/S/W)? ");
          switch (in.next()) {
            case "n":
            case "N":
              currentGameState.movePlayer(Direction.NORTH);
              break;
            case "e":
            case "E":
              currentGameState.movePlayer(Direction.EAST);
              break;
            case "s":
            case "S":
              currentGameState.movePlayer(Direction.SOUTH);
              break;
            case "w":
            case "W":
              currentGameState.movePlayer(Direction.WEST);
              break;
            default:
              out.append("Invalid input! Please try again!");
              break;
          }
          break;
        case "p":
        case "P":
          if (currentGameState.getPlayerLocation().hasTreasure()) {
            out.append("Which treasure do you take? ");
            if (!currentGameState.pickTreasure(in.nextInt() - 1)) {
              out.append("Invalid input! Please try again!\n");
            }
          } else {
            out.append("There are no treasures here!\n");
          }
          if (currentGameState.getPlayerLocation().hasWeapon()) {
            out.append("Which weapon do you take? ");
            if (!currentGameState.pickWeapon(in.nextInt() - 1)) {
              out.append("Invalid input! Please try again!\n");
            }
          } else {
            out.append("There are no weapons here!\n");
          }
          break;
        case "s":
        case "S":
          out.append("Choose bow draw power (1-5): ");
          int power = in.nextInt();
          out.append("Which direction do you shoot (N/E/S/W)? ");
          switch (in.next()) {
            case "n":
            case "N":
              out.append(currentGameState.shoot(Weapon.CROOKED_ARROW, power, Direction.NORTH));
              break;
            case "e":
            case "E":
              out.append(currentGameState.shoot(Weapon.CROOKED_ARROW, power, Direction.EAST));
              break;
            case "s":
            case "S":
              out.append(currentGameState.shoot(Weapon.CROOKED_ARROW, power, Direction.SOUTH));
              break;
            case "w":
            case "W":
              out.append(currentGameState.shoot(Weapon.CROOKED_ARROW, power, Direction.WEST));
              break;
            default:
              out.append("Invalid input! Please try again!\n");
              break;
          }
          break;
        case "q":
        case "Q":
          out.append("Game quit unexpectedly!\n");
          return;
        default:
          out.append("Invalid input! Please try again!\n");
          break;
      }
      out.append(currentGameState.getPlayer().toString());
    }
  }
}
