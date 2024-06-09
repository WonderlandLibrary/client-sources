package lunadevs.luna.utils;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.chunk.Chunk;

public class ModuleHelper
  extends Gui
{
  public Minecraft mc = Minecraft.getMinecraft();
  public ArrayList<Entity> entities = new ArrayList();
  public float delay;
  public int lookDelay;
  public float oldYaw;
  public float oldPitch;
  public float blockDamage;
  public float hitDelay;
  
  public int getBestToolForBlock(BlockPos pos)
  {
    Block block = this.mc.theWorld.getBlockState(pos).getBlock();
    int slot = 0;
    float damage = 0.1F;
    for (int index = 36; index < 45; index++)
    {
      ItemStack itemStack = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
      if ((itemStack != null) && (block != null) && (itemStack.getItem().getStrVsBlock(itemStack, block) > damage))
      {
        slot = index - 36;
        damage = itemStack.getItem().getStrVsBlock(itemStack, block);
      }
    }
    if (damage > 0.1F) {
      return slot;
    }
    return this.mc.thePlayer.inventory.currentItem;
  }
  
  public boolean shouldHitBlock(int x, int y, int z, double dist)
  {
    Block block = getBlock(x, y, z);
    boolean isNotLiquid = !(block instanceof BlockLiquid);
    boolean canSeeBlock = canBlockBeSeen(x, y, z);
    return (isNotLiquid) && (canSeeBlock);
  }
  
  public void placeBlock(int posX, int posY, int posZ)
  {
    this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(getBlockPos(posX, posY, posZ), getEnumFacing(posX, posY, posZ).getIndex(), this.mc.thePlayer.getHeldItem(), 0.0F, 0.0F, 0.0F));
  }
  
  public int getNextSlotInContainer(Container container)
  {
    int i = 0;
    for (int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27; i < slotAmount; i++) {
      if (container.getInventory().get(i) != null) {
        return i;
      }
    }
    return -1;
  }
  
  public boolean isContainerEmpty(Container container)
  {
    int i = 0;
    for (int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27; i < slotAmount; i++) {
      if (container.getSlot(i).getHasStack()) {
        return false;
      }
    }
    return true;
  }
  
  public boolean canReach(double posX, double posY, double posZ, float distance)
  {
    double blockDistance = getDistance(posX, posY, posZ);
    return (blockDistance < distance) && (blockDistance > -distance);
  }
  
  public TileEntityChest getBestEntity(double range)
  {
    TileEntityChest tempEntity = null;
    double dist = range;
    for (Object i : this.mc.theWorld.loadedEntityList)
    {
      TileEntityChest entity = (TileEntityChest)i;
      if (getDistanceToEntity(entity) <= 6.0F)
      {
        double curDist = getDistanceToEntity(entity);
        if (curDist <= dist)
        {
          dist = curDist;
          tempEntity = entity;
        }
      }
    }
    return tempEntity;
  }
  
  public float getDistanceToEntity(TileEntity tileEntity)
  {
    float var2 = (float)(this.mc.thePlayer.posX - tileEntity.getPos().getX());
    float var3 = (float)(this.mc.thePlayer.posY - tileEntity.getPos().getY());
    float var4 = (float)(this.mc.thePlayer.posZ - tileEntity.getPos().getZ());
    return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
  }
  
  public void breakBlockLegit(int posX, int posY, int posZ, int delay)
  {
    this.hitDelay += 1.0F;
    this.mc.thePlayer.swingItem();
    if (this.blockDamage == 0.0F) {
      this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, getBlockPos(posX, posY, posZ), getEnumFacing(posX, posY, posZ)));
    }
    if (this.hitDelay >= delay)
    {
      this.blockDamage += getBlock(posX, posY, posZ).getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.theWorld, new BlockPos(posX, posY, posZ));
      this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), new BlockPos(posX, posY, posZ), (int)(this.blockDamage * 10.0F) - 1);
      if (this.blockDamage >= (this.mc.playerController.isInCreativeMode() ? 0.0F : 1.0F))
      {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, getBlockPos(posX, posY, posZ), getEnumFacing(posX, posY, posZ)));
        this.mc.playerController.func_178888_a(getBlockPos(posX, posY, posZ), getEnumFacing(posX, posY, posZ));
        this.blockDamage = 0.0F;
        this.hitDelay = 0.0F;
      }
    }
  }
  
  public boolean canBlockBeSeen(int posX, int posY, int posZ)
  {
    MovingObjectPosition result = rayTrace(posX, posY, posZ);
    System.out.println(result.field_178784_b);
    return result.field_178784_b != null;
  }
  
  public MovingObjectPosition rayTrace(int posX, int posY, int posZ)
  {
    Vec3 player = new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight(), this.mc.thePlayer.posZ);
    Vec3 block = new Vec3(posX + 0.5F, posY + 0.5F, posZ + 0.5F);
    return this.mc.theWorld.rayTraceBlocks(player, block);
  }
  
  public void breakBlock(double posX, double posY, double posZ)
  {
    this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, getBlockPos(posX, posY, posZ), getEnumFacing((int)posX, (int)posY, (int)posZ)));
    this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, getBlockPos(posX, posY, posZ), getEnumFacing((int)posX, (int)posY, (int)posZ)));
  }
  
  public EnumFacing getEnumFacing(float posX, float posY, float posZ)
  {
    return EnumFacing.func_176737_a(posX, posY, posZ);
  }
  
  public BlockPos getBlockPos(double x, double y, double z)
  {
    BlockPos pos = new BlockPos(x, y, z);
    return pos;
  }
  
  public void moveForward()
  {
    this.mc.gameSettings.keyBindForward.pressed = true;
  }
  
  public void moveLeft()
  {
    this.mc.gameSettings.keyBindLeft.pressed = true;
  }
  
  public void moveRight()
  {
    this.mc.gameSettings.keyBindRight.pressed = true;
  }
  
  public void moveBack()
  {
    this.mc.gameSettings.keyBindBack.pressed = true;
  }
  
  public void stopMoving()
  {
    this.mc.gameSettings.keyBindForward.pressed = false;
    this.mc.gameSettings.keyBindLeft.pressed = false;
    this.mc.gameSettings.keyBindRight.pressed = false;
    this.mc.gameSettings.keyBindBack.pressed = false;
  }
  
  public Block getBlock(double posX, double posY, double posZ)
  {
    posX = MathHelper.floor_double(posX);
    posY = MathHelper.floor_double(posY);
    posZ = MathHelper.floor_double(posZ);
    return this.mc.theWorld.getChunkFromBlockCoords(new BlockPos(posX, posY, posZ)).getBlock(new BlockPos(posX, posY, posZ));
  }
  
  public float getDistance(double x, double y, double z)
  {
    float xDiff = (float)(this.mc.thePlayer.posX - x);
    float yDiff = (float)(this.mc.thePlayer.posY - y);
    float zDiff = (float)(this.mc.thePlayer.posZ - z);
    return MathHelper.sqrt_float(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
  }
  
  public float getDistanceToVec(double x, double y, double z, double x2, double y2, double z2)
  {
    float xDiff = (float)(x - x2);
    float yDiff = (float)(y - y2);
    float zDiff = (float)(z - z2);
    return MathHelper.sqrt_float(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
  }
  
  public void faceBlock(double posX, double posY, double posZ)
  {
    double diffX = posX - this.mc.thePlayer.posX;
    double diffZ = posZ - this.mc.thePlayer.posZ;
    double diffY = posY - (this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight());
    double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
    float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
    float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
    this.mc.thePlayer.rotationPitch += MathHelper.wrapAngleTo180_float(pitch - this.mc.thePlayer.rotationPitch);
    this.mc.thePlayer.rotationYaw += MathHelper.wrapAngleTo180_float(yaw - this.mc.thePlayer.rotationYaw);
  }
  
  public boolean isVisibleFOV(TileEntity tileEntity, EntityPlayerSP thePlayer, int fov)
  {
    return (Math.abs(getRotationsTileEntity(tileEntity)[0].floatValue() - thePlayer.rotationYaw) % 360.0F > 180.0F ? 360.0F - Math.abs(getRotationsTileEntity(tileEntity)[0].floatValue() - thePlayer.rotationYaw) % 360.0F : Math.abs(getRotationsTileEntity(tileEntity)[0].floatValue() - thePlayer.rotationYaw) % 360.0F) <= fov;
  }
  
  public void damage()
  {
    double[] d = { 0.2D, 0.26D };
    for (int a = 0; a < 60; a++) {
      for (int i = 0; i < d.length; i++) {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + d[i], this.mc.thePlayer.posZ, false));
      }
    }
  }
  
  public String direction(EntityLivingBase player)
  {
    EnumFacing face = EnumFacing.getHorizontal(MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3);
    int direction = face.getHorizontalIndex();
    if (this.mc.thePlayer.moveForward < 0.0F) {
      direction = face.getOpposite().getHorizontalIndex();
    }
    if (direction == 0) {
      return "SOUTH";
    }
    if (direction == 1) {
      return "WEST";
    }
    if (direction == 2) {
      return "NORTH";
    }
    if (direction == 3) {
      return "EAST";
    }
    return null;
  }
  
  public void saveAngles()
  {
    this.oldPitch = this.mc.thePlayer.rotationPitch;
    this.oldYaw = this.mc.thePlayer.rotationYaw;
  }
  
  public void resetAngles()
  {
    this.mc.thePlayer.rotationPitch = this.oldPitch;
    this.mc.thePlayer.rotationYaw = this.oldYaw;
  }
  
  public boolean isOnFire()
  {
    if (((this.mc.thePlayer.isBurning()) && (!this.mc.thePlayer.isUsingItem()) && (!this.mc.thePlayer.isPotionActive(Potion.fireResistance)) && (!this.mc.thePlayer.isImmuneToFire()) && (this.mc.thePlayer.onGround) && (!this.mc.thePlayer.isInWater()) && (!this.mc.thePlayer.isInsideOfMaterial(Material.lava)) && (!this.mc.thePlayer.isInsideOfMaterial(Material.fire))) || (this.mc.thePlayer.isPotionActive(Potion.digSlowdown)) || (this.mc.thePlayer.isPotionActive(Potion.poison)) || (this.mc.thePlayer.isPotionActive(Potion.weakness)) || (this.mc.thePlayer.isPotionActive(Potion.moveSlowdown))) {
      return true;
    }
    return false;
  }
  
  public Entity getBestEntity(double range, float fov, boolean rayTrace, int ticksExisted, int invisibles, int players, int mobs, int animals)
  {
    Entity tempEntity = null;
    double dist = range;
    for (Object i : this.mc.theWorld.loadedEntityList)
    {
      boolean isValidEntity = ((mobs == 1) && ((i instanceof EntityMob)) && (!((Entity)i).isInvisible()) && (!(i instanceof EntityAnimal)) && (!(i instanceof EntityPlayer))) || ((animals == 1) && ((i instanceof EntityAnimal)) && (!((Entity)i).isInvisible()) && (!(i instanceof EntityMob)) && (!(i instanceof EntityPlayer))) || ((players == 1) && ((i instanceof EntityPlayer)) && (!((Entity)i).isInvisible()) && (!(i instanceof EntityAnimal)) && (!(i instanceof EntityMob)) && (!lunadevs.luna.friend.FriendManager.isFriend(((Entity)i).getName()))) || ((invisibles == 1) && (((Entity)i).isInvisible()) && (!lunadevs.luna.friend.FriendManager.isFriend(((Entity)i).getName())));
      if (isValidEntity)
      {
        Entity entity = (Entity)i;
        if (shouldHitEntity(entity, range, fov, rayTrace, ticksExisted, invisibles, players, mobs, animals))
        {
          double curDist = this.mc.thePlayer.getDistanceToEntity(entity);
          if (curDist <= dist)
          {
            dist = curDist;
            tempEntity = entity;
          }
        }
      }
    }
    return tempEntity;
  }
  
  public void clearEntities()
  {
    this.entities.clear();
  }
  

  
  public boolean shouldHitEntity(Entity e, double range, float fov, boolean rayTrace, int ticksExisted, int invisibles, int players, int mobs, int animals)
  {
    boolean isAlive = e instanceof EntityLivingBase;
    boolean isNotMe = e != this.mc.thePlayer;
    boolean isNotNull = e != null;
    boolean isInRange = this.mc.thePlayer.getDistanceToEntity(e) <= range;
    boolean isInFov = isVisibleFOV(e, this.mc.thePlayer, fov);
    boolean isNotDead = !e.isDead;
    boolean canSeeEntity = this.mc.thePlayer.canEntityBeSeen(e);
    boolean ticks = e.ticksExisted >= ticksExisted;
    boolean isNotFakeDummie = e.getName() != this.mc.thePlayer.getName();
    if (rayTrace) {
      return (isAlive) && (isNotFakeDummie) && (isNotDead) && (canSeeEntity) && (ticks) && (isInFov) && (isNotMe) && (isNotNull) && (isInRange);
    }
    return (isAlive) && (isNotFakeDummie) && (isNotDead) && (ticks) && (isInFov) && (isNotMe) && (isNotNull) && (isInRange);
  }
  
  public float getDistanceBetweenAngles(float ang1, float ang2)
  {
    return Math.abs(((ang1 - ang2 + 180.0F) % 360.0F + 360.0F) % 360.0F - 180.0F);
  }
  
  public void attackTarget(EntityLivingBase entity)
  {
    float sharpLevel = EnchantmentHelper.func_152377_a(this.mc.thePlayer.getHeldItem(), entity.getCreatureAttribute());
    this.mc.thePlayer.swingItem();
    this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
    if (sharpLevel > 0.0F) {
      this.mc.thePlayer.onEnchantmentCritical(entity);
    }
  }
  
  public float getPitchChange(Entity entity)
  {
    double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
    double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
    double deltaY = entity.posY - 2.2D + entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY;
    double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
    double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
    return -MathHelper.wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationPitch - (float)pitchToEntity) - 2.5F;
  }
  
  public float getYawChange(Entity entity)
  {
    double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
    double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
    double yawToEntity = 0.0D;
    if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
      yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
    } else if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
      yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
    } else {
      yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
    }
    return MathHelper.wrapAngleTo180_float(-(Minecraft.getMinecraft().thePlayer.rotationYaw - (float)yawToEntity));
  }
  
  public boolean canCrit()
  {
    return (!this.mc.thePlayer.isInWater()) && (this.mc.thePlayer.onGround);
  }
  
  public boolean needsHealth()
  {
    boolean isLow = this.mc.thePlayer.getHealth() <= 20.0F;
    return isLow;
  }
  
  public void faceEntity(Entity entity)
  {
    double diffX = entity.posX - this.mc.thePlayer.posX;
    double diffZ = entity.posZ - this.mc.thePlayer.posZ;
    double diffY = entity.posY + entity.getEyeHeight() - this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight() * -2.0F + 1.2999999523162842D;
    
    double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
    float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
    float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
    this.mc.thePlayer.rotationPitch += MathHelper.wrapAngleTo180_float(pitch - this.mc.thePlayer.rotationPitch);
    this.mc.thePlayer.rotationYaw += MathHelper.wrapAngleTo180_float(yaw - this.mc.thePlayer.rotationYaw);
  }
  
  public boolean isMouseOverEntity(Entity entity)
  {
    if (this.mc.objectMouseOver.entityHit == entity) {
      return true;
    }
    return false;
  }
  
  private Float[] getRotations(Entity entity)
  {
    double posX = entity.posX - this.mc.thePlayer.posX;
    double posZ = entity.posZ - this.mc.thePlayer.posZ;
    double posY = entity.posY + entity.getEyeHeight() - this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight();
    double helper = MathHelper.sqrt_double(posX * posX + posZ * posZ);
    float newYaw = (float)Math.toDegrees(-Math.atan(posX / posZ));
    float newPitch = (float)-Math.toDegrees(Math.atan(posY / helper));
    if ((posZ < 0.0D) && (posX < 0.0D)) {
      newYaw = (float)(90.0D + Math.toDegrees(Math.atan(posZ / posX)));
    } else if ((posZ < 0.0D) && (posX > 0.0D)) {
      newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(posZ / posX)));
    }
    return new Float[] { Float.valueOf(newYaw), Float.valueOf(newPitch) };
  }
  
  private Float[] getRotationsTileEntity(TileEntity entity)
  {
    double posX = entity.getPos().getX() - this.mc.thePlayer.posX;
    double posZ = entity.getPos().getY() - this.mc.thePlayer.posZ;
    double posY = entity.getPos().getZ() + 1 - this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight();
    double helper = MathHelper.sqrt_double(posX * posX + posZ * posZ);
    float newYaw = (float)Math.toDegrees(-Math.atan(posX / posZ));
    float newPitch = (float)-Math.toDegrees(Math.atan(posY / helper));
    if ((posZ < 0.0D) && (posX < 0.0D)) {
      newYaw = (float)(90.0D + Math.toDegrees(Math.atan(posZ / posX)));
    } else if ((posZ < 0.0D) && (posX > 0.0D)) {
      newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(posZ / posX)));
    }
    return new Float[] { Float.valueOf(newYaw), Float.valueOf(newPitch) };
  }
  
  public boolean faceEntitySmooth(Entity e, float speed)
  {
    double x = e.posX - this.mc.thePlayer.posX;
    double y = e.posY - this.mc.thePlayer.posY;
    double z = e.posZ - this.mc.thePlayer.posZ;
    double d1 = this.mc.thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() - (e.posY + e.getEyeHeight());
    double d3 = MathHelper.sqrt_double(x * x + z * z);
    float f = (float)(Math.atan2(z, x) * 180.0D / 3.141592653589793D) - 90.0F;
    float f1 = (float)(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D);
    
    f = MathHelper.wrapAngleTo180_float(f);
    f1 = MathHelper.wrapAngleTo180_float(f1);
    
    f -= MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw);
    f1 -= MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationPitch);
    
    boolean facing = (f < speed) && (f1 < speed) && (f > -speed) && (f1 > -speed);
    if (f > 0.0F) {
      f = MathHelper.clamp_float(f, 0.0F, speed);
    } else if (f < 0.0F) {
      f = MathHelper.clamp_float(f, -speed, 0.0F);
    }
    if (f1 > 0.0F) {
      f1 = MathHelper.clamp_float(f1, 0.0F, speed);
    } else if (f1 < 0.0F) {
      f1 = MathHelper.clamp_float(f1, -speed, 0.0F);
    }
    this.mc.thePlayer.rotationYaw += f;
    this.mc.thePlayer.rotationPitch += f1;
    
    return facing;
  }
  
  public boolean isVisibleFOV(Entity e, Entity e2, float fov)
  {
    return (Math.abs(getRotations(e)[0].floatValue() - e2.rotationYaw) % 360.0F > 180.0F ? 360.0F - Math.abs(getRotations(e)[0].floatValue() - e2.rotationYaw) % 360.0F : Math.abs(getRotations(e)[0].floatValue() - e2.rotationYaw) % 360.0F) <= fov;
  }
  
  public boolean isVisibleFOVTileEntity(TileEntity e, Entity e2, float fov)
  {
    return (Math.abs(getRotationsTileEntity(e)[0].floatValue() - e2.rotationYaw) % 360.0F > 180.0F ? 360.0F - Math.abs(getRotationsTileEntity(e)[0].floatValue() - e2.rotationYaw) % 360.0F : Math.abs(getRotationsTileEntity(e)[0].floatValue() - e2.rotationYaw) % 360.0F) <= fov;
  }
  
  public boolean canAttackEntityNotLegit(Entity e)
  {
    if (e == null) {
      return false;
    }
    boolean isNotMe = e != this.mc.thePlayer;
    boolean isInRange = this.mc.thePlayer.getDistanceToEntity(e) <= 6.0F;
    boolean isAlive = ((e instanceof EntityLivingBase)) && (!e.isDead);
    return (isNotMe) && (isInRange) && (isAlive);
  }
  
  public void attackEntityNotLegit(Entity e)
  {
    this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(e, C02PacketUseEntity.Action.ATTACK));
  }
}
