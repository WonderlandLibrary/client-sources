package com.example.editme.util.client;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenServerList;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.entity.RenderManager;
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
import net.minecraft.util.Session;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLLog;

public class ReflectionFields {
   public static Minecraft mc;
   public static Field modelManager;
   public static Field sPacketChatChatComponent;
   public static Field sleeping;
   public static Field renderPosZ;
   public static Field speedInAir;
   public static Field rightClickDelayTimer;
   public static Field pressed;
   public static Field spacketPlayerPosLookPitch;
   public static Field cpacketPlayerY;
   public static Field hopperInventory;
   public static Field lowerChestInventory;
   public static Field playerViewX;
   public static Field cPacketUpdateSignLines;
   public static Field guiSceenServerListServerData;
   public static Field renderPosX;
   public static Field spacketExplosionMotionZ;
   public static Field mapTextureObjects;
   public static Field spacketExplosionMotionY;
   public static Field cpacketVehicleMoveY;
   public static Field cPacketChatMessage;
   public static Field session;
   public static Field blockHitDelay;
   public static Field boundingBox;
   public static Field PLAYER_MODEL_FLAG;
   private static Field modifiersField;
   public static Field spacketPlayerPosLookYaw;
   public static Field renderPosY;
   public static Field foodExhaustionLevel;
   public static Field ridingEntity;
   public static Field curBlockDamageMP;
   public static Field cpacketPlayerPitch;
   public static Field shulkerInventory;
   public static Method rightClickMouse;
   public static Field y_vec3d;
   public static Field guiDisconnectedParentScreen;
   public static Field playerViewY;
   public static Field debugFps;
   public static Field sleepTimer;
   public static Field cpacketPlayerOnGround;
   public static Field spacketExplosionMotionX;
   public static Field cpacketPlayerYaw;
   public static Field guiButtonHovered;
   public static Field timer;
   public static Field horseJumpPower;

