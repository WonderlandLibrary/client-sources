package net;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.ModuleManager$ModuleCreator;
import cc.novoline.modules.configurations.holder.CreatingModuleHolder;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import cc.novoline.utils.notifications.NotificationType;
import java.util.ArrayList;
import java.util.Iterator;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.CP;
import net.Gb;
import net.Uz;
import net.aIj;
import net.aIu;
import net.aOR;
import net.aSv;
import net.acE;
import net.arW;
import net.axW;
import net.g2;
import net.o0;
import net.u6;
import net.xB;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C02PacketUseEntity$Action;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.network.play.client.C0BPacketEntityAction$Action;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import org.lwjgl.opengl.GL11;

public class K6 {
   public String c;
   public String e;
   public boolean i;
   public ScriptEngine h;
   public Invocable d;
   public CreatingModuleHolder g;
   public AbstractModule f;
   private static int[] b;

   public K6(String var1, String var2) {
      this.c = var2;
      this.e = var1;
      ScriptEngineManager var4 = new ScriptEngineManager();
      ScriptEngine var5 = var4.getEngineByName("nashorn");
      this.h = var5;
      this.d = (Invocable)var5;
      var5.put("script", this);
      var5.put("COMBAT", EnumModuleType.COMBAT);
      b();
      var5.put("MOVEMENT", EnumModuleType.MOVEMENT);
      var5.put("PLAYER", EnumModuleType.PLAYER);
      var5.put("MISC", EnumModuleType.MISC);
      var5.put("VISUALS", EnumModuleType.VISUALS);
      var5.put("EXPLOITS", EnumModuleType.EXPLOITS);
      var5.put("connection", new CP(var5));
      var5.put("player", new g2());
      var5.put("world", new aIj());
      var5.put("angle_util", new aOR());
      var5.put("entity_util", new u6());
      var5.put("timer_util", new aIu());
      var5.put("file_util", new axW());
      var5.put("web_util", new Gb());
      var5.put("game", new xB());
      var5.put("client", new Uz());
      var5.put("render_util", new o0());
      var5.put("ATTACK", C02PacketUseEntity$Action.ATTACK);
      var5.put("INTERACT", C02PacketUseEntity$Action.INTERACT);
      var5.put("INTERACT_AT", C02PacketUseEntity$Action.INTERACT_AT);
      var5.put("START_SNEAKING", C0BPacketEntityAction$Action.START_SNEAKING);
      var5.put("STOP_SNEAKING", C0BPacketEntityAction$Action.STOP_SNEAKING);
      var5.put("START_SPRINTING", C0BPacketEntityAction$Action.START_SPRINTING);
      var5.put("STOP_SPRINTING", C0BPacketEntityAction$Action.STOP_SPRINTING);
      var5.put("STOP_SLEEPING", C0BPacketEntityAction$Action.STOP_SLEEPING);
      var5.put("RIDING_JUMP", C0BPacketEntityAction$Action.RIDING_JUMP);
      var5.put("OPEN_INVENTORY", C0BPacketEntityAction$Action.OPEN_INVENTORY);
      var5.put("UP", EnumFacing.UP);
      var5.put("DOWN", EnumFacing.DOWN);
      var5.put("SOUTH", EnumFacing.SOUTH);
      var5.put("NORTH", EnumFacing.NORTH);
      var5.put("WEST", EnumFacing.WEST);
      var5.put("EAST", EnumFacing.EAST);
      var5.put("START_DESTROY_BLOCK", C07PacketPlayerDigging$Action.START_DESTROY_BLOCK);
      var5.put("ABORT_DESTROY_BLOCK", C07PacketPlayerDigging$Action.ABORT_DESTROY_BLOCK);
      var5.put("STOP_DESTROY_BLOCK", C07PacketPlayerDigging$Action.STOP_DESTROY_BLOCK);
      var5.put("DROP_ALL_ITEMS", C07PacketPlayerDigging$Action.DROP_ALL_ITEMS);
      var5.put("DROP_ITEM", C07PacketPlayerDigging$Action.DROP_ITEM);
      var5.put("RELEASE_USE_ITEM", C07PacketPlayerDigging$Action.RELEASE_USE_ITEM);
      var5.put("INFO", NotificationType.INFO);
      var5.put("SUCCESS", NotificationType.SUCCESS);
      var5.put("WARNING", NotificationType.WARNING);
      var5.put("ERROR", NotificationType.ERROR);
      var5.put("GL11", GL11.class);
   }

   public String d() {
      return this.c;
   }

   public String g() {
      return this.e;
   }

   public void a(String var1) {
      this.c = var1;
   }

   public boolean e() {
      return this.i;
   }

   public void a(boolean var1) {
      int[] var2 = b();
      this.i = var1;
      this.c();
   }

   public void c() {
      b();
      EventManager.unregister(this);
      Iterator var2 = (new ArrayList(Novoline.getInstance().getModuleManager().getModuleManager().values())).iterator();
      if(var2.hasNext()) {
         ModuleHolder var3 = (ModuleHolder)var2.next();
         if(var3.getName().equals(this.e + "-SCRIPT")) {
            var3.getModule().setEnabled(false);
            Iterator var4 = Manager.getSettingsByMod(this.f).iterator();
            if(var4.hasNext()) {
               Setting var5 = (Setting)var4.next();
               Manager.getSettingList().remove(var5);
            }

            Novoline.getInstance().getModuleManager().getAbstractModules().remove(this.f);
            Novoline.getInstance().getModuleManager().getModuleManager().remove(this.e + "-SCRIPT");
            Novoline.getInstance().u().b().remove(this.f);
         }
      }

   }

   public void f() {
      try {
         this.h.eval(this.d());
      } catch (ScriptException var2) {
         Minecraft.getInstance().ingameGUI.n().a((IChatComponent)(new ChatComponentText(EnumChatFormatting.RED + var2.getMessage().split("\tat")[0])));
      }

   }

   @aSv
   public arW registerModule(String var1, EnumModuleType var2) {
      this.c();
      EventManager.register(this);
      arW var4 = new arW(Novoline.getInstance().getModuleManager(), var1, var2, this.h);
      ModuleManager$ModuleCreator var5 = K6::lambda$registerModule$0;
      b();
      Novoline.getInstance().getModuleManager().a(this.e + "-SCRIPT", var5, false);
      Novoline.getInstance().getModuleManager().getAbstractModules().add(var4);
      this.f = var4;
      Novoline.getInstance().u().b().add(var4);
      if(acE.b() == null) {
         b(new int[3]);
      }

      return var4;
   }

   public AbstractModule a() {
      return this.f;
   }

   private static AbstractModule lambda$registerModule$0(arW var0, ModuleManager var1) {
      return var0;
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      b((int[])null);
   }
}
