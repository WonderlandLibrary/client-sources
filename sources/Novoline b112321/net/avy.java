package net;

import cc.novoline.events.events.EventState;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.Locale;
import java.util.function.Supplier;
import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aE8;
import net.aEs;
import net.aEu;
import net.aG1;
import net.aSt;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.ava;
import net.axu;
import net.cz;
import org.lwjgl.input.Mouse;

public class avy extends as0 {
   @VN("mc-lock")
   aEu D = axu.a(Boolean.valueOf(true));
   @VN("view-clip")
   aEu z = axu.a(Boolean.valueOf(true));
   @VN("no-hurt-cam")
   aEu y = axu.a(Boolean.valueOf(true));
   @VN("motion-blur")
   aEu x = axu.a(Boolean.valueOf(true));
   @VN("amount")
   private final aE8 B = (aE8)((aE8)axu.b(Integer.valueOf(1)).d(Integer.valueOf(1))).c(Integer.valueOf(10));
   @VN("mouse-button")
   private aEs C = axu.a("MB").a(new String[]{"MB", "ARB", "ALB"});
   private boolean A;

   public avy(UW var1) {
      super(var1, a2V.VISUALS, "Camera");
      ae9.a(new adZ("CAMERA_MC_LOCK", "Camera Lock", a6d.CHECKBOX, this, this.D));
      a6d var10004 = a6d.COMBOBOX;
      aEs var10006 = this.C;
      aEu var10007 = this.D;
      this.D.getClass();
      ae9.a(new adZ("CAMERA_BUTTON", "Camera Button", var10004, this, var10006, var10007::a));
      ae9.a(new adZ("CAMERA_VC", "View Clip", a6d.CHECKBOX, this, this.z));
      ae9.a(new adZ("CAMERA_NHC", "No Hurt Cam", a6d.CHECKBOX, this, this.y));
      ae9.a(new adZ("CAMERA_MB", "Motion Blur", a6d.CHECKBOX, this, this.x));
      var10004 = a6d.SLIDER;
      aE8 var3 = this.B;
      aEu var10008 = this.x;
      this.x.getClass();
      ae9.a(new adZ("CAMERA_BA", "Blur Amount", var10004, this, var3, 1.0D, var10008::a));
   }

   public void n() {
      int var1 = ava.h();
      if(((Boolean)this.x.a()).booleanValue()) {
         this.w.entityRenderer.ad = null;
         if(this.w.theWorld != null) {
            try {
               this.w.entityRenderer.e();
            } catch (IOException var3) {
               var3.printStackTrace();
            }
         }
      }

   }

   public void c() {
      this.w.entityRenderer.ad = null;
   }

   @agu
   public void a(cz var1) {
      int var2 = ava.e();
      if(var1.a().equals("CAMERA_MB")) {
         if(((Boolean)this.x.a()).booleanValue()) {
            this.w.entityRenderer.ad = null;
            if(this.w.theWorld == null) {
               return;
            }

            try {
               this.w.entityRenderer.e();
               return;
            } catch (IOException var4) {
               var4.printStackTrace();
            }
         }

         this.w.entityRenderer.ad = null;
      }

   }

   public JsonObject c() {
      String var1 = "{\"targets\":[\"swap\",\"previous\"],\"passes\":[{\"name\":\"phosphor\",\"intarget\":\"minecraft:main\",\"outtarget\":\"swap\",\"auxtargets\":[{\"name\":\"PrevSampler\",\"id\":\"previous\"}],\"uniforms\":[{\"name\":\"Phosphor\",\"values\":[%.2f, %.2f, %.2f]}]},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"minecraft:main\"}]}";
      double var2 = 0.7D + (double)((Integer)this.B.a()).intValue() / 100.0D * 3.0D - 0.01D;
      return (new JsonParser()).parse(String.format(Locale.ENGLISH, var1, new Object[]{Double.valueOf(var2), Double.valueOf(var2), Double.valueOf(var2)})).getAsJsonObject();
   }

   @agu
   public void a(aSt var1) {
      int var2 = ava.h();
      if(((Boolean)this.x.a()).booleanValue() && this.w.entityRenderer.ad == null && this.w.theWorld != null) {
         try {
            this.w.entityRenderer.e();
         } catch (IOException var4) {
            var4.printStackTrace();
         }
      }

   }

   public int b() {
      int var1 = ava.h();
      return this.C.a("MB")?2:(this.C.a("ARB")?3:(this.C.a("ALB")?4:0));
   }

   @agu
   public void a(aG1 var1) {
      int var2 = ava.h();
      if(var1.h() == EventState.PRE && ((Boolean)this.D.a()).booleanValue()) {
         if(Mouse.isButtonDown(this.b())) {
            this.w.gameSettings.thirdPersonView = 1;
            this.A = false;
         }

         if(!this.A) {
            this.w.gameSettings.thirdPersonView = 0;
            this.A = true;
         }
      }

   }

   public aEu a() {
      return this.z;
   }

   public aEu d() {
      return this.y;
   }

   public boolean f() {
      int var1 = ava.e();
      return ((Boolean)this.D.a()).booleanValue() && Mouse.isButtonDown(this.b());
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
