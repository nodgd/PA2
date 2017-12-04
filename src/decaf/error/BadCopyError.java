package decaf.error;

import decaf.Location;

/**
 * example：expected class type for copy expr but int given<br>
 * PA2
 */
public class BadCopyError extends DecafError {

	private String owner;
	
	public BadCopyError(Location location, String owner) {
		super(location);
		this.owner = owner;
	}

	@Override
	protected String getErrMsg() {
		return "expected class type for copy expr but " + owner + " given";
	}

}
