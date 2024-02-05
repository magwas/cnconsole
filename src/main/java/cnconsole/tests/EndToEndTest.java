package cnconsole.tests;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

import cnconsole.App;
import cnconsole.ConfigParser;
import cnconsole.system.Event;
import cnconsole.system.SystemServices;
import cnconsole.system.device.Device;
import io.webfolder.curses4j.Curses;

public class EndToEndTest {

	public static void main(String[] args)
			throws IOException, InterruptedException {
		System.setProperty("user.home", System.getProperty("user.dir"));
		SystemServices sys = new SystemServices(args);
		ConfigParser parser = new ConfigParser();
		App app = new App(sys, parser);
		app.init();
		app.screenSetup();
		if (args.length != 1)
			Curses.getch();
		sys.endUI();
		System.out.println("\n\n\nscreen set up");
		System.out.println("init: " + app.config.initGcodes);
		System.out.println("home: " + sys.getHome());
		System.out.println("from testfile: " + sys.readfile("testfile"));
		System.out.println("args: " + Arrays.asList(sys.getargs()));
		System.out.println("screen width: " + sys.getScreenWidth());
		LinkedTransferQueue<Event<String>> inputQueue = new LinkedTransferQueue<Event<String>>();
		Device fifo = new Device(new File("testfifo"), "FIFO", inputQueue);
		fifo.write("hello from test through fifo");
		Event<String> inputEvent = inputQueue.poll(2, TimeUnit.SECONDS);
		String read = inputEvent.name + ": " + inputEvent.value;
		System.out.println("from device: " + read);
		fifo.close();
		System.exit(0);
	}

}
