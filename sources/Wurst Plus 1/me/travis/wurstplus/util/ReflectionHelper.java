// 
// Decompiled by Procyon v0.5.36
// 

package me.travis.wurstplus.util;

import net.minecraft.util.text.TextComponentString;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.Session;
import net.minecraft.inventory.IInventory;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import java.util.Map;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreenServerList;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.util.FoodStats;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class ReflectionHelper
{
    public static Field renderPosX;
    public static Field renderPosY;
    public static Field renderPosZ;
    public static Field playerViewX;
    public static Field playerViewY;
    public static Field timer;
    public static Field modelManager;
    public static Field pressed;
    public static Field cpacketPlayerYaw;
    public static Field cpacketPlayerPitch;
    public static Field spacketPlayerPosLookYaw;
    public static Field spacketPlayerPosLookPitch;
    public static Field mapTextureObjects;
    public static Field cpacketPlayerOnGround;
    public static Field rightClickDelayTimer;
    public static Field horseJumpPower;
    public static Method rightClickMouse;
    public static Field curBlockDamageMP;
    public static Field blockHitDelay;
    public static Field debugFps;
    public static Field lowerChestInventory;
    public static Field shulkerInventory;
    public static Field spacketExplosionMotionX;
    public static Field spacketExplosionMotionY;
    public static Field spacketExplosionMotionZ;
    public static Field cpacketPlayerY;
    public static Field cpacketVehicleMoveY;
    public static Field session;
    public static Field PLAYER_MODEL_FLAG;
    public static Field speedInAir;
    public static Field guiButtonHovered;
    public static Field ridingEntity;
    public static Field foodExhaustionLevel;
    public static Field cPacketUpdateSignLines;
    public static Field hopperInventory;
    public static Field cPacketChatMessage;
    public static Field guiSceenServerListServerData;
    public static Field guiDisconnectedParentScreen;
    public static Field sPacketChatChatComponent;
    public static Field boundingBox;
    public static Field y_vec3d;
    public static Field sleeping;
    public static Field sleepTimer;
    private static Field modifiersField;
    
    public static void init() {
        try {
            ReflectionHelper.renderPosX = getField(RenderManager.class, "renderPosX", "field_78725_b");
            ReflectionHelper.renderPosY = getField(RenderManager.class, "renderPosY", "field_78726_c");
            ReflectionHelper.renderPosZ = getField(RenderManager.class, "renderPosZ", "field_78723_d");
            ReflectionHelper.playerViewX = getField(RenderManager.class, "playerViewX", "field_78732_j");
            ReflectionHelper.playerViewY = getField(RenderManager.class, "playerViewY", "field_78735_i");
            ReflectionHelper.timer = getField(Minecraft.class, "timer", "field_71428_T");
            ReflectionHelper.modelManager = getField(Minecraft.class, "modelManager", "field_175617_aL");
            ReflectionHelper.rightClickMouse = getMethod(Minecraft.class, new String[] { "rightClickMouse", "func_147121_ag" }, (Class<?>[])new Class[0]);
            ReflectionHelper.pressed = getField(KeyBinding.class, "pressed", "field_74513_e");
            ReflectionHelper.cpacketPlayerYaw = getField(CPacketPlayer.class, "yaw", "field_149476_e");
            ReflectionHelper.cpacketPlayerPitch = getField(CPacketPlayer.class, "pitch", "field_149473_f");
            ReflectionHelper.spacketPlayerPosLookYaw = getField(SPacketPlayerPosLook.class, "yaw", "field_148936_d");
            ReflectionHelper.spacketPlayerPosLookPitch = getField(SPacketPlayerPosLook.class, "pitch", "field_148937_e");
            ReflectionHelper.mapTextureObjects = getField(TextureManager.class, "mapTextureObjects", "field_110585_a");
            ReflectionHelper.cpacketPlayerOnGround = getField(CPacketPlayer.class, "onGround", "field_149474_g");
            ReflectionHelper.rightClickDelayTimer = getField(Minecraft.class, "rightClickDelayTimer", "field_71467_ac");
            ReflectionHelper.horseJumpPower = getField(EntityPlayerSP.class, "horseJumpPower", "field_110321_bQ");
            ReflectionHelper.curBlockDamageMP = getField(PlayerControllerMP.class, "curBlockDamageMP", "field_78770_f");
            ReflectionHelper.blockHitDelay = getField(PlayerControllerMP.class, "blockHitDelay", "field_78781_i");
            ReflectionHelper.debugFps = getField(Minecraft.class, "debugFPS", "field_71470_ab");
            ReflectionHelper.lowerChestInventory = getField(GuiChest.class, "lowerChestInventory", "field_147015_w");
            ReflectionHelper.shulkerInventory = getField(GuiShulkerBox.class, "inventory", "field_190779_v");
            ReflectionHelper.spacketExplosionMotionX = getField(SPacketExplosion.class, "motionX", "field_149152_f");
            ReflectionHelper.spacketExplosionMotionY = getField(SPacketExplosion.class, "motionY", "field_149153_g");
            ReflectionHelper.spacketExplosionMotionZ = getField(SPacketExplosion.class, "motionZ", "field_149159_h");
            ReflectionHelper.cpacketPlayerY = getField(CPacketPlayer.class, "y", "field_149477_b");
            ReflectionHelper.cpacketVehicleMoveY = getField(CPacketVehicleMove.class, "y", "field_187008_b");
            ReflectionHelper.session = getField(Minecraft.class, "session", "field_71449_j");
            ReflectionHelper.PLAYER_MODEL_FLAG = getField(EntityPlayer.class, "PLAYER_MODEL_FLAG", "field_184827_bp");
            ReflectionHelper.speedInAir = getField(EntityPlayer.class, "speedInAir", "field_71102_ce");
            ReflectionHelper.guiButtonHovered = getField(GuiButton.class, "hovered", "field_146123_n");
            ReflectionHelper.ridingEntity = getField(Entity.class, "ridingEntity", "field_184239_as");
            ReflectionHelper.foodExhaustionLevel = getField(FoodStats.class, "foodExhaustionLevel", "field_75126_c");
            ReflectionHelper.cPacketUpdateSignLines = getField(CPacketUpdateSign.class, "lines", "field_149590_d");
            ReflectionHelper.hopperInventory = getField(GuiHopper.class, "hopperInventory", "field_147083_w");
            ReflectionHelper.cPacketChatMessage = getField(CPacketChatMessage.class, "message", "field_149440_a");
            ReflectionHelper.guiSceenServerListServerData = getField(GuiScreenServerList.class, "serverData", "field_146301_f");
            ReflectionHelper.guiDisconnectedParentScreen = getField(GuiDisconnected.class, "parentScreen", "field_146307_h");
            ReflectionHelper.sPacketChatChatComponent = getField(SPacketChat.class, "chatComponent", "field_148919_a");
            ReflectionHelper.boundingBox = getField(Entity.class, "boundingBox", "field_148919_a");
            ReflectionHelper.y_vec3d = getField(Vec3d.class, "y", "field_72448_b", "c");
            ReflectionHelper.sleeping = getField(EntityPlayer.class, "sleeping", "field_71083_bS", "bK");
            ReflectionHelper.sleepTimer = getField(EntityPlayer.class, "sleepTimer", "field_71076_b");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Field getField(final Class c, final String... names) {
        for (final String s : names) {
            try {
                final Field f = c.getDeclaredField(s);
                f.setAccessible(true);
                ReflectionHelper.modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
                return f;
            }
            catch (NoSuchFieldException e) {
                FMLLog.log.info("unable to find field: " + s);
            }
            catch (IllegalAccessException e2) {
                FMLLog.log.info("unable to make field changeable!");
            }
        }
        throw new IllegalStateException("Field with names: " + names + " not found!");
    }
    
    public static Method getMethod(final Class c, final String[] names, final Class<?>... args) {
        final int length = names.length;
        int i = 0;
        while (i < length) {
            final String s = names[i];
            try {
                final Method m = c.getDeclaredMethod(s, (Class[])args);
                m.setAccessible(true);
                return m;
            }
            catch (NoSuchMethodException e) {
                FMLLog.log.info("unable to find method: " + s);
                ++i;
                continue;
            }
        }
        throw new IllegalStateException("Method with names: " + names + " not found!");
    }
    
    public static double getRenderPosX() {
        try {
            return (double)ReflectionHelper.renderPosX.get(Wrapper.getMinecraft().getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getRenderPosY() {
        try {
            return (double)ReflectionHelper.renderPosY.get(Wrapper.getMinecraft().getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getRenderPosZ() {
        try {
            return (double)ReflectionHelper.renderPosZ.get(Wrapper.getMinecraft().getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static float getPlayerViewY() {
        try {
            return (float)ReflectionHelper.playerViewY.get(Wrapper.getMinecraft().getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static float getPlayerViewX() {
        try {
            return (float)ReflectionHelper.playerViewX.get(Wrapper.getMinecraft().getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static Timer getTimer() {
        try {
            return (Timer)ReflectionHelper.timer.get(Wrapper.getMinecraft());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static ModelManager getModelManager() {
        try {
            return (ModelManager)ReflectionHelper.modelManager.get(Wrapper.getMinecraft());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void rightClickMouse() {
        try {
            ReflectionHelper.rightClickMouse.invoke(Wrapper.getMinecraft(), new Object[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static boolean getPressed(final KeyBinding binding) {
        try {
            return (boolean)ReflectionHelper.pressed.get(binding);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setPressed(final KeyBinding keyBinding, final boolean state) {
        try {
            ReflectionHelper.pressed.set(keyBinding, state);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketPlayerYaw(final CPacketPlayer packet, final float value) {
        try {
            ReflectionHelper.cpacketPlayerYaw.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketPlayerPitch(final CPacketPlayer packet, final float value) {
        try {
            ReflectionHelper.cpacketPlayerPitch.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSPacketPlayerPosLookYaw(final float value, final SPacketPlayerPosLook packet) {
        try {
            ReflectionHelper.spacketPlayerPosLookYaw.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSPacketPlayerPosLookPitch(final float value, final SPacketPlayerPosLook packet) {
        try {
            ReflectionHelper.spacketPlayerPosLookPitch.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static Map<ResourceLocation, ITextureObject> getMapTextureObjects() {
        try {
            return (Map<ResourceLocation, ITextureObject>)ReflectionHelper.mapTextureObjects.get(Wrapper.getMinecraft().getTextureManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketPlayerOnGround(final CPacketPlayer packet, final boolean onGround) {
        try {
            ReflectionHelper.cpacketPlayerOnGround.set(packet, onGround);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setRightClickDelayTimer(final int value) {
        try {
            ReflectionHelper.rightClickDelayTimer.set(Wrapper.getMinecraft(), value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setHorseJumpPower(final float value) {
        try {
            ReflectionHelper.horseJumpPower.set(Wrapper.getMinecraft().player, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static float getCurBlockDamageMP() {
        try {
            return (float)ReflectionHelper.curBlockDamageMP.get(Wrapper.getMinecraft().playerController);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCurBlockDamageMP(final float value) {
        try {
            ReflectionHelper.curBlockDamageMP.set(Wrapper.getMinecraft().playerController, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static int getBlockHitDelay() {
        try {
            return (int)ReflectionHelper.blockHitDelay.get(Wrapper.getMinecraft().playerController);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setBlockHitDelay(final float value) {
        try {
            ReflectionHelper.blockHitDelay.set(Wrapper.getMinecraft().playerController, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static int getDebugFps() {
        try {
            return (int)ReflectionHelper.debugFps.get(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static IInventory getLowerChestInventory(final GuiChest chest) {
        try {
            return (IInventory)ReflectionHelper.lowerChestInventory.get(chest);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static IInventory getShulkerInventory(final GuiShulkerBox chest) {
        try {
            return (IInventory)ReflectionHelper.shulkerInventory.get(chest);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSPacketExplosionMotionX(final SPacketExplosion packet, final float value) {
        try {
            ReflectionHelper.spacketExplosionMotionX.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSPacketExplosionMotionY(final SPacketExplosion packet, final float value) {
        try {
            ReflectionHelper.spacketExplosionMotionY.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSPacketExplosionMotionZ(final SPacketExplosion packet, final float value) {
        try {
            ReflectionHelper.spacketExplosionMotionZ.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getCPacketPlayerY(final CPacketPlayer packet) {
        try {
            return (double)ReflectionHelper.cpacketPlayerY.get(packet);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketPlayerY(final CPacketPlayer packet, final double value) {
        try {
            ReflectionHelper.cpacketPlayerY.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getCPacketVehicleMoveY(final CPacketVehicleMove packet) {
        try {
            return (double)ReflectionHelper.cpacketVehicleMoveY.get(packet);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketVehicleMoveY(final CPacketVehicleMove packet, final double value) {
        try {
            ReflectionHelper.cpacketVehicleMoveY.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSession(final Session newSession) {
        try {
            ReflectionHelper.session.set(Wrapper.getMinecraft(), newSession);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static DataParameter<Byte> getPLAYER_MODEL_FLAG() {
        try {
            return (DataParameter<Byte>)ReflectionHelper.PLAYER_MODEL_FLAG.get(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSpeedInAir(final EntityPlayer entityPlayer, final float newValue) {
        try {
            ReflectionHelper.speedInAir.set(entityPlayer, newValue);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static float getSpeedInAir(final EntityPlayer entityPlayer) {
        try {
            return (float)ReflectionHelper.speedInAir.get(entityPlayer);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static boolean getGuiButtonHovered(final GuiButton button) {
        try {
            return (boolean)ReflectionHelper.guiButtonHovered.get(button);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setGuiButtonHovered(final GuiButton button, final boolean value) {
        try {
            ReflectionHelper.guiButtonHovered.set(button, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static Entity getRidingEntity(final Entity toGetFrom) {
        try {
            return (Entity)ReflectionHelper.ridingEntity.get(toGetFrom);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static float getFoodExhaustionLevel() {
        try {
            return (float)ReflectionHelper.foodExhaustionLevel.get(Wrapper.getMinecraft().player.getFoodStats());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketUpdateSignLines(final CPacketUpdateSign packet, final String[] value) {
        try {
            ReflectionHelper.cPacketUpdateSignLines.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static IInventory getHopperInventory(final GuiHopper chest) {
        try {
            return (IInventory)ReflectionHelper.hopperInventory.get(chest);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketChatMessage(final CPacketChatMessage packet, final String value) {
        try {
            ReflectionHelper.cPacketChatMessage.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static ServerData getServerData(final GuiScreenServerList data) {
        try {
            return (ServerData)ReflectionHelper.guiSceenServerListServerData.get(data);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static GuiScreen getGuiDisconnectedParentScreen(final GuiDisconnected toGetFrom) {
        try {
            return (GuiScreen)ReflectionHelper.guiDisconnectedParentScreen.get(toGetFrom);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSPacketChatChatComponent(final SPacketChat packet, final TextComponentString value) {
        try {
            ReflectionHelper.sPacketChatChatComponent.set(packet, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setY_vec3d(final Vec3d vec, final double val) {
        try {
            ReflectionHelper.y_vec3d.set(vec, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static boolean getSleeping(final EntityPlayer mgr) {
        try {
            return (boolean)ReflectionHelper.sleeping.get(mgr);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setSleeping(final EntityPlayer entityPlayer, final boolean value) {
        try {
            ReflectionHelper.sleeping.set(entityPlayer, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void sleepTimer(final EntityPlayer entityPlayer, final int value) {
        try {
            ReflectionHelper.sleeping.set(entityPlayer, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    static {
        try {
            (ReflectionHelper.modifiersField = Field.class.getDeclaredField("modifiers")).setAccessible(true);
        }
        catch (Exception ex) {}
    }
}