   public static Map getMapTextureObjects() {
      try {
         return (Map)mapTextureObjects.get(mc.func_110434_K());
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static float getPlayerViewY() {
      try {
         return (Float)playerViewY.get(mc.func_175598_ae());
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static void setSPacketChatChatComponent(SPacketChat var0, TextComponentString var1) {
      try {
         sPacketChatChatComponent.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static boolean getGuiButtonHovered(GuiButton var0) {
      try {
         return (Boolean)guiButtonHovered.get(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static void setCurBlockDamageMP(float var0) {
      try {
         curBlockDamageMP.set(mc.field_71442_b, var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static void setPressed(KeyBinding var0, boolean var1) {
      try {
         pressed.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static double getCPacketPlayerY(CPacketPlayer var0) {
      try {
         return (Double)cpacketPlayerY.get(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static void setBlockHitDelay(float var0) {
      try {
         blockHitDelay.set(mc.field_71442_b, var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static void setSPacketExplosionMotionY(SPacketExplosion var0, float var1) {
      try {
         spacketExplosionMotionY.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static void setCPacketVehicleMoveY(CPacketVehicleMove var0, double var1) {
      try {
         cpacketVehicleMoveY.set(var0, var1);
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new IllegalStateException(var4);
      }
   }

   public static Method getMethod(Class var0, String[] var1, Class... var2) {
      String[] var3 = var1;
      int var4 = var1.length;
      int var5 = 0;

      while(var5 < var4) {
         String var6 = var3[var5];

         try {
            Method var7 = var0.getDeclaredMethod(var6, var2);
            var7.setAccessible(true);
            return var7;
         } catch (NoSuchMethodException var8) {
            FMLLog.log.info(String.valueOf((new StringBuilder()).append("unable to find method: ").append(var6)));
            ++var5;
         }
      }

      throw new IllegalStateException(String.valueOf((new StringBuilder()).append("Method with names: ").append(var1).append(" not found!")));
   }

   public static void setCPacketPlayerOnGround(CPacketPlayer var0, boolean var1) {
      try {
         cpacketPlayerOnGround.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static void setHorseJumpPower(float var0) {
      try {
         horseJumpPower.set(mc.field_71439_g, var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static void rightClickMouse() {
      try {
         rightClickMouse.invoke(mc);
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static float getCurBlockDamageMP() {
      try {
         return (Float)curBlockDamageMP.get(mc.field_71442_b);
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static void setSPacketExplosionMotionX(SPacketExplosion var0, float var1) {
      try {
         spacketExplosionMotionX.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static net.minecraft.util.Timer getTimer() {
      try {
         return (net.minecraft.util.Timer)timer.get(mc);
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static void sleepTimer(EntityPlayer var0, int var1) {
      try {
         sleeping.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static double getRenderPosX() {
      try {
         return (Double)renderPosX.get(mc.func_175598_ae());
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static boolean getSleeping(EntityPlayer var0) {
      try {
         return (Boolean)sleeping.get(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static void setCPacketPlayerY(CPacketPlayer var0, double var1) {
      try {
         cpacketPlayerY.set(var0, var1);
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new IllegalStateException(var4);
      }
   }

   public static ModelManager getModelManager() {
      try {
         return (ModelManager)modelManager.get(mc);
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static int getBlockHitDelay() {
      try {
         return (Integer)blockHitDelay.get(mc.field_71442_b);
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static void setCPacketChatMessage(CPacketChatMessage var0, String var1) {
      try {
         cPacketChatMessage.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static void setSpeedInAir(EntityPlayer var0, float var1) {
      try {
         speedInAir.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static IInventory getHopperInventory(GuiHopper var0) {
      try {
         return (IInventory)hopperInventory.get(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static void setCPacketPlayerYaw(CPacketPlayer var0, float var1) {
      try {
         cpacketPlayerYaw.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static void setY_vec3d(Vec3d var0, double var1) {
      try {
         y_vec3d.set(var0, var1);
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new IllegalStateException(var4);
      }
   }

   public static double getCPacketVehicleMoveY(CPacketVehicleMove var0) {
      try {
         return (Double)cpacketVehicleMoveY.get(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static void setSPacketPlayerPosLookPitch(float var0, SPacketPlayerPosLook var1) {
      try {
         spacketPlayerPosLookPitch.set(var1, var0);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   static {
      try {
         modifiersField = Field.class.getDeclaredField("modifiers");
         modifiersField.setAccessible(true);
      } catch (Exception var1) {
      }

   }

   public static Field getField(Class var0, String... var1) {
      String[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];

         try {
            Field var6 = var0.getDeclaredField(var5);
            var6.setAccessible(true);
            modifiersField.setInt(var6, var6.getModifiers() & -17);
            return var6;
         } catch (NoSuchFieldException var7) {
            FMLLog.log.info(String.valueOf((new StringBuilder()).append("unable to find field: ").append(var5)));
         } catch (IllegalAccessException var8) {
            FMLLog.log.info("unable to make field changeable!");
         }
      }

      throw new IllegalStateException(String.valueOf((new StringBuilder()).append("Field with names: ").append(var1).append(" not found!")));
   }

   public static IInventory getShulkerInventory(GuiShulkerBox var0) {
      try {
         return (IInventory)shulkerInventory.get(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static boolean getPressed(KeyBinding var0) {
      try {
         return (Boolean)pressed.get(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static float getFoodExhaustionLevel() {
      try {
         return (Float)foodExhaustionLevel.get(mc.field_71439_g.func_71024_bL());
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static double getRenderPosY() {
      try {
         return (Double)renderPosY.get(mc.func_175598_ae());
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static void setCPacketUpdateSignLines(CPacketUpdateSign var0, String[] var1) {
      try {
         cPacketUpdateSignLines.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static IInventory getLowerChestInventory(GuiChest var0) {
      try {
         return (IInventory)lowerChestInventory.get(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static void init(Minecraft var0) {
      try {
         mc = var0;
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
      } catch (Exception var2) {
      }

   }

   public static Entity getRidingEntity(Entity var0) {
      try {
         return (Entity)ridingEntity.get(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static void setSPacketExplosionMotionZ(SPacketExplosion var0, float var1) {
      try {
         spacketExplosionMotionZ.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static GuiScreen getGuiDisconnectedParentScreen(GuiDisconnected var0) {
      try {
         return (GuiScreen)guiDisconnectedParentScreen.get(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static int getDebugFps() {
      try {
         return (Integer)debugFps.get((Object)null);
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static void setSleeping(EntityPlayer var0, boolean var1) {
      try {
         sleeping.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static float getSpeedInAir(EntityPlayer var0) {
      try {
         return (Float)speedInAir.get(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static float getPlayerViewX() {
      try {
         return (Float)playerViewX.get(mc.func_175598_ae());
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static DataParameter getPLAYER_MODEL_FLAG() {
      try {
         return (DataParameter)PLAYER_MODEL_FLAG.get((Object)null);
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static double getRenderPosZ() {
      try {
         return (Double)renderPosZ.get(mc.func_175598_ae());
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new IllegalStateException(var1);
      }
   }

   public static ServerData getServerData(GuiScreenServerList var0) {
      try {
         return (ServerData)guiSceenServerListServerData.get(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static void setGuiButtonHovered(GuiButton var0, boolean var1) {
      try {
         guiButtonHovered.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static void setCPacketPlayerPitch(CPacketPlayer var0, float var1) {
      try {
         cpacketPlayerPitch.set(var0, var1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static void setSession(Session var0) {
      try {
         session.set(mc, var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }

   public static void setSPacketPlayerPosLookYaw(float var0, SPacketPlayerPosLook var1) {
      try {
         spacketPlayerPosLookYaw.set(var1, var0);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3);
      }
   }

   public static void setRightClickDelayTimer(int var0) {
      try {
         rightClickDelayTimer.set(mc, var0);
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new IllegalStateException(var2);
      }
   }
}
