package cnconsole;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.concurrent.LinkedTransferQueue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cnconsole.data.Event;
import cnconsole.deviceparser.DeviceParser;
import cnconsole.system.SystemServices;

class DeviceParserTest {

	@Test
	@DisplayName("coordinate update results in a COORDINATES")
	void test() throws InterruptedException {
		SystemServices sys = mock(SystemServices.class);
		App app = mock(App.class);
		String line = TestData.COORDINATES_LINES;
		LinkedTransferQueue<Event<String>> inQueue = new LinkedTransferQueue<Event<String>>();
		LinkedTransferQueue<Event<?>> outQueue = new LinkedTransferQueue<Event<?>>();
		DeviceParser parser = new DeviceParser(app, sys, inQueue, outQueue);
		parser.start();

		inQueue.add(new Event<String>(TestData.DEVICE, line));
		inQueue.add(new Event<String>(TestData.DEVICE, TestData.SOME_GARBAGE));

		@SuppressWarnings("unchecked")
		Event<Float[]> outEvent = (Event<Float[]>) outQueue.take();
		assertEquals(TestData.COORDINATES, outEvent.name);
		assertEquals(TestData.X_COORDINATE_IN_FLOAT, outEvent.value[0]);
		assertEquals(TestData.Y_COORDINATE_IN_FLOAT, outEvent.value[1]);
		assertEquals(TestData.Z_COORDINATE_IN_FLOAT, outEvent.value[2]);
		@SuppressWarnings("unchecked")
		Event<String> okEvent = (Event<String>) outQueue.take();
		assertEquals(TestData.OK, okEvent.name);
		@SuppressWarnings("unchecked")
		Event<String> garbageEvent = (Event<String>) outQueue.take();
		assertEquals(TestData.UNPARSED, garbageEvent.name);
		assertEquals(TestData.SOME_GARBAGE, garbageEvent.value);
		parser.cease();
	}

}
