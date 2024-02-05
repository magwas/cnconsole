package cnconsole.system.device;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

class Writer extends Thread {

	private final Device device;

	Writer(Device device) {
		this.device = device;
	}

	@Override
	public void run() {
		while (this.device.isopen) {
			loop();
		}
	}

	public String loop() {
		String element = null;
		try {
			element = this.device.outputQueue.poll(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
		try {
			if (null != element)
				this.device.output.write(element.getBytes(Charset.defaultCharset()));
		} catch (IOException e) {
			this.device.isopen = false;
		}
		return element;
	}
}