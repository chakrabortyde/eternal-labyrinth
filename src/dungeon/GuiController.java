package dungeon;

import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * A simple GUI-based controller that can be used to play the game. Also,
 * adding enhancements to the dungeon to make the game more interactive and fun.
 */
public class GuiController implements EnhancedController {

  private final View view;
  private final Model model;
  private final GameState gameState;
  private boolean playerInWait = false;

  @Override
  public boolean isPlayerInWait() {
    return playerInWait;
  }

  /**
   * Constructor to initialize the controller with the ability to specify that
   * the model and the view separately, and push updates to the view for interaction.
   *
   * @param view view
   * @param model model
   */
  public GuiController(View view, Model model) {
    this.view = view;
    this.model = model;
    this.gameState = model.getCurrentGameState();
  }

  @Override
  public void playGame() {
    view.addAllListeners(this);
    view.makeVisible();
  }

  @Override
  public void handlePlayerMove(int xPrev, int yPrev) {
    model.getValidActions().forEach(a -> {
      if (a.getPosition().equals(new Position(xPrev, yPrev))) {
        move(a);
      }
    });
    view.refresh();
  }

  @Override
  public void handlePlayerMove(Direction direction) {
    model.getValidActions().forEach(a -> {
      if (a.getDirection().equals(direction)) {
        move(a);
      }
    });
    view.refresh();
  }

  private void move(Action action) {
    gameState.movePlayer(action.getDirection());
    if (gameState.getPlayerLocation().hasMonster()) {
      Monster monster = gameState.getPlayerLocation().getMonster();
      if (monster.getHealthPoints() == 2) {
        deadGame("You have been eaten by a Otyugh!");
        flipPlayerWait();
      } else {
        if (RandomNetwork.nextInt(2) == 0) {
          deadGame("You have been eaten by a Otyugh!");
          flipPlayerWait();
        } else {
          diceRoll();
        }
      }
      return;
    }
    if (gameState.getPlayerLocation().hasPit()) {
      deadGame("You fell into a pit!");
      flipPlayerWait();
      return;
    }
    if (gameState.getPlayerLocation().hasThief()) {
      if (gameState.getPlayerTreasures().isEmpty()) {
        infoDialogGame("You are broke!", "Thief sends his deepest sympathies!");
      } else {
        int i = RandomNetwork.nextInt(gameState.getPlayer().getTreasures().size());
        infoDialogGame(String.format("You were looted of a %s!",
                gameState.getPlayer().getTreasures().remove(i).name().toLowerCase(Locale.ROOT)));
      }
      return;
    }
    if (gameState.getPlayerLocation().hasMovingMonster()) {
      if (RandomNetwork.nextInt(2) == 0) {
        deadGame("You have been eaten by a Beholder!");
        flipPlayerWait();
      } else {
        diceRoll();
        gameState.removeMovingMonsterState();
      }
      return;
    }
    if (gameState.isGameOver()) {
      infoDialogGame("You emerged victorious!");
      view.removeAllListeners();
    }
  }

  @Override
  public void pickTreasure(int index) {
    model.pickTreasure(index);
    view.refresh();
  }

  @Override
  public void pickWeapon(int index) {
    model.pickWeapon(index);
    view.refresh();
  }

  private void flipPlayerWait() {
    playerInWait = !playerInWait;
  }

  @Override
  public void shoot(Weapon weapon, int power, Direction direction) {
    model.shoot(weapon, power, direction);
    view.refresh();
  }

  @Override
  public void restartPlayer() {
    if (gameState.getPlayer().getHealth() > 0) {
      gameState.getPlayer().useHealth();
      gameState.resetPlayer();
      flipPlayerWait();
    } else {
      deadGame("You are out of health!");
      view.removeAllListeners();
    }
    view.refresh();
  }

  private void diceRoll() throws IllegalStateException {
    JOptionPane optionPane = new JOptionPane();
    optionPane.setMessage(new Object[] {"You were lucky this time!"});
    optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
    optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);
    JDialog dialog = optionPane.createDialog((GuiView) view, "Game over");
    dialog.pack();
    dialog.setVisible(true);
  }

  private void deadGame(String message) throws IllegalStateException {
    JOptionPane optionPane = new JOptionPane();
    optionPane.setMessage(new Object[]{message, "Better luck next time"});
    optionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
    optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);
    JDialog dialog = optionPane.createDialog((GuiView) view, "Game over");
    dialog.pack();
    dialog.setVisible(true);
  }

  private void infoDialogGame(String... message) throws IllegalStateException {
    JOptionPane optionPane = new JOptionPane();
    optionPane.setMessage(message);
    optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
    optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);
    JDialog dialog = optionPane.createDialog((GuiView) view, "Game over");
    dialog.pack();
    dialog.setVisible(true);
  }
}
