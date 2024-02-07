package cnconsole.machine;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

import cnconsole.App;
import cnconsole.data.Event;

public class Machine extends Thread {

	private LinkedTransferQueue<Event<String>> inputQueue;
	private LinkedTransferQueue<Event<?>> outputQueue;
	private LinkedTransferQueue<Event<?>> fromDeviceQueue;
	private LinkedTransferQueue<String> toDeviceQueue;
	private boolean isRunning;

	public Machine(App app, LinkedTransferQueue<Event<String>> inputQueue,
			LinkedTransferQueue<Event<?>> outputQueue,
			LinkedTransferQueue<Event<?>> fromDeviceQueue,
			LinkedTransferQueue<String> toDeviceQueue) {
		this.inputQueue = inputQueue;
		this.outputQueue = outputQueue;
		this.fromDeviceQueue = fromDeviceQueue;
		this.toDeviceQueue = toDeviceQueue;
	}

	@Override
	public void run() {
		isRunning = true;
		while (isRunning) {
			loop();
		}
	}

	public void loop() {
		Event<String> input = inputQueue.poll();
		if (null != input) {
			toDeviceQueue.add(input.value + "\n");
		}
		Event<?> fromDevice = null;
		try {
			fromDevice = fromDeviceQueue.poll(10, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			return;
		}
		if (null == fromDevice)
			return;
		outputQueue.add(fromDevice);
	}

	public void cease() {
		isRunning = false;
	}
}
