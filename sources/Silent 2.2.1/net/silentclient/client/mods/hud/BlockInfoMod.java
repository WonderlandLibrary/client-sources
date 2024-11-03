package net.silentclient.client.mods.hud;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.silentclient.client.Client;
import net.silentclient.client.gui.hud.ScreenPosition;
import net.silentclient.client.gui.lite.clickgui.utils.GlUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.CustomFontRenderer;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.ModDraggable;

import java.awt.*;

public class BlockInfoMod extends ModDraggable {
	private BlockPos pos;
    private IBlockState state;
    private Block block;
    private int maxWidth = 150;
	
	public BlockInfoMod() {
		super("BlockInfo", ModCategory.MODS, "silentclient/icons/mods/blockinfo.png");
	}
	
	@Override
	public void setup() {
		super.setup();
		this.addBooleanSetting("Block Coords", this, true);
		this.addBooleanSetting("Correct Tool", this, true);
		this.addBooleanSetting("Break Time", this, true);
		this.addBooleanSetting("Background", this, true);
		this.addColorSetting("Background Color", this, new Color(0, 0, 0), 127);
		this.addColorSetting("Color", this, new Color(255, 255, 255));
		this.addBooleanSetting("Font Shadow", this, true);
		this.addBooleanSetting("Fancy Font", this, false);
		this.addBooleanSetting("Rounded Corners", this, false);
		this.addSliderSetting("Rounding Strength", this, 3, 1, 6, true);
	}

	@Override
	public int getWidth() {
		return this.maxWidth;
	}

	@Override
	public int getHeight() {
		return 50;
	}

	@Override
	public boolean render(ScreenPosition screenPosition) {
		if(mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK) {
			pos = mc.objectMouseOver.getBlockPos();
			state = mc.theWorld.getBlockState(pos);
			block = state.getBlock();
		} else {
			pos = null;
			state = null;
			block = null;
		}
		
		if(block != null && !block.equals(Blocks.portal) && !block.equals(Blocks.end_portal)) {
			String harvest = "";
			boolean background = Client.getInstance().getSettingsManager().getSettingByName(this, "Background").getValBoolean();
			boolean roundedCorners = Client.getInstance().getSettingsManager().getSettingByName(this, "Rounded Corners").getValBoolean();

			Color backgroundColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Background Color").getValColor();
			boolean fontShadow = Client.getInstance().getSettingsManager().getSettingByName(this, "Font Shadow").getValBoolean();
			Color color = Client.getInstance().getSettingsManager().getSettingByName(this, "Color").getValColor();
			boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
			boolean coords = Client.getInstance().getSettingsManager().getSettingByName(this, "Block Coords").getValBoolean();
			boolean correctTool = Client.getInstance().getSettingsManager().getSettingByName(this, "Correct Tool").getValBoolean();
			boolean breakTimeBool = Client.getInstance().getSettingsManager().getSettingByName(this, "Break Time").getValBoolean();
			CustomFontRenderer font = new CustomFontRenderer();
			
			float infoY = 4.5F;
			
			font.setRenderMode(fancyFont ? CustomFontRenderer.RenderMode.CUSTOM : CustomFontRenderer.RenderMode.DEFAULT);

			if(background) {
				if(roundedCorners) {
					RenderUtil.drawRound(0, 0, getWidth(), getHeight(), Client.getInstance().getSettingsManager().getSettingByName(this, "Rounding Strength").getValInt(), backgroundColor);
				} else {
					RenderUtils.drawRect(0, 0, getWidth(), getHeight(), backgroundColor.getRGB());
				}
			}

			GlUtils.startScale(0, 0, 5, 50, 1.8F);
			RenderHelper.enableGUIStandardItemLighting();
			mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(block), 0 + 5, 0 + (50 / 2) - 8);
			RenderHelper.disableStandardItemLighting();
			GlUtils.stopScale();
			font.drawString(block.getLocalizedName(), 40, infoY, color.getRGB(), fontShadow);
			this.maxWidth = 40 + font.getStringWidth(block.getLocalizedName()) + 5;
			infoY += 10;
			if(coords) {
				font.drawString("(" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")", 40, infoY, color.getRGB(), fontShadow);
				if((40 + font.getStringWidth("(" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")")) > maxWidth) {
					this.maxWidth = 40 + font.getStringWidth("(" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")") + 5;
				}
				infoY += 10;
			}
			
			if(correctTool) {
				if(mc.thePlayer.inventory.getCurrentItem() != null && (!(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBlock))) {
					harvest = "Correct Item: " + (mc.thePlayer.inventory.getCurrentItem().getStrVsBlock(block) > 1 ? "true" : "false");
				} else {
					harvest = "Correct Item: false";
				}
											
				font.drawString(harvest, 40, infoY, color.getRGB(), fontShadow);
				if((40 + font.getStringWidth(harvest)) > maxWidth) {
					this.maxWidth = 40 + font.getStringWidth(harvest) + 5;
				}
				infoY += 10;
			}
			if(breakTimeBool) {
				float breakTime = BlockInfoMod.getBlockBreakTime(block, pos);
				String breakTimeText = "Break Time: " + (breakTime > 0 ? String.format("%.2f", breakTime) + " seconds" : breakTime == -1 ? "Instant" : "Unbreakable");
				font.drawString(breakTimeText, 40, infoY, color.getRGB(), fontShadow);
				if((40 + font.getStringWidth(breakTimeText)) > maxWidth) {
					this.maxWidth = 40 + font.getStringWidth(breakTimeText) + 5;
				}
			}

		}
		
		return true;
	}
	
	public static float getBlockBreakTime(Block block, BlockPos pos) {
		float seconds = 0;
		
		if(Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode) {
			return -1;
		}
				
		float hardness = block.getBlockHardness(Minecraft.getMinecraft().theWorld, pos);
		float speedMultiplier = 1;
		float damage = 0;
		if(Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null && (!(Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBlock)) && Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getStrVsBlock(block) > 1) {
			speedMultiplier = Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getStrVsBlock(block);
			if(!Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().canHarvestBlock(block)) {
				speedMultiplier = 1;
			} else if(EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem()) != 0) {
				speedMultiplier += Math.pow(EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem()), 2) + 1;
			}
		}
		
		if(Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.digSpeed) != null) {
			speedMultiplier *= 0.2F * Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1;
		}
		
		if(Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.digSlowdown) != null) {
			speedMultiplier *= Math.pow(0.3, Math.min(Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.digSlowdown).getAmplifier() + 1, 4));
		}
		
		if(Minecraft.getMinecraft().thePlayer.isInWater()) {
			speedMultiplier /= 5;
		}
		
		if(!Minecraft.getMinecraft().thePlayer.onGround) {
			speedMultiplier /= 5;
		}
		
		damage = speedMultiplier / hardness;
		
		damage /= Minecraft.getMinecraft().thePlayer.canHarvestBlock(block) ? 30 : 100;
		
		if (damage > 1) {
			return -1;
		}
		
		seconds = (1 / damage) / 20;
		
		return seconds;
	}
}
