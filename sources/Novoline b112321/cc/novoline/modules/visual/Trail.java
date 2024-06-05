package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.visual.HUD;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.aQi;
import net.xf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

public class Trail extends AbstractModule {
   private List x = new CopyOnWriteArrayList();

   public Trail(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.VISUALS, "Trail");
   }

   private boolean a(EntityPlayer var1) {
      int var2 = HUD.e();
      return !var1.isDead && !var1.isInvisible();
   }

   @EventTarget
   public void a(PlayerUpdateEvent var1) {
      HUD.h();
      Iterator var3 = ((List)this.mc.world.getPlayerEntities().stream().filter(this::a).collect(Collectors.toList())).iterator();
      if(var3.hasNext()) {
         EntityPlayer var4 = (EntityPlayer)var3.next();
         if(!this.c(var4) && !var4.isInvisible()) {
            this.x.add(new aQi(this, var4));
         }
      }

      var3 = this.x.iterator();
      if(var3.hasNext()) {
         aQi var6 = (aQi)var3.next();
         if(!this.mc.world.getPlayerEntities().contains(var6.b())) {
            var6.a().clear();
            this.x.remove(var6);
         }

         if(aQi.a(var6).getLastTickDistance() > 0.0D) {
            var6.a().add(new xf(this, aQi.a(var6)));
         }
      }

   }

   @EventTarget
   public void a(Render3DEvent var1) {
      HUD.h();
      HUD var3 = (HUD)this.getModule(HUD.class);
      Iterator var4 = this.x.iterator();
      if(var4.hasNext()) {
         aQi var5 = (aQi)var4.next();
         var5.a(var3);
      }

   }

   private boolean c(EntityPlayer var1) {
      HUD.e();
      Iterator var3 = this.x.iterator();
      if(var3.hasNext()) {
         aQi var4 = (aQi)var3.next();
         if(var4.b() == var1) {
            return true;
         }
      }

      return false;
   }

   private void b(EntityPlayer var1) {
      this.x.removeIf(Trail::lambda$removeByEntity$0);
   }

   private static boolean lambda$removeByEntity$0(EntityPlayer var0, aQi var1) {
      return var1.b() == var0;
   }

   static Minecraft i(Trail var0) {
      return var0.mc;
   }

   static Minecraft b(Trail var0) {
      return var0.mc;
   }

   static Minecraft c(Trail var0) {
      return var0.mc;
   }

   static Minecraft d(Trail var0) {
      return var0.mc;
   }

   static Minecraft h(Trail var0) {
      return var0.mc;
   }

   static Minecraft e(Trail var0) {
      return var0.mc;
   }

   static Minecraft g(Trail var0) {
      return var0.mc;
   }

   static Minecraft f(Trail var0) {
      return var0.mc;
   }

   static Minecraft a(Trail var0) {
      return var0.mc;
   }
}
