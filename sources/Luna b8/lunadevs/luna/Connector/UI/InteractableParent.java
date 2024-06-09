package lunadevs.luna.Connector.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.Connector.UI.Util.Inter;
import lunadevs.luna.Connector.UI.Util.TextBox;

public abstract class InteractableParent {

	public List<Inter> inters = new CopyOnWriteArrayList<Inter>();
	public boolean hover;
	public float x, y, width, height;
	
	public InteractableParent(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}
	
	public void update(int mx, int my) {
		for(Inter i : inters) {
			if (i.shown) { i.update(mx, my); }
		}
		hover = mouseOver(mx, my);
	}
	
	public void mouseClick(int mx, int my, int b) {
		for(Inter i : inters) {
			if (i.shown && i.enabled) { i.mouseClick(mx, my, b); }
		}
	}
	
	public void mouseRelease(int mx, int my, int b) {
		for(Inter i : inters) {
			if (i.shown && i.enabled) { i.mouseRelease(mx, my, b); }
		}
	}
	
	public void mouseScroll(int s) {
		for(Inter i : inters) {
			if (i.shown && i.enabled) { i.mouseScroll(s); }
		}
	}
	
	public void keyboardPress(int key) {
		for(Inter i : inters) {
			if (i.shown && i.enabled) { i.keyboardPress(key); }
		}
		if (key == Keyboard.KEY_TAB) {
			List<TextBox> tb = new ArrayList<TextBox>();
			for(int i = 0; i < inters.size(); i++) {
				Inter in = inters.get(i);
				if (in instanceof TextBox) {
					tb.add((TextBox)in);
				}
			}
			
			int active = -1;
			for(int i = 0; i < tb.size(); i++) {
				TextBox t = tb.get(i);
				if (t.active) {
					active = i;
					break;
				}
			}
			
			if (active != -1) {
				tb.get(active%tb.size()).active = false;
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					tb.get((active+tb.size()-1)%tb.size()).active = true;
				} else {
					tb.get((active+tb.size()+1)%tb.size()).active = true;
				}
			}
		}
	}
	
	public void keyboardRelease(int key) {
		for(Inter i : inters) {
			if (i.shown && i.enabled) { i.keyboardRelease(key); }
		}
	}
	
	public boolean mouseOver(int mx, int my) {
		if (x == -1 && y == -1 && width == -1 && height == -1) {
			return true;
		}
		return mx >= x && mx < x+width && my > y && my <= y+height;
	}
	
	public void keyTyped(int key, char keyChar) {
		for(Inter i : inters) {
			if (i.shown && i.enabled) { i.keyTyped(key, keyChar); }
		}
	}
	
	public abstract void interact(Inter i);
	
	public void render(float opacity) {
		for(Inter i : inters) {
			if (i.shown) {
				i.render(opacity);
			}
		}
	}
	
}