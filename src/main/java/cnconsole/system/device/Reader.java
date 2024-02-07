package cnconsole.system.device;

import java.io.IOException;
import java.util.Arrays;

import cnconsole.App;
import cnconsole.data.Event;
import cnconsole.system.SystemServices;

class Reader extends Thread {
	private final Device device;
	private App app;
	private SystemServices sys;

	Reader(App app, SystemServices sys, Device device) {
		this.app = app;
		this.device = device;
		this.sys = sys;
	}

	@Override
	public void run() {
		sys.writetoConsole("Reader thread started");
		while (this.device.isopen) {
			try {
				loop();
			} catch (InterruptedException | IOException e) {
				app.panic("device.Reader", e);
			}
		}

	}

	public void loop() throws InterruptedException, IOException {
		byte[] readBuf = new byte[1024];
		int read = 0;
		Event<String> event = new Event<String>();
		read = device.input.read(readBuf);
		byte[] arr = Arrays.copyOfRange(readBuf, 0, read);
		event.name = this.device.eventName;
		event.value = new String(arr);
		this.device.fromDevice.transfer(event);
	}
}