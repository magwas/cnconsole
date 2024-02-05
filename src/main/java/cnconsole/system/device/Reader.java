package cnconsole.system.device;

import java.io.IOException;
import java.util.Arrays;

import cnconsole.system.Event;

class Reader extends Thread {
	private final Device device;

	Reader(Device device) {
		this.device = device;
	}

	@Override
	public void run() {
		while (this.device.isopen) {
			loop();
		}

	}

	public void loop() {
		byte[] readBuf = new byte[1024];
		int read;
		Event<String> event = new Event<String>();
		try {
			read = this.device.input.read(readBuf);
		} catch (IOException e) {
			this.device.isopen = false;
			event.name = Device.CLOSED;
			this.device.inputQueue.add(event);
			return;
		}
		byte[] arr = Arrays.copyOfRange(readBuf, 0, read);
		event.name = this.device.eventName;
		event.value = new String(arr);
		this.device.inputQueue.add(event);
	}
}