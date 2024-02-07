package cnconsole.deviceparser;

import java.util.HashMap;
import java.util.concurrent.LinkedTransferQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cnconsole.App;
import cnconsole.data.Event;
import cnconsole.system.SystemServices;

public class DeviceParser extends Thread {

	public static final String OK = "OK";
	public static final String COORDINATES = "COORDINATES";
	private LinkedTransferQueue<Event<String>> inQueue;
	LinkedTransferQueue<Event<?>> outQueue;
	HashMap<Pattern, String> matches = new HashMap<Pattern, String>();
	private Posters posters;
	private App app;
	private boolean isRunning;
	private SystemServices sys;

	public DeviceParser(App app, SystemServices sys,
			LinkedTransferQueue<Event<String>> inQueue,
			LinkedTransferQueue<Event<?>> outQueue) {
		this.inQueue = inQueue;
		this.outQueue = outQueue;
		this.app = app;
		this.sys = sys;
		posters = new Posters(outQueue);
		initializeMatches();

	}

	@Override
	public void run() {
		isRunning = true;
		sys.writetoConsole("DeviceParser started");
		while (isRunning) {
			loop();
		}
	}

	public void loop() {
		Event<String> currentEvent = null;
		try {
			currentEvent = inQueue.take();
		} catch (InterruptedException e) {
			app.panic("DeviceParser", e);
		}
		sys.writetoConsole("DeviceParser: " + currentEvent.value);
		for (String line : currentEvent.value.split("\n")) {
			checkPatterns(matches, line);
		}
	}

	public void initializeMatches() {
		matches.put(
				Pattern.compile(
						".*X:(?<X>[\\w\\.]+) Y:(?<Y>[\\w\\.]+) Z:(?<Z>[\\w\\.]+).*"),
				COORDINATES);
		matches.put(Pattern.compile("ok"), OK);
	}

	public void checkPatterns(HashMap<Pattern, String> matches, String line) {
		for (Pattern pat : matches.keySet()) {
			Matcher match = pat.matcher(line);
			if (match.matches()) {
				switch (matches.get(pat)) {
				case COORDINATES:
					posters.postCoordinates(match);
					return;
				case OK:
					posters.postOk();
					return;
				}
			}
		}
		posters.postUnparsed(line);
	}

	public void cease() {
		isRunning = false;
	}
}
