package cnconsole;

import cnconsole.system.SystemServices;

public class Display {

	private SystemServices sys;

	public Display(SystemServices sys) {
		this.sys = sys;
	}

	public void setCoords(double x, double y, double z) {
		sys.writeAt(Constants.COORDS_X[0], Constants.COORDS_X[1],
				String.format("%6.2f", x));
		sys.writeAt(Constants.COORDS_Y[0], Constants.COORDS_Y[1],
				String.format("%6.2f", y));
		sys.writeAt(Constants.COORDS_Z[0], Constants.COORDS_Z[1],
				String.format("%6.2f", z));

	}

	public void setPower(int percent) {
		sys.writeAt(Constants.COORDS_P[0], Constants.COORDS_P[1],
				String.format("%3d", percent));
	}

}
