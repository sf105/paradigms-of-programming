package accu.browser.rules;
import java.util.HashSet;
import java.util.Set;


public class RuleEngine {
	private Set<Rule> rules = new HashSet<Rule>();
	private ControllerState state;
	
	public RuleEngine(ControllerState state) {
		this.state = state;
	}

	public void evaluateWith(FactStore facts) {
		Rule rule;
		while(null != (rule = findMatchingRule(facts))) {
			rule.apply(state, facts);
		}
	}

	public void addRule(Rule rule) {
		rules.add(rule);
	}

  private Rule findMatchingRule(FactStore facts) {
    for(Rule rule : rules) {
      if (rule.applies(state, facts))
        return rule;
    }
    return null;
  }
}
