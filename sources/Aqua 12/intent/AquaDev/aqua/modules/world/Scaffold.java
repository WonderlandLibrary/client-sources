// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.world;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import intent.AquaDev.aqua.utils.RotationUtil;
import events.listeners.EventPreMotion;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import intent.AquaDev.aqua.modules.movement.Fly;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import intent.AquaDev.aqua.utils.RandomUtil;
import intent.AquaDev.aqua.modules.combat.Killaura;
import net.minecraft.potion.Potion;
import events.EventType;
import events.listeners.EventUpdate;
import events.listeners.EventSycItem;
import events.listeners.EventPostRender2D;
import intent.AquaDev.aqua.modules.visual.Shadow;
import intent.AquaDev.aqua.modules.visual.ShaderMultiplier;
import intent.AquaDev.aqua.gui.novolineOld.themesScreen.ThemeScreen;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import events.listeners.EventRender2D;
import events.listeners.EventTick;
import intent.AquaDev.aqua.modules.visual.Blur;
import intent.AquaDev.aqua.utils.RenderUtil;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import events.listeners.EventRenderNameTags;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.TimeUtil;
import net.minecraft.util.Vec3;
import java.util.ArrayList;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.BlockPos;
import intent.AquaDev.aqua.modules.Module;

public class Scaffold extends Module
{
    private BlockPos espPos;
    private final BlackList blackList;
    public float[] rots;
    public float[] lastRots;
    public int slot;
    public MovingObjectPosition objectPosition;
    private final ArrayList<Vec3> lastPositions;
    private double[] xyz;
    public static BlockData data;
    TimeUtil timeUtil;
    private double posY;
    public boolean down;
    private int offGround;
    
    public Scaffold() {
        super("Scaffold", Type.World, "Scaffold", 0, Category.World);
        this.espPos = null;
        this.blackList = new BlackList();
        this.rots = new float[2];
        this.lastRots = new float[2];
        this.objectPosition = null;
        this.lastPositions = new ArrayList<Vec3>();
        this.xyz = new double[3];
        this.timeUtil = new TimeUtil();
        this.offGround = 0;
        Aqua.setmgr.register(new Setting("Sprint", this, false));
        Aqua.setmgr.register(new Setting("EnableCalculate", this, false));
        Aqua.setmgr.register(new Setting("EnableSneak", this, false));
        Aqua.setmgr.register(new Setting("BlockCount", this, true));
        Aqua.setmgr.register(new Setting("BlockESP", this, true));
        Aqua.setmgr.register(new Setting("Intave", this, false));
        Aqua.setmgr.register(new Setting("Swing", this, false));
        Aqua.setmgr.register(new Setting("Down", this, false));
        Aqua.setmgr.register(new Setting("SameY", this, false));
        Aqua.setmgr.register(new Setting("Expand", this, false));
        Aqua.setmgr.register(new Setting("AutoDisable", this, false));
        Aqua.setmgr.register(new Setting("BMCBoost", this, false));
        Aqua.setmgr.register(new Setting("LegitPlace", this, false));
        Aqua.setmgr.register(new Setting("ReverseYaw", this, false));
        Aqua.setmgr.register(new Setting("SneakModify", this, 1.0, 0.3, 1.0, false));
        Aqua.setmgr.register(new Setting("Expandlength", this, 8.0, 0.0, 25.0, false));
        Aqua.setmgr.register(new Setting("YawPosition", this, 90.0, 0.0, 380.0, false));
        Aqua.setmgr.register(new Setting("RotationMode", this, "Static", new String[] { "Static", "Calculated" }));
        Aqua.setmgr.register(new Setting("Shader", this, "Glow", new String[] { "Glow", "Shadow", "Jello" }));
        Aqua.setmgr.register(new Setting("Tower", this, "None", new String[] { "None", "Watchdog", "VerusFast", "IntaveFast", "Cubecraft", "UpdatedNCP", "WatchdogNew" }));
    }
    
