package Squad.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import Squad.base.Module;

public class KeyBindManager {

	public void bind(Module mod, int bind) {
		mod.setKeyBind(bind);
		saveKeybinds();
	}
	
	public void unbind(Module mod) {
		mod.setKeyBind(0);
		saveKeybinds();
	}

	private void saveKeybinds() {
		try {
			File f = new File("keybinds.i");
			
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			
			PrintWriter output = new PrintWriter(new FileWriter(f, true));
			for (Module m : Squad.Squad.moduleManager.getModules()) {
				output.println(m.getClass().getName() + ":" + m.getKeyBind());
			}
			output.close();
		
		
	
	}catch (Exception e) {
		
	}
	}
	
	public void setKeyBinds() {
		try {
			for (Module m : Squad.Squad.moduleManager.getModules()) {
				for (String bind : readKeybinds()) {
					String[] splitted = bind.split(":");
					if (m.getClass().getName().equalsIgnoreCase(splitted[0])) {
						m.setKeyBind(Integer.valueOf(splitted[1]).intValue());
						
					}
				}
			}
		} catch (Exception e) {
			
		}
	}
	
	public String[] readKeybinds() {
		try {
			File f = new File("keybinds.i");
			if (!f.exists()) {
				f.createNewFile();
			}
			FileReader fileReader = new FileReader(f);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			List<String> lines = new ArrayList();
			String line = null;
			while ((line = bufferedReader.readLine()) !=null) {
				lines.add(line);
			}
			bufferedReader.close();
			return (String[]) lines.toArray(new String [lines.size()]);
		} catch (Exception e) {
			
		}
		return new String[] { "error" };
	}
	
