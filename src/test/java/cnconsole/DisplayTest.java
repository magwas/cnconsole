package cnconsole;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.concurrent.LinkedTransferQueue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cnconsole.data.Event;
import cnconsole.display.Constants;
import cnconsole.display.Display;
import cnconsole.system.SystemServices;

class DisplayTest {

	private static SystemServices sys;
	private static LinkedTransferQueue<Event<?>> displayQueue;
	private static Display display;

	@BeforeAll
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static void startUp() {
		sys = mock(SystemServices.class);
		App app = AppMock.behaviour();
		when(sys.getScreenWidth()).thenReturn(80);
		displayQueue = new LinkedTransferQueue<Event<?>>();
		display = new Display(app, sys, displayQueue);
		display.start();
		displayQueue
				.add(new Event("COORDINATES", new Float[] { 2.7171f, 31.4f, 623f }));
		displayQueue.add(new Event("POWER", 0));
		displayQueue.add(new Event("POWER", 50));
		displayQueue.add(new Event("POWER", 100));
		displayQueue.add(new Event("WORKING", ""));
		displayQueue.add(new Event("IDLE", ""));
		displayQueue.add(new Event("UNPARSED", TestData.SOME_GARBAGE));
		displayQueue.add(new Event("WARNING", TestData.WARNING_TEXT));
	}

	@AfterAll
	static void tearDown() {
		display.cease();
	}

	@Test
	@DisplayName("setting coordinates updates the X coordinate")
	void test2() throws IOException {
		verify(sys).writeAt(Constants.COORDS_X[0], 40 + Constants.COORDS_X[1],
				"  2.72");
	}

	@Test
	@DisplayName("setting coordinates updates the Y coordinate")
	void test3() throws IOException {
		verify(sys).writeAt(Constants.COORDS_Y[0], 40 + Constants.COORDS_Y[1],
				" 31.40");
	}

	@Test
	@DisplayName("setting coordinates updates the Y coordinate")
	void test4() throws IOException {
		verify(sys).writeAt(Constants.COORDS_Z[0], 40 + Constants.COORDS_Z[1],
				"623.00");
	}

	@Test
	@DisplayName("setting power updates the P value")
	void test5() throws IOException {
		verify(sys).writeAt(Constants.COORDS_P[0], 40 + Constants.COORDS_P[1],
				" 50");
	}

	@Test
	@DisplayName("setting power updates the P value - works for 100")
	void test6() throws IOException {
		verify(sys).writeAt(Constants.COORDS_P[0], 40 + Constants.COORDS_P[1],
				"100");
	}

	@Test
	@DisplayName("setting power updates the P value - works for 0")
	void test7() throws IOException {
		verify(sys).writeAt(Constants.COORDS_P[0], 40 + Constants.COORDS_P[1],
				"  0");
	}

	@Test
	@DisplayName("setting working to false displays 'idle'")
	void test8() throws IOException {
		verify(sys).writeAt(0, 40, "idle    ");
	}

	@Test
	@DisplayName("setting working to true displays 'working'")
	void test9() throws IOException {
		verify(sys).writeAt(0, 40, "working");
	}

	@Test
	@DisplayName("unparsed input goes to the console window")
	void test10() throws IOException {
		verify(sys).writetoConsole(TestData.SOME_GARBAGE);
	}

	@Test
	@DisplayName("warning goes to the console window in yellow")
	void test11() throws IOException {
		verify(sys).writeWarningToConsole(TestData.WARNING_TEXT);
	}

}
