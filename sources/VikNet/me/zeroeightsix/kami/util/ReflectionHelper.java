package me.zeroeightsix.kami.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLLog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * Created on 26 October 2019 by hub.
 * <p>
 * To lazy to set up myself, so: RusherHack leak skid :P
 * <p>
 * Please just use the Reflections for things that are not accessible easily otherwise!
 */
public class ReflectionHelper {

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

    static {
        try {
            modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
        } catch (Exception ignored) {
        }
    }

    public static void init() {
        try {
            renderPosX = getField(RenderManager.class, "renderPosX", "field_78725_b");
            renderPosY = getField(RenderManager.class, "renderPosY", "field_78726_c");
            renderPosZ = getField(RenderManager.class, "renderPosZ", "field_78723_d");
            playerViewX = getField(RenderManager.class, "playerViewX", "field_78732_j");
            playerViewY = getField(RenderManager.class, "playerViewY", "field_78735_i");
            timer = getField(Minecraft.class, "timer", "field_71428_T");
            modelManager = getField(Minecraft.class, "modelManager", "field_175617_aL");
            rightClickMouse = getMethod(Minecraft.class, new String[]{"rightClickMouse", "func_147121_ag"});
            pressed = getField(KeyBinding.class, "pressed", "field_74513_e");

            cpacketPlayerYaw = getField(CPacketPlayer.class, "yaw", "field_149476_e");
            cpacketPlayerPitch = getField(CPacketPlayer.class, "pitch", "field_149473_f");

            spacketPlayerPosLookYaw = getField(SPacketPlayerPosLook.class, "yaw", "field_148936_d");
            spacketPlayerPosLookPitch = getField(SPacketPlayerPosLook.class, "pitch", "field_148937_e");

            mapTextureObjects = getField(TextureManager.class, "mapTextureObjects", "field_110585_a");

            cpacketPlayerOnGround = getField(CPacketPlayer.class, "onGround", "field_149474_g");

            rightClickDelayTimer = getField(Minecraft.class, "rightClickDelayTimer", "field_71467_ac");

            horseJumpPower = getField(EntityPlayerSP.class, "horseJumpPower", "field_110321_bQ");

            curBlockDamageMP = getField(PlayerControllerMP.class, "curBlockDamageMP", "field_78770_f");

            blockHitDelay = getField(PlayerControllerMP.class, "blockHitDelay", "field_78781_i");

            debugFps = getField(Minecraft.class, "debugFPS", "field_71470_ab");

            lowerChestInventory = getField(GuiChest.class, "lowerChestInventory", "field_147015_w");
            shulkerInventory = getField(GuiShulkerBox.class, "inventory", "field_190779_v");

            spacketExplosionMotionX = getField(SPacketExplosion.class, "motionX", "field_149152_f");
            spacketExplosionMotionY = getField(SPacketExplosion.class, "motionY", "field_149153_g");
            spacketExplosionMotionZ = getField(SPacketExplosion.class, "motionZ", "field_149159_h");

            cpacketPlayerY = getField(CPacketPlayer.class, "y", "field_149477_b");
            cpacketVehicleMoveY = getField(CPacketVehicleMove.class, "y", "field_187008_b");
            session = getField(Minecraft.class, "session", "field_71449_j");

            PLAYER_MODEL_FLAG = getField(EntityPlayer.class, "PLAYER_MODEL_FLAG", "field_184827_bp");
            speedInAir = getField(EntityPlayer.class, "speedInAir", "field_71102_ce");

            guiButtonHovered = getField(GuiButton.class, "hovered", "field_146123_n");

            ridingEntity = getField(Entity.class, "ridingEntity", "field_184239_as");

            foodExhaustionLevel = getField(FoodStats.class, "foodExhaustionLevel", "field_75126_c");

            cPacketUpdateSignLines = getField(CPacketUpdateSign.class, "lines", "field_149590_d");

            hopperInventory = getField(GuiHopper.class, "hopperInventory", "field_147083_w");

            cPacketChatMessage = getField(CPacketChatMessage.class, "message", "field_149440_a");

            guiSceenServerListServerData = getField(GuiScreenServerList.class, "serverData", "field_146301_f");
            guiDisconnectedParentScreen = getField(GuiDisconnected.class, "parentScreen", "field_146307_h");
            sPacketChatChatComponent = getField(SPacketChat.class, "chatComponent", "field_148919_a");

            boundingBox = getField(Entity.class, "boundingBox", "field_148919_a");

            y_vec3d = getField(Vec3d.class, "y", "field_72448_b", "c");

            sleeping = getField(EntityPlayer.class, "sleeping", "field_71083_bS", "bK");

            sleepTimer = getField(EntityPlayer.class, "sleepTimer", "field_71076_b");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Field getField(Class c, String... names) {
        for (String s : names) {
            try {
                Field f = c.getDeclaredField(s);
                f.setAccessible(true);
                modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                return f;
            } catch (NoSuchFieldException e) {
                FMLLog.log.info("unable to find field: " + s);
            } catch (IllegalAccessException e) {
                FMLLog.log.info("unable to make field changeable!");
            }
        }

        throw new IllegalStateException("Field with names: " + names + " not found!");
    }

    public static Method getMethod(Class c, String[] names, Class<?>... args) {
        for (String s : names) {
            try {
                Method m = c.getDeclaredMethod(s, args);
                m.setAccessible(true);
                return m;
            } catch (NoSuchMethodException e) {
                FMLLog.log.info("unable to find method: " + s);
            }
        }

        throw new IllegalStateException("Method with names: " + names + " not found!");
    }

    public static double getRenderPosX() {
        try {
            return (double) renderPosX.get(Wrapper.getMinecraft().getRenderManager());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getRenderPosY() {
        try {
            return (double) renderPosY.get(Wrapper.getMinecraft().getRenderManager());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getRenderPosZ() {
        try {
            return (double) renderPosZ.get(Wrapper.getMinecraft().getRenderManager());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static float getPlayerViewY() {
        try {
            return (float) playerViewY.get(Wrapper.getMinecraft().getRenderManager());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static float getPlayerViewX() {
        try {
            return (float) playerViewX.get(Wrapper.getMinecraft().getRenderManager());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static Timer getTimer() {
        try {
            return (Timer) timer.get(Wrapper.getMinecraft());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static ModelManager getModelManager() {
        try {
            return (ModelManager) modelManager.get(Wrapper.getMinecraft());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void rightClickMouse() {
        try {
            rightClickMouse.invoke(Wrapper.getMinecraft());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static boolean getPressed(KeyBinding binding) {
        try {
            return (boolean) pressed.get(binding);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setPressed(KeyBinding keyBinding, boolean state) {
        try {
            pressed.set(keyBinding, state);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketPlayerYaw(CPacketPlayer packet, float value) {
        try {
            cpacketPlayerYaw.set(packet, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketPlayerPitch(CPacketPlayer packet, float value) {
        try {
            cpacketPlayerPitch.set(packet, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSPacketPlayerPosLookYaw(float value, SPacketPlayerPosLook packet) {
        try {
            spacketPlayerPosLookYaw.set(packet, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSPacketPlayerPosLookPitch(float value, SPacketPlayerPosLook packet) {
        try {
            spacketPlayerPosLookPitch.set(packet, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static Map<ResourceLocation, ITextureObject> getMapTextureObjects() {
        try {
            return (Map<ResourceLocation, ITextureObject>) mapTextureObjects.get(Wrapper.getMinecraft().getTextureManager());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketPlayerOnGround(CPacketPlayer packet, boolean onGround) {
        try {
            cpacketPlayerOnGround.set(packet, onGround);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setRightClickDelayTimer(int value) {
        try {
            rightClickDelayTimer.set(Wrapper.getMinecraft(), value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setHorseJumpPower(float value) {
        try {
            horseJumpPower.set(Wrapper.getMinecraft().player, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static float getCurBlockDamageMP() {
        try {
            return (float) curBlockDamageMP.get(Wrapper.getMinecraft().playerController);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCurBlockDamageMP(float value) {
        try {
            curBlockDamageMP.set(Wrapper.getMinecraft().playerController, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static int getBlockHitDelay() {
        try {
            return (int) blockHitDelay.get(Wrapper.getMinecraft().playerController);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setBlockHitDelay(float value) {
        try {
            blockHitDelay.set(Wrapper.getMinecraft().playerController, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static int getDebugFps() {
        try {
            return (int) debugFps.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static IInventory getLowerChestInventory(GuiChest chest) {
        try {
            return (IInventory) lowerChestInventory.get(chest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static IInventory getShulkerInventory(GuiShulkerBox chest) {
        try {
            return (IInventory) shulkerInventory.get(chest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSPacketExplosionMotionX(SPacketExplosion packet, float value) {
        try {
            spacketExplosionMotionX.set(packet, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSPacketExplosionMotionY(SPacketExplosion packet, float value) {
        try {
            spacketExplosionMotionY.set(packet, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSPacketExplosionMotionZ(SPacketExplosion packet, float value) {
        try {
            spacketExplosionMotionZ.set(packet, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getCPacketPlayerY(CPacketPlayer packet) {
        try {
            return (double) cpacketPlayerY.get(packet);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketPlayerY(CPacketPlayer packet, double value) {
        try {
            cpacketPlayerY.set(packet, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static double getCPacketVehicleMoveY(CPacketVehicleMove packet) {
        try {
            return (double) cpacketVehicleMoveY.get(packet);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketVehicleMoveY(CPacketVehicleMove packet, double value) {
        try {
            cpacketVehicleMoveY.set(packet, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSession(Session newSession) {
        try {
            session.set(Wrapper.getMinecraft(), newSession);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }


    public static DataParameter<Byte> getPLAYER_MODEL_FLAG() {
        try {
            return (DataParameter<Byte>) PLAYER_MODEL_FLAG.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);


        }
    }

    public static void setSpeedInAir(EntityPlayer entityPlayer, float newValue) {
        try {
            speedInAir.set(entityPlayer, newValue);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static float getSpeedInAir(EntityPlayer entityPlayer) {
        try {
            return (float) speedInAir.get(entityPlayer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);


        }
    }

    public static boolean getGuiButtonHovered(GuiButton button) {
        try {
            return (boolean) guiButtonHovered.get(button);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setGuiButtonHovered(GuiButton button, boolean value) {
        try {
            guiButtonHovered.set(button, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static Entity getRidingEntity(Entity toGetFrom) {
        try {
            return (Entity) ridingEntity.get(toGetFrom);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static float getFoodExhaustionLevel() {
        try {
            return (float) foodExhaustionLevel.get(Wrapper.getMinecraft().player.getFoodStats());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketUpdateSignLines(CPacketUpdateSign packet, String[] value) {
        try {
            cPacketUpdateSignLines.set(packet, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static IInventory getHopperInventory(GuiHopper chest) {
        try {
            return (IInventory) hopperInventory.get(chest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setCPacketChatMessage(CPacketChatMessage packet, String value) {
        try {
            cPacketChatMessage.set(packet, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }


    public static ServerData getServerData(GuiScreenServerList data) {
        try {
            return (ServerData) guiSceenServerListServerData.get(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static GuiScreen getGuiDisconnectedParentScreen(GuiDisconnected toGetFrom) {
        try {
            return (GuiScreen) guiDisconnectedParentScreen.get(toGetFrom);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSPacketChatChatComponent(SPacketChat packet, TextComponentString value) {
        try {
            sPacketChatChatComponent.set(packet, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setY_vec3d(Vec3d vec, double val) {
        try {
            y_vec3d.set(vec, val);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static boolean getSleeping(EntityPlayer mgr) {
        try {
            return (boolean) sleeping.get(mgr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static void setSleeping(EntityPlayer entityPlayer, boolean value) {
        try {
            sleeping.set(entityPlayer, Boolean.valueOf(value));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }

    }

    public static void sleepTimer(EntityPlayer entityPlayer, int value) {
        try {
            sleeping.set(entityPlayer, Integer.valueOf(value));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }

    }

}
