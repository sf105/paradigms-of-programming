package accu.browser.rules;
import java.util.ArrayList;
import java.util.List;

public class ListBoxController {	
	private static final String ARROW_DOWN = "arrow-down";
  private static final String ARROW_UP = "arrow-up";
  private final ListBoxView view;
	private final ControllerState state = new ControllerState();
	private final RuleEngine engine = new RuleEngine(state);
	
	public ListBoxController(List<String> model, ListBoxView view) {
		this.state.model = model;
		this.view = view;

		state.selectionPosition = model.size() > 0 ? 0 : ListBoxView.NO_SELECTION;
		state.windowFirstRowPosition = 0;
		state.windowSize = Math.min(10, model.size());
		
		engine.addRule(new Rule() {
			public boolean applies(ControllerState aState, FactStore facts) {
				return facts.isTrue(ARROW_DOWN) 
					&& aState.selectionPosition != aState.model.size() - 1
					&& aState.selectionPosition != aState.windowLastRowPosition();
			}
			
			public void apply(ControllerState aState, FactStore facts) {
				facts.revoke(ARROW_DOWN);
				aState.selectionPosition++;
			}
		});
		
		engine.addRule(new Rule() {
			public boolean applies(ControllerState aState, FactStore facts) {
				return facts.isTrue(ARROW_DOWN) 
					&& aState.selectionPosition != aState.model.size() - 1 
					&& aState.selectionPosition == aState.windowLastRowPosition();
			}
			
			public void apply(ControllerState aState, FactStore facts) {
				facts.revoke(ARROW_DOWN);
				aState.selectionPosition++;
				aState.windowFirstRowPosition++;
			}
		});
		
		engine.addRule(new Rule() {
			public boolean applies(ControllerState aState, FactStore facts) {
				return facts.isTrue(ARROW_UP) 
					&& aState.selectionPosition != 0
					&& aState.selectionPosition != aState.windowFirstRowPosition;
			}
			
			public void apply(ControllerState aState, FactStore facts) {
				facts.revoke(ARROW_UP);
				aState.selectionPosition--;
			}
		});
		
		engine.addRule(new Rule() {
			public boolean applies(ControllerState aState, FactStore facts) {
				return facts.isTrue(ARROW_UP) 
					&& aState.selectionPosition != 0
					&& aState.selectionPosition == aState.windowFirstRowPosition;
			}
			
			public void apply(ControllerState aState, FactStore facts) {
				facts.revoke(ARROW_UP);
				aState.selectionPosition--;
				aState.windowFirstRowPosition--;
			}
		});
		view.update(model, state.selectionPosition);
	}

	
	private List<String> window() {
		List<String> result = new ArrayList<String>();
		for(int n = 0; n < state.windowSize; n++) {
			result.add(state.model.get(state.windowFirstRowPosition + n));
		}
		return result;
	}

	public int getWindowSize() {
		return state.windowSize;
	} 
	
	public void arrowDown() {
		arrow(ARROW_DOWN);
	}

	public void arrowUp() {
		arrow(ARROW_UP);
	}


  private void arrow(String directionName) {
    engine.evaluateWith(new FactStore().withFact(directionName));
		view.update(window(), state.selectionPosition);
  }

}
