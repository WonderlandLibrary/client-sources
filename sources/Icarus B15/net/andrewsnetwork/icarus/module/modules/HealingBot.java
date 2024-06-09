// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.Entity;
import net.andrewsnetwork.icarus.utilities.BlockHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.andrewsnetwork.icarus.event.events.SentPacket;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.event.events.PostMotion;
import net.andrewsnetwork.icarus.utilities.EntityHelper;
import net.andrewsnetwork.icarus.utilities.InventoryUtils;
import net.minecraft.potion.Potion;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.values.ConstrainedValue;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.andrewsnetwork.icarus.values.Value;
import net.andrewsnetwork.icarus.module.Module;

public class HealingBot extends Module
{
    private final Value<Integer> delay;
    private final Value<Integer> health;
    private final Value<Boolean> predict;
    private final Value<Boolean> freeze;
    private final Value<Boolean> potions;
    private final Value<Boolean> soups;
    private final Value<Boolean> cookies;
    private final Value<Boolean> heads;
    private final Value<Boolean> ink;
    private final TimeHelper potionTime;
    private final TimeHelper time;
    private boolean needsToHeal;
    private boolean healing;
    private boolean face;
    private boolean shouldFreeze;
    private float smoothPitch;
    
    public HealingBot() {
        super("HealingBot", -8336163, Category.COMBAT);
        this.delay = (Value<Integer>)new ConstrainedValue("healingbot_Delay", "delay", 250, 0, 1000, this);
        this.health = (Value<Integer>)new ConstrainedValue("healingbot_Health", "health", 10, 0, 20, this);
        this.predict = new Value<Boolean>("healingbot_Predict", "predict", true, this);
        this.freeze = new Value<Boolean>("healingbot_Freeze", "freeze", false, this);
        this.potions = new Value<Boolean>("healingbot_Potions", "potions", true, this);
        this.soups = new Value<Boolean>("healingbot_Soups", "soups", true, this);
        this.cookies = new Value<Boolean>("healingbot_Cookies", "cookies", false, this);
        this.heads = new Value<Boolean>("healingbot_Heads", "heads", false, this);
        this.ink = new Value<Boolean>("healingbot_Ink", "ink", false, this);
        this.potionTime = new TimeHelper();
        this.time = new TimeHelper();
        this.setTag("Healing Bot");
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PreMotion) {
            this.needsToHeal = (HealingBot.mc.thePlayer.getHealth() <= this.health.getValue());
            final PreMotion event = (PreMotion)e;
            this.updateCounter();
            if (this.needsToHeal && this.potionTime.hasReached(this.delay.getValue()) && this.potions.getValue()) {
                if (this.shouldFreeze) {
                    this.shouldFreeze = false;
                    return;
                }
                if (!InventoryUtils.hotbarHasPotion(Potion.heal, true) && !InventoryUtils.hotbarIsFull()) {
                    InventoryUtils.shiftClickPotion(Potion.heal, true);
                }
                if (InventoryUtils.hotbarHasPotion(Potion.heal, true)) {
                    event.setPitch(98.0f);
                    if (this.predict.getValue()) {
                        final double blocking = HealingBot.mc.thePlayer.posX + HealingBot.mc.thePlayer.motionX * 16.0;
                        final double movedPosY = HealingBot.mc.thePlayer.boundingBox.minY - 3.6;
                        final double movedPosZ = HealingBot.mc.thePlayer.posZ + HealingBot.mc.thePlayer.motionZ * 16.0;
                        final float yaw = EntityHelper.getRotationFromPosition(blocking, movedPosZ, movedPosY)[0];
                        final float pitch = EntityHelper.getRotationFromPosition(blocking, movedPosZ, movedPosY)[1];
                        event.setYaw(yaw);
                        event.setPitch(pitch);
                    }
                    this.needsToHeal = false;
                    this.healing = true;
                    this.face = true;
                }
            }
        }
        if (e instanceof PostMotion) {
            if (this.healing) {
                this.shouldFreeze = true;
                InventoryUtils.useFirstPotion(Potion.heal, true);
                this.healing = false;
                this.potionTime.reset();
            }
            if (this.heads.getValue()) {
                this.needsToHeal = ((this.needsToHeal || Icarus.getModuleManager().getModuleByName("teams").isEnabled()) && this.teammateNeedsToHeal());
            }
            if (this.needsToHeal) {
                if (this.soups.getValue()) {
                    InventoryUtils.dropFirst(Items.bowl);
                    if (!InventoryUtils.hotbarHas(Items.mushroom_stew) && !InventoryUtils.hotbarIsFull()) {
                        InventoryUtils.shiftClick(Items.mushroom_stew);
                    }
                    if (this.time.hasReached(this.delay.getValue()) && InventoryUtils.hotbarHas(Items.mushroom_stew)) {
                        InventoryUtils.useFirst(Items.mushroom_stew);
                        this.needsToHeal = false;
                        this.time.reset();
                    }
                }
                if (this.heads.getValue()) {
                    if (!InventoryUtils.hotbarHas(Items.skull) && !InventoryUtils.hotbarIsFull()) {
                        InventoryUtils.shiftClick(Items.skull);
                    }
                    if (this.time.hasReached(this.delay.getValue()) && InventoryUtils.hotbarHas(Items.skull)) {
                        InventoryUtils.useFirst(Items.skull);
                        this.needsToHeal = false;
                        this.time.reset();
                    }
                }
                if (this.cookies.getValue()) {
                    if (!InventoryUtils.hotbarHas(Items.cookie) && !InventoryUtils.hotbarIsFull()) {
                        InventoryUtils.shiftClick(Items.cookie);
                    }
                    if (this.time.hasReached(this.delay.getValue()) && InventoryUtils.hotbarHas(Items.cookie)) {
                        InventoryUtils.useFirst(Items.cookie);
                        this.needsToHeal = false;
                        this.time.reset();
                    }
                }
                if (this.ink.getValue()) {
                    if (!InventoryUtils.hotbarHas(Item.getByNameOrId("351")) && !InventoryUtils.hotbarIsFull()) {
                        InventoryUtils.shiftClick(Item.getByNameOrId("351"));
                    }
                    if (this.time.hasReached(this.delay.getValue()) && InventoryUtils.hotbarHas(Item.getByNameOrId("351"))) {
                        InventoryUtils.useFirst(Item.getByNameOrId("351"));
                        this.needsToHeal = false;
                        this.time.reset();
                    }
                }
            }
        }
        if (e instanceof SentPacket) {
            final SentPacket event2 = (SentPacket)e;
            if (event2.getPacket() instanceof C03PacketPlayer) {
                final C03PacketPlayer player = (C03PacketPlayer)event2.getPacket();
                if (player.rotating && this.face) {
                    player.pitch = 90.0f;
                    if (this.predict.getValue()) {
                        final double blocking2 = HealingBot.mc.thePlayer.posX + HealingBot.mc.thePlayer.motionX * 16.0;
                        final double movedPosY2 = HealingBot.mc.thePlayer.boundingBox.minY - 3.6;
                        final double movedPosZ2 = HealingBot.mc.thePlayer.posZ + HealingBot.mc.thePlayer.motionZ * 16.0;
                        final float yaw2 = EntityHelper.getRotationFromPosition(blocking2, movedPosZ2, movedPosY2)[0];
                        final float pitch2 = EntityHelper.getRotationFromPosition(blocking2, movedPosZ2, movedPosY2)[1];
                        player.yaw = yaw2;
                        player.pitch = pitch2;
                        this.face = false;
                    }
                }
            }
        }
    }
    
    private boolean teammateNeedsToHeal() {
        return HealingBot.mc.theWorld.playerEntities.stream().filter(player -> ((EntityLivingBase) player).getHealth() <= this.health.getValue()).filter(player -> HealingBot.mc.thePlayer.isOnSameTeam((EntityLivingBase) player) || Icarus.getFriendManager().isFriend(((Entity) player).getName())).findFirst().isPresent();
    }
    
    private boolean canSafelyThrowPot() {
        final Block blockUnder = BlockHelper.getBlock(HealingBot.mc.thePlayer, -2.1);
        final Block block2Under = BlockHelper.getBlock(HealingBot.mc.thePlayer, -1.1);
        boolean airCheck = false;
        boolean waterCheck = false;
        if (blockUnder instanceof BlockAir && block2Under instanceof BlockAir) {
            airCheck = true;
        }
        else if (blockUnder instanceof BlockLiquid && block2Under instanceof BlockLiquid) {
            waterCheck = true;
        }
        return InventoryUtils.countPotion(Potion.heal, true) > 0 && !airCheck && !waterCheck;
    }
    
    private void updateCounter() {
        String displayName = "Healing Bot";
        if (this.soups.getValue()) {
            final int soups = InventoryUtils.countItem(Items.mushroom_stew);
            if (soups > 0) {
                displayName = String.valueOf(displayName) + " §6" + soups;
            }
        }
        if (this.heads.getValue()) {
            final int heads = InventoryUtils.countItem(Items.skull);
            if (heads > 0) {
                displayName = String.valueOf(displayName) + " §7" + heads;
            }
        }
        if (this.cookies.getValue()) {
            final int cookies = InventoryUtils.countItem(Items.cookie);
            if (cookies > 0) {
                displayName = String.valueOf(displayName) + " §5" + cookies;
            }
        }
        if (this.potions.getValue()) {
            final int potions = InventoryUtils.countPotion(Potion.heal, true);
            if (potions > 0) {
                displayName = String.valueOf(displayName) + " §c" + potions;
            }
        }
        if (this.ink.getValue()) {
            final int ink = InventoryUtils.countItem(Item.getByNameOrId("351"));
            if (ink > 0) {
                displayName = String.valueOf(displayName) + " §b" + ink;
            }
        }
        this.setTag(displayName);
    }
    
    public boolean isHealing() {
        return this.healing;
    }
}
