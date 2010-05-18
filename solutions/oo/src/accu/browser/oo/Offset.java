package accu.browser.oo;


public class Offset {
  public final int value;

  public Offset(int offset) {
    this.value = offset;
  }

  public Offset movedIn(Direction direction) {
    return new Offset(direction.moved(value));
  }

  public boolean isWithin(int visibleSize) {
    return 0 <= value && value < visibleSize;
  }
  
  public static Offset asOffset(int value) {
    return new Offset(value);
  }
}
