package lunadevs.luna.module.combat;

import java.util.ArrayList;
import java.util.List;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventMotion;
import lunadevs.luna.events.EventType;
import lunadevs.luna.events.Location;
import lunadevs.luna.friend.FriendManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.Option;
import lunadevs.luna.utils.EntityUtils;
import lunadevs.luna.utils.MathUtil;
import lunadevs.luna.utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class Killaura extends Module {
	public static boolean active;
    @Option.Op(min=1.0D, max=20.0D, increment=0.1D, name="Range")
    public static double range = 7;
    @Option.Op(min=1.0D, max=20.0D, increment=0.1D, name="Speed")
    public static double speed = 13;
    @Option.Op(name="Block")
    public static boolean block = true;
    @Option.Op(name="Friend")
    public static boolean friend = false;
    @Option.Op(name="Lock")
    public static boolean lock = false;
	  public static List<EntityLivingBase> entities;
	  public static EntityLivingBase target;
	  public static TimeHelper time;
	  public static int mode;
      public static String modname;
	  
	public Killaura() {
		super("KillAura", 19, Category.COMBAT, true);
		this.entities = new ArrayList<EntityLivingBase>();
		this.time = new TimeHelper();
	}

	  private void swap(int slot, int hotbarNum)
	  {
	    mc.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, Minecraft.thePlayer);
	  }
	
	  @EventTarget
	  public void aura(EventMotion event){
        if (mode==0){
            Switch(event);
        }else  if (mode==1){
            Tick(event);
        }
      }

	  public void Switch(EventMotion event)
	  {
		  if (!this.isEnabled) return;
		  final boolean isSword = this.block && mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
	        if (event.getType() == EventType.PRE) {
	            if (this.entities.isEmpty()) {
	                for (final Object object : Minecraft.theWorld.loadedEntityList) {
	                    if (object instanceof EntityLivingBase) {
	                        final EntityLivingBase entity = (EntityLivingBase)object;
	                        if (!isValid(entity)) {
	                            continue;
	                        }
	                        this.entities.add(entity);
	                    }
	                }
	            }
	            if (!this.entities.isEmpty()) {
	                double distance = Double.MAX_VALUE;
	                EntityLivingBase entity = null;
	                for (int i = 0; i < this.entities.size(); ++i) {
	                    final EntityLivingBase e = this.entities.get(i);
	                    if (!isValid(e)) {
	                        this.entities.remove(e);
	                    }
	                    if (e.getDistanceToEntity(mc.thePlayer) < distance && e.getDistanceToEntity(mc.thePlayer) < this.range) {
	                        entity = e;
	                        distance = e.getDistanceToEntity(mc.thePlayer);
	                    }
	                }
	                this.target = entity;
	            }
	            if (isValid(this.target)) {
	            	if (this.lock==false) {
                        event.getLocation().setYaw(getRotations(this.target)[0]);
                        event.getLocation().setPitch(getRotations(this.target)[1]);
                    }else if (this.lock==true){
	            	    EntityUtils.faceEntity(this.target);
                    }
	            }
	        }
	        else if (isValid(this.target)) {
	            if (isSword && mc.thePlayer.getDistanceToEntity(this.target) < this.range) {
	                final PlayerControllerMP playerController = mc.playerController;
	                final EntityPlayerSP p = mc.thePlayer;
	                final Minecraft mc2 = mc;
	                playerController.sendUseItem(p, Minecraft.theWorld, mc.thePlayer.inventory.getCurrentItem());
	            }
	            
	            float kill = (float) (1000.0D / this.speed);
	            
	            if (this.target != null && this.time.hasReached((long) kill)) {
	                attack(this.target);
	                this.entities.remove(this.target);
	                this.target = null;
	                this.time.reset();
	            }
	        }
	    }

	  public void Tick(final EventMotion event) {
	        if (event.getType() == EventType.PRE) {
	            this.target = findClosestEntity();
	            if (isValid(this.target)) {
	                if (this.lock==false) {
	                    final float[] rotations = getRotations(this.target);
	                    final Location location = event.getLocation();
	                    location.setYaw(rotations[0]);
	                    location.setPitch(rotations[1]);
	                    event.setLocation(location);
	                }else if(this.lock==true){
	                    EntityUtils.faceEntity(this.target);
	                }
	            }
	        }
	        else {
	            double dist = 1.0E9;
	            EntityLivingBase entity = null;
	            for (final Object object : Minecraft.theWorld.loadedEntityList) {
	                if (object instanceof EntityLivingBase) {
	                    final EntityLivingBase e = (EntityLivingBase)object;
	                    if (e.getDistanceToEntity(mc.thePlayer) >= dist) {
	                        continue;
	                    }
	                    if (!isValid(e)) {
	                        continue;
	                    }
	                    dist = e.getDistanceToEntity(mc.thePlayer);
	                    entity = e;
	                }
	            }
	            final boolean block = entity != null && this.block && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
	            if (block && entity.getDistanceToEntity(mc.thePlayer) < 8.0f) {
	                final PlayerControllerMP playerController = mc.playerController;
	                playerController.sendUseItem(mc.thePlayer, Minecraft.theWorld, mc.thePlayer.inventory.getCurrentItem());
	            }
	            if (isValid(this.target) && this.time.hasReached(550)) {
	                if (this.block==false) {
	                    attack(this.target);
	                    attack(this.target);
	                    attack(this.target);
	                }
	                if (this.block==true) {
	                    final int slot = 9;
	                    this.swap(slot, Minecraft.thePlayer.inventory.currentItem);
	                    attack(this.target);
	                    attack(this.target);
	                    attack(this.target);
	                    final int slot2 = 9;
	                    this.swap(slot2, Minecraft.thePlayer.inventory.currentItem);
	                    attack(this.target);
	                    attack(this.target);
	                this.target = null;
	                this.time.reset();
	            }
	            }}
	    }
	  
	  public static float[] getRotations(Entity entity)
	  {
	    if (entity == null) {
	      return null;
	    }
	    double diffX = entity.posX - Minecraft.thePlayer.posX;
	    double diffZ = entity.posZ - Minecraft.thePlayer.posZ;
	    double diffY;
	    if ((entity instanceof EntityPlayer))
	    {
	      EntityPlayer elb = (EntityPlayer)entity;
	      diffY = elb.posY + (
	        elb.getEyeHeight() - 0.4D) - (
	        		Minecraft.thePlayer.posY + Minecraft.thePlayer
	        .getEyeHeight());
	    }
	    else
	    {
	      diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 
	        2.0D - (
	        		Minecraft.thePlayer.posY + Minecraft.thePlayer
	        .getEyeHeight());
	    }
	    double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
	    float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
	    float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
	    
	    return new float[] { yaw, pitch };
	  }

	public void onEnable() {
		active = true;
		super.onEnable();
	}

	public void onUpdate() {
        if (mode==0){
            modname="Switch ";
        }else if (mode==1){
            modname="Tick ";
            
        }
		super.onUpdate();
	}

	public void onDisable() {
		active = false;
		super.onDisable();
	}

	public String getValue() {
		return modname;
	}
	
    public boolean isEntityInFov(final EntityLivingBase entity, double angle) {
        angle *= 0.5;
        final double angleDifference = MathUtil.getAngleDifference(mc.thePlayer.rotationYaw, getRotations(entity)[0]);
        return (angleDifference > 0.0 && angleDifference < angle) || (-angle < angleDifference && angleDifference < 0.0);
    }
    
    
    public void attack(final EntityLivingBase entity) {
        final boolean blocking = mc.thePlayer.isBlocking();
        if (blocking) {
        	Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,new BlockPos(0, 0, 0), EnumFacing.UP));     
        	}
        mc.thePlayer.swingItem();
        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
    }
    
    public boolean isValid(final EntityLivingBase entity) {
        return entity != null && entity.isEntityAlive() && this.isEntityInFov(entity, 360) && entity != mc.thePlayer && ((entity instanceof EntityPlayer) || (entity instanceof EntityAnimal) || (entity instanceof EntityMob)) && entity.getDistanceToEntity(mc.thePlayer) <= (mc.thePlayer.canEntityBeSeen(entity) ? this.range : 3.0) && (!entity.isInvisible()) && (entity.ticksExisted) > 10 && (this.friend==false && !FriendManager.isFriend(entity.getName()));
    }

    private EntityLivingBase findClosestEntity()
    {
      double distance = Double.MAX_VALUE;
      EntityLivingBase entity = null;
      for (Object object : Minecraft.theWorld.loadedEntityList) {
        if ((object instanceof EntityLivingBase))
        {
          EntityLivingBase e = (EntityLivingBase)object;
          if ((e.getDistanceToEntity(mc.thePlayer) < distance) && (isValid(e)))
          {
            entity = e;
            distance = e.getDistanceToEntity(mc.thePlayer);
          }
        }
      }
      return entity;
    }
}
