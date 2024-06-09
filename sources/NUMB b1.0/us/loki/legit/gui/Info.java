package us.loki.legit.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import us.loki.legit.Client;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class Info extends GuiScreen {

	private FontRenderer fr;
	private static final ResourceLocation background = new ResourceLocation("loki/g59.jpg");

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(this.width, this.height);
		mc.fontRendererObj.drawString("§6- §1Graphics must be set to  Fast, or screen won't render", this.width / 2 - 115, this.height / 2 - 55,
				0xfffff);
		mc.fontRendererObj.drawString("§6- §1Ears Cosmetic won't render correctly in 3rd person if Wings cosmetic is toggled", this.width / 2 - 205, this.height / 2 - 45,
				0xfffff);
		mc.fontRendererObj.drawString("§a+ §1Optifine", this.width / 2 - 115, this.height / 2 - 25, 0xfffff);
		mc.fontRendererObj.drawString("§a+ §4Current Mods:", this.width / 2 - 115, this.height / 2 - 35, 0xffff);
		mc.fontRendererObj.drawString("§a+ §1Shaders", this.width / 2 - 115, this.height / 2 - 15,
				0xfffff);
		mc.fontRendererObj.drawString("§a+ §1WDL", this.width / 2 - 115, this.height / 2 - 5,
				0xfffff);
		mc.fontRendererObj.drawString("§a+ §1Dynamic Lights", this.width / 2 - 115, this.height / 2 - -5, 0xfffff);
		mc.fontRendererObj.drawString("§a+ §1Armor HUD", this.width / 2 - 115, this.height / 2 - -15, 0xfffff);
		mc.fontRendererObj.drawString("§a+ §11.7 Animations (still testing)", this.width / 2 - 115, this.height / 2 - -25, 0xfffff);
		mc.fontRendererObj.drawString("§a+ §1Status HUD (potion hud)", this.width / 2 - 115, this.height / 2 - -35, 0xfffff);
		mc.fontRendererObj.drawString("§a+ §1Keystrokes", this.width / 2 - 115, this.height / 2 - -45, 0xfffff);
		mc.fontRendererObj.drawString("§a+ §1ToggleSprint/Sneak", this.width / 2 - 115, this.height / 2 - -55, 0xfffff);
		mc.fontRendererObj.drawString("§a+ §1GammaBright", this.width / 2 - 115, this.height / 2 - -65, 0xfffff);
		mc.fontRendererObj.drawString("§a+ §1NoScoreBoard", this.width / 2 - 115, this.height / 2 - -75, 0xfffff);
		mc.fontRendererObj.drawString("§a+ §1Cosmetics", this.width / 2 - 115, this.height / 2 - -85, 0xfffff);


		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(new GuiButtonDark(2, width / 2 - 100, height / 4 - 20, "§dBack"));
		super.initGui();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 2) {
			mc.displayGuiScreen(new GuiMainMenu());
		}

		super.actionPerformed(button);
	}
	public void renderBackground(int par1, int par2) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		this.mc.getTextureManager().bindTexture(background);
		Tessellator var3 = Tessellator.instance;
		var3.getWorldRenderer().startDrawingQuads();
		var3.getWorldRenderer().addVertexWithUV(0.0D, (double) par2, -90.0D, 0.0D, 1.0D);
		var3.getWorldRenderer().addVertexWithUV((double) par1, (double) par2, -90.0D, 1.0D, 1.0D);
		var3.getWorldRenderer().addVertexWithUV((double) par1, 0.0D, -90.0D, 1.0D, 0.0D);
		var3.getWorldRenderer().addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		var3.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
