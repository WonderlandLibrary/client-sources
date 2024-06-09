// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.combat;

import intent.AquaDev.aqua.utils.RenderUtil;
import intent.AquaDev.aqua.utils.ColorUtils;
import net.minecraft.client.renderer.GlStateManager;
import java.awt.Color;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.Minecraft;
import intent.AquaDev.aqua.utils.FriendSystem;
import java.util.Iterator;
import net.minecraft.network.Packet;
import events.listeners.EventUpdate;
import events.listeners.EventPreMotion;
import intent.AquaDev.aqua.utils.RotationUtil;
import events.listeners.EventFakePreMotion;
import net.minecraft.client.network.NetworkPlayerInfo;
import events.listeners.EventKillaura;
import intent.AquaDev.aqua.modules.visual.ShaderMultiplier;
import events.listeners.EventRender3D;
import events.listeners.EventSilentMove;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import java.util.Random;
import events.listeners.EventPostRender2D;
import net.minecraft.network.play.server.S02PacketChat;
import events.listeners.EventPacket;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import net.minecraft.entity.EntityLivingBase;
import intent.AquaDev.aqua.utils.TimeUtil;
import intent.AquaDev.aqua.utils.Translate;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import intent.AquaDev.aqua.modules.Module;

public class Killaura extends Module
{
    public static EntityPlayer target;
    public static ArrayList<Entity> bots;
    private final Translate translate;
    private final double scale = 1.0;
    TimeUtil timeUtil;
    TimeUtil timer;
    public double yaw;
    float lostHealthPercentage;
    float lastHealthPercentage;
    private EntityLivingBase lastTarget;
    private float displayHealth;
    private float health;
    
    public Killaura() {
        super("Killaura", Type.Combat, "Killaura", 0, Category.Combat);
        this.translate = new Translate(0.0f, 0.0f);
        this.timeUtil = new TimeUtil();
        this.timer = new TimeUtil();
        this.lostHealthPercentage = 0.0f;
        this.lastHealthPercentage = 0.0f;
    }
    
    @Override
    public void setup() {
        Aqua.setmgr.register(new Setting("Range", this, 6.0, 3.0, 6.0, false));
        Aqua.setmgr.register(new Setting("minCPS", this, 17.0, 1.0, 20.0, false));
        Aqua.setmgr.register(new Setting("maxCPS", this, 19.0, 1.0, 20.0, false));
        Aqua.setmgr.register(new Setting("RotationSpeed", this, 180.0, 1.0, 180.0, false));
        Aqua.setmgr.register(new Setting("InstantRotation", this, true));
        Aqua.setmgr.register(new Setting("Autoblock", this, true));
        Aqua.setmgr.register(new Setting("JumpFix", this, false));
        Aqua.setmgr.register(new Setting("TargetESP", this, true));
        Aqua.setmgr.register(new Setting("MouseSensiFix", this, false));
        Aqua.setmgr.register(new Setting("MouseDelayFix", this, false));
        Aqua.setmgr.register(new Setting("RangeBlock", this, false));
        Aqua.setmgr.register(new Setting("CPSFix", this, false));
        Aqua.setmgr.register(new Setting("Wtap", this, false));
        Aqua.setmgr.register(new Setting("KeepSprint", this, true));
        Aqua.setmgr.register(new Setting("AntiBots", this, true));
        Aqua.setmgr.register(new Setting("RenderPitch", this, false));
        Aqua.setmgr.register(new Setting("ClampPitch", this, false));
        Aqua.setmgr.register(new Setting("Rotations", this, false));
        Aqua.setmgr.register(new Setting("1.9", this, false));
        Aqua.setmgr.register(new Setting("Cubecraft", this, false));
        Aqua.setmgr.register(new Setting("CorrectMovement", this, false));
        Aqua.setmgr.register(new Setting("SilentMoveFix", this, false));
        Aqua.setmgr.register(new Setting("Random", this, false));
        Aqua.setmgr.register(new Setting("LegitAttack", this, false));
        Aqua.setmgr.register(new Setting("ThroughWalls", this, false));
        Aqua.setmgr.register(new Setting("Mode", this, "Watchdog", new String[] { "Watchdog", "Matrix", "Packet" }));
    }
    
