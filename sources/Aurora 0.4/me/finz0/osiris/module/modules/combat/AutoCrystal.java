package me.finz0.osiris.module.modules.combat;

import de.Hero.settings.Setting;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.chat.AutoGG;
import me.finz0.osiris.util.AuroraTessellator;
import me.finz0.osiris.util.GeometryMasks;
import me.finz0.osiris.util.OsirisTessellator;
import me.finz0.osiris.util.Rainbow;
import me.finz0.osiris.friends.Friends;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//kami skid
public class AutoCrystal extends Module {
    public AutoCrystal() {
        super("AutoCrystal", Category.COMBAT);
    }

    private BlockPos render;
    private Entity renderEnt;
    // we need this cooldown to not place from old hotbar slot, before we have switched to crystals
    private boolean switchCooldown = false;
    private boolean isAttacking = false;
    private int oldSlot = -1;
    private int newSlot;
    private int waitCounter;
    EnumFacing f;
    public int crystals;

    Setting explode;
    Setting waitTick;
    Setting range;
    Setting walls;
    Setting antiWeakness;
    Setting place;
    Setting autoSwitch;
    Setting placeRange;
    Setting minDmg;
    Setting facePlace;
    Setting raytrace;
    Setting rotate;
    Setting spoofRotations;
    Setting chat;
    Setting rainbow;
    Setting espR;
    Setting espG;
    Setting espB;
    Setting espA;
    Setting maxSelfDmg;
    Setting noGappleSwitch;
    Setting renderMode;

    public boolean isActive = false;


    public void setup() {
        explode = new Setting("Hit", this, true, "AutoCrystalHit");
        AuroraMod.getInstance().settingsManager.rSetting(explode);
        waitTick = new Setting("TickDelay", this, 1, 0, 20.0, true, "AutoCrystalTickDelay");
        AuroraMod.getInstance().settingsManager.rSetting(waitTick);
        range = new Setting("HitRange", this, 5.0, 0.0, 10.0, false, "AutoCrystalHitRange");
        AuroraMod.getInstance().settingsManager.rSetting(range);
        walls = new Setting("WallsRange", this, 3.5, 0.0, 10.0, false, "AutoCrystalWallsRange");
        AuroraMod.getInstance().settingsManager.rSetting(walls);
        antiWeakness = new Setting("AntiWeakness", this, true, "AutoCrystalAntiWeakness");
        AuroraMod.getInstance().settingsManager.rSetting(antiWeakness);


        place = new Setting("Place", this, true, "AutoCrystalPlace");
        AuroraMod.getInstance().settingsManager.rSetting(place);
        autoSwitch = new Setting("AutoSwitch", this, true, "AutoCrystalAutoSwitch");
        AuroraMod.getInstance().settingsManager.rSetting(autoSwitch);
        AuroraMod.getInstance().settingsManager.rSetting(noGappleSwitch = new Setting("NoGapSwitch", this, false, "AutoCrystalNoGapSwitch"));
        placeRange = new Setting("PlaceRange", this, 5.0, 0.0, 10.0, false, "AutoCrystalPlaceRange");
        AuroraMod.getInstance().settingsManager.rSetting(placeRange);
        minDmg = new Setting("MinDamage", this, 5.0, 0.0, 40.0, false, "AutoCrystalMinDamage");
        AuroraMod.getInstance().settingsManager.rSetting(minDmg);
        facePlace = new Setting("FaceplaceHP", this, 6.0, 0.0, 40.0, false, "AutoCrystalFaceplaceHP");
        AuroraMod.getInstance().settingsManager.rSetting(facePlace);
        raytrace = new Setting("Raytrace", this, false, "AutoCrystalRaytrace");
        AuroraMod.getInstance().settingsManager.rSetting(raytrace);
        rotate = new Setting("Rotate", this, true, "AutoCrystalRotate");
        AuroraMod.getInstance().settingsManager.rSetting(rotate);
        spoofRotations = new Setting("SpoofAngles", this, true, "AutoCrystalSpoofAngles");
        AuroraMod.getInstance().settingsManager.rSetting(spoofRotations);
        AuroraMod.getInstance().settingsManager.rSetting(maxSelfDmg = new Setting("MaxSelfDmg", this, 10, 0, 36, false, "AutoCrystalMaxSelfDamage"));
        chat = new Setting("ToggleMsgs", this, true, "AutoCrystalToggleMessages");
        AuroraMod.getInstance().settingsManager.rSetting(chat);

        rainbow = new Setting("EspRainbow", this, false, "AutoCrystalEspRainbow");
        AuroraMod.getInstance().settingsManager.rSetting(rainbow);
        espR = new Setting("EspRed", this, 200, 0, 255, true, "AutoCrystalEspRed");
        AuroraMod.getInstance().settingsManager.rSetting(espR);
        espG = new Setting("EspGreen", this, 50, 0, 255, true, "AutoCrystalEspGreen");
        AuroraMod.getInstance().settingsManager.rSetting(espG);
        espB = new Setting("EspBlue", this, 200, 0, 255, true, "AutoCrystalEspBlue");
        AuroraMod.getInstance().settingsManager.rSetting(espB);
        espA = new Setting("EspAlpha", this, 50, 0, 255, true, "AutoCrystalEspAlpha");
        AuroraMod.getInstance().settingsManager.rSetting(espA);

}

