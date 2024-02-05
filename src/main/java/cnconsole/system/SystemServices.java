package cnconsole.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Scanner;

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
			data = data.concat(myReader.nextLine());
		}
		myReader.close();
		return data;
	}

	public String[] getargs() {
		return args;
	}

	public Device openDevice(String deviceFileName) throws IOException {
		File file = new File(deviceFileName);
		Device dev = new Device();
		dev.input = new FileInputStream(file);
		dev.output = new FileOutputStream(file);
		return dev;
	}

	public void writeToDevice(Device dev, String data) throws IOException {
		dev.output.write(data.getBytes(Charset.defaultCharset()));
	}

	public String readFromDevice(Device dev) throws IOException {
		int available = dev.input.available();
		if (0 == available)
			return null;
		byte[] buf = new byte[1024];
		int read = dev.input.read(buf);
		byte[] arr = Arrays.copyOfRange(buf, 0, read);
		return new String(arr);
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

}