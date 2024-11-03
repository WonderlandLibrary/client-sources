package net.augustus.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.augustus.events.EventReadPacket;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AntiBot extends Module {
   public static List<EntityPlayer> bots = new ArrayList<>();
   public StringValue mode = new StringValue(1, "Mode", this, "Mineplex", new String[]{"Mineplex", "Custom"});
   public DoubleValue ticksExisted = new DoubleValue(1, "TicksExisted", this, 20.0, 1.0, 100.0, 0);
   public BooleanValue checkTab = new BooleanValue(2, "CheckTab", this, false);

   public AntiBot() {
      super("AntiBot", Color.BLUE, Categorys.COMBAT);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      bots.clear();
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      this.setDisplayName(super.getName() + " §8" + this.mode.getSelected());
      List<EntityPlayer> tab = getTabPlayerList();
      String var2 = this.mode.getSelected();
      switch(var2) {
         case "Mineplex":
            if (mc.thePlayer.ticksExisted > 110) {
               for(Entity entity : mc.theWorld.loadedEntityList) {
                  if (entity instanceof EntityPlayer && entity != mc.thePlayer && entity.getCustomNameTag() == "" && !bots.contains((EntityPlayer)entity)) {
                     bots.add((EntityPlayer)entity);
                  }
               }
            } else {
               bots = new ArrayList<>();
            }
            break;
         case "Custom":
            bots.clear();

            for(Entity entity : mc.theWorld.loadedEntityList) {
               if(entity instanceof EntityPlayer) {
                  if (checkTab.getBoolean()) {
                     if (!tab.contains(entity))
                        bots.add((EntityPlayer) entity);
                  }

                  if ((double) entity.ticksExisted < this.ticksExisted.getValue() && entity != mc.thePlayer && !bots.contains(entity)) {
                     bots.add((EntityPlayer) entity);
                  }
               }
            }
      }
   }
   public static List<EntityPlayer> getTabPlayerList() {
      NetHandlerPlayClient var4 = (Minecraft.getMinecraft()).thePlayer.sendQueue;
      List<EntityPlayer> list = new ArrayList<>();
      List<NetworkPlayerInfo> players = GuiPlayerTabOverlay.field_175252_a.sortedCopy(var4.getPlayerInfoMap());
      for (NetworkPlayerInfo o : players) {
         NetworkPlayerInfo info = o;
         if (info == null)
            continue;
         list.add((Minecraft.getMinecraft()).theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
      }
      return list;
   }

   @EventTarget
   public void onEventReadPacket(EventReadPacket eventReadPacket) {
   }
}
