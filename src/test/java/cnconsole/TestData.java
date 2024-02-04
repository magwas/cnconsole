package cnconsole;

public class TestData {
	public static final String DEVICE = "/dev/ttyUSB0";
	public static final String FILE_NAME_2 = "b.cnc";
	public static final String FILE_NAME_1 = "a.gcode";
	public static final String[] ARGS = new String[] { DEVICE, FILE_NAME_1, FILE_NAME_2 };
	public static final String RC_DATA = "init,init,G28;G90;a,left 1cm,G91;"
			+ "G0X100;G90\nd,right 1cm,G91;G0X0-100;G91\n" + "h,home,g G28\n";
	public static final String USER_HOME = "/home/joe";
	public static final String RCFILE = USER_HOME + "/.cncrc";

}
