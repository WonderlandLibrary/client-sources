package net.minecraft.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventManager;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import Squad.Squad;
import Squad.Events.EventRender2D;
import Squad.Utils.FontUtils;
import Squad.Utils.TimeHelper;
import Squad.Utils.Wrapper;
import Squad.base.Category;
import Squad.base.Module;
import Squad.info.ModuleInfo;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColors;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.border.WorldBorder;

public class GuiIngame extends Gui {
	private static final ResourceLocation vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
	private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
	private static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
	private final Random rand = new Random();
	private final Minecraft mc;
	private final RenderItem itemRenderer;

	/** ChatGUI instance that retains all previous chat data */
	private final GuiNewChat persistantChatGUI;
	private final GuiStreamIndicator streamIndicator;
	private int updateCounter;
	TimeHelper time;
	public static int selectedTab = 0;
	public static int selectedModule = 0;
	public static final int y = 10;
	public static boolean collapsed = false;

	/** The string specifying which record music is playing */
	private String recordPlaying = "";

	/** How many ticks the record playing message will be displayed */
	private int recordPlayingUpFor;
	private boolean recordIsPlaying;

	/** Previous frame vignette brightness (slowly changes by 1% each frame) */
	public float prevVignetteBrightness = 1.0F;

	/** Remaining ticks the item highlight should be visible */
	private int remainingHighlightTicks;

	/** The ItemStack that is currently being highlighted */
	private ItemStack highlightingItemStack;
	private final GuiOverlayDebug overlayDebug;

	/** The spectator GUI for this in-game GUI instance */
	private final GuiSpectator spectatorGui;
	private final GuiPlayerTabOverlay overlayPlayerList;
	private int field_175195_w;
	private String field_175201_x = "";
	private String field_175200_y = "";
	private int field_175199_z;
	private int field_175192_A;
	private int field_175193_B;
	private int playerHealth = 0;
	private int lastPlayerHealth = 0;

	/** The last recorded system time */
	private long lastSystemTime = 0L;

	/** Used with updateCounter to make the heart bar flash */
	private long healthUpdateCounter = 0L;

