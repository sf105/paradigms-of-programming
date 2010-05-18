package accu.browser.oo;

import static accu.browser.oo.Direction.Down;
import static accu.browser.oo.Direction.Up;
import static accu.browser.oo.Offset.asOffset;

/**
 * "Hard-Core" OO version. Not necessarily the best solution...
 */
public class ItemController {
  private final Selection selection;

  public ItemController(Model model, View view, int offset, int visibleSize, int initialSelection) {
    this.selection = new Selection(asOffset(initialSelection), view, 
                      new VisibleRange(asOffset(offset), visibleSize, model));
  }

  public void downArrow() {
    arrow(Down);
  }

  public void upArrow() {
    arrow(Up);
  }

  private void arrow(Direction direction) {
    if (selection.movedIn(direction)) {
      selection.show();
    }
  }
  
 
  static class Selection extends Location {
    private final View view;
    private final VisibleRange visibleRange;
    
    public Selection(Offset offset, View view, VisibleRange visibleRange) {
      super(offset, visibleRange);
      this.view = view;
      this.visibleRange = visibleRange;
    }
    @Override public boolean canMoveIn(Direction direction) {
      return visibleRange.canSelect(offset.movedIn(direction));
    }
    public void show() {
      view.show(visibleRange.retrieveItems(), offset.value);
    }
  }
  
  private static class VisibleRange extends Location {
    private final int visibleSize;
    private final Model model;
    
    public VisibleRange(Offset offset, int visibleSize, Model model) {
      super(offset, NULL_LOCATION);
      this.visibleSize = visibleSize;
      this.model = model;
    }
    @Override
    public boolean canMoveIn(Direction direction) {
      return model.hasItemsFor(offset.movedIn(direction).value, visibleSize);
    }
    public Items retrieveItems() {
      return model.retrieve(offset.value, visibleSize);
    }
    public boolean canSelect(Offset selectionOffset) {
      return selectionOffset.isWithin(visibleSize);
    }
  }
}
