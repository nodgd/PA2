package decaf.error;

import decaf.Location;

/**
 * example：incompatible case expr: bool given, but int expected<br>
 * PA2
 */
public class CaseExprSwitchTypeError extends DecafError {

	private String switchExpr;
	
	public CaseExprSwitchTypeError(Location location, String switchExpr) {
		super(location);
		this.switchExpr = switchExpr.equals("img") ? "complex" : switchExpr;
	}

	@Override
	protected String getErrMsg() {
		return "incompatible case expr: " + switchExpr + " given, but int expected";
	}

}
