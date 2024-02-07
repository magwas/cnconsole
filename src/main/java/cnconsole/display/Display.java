package cnconsole.display;

import java.util.concurrent.LinkedTransferQueue;

import cnconsole.App;
import cnconsole.data.Event;
import cnconsole.system.SystemServices;

public class Display extends Thread {

	public static final String UNPARSED = "UNPARSED";
	public static final String IDLE = "IDLE";
	public static final String WORKING = "WORKING";
	public static final String POWER = "POWER";
	public static final String COORDINATES = "COORDINATES";
	public static final String WARNING = "WARNING";
	private SystemServices sys;
	private LinkedTransferQueue<Event<?>> queue;
	private Actions actions;
	private App app;
	private boolean isRunning;

	public Display(App app, SystemServices sys,
			LinkedTransferQueue<Event<?>> queue) {
		this.app = app;
		this.sys = sys;
		this.queue = queue;
		this.actions = new Actions(app, sys);
		actions.screenSetup();
	}

	@Override
	public void run() {
		sys.writetoConsole("Display started");
		isRunning = true;
		while (isRunning) {
			loop();
		}
	}

	public void cease() {
		isRunning = false;
	}

	public Event<?> loop() {
		Event<?> event = null;
		try {
			event = queue.take();
		} catch (InterruptedException e) {
			app.panic("Display", e);
		}
		switch (event.name) {
		case COORDINATES:
			actions.setCoords((Float[]) event.value);
			break;
		case POWER:
			actions.setPower((int) event.value);
			break;
		case WORKING:
			actions.setWorking(true);
			break;
		case IDLE:
			actions.setWorking(false);
			break;
		case WARNING:
			actions.writeWarningToConsole((String) event.value);
			break;
		case UNPARSED:
		default:
			actions.writeToConsole((String) event.value);

		}
		return event;
	}
}
