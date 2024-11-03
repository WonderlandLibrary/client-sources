package vestige.module.impl.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.MotionEvent;
import vestige.event.impl.Render3DEvent;
import vestige.event.impl.TickEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.misc.Targets;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.player.MovementUtil;
import vestige.util.render.RenderUtil;
import vestige.util.render.RenderUtils2;
import vestige.util.util.GLUtil;

public class ItemBot extends Module {
   public BooleanSetting bow = new BooleanSetting("Bow", true);
   public BooleanSetting rod = new BooleanSetting("Fishing Rod", true);
   public BooleanSetting projectiles = new BooleanSetting("Projectiles", true);
   public BooleanSetting moveCorrection = new BooleanSetting("Move Correction", true);
   private ModeSetting sortingmode = new ModeSetting("SortMode", "FOV", new String[]{"FOV", "DISTANCE", "HEALTH", "CYCLE", "ARMOR"});
   public IntegerSetting range = new IntegerSetting("Range", 70, 1, 150, 1);
   private EntityLivingBase target;
   private List<EntityLivingBase> targets = new ArrayList();

   public ItemBot() {
      super("ItemAimbot", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.bow, this.rod, this.projectiles, this.moveCorrection, this.sortingmode, this.range});
   }

   @Listener
   public void ontick(TickEvent event) {
      if (this.moveCorrection.isEnabled() && this.target != null) {
         Vec3 pos = predictPos(this.target, 11.0F);
         double difX = pos.xCoord - mc.thePlayer.posX;
         double difZ = pos.zCoord - mc.thePlayer.posZ;
         float yaw = (float)(Math.atan2(difZ, difX) * 180.0D / 3.141592653589793D) - 90.0F;
         float diff = MathHelper.wrapAngleTo180_float(MathHelper.wrapAngleTo180_float(yaw) - MathHelper.wrapAngleTo180_float(MovementUtil.getPlayerDirection())) + 22.5F;
         if (diff < 0.0F) {
            diff += 360.0F;
         }

         int a = (int)((double)diff / 45.0D);
         float value = mc.thePlayer.moveForward != 0.0F ? Math.abs(mc.thePlayer.moveForward) : Math.abs(mc.thePlayer.moveStrafing);
         float forward = value;
         float strafe = 0.0F;

         for(int i = 0; i < 8 - a; ++i) {
            float[] dirs = MovementUtil.incrementMoveDirection(forward, strafe);
            forward = dirs[0];
            strafe = dirs[1];
         }

         if (forward < 0.8F) {
            mc.gameSettings.keyBindSprint.pressed = false;
            mc.thePlayer.setSprinting(false);
         }
      }

   }

   @Listener
   public void MotionEvent(MotionEvent event) {
      if ((mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) || !Mouse.isButtonDown(1) || !this.bow.isEnabled()) && (mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemFishingRod) || !this.rod.isEnabled()) && (mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSnowball) || !this.projectiles.isEnabled()) && (mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemEgg) || !this.projectiles.isEnabled())) {
         this.target = null;
      } else {
         this.target = this.getBestTarget(event.getYaw());
         if (this.target != null) {
            double var10000 = 3.0D;
            double g = 0.05000000074505806D;
            if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) {
               g = 0.05000000074505806D;
            } else if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemFishingRod) {
               g = 0.2800000011920929D;
            } else if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSnowball || mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemEgg) {
               g = 0.15000000596046448D;
            }

            float pitch = (float)(-Math.toDegrees((double)this.getLaunchAngle(this.target, 3.0D, g)));
            if (Double.isNaN((double)pitch)) {
               return;
            }

            Vec3 pos = predictPos(this.target, 11.0F);
            double difX = pos.xCoord - mc.thePlayer.posX;
            double difZ = pos.zCoord - mc.thePlayer.posZ;
            float yaw = (float)(Math.atan2(difZ, difX) * 180.0D / 3.141592653589793D) - 90.0F;
            event.setYaw(yaw);
            event.setPitch(pitch);
         }
      }

   }

   @Listener
   public void onRender3D(Render3DEvent event) {
      if (this.target != null) {
         predictPos(this.target, 11.0F);
         RenderUtils2.renderEntity(this.target, 2, 0.0D, 0.0D, Color.green.getRGB(), this.target.hurtTime != 0);
      }

   }

   private void drawEntityESP(double x, double y, double z, double height, double width, Color color) {
      GL11.glPushMatrix();
      GLUtil.setGLCap(3042, true);
      GLUtil.setGLCap(3553, false);
      GLUtil.setGLCap(2896, false);
      GLUtil.setGLCap(2929, false);
      GL11.glDepthMask(false);
      GL11.glLineWidth(1.8F);
      GL11.glBlendFunc(770, 771);
      GLUtil.setGLCap(2848, true);
      GL11.glDepthMask(true);
      RenderUtil.BB(new AxisAlignedBB(x - width + 0.25D, y + 0.1D, z - width + 0.25D, x + width - 0.25D, y + height + 0.25D, z + width - 0.25D), (new Color(color.getRed(), color.getGreen(), color.getBlue(), 120)).getRGB());
      RenderUtil.OutlinedBB(new AxisAlignedBB(x - width + 0.25D, y + 0.1D, z - width + 0.25D, x + width - 0.25D, y + height + 0.25D, z + width - 0.25D), 1.0F, color.getRGB());
      GLUtil.revertAllCaps();
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private static Vec3 lerp(Vec3 pos, Vec3 prev, float time) {
      double x = pos.xCoord + (pos.xCoord - prev.xCoord) * (double)time;
      double y = pos.yCoord + (pos.yCoord - prev.yCoord) * (double)time;
      double z = pos.zCoord + (pos.zCoord - prev.zCoord) * (double)time;
      return new Vec3(x, y, z);
   }

   public static Vec3 predictPos(Entity entity, float time) {
      return lerp(new Vec3(entity.posX, entity.posY, entity.posZ), new Vec3(entity.prevPosX, entity.prevPosY, entity.prevPosZ), time);
   }

   private float getLaunchAngle(EntityLivingBase targetEntity, double v, double g) {
      double yDif = targetEntity.posY + (double)(targetEntity.getEyeHeight() / 2.0F) - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
      double xDif = targetEntity.posX - mc.thePlayer.posX;
      double zDif = targetEntity.posZ - mc.thePlayer.posZ;
      double xCoord = Math.sqrt(xDif * xDif + zDif * zDif);
      return this.theta(v, g, xCoord, yDif);
   }

   private float theta(double v, double g, double x, double y) {
      double yv = 2.0D * y * v * v;
      double gx = g * x * x;
      double g2 = g * (gx + yv);
      double insqrt = v * v * v * v - g2;
      double sqrt = Math.sqrt(insqrt);
      double numerator = v * v + sqrt;
      double numerator2 = v * v - sqrt;
      double atan1 = Math.atan2(numerator, g * x);
      double atan2 = Math.atan2(numerator2, g * x);
      return (float)Math.min(atan1, atan2);
   }

   private EntityLivingBase getBestTarget(float yaw) {
      this.targets.clear();
      Iterator var2 = mc.theWorld.loadedEntityList.iterator();

      while(var2.hasNext()) {
         Entity e = (Entity)var2.next();
         if (e instanceof EntityLivingBase) {
            EntityLivingBase ent = (EntityLivingBase)e;
            if (this.isValid(ent)) {
               this.targets.add(ent);
            }
         }
      }

      if (this.targets.isEmpty()) {
         return null;
      } else {
         this.sortTargets(yaw);
         return (EntityLivingBase)this.targets.get(0);
      }
   }

   private void sortTargets(float yaw) {
      String var2 = this.sortingmode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 69805:
         if (var2.equals("FOV")) {
            var3 = 2;
         }
         break;
      case 62548255:
         if (var2.equals("ARMOR")) {
            var3 = 4;
         }
         break;
      case 64594118:
         if (var2.equals("CYCLE")) {
            var3 = 3;
         }
         break;
      case 1071086581:
         if (var2.equals("DISTANCE")) {
            var3 = 0;
         }
         break;
      case 2127033948:
         if (var2.equals("HEALTH")) {
            var3 = 1;
         }
      }

      switch(var3) {
      case 0:
         List var10000 = this.targets;
         EntityPlayerSP var10001 = mc.thePlayer;
         Objects.requireNonNull(var10001);
         var10000.sort(Comparator.comparingDouble(var10001::getDistanceSqToEntity));
         break;
      case 1:
         this.targets.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
         break;
      case 2:
         this.targets.sort(Comparator.comparingDouble(this::yawDist));
         break;
      case 3:
         this.targets.sort(Comparator.comparingDouble((player) -> {
            return this.yawDistCycle(player, yaw);
         }));
         break;
      case 4:
         this.targets.sort(Comparator.comparingDouble(this::getArmorVal));
      }

   }

   private boolean isValid(EntityLivingBase entity) {
      double d = (double)this.range.getValue();
      return mc.thePlayer.canEntityBeSeen(entity) && entity != null && mc.thePlayer != entity && (entity instanceof EntityPlayer && ((Targets)Flap.instance.getModuleManager().getModule(Targets.class)).playerTarget.isEnabled() || entity instanceof EntityAnimal && ((Targets)Flap.instance.getModuleManager().getModule(Targets.class)).animalTarget.isEnabled() || (entity instanceof EntityMob || entity instanceof EntitySlime) && ((Targets)Flap.instance.getModuleManager().getModule(Targets.class)).mobsTarget.isEnabled()) && entity.getDistanceSqToEntity(mc.thePlayer) <= d * d && entity.isEntityAlive() && (!entity.isInvisible() || ((Targets)Flap.instance.getModuleManager().getModule(Targets.class)).invisiblesTarget.isEnabled());
   }

   private double getArmorVal(EntityLivingBase ent) {
      if (ent instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)ent;
         double armorstrength = 0.0D;

         for(int index = 3; index >= 0; --index) {
            ItemStack stack = player.inventory.armorInventory[index];
            if (stack != null) {
               armorstrength += this.getArmorStrength(stack);
            }
         }

         return armorstrength;
      } else {
         return 0.0D;
      }
   }

   private double getArmorStrength(ItemStack itemStack) {
      if (!(itemStack.getItem() instanceof ItemArmor)) {
         return -1.0D;
      } else {
         float damageReduction = (float)((ItemArmor)itemStack.getItem()).damageReduceAmount;
         Map enchantments = EnchantmentHelper.getEnchantments(itemStack);
         if (enchantments.containsKey(Enchantment.protection.effectId)) {
            int level = (Integer)enchantments.get(Enchantment.protection.effectId);
            damageReduction += (float)Enchantment.protection.calcModifierDamage(level, DamageSource.generic);
         }

         return (double)damageReduction;
      }
   }

   private double yawDist(EntityLivingBase e) {
      Vec3 difference = e.getPositionVector().addVector(0.0D, (double)(e.getEyeHeight() / 2.0F), 0.0D).subtract(mc.thePlayer.getPositionVector().addVector(0.0D, (double)mc.thePlayer.getEyeHeight(), 0.0D));
      double d = Math.abs((double)mc.thePlayer.rotationYaw - (Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0D)) % 360.0D;
      return d > 180.0D ? 360.0D - d : d;
   }

   private double yawDistCycle(EntityLivingBase e, float yaw) {
      Vec3 difference = e.getPositionVector().addVector(0.0D, (double)(e.getEyeHeight() / 2.0F), 0.0D).subtract(mc.thePlayer.getPositionVector().addVector(0.0D, (double)mc.thePlayer.getEyeHeight(), 0.0D));
      double d = Math.abs((double)yaw - Math.atan2(difference.zCoord, difference.xCoord)) % 90.0D;
      return d;
   }
}
