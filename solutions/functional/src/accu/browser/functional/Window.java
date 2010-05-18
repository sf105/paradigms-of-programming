package accu.browser.functional;


public class Window {
	public final Integer top;
	public final Integer bottom;
	public final Integer selection;
	
	public Window(int top, int bottom, int selection) {
		this.top = top;
		this.bottom = bottom;
		this.selection = selection;
	}

	public static Integer windowSize(Window window) {
		return window.bottom - window.top + 1;
	}
	
	public static Boolean selectionAtEdge(Window window) {
		return window.top == window.selection;
	}
	
	public static Boolean windowAtEdge(Window window) {
		return window.top == 0;
	}
	
  public static Window withSelectionAtTop(final Window newWindow) {
    return new Window(newWindow.top, newWindow.bottom, newWindow.top);
  }
  
	public static Window reflectWindow(Integer about, Window window) {
		return new Window(inv(about, window.bottom), inv(about, window.top), 
				inv(about, window.selection));
	}
	
	public static Window shiftSelection(Window window) {
		return new Window(window.top, window.bottom, window.selection - 1);
	}
	
	public static Window shiftWindow(Window window) {
		return new Window(window.top - 1, window.bottom - 1, window.selection - 1);
	}
	
	public static Integer inv(Integer about, Integer x) {
		return (about - 1) - x;
	}

}
