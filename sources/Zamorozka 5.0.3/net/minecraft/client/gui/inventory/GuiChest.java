package net.minecraft.client.gui.inventory;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import zamorozka.main.Zamorozka;
import zamorozka.module.ModuleManager;
import zamorozka.modules.VISUALLY.HUD;
import zamorozka.ui.RenderingUtils;

public class GuiChest extends GuiContainer {
	/** The ResourceLocation containing the chest GUI texture. */
	private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
	private final IInventory upperChestInventory;
	public final IInventory lowerChestInventory;

	/**
	 * window height is calculated with these values; the more rows, the heigher
	 */
	private final int inventoryRows;
	private float curAlpha;

	public GuiChest(IInventory upperInv, IInventory lowerInv) {
		super(new ContainerChest(upperInv, lowerInv, Minecraft.getMinecraft().player));
		this.upperChestInventory = upperInv;
		this.lowerChestInventory = lowerInv;
		this.allowUserInput = false;
		int i = 222;
		int j = 114;
		this.inventoryRows = lowerInv.getSizeInventory() / 9;
		this.ySize = 114 + this.inventoryRows * 18;
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
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.func_191948_b(mouseX, mouseY);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the
	 * items)
	 */
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRendererObj.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		this.fontRendererObj.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	/**
	 * Draws the background layer of this container (behind the items).
	 */
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
		this.drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
	}

	public IInventory getLowerChestInventory() {
		return lowerChestInventory;
	}

	public void initGui() {
		super.initGui();
		int posY = (height - ySize) / 2 + 5;
		buttonList.add(new GuiButton(1, width / 2 - 5, posY, 40, 10, "Steal"));
		buttonList.add(new GuiButton(2, width / 2 + 40, posY, 40, 10, "Store"));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);

		if (button.id == 1) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						for (int i = 0; i < GuiChest.this.inventoryRows * 9; i++) {
							ContainerChest container = (ContainerChest) Zamorozka.player().openContainer;
							if (container != null) {
								Thread.sleep(50L);
								Zamorozka.mc().playerController.windowClick(container.windowId, i, 0, ClickType.QUICK_MOVE, Zamorozka.player());
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}).start();
		} else if (button.id == 2) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						for (int i = GuiChest.this.inventoryRows * 9; i < GuiChest.this.inventoryRows * 9 + 44; i++) {
							Slot slot = (Slot) GuiChest.this.inventorySlots.inventorySlots.get(i);
							if (slot.getStack() != null) {
								Thread.sleep(5L);
								GuiChest.this.handleMouseClick(slot, slot.slotNumber, 0, ClickType.QUICK_MOVE);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}).start();
		}
	}

	public int getInventoryRows() {
		return inventoryRows;
	}

}