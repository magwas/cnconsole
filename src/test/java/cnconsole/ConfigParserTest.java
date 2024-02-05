package cnconsole;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConfigParserTest {

	private ConfigParser parser;

	@BeforeEach
	void setUp() {
		parser = new ConfigParser();

	}

	@Test
	@DisplayName("config lines should have at least two comma")
	void test() {
		assertThrows(IllegalArgumentException.class, () -> parser.parse(TestData.TRUNCATED_CONFIG_LINE));
	}

	@Test
	@DisplayName("if no init line, the init list is empty")
	void test1() {
		Config config = parser.parse(TestData.NO_INIT_CONFIG);
		assertEquals(0, config.initGcodes.size());
	}

	@Test
	@DisplayName("if the key is one character, it goes to the command key")
	void test2() {
		Config config = parser.parse(TestData.NO_INIT_CONFIG);
		assertEquals('a', config.commands.get(0).key);
	}

	@Test
	@DisplayName("if the key is more characters and not a keycode string, an IllegalException is thrown")
	void test3() {
		assertThrows(IllegalArgumentException.class,
				() -> parser.parse(TestData.MORE_CHARACTER_NON_KEYWORD_KEY_CONFIG_LINE));
	}

	@Test
	@DisplayName("non-hex key with more than one char a ncurses keycode string")
	void test4() {
		Config config = parser.parse(TestData.VALID_CONFIG_LINE);
		assertEquals(TestData.VALID_CONFIG_KEY, config.commands.get(0).key);
	}

	@Test
	@DisplayName("description is stored with the key and description provided in the config")
	void test5() {
		Config config = parser.parse(TestData.VALID_CONFIG_LINE);
		assertEquals(TestData.DESCRIPTION_OF_VALID_CONFIG_LINE, config.commands.get(0).description);
	}

	@Test
	@DisplayName("gcode is stored for the key")
	void test6() {
		Config config = parser.parse(TestData.VALID_CONFIG_LINE);
		assertEquals(TestData.VALID_CONFIG_GCODE, config.commands.get(0).gcode.get(0));
	}

	@Test
	@DisplayName("more lines of gcode are separated by ','")
	void test7() {
		Config config = parser.parse(TestData.VALID_CONFIG_LINE_MORE_LINES);
		assertEquals(TestData.THIRD_GCODE, config.commands.get(0).gcode.get(2));
	}

	@Test
	@DisplayName("init directive does not get to the commands list")
	void test8() {
		Config config = parser.parse(TestData.INIT_LINE);
		assertEquals(0, config.commands.size());
	}

	@Test
	@DisplayName("init sequence is remembered as such")
	void test9() {
		Config config = parser.parse(TestData.INIT_LINE);
		assertEquals(TestData.THIRD_GCODE, config.initGcodes.get(2));
	}

	@Test
	@DisplayName("all lines of config are parsed")
	void test10() {
		ConfigParser parser = new ConfigParser();
		Config config = parser.parse(TestData.RC_DATA);
		assertEquals(TestData.VALID_CONFIG_GCODE, config.commands.get(1).gcode.get(0));
	}

}
