package cnconsole;

public class TestData {
	public static final String UNPARSED_TEXT = "unparsed text from device";

	public static final String DEVICE = "/dev/ttyUSB0";
	public static final String FILE_NAME_2 = "b.cnc";
	public static final String FILE_NAME_1 = "a.gcode";
	public static final String[] ARGS = new String[] {
			DEVICE,
			FILE_NAME_1,
			FILE_NAME_2 };
	public static final String USER_HOME = "/home/joe";
	public static final String RCFILE = USER_HOME + "/.cncrc";
	public static final String NO_INIT_CONFIG = "a,b,R1";
	public static final String INIT_LINE = "INIT,init,G28,G21,G91\n";
	public static final String TRUNCATED_CONFIG_LINE = "a,b";
	public static final String MORE_CHARACTER_NON_KEYWORD_KEY_CONFIG_LINE = "101,b,G0 Y-0.01";
	public static final String DESCRIPTION_OF_VALID_CONFIG_LINE = "HOME: auto-home";
	public static final String VALID_CONFIG_LINE = "HOME,auto-home,G28\n";
	public static final String VALID_CONFIG_GCODE = "G28";
	public static final String VALID_CONFIG_LINE_MORE_LINES = "DOWN,backwards 10 um,G90,G0 Y-0.01,G91";
	public static final String VALID_CONFIG_LINE_MORE_LINES_GCODE_3 = "G91";
	public static final String VALID_CONFIG_LINE_MORE_LINES_GCODE_2 = "G0 Y-0.01";
	public static final String VALID_CONFIG_LINE_MORE_LINES_GCODE_1 = "G90";

	public static final String RC_DATA = INIT_LINE + VALID_CONFIG_LINE_MORE_LINES
			+ "\n" + VALID_CONFIG_LINE;
	public static final String KEY_MAP_STRING = "DOWN: backwards 10 um\n"
			+ DESCRIPTION_OF_VALID_CONFIG_LINE + "\n";

	public static final String OK = "OK";
	public static final String COORDINATES = "COORDINATES";
	public static final String UNPARSED = "UNPARSED";
	public static final String COMMAND = "COMMAND";
	public static final String IDLE = "IDLE";

	public static final String SOME_GARBAGE = "some garbage";
	public static final String COORDINATES_LINE = "X:0.00 Y:127.2 Z:145.14 E:0.00 Count X: 0 Y:10160 Z:116000";
	public static final float X_COORDINATE_IN_FLOAT = 0.0f;
	public static final float Y_COORDINATE_IN_FLOAT = 127.2f;
	public static final float Z_COORDINATE_IN_FLOAT = 145.14f;
	public static final Float[] COORDINATES_PAYLOAD = new Float[] {
			X_COORDINATE_IN_FLOAT,
			Y_COORDINATE_IN_FLOAT,
			Z_COORDINATE_IN_FLOAT };
	public static final String COORDINATES_LINES = COORDINATES_LINE + "\nok";

	public static final String WARNING_TEXT = "I am deeply concerned";

}
