package cnconsole;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cnconsole.system.SystemServices;

class DisplayTest {

	private SystemServices sys;
	private Display display;

	@BeforeEach
	void setUp() {
		sys = mock(SystemServices.class);
		display = new Display(sys);

	}

	@Test
	@DisplayName("setting coordinates updates the X coordinate")
	void test2() throws IOException {
		display.setCoords(2.7171, 3.1415, 623);
		verify(sys).writeAt(Constants.COORDS_X[0], Constants.COORDS_X[1], "  2.72");
	}

	@Test
	@DisplayName("setting coordinates updates the Y coordinate")
	void test3() throws IOException {
		display.setCoords(2.7171, 31.4, 623);
		verify(sys).writeAt(Constants.COORDS_Y[0], Constants.COORDS_Y[1], " 31.40");
	}

	@Test
	@DisplayName("setting coordinates updates the Y coordinate")
	void test4() throws IOException {
		display.setCoords(2.7171, 31.4, 623);
		verify(sys).writeAt(Constants.COORDS_Z[0], Constants.COORDS_Z[1], "623.00");
	}

	@Test
	@DisplayName("setting power updates the P value")
	void test5() throws IOException {
		display.setPower(50);
		verify(sys).writeAt(Constants.COORDS_P[0], Constants.COORDS_P[1], " 50");
	}

	@Test
	@DisplayName("setting power updates the P value - works for 100")
	void test6() throws IOException {
		display.setPower(100);
		verify(sys).writeAt(Constants.COORDS_P[0], Constants.COORDS_P[1], "100");
	}

	@Test
	@DisplayName("setting power updates the P value - works for 0")
	void test7() throws IOException {
		display.setPower(0);
		verify(sys).writeAt(Constants.COORDS_P[0], Constants.COORDS_P[1], "  0");
	}

}
