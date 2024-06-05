package net;

import cc.novoline.events.events.EventState;
import cc.novoline.events.events.PacketDirection;
import net.BT;
import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aEu;
import net.aG1;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap9;
import net.as0;
import net.avq;
import net.axu;
import net.lS;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class ave extends as0 {
   private boolean A = false;
   private float B = 0.0F;
   private BlockPos x;
   private EnumFacing z;
   @VN("no-slow-break")
   private aEu y = axu.a(Boolean.valueOf(false));

   public ave(UW var1) {
      super(var1, "SpeedMine", "Speed Mine", a2V.PLAYER, "Speeds up block breaking");
      ae9.a(new adZ("SM_NO_SLOW_BREAK", "No Slow Breaking", a6d.CHECKBOX, this, this.y));
   }

   @agu
   public void b(ap9 var1) {
      int[] var2 = avq.a();
      if(var1.a().equals(PacketDirection.OUTGOING)) {
         if(this.w.playerController.getCurrentGameType().isCreative()) {
            return;
         }

         if(var1.d() instanceof C07PacketPlayerDigging && !this.w.playerController.extendedReach() && this.w.playerController != null) {
            C07PacketPlayerDigging var3 = (C07PacketPlayerDigging)var1.d();
            if(var3.getStatus() == C07PacketPlayerDigging$Action.START_DESTROY_BLOCK) {
               this.A = true;
               this.x = var3.getPosition();
               this.z = var3.getFacing();
               this.B = 0.0F;
            }

            if(var3.getStatus() == C07PacketPlayerDigging$Action.ABORT_DESTROY_BLOCK || var3.getStatus() == C07PacketPlayerDigging$Action.STOP_DESTROY_BLOCK) {
               this.A = false;
               this.B = 0.0F;
               this.x = null;
               this.z = null;
            }
         }
      }

   }

   @agu
   public void a(BT var1) {
      int[] var2 = avq.a();
      if(!this.w.playerController.getCurrentGameType().isCreative()) {
         if(this.w.playerController.extendedReach()) {
            this.w.playerController.blockHitDelay = 0;
         }

         if(this.A && this.w.playerController.func_181040_m() && this.w.thePlayer.canHarvestBlock(this.w.theWorld.getBlockState(this.w.objectMouseOver.getBlockPos()).getBlock())) {
            Block var3 = this.w.theWorld.getBlockState(this.x).getBlock();
            this.B += var3.getPlayerRelativeBlockHardness(this.w.thePlayer, this.w.theWorld, this.x) * 1.4F;
            if(this.B >= 1.0F) {
               this.w.theWorld.setBlockState(this.x, Blocks.air.getDefaultState(), 11);
               this.a((Packet)(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.STOP_DESTROY_BLOCK, this.x, this.z)));
            }
         }

      }
   }

   @agu
   public void a(aG1 var1) {
      int[] var2 = avq.a();
      if(var1.h().equals(EventState.PRE) && this.w.playerController.f() && this.a() && (this.w.thePlayer.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this.w.thePlayer) || !this.w.thePlayer.onGround)) {
         var1.c(true);
      }

   }

   public void c() {
      this.B = 0.0F;
      this.A = false;
      this.x = null;
      this.z = null;
   }

   public boolean a() {
      int[] var1 = avq.a();
      return ((Boolean)this.y.a()).booleanValue() && (!lS.c() || (double)this.w.thePlayer.fallDistance < 1.0D);
   }
}
