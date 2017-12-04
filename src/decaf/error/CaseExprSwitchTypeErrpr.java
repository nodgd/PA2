package decaf.error;

import decaf.Location;

/**
 * example：incompatible case expr: bool given, but int expected<br>
 * PA2
 */
public class CaseExprSwitchTypeErrpr extends DecafError {

	private String switchExpr;
	
	public CaseExprSwitchTypeErrpr(Location location, String switchExpr) {
		super(location);
		this.switchExpr = switchExpr.equals("img") ? "complex" : switchExpr;
	}

	@Override
	protected String getErrMsg() {
		return "incompatible case expr: " + switchExpr + " given, but int expected";
	}

}
