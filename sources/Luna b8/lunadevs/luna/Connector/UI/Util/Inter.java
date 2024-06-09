package lunadevs.luna.Connector.UI.Util;

import org.newdawn.slick.TrueTypeFont;

import lunadevs.luna.Connector.UI.InteractableParent;


public class Inter {
	public enum TYPE {
		button,
		slider,
		checkbox,
		textbox,
		link,
		chatcomponent;
	}

	public InteractableParent parent;
	public TYPE type;
	public boolean shown, enabled, hover, active;
	public float x, y, width, height;
	public TrueTypeFont font;
	public int fontColor;
	public Align fontAlignH, fontAlignV;
	
	public Inter(InteractableParent p, TYPE t, float xx, float yy, float w, float h, TrueTypeFont f, int fc, Align fh, Align fv) {
		parent = p;
		type = t;
		x = xx;
		y = yy;
		width = w;
		height = h;
		font = f;
		fontColor = fc;
		fontAlignH = fh;
		fontAlignV = fv;
		shown = true;
		enabled = true;
	}
	
	public void update(int mx, int my) {
		hover = mouseOver(mx, my) && enabled;
	}

	public void mouseClick(int mx, int my, int b) {
		if (mouseOver(mx, my)) {
			parent.interact(this);
			active = true;
		} else {
			active = false;
		}
	}
	
	public void mouseRelease(int mx, int my, int b) {}
	public void mouseScroll(int s) {}
	public boolean mouseOver(int mx, int my) {
		return parent.hover && shown && mx >= x && mx < x+width && my > y && my <= y+height;
	}
	
	public void keyboardPress(int key) {}
	public void keyboardRelease(int key) {}
	public void keyTyped(int key, char keyChar) {}
	
	public void render(float opacity) {}
}