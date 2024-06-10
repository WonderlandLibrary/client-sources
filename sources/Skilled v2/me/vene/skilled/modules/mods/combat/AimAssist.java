 package me.vene.skilled.modules.mods.combat;
 
 import java.lang.reflect.Field;
 import me.vene.skilled.SkilledClient;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.MathUtil;
 import me.vene.skilled.utilities.ReflectionUtil;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.entity.EntityPlayerSP;
 import net.minecraft.client.multiplayer.PlayerControllerMP;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.item.ItemAxe;
 import net.minecraft.item.ItemStack;
 import net.minecraft.item.ItemSword;
 import net.minecraft.potion.Potion;
 import net.minecraft.util.MathHelper;
 import net.minecraft.util.MovingObjectPosition.MovingObjectType;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 import org.lwjgl.input.Mouse;
 
public class AimAssist extends Module
{
    private String horizontal;
    private String vertical;
    private String range;
    private String onlyclick;
    private String angle;
    private String swords;
    private NumberValue hor;
    private NumberValue ver;
    private NumberValue dist;
    private NumberValue fov;
    private BooleanValue weapon;
    private BooleanValue click;
    private BooleanValue strafeIncrease;
    private BooleanValue breakBlocksCheck;
    private BooleanValue StopOnHitboxes;
    private Minecraft mc;
    private Field damnfield;
    boolean ghu;
    
