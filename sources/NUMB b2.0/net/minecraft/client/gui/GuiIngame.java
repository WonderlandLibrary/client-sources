package net.minecraft.client.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventManager;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.border.WorldBorder;
import optifine.Config;
import us.loki.legit.Client;
import us.loki.legit.events.EventTick;
import us.loki.legit.gui.cirexminimap.Minimap;
import us.loki.legit.modules.Category;
import us.loki.legit.modules.Module;
import us.loki.legit.modules.ModuleManager;
import us.loki.legit.modules.impl.Render.NoScoreBoard;
import us.loki.legit.utils.ColorUtils;
import us.loki.legit.utils.R2DUtils;
import us.loki.legit.utils.RenderUtils;

public class GuiIngame extends Gui {
	private static final ResourceLocation vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
	private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
	private static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
	private final Random rand = new Random();
	private final Minecraft mc;
	public final RenderItem itemRenderer;
	private KeyBinding Forward = Minecraft.getMinecraft().gameSettings.keyBindForward;
    private KeyBinding Left = Minecraft.getMinecraft().gameSettings.keyBindLeft;
    private KeyBinding Back = Minecraft.getMinecraft().gameSettings.keyBindBack;
    private KeyBinding Right = Minecraft.getMinecraft().gameSettings.keyBindRight;
    private int color = 0xFF2571D7;
    private int color2 = 0xFFFFFFFF;

	/** ChatGUI instance that retains all previous chat data */
	public GuiNewChat persistantChatGUI;
	private final GuiStreamIndicator streamIndicator;
	private int updateCounter;

	/** The string specifying which record music is playing */
	private String recordPlaying = "";

	/** How many ticks the record playing message will be displayed */
	private int recordPlayingUpFor;
	private boolean recordIsPlaying;

	/** Previous frame vignette brightness (slowly changes by 1% each frame) */
	public float prevVignetteBrightness = 1.0F;

	/** Remaining ticks the item highlight should be visible */
	private int remainingHighlightTicks;
	
	/* some weird shit for the radar*/
	public static int RadarX = 0;
    public static int RadarY = 0;
    public static int tick = 0;

	/** The ItemStack that is currently being highlighted */
	private ItemStack highlightingItemStack;
	private final GuiOverlayDebug overlayDebug;
	private final GuiSpectator field_175197_u;
	private final GuiPlayerTabOverlay overlayPlayerList;
	private int field_175195_w;
	private String field_175201_x = "";
	private String field_175200_y = "";
	private int field_175199_z;
	private int field_175192_A;
	private int field_175193_B;
	private int field_175194_C = 0;
	private int field_175189_D = 0;
	private long field_175190_E = 0L;
	private long field_175191_F = 0L;
	private static final String __OBFID = "CL_00000661";
	private Minimap map = new Minimap();

	public GuiIngame(Minecraft mcIn) {
		this.mc = mcIn;
		this.itemRenderer = mcIn.getRenderItem();
		this.overlayDebug = new GuiOverlayDebug(mcIn);
		this.field_175197_u = new GuiSpectator(mcIn);
		this.persistantChatGUI = new GuiNewChat(mcIn);
		this.streamIndicator = new GuiStreamIndicator(mcIn);
		this.overlayPlayerList = new GuiPlayerTabOverlay(mcIn, this);
		this.func_175177_a();
	}

	public void func_175177_a() {
		this.field_175199_z = 10;
		this.field_175192_A = 70;
		this.field_175193_B = 20;
	}

	public void func_175180_a(float p_175180_1_) {
		EventTick event = new EventTick();
		EventManager.call(event);
		ScaledResolution var2 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		int var3 = var2.getScaledWidth();
		int var4 = var2.getScaledHeight();
		this.mc.entityRenderer.setupOverlayRendering();
		GlStateManager.enableBlend();
		// #####################################################################################################//
		// -----------------------------------------------------------------------------------------------------//
		// #####################################################################################################//
		// #####################################################################################################//
		// -----------------------------------------------------------------------------------------------------//
		// #####################################################################################################//
		// #####################################################################################################//
		// -----------------------------------------------------------------------------------------------------//
		// #####################################################################################################//

		renderEverything();
		renderArrayList();
		renderStuffStatus(var2);
		renderPotionStatus(var2);
		renderKeystrokes();
		renderName(var2);
		drawClientVersion(var2);
		oldKeystrokes();
		Keystrokes1();
		DirectionHUD(var2);
		Keystrokes2();
		Keystrokes3();

		if (Config.isVignetteEnabled()) {
			this.func_180480_a(this.mc.thePlayer.getBrightness(p_175180_1_), var2);
		} else {
			GlStateManager.enableDepth();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		}

		ItemStack var5 = this.mc.thePlayer.inventory.armorItemInSlot(3);

		if (this.mc.gameSettings.thirdPersonView == 0 && var5 != null
				&& var5.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
			this.func_180476_e(var2);
		}

		float var7;

		if (!this.mc.thePlayer.isPotionActive(Potion.confusion)) {
			var7 = this.mc.thePlayer.prevTimeInPortal
					+ (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * p_175180_1_;

			if (var7 > 0.0F) {
				this.func_180474_b(var7, var2);
			}
		}

		if (this.mc.playerController.enableEverythingIsScrewedUpMode()) {
			this.field_175197_u.func_175264_a(var2, p_175180_1_);
		} else {
			this.func_180479_a(var2, p_175180_1_);
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(icons);
		GlStateManager.enableBlend();

		if (this.func_175183_b() && this.mc.gameSettings.thirdPersonView < 1) {
			GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
			GlStateManager.enableAlpha();
			this.drawTexturedModalRect(var3 / 2 - 7, var4 / 2 - 7, 0, 0, 16, 16);
		}

		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		this.mc.mcProfiler.startSection("bossHealth");
		this.renderBossHealth();
		this.mc.mcProfiler.endSection();

		if (this.mc.playerController.shouldDrawHUD()) {
			this.func_180477_d(var2);
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
			this.func_175186_a(var2, var11);
		} else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
			this.func_175176_b(var2, var11);
		}

		if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.enableEverythingIsScrewedUpMode()) {
			this.func_175182_a(var2);
		} else if (this.mc.thePlayer.func_175149_v()) {
			this.field_175197_u.func_175263_a(var2);
		}

		if (this.mc.isDemo()) {
			this.func_175185_b(var2);
		}

		if (this.mc.gameSettings.showDebugInfo) {
			this.overlayDebug.func_175237_a(var2);
		}

		int var9;

		if (this.recordPlayingUpFor > 0) {
			this.mc.mcProfiler.startSection("overlayMessage");
			var7 = (float) this.recordPlayingUpFor - p_175180_1_;
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

				this.func_175179_f().drawString(this.recordPlaying,
						-this.func_175179_f().getStringWidth(this.recordPlaying) / 2, -4,
						var9 + (var8 << 24 & -16777216));
				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}

			this.mc.mcProfiler.endSection();
		}

		if (this.field_175195_w > 0) {
			this.mc.mcProfiler.startSection("titleAndSubtitle");
			var7 = (float) this.field_175195_w - p_175180_1_;
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
				this.func_175179_f().func_175065_a(this.field_175201_x,
						(float) (-this.func_175179_f().getStringWidth(this.field_175201_x) / 2), -10.0F,
						16777215 | var9, true);
				GlStateManager.popMatrix();
				GlStateManager.pushMatrix();
				GlStateManager.scale(2.0F, 2.0F, 2.0F);
				this.func_175179_f().func_175065_a(this.field_175200_y,
						(float) (-this.func_175179_f().getStringWidth(this.field_175200_y) / 2), 5.0F, 16777215 | var9,
						true);
				GlStateManager.popMatrix();
				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}

