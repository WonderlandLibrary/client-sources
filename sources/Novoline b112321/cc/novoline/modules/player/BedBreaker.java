package cc.novoline.modules.player;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.player.Freecam;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.RotationUtil;
import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBed$EnumPartType;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

public final class BedBreaker extends AbstractModule {
   private double currentDamage;
   private boolean digging;
   private BlockPos currentPos;
   BlockPos whiteListed = new BlockPos(0, 0, 0);
   @Property("mode")
   private final StringProperty mode = PropertyFactory.createString("Simple").acceptableValues(new String[]{"Simple", "Advanced"});
   @Property("radious")
   private final IntProperty radius = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(4)).minimum(Integer.valueOf(3))).maximum(Integer.valueOf(5));
   @Property("draw-radius")
   private final BooleanProperty drawRadius = PropertyFactory.createBoolean(Boolean.valueOf(false));

   public BedBreaker(ModuleManager var1) {
      super(var1, "BedBreaker", EnumModuleType.PLAYER, "Breaks bed around you");
      Manager.put(new Setting("BB_MODE", "Mode", SettingType.COMBOBOX, this, this.mode));
      Manager.put(new Setting("BB_RADIUS", "Break radius", SettingType.SLIDER, this, this.radius, 1.0D));
      Manager.put(new Setting("AV_BOOST", "Draw radius", SettingType.CHECKBOX, this, this.drawRadius));
   }

   @EventTarget
   public void onTick(TickUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(this.currentPos != null) {
         if(this.currentDamage == 0.0D) {
            this.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.START_DESTROY_BLOCK, this.currentPos, EnumFacing.UP));
         }

         Block var3 = this.mc.world.getBlockState(this.currentPos).getBlock();
         this.currentDamage += (double)var3.getPlayerRelativeBlockHardness(this.mc.player, this.mc.player.worldObj, this.currentPos);
         if(this.currentDamage >= 1.0D) {
            this.digging = false;
            this.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging$Action.STOP_DESTROY_BLOCK, this.currentPos, EnumFacing.UP));
            this.mc.at.c(this.currentPos, EnumFacing.UP);
            this.currentDamage = 0.0D;
            this.currentPos = null;
         }

         this.sendPacket(new C0APacketAnimation());
         this.mc.world.sendBlockBreakProgress(this.mc.player.getEntityID(), this.currentPos, (int)(this.currentDamage * 10.0D) - 1);
      }

   }

   @EventTarget
   public void onMotionUpdate(MotionUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getState().equals(EventState.PRE)) {
         if(this.currentPos != null && (!((KillAura)this.getModule(KillAura.class)).isEnabled() || ((KillAura)this.getModule(KillAura.class)).getTarget() == null)) {
            if(!this.bedAround()) {
               this.currentPos = null;
               this.currentDamage = 0.0D;
            }

            float[] var3 = RotationUtil.getRotations((double)this.currentPos.getX() + 0.5D, (double)this.currentPos.getY() + 0.5D, (double)this.currentPos.getZ() + 0.5D);
            var1.setYaw(var3[0]);
            var1.setPitch(var3[1]);
            this.mc.player.updateTool(this.currentPos);
            this.mc.player.rotationYawHead = var3[0];
            this.mc.player.renderArmPitch = var3[1];
         }

         int var13 = -((Integer)this.radius.get()).intValue();
         if(var13 < ((Integer)this.radius.get()).intValue() + 1) {
            int var4 = -((Integer)this.radius.get()).intValue();
            if(var4 < ((Integer)this.radius.get()).intValue() + 1) {
               int var5 = -3;
               if(var5 < 5) {
                  BlockPos var6 = new BlockPos(this.mc.player.posX - (double)var13, this.mc.player.posY + (double)var5, this.mc.player.posZ - (double)var4);
                  Block var7 = this.mc.world.getBlockState(var6).getBlock();
                  if(!this.isWhitelisted(var6) && this.mc.world.getBlockState(var6).getBlock() == Blocks.bed && this.mc.world.getBlockState(var6).getValue(BlockBed.PART) == BlockBed$EnumPartType.HEAD) {
                     if(((String)this.mode.get()).equals("Advanced")) {
                        float[] var8 = RotationUtil.getRotations((double)var6.getX() + 0.5D, (double)var6.getY() + 0.5D, (double)var6.getZ() + 0.5D);
                        Vec3 var9 = new Vec3(this.mc.player.posX, this.mc.player.posY + (double)this.mc.player.getEyeHeight(), this.mc.player.posZ);
                        Vec3 var10 = this.getVectorForRotation(var8[1], var8[0]);
                        Vec3 var11 = var9.addVector(var10.xCoord * (double)((Integer)this.radius.get()).intValue(), var10.yCoord * (double)((Integer)this.radius.get()).intValue(), var10.zCoord * (double)((Integer)this.radius.get()).intValue());
                        MovingObjectPosition var12 = this.mc.world.rayTraceBlocks(var9, var11, false, false, false);
                        this.currentPos = var12.getBlockPos();
                        this.currentDamage = 0.0D;
                        if(this.mc.world.getBlockState(this.currentPos).getBlock() == Blocks.bed) {
                           ;
                        }
                     }

                     this.currentPos = var6;
                     this.currentDamage = 0.0D;
                  }

                  ++var5;
               }

               ++var4;
            }

            ++var13;
         }
      }

   }

   protected final Vec3 getVectorForRotation(float var1, float var2) {
      float var3 = MathHelper.cos(Math.toRadians((double)(-var2)) - 3.1415927410125732D);
      float var4 = MathHelper.sin(Math.toRadians((double)(-var2)) - 3.1415927410125732D);
      float var5 = -MathHelper.cos(Math.toRadians((double)(-var1)));
      float var6 = MathHelper.sin(Math.toRadians((double)(-var1)));
      return new Vec3((double)(var4 * var5), (double)var6, (double)(var3 * var5));
   }

   public Vec3 getPositionEyes(float var1) {
      return new Vec3(this.mc.player.posX, this.mc.player.posY + (double)this.mc.player.getEyeHeight(), this.mc.player.posZ);
   }

   @EventTarget
   public void onRender3D(Render3DEvent var1) {
      int[] var2 = Freecam.a();
      if(((Boolean)this.drawRadius.get()).booleanValue()) {
         RenderUtils.pre3D();
         GL11.glLineWidth(6.0F);
         GL11.glBegin(3);
         double var3 = 0.0D;
         if(var3 < 6.283185307179586D) {
            float var5 = (float)((new Color(255, 255, 255, 150)).getRGB() >> 24 & 255) / 255.0F;
            float var6 = (float)((new Color(255, 255, 255, 150)).getRGB() >> 16 & 255) / 255.0F;
            float var7 = (float)((new Color(255, 255, 255, 150)).getRGB() >> 8 & 255) / 255.0F;
            float var8 = (float)((new Color(255, 255, 255, 150)).getRGB() & 255) / 255.0F;
            GL11.glColor4f(var6, var7, var8, var5);
            double var9 = this.mc.player.lastTickPosX + (this.mc.player.posX - this.mc.player.lastTickPosX) * (double)var1.getPartialTicks() + Math.sin(var3) * (double)((Integer)this.radius.get()).intValue() - this.mc.getRenderManager().renderPosX;
            double var11 = this.mc.player.lastTickPosY + (this.mc.player.posY - this.mc.player.lastTickPosY) * (double)var1.getPartialTicks() - this.mc.getRenderManager().renderPosY;
            double var13 = this.mc.player.lastTickPosZ + (this.mc.player.posZ - this.mc.player.lastTickPosZ) * (double)var1.getPartialTicks() + Math.cos(var3) * (double)((Integer)this.radius.get()).intValue() - this.mc.getRenderManager().renderPosZ;
            GL11.glVertex3d(var9, var11, var13);
            var3 = var3 + 0.241660973353061D;
         }

         GL11.glEnd();
         RenderUtils.post3D();
      }

      if(this.currentPos != null) {
         RenderUtils.drawSolidBlockESP(new BlockPos(this.currentPos), (new Color(255, 255, 255, 50)).getRGB());
      }

   }

   private boolean bedAround() {
      Freecam.a();
      int var2 = -((Integer)this.radius.get()).intValue();
      if(var2 < ((Integer)this.radius.get()).intValue() + 1) {
         int var3 = -((Integer)this.radius.get()).intValue();
         if(var3 < ((Integer)this.radius.get()).intValue() + 1) {
            int var4 = -3;
            if(var4 < 5) {
               BlockPos var5 = new BlockPos(this.mc.player.posX - (double)var2, this.mc.player.posY + (double)var4, this.mc.player.posZ - (double)var3);
               Block var6 = this.mc.world.getBlockState(var5).getBlock();
               if(!this.isWhitelisted(var5) && this.mc.world.getBlockState(var5).getBlock() == Blocks.bed && this.mc.world.getBlockState(var5).getValue(BlockBed.PART) == BlockBed$EnumPartType.HEAD) {
                  return true;
               }

               ++var4;
            }

            ++var3;
         }

         ++var2;
      }

      return false;
   }

   public BlockPos getWhiteListed() {
      return this.whiteListed;
   }

   public void setWhiteListed(BlockPos var1) {
      this.whiteListed = var1;
   }

   private boolean isWhitelisted(@NotNull BlockPos var1) {
      int[] var2 = Freecam.a();
      return var1.getX() == this.whiteListed.getX() && var1.getY() == this.whiteListed.getY() && var1.getZ() == this.whiteListed.getZ();
   }
}
