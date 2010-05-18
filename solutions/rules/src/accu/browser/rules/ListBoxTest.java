package accu.browser.rules;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;


@SuppressWarnings("serial")
public class ListBoxTest extends TestCase {

	private FakeListBoxView view;
	
	@Override
  protected void setUp() {
		view = new FakeListBoxView();
	}
	
	private List<String> makeModel(int size) {
		List<String> result = new ArrayList<String>();
		for(int n = 0; n < size; n++) {
			result.add(new Integer(n).toString());
		}
		return result;
	}
	
	public void testChangesSelectionOnArrowDown() {
		List<String> model = new ArrayList<String> () {{ add("a"); add("b"); }};
		ListBoxController controller = new ListBoxController(model, view);
		
		controller.arrowDown();
		assertEquals(1, view.selectionPosition);
	}
	
	public void testListBoxStartsWithSelectionAtZero() {
		List<String> model = new ArrayList<String> () {{ add("a"); add("b"); }};
		new ListBoxController(model, view);
		
		assertEquals(0, view.selectionPosition);
	}
	
	public void testListBoxChangesSelectionOnTwoArrowDowns() {
		List<String> model = new ArrayList<String> () {{ add("a"); add("b"); add("c"); }};
		ListBoxController controller = new ListBoxController(model, view);
		
		controller.arrowDown();
		controller.arrowDown();
		assertEquals(2, view.selectionPosition);
	}
	
	public void testListBoxDoesNotChangeSelectionPositionOnArrowUpFromTheTop() {
		List<String> model = new ArrayList<String> () {{ add("a"); add("b"); add("c"); }};
		ListBoxController controller = new ListBoxController(model, view);
		
		controller.arrowUp();
		assertEquals(0, view.selectionPosition);
	}
	
	
	public void testListBoxDoesNotChangeSelectionPositionOnArrowUpFromTheBottom() {
		FakeListBoxView fakeView = new FakeListBoxView();
		List<String> model = new ArrayList<String> () {{ add("a"); }};
		ListBoxController controller = new ListBoxController(model, fakeView);
		
		controller.arrowUp();
		assertEquals(0, fakeView.selectionPosition);
	}
	
	public void testChangesSelectionOnArrowUp() {
		List<String> model = new ArrayList<String> () {{ add("a"); add("b"); }};
		ListBoxController controller = new ListBoxController(model, view);		
		controller.arrowDown();
		
		controller.arrowUp();
		assertEquals(0, view.selectionPosition);
	}
	
	public void testListBoxDoesNotChangeSelectionPositionOnArrowDownFromTheBottom() {
		List<String> model = new ArrayList<String> () {{ add("a"); }};
		ListBoxController controller = new ListBoxController(model, view);
		
		controller.arrowDown();
		assertEquals(0, view.selectionPosition);
	}
	
	
	public void testArrowingDownInAnEmptyListIsANoop() {
		List<String> model = new ArrayList<String> ();
		ListBoxController controller = new ListBoxController(model, view);
		
		controller.arrowDown();
		assertEquals(ListBoxView.NO_SELECTION, view.selectionPosition);
	}
	
	public void testArrowDownMovesWindowWhenSelectionIsAtTheBottom() {
		List<String> model = makeModel(100);
		ListBoxController controller = new ListBoxController(model, view);
		
		// Advance to the bottom of the window
		for(int n = 0; n < controller.getWindowSize() -1; n++) {
			controller.arrowDown();
		}
		
		assertEquals(9, view.selectionPosition);
		
		controller.arrowDown();
		
		assertEquals(10, view.selectionPosition);
		assertEquals("1", view.model.get(0));
	}
	
	public void testArrowUpMovesWindowWhenSelectionIsAtTheTop() {
		List<String> model = makeModel(100);
		ListBoxController controller = new ListBoxController(model, view);
		
		// Advance to the bottom of the window and move the window down one position
		for(int n = 0; n < controller.getWindowSize(); n++) {
			controller.arrowDown();
		}
		
		// Move the selection back to the top and then move one more to move up the window
		for(int n = 0; n < controller.getWindowSize(); n++) {
			controller.arrowUp();
		}
		
		assertEquals(0, view.selectionPosition);
		assertEquals("0", view.model.get(0));
	}
	
	public void testWindowSizeIsTenWhenTheListIsGreaterThanTen() {
		new ListBoxController(makeModel(100), view).arrowDown();
		assertEquals(10, view.model.size());
	}
	
	public void testWindowSizeMatchesListSizeWhenItIsLessThanTen() {
		new ListBoxController(makeModel(5), view).arrowDown();
		assertEquals(5, view.model.size());
	}
	
	public void tesWindowSizeIsZeroWhenListIsEmpty() {
		new ListBoxController(makeModel(0), view).arrowDown();
		assertEquals(0, view.model.size());
	}
}
