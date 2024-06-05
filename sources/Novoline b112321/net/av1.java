package net;

import cc.novoline.events.events.EventState;
import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aEs;
import net.aG1;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.avq;
import net.axu;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;

public class av1 extends as0 {
   @VN("mouse-button")
   private final aEs y = axu.a("Side Front").a(new String[]{"Left", "Right", "Middle", "Side Front", "Side Back"});
   private boolean x;

   public av1(UW var1) {
      super(var1, a2V.PLAYER, "ClickTeleport", "Click Teleport");
      ae9.a(new adZ("CT_MOUSE_BUTTON", "Mouse", a6d.COMBOBOX, this, this.y));
   }

   private int b() {
      int[] var1 = avq.a();
      return this.y.a("Left")?0:(this.y.a("Right")?1:(this.y.a("Middle")?2:(this.y.a("Side Back")?3:(this.y.a("Side Front")?4:-1))));
   }

   @agu
   private void a(aG1 var1) {
      int[] var2 = avq.a();
      if(var1.h().equals(EventState.PRE) && this.w.currentScreen == null) {
         if(Mouse.isButtonDown(this.b())) {
            if(!this.x) {
               return;
            }

            if(this.a() != null) {
               int var3 = this.a().getBlockPos().getX();
               int var4 = this.a().getBlockPos().getY();
               int var5 = this.a().getBlockPos().getZ();
               this.w.thePlayer.sendChatMessage(".tp " + var3 + " " + var4 + " " + var5);
            }

            this.x = false;
         }

         this.x = true;
      }

   }

   public MovingObjectPosition a() {
      Vec3 var1 = new Vec3(this.w.thePlayer.posX, this.w.thePlayer.posY + (double)this.w.thePlayer.getEyeHeight(), this.w.thePlayer.posZ);
      Vec3 var2 = this.a(this.w.thePlayer.rotationPitch, this.w.thePlayer.rotationYaw);
      Vec3 var3 = var1.addVector(var2.xCoord * 500.0D, var2.yCoord * 500.0D, var2.zCoord * 500.0D);
      return this.w.theWorld.rayTraceBlocks(var1, var3, false, false, false);
   }

   protected final Vec3 a(float var1, float var2) {
      float var3 = MathHelper.d(Math.toRadians((double)(-var2)) - 3.1415927410125732D);
      float var4 = MathHelper.h(Math.toRadians((double)(-var2)) - 3.1415927410125732D);
      float var5 = -MathHelper.d(Math.toRadians((double)(-var1)));
      float var6 = MathHelper.h(Math.toRadians((double)(-var1)));
      return new Vec3((double)(var4 * var5), (double)var6, (double)(var3 * var5));
   }
}
