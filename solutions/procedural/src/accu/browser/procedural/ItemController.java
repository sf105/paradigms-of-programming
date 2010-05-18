package accu.browser.procedural;


/**
 * In this version we only allow static code and data structures within the implementation, 
 * although the external interfaces are still OO to make testing easier.
 *
 */

public class ItemController {
  private static final int UP = -1;
  private static final int DOWN = 1;
  private final Model model;
  private final View view;
  private final ViewableRegion region = new ViewableRegion();


  private class ViewableRegion {
    public int visibleSize;
    public int offset;
    public int selection;
  }
  
  public ItemController(Model model, View view, int offset, int visibleSize, int initialSelection) {
    this.model = model;
    this.view = view;
    this.region.visibleSize = visibleSize;
    this.region.offset = offset;
    this.region.selection = initialSelection;
  }


  public void downArrow() {
    arrow(DOWN, region, model, view);
  }

  public void upArrow() {
    arrow(UP, region, model, view);
  }

  private static void arrow(int increment, ViewableRegion region, Model model, View view) {
    if (shiftedSelection(increment, region) || shiftedRegion(increment, region, model)) {
      view.show(model.retrieve(region.offset, region.visibleSize), region.selection);
    } 
  }

  private static boolean shiftedSelection(int increment, ViewableRegion region) {
    int nextSelection = region.selection + increment;
    if (0 <= nextSelection && nextSelection < region.visibleSize) {
      region.selection = nextSelection;
      return true;
    }
    return false;
  }

  private static boolean shiftedRegion(int increment, ViewableRegion region, Model model) {
    int nextOffset = region.offset + increment;
    if (model.hasItemsFor(nextOffset, region.visibleSize)) {
      region.offset = nextOffset;
      return true;
    }
    return false;
  }
}
