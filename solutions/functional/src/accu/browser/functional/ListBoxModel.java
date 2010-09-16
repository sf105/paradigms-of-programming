package accu.browser.functional;

import static accu.browser.functional.Window.reflectWindow;
import static accu.browser.functional.Window.selectionAtEdge;
import static accu.browser.functional.Window.withSelectionAtTop;
import static accu.browser.functional.Window.shiftSelection;
import static accu.browser.functional.Window.shiftWindow;
import static accu.browser.functional.Window.windowAtEdge;


public class ListBoxModel {
	public final Integer size;
	public final Window window;
	
	public ListBoxModel(Integer size, Window window) {
		this.size = size;
		this.window = window;
	}
	
	public ListBoxModel withWindow(Window aWindow) {
	  return new ListBoxModel(size, aWindow);
	}
	
	public static ListBoxModel reflect(ListBoxModel model) {
		return model.withWindow(reflectWindow(model.size, model.window));
	}
	
	public static ListBoxModel selectTop(ListBoxModel model) {
		return new ListBoxModel(model.size, withSelectionAtTop(model.window));
	}

  public static ListBoxModel arrowUp(ListBoxModel model) {
	  return selectionAtEdge(model.window) 
	      ? (windowAtEdge(model.window) ? model : model.withWindow(shiftWindow(model.window)))
        : model.withWindow(shiftSelection(model.window));
	}
	
	public static ListBoxModel arrowDown(ListBoxModel model) {
		return reflect(arrowUp(reflect(model)));
	}
	
	public static ListBoxModel iterate(ModelFunction function, ListBoxModel model, Integer count) {
	  return count == 0 
	    ? model
	    : iterate(function, function.apply(model), count - 1);
	}
	
	public static ListBoxModel pageWith(ModelFunction step, ListBoxModel model) {
		return selectTop(iterate(step, selectTop(model), Window.windowSize(model.window)));	
	}
	
	public static ListBoxModel pageDown(ListBoxModel model) {
		return pageWith(arrowDownFunction(), model);
	}
	
	public static ListBoxModel pageUp(ListBoxModel model) {
		return pageWith(arrowUpFunction(), model);
	}
	
	private static ModelFunction arrowUpFunction() {
	  return new ModelFunction() {
      public ListBoxModel apply(ListBoxModel aModel) { return arrowUp(aModel); }
    };
	}
  private static ModelFunction arrowDownFunction() {
    return new ModelFunction() {
      public ListBoxModel apply(ListBoxModel model) { return arrowDown(model); }
    };
  }
}
