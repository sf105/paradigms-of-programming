package accu.browser.functional2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OnePageTest {

  @Test public void
  movesSelectionDownWithinVisibleWindowOnDownArrow() {
    ListBox result = new ListBox(new View(0, 10, 5), new Model(10)).arrowDown().arrowDown();
    
    assertEquals(0, result.view.offset);
    assertEquals(10, result.view.size);
    assertEquals(7, result.view.selectionOffset);
  }
  
  @Test public void
  movesSelectionUpWithinVisibleWindowOnUpArrow() {
    ListBox result = new ListBox(new View(0, 10, 5), new Model(10)).arrowUp().arrowUp();
    
    assertEquals(0, result.view.offset);
    assertEquals(10, result.view.size);
    assertEquals(3, result.view.selectionOffset);
  }
  
  @Test public void
  returnsSelectionToSameItemWithinVisibleWindowOnDownAndUpArrow() {
    ListBox result = new ListBox(new View(0, 10, 5), new Model(10)).arrowDown().arrowUp();
    
    assertEquals(0, result.view.offset);
    assertEquals(10, result.view.size);
    assertEquals(5, result.view.selectionOffset);
  }
  
  @Test public void
  doesNotMoveSelectionPastVisibleWindowOnDownArrow() {
    ListBox result = new ListBox(new View(0, 10, 9), new Model(10)).arrowDown();
    
    assertEquals(0, result.view.offset);
    assertEquals(10, result.view.size);
    assertEquals(9, result.view.selectionOffset);
  }
  
  @Test public void
  doesNotMoveSelectionPastVisibleWindowOnUpArrow() {
    ListBox result = new ListBox(new View(0, 10, 0), new Model(10)).arrowUp();
    
    assertEquals(0, result.view.offset);
    assertEquals(10, result.view.size);
    assertEquals(0, result.view.selectionOffset);
  }
}
