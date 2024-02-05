package cnconsole.tests;

import java.io.IOException;
import java.util.Arrays;

import cnconsole.system.Device;
import cnconsole.system.SystemServices;

public class EndToEndTest {

	public static void main(String[] args) throws IOException {
		SystemServices sys = new SystemServices(args);
		sys.init();
		sys.refresh();
		System.out.println(sys.getHome());
		System.out.println(sys.readfile("testfile"));
		System.out.println(Arrays.asList(sys.getargs()));
		Device fifo = sys.openDevice("testfifo");
		sys.writeToDevice(fifo, "hello from test");
		System.out.println(sys.readFromDevice(fifo));
	}

}
