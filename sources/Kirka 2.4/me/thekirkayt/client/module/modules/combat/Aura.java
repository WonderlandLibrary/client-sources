/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.combat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.thekirkayt.client.Client;
import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.combat.aura.Single;
import me.thekirkayt.client.module.modules.combat.aura.Switch;
import me.thekirkayt.client.module.modules.combat.aura.Tick;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.JumpEvent;
import me.thekirkayt.event.events.PacketReceiveEvent;
import me.thekirkayt.event.events.PacketSendEvent;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.StateManager;
import me.thekirkayt.utils.Timer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;

@Module.Mod
public class Aura
extends Module {
    private Switch switchMode = new Switch("Switch", true, this);
    private Single Single = new Single("Single", false, this);
    private Tick tick = new Tick("Tick", false, this);
    @Option.Op(min=0.0, max=20.0, increment=0.25)
    public double speed = 8.0;
    @Option.Op(min=0.1, max=8.0, increment=0.1)
    public double range = 4.2;
    @Option.Op(name="Block Range", min=3.5, max=12.0, increment=0.5)
    public double blockRange = 8.0;
    @Option.Op(min=0.0, max=180.0, increment=5.0)
    public double degrees = 90.0;
    @Option.Op(name="Ticks Existed", min=0.0, max=50.0, increment=1.0)
    public double ticksExisted = 10.0;
    @Option.Op
    private boolean players = true;
    @Option.Op
    private boolean monsters;
    @Option.Op
    private boolean gwen;
    @Option.Op
    private boolean animals;
    @Option.Op(name="Teams")
    public boolean teams;
    @Option.Op
    private boolean ncp = true;
    @Option.Op
    private boolean noswing;
    @Option.Op
    public boolean noslowdown;
    @Option.Op
    public boolean criticals;
    @Option.Op
    public boolean focus;
    @Option.Op
    public boolean experimental;
    @Option.Op(name="gwen2")
    public static boolean antibot;
    @Option.Op(name="Durability")
    public boolean dura;
    @Option.Op(name="Clans")
    public boolean clans;
    @Option.Op(name="Auto Block")
    public boolean autoblock;
    @Option.Op(name="Armor Check")
    private boolean armorCheck;
    @Option.Op(name="Attack Friends")
    private boolean attackFriends;
    private boolean jumpNextTick;
    private ItemStack predictedItem;
    private Timer pickupTimer = new Timer();
    private Timer potTimer = new Timer();

    @Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.switchMode);
        OptionManager.getOptionList().add(this.Single);
        OptionManager.getOptionList().add(this.tick);
        this.updateSuffix();
        super.preInitialize();
    }

    @Override
    public void enable() {
        this.Single.enable();
        this.tick.enable();
        super.enable();
    }

    private void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S0DPacketCollectItem) {
            S0DPacketCollectItem packet = (S0DPacketCollectItem)event.getPacket();
            Entity item = ClientUtils.world().getEntityByID(packet.func_149354_c());
            if (item instanceof EntityItem) {
                EntityItem itemEntity = (EntityItem)item;
                this.predictedItem = itemEntity.getEntityItem();
                this.pickupTimer.reset();
            }
        }
        if (event.getPacket() instanceof S2FPacketSetSlot) {
            S2FPacketSetSlot packet2 = (S2FPacketSetSlot)event.getPacket();
            if (!Timer.delay(6.0f) && packet2.func_149173_d() == -1 && packet2.func_149175_c() == -1) {
                this.potTimer.setDifference(7L);
                new Thread(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(6L);
                        }
                        catch (InterruptedException interruptedException) {
                            // empty catch block
                        }
                        ClientUtils.sendMessage("Post - " + System.currentTimeMillis());
                        ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
                        ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, -999, 0, 5, ClientUtils.player());
                        ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, 36 + ClientUtils.player().inventory.currentItem, 1, 5, ClientUtils.player());
                        ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, -999, 2, 5, ClientUtils.player());
                        if (ClientUtils.player().inventory.getItemStack() != null) {
                            ClientUtils.sendMessage("Fake Placingxd");
                            ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, 36 + ClientUtils.player().inventory.currentItem, 0, 0, ClientUtils.player());
                        }
                    }
                }).start();
            }
            if (!Timer.delay(10.0f) && this.predictedItem != null && ItemStack.areItemStackTagsEqual(this.predictedItem, packet2.func_149174_e()) && packet2.func_149173_d() == 36) {
                event.setCancelled(true);
                this.predictedItem = null;
            } else {
                this.predictedItem = null;
            }
        }
        if (event.getPacket() instanceof S30PacketWindowItems && Timer.delay(400.0f)) {
            S30PacketWindowItems packet3 = (S30PacketWindowItems)event.getPacket();
            ClientUtils.sendMessage("Pre - " + System.currentTimeMillis());
            this.potTimer.reset();
        }
        Timer.delay(10.0f);
    }

    @EventTarget
    private void onJump(JumpEvent event) {
        if (StateManager.offsetLastPacketAura()) {
            this.jumpNextTick = true;
            event.setCancelled(true);
        }
    }

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        if (this.jumpNextTick && !StateManager.offsetLastPacketAura()) {
            ClientUtils.player().jump();
            this.jumpNextTick = false;
        }
        this.switchMode.onUpdate(event);
        this.Single.onUpdate(event);
        this.tick.onUpdate(event);
    }

    @EventTarget
    private void onTick(TickEvent event) {
        this.updateSuffix();
    }

    private void updateSuffix() {
        if (((Boolean)this.switchMode.getValue()).booleanValue()) {
            this.setSuffix("");
        } else if (((Boolean)this.tick.getValue()).booleanValue()) {
            this.setSuffix("");
        } else {
            this.setSuffix("");
        }
    }

    public boolean isEntityValid(Entity entity) {
        block19 : {
            EntityLivingBase entityLiving;
            block21 : {
                block20 : {
                    if (!(entity instanceof EntityLivingBase)) break block19;
                    entityLiving = (EntityLivingBase)entity;
                    if (!ClientUtils.player().isEntityAlive() || !entityLiving.isEntityAlive()) break block20;
                    double d = ClientUtils.player().canEntityBeSeen(entityLiving) ? this.range : 3.0;
                    if (!((double)entityLiving.getDistanceToEntity(ClientUtils.player()) > d)) break block21;
                }
                return false;
            }
            if ((double)entityLiving.ticksExisted < this.ticksExisted) {
                return false;
            }
            if (this.isOnSameTeam(entityLiving) && this.teams) {
                return false;
            }
            if (!entityLiving.hasHit && this.gwen) {
                return false;
            }
            if (entityLiving.getHealth() < 0.0f) {
                return false;
            }
            if (entityLiving.isDead) {
                return false;
            }
            if (this.players && entityLiving instanceof EntityPlayer) {
                EntityPlayer entityPlayer = (EntityPlayer)entityLiving;
                if (FriendManager.isFriend(entityPlayer.getName()) && !this.attackFriends) {
                    return false;
                }
                if (antibot && entity instanceof EntityPlayer && Client.isBot((EntityPlayer)entityLiving)) {
                    return false;
                }
                if (this.armorCheck && !this.hasArmor(entityPlayer)) {
                    return false;
                }
                ItemStack boots = entityPlayer.getEquipmentInSlot(1);
                ItemStack legs = entityPlayer.getEquipmentInSlot(2);
                ItemStack chest = entityPlayer.getEquipmentInSlot(3);
                ItemStack helm = entityPlayer.getEquipmentInSlot(4);
                boolean fuckedUpArmorOrder = false;
                if (boots != null && boots.getUnlocalizedName().contains("helmet")) {
                    fuckedUpArmorOrder = true;
                }
                if (legs != null && legs.getUnlocalizedName().contains("chestplate")) {
                    fuckedUpArmorOrder = true;
                }
                if (chest != null && chest.getUnlocalizedName().contains("leggings")) {
                    fuckedUpArmorOrder = true;
                }
                if (helm != null && helm.getUnlocalizedName().contains("boots")) {
                    fuckedUpArmorOrder = true;
                }
                return !fuckedUpArmorOrder;
            }
            if (!entity.getName().equalsIgnoreCase("")) {
                if (entity instanceof EntityPlayer) {
                    return !FriendManager.isFriend(entity.getName()) && this.players && (!this.armorCheck || this.hasArmor((EntityPlayer)entity));
                }
                if (!(entity instanceof IAnimals) || entity instanceof IMob) {
                    return entity instanceof IMob && this.monsters;
                }
                return entity instanceof EntityHorse || this.animals;
            }
        }
        return false;
    }

    @EventTarget
    private void onPacketSend(PacketSendEvent event) {
        if (event.getPacket() instanceof C09PacketHeldItemChange) {
            Switch.potTimer.reset();
        }
    }

    public boolean isEntityValidType(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLiving = (EntityLivingBase)entity;
            if (!ClientUtils.player().isEntityAlive() || !entityLiving.isEntityAlive()) {
                return false;
            }
            if (this.players && entityLiving instanceof EntityPlayer) {
                EntityPlayer entityPlayer = (EntityPlayer)entityLiving;
                if (entityPlayer.isInvisible() && !this.ncp) {
                    return false;
                }
                if (FriendManager.isFriend(entityPlayer.getName()) && !this.attackFriends) {
                    return false;
                }
                if (this.armorCheck && !this.hasArmor(entityPlayer)) {
                    return false;
                }
                ItemStack boots = entityPlayer.getEquipmentInSlot(1);
                ItemStack legs = entityPlayer.getEquipmentInSlot(2);
                ItemStack chest = entityPlayer.getEquipmentInSlot(3);
                ItemStack helm = entityPlayer.getEquipmentInSlot(4);
                boolean fuckedUpArmorOrder = false;
                if (boots != null && boots.getUnlocalizedName().contains("helmet")) {
                    fuckedUpArmorOrder = true;
                }
                if (legs != null && legs.getUnlocalizedName().contains("chestplate")) {
                    fuckedUpArmorOrder = true;
                }
                if (chest != null && chest.getUnlocalizedName().contains("leggings")) {
                    fuckedUpArmorOrder = true;
                }
                if (helm != null && helm.getUnlocalizedName().contains("boots")) {
                    fuckedUpArmorOrder = true;
                }
                return !fuckedUpArmorOrder;
            }
            if (this.monsters && (entityLiving instanceof EntityMob || entityLiving instanceof EntityGhast || entityLiving instanceof EntityDragon || entityLiving instanceof EntityWither || entityLiving instanceof EntitySlime || entityLiving instanceof EntityWolf && ((EntityWolf)entityLiving).getOwner() != ClientUtils.player())) {
                return true;
            }
            if (this.animals && (entityLiving instanceof EntityAnimal || entityLiving instanceof EntitySquid)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasArmor(EntityPlayer player) {
        ItemStack boots = player.inventory.armorInventory[0];
        ItemStack pants = player.inventory.armorInventory[1];
        ItemStack chest = player.inventory.armorInventory[2];
        ItemStack head = player.inventory.armorInventory[3];
        return boots != null || pants != null || chest != null || head != null;
    }

    public void attack(EntityLivingBase entity) {
        this.attack(entity, this.criticals);
    }

    public void attack(EntityLivingBase entity, boolean crit) {
        this.swingItem();
        float sharpLevel = EnchantmentHelper.func_152377_a(ClientUtils.player().getHeldItem(), entity.getCreatureAttribute());
        boolean vanillaCrit = ClientUtils.player().fallDistance > 0.0f && !ClientUtils.player().onGround && !ClientUtils.player().isOnLadder() && !ClientUtils.player().isInWater() && !ClientUtils.player().isPotionActive(Potion.blindness) && ClientUtils.player().ridingEntity == null;
        ClientUtils.packet(new C02PacketUseEntity((Entity)entity, C02PacketUseEntity.Action.ATTACK));
        if (crit || vanillaCrit) {
            ClientUtils.player().onCriticalHit(entity);
        }
        if (sharpLevel > 0.0f) {
            ClientUtils.player().onEnchantmentCritical(entity);
        }
    }

    public void pseudoAttack(EntityLivingBase entity, boolean crit) {
        boolean vanillaCrit;
        this.swingItem();
        float sharpLevel = EnchantmentHelper.func_152377_a(ClientUtils.player().getHeldItem(), entity.getCreatureAttribute());
        boolean bl = vanillaCrit = ClientUtils.player().fallDistance > 0.0f && !ClientUtils.player().onGround && !ClientUtils.player().isOnLadder() && !ClientUtils.player().isInWater() && !ClientUtils.player().isPotionActive(Potion.blindness) && ClientUtils.player().ridingEntity == null;
        if (crit || vanillaCrit) {
            ClientUtils.player().onCriticalHit(entity);
        }
        if (sharpLevel > 0.0f) {
            ClientUtils.player().onEnchantmentCritical(entity);
        }
    }

    public void swingItem() {
        if (!this.noswing) {
            ClientUtils.player().swingItem();
        }
    }

    @Override
    public void disable() {
        StateManager.setOffsetLastPacketAura(false);
        this.tick.disable();
        super.disable();
    }

    public boolean isOnSameTeam(Entity entity) {
        return ClientUtils.player().getDisplayName().getFormattedText().contains("\u00a7" + Aura.getTeamFromName(ClientUtils.player())) && entity.getDisplayName().getFormattedText().contains("\u00a7" + Aura.getTeamFromName(ClientUtils.player()));
    }

    public static String getTeamFromName(Entity entity) {
        Matcher matcher = Pattern.compile("\u00a7(.).*\u00a7r").matcher(entity.getDisplayName().getFormattedText());
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "f";
    }

}

