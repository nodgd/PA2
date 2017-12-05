package decaf.error;

import decaf.Location;

/**
 * example：condition is not unique<br>
 * PA2
 */
public class CaseExprSwitchRepeatError extends DecafError {
	
	public CaseExprSwitchRepeatError(Location location) {
		super(location);
	}

	@Override
	protected String getErrMsg() {
		return "condition is not unique";
	}

}
