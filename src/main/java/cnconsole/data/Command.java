package cnconsole.data;

import java.util.List;

public class Command {

	public String description;
	public List<String> gcode;

	public Command() {
	}

	public Command(String description, List<String> gcode) {
		this.description = description;
		this.gcode = gcode;
	}

}
