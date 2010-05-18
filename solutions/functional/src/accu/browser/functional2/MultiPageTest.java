package accu.browser.functional2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MultiPageTest {

  @Test public void
  movesVisibleWindowOnDownArrowAtBottomOfWindow() {
    ListBox result = new ListBox(new View(0, 10, 9), new Model(20)).arrowDown().arrowDown();
    
    assertEquals(2, result.view.offset);
    assertEquals(10, result.view.size);
    assertEquals(9, result.view.selectionOffset);
  }
  
  @Test public void
  movesVisibleWindowOnUpArrowAtTopOfWindow() {
    ListBox result = new ListBox(new View(5, 10, 9), new Model(20)).arrowUp().arrowUp();
    
    assertEquals(3, result.view.offset);
    assertEquals(10, result.view.size);
    assertEquals(9, result.view.selectionOffset);
  }
}
