package me.zeroeightsix.kami.module.modules.combat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Friends;
import me.zeroeightsix.kami.util.KamiTessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Module.Info(name = "VikNetAura", category = Module.Category.COMBAT)
public class VikNetAura extends Module {

    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;
    EnumFacing f;
    private Setting<Page> p = register(Settings.enumBuilder(Page.class).withName("Page").withValue(Page.ONE).build());

    private Setting<Boolean> place = register(Settings.booleanBuilder("Place").withValue(true).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> explode = register(Settings.booleanBuilder("Explode").withValue(true).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> autoSwitch = register(Settings.booleanBuilder("Auto Switch").withValue(true).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> antiWeakness = register(Settings.booleanBuilder("AntiWeakness").withValue(true).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> noGappleSwitch = register(Settings.booleanBuilder("NoGappleSwitch").withValue(false).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> nodesync = register(Settings.booleanBuilder("Nodesync").withValue(true).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> spoofRotations = register(Settings.booleanBuilder("SpoofRotations").withValue(true).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> NoSwingCONST = register(Settings.booleanBuilder("NoSwingCONST").withValue(false).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> AbsorptionEAT = register(Settings.booleanBuilder("AbsorptionEAT").withValue(false).withVisibility(v -> p.getValue().equals(Page.ONE)).build());

    private Setting<Boolean> chat = register(Settings.booleanBuilder("Chat").withValue(true).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> rotate = register(Settings.booleanBuilder("Rotate").withValue(false).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Boolean> raytrace = register(Settings.booleanBuilder("Raytrace").withValue(false).withVisibility(v -> p.getValue().equals(Page.ONE)).build());

    private Setting<Double> waitTick = register(Settings.d("WaitTick", 0.1d));

    private Setting<Double> range = register(Settings.doubleBuilder("Range").withMinimum(0.0).withValue(6.0).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Double> placeRange = register(Settings.doubleBuilder("Place Range").withMinimum(0.0).withValue(6.0).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Double> maxSelfDmg = register(Settings.doubleBuilder("MaxSelfDmg").withMinimum(0.0).withValue(15.0).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Double> minDmg = register(Settings.doubleBuilder("MinDmg").withMinimum(0.0).withValue(9.0).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Double> facePlace = register(Settings.doubleBuilder("FacePlaceHP").withMinimum(0.0).withValue(6.0).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    private Setting<Double> walls = register(Settings.doubleBuilder("WallsRange").withMinimum(0.0).withValue(3.6).withMaximum(20.0).withVisibility(v -> p.getValue().equals(Page.ONE)).build());
    //page 2 of this fucking ca
    private Setting<Boolean> customColours = register(Settings.booleanBuilder("RGB").withValue(false).withVisibility(v -> p.getValue().equals(Page.TWO)).build());
    private Setting<Integer> red = register(Settings.integerBuilder("Red").withMinimum(0).withValue(130).withMaximum(255).withVisibility(v -> p.getValue().equals(Page.TWO) && customColours.getValue()).build());
    private Setting<Integer> green = register(Settings.integerBuilder("Green").withMinimum(0).withValue(0).withMaximum(255).withVisibility(v -> p.getValue().equals(Page.TWO) && customColours.getValue()).build());
    private Setting<Integer> blue = register(Settings.integerBuilder("Blue").withMinimum(0).withValue(130).withMaximum(255).withVisibility(v -> p.getValue().equals(Page.TWO) && customColours.getValue()).build());
    private Setting<Integer> alpha = register(Settings.integerBuilder("Alpha").withMinimum(0).withValue(52).withMaximum(255).withVisibility(v -> p.getValue().equals(Page.TWO) && customColours.getValue()).build());
    private Setting<Boolean> rainbow = register(Settings.booleanBuilder("RainBow ").withValue(false).withVisibility(v -> p.getValue().equals(Page.TWO)).build());
    private Setting<Integer> redB = register(Settings.integerBuilder("RedBoundingBox").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> p.getValue().equals(Page.TWO) && customColours.getValue()).build());
    private Setting<Integer> greenB = register(Settings.integerBuilder("GreenBoundingBox").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> p.getValue().equals(Page.TWO) && customColours.getValue()).build());
    private Setting<Integer> blueB = register(Settings.integerBuilder("BlueBoundingBox").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> p.getValue().equals(Page.TWO) && customColours.getValue()).build());
    private Setting<Integer> alphabounding = register(Settings.integerBuilder("BoundingBoxAlpha").withMinimum(0).withValue(255).withMaximum(255).withVisibility(v -> p.getValue().equals(Page.TWO) && customColours.getValue()).build());


    private BlockPos renderBlock;
    private EntityPlayer target;
    public boolean isActive = false;

    private enum Page {ONE, TWO}

    // we need this cooldown to not place from old hotbar slot, before we have switched to crystals.
    // otherwise we will attempt to click with whatever was in the last slot.
    private boolean switchCooldown = false;
    private boolean isAttacking = false;
    private int oldSlot = -1;
    private int newSlot;
    private BlockPos render;
    private String renderDmg;
    private Entity renderEnt;
    // we need this cooldown to not place from old hotbar slot, before we have switched to crystals

    private int waitCounter;
    private int hitDelayCounter;

    public void onUpdate() {
        isActive = false;
        if (mc.player == null || mc.player.isDead) return; // bruh
        EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityEnderCrystal)
                .filter(e -> mc.player.getDistance(e) <= range.getValue())
                .map(entity -> (EntityEnderCrystal) entity)
                .min(Comparator.comparing(c -> mc.player.getDistance(c)))
                .orElse(null);
        if (explode.getValue() && crystal != null) {
            if (!mc.player.canEntityBeSeen(crystal) && mc.player.getDistance(crystal) > walls.getValue()) return;

            if (waitTick.getValue() > 0) {
                if (waitCounter < waitTick.getValue()) {
                    waitCounter++;
                    return;
                } else {
                    waitCounter = 0;
                }
            }

            if (antiWeakness.getValue() && mc.player.isPotionActive(MobEffects.WEAKNESS)) {
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
            if (rotate.getValue()) {
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
                if (d < minDmg.getValue() && ((EntityLivingBase) entity).getHealth() + ((EntityLivingBase) entity).getAbsorptionAmount() > facePlace.getValue()) {
                    continue;
                }
                if (d > damage) {
                    double self = calculateDamage(blockPos.getX() + .5, blockPos.getY() + 1, blockPos.getZ() + .5, mc.player);
                    // If this deals more damage to ourselves than it does to our target, continue. This is only ignored if the crystal is sure to kill our target but not us.
                    // Also continue if our crystal is going to hurt us.. alot
                    if ((self > d && !(d < ((EntityLivingBase) entity).getHealth())) || self - .5 > mc.player.getHealth()) {
                        continue;
                    }
                    if (self > maxSelfDmg.getValue())
                        continue;
                    damage = d;
                    q = blockPos;
                    renderEnt = entity;
                    renderDmg = String.valueOf((int) damage);
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

        if (place.getValue()) {
            if (mc.player == null) return;
            isActive = true;
            if (rotate.getValue()) {
                lookAtPacket(q.getX() + .5, q.getY() - .5, q.getZ() + .5, mc.player);
            }
            RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(q.getX() + .5, q.getY() - .5d, q.getZ() + .5));
            if (raytrace.getValue()) {
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
                if (autoSwitch.getValue()) {
                    if (noGappleSwitch.getValue() && isEatingGap()) {
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
                if (raytrace.getValue() && f != null) {
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(q, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
                } else {
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(q, EnumFacing.UP, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
                }

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
            if (rainbow.getValue()) {
                KamiTessellator.prepare(7);
                KamiTessellator.drawBox(this.render, r, g, b, this.alpha.getValue(), 63);
                KamiTessellator.release();
                KamiTessellator.prepare(7);
                KamiTessellator.drawBoundingBoxBlockPos(this.render, 1.00f, r, g, b, this.alphabounding.getValue());
            } else {
                KamiTessellator.prepare(7);
                KamiTessellator.drawBox(this.render, this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue(), 63);
                KamiTessellator.release();
                KamiTessellator.prepare(7);
                KamiTessellator.drawBoundingBoxBlockPos(this.render, 1.00f, this.redB.getValue(), this.greenB.getValue(), this.blueB.getValue(), this.alphabounding.getValue());
            }
            KamiTessellator.release();
            drawString(this.render, this.renderDmg);
        }

    }

    private boolean isEatingGap() {
        return mc.player.getHeldItemMainhand().getItem() instanceof ItemAppleGold && mc.player.isHandActive();
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
        NonNullList positions = NonNullList.create();
        positions.addAll((Collection) this.getSphere(getPlayerPos(), ((Double) this.placeRange.getValue()).floatValue(), ((Double) this.placeRange.getValue()).intValue(), false, true, 0).stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
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

    public static void glBillboard(float x, float y, float z) {
        float scale = 0.03f * 1.6f;
        GlStateManager.translate(x - mc.getRenderManager().renderPosX, y - mc.getRenderManager().renderPosY, z - mc.getRenderManager().renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(mc.player.rotationPitch, mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
    }

    public static void glBillboardDistanceScaled(float x, float y, float z, EntityPlayer player, float scale) {
        glBillboard(x, y, z);
        int distance = (int) player.getDistance(x, y, z);
        float scaleDistance = (distance / 2.0f) / (2.0f + (2.0f - scale));
        if (scaleDistance < 1f)
            scaleDistance = 1;
        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }

    private void drawString(BlockPos blockPos, String str) {
        GlStateManager.pushMatrix();
        glBillboardDistanceScaled((float) blockPos.x + 0.5f, (float) blockPos.y + 0.5f, (float) blockPos.z + 0.5f, mc.player, 1.5f);
        GlStateManager.disableDepth();
        GlStateManager.translate(-(mc.fontRenderer.getStringWidth(str) / 2.0d), 0, 0);
        mc.fontRenderer.drawStringWithShadow(str, 0, 0, 0xFFFF55);
        GlStateManager.popMatrix();
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
        if (packet instanceof CPacketPlayer && spoofRotations.getValue()) {
            if (isSpoofingAngles) {
                ((CPacketPlayer) packet).yaw = (float) yaw;
                ((CPacketPlayer) packet).pitch = (float) pitch;
            }
        }
    });

    @EventHandler
    private Listener<PacketEvent.Receive> packetReceiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketSoundEffect && nodesync.getValue()) {
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
        KamiMod.EVENT_BUS.subscribe(this);
        isActive = false;
        if (chat.getValue() && mc.player != null) {
            Command.sendChatMessage("VikNetAura \u00A72ON");
        }
        if (this.NoSwingCONST.getValue()) {
            ModuleManager.getModuleByName("NoSwingCONST").disable();
        }
        if (this.AbsorptionEAT.getValue()) {
            ModuleManager.getModuleByName("AbsorptionEat").disable();
        }
    }

    @Override
    public void onDisable() {
        KamiMod.EVENT_BUS.unsubscribe(this);
        render = null;
        renderEnt = null;
        resetRotation();
        isActive = false;
        if (chat.getValue()) {
            Command.sendChatMessage("VikNetAura \u00A74OFF");
        }
        if (this.NoSwingCONST.getValue()) {
            ModuleManager.getModuleByName("NoSwingCONST").enable();
        }
        if (this.AbsorptionEAT.getValue()) {
            ModuleManager.getModuleByName("AbsorptionEat").enable();
        }
    }
}
