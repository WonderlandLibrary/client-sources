package com.kilo.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.kilo.Kilo;
import com.kilo.ui.inter.slotlist.part.Macro;
import com.kilo.util.ServerUtil;

public class MacroManager {

	private static List<Macro> macros = new CopyOnWriteArrayList<Macro>();
	
	public static List<Macro> getList() {
		return macros;
	}
	
	public static void addMacro(Macro f) {
		macros.add(f);
	}
	
	public static void addMacro(int index, Macro w) {
		macros.add(index, w);
	}
	
	public static void removeMacro(Macro w) {
		macros.remove(w);
	}
	
	public static void removeMacro(int index) {
		macros.remove(macros.get(index));
	}
	
	public static Macro getMacro(int index) {
		if (macros.size() == 0) {
			return null;
		}
		return macros.get(index);
	}
	
	public static Macro getMacro(String n) {
		for(Macro w : macros) {
			if (w.name.equalsIgnoreCase(n)) {
				return w;
			}
		}
		return null;
	}
	
	public static int getIndex(Macro w) {
		return macros.indexOf(w);
	}
	
	public static int getSize() {
		return getList().size();
	}
}
