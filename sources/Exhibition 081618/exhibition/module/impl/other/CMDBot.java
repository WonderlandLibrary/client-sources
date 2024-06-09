package exhibition.module.impl.other;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventPacket;
import exhibition.management.friend.Friend;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.misc.ChatUtil;
import exhibition.util.misc.PathFind;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;

public class CMDBot extends Module {
   private boolean following;
   private String followName;

   public CMDBot(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventPacket.class, EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate e = (EventMotionUpdate)event;
         if (e.isPre()) {
            if (mc.thePlayer.isDead) {
               mc.thePlayer.respawnPlayer();
            }

            if (this.following) {
               try {
                  new PathFind(this.followName);
                  e.setPitch(PathFind.fakePitch - 30.0F);
                  e.setYaw(PathFind.fakeYaw);
               } catch (Exception var12) {
                  ;
               }
            }
         }
      }

      if (event instanceof EventPacket) {
         EventPacket ep = (EventPacket)event;
         if (ep.isIncoming() && ep.getPacket() instanceof S02PacketChat) {
            S02PacketChat message = (S02PacketChat)ep.getPacket();
            Iterator var4;
            Friend friend;
            String s;
            if (message.func_148915_c().getFormattedText().contains("-follow")) {
               var4 = FriendManager.friendsList.iterator();

               while(var4.hasNext()) {
                  friend = (Friend)var4.next();
                  if (message.func_148915_c().getFormattedText().contains(friend.name)) {
                     s = message.func_148915_c().getFormattedText();
                     s = s.substring(s.indexOf("-follow ") + 8);
                     s = s.substring(0, s.indexOf("§"));
                     this.following = true;
                     this.followName = s;
                     break;
                  }
               }
            }

            if (message.func_148915_c().getFormattedText().contains("-stopfollow")) {
               var4 = FriendManager.friendsList.iterator();

               while(var4.hasNext()) {
                  friend = (Friend)var4.next();
                  if (message.func_148915_c().getFormattedText().contains(friend.name)) {
                     s = message.func_148915_c().getFormattedText();
                     s = s.substring(s.indexOf("-stopfollow ") + 12);
                     s = s.substring(0, s.indexOf("§"));
                     this.following = false;
                     this.followName = "";
                     break;
                  }
               }
            }

            int index;
            if (message.func_148915_c().getFormattedText().contains("-amandatodd")) {
               var4 = FriendManager.friendsList.iterator();

               label178:
               while(var4.hasNext()) {
                  friend = (Friend)var4.next();
                  if (message.func_148915_c().getFormattedText().contains(friend.name)) {
                     index = 0;

                     while(true) {
                        if (index >= 81) {
                           break label178;
                        }

                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 20.0D, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        ++index;
                     }
                  }
               }
            }

            if (message.func_148915_c().getFormattedText().contains("-tpahere")) {
               var4 = FriendManager.friendsList.iterator();

               while(var4.hasNext()) {
                  friend = (Friend)var4.next();
                  if (message.func_148915_c().getFormattedText().contains(friend.name)) {
                     ChatUtil.sendChat("/tpa " + friend.name);
                     break;
                  }
               }
            }

            Iterator var7;
            Friend friend2;
            Object o;
            EntityPlayer mod;
            if (message.func_148915_c().getFormattedText().contains("-friend ")) {
               var4 = mc.theWorld.getLoadedEntityList().iterator();

               label158:
               while(true) {
                  while(true) {
                     do {
                        do {
                           if (!var4.hasNext()) {
                              break label158;
                           }

                           o = var4.next();
                        } while(!(o instanceof EntityPlayer));

                        mod = (EntityPlayer)o;
                     } while(!message.func_148915_c().getFormattedText().contains("-friend " + mod.getName()));

                     var7 = FriendManager.friendsList.iterator();

                     while(var7.hasNext()) {
                        friend2 = (Friend)var7.next();
                        if (message.func_148915_c().getFormattedText().contains(friend2.name)) {
                           if (FriendManager.isFriend(mod.getName())) {
                              ChatUtil.sendChat(mod.getName() + " is already a friend.");
                           } else if (!FriendManager.isFriend(mod.getName())) {
                              ChatUtil.sendChat(mod.getName() + " has been friended.");
                              FriendManager.addFriend(mod.getName(), mod.getName());
                           }
                           break;
                        }
                     }
                  }
               }
            }

            if (message.func_148915_c().getFormattedText().contains("-friendremove ")) {
               var4 = mc.theWorld.getLoadedEntityList().iterator();

               label134:
               while(true) {
                  while(true) {
                     do {
                        do {
                           if (!var4.hasNext()) {
                              break label134;
                           }

                           o = var4.next();
                        } while(!(o instanceof EntityPlayer));

                        mod = (EntityPlayer)o;
                     } while(!message.func_148915_c().getFormattedText().contains("-friendremove " + mod.getName()));

                     var7 = FriendManager.friendsList.iterator();

                     while(var7.hasNext()) {
                        friend2 = (Friend)var7.next();
                        if (message.func_148915_c().getFormattedText().contains(friend2.name)) {
                           if (FriendManager.isFriend(mod.getName())) {
                              FriendManager.removeFriend(mod.getName());
                              ChatUtil.sendChat(mod.getName() + " has been removed from friends.");
                           } else if (!FriendManager.isFriend(mod.getName())) {
                              ChatUtil.sendChat(mod.getName() + " is not friended.");
                           }
                           break;
                        }
                     }
                  }
               }
            }

            if (message.func_148915_c().getFormattedText().contains("-toggle ")) {
               Module[] var14 = (Module[])Client.getModuleManager().getArray();
               int var16 = var14.length;

               for(index = 0; index < var16; ++index) {
                  Module mod2 = var14[index];
                  if (message.func_148915_c().getFormattedText().contains("-toggle " + mod2.getName())) {
                     Iterator var20 = FriendManager.friendsList.iterator();

                     while(var20.hasNext()) {
                        friend2 = (Friend)var20.next();
                        if (message.func_148915_c().getFormattedText().contains(friend2.name)) {
                           mod2.toggle();
                           boolean state = mod2.isEnabled();
                           String var11 = state ? "On" : "Off";
                           break;
                        }
                     }
                  }
               }
            }
         }
      }

   }
}
