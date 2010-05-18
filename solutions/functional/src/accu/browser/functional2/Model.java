package accu.browser.functional2;

public class Model {
  public final int numberOfItems;

  public Model(int size) {
    this.numberOfItems = size;
  }

  public boolean isLargerThan(int size) {
    return size < numberOfItems;
  }
}
