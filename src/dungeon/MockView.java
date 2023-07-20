package dungeon;

import java.io.IOException;

/**
 * A mock version of the view to facilitate testing.
 */
public class MockView implements View {

  private final Appendable log;

  /**
   * Constructor to initialize the view.
   *
   * @param log appendable log
   */
  public MockView(Appendable log) {
    this.log = log;
  }

  @Override
  public void addAllListeners(GuiController listener) {
    try {
      log.append("GuiController added!\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void refresh() {
    try {
      log.append("Refresh!\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void makeVisible() {
    try {
      log.append("Made visible!\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void removeAllListeners() {
    try {
      log.append("Removed all listeners!\n");
    } catch (IOException e) {
      // do nothing
    }
  }
}
