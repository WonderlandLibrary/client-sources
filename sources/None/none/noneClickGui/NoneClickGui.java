package none.noneClickGui;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;
import none.Client;
import none.event.events.EventChat;
import none.fontRenderer.xdolf.Fonts;
import none.module.Category;
import none.module.Checker;
import none.module.Module;
import none.module.modules.combat.AutoAwakeNgineXE;
import none.module.modules.render.ClientColor;
import none.noneClickGui.Panel.ModulePanel;
import none.noneClickGui.Panel.Panel;
import none.noneClickGui.Panel.Target.TargetPanel;
import none.noneClickGui.Panel.ValuePanel.ValuePanel;
import none.utils.Utils;
import none.utils.render.Colors;
import none.utils.render.TTFFontRenderer;

public class NoneClickGui extends GuiScreen{
	public ArrayList<Panel> p;
	public ArrayList<Panel> rp;
	public static ValuePanel valuemenu;
	public static TargetPanel targetmenu;
	
	public FontRenderer fr;
	
	public Category currentCategory;
	
	public static int opening = 0;
	
	public NoneClickGui() {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		fr = Fonts.roboto22;
		p = new ArrayList<>();
		valuemenu = new ValuePanel();
		targetmenu = new TargetPanel();
		int mstartX = fr.getStringWidth("Module") / 2;
		int vstartX = fr.getStringWidth("Value") / 2;
		int mrealX = 45 + mstartX;
		int mrealY = 60 + fr.FONT_HEIGHT + 2;
    	NoneClickGui.valuemenu.setExtended(true);
		TTFFontRenderer jigsawFont = Client.fm.getFont("JIGR 19");
		for (final Category c : Category.values()) {
			String title = Character.toUpperCase(c.name().toLowerCase().charAt(0)) + c.name().toLowerCase().substring(1);
			p.add(new Panel(title, mrealX, mrealY, jigsawFont, c) {
				@Override
				public void SetUp() {
					ArrayList<Module> mods = (ArrayList<Module>) Client.instance.moduleManager.getModules().clone();
					Collections.sort(mods, new Comparator<Module>() {
						public int compare(Module m1, Module m2) {
							if ((int)jigsawFont.getStringWidth(m1.getName()) > (int)jigsawFont.getStringWidth(m2.getName())) {
								return -1;
							}
							
							if ((int)jigsawFont.getStringWidth(m1.getName()) < (int)jigsawFont.getStringWidth(m2.getName())) {
								return 1;
							}
							return 0;
						}
					});
					for (Module m : mods) {
						if (!m.getCategory().equals(c) || m.getClass().equals(Checker.class) || m.getClass().equals(AutoAwakeNgineXE.class)) {
							continue;
						}else {
							this.Elements.add(new ModulePanel(m, this, jigsawFont, false));
						}
					}
				}
			});
		}
		
		rp = new ArrayList<Panel>();
		for (Panel p : p) {
			rp.add(p);
		}
		Collections.reverse(rp);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (opening > 0) {
			opening = opening - 4;
		}else if (opening < 0 ) {
			opening = 0;
		}
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		Point point = Utils.calculateMouseLocation();
		valuemenu.isHovered(point.x, point.y);
		targetmenu.isHovered(point.x, point.y);
		Gui.drawRect(0, 0, res.getScaledWidth(), res.getScaledHeight(), Colors.getColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 140));
		//Left
		Gui.drawRectRGB((30 + opening) - 6 - 2, (30 + opening) - 6 - 2, (30 + opening) - 4, res.getScaledHeight() - (45 + opening) + 2 + 4, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
		//Up
		Gui.drawRectRGB((30 + opening) - 6, (30 + opening) - 4 - 4, res.getScaledWidth() - (30 + opening) + 5, (30 + opening) - 4, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
		//Right
		Gui.drawRectRGB(res.getScaledWidth() - (30 + opening) + 3, (30 + opening) - 6 - 2, res.getScaledWidth() - (30 + opening) + 3 + 4, res.getScaledHeight() - (45 + opening) + 2 + 4, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
		//Down
		Gui.drawRectRGB((30 + opening) - 6, res.getScaledHeight() - (45 + opening) + 2 + 1, res.getScaledWidth() - (30 + opening) + 5, res.getScaledHeight() - (45 + opening) + 2 + 4, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
		//Nothing
		Gui.drawRect((30 + opening) - 4, (30 + opening) - 4, res.getScaledWidth() - (30 + opening) + 3, res.getScaledHeight() - (45 + opening) + 2 + 1, Color.BLACK.getRGB());
		Gui.drawRectRGB(res.getScaledWidth() / 2 - 2, 30 - 4 + opening, res.getScaledWidth() / 2 + 2, res.getScaledHeight() - 45 + 2 - opening, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
		Combat(30 + opening, 30 + opening);Movement(res.getScaledWidth() / 2, 30 + opening);Player(res.getScaledWidth() - 60 - 1 - opening, 30 + opening);Render(30 + opening, res.getScaledHeight() - 60 - opening + 1);World(res.getScaledWidth() - 60 - opening, res.getScaledHeight() - 60 - opening + 1);
		int mstartX = fr.drawString("Module", 45 + opening, 60, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100), false);
		int vstartX = fr.drawString("Value", res.getScaledWidth() / 2 + 45 - opening, 60, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100), false);
		if (currentCategory != null) {
			for (Panel p : p) {
				if (p.getCategory() == currentCategory) {
					p.drawScreen(mouseX, mouseY, partialTicks);
					p.mouseMoved(point.x, point.y);
					p.isHovered(point.x, point.y);
				}
			}
		}
//		
		if (valuemenu.isExtended()) {
			valuemenu.drawScreen(mouseX, mouseY, partialTicks);
		}
		
		targetmenu.drawScreen(mouseX, mouseY, partialTicks);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		valuemenu.mousePressed(mouseButton, mouseX, mouseY);
		targetmenu.mousePressed(mouseButton, mouseX, mouseY);
		if (currentCategory != null) {
			for (Panel p : p) {
				if (p.getCategory() == currentCategory) {
					p.mouseMoved(mouseX, mouseY);
					p.mousePressed(mouseButton, mouseX, mouseY);
					if (p!= null && p.Elements != null
							&& p.Elements.size() > 0) {
						for (ModulePanel e : p.Elements) {
							e.mousePressed(mouseButton, mouseX, mouseY);
						}
					}
				}
			}
		}
//		for (Panel window : p) {
		
//		}
		if (mouseButton != 2) {
			onCombat(mouseX, mouseY);
			onMovement(mouseX, mouseY);
			onPlayer(mouseX, mouseY);
			onRender(mouseX, mouseY);
			onWorld(mouseX, mouseY);
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		valuemenu.mouseReleased(state, mouseX, mouseY);
		targetmenu.mouseReleased(state, mouseX, mouseY);
//		for (Panel window : p) {
//			window.mouseMoved(mouseX, mouseY);
//	        window.mouseReleased(state, mouseX, mouseY);
//	        if (window != null && window.Elements != null
//					&& window.Elements.size() > 0) {
//				for (ModulePanel e : window.Elements) {
//					e.mouseReleased(state, mouseX, mouseY);
//				}
//			}
//		}
		
		if (currentCategory != null) {
			for (Panel p : p) {
				if (p.getCategory() == currentCategory) {
					p.mouseMoved(mouseX, mouseY);
			        p.mouseReleased(state, mouseX, mouseY);
			        if (p != null && p.Elements != null
							&& p.Elements.size() > 0) {
						for (ModulePanel e : p.Elements) {
							e.mouseReleased(state, mouseX, mouseY);
						}
					}
				}
			}
		}
		super.mouseReleased(mouseX, mouseY, state);
	}
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
//		for (Panel window : p) {
//			window.mouseMoved(mouseX, mouseY);
//		}
		if (currentCategory != null) {
			for (Panel p : p) {
				if (p.getCategory() == currentCategory) {
					p.mouseMoved(mouseX, mouseY);
				}
			}
		}
		valuemenu.mouseMoved(mouseX, mouseY);
		targetmenu.mouseMoved(mouseX, mouseY);
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}
	
	@Override
	public void onGuiClosed() {
		targetmenu.onClose();
		valuemenu.Clear();
		valuemenu.update();
		super.onGuiClosed();
	}
	
	public void Combat(int x,int y) {
		int renderColor = !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100);
		Gui.drawRect(x - 4, y - 4, x + fr.getStringWidth("Combat") + 4, y + fr.FONT_HEIGHT + 4, Colors.getColor(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue(), 200));
		Gui.drawRect(x - 2, y - 2, x + fr.getStringWidth("Combat") + 2, y + fr.FONT_HEIGHT + 2, Color.BLACK.getRGB());
		fr.drawString("Combat", x, y, Colors.getColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 150), false);
		fr.drawString("Combat", x, y + 1, renderColor, false);
	}
	
