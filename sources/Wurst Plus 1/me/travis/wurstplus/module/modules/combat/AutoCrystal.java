package me.travis.wurstplus.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.event.events.PacketEvent;
import me.travis.wurstplus.event.events.RenderEvent;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.module.ModuleManager;
import me.travis.wurstplus.module.modules.chat.AutoEZ;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.EntityUtil;
import me.travis.wurstplus.util.Friends;
import me.travis.wurstplus.util.Pair;
import me.travis.wurstplus.util.wurstplusTessellator;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

@Module.Info(name = "Travis Aura", category = Module.Category.AURAS)
public class AutoCrystal extends Module {

  private Setting<Boolean> debug = this.register(Settings.b("Debug", false));
  private Setting<Boolean> place = this.register(Settings.b("Place", true));
  private Setting<Boolean> explode = this.register(Settings.b("Explode", true));
  private Setting<Boolean> onlyOwn = this.register(Settings.b("Only Explode Own", true));

  private Setting<Boolean> antiWeakness = this.register(Settings.b("Anti Weakness", true));
  private Setting<Boolean> offhand = this.register(Settings.b("Smart Offhand", false));
  private Setting<Integer> offhandHealth = this.register(Settings.integerBuilder("Offhand Min Health").withMinimum(0).withValue(3).withMaximum(20).withVisibility(o -> offhand.getValue()).build());

  private Setting<Integer> hitTickDelay = this.register(Settings.integerBuilder("Hit Delay").withMinimum(0).withValue(3).withMaximum(20).build());
  private Setting<Integer> placeTickDelay = this.register(Settings.integerBuilder("Place Delay").withMinimum(0).withValue(3).withMaximum(20).build());
  private Setting<Double> hitRange = this.register(Settings.doubleBuilder("Hit Range").withMinimum(0.0).withValue(5.5).build());
  private Setting<Double> placeRange = this.register(Settings.doubleBuilder("Place Range").withMinimum(0.0).withValue(3.5).build());

  private Setting<Double> minDamage = this.register(Settings.doubleBuilder("Min Damage").withMinimum(0.0).withValue(2.0).withMaximum(20.0).build());
  private Setting<Double> maxSelfDamage = this.register(Settings.doubleBuilder("Max Self Damage").withMinimum(0.0).withValue(8.0).withMaximum(20.0).build());

  private Setting<Boolean> rotate = this.register(Settings.b("Spoof Rotations", false));

  private Setting<Boolean> tabbottMode = this.register(Settings.b("Tabbott Mode", false));

  private Setting<PlaceMode> placeMode = this.register(Settings.e("Place Mode", PlaceMode.PLACEFIRST));

  private Setting<RenderMode> renderMode = this.register(Settings.e("Render Mode", RenderMode.UP));
  private Setting<Integer> alpha = this.register(Settings.integerBuilder("Transparency").withRange(0, 255).withValue(70).build());

  private Setting<Boolean> rainbow = this.register(Settings.b("RainbowMode", false));
  private Setting<Float> satuation = this.register(Settings.floatBuilder("Saturation").withRange(0.0f, 1.0f).withValue(0.6f).withVisibility(o -> rainbow.getValue()).build());
  private Setting<Float> brightness = this.register(Settings.floatBuilder("Brightness").withRange(0.0f, 1.0f).withValue(0.6f).withVisibility(o -> rainbow.getValue()).build());
  private Setting<Integer> speed = this.register(Settings.integerBuilder("Speed").withRange(0, 10).withValue(2).withVisibility(o -> rainbow.getValue()).build());

