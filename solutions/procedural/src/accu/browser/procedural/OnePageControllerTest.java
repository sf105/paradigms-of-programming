package accu.browser.procedural;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class OnePageControllerTest {
  private final int VISIBLE_SIZE = 3;
  private final Mockery context = new Mockery();
  final Sequence views = context.sequence("views");
  private final View view = context.mock(View.class);
  private final Model model = context.mock(Model.class);
  private final Items retrievedItems = items();
  
  @Before public void modelReturnsRetrievedItems() {
    context.checking(new Expectations() {{
      allowing(model).retrieve(0, VISIBLE_SIZE); will(returnValue(retrievedItems));
    }});
  }
  
  @Test public void
  movesSelectionDownWithinVisibleWindowOnDownArrow() {
    final ItemController controller = newControllerWithSelection(0);
    
    context.checking(new Expectations() {{
      one(view).show(retrievedItems, 1); inSequence(views); 
      one(view).show(retrievedItems, 2); inSequence(views); 
    }});
    
    controller.downArrow();
    controller.downArrow();
  }

  @Test public void
  movesSelectionUpWithinVisibleWindowOnUpArrow() {
    final ItemController controller = newControllerWithSelection(2);
    
    context.checking(new Expectations() {{
      one(view).show(retrievedItems, 1); inSequence(views); 
      one(view).show(retrievedItems, 0); inSequence(views); 
    }});
    
    controller.upArrow();
    controller.upArrow();
  }

  @Test public void
  returnsSelectionToSameItemWithinVisibleWindowOnDownAndUpArrow() {
    final ItemController controller = newControllerWithSelection(0);
    
    context.checking(new Expectations() {{
      one(view).show(retrievedItems, 1); inSequence(views); 
      one(view).show(retrievedItems, 0); inSequence(views); 
    }});
    
    controller.downArrow();
    controller.upArrow();
  }

  @Test public void
  doesNotMoveSelectionPastVisibleWindowOnDownArrow() {
    final ItemController controller = newControllerWithSelection(2);
    context.checking(new Expectations() {{
      allowing(model).hasItemsFor(1, VISIBLE_SIZE); will(returnValue(false)); 
    }});
    
    controller.downArrow();
  }

  @Test public void
  doesNotMoveSelectionPastVisibleWindowOnUpArrow() {
    final ItemController controller = newControllerWithSelection(0);
    context.checking(new Expectations() {{
      allowing(model).hasItemsFor(-1, VISIBLE_SIZE); will(returnValue(false)); 
    }});
    
    controller.upArrow();
  }

  private ItemController newControllerWithSelection(int initialSelection) {
    return new ItemController(model, view, 0, VISIBLE_SIZE, initialSelection);
  }

  private static Items items() {
    return new Items() {
      @Override public String toString() { return "Items"; }
    };
  }
}
