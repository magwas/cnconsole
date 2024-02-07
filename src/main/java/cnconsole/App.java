package cnconsole;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.LinkedTransferQueue;

import cnconsole.data.Config;
import cnconsole.data.Event;
import cnconsole.system.SystemServices;
import cnconsole.system.device.Device;

public class App {

	private SystemServices sys;
	private ConfigParser parser;
	private Config config;
	private Device device;
	private LinkedTransferQueue<Event<String>> inputQueue;

	public App(SystemServices sys, ConfigParser parser) {
		this.sys = sys;
		this.parser = parser;
	}

	public Device init() throws IOException {
		String home = sys.getHome();
		String configString = sys.readfile(home + "/.cncrc");
		config = parser.parse(configString);
		String[] args = sys.getargs();
		inputQueue = new LinkedTransferQueue<Event<String>>();
		device = open(args[0], "DEVICE", getInputQueue());
		return device;
	}

	public void panic(String where, Exception exception) {
		sys.doPanic(where, exception);
	}

	public Device open(String path, String eventName,
			LinkedTransferQueue<Event<String>> inputQueue)
			throws FileNotFoundException {

		return new Device(this, sys, path, "DEVICE", inputQueue);
	}

	public Config getConfig() {
		return config;
	}

	public LinkedTransferQueue<Event<String>> getInputQueue() {
		return inputQueue;
	}

	public Device getDevice() {
		return device;
	}

}
