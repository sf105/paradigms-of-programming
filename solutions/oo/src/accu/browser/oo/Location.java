package accu.browser.oo;

import static accu.browser.oo.Offset.asOffset;

public abstract class Location {
  public static final Location NULL_LOCATION = new Location(asOffset(0), null) {
    @Override public boolean movedIn(Direction direction) { return false; }
    @Override public boolean canMoveIn(Direction direction) { return false; }
  };
  
  private final Location nextLocation;
  protected Offset offset;
  public Location(Offset offset, Location nextLocation) {
    this.offset = offset;
    this.nextLocation = nextLocation;
  }
  
  public boolean movedIn(Direction direction) {
    if (canMoveIn(direction)) {
      move(direction);
      return true;
    }
    return nextLocation.movedIn(direction);
  }
  final public void move(Direction direction) {
    offset = offset.movedIn(direction);
  }

  public abstract boolean canMoveIn(Direction direction);
}