			this.mc.mcProfiler.endSection();
		}

		Scoreboard var121 = this.mc.theWorld.getScoreboard();
		ScoreObjective var13 = null;
		ScorePlayerTeam var15 = var121.getPlayersTeam(this.mc.thePlayer.getName());

		if (var15 != null) {
			int var16 = var15.func_178775_l().func_175746_b();

			if (var16 >= 0) {
				var13 = var121.getObjectiveInDisplaySlot(3 + var16);
			}
		}

		ScoreObjective var161 = var13 != null ? var13 : var121.getObjectiveInDisplaySlot(1);

		if (var161 != null) {
			if (!NoScoreBoard.enabled) {
				this.func_180475_a(var161, var2);
			}
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
		var161 = var121.getObjectiveInDisplaySlot(0);

		if (this.mc.gameSettings.keyBindPlayerList.getIsKeyPressed() && (!this.mc.isIntegratedServerRunning()
				|| this.mc.thePlayer.sendQueue.func_175106_d().size() > 1 || var161 != null)) {
			this.overlayPlayerList.func_175246_a(true);
			this.overlayPlayerList.func_175249_a(var3, var121, var161);
		} else {
			this.overlayPlayerList.func_175246_a(false);
		}
		//TODO: Radar
		Client.onGui(true);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		
	}

	// Rainbow Array
	// ###############################################################################
	// Rainbow Array
	// ###############################################################################
	// Rainbow Array
	// ###############################################################################
	// Rainbow Array
	// ###############################################################################
	private void renderArrayList() {
		if (ModuleManager.getModuleByName("Arraylist").isEnabled()) {
			if (ModuleManager.getModuleByName("HUD").isEnabled()) {
				int index = 0;
				ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
				sr.getScaledHeight();
				sr.getScaledWidth();
				final FontRenderer fr = this.mc.fontRendererObj;
				int i = 0;
				int yCount = 0;
				long x = 0;
				List<String> display = new ArrayList();
				for (Module m : Client.instance.modulemanager.getModules()) {
					if ((m.isEnabled()) && (m.visible) && (!m.isCategory(Category.GUI))) {
						GlStateManager.scale(1.0F, 1.0F, 1.0F);
						int right = sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getDisplayname());
						GuiIngame.drawRect(
								sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getDisplayname()) - 4, yCount,
								sr.getScaledWidth(), yCount + 10, new Color(25, 25, 25, 155).getRGB());
						mc.fontRendererObj.drawString(m.getDisplayname(), right - 1, yCount + 1,
								ColorUtils.rainbowEffekt(index + x * 200000000L, 1.0F).getRGB());
						yCount += 10;
						index++;
						x++;

					}
				}
				Collections.sort(Client.instance.modulemanager.getModules(), new Comparator<Module>() {

					@Override
					public int compare(Module mod1, Module mod2) {
						if (fr.getStringWidth(mod1.getDisplayname()) > fr.getStringWidth(mod2.getDisplayname())) {
							return -1;
						}
						if (fr.getStringWidth(mod1.getDisplayname()) < fr.getStringWidth(mod2.getDisplayname())) {
							return 1;
						}
						return 0;
					}

				});

				for (String s : display) {
					int x1 = 0;
					int mwidth = sr.getScaledWidth() - fr.getStringWidth(s) - 2;
					int mheight = 10 * i + 2;

					fr.func_175063_a(s, mwidth, mheight, rainbowEffekt(1L, 1.0f).hashCode());
					i++;
					x1++;
				}
				display.clear();
			}
		}
	}

	public static Color rainbowEffekt(long offset, float fade) {
		float hue = (float) (System.nanoTime() + offset) / 1.0E10f % 1.0f;
		long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
		Color c = new Color((int) color);
		return new Color((float) c.getRed() / 255.0f * fade, (float) c.getGreen() / 255.0f * fade,
				(float) c.getBlue() / 255.0f * fade, (float) c.getAlpha() / 255.0f);
	}

	public void renderEverything() {
		if (ModuleManager.getModuleByName("HUD").isEnabled()) {
			int index = 0;
			long x = 0;
			int yCount = 0;
			int y = 25;

			ScaledResolution scaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
			int width = scaled.getScaledWidth();
			int height = scaled.getScaledHeight();
			/*
			 * Hotbar
			 */
			ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
			int right = sr.getScaledWidth();
			int ping = 0;
			int yPos = 100;
			if(ModuleManager.getModuleByName("Minime").isEnabled()) {
			GuiInventory.drawEntityOnScreen(20, yPos, 30, -10.0f, 1.0f, this.mc.thePlayer);
			try {
			ping = mc.getNetHandler().func_175102_a(mc.thePlayer.getUniqueID()).getResponseTime();
        	} catch (Exception var16) {
        		ping = 0;
        	}
			}
			this.drawRect(0, sr.getScaledHeight() - 23, sr.getScaledWidth(), sr.getScaledHeight(), -1610612735);
			
			int i = 8;

			if (mc.thePlayer.inventory.currentItem == 0) {
				drawRect((sr.getScaledWidth() / 2) - 91 + mc.thePlayer.inventory.currentItem * 20,
						sr.getScaledHeight() - 23, (sr.getScaledWidth() / 2) + 91 - 20 * i, sr.getScaledHeight(),
						ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
			} else {
				drawRect((sr.getScaledWidth() / 2) - 91 + mc.thePlayer.inventory.currentItem * 20,
						sr.getScaledHeight() - 23,
						(sr.getScaledWidth() / 2) + 91 - 20 * (8 - mc.thePlayer.inventory.currentItem),
						sr.getScaledHeight(), ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
			}
			
			
			mc.fontRendererObj.drawStringWithShadow("FPS:", 4, sr.getScaledHeight() - 20,
					ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
			mc.fontRendererObj.drawString("" + Minecraft.debugFPS, 28, sr.getScaledHeight() - 20, 0xFFFAFA);
			mc.fontRendererObj.drawStringWithShadow("User:", sr.getScaledWidth() - 120, sr.getScaledHeight() - 10, ColorUtils.rainbowEffekt(index + x * 20000000L, 1.0F).getRGB());
			mc.fontRendererObj.drawString("" + mc.thePlayer.getName(), right - 90, sr.getScaledHeight() - 10, 0xFFFAFA);
			mc.fontRendererObj.drawStringWithShadow("XYZ:", 4, sr.getScaledHeight() - 10, ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
			mc.fontRendererObj.drawString("" + String.valueOf(Math.round(Client.mc.thePlayer.posX)) + ", " + ("" + String.valueOf(Math.round(Client.mc.thePlayer.posY) + ", "))	+ ("" + String.valueOf(Math.round(Client.mc.thePlayer.posZ) + " ")), 28, sr.getScaledHeight() - 10, 0xFFFAFA);
			String var1 = "IP:";
			mc.fontRendererObj.drawStringWithShadow(" " + var1, right - 75, sr.getScaledHeight() - 20, ColorUtils.rainbowEffekt(index + x * 20000000L, 1.0F).getRGB());
			mc.fontRendererObj.drawString(mc.isSingleplayer() ? "NONE" : mc.getCurrentServerData().serverIP.toLowerCase(), right - 55, sr.getScaledHeight() - 20, 0xFFFAFA);
			mc.fontRendererObj.drawStringWithShadow("Ping: ", right - 120, sr.getScaledHeight() - 20, ColorUtils.rainbowEffekt(index + x * 20000000L, 1.0F).getRGB());
			mc.fontRendererObj.drawString("" + ping, right - 95, sr.getScaledHeight() - 20, 0xFFFAFA);			
			String direction = mc.func_175606_aa().func_174811_aO().name();
			String var2 = "Facing: ";
			mc.fontRendererObj.drawStringWithShadow(var2, 50, sr.getScaledHeight() - 20,
					ColorUtils.rainbowEffekt(index + x * 20000000L, 1.0F).getRGB());
			mc.fontRendererObj.drawString(direction, 88, sr.getScaledHeight() - 20, 0xFFFAFA);
			

			if (ModuleManager.getModuleByName("Sprint").isEnabled()
					&& !ModuleManager.getModuleByName("Sneak").isEnabled()) {
				mc.fontRendererObj.drawStringWithShadow("Sprint [TOGGLED]", 65, sr.getScaledHeight() - 100, 0xFF00FF);
			}
			if (ModuleManager.getModuleByName("Sneak").isEnabled()
					&& !ModuleManager.getModuleByName("Sprint").isEnabled()) {
				mc.fontRendererObj.drawStringWithShadow("Sneak [TOGGLED]", 65, sr.getScaledHeight() - 100, 0x00FF00);
			}
			
			/*if (Client.instance.setmgr.getSettingByName("MiniMapIngame").getValBoolean()) {
				map.draw();
			}*/
			

		}
		ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int index = 0;
		long x = 0;
	}
	
	/*public void drawRadarTest(){
		if(tick==55){
            tick=0;
        }else{
            tick++;
        } 
        GL11.glPushMatrix();
        GL11.glTranslatef(this.RadarX, this.RadarY, 0);
        int m = 0x1b00f0f0;
        DrawFullCircle(0,0,55.9,0x2000f0f0);
        for(double g = 0;g < 55.9;g+=15){
            DrawFullCircle(0,0,g,m);
        }
        for(double g = 0;g < 55.9;g+=15){
            DrawCircle(0.5F, 0F,0F,(int)g,360,0xff00f0f0);
        }
        drawBorderedRect(-3, -3, 3,3, 0.5, 0xff00f0f0, 0x00ff00ff);
        DrawCircle(1.5F,0F,0F,(float)55.9,360,0xff00f0f0);
        dr(-55.9, -0.2, -3, 0.3, 0xff00f0f0);
        dr(55.9, -0.2, 3, 0.3, 0xff00f0f0);
        dr(0-0.3, -3, 0.2, -55.9, 0xff00f0f0);
        dr(0-0.3, 3, 0.2, 55.9, 0xff00f0f0);
        for(double g= -54; g < -5;g+=4){
            dr(g, -3, g+0.3, 3, 0xff00f0f0);
        }
        for(double g= 5; g < 54;g+=4){
            dr(g, -3, g+0.3, 3, 0xff00f0f0);
        }
        for(double g= 5; g < 54;g+=4){
            dr(-3, g, 3, g+0.3, 0xff00f0f0);
        }
        for(double g= -54; g < -5;g+=4){
            dr(-3, g, 3, g+0.3, 0xff00f0f0);
        }
        DrawCircle(0.9F, 0F,0F,tick,900,0xff00fff0);
        GL11.glRotatef(-mc.thePlayer.rotationYaw, 0, 0, 1);
        drawRadar(null, mc.fontRendererObj);
        GL11.glPopMatrix();
    }

    public void drawBorderedRect(double x, double y, double x1, double y1, double size, int borderC, int insideC) {
        dr(x + size, y + size, x1 - size, y1 - size, insideC);
        dr(x+size, y+size, x1-0.5, y, borderC);
        dr(x, y+0.5, x+size, y1-0.5, borderC);
        dr(x1, y1-0.5, x1-size, y+size, borderC);
        dr(x+0.5, y1-size, x1-0.5, y1, borderC);
    }
    public void drawRadar(EntityPlayer entityplayer, FontRenderer fontrenderer) {
        List list = mc.theWorld.loadedEntityList;
        for (int i = 0; i < list.size(); i++) {
            Entity entity = (Entity) list.get(i);
            double j =  Math.round(mc.thePlayer.posX);
            double k =  Math.round(mc.thePlayer.posZ);
            double l =  Math.round(entity.posX);
            double i1 =  Math.round(entity.posZ);
            double j1 = j - l;
            double k1 = k - i1;
            if (Math.hypot(j1, k1) >= 110D || entity == mc.thePlayer || !(entity instanceof EntityLiving)) {
                continue;
            }
            GL11.glScalef(0.5F, 0.5F, 0.5F);

            if (entity instanceof EntityAnimal) {
                DrawFullCircle(j1, k1, 1.5, 0x9000ff00);
            }
            if (entity instanceof EntityPlayer) {
                //DrawFullCircle(j1, k1, 3D, f ? 0xff55ff55 : 0xffff5555);
                GL11.glScaled(2, 2, 2);System.exit(0);
                GL11.glTranslated(j1/2, k1/2, 0.0F);
                GL11.glRotatef(180, 0F,0F,1.0F);
                this.drawTriangle(entity, 0, 0, 0xffff5555);
                GL11.glRotatef(-180, 0F,0F,1.0F);
                GL11.glTranslated(-j1/2, -k1/2, 0.0F);
                GL11.glScaled(0.5, 0.5, 0.5);

                int xDif = (int)(mc.thePlayer.posX - ((EntityPlayer) entity).posX);
                int zDif = (int)(mc.thePlayer.posZ - ((EntityPlayer) entity).posZ);
                GL11.glTranslatef(xDif, zDif, 0.0F);
                GL11.glRotatef(mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
                int wid = mc.fontRendererObj.getStringWidth(((EntityPlayer) entity).getName())/2;
                String s = ((EntityPlayer) entity).getName();
                fontrenderer.drawString(s, 0-wid, -12, 0xff);
                GL11.glRotatef(-mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(-xDif, -zDif, 0.0F);
                GL11.glScalef(1F, 1F, 1F);
            }
            if(entity instanceof EntityMob) {
                DrawFullCircle(j1, k1, 1.5D, 0x90ff0000);
            }
            GL11.glScalef(2.0F, 2.0F, 2.0F);
        }
    }

    protected void dr(double i, double j, double k, double l, int i1)
    {
        if(i < k)
        {
            double j1 = i;
            i = k;
            k = j1;
        }
        if(j < l)
        {
            double k1 = j;
            j = l;
            l = k1;
        }
        float f = (float)(i1 >> 24 & 0xff) / 255F;
        float f1 = (float)(i1 >> 16 & 0xff) / 255F;
        float f2 = (float)(i1 >> 8 & 0xff) / 255F;
        float f3 = (float)(i1 & 0xff) / 255F;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f1, f2, f3, f);
        Tessellator var4 = Tessellator.getInstance();
        WorldRenderer var5 = var4.getWorldRenderer();
        glBegin(GL_QUADS);
        var5.addVertex(i, l, 0.0D);
        var5.addVertex(k, l, 0.0D);
        var5.addVertex(k, j, 0.0D);
        var5.addVertex(i, j, 0.0D);
        var5.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    public void DrawCircle(float w, float cx, float cy, float r, int num_segments, int c)
    {
        float f = (float) (c >> 24 & 0xff) / 255F;
        float f1 = (float) (c >> 16 & 0xff) / 255F;
        float f2 = (float) (c >> 8 & 0xff) / 255F;
        float f3 = (float) (c & 0xff) / 255F;
        float theta = (float) (2 * Math.PI / (num_segments));
        float p = (float) Math.cos(theta);//calculate the sine and cosine
        float s = (float) Math.sin(theta);
        float t;
        GL11.glColor4f(f1, f2, f3, f);
        float x = r;
        float y = 0;//start at angle = 0
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(w);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        for(int ii = 0; ii < num_segments; ii++)
        {
            GL11.glVertex2f(x + cx, y + cy);//final vertex vertex

            //rotate the stuff
            t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void DrawFullCircle(double cx, double cy, double r, int c) {
        float f = (float) (c >> 24 & 0xff) / 255F;
        float f1 = (float) (c >> 16 & 0xff) / 255F;
        float f2 = (float) (c >> 8 & 0xff) / 255F;
        float f3 = (float) (c & 0xff) / 255F;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        for (int i = 0; i <= 360; i++) {
            double x = Math.sin((i * Math.PI / 180)) * r;
            double y = Math.cos((i * Math.PI / 180)) * r;
            GL11.glVertex2d(cx + x, cy + y);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    public static void drawTri(int cx, int cy, int c)
    {
        GL11.glRotatef(180, 0F,0F,1.0F);
        float f = (float) (c >> 24 & 0xff) / 255F;
        float f1 = (float) (c >> 16 & 0xff) / 255F;
        float f2 = (float) (c >> 8 & 0xff) / 255F;
        float f3 = (float) (c & 0xff) / 255F;
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glRotatef(180, 0F,0F,1.0F);
        GL11.glVertex2d(cx,cy+2);
        GL11.glVertex2d(cx+2,cy-2);
        GL11.glVertex2d(cx-2,cy-2);

        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glRotatef(-180, 0F,0F,1.0F);
    }
    public static void drawTriangle(Entity e,double cx, double cy, int c)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(cx, cy, 0);
        GL11.glRotatef(-e.rotationYaw, 0F,0F,1.0F);
        float f = (float) (c >> 24 & 0xff) / 255F;
        float f1 = (float) (c >> 16 & 0xff) / 255F;
        float f2 = (float) (c >> 8 & 0xff) / 255F;
        float f3 = (float) (c & 0xff) / 255F;
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(GL11.GL_TRIANGLES);

        GL11.glVertex2d(0,0+6);
        GL11.glVertex2d(0+3,0-2);
        GL11.glVertex2d(0-3,0-2);

        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glRotatef(e.rotationYaw, 0F,0F,1.0F);

        GL11.glPopMatrix();
    }*/
	/*public void renderEntitiesRelative(int x, int y) {
		  double playerX = ((mc.thePlayer.posX + mc.thePlayer.lastTickPosX) / 2); //Average the positions for smoothness
		  double playerZ = ((mc.thePlayer.posZ + mc.thePlayer.lastTickPosZ) / 2);//Same^
		  GL11.glRotatef(-mc.thePlayer.rotationYaw, 0, 0, 1);//Rotate the radar according to the player's yaw
		  for (Object o : mc.theWorld.loadedEntityList) {//loop through the world 
		    if (o instanceof EntityLiving) {//make sure it's a living entity
		    EntityLiving entityLiving = (EntityLiving) o;//create our entityliving object
		    if (entityLiving.ticksExisted > 10) {//check to make sure the entity is not spawning for a split second, this prevents flashing radars in peaceful mode
		    double drawX = playerX - ((entityLiving.posX + entityLiving.lastTickPosX) / 2);//relative x position of the entity to the player
		    double drawZ = playerZ - ((entityLiving.posZ + entityLiving.lastTickPosZ) / 2);//relative y position of the entity to the player
		    if (Math.abs(drawX) < 39 && Math.abs(drawZ) < 39) {//make sure it's not too far
		    int j = 0xff;*///create our base color
		    /*This section will change the color depending on what type of entity it is */
		    /**///if (entityLiving.equals(mc.thePlayer))
		    /**/    //j = 0xff;
		    /**///if (entityLiving instanceof EntityCreature)
		    /**///j = 0xff00ff11;
		    /**///if (entityLiving instanceof EntityMob)
		    /**///j = 0xffffcc00;
		    /**///if (entityLiving instanceof EntityPlayer) {
		    /**/    //EntityPlayer entityPlayer = (EntityPlayer) entityLiving;
		    //}
		    //R2DUtils.drawCircle(drawX, drawZ, 1.2, j);//finally, draw the circle at the calculated position using the calculated color
		    //}
		    //}
		    //}
		  //}
		  //GL11.glRotatef(mc.thePlayer.rotationYaw, 0, 0, 1);//reset the rotation
		    //}
	public void renderName(ScaledResolution sr) {
		if(ModuleManager.getModuleByName("HUD").isEnabled()) {
		int index = 0;
		long x = 0;
		int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        Double scale = 1.8;
        GL11.glPushMatrix();
        GlStateManager.scale(scale, scale, scale);
        mc.fontRendererObj.func_175063_a("Numb", 1.0f, 2.0f, 0x00FF00);
        GlStateManager.scale(1.0 / scale, 1.0 / scale, 1.0 / scale);
        GL11.glPopMatrix();
		}
	}
	 public void drawClientVersion(ScaledResolution sr) {
		 if(ModuleManager.getModuleByName("HUD").isEnabled()) {
	        int width = sr.getScaledWidth();
	        int height = sr.getScaledHeight();
	        Double scale = 0.8;
	        GL11.glPushMatrix();
	        GlStateManager.scale(scale, scale, scale);
	        mc.fontRendererObj.func_175063_a(Client.instance.Client_Version, 60.0f, 2.0f, 16711680);
	        GlStateManager.scale(1.0 / scale, 1.0 / scale, 1.0 / scale);
	        GL11.glPopMatrix();
	    }
	 }

	private void renderStuffStatus(ScaledResolution scaledRes) {
		if (ModuleManager.getModuleByName("ArmorHUD").isEnabled()) {
			if(Client.instance.setmgr.getSettingByName("Hotbar").getValBoolean()){
			int yOffset = Minecraft.getMinecraft().thePlayer.isInsideOfMaterial(Material.water) ? 40 : 0;
			for (int slot = 3, xOffset = 0; slot >= 0; slot--) {
				ItemStack stack = mc.thePlayer.inventory.armorItemInSlot(slot);

				if (stack != null) {
					this.itemRenderer.func_175042_a(stack, scaledRes.getScaledWidth() / 2 + 78 - xOffset,
							scaledRes.getScaledHeight() - 55 - (yOffset / 2));

					GL11.glDisable(GL11.GL_DEPTH_TEST);
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					mc.fontRendererObj.func_175065_a(stack.getMaxDamage() - stack.getItemDamage() + "",
							scaledRes.getScaledWidth() + 160 - xOffset * 2
									+ (stack.getMaxDamage() - stack.getItemDamage() >= 100 ? 4
											: (stack.getMaxDamage() - stack.getItemDamage() <= 100
													&& stack.getMaxDamage() - stack.getItemDamage() >= 10 ? 7 : 11)),
							scaledRes.getScaledHeight() * 2 - 88 - yOffset, 0xFFFFFF, true);
					GL11.glScalef(2F, 2F, 2F);
					GL11.glEnable(GL11.GL_DEPTH_TEST);

					xOffset += 18;
				}
			}
		}
		}
	}
	public void renderPotionStatus(ScaledResolution sr) {
		if (ModuleManager.getModuleByName("StatusHUD").isEnabled() && (Client.instance.setmgr.getSettingByName("Text").getValBoolean())) {
			int pY = 0;
			pY = -23;

			Iterator localIterator = this.mc.thePlayer.getActivePotionEffects().iterator();
			while (localIterator.hasNext()) {
				PotionEffect effect = (PotionEffect) localIterator.next();
				Potion potion = Potion.potionTypes[effect.getPotionID()];
				String PType = I18n.format(potion.getName(), new Object[0]);
				if (effect.getAmplifier() == 1) {
					PType = PType + " II";
				} else if (effect.getAmplifier() == 2) {
					PType = PType + " III";
				} else if (effect.getAmplifier() == 3) {
					PType = PType + " IV";
				} else if (effect.getAmplifier() == 4) {
					PType = PType + " V";
				} else if (effect.getAmplifier() == 5) {
					PType = PType + " VI";
				} else if (effect.getAmplifier() == 6) {
					PType = PType + " VII";
				} else if (effect.getAmplifier() == 7) {
					PType = PType + " VIII";
				} else if (effect.getAmplifier() == 8) {
					PType = PType + " IX";
				} else if (effect.getAmplifier() == 9) {
					PType = PType + " X";
				} else if (effect.getAmplifier() >= 10) {
					PType = PType + " X+";
				} else {
					PType = PType + " I";
				}
				if ((effect.getDuration() < 600) && (effect.getDuration() > 300)) {
					PType = PType + " " + Potion.getDurationString(effect);
				} else if (effect.getDuration() < 300) {
					PType = PType + " " + Potion.getDurationString(effect);
				} else if (effect.getDuration() > 600) {
					PType = PType + " " + Potion.getDurationString(effect);
				}
				this.mc.fontRendererObj.drawStringWithShadow(PType,
						sr.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(PType),
						sr.getScaledHeight() - this.mc.fontRendererObj.FONT_HEIGHT + pY, potion.getLiquidColor());
				pY -= 9;
			}
		}
	}
	
	public void renderKeystrokes() {
			if (ModuleManager.getModuleByName("Keystrokes").isEnabled() && Client.instance.setmgr.getSettingByName("1").getValBoolean()) {
				/*if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
	                RenderUtils.drawBorderRect(0.0f, 500.0f, 1000.0f, 0.0f, -1442840576, -1442840576, 0.0f);
	            }*/
	            RenderUtils.drawBorderRect(108.0f, 1.0f, 90.0f, 16.0f, Keyboard.isKeyDown((int)17) ? Integer.MAX_VALUE : -1442840576, Keyboard.isKeyDown((int)17) ? Integer.MAX_VALUE : -1442840576, 0.0f);
	            mc.fontRendererObj.func_175063_a(Keyboard.isKeyDown((int)17) ? "\u00a7eW" : "W", 96.0f, 5.0f, Keyboard.isKeyDown((int)17) ? -1 : Integer.MAX_VALUE);
	            RenderUtils.drawBorderRect(108.0f, 17.0f, 90.0f, 33.0f, Keyboard.isKeyDown((int)31) ? Integer.MAX_VALUE : -1442840576, Keyboard.isKeyDown((int)31) ? Integer.MAX_VALUE : -1442840576, 0.0f);
	            mc.fontRendererObj.func_175063_a(Keyboard.isKeyDown((int)31) ? "\u00a7eS" : "S", 96.0f, 22.0f, Keyboard.isKeyDown((int)31) ? -1 : Integer.MAX_VALUE);
	            RenderUtils.drawBorderRect(88.0f, 17.0f, 70.0f, 33.0f, Keyboard.isKeyDown((int)30) ? Integer.MAX_VALUE : -1442840576, Keyboard.isKeyDown((int)30) ? Integer.MAX_VALUE : -1442840576, 0.0f);
	            mc.fontRendererObj.func_175063_a(Keyboard.isKeyDown((int)30) ? "\u00a7eA" : "A", 77.0f, 22.0f, Keyboard.isKeyDown((int)30) ? -1 : Integer.MAX_VALUE);
	            RenderUtils.drawBorderRect(128.0f, 17.0f, 110.0f, 33.0f, Keyboard.isKeyDown((int)32) ? Integer.MAX_VALUE : -1442840576, Keyboard.isKeyDown((int)32) ? Integer.MAX_VALUE : -1442840576, 0.0f);
	            mc.fontRendererObj.func_175063_a(Keyboard.isKeyDown((int)32) ? "\u00a7eD" : "D", 116.0f, 22.0f, Keyboard.isKeyDown((int)32) ? -1 : Integer.MAX_VALUE);
	            RenderUtils.drawBorderRect(128.0f, 35.0f, 70.0f, 50.0f, Keyboard.isKeyDown((int)57) ? Integer.MAX_VALUE : -1442840576, Keyboard.isKeyDown((int)57) ? Integer.MAX_VALUE : -1442840576, 0.0f);
	            mc.fontRendererObj.func_175063_a(Keyboard.isKeyDown((int)57) ? "\u00a7ESPACE" : "SPACE", 85.0f, 39.0f, Keyboard.isKeyDown((int)57) ? -1 : Integer.MAX_VALUE);
	            RenderUtils.drawBorderRect(98.0f, 65.0f, 70.0f, 52.0f, mc.gameSettings.keyBindAttack.pressed ? Integer.MAX_VALUE : -1442840576, mc.gameSettings.keyBindAttack.pressed ? Integer.MAX_VALUE : -1442840576, 0.0f);
	            mc.fontRendererObj.func_175063_a(mc.gameSettings.keyBindAttack.pressed ? "\u00a7eLMB" : "LMB", 75.0f, 55.0f, mc.gameSettings.keyBindAttack.pressed ? -1 : Integer.MAX_VALUE);
	            RenderUtils.drawBorderRect(101.0f, 65.0f, 128.0f, 52.0f, mc.gameSettings.keyBindUseItem.pressed ? Integer.MAX_VALUE : -1442840576, mc.gameSettings.keyBindUseItem.pressed ? Integer.MAX_VALUE : -1442840576, 0.0f);
	            mc.fontRendererObj.func_175063_a(mc.gameSettings.keyBindUseItem.pressed ? "\u00a7eRMB" : "RMB", 107.0f, 55.0f, mc.gameSettings.keyBindUseItem.pressed ? -1 : Integer.MAX_VALUE);
	        }
	}
	public void oldKeystrokes(){
		if (ModuleManager.getModuleByName("Keystrokes").isEnabled() && Client.instance.setmgr.getSettingByName("2").getValBoolean()) {
    		ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    	/*W,A,S,D Rendern*/

        /*W*/
        Gui.drawRect(GuiScreen.width - 23, GuiScreen.height - 50, GuiScreen.width - 40, GuiScreen.height - 65,new Color(0,0,0, 0.75F).getRGB());
        drawRectangleBorder(GuiScreen.width - 23, GuiScreen.height - 50, GuiScreen.width - 40, GuiScreen.height - 65, 2.0F,Forward.pressed ?  color : color2);
        mc.fontRendererObj.drawString(Keyboard.getKeyName(Forward.getKeyCode()), GuiScreen.width - 34, GuiScreen.height - 62,Forward.pressed ? color : new Color(255, 255, 255).getRGB());

        /*A*/
        Gui. drawRect(GuiScreen.width - 43, GuiScreen.height - 47, GuiScreen.width - 60, GuiScreen.height - 32,new Color(0,0,0, 0.75F).getRGB());
        drawRectangleBorder(GuiScreen.width - 43, GuiScreen.height - 47, GuiScreen.width - 60, GuiScreen.height - 32, 2.0F,Left.pressed ? color : color2);
        mc.fontRendererObj.drawString(Keyboard.getKeyName(Left.getKeyCode()), GuiScreen.width - 54, GuiScreen.height - 44,Left.pressed ? color : color2);

        /*S*/
        Gui.drawRect(GuiScreen.width - 23, GuiScreen.height - 47, GuiScreen.width - 40, GuiScreen.height - 32, new Color(0,0,0, 0.75F).getRGB());
        drawRectangleBorder(GuiScreen.width - 23, GuiScreen.height - 47, GuiScreen.width - 40, GuiScreen.height - 32, 2.0F,Back.pressed ? color : color2);
        mc.fontRendererObj.drawString(Keyboard.getKeyName(Back.getKeyCode()), GuiScreen.width - 34, GuiScreen.height - 44,Back.pressed ? color : color2);

        /*D*/
        Gui.drawRect(GuiScreen.width - 3, GuiScreen.height - 47, GuiScreen.width - 20, GuiScreen.height - 32, new Color(0,0,0, 0.75F).getRGB());
        drawRectangleBorder(GuiScreen.width - 3, GuiScreen.height - 47, GuiScreen.width - 20, GuiScreen.height - 32, 2.0F, Right.pressed ? color : color2);
        mc.fontRendererObj.drawString(Keyboard.getKeyName(Right.getKeyCode()), GuiScreen.width - 14, GuiScreen.height - 44,Right.pressed ? color : color2);
    }
    }
	public void Keystrokes1(){
		if (ModuleManager.getModuleByName("Keystrokes").isEnabled() && Client.instance.setmgr.getSettingByName("3").getValBoolean()) {
		Gui.drawRect(20, 111, 41, 130, 0x90000000);
		Gui.drawRect(1, 170, 30, 150, 0x90000000);
		Gui.drawRect(30, 170, 61, 150, 0x90000000);
		Gui.drawRect(1, 130, 61, 150, 0x90000000);
		// Gui.drawRect(1, 170, 61, 190, 0x90000000);//Space
		// Gui.drawRect(25, 20, 50, 40, 0x90000000);
		// Gui.drawRect(10, 20, 50, 40, 0x90000000);
		if (mc.gameSettings.keyBindForward.pressed) {
			Gui.drawRect(21, 112, 40, 130, Integer.MAX_VALUE);
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(21, 112, 40, 130, RenderHelper.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindBack.pressed) {
			Gui.drawRect(21, 131, 40, 149, color);
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(21, 131, 40, 149, RenderHelper.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindLeft.pressed) {
			Gui.drawRect(2, 131, 20, 149, color);
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(2, 131, 20, 149, RenderHelper.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindRight.pressed) {
			Gui.drawRect(41, 131, 60, 149, color);
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(41, 131, 60, 149, RenderHelper.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindAttack.pressed) {
			Gui.drawRect(2, 150, 30, 169, color);
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(2, 150, 30, 169, RenderHelper.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindUseItem.pressed) {
			Gui.drawRect(60, 150, 31, 169, color);
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(60, 150, 31, 169, RenderHelper.getRainbow(6000,
			// 64636));
			// }
		}
		// if (mc.gameSettings.keyBindJump.pressed) {
		// Gui.drawRect(2, 170, 60, 189, color); //Space
		// if (hud.rainbow == true) {
		// Gui.drawRect(2, 170, 30, 189, RenderHelper.getRainbow(6000,
		// 64636));
		// }
		// }
		mc.fontRendererObj.drawString("W", 28, 117, 0xffffffff);
		mc.fontRendererObj.drawString("A", 8, 136, 0xffffffff);
		mc.fontRendererObj.drawString("S", 28, 136, 0xffffffff);
		mc.fontRendererObj.drawString("D", 48, 136, 0xffffffff);
		mc.fontRendererObj.drawCenteredString("LMB", 15, 154, 0xffffffff);
		mc.fontRendererObj.drawCenteredString("RMB", 46, 154, 0xffffffff);
		// mc.fontRendererObj.drawCenteredString("Space", 30, 174,
		// 0xffffffff); //Space
	}
	}
	public void Keystrokes2(){
		if (ModuleManager.getModuleByName("Keystrokes").isEnabled() && Client.instance.setmgr.getSettingByName("Rainbow v1").getValBoolean()) {			
			int index = 0;
		RenderUtils.drawRect(20, 111, 41, 130, 0x90000000);
		RenderUtils.drawRect(1, 170, 30, 150, 0x90000000);
		RenderUtils.drawRect(30, 170, 61, 150, 0x90000000);
		RenderUtils.drawRect(1, 130, 61, 150, 0x90000000);
		Gui.drawRect(1, 170, 61, 190, 0x90000000);//Space
		// Gui.drawRect(25, 20, 50, 40, 0x90000000);
		// Gui.drawRect(10, 20, 50, 40, 0x90000000);
		if (mc.gameSettings.keyBindForward.pressed) {
			RenderUtils.drawRect(21, 112, 40, 130, ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(21, 112, 40, 130, RenderUtils.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindBack.pressed) {
			RenderUtils.drawRect(21, 131, 40, 149, ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(21, 131, 40, 149, RenderUtils.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindLeft.pressed) {
			RenderUtils.drawRect(2, 131, 20, 149, ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(2, 131, 20, 149, RenderUtils.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindRight.pressed) {
			RenderUtils.drawRect(41, 131, 60, 149, ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(41, 131, 60, 149, RenderUtils.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindAttack.pressed) {
			RenderUtils.drawRect(2, 150, 30, 169, ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(2, 150, 30, 169, RenderUtils.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindUseItem.pressed) {
			RenderUtils.drawRect(60, 150, 31, 169, ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(60, 150, 31, 169, RenderUtils.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindJump.pressed) {
		Gui.drawRect(2, 170, 60, 189, ColorUtils.rainbowEffekt(index, 1.0F).getRGB()); //Space
		// if (hud.rainbow == true) {
		// Gui.drawRect(2, 170, 30, 189, RenderUtils.getRainbow(6000,
		// 64636));
		// }
		}
		mc.fontRendererObj.drawString("W", 28, 117, 0xffffffff);
		mc.fontRendererObj.drawString("A", 8, 136, 0xffffffff);
		mc.fontRendererObj.drawString("S", 28, 136, 0xffffffff);
		mc.fontRendererObj.drawString("D", 48, 136, 0xffffffff);
		mc.fontRendererObj.drawCenteredString("LMB", 15, 154, 0xffffffff);
		mc.fontRendererObj.drawCenteredString("RMB", 46, 154, 0xffffffff);
		mc.fontRendererObj.drawCenteredString("Space", 30, 174,
		0xffffffff); //Space
	}
	}
	public void Keystrokes3(){
		if (ModuleManager.getModuleByName("Keystrokes").isEnabled() && Client.instance.setmgr.getSettingByName("Rainbow v2").getValBoolean()) {			
			int index = 0;
		RenderUtils.drawRect(20, 111, 41, 130, 0x90000000);
		RenderUtils.drawRect(1, 170, 30, 150, 0x90000000);
		RenderUtils.drawRect(30, 170, 61, 150, 0x90000000);
		RenderUtils.drawRect(1, 130, 61, 150, 0x90000000);
		Gui.drawRect(1, 170, 61, 190, 0x90000000);//Space
		// Gui.drawRect(25, 20, 50, 40, 0x90000000);
		// Gui.drawRect(10, 20, 50, 40, 0x90000000);
		if (mc.gameSettings.keyBindForward.pressed) {
			RenderUtils.drawRect(21, 112, 40, 130, Integer.MAX_VALUE);
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(21, 112, 40, 130, RenderUtils.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindBack.pressed) {
			RenderUtils.drawRect(21, 131, 40, 149, Integer.MAX_VALUE);
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(21, 131, 40, 149, RenderUtils.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindLeft.pressed) {
			RenderUtils.drawRect(2, 131, 20, 149, Integer.MAX_VALUE);
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(2, 131, 20, 149, RenderUtils.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindRight.pressed) {
			RenderUtils.drawRect(41, 131, 60, 149, Integer.MAX_VALUE);
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(41, 131, 60, 149, RenderUtils.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindAttack.pressed) {
			RenderUtils.drawRect(2, 150, 30, 169, Integer.MAX_VALUE);
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(2, 150, 30, 169, RenderUtils.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindUseItem.pressed) {
			RenderUtils.drawRect(60, 150, 31, 169, Integer.MAX_VALUE);
			// if (hud.rainbowhud == true) {
			// Gui.drawRect(60, 150, 31, 169, RenderUtils.getRainbow(6000,
			// 64636));
			// }
		}
		if (mc.gameSettings.keyBindJump.pressed) {
		Gui.drawRect(2, 170, 60, 189, Integer.MAX_VALUE); //Space
		// if (hud.rainbow == true) {
		// Gui.drawRect(2, 170, 30, 189, RenderUtils.getRainbow(6000,
		// 64636));
		// }
		}
		mc.fontRendererObj.drawString("W", 28, 117, ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
		mc.fontRendererObj.drawString("A", 8, 136, ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
		mc.fontRendererObj.drawString("S", 28, 136, ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
		mc.fontRendererObj.drawString("D", 48, 136, ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
		mc.fontRendererObj.drawCenteredString("LMB", 15, 154, ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
		mc.fontRendererObj.drawCenteredString("RMB", 46, 154, ColorUtils.rainbowEffekt(index, 1.0F).getRGB());
		mc.fontRendererObj.drawCenteredString("Space", 30, 174,
				ColorUtils.rainbowEffekt(index, 1.0F).getRGB()); //Space
	}
	}
	public void DirectionHUD(ScaledResolution sr){	
		if (ModuleManager.getModuleByName("DirectionHUD").isEnabled()){
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                displayHUD(mc);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
	}
	//int k = sr.getScaledWidth()/2 - 50/2;
	private static void displayHUD(Minecraft mc)
    {

		if(Client.instance.setmgr.getSettingByName("Top").getValBoolean()){
		int i = MathHelper.floor_double((double)(mc.thePlayer.rotationYaw * 256.0F / 360.0F) + 0.5D) & 255;
        int j = 1;
        int k = 65;
        
        mc.getTextureManager().bindTexture(new ResourceLocation("directionhud/textures/gui/compass.png"));

        if (i < 128)
        {
            drawTexturedModalRect(k, j, i, 0, 65, 12, -100.0F);
        }
        else
        {
            drawTexturedModalRect(k, j, i - 128, 12, 65, 12, -100.0F);
        }
        mc.fontRendererObj.drawString("\u00a7" + "|", k + 32, j + 1, 16777215);
        mc.fontRendererObj.drawString("\u00a7" + "|\u00a7r", k + 32, j + 5, 16777215);
		}

		if (Client.instance.setmgr.getSettingByName("Under").getValBoolean()){
        	int i1 = MathHelper.floor_double((double)(mc.thePlayer.rotationYaw * 256.0F / 360.0F) + 0.5D) & 255;
            int j1 = 20;
            int k1 = 5/2;
            
            mc.getTextureManager().bindTexture(new ResourceLocation("directionhud/textures/gui/compass.png"));
            
        	if (i1 < 128)
            {
                drawTexturedModalRect(k1, j1, i1, 0, 65, 12, -100.0F);
            }
            else
            {
                drawTexturedModalRect(k1, j1, i1 - 128, 12, 65, 12, -100.0F);
            }
        	mc.fontRendererObj.drawString("\u00a7" + "|", k1 + 32, j1 + 1, 16777215);
            mc.fontRendererObj.drawString("\u00a7" + "|\u00a7r", k1 + 32, j1 + 5, 16777215);
    	}

    }


	public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float zLevel)
    {
    		float var7 = 0.00390625F;
    		float var8 = 0.00390625F;
    		Tessellator var9 = Tessellator.getInstance();
    		WorldRenderer var10 = var9.getWorldRenderer();
    		var10.startDrawingQuads();
    		var10.addVertexWithUV((double) (x + 0), (double) (y + height), (double) zLevel,
    				(double) ((float) (textureX + 0) * var7), (double) ((float) (textureY + height) * var8));
    		var10.addVertexWithUV((double) (x + width), (double) (y + height), (double) zLevel,
    				(double) ((float) (textureX + width) * var7), (double) ((float) (textureY + height) * var8));
    		var10.addVertexWithUV((double) (x + width), (double) (y + 0), (double) zLevel,
    				(double) ((float) (textureX + width) * var7), (double) ((float) (textureY + 0) * var8));
    		var10.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) zLevel,
    				(double) ((float) (textureX + 0) * var7), (double) ((float) (textureY + 0) * var8));
    		var9.draw();
    	}
	public static void drawRectangleBorder(double left, double top, double right, double bottom, float borderWidth, int borderColor)
    {
        float alpha = 1.0F;
        float red = (borderColor >> 16 & 0xFF) / 255.0F;
        float green = (borderColor >> 8 & 0xFF) / 255.0F;
        float blue = (borderColor & 0xFF) / 255.0F;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        if (borderWidth == 1.0F) {
            GL11.glEnable(2848);
        }
        GL11.glLineWidth(borderWidth);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(left, top, 0.0D);
        worldRenderer.addVertex(left, bottom, 0.0D);
        worldRenderer.addVertex(right, bottom, 0.0D);
        worldRenderer.addVertex(right, top, 0.0D);
        worldRenderer.addVertex(left, top, 0.0D);
        worldRenderer.addVertex(right, top, 0.0D);
        worldRenderer.addVertex(left, bottom, 0.0D);
        worldRenderer.addVertex(right, bottom, 0.0D);
        tessellator.draw();
        GL11.glLineWidth(2.0F);
        if (borderWidth == 1.0F) {
            GL11.glDisable(2848);
        }
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
	
	


	// Rainbow Array
	// ###############################################################################
	// Rainbow Array
	// ###############################################################################
	// Rainbow Array
	// ###############################################################################
	// Rainbow Array
	// ###############################################################################

	protected void func_180479_a(ScaledResolution var1, float var2)
    {
        if (this.mc.func_175606_aa() instanceof EntityPlayer)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(widgetsTexPath);
            EntityPlayer var3 = (EntityPlayer)this.mc.func_175606_aa();
            int var4 = var1.getScaledWidth() / 2;
            float var5 = this.zLevel;
            this.zLevel = -90.0F;
            this.drawTexturedModalRect(var4 - 91, var1.getScaledHeight() - 22, 0, 0, 182, 22);
            this.drawTexturedModalRect(var4 - 91 - 1 + var3.inventory.currentItem * 20, var1.getScaledHeight() - 22 - 1, 0, 22, 24, 22);
            this.zLevel = var5;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();
            int i;
            int y;

            for (i = 0; i < 9; ++i)
            {
                y = var1.getScaledWidth() / 2 - 90 + i * 20 + 2;
                int var8 = var1.getScaledHeight() - 16 - 3;
                this.func_175184_a(i, y, var8, var2, var3);
            }
            
            if(ModuleManager.getModuleByName("ArmorHUD").isEnabled()){
            	if(Client.instance.setmgr.getSettingByName("Urushibara (Side)").getValBoolean()){
            for (i = 0; i < 4; ++i)
            {
                y = var1.getScaledHeight() - 40 - i * 20;
                this.func_175184_a_armor(i, var1.getScaledWidth() - 20, y - 30, var2, var3);
            }
            }
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }

	public void func_175186_a(ScaledResolution p_175186_1_, int p_175186_2_) {
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

	public void func_175176_b(ScaledResolution p_175176_1_, int p_175176_2_) {
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
				int index = 0;
				long x = 0;
				var91 = ColorUtils.rainbowEffekt(index + x * 20000000L, 1.0F).getRGB();
			}

			String var101 = "" + this.mc.thePlayer.experienceLevel;
			var6 = (p_175176_1_.getScaledWidth() - this.func_175179_f().getStringWidth(var101)) / 2;
			int var7 = p_175176_1_.getScaledHeight() - 31 - 4;
			boolean var8 = false;
			this.func_175179_f().drawString(var101, var6 + 1, var7, 0);
			this.func_175179_f().drawString(var101, var6 - 1, var7, 0);
			this.func_175179_f().drawString(var101, var6, var7 + 1, 0);
			this.func_175179_f().drawString(var101, var6, var7 - 1, 0);
			this.func_175179_f().drawString(var101, var6, var7, var91);
			this.mc.mcProfiler.endSection();
		}
	}
	/*
	 * PopEnchantsTag
	 */
	
	private void disp_enchanted_name(ScaledResolution p_175182_1_)
    {
        ItemStack items = this.highlightingItemStack;

        if (items.hasTagCompound())
        {
            String var2 = "";
            boolean hidden = false;

            if (items.getTagCompound().hasKey("HideFlags", 99))
            {
                hidden = (items.getTagCompound().getInteger("HideFlags") & 1) != 0;
            }

            NBTTagList var15 = items.getEnchantmentTagList();
            int var3;

            if (var15 != null)
            {
                for (var3 = 0; var3 < var15.tagCount(); ++var3)
                {
                    short var4 = var15.getCompoundTagAt(var3).getShort("id");
                    short var5 = var15.getCompoundTagAt(var3).getShort("lvl");

                    if (Enchantment.func_180306_c(var4) != null)
                    {
                        var2 = var2 + (var2 != "" ? " " : "");
                        var2 = var2 + Enchantment.func_180306_c(var4).getTranslatedName(var5);
                    }
                }

                var2 = EnumChatFormatting.AQUA + var2;
            }

            var3 = (p_175182_1_.getScaledWidth() - this.func_175179_f().getStringWidth(var2)) / 2;
            int var9 = p_175182_1_.getScaledHeight() - 59;

            if (!this.mc.playerController.shouldDrawHUD())
            {
                var9 += 14;
            }

            int var10 = (int)((float)this.remainingHighlightTicks * 256.0F / 10.0F);

            if (var10 > 255)
            {
                var10 = 255;
            }

            if (var10 > 0)
            {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                this.func_175179_f().drawString(var2, var3, var9, 16777215 + (var10 << 24));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
    }

	public void func_175182_a(ScaledResolution var1)
    {
        this.mc.mcProfiler.startSection("toolHighlight");

        if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null)
        {
            String var2 = this.highlightingItemStack.getDisplayName();
            boolean ench = this.highlightingItemStack.isItemEnchanted();

            if (ench)
            {
                this.disp_enchanted_name(var1);
            }

            if (this.highlightingItemStack.hasDisplayName())
            {
                var2 = EnumChatFormatting.ITALIC + var2;
            }

            int var3 = (var1.getScaledWidth() - this.func_175179_f().getStringWidth(var2)) / 2;
            int var4 = var1.getScaledHeight() - 59 - (ench ? 14 : 0);

            if (!this.mc.playerController.shouldDrawHUD())
            {
                var4 += 14;
            }

            int var5 = (int)((float)this.remainingHighlightTicks * 256.0F / 10.0F);

            if (var5 > 255)
            {
                var5 = 255;
            }

            if (var5 > 0)
            {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                this.func_175179_f().func_175063_a(var2, (float)var3, (float)var4, 16777215 + (var5 << 24));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }

        this.mc.mcProfiler.endSection();
    }


	public void func_175185_b(ScaledResolution p_175185_1_) {
		this.mc.mcProfiler.startSection("demo");
		String var2 = "";

		if (this.mc.theWorld.getTotalWorldTime() >= 120500L) {
			var2 = I18n.format("demo.demoExpired", new Object[0]);
		} else {
			var2 = I18n.format("demo.remainingTime", new Object[] {
					StringUtils.ticksToElapsedTime((int) (120500L - this.mc.theWorld.getTotalWorldTime())) });
		}

		int var3 = this.func_175179_f().getStringWidth(var2);
		this.func_175179_f().func_175063_a(var2, (float) (p_175185_1_.getScaledWidth() - var3 - 10), 5.0F, 16777215);
		this.mc.mcProfiler.endSection();
	}

	protected boolean func_175183_b() {
		if (this.mc.gameSettings.showDebugInfo && !this.mc.thePlayer.func_175140_cp()
				&& !this.mc.gameSettings.field_178879_v) {
			return false;
		} else if (this.mc.playerController.enableEverythingIsScrewedUpMode()) {
			if (this.mc.pointedEntity != null) {
				return true;
			} else {
				if (this.mc.objectMouseOver != null
						&& this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					BlockPos var1 = this.mc.objectMouseOver.func_178782_a();

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

	public void func_180478_c(ScaledResolution p_180478_1_) {
		this.streamIndicator.render(p_180478_1_.getScaledWidth() - 10, 10);
	}

	private void func_180475_a(ScoreObjective p_180475_1_, ScaledResolution p_180475_2_) {
		Scoreboard var3 = p_180475_1_.getScoreboard();
		Collection var4 = var3.getSortedScores(p_180475_1_);
		ArrayList var5 = Lists.newArrayList(Iterables.filter(var4, new Predicate() {
			private static final String __OBFID = "CL_00001958";

			public boolean func_178903_a(Score p_178903_1_) {
				return p_178903_1_.getPlayerName() != null && !p_178903_1_.getPlayerName().startsWith("#");
			}

			public boolean apply(Object p_apply_1_) {
				return this.func_178903_a((Score) p_apply_1_);
			}
		}));
		ArrayList var21;

		if (var5.size() > 15) {
			var21 = Lists.newArrayList(Iterables.skip(var5, var4.size() - 15));
		} else {
			var21 = var5;
		}

		int var6 = this.func_175179_f().getStringWidth(p_180475_1_.getDisplayName());
		String var10;

		for (Iterator var22 = var21.iterator(); var22
				.hasNext(); var6 = Math.max(var6, this.func_175179_f().getStringWidth(var10))) {
			Score var23 = (Score) var22.next();
			ScorePlayerTeam var24 = var3.getPlayersTeam(var23.getPlayerName());
			var10 = ScorePlayerTeam.formatPlayerName(var24, var23.getPlayerName()) + ": " + EnumChatFormatting.RED
					+ var23.getScorePoints();
		}

		int var221 = var21.size() * this.func_175179_f().FONT_HEIGHT;
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
			int var18 = var231 - var11 * this.func_175179_f().FONT_HEIGHT;
			int var19 = p_180475_2_.getScaledWidth() - var241 + 2;
			drawRect(var25 - 2, var18, var19, var18 + this.func_175179_f().FONT_HEIGHT, 1342177280);
			this.func_175179_f().drawString(var15, var25, var18, 553648127);
			this.func_175179_f().drawString(var16, var19 - this.func_175179_f().getStringWidth(var16), var18,
					553648127);

			if (var11 == var21.size()) {
				String var20 = p_180475_1_.getDisplayName();
				drawRect(var25 - 2, var18 - this.func_175179_f().FONT_HEIGHT - 1, var19, var18 - 1, 1610612736);
				drawRect(var25 - 2, var18 - 1, var19, var18, 1342177280);
				this.func_175179_f().drawString(var20,
						var25 + var6 / 2 - this.func_175179_f().getStringWidth(var20) / 2,
						var18 - this.func_175179_f().FONT_HEIGHT, 553648127);
			}
		}
	}

	private void func_180477_d(ScaledResolution p_180477_1_) {
		if (this.mc.func_175606_aa() instanceof EntityPlayer) {
			EntityPlayer var2 = (EntityPlayer) this.mc.func_175606_aa();
			int var3 = MathHelper.ceiling_float_int(var2.getHealth());
			boolean var4 = this.field_175191_F > (long) this.updateCounter
					&& (this.field_175191_F - (long) this.updateCounter) / 3L % 2L == 1L;

			if (var3 < this.field_175194_C && var2.hurtResistantTime > 0) {
				this.field_175190_E = Minecraft.getSystemTime();
				this.field_175191_F = (long) (this.updateCounter + 20);
			} else if (var3 > this.field_175194_C && var2.hurtResistantTime > 0) {
				this.field_175190_E = Minecraft.getSystemTime();
				this.field_175191_F = (long) (this.updateCounter + 10);
			}

			if (Minecraft.getSystemTime() - this.field_175190_E > 1000L) {
				this.field_175194_C = var3;
				this.field_175189_D = var3;
				this.field_175190_E = Minecraft.getSystemTime();
			}

			this.field_175194_C = var3;
			int var5 = this.field_175189_D;
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
				int index = 0;
				long x = 0;
				bossTextColor = ColorUtils.rainbowEffekt(index + x * 20000000L, 1.0F).getRGB();
			}

			this.func_175179_f().func_175063_a(var8, (float) (var3 / 2 - this.func_175179_f().getStringWidth(var8) / 2),
					(float) (var7 - 10), bossTextColor);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(icons);
		}
	}

	private void func_180476_e(ScaledResolution p_180476_1_) {
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

	private void func_180480_a(float p_180480_1_, ScaledResolution p_180480_2_) {
		if (Config.isVignetteEnabled()) {
			p_180480_1_ = 1.0F - p_180480_1_;
			p_180480_1_ = MathHelper.clamp_float(p_180480_1_, 0.0F, 1.0F);
			WorldBorder var3 = this.mc.theWorld.getWorldBorder();
			float var4 = (float) var3.getClosestDistance(this.mc.thePlayer);
			double var5 = Math.min(var3.func_177749_o() * (double) var3.getWarningTime() * 1000.0D,
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
		TextureAtlasSprite var3 = this.mc.getBlockRendererDispatcher().func_175023_a()
				.func_178122_a(Blocks.portal.getDefaultState());
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

	private void func_175184_a(int p_175184_1_, int p_175184_2_, int p_175184_3_, float p_175184_4_,
			EntityPlayer p_175184_5_) {
		ItemStack var6 = p_175184_5_.inventory.mainInventory[p_175184_1_];

		if (var6 != null) {
			float var7 = (float) var6.animationsToGo - p_175184_4_;

			if (var7 > 0.0F) {
				GlStateManager.pushMatrix();
				float var8 = 1.0F + var7 / 5.0F;
				GlStateManager.translate((float) (p_175184_2_ + 8), (float) (p_175184_3_ + 12), 0.0F);
				GlStateManager.scale(1.0F / var8, (var8 + 1.0F) / 2.0F, 1.0F);
				GlStateManager.translate((float) (-(p_175184_2_ + 8)), (float) (-(p_175184_3_ + 12)), 0.0F);
			}

			this.itemRenderer.func_180450_b(var6, p_175184_2_, p_175184_3_);

			if (var7 > 0.0F) {
				GlStateManager.popMatrix();
			}

			this.itemRenderer.func_175030_a(this.mc.fontRendererObj, var6, p_175184_2_, p_175184_3_);
		}
	}
	/*
	 * PopEnchantsTag
	 */
	private void func_175184_a_armor(int p_175184_1_, int p_175184_2_, int p_175184_3_, float p_175184_4_, EntityPlayer p_175184_5_)
    {
        ItemStack var6 = p_175184_5_.inventory.armorInventory[p_175184_1_];

        if (var6 != null)
        {
            float var7 = (float)var6.animationsToGo - p_175184_4_;

            if (var7 > 0.0F)
            {
                GlStateManager.pushMatrix();
                float var8 = 1.0F + var7 / 5.0F;
                GlStateManager.translate((float)(p_175184_2_ + 8), (float)(p_175184_3_ + 12), 0.0F);
                GlStateManager.scale(1.0F / var8, (var8 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate((float)(-(p_175184_2_ + 8)), (float)(-(p_175184_3_ + 12)), 0.0F);
            }

            this.itemRenderer.func_180450_b(var6, p_175184_2_, p_175184_3_);

            if (var7 > 0.0F)
            {
                GlStateManager.popMatrix();
            }

            this.itemRenderer.func_175030_a(this.mc.fontRendererObj, var6, p_175184_2_, p_175184_3_);
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

	public void func_175178_a(String p_175178_1_, String p_175178_2_, int p_175178_3_, int p_175178_4_,
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

	public void func_175188_a(IChatComponent p_175188_1_, boolean p_175188_2_) {
		this.setRecordPlaying(p_175188_1_.getUnformattedText(), p_175188_2_);
	}

	/**
	 * returns a pointer to the persistant Chat GUI, containing all previous chat
	 * messages and such
	 */
	public GuiNewChat getChatGUI() {
		return this.persistantChatGUI;
	}

	public int getUpdateCounter() {
		return this.updateCounter;
	}

	public FontRenderer func_175179_f() {
		return this.mc.fontRendererObj;
	}

	public GuiSpectator func_175187_g() {
		return this.field_175197_u;
	}

	public GuiPlayerTabOverlay getTabList() {
		return this.overlayPlayerList;
	}
}
