package cnconsole;

import java.io.IOException;
import java.util.concurrent.LinkedTransferQueue;

import cnconsole.data.Event;
import cnconsole.deviceparser.DeviceParser;
import cnconsole.display.Display;
import cnconsole.machine.Machine;
import cnconsole.router.Router;
import cnconsole.system.SystemServices;
import cnconsole.system.device.Device;
import cnconsole.userinput.UserInput;

public class Main {

	public static void main(String[] args) {

		SystemServices sys = new SystemServices(args);
		ConfigParser parser = new ConfigParser();
		App app = new App(sys, parser);
		Device device = null;
		try {
			device = app.init();
		} catch (IOException e) {
			System.err.println("Could not open " + args[0]);
			System.exit(1);
		}

		LinkedTransferQueue<Event<String>> rawOutputOfDevice = device.fromDevice;
		LinkedTransferQueue<String> rawCommandsToDevice = device.toDevice;
		LinkedTransferQueue<Integer> inputFromUser = new LinkedTransferQueue<Integer>();
		LinkedTransferQueue<Event<?>> displayableEvents = new LinkedTransferQueue<Event<?>>();
		LinkedTransferQueue<Event<String>> commandsToMachine = new LinkedTransferQueue<Event<String>>();
		LinkedTransferQueue<Event<?>> eventsFromMachine = new LinkedTransferQueue<Event<?>>();
		LinkedTransferQueue<Event<?>> parsedOutputOfDevice = new LinkedTransferQueue<Event<?>>();

		Display display = new Display(app, sys, displayableEvents);
		UserInput userInput = new UserInput(sys, inputFromUser);
		DeviceParser deviceParser = new DeviceParser(app, sys, rawOutputOfDevice,
				parsedOutputOfDevice);
		Machine machine = new Machine(app, commandsToMachine, eventsFromMachine,
				parsedOutputOfDevice, rawCommandsToDevice);

		Router router = new Router(app, sys, inputFromUser, eventsFromMachine,
				commandsToMachine, displayableEvents);

		device.start();
		display.start();
		userInput.start();
		deviceParser.start();
		machine.start();
		router.start();
		displayableEvents.add(new Event<Object>(Display.UNPARSED, "main started"));

	}

}
