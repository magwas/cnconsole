package cnconsole;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cnconsole.system.SystemServices;

class InitializationTest {

	private static SystemServices sys;
	private static ConfigParser parser;

	@BeforeAll
	public static void setUp() throws IOException {
		sys = mock(SystemServices.class);
		parser = mock(ConfigParser.class);
		when(sys.getargs()).thenReturn(TestData.ARGS);
		when(sys.getHome()).thenReturn(TestData.USER_HOME);
		when(sys.readfile(TestData.RCFILE)).thenReturn(TestData.RC_DATA);
		when(sys.getInputStream(any()))
				.thenReturn(new ByteArrayInputStream(new byte[100]));
		App app = new App(sys, parser);
		app.init();
	}

	@Test
	@DisplayName("looks up the home directory")
	void test2() {
		verify(sys).getHome();
	}

	@Test
	@DisplayName("reads .cncrc from the user's home directory")
	void test3() throws FileNotFoundException {
		verify(sys).readfile(TestData.RCFILE);
	}

	@Test
	@DisplayName("gets the program arguments")
	void test4() {
		verify(sys).getargs();
	}

	@Test
	@DisplayName("opens the device given as first argument in the command line")
	void test5() throws FileNotFoundException {
		verify(sys).getInputStream(TestData.ARGS[0]);
	}

	@Test
	@DisplayName("parses the config")
	void test6() {
		verify(parser).parse(TestData.RC_DATA);
	}

}
