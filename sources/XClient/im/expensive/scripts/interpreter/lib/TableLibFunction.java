package im.expensive.scripts.interpreter.lib;

import im.expensive.scripts.interpreter.LuaValue;

class TableLibFunction extends LibFunction {
	public LuaValue call() {
		return argerror(1, "table expected, got no value");
	}
}
