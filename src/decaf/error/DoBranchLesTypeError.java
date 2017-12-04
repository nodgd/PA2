
package decaf.error;

import decaf.Location;

/**
 * example: The condition of Do Stmt requestd type bool but int given<br>
 * PA2
 */
public class DoBranchLesTypeError extends DecafError {

	private String lesType;

	public DoBranchLesTypeError(Location location, String lesType) {
		super(location);
		this.lesType = lesType;
	}

	@Override
	protected String getErrMsg() {
		return "The condition of Do Stmt requestd type bool but " + lesType + " given";
	}

}
