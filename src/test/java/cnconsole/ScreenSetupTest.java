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
import cnconsole.display.Display;
import cnconsole.system.SystemServices;

class ScreenSetupTest {

	private static final int SCREEN_WIDTH = 80;
	private static final int RANDOM_LINENUMBER = 5;
	private static final String RANDOM_LINE_OF_ASCII_ART = "                | /       ";
	private static final int HALF_SCREEN_WIDTH = 40;
	private static SystemServices sys;
	private static Display display;

	@BeforeAll
	public static void setUp() throws IOException {
		sys = mock(SystemServices.class);
		App app = AppMock.behaviour();
		when(sys.getargs()).thenReturn(TestData.ARGS);
		when(sys.getHome()).thenReturn(TestData.USER_HOME);
		when(sys.readfile(TestData.RCFILE)).thenReturn(TestData.RC_DATA);
		when(sys.getScreenWidth()).thenReturn(SCREEN_WIDTH);
		LinkedTransferQueue<Event<?>> displayQueue = new LinkedTransferQueue<Event<?>>();
		display = new Display(app, sys, displayQueue);
	}

	@AfterAll
	public static void tearDown() {
		display.cease();
	}

	@Test
	@DisplayName("initializes the ui")
	void test() throws IOException {
		verify(sys).initScreen();
	}

	@Test
	@DisplayName("gets screen width")
	void test2() throws IOException {
		verify(sys).getScreenWidth();
	}

	@Test
	@DisplayName("writes out the ascii art")
	void test3() throws IOException {
		verify(sys).writeAt(RANDOM_LINENUMBER, HALF_SCREEN_WIDTH,
				RANDOM_LINE_OF_ASCII_ART);
	}

}
