// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.combat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import me.chrest.client.friend.FriendManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import me.chrest.event.events.PacketSendEvent;
import me.chrest.event.events.TickEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import me.chrest.utils.ClientUtils;
import me.chrest.event.events.UpdateEvent;
import me.chrest.event.EventTarget;
import me.chrest.utils.StateManager;
import me.chrest.event.events.JumpEvent;
import me.chrest.client.option.OptionManager;
import java.util.Random;
import me.chrest.utils.Timer;
import net.minecraft.item.ItemStack;
import me.chrest.client.option.Option;
import me.chrest.client.module.modules.combat.aura.Ruggero;
import me.chrest.client.module.modules.combat.aura.Tick;
import me.chrest.client.module.modules.combat.aura.Single;
import me.chrest.client.module.modules.combat.aura.Vanilla;
import me.chrest.client.module.modules.combat.aura.Switch;
import me.chrest.client.module.Module;

@Mod(displayName = "Kill Aura")
public class Aura extends Module
{
    String Ruggero;
    private Switch switchMode;
    private Vanilla vanilla;
    private Single single;
    private Tick tick;
    private Ruggero rug;
    @Option.Op(name = "Teams")
    private boolean teams;
    @Option.Op(name = "Players")
    private boolean players;
    @Option.Op(name = "Prioritize Enemy")
    public boolean enemyPriority;
    @Option.Op
    private boolean villagers;
    @Option.Op
    private boolean golems;
    @Option.Op(name = "\u0131njector")
    private boolean \u0131njector;
    @Option.Op
    private boolean booblebee;
    @Option.Op(name = "Monsters")
    private boolean monsters;
    @Option.Op(name = "Animals")
    private boolean animals;
    @Option.Op(name = "Bats")
    private boolean bats;
    @Option.Op(name = "NoSlowdown")
    public boolean noslowdown;
    @Option.Op(name = "Criticals")
    public boolean criticals;
    @Option.Op(name = "Auto Block")
    public boolean autoblock;
    @Option.Op(name = "Armor Check")
    private boolean armorCheck;
    @Option.Op(name = "Attack Friends")
    private boolean attackFriends;
    private boolean jumpNextTick;
    @Option.Op(name = "Durability")
    public boolean dura;
    @Option.Op(min = 1.0, max = 6.0, increment = 0.1, name = "Range")
    public double range;
    @Option.Op(name = "Block Range", min = 1.0, max = 11.0, increment = 0.5)
    public double blockRange;
    @Option.Op(min = 0.0, max = 180.0, increment = 5.0, name = "Degrees")
    public double degrees;
    @Option.Op(name = "Ticks Existed", min = 0.0, max = 44.0, increment = 1.0)
    public double ticksExisted;
    public double ticks;
    private ItemStack predictedItem;
    private Timer pickupTimer;
    private Timer potTimer;
    private Timer timer;
    private long attackDelay;
    private Random rand;
    
    public Aura() {
        this.Ruggero = "Best Aura 2K18";
        this.rand = new Random();
        this.switchMode = new Switch("Switch", true, this);
        this.vanilla = new Vanilla("Vanilla", false, this);
        this.single = new Single("Single", false, this);
        this.tick = new Tick("Tick", false, this);
        this.rug = new Ruggero("Ruggero", false, this);
        this.range = 5.0;
        this.degrees = 50.0;
        this.ticksExisted = 5.0;
        this.players = true;
    }
    
    @Override
    public void postInitialize() {
        OptionManager.getOptionList().add(this.switchMode);
        OptionManager.getOptionList().add(this.vanilla);
        OptionManager.getOptionList().add(this.single);
        OptionManager.getOptionList().add(this.tick);
        OptionManager.getOptionList().add(this.rug);
        this.updateSuffix();
        super.postInitialize();
    }
    
    @Override
    public void enable() {
        this.single.enable();
        super.enable();
    }
    
    @EventTarget
    private void onJump(final JumpEvent event) {
        if (StateManager.offsetLastPacketAura()) {
            event.setCancelled(this.jumpNextTick = true);
        }
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (this.jumpNextTick && !StateManager.offsetLastPacketAura()) {
            this.attackDelay = 100L;
            this.attackDelay = -20L;
            ClientUtils.player().jump();
            final EntityPlayerSP player = ClientUtils.player();
            player.motionY -= 0.30000001192092896;
            this.jumpNextTick = false;
        }
        this.vanilla.onUpdate(event);
        this.switchMode.onUpdate(event);
        this.single.onUpdate(event);
        this.tick.onUpdate(event);
        this.rug.onUpdate(event);
    }
    
    @EventTarget
    private void onTick(final TickEvent event) {
        this.updateSuffix();
    }
    
    private void updateSuffix() {
        if (this.switchMode.getValue()) {
            this.setSuffix("Switch");
        }
        else if (this.vanilla.getValue()) {
            this.setSuffix("Vanilla");
        }
        else if (this.tick.getValue()) {
            this.setSuffix("Tick");
        }
        else if (this.rug.getValue()) {
            this.setSuffix("Ruggero");
        }
        else if (this.single.getValue()) {
            this.setSuffix("Single");
        }
        else {
            this.setSuffix("New");
        }
    }
    
    @EventTarget
    private void onPacketSend(final PacketSendEvent event) {
        final boolean b = event.getPacket() instanceof C09PacketHeldItemChange;
    }
    
