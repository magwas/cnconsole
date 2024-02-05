package cnconsole;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.LinkedTransferQueue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cnconsole.system.Event;

class DeviceParserTest {

	@Test
	@DisplayName("coordinate update results in a COORDINATES")
	void test() throws InterruptedException {
		String line = "X:0.00 Y:127.2 Z:145.14 E:0.00 Count X: 0 Y:10160 Z:116000\nok";
		LinkedTransferQueue<Event<String>> inQueue = new LinkedTransferQueue<Event<String>>();
		LinkedTransferQueue<Event<?>> outQueue = new LinkedTransferQueue<Event<?>>();
		DeviceParser parser = new DeviceParser(inQueue, outQueue);
		parser.start();

		Event<String> event = new Event<String>(null, line);
		inQueue.add(event);
		Event<String> event2 = new Event<String>(null, "some garbage");
		inQueue.add(event2);

		@SuppressWarnings("unchecked")
		Event<Float[]> outEvent = (Event<Float[]>) outQueue.take();
		assertEquals("COORDINATES", outEvent.name);
		assertEquals(0.0f, outEvent.value[0]);
		assertEquals(127.2f, outEvent.value[1]);
		assertEquals(145.14f, outEvent.value[2]);
		@SuppressWarnings("unchecked")
		Event<String> okEvent = (Event<String>) outQueue.take();
		assertEquals("OK", okEvent.name);
		@SuppressWarnings("unchecked")
		Event<String> garbageEvent = (Event<String>) outQueue.take();
		assertEquals("UNPARSED", garbageEvent.name);
		assertEquals("some garbage", garbageEvent.value);
	}

}