	public void Movement(int x,int y) {
		int renderColor = !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100);
		Gui.drawRect(x - (fr.getStringWidth("Movement") / 2) - 4, y - 4, x + (fr.getStringWidth("Movement") / 2) + 4, y + fr.FONT_HEIGHT + 4, Colors.getColor(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue(), 200));
		Gui.drawRect(x - (fr.getStringWidth("Movement") / 2) - 2, y - 2, x + (fr.getStringWidth("Movement") / 2) + 2, y + fr.FONT_HEIGHT + 2, Color.BLACK.getRGB());
		fr.drawString("Movement", x - (fr.getStringWidth("Movement") / 2), y, Colors.getColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 150), false);
		fr.drawString("Movement", x - (fr.getStringWidth("Movement") / 2), y + 1, renderColor, false);
	}
	
	public void Player(int x,int y) {
		int renderColor = !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100);
		Gui.drawRect(x - 4, y - 4, x + fr.getStringWidth("Player") + 4, y + fr.FONT_HEIGHT + 4, Colors.getColor(Color.ORANGE.getRed(), Color.ORANGE.getGreen(), Color.ORANGE.getBlue(), 200));
		Gui.drawRect(x - 2, y - 2, x + fr.getStringWidth("Player") + 2, y + fr.FONT_HEIGHT + 2, Color.BLACK.getRGB());
		fr.drawString("Player", x, y, Colors.getColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 150), false);
		fr.drawString("Player", x, y + 1, renderColor, false);
	}
	
	public void Render(int x,int y) {
		int renderColor = !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100);
		Gui.drawRect(x - 4, y - 4, x + fr.getStringWidth("Render") + 4, y + fr.FONT_HEIGHT + 4, Colors.getColor(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.YELLOW.getBlue(), 200));
		Gui.drawRect(x - 2, y - 2, x + fr.getStringWidth("Render") + 2, y + fr.FONT_HEIGHT + 2, Color.BLACK.getRGB());
		fr.drawString("Render", x, y, Colors.getColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 150), false);
		fr.drawString("Render", x, y + 1, renderColor, false);
	}
	
	public void World(int x,int y) {
		int renderColor = !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100);
		Gui.drawRect(x - 4, y - 4, x + fr.getStringWidth("World") + 4, y + fr.FONT_HEIGHT + 4, Colors.getColor(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(), 200));
		Gui.drawRect(x - 2, y - 2, x + fr.getStringWidth("World") + 2, y + fr.FONT_HEIGHT + 2, Color.BLACK.getRGB());
		fr.drawString("World", x, y, Colors.getColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 150), false);
		fr.drawString("World", x, y + 1, renderColor, false);
	}
	
	public void onCombat(int mouseX,int mouseY) {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		if (mouseX >= 30 - 2 && mouseX <= 30 + fr.getStringWidth("Combat") + 2 && mouseY >= 30 - 2 && mouseY <= 30 + fr.FONT_HEIGHT + 2) {
			setCategory(Category.COMBAT);
		}
	}
	
	public void onMovement(int mouseX,int mouseY) {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		if (mouseX >= res.getScaledWidth() / 2 - (fr.getStringWidth("Movement") / 2) - 2 && mouseX <= res.getScaledWidth() / 2 + ((fr.getStringWidth("Movement") / 2)) + 2 && mouseY >= 30 - 2 && mouseY <= 30 + fr.FONT_HEIGHT + 2) {
			setCategory(Category.MOVEMENT);
		}
	}
	
	public void onPlayer(int mouseX,int mouseY) {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		if (mouseX >= res.getScaledWidth() - 60 - 1 && mouseX <= res.getScaledWidth() - 60 - 1 + fr.getStringWidth("Player") + 2 && mouseY >= 30 && mouseY <= 30 + fr.FONT_HEIGHT + 2) {
			setCategory(Category.PLAYER);
		}
	}	
	
	public void onRender(int mouseX,int mouseY) {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		if (mouseX >= 30 && mouseX <= 30 + fr.getStringWidth("Render") + 2 && mouseY >= res.getScaledHeight() - 60 + 1 && mouseY <= res.getScaledHeight() - 60 + 1 + fr.FONT_HEIGHT + 2) {
			setCategory(Category.RENDER);
		}
	}	
	
	public void onWorld(int mouseX,int mouseY) {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		if (mouseX >= res.getScaledWidth() - 60 && mouseX <= res.getScaledWidth() - 60 + fr.getStringWidth("World") + 2 && mouseY >= res.getScaledHeight() - 60 + 1&& mouseY <= res.getScaledHeight() - 60 + 1 + fr.FONT_HEIGHT + 2) {
			setCategory(Category.WORLD);
		}
	}
	
	public void setCategory(Category category) {
		currentCategory = category;
	}
}