    public boolean isEntityValid(final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLiving = (EntityLivingBase)entity;
            if (!ClientUtils.player().isEntityAlive() || !entityLiving.isEntityAlive() || entityLiving.getDistanceToEntity(ClientUtils.player()) > (ClientUtils.player().canEntityBeSeen(entityLiving) ? this.range : 3.0)) {
                return false;
            }
            if (this.\u0131njector) {
                ++this.ticks;
                if (this.ticks > 20.0) {
                    this.ticks = 20.0;
                }
                final double n = 2.0;
                this.ticksExisted = n;
                this.ticks = n;
                this.attack(entityLiving, false);
                this.attack(entityLiving, true);
                return true;
            }
            if (entityLiving.ticksExisted < this.ticksExisted) {
                return false;
            }
            if (this.players && entityLiving instanceof EntityPlayer) {
                final EntityPlayer entityPlayer = (EntityPlayer)entityLiving;
                if (FriendManager.isFriend(entityPlayer.getName()) && !this.attackFriends) {
                    return false;
                }
                if (this.armorCheck && !this.hasArmor(entityPlayer)) {
                    return false;
                }
                final ItemStack boots = entityPlayer.getEquipmentInSlot(1);
                final ItemStack legs = entityPlayer.getEquipmentInSlot(2);
                final ItemStack chest = entityPlayer.getEquipmentInSlot(3);
                final ItemStack helm = entityPlayer.getEquipmentInSlot(4);
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
            else {
                if (this.monsters && (entityLiving instanceof EntityMob || entityLiving instanceof EntityGhast || entityLiving instanceof EntityDragon || entityLiving instanceof EntityWither || entityLiving instanceof EntitySlime || (entityLiving instanceof EntityWolf && ((EntityWolf)entityLiving).getOwner() != ClientUtils.player()))) {
                    return true;
                }
                if (this.golems && entityLiving instanceof EntityGolem) {
                    return true;
                }
                if (this.animals && (entityLiving instanceof EntityAnimal || entityLiving instanceof EntitySquid)) {
                    return true;
                }
                if (this.bats && entityLiving instanceof EntityBat) {
                    return true;
                }
                if (this.villagers && entityLiving instanceof EntityVillager) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isEntityValidType(final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLiving = (EntityLivingBase)entity;
            if (!ClientUtils.player().isEntityAlive() || !entityLiving.isEntityAlive()) {
                return false;
            }
            if (this.players && entityLiving instanceof EntityPlayer) {
                final EntityPlayer entityPlayer = (EntityPlayer)entityLiving;
                if (entityPlayer.isInvisible() && !this.booblebee) {
                    return false;
                }
                if (FriendManager.isFriend(entityPlayer.getName()) && !this.attackFriends) {
                    return false;
                }
                if (this.armorCheck && !this.hasArmor(entityPlayer)) {
                    return false;
                }
                final ItemStack boots = entityPlayer.getEquipmentInSlot(1);
                final ItemStack legs = entityPlayer.getEquipmentInSlot(2);
                final ItemStack chest = entityPlayer.getEquipmentInSlot(3);
                final ItemStack helm = entityPlayer.getEquipmentInSlot(4);
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
            else {
                if (this.monsters && (entityLiving instanceof EntityMob || entityLiving instanceof EntityGhast || entityLiving instanceof EntityDragon || entityLiving instanceof EntityWither || entityLiving instanceof EntitySlime || (entityLiving instanceof EntityWolf && ((EntityWolf)entityLiving).getOwner() != ClientUtils.player()))) {
                    return true;
                }
                if (this.golems && entityLiving instanceof EntityGolem) {
                    return true;
                }
                if (this.animals && (entityLiving instanceof EntityAnimal || entityLiving instanceof EntitySquid)) {
                    return true;
                }
                if (this.bats && entityLiving instanceof EntityBat) {
                    return true;
                }
                if (this.villagers && entityLiving instanceof EntityVillager) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean hasArmor(final EntityPlayer player) {
        final ItemStack boots = player.inventory.armorInventory[0];
        final ItemStack pants = player.inventory.armorInventory[1];
        final ItemStack chest = player.inventory.armorInventory[2];
        final ItemStack head = player.inventory.armorInventory[3];
        return boots != null || pants != null || chest != null || head != null;
    }
    
    public void attack(final EntityLivingBase entity) {
        this.attack(entity, this.criticals);
    }
    
    public void attack(final EntityLivingBase entity, final boolean crit) {
        this.swingItem();
        final float sharpLevel = EnchantmentHelper.func_152377_a(ClientUtils.player().getHeldItem(), entity.getCreatureAttribute());
        final boolean vanillaCrit = ClientUtils.player().fallDistance > 0.0f && !ClientUtils.player().onGround && !ClientUtils.player().isOnLadder() && !ClientUtils.player().isInWater() && !ClientUtils.player().isPotionActive(Potion.blindness) && ClientUtils.player().ridingEntity == null;
        ClientUtils.packet(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
    }
    
    public static boolean isOnSameTeam(final EntityPlayer e, final EntityPlayer e2) {
        return e.getDisplayName().getFormattedText().contains("§" + getTeamFromName(e)) && e2.getDisplayName().getFormattedText().contains("§" + getTeamFromName(e));
    }
    
    public static String getTeamFromName(final Entity e) {
        final Matcher m = Pattern.compile("§(.).*§r").matcher(e.getDisplayName().getFormattedText());
        if (m.find()) {
            return m.group(1);
        }
        return "f";
    }
    
    public int randomNumberFromRange(final int min, final int max) {
        return this.rand.nextInt(max - min + 1) + max;
    }
    
    public void swingItem() {
        ClientUtils.player().swingItem();
    }
    
    @Override
    public void disable() {
        StateManager.setOffsetLastPacketAura(false);
        super.disable();
    }
}
