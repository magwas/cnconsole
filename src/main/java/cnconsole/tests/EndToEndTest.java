package cnconsole.tests;

import java.io.IOException;
import java.util.Arrays;

import cnconsole.App;
import cnconsole.ConfigParser;
import cnconsole.system.Device;
import cnconsole.system.SystemServices;
import io.webfolder.curses4j.Curses;

public class EndToEndTest {

	public static void main(String[] args) throws IOException {
		System.setProperty("user.home", System.getProperty("user.dir"));
		SystemServices sys = new SystemServices(args);
		ConfigParser parser = new ConfigParser();
		App app = new App(sys, parser);
		app.init();
		app.screenSetup();
		Curses.endwin();
		System.err.println("\n\n\nscreen set up");
		System.out.println("home: " + sys.getHome());
		System.out.println("from testfile: " + sys.readfile("testfile"));
		System.out.println("args: " + Arrays.asList(sys.getargs()));
		System.out.println("screen width: " + sys.getScreenWidth());
		Device fifo = sys.openDevice("testfifo");
		sys.writeToDevice(fifo, "hello from test through fifo");
		System.out.println("from device: " + sys.readFromDevice(fifo));
	}

}