    @Override
    public void onEnable() {
        if (Aqua.setmgr.getSetting("ScaffoldEnableCalculate").isState()) {
            Scaffold.mc.thePlayer.rotationPitchHead = 82.0f;
            Scaffold.mc.thePlayer.rotationYawHead = 180.0f;
        }
        this.offGround = 0;
        if (Aqua.setmgr.getSetting("ScaffoldEnableSneak").isState()) {
            Scaffold.mc.gameSettings.keyBindSneak.pressed = true;
        }
        this.posY = Scaffold.mc.thePlayer.posY;
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        Scaffold.mc.timer.timerSpeed = 1.0f;
        Scaffold.mc.gameSettings.keyBindSneak.pressed = false;
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventRenderNameTags && Aqua.setmgr.getSetting("ScaffoldBlockESP").isState()) {
            final BlockPos pos = new BlockPos(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY - 1.0, Scaffold.mc.thePlayer.posZ);
            this.espPos = (Scaffold.mc.theWorld.getBlockState(pos).getBlock().isFullBlock() ? pos : this.espPos);
            if (this.espPos != null) {
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(2848);
                GL11.glDisable(2929);
                GL11.glDisable(3553);
                GlStateManager.disableCull();
                GL11.glDepthMask(false);
                final Color color = new Color(Aqua.setmgr.getSetting("HUDColor").getColor());
                final Color color2;
                Blur.drawBlurred(() -> RenderUtil.drawBlockESP(this.espPos, color2.getRed() / 255.0f, color2.getGreen() / 255.0f, color2.getBlue() / 255.0f, 1.0f, 0.0f, 1.0f), false);
                RenderUtil.drawBlockESP(this.espPos, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.39215687f, 0.0f, 1.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDepthMask(true);
                GlStateManager.enableCull();
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDisable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDisable(2848);
            }
        }
        if (event instanceof EventTick) {
            if (Scaffold.mc.thePlayer.onGround) {
                this.offGround = 0;
            }
            else {
                ++this.offGround;
            }
        }
        if (event instanceof EventRender2D && Aqua.setmgr.getSetting("ScaffoldBlockCount").isState()) {
            final ScaledResolution sr = new ScaledResolution(Scaffold.mc);
            if (!GuiNewChat.animatedChatOpen) {
                if (Aqua.setmgr.getSetting("ScaffoldShader").getCurrentMode().equalsIgnoreCase("Jello") && !ThemeScreen.themeHero) {
                    final ScaledResolution scaledResolution;
                    ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect2Alpha(scaledResolution.getScaledWidth() / 2.0f - 18.0f, scaledResolution.getScaledHeight() - 85, Aqua.INSTANCE.novolineSmall.getStringWidth(getBlockCount() + "     Blocks"), 15.0, 3.0, new Color(5, 5, 5, 255)), false);
                    final ScaledResolution scaledResolution2;
                    ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawTriangleFilled2(scaledResolution2.getScaledWidth() / 2.0f - 4.0f, (float)(scaledResolution2.getScaledHeight() - 70), 5.0f, 5.0f, new Color(5, 5, 5, 255).getRGB()), false);
                    RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() / 2.0f - 18.0f, sr.getScaledHeight() - 85, Aqua.INSTANCE.novolineSmall.getStringWidth(getBlockCount() + "     Blocks"), 15.0, 3.0, new Color(40, 40, 40, 255));
                    RenderUtil.drawTriangleFilled2(sr.getScaledWidth() / 2.0f - 4.0f, (float)(sr.getScaledHeight() - 70), 5.0f, 5.0f, new Color(40, 40, 40, 255).getRGB());
                    Aqua.INSTANCE.novolineSmall.drawString(getBlockCount() + "", sr.getScaledWidth() / 2.0f - 16.0f, sr.getScaledHeight() - 81.0f, -1);
                    Aqua.INSTANCE.novolineSmall.drawString("Blocks", Aqua.INSTANCE.novolineSmall.getStringWidth(getBlockCount() + "") + sr.getScaledWidth() / 2.0f - 12.0f, (float)(sr.getScaledHeight() - 81), Color.gray.getRGB());
                }
            }
            else if (Aqua.setmgr.getSetting("ScaffoldShader").getCurrentMode().equalsIgnoreCase("Jello") && !ThemeScreen.themeHero) {
                final ScaledResolution scaledResolution3;
                Shadow.drawGlow(() -> RenderUtil.drawRoundedRect2Alpha(scaledResolution3.getScaledWidth() / 2.0f - 18.0f, scaledResolution3.getScaledHeight() - 80, Aqua.INSTANCE.novolineSmall.getStringWidth(getBlockCount() + "     Blocks"), 15.0, 3.0, new Color(40, 40, 40, 255)), false);
                final ScaledResolution scaledResolution4;
                Shadow.drawGlow(() -> RenderUtil.drawTriangleFilled2(scaledResolution4.getScaledWidth() / 2.0f - 4.0f, (float)(scaledResolution4.getScaledHeight() - 65), 5.0f, 5.0f, new Color(40, 40, 40, 255).getRGB()), false);
                RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() / 2.0f - 18.0f, sr.getScaledHeight() - 80, Aqua.INSTANCE.novolineSmall.getStringWidth(getBlockCount() + "     Blocks"), 15.0, 3.0, new Color(40, 40, 40, 255));
                RenderUtil.drawTriangleFilled2(sr.getScaledWidth() / 2.0f - 4.0f, (float)(sr.getScaledHeight() - 65), 5.0f, 5.0f, new Color(40, 40, 40, 255).getRGB());
                Aqua.INSTANCE.novolineSmall.drawString(getBlockCount() + "", sr.getScaledWidth() / 2.0f - 16.0f, sr.getScaledHeight() - 76.5f, -1);
                Aqua.INSTANCE.novolineSmall.drawString("Blocks", Aqua.INSTANCE.novolineSmall.getStringWidth(getBlockCount() + "") + sr.getScaledWidth() / 2.0f - 12.0f, sr.getScaledHeight() - 76.5f, Color.gray.getRGB());
            }
        }
        if (event instanceof EventRender2D && Aqua.setmgr.getSetting("ScaffoldBlockCount").isState()) {
            final ScaledResolution sr = new ScaledResolution(Scaffold.mc);
            if (ThemeScreen.themeLoaded && ThemeScreen.themeHero) {
                RenderUtil.drawRoundedRect2Alpha((int)(sr.getScaledWidth() / 2.0f + 10.0f), (int)(sr.getScaledHeight() / 2.0f - 5.0f), Aqua.INSTANCE.roboto2.getStringWidth(" " + getBlockCount()) + 1, 13.0, 0.0, new Color(0, 0, 0, 255));
                RenderUtil.drawRoundedRect2Alpha((int)(sr.getScaledWidth() / 2.0f + 10.0f), (int)(sr.getScaledHeight() / 2.0f - 6.0f), Aqua.INSTANCE.roboto2.getStringWidth(" " + getBlockCount()) + 1, 1.0, 0.0, new Color(6, 226, 70, 160));
                Aqua.INSTANCE.roboto2.drawString(getBlockCount() + "", (float)(int)(sr.getScaledWidth() / 2.0f + 11.0f), (float)(int)(sr.getScaledHeight() / 2.0f - 6.0f), -1);
            }
            else if (!Aqua.setmgr.getSetting("ScaffoldShader").getCurrentMode().equalsIgnoreCase("Jello") && Aqua.setmgr.getSetting("ScaffoldShader").getCurrentMode().equalsIgnoreCase("Shadow")) {
                if (!Aqua.setmgr.getSetting("ScaffoldShader").getCurrentMode().equalsIgnoreCase("Jello")) {
                    final ScaledResolution scaledResolution5;
                    Shadow.drawGlow(() -> RenderUtil.drawRoundedRect2Alpha((int)(scaledResolution5.getScaledWidth() / 2.0f - 25.0f), (int)(scaledResolution5.getScaledHeight() / 2.0f + 20.0f), Scaffold.mc.fontRendererObj.getStringWidth("Blocks: " + getBlockCount()) + 5, 13.0, 5.0, Color.BLACK), false);
                }
                else {
                    final ScaledResolution scaledResolution6;
                    ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect2Alpha((int)(scaledResolution6.getScaledWidth() / 2.0f - 25.0f), (int)(scaledResolution6.getScaledHeight() / 2.0f + 20.0f), Scaffold.mc.fontRendererObj.getStringWidth("Blocks: " + getBlockCount()) + 5, 13.0, 5.0, new Color(Aqua.setmgr.getSetting("HUDColor").getColor())), false);
                }
                final ScaledResolution scaledResolution7;
                Blur.drawBlurred(() -> RenderUtil.drawRoundedRect((int)(scaledResolution7.getScaledWidth() / 2.0f - 25.0f), (int)(scaledResolution7.getScaledHeight() / 2.0f + 20.0f), Scaffold.mc.fontRendererObj.getStringWidth("Blocks: " + getBlockCount()) + 5, 13.0, 5.0, new Color(0, 0, 0, 120).getRGB()), false);
            }
        }
        if (event instanceof EventPostRender2D) {
            final ScaledResolution sr = new ScaledResolution(Scaffold.mc);
            if (Aqua.setmgr.getSetting("ScaffoldBlockCount").isState() && !Aqua.setmgr.getSetting("ScaffoldShader").getCurrentMode().equalsIgnoreCase("Jello")) {
                if (!ThemeScreen.themeLoaded || !ThemeScreen.themeHero) {
                    if (Aqua.setmgr.getSetting("ScaffoldShader").getCurrentMode().equalsIgnoreCase("Shadow") || Aqua.setmgr.getSetting("ScaffoldShader").getCurrentMode().equalsIgnoreCase("Glow")) {
                        RenderUtil.drawRoundedRect2Alpha((int)(sr.getScaledWidth() / 2.0f - 25.0f), (int)(sr.getScaledHeight() / 2.0f + 20.0f), Scaffold.mc.fontRendererObj.getStringWidth("Blocks: " + getBlockCount()) + 5, 13.0, 5.0, new Color(0, 0, 0, 70));
                        Aqua.INSTANCE.comfortaa4.drawString("Blocks : " + getBlockCount(), (float)(int)(sr.getScaledWidth() / 2.0f - 20.0f), (float)(int)(sr.getScaledHeight() / 2.0f + 23.0f), -1);
                    }
                }
            }
        }
        if (event instanceof EventSycItem && this.getBlockSlot() != -1) {
            final EventSycItem eventSycItem = (EventSycItem)event;
            final int blockSlot = this.getBlockSlot();
            this.slot = blockSlot;
            eventSycItem.slot = blockSlot;
        }
        if (event instanceof EventUpdate && event.type == EventType.PRE) {
            this.objectPosition = null;
            Scaffold.data = this.find(new Vec3(0.0, 0.0, 0.0));
        }
        if (event instanceof EventUpdate) {
            Scaffold.data = this.find(new Vec3(0.0, 0.0, 0.0));
            if (Scaffold.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null) {
                if (Aqua.setmgr.getSetting("ScaffoldBMCBoost").isState()) {
                    Scaffold.mc.timer.timerSpeed = 1.105f;
                }
            }
            else {
                Scaffold.mc.timer.timerSpeed = 1.0f;
            }
            if (Aqua.setmgr.getSetting("ScaffoldAutoDisable").isState() && Killaura.target != null) {
                Aqua.moduleManager.getModuleByName("Scaffold").setState(false);
            }
        }
        if (event instanceof EventUpdate) {
            if (Aqua.setmgr.getSetting("ScaffoldTower").getCurrentMode().equalsIgnoreCase("Cubecraft") && Scaffold.mc.gameSettings.keyBindJump.pressed) {
                if (this.timeUtil.hasTimePassed(50L)) {
                    Scaffold.mc.thePlayer.motionY = 0.42;
                    this.timeUtil.reset();
                }
                if (isOnGround(0.1)) {
                    Scaffold.mc.thePlayer.motionY = 0.42;
                }
            }
            if (Aqua.setmgr.getSetting("ScaffoldTower").getCurrentMode().equalsIgnoreCase("Watchdog") && Scaffold.mc.gameSettings.keyBindJump.pressed) {
                if (Scaffold.mc.thePlayer.onGround) {
                    Scaffold.mc.thePlayer.motionY = 0.409;
                }
                if (this.offGround == 3) {
                    final float random1 = RandomUtil.instance.nextFloat(0.9908900590734863, 0.9909900590734863);
                    Scaffold.mc.thePlayer.motionY = (Scaffold.mc.thePlayer.motionY - 0.1) * random1;
                }
            }
            if (Aqua.setmgr.getSetting("ScaffoldTower").getCurrentMode().equalsIgnoreCase("UpdatedNCP") && Scaffold.mc.gameSettings.keyBindJump.pressed) {
                if (!Scaffold.mc.thePlayer.isMoving()) {
                    if (this.timeUtil.hasReached(2000L)) {
                        this.timeUtil.reset();
                    }
                    else if (Scaffold.mc.thePlayer.ticksExisted % 3 == 1 && this.offGround > 1 && !Scaffold.mc.thePlayer.onGround) {
                        Scaffold.mc.thePlayer.motionY = 0.4196;
                    }
                    this.timeUtil.reset();
                }
                else {
                    if (Scaffold.mc.thePlayer.onGround) {
                        Scaffold.mc.thePlayer.motionY = 0.409;
                    }
                    if (this.offGround == 3) {
                        final float random1 = RandomUtil.instance.nextFloat(0.9908900590734863, 0.9909900590734863);
                        Scaffold.mc.thePlayer.motionY = (Scaffold.mc.thePlayer.motionY - 0.1) * random1;
                    }
                }
            }
            if (Aqua.setmgr.getSetting("ScaffoldTower").getCurrentMode().equalsIgnoreCase("WatchdogNew") && Scaffold.mc.gameSettings.keyBindJump.pressed && Scaffold.mc.thePlayer.isMoving()) {
                if (this.timeUtil.hasReached(2000L)) {
                    this.timeUtil.reset();
                }
                else if (Scaffold.mc.thePlayer.ticksExisted % 3 == 1 && this.offGround > 1 && !Scaffold.mc.thePlayer.onGround) {
                    Scaffold.mc.thePlayer.motionY = 0.4196;
                }
                this.timeUtil.reset();
            }
            if (Aqua.setmgr.getSetting("ScaffoldTower").getCurrentMode().equalsIgnoreCase("IntaveFast") && Scaffold.mc.gameSettings.keyBindJump.pressed && !Scaffold.mc.gameSettings.keyBindForward.pressed) {
                if (Scaffold.mc.thePlayer.onGround) {
                    Scaffold.mc.thePlayer.motionY = 0.405;
                }
                if (this.offGround == 5) {
                    final float random1 = RandomUtil.instance.nextFloat(0.9908900590734863, 0.9909900590734863);
                    Scaffold.mc.thePlayer.motionY = (Scaffold.mc.thePlayer.motionY - 0.08) * random1;
                }
            }
            if (Aqua.setmgr.getSetting("ScaffoldTower").getCurrentMode().equalsIgnoreCase("VerusFast")) {
                if (Scaffold.mc.gameSettings.keyBindJump.pressed) {
                    Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                    Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(Fly.getX(), Fly.getY() - 1.5, Fly.getZ()), 1, new ItemStack(Blocks.stone.getItem(Scaffold.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
                    if (this.timeUtil.hasReached(5000L)) {
                        this.timeUtil.reset();
                    }
                    else {
                        Scaffold.mc.thePlayer.motionY = 0.70096;
                    }
                }
                else {
                    Scaffold.mc.timer.timerSpeed = 1.0f;
                }
                this.timeUtil.reset();
            }
            if (!Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Intave")) {
                if (Aqua.setmgr.getSetting("ScaffoldSprint").isState()) {
                    Scaffold.mc.gameSettings.keyBindSprint.pressed = true;
                }
                else {
                    Scaffold.mc.thePlayer.setSprinting(false);
                }
            }
            else if (Aqua.setmgr.getSetting("ScaffoldSprint").isState() && Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Intave") && !Scaffold.mc.gameSettings.keyBindJump.pressed) {
                Scaffold.mc.thePlayer.setSprinting(true);
            }
            else {
                Scaffold.mc.thePlayer.setSprinting(false);
            }
            if (!Aqua.setmgr.getSetting("ScaffoldLegitPlace").isState()) {
                if (!Aqua.setmgr.getSetting("ScaffoldSameY").isState() || Scaffold.mc.thePlayer.onGround) {}
                if (Aqua.setmgr.getSetting("ScaffoldDown").isState()) {
                    if (Scaffold.mc.gameSettings.keyBindSneak.pressed) {
                        this.down = true;
                        this.posY = Scaffold.mc.thePlayer.posY - 1.0;
                    }
                    else {
                        this.down = false;
                        this.posY = Scaffold.mc.thePlayer.posY;
                    }
                }
                Scaffold.data = this.find(new Vec3(0.0, 0.0, 0.0));
                if (Scaffold.data != null && this.getBlockSlot() != -1) {
                    final BlockPos blockpos = Scaffold.mc.objectMouseOver.getBlockPos();
                    Scaffold.mc.playerController.updateController();
                    final Vec3 hitVec = new Vec3(BlockData.getPos()).addVector(0.5, 0.5, 0.5).add(new Vec3(BlockData.getFacing().getDirectionVec()).multi(0.5));
                    if (this.slot != -1 && Scaffold.mc.playerController.onPlayerRightClick(Scaffold.mc.thePlayer, Scaffold.mc.theWorld, Scaffold.mc.thePlayer.inventory.getStackInSlot(this.slot), BlockData.getPos(), BlockData.getFacing(), hitVec)) {
                        if (Aqua.setmgr.getSetting("ScaffoldSwing").isState()) {
                            Scaffold.mc.thePlayer.swingItem();
                        }
                        else {
                            Scaffold.mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
                        }
                    }
                }
                Scaffold.mc.objectMouseOver.getBlockPos();
            }
            else {
                Scaffold.data = this.find(new Vec3(0.0, 0.0, 0.0));
                final BlockPos blockPos = new BlockPos(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY - 1.0, Scaffold.mc.thePlayer.posZ);
                if (Scaffold.mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.air) {
                    Scaffold.mc.gameSettings.keyBindSneak.pressed = true;
                }
                else if (!Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Intave")) {
                    Scaffold.mc.gameSettings.keyBindSneak.pressed = false;
                }
                if (Scaffold.mc.gameSettings.keyBindJump.pressed) {
                    Scaffold.mc.thePlayer.setSprinting(false);
                }
                if (Scaffold.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null || !Aqua.setmgr.getSetting("ScaffoldSprint").isState()) {
                    Scaffold.mc.thePlayer.setSprinting(false);
                    Scaffold.mc.gameSettings.keyBindSprint.pressed = false;
                }
                if (Scaffold.mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.air) {
                    this.rightClickMouse(Scaffold.mc.thePlayer.inventory.getStackInSlot(this.slot), this.slot);
                }
                if (Scaffold.mc.gameSettings.keyBindJump.pressed) {
                    Scaffold.mc.gameSettings.keyBindSneak.pressed = true;
                }
                else {
                    Scaffold.mc.gameSettings.keyBindSneak.pressed = (Scaffold.mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.air);
                }
            }
        }
        if (event instanceof EventPreMotion) {
            if (Scaffold.data == null) {
                return;
            }
            final float[] rotation2 = rotationrecode2(Scaffold.data);
            if (Aqua.setmgr.getSetting("ScaffoldRotationMode").getCurrentMode().equalsIgnoreCase("Static")) {
                RotationUtil.yaw = Scaffold.mc.thePlayer.rotationYawHead;
                RotationUtil.pitch = Scaffold.mc.thePlayer.rotationPitchHead;
                ((EventPreMotion)event).setYaw(Scaffold.mc.thePlayer.rotationYaw + 180.0f);
                ((EventPreMotion)event).setPitch(82.0f);
            }
            if (Aqua.setmgr.getSetting("ScaffoldRotationMode").getCurrentMode().equalsIgnoreCase("Calculated")) {
                RotationUtil.yaw = Scaffold.mc.thePlayer.rotationYawHead;
                RotationUtil.pitch = Scaffold.mc.thePlayer.rotationPitchHead;
                ((EventPreMotion)event).setYaw(rotation2[0]);
                ((EventPreMotion)event).setPitch(rotation2[1]);
            }
        }
    }
    
    private Vec3 getPositionByFace(final BlockPos position, final EnumFacing facing) {
        final Vec3 offset = new Vec3(facing.getDirectionVec().getX() / 2.0, facing.getDirectionVec().getY() / 2.0, facing.getDirectionVec().getZ() / 2.0);
        final Vec3 point = new Vec3(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
        return point.add(offset);
    }
    
    private boolean rayTrace(final Vec3 origin, final Vec3 position) {
        final Vec3 difference = position.subtract(origin);
        final int steps = 10;
        final double x = difference.xCoord / steps;
        final double y = difference.yCoord / steps;
        final double z = difference.zCoord / steps;
        Vec3 point = origin;
        for (int i = 0; i < steps; ++i) {
            final BlockPos blockPosition = new BlockPos(point = point.addVector(x, y, z));
            final Minecraft mc = Scaffold.mc;
            final IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(blockPosition);
            if (!(blockState.getBlock() instanceof BlockLiquid)) {
                if (!(blockState.getBlock() instanceof BlockAir)) {
                    final Block block = blockState.getBlock();
                    final Minecraft mc2 = Scaffold.mc;
                    AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Minecraft.getMinecraft().theWorld, blockPosition, blockState);
                    if (boundingBox == null) {
                        boundingBox = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                    }
                    if (boundingBox.offset(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ()).isVecInside(point)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private BlockData find(final Vec3 offset3) {
        final double xDiff = Scaffold.mc.thePlayer.posX - Scaffold.mc.thePlayer.prevPosX;
        final double zDiff = Scaffold.mc.thePlayer.posZ - Scaffold.mc.thePlayer.prevPosZ;
        final float expand = (float)Aqua.setmgr.getSetting("ScaffoldExpandlength").getCurrentNumber();
        final double x = Aqua.setmgr.getSetting("ScaffoldExpand").isState() ? (Scaffold.mc.thePlayer.posX + xDiff * expand) : Scaffold.mc.thePlayer.posX;
        final double y = (Aqua.setmgr.getSetting("ScaffoldSameY").isState() || Aqua.setmgr.getSetting("ScaffoldDown").isState()) ? (Scaffold.mc.gameSettings.keyBindForward.pressed ? this.posY : Scaffold.mc.thePlayer.posY) : Scaffold.mc.thePlayer.posY;
        final double z = Aqua.setmgr.getSetting("ScaffoldExpand").isState() ? (Scaffold.mc.thePlayer.posZ + zDiff * expand) : Scaffold.mc.thePlayer.posZ;
        final EnumFacing[] invert = { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST };
        final BlockPos position = new BlockPos(new Vec3(x, y, z).add(offset3)).offset(EnumFacing.DOWN);
        for (final EnumFacing facing : EnumFacing.values()) {
            final BlockPos offset4 = position.offset(facing);
            if (!(Scaffold.mc.theWorld.getBlockState(offset4).getBlock() instanceof BlockAir) && !this.rayTrace(Scaffold.mc.thePlayer.getLook(0.0f), this.getPositionByFace(offset4, invert[facing.ordinal()]))) {
                return new BlockData(invert[facing.ordinal()], offset4);
            }
        }
        final BlockPos[] array;
        final BlockPos[] offsets = array = new BlockPos[] { new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1), new BlockPos(0, 0, 2), new BlockPos(0, 0, -2), new BlockPos(2, 0, 0), new BlockPos(-2, 0, 0) };
        for (int length2 = array.length, j = 0; j < length2; ++j) {
            final BlockPos offset4 = array[j];
            final BlockPos offsetPos = position.add(offset4.getX(), 0, offset4.getZ());
            if (Scaffold.mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
                for (final EnumFacing facing2 : EnumFacing.values()) {
                    final BlockPos offset5 = offsetPos.offset(facing2);
                    if (!(Scaffold.mc.theWorld.getBlockState(offset5).getBlock() instanceof BlockAir) && !this.rayTrace(Scaffold.mc.thePlayer.getLook(0.01f), this.getPositionByFace(offset4, invert[facing2.ordinal()]))) {
                        return new BlockData(invert[facing2.ordinal()], offset5);
                    }
                }
            }
        }
        return null;
    }
    
    public int getBlockSlot() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack s = Scaffold.mc.thePlayer.inventory.getStackInSlot(i);
            if (s != null && s.getItem() instanceof ItemBlock) {
                final BlackList blackList = this.blackList;
                s.getItem();
                if (blackList.isNotBlackListed(Item.itemRegistry.getNameForObject(s.getItem()).toString())) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static float[] mouseSens(float yaw, float pitch) {
        final float f2 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[] { yaw, pitch };
    }
    
    public static float updateRotation(final float current, final float needed, final float speed) {
        float f = MathHelper.wrapAngleTo180_float(needed - current);
        if (f > speed) {
            f = speed;
        }
        if (f < -speed) {
            f = -speed;
        }
        return current + f;
    }
    
    public static float[] rotationrecode2(final BlockData blockData) {
        final double x = BlockData.getPos().getX() + 0.5 - Scaffold.mc.thePlayer.posX + BlockData.getFacing().getFrontOffsetX() / 2.0;
        final double z = BlockData.getPos().getZ() + 0.5 - Scaffold.mc.thePlayer.posZ + BlockData.getFacing().getFrontOffsetZ() / 2.0;
        final double y = BlockData.getPos().getY() + 0.6;
        final double ymax = Scaffold.mc.thePlayer.posY + Scaffold.mc.thePlayer.getEyeHeight() - y;
        final double allmax = MathHelper.sqrt_double(x * x + z * z);
        float yaw = Aqua.setmgr.getSetting("ScaffoldReverseYaw").isState() ? ((float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 270.0f) : ((float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - (float)Aqua.setmgr.getSetting("ScaffoldYawPosition").getCurrentNumber());
        float pitch = (float)(Math.atan2(ymax, allmax) * 180.0 / 3.141592653589793);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        final float f2 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[] { yaw, MathHelper.clamp_float(pitch, 78.0f, 80.0f) };
    }
    
    public void rightClickMouse(final ItemStack itemstack, final int slot) {
        if (!Scaffold.mc.playerController.getIsHittingBlock()) {
            Scaffold.mc.rightClickDelayTimer = 4;
            try {
                switch (Scaffold.mc.objectMouseOver.typeOfHit) {
                    case ENTITY: {
                        if (Scaffold.mc.playerController.isPlayerRightClickingOnEntity(Scaffold.mc.thePlayer, Scaffold.mc.objectMouseOver.entityHit, Scaffold.mc.objectMouseOver)) {
                            break;
                        }
                        if (Scaffold.mc.playerController.interactWithEntitySendPacket(Scaffold.mc.thePlayer, Scaffold.mc.objectMouseOver.entityHit)) {
                            break;
                        }
                        break;
                    }
                    case BLOCK: {
                        final BlockPos blockpos = Scaffold.mc.objectMouseOver.getBlockPos();
                        if (Scaffold.mc.theWorld.getBlockState(blockpos).getBlock().getMaterial() == Material.air) {
                            break;
                        }
                        final int i = (itemstack != null) ? itemstack.stackSize : 0;
                        if (Scaffold.mc.playerController.onPlayerRightClick(Scaffold.mc.thePlayer, Scaffold.mc.theWorld, itemstack, blockpos, Scaffold.mc.objectMouseOver.sideHit, Scaffold.mc.objectMouseOver.hitVec)) {
                            if (!Aqua.setmgr.getSetting("ScaffoldSwing").isState()) {
                                Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                            }
                            else {
                                Scaffold.mc.thePlayer.swingItem();
                            }
                        }
                        if (itemstack == null) {
                            return;
                        }
                        if (itemstack.stackSize == 0) {
                            Scaffold.mc.thePlayer.inventory.mainInventory[slot] = null;
                            break;
                        }
                        break;
                    }
                }
            }
            catch (NullPointerException ex) {}
        }
    }
    
    public static int getBlockCount() {
        int itemCount = 0;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Scaffold.mc.thePlayer.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemBlock) {
                itemCount += stack.stackSize;
            }
        }
        return itemCount;
    }
    
    public static boolean isOnGround(final double height) {
        return !Scaffold.mc.theWorld.getCollidingBoundingBoxes(Scaffold.mc.thePlayer, Scaffold.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }
    
    class BlackList
    {
        List<String> stringids;
        
        public BlackList() {
            this.stringids = new ArrayList<String>();
            this.addID("minecraft:wooden_slab");
            this.addID("minecraft:stone_slab");
            this.addID("minecraft:banner");
            this.addID("minecraft:beacon");
            this.addID("minecraft:trapped_chest");
            this.addID("minecraft:chest");
            this.addID("minecraft:anvil");
            this.addID("minecraft:enchanting_table");
            this.addID("minecraft:crafting_table");
            this.addID("minecraft:furnace");
            this.addID("minecraft:banner");
            this.addID("minecraft:wall_banner");
            this.addID("minecraft:standing_banner");
            this.addID("minecraft:web");
            this.addID("minecraft:sapling");
        }
        
        public List<String> getStringids() {
            return this.stringids;
        }
        
        public boolean isNotBlackListed(final String blockID) {
            return !this.stringids.contains(blockID);
        }
        
        public void addID(final String id) {
            this.stringids.add(id);
        }
    }
    
    public static class BlockData
    {
        private static EnumFacing facing;
        private static BlockPos pos;
        
        public BlockData(final EnumFacing facing, final BlockPos pos) {
            BlockData.facing = facing;
            BlockData.pos = pos;
        }
        
        public static EnumFacing getFacing() {
            return BlockData.facing;
        }
        
        public static BlockPos getPos() {
            return BlockData.pos;
        }
    }
}
