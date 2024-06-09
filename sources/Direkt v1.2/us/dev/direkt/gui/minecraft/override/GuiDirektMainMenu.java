package us.dev.direkt.gui.minecraft.override;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.gui.accounts.GuiAccountList;
import us.dev.direkt.gui.color.ClientColors;
import us.dev.direkt.gui.font.CustomFont;
import us.dev.direkt.gui.particle.ParticleManager;
import us.dev.direkt.resources.Resources;

import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/**
 * @author Foundry
 */
public class GuiDirektMainMenu extends GuiMainMenu {

	private static final CustomFont MAIN_MENU_FONT;
	private ResourceLocation resource;
	private ResourceLocation resource2;
	private static ParticleManager particleManager;

	@Override
	public void initGui() {
		System.out.println("asd");
		particleManager = null;
		try {
			if(resource == null)
				resource = Wrapper.getMinecraft().getTextureManager().getDynamicTextureLocation("MainMenu", new DynamicTexture(ImageIO.read(Resources.getResource("options_background.png"))));
			if(resource2 == null)
				resource2 = Wrapper.getMinecraft().getTextureManager().getDynamicTextureLocation("DirektPurpleThing", new DynamicTexture(ImageIO.read(Resources.getResource("dieeeeeeeerektbackground.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if(particleManager == null) {
			particleManager = new ParticleManager(this);
			particleManager.init(width, height);
		}
		
		//dieeeeeeeerektbackground.png
		super.initGui();
		this.buttonList.set(0, new GuiButton(1, this.width / 2 - 100, this.height / 4 + 48, 200, 20, I18n.format("menu.singleplayer")));
		this.buttonList.set(1, new GuiButton(2, this.width / 2 - 100, this.height / 4 + 48 + 24, 200, 20, I18n.format("menu.multiplayer")));
		this.buttonList.set(2, new GuiButton(14, this.width / 2 - 100, this.height / 4 + 120 - 24, 98, 20, I18n.format("Credits")));
		this.buttonList.set(3, new GuiButton(0, this.width / 2 - 100, this.height / 4 + 48 + 72 + 12, 98, 20, I18n.format("menu.options")));
		this.buttonList.set(4, new GuiButton(4, this.width / 2 + 2, this.height / 4 + 48 + 72 + 12, 98, 20, I18n.format("menu.quit")));
		this.buttonList.set(5, new GuiButton(5, this.width / 2 - 124, this.height / 4 + 48 + 72 + 12, 20, 20, "L"));
		this.buttonList.add(new GuiButton(1337, this.width / 2 + 2, this.height / 4 + 120 - 24, 98, 20, "Account"));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if(button.id == 14) {
			this.mc.displayGuiScreen(new GuiDirektCreditsMenu(this));
		}
		if (button.id == 1337) {
			this.mc.displayGuiScreen(new GuiAccountList(this));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		Wrapper.getMinecraft().getTextureManager().bindTexture(resource2);
		//		GlStateManager.scale(, 2, 4);
		this.drawModalRectWithCustomSizedTexture(0, 0, width, height, width, height, width, height);
		particleManager.render();
		//	drawTexturedModalRect(0, 0, 0, 0, this.width, this.height);
		//	GlStateManager.scale(0.25, 0.5, 0.25);
		GlStateManager.enableAlpha();
		int i = 274;
		int j = this.width / 2 - i / 2;
		int k = 30;
		GL11.glPushMatrix();
		Wrapper.getMinecraft().getTextureManager().bindTexture(resource);
		ScaledResolution sr = new ScaledResolution(Wrapper.getMinecraft());

		GlStateManager.scale(1, 0.393, 1);
		//drawTexturedModalRect(this.width / 2 - 115, this.height / 2 - 165, 0, 0, 255, 248);
		GlStateManager.enableBlend();
		drawTexturedModalRect(sr.getScaledWidth() / 2 - 125, 0, 0, 0, 255, 248);
		GlStateManager.scale(2, 4, 2);
		GL11.glPopMatrix();

		/************
		 * TODO: Direkt: Make new logo without as much transparent
		 */
		//MAIN_MENU_FONT.renderString(Direkt.getInstance().getClientName(), this.width / 2 - MAIN_MENU_FONT.getStringWidth(Direkt.getInstance().getClientName()) / 2 + 1, this.height / 4 + 10 + 1, 0x4000AA00, true);
		//MAIN_MENU_FONT.renderString(Direkt.getInstance().getClientName(), this.width / 2 - MAIN_MENU_FONT.getStringWidth(Direkt.getInstance().getClientName()) / 2, this.height / 4 + 10, ClientColors.FADING_GREEN.getColor(), true);

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) (this.width / 2 + 90), this.height / 4 + 10, 0.0F);
		GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
		float f = 1.8F - MathHelper.abs(MathHelper.sin((float) (Minecraft.getSystemTime() % 1000L) / 1000.0F * ((float) Math.PI * 2F)) * 0.1F);
		f = f * 100.0F / (float) (this.fontRendererObj.getStringWidth(this.splashText) + 32);
		GlStateManager.scale(f, f, f);
		//	this.drawCenteredString(this.fontRendererObj, this.splashText, 0, -8, -256);
		GlStateManager.popMatrix();

		String s = "Minecraft 1.10.2";

		if (this.mc.isDemo()) {
			s = s + " Demo";
		} else {
			s = s + ("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : "/" + this.mc.getVersionType());
		}

		this.drawString(this.fontRendererObj, s, 2, this.height - 10, -1);
		String s1 = "Copyright Mojang AB. Do not distribute!";
		this.drawString(this.fontRendererObj, s1, this.width - this.fontRendererObj.getStringWidth(s1) - 2, this.height - 10, -1);

		if (this.openGLWarning1 != null && !this.openGLWarning1.isEmpty()) {
			drawRect(this.openGLWarningX1 - 2, this.openGLWarningY1 - 2, this.openGLWarningX2 + 2, this.openGLWarningY2 - 1, 1428160512);
			this.drawString(this.fontRendererObj, this.openGLWarning1, this.openGLWarningX1, this.openGLWarningY1, -1);
			this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.openGLWarning2Width) / 2, ((GuiButton) this.buttonList.get(0)).yPosition - 12, -1);
		}

		for (GuiButton aButtonList : buttonList) {
			aButtonList.drawButton(this.mc, mouseX, mouseY);
		}
		for (GuiLabel aLabelList : this.labelList) {
			aLabelList.drawLabel(this.mc, mouseX, mouseY);
		}

		if (this.areRealmsNotificationsEnabled()) {
			this.realmsNotification.drawScreen(mouseX, mouseY, partialTicks);
		}
	}

	static {
		try {
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, Resources.getResource("fonts/MainMenuFont.ttf")).deriveFont(Font.PLAIN, 1));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		MAIN_MENU_FONT = new CustomFont("ScreamReal", 130);
	}

}
