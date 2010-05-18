package accu.browser.oo;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JMock.class)
public class MultiPageControllerTest {
  private final int VISIBLE_SIZE = 3;
  private final Mockery context = new Mockery();
  final Sequence views = context.sequence("views");
  private final View view = context.mock(View.class);
  private final Model model = context.mock(Model.class);
  private final Items retrievedItems1 = items(1);
  private final Items retrievedItems2 = items(2);
  
  @Before public void modelReturnsRetrievedItems() {
    context.checking(new Expectations() {{
    }});
  }
  
  @Test public void
  movesVisibleWindowOnDownArrowAtBottomOfWindow() {
    final ItemController controller = newControllerAtOffset(0);
    
    context.checking(new Expectations() {{
      allowing(model).retrieve(0, VISIBLE_SIZE); will(returnValue(retrievedItems1));
      allowing(model).hasItemsFor(1, VISIBLE_SIZE); will(returnValue(true));
      allowing(model).retrieve(1, VISIBLE_SIZE); will(returnValue(retrievedItems2));
      
      one(view).show(retrievedItems1, 2); inSequence(views); 
      one(view).show(retrievedItems2, 2); inSequence(views); 
    }});
    
    controller.downArrow();
    controller.downArrow();
  }


  @Test public void
  movesVisibleWindowOnUpArrowAtTopOfWindow() {
    final ItemController controller = newControllerAtOffset(10);
    
    context.checking(new Expectations() {{
      allowing(model).retrieve(10, VISIBLE_SIZE); will(returnValue(retrievedItems1));
      allowing(model).hasItemsFor(9, VISIBLE_SIZE); will(returnValue(true));
      allowing(model).retrieve(9, VISIBLE_SIZE); will(returnValue(retrievedItems2));
      
      one(view).show(retrievedItems1, 0); inSequence(views); 
      one(view).show(retrievedItems2, 0); inSequence(views); 
    }});
    
    controller.upArrow();
    controller.upArrow();
  }

  private ItemController newControllerAtOffset(int offset) {
    return new ItemController(model, view, offset, VISIBLE_SIZE, 1);
  }

  private static Items items(final int id) {
    return new Items() {
      @Override public String toString() { return "Items-" + id; }
    };
  }
}
