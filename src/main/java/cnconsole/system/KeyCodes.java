package cnconsole.system;

import java.util.HashMap;

public class KeyCodes {

	public static final String INIT = "INIT";

	public HashMap<String, Integer> keycodes;

	static KeyCodes instance;

	public static KeyCodes getInstance() {
		if (null == instance) {
			instance = new KeyCodes();
		}
		return instance;
	}

	KeyCodes() {
		keycodes = new HashMap<String, Integer>();
		keycodes.put(INIT, 0x1000);
		keycodes.put("CODE_YES", 0x100);
		keycodes.put("BREAK", 0x101);
		keycodes.put("DOWN", 0x102);
		keycodes.put("UP", 0x103);
		keycodes.put("LEFT", 0x104);
		keycodes.put("RIGHT", 0x105);
		keycodes.put("HOME", 0x106);
		keycodes.put("BACKSPACE", 0x107);
		keycodes.put("F0", 0x108);
		keycodes.put("DL", 0x148);
		keycodes.put("IL", 0x149);
		keycodes.put("DC", 0x14a);
		keycodes.put("IC", 0x14b);
		keycodes.put("EIC", 0x14c);
		keycodes.put("CLEAR", 0x14d);
		keycodes.put("EOS", 0x14e);
		keycodes.put("EOL", 0x14f);
		keycodes.put("SF", 0x150);
		keycodes.put("SR", 0x151);
		keycodes.put("NPAGE", 0x152);
		keycodes.put("PPAGE", 0x153);
		keycodes.put("STAB", 0x154);
		keycodes.put("CTAB", 0x155);
		keycodes.put("CATAB", 0x156);
		keycodes.put("ENTER", 0x157);
		keycodes.put("SRESET", 0x158);
		keycodes.put("RESET", 0x159);
		keycodes.put("PRINT", 0x15a);
		keycodes.put("LL", 0x15b);
		keycodes.put("ABORT", 0x15c);
		keycodes.put("SHELP", 0x15d);
		keycodes.put("LHELP", 0x15e);
		keycodes.put("BTAB", 0x15f);
		keycodes.put("BEG", 0x160);
		keycodes.put("CANCEL", 0x161);
		keycodes.put("CLOSE", 0x162);
		keycodes.put("COMMAND", 0x163);
		keycodes.put("COPY", 0x164);
		keycodes.put("CREATE", 0x165);
		keycodes.put("END", 0x166);
		keycodes.put("EXIT", 0x167);
		keycodes.put("FIND", 0x168);
		keycodes.put("HELP", 0x169);
		keycodes.put("MARK", 0x16a);
		keycodes.put("MESSAGE", 0x16b);
		keycodes.put("MOVE", 0x16c);
		keycodes.put("NEXT", 0x16d);
		keycodes.put("OPEN", 0x16e);
		keycodes.put("OPTIONS", 0x16f);
		keycodes.put("PREVIOUS", 0x170);
		keycodes.put("REDO", 0x171);
		keycodes.put("REFERENCE", 0x172);
		keycodes.put("REFRESH", 0x173);
		keycodes.put("REPLACE", 0x174);
		keycodes.put("RESTART", 0x175);
		keycodes.put("RESUME", 0x176);
		keycodes.put("SAVE", 0x177);
		keycodes.put("SBEG", 0x178);
		keycodes.put("SCANCEL", 0x179);
		keycodes.put("SCOMMAND", 0x17a);
		keycodes.put("SCOPY", 0x17b);
		keycodes.put("SCREATE", 0x17c);
		keycodes.put("SDC", 0x17d);
		keycodes.put("SDL", 0x17e);
		keycodes.put("SELECT", 0x17f);
		keycodes.put("SEND", 0x180);
		keycodes.put("SEOL", 0x181);
		keycodes.put("SEXIT", 0x182);
		keycodes.put("SFIND", 0x183);
		keycodes.put("SHOME", 0x184);
		keycodes.put("SIC", 0x185);
		keycodes.put("SLEFT", 0x187);
		keycodes.put("SMESSAGE", 0x188);
		keycodes.put("SMOVE", 0x189);
		keycodes.put("SNEXT", 0x18a);
		keycodes.put("SOPTIONS", 0x18b);
		keycodes.put("SPREVIOUS", 0x18c);
		keycodes.put("SPRINT", 0x18d);
		keycodes.put("SREDO", 0x18e);
		keycodes.put("SREPLACE", 0x18f);
		keycodes.put("SRIGHT", 0x190);
		keycodes.put("SRSUME", 0x191);
		keycodes.put("SSAVE", 0x192);
		keycodes.put("SSUSPEND", 0x193);
		keycodes.put("SUNDO", 0x194);
		keycodes.put("SUSPEND", 0x195);
		keycodes.put("UNDO", 0x196);

	}

}
