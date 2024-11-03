package net.augustus.modules.misc;

import java.awt.Color;
import java.util.ArrayList;
import net.augustus.events.EventMidClick;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

public class MidClick extends Module {
   public static ArrayList<String> friends = new ArrayList<>();
   private final ArrayList<String> backUpFriends = new ArrayList<>();
   private final BooleanValue autoUnfriend = new BooleanValue(1, "AutoUnfriend", this, true);
   public boolean noFiends = false;

   public MidClick() {
      super("MidClick", new Color(76, 78, 76), Categorys.MISC);
   }

   @Override
   public void onEnable() {
      friends = new ArrayList<>();
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      boolean b = true;
      if (this.autoUnfriend.getBoolean()) {
         b = false;
         NetHandlerPlayClient nethandlerplayclient = mc.thePlayer.sendQueue;

         for(NetworkPlayerInfo networkplayerinfo : GuiPlayerTabOverlay.field_175252_a.sortedCopy(nethandlerplayclient.getPlayerInfoMap())) {
            String string = networkplayerinfo.getGameProfile().getName();
            if (!friends.contains(string) && !mc.thePlayer.getName().equals(string)) {
               b = true;
               break;
            }
         }
      }

      this.noFiends = !b;
   }

   @EventTarget
   public void onEventMidClick(EventMidClick eventMidClick) {
      if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
         if (friends.contains(mc.objectMouseOver.entityHit.getName())) {
            friends.remove(mc.objectMouseOver.entityHit.getName());
         } else {
            friends.add(mc.objectMouseOver.entityHit.getName());
         }
      }
   }

   @Override
   public void onDisable() {
      friends = new ArrayList<>();
   }
}
