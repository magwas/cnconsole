package cnconsole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import cnconsole.data.Command;
import cnconsole.data.Config;
import cnconsole.system.KeyCodes;

public class ConfigParser {

	HashMap<String, Integer> keycodes = KeyCodes.getInstance().keycodes;

	public String[] parseParts(String line) {
		String[] splat = line.split(",", 3);
		if (splat.length != 3) {
			throw new IllegalArgumentException(
					"form of config lines:<key>,<description>,<gcommand>[;<gcommand>]*");
		}
		return splat;
	}

	public Config parse(String rcData) {
		Config config = new Config();
		config.initGcodes = new ArrayList<String>();
		config.commands = new HashMap<Integer, Command>();
		String[] splat = rcData.split("\\R");
		for (String line : Arrays.asList(splat)) {
			parseLine(config, line);
		}
		return config;
	}

	public void parseLine(Config config, String line) {
		Command command = new Command();
		String[] parts = parseParts(line);
		Integer keyCode = parseKey(parts);
		command.description = parts[0] + ": " + parts[1];
		command.gcode = parseGcode(parts);
		if (keyCode == keycodes.get(KeyCodes.INIT))
			config.initGcodes = command.gcode;
		else
			config.commands.put(keyCode, command);
	}

	public ArrayList<String> parseGcode(String[] parts) {
		ArrayList<String> gcode = new ArrayList<String>();
		String[] codes = parts[2].split(",");
		for (String code : Arrays.asList(codes)) {
			gcode.add(code);
		}
		return gcode;
	}

	public Integer parseKey(String[] parts) {
		String key = parts[0];
		Integer keyCode;
		if (key.length() == 1) {
			keyCode = (int) key.charAt(0);
		} else {
			keyCode = keycodes.get(key);
			if (null == keyCode) {
				throw new IllegalArgumentException(
						"keycodes are either one character or a curses name");
			}
		}
		return keyCode;
	}

}
