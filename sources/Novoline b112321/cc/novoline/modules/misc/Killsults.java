package cc.novoline.modules.misc;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.LoadWorldEvent;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.misc.WindowedFullscreen;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import cc.novoline.utils.Timer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import net.aXg;
import net.minecraft.client.entity.AbstractClientPlayer$1;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S38PacketPlayerListItem$Action;
import net.minecraft.util.MathHelper;

public final class Killsults extends AbstractModule {
   private List lastTargets = new CopyOnWriteArrayList();
   private List killsults = new CopyOnWriteArrayList();
   private final Timer timer = new Timer();
   private Path path;
   private int order;
   private boolean spectator;
   @Property("random-order")
   private final BooleanProperty randomOrder;

   public Killsults(ModuleManager var1) {
      super(var1, "Killsults", "Killsults", EnumModuleType.MISC, "Insults your opponents");
      this.path = Paths.get(this.novoline.getPathString() + "Killsults.novo", new String[0]);
      this.randomOrder = PropertyFactory.booleanFalse();
      Manager.put(new Setting("KS_RANDOM", "Random Order", SettingType.CHECKBOX, this, this.randomOrder));
   }

   public void onDisable() {
      String[] var1 = WindowedFullscreen.a();
      if(!this.lastTargets.isEmpty()) {
         this.lastTargets.clear();
      }

      this.spectator = false;
      this.order = 0;
   }

   public void onEnable() {
      this.loadSults();
   }

   public void loadSults() {
      String[] var1 = WindowedFullscreen.a();

      try {
         BufferedReader var2 = new BufferedReader(new FileReader(this.path.toString()));
         List var3 = (List)var2.lines().collect(Collectors.toList());
         if(!var3.isEmpty()) {
            Iterator var4 = var3.iterator();
            if(var4.hasNext()) {
               String var5 = (String)var4.next();
               if(!this.killsults.contains(var5)) {
                  this.killsults.add(var5);
               }
            }
         }
      } catch (FileNotFoundException var6) {
         var6.printStackTrace();
      }

   }

   private int delay() {
      WindowedFullscreen.a();
      String var2 = this.mc.player.getDisplayName().getFormattedText();
      return !var2.contains("VIP") && !var2.contains("MVP")?3100:1100;
   }

   public int neededDelay() {
      return (int)((float)this.delay() - MathHelper.clamp_float((float)(this.timer.getCurrentMS() - this.timer.getLastMS()), 0.0F, (float)this.delay()));
   }

   private void sendSultMessage(EntityPlayer var1) {
      String[] var2 = WindowedFullscreen.a();
      if(((Boolean)this.randomOrder.get()).booleanValue()) {
         this.order = ThreadLocalRandom.current().nextInt(0, this.killsults.size());
      }

      ++this.order;
      if(this.order >= this.killsults.size()) {
         this.order = 0;
      }

      String var3 = (String)this.killsults.get(this.order);
      String var4 = var3.replace("§", "").replace("%s", var1.getName());
      char[] var5 = var4.toCharArray();
      StringBuilder var6 = new StringBuilder();
      int var8 = var5.length;
      int var9 = 0;
      if(var9 < var8) {
         char var10 = var5[var9];
         var6.append(var10);
         int var11 = 0;
         if(var11 < ThreadLocalRandom.current().nextInt(0, 3)) {
            var6.append('\u05fc');
            ++var11;
         }

         ++var9;
      }

      this.sendPacketNoEvent(new C01PacketChatMessage(var6.toString()));
      this.lastTargets.remove(var1);
      this.timer.reset();
   }

   @EventTarget
   public void onMessage(PacketEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getDirection().equals(PacketDirection.INCOMING)) {
         if(var1.getPacket() instanceof S2FPacketSetSlot) {
            S2FPacketSetSlot var3 = (S2FPacketSetSlot)var1.getPacket();
            if(var3.getItem().getDisplayName().contains("§") && var3.getItem().getDisplayName().contains("Spectator")) {
               this.spectator = true;
            }
         }

         if(!ServerUtils.serverIs(Servers.PRE) && !ServerUtils.serverIs(Servers.LOBBY)) {
            if(this.spectator || !(var1.getPacket() instanceof S38PacketPlayerListItem)) {
               return;
            }

            S38PacketPlayerListItem var7 = (S38PacketPlayerListItem)var1.getPacket();
            Iterator var4 = var7.playersDataList().iterator();
            if(var4.hasNext()) {
               aXg var5 = (aXg)var4.next();
               EntityPlayer var6 = this.mc.world.getPlayerEntityByUUID(var5.a().getId());
               if(var7.getAction().equals(S38PacketPlayerListItem$Action.REMOVE_PLAYER) && !var6.equals(this.mc.player) && var5.a().getName() == null && var6.getHealth() < var6.getMaxHealth() && this.lastTargets.contains(var6)) {
                  if(this.timer.delay((double)this.delay())) {
                     this.sendSultMessage(var6);
                  }

                  this.novoline.getTaskManager().queue(new AbstractClientPlayer$1(this, this.neededDelay(), var6));
               }
            }
         }

         if(this.spectator) {
            this.spectator = false;
         }
      }

   }

   @EventTarget
   public void onLoadWorld(LoadWorldEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(!this.lastTargets.isEmpty()) {
         this.lastTargets.clear();
      }

      this.spectator = false;
      this.order = 0;
   }

   @EventTarget
   public void onUpdate(PlayerUpdateEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(this.isEnabled(KillAura.class) && ((KillAura)this.getModule(KillAura.class)).shouldAttack()) {
         KillAura var3 = (KillAura)this.getModule(KillAura.class);
         if(var3.getTarget() != null && var3.getTarget() instanceof EntityPlayer) {
            EntityPlayer var4 = (EntityPlayer)var3.getTarget();
            if(!this.lastTargets.contains(var4)) {
               this.lastTargets.add(var4);
            }
         }
      }

   }

   public List getKillsults() {
      return this.killsults;
   }

   public Path getPath() {
      return this.path;
   }

   static void a(Killsults var0, EntityPlayer var1) {
      var0.sendSultMessage(var1);
   }

   private static FileNotFoundException a(FileNotFoundException var0) {
      return var0;
   }
}
