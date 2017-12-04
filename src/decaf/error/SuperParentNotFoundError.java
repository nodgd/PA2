package decaf.error;

import decaf.Location;

/**
 * example：no parent class exist for class : people<br>
 * PA2
 */
public class SuperParentNotFoundError extends DecafError {

	private String owner;

	public SuperParentNotFoundError(Location location, String owner) {
		super(location);
		this.owner = owner;
	}

	@Override
	protected String getErrMsg() {
		return "no parent class exist for " + owner;
	}

}
