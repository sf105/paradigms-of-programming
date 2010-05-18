package accu.browser.functional;

import junit.framework.TestCase;


public class WindowTest extends TestCase {
	public void testWindowSize() {
		assertEquals(new Integer(10), Window.windowSize(new Window(0,9,0)));
	}
	
	public void testSelectionAtEdge() {
		assertTrue(Window.selectionAtEdge(new Window(0,9,0)));
		assertFalse(Window.selectionAtEdge(new Window(0, 9, 1)));
	}
	
	public void testShiftSelection() {
		assertEquals(new Integer(0), Window.shiftSelection(new Window(0,9,1)).selection);
	}
	
	public void testShiftWindow() {
		Window origin = new Window(1,2,1);
		assertEquals(new Integer(0), Window.shiftWindow(origin).top);
		assertEquals(new Integer(1), Window.shiftWindow(origin).bottom);
	}
	
	public void testReflectWindow() {
		Window origin = new Window(1,2,1);
		Window reflection = Window.reflectWindow(10, origin);
		assertEquals(new Integer(7), reflection.top);
		assertEquals(new Integer(8), reflection.bottom);
		assertEquals(new Integer(8), reflection.selection);
		
	}
}
