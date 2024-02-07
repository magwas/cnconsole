package cnconsole.router;

import java.util.HashMap;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

import cnconsole.App;
import cnconsole.data.Command;
import cnconsole.data.Event;
import cnconsole.deviceparser.DeviceParser;
import cnconsole.display.Display;
import cnconsole.system.SystemServices;

public class Router extends Thread {

	private LinkedTransferQueue<Integer> inputQueue;
	private LinkedTransferQueue<Event<?>> fromDeviceQueue;
	private LinkedTransferQueue<Event<String>> toDeviceQueue;
	private LinkedTransferQueue<Event<?>> displayQueue;
	private App app;
	private HashMap<Integer, Command> commands;
	private boolean isRunning;
	private SystemServices sys;

	public Router(App app, SystemServices sys,
			LinkedTransferQueue<Integer> inputQueue,
			LinkedTransferQueue<Event<?>> fromDeviceQueue,
			LinkedTransferQueue<Event<String>> toDeviceQueue,
			LinkedTransferQueue<Event<?>> displayQueue) {
		this.app = app;
		this.sys = sys;
		this.inputQueue = inputQueue;
		this.fromDeviceQueue = fromDeviceQueue;
		this.toDeviceQueue = toDeviceQueue;
		this.displayQueue = displayQueue;
		this.commands = app.getConfig().commands;
	}

	@Override
	public void run() {
		for (String command : app.getConfig().initGcodes) {
			toDeviceQueue.put(new Event<String>("COMMAND", command));
		}
		sys.writetoConsole("Router starting");
		isRunning = true;
		while (isRunning)
			loop();
	}

	public void loop() {
		Event<?> deviceEvent = fromDeviceQueue.poll();
		Integer key = null;
		if (null != deviceEvent) {
			switch (deviceEvent.name) {
			case DeviceParser.OK:
				displayQueue.add(new Event<String>(Display.IDLE, ""));
				break;
			case DeviceParser.COORDINATES:
				displayQueue
						.add(new Event<Object>(Display.COORDINATES, deviceEvent.value));
				break;
			default:
				displayQueue
						.add(new Event<Object>(Display.UNPARSED, deviceEvent.value));
			}
		}
		try {
			key = inputQueue.poll(10, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			return;
		}
		if (null == key || -1 == key)
			return;
		Command command = commands.get(key);
		if (null == command) {
			String hexCode = String.format("0x%x", key);
			String msg = "no command for key " + hexCode;
			displayQueue.add(new Event<String>(Display.WARNING, msg));
			return;
		}
		for (String code : command.gcode) {
			toDeviceQueue.add(new Event<String>("COMMAND", code));
		}

	}

	public void cease() {
		isRunning = false;
	}
}
