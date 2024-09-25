package none.clickGui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import none.Client;
import none.clickGui.configpanel.ConfigPanel;
import none.clickGui.configpanel.CustomButton;
import none.clickGui.modulepanel.CategoryPanel;
import none.clickGui.modulepanel.ModulePanel;
import none.clickGui.valuepanel.ValuePanel;
import none.module.Category;
import none.module.Checker;
import none.module.Module;
import none.module.modules.combat.AutoAwakeNgineXE;
import none.noneClickGui.Panel.Target.TargetPanel;
import none.utils.render.TTFFontRenderer;

public class clickgui extends GuiScreen {
	public TTFFontRenderer fontRenderer;
	
	public ArrayList<CategoryPanel> panel;
	private ScaledResolution res;
	private boolean finish = false;
	
	public static ValuePanel valuePanel;
	public static TargetPanel targetPanel;
	public static ConfigPanel configPanel;
	public clickgui() {
		panel = new ArrayList<>();
		valuePanel = new ValuePanel();
		targetPanel = new TargetPanel();
	}
	
	@Override
	public void initGui() {
		buttonList.add(new CustomButton(-1, 3, height - 14, 100, 14, "Config"));
		super.initGui();
	}
	
	public void UpdateConfigDisplay() {
		configPanel = new ConfigPanel(this);
		mc.displayGuiScreen(configPanel);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case -1:
			mc.displayGuiScreen(configPanel = new ConfigPanel(this));
			break;
		default:
			break;
		}
		super.actionPerformed(button);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		fontRenderer = Client.fm.getFont("BebasNeue");
		res = new ScaledResolution(mc);
		int lenth = res.getScaledWidth() / 5;
		int x = 0;
		for (Category c : Category.values()) {
			if (!finish) {
				panel.add(new CategoryPanel(c, x, lenth) {
					@Override
					public void setup() {
						ArrayList<Module> mods = (ArrayList<Module>) Client.instance.moduleManager.getModules().clone();
						Collections.sort(mods, new Comparator<Module>() {
							public int compare(Module m1, Module m2) {
								if ((int)fontRenderer.getStringWidth(m1.getName()) > (int)fontRenderer.getStringWidth(m2.getName())) {
									return -1;
								}
								
								if ((int)fontRenderer.getStringWidth(m1.getName()) < (int)fontRenderer.getStringWidth(m2.getName())) {
									return 1;
								}
								return 0;
							}
						});
						for (Module m : mods) {
							if (!m.getCategory().equals(c) || m.getClass().equals(Checker.class) || m.getClass().equals(AutoAwakeNgineXE.class)) {
								continue;
							}else {
								this.modulespanel.add(new ModulePanel(m, this));
							}
						}
					}
				});
				x = x + lenth;
			}
		}
		
		finish = true;
		
		for (CategoryPanel panel1 : panel) {
			panel1.drawScreen(mouseX, mouseY, partialTicks);
		}
		
		valuePanel.drawScreen(mouseX, mouseY, partialTicks);
		targetPanel.drawScreen(mouseX, mouseY, partialTicks);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		for (CategoryPanel panel1 : panel) {
			panel1.onClick(mouseX, mouseY, mouseButton);
		}
		valuePanel.mousePressed(mouseButton, mouseX, mouseY);
		targetPanel.mousePressed(mouseButton, mouseX, mouseY);
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		for (CategoryPanel panel1 : panel) {
			panel1.onReleased(mouseX, mouseY, state);
		}
		valuePanel.mouseReleased(state, mouseX, mouseY);
		targetPanel.mouseReleased(state, mouseX, mouseY);
	}
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
		for (CategoryPanel panel1 : panel) {
			panel1.onClickMove(mouseX, mouseY);
		}
		valuePanel.mouseMoved(mouseX, mouseY);
		targetPanel.mouseMoved(mouseX, mouseY);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		valuePanel.onKey(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public void onGuiClosed() {
		valuePanel.onClosed();
		super.onGuiClosed();
	}
	
	@Override
	public void updateScreen() {
		valuePanel.onUpdate();
		super.updateScreen();
	}
	
}
