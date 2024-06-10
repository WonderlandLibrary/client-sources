 package me.vene.skilled.modules.mods.combat;
 
 import java.lang.reflect.Field;
 import java.util.List;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.ClientUtil;
 import me.vene.skilled.utilities.MathUtil;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.item.ItemAxe;
 import net.minecraft.item.ItemSword;
 import net.minecraft.network.play.server.S18PacketEntityTeleport;
 import net.minecraft.potion.Potion;
 import net.minecraft.util.AxisAlignedBB;
 import net.minecraft.util.MathHelper;
 import net.minecraft.util.MovingObjectPosition;
 import net.minecraft.util.Vec3;

 public class Reach extends Module
 {
     private long lastRangeChange;
     private double currentRange;
     private String MinDistance;
     private String MaxDistance;
     private NumberValue mindist;
     private NumberValue maxdist;
     private static Minecraft mc;
     private BooleanValue misplaceOpt;
     private BooleanValue onlySprinting;
     private BooleanValue verticalCheck;
     private BooleanValue Weapon;
     private BooleanValue SpeedOnly;
     private String packetXstring;
     private String packetZstring;
     private Field packetXField;
     private Field packetZField;
     
     public Reach() {
         super(StringRegistry.register(new String(new char[] { 'R', 'e', 'a', 'c', 'h' })), 0, Category.C);
         this.MinDistance = StringRegistry.register(new String(new char[] { 'M', 'i', 'n', ' ', 'D', 'i', 's', 't', 'a', 'n', 'c', 'e' }));
         this.MaxDistance = StringRegistry.register(new String(new char[] { 'M', 'a', 'x', ' ', 'D', 'i', 's', 't', 'a', 'n', 'c', 'e' }));
         this.mindist = new NumberValue(StringRegistry.register(this.MinDistance), 3.1, 3.0, 6.0);
         this.maxdist = new NumberValue(StringRegistry.register(this.MaxDistance), 3.2, 3.0, 6.0);
         this.mc = Minecraft.getMinecraft();
         Reach.mc = Minecraft.getMinecraft();
         this.misplaceOpt = new BooleanValue(StringRegistry.register("Misplace"), false);
         this.onlySprinting = new BooleanValue(StringRegistry.register("Only sprinting"), true);
         this.Weapon = new BooleanValue(StringRegistry.register("Weapon"), false);
         this.SpeedOnly = new BooleanValue(StringRegistry.register("Only Speed"), false);
         this.packetXstring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '1', '4', '9', '4', '5', '6', '_', 'b' });
         this.packetZstring = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '1', '4', '9', '4', '5', '4', '_', 'd' });
         this.addValue(this.mindist);
         this.addValue(this.maxdist);
         this.addOption(this.misplaceOpt);
         this.addOption(this.onlySprinting);
         this.addOption(this.Weapon);
         this.addOption(this.SpeedOnly);
     
     try {
       this.packetXField = S18PacketEntityTeleport.class.getDeclaredField(StringRegistry.register(this.packetXstring));
       this.packetZField = S18PacketEntityTeleport.class.getDeclaredField(StringRegistry.register(this.packetZstring));
     } catch (NoSuchFieldException noSuchFieldException) {}
   }
  
   public void onMouseOver(float particalTicks) {
    if (this.misplaceOpt.getState()) {
       return;
    }
    if (this.mc.thePlayer == null || this.mc.theWorld == null || this.mc.currentScreen != null) {
      return;
    }
    if (this.mc.thePlayer.isRiding()) {
       return;
     }

   if (!Reach.mc.thePlayer.isInWater() == false) { 
	    return;	 
    }

    if (!Reach.mc.thePlayer.isInLava() == false) {
	    return;	 
   }
 
   if (!Reach.mc.thePlayer.isOnLadder() == false) {
       return;	 
    }
 
   if (!Reach.mc.thePlayer.isSneaking() == false) {
       return;	 
    }

  if (!(Reach.mc.thePlayer.getFoodStats().getFoodLevel() > 6)) {
          return;	 
     }

  if (!Reach.mc.thePlayer.isPotionActive(Potion.poison) == false) {
           return;	 
     } 
  
    if (!Reach.mc.thePlayer.isPotionActive(Potion.moveSlowdown) == false) {
            return;	 
     }
   if (!Reach.mc.thePlayer.isPotionActive(Potion.digSlowdown) == false) {
     return;	 
     }

    if (!Reach.mc.thePlayer.isPotionActive(Potion.wither) == false) {
      return;	 
       }

   if (!Reach.mc.thePlayer.isPotionActive(Potion.jump) == false) {
       return;	 
      }
    if (!Reach.mc.thePlayer.isPotionActive(Potion.blindness) == false) {
        return;	 
      }
    
    if (this.onlySprinting.getState() && !this.mc.thePlayer.isSprinting()) {
        return;
     }
    
    if (this.SpeedOnly.getState()) {
	       if (!Reach.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
       return;   
	          }
      }    

    if (this.Weapon.getState()) {
  	  if (Reach.mc.thePlayer.getCurrentEquippedItem() == null) {
            return;
      }
  	  if (!(Reach.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) && !(Reach.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
            return;
        } 
} 
    try {
        final String server = this.mc.getCurrentServerData().serverIP;
        final double minRange = this.mindist.getValue();
        final double maxRange = this.maxdist.getValue();
        if (System.currentTimeMillis() > this.lastRangeChange + 1000L) {
            this.lastRangeChange = System.currentTimeMillis();
            this.currentRange = minRange + MathUtil.random.nextDouble() * (maxRange - minRange);
        }
        final double expand = 0.0;
        final MovingObjectPosition object = ClientUtil.getMouseOver(this.currentRange, 0.0, particalTicks);
        if (object != null) {
            this.mc.objectMouseOver = object;
        }
    }
    catch (Exception e) {
        final double expand2 = 0.0;
        final MovingObjectPosition object2 = ClientUtil.getMouseOver(this.currentRange, 0.0, particalTicks);
        if (object2 != null) {
            this.mc.objectMouseOver = object2;
        }
    }
}
  public S18PacketEntityTeleport onEntityTeleport(S18PacketEntityTeleport packet) {
     Entity entity = this.mc.theWorld.getEntityByID(packet.getEntityId());
     if (entity instanceof net.minecraft.entity.player.EntityPlayer && this.misplaceOpt.getState()) {
       double x = packet.getX() / 32.0D;
       double z = packet.getZ() / 32.0D;
       double minRange = this.mindist.getValue();
       double maxRange = this.maxdist.getValue();
       double distance = (minRange + MathUtil.random.nextDouble() * (maxRange - minRange)) / 3.0D;
       double f = distance;
       if (f == 0.0D) {
         onEntityTeleport(packet);
         return packet;
       } 
       double c = Math.hypot(this.mc.thePlayer.posX - x, this.mc.thePlayer.posZ - z);
       
       if (f > c) {
         f -= c;
       }      
      float r = getAngle(x, z);
       if (a(this.mc.thePlayer.rotationYaw, r) > 180.0D) {
        onEntityTeleport(packet);
         return packet;
       } 
       if (MathUtil.getDistanceBetweenAngles(this.mc.thePlayer.rotationYaw, r = getAngle(x, z)) > 90.0D) {
         return packet;
       }
       double a = Math.cos(Math.toRadians((r + 90.0F)));
       double b = Math.sin(Math.toRadians((r + 90.0F)));
       x -= a * f;
      z -= b * f;
       try {
         this.packetXField.setAccessible(true);
         this.packetXField.set(packet, Integer.valueOf(MathHelper.floor_double(x * 32.0D)));
         this.packetXField.setAccessible(false);
         this.packetZField.setAccessible(true);
         this.packetZField.set(packet, Integer.valueOf(MathHelper.floor_double(z * 32.0D)));
         this.packetZField.setAccessible(false);
       } catch (Exception e) {
         e.printStackTrace();
       } 
     } 
     return packet;
   }
   
    private double a(float a, float b) {
      float d = Math.abs(a - b) % 360.0F;
     if (d > 180.0F) {
       d = 360.0F - d;
     }
     return d;
   }
   
   private float getAngle(double posX, double posZ) {
     double x = posX - this.mc.thePlayer.posX;
     double z = posZ - this.mc.thePlayer.posZ;
     float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
     if (z < 0.0D && x < 0.0D) {
       newYaw = (float)(90.0D + Math.toDegrees(Math.atan(z / x)));
     } else if (z < 0.0D && x > 0.0D) {
       newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(z / x)));
     } 
     return newYaw;
   }

   private Object[] getEntity(double distance, double expand, float partialTicks) {
     if (this.mc.getRenderViewEntity() != null && this.mc.theWorld != null) {
       Entity entity = null;
       Vec3 var6 = this.mc.getRenderViewEntity().getPositionEyes(partialTicks);
       Vec3 var7 = this.mc.getRenderViewEntity().getLook(partialTicks);
       Vec3 var8 = var6.addVector(var7.xCoord * distance, var7.yCoord * distance, var7.zCoord * distance);
       Vec3 var9 = null;
       float var10 = 1.0F;
       List<Entity> var11 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.getRenderViewEntity(), this.mc.getRenderViewEntity().getEntityBoundingBox().addCoord(var7.xCoord * distance, var7.yCoord * distance, var7.zCoord * distance).expand(1.0D, 1.0D, 1.0D));
       double var12 = distance;
      for (int var13 = 0; var13 < var11.size(); var13++) {         
         Entity var14 = var11.get(var13);
         if (var14.canBeCollidedWith()) {
           float var15 = var14.getCollisionBorderSize();
           AxisAlignedBB var16 = var14.getEntityBoundingBox().expand(var15, var15, var15);
           var16 = var16.expand(expand, expand, expand);
           MovingObjectPosition var17 = var16.calculateIntercept(var6, var8);
           if (var16.isVecInside(var6))
           { if (0.0D < var12 || var12 == 0.0D) {
               entity = var14;
               var9 = (var17 == null) ? var6 : var17.hitVec;
               var12 = 0.0D;
             }  }
           else
           { double var18; if (var17 != null && ((var18 = var6.distanceTo(var17.hitVec)) < var12 || var12 == 0.0D))
               if (var14 == (this.mc.getRenderViewEntity()).ridingEntity && !entity.canRiderInteract())
               { if (var12 == 0.0D) {
                   entity = var14;
                   var9 = var17.hitVec;
                }  }
               else
               { entity = var14;
               var9 = var17.hitVec;
                var12 = var18; }   } 
         } 
      }  if (var12 < distance && (entity instanceof EntityLivingBase || entity instanceof net.minecraft.entity.item.EntityItemFrame) && ((EntityLivingBase)entity).canEntityBeSeen((Entity)(Minecraft.getMinecraft()).thePlayer)) {
         return new Object[] { entity, var9 };
      }
     } 
     return null;
   }
 }
