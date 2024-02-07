package cnconsole;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.concurrent.LinkedTransferQueue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cnconsole.data.Event;
import cnconsole.router.Router;
import cnconsole.system.SystemServices;
import io.webfolder.curses4j.Curses;

class RouterTest {

	private static LinkedTransferQueue<Integer> inputQueue;
	private static LinkedTransferQueue<Event<?>> fromDeviceQueue;
	private static LinkedTransferQueue<Event<String>> toDeviceQueue;
	private static LinkedTransferQueue<Event<?>> displayQueue;
	private static Router router;

	@BeforeAll
	static void startUp() throws InterruptedException {
		App app = AppMock.behaviour();
		SystemServices sys = mock(SystemServices.class);

		inputQueue = new LinkedTransferQueue<Integer>();
		fromDeviceQueue = new LinkedTransferQueue<Event<?>>();
		toDeviceQueue = new LinkedTransferQueue<Event<String>>();
		displayQueue = new LinkedTransferQueue<Event<?>>();
		router = new Router(app, sys, inputQueue, fromDeviceQueue, toDeviceQueue,
				displayQueue);
		router.start();
		Event<String> taken = toDeviceQueue.take();
		assertEquals(TestData.COMMAND, taken.name);
		assertEquals("G28", taken.value);
		assertEquals("G21", toDeviceQueue.take().value);
		assertEquals("G91", toDeviceQueue.take().value);

	}

	@AfterAll
	public static void tearDown() {
		router.cease();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	@DisplayName("unparsed input from the device goes to the console")
	void test1() throws InterruptedException {
		fromDeviceQueue.add(new Event(TestData.UNPARSED, TestData.UNPARSED_TEXT));
		Event<?> got = displayQueue.take();
		assertEquals(TestData.UNPARSED_TEXT, got.value);
		assertEquals(TestData.UNPARSED, got.name);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	@DisplayName("ok from the device updates the console to idle state")
	void test2() throws InterruptedException {
		fromDeviceQueue.add(new Event(TestData.OK, ""));
		Event<?> got = displayQueue.take();
		assertEquals(TestData.IDLE, got.name);
	}

	@Test
	@DisplayName("coordinates from the device updates the coordinates on the console")
	void test4() throws InterruptedException {
		fromDeviceQueue.add(
				new Event<Float[]>(TestData.COORDINATES, TestData.COORDINATES_PAYLOAD));
		@SuppressWarnings("unchecked")
		Event<Float[]> got = (Event<Float[]>) displayQueue.take();
		assertEquals(TestData.COORDINATES, got.name);
		assertEquals(TestData.X_COORDINATE_IN_FLOAT, got.value[0]);
		assertEquals(TestData.Y_COORDINATE_IN_FLOAT, got.value[1]);
		assertEquals(TestData.Z_COORDINATE_IN_FLOAT, got.value[2]);
	}

	@Test
	@DisplayName("a key from the keyboard sends the associates codes to the device")
	void test3() throws InterruptedException {
		inputQueue.add(Curses.KEY_DOWN);
		Event<String> got = toDeviceQueue.take();
		assertEquals(TestData.COMMAND, got.name);
		assertEquals(TestData.VALID_CONFIG_LINE_MORE_LINES_GCODE_1, got.value);
		got = toDeviceQueue.take();
		assertEquals(TestData.VALID_CONFIG_LINE_MORE_LINES_GCODE_2, got.value);
		got = toDeviceQueue.take();
		assertEquals(TestData.VALID_CONFIG_LINE_MORE_LINES_GCODE_3, got.value);
	}

}
