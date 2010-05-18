package accu.browser.rules;

public interface Rule {
	public void apply(ControllerState state, FactStore facts);
	public boolean applies(ControllerState state, FactStore facts);
}
