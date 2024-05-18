package com.kilo.mod.toolbar;

import org.newdawn.slick.opengl.Texture;

import com.kilo.mod.Category;
import com.kilo.mod.toolbar.dropdown.Window;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.UIHacks;
import com.kilo.ui.UIHandler;
import com.kilo.util.Align;
import com.kilo.util.Util;

public class Button extends Component {

	protected float hoverTransition;
	
	public Button(int id, Texture texture, float x, float y, float width, float height) {
		super(id, texture, x, y, width, height);
	}

	@Override
	public void update(int mouseX, int mouseY) {
		if (!enabled) { return; }
		
		this.hover = mouseOver(mouseX, mouseY);

		if (hover) {
			this.hoverTransition+= (1-hoverTransition)/UIHacks.transitionSpeed;
		} else {
			this.hoverTransition+= (0-hoverTransition)/UIHacks.transitionSpeed;
		}
		
		if (active) {
			Window window = ((UIHacks)UIHandler.currentUI).window;
			window.active = true;
			window.translate(x+(width/2), y+height+16);
		}
	}

	@Override
	public void mouseClick(int mouseX, int mouseY, int button) {
		if (!enabled || button != 0) { return; }
		
		Window wm = ((UIHacks)UIHandler.currentUI).window;
		Window wo = ((UIHacks)UIHandler.currentUI).options;
		Window wso = ((UIHacks)UIHandler.currentUI).subOptions;
		
		if ((!wm.mouseOver(mouseX, mouseY) && wm.active && wm.exist.getTime() >= wm.existFor) && !wo.active && !wso.active) {
			this.active = false;
		}
		
		if (wo.active || wso.active) {
			return;
		}

		if (mouseOver(mouseX, mouseY)) {
			for(Component component : ((UIHacks)UIHandler.currentUI).toolbarComponents) {
				if (component != this) {
					component.active = false;
				}
			}
			
			if (!active) {
				Window window = ((UIHacks)UIHandler.currentUI).window;
				window.updateContentParent(id);
			}

			this.active = !this.active;
		}
	}

	@Override
	public void mouseRelease(int mouseX, int mouseY, int button) {
		if (!enabled) { return; }
	}
	
	@Override
	public void render(float transparency) {
		if (!enabled) { return; }
		
		float w = 16;
		float h = 16;

		Draw.rect(x, y, x+width, y+height, Util.reAlpha(Colors.BLACK.c, (active?1/1.5f:(hoverTransition/1.5f))*transparency));
		
		Draw.rectTexture(x+32-(w/2), y+32-(h/2), w, h, texture, Util.reAlpha(Colors.WHITE.c, (active?1f:(0.5f+(hoverTransition/2)))*transparency));

		String t = Category.values()[id].name();
		String b = t.substring(0, 1).toUpperCase();
		String e = t.substring(1, t.length()).toLowerCase();
		Draw.string(Fonts.ttfRoundedBold10, x+(width/2), y+(height/2)+8, b+e, Util.reAlpha(Colors.WHITE.c, hover || active?1f:0.5f), Align.C, Align.T);
	}
}
