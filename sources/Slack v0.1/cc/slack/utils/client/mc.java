package cc.slack.utils.client;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Timer;

public class mc {
   public static Minecraft getMinecraft() {
      return Minecraft.getMinecraft();
   }

   public static Timer getTimer() {
      return getMinecraft().timer;
   }

   public static EntityRenderer getEntityRenderer() {
      return getMinecraft().entityRenderer;
   }

   public static EntityPlayerSP getPlayer() {
      return getMinecraft().thePlayer;
   }

   public static WorldClient getWorld() {
      return getMinecraft().theWorld;
   }

   public static FontRenderer getFontRenderer() {
      return getMinecraft().MCfontRenderer;
   }

   public static PlayerControllerMP getPlayerController() {
      return getMinecraft().playerController;
   }

   public static NetHandlerPlayClient getNetHandler() {
      return getMinecraft().getNetHandler();
   }

   public static GameSettings getGameSettings() {
      return getMinecraft().gameSettings;
   }

   public static GuiScreen getCurrentScreen() {
      return getMinecraft().currentScreen;
   }

   public static List<EntityPlayer> getLoadedPlayers() {
      return getWorld().playerEntities;
   }

   public static TextureManager getTextureManager() {
      return getMinecraft().getTextureManager();
   }

   public static RenderManager getRenderManager() {
      return getMinecraft().getRenderManager();
   }

   public static ScaledResolution getScaledResolution() {
      return new ScaledResolution(getMinecraft());
   }

   public static List<EntityLivingBase> getLivingEntities(Predicate<EntityLivingBase> validator) {
      List<EntityLivingBase> entities = new ArrayList();
      getWorld().loadedEntityList.forEach((entity) -> {
         if (entity instanceof EntityLivingBase) {
            EntityLivingBase ent = (EntityLivingBase)entity;
            if (validator.test(ent)) {
               entities.add(ent);
            }
         }

      });
      return entities;
   }
}
