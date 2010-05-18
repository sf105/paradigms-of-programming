package accu.browser.procedural;

public interface Model {

  Items retrieve(int firstItemOffset, int numberOfItems);
  boolean hasItemsFor(int fistItemOffset, int numberOfItems);

}
