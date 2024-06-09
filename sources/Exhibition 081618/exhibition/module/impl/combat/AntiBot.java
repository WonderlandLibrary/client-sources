package exhibition.module.impl.combat;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventAttack;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventPacket;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.module.impl.movement.Debug;
import exhibition.util.Timer;
import exhibition.util.misc.ChatUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class AntiBot extends Module {
   public static String MODE = "MODE";
   private String DEAD = "DEAD";
   private String DEBUG = "DEBUG";
   private Timer timer = new Timer();
   private static List invalid = new ArrayList();

   public AntiBot(ModuleData data) {
      super(data);
      this.settings.put(this.DEAD, new Setting(this.DEAD, true, "Removes dead bodies from the game."));
      this.settings.put(this.DEBUG, new Setting(this.DEBUG, false, "Show debug information in console/dev log."));
      this.settings.put(MODE, new Setting(MODE, new Options("Mode", "Hypixel", new String[]{"Hypixel", "Packet", "Mineplex"}), "Check method for bots."));
   }

   public static List getInvalid() {
      return invalid;
   }

   public void onEnable() {
      invalid.clear();
   }

   public void onDisable() {
      invalid.clear();

      try {
         if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP != null && mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") || mc.getCurrentServerData().serverIP.toLowerCase().contains("mineplex")) {
            ChatUtil.printChat("§4[§cE§4]§8 AntiBot was kept enabled for your protection.");
            this.toggle();
         }
      } catch (Exception var2) {
         ;
      }

   }

   @RegisterEvent(
      events = {EventPacket.class, EventMotionUpdate.class, EventAttack.class}
   )
   public void onEvent(Event event) {
      boolean var10000;
      if (((Boolean)((Setting)this.settings.get(this.DEBUG)).getValue()).booleanValue() && Client.getModuleManager().isEnabled(Debug.class)) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      String currentSetting = ((Options)((Setting)this.settings.get(MODE)).getValue()).getSelected();
      double groundVelocity;
      if (event instanceof EventPacket && currentSetting.equalsIgnoreCase("Packet")) {
         EventPacket ep = (EventPacket)event;
         if (ep.isIncoming() && ep.getPacket() instanceof S0CPacketSpawnPlayer) {
            S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer)ep.getPacket();
            double entX = (double)(packet.func_148942_f() / 32);
            double entY = (double)(packet.func_148949_g() / 32);
            double entZ = (double)(packet.func_148946_h() / 32);
            groundVelocity = mc.thePlayer.posX;
            double posY = mc.thePlayer.posY;
            double posZ = mc.thePlayer.posZ;
            double var7 = groundVelocity - entX;
            double var9 = posY - entY;
            double var11 = posZ - entZ;
            float distance = MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
            if (distance <= 17.0F && entY > mc.thePlayer.posY + 1.0D && mc.thePlayer.posX != entX && mc.thePlayer.posY != entY && mc.thePlayer.posZ != entZ) {
               ep.setCancelled(true);
            }
         }
      }

      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         this.setSuffix(currentSetting);
         EntityPlayer ent;
         Iterator var27;
         Object o;
         if (em.isPre()) {
            if (mc.getIntegratedServer() == null) {
               if (mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") && !currentSetting.equals("Hypixel")) {
                  ((Options)((Setting)this.settings.get(MODE)).getValue()).setSelected("Hypixel");
                  ChatUtil.printChat("§4[§cE§4]§8 AntiBot has been set to the proper mode.");
               } else if (mc.getCurrentServerData().serverIP.toLowerCase().contains("mineplex") && !currentSetting.equals("Mineplex")) {
                  ((Options)((Setting)this.settings.get(MODE)).getValue()).setSelected("Mineplex");
                  ChatUtil.printChat("§4[§cE§4]§8 AntiBot has been set to the proper mode.");
               }
            }

            if (((Boolean)((Setting)this.settings.get(this.DEAD)).getValue()).booleanValue()) {
               var27 = mc.theWorld.loadedEntityList.iterator();

               label231:
               while(true) {
                  do {
                     do {
                        if (!var27.hasNext()) {
                           break label231;
                        }

                        o = var27.next();
                     } while(!(o instanceof EntityPlayer));

                     ent = (EntityPlayer)o;

                     assert ent != mc.thePlayer;
                  } while(!ent.isPlayerSleeping() && !ent.isDead);

                  mc.theWorld.removeEntity(ent);
               }
            }
         }

         if (em.isPre() && !currentSetting.equalsIgnoreCase("Packet")) {
            if (this.timer.delay(1000.0F)) {
               invalid.clear();
               this.timer.reset();
            }

            var27 = mc.theWorld.getLoadedEntityList().iterator();

            while(true) {
               while(true) {
                  do {
                     do {
                        do {
                           if (!var27.hasNext()) {
                              return;
                           }

                           o = var27.next();
                        } while(!(o instanceof EntityPlayer));

                        ent = (EntityPlayer)o;
                     } while(ent == mc.thePlayer);

                     boolean onGround = mc.theWorld.getBlockState(new BlockPos(ent.posX, ent.posY - 0.5D, ent.posZ)).getBlock().getMaterial() != Material.air;
                     if (onGround) {
                        ++ent.ticksOnGround;
                     }
                  } while(invalid.contains(ent));

                  byte var31 = -1;
                  switch(currentSetting.hashCode()) {
                  case -1298025822:
                     if (currentSetting.equals("Mineplex")) {
                        var31 = 1;
                     }
                     break;
                  case -1248403467:
                     if (currentSetting.equals("Hypixel")) {
                        var31 = 0;
                     }
                  }

                  switch(var31) {
                  case 0:
                     String str = ent.getDisplayName().getFormattedText();
                     groundVelocity = Math.abs(ent.motionX + ent.motionZ);
                     if ((groundVelocity >= 5.0D || Math.abs(ent.lastTickPosY - ent.posY) <= 5.0D) && (groundVelocity <= 5.0D || Math.abs(ent.lastTickPosY - ent.posY) >= 5.0D)) {
                        var10000 = false;
                     } else {
                        var10000 = true;
                     }

                     if (ent.ticksOnGround < 10) {
                        double var15 = ent.getPositionVector().distanceTo(new Vec3(ent.lastTickPosX, ent.lastTickPosY, ent.lastTickPosZ));
                     }

                     if ((str.equalsIgnoreCase(ent.getName() + "§r") || str.equalsIgnoreCase(ent.getName()) || str.contains("NPC") || !getTabPlayerList().contains(ent)) && str.equalsIgnoreCase(ent.getName() + "§r") && !getTabPlayerList().contains(ent) && mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") && mc.getIntegratedServer() == null && ent.ticksExisted < 10 && ent.getDistance(ent.lastTickPosX, ent.lastTickPosY, ent.lastTickPosZ) > 10.0D) {
                        ;
                     }

                     if (ent.isInvisible() && ent.ticksOnGround < 5 && str.equalsIgnoreCase(ent.getName() + "§r")) {
                        mc.theWorld.removeEntity(ent);
                     } else if (str.equalsIgnoreCase(ent.getName() + "§r") || str.equalsIgnoreCase(ent.getName()) || str.contains("NPC")) {
                        if (str.equalsIgnoreCase(ent.getName() + "§r") && !getTabPlayerList().contains(ent) && mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") && mc.getIntegratedServer() == null && (!ent.onGround || !ent.isCollidedVertically) && Math.abs(ent.posY - mc.thePlayer.posY) > 7.0D) {
                           mc.theWorld.removeEntity(ent);
                        } else {
                           this.timer.reset();
                           invalid.add(ent);
                        }
                     }
                     break;
                  case 1:
                     if (ent.getHealth() >= 0.0F) {
                        invalid.add(ent);
                     }

                     if (ent.isPlayerSleeping()) {
                        invalid.add(ent);
                     }
                  }
               }
            }
         }
      } else if (event instanceof EventAttack) {
         EventAttack eventAttack = (EventAttack)event.cast();
         if (eventAttack.isPreAttack() && eventAttack.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)eventAttack.getEntity();
            if (invalid.contains(player)) {
               invalid.remove(player);
            }
         }
      }

   }

   public static List getTabPlayerList() {
      NetHandlerPlayClient var4 = mc.thePlayer.sendQueue;
      List list = new ArrayList();
      List players = GuiPlayerTabOverlay.field_175252_a.sortedCopy(var4.func_175106_d());
      Iterator var3 = players.iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         NetworkPlayerInfo info = (NetworkPlayerInfo)o;
         if (info != null) {
            list.add(mc.theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
         }
      }

      return list;
   }
}
