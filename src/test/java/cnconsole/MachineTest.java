package cnconsole;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.LinkedTransferQueue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cnconsole.data.Event;
import cnconsole.machine.Machine;

class MachineTest {

	private static LinkedTransferQueue<Event<String>> inputQueue;
	private static LinkedTransferQueue<Event<?>> outputQueue;
	private static LinkedTransferQueue<Event<?>> fromDeviceQueue;
	private static LinkedTransferQueue<String> toDeviceQueue;
	private static Machine machine;

	@BeforeAll
	public static void setUp() {
		App app = AppMock.behaviour();
		inputQueue = new LinkedTransferQueue<Event<String>>();
		fromDeviceQueue = new LinkedTransferQueue<Event<?>>();
		toDeviceQueue = new LinkedTransferQueue<String>();
		outputQueue = new LinkedTransferQueue<Event<?>>();
		machine = new Machine(app, inputQueue, outputQueue, fromDeviceQueue,
				toDeviceQueue);
		machine.start();
	}

	@AfterAll
	public static void tearDown() {
		machine.cease();
	}

	@Test
	@DisplayName("simple commands go directly to the device with newline")
	void test() throws InterruptedException {
		inputQueue
				.add(new Event<String>(TestData.COMMAND, TestData.VALID_CONFIG_GCODE));
		String got = toDeviceQueue.take();
		assertEquals(TestData.VALID_CONFIG_GCODE + "\n", got);
	}

	@Test
	@DisplayName("normal device output goes directly to the output queue")
	void test2() throws InterruptedException {
		fromDeviceQueue
				.add(new Event<String>(TestData.UNPARSED, TestData.SOME_GARBAGE));
		@SuppressWarnings("unchecked")
		Event<String> got = (Event<String>) outputQueue.take();
		assertEquals(TestData.UNPARSED, got.name);
		assertEquals(TestData.SOME_GARBAGE, got.value);
	}

}