  private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(255).withVisibility(o -> !rainbow.getValue()).build());
  private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(255).withVisibility(o -> !rainbow.getValue()).build());
  private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).withVisibility(o -> !rainbow.getValue()).build());
  
  private BlockPos renderBlock;
  private boolean switchCooldown = false;
  private boolean isAttacking = false;
  private static boolean togglePitch = false;
  private static boolean isSpoofingAngles;
  private static double yaw;
  private static double pitch;
  private int oldSlot = -1;
  private int newSlot;
  private int hitDelayCounter;
  private int placeDelayCounter;
  EntityEnderCrystal crystal;
  private float hue;
  private Color rgbc;
    
  // made by travis#0001

  @EventHandler
    private Listener<PacketEvent.Send> packetListener = new Listener<PacketEvent.Send>(event -> {
        if (!this.rotate.getValue()) {
            return;
        }
        Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer && isSpoofingAngles) {
            ((CPacketPlayer)packet).yaw = (float)yaw;
            ((CPacketPlayer)packet).pitch = (float)pitch;
        }
    }, new Predicate[0]);

  public void onUpdate() {
    
    if (mc.player.getHealth() >= (float) offhandHealth.getValue() && this.offhand.getValue()) {
      this.placeCrystalOffhand();
    }

    if (this.placeMode.getValue().equals(PlaceMode.PLACEFIRST)) {
      if (this.placeDelayCounter >= this.placeTickDelay.getValue()) {
        this.placeCrystal();
      }
      if (this.hitDelayCounter >= this.hitTickDelay.getValue()) {
        this.breakCrystal();
      }
    } else {
      if (this.hitDelayCounter >= this.hitTickDelay.getValue()) {
        this.breakCrystal();
      }
      if (this.placeDelayCounter >= this.placeTickDelay.getValue()) {
        this.placeCrystal();
      }
    }

    this.placeDelayCounter++;
    this.hitDelayCounter++;
    resetRotation();

  }

  public EntityEnderCrystal getBestCrystal(double range) {

    int totems = getTotems();
    double bestDam = 0;
    double minDam = this.minDamage.getValue();

    EntityEnderCrystal bestCrystal = null;
    Entity target = null;

    List<Entity> players = mc.world.playerEntities.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.getName())).collect(Collectors.toList());
    List<Entity> crystals = mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).collect(Collectors.toList());

    for (Entity crystal : crystals) {
      if (mc.player.getDistance((Entity) crystal) > range || crystal == null) continue;
      for (Entity player : players) {
        if (player == mc.player || !(player instanceof EntityPlayer)) continue;
        EntityPlayer testTarget = (EntityPlayer)player;
        if (testTarget.isDead || testTarget.getHealth() <= 0.0f || testTarget.getDistanceSq(mc.player.getPosition()) >= 169.0) {
          if (this.debug.getValue()) {
            Command.sendChatMessage("passing - target shit");
          }
          continue;
        } 
        if (testTarget.getDistanceSq(crystal) >= 169.0) continue;
        if (testTarget.getHealth() < 8 && tabbottMode.getValue()) {
          minDam = 0.1;
        } 
        double targetDamage = calculateDamage(crystal.posX, crystal.posY, crystal.posZ, (Entity) testTarget);
        double selfDamage = calculateDamage(crystal.posX, crystal.posY, crystal.posZ, (Entity) mc.player);
        float healthTarget = testTarget.getHealth() + testTarget.getAbsorptionAmount();
        float healthSelf = mc.player.getHealth() + mc.player.getAbsorptionAmount();
        if (targetDamage < minDam || selfDamage > targetDamage && targetDamage < (double)healthTarget || selfDamage > this.maxSelfDamage.getValue() || healthSelf < .5 && totems == 0) {
          if (this.debug.getValue()) {
            Command.sendChatMessage("passing - too much self damage / too little damage");
          }
        }
        if (targetDamage > bestDam) {
          if (this.debug.getValue()) {
            Command.sendChatMessage("lookin good");
          }
          target = player;
          bestDam = targetDamage;
          bestCrystal = (EntityEnderCrystal) crystal;
        }
      }
    }
    if (ModuleManager.getModuleByName("AutoEZ").isEnabled() && target != null) {
      AutoEZ autoGG = (AutoEZ)ModuleManager.getModuleByName("AutoEZ");
      autoGG.addTargetedPlayer(target.getName());
    }
    players.clear();
    crystals.clear();
    return bestCrystal;
  }

  public BlockPos getBestBlock() {
    List<BlockPos> blocks = this.findCrystalBlocks(this.placeRange.getValue());
    List<Entity> players = mc.world.playerEntities.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.getName())).collect(Collectors.toList());

    BlockPos targetBlock = null;
    EntityPlayer target = null;

    int totems = getTotems();
    double bestDam = 0;
    double minDam = this.minDamage.getValue();
    
    for (Entity player : players) {
        if (player == mc.player || !(player instanceof EntityPlayer)) continue;
        EntityPlayer testTarget = (EntityPlayer) player;
        if (testTarget.isDead || testTarget.getHealth() <= 0.0f || testTarget.getDistanceSq(mc.player.getPosition()) >= 169.0) continue;
        for (BlockPos blockPos : blocks) {
            if (testTarget.getDistanceSq(blockPos) >= 169.0) continue;
            double targetDamage = calculateDamage((double)blockPos.x + 0.5, blockPos.y + 1, (double)blockPos.z + 0.5, (Entity)testTarget);
            double selfDamage = calculateDamage((double)blockPos.x + 0.5, blockPos.y + 1, (double)blockPos.z + 0.5, (Entity)mc.player);
            float healthTarget = testTarget.getHealth() + testTarget.getAbsorptionAmount();
            float healthSelf = mc.player.getHealth() + mc.player.getAbsorptionAmount();
            if (testTarget.getHealth() < 8 && tabbottMode.getValue()) {
                minDam = 0.1;
            }
            if (targetDamage < minDam || selfDamage > targetDamage && targetDamage < (double)healthTarget || selfDamage > this.maxSelfDamage.getValue() || healthSelf < .5 && totems == 0) continue;
            if (targetDamage > bestDam) {
              bestDam = targetDamage;
              targetBlock = blockPos;
              target = testTarget;
            }
        }
    }
    if (target == null) {
        this.renderBlock = null;
        resetRotation();
    }
    return targetBlock;
  }

  public void breakCrystal() {
    crystal = getBestCrystal(this.hitRange.getValue());
    if (crystal == null) {
      return;
    }
    if (this.explode.getValue()) {
      if (this.antiWeakness.getValue().booleanValue() && mc.player.isPotionActive(MobEffects.WEAKNESS)) {
        if (!this.isAttacking) {
          this.oldSlot = mc.player.inventory.currentItem;
          this.isAttacking = true;
        }
        this.newSlot = -1;
        for (int i = 0; i < 9; ++i) {
          ItemStack stack = mc.player.inventory.getStackInSlot(i);
          if (stack == ItemStack.EMPTY) continue;
          if (stack.getItem() instanceof ItemSword) {
            this.newSlot = i;
            break;
          }
          if (!(stack.getItem() instanceof ItemTool)) continue;
          this.newSlot = i;
          break;
        }
        if (this.newSlot != -1) {
          mc.player.inventory.currentItem = this.newSlot;
          this.switchCooldown = true;
        }
      }
      if (this.debug.getValue()) {
        Command.sendChatMessage("hitting crystal");
      }
      this.lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, (EntityPlayer)mc.player);
      mc.playerController.attackEntity((EntityPlayer)mc.player, (Entity)crystal);
      mc.player.swingArm(EnumHand.MAIN_HAND);
    }  
    this.hitDelayCounter = 0;
  }            

  public void placeCrystal() {

    // getting crystal slot
    int crystalSlot;
    if (this.oldSlot != -1) {
        mc.player.inventory.currentItem = this.oldSlot;
        this.oldSlot = -1;
    }
    this.isAttacking = false;
    crystalSlot = mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL ? mc.player.inventory.currentItem : -1;
    if (crystalSlot == -1) {
      for (int i = 0; i < 9; i++) {
        if (mc.player.inventory.getStackInSlot(i).getItem() != Items.END_CRYSTAL) continue;
        crystalSlot = i;
        break;
      }
    }
    boolean offhand = false;
    if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
      offhand = true;
    } else if (crystalSlot == -1) {
      return;
    }

    // getting & drawing on target block
    BlockPos targetBlock = getBestBlock();
    if (targetBlock == null) {
      return;
    }
    this.renderBlock = targetBlock;

    // placing
    if (this.place.getValue().booleanValue()) {
      if (!offhand && mc.player.inventory.currentItem != crystalSlot) { // cannot place
        mc.player.inventory.currentItem = crystalSlot;
        resetRotation();
        this.switchCooldown = true;
      }
      this.lookAtPacket((double)targetBlock.x + 0.5, (double)targetBlock.y - 0.5, (double)targetBlock.z + 0.5, (EntityPlayer)mc.player);
      // not a clue
      RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ), new Vec3d((double)targetBlock.x + 0.5, (double)targetBlock.y - 0.5, (double)targetBlock.z + 0.5));
      EnumFacing f = result == null || result.sideHit == null ? EnumFacing.UP : result.sideHit;
     
      if (this.switchCooldown) {
        this.switchCooldown = false;
        return;
      }
      if (this.debug.getValue()) {
        Command.sendChatMessage("placing crystal");
      }
      mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(targetBlock, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
    }
    if (this.rotate.getValue().booleanValue() && isSpoofingAngles) {
      if (togglePitch) {
        mc.player.rotationPitch = (float)((double)mc.player.rotationPitch + 4.0E-4);
        togglePitch = false;
      } else {
        mc.player.rotationPitch = (float)((double)mc.player.rotationPitch - 4.0E-4);
        togglePitch = true;
      }
    }
    this.placeDelayCounter = 0;
  }

  private void placeCrystalOffhand() {
    int slot = this.findCrystalsInHotbar();
    if (this.getOffhand().getItem() == Items.END_CRYSTAL || slot == -1) {
      return;
    }
    if (this.debug.getValue()) {
      Command.sendChatMessage("swapping "+mc.player.inventory.getStackInSlot(45).getItem());
      Command.sendChatMessage("with "+mc.player.inventory.getStackInSlot(slot).getItem());
    }
    this.invPickup(slot);
    this.invPickup(45);
    this.invPickup(slot);
  }

  private void invPickup(int slot) {
    mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
  }

  private ItemStack getOffhand() {
    return mc.player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
  }

  public int getTotems() {
    return offhand() +  mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
  }

  public int offhand() {
    if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
        return 1;
    }
    return 0;
  }

  @Override
  public void onWorldRender(RenderEvent event) {
      if (mc.getRenderManager().options == null) return;
      if (this.renderBlock != null) {
          if (rainbow.getValue()) {
              this.rgbc = Color.getHSBColor(this.hue, this.satuation.getValue(), this.brightness.getValue());
              this.drawBlock(this.renderBlock, rgbc.getRed(), rgbc.getGreen(), rgbc.getBlue());
              if (this.hue + ( (float) speed.getValue()/200) > 1) {
                  this.hue = 0;   
              } else {
                  this.hue += ( (float) speed.getValue()/200);
              }
          } else {
              this.drawBlock(this.renderBlock, this.red.getValue(), this.green.getValue(), this.blue.getValue());
          }
      }
  }

  private int findCrystalsInHotbar() {
    int slot = -1;
    for (int i = 44; i >= 9; --i) {
        if (mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
          slot = i;
          break;
        }
    }
    return slot;
  }

  private void drawBlock(final BlockPos blockPos, final int r, final int g, final int b) {
    final Color color = new Color(r, g, b, this.alpha.getValue());
    wurstplusTessellator.prepare(7);
    if (this.renderMode.getValue().equals(RenderMode.UP)) {
        wurstplusTessellator.drawBox(blockPos, color.getRGB(), 2);
    } else if (this.renderMode.getValue().equals(RenderMode.SOLID)) {
        wurstplusTessellator.drawBox(blockPos, color.getRGB(), 63);
    } else if (this.renderMode.getValue().equals(RenderMode.OUTLINE)) {
        IBlockState iBlockState2 = mc.world.getBlockState(blockPos);
        Vec3d interp2 = interpolateEntity((Entity)mc.player, mc.getRenderPartialTicks());
        wurstplusTessellator.drawBoundingBox(iBlockState2.getSelectedBoundingBox((World)mc.world, blockPos).offset(-interp2.x, -interp2.y, -interp2.z), 1.5f, r, g, b, this.alpha.getValue());
    } else {
        IBlockState iBlockState3 = mc.world.getBlockState(blockPos);
        Vec3d interp3 = interpolateEntity((Entity)mc.player, mc.getRenderPartialTicks());
        wurstplusTessellator.drawFullBox(iBlockState3.getSelectedBoundingBox((World)mc.world, blockPos).offset(-interp3.x, -interp3.y, -interp3.z), blockPos, 1.5f, r, g, b, this.alpha.getValue());
    }
    wurstplusTessellator.release();
  }

  public static Vec3d interpolateEntity(Entity entity, float time) {
      return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)time);
  }

  private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
    double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
    setYawAndPitch((float) v[0], (float) v[1]);
  }

  private boolean canPlaceCrystal(BlockPos blockPos) {
    BlockPos boost = blockPos.add(0, 1, 0);
    BlockPos boost2 = blockPos.add(0, 2, 0);
    return (mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && mc.world.getBlockState(boost).getBlock() == Blocks.AIR && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty() && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
  }

  public static BlockPos getPlayerPos() {
    return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
  }

  private List<BlockPos> findCrystalBlocks(double range) {
    NonNullList<BlockPos> positions = NonNullList.create();
    positions.addAll(getSphere(getPlayerPos(), (float) range, (int) range, false, true, 0)
     .stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
    return positions;
  }

  public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
    List<BlockPos> circleblocks = new ArrayList<BlockPos>();
    int cx = loc.getX();
    int cy = loc.getY();
    int cz = loc.getZ();
    for (int x = cx - (int) r; x <= cx + r; x++) {
      for (int z = cz - (int) r; z <= cz + r; z++) {
        for (int y = sphere ? (cy - (int) r) : cy; y < (sphere ? (cy + r) : (cy + h)); y++) {
          double dist = ((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0));
          if (dist < (r * r) && (!hollow || dist >= ((r - 1.0F) * (r - 1.0F)))) {
            BlockPos l = new BlockPos(x, y + plus_y, z);
            circleblocks.add(l);
          }
        }
      }
    }
    return circleblocks;
  }

  public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
    float doubleExplosionSize = 12.0F;
    double distancedsize = entity.getDistance(posX, posY, posZ) / doubleExplosionSize;
    Vec3d vec3d = new Vec3d(posX, posY, posZ);
    double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
    double v = (1.0D - distancedsize) * blockDensity;
    float damage = (int) ((v * v + v) / 2.0D * 9.0D * doubleExplosionSize + 1.0D);
    double finald = 1.0D;
    if (entity instanceof EntityLivingBase)
      finald = getBlastReduction((EntityLivingBase) entity, getDamageMultiplied(damage),
          new Explosion(mc.world, null, posX, posY, posZ, 6.0F, false, true));
    return (float) finald;
  }

  public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
    if (entity instanceof EntityPlayer) {
      EntityPlayer ep = (EntityPlayer) entity;
      DamageSource ds = DamageSource.causeExplosionDamage(explosion);
      damage = CombatRules.getDamageAfterAbsorb(damage, ep.getTotalArmorValue(),
          (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
      int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
      float f = MathHelper.clamp(k, 0.0F, 20.0F);
      damage *= (1.0F - f / 25.0F);
      if (entity.isPotionActive(Potion.getPotionById(11)))
        damage -= damage / 4.0F;
      return Math.max(damage - ep.getAbsorptionAmount(), 0.0F);
    }
    return CombatRules.getDamageAfterAbsorb(damage, entity.getTotalArmorValue(),
        (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
  }

  private static float getDamageMultiplied(float damage) {
    int diff = mc.world.getDifficulty().getId();
    return damage * ((diff == 0) ? 0.0F : ((diff == 2) ? 1.0F : ((diff == 1) ? 0.5F : 1.5F)));
  }

  public static float calculateDamage(EntityEnderCrystal crystal, Entity entity) {
    return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
  }

  private static void setYawAndPitch(float yaw1, float pitch1) {
    yaw = yaw1;
    pitch = pitch1;
    isSpoofingAngles = true;
  }

  private static void resetRotation() {
    if (isSpoofingAngles) {
      yaw = mc.player.rotationYaw;
      pitch = mc.player.rotationPitch;
      isSpoofingAngles = false;
    }
  }

  protected void onEnable() {
    Command.sendChatMessage("we \u00A7l\u00A72gaming\u00A7r");
    if (mc.player == null) {
      this.disable();
      return;
    }
  }

  public void onDisable() {
    this.renderBlock = null;
    Command.sendChatMessage("we aint \u00A7l\u00A74gaming\u00A7r no more");
    resetRotation();
  }

  private static enum RenderMode {
    SOLID,
    OUTLINE,
    UP,
    FULL;   
  }

  private static enum PlaceMode {
    PLACEFIRST,
    BREAKFIRST;
  }

}