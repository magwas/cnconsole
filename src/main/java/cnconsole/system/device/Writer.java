package cnconsole.system.device;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import cnconsole.App;
import cnconsole.system.SystemServices;

class Writer extends Thread {

	private final Device device;
	private App app;
	private SystemServices sys;

	Writer(App app, SystemServices sys, Device device) {
		this.app = app;
		this.device = device;
		this.sys = sys;
	}

	@Override
	public void run() {
		sys.writetoConsole("Writer thread started");
		while (this.device.isopen) {
			try {
				loop();
			} catch (InterruptedException | IOException e) {
				app.panic("device.Writer", e);
			}

		}
	}

	public String loop() throws InterruptedException, IOException {
		String element = null;
		element = this.device.toDevice.poll(1, TimeUnit.SECONDS);
		if (null != element)
			this.device.output.write(element.getBytes(Charset.defaultCharset()));
		return element;
	}
}