	public int toInt(String keyCode) {
		if (keyCode.equalsIgnoreCase("a")) return Keyboard.KEY_A;
		if (keyCode.equalsIgnoreCase("b")) return Keyboard.KEY_B;
		if (keyCode.equalsIgnoreCase("c")) return Keyboard.KEY_C;
		if (keyCode.equalsIgnoreCase("d")) return Keyboard.KEY_D;
		if (keyCode.equalsIgnoreCase("e")) return Keyboard.KEY_E;
		if (keyCode.equalsIgnoreCase("f")) return Keyboard.KEY_F;
		if (keyCode.equalsIgnoreCase("g")) return Keyboard.KEY_G;
		if (keyCode.equalsIgnoreCase("h")) return Keyboard.KEY_H;
		if (keyCode.equalsIgnoreCase("i")) return Keyboard.KEY_I;
		if (keyCode.equalsIgnoreCase("j")) return Keyboard.KEY_J;
		if (keyCode.equalsIgnoreCase("k")) return Keyboard.KEY_K;
		if (keyCode.equalsIgnoreCase("l")) return Keyboard.KEY_L;
		if (keyCode.equalsIgnoreCase("m")) return Keyboard.KEY_M;
		if (keyCode.equalsIgnoreCase("n")) return Keyboard.KEY_N;
		if (keyCode.equalsIgnoreCase("o")) return Keyboard.KEY_O;
		if (keyCode.equalsIgnoreCase("p")) return Keyboard.KEY_P;
		if (keyCode.equalsIgnoreCase("q")) return Keyboard.KEY_Q;
		if (keyCode.equalsIgnoreCase("r")) return Keyboard.KEY_R;
		if (keyCode.equalsIgnoreCase("s")) return Keyboard.KEY_S;
		if (keyCode.equalsIgnoreCase("t")) return Keyboard.KEY_T;
		if (keyCode.equalsIgnoreCase("u")) return Keyboard.KEY_U;
		if (keyCode.equalsIgnoreCase("v")) return Keyboard.KEY_V;
		if (keyCode.equalsIgnoreCase("w")) return Keyboard.KEY_W;
		if (keyCode.equalsIgnoreCase("x")) return Keyboard.KEY_X;
		if (keyCode.equalsIgnoreCase("y")) return Keyboard.KEY_Y;
		if (keyCode.equalsIgnoreCase("z")) return Keyboard.KEY_Z;

		if (keyCode.equalsIgnoreCase("0")) return Keyboard.KEY_0;
		if (keyCode.equalsIgnoreCase("1")) return Keyboard.KEY_1;
		if (keyCode.equalsIgnoreCase("2")) return Keyboard.KEY_2;
		if (keyCode.equalsIgnoreCase("3")) return Keyboard.KEY_3;
		if (keyCode.equalsIgnoreCase("4")) return Keyboard.KEY_4;
		if (keyCode.equalsIgnoreCase("5")) return Keyboard.KEY_5;
		if (keyCode.equalsIgnoreCase("6")) return Keyboard.KEY_6;
		if (keyCode.equalsIgnoreCase("7")) return Keyboard.KEY_7;
		if (keyCode.equalsIgnoreCase("8")) return Keyboard.KEY_8;
		if (keyCode.equalsIgnoreCase("9")) return Keyboard.KEY_9;

		if (keyCode.equalsIgnoreCase("f1")) return Keyboard.KEY_F1;
		if (keyCode.equalsIgnoreCase("f2")) return Keyboard.KEY_F2;
		if (keyCode.equalsIgnoreCase("f3")) return Keyboard.KEY_F3;
		if (keyCode.equalsIgnoreCase("f4")) return Keyboard.KEY_F4;
		if (keyCode.equalsIgnoreCase("f5")) return Keyboard.KEY_F5;
		if (keyCode.equalsIgnoreCase("f6")) return Keyboard.KEY_F6;
		if (keyCode.equalsIgnoreCase("f7")) return Keyboard.KEY_F7;
		if (keyCode.equalsIgnoreCase("f8")) return Keyboard.KEY_F8;
		if (keyCode.equalsIgnoreCase("f9")) return Keyboard.KEY_F9;
		if (keyCode.equalsIgnoreCase("f10")) return Keyboard.KEY_F10;
		if (keyCode.equalsIgnoreCase("f11")) return Keyboard.KEY_F11;
		if (keyCode.equalsIgnoreCase("f12")) return Keyboard.KEY_F12;

		if (keyCode.equalsIgnoreCase("numpad0")) return Keyboard.KEY_NUMPAD0;
		if (keyCode.equalsIgnoreCase("numpad1")) return Keyboard.KEY_NUMPAD1;
		if (keyCode.equalsIgnoreCase("numpad2")) return Keyboard.KEY_NUMPAD2;
		if (keyCode.equalsIgnoreCase("numpad3")) return Keyboard.KEY_NUMPAD3;
		if (keyCode.equalsIgnoreCase("numpad4")) return Keyboard.KEY_NUMPAD4;
		if (keyCode.equalsIgnoreCase("numpad5")) return Keyboard.KEY_NUMPAD5;
		if (keyCode.equalsIgnoreCase("numpad6")) return Keyboard.KEY_NUMPAD6;
		if (keyCode.equalsIgnoreCase("numpad7")) return Keyboard.KEY_NUMPAD7;
		if (keyCode.equalsIgnoreCase("numpad8")) return Keyboard.KEY_NUMPAD8;
		if (keyCode.equalsIgnoreCase("numpad9")) return Keyboard.KEY_NUMPAD9;
		
		if (keyCode.equalsIgnoreCase("up")) return Keyboard.KEY_UP;
		if (keyCode.equalsIgnoreCase("down")) return Keyboard.KEY_DOWN;
		if (keyCode.equalsIgnoreCase("right")) return Keyboard.KEY_RIGHT;
		if (keyCode.equalsIgnoreCase("left")) return Keyboard.KEY_LEFT;
		
		if (keyCode.equalsIgnoreCase("del")) return Keyboard.KEY_DELETE;
		if (keyCode.equalsIgnoreCase("insert"))	return Keyboard.KEY_INSERT;
		if (keyCode.equalsIgnoreCase("end")) return Keyboard.KEY_END;
		if (keyCode.equalsIgnoreCase("home")) return Keyboard.KEY_HOME;
		if (keyCode.equalsIgnoreCase("rshift"))	return Keyboard.KEY_RSHIFT;
		if (keyCode.equalsIgnoreCase("lshift")) return Keyboard.KEY_LSHIFT;
		if (keyCode.equalsIgnoreCase("tab")) return Keyboard.KEY_TAB;
		if (keyCode.equalsIgnoreCase(".")) return Keyboard.KEY_PERIOD;
		if (keyCode.equalsIgnoreCase("strg")) return Keyboard.KEY_LCONTROL;
		if (keyCode.equalsIgnoreCase("alt")) return Keyboard.KEY_LMENU;
		if (keyCode.equalsIgnoreCase("hashtag")) return Keyboard.KEY_SLASH;
		
		return 0;
	}

}