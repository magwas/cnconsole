package cnconsole;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cnconsole.system.SystemServices;

class InitializationTest {

	private SystemServices sys;
	private App app;
	private ConfigParser parser;

	@BeforeEach
	public void setUp() throws FileNotFoundException {
		sys = mock(SystemServices.class);
		parser = mock(ConfigParser.class);
		when(sys.getargs()).thenReturn(TestData.ARGS);
		when(sys.getHome()).thenReturn(TestData.USER_HOME);
		when(sys.readfile(TestData.RCFILE)).thenReturn(TestData.RC_DATA);
		app = new App(sys, parser);
	}

	@Test
	@DisplayName("looks up the home directory")
	void test2() throws IOException {
		app.init();
		verify(sys).getHome();
	}

	@Test
	@DisplayName("reads .cncrc from the user's home directory")
	void test3() throws IOException {
		app.init();
		verify(sys).readfile(TestData.RCFILE);
	}

	@Test
	@DisplayName("gets the program arguments")
	void test4() throws IOException {
		app.init();
		verify(sys).getargs();
	}

	@Test
	@DisplayName("opens the device given as first argument in the command line")
	void test5() throws IOException {
		app.init();
		verify(sys).open(eq(TestData.ARGS[0]), eq("DEVICE"), any());
	}

	@Test
	@DisplayName("parses the config")
	void test6() throws IOException {
		app.init();
		verify(parser).parse(TestData.RC_DATA);
	}

}