    public AimAssist() {
        super(StringRegistry.register(new String(new char[] { 'A', 'i', 'm', 'A', 's', 's', 'i', 's', 't' })), 0, Category.C);
        this.horizontal = StringRegistry.register(new String(new char[] { 'H', 'o', 'r', 'i', 'z', 'o', 'n', 't', 'a', 'l' }));
        this.vertical = StringRegistry.register(new String(new char[] { 'V', 'e', 'r', 't', 'i', 'c', 'a', 'l' }));
        this.range = StringRegistry.register(new String(new char[] { 'D', 'i', 's', 't', 'a', 'n', 'c', 'e' }));
        this.onlyclick = StringRegistry.register(new String(new char[] { 'C', 'l', 'i', 'c', 'k', ' ', 'A', 'i', 'm' }));
        this.angle = StringRegistry.register(new String(new char[] { 'F', 'O', 'V' }));
        this.swords = StringRegistry.register("Weapon");
        this.hor = new NumberValue(StringRegistry.register(this.horizontal), 5.0, 0.0, 10.0);
        this.ver = new NumberValue(StringRegistry.register(this.vertical), 0.0, 0.0, 10.0);
        this.dist = new NumberValue(StringRegistry.register(this.range), 6.0, 2.0, 8.0);
        this.fov = new NumberValue(StringRegistry.register(this.angle), 73.0, 10.0, 180.0);
        this.weapon = new BooleanValue(StringRegistry.register(this.swords), true);
        this.click = new BooleanValue(StringRegistry.register(this.onlyclick), true);
        this.strafeIncrease = new BooleanValue(StringRegistry.register("Strafe increase"), false);
        this.breakBlocksCheck = new BooleanValue(StringRegistry.register("Break Block Check"), true);
        this.mc = Minecraft.getMinecraft();
        this.ghu = false;
        this.addValue(this.hor);
        this.addValue(this.ver);
        this.addValue(this.dist);
        this.addValue(this.fov);
        this.addOption(this.breakBlocksCheck);
        this.addOption(this.weapon);
        this.addOption(this.click);
        this.addOption(this.strafeIncrease);
        
        try {
            final String fieldshit = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '7', '8', '_', 'j' });
            this.damnfield = PlayerControllerMP.class.getDeclaredField(StringRegistry.register(fieldshit));
        }
        catch (NoSuchFieldException ex) {}
    }
    
    private void faceEntity(final EntityLivingBase entity) {
        final float[] rotations = this.getRotationsNeeded((Entity)entity);
        if (rotations != null) {
            double theSpeed;
            if (this.strafeIncrease.getState() && this.mc.thePlayer.moveStrafing != 0.0f) {
                theSpeed = this.hor.getValue() / 3.0 * 1.25;
            }
            else {
                theSpeed = this.hor.getValue() / 3.0;
            }
            this.mc.thePlayer.rotationYaw = (float)this.limitAngleChange(this.mc.thePlayer.rotationYaw, rotations[0] + MathUtil.random.nextFloat() * 0.95 + 0.05, theSpeed);
            this.mc.thePlayer.rotationPitch = (float)this.limitAngleChange(this.mc.thePlayer.rotationPitch, rotations[1] + MathUtil.random.nextFloat() * 0.95 + 0.05, this.ver.getValue() / 4.5);
        }
    }
    
    private float[] getRotationsNeeded(final Entity entity) {
        if (entity == null) {
            return null;
        }
        final double diffX = entity.posX - this.mc.thePlayer.posX;
        final double diffZ = entity.posZ - this.mc.thePlayer.posZ;
        double diffY;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.6 - (this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight());
        }
        else {
            diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight());
        }
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { this.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - this.mc.thePlayer.rotationYaw), this.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - this.mc.thePlayer.rotationPitch) };
    }
    
    private EntityLivingBase getTargetEntity() {
        final double maxDistance = 360.0;
        EntityLivingBase target = null;
        for (final Object object : this.mc.theWorld.loadedEntityList) {
            if (!(object instanceof EntityLivingBase)) {
                continue;
            }
            final EntityLivingBase entity = (EntityLivingBase)object;
            if (!this.canTarget(entity)) {
                continue;
            }
            final float yaw = MathUtil.getAngle((Entity)entity)[1];
            final double yawDistance = MathUtil.getDistanceBetweenAngles(yaw, this.mc.thePlayer.rotationYaw);
            if (maxDistance <= yawDistance) {
                continue;
            }
            target = entity;
        }
        return target;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || this.mc.thePlayer == null || this.mc.theWorld == null || this.mc.currentScreen != null) {
            return;
        }
        if (!this.mc.thePlayer.isSprinting()) {
            return;
        }  
        
        if (!this.mc.thePlayer.onGround == true) { 
   		 return;	 
      	 }
        
   	 if (!this.mc.thePlayer.isInWater() == false) { 
   		 return;	 
   	    }
   	 
   	 if (!this.mc.thePlayer.isInLava() == false) {
   		 return;	 
   	    }
   	 
   	  if (!this.mc.thePlayer.isOnLadder() == false) {
  	          return;	 
  	     }
   	  
   	  if (!this.mc.thePlayer.isEating() == false) {
   		  return;	  
	      }
   	  
   	  if (!this.mc.thePlayer.isSneaking() == false) {
	          return;	 
           }
   	  
   	if (!this.mc.thePlayer.isPotionActive(Potion.poison) == false) {
             return;	 
           }

   	  if (!this.mc.thePlayer.isPotionActive(Potion.wither) == false) {
         return;	 
       }
   	 
 	  if (!this.mc.thePlayer.isPotionActive(Potion.blindness) == false) {
 		  return;	 
     }
        
        
        if (!this.breakingBlock((EntityPlayer)this.mc.thePlayer) && this.breakBlocksCheck.getState()) {
            return;
        }

        if (this.mc.thePlayer.isDead) {
            return;
        }
        if (!Mouse.isButtonDown(0) && this.click.getState() && System.currentTimeMillis() - SkilledClient.getInstance().getEventManager().getLastClick() >= 150L) {
            return;
        }
        final Entity targetEntity = (Entity)this.getTargetEntity();
        if (targetEntity != null && this.check(this.mc.thePlayer)) {
            this.faceEntity((EntityLivingBase)targetEntity);
        }
    }
    
    private boolean check(final EntityPlayerSP playerSP) {
        return !this.weapon.getState() || (playerSP.getCurrentEquippedItem() != null && (playerSP.getCurrentEquippedItem().getItem() instanceof ItemSword || playerSP.getCurrentEquippedItem().getItem() instanceof ItemAxe));
    }
    
    private boolean canTarget(final EntityLivingBase entity) {
        if (entity == this.mc.thePlayer || !this.mc.thePlayer.canEntityBeSeen((Entity)entity)) {
            return false;
        }
        final float distance = entity.getDistanceToEntity((Entity)this.mc.thePlayer);
        final float range = (float)this.dist.getValue();
        if (distance > range || distance < 1.0f) {
            return false;
        }
        final float yaw = MathUtil.getAngle((Entity)entity)[1];
        final double yawDistance = MathUtil.getDistanceBetweenAngles(yaw, this.mc.thePlayer.rotationYaw);
        final double maxDistance = this.fov.getValue();
        if (yawDistance > maxDistance) {
            return false;
        }
        if (!(entity instanceof EntityPlayer)) {
            return false;
        }
        if (SkilledClient.getInstance().getFriendManager().isFriend(((EntityPlayer)entity).getDisplayNameString())) {
            return false;
        }
        if (entity.isInvisible()) {
            final EntityPlayer entityPlayer = (EntityPlayer)entity;
            return this.hasArmor(entityPlayer);
        }
        return true;
    }
    
    public boolean isOnSameTeam(final EntityPlayer entity)  {
        return !(entity instanceof EntityPlayer) || ((EntityPlayer)entity).getTeam() == null || !((EntityPlayer)entity).isOnSameTeam((EntityPlayer)this.mc.thePlayer);
    }
    
    private boolean hasArmor(final EntityPlayer player) {
        final ItemStack[] armor = player.inventory.armorInventory;
        return player.getCurrentEquippedItem() != null || (armor != null && (armor[0] != null || armor[1] != null || armor[2] != null || armor[3] != null));
    }
    
    private double limitAngleChange(final double current, final double intended, final double speed) {
        double change = intended - current;
        if (change > speed) {
            change = speed;
        }
        else if (change < -speed) {
            change = -speed;
        }
        return current + change;
    }
    
    private boolean breakingBlock(final EntityPlayer player) {
        if (player instanceof EntityPlayerSP) {
            final PlayerControllerMP controller = Minecraft.getMinecraft().playerController;
            final String fieldmeme = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '7', '8', '_', 'j' });
            final boolean hasBlock = Boolean.valueOf(ReflectionUtil.getFieldValue(StringRegistry.register(fieldmeme), controller, PlayerControllerMP.class).toString());
            try {
                this.damnfield.setAccessible(true);
                this.damnfield.getBoolean(controller);
                this.damnfield.setAccessible(false);
            }
            catch (Exception ex) {}
            if (hasBlock) {
                return false;
            }
        }
        return true;
    }
}
