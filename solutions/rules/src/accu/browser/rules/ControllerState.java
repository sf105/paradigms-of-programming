package accu.browser.rules;
import java.util.List;


public class ControllerState {

	public int selectionPosition;
	public List<String> model;
	public int windowFirstRowPosition;
	public int windowSize;

	public int windowLastRowPosition() {
		return windowFirstRowPosition + windowSize -1;
	}

}