	public GuiIngame(Minecraft mcIn) {
		this.mc = mcIn;
		this.itemRenderer = mcIn.getRenderItem();
		this.overlayDebug = new GuiOverlayDebug(mcIn);
		this.spectatorGui = new GuiSpectator(mcIn);
		this.persistantChatGUI = new GuiNewChat(mcIn);
		this.streamIndicator = new GuiStreamIndicator(mcIn);
		this.overlayPlayerList = new GuiPlayerTabOverlay(mcIn, this);
		this.func_175177_a();
	}
	public void darwIcons(){
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("Ordner/Icon"));
	}

	public void func_175177_a() {
		this.field_175199_z = 10;
		this.field_175192_A = 70;
		this.field_175193_B = 20;
	}
	public void func_175180_a(float p_175180_1_)
    {
		
        ScaledResolution var2 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        int var3 = var2.getScaledWidth();
        int var4 = var2.getScaledHeight();
        this.mc.entityRenderer.setupOverlayRendering();
        GlStateManager.enableBlend();
        
        Renderimage();


        if (Minecraft.isFancyGraphicsEnabled())
        {
            this.func_180474_b(this.mc.thePlayer.getBrightness(p_175180_1_), var2);
        }
        else
        {
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }

        ItemStack var5 = this.mc.thePlayer.inventory.armorItemInSlot(3);

        if (this.mc.gameSettings.thirdPersonView == 0 && var5 != null && var5.getItem() == Item.getItemFromBlock(Blocks.pumpkin))
        {
            
        }

        if (!this.mc.thePlayer.isPotionActive(Potion.confusion))
        {
            float var6 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * p_175180_1_;

            if (var6 > 0.0F)
            {
                this.func_180474_b(var6, var2);
            }
        }
    }
	public void renderGameOverlay(float partialTicks) {
		ScaledResolution var2 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		int var3 = var2.getScaledWidth();
		int var4 = var2.getScaledHeight();
		this.mc.entityRenderer.setupOverlayRendering();
		 ModuleInfo.render();
         if(ModuleInfo.buildUp)
         {
          if(ModuleInfo.fading < 125)
          {
           ModuleInfo.fading++;
          }
          
          if(ModuleInfo.fading >= 125)
          {
           ModuleInfo.buildUp = false;
          }
      
          
         } else {
          if(ModuleInfo.fading > 0)
          {
           ModuleInfo.fading--;
          }
         }
		GlStateManager.enableBlend();
         
		
		if (Config.isVignetteEnabled()) {
			this.renderVignette(this.mc.thePlayer.getBrightness(partialTicks), var2);
		} else {
			GlStateManager.enableDepth();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		}

		ItemStack var5 = this.mc.thePlayer.inventory.armorItemInSlot(3);

		if (this.mc.gameSettings.thirdPersonView == 0 && var5 != null
				&& var5.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
			this.renderPumpkinOverlay(var2);
		}

		float var7;

		if (!this.mc.thePlayer.isPotionActive(Potion.confusion)) {
			var7 = this.mc.thePlayer.prevTimeInPortal
					+ (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;

			if (var7 > 0.0F) {
				this.func_180474_b(var7, var2);
			}
		}

		if (this.mc.playerController.isSpectator()) {
			this.spectatorGui.renderTooltip(var2, partialTicks);
		} else {
			this.renderTooltip(var2, partialTicks);
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(icons);
		GlStateManager.enableBlend();

		if (this.showCrosshair() && this.mc.gameSettings.thirdPersonView < 1) {
			GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
			GlStateManager.enableAlpha();
			this.drawTexturedModalRect(var3 / 2 - 7, var4 / 2 - 7, 0, 0, 16, 16);

}

		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		this.mc.mcProfiler.startSection("bossHealth");
		this.renderBossHealth();
		this.mc.mcProfiler.endSection();

		if (this.mc.playerController.shouldDrawHUD()) {
			this.renderPlayerStats(var2);
		}

		GlStateManager.disableBlend();
			     
		int var8;
		int var11;

		if (this.mc.thePlayer.getSleepTimer() > 0) {
			this.mc.mcProfiler.startSection("sleep");
			GlStateManager.disableDepth();
			GlStateManager.disableAlpha();
			var11 = this.mc.thePlayer.getSleepTimer();
			var7 = (float) var11 / 100.0F;

			if (var7 > 1.0F) {
				var7 = 1.0F - (float) (var11 - 100) / 10.0F;
			}

			var8 = (int) (220.0F * var7) << 24 | 1052704;
			drawRect(0, 0, var3, var4, var8);
			GlStateManager.enableAlpha();
			GlStateManager.enableDepth();
			this.mc.mcProfiler.endSection();
		}
		   

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		var11 = var3 / 2 - 91;

		if (this.mc.thePlayer.isRidingHorse()) {
			this.renderHorseJumpBar(var2, var11);
		} else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
			this.renderExpBar(var2, var11);
		}

		if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator()) {
			this.renderHeldItemTooltips(var2);
		} else if (this.mc.thePlayer.isSpectator()) {
			this.spectatorGui.func_175263_a(var2);
		}

		if (this.mc.isDemo()) {
			this.renderDemo(var2);
		}

		if (this.mc.gameSettings.showDebugInfo) {
			this.overlayDebug.renderDebugInfo(var2);
		}

		int var9;

		if (this.recordPlayingUpFor > 0) {
			this.mc.mcProfiler.startSection("overlayMessage");
			var7 = (float) this.recordPlayingUpFor - partialTicks;
			var8 = (int) (var7 * 255.0F / 20.0F);

			if (var8 > 255) {
				var8 = 255;
			}

			if (var8 > 8) {
				GlStateManager.pushMatrix();
				GlStateManager.translate((float) (var3 / 2), (float) (var4 - 68), 0.0F);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				var9 = 16777215;
						if (this.recordIsPlaying) {
					var9 = Color.HSBtoRGB(var7 / 50.0F, 0.7F, 0.6F) & 16777215;
				}

				this.getFontRenderer().drawString(this.recordPlaying,
						-this.getFontRenderer().getStringWidth(this.recordPlaying) / 2, -4,
						var9 + (var8 << 24 & -16777216));
				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}

			this.mc.mcProfiler.endSection();
		}

		if (this.field_175195_w > 0) {
			this.mc.mcProfiler.startSection("titleAndSubtitle");
			var7 = (float) this.field_175195_w - partialTicks;
			var8 = 255;

			if (this.field_175195_w > this.field_175193_B + this.field_175192_A) {
				float var12 = (float) (this.field_175199_z + this.field_175192_A + this.field_175193_B) - var7;
				var8 = (int) (var12 * 255.0F / (float) this.field_175199_z);
			}

			if (this.field_175195_w <= this.field_175193_B) {
				var8 = (int) (var7 * 255.0F / (float) this.field_175193_B);
			}

			var8 = MathHelper.clamp_int(var8, 0, 255);

			if (var8 > 8) {
				GlStateManager.pushMatrix();
				GlStateManager.translate((float) (var3 / 2), (float) (var4 / 2), 0.0F);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.pushMatrix();
				GlStateManager.scale(4.0F, 4.0F, 4.0F);
				var9 = var8 << 24 & -16777216;
				this.getFontRenderer().drawString(this.field_175201_x,
						(float) (-this.getFontRenderer().getStringWidth(this.field_175201_x) / 2), -10.0F,
						16777215 | var9, true);
				GlStateManager.popMatrix();
				GlStateManager.pushMatrix();
				GlStateManager.scale(2.0F, 2.0F, 2.0F);
				this.getFontRenderer().drawString(this.field_175200_y,
						(float) (-this.getFontRenderer().getStringWidth(this.field_175200_y) / 2), 5.0F,
						16777215 | var9, true);
				GlStateManager.popMatrix();
				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}

			this.mc.mcProfiler.endSection();
		}

		Scoreboard var12 = this.mc.theWorld.getScoreboard();
        ScoreObjective var13 = null;
        ScorePlayerTeam var15 = var12.getPlayersTeam(this.mc.thePlayer.getCommandSenderName());

		if (var15 != null) {
			int var16 = var15.getChatFormat().getColorIndex();

			if (var16 >= 0) {
				var13 = var12.getObjectiveInDisplaySlot(3 + var16);
			}
		}

		ScoreObjective var161 = var13 != null ? var13 : var12.getObjectiveInDisplaySlot(1);

		if (var161 != null) {
			this.renderScoreboard(var161, var2);
		}
		
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.disableAlpha();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0F, (float) (var4 - 48), 0.0F);
		
		this.mc.mcProfiler.startSection("chat");
		this.persistantChatGUI.drawChat(this.updateCounter);
		this.mc.mcProfiler.endSection();
		GlStateManager.popMatrix();
		var161 = var12.getObjectiveInDisplaySlot(0);

		if (this.mc.gameSettings.keyBindPlayerList.getIsKeyPressed() && (!this.mc.isIntegratedServerRunning()
				|| this.mc.thePlayer.sendQueue.getPlayerInfoMap().size() > 1 || var161 != null)) {
			this.overlayPlayerList.updatePlayerList(true);
			this.overlayPlayerList.renderPlayerlist(var3, var12, var161);
		} else {
			this.overlayPlayerList.updatePlayerList(false);
		}

		
		EventManager.call(new EventRender2D());
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
	}


	
	protected void renderTooltip(ScaledResolution sr, float partialTicks) {
		if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(widgetsTexPath);
			EntityPlayer var3 = (EntityPlayer) this.mc.getRenderViewEntity();
			int var4 = sr.getScaledWidth() / 2;
			float var5 = this.zLevel;
			this.zLevel = -90.0F;
			GuiScreen.drawRect(0, GuiScreen.height - 22, GuiScreen.width, GuiScreen.height, 0x88000000);
	    	Gui.drawRect(var4 - 90 - 1 + var3.inventory.currentItem * 20, sr.getScaledHeight() - 22,
	    	var4 - 92 - 1 + var3.inventory.currentItem * 20 + 23, GuiScreen.height -2, 0x87ffffff);
			this.drawTexturedModalRect(var4 - 91,
			    	sr.getScaledHeight() - 22, 0, 0, 182, 22);
			    	this.drawTexturedModalRect(var4 - 91 - 1 +
			    	var3.inventory.currentItem * 20, sr.getScaledHeight() -
			    	22 - 1, 0, 22, 24, 22);
			    	}
	//	GuiHUD.fu1.drawStringWithShadow("�3S", 1, 214, 0xFFFFFF);
			    	GlStateManager.enableRescaleNormal();
			    	GlStateManager.enableBlend();
			    	GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			    	RenderHelper.enableGUIStandardItemLighting();
            
			    	for (int var6 = 0; var6 < 9; ++var6) {
			    	int var7 = sr.getScaledWidth() / 2 - 90 + var6 * 20 + 2;
			    	int var8 = sr.getScaledHeight() - 16 - 3;
			    	EntityPlayer var3 = (EntityPlayer) this.mc.getRenderViewEntity();
					this.renderHotbarItem(var6, var7, var8, partialTicks, var3);
			    	}

			    	RenderHelper.disableStandardItemLighting();
			    	GlStateManager.disableRescaleNormal();
			    	GlStateManager.disableBlend();		    	
	}
	public static void drawImage(ResourceLocation src, int x, int y, int x1, int y1){
		  GL11.glPopMatrix();
		  Minecraft.getMinecraft().getTextureManager().bindTexture(src);
		  GlStateManager.color(1.0f, 1.0f, 1.0f , 1.0f);
		  GL11.glEnable(GL11.GL_BLEND);
		  GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		  Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, x1, y1, x1, y1);
		 GL11.glPushMatrix();
		 }
	private void Renderimage(){
		drawImage(new ResourceLocation("textures/gui/title/background/new"), Wrapper.getScaledRes().getScaledHeight()/ 2 -5, -20, 120, 120);
	}

	public void renderHorseJumpBar(ScaledResolution p_175186_1_, int p_175186_2_) {
		this.mc.mcProfiler.startSection("jumpBar");
		this.mc.getTextureManager().bindTexture(Gui.icons);
		float var3 = this.mc.thePlayer.getHorseJumpPower();
		short var4 = 182;
		int var5 = (int) (var3 * (float) (var4 + 1));
		int var6 = p_175186_1_.getScaledHeight() - 32 + 3;
		this.drawTexturedModalRect(p_175186_2_, var6, 0, 84, var4, 5);

		if (var5 > 0) {
			this.drawTexturedModalRect(p_175186_2_, var6, 0, 89, var5, 5);
		}

		this.mc.mcProfiler.endSection();
	}

	public void renderExpBar(ScaledResolution p_175176_1_, int p_175176_2_) {
		this.mc.mcProfiler.startSection("expBar");
		this.mc.getTextureManager().bindTexture(Gui.icons);
		int var3 = this.mc.thePlayer.xpBarCap();
		int var6;

		if (var3 > 0) {
			short var9 = 182;
			int var10 = (int) (this.mc.thePlayer.experience * (float) (var9 + 1));
			var6 = p_175176_1_.getScaledHeight() - 32 + 3;
			this.drawTexturedModalRect(p_175176_2_, var6, 0, 64, var9, 5);

			if (var10 > 0) {
				this.drawTexturedModalRect(p_175176_2_, var6, 0, 69, var10, 5);
			}
		}

		this.mc.mcProfiler.endSection();

		if (this.mc.thePlayer.experienceLevel > 0) {
			this.mc.mcProfiler.startSection("expLevel");
			int var91 = 8453920;

			if (Config.isCustomColors()) {
				var91 = CustomColors.getExpBarTextColor(var91);
			}
			String var101 = "" + this.mc.thePlayer.experienceLevel;
			var6 = (p_175176_1_.getScaledWidth() - this.getFontRenderer().getStringWidth(var101)) / 2;
			int var7 = p_175176_1_.getScaledHeight() - 31 - 4;
			boolean var8 = false;
			this.getFontRenderer().drawString(var101, var6 + 1, var7, 0);
			this.getFontRenderer().drawString(var101, var6 - 1, var7, 0);
			this.getFontRenderer().drawString(var101, var6, var7 + 1, 0);
			this.getFontRenderer().drawString(var101, var6, var7 - 1, 0);
			this.getFontRenderer().drawString(var101, var6, var7, var91);
			this.mc.mcProfiler.endSection();
		}
	}

	public void renderHeldItemTooltips(ScaledResolution p_175182_1_) {
		this.mc.mcProfiler.startSection("toolHighlight");

		if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
			String var2 = this.highlightingItemStack.getDisplayName();

			if (this.highlightingItemStack.hasDisplayName()) {
				var2 = EnumChatFormatting.ITALIC + var2;
			}

			int var3 = (p_175182_1_.getScaledWidth() - this.getFontRenderer().getStringWidth(var2)) / 2;
			int var4 = p_175182_1_.getScaledHeight() - 59;

			if (!this.mc.playerController.shouldDrawHUD()) {
				var4 += 14;
			}

			int var5 = (int) ((float) this.remainingHighlightTicks * 256.0F / 10.0F);

			if (var5 > 255) {
				var5 = 255;
			}

			if (var5 > 0) {
				GlStateManager.pushMatrix();
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				this.getFontRenderer().drawStringWithShadow(var2, (float) var3, (float) var4, 16777215 + (var5 << 24));
				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}
		}

		this.mc.mcProfiler.endSection();
	}
	public static FontUtils fu1 = new FontUtils("Mortal Kombat 5", Font.BOLD,50);
	public static FontUtils f2u24 = new FontUtils("Thruster Regular", Font.PLAIN,19);


	public void HUD(){
		Gui.drawRect(60, 27, 0, 0, 0x99000000);
		fu1.drawStringWithShadow("A�fouix", 2, -4, 0xff1167a7);
		
		 ModuleInfo.render();
         if(ModuleInfo.buildUp)
         {
          if(ModuleInfo.fading < 125)
          {
           ModuleInfo.fading++;
          }
          
          if(ModuleInfo.fading >= 125)
          {
           ModuleInfo.buildUp = false;
          }
      
          
         } else {
          if(ModuleInfo.fading > 0)
          {
           ModuleInfo.fading--;
          }
         }

	}

	public void TabGUi(){
		int count = 1;
		for (Category cat : Category.values()) {

			int color = 0xFF26C6DA;

			if (collapsed) {
				
				if (Category.values()[selectedTab].toString().equals(cat.name())) {
					color = 0xff1167a7;
				} else {
					color = 0x99000000;
				}
				
				
			} else if (Category.values()[selectedTab].toString().equals(cat.name())) {
				color = 0xff1167a7;
			} else {

				color = 0x99000000;
			}

			//RenderHelper.drawRect(3, ((count * 10) + y)+2, 55, ((count * 10) + 12) + y, 0xFF212121);
			
			Gui.drawRect(0, (count * 10) + 17.0F, 60, ((count * 10) + 10) + 17.0F, color);
			Gui.drawRect(59, (count * 10) - 80.0F, 60, ((count * 10) + 10) + 17.0F, 0xff1167a7);
			Gui.drawRect(0, (7 * 10) + 26.5F, 60, ((7 * 10) + 10) + 17.0F, 0xff1167a7);

			Gui.drawRect(3, (5 * 17) + 17, 53, ((5 * 14) + 12) +17, 0xFF37474F);
			Gui.drawRect(0, (5*17) + 17, 3, ((5 * 14) + 12) + 17, 0xFF6A1B9A);
			
			
			
			f2u24.drawCenteredString(cat.name(), 27, (count * 10) + (17 - 1), 0xFFFFFF);

			count++;
		}

		if (collapsed) {
			java.util.ArrayList<Module> modules = new java.util.ArrayList<Module>();

			for (Module m : Squad.instance.moduleManager.getModules()) {
				if (m.getCategory() == Category.values()[selectedTab]) {
					modules.add(m);
				}
			}

			Collections.sort(modules, new Comparator<Module>() {
				public int compare(Module m1, Module m2) {

					return Integer.valueOf(Minecraft.getMinecraft().fontRendererObj.getStringWidth(m2.getName()))
							.compareTo(Integer.valueOf(Minecraft.getMinecraft().fontRendererObj.getStringWidth(m1.getName())));
				}

			});

			int count2 = 1;
			for (Module cat : modules) {

				int color = 0xFF26C6DA;


				
				if (modules.get(selectedModule).getName().equals(cat.getName())) {
					color = 0xff1167a7;
				} else if (cat.toggled) {

					color = 0xff1167a7;

				} else {

					color = 0x99000000;
				}
				

				Gui.drawRect(60, (10*selectedTab)+(count2 * 10) + 17,
						f2u24.getWidth(modules.get(0).getName()) + 75, ((10*selectedTab)+(count2 * 10) + 10) + 17, color);
				f2u24.drawString(cat.getName(), 60, ((10*selectedTab)+count2 * 10) + (17 -2), 0xFFFFFF);

				count2++;
			}
		}

	}



	public void ArrayList(){
		   int count = 0;
	       for (Module module : Squad.instance.moduleManager.getModules()) {


	           ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().getMinecraft().displayHeight);
	           int sw = res.getScaledWidth();


	           if (module.isFading()) {
	             
	                   if (module.toggled) {
	                       if (module.getFadeAmount() != 2) {
	                           module.setFadeAmount(module.getFadeAmount() + 1);
	                           this.time.setLastMS();
	                       } else {
	                           module.setFading(false);
	                       }


	                   } else if (module.getFadeAmount() != -Minecraft.getMinecraft().fontRendererObj.getStringWidth(module.getName())) {
	                       module.setFadeAmount(module.getFadeAmount() - 1);
	                       this.time.setLastMS();
	                   } else {
	                       module.setFading(false);
	                   }
	               }
	           
	           if ((module.toggled) || (module.isFading())    ) {

	               Gui.drawRect(
	                                  sw - (f2u24.getWidth(module.getName()) + (module.getFadeAmount() + 4)), 10 * count,
	                                  sw - (f2u24.getWidth(module.getName()) - 200), 10 * count + 10,
	                                  0x99000000);
	               Gui.drawRect(
	                       sw - (f2u24.getWidth(module.getName()) + (module.getFadeAmount() + 4)), 10 * count,
	                       sw - (f2u24.getWidth(module.getName()) - 200), 10 * count + 10,
	                       0x99000000);
	           
	           	Gui.drawRect(
						sw - ((module.getFadeAmount())-0), (10 * count),
						sw - (f2u24.getWidth(module.getName()) - 200), (10 * count) + 10,
						getRainbow(6000, -75 * count));
	         		      f2u24.drawStringWithShadow(module.getName(),
	                              sw - (f2u24.getWidth(module.getName()) + module.getFadeAmount() + 2), 10 * count - 1,
	                              getRainbow(6000, -75 * count));

	      
	                      count++;
	           }
	           
	       }
	       

	   }

	
	   public void Arraylist(){
	    	
    	   int count = 0;
           for (Module module : Squad.instance.moduleManager.getModules()) {


               ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
               int sw = res.getScaledWidth();


               if (module.isFading()) {
                   if (this.time.isDelayComplete(7L)) {
                       if (module.toggled) {
                           if (module.getFadeAmount() != 2) {
                               module.setFadeAmount(module.getFadeAmount() + 1);
                               this.time.setLastMS();
                           } else {
                               module.setFading(false);
                           }


                       } else if (module.getFadeAmount() != -Minecraft.getMinecraft().fontRendererObj.getStringWidth(module.getName())) {
                           module.setFadeAmount(module.getFadeAmount() - 1);
                           this.time.setLastMS();
                       } else {
                           module.setFading(false);
                       }
                   }
               }
               if ((module.toggled) || (module.isFading())) {
                   Gui.drawRect(
                	          sw - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(module.getName()) + (module.getFadeAmount() + 5)), 10 * count, 
                	          sw - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(module.getName()) - 200), 10 * count + 10, 
                	          0xff000000);
          
                   f2u24.drawStringWithShadow(module.getName(),
                           sw - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(module.getName()) + module.getFadeAmount() + 0), 10 * count - 0,
                           module.getColor());

                   count++;
               }
           }

    	
    	
    	
    }

	
	public void notifications(){

        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().getMinecraft().displayHeight);
	    int sw = res.getScaledWidth();

		  for (Module module : Squad.instance.moduleManager.getModules()) {

	    if (module.isslidie()) {
	      
	            if (module.toggled) {
	                if (module.getSlidieAmount() != 2) {
	                    module.setSlidieAmount(module.getSlidieAmount() + 1);
	                    this.time.setLastMS();
	                } else {
	                        }
	                
	            }
	            if (module.toggled || module.isslidie()){
	                
	         
	            	Gui.drawRect(
	                    sw - (f2u24.getWidth(module.getName()) + (module.getSlidieAmount() + 100)), 8 * 45,
	                    sw - (f2u24.getWidth(module.getName()) - 300), 20 * 20 + 30,
	                    0xff000000);
	                f2u24.drawStringWithShadow(module.getName(),
	                        sw - (f2u24.getWidth(module.getName() + " Activited!") + module.getFadeAmount() + 15), 10 * 36 - 1,
	                        getRainbow(6000, -75 * 10));

	    }else{
	        f2u24.drawStringWithShadow(module.getName(),
	                sw - (f2u24.getWidth(module.getName() + " Disabled!") + module.getFadeAmount() + 15), 10 * 36 - 1,
	                getRainbow(6000, -75 * 10));

	    }
		  }
		  }
	}

	   private int getRainbow(int speed, int offset) {
	       float hue = (System.currentTimeMillis() + offset) % speed;
	       hue /= speed;
	       return Color.getHSBColor(hue, 1f, 1f).getRGB();
	   }


	public void renderDemo(ScaledResolution p_175185_1_) {
		this.mc.mcProfiler.startSection("demo");
		String var2 = "";

		if (this.mc.theWorld.getTotalWorldTime() >= 120500L) {
			var2 = I18n.format("demo.demoExpired", new Object[0]);
		} else {
			var2 = I18n.format("demo.remainingTime", new Object[] {
					StringUtils.ticksToElapsedTime((int) (120500L - this.mc.theWorld.getTotalWorldTime())) });
		}

		int var3 = this.getFontRenderer().getStringWidth(var2);
		this.getFontRenderer().drawStringWithShadow(var2, (float) (p_175185_1_.getScaledWidth() - var3 - 10), 5.0F,
				16777215);
		this.mc.mcProfiler.endSection();
	}

	protected boolean showCrosshair() {
		if (this.mc.gameSettings.showDebugInfo && !this.mc.thePlayer.hasReducedDebug()
				&& !this.mc.gameSettings.reducedDebugInfo) {
			return false;
		} else if (this.mc.playerController.isSpectator()) {
			if (this.mc.pointedEntity != null) {
				return true;
			} else {
				if (this.mc.objectMouseOver != null
						&& this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					BlockPos var1 = this.mc.objectMouseOver.getBlockPos();

					if (this.mc.theWorld.getTileEntity(var1) instanceof IInventory) {
						return true;
					}
				}

				return false;
			}
		} else {
			return true;
		}
	}

	public void renderStreamIndicator(ScaledResolution p_180478_1_) {
		this.streamIndicator.render(p_180478_1_.getScaledWidth() - 10, 10);
	}

	private void renderScoreboard(ScoreObjective p_180475_1_, ScaledResolution p_180475_2_) {
		Scoreboard var3 = p_180475_1_.getScoreboard();
		Collection var4 = var3.getSortedScores(p_180475_1_);
		ArrayList var5 = Lists.newArrayList(Iterables.filter(var4, new Predicate() {
			public boolean apply(Score p_178903_1_) {
				return p_178903_1_.getPlayerName() != null && !p_178903_1_.getPlayerName().startsWith("#");
			}

			public boolean apply(Object p_apply_1_) {
				return this.apply((Score) p_apply_1_);
			}
		}));
		ArrayList var21;

		if (var5.size() > 15) {
			var21 = Lists.newArrayList(Iterables.skip(var5, var4.size() - 15));
		} else {
			var21 = var5;
		}

		int var6 = this.getFontRenderer().getStringWidth(p_180475_1_.getDisplayName());
		String var10;

		for (Iterator var22 = var21.iterator(); var22
				.hasNext(); var6 = Math.max(var6, this.getFontRenderer().getStringWidth(var10))) {
			Score var23 = (Score) var22.next();
			ScorePlayerTeam var24 = var3.getPlayersTeam(var23.getPlayerName());
			var10 = ScorePlayerTeam.formatPlayerName(var24, var23.getPlayerName()) + ": " + EnumChatFormatting.RED
					+ var23.getScorePoints();
		}

		int var221 = var21.size() * this.getFontRenderer().FONT_HEIGHT;
		int var231 = p_180475_2_.getScaledHeight() / 2 + var221 / 3;
		byte var241 = 3;
		int var25 = p_180475_2_.getScaledWidth() - var6 - var241;
		int var11 = 0;
		Iterator var12 = var21.iterator();

		while (var12.hasNext()) {
			Score var13 = (Score) var12.next();
			++var11;
			ScorePlayerTeam var14 = var3.getPlayersTeam(var13.getPlayerName());
			String var15 = ScorePlayerTeam.formatPlayerName(var14, var13.getPlayerName());
			String var16 = EnumChatFormatting.RED + "" + var13.getScorePoints();
			int var18 = var231 - var11 * this.getFontRenderer().FONT_HEIGHT;
			int var19 = p_180475_2_.getScaledWidth() - var241 + 2;
			drawRect(var25 - 2, var18, var19, var18 + this.getFontRenderer().FONT_HEIGHT, 1342177280);
			this.getFontRenderer().drawString(var15, var25, var18, 553648127);
			this.getFontRenderer().drawString(var16, var19 - this.getFontRenderer().getStringWidth(var16), var18,
					553648127);

			if (var11 == var21.size()) {
				String var20 = p_180475_1_.getDisplayName();
				drawRect(var25 - 2, var18 - this.getFontRenderer().FONT_HEIGHT - 1, var19, var18 - 1, 1610612736);
				drawRect(var25 - 2, var18 - 1, var19, var18, 1342177280);
				this.getFontRenderer().drawString(var20,
						var25 + var6 / 2 - this.getFontRenderer().getStringWidth(var20) / 2,
						var18 - this.getFontRenderer().FONT_HEIGHT, 553648127);
			}
		}
	}

	private void renderPlayerStats(ScaledResolution p_180477_1_) {
		if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
			EntityPlayer var2 = (EntityPlayer) this.mc.getRenderViewEntity();
			int var3 = MathHelper.ceiling_float_int(var2.getHealth());
			boolean var4 = this.healthUpdateCounter > (long) this.updateCounter
					&& (this.healthUpdateCounter - (long) this.updateCounter) / 3L % 2L == 1L;

			if (var3 < this.playerHealth && var2.hurtResistantTime > 0) {
				this.lastSystemTime = Minecraft.getSystemTime();
				this.healthUpdateCounter = (long) (this.updateCounter + 20);
			} else if (var3 > this.playerHealth && var2.hurtResistantTime > 0) {
				this.lastSystemTime = Minecraft.getSystemTime();
				this.healthUpdateCounter = (long) (this.updateCounter + 10);
			}

			if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
				this.playerHealth = var3;
				this.lastPlayerHealth = var3;
				this.lastSystemTime = Minecraft.getSystemTime();
			}

			this.playerHealth = var3;
			int var5 = this.lastPlayerHealth;
			this.rand.setSeed((long) (this.updateCounter * 312871));
			boolean var6 = false;
			FoodStats var7 = var2.getFoodStats();
			int var8 = var7.getFoodLevel();
			int var9 = var7.getPrevFoodLevel();
			IAttributeInstance var10 = var2.getEntityAttribute(SharedMonsterAttributes.maxHealth);
			int var11 = p_180477_1_.getScaledWidth() / 2 - 91;
			int var12 = p_180477_1_.getScaledWidth() / 2 + 91;
			int var13 = p_180477_1_.getScaledHeight() - 39;
			float var14 = (float) var10.getAttributeValue();
			float var15 = var2.getAbsorptionAmount();
			int var16 = MathHelper.ceiling_float_int((var14 + var15) / 2.0F / 10.0F);
			int var17 = Math.max(10 - (var16 - 2), 3);
			int var18 = var13 - (var16 - 1) * var17 - 10;
			float var19 = var15;
			int var20 = var2.getTotalArmorValue();
			int var21 = -1;

			if (var2.isPotionActive(Potion.regeneration)) {
				var21 = this.updateCounter % MathHelper.ceiling_float_int(var14 + 5.0F);
			}

			this.mc.mcProfiler.startSection("armor");
			int var22;
			int var23;

			for (var22 = 0; var22 < 10; ++var22) {
				if (var20 > 0) {
					var23 = var11 + var22 * 8;

					if (var22 * 2 + 1 < var20) {
						this.drawTexturedModalRect(var23, var18, 34, 9, 9, 9);
					}

					if (var22 * 2 + 1 == var20) {
						this.drawTexturedModalRect(var23, var18, 25, 9, 9, 9);
					}

					if (var22 * 2 + 1 > var20) {
						this.drawTexturedModalRect(var23, var18, 16, 9, 9, 9);
					}
				}
			}

			this.mc.mcProfiler.endStartSection("health");
			int var25;
			int var26;
			int var27;

			for (var22 = MathHelper.ceiling_float_int((var14 + var15) / 2.0F) - 1; var22 >= 0; --var22) {
				var23 = 16;

				if (var2.isPotionActive(Potion.poison)) {
					var23 += 36;
				} else if (var2.isPotionActive(Potion.wither)) {
					var23 += 72;
				}

				byte var34 = 0;

				if (var4) {
					var34 = 1;
				}

				var25 = MathHelper.ceiling_float_int((float) (var22 + 1) / 10.0F) - 1;
				var26 = var11 + var22 % 10 * 8;
				var27 = var13 - var25 * var17;

				if (var3 <= 4) {
					var27 += this.rand.nextInt(2);
				}

				if (var22 == var21) {
					var27 -= 2;
				}

				byte var36 = 0;

				if (var2.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
					var36 = 5;
				}

				this.drawTexturedModalRect(var26, var27, 16 + var34 * 9, 9 * var36, 9, 9);

				if (var4) {
					if (var22 * 2 + 1 < var5) {
						this.drawTexturedModalRect(var26, var27, var23 + 54, 9 * var36, 9, 9);
					}

					if (var22 * 2 + 1 == var5) {
						this.drawTexturedModalRect(var26, var27, var23 + 63, 9 * var36, 9, 9);
					}
				}

				if (var19 > 0.0F) {
					if (var19 == var15 && var15 % 2.0F == 1.0F) {
						this.drawTexturedModalRect(var26, var27, var23 + 153, 9 * var36, 9, 9);
					} else {
						this.drawTexturedModalRect(var26, var27, var23 + 144, 9 * var36, 9, 9);
					}

					var19 -= 2.0F;
				} else {
					if (var22 * 2 + 1 < var3) {
						this.drawTexturedModalRect(var26, var27, var23 + 36, 9 * var36, 9, 9);
					}

					if (var22 * 2 + 1 == var3) {
						this.drawTexturedModalRect(var26, var27, var23 + 45, 9 * var36, 9, 9);
					}
				}
			}

			Entity var371 = var2.ridingEntity;
			int var38;

			if (var371 == null) {
				this.mc.mcProfiler.endStartSection("food");

				for (var23 = 0; var23 < 10; ++var23) {
					var38 = var13;
					var25 = 16;
					byte var35 = 0;

					if (var2.isPotionActive(Potion.hunger)) {
						var25 += 36;
						var35 = 13;
					}

					if (var2.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (var8 * 3 + 1) == 0) {
						var38 = var13 + (this.rand.nextInt(3) - 1);
					}

					if (var6) {
						var35 = 1;
					}

					var27 = var12 - var23 * 8 - 9;
					this.drawTexturedModalRect(var27, var38, 16 + var35 * 9, 27, 9, 9);

					if (var6) {
						if (var23 * 2 + 1 < var9) {
							this.drawTexturedModalRect(var27, var38, var25 + 54, 27, 9, 9);
						}

						if (var23 * 2 + 1 == var9) {
							this.drawTexturedModalRect(var27, var38, var25 + 63, 27, 9, 9);
						}
					}

					if (var23 * 2 + 1 < var8) {
						this.drawTexturedModalRect(var27, var38, var25 + 36, 27, 9, 9);
					}

					if (var23 * 2 + 1 == var8) {
						this.drawTexturedModalRect(var27, var38, var25 + 45, 27, 9, 9);
					}
				}
			} else if (var371 instanceof EntityLivingBase) {
				this.mc.mcProfiler.endStartSection("mountHealth");
				EntityLivingBase var391 = (EntityLivingBase) var371;
				var38 = (int) Math.ceil((double) var391.getHealth());
				float var37 = var391.getMaxHealth();
				var26 = (int) (var37 + 0.5F) / 2;

				if (var26 > 30) {
					var26 = 30;
				}

				var27 = var13;

				for (int var39 = 0; var26 > 0; var39 += 20) {
					int var29 = Math.min(var26, 10);
					var26 -= var29;

					for (int var30 = 0; var30 < var29; ++var30) {
						byte var31 = 52;
						byte var32 = 0;

						if (var6) {
							var32 = 1;
						}

						int var33 = var12 - var30 * 8 - 9;
						this.drawTexturedModalRect(var33, var27, var31 + var32 * 9, 9, 9, 9);

						if (var30 * 2 + 1 + var39 < var38) {
							this.drawTexturedModalRect(var33, var27, var31 + 36, 9, 9, 9);
						}

						if (var30 * 2 + 1 + var39 == var38) {
							this.drawTexturedModalRect(var33, var27, var31 + 45, 9, 9, 9);
						}
					}

					var27 -= 10;
				}
			}

			this.mc.mcProfiler.endStartSection("air");

			if (var2.isInsideOfMaterial(Material.water)) {
				var23 = this.mc.thePlayer.getAir();
				var38 = MathHelper.ceiling_double_int((double) (var23 - 2) * 10.0D / 300.0D);
				var25 = MathHelper.ceiling_double_int((double) var23 * 10.0D / 300.0D) - var38;

				for (var26 = 0; var26 < var38 + var25; ++var26) {
					if (var26 < var38) {
						this.drawTexturedModalRect(var12 - var26 * 8 - 9, var18, 16, 18, 9, 9);
					} else {
						this.drawTexturedModalRect(var12 - var26 * 8 - 9, var18, 25, 18, 9, 9);
					}
				}
			}

			this.mc.mcProfiler.endSection();
		}
	}

	/**
	 * Renders dragon's (boss) health on the HUD
	 */
	private void renderBossHealth() {
		if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
			--BossStatus.statusBarTime;
			FontRenderer var1 = this.mc.fontRendererObj;
			ScaledResolution var2 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
			int var3 = var2.getScaledWidth();
			short var4 = 182;
			int var5 = var3 / 2 - var4 / 2;
			int var6 = (int) (BossStatus.healthScale * (float) (var4 + 1));
			byte var7 = 12;
			this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
			this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);

			if (var6 > 0) {
				this.drawTexturedModalRect(var5, var7, 0, 79, var6, 5);
			}

			String var8 = BossStatus.bossName;
			int bossTextColor = 16777215;

			if (Config.isCustomColors()) {
				bossTextColor = CustomColors.getBossTextColor(bossTextColor);
			}

			this.getFontRenderer().drawStringWithShadow(var8,
					(float) (var3 / 2 - this.getFontRenderer().getStringWidth(var8) / 2), (float) (var7 - 10),
					bossTextColor);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(icons);
		}
	}

	private void renderPumpkinOverlay(ScaledResolution p_180476_1_) {
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableAlpha();
		this.mc.getTextureManager().bindTexture(pumpkinBlurTexPath);
		Tessellator var2 = Tessellator.getInstance();
		WorldRenderer var3 = var2.getWorldRenderer();
		var3.startDrawingQuads();
		var3.addVertexWithUV(0.0D, (double) p_180476_1_.getScaledHeight(), -90.0D, 0.0D, 1.0D);
		var3.addVertexWithUV((double) p_180476_1_.getScaledWidth(), (double) p_180476_1_.getScaledHeight(), -90.0D,
				1.0D, 1.0D);
		var3.addVertexWithUV((double) p_180476_1_.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
		var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		var2.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Renders a Vignette arount the entire screen that changes with light
	 * level.
	 */
	private void renderVignette(float p_180480_1_, ScaledResolution p_180480_2_) {
		if (Config.isVignetteEnabled()) {
			p_180480_1_ = 1.0F - p_180480_1_;
			p_180480_1_ = MathHelper.clamp_float(p_180480_1_, 0.0F, 1.0F);
			WorldBorder var3 = this.mc.theWorld.getWorldBorder();
			float var4 = (float) var3.getClosestDistance(this.mc.thePlayer);
			double var5 = Math.min(var3.getResizeSpeed() * (double) var3.getWarningTime() * 1000.0D,
					Math.abs(var3.getTargetSize() - var3.getDiameter()));
			double var7 = Math.max((double) var3.getWarningDistance(), var5);

			if ((double) var4 < var7) {
				var4 = 1.0F - (float) ((double) var4 / var7);
			} else {
				var4 = 0.0F;
			}

			this.prevVignetteBrightness = (float) ((double) this.prevVignetteBrightness
					+ (double) (p_180480_1_ - this.prevVignetteBrightness) * 0.01D);
			GlStateManager.disableDepth();
			GlStateManager.depthMask(false);
			GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);

			if (var4 > 0.0F) {
				GlStateManager.color(0.0F, var4, var4, 1.0F);
			} else {
				GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness,
						this.prevVignetteBrightness, 1.0F);
			}

			this.mc.getTextureManager().bindTexture(vignetteTexPath);
			Tessellator var9 = Tessellator.getInstance();
			WorldRenderer var10 = var9.getWorldRenderer();
			var10.startDrawingQuads();
			var10.addVertexWithUV(0.0D, (double) p_180480_2_.getScaledHeight(), -90.0D, 0.0D, 1.0D);
			var10.addVertexWithUV((double) p_180480_2_.getScaledWidth(), (double) p_180480_2_.getScaledHeight(), -90.0D,
					1.0D, 1.0D);
			var10.addVertexWithUV((double) p_180480_2_.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
			var10.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
			var9.draw();
			GlStateManager.depthMask(true);
			GlStateManager.enableDepth();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		}
	}

	private void func_180474_b(float p_180474_1_, ScaledResolution p_180474_2_) {
		if (p_180474_1_ < 1.0F) {
			p_180474_1_ *= p_180474_1_;
			p_180474_1_ *= p_180474_1_;
			p_180474_1_ = p_180474_1_ * 0.8F + 0.2F;
		}

		GlStateManager.disableAlpha();
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(1.0F, 1.0F, 1.0F, p_180474_1_);
		this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		TextureAtlasSprite var3 = this.mc.getBlockRendererDispatcher().getBlockModelShapes()
				.getTexture(Blocks.portal.getDefaultState());
		float var4 = var3.getMinU();
		float var5 = var3.getMinV();
		float var6 = var3.getMaxU();
		float var7 = var3.getMaxV();
		Tessellator var8 = Tessellator.getInstance();
		WorldRenderer var9 = var8.getWorldRenderer();
		var9.startDrawingQuads();
		var9.addVertexWithUV(0.0D, (double) p_180474_2_.getScaledHeight(), -90.0D, (double) var4, (double) var7);
		var9.addVertexWithUV((double) p_180474_2_.getScaledWidth(), (double) p_180474_2_.getScaledHeight(), -90.0D,
				(double) var6, (double) var7);
		var9.addVertexWithUV((double) p_180474_2_.getScaledWidth(), 0.0D, -90.0D, (double) var6, (double) var5);
		var9.addVertexWithUV(0.0D, 0.0D, -90.0D, (double) var4, (double) var5);
		var8.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private void renderHotbarItem(int index, int xPos, int yPos, float partialTicks, EntityPlayer p_175184_5_) {
		ItemStack var6 = p_175184_5_.inventory.mainInventory[index];

		if (var6 != null) {
			float var7 = (float) var6.animationsToGo - partialTicks;

			if (var7 > 0.0F) {
				GlStateManager.pushMatrix();
				float var8 = 1.0F + var7 / 5.0F;
				GlStateManager.translate((float) (xPos + 8), (float) (yPos + 12), 0.0F);
				GlStateManager.scale(1.0F / var8, (var8 + 1.0F) / 2.0F, 1.0F);
				GlStateManager.translate((float) (-(xPos + 8)), (float) (-(yPos + 12)), 0.0F);
			}

			this.itemRenderer.renderItemAndEffectIntoGUI(var6, xPos, yPos);

			if (var7 > 0.0F) {
				GlStateManager.popMatrix();
			}

			this.itemRenderer.renderItemOverlays(this.mc.fontRendererObj, var6, xPos, yPos);
		}
	}

	/**
	 * The update tick for the ingame UI
	 */
	public void updateTick() {
		if (this.recordPlayingUpFor > 0) {
			--this.recordPlayingUpFor;
		}

		if (this.field_175195_w > 0) {
			--this.field_175195_w;

			if (this.field_175195_w <= 0) {
				this.field_175201_x = "";
				this.field_175200_y = "";
			}
		}

		++this.updateCounter;
		this.streamIndicator.func_152439_a();

		if (this.mc.thePlayer != null) {
			ItemStack var1 = this.mc.thePlayer.inventory.getCurrentItem();

			if (var1 == null) {
				this.remainingHighlightTicks = 0;
			} else if (this.highlightingItemStack != null && var1.getItem() == this.highlightingItemStack.getItem()
					&& ItemStack.areItemStackTagsEqual(var1, this.highlightingItemStack)
					&& (var1.isItemStackDamageable()
							|| var1.getMetadata() == this.highlightingItemStack.getMetadata())) {
				if (this.remainingHighlightTicks > 0) {
					--this.remainingHighlightTicks;
				}
			} else {
				this.remainingHighlightTicks = 40;
			}

			this.highlightingItemStack = var1;
		}
	}

	public void setRecordPlayingMessage(String p_73833_1_) {
		this.setRecordPlaying(I18n.format("record.nowPlaying", new Object[] { p_73833_1_ }), true);
	}

	public void setRecordPlaying(String p_110326_1_, boolean p_110326_2_) {
		this.recordPlaying = p_110326_1_;
		this.recordPlayingUpFor = 60;
		this.recordIsPlaying = p_110326_2_;
	}

	public void displayTitle(String p_175178_1_, String p_175178_2_, int p_175178_3_, int p_175178_4_,
			int p_175178_5_) {
		if (p_175178_1_ == null && p_175178_2_ == null && p_175178_3_ < 0 && p_175178_4_ < 0 && p_175178_5_ < 0) {
			this.field_175201_x = "";
			this.field_175200_y = "";
			this.field_175195_w = 0;
		} else if (p_175178_1_ != null) {
			this.field_175201_x = p_175178_1_;
			this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
		} else if (p_175178_2_ != null) {
			this.field_175200_y = p_175178_2_;
		} else {
			if (p_175178_3_ >= 0) {
				this.field_175199_z = p_175178_3_;
			}

			if (p_175178_4_ >= 0) {
				this.field_175192_A = p_175178_4_;
			}

			if (p_175178_5_ >= 0) {
				this.field_175193_B = p_175178_5_;
			}

			if (this.field_175195_w > 0) {
				this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
			}
		}
	}

	public void setRecordPlaying(IChatComponent p_175188_1_, boolean p_175188_2_) {
		this.setRecordPlaying(p_175188_1_.getUnformattedText(), p_175188_2_);
	}

	/**
	 * returns a pointer to the persistant Chat GUI, containing all previous
	 * chat messages and such
	 */
	public GuiNewChat getChatGUI() {
		return this.persistantChatGUI;
	}

	public int getUpdateCounter() {
		return this.updateCounter;
	}

	public FontRenderer getFontRenderer() {
		return this.mc.fontRendererObj;
	}

	public GuiSpectator getSpectatorGui() {
		return this.spectatorGui;
	}

	public GuiPlayerTabOverlay getTabList() {
		return this.overlayPlayerList;
	}
}
