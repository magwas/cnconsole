package cnconsole;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cnconsole.system.SystemServices;

class ScreenSetupTest {

	private static final int SCREEN_WIDTH = 80;
	private static final int RANDOM_LINENUMBER = 5;
	private static final String RANDOM_LINE_OF_ASCII_ART = "                | /       ";
	private static final int HALF_SCREEN_WIDTH = 40;
	private SystemServices sys;
	private App app;
	private ConfigParser parser;

	@BeforeEach
	public void setUp() throws IOException {
		sys = mock(SystemServices.class);
		parser = new ConfigParser();
		when(sys.getargs()).thenReturn(TestData.ARGS);
		when(sys.getHome()).thenReturn(TestData.USER_HOME);
		when(sys.readfile(TestData.RCFILE)).thenReturn(TestData.RC_DATA);
		when(sys.getScreenWidth()).thenReturn(SCREEN_WIDTH);
		app = new App(sys, parser);
		app.init();
	}

	@Test
	@DisplayName("initializes the ui")
	void test() throws IOException {
		app.screenSetup();
		verify(sys).initScreen();
	}

	@Test
	@DisplayName("gets screen width")
	void test2() throws IOException {
		app.screenSetup();
		verify(sys).getScreenWidth();
	}

	@Test
	@DisplayName("writes out the ascii art")
	void test3() throws IOException {
		app.screenSetup();
		verify(sys).writeAt(RANDOM_LINENUMBER, HALF_SCREEN_WIDTH,
				RANDOM_LINE_OF_ASCII_ART);
	}

	@Test
	@DisplayName("writes out the keybindings")
	void test4() throws IOException {
		app.screenSetup();
		verify(sys).writeAt(2, 0, TestData.DESCRIPTION_OF_VALID_CONFIG_LINE);
	}

}
