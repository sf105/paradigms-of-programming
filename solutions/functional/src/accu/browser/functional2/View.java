package accu.browser.functional2;

public class View {
  public final int offset;
  public final int size;
  public final int selectionOffset;

  public View(int offset, int size, int selectionOffset) {
    this.offset = offset;
    this.size = size;
    this.selectionOffset = selectionOffset;
  }

  public boolean canMoveSelection() {
    return 0 < selectionOffset && selectionOffset < size - 1; 
  }

  public boolean canMoveWindowDownIn(Model model) {
    return model.isLargerThan(offset + size);
  }

  public boolean canMoveWindowUp() {
    return offset > 0;
  }

  public View withWindowMovedBy(int increment) {
    return new View(offset + increment, size, selectionOffset);
  }
  
  public View withSelectionMovedBy(int increment) {
    return new View(offset, size, selectionOffset + increment);
  }

  public boolean canMoveWindowUp(Model model) {
    return model.isLargerThan(offset + size + 1);
  }
  public boolean canMoveWindowDown() {
    return 0 < offset;
  }

}
