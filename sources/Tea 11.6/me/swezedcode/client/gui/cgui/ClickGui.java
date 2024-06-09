package me.swezedcode.client.gui.cgui;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.cgui.component.Frames;
import me.swezedcode.client.gui.cgui.parts.keybinder.KeybindPart;
import me.swezedcode.client.gui.cgui.parts.vals.BooleanValuePart;
import me.swezedcode.client.gui.cgui.parts.vals.ModuleTogglePart;
import me.swezedcode.client.gui.cgui.parts.vals.RestrictedValuePart;
import me.swezedcode.client.gui.clickGui.Frame;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.ModuleManager;
import me.swezedcode.client.manager.managers.ValueManager;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.values.BooleanValue;
import me.swezedcode.client.utils.values.NumberValue;
import me.swezedcode.client.utils.values.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ClickGui extends GuiScreen {

	public static ArrayList<Frames> frames = new ArrayList<Frames>();

	public ClickGui() {
		int frameX = 10;
		int frameY = 20;
		ModCategory[] values;
		for (int length = (values = ModCategory.values()).length, i = 0; i < length; ++i) {
			ModCategory cat = values[i];

			if (cat == ModCategory.Gui)
				continue;

			if (cat == ModCategory.NONE)
				continue;

			Frames frame = new Frames(cat.name());
			frame.setX(frameX);
			frame.setY(frameY);
			frames.add(frame);
			frameY = 20;
			frameX += 200;
			for (Module mod : getModsInCategory(cat)) {

				ModuleTogglePart modsincat = new ModuleTogglePart(mod.getName(), frame) {
					@Override
					public void onUpdate(int mouseX, int mouseY, float partialTicks) {
						this.enabled = mod.isToggled();
					}

					@Override
					public void onToggle() {
						mod.toggle();
						this.enabled = !this.enabled;
					}
				};
				KeybindPart binding = new KeybindPart(mod, modsincat);
				modsincat.addComponent(binding);
				for (Value value : ValueManager.INSTANCE.getValues(mod)) {
					if (value instanceof BooleanValue) {
						BooleanValue bV;
						bV = (BooleanValue) value;
						BooleanValuePart component = new BooleanValuePart(bV.getName(), bV) {
							@Override
							public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
								Point mouse = new Point(mouseX, mouseY);
								if (this.MouseIsInside().contains(mouse)) {
									bV.setValue(!bV.getValue());
								}
								super.mouseClicked(mouseX, mouseY, mouseButton);
							}
						};
						modsincat.addComponent(component);
					} else if (value instanceof NumberValue) {
						NumberValue rV;
						rV = (NumberValue) value;
						RestrictedValuePart component2 = new RestrictedValuePart(rV.getName(), rV) {
							@Override
							public void onUpdate(int mouseX, int mouseY, float partialTicks) {
								super.onUpdate(mouseX, mouseY, partialTicks);
							}
						};
						modsincat.addComponent(component2);
					}
				}
				frame.addComponent(modsincat);
			}
		}
		File f = new File(
				Minecraft.getMinecraft().mcDataDir + "\\" + Tea.getInstance().getName() + "\\" + "gui" + ".txt");
		if (f.exists() && !f.isDirectory()) {
			Manager.getManager().getFileManager().loadGui();
		}
	}
	
	private void layoutFrames(ScaledResolution sr) {
		int x = 10;
		int y = 10;
		int h = 15;
		int w = 130;
		for (Frames frame : frames) {
			if (frame.getX() == 0 || frame.getY() == 0) {
				if (x + w > sr.getScaledWidth()) {
					x = 10;
					y += h + 20;
				}
				frame.setX(x);
				frame.setY(y);
				x += w + 10;
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawDefaultBackground();
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth,
				Minecraft.getMinecraft().displayHeight);
		GL11.glPushMatrix();
		float scale = sr.getScaleFactor() / (float) Math.pow(sr.getScaleFactor(), 2.0);
		GL11.glScalef(scale, scale, scale);
		mouseX = Mouse.getX();
		mouseY = Display.getHeight() - Mouse.getY();
		for (Frames frame : frames) {
			frame.onUpdate(mouseX, mouseY, partialTicks);
			frame.draw(mouseX, mouseY, partialTicks);
		}
		GL11.glPopMatrix();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		mouseX = Mouse.getX();
		mouseY = Display.getHeight() - Mouse.getY();
		Frames newTop = null;
		for (int i = frames.size() - 1; i >= 0; --i) {
			Frames frame = frames.get(i);
			frame.mouseClicked(mouseX, mouseY, mouseButton);
			if (frame.didCollide(mouseX, mouseY)) {
				newTop = frame;
				break;
			}
		}
		if (newTop != null) {
			ArrayList<Frames> newList = new ArrayList<Frames>();
			for (Frames frame : frames) {
				if (frame != newTop) {
					newList.add(frame);
				}
			}
			newList.add(newTop);
			frames = newList;
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		mouseX = Mouse.getX();
		mouseY = Display.getHeight() - Mouse.getY();
		for (int i = frames.size() - 1; i >= 0; --i) {
			Frames frame = frames.get(i);
			frame.mouseReleased(mouseX, mouseY, state);
			if (frame.didCollide(mouseX, mouseY)) {
				break;
			}
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		try {
			super.keyTyped(typedChar, keyCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Frames frame : frames) {
			frame.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Manager.getManager().getFileManager().saveGui();
	}
	
	public ArrayList<Module> getModsInCategory(ModCategory cat) {
		ArrayList<Module> mods = new ArrayList<Module>();
		for (Module mod : ModuleManager.getModules()) {
			if (mod.getModcategory2(cat)) {
				mods.add(mod);
			}
		}
		return mods;
	}

}
