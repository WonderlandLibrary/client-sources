package me.swezedcode.client.gui.other;

import java.io.IOException;

import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.FileManager;
import me.swezedcode.client.manager.managers.file.files.Colours;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Visual.ClickGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ColorPickerGui extends GuiScreen {

	public static boolean defualt = true;
	public static boolean orange = false;
	public static boolean blue = false;
	public static boolean red = false;
	public static boolean cyan = false;
	public static boolean yellow = false;
	
	public static int color = 0xFF62E354;
	private String status = "";

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/title/background/index.jpg"));
		drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight());
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawRect(1000, 1000, 1000, 1000, -1);
		this.drawCenteredString(fontRendererObj, "Choose an colour!", this.width / 2, 60, 0xFFFFFFFF);
		this.drawCenteredString(fontRendererObj, this.status, this.width / 2, 80, 0xFFFFFFFF);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void initGui() {
		super.initGui();
		int var4 = this.height / 4 + 48;
		this.buttonList.add(new CustomButton(1, this.width / 2 - 100, var4 - 38, "Orange"));
		this.buttonList.add(new CustomButton(2, this.width / 2 - 100, var4 - 16, "Blue"));
		this.buttonList.add(new CustomButton(3, this.width / 2 - 100, var4 + 6, "Red"));
		this.buttonList.add(new CustomButton(4, this.width / 2 - 100, var4 + 28, "Cyan"));
		this.buttonList.add(new CustomButton(5, this.width / 2 - 100, var4 + 75 - 25, "Yellow"));
		this.buttonList.add(new CustomButton(6, this.width / 2 - 100, var4 + 97 - 25, "Default"));
		this.buttonList.add(new CustomButton(7, this.width / 2 - 100, var4 + 119 - 25, "Back To Client Settings"));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		switch (button.id) {
		case 1:
			color = 0xFFFF8847;
			this.status = "Color was changed to §6Orange§f!";
			defualt = false;
			orange = true;
			blue = false;
			red = false;
			cyan = false;
			yellow = false;
			Colours.saveEnabled();
			break;
		case 2:
			color = 0xFF4999F5;
			this.status = "Color was changed to §3Blue§f!";
			defualt = false;
			orange = false;
			blue = true;
			red = false;
			cyan = false;
			yellow = false;
			Colours.saveEnabled();
			break;
		case 3:
			color = 0xFFFF4545;
			this.status = "Color was changed to §cRed§f!";
			defualt = false;
			orange = false;
			blue = false;
			red = true;
			cyan = false;
			yellow = false;
			Colours.saveEnabled();
			break;
		case 4:
			color = 0xFF45FFE9;
			this.status = "Color was changed to §bCyan§f!";
			defualt = false;
			orange = false;
			blue = false;
			red = false;
			cyan = true;
			yellow = false;
			Colours.saveEnabled();
			break;
		case 5:
			color = 0xFFDFFC49;
			this.status = "Color was changed to §eYellow§f!";
			defualt = false;
			orange = false;
			blue = false;
			red = false;
			cyan = false;
			yellow = true;
			Colours.saveEnabled();
			break;
		case 6:
			color = 0xFF62E354;
			this.status = "Color was changed to §aDefault§f!";
			defualt = true;
			orange = false;
			blue = false;
			red = false;
			cyan = false;
			yellow = false;
			Colours.saveEnabled();
			break;
		case 7:
			mc.displayGuiScreen(new GuiClientSettings());
			break;
		}
	}

}