    public void onUpdate() {
        isActive = false;
        if (mc.player == null || mc.player.isDead) return; // bruh
        EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityEnderCrystal)
                .filter(e -> mc.player.getDistance(e) <= range.getValDouble())
                .map(entity -> (EntityEnderCrystal) entity)
                .min(Comparator.comparing(c -> mc.player.getDistance(c)))
                .orElse(null);
        if (explode.getValBoolean() && crystal != null) {
            if (!mc.player.canEntityBeSeen(crystal) && mc.player.getDistance(crystal) > walls.getValDouble()) return;

            if (waitTick.getValDouble() > 0) {
                if (waitCounter < waitTick.getValDouble()) {
                    waitCounter++;
                    return;
                } else {
                    waitCounter = 0;
                }
            }

            if (antiWeakness.getValBoolean() && mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                if (!isAttacking) {
                    // save initial player hand
                    oldSlot = mc.player.inventory.currentItem;
                    isAttacking = true;
                }
                // search for sword and tools in hotbar
                newSlot = -1;
                for (int i = 0; i < 9; i++) {
                    ItemStack stack = mc.player.inventory.getStackInSlot(i);
                    if (stack == ItemStack.EMPTY) {
                        continue;
                    }
                    if ((stack.getItem() instanceof ItemSword)) {
                        newSlot = i;
                        break;
                    }
                    if ((stack.getItem() instanceof ItemTool)) {
                        newSlot = i;
                        break;
                    }
                }
                // check if any swords or tools were found
                if (newSlot != -1) {
                    mc.player.inventory.currentItem = newSlot;
                    switchCooldown = true;
                }
            }

            isActive = true;
            if (rotate.getValBoolean()) {
                lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, mc.player);
            }
            mc.playerController.attackEntity(mc.player, crystal);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            isActive = false;
            return;
        } else {
            resetRotation();
            if (oldSlot != -1) {
                mc.player.inventory.currentItem = oldSlot;
                oldSlot = -1;
            }
            isAttacking = false;
            isActive = false;
        }

        int crystalSlot = mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL ? mc.player.inventory.currentItem : -1;
        if (crystalSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
                    crystalSlot = l;
                    break;
                }
            }
        }
        boolean offhand = false;
        if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            offhand = true;
        } else if (crystalSlot == -1) {
            return;
        }

        List<BlockPos> blocks = findCrystalBlocks();
        List<Entity> entities = new ArrayList<>();
        entities.addAll(mc.world.playerEntities.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.getName())).sorted(Comparator.comparing(e -> mc.player.getDistance(e))).collect(Collectors.toList()));

        BlockPos q = null;
        double damage = .5;
        for (Entity entity : entities) {
            if (entity == mc.player) continue;
            if (((EntityLivingBase) entity).getHealth() <= 0 || entity.isDead || mc.player == null) {
                continue;
            }
            for (BlockPos blockPos : blocks) {
                double b = entity.getDistanceSq(blockPos);
                if (b >= 169) {
                    continue; // If this block if further than 13 (3.6^2, less calc) blocks, ignore it. It'll take no or very little damage
                }
                double d = calculateDamage(blockPos.getX() + .5, blockPos.getY() + 1, blockPos.getZ() + .5, entity);
                if (d < minDmg.getValDouble() && ((EntityLivingBase) entity).getHealth() + ((EntityLivingBase) entity).getAbsorptionAmount() > facePlace.getValDouble()) {
                    continue;
                }
                if (d > damage) {
                    double self = calculateDamage(blockPos.getX() + .5, blockPos.getY() + 1, blockPos.getZ() + .5, mc.player);
                    // If this deals more damage to ourselves than it does to our target, continue. This is only ignored if the crystal is sure to kill our target but not us.
                    // Also continue if our crystal is going to hurt us.. alot
                    if ((self > d && !(d < ((EntityLivingBase) entity).getHealth())) || self - .5 > mc.player.getHealth()) {
                        continue;
                    }
                    if (self > maxSelfDmg.getValDouble())
                        continue;
                    damage = d;
                    q = blockPos;
                    renderEnt = entity;
                }
            }
        }
        if (damage == .5) {
            render = null;
            renderEnt = null;
            resetRotation();
            return;
        }
        render = q;

        if (place.getValBoolean()) {
            if (mc.player == null) return;
            isActive = true;
            if (rotate.getValBoolean()) {
                lookAtPacket(q.getX() + .5, q.getY() - .5, q.getZ() + .5, mc.player);
            }
            RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(q.getX() + .5, q.getY() - .5d, q.getZ() + .5));
            if (raytrace.getValBoolean()) {
                if (result == null || result.sideHit == null) {
                    q = null;
                    f = null;
                    render = null;
                    resetRotation();
                    isActive = false;
                    return;
                } else {
                    f = result.sideHit;
                }
            }

            if (!offhand && mc.player.inventory.currentItem != crystalSlot) {
                if (autoSwitch.getValBoolean()) {
                    if (noGappleSwitch.getValBoolean() && isEatingGap()) {
                        isActive = false;
                        resetRotation();
                        return;
                    } else {
                        isActive = true;
                        mc.player.inventory.currentItem = crystalSlot;
                        resetRotation();
                        switchCooldown = true;
                    }
                }
                return;
            }
            // return after we did an autoswitch
            if (switchCooldown) {
                switchCooldown = false;
                return;
            }
            //mc.playerController.processRightClickBlock(mc.player, mc.world, q, f, new Vec3d(0, 0, 0), EnumHand.MAIN_HAND);
            if (q != null && mc.player != null) {
                isActive = true;
                if (raytrace.getValBoolean() && f != null) {
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(q, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
                } else {
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(q, EnumFacing.UP, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
                }
                if (ModuleManager.isModuleEnabled("AutoGG"))
                    AutoGG.INSTANCE.addTargetedPlayer(renderEnt.getName());
            }
            isActive = false;
        }
    }

    @Override
    public void onWorldRender(final RenderEvent event) {
        if (this.render != null) {
            final float[] hue = {(System.currentTimeMillis() % (360 * 32)) / (360f * 32)};
            int rgb = Color.HSBtoRGB(hue[0], 1, 1);
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = rgb & 0xFF;
            if (rainbow.getValBoolean()) {
                OsirisTessellator.prepare(7);
                OsirisTessellator.drawBox(this.render, r, g, b, espA.getValInt(), 63);
                OsirisTessellator.release();
                AuroraTessellator.prepare(7);
                AuroraTessellator.drawBoundingBoxBlockPos(this.render, 1.00f,  r, g, b, 255);
            } else {
                OsirisTessellator.prepare(7);
                OsirisTessellator.drawBox(this.render, espR.getValInt(), espG.getValInt(), espB.getValInt(), espA.getValInt(), 63);
                OsirisTessellator.release();
                AuroraTessellator.prepare(7);
                AuroraTessellator.drawBoundingBoxBlockPos(this.render, 1.00f, espR.getValInt(), espG.getValInt(), espB.getValInt(), 255);
            }
            AuroraTessellator.release();
        }

    }

    private boolean isEatingGap() {
        return mc.player.getHeldItemMainhand().getItem() instanceof ItemAppleGold && mc.player.isHandActive();
    }

    private void drawCurrentBlock(BlockPos render, int r, int g, int b, int a) {
        if (renderMode.getValString().equalsIgnoreCase("halfbox"))
            OsirisTessellator.drawHalfBox(render, r, g, b, a, GeometryMasks.Quad.ALL);
        else if (renderMode.getValString().equalsIgnoreCase("plane"))
            OsirisTessellator.drawBox(render, r, g, b, a, GeometryMasks.Quad.DOWN);
        else
            OsirisTessellator.drawBox(render, r, g, b, a, GeometryMasks.Quad.ALL);
    }


    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = calculateLookAt(px, py, pz, me);
        setYawAndPitch((float) v[0], (float) v[1]);
    }

    private boolean canPlaceCrystal(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        return (mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK
                || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN)
                && mc.world.getBlockState(boost).getBlock() == Blocks.AIR
                && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR
                && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty()
                && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    private List<BlockPos> findCrystalBlocks() {
        NonNullList<BlockPos> positions = NonNullList.create();
        positions.addAll(getSphere(getPlayerPos(), (float) placeRange.getValDouble(), (int) placeRange.getValDouble(), false, true, 0).stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
        return positions;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleblocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
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
        double distancedsize = entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = (double) entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v = (1.0D - distancedsize) * blockDensity;
        float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));
        double finald = 1.0D;
        /*if (entity instanceof EntityLivingBase)
            finald = getBlastReduction((EntityLivingBase) entity,getDamageMultiplied(damage));*/
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase) entity, getDamageMultiplied(damage), new Explosion(mc.world, null, posX, posY, posZ, 6F, false, true));
        }
        return (float) finald;
    }

    public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer) entity;
            DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());

            int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            float f = MathHelper.clamp(k, 0.0F, 20.0F);
            damage *= 1.0F - f / 25.0F;

            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage = damage - (damage / 4);
            }
            //   damage = Math.max(damage - ep.getAbsorptionAmount(), 0.0F);
            return damage;
        } else {
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            return damage;
        }
    }

    private static float getDamageMultiplied(float damage) {
        int diff = mc.world.getDifficulty().getId();
        return damage * (diff == 0 ? 0 : (diff == 2 ? 1 : (diff == 1 ? 0.5f : 1.5f)));
    }

    public static float calculateDamage(EntityEnderCrystal crystal, Entity entity) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }

    //Better Rotation Spoofing System:

    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;

    //this modifies packets being sent so no extra ones are made. NCP used to flag with "too many packets"
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

    public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;

        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        //to degree
        pitch = pitch * 180.0d / Math.PI;
        yaw = yaw * 180.0d / Math.PI;

        yaw += 90f;

        return new double[]{yaw, pitch};
    }

    @EventHandler
    private Listener<PacketEvent.Send> packetSendListener = new Listener<>(event -> {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer && spoofRotations.getValBoolean()) {
            if (isSpoofingAngles) {
                ((CPacketPlayer) packet).yaw = (float) yaw;
                ((CPacketPlayer) packet).pitch = (float) pitch;
            }
        }
    });

    @EventHandler
    private Listener<PacketEvent.Receive> packetReceiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
                    if (e instanceof EntityEnderCrystal) {
                        if (e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
                            e.setDead();
                        }
                    }
                }
            }
        }

    });

    @Override
    public void onEnable() {
        AuroraMod.EVENT_BUS.subscribe(this);
        isActive = false;
        if (chat.getValBoolean() && mc.player != null) {
            Command.sendClientMessage("\u00A7aAutoCrystal ON");
        }
    }

    @Override
    public void onDisable() {
        AuroraMod.EVENT_BUS.unsubscribe(this);
        render = null;
        renderEnt = null;
        resetRotation();
        isActive = false;
        if (chat.getValBoolean()) {
            Command.sendClientMessage("\u00A7cAutoCrystal OFF");
        }
    }
}
