package cc.novoline.modules.misc;

import cc.novoline.Novoline;
import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.LoadWorldEvent;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.misc.AutoHypixel$1;
import cc.novoline.modules.misc.Spammer;
import cc.novoline.modules.misc.WindowedFullscreen;
import cc.novoline.utils.Channels;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import cc.novoline.utils.notifications.NotificationType;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.EnumChatFormatting;

public class AutoHypixel extends AbstractModule {
   @Property("reconnect")
   private final BooleanProperty reconnect = PropertyFactory.booleanFalse();
   @Property("auto-play")
   private final BooleanProperty auto_play = PropertyFactory.booleanFalse();
   @Property("delay")
   private final IntProperty delay = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(3)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(10));
   @Property("auto-pit-list")
   private final ListProperty x = PropertyFactory.createList((Object)"Quick Math").acceptableValues((Object[])(new String[]{"Quick Math", "Target Bounty"}));
   @Property("craft-list")
   private final ListProperty crafts = PropertyFactory.createList((Object)"Planks").acceptableValues((Object[])(new String[]{"Planks"}));
   @Property("min-bounty")
   private final IntProperty min_bounty = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(500)).minimum(Integer.valueOf(50))).maximum(Integer.valueOf(5000));
   @Property("auto-gg")
   private final BooleanProperty auto_gg = PropertyFactory.booleanFalse();
   @Property("message")
   private final StringProperty message = PropertyFactory.createString("GG");
   @Property("auto-sw-list")
   private final ListProperty z = PropertyFactory.createList((Object)"Auto Cage Teleport").acceptableValues((Object[])(new String[]{"Auto Cage Teleport"}));
   private List entities = new CopyOnWriteArrayList();
   private boolean F;
   private boolean B;

   public AutoHypixel(ModuleManager var1) {
      super(var1, EnumModuleType.MISC, "AutoHypixel", "Auto Hypixel");
      Manager.put(new Setting("AH_CRAFTS", "Auto Crafts", SettingType.SELECTBOX, this, this.crafts));
      Manager.put(new Setting("AH_AUTO_PIT", "Auto Pit", SettingType.SELECTBOX, this, this.x));
      Manager.put(new Setting("AH_BOUNTY", "Minimal Bounty", SettingType.SLIDER, this, this.min_bounty, 50.0D, this::lambda$new$0));
      Manager.put(new Setting("AH_AUTO_SW", "Auto SW", SettingType.SELECTBOX, this, this.z));
      Manager.put(new Setting("AH_AUTO_REC", "Reconnect", SettingType.CHECKBOX, this, this.reconnect));
      WindowedFullscreen.a();
      Manager.put(new Setting("AH_AUTO_AP", "Auto Play", SettingType.CHECKBOX, this, this.auto_play));
      SettingType var10004 = SettingType.SLIDER;
      IntProperty var10006 = this.delay;
      BooleanProperty var10008 = this.auto_play;
      this.auto_play.getClass();
      Manager.put(new Setting("AH_AP_DELAY", "Delay (Seconds)", var10004, this, var10006, 1.0D, var10008::get));
      Manager.put(new Setting("AH_AUTO_GG", "Auto GG", SettingType.CHECKBOX, this, this.auto_gg));
      var10004 = SettingType.TEXTBOX;
      StringProperty var10007 = this.message;
      var10008 = this.auto_gg;
      this.auto_gg.getClass();
      Manager.put(new Setting("AH_AUTO_GG_TEXT", "Message", var10004, this, "Message text", var10007, var10008::get));
   }

   @EventTarget
   public void onPreUpdate(MotionUpdateEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getState().equals(EventState.PRE)) {
         Iterator var3 = this.entities.iterator();
         if(var3.hasNext()) {
            Entity var4 = (Entity)var3.next();
            if(!this.mc.world.getPlayerEntities().contains(var4)) {
               this.entities.remove(var4);
               this.novoline.getPlayerManager().removeType(var4.getName(), PlayerManager$EnumPlayerType.TARGET);
            }
         }

         if(this.crafts.contains("Planks")) {
            int var9 = 0;
            if(var9 < 36) {
               ItemStack var13 = this.mc.player.inventory.getStackInSlot(var9);
               if(var13 != null) {
                  int var5 = ThreadLocalRandom.current().nextInt(1, 10);
                  if(Item.b(var13.getItem()) == 17 || Item.b(var13.getItem()) == 162) {
                     if(var9 < 9) {
                        this.mc.at.b(0, 1, var9, 2, this.mc.player);
                        this.sendPacketNoEvent(new C0EPacketClickWindow(var5, -999, 0, -4, (ItemStack)null, this.mc.player.openContainer.d()));
                        this.mc.at.b(0, 0, 0, 1, this.mc.player);
                        this.sendPacketNoEvent(new C0EPacketClickWindow(var5, -999, 0, -4, (ItemStack)null, this.mc.player.openContainer.d()));
                        this.sendPacketNoEvent(new C0DPacketCloseWindow(var5));
                     }

                     int var6 = 0;
                     if(var6 < 9) {
                        ItemStack var7 = this.mc.player.inventory.getStackInSlot(var6);
                        if(var7 == null) {
                           this.mc.at.b(0, var9, var6, 2, this.mc.player);
                           this.sendPacketNoEvent(new C0EPacketClickWindow(var5, -999, 0, -4, (ItemStack)null, this.mc.player.openContainer.d()));
                        }

                        ++var6;
                     }
                  }
               }

               ++var9;
            }
         }

         if(this.x.contains("Target Bounty")) {
            var3 = this.mc.world.getPlayerEntities().iterator();
            if(var3.hasNext()) {
               EntityPlayer var14 = (EntityPlayer)var3.next();
               if(!var14.equals(this.mc.player)) {
                  String var16 = var14.getDisplayName().getUnformattedText();
                  String var19 = var14.getName();
                  if(var16.contains("§6§l")) {
                     String[] var21 = var16.split(" ");
                     if(var21.length > 2) {
                        int var8 = Integer.parseInt(var21[var21.length - 1].replace("§6§l", "").replace("g", ""));
                        if(var8 >= ((Integer)this.min_bounty.get()).intValue() && !this.entities.contains(var14) && !this.novoline.getPlayerManager().hasType(var14.getName(), PlayerManager$EnumPlayerType.FRIEND)) {
                           this.entities.add(var14);
                           this.novoline.getNotificationManager().pop("Found a bountied player", var14.getName() + " has " + EnumChatFormatting.GOLD + var8 + "g" + EnumChatFormatting.RESET + " on him!", 7000, NotificationType.INFO);
                        }
                     }
                  }
               }
            }

            var3 = this.entities.iterator();
            if(var3.hasNext()) {
               Entity var15 = (Entity)var3.next();
               if(this.novoline.getPlayerManager().hasType(var15.getName(), PlayerManager$EnumPlayerType.FRIEND)) {
                  this.entities.remove(var15);
                  this.novoline.getPlayerManager().removeType(var15.getName(), PlayerManager$EnumPlayerType.TARGET);
               }

               String var17 = var15.getDisplayName().getUnformattedText();
               String var20 = var15.getName();
               this.novoline.getPlayerManager().setType(var15.getName(), PlayerManager$EnumPlayerType.TARGET);
               if(var17.contains("§6§l")) {
                  String[] var22 = var17.split(" ");
                  if(var22.length < 3) {
                     this.entities.remove(var15);
                     this.novoline.getPlayerManager().removeType(var15.getName(), PlayerManager$EnumPlayerType.TARGET);
                  }

                  int var23 = Integer.parseInt(var22[var22.length - 1].replace("§6§l", "").replace("g", ""));
                  if(var23 < ((Integer)this.min_bounty.get()).intValue()) {
                     this.entities.remove(var15);
                     this.novoline.getPlayerManager().removeType(var15.getName(), PlayerManager$EnumPlayerType.TARGET);
                  }
               }

               this.entities.remove(var15);
               this.novoline.getPlayerManager().removeType(var15.getName(), PlayerManager$EnumPlayerType.TARGET);
            }
         }
      }

   }

   @EventTarget
   public void a(LoadWorldEvent var1) {
      this.F = true;
   }

   @EventTarget
   public void a(PlayerUpdateEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(this.z.contains("Auto Cage Teleport") && this.mc.world.getScoreboard().getObjectiveInDisplaySlot(1).getDisplayName().contains("SKYWARS") && this.F && this.mc.player.onGround && this.mc.player.ticksExisted > 25 && ServerUtils.serverIs(Servers.PRE)) {
         this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY - 3.0D, this.mc.player.posZ);
         this.F = false;
      }

   }

   @EventTarget
   public void c(PacketEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getDirection().equals(PacketDirection.INCOMING)) {
         if(((Boolean)this.reconnect.get()).booleanValue()) {
            this.reconnect(var1);
         }

         if(((Boolean)this.auto_play.get()).booleanValue()) {
            this.autoplay(var1);
         }

         if(this.x.contains("Quick Math")) {
            this.quickmath(var1);
         }

         if(((Boolean)this.auto_gg.get()).booleanValue()) {
            this.autogg(var1);
         }
      }

   }

   private void reconnect(PacketEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getPacket() instanceof S02PacketChat) {
         S02PacketChat var3 = (S02PacketChat)var1.getPacket();
         String var4 = var3.getChatComponent().getUnformattedText();
         if(var4.contains("Flying or related")) {
            this.sendPacketNoEvent(new C01PacketChatMessage("/back"));
         }
      }

   }

   private void autoplay(PacketEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getPacket() instanceof S02PacketChat) {
         S02PacketChat var3 = (S02PacketChat)var1.getPacket();
         String var4 = var3.getChatComponent().toString().split("action=RUN_COMMAND, value=\'")[1];
         if(var4.startsWith("/play ")) {
            String var5 = var4.split("\'}")[0];
            Novoline.getInstance().getNotificationManager().pop("Sending you to the next game in ", ((Integer)this.delay.get()).intValue() * 1000, NotificationType.INFO);
            Novoline.getInstance().getTaskManager().queue(new AutoHypixel$1(this, ((Integer)this.delay.get()).intValue() * 1000, var5));
         }
      }

   }

   private void autogg(PacketEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getPacket() instanceof S45PacketTitle) {
         S45PacketTitle var3 = (S45PacketTitle)var1.getPacket();
         if(!this.isEnabled(Spammer.class) && var3.getMessage().getUnformattedText().equals("§6§lVICTORY!")) {
            if(((String)this.message.get()).startsWith("/")) {
               this.sendPacketNoEvent(new C01PacketChatMessage((String)this.message.get()));
            }

            if(!ServerUtils.isHypixel() || !ServerUtils.channelIs(Channels.PM)) {
               if(((String)this.message.get()).toLowerCase().startsWith("gg")) {
                  this.sendPacketNoEvent(new C01PacketChatMessage((String)this.message.get()));
               }

               this.sendPacket(new C01PacketChatMessage((String)this.message.get()));
            }
         }
      }

   }

   private void quickmath(PacketEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getPacket() instanceof S02PacketChat) {
         S02PacketChat var3 = (S02PacketChat)var1.getPacket();
         String var4 = var3.getChatComponent().getUnformattedText();
         if(var4.split(":").length > 1) {
            return;
         }

         if(var4.contains("QUICK MATHS! Solve:")) {
            String[] var5 = var4.split("Solve: ");
            ScriptEngineManager var6 = new ScriptEngineManager();
            ScriptEngine var7 = var6.getEngineByName("JavaScript");

            try {
               this.sendPacketNoEvent(new C01PacketChatMessage(var7.eval(var5[1].replace("x", "*")).toString()));
            } catch (ScriptException var9) {
               var9.printStackTrace();
            }
         }
      }

   }

   public void onDisable() {
      String[] var1 = WindowedFullscreen.a();
      if(!this.entities.isEmpty()) {
         this.entities.clear();
      }

   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.x.contains("Target Bounty"));
   }

   private static ScriptException a(ScriptException var0) {
      return var0;
   }
}