    @Override
    public void onEnable() {
        if (Killaura.mc.thePlayer != null) {
            Killaura.mc.thePlayer.sprintReset = false;
        }
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        Killaura.target = null;
        Killaura.bots.clear();
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventPacket) {
            if (Aqua.setmgr.getSetting("KillauraWtap").isState() && Killaura.target != null && Killaura.target.hurtTime == 9) {
                Killaura.mc.thePlayer.sprintReset = true;
            }
            final Packet packet = EventPacket.getPacket();
            if (packet instanceof S02PacketChat) {
                final S02PacketChat s02PacketChat = (S02PacketChat)packet;
                final String cp21 = s02PacketChat.getChatComponent().getUnformattedText();
                if (cp21.contains("Cages opened! FIGHT!")) {
                    Killaura.bots.clear();
                    Antibot.bots.clear();
                }
            }
        }
        if (event instanceof EventPostRender2D && Aqua.setmgr.getSetting("KillauraCPSFix").isState() && !Killaura.mc.getCurrentServerData().serverIP.equalsIgnoreCase("hypixel.net")) {
            final float minCPS = (float)Aqua.setmgr.getSetting("KillauraminCPS").getCurrentNumber();
            final float maxCPS = (float)Aqua.setmgr.getSetting("KillauramaxCPS").getCurrentNumber();
            final float CPS = (float)MathHelper.getRandomDoubleInRange(new Random(), minCPS, maxCPS);
            Killaura.target = this.searchTargets();
            if (Killaura.target != null) {
                this.lastTarget = Killaura.target;
            }
            if ((!Aqua.setmgr.getSetting("Killaura1.9").isState() || Aqua.setmgr.getSetting("KillauraLegitAttack").isState()) && this.timeUtil.hasReached((long)(1000.0f / CPS))) {
                if (Killaura.target != null) {
                    if (Aqua.setmgr.getSetting("KillauraLegitAttack").isState()) {
                        Killaura.mc.clickMouse();
                    }
                    else {
                        Killaura.mc.thePlayer.swingItem();
                        Killaura.mc.playerController.attackEntity(Killaura.mc.thePlayer, Killaura.target);
                    }
                }
                this.timeUtil.reset();
            }
            if (Aqua.setmgr.getSetting("KillauraCubecraft").isState()) {
                if (Aqua.setmgr.getSetting("Killaura1.9").isState() && Killaura.mc.thePlayer.getHeldItem() != null && Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                    if (Killaura.mc.thePlayer.ticksExisted % 12 == 0) {
                        if (Killaura.target != null) {
                            Killaura.mc.playerController.attackEntity(Killaura.mc.thePlayer, Killaura.target);
                            Killaura.mc.thePlayer.swingItem();
                        }
                        this.timeUtil.reset();
                    }
                }
                else if (Aqua.setmgr.getSetting("Killaura1.9").isState() && Killaura.mc.thePlayer.getHeldItem() != null && Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemAxe) {
                    if (Killaura.mc.thePlayer.ticksExisted % 22 == 0) {
                        if (Killaura.target != null) {
                            Killaura.mc.playerController.attackEntity(Killaura.mc.thePlayer, Killaura.target);
                            Killaura.mc.thePlayer.swingItem();
                        }
                        this.timeUtil.reset();
                    }
                }
                else if (Killaura.mc.thePlayer.ticksExisted % 12 == 0) {
                    if (Killaura.target != null) {
                        Killaura.mc.playerController.attackEntity(Killaura.mc.thePlayer, Killaura.target);
                        Killaura.mc.thePlayer.swingItem();
                    }
                    this.timeUtil.reset();
                }
            }
        }
        if (event instanceof EventSilentMove && Aqua.setmgr.getSetting("KillauraSilentMoveFix").isState() && Killaura.target != null && Aqua.setmgr.getSetting("KillauraCorrectMovement").isState()) {
            ((EventSilentMove)event).setSilent(true);
        }
        if (event instanceof EventRender3D && Killaura.target != null && Aqua.setmgr.getSetting("KillauraTargetESP").isState()) {
            if (Aqua.moduleManager.getModuleByName("ShaderMultiplier").isToggled()) {
                ShaderMultiplier.drawGlowESP(this::drawTargetESP, false);
            }
            this.drawTargetESP();
        }
        if (event instanceof EventKillaura) {
            if (Killaura.target != null) {
                Killaura.mc.gameSettings.keyBindAttack.pressed = false;
            }
            if (Aqua.setmgr.getSetting("KillauraAntiBots").isState()) {
                if (Killaura.mc.getCurrentServerData().serverIP.equalsIgnoreCase("jartexnetwork.com") || Killaura.mc.getCurrentServerData().serverIP.equalsIgnoreCase("pika.host") || Killaura.mc.getCurrentServerData().serverIP.equalsIgnoreCase("mc.hydracraft.es") || Killaura.mc.getCurrentServerData().serverIP.equalsIgnoreCase("gamster.org") || Killaura.mc.getCurrentServerData().serverIP.equalsIgnoreCase("play.pika.network.com") || Killaura.mc.getCurrentServerData().serverIP.equalsIgnoreCase("jartex.fun")) {
                    if (Killaura.mc.thePlayer.ticksExisted > 110) {
                        for (final Entity entity : Killaura.mc.theWorld.loadedEntityList) {
                            if (entity instanceof EntityPlayer && entity != Killaura.mc.thePlayer && entity.getCustomNameTag() == "" && !Killaura.bots.contains(entity)) {
                                Killaura.bots.add(entity);
                            }
                        }
                    }
                    else {
                        Killaura.bots = new ArrayList<Entity>();
                    }
                }
                for (final Entity entity : Killaura.mc.theWorld.getLoadedEntityList()) {
                    if (entity instanceof EntityPlayer && (this.isBot((EntityPlayer)entity) || entity.isInvisible()) && entity != Killaura.mc.thePlayer) {
                        Killaura.bots.add(entity);
                        if (!Killaura.mc.getCurrentServerData().serverIP.equalsIgnoreCase("hypixel.net")) {
                            continue;
                        }
                        for (final NetworkPlayerInfo playerInfo : Killaura.mc.thePlayer.sendQueue.getPlayerInfoMap()) {
                            if (playerInfo.getGameProfile().getId().equals(entity.getUniqueID()) && playerInfo.getResponseTime() != 1) {
                                Killaura.bots.add(entity);
                            }
                        }
                        Killaura.mc.theWorld.removeEntity(entity);
                    }
                }
            }
            if (Aqua.setmgr.getSetting("KillauraAutoblock").isState()) {
                if (!Aqua.setmgr.getSetting("KillauraMode").getCurrentMode().equalsIgnoreCase("Packet")) {
                    if (Killaura.mc.thePlayer.isSwingInProgress && Killaura.mc.thePlayer.getHeldItem() != null && Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && Killaura.target != null) {
                        Killaura.mc.gameSettings.keyBindUseItem.pressed = true;
                    }
                }
            }
        }
        if (event instanceof EventFakePreMotion) {
            final float[] rota = RotationUtil.Intavee(Killaura.mc.thePlayer, Killaura.target);
            if (!Aqua.setmgr.getSetting("KillauraRotations").isState()) {
                ((EventFakePreMotion)event).setPitch(RotationUtil.pitch);
                ((EventFakePreMotion)event).setYaw(RotationUtil.yaw);
                RotationUtil.setYaw(rota[0], 180.0f);
                RotationUtil.setPitch(rota[1], 8.0f);
            }
        }
        if (event instanceof EventPreMotion) {
            if (!Aqua.setmgr.getSetting("KillauraAutoblock").isState() || !Aqua.setmgr.getSetting("KillauraMode").getCurrentMode().equalsIgnoreCase("Packet") || Killaura.target == null || Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {}
            if (!Aqua.moduleManager.getModuleByName("Scaffold").isToggled()) {
                if (Aqua.setmgr.getSetting("KillauraAutoblock").isState()) {
                    if (Aqua.setmgr.getSetting("KillauraMode").getCurrentMode().equalsIgnoreCase("Watchdog") && Killaura.mc.thePlayer.getHeldItem() != null && Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && Killaura.mc.thePlayer.ticksExisted % 2 == 0) {
                        Killaura.mc.gameSettings.keyBindUseItem.pressed = false;
                    }
                    if (Aqua.setmgr.getSetting("KillauraMode").getCurrentMode().equalsIgnoreCase("Matrix") && Killaura.mc.thePlayer.getHeldItem() != null && Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && Killaura.target == null) {
                        Killaura.mc.gameSettings.keyBindUseItem.pressed = false;
                    }
                }
                final float[] rota = RotationUtil.Intavee(Killaura.mc.thePlayer, Killaura.target);
                if (Aqua.setmgr.getSetting("KillauraRotations").isState()) {
                    if (Aqua.setmgr.getSetting("KillauraInstantRotation").isState()) {
                        ((EventPreMotion)event).setPitch(RotationUtil.pitch);
                        ((EventPreMotion)event).setYaw(RotationUtil.yaw);
                        RotationUtil.setYaw(rota[0], 180.0f);
                        RotationUtil.setPitch(rota[1], 180.0f);
                    }
                    else {
                        ((EventPreMotion)event).setPitch(RotationUtil.pitch);
                        ((EventPreMotion)event).setYaw(RotationUtil.yaw);
                        RotationUtil.setYaw(rota[0], (float)Aqua.setmgr.getSetting("KillauraRotationSpeed").getCurrentNumber());
                        RotationUtil.setPitch(rota[1], (float)Aqua.setmgr.getSetting("KillauraRotationSpeed").getCurrentNumber());
                    }
                }
            }
        }
        if (event instanceof EventUpdate) {
            if (Aqua.setmgr.getSetting("KillauraRangeBlock").isState()) {
                if (Killaura.target != null) {
                    if (this.timer.hasReached(700L)) {
                        Killaura.mc.gameSettings.keyBindUseItem.pressed = true;
                        this.timer.reset();
                    }
                    else {
                        Killaura.mc.gameSettings.keyBindUseItem.pressed = false;
                    }
                }
                else {
                    Killaura.mc.gameSettings.keyBindUseItem.pressed = false;
                }
            }
            if (!Aqua.setmgr.getSetting("KillauraCPSFix").isState()) {
                final float minCPS = (float)Aqua.setmgr.getSetting("KillauraminCPS").getCurrentNumber();
                final float maxCPS = (float)Aqua.setmgr.getSetting("KillauramaxCPS").getCurrentNumber();
                final float CPS = (float)MathHelper.getRandomDoubleInRange(new Random(), minCPS, maxCPS);
                Killaura.target = this.searchTargets();
                if (Killaura.target != null) {
                    this.lastTarget = Killaura.target;
                }
                if ((!Aqua.setmgr.getSetting("Killaura1.9").isState() || Aqua.setmgr.getSetting("KillauraLegitAttack").isState()) && this.timeUtil.hasReached((long)(1000.0f / CPS))) {
                    if (Killaura.target != null) {
                        if (Aqua.setmgr.getSetting("KillauraLegitAttack").isState()) {
                            Killaura.mc.clickMouse();
                        }
                        else {
                            Killaura.mc.thePlayer.swingItem();
                            Killaura.mc.playerController.attackEntity(Killaura.mc.thePlayer, Killaura.target);
                        }
                    }
                    this.timeUtil.reset();
                }
            }
            if (Aqua.setmgr.getSetting("KillauraCubecraft").isState()) {
                if (Aqua.setmgr.getSetting("Killaura1.9").isState() && Killaura.mc.thePlayer.getHeldItem() != null && Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                    if (Killaura.mc.thePlayer.ticksExisted % 12 == 0) {
                        if (Killaura.target != null) {
                            Killaura.mc.playerController.attackEntity(Killaura.mc.thePlayer, Killaura.target);
                            Killaura.mc.thePlayer.swingItem();
                        }
                        this.timeUtil.reset();
                    }
                }
                else if (Aqua.setmgr.getSetting("Killaura1.9").isState() && Killaura.mc.thePlayer.getHeldItem() != null && Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemAxe) {
                    if (Killaura.mc.thePlayer.ticksExisted % 22 == 0) {
                        if (Killaura.target != null) {
                            Killaura.mc.playerController.attackEntity(Killaura.mc.thePlayer, Killaura.target);
                            Killaura.mc.thePlayer.swingItem();
                        }
                        this.timeUtil.reset();
                    }
                }
                else if (Killaura.mc.thePlayer.ticksExisted % 12 == 0) {
                    if (Killaura.target != null) {
                        Killaura.mc.playerController.attackEntity(Killaura.mc.thePlayer, Killaura.target);
                        Killaura.mc.thePlayer.swingItem();
                    }
                    this.timeUtil.reset();
                }
            }
        }
    }
    
