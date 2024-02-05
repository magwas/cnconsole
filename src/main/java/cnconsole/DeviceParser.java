package cnconsole;

import java.util.HashMap;
import java.util.concurrent.LinkedTransferQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cnconsole.system.Event;

public class DeviceParser extends Thread {

	private LinkedTransferQueue<Event<String>> inQueue;
	LinkedTransferQueue<Event<?>> outQueue;
	HashMap<Pattern, String> matches = new HashMap<Pattern, String>();
	private Posters posters;

	public DeviceParser(LinkedTransferQueue<Event<String>> inQueue,
			LinkedTransferQueue<Event<?>> outQueue) {
		this.inQueue = inQueue;
		this.outQueue = outQueue;
		posters = new Posters(outQueue);
		initializeMatches();

	}

	@Override
	public void run() {
		while (true) {
			loop();
		}
	}

	public void loop() {
		Event<String> currentEvent;
		try {
			currentEvent = inQueue.take();
		} catch (InterruptedException e) {
			Event<InterruptedException> failEvent = new Event<InterruptedException>(
					"FAIL", e);
			outQueue.put(failEvent);
			return;
		}
		for (String line : currentEvent.value.split("\n")) {
			checkPatterns(matches, line);
		}
	}

	public void initializeMatches() {
		matches.put(
				Pattern.compile(
						".*X:(?<X>[\\w\\.]+) Y:(?<Y>[\\w\\.]+) Z:(?<Z>[\\w\\.]+).*"),
				"COORDINATES");
		matches.put(Pattern.compile("ok"), "OK");
	}

	public void checkPatterns(HashMap<Pattern, String> matches, String line) {
		for (Pattern pat : matches.keySet()) {
			Matcher match = pat.matcher(line);
			if (match.matches()) {
				switch (matches.get(pat)) {
				case "COORDINATES":
					posters.postCoordinates(match);
					return;
				case "OK":
					posters.postOk();
					return;
				}
			}
		}
		posters.postUnparsed(line);
	}
}
