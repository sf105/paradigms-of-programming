package accu.browser.functional;

import junit.framework.TestCase;


public class ListBoxModelTest extends TestCase {
	public void testEdge() {
		ListBoxModel m = new ListBoxModel(10, new Window(1,2,1));
		assertEquals(new Integer(0), ListBoxModel.arrowUp(m).window.selection);
		assertEquals(new Integer(0), ListBoxModel.arrowUp(m).window.top);
		assertEquals(new Integer(1), ListBoxModel.arrowUp(m).window.bottom);
	}
}
