package cnconsole.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.LinkedTransferQueue;

import cnconsole.system.device.Device;
import io.webfolder.curses4j.Curses;

public class SystemServices {
	private String[] args;

	public SystemServices(String[] args) {
		this.args = args;
	}

	public String getHome() {
		return System.getProperty("user.home");
	}

	public String readfile(String file) throws FileNotFoundException {
		File myObj = new File(file);
		Scanner myReader = new Scanner(myObj);
		String data = "";
		while (myReader.hasNextLine()) {
			data = data.concat(myReader.nextLine() + "\n");
		}
		myReader.close();
		return data;
	}

	public String[] getargs() {
		return args;
	}

	public void initScreen() {
		Curses.initscr();
	}

	public void refresh() {
		Curses.refresh();
	}

	public int getScreenWidth() {
		return Curses.COLS();
	}

	public void writeAt(int row, int col, String string) {
		Curses.mvprintw(row, col, "%s", string);
	}

	public void endUI() {
		Curses.endwin();
	}

	public Device open(String path, String eventName,
			LinkedTransferQueue<Event<String>> inputQueue)
			throws FileNotFoundException {

		return new Device(new File(path), "DEVICE", inputQueue);
	}

}