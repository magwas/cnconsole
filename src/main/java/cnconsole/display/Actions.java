package cnconsole.display;

import java.util.Arrays;
import java.util.Map.Entry;

import cnconsole.App;
import cnconsole.data.Command;
import cnconsole.system.SystemServices;

public class Actions {

	private int screenWidth;
	private SystemServices sys;
	private App app;

	public Actions(App app, SystemServices sys) {
		this.app = app;
		this.sys = sys;
	}

	public void screenSetup() {
		sys.initScreen();
		screenWidth = sys.getScreenWidth();
		int row = 1;
		sys.writeAt(0, 0, "Commands:");
		for (Entry<Integer, Command> entry : app.getConfig().commands.entrySet()) {
			sys.writeAt(row, 0, entry.getValue().description);
			row++;
		}

		row = 0;
		for (String artLine : Arrays.asList(Constants.ART)) {
			sys.writeAt(row, screenWidth / 2, artLine);
			row++;
		}
		sys.refresh();
	}

	public void setCoords(Float[] values) {
		sys.writeAt(Constants.COORDS_X[0], screenWidth / 2 + Constants.COORDS_X[1],
				String.format("%6.2f", values[0]));
		sys.writeAt(Constants.COORDS_Y[0], screenWidth / 2 + Constants.COORDS_Y[1],
				String.format("%6.2f", values[1]));
		sys.writeAt(Constants.COORDS_Z[0], screenWidth / 2 + Constants.COORDS_Z[1],
				String.format("%6.2f", values[2]));

	}

	public void setPower(int percent) {
		sys.writeAt(Constants.COORDS_P[0], screenWidth / 2 + Constants.COORDS_P[1],
				String.format("%3d", percent));
	}

	public void setWorking(boolean isWorking) {
		if (isWorking)
			sys.writeAt(0, screenWidth / 2, "working");
		else
			sys.writeAt(0, screenWidth / 2, "idle    ");
	}

	public void writeToConsole(String value) {
		sys.writetoConsole(value);

	}

	public void writeWarningToConsole(String value) {
		sys.writeWarningToConsole(value);
	}
}
