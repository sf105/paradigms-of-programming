/**
 * 
 */
package accu.browser.oo;

enum Direction {
  Up(-1),
  Down(1);

  public int moved(int from) {
    return from + increment;
  }
  private final int increment;
  private Direction(int increment) {
    this.increment = increment;
  }
}