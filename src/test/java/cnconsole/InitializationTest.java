package cnconsole;

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

	@BeforeEach
	public void setUp() throws FileNotFoundException {
		sys = mock(SystemServices.class);
		when(sys.getargs()).thenReturn(TestData.ARGS);
		when(sys.getHome()).thenReturn(TestData.USER_HOME);
		when(sys.readfile(TestData.RCFILE)).thenReturn(TestData.RC_DATA);
		app = new App(sys);
	}

	@Test
	@DisplayName("initializes the ui")
	void test() throws IOException {
		app.init();
		verify(sys).init();
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
		verify(sys).openDevice(TestData.DEVICE);
	}

}
