package me.swezedcode.client.gui.clickGui;

import java.awt.TextField;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.swezedcode.client.Tea;
import me.swezedcode.client.command.commands.CommandBlur;
import me.swezedcode.client.gui.cgui.component.Component;
import me.swezedcode.client.gui.cgui.parts.vals.RestrictedValuePart;
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

	public static ArrayList<Frame> Frames = new ArrayList<Frame>();

	public ClickGui() {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution sr = new ScaledResolution(mc, ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight());
		List<ModCategory> cats = new ArrayList<ModCategory>();
		for (ModCategory cat : ModCategory.values()) {

			if (cat == ModCategory.Gui)
				continue;
			if (cat == ModCategory.NONE)
				continue;

			Frame modFrame = new Frame(cat.name(), getModsInCategory(cat).size());
			ArrayList<Module> mods = getModsInCategory(cat);
			for (final Module mod : mods) {
				Button button = new Button(mod.getName(), null, modFrame) {
					@Override
					public void onPressed() {
						mod.toggle();
						this.enabled = !enabled;
					}

					@Override
					public void onUpdate() {
						this.enabled = mod.isToggled();
					}
				};
				for (Value val : ValueManager.INSTANCE.getValues(mod)) {
					/*
					 * Create Button Of Boolean Value
					 */
					if (val instanceof BooleanValue) {
						final BooleanValue bV;
						bV = (BooleanValue) val;
						SpecialButton valButton = new SpecialButton(bV.getName(), null, button) {
							@Override
							public void onPressed() {
								/*
								 * Toggle value of true or false
								 */
								bV.setValue(!bV.getValue());
							}

							@Override
							public void onUpdate() {
								/*
								 * Toggle value of off and on/true & false
								 */
								this.enabled = bV.getValue();
							}
						};
						button.elements.add(valButton);
					}
				}
				//for (Value val : ValueManager.INSTANCE.getValues(mod)) {
				//	if ((val instanceof NumberValue)) {
						//NumberValue nV = (NumberValue) val;
						//TextBox textBox = new TextBox(nV, button);
						//button.elements.add(textBox);
				//	}
				//}
				button.elements.add(new KeyBinder(mod, button));
				modFrame.addItem(button);
			}
			Frames.add(modFrame);
		}
		layoutFrames(sr);
		File f = new File(
				Minecraft.getMinecraft().mcDataDir + "\\" + Tea.getInstance().getName() + "\\" + "gui" + ".txt");
		if (f.exists() && !f.isDirectory()) {
			Manager.getManager().getFileManager().loadGui();
		}
	}

	public static ArrayList<Frame> getFrames() {
		return Frames;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		for (Frame rFrame : Frames) {
			rFrame.draw(mouseX, mouseY, partialTicks, 0, 0, this);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		for (Frame rFrame : Frames) {
			rFrame.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		for (Frame rFrame : Frames) {
			rFrame.mouseReleased(mouseX, mouseY, state);
		}

	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		for (Frame rFrame : Frames) {
			rFrame.keyTyped(typedChar, keyCode);
		}

	};

	protected ArrayList<Component> components;

	public void addComponent(final Component component) {
		this.components.add(component);
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

	private void layoutFrames(ScaledResolution sr) {
		int x = 10;
		int y = 10;
		int h = 15;
		int w = 130;
		for (Frame frame : Frames) {
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
	public void initGui() {
		if (CommandBlur.isOn()) {
			mc.entityRenderer.enableShader(18);
		}
	}

	@Override
	public void onGuiClosed() {
		if (CommandBlur.isOn()) {
			mc.entityRenderer.disableShader();
		}
		Manager.getManager().getFileManager().saveGui();
	}

}
