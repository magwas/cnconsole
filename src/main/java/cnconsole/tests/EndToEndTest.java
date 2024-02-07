package cnconsole.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

import cnconsole.App;
import cnconsole.ConfigParser;
import cnconsole.data.Event;
import cnconsole.display.Display;
import cnconsole.system.SystemServices;
import cnconsole.system.device.Device;
import io.webfolder.curses4j.Curses;

public class EndToEndTest {

	public static void main(String[] args)
			throws IOException, InterruptedException {
		App app = null;
		LinkedTransferQueue<Event<?>> displayQueue = new LinkedTransferQueue<Event<?>>();
		System.setProperty("user.home", System.getProperty("user.dir"));
		SystemServices sys = new SystemServices(args);
		ConfigParser parser = new ConfigParser();
		try {
			app = new App(sys, parser);
			Device device = app.init();
			device.start();
			new Display(app, sys, displayQueue).start();

			if (args.length != 1)
				Curses.getch();
			sys.endUI();
			System.out.println("\n\n\nscreen set up");
			System.out.println("init: " + app.getConfig().initGcodes);
			System.out.println("home: " + sys.getHome());
			System.out.println("from testfile: " + sys.readfile("testfile"));
			System.out.println("args: " + Arrays.asList(sys.getargs()));
			System.out.println("screen width: " + sys.getScreenWidth());
			app.getDevice().write("hello from test through fifo");
			Event<String> inputEvent = null;
			for (int i = 0; i < 5; i++) {
				inputEvent = app.getInputQueue().poll(7, TimeUnit.SECONDS);
				if (null != inputEvent)
					break;
			}
			String read = inputEvent.name + ": " + inputEvent.value;
			System.out.println("from device: " + read);
			System.exit(0);
		} catch (Exception t) {
			app.panic("e2e", t);
		}
	}

}
