package accu.browser.rules;
import java.util.Set;
import java.util.HashSet;

public class FactStore {
	private Set<String> facts = new HashSet<String>();
	
	public FactStore withFact(String fact) {
		facts.add(fact);
		return this;
	}
	
	public boolean isTrue(String fact) {
		return facts.contains(fact);
	}
	
	public void revoke(String fact) {
		facts.remove(fact);
	}
}
