package decaf.error;

import decaf.Location;

/**
 * example：For copy expr, the source class : student and the destination class : people are not same<br>
 * PA2
 */
public class AssignCopyTypeError extends DecafError {

	private String left;
	private String expr;
	
	public AssignCopyTypeError(Location location, String left, String expr) {
		super(location);
		this.left = left;
		this.expr = expr;
	}

	@Override
	protected String getErrMsg() {
		return "For copy expr, the source " + expr + " and the destination " + left + " are not same";
	}

}
