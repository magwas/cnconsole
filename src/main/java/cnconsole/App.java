package cnconsole;

import java.io.IOException;

import cnconsole.system.SystemServices;

public class App {

	private SystemServices sys;

	public App(SystemServices sys) {
		this.sys = sys;
	}

	public void init() throws IOException {
		sys.init();
		String home = sys.getHome();
		sys.readfile(home + "/.cncrc");
		String[] files = sys.getargs();
		sys.openDevice(files[0]);
	}

}
