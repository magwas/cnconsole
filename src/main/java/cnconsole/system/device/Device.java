package cnconsole.system.device;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.LinkedTransferQueue;

import cnconsole.App;
import cnconsole.data.Event;
import cnconsole.system.SystemServices;

public class Device {
	public static final String CLOSED = "CLOSED";

	public LinkedTransferQueue<Event<String>> fromDevice;
	public LinkedTransferQueue<String> toDevice = new LinkedTransferQueue<String>();
	InputStream input;
	OutputStream output;
	boolean isopen = true;
	String eventName;

	private Thread writer;

	private Thread reader;

	public Device(App app, SystemServices sys, String path, String eventName,
			LinkedTransferQueue<Event<String>> inputQueue)
			throws FileNotFoundException {

		this.fromDevice = inputQueue;
		this.eventName = eventName;
		input = sys.getInputStream(path);
		output = sys.getOutputStream(path);
		writer = new Writer(app, sys, this);
		reader = new Reader(app, sys, this);
	}

	public void start() {
		writer.start();
		reader.start();
	}

	public void cease() {
		isopen = false;
	}

	public void close() {
		isopen = false;
		reader.interrupt();
		writer.interrupt();
	}

	public void write(String data) throws IOException {
		if (!isopen)
			throw new IOException("this device is closed");
		toDevice.add(data);
	}
}