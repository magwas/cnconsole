package cnconsole.deviceparser;

import java.util.concurrent.LinkedTransferQueue;
import java.util.regex.Matcher;

import cnconsole.data.Event;

class Posters {
	LinkedTransferQueue<Event<?>> outQueue;

	Posters(LinkedTransferQueue<Event<?>> outQueue) {
		this.outQueue = outQueue;
	}

	void postUnparsed(String line) {
		Event<String> okEvent = new Event<String>("UNPARSED", line);
		this.outQueue.add(okEvent);
	}

	void postOk() {
		Event<String> okEvent = new Event<String>("OK", null);
		this.outQueue.add(okEvent);
	}

	public void postCoordinates(Matcher match) {
		Float x = new Float(match.group("X"));
		Float y = new Float(match.group("Y"));
		Float z = new Float(match.group("Z"));
		Event<Float[]> xyzEvent = new Event<Float[]>("COORDINATES",
				new Float[] { x, y, z });
		this.outQueue.add(xyzEvent);
	}
}