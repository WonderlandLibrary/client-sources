package de.tired.api.performanceMode;

import de.tired.api.util.render.CheckBoxButton;
import de.tired.interfaces.IHook;
import de.tired.shaderloader.ShaderManager;
import de.tired.shaderloader.list.BackGroundShader;
import de.tired.shaderloader.ShaderRenderer;
import de.tired.api.particle.ParticleRenderer;

import de.tired.api.util.render.Translate;
import de.tired.api.util.font.FontManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class PerformanceGui extends GuiScreen implements IHook {

	public static UsingType usingType;

	private ParticleRenderer particleRenderer;
	private Translate translate;
	public CheckBoxButton checkBoxButton;
	public static boolean isState;

	public PerformanceGui() {
		this.translate = new Translate(0, 0);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		final ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());


		GL11.glPushMatrix();
		translate.interpolate(resolution.getScaledWidth(), resolution.getScaledHeight(), 9);

		GL11.glTranslatef(resolution.getScaledWidth() / 2, resolution.getScaledHeight() / 2, 0);
		GL11.glScaled(translate.getX() / resolution.getScaledWidth(), translate.getY() / resolution.getScaledHeight(), 0);
		GL11.glTranslatef(-resolution.getScaledWidth() / 2, -resolution.getScaledHeight() / 2, 0);
		GlStateManager.disableAlpha();

		GlStateManager.disableAlpha();
		GL11.glPopMatrix();
		GlStateManager.pushMatrix();
		ShaderManager.shaderBy(BackGroundShader.class).doRender();

		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		ShaderRenderer.startBlur();
		FontManager.notosansFBig.drawCenteredString("Select your performance mode!", this.width / 2, 120, -1);
		FontManager.robotoF.drawCenteredString("if you use high performance mode, effects of shaders and other things like fontrenderer are withheld\"", this.width / 2, 150, -1);


		particleRenderer.draw(mouseX, mouseY);
		ShaderRenderer.stopBlur();


		FontManager.notosansFBig.drawCenteredString("Select your performance mode!", this.width / 2, 120, new Color(244, 244, 244, 222).getRGB());
		FontManager.robotoF.drawCenteredString("if you use high performance mode, effects of shaders and other things like fontrenderer are withheld", this.width / 2, 150, new Color(244, 244, 244, 166).getRGB());

		FontManager.robotoF.drawCenteredString("Please restart your game after changing performance mode to avoid bugs.", this.width / 2, 190, new Color(244, 244, 244, 166).getRGB());

		GlStateManager.popMatrix();
		checkBoxButton.drawButton();



		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		checkBoxButton.mouseClicked(mouseX, mouseY, mouseButton);

		isState = checkBoxButton.isState;


		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void initGui() {
		particleRenderer = new ParticleRenderer();
		this.translate = new Translate(0, 0);
		int defaultHeight = this.height / 4 + 100;
		this.buttonList.add(new GuiButton(1, (this.width / 2 - 100), defaultHeight, 98, 20, "HighPerformance"));
		this.buttonList.add(new GuiButton(2, this.width / 2 + 2, defaultHeight, 98, 20, "NormalPerformance"));

		checkBoxButton = new CheckBoxButton("Save setting", 12, 32, 12);

		super.initGui();
	}


	@Override
	protected void actionPerformed(CheckBoxButton button) throws IOException {
		if (button.id == 12) {
			if (checkBoxButton.isState) {
				System.out.println("Among");
			}
		}
		super.actionPerformed(button);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 1) {
			usingType = UsingType.HIGH_PERFORMANCE;
		} else {
			usingType = UsingType.NORMAL_PERFORMANCE;
		}

		MC.displayGuiScreen(new GuiMainMenu());
		super.actionPerformed(button);
	}
}
