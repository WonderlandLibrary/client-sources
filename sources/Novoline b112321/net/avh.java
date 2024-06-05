package net;

import cc.novoline.events.events.PacketDirection;
import java.util.function.Supplier;
import net.UW;
import net.VN;
import net.WB;
import net.a2V;
import net.a6d;
import net.aE8;
import net.aEE;
import net.aEs;
import net.aEu;
import net.aSt;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap9;
import net.as0;
import net.ava;
import net.axu;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S2BPacketChangeGameState;

public final class avh extends as0 {
   @VN("time-mode")
   private final aEs z = axu.a("Static").a(new String[]{"Static", "Cycle"});
   @VN("time")
   private final aEE C = (aEE)((aEE)axu.a(Double.valueOf(12.0D)).d(Double.valueOf(1.0D))).c(Double.valueOf(24.0D));
   @VN("cycle-speed")
   private final aE8 A = (aE8)((aE8)axu.b(Integer.valueOf(5)).d(Integer.valueOf(1))).c(Integer.valueOf(20));
   @VN("weather-mode")
   private final aEs x = axu.a("Clean").a(new String[]{"Clean", "Rain", "Thunder", "Snowfall", "Snowstorm"});
   @VN("weather-control")
   private final aEu B = axu.g();
   private long y;

   public avh(UW var1) {
      super(var1, "Atmosphere", a2V.VISUALS);
      ae9.a(new adZ("WORLD_TIME_MODE", "Time Mode", a6d.COMBOBOX, this, this.z));
      ae9.a(new adZ("WORLD_TIME", "Hour", a6d.SLIDER, this, this.C, 0.5D, this::lambda$new$0));
      ae9.a(new adZ("WORLD_CYCLE_SPEED", "Cycle Speed", a6d.SLIDER, this, this.A, 1.0D, this::lambda$new$1));
      ae9.a(new adZ("WEATHER_CONTROL", "Weather control", a6d.CHECKBOX, this, this.B));
      a6d var10004 = a6d.COMBOBOX;
      aEs var10006 = this.x;
      aEu var10007 = this.B;
      this.B.getClass();
      ae9.a(new adZ("WEATHER_MODE", "Weather", var10004, this, var10006, var10007::a));
   }

   @agu
   public void a(WB var1) {
      int var2 = ava.e();
      if(this.w.theWorld != null) {
         if(((Boolean)this.B.a()).booleanValue()) {
            String var3 = (String)this.x.a();
            byte var4 = -1;
            switch(var3.hashCode()) {
            case 756396958:
               if(!var3.equals("Snowfall")) {
                  break;
               }

               var4 = 0;
            case 2539444:
               if(!var3.equals("Rain")) {
                  break;
               }

               var4 = 1;
            case 1986044198:
               if(!var3.equals("Snowstorm")) {
                  break;
               }

               var4 = 2;
            case 329757892:
               if(var3.equals("Thunder")) {
                  var4 = 3;
               }
            }

            switch(var4) {
            case 0:
            case 1:
               this.w.theWorld.setRainStrength(1.0F);
               this.w.theWorld.setThunderStrength(0.0F);
            case 2:
            case 3:
               this.w.theWorld.setRainStrength(1.0F);
               this.w.theWorld.setThunderStrength(1.0F);
            default:
               this.w.theWorld.setRainStrength(0.0F);
               this.w.theWorld.setThunderStrength(0.0F);
            }
         }

         if(this.z.a("Static")) {
            this.w.theWorld.setWorldTime(((Double)this.C.a()).longValue() * 1000L);
         }
      }

   }

   @agu
   public void a(aSt var1) {
      int var2 = ava.h();
      if(this.z.a("Cycle")) {
         this.y += (long)((Integer)this.A.a()).intValue();
         this.w.theWorld.setWorldTime(this.y);
         if(this.y > 24000L) {
            this.y = 0L;
         }
      }

   }

   @agu
   public void b(ap9 var1) {
      int var2 = ava.h();
      if(var1.a().equals(PacketDirection.INCOMING)) {
         if(((Boolean)this.B.a()).booleanValue() && var1.d() instanceof S2BPacketChangeGameState) {
            S2BPacketChangeGameState var3 = (S2BPacketChangeGameState)var1.d();
            if(var3.getGameState() == 1 || var3.getGameState() == 2 || var3.getGameState() == 7 || var3.getGameState() == 8) {
               var1.setCancelled(true);
            }
         }

         if(var1.d() instanceof S03PacketTimeUpdate) {
            var1.setCancelled(true);
         }
      }

   }

   public aEs a() {
      return this.x;
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(this.z.a("Cycle"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.z.a("Static"));
   }
}
