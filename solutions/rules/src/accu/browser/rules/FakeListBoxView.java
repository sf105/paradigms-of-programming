package accu.browser.rules;
import java.util.List;


public class FakeListBoxView implements ListBoxView {
	public List<String> model;
	public int selectionPosition;
	
	public void update(List<String> newModel, int newSelectionPosition) {
		this.model = newModel;
		this.selectionPosition = newSelectionPosition;
	}

}
