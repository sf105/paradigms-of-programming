package accu.browser.functional2;

public class ListBox {
  public final View view;
  public final Model model;

  public ListBox(View view, Model model) {
    this.view = view;
    this.model = model;
  }

  public ListBox arrowDown() {
    return arrowMovedBy(
        1, 
        new Limit() { public boolean canMoveWindow() { return view.canMoveWindowDownIn(model); } });
  }

  public ListBox arrowUp() {
    return arrowMovedBy(
        -1, 
        new Limit() { public boolean canMoveWindow() { return view.canMoveWindowUp(); } });
  }

  private ListBox arrowMovedBy(int increment, Limit limit) {
    return view.canMoveSelection()  
             ? boxWithView(view.withSelectionMovedBy(increment))
             : (limit.canMoveWindow() ? boxWithView(view.withWindowMovedBy(increment)) : this);
  }

  private ListBox boxWithView(View nextView) {
    return new ListBox(nextView, model);
  }
}
