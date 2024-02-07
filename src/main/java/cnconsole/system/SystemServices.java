package cnconsole.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import io.webfolder.curses4j.Curses;
import io.webfolder.curses4j.Window;

public class SystemServices {
	private static final int YELLOW_TEXT = 2;
	private static final int SPACE_ABOVE_CONSOLE = 15;
	private static final int NORMAL_TEXT = 1;
	private String[] args;
	private Window win;
	private boolean hasColors;

	public SystemServices(String[] args) {
		this.args = args;

	}

	public InputStream getInputStream(String path) throws FileNotFoundException {
		return new FileInputStream(path);
	}

	public OutputStream getOutputStream(String path)
			throws FileNotFoundException {
		return new FileOutputStream(path);
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

	public void doPanic(String where, Exception e) {
		this.endUI();
		System.err.println("PANIC at " + where);
		e.printStackTrace();
		System.exit(1);
	}

	public String[] getargs() {
		return args;
	}

	public void initScreen() {
		Curses.initscr();
		Curses.noecho();
		Curses.keypad(true);
		Window.stdscr.nodelay(true);
		win = Curses.subwin(Curses.LINES() - SPACE_ABOVE_CONSOLE, getScreenWidth(),
				15, 0);
		win.scrollok(true);
		win.printw("starting up\n");
		win.refresh();
		hasColors = Curses.has_colors();
		if (hasColors) {
			writetoConsole("Has colors");
			Curses.start_color();
			Curses.init_pair(NORMAL_TEXT, Curses.COLOR_WHITE, Curses.COLOR_BLACK);
			Curses.init_pair(YELLOW_TEXT, Curses.COLOR_YELLOW, Curses.COLOR_BLACK);
		} else {
			writetoConsole("No colors");
		}

	}

	public void refresh() {
		Curses.refresh();
	}

	public int getScreenWidth() {
		return Curses.COLS();
	}

	public int getCh() {
		return Curses.getch();
	}

	public void writeAt(int row, int col, String string) {
		Curses.mvprintw(row, col, "%s", string);
	}

	public void writetoConsole(String string) {
		win.printw("%s\n", string);
		win.refresh();
	}

	public void writeWarningToConsole(String string) {
		Curses.attron(Curses.COLOR_PAIR(YELLOW_TEXT));
		win.printw("%s\n", string);
		Curses.attroff(Curses.COLOR_PAIR(YELLOW_TEXT));
		win.refresh();
	}

	public void endUI() {
		Curses.endwin();
	}

}