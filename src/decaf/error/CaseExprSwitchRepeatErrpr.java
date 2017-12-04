package decaf.error;

import decaf.Location;

/**
 * example：condition is not unique<br>
 * PA2
 */
public class CaseExprSwitchRepeatErrpr extends DecafError {
	
	public CaseExprSwitchRepeatErrpr(Location location) {
		super(location);
	}

	@Override
	protected String getErrMsg() {
		return "condition is not unique";
	}

}
