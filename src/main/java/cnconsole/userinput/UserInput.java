package cnconsole.userinput;

import java.util.concurrent.LinkedTransferQueue;

import cnconsole.system.SystemServices;

public class UserInput extends Thread {

	private LinkedTransferQueue<Integer> inputQueue;
	private SystemServices sys;
	private boolean isRunning;

	public UserInput(SystemServices sys,
			LinkedTransferQueue<Integer> inputQueue) {
		this.sys = sys;
		this.inputQueue = inputQueue;
	}

	@Override
	public void run() {
		isRunning = true;
		while (isRunning) {
			int got = sys.getCh();
			inputQueue.add(got);
		}
	}

	public void cease() {
		isRunning = false;
	}

}
