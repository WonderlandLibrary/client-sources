package fun.ellant.scripts.interpreter.lib;

import fun.ellant.scripts.interpreter.LuaValue;

class TableLibFunction extends LibFunction {
	public LuaValue call() {
		return argerror(1, "table expected, got no value");
	}
}
