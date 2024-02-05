package cnconsole;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.LinkedTransferQueue;

import cnconsole.system.Event;
import cnconsole.system.SystemServices;
import cnconsole.system.device.Device;

public class App {

	private SystemServices sys;
	private ConfigParser parser;
	public Config config;
	public Device device;
	public LinkedTransferQueue<Event<String>> inputQueue;

	public App(SystemServices sys, ConfigParser parser) {
		this.sys = sys;
		this.parser = parser;
	}

	public void init() throws IOException {
		String home = sys.getHome();
		String configString = sys.readfile(home + "/.cncrc");
		config = parser.parse(configString);
		String[] args = sys.getargs();
		inputQueue = new LinkedTransferQueue<Event<String>>();
		device = sys.open(args[0], "DEVICE", inputQueue);
	}

	public void screenSetup() {
		sys.initScreen();
		int row = 1;
		sys.writeAt(0, 0, "Commands:");
		for (Command command : config.commands) {
			sys.writeAt(row, 0, command.description);
			row++;
		}
		int col = sys.getScreenWidth() / 2;

		row = 0;
		for (String artLine : Arrays.asList(Constants.ART)) {
			sys.writeAt(row, col, artLine);
			row++;
		}
		sys.refresh();
	}

}
