package cnconsole.system.device;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.LinkedTransferQueue;

import cnconsole.system.Event;

public class Device {
	public static final String CLOSED = "CLOSED";

	LinkedTransferQueue<Event<String>> inputQueue;
	LinkedTransferQueue<String> outputQueue = new LinkedTransferQueue<String>();
	FileInputStream input;
	FileOutputStream output;
	boolean isopen = true;
	String eventName;

	private Thread writer;

	private Thread reader;

	public Device(File file, String eventName,
			LinkedTransferQueue<Event<String>> inputQueue)
			throws FileNotFoundException {

		this.inputQueue = inputQueue;
		this.eventName = eventName;
		input = new FileInputStream(file);
		output = new FileOutputStream(file);
		writer = new Writer(this);
		writer.start();
		reader = new Reader(this);
		reader.start();
	}

	public void close() {
		isopen = false;
		reader.interrupt();
		writer.interrupt();
	}

	public void write(String data) throws IOException {
		if (!isopen)
			throw new IOException("this device is closed");
		outputQueue.add(data);
	}
}