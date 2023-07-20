package dungeon;

/**
 * This interface enables to play the game using an interactive graphical user interface (GUI).
 * Provides basic methods like adding and removing listeners, refreshing, etc.
 */
public interface View {

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  void addAllListeners(GuiController listener);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Removes all the listeners set to this view.
   */
  void removeAllListeners();
}
