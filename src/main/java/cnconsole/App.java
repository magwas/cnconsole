package cnconsole;

import java.io.IOException;
import java.util.Arrays;

import cnconsole.system.SystemServices;

public class App {

	public static String[] ART = {
			"                |         ",
			"            Z: 000.00 mm   ",
			"                |    /    ",
			"  P: 000%       |  Y: 000.00 mm",
			"                |  /      ",
			"                | /       ",
			"------------------------X: 000.00 mm---",
			"                A         ",
			"               /|         ",
			"              / |         ",
			"             /  |         " };
	public static Integer[] COORDS_Z = { 1, 15 };
	public static Integer[] COORDS_P = { 3, 5 };
	public static Integer[] COORDS_Y = { 3, 22 };
	public static Integer[] COORDS_X = { 6, 27 };

	private SystemServices sys;
	private ConfigParser parser;
	private Config config;

	public App(SystemServices sys, ConfigParser parser) {
		this.sys = sys;
		this.parser = parser;
	}

	public void init() throws IOException {
		String home = sys.getHome();
		String configString = sys.readfile(home + "/.cncrc");
		config = parser.parse(configString);
		String[] files = sys.getargs();
		sys.openDevice(files[0]);
	}

	public void screenSetup() {
		sys.initScreen();
		String keyDescription = "";
		for (Command command : config.commands) {
			keyDescription += command.description + "\n";
		}
		sys.writeAt(0, 0, keyDescription);
		int col = sys.getScreenWidth() / 2;

		int row = 0;
		for (String artLine : Arrays.asList(ART)) {
			sys.writeAt(row, col, artLine);
			row++;
		}
		sys.refresh();
	}

}
