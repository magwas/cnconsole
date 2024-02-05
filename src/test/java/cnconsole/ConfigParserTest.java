package cnconsole;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConfigParserTest {

	@Test
	@DisplayName("config lines should have at least two comma")
	void test() {
		ConfigParser parser = new ConfigParser();
		assertThrows(IllegalArgumentException.class, () -> parser.parse("a,b"));
	}

	@Test
	@DisplayName("if no init line, the init list is empty")
	void test1() {
		ConfigParser parser = new ConfigParser();
		Config config = parser.parse("a,b,R1");
		assertEquals(0, config.initGcodes.size());
	}

	@Test
	@DisplayName("if the key is one character, it goes to the command key")
	void test2() {
		ConfigParser parser = new ConfigParser();
		Config config = parser.parse("a,b,R1");
		assertEquals('a', config.commands.get(0).key);
	}

	@Test
	@DisplayName("if the key is more characters and not a keycode string, an IllegalException is thrown")
	void test3() {
		ConfigParser parser = new ConfigParser();
		assertThrows(IllegalArgumentException.class, () -> parser.parse("101,b,R1"));
	}

	@Test
	@DisplayName("non-hex key with more than one char a ncurses keycode string")
	void test4() {
		ConfigParser parser = new ConfigParser();
		Config config = parser.parse("DOWN,b,R1");
		assertEquals(0x102, config.commands.get(0).key);
	}

}
