package accu.browser.rules;
import java.util.List;


public interface ListBoxView {
	public static final int NO_SELECTION = -1;
	public void update(List<String> model, int selectionPosition);
}
