package net.minecraft.client.gui.inventory;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import zamorozka.gui.GuiIngameHook;
import zamorozka.main.Zamorozka;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.VISUALLY.HUD;
import zamorozka.modules.VISUALLY.NameTags;
import zamorozka.ui.ColorUtilities;
import zamorozka.ui.Colors;
import zamorozka.ui.MathUtil;
import zamorozka.ui.RenderingUtils;

public class GuiInventory extends InventoryEffectRenderer implements IRecipeShownListener {

	/** The old x position of the mouse pointer */
	public static float oldMouseX;

	/** The old y position of the mouse pointer */
	public static float oldMouseY;
	private GuiButtonImage field_192048_z;
	private final GuiRecipeBook field_192045_A = new GuiRecipeBook();
	private boolean field_192046_B;
	private boolean field_194031_B;

	private int curAlpha;

	private double animationState;
	private double animationState1;
	private static double ANIMATION_SPEED = Zamorozka.settingsManager.getSettingByName("SmoothInventory Speed").getValDouble();

	public GuiInventory(EntityPlayer player) {
		super(player.inventoryContainer);
		this.allowUserInput = true;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		if (this.mc.playerController.isInCreativeMode()) {
			this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.player));
		}

		this.field_192045_A.func_193957_d();
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when
	 * the GUI is displayed and when the window resizes, the buttonList is cleared
	 * beforehand.
	 */
	public void initGui() {

		this.buttonList.clear();
		if (this.mc.playerController.isInCreativeMode()) {
			this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.player));
		} else {
			if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
				if (mc.entityRenderer.theShaderGroup != null) {
					mc.entityRenderer.theShaderGroup.deleteShaderGroup();
				}
				mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
			}
			super.initGui();
		}
		this.field_192046_B = this.width < 379;
		this.field_192045_A.func_194303_a(this.width, this.height, this.mc, this.field_192046_B, ((ContainerPlayer) this.inventorySlots).craftMatrix);
		this.guiLeft = this.field_192045_A.func_193011_a(this.field_192046_B, this.width, this.xSize);
		this.field_192048_z = new GuiButtonImage(10, this.guiLeft + 104, this.height / 2 - 22, 20, 18, 178, 0, 19, INVENTORY_BACKGROUND);
		this.buttonList.add(this.field_192048_z);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the
	 * items)
	 */
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRendererObj.drawString(I18n.format("container.crafting"), 97, 8, 4210752);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if ((ModuleManager.getModule(HUD.class).getState() && Zamorozka.settingsManager.getSettingByName("GradientBackground").getValBoolean())) {
			String mode2 = Zamorozka.settingsManager.getSettingByName("GradientColor Mode").getValString();
			String mode = Zamorozka.settingsManager.getSettingByName("Gradient Mode").getValString();
			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			float alpha = (float) Zamorozka.settingsManager.getSettingByName("GradientAlpha").getValDouble();
			int step = (int) (alpha / 100);
			if (this.curAlpha < alpha - step) {
				this.curAlpha += step;
			} else if (this.curAlpha > alpha - step && this.curAlpha != alpha) {
				this.curAlpha = (int) alpha;

			} else if (this.curAlpha != alpha) {
				this.curAlpha = (int) alpha;
			}

			if (mode2.equalsIgnoreCase("Client")) {
				Color c = new Color(Zamorozka.getClientColors().getRed(), Zamorozka.getClientColors().getGreen(), Zamorozka.getClientColors().getBlue(), (int) curAlpha);
				Color none = new Color(0, 0, 0, 0);
				if (Zamorozka.settingsManager.getSettingByName("GradientBackground").getValBoolean() && ModuleManager.getModule(HUD.class).getState()) {
					if (mode.equalsIgnoreCase("Top")) {
						this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), c.getRGB(), none.getRGB());
					} else if (mode.equalsIgnoreCase("Bottom")) {
						this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), none.getRGB(), c.getRGB());
					} else if (mode.equalsIgnoreCase("Everywhere")) {
						this.drawGradientRect(0, 0, (int) sr.getScaledWidth(), (int) sr.getScaledHeight(), c.getRGB(), c.getRGB());
					}
				}
			}
			if (mode2.equalsIgnoreCase("Custom")) {
				int red = (int) Zamorozka.settingsManager.getSettingByName("GradientRed").getValDouble();
				int green = (int) Zamorozka.settingsManager.getSettingByName("GradientGreen").getValDouble();
				int blue = (int) Zamorozka.settingsManager.getSettingByName("GradientBlue").getValDouble();
				Color c = new Color(red, green, blue, (int) curAlpha);
				Color none = new Color(0, 0, 0, 0);
				if (Zamorozka.settingsManager.getSettingByName("GradientBackground").getValBoolean() && ModuleManager.getModule(HUD.class).getState()) {
					if (mode.equalsIgnoreCase("Top")) {
						this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), c.getRGB(), none.getRGB());
					} else if (mode.equalsIgnoreCase("Bottom")) {
						this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), none.getRGB(), c.getRGB());
					} else if (mode.equalsIgnoreCase("Everywhere")) {
						this.drawGradientRect(0, 0, (int) sr.getScaledWidth(), (int) sr.getScaledHeight(), c.getRGB(), c.getRGB());
					}
				}
			}

		} else
			this.drawDefaultBackground();

		this.hasActivePotionEffects = !this.field_192045_A.func_191878_b();

		if (this.field_192045_A.func_191878_b() && this.field_192046_B) {

			this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
			this.field_192045_A.func_191861_a(mouseX, mouseY, partialTicks);
		} else {

			this.field_192045_A.func_191861_a(mouseX, mouseY, partialTicks);
			super.drawScreen(mouseX, mouseY, partialTicks);
			this.field_192045_A.func_191864_a(this.guiLeft, this.guiTop, false, partialTicks);
		}

		this.func_191948_b(mouseX, mouseY);
		this.field_192045_A.func_191876_c(this.guiLeft, this.guiTop, mouseX, mouseY);
		this.oldMouseX = (float) mouseX;
		this.oldMouseY = (float) mouseY;

	}

	/**
	 * Draws the background layer of this container (behind the items).
	 */
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		if (Zamorozka.settingsManager.getSettingByName("SmoothInventoryOpen").getValBoolean() && ModuleManager.getModule(HUD.class).getState()) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
			animationState += Zamorozka.settingsManager.getSettingByName("SmoothInventory Speed").getValDouble() * mc.frameTime / 15;
			if (animationState >= ySize) {
				animationState = ySize;
			}
			animationState1 += Zamorozka.settingsManager.getSettingByName("SmoothInventory Speed").getValDouble() * mc.frameTime / 15;
			if (animationState1 >= guiTop) {
				animationState1 = guiTop;
			}
			int i = this.guiLeft;
			int j = (int) animationState1;
			this.drawTexturedModalRect(i, j, 0, 0, this.xSize, (int) animationState);
			drawEntityOnScreen(i + 51, j + 75, 30, (float) (i + 51) - this.oldMouseX, (float) (j + 75 - 50) - this.oldMouseY, this.mc.player);

		} else {

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
			int i = this.guiLeft;
			int j = (int) guiTop;
			this.drawTexturedModalRect(i, j, 0, 0, this.xSize, (int) animationState);
			drawEntityOnScreen(i + 51, j + 75, 30, (float) (i + 51) - this.oldMouseX, (float) (j + 75 - 50) - this.oldMouseY, this.mc.player);
		}
	}

	/**
	 * Draws an entity on the screen looking toward the cursor.
	 */
	public static void drawEntityOnScreen(double posX, double posY, double scale, float mouseX, float mouseY, EntityLivingBase ent) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) posX, (float) posY, 50.0F);
		GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		float f = ent.renderYawOffset;
		float f1 = ent.rotationYaw;
		float f2 = ent.rotationPitchHead;
		float f3 = ent.prevRotationYawHead;
		float f4 = ent.rotationYawHead;
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		ent.renderYawOffset = (float) Math.atan((double) (mouseX / 40.0F)) * 20.0F;
		ent.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 40.0F;
		ent.rotationPitchHead = -((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F;
		ent.rotationYawHead = ent.rotationYaw;
		ent.prevRotationYawHead = ent.rotationYaw;
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		ent.renderYawOffset = f;
		ent.rotationYaw = f1;
		ent.rotationPitchHead = f2;
		ent.prevRotationYawHead = f3;
		ent.rotationYawHead = f4;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	/**
	 * Test if the 2D point is in a rectangle (relative to the GUI). Args : rectX,
	 * rectY, rectWidth, rectHeight, pointX, pointY
	 */
	protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
		return (!this.field_192046_B || !this.field_192045_A.func_191878_b()) && super.isPointInRegion(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (!this.field_192045_A.func_191862_a(mouseX, mouseY, mouseButton)) {
			if (!this.field_192046_B || !this.field_192045_A.func_191878_b()) {
				super.mouseClicked(mouseX, mouseY, mouseButton);
			}
		}
	}

	/**
	 * Called when a mouse button is released.
	 */
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		if (this.field_194031_B) {
			this.field_194031_B = false;
		} else {
			super.mouseReleased(mouseX, mouseY, state);
		}
	}

	protected boolean func_193983_c(int p_193983_1_, int p_193983_2_, int p_193983_3_, int p_193983_4_) {
		boolean flag = p_193983_1_ < p_193983_3_ || p_193983_2_ < p_193983_4_ || p_193983_1_ >= p_193983_3_ + this.xSize || p_193983_2_ >= p_193983_4_ + this.ySize;
		return this.field_192045_A.func_193955_c(p_193983_1_, p_193983_2_, this.guiLeft, this.guiTop, this.xSize, this.ySize) && flag;
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for
	 * buttons)
	 */
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 10) {
			this.field_192045_A.func_193014_a(this.field_192046_B, ((ContainerPlayer) this.inventorySlots).craftMatrix);
			this.field_192045_A.func_191866_a();
			this.guiLeft = this.field_192045_A.func_193011_a(this.field_192046_B, this.width, this.xSize);
			this.field_192048_z.func_191746_c(this.guiLeft + 104, this.height / 2 - 22);
			this.field_194031_B = true;
		}
	}

	/**
	 * Fired when a key is typed (except F11 which toggles full screen). This is the
	 * equivalent of KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (!this.field_192045_A.func_191859_a(typedChar, keyCode)) {
			super.keyTyped(typedChar, keyCode);
		}
	}

	/**
	 * Called when the mouse is clicked over a slot or outside the gui.
	 */
	protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
		super.handleMouseClick(slotIn, slotId, mouseButton, type);
		this.field_192045_A.func_191874_a(slotIn);
	}

	public void func_192043_J_() {
		this.field_192045_A.func_193948_e();
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat events
	 */
	public void onGuiClosed() {
		if (mc.entityRenderer.theShaderGroup != null) {
			mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			mc.entityRenderer.theShaderGroup = null;
		}
		this.field_192045_A.func_191871_c();
		super.onGuiClosed();
	}

	public GuiRecipeBook func_194310_f() {
		return this.field_192045_A;
	}
}
