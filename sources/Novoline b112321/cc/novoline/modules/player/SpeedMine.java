package cc.novoline.modules.player;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
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
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.player.Freecam;
import cc.novoline.utils.ServerUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class SpeedMine extends AbstractModule {
   private boolean destroy = false;
   private float progress = 0.0F;
   private BlockPos blockPos;
   private EnumFacing facing;
   @Property("no-slow-break")
   private BooleanProperty y = PropertyFactory.createBoolean(Boolean.valueOf(false));

   public SpeedMine(ModuleManager var1) {
      super(var1, "SpeedMine", "Speed Mine", EnumModuleType.PLAYER, "Speeds up block breaking");
      Manager.put(new Setting("SM_NO_SLOW_BREAK", "No Slow Breaking", SettingType.CHECKBOX, this, this.y));
   }

   @EventTarget
   public void onDamageBlock(PacketEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getDirection().equals(PacketDirection.OUTGOING)) {
         if(this.mc.at.l().isCreative()) {
            return;
         }

         if(var1.getPacket() instanceof C07PacketPlayerDigging && !this.mc.at.n() && this.mc.at != null) {
            C07PacketPlayerDigging var3 = (C07PacketPlayerDigging)var1.getPacket();
            if(var3.getStatus() == C07PacketPlayerDigging$Action.START_DESTROY_BLOCK) {
               this.destroy = true;
               this.blockPos = var3.getPosition();
               this.facing = var3.getFacing();
               this.progress = 0.0F;
            }

            if(var3.getStatus() == C07PacketPlayerDigging$Action.ABORT_DESTROY_BLOCK || var3.getStatus() == C07PacketPlayerDigging$Action.STOP_DESTROY_BLOCK) {
               this.destroy = false;
               this.progress = 0.0F;
               this.blockPos = null;
               this.facing = null;
            }
         }
      }

   }

   @EventTarget
   public void onUpdate(PlayerUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(!this.mc.at.l().isCreative()) {
         if(this.mc.at.n()) {
            this.mc.at.i = 0;
         }

         if(this.destroy && this.mc.at.m() && this.mc.player.canHarvestBlock(this.mc.world.getBlockState(this.mc.objectMouseOver.getBlockPos()).getBlock())) {
            Block var3 = this.mc.world.getBlockState(this.blockPos).getBlock();
            this.progress += var3.getPlayerRelativeBlockHardness(this.mc.player, this.mc.world, this.blockPos) * 1.4F;
            if(this.progress >= 1.0F) {
               this.mc.world.setBlockState(this.blockPos, Blocks.air.getDefaultState(), 11);
               this.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.STOP_DESTROY_BLOCK, this.blockPos, this.facing));
            }
         }

      }
   }

   @EventTarget
   public void a(MotionUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getState().equals(EventState.PRE) && this.mc.at.f() && this.a() && (this.mc.player.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this.mc.player) || !this.mc.player.onGround)) {
         var1.setOnGround(true);
      }

   }

   public void onDisable() {
      this.progress = 0.0F;
      this.destroy = false;
      this.blockPos = null;
      this.facing = null;
   }

   public boolean a() {
      int[] var1 = Freecam.a();
      return ((Boolean)this.y.get()).booleanValue() && (!ServerUtils.isHypixel() || (double)this.mc.player.fallDistance < 1.0D);
   }
}
