/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.Criticals;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Movement.MoveMeHelp;

public class FakePlayer
extends Module {
    EntityOtherPlayerMP fakePlayer = null;
    UUID uuid = UUID.fromString("70ee432d-0a96-4137-a2c0-37cc9df67f03");

    public FakePlayer() {
        super("FakePlayer", 0, Module.Category.PLAYER);
    }

    @Override
    public void onRender2D(ScaledResolution sr) {
        if (FakePlayer.mc.world == null || this.fakePlayer == null || Minecraft.player == null || this.fakePlayer == null) {
            return;
        }
        EntityOtherPlayerMP e = this.fakePlayer;
        float[] rotate = RotationUtil.getNeededFacing(RotationUtil.getEyesPos(), false, e, false);
        e.rotationYaw = rotate[0];
        e.rotationYawHead = rotate[0];
        e.rotationPitch = rotate[1];
        e.rotationPitchHead = rotate[1];
        e.setPrimaryHand(Minecraft.player.getPrimaryHand());
        e.openContainer = Minecraft.player.openContainer;
        if (Minecraft.player.getActiveHand() != null && Minecraft.player.isHandActive()) {
            e.setActiveHand(Minecraft.player.getActiveHand());
        } else {
            e.resetActiveHand();
        }
        e.activeItemStack = Minecraft.player.activeItemStack;
    }

    @EventTarget
    public void onPacket(EventSendPacket event) {
        if (this.actived && FakePlayer.mc.world != null && this.fakePlayer != null) {
            Object targetEntity;
            CPacketUseEntity packet;
            Object object = event.getPacket();
            if (object instanceof CPacketUseEntity && (packet = (CPacketUseEntity)object).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld(FakePlayer.mc.world) != null && (object = packet.getEntityFromWorld(FakePlayer.mc.world)) instanceof EntityOtherPlayerMP && (targetEntity = (EntityOtherPlayerMP)object) == this.fakePlayer) {
                boolean cancelGR;
                if (((EntityOtherPlayerMP)targetEntity).hurtTime > 0) {
                    Minecraft.player.world.playSound(Minecraft.player, ((EntityOtherPlayerMP)targetEntity).posX, ((EntityOtherPlayerMP)targetEntity).posY, ((EntityOtherPlayerMP)targetEntity).posZ, SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, Minecraft.player.getSoundCategory(), 1.0f, 1.0f);
                    return;
                }
                ((EntityOtherPlayerMP)targetEntity).hurtTime = 9;
                boolean cooled = (double)Minecraft.player.getCooledAttackStrength(1.0f) >= 0.9;
                boolean bl = cancelGR = FreeCam.get.actived && FreeCam.fakePlayer != null && FreeCam.fakePlayer.fallDistance != 0.0f;
                if (Criticals.get.actived && Criticals.get.currentMode("Mode").equalsIgnoreCase("VanillaHop") && HitAura.TARGET != null && !Minecraft.player.isJumping() && !Minecraft.player.isInWater() && Minecraft.player.onGround) {
                    cancelGR = true;
                }
                boolean canCrit = cooled && (!Minecraft.player.serverSprintState || !Minecraft.player.isSprinting() || !MoveMeHelp.moveKeysPressed() && !Minecraft.player.onGround && cancelGR) && (Minecraft.player.fallDistance != 0.0f && !Minecraft.player.onGround || cancelGR);
                boolean canKnock = cooled && !canCrit && (Minecraft.player.serverSprintState || Minecraft.player.isSprinting());
                boolean canSweep = cooled && (!Minecraft.player.serverSprintState || !Minecraft.player.isSprinting()) && Minecraft.player.onGround && Minecraft.player.isCollidedVertically && Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemSword;
                boolean canStrong = !canCrit && !canKnock && !canSweep && Minecraft.player.fallDistance == 0.0f && cooled;
                boolean canWeak = !canStrong;
                float f = (float)Minecraft.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                float f1 = EnchantmentHelper.getModifierForCreature(Minecraft.player.getHeldItemMainhand(), Minecraft.player.getCreatureAttribute());
                float f2 = Minecraft.player.getCooledAttackStrength(0.5f);
                f *= 0.2f + f2 * f2 * 0.8f;
                float damage = (f1 *= f2) / 2.0f + 1.6f;
                if (canCrit || canKnock || canSweep || canStrong || canWeak) {
                    Minecraft.player.world.playSound(Minecraft.player, ((EntityOtherPlayerMP)targetEntity).posX, ((EntityOtherPlayerMP)targetEntity).posY, ((EntityOtherPlayerMP)targetEntity).posZ, SoundEvents.ENTITY_PLAYER_HURT, Minecraft.player.getSoundCategory(), 0.5f, 1.0f);
                }
                if (canCrit) {
                    damage = (float)((double)damage * 1.5);
                    Minecraft.player.world.playSound(Minecraft.player, ((EntityOtherPlayerMP)targetEntity).posX, ((EntityOtherPlayerMP)targetEntity).posY, ((EntityOtherPlayerMP)targetEntity).posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, Minecraft.player.getSoundCategory(), 1.0f, 1.0f);
                } else if (canKnock) {
                    Minecraft.player.world.playSound(Minecraft.player, ((EntityOtherPlayerMP)targetEntity).posX, ((EntityOtherPlayerMP)targetEntity).posY, ((EntityOtherPlayerMP)targetEntity).posZ, SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, Minecraft.player.getSoundCategory(), 1.0f, 1.0f);
                } else if (canSweep) {
                    Minecraft.player.world.playSound(Minecraft.player, ((EntityOtherPlayerMP)targetEntity).posX, ((EntityOtherPlayerMP)targetEntity).posY, ((EntityOtherPlayerMP)targetEntity).posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, Minecraft.player.getSoundCategory(), 1.0f, 1.0f);
                } else if (canStrong) {
                    Minecraft.player.world.playSound(Minecraft.player, ((EntityOtherPlayerMP)targetEntity).posX, ((EntityOtherPlayerMP)targetEntity).posY, ((EntityOtherPlayerMP)targetEntity).posZ, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, Minecraft.player.getSoundCategory(), 1.0f, 1.0f);
                } else if (canWeak) {
                    Minecraft.player.world.playSound(Minecraft.player, ((EntityOtherPlayerMP)targetEntity).posX, ((EntityOtherPlayerMP)targetEntity).posY, ((EntityOtherPlayerMP)targetEntity).posZ, SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, Minecraft.player.getSoundCategory(), 1.0f, 1.0f);
                }
                if (((EntityPlayer)targetEntity).getAbsorptionAmount() != 0.0f) {
                    float addToDamageHP = ((EntityPlayer)targetEntity).getAbsorptionAmount() - damage / 2.0f + ((EntityPlayer)targetEntity).getAbsorptionAmount();
                    ((EntityPlayer)targetEntity).setAbsorptionAmount(MathUtils.clamp(((EntityPlayer)targetEntity).getAbsorptionAmount() - damage, 0.0f, 1000.0f));
                    ((EntityLivingBase)targetEntity).setHealth(MathUtils.clamp(((EntityLivingBase)targetEntity).getHealth() - addToDamageHP, 0.99f, 20.0f));
                } else {
                    ((EntityLivingBase)targetEntity).setHealth(MathUtils.clamp(((EntityLivingBase)targetEntity).getHealth() - damage + ((EntityPlayer)targetEntity).getAbsorptionAmount(), 0.99f, 20.0f));
                }
                ((EntityOtherPlayerMP)targetEntity).limbSwingAmount = (float)((EntityOtherPlayerMP)targetEntity).hurtTime / 10.0f;
                if (((EntityLivingBase)targetEntity).getHealth() < 1.0f && ((EntityPlayer)targetEntity).getAbsorptionAmount() == 0.0f) {
                    ((EntityLivingBase)targetEntity).clearActivePotions();
                    FakePlayer.mc.effectRenderer.func_191271_a((Entity)targetEntity, EnumParticleTypes.TOTEM, 30);
                    FakePlayer.mc.world.playSound(((EntityOtherPlayerMP)targetEntity).posX, ((EntityOtherPlayerMP)targetEntity).posY, ((EntityOtherPlayerMP)targetEntity).posZ, SoundEvents.field_191263_gW, ((EntityPlayer)targetEntity).getSoundCategory(), 1.0f, 1.0f, false);
                    ((EntityLivingBase)targetEntity).addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 0));
                    ((EntityLivingBase)targetEntity).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 880, 0));
                    ((EntityPlayer)targetEntity).setAbsorptionAmount(8.0f);
                    ((EntityLivingBase)targetEntity).setHealth(1.0f);
                }
            }
            if ((targetEntity = event.getPacket()) instanceof SPacketExplosion) {
                boolean canDamage;
                SPacketExplosion explosion = (SPacketExplosion)targetEntity;
                if (this.fakePlayer.hurtTime > 1) {
                    return;
                }
                Vec3d posExplosion = new Vec3d(explosion.getX(), explosion.getY(), explosion.getZ());
                boolean bl = canDamage = BlockUtils.getDistanceAtVecToVec(this.fakePlayer.getPositionVector(), posExplosion) < 11.0;
                if (!canDamage) {
                    return;
                }
                float damage = (float)((9.0 - BlockUtils.getDistanceAtVecToVec(this.fakePlayer.getPositionVector(), posExplosion)) / 9.0) * 10.0f;
                float hp = (float)MathUtils.clamp((double)(this.fakePlayer.getHealth() - damage + this.fakePlayer.getAbsorptionAmount()), 0.001, 20.0);
                if (this.fakePlayer.getAbsorptionAmount() != 0.0f) {
                    this.fakePlayer.setAbsorptionAmount(MathUtils.clamp(this.fakePlayer.getAbsorptionAmount() - damage, 0.0f, 8.0f));
                }
                if (this.fakePlayer.getAbsorptionAmount() == 0.0f || damage > this.fakePlayer.getAbsorptionAmount()) {
                    this.fakePlayer.setHealth((int)MathUtils.clamp(hp + 1.0f, 1.0f, 20.0f));
                }
                Minecraft.player.world.playSound(Minecraft.player, this.fakePlayer.posX, this.fakePlayer.posY, this.fakePlayer.posZ, SoundEvents.ENTITY_PLAYER_DEATH, Minecraft.player.getSoundCategory(), 0.5f, 1.0f);
                if (hp < 1.0f && this.fakePlayer.getAbsorptionAmount() < 4.0f && this.fakePlayer.getHeldItemOffhand().getItem() == Items.TOTEM) {
                    this.fakePlayer.clearActivePotions();
                    FakePlayer.mc.effectRenderer.func_191271_a(this.fakePlayer, EnumParticleTypes.TOTEM, 30);
                    FakePlayer.mc.world.playSound(this.fakePlayer.posX, this.fakePlayer.posY, this.fakePlayer.posZ, SoundEvents.field_191263_gW, this.fakePlayer.getSoundCategory(), 1.0f, 1.0f, false);
                    this.fakePlayer.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
                    this.fakePlayer.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 880, 0));
                    this.fakePlayer.setHeldItem(EnumHand.OFF_HAND, new ItemStack(Item.getItemById(449), this.fakePlayer.getHeldItemOffhand().stackSize - 1));
                    this.fakePlayer.setHealth(1.0f);
                }
            }
        }
    }

    @Override
    public void onToggled(boolean actived) {
        if (!actived && FakePlayer.mc.world.getPlayerEntityByUUID(this.uuid) != null) {
            FakePlayer.mc.world.removeEntityFromWorld(462462998);
            this.fakePlayer = null;
        } else if (actived) {
            this.fakePlayer = new EntityOtherPlayerMP(FakePlayer.mc.world, new GameProfile(UUID.fromString("70ee432d-0a96-4137-a2c0-37cc9df67f03"), "\u00a76V\u0435gaLine\u00a7f > \u00a7cNPC\u00a7r"));
            FakePlayer.mc.world.addEntityToWorld(462462998, this.fakePlayer);
            this.fakePlayer.copyLocationAndAnglesFrom(Minecraft.player);
            this.fakePlayer.setPosition(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ);
            this.fakePlayer.inventory.copyInventory(Minecraft.player.inventory);
            ItemStack stack = Minecraft.player.getHeldItemOffhand();
            this.fakePlayer.setHeldItem(EnumHand.OFF_HAND, new ItemStack(Item.getItemById(449), 64));
            Minecraft.player.setHeldItem(EnumHand.OFF_HAND, stack);
            this.fakePlayer.setHealth(20.0f);
        }
        super.onToggled(actived);
    }

    public void onDisable() {
    }
}

