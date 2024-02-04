package cnconsole.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import io.webfolder.curses4j.Curses;

public class SystemServices {
	private String[] args;

	public SystemServices(String[] args) {
		this.args = args;
	}

	public void init() {
		Curses.initscr();
	}

	public String getHome() {
		return System.getProperty("user.home");
	}

	public String readfile(String file) throws FileNotFoundException {
		File myObj = new File("filename.txt");
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

	public FileChannel openDevice(String deviceFileName) throws IOException {
		File file = new File(deviceFileName);
		HashSet<OpenOption> opts = new HashSet<OpenOption>(Arrays.asList(
				new OpenOption[] { StandardOpenOption.DSYNC, StandardOpenOption.READ, StandardOpenOption.WRITE }));
		return FileChannel.open(file.toPath(), opts);
	}

}