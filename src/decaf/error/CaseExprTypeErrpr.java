package decaf.error;

import decaf.Location;

/**
 * example：type: complex is different with other expr's type int<br>
 * PA2
 */
public class CaseExprTypeErrpr extends DecafError {

	private String otherType;
	
	private String exprType;
	
	public CaseExprTypeErrpr(Location location, String otherType, String exprType) {
		super(location);
		this.otherType = otherType;
		this.exprType = exprType;
	}

	@Override
	protected String getErrMsg() {
		return "type: " + exprType + " is different with other expr's type " + otherType;
	}

}