    public EntityPlayer searchTargets() {
        final float range = (float)Aqua.setmgr.getSetting("KillauraRange").getCurrentNumber();
        EntityPlayer player = null;
        double closestDist = 100000.0;
        for (final Entity o : Killaura.mc.theWorld.loadedEntityList) {
            if (!o.getName().equals(Killaura.mc.thePlayer.getName()) && o instanceof EntityPlayer && !FriendSystem.isFriend(o.getName()) && !Antibot.bots.contains(o) && !Killaura.bots.contains(o) && !Killaura.mc.session.getUsername().equalsIgnoreCase("Administradora") && Killaura.mc.thePlayer.getDistanceToEntity(o) < range) {
                final double dist = Killaura.mc.thePlayer.getDistanceToEntity(o);
                if (dist >= closestDist) {
                    continue;
                }
                closestDist = dist;
                player = (EntityPlayer)o;
            }
        }
        return player;
    }
    
    boolean isBot(final EntityPlayer player) {
        return !this.isInTablist(player) || this.invalidName(player);
    }
    
    boolean isInTablist(final EntityPlayer player) {
        if (Minecraft.getMinecraft().isSingleplayer()) {
            return false;
        }
        for (final NetworkPlayerInfo playerInfo : Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()) {
            if (playerInfo.getGameProfile().getName().equalsIgnoreCase(player.getName())) {
                return true;
            }
        }
        return false;
    }
    
    boolean invalidName(final Entity e) {
        return e.getName().contains("-") || e.getName().contains("/") || e.getName().contains("|") || e.getName().contains("<") || e.getName().contains(">") || e.getName().contains("\u0e22\u0e07");
    }
    
    public static void renderPlayerModelTexture(final double x, final double y, final float u, final float v, final int uWidth, final int vHeight, final int width, final int height, final float tileWidth, final float tileHeight, final AbstractClientPlayer target) {
        final ResourceLocation skin = target.getLocationSkin();
        Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
        GL11.glEnable(3042);
        Gui.drawScaledCustomSizeModalRect((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
        GL11.glDisable(3042);
    }
    
    public void drawTargetESP() {
        final double x = Killaura.target.lastTickPosX + (Killaura.target.posX - Killaura.target.lastTickPosX) * Killaura.mc.timer.renderPartialTicks - Killaura.mc.getRenderManager().getRenderPosX();
        final double y = Killaura.target.lastTickPosY + (Killaura.target.posY - Killaura.target.lastTickPosY) * Killaura.mc.timer.renderPartialTicks - Killaura.mc.getRenderManager().getRenderPosY();
        final double z = Killaura.target.lastTickPosZ + (Killaura.target.posZ - Killaura.target.lastTickPosZ) * Killaura.mc.timer.renderPartialTicks - Killaura.mc.getRenderManager().getRenderPosZ();
        final int rgb = new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.enableAlpha();
        GL11.glLineWidth(3.0f);
        GL11.glShadeModel(7425);
        GL11.glDisable(2884);
        final double size = Killaura.target.width * 1.2;
        final float factor = (float)Math.sin(System.nanoTime() / 3.0E8f);
        GL11.glTranslatef(0.0f, factor, 0.0f);
        GL11.glBegin(5);
        for (int j = 0; j < 361; ++j) {
            RenderUtil.color(ColorUtils.getColorAlpha(rgb, 160));
            final double x2 = x + Math.cos(Math.toRadians(j)) * size;
            final double z2 = z - Math.sin(Math.toRadians(j)) * size;
            GL11.glVertex3d(x2, y + 1.0, z2);
            RenderUtil.color(ColorUtils.getColorAlpha(rgb, 0));
            GL11.glVertex3d(x2, y + 1.0 + factor * 0.4f, z2);
        }
        GL11.glEnd();
        GL11.glBegin(2);
        for (int j = 0; j < 361; ++j) {
            RenderUtil.color(ColorUtils.getColorAlpha(rgb, 50));
            GL11.glVertex3d(x + Math.cos(Math.toRadians(j)) * size, y + 1.0, z - Math.sin(Math.toRadians(j)) * size);
        }
        GL11.glEnd();
        GlStateManager.enableAlpha();
        GL11.glShadeModel(7424);
        GL11.glDisable(2848);
        GL11.glEnable(2884);
        GlStateManager.enableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
        GlStateManager.popMatrix();
    }
    
    static {
        Killaura.target = null;
        Killaura.bots = new ArrayList<Entity>();
    }
}
