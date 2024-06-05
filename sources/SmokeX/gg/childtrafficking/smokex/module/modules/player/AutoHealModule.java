// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.player;

import net.minecraft.potion.Potion;
import net.minecraft.entity.Entity;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import net.minecraft.client.Minecraft;
import java.util.Iterator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSkull;
import net.minecraft.network.Packet;
import gg.childtrafficking.smokex.utils.system.NetworkUtils;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.PotionEffect;
import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import net.minecraft.item.ItemPotion;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.utils.player.MovementUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.events.player.EventMove;
import gg.childtrafficking.smokex.event.events.player.EventWindowClick;
import gg.childtrafficking.smokex.event.EventListener;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import gg.childtrafficking.smokex.utils.system.TimerUtil;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "AutoHeal", renderName = "Auto Heal", description = "Automatically heals.", aliases = { "heal" }, category = ModuleCategory.PLAYER)
public final class AutoHealModule extends Module
{
    private final BooleanProperty headsProperty;
    private final BooleanProperty potionsProperty;
    private final BooleanProperty jumpOnlyProperty;
    private final NumberProperty<Integer> healthProperty;
    private final NumberProperty<Integer> slotProperty;
    private final BooleanProperty distanceCheckProperty;
    private final NumberProperty<Double> minDistanceProperty;
    private final NumberProperty<Long> delayProperty;
    private final TimerUtil interactionTimer;
    private int prevSlot;
    private boolean potting;
    private String potionCounter;
    private int jumpTicks;
    private boolean jump;
    private static final PotionType[] VALID_POTIONS;
    private final C08PacketPlayerBlockPlacement THROW_POTION_PACKET;
    private final EventListener<EventWindowClick> windowClickEventListener;
    private final EventListener<EventMove> moveEventListener;
    private final EventListener<EventUpdate> updateEventListener;
    
    public AutoHealModule() {
        this.headsProperty = new BooleanProperty("Heads", true);
        this.potionsProperty = new BooleanProperty("Potions", true);
        this.jumpOnlyProperty = new BooleanProperty("JumpOnly", "Jump Only", true);
        this.healthProperty = new NumberProperty<Integer>("Health", 14, 1, 20, 1);
        this.slotProperty = new NumberProperty<Integer>("Slot", 7, 1, 9, 1);
        this.distanceCheckProperty = new BooleanProperty("DistCheck", "Dist Check", true, this.potionsProperty::getValue);
        this.minDistanceProperty = new NumberProperty<Double>("MinPlayerDist", "Min Player Dist", 1.0, 0.1, 5.0, 1.0, this.distanceCheckProperty::getValue);
        this.delayProperty = new NumberProperty<Long>("Delay", "Delay", 500L, 100L, 2000L, 100L);
        this.interactionTimer = new TimerUtil();
        this.THROW_POTION_PACKET = new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f);
        this.windowClickEventListener = (event -> this.interactionTimer.reset());
        this.moveEventListener = (event -> {
            if (this.jump && this.jumpTicks >= 0) {
                event.setX(0.0);
                event.setZ(0.0);
            }
            return;
        });
        this.updateEventListener = (event -> {
            this.setSuffix(((boolean)this.jumpOnlyProperty.getValue()) ? "Jump Only" : "Down");
            if (this.potionsProperty.getValue()) {
                if (event.isPre()) {
                    if (this.jump) {
                        --this.jumpTicks;
                        if (MovementUtils.isOnGround()) {
                            this.jump = false;
                            this.jumpTicks = -1;
                        }
                    }
                    if (ModuleManager.getInstance(ScaffoldModule.class).isToggled()) {
                        return;
                    }
                    else {
                        this.potionCounter = Integer.toString(this.getValidPotionsInInv());
                        if (this.interactionTimer.hasElapsed(this.delayProperty.getValue())) {
                            final float health = this.healthProperty.getValue() * 2.0f;
                            for (int slot = 9; slot < 45; ++slot) {
                                final ItemStack stack = getStackInSlot(slot);
                                if (stack != null && stack.getItem() instanceof ItemPotion && ItemPotion.isSplash(stack.getMetadata()) && PlayerUtils.isBuffPotion(stack)) {
                                    final ItemPotion itemPotion = (ItemPotion)stack.getItem();
                                    boolean validEffects = false;
                                    itemPotion.getEffects(stack.getMetadata()).iterator();
                                    final Iterator iterator;
                                    while (iterator.hasNext()) {
                                        final PotionEffect effect = iterator.next();
                                        if (this.checkEffectAmplifier(stack, effect)) {
                                            final PotionType[] valid_POTIONS = AutoHealModule.VALID_POTIONS;
                                            int j = 0;
                                            for (int length = valid_POTIONS.length; j < length; ++j) {
                                                final PotionType potionType = valid_POTIONS[j];
                                                if (potionType.potionId == effect.getPotionID()) {
                                                    validEffects = true;
                                                    potionType.requirements;
                                                    final Requirement[] array;
                                                    final int length2 = array.length;
                                                    int k = 0;
                                                    while (k < length2) {
                                                        final Requirement requirement = array[k];
                                                        if (!requirement.test(health, effect.getAmplifier(), potionType.potionId)) {
                                                            validEffects = false;
                                                            break;
                                                        }
                                                        else {
                                                            ++k;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (validEffects) {
                                        if (MovementUtils.isOverVoid()) {
                                            return;
                                        }
                                        else if (this.mc.currentScreen != null) {
                                            return;
                                        }
                                        else if (this.distanceCheckProperty.getValue() && this.isPlayerInRange(this.minDistanceProperty.getValue())) {
                                            return;
                                        }
                                        else {
                                            this.prevSlot = this.mc.thePlayer.inventory.currentItem;
                                            final double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX;
                                            final double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ;
                                            final double speed = StrictMath.sqrt(xDist * xDist + zDist * zDist);
                                            final boolean shouldPredict = speed > 0.38;
                                            final boolean shouldJump = speed < 0.221;
                                            final boolean onGround = MovementUtils.isOnGround();
                                            final boolean jumpOnly = this.jumpOnlyProperty.getValue();
                                            if ((shouldJump || jumpOnly) && onGround && !MovementUtils.isBlockAbove() && MovementUtils.getJumpBoostModifier() == 0) {
                                                this.mc.thePlayer.motionX = 0.0;
                                                this.mc.thePlayer.motionZ = 0.0;
                                                event.setPitch(-90.0f);
                                                this.mc.thePlayer.jump();
                                                this.jump = true;
                                                this.jumpTicks = 9;
                                            }
                                            else if ((shouldPredict || onGround) && !jumpOnly) {
                                                event.setYaw(MovementUtils.getMovementDirection());
                                                event.setPitch(shouldPredict ? 0.0f : 45.0f);
                                            }
                                            else {
                                                return;
                                            }
                                            int potSlot;
                                            if (slot >= 36) {
                                                potSlot = slot - 36;
                                            }
                                            else {
                                                final int potionSlot = this.slotProperty.getValue() - 1;
                                                PlayerUtils.windowClick(slot, potionSlot, PlayerUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
                                                potSlot = potionSlot;
                                            }
                                            NetworkUtils.sendPacket(new C09PacketHeldItemChange(potSlot));
                                            this.potting = true;
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else if (this.potting && this.prevSlot != -1) {
                    NetworkUtils.sendPacket(this.THROW_POTION_PACKET);
                    NetworkUtils.sendPacket(new C09PacketHeldItemChange(this.prevSlot));
                    this.interactionTimer.reset();
                    this.prevSlot = -1;
                    this.potting = false;
                    return;
                }
            }
            if (this.headsProperty.getValue() && event.isPre() && this.interactionTimer.hasElapsed(200.0) && !ModuleManager.getInstance(ScaffoldModule.class).isToggled() && this.mc.thePlayer.getHealth() <= 14.0f) {
                int i = 0;
                while (i < 45) {
                    final ItemStack stack2 = getStackInSlot(i);
                    if (stack2 != null && ((stack2.getItem() instanceof ItemSkull && stack2.getDisplayName().contains("Golden")) || stack2.getItem() == Items.magma_cream || stack2.getItem() == Items.mutton || stack2.getItem() == Items.baked_potato || stack2.getDisplayName().contains("§cFirst"))) {
                        int headSlot;
                        if (i >= 36) {
                            headSlot = i - 36;
                        }
                        else {
                            final int desiredSlot = 6;
                            PlayerUtils.windowClick(i, desiredSlot, PlayerUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
                            headSlot = desiredSlot;
                        }
                        final int oldSlot = this.mc.thePlayer.inventory.currentItem;
                        NetworkUtils.sendPacket(new C09PacketHeldItemChange(headSlot));
                        NetworkUtils.sendPacket(this.THROW_POTION_PACKET);
                        NetworkUtils.sendPacket(new C09PacketHeldItemChange(oldSlot));
                        this.interactionTimer.reset();
                    }
                    else {
                        ++i;
                    }
                }
            }
        });
    }
    
    public static ItemStack getStackInSlot(final int index) {
        return Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(index).getStack();
    }
    
    private int getValidPotionsInInv() {
        int count = 0;
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemPotion && ItemPotion.isSplash(stack.getMetadata()) && PlayerUtils.isBuffPotion(stack)) {
                final ItemPotion itemPotion = (ItemPotion)stack.getItem();
                final List<PotionEffect> effects = itemPotion.getEffects(stack.getMetadata());
                if (effects != null) {
                    for (final PotionEffect effect : effects) {
                        boolean breakOuter = false;
                        for (final PotionType type : AutoHealModule.VALID_POTIONS) {
                            if (type.potionId == effect.getPotionID()) {
                                ++count;
                                breakOuter = true;
                                break;
                            }
                        }
                        if (breakOuter) {
                            break;
                        }
                    }
                }
            }
        }
        return count;
    }
    
    private boolean checkEffectAmplifier(final ItemStack stack, final PotionEffect effectToCheck) {
        int bestAmplifier = -1;
        ItemStack bestStack = null;
        for (int i = 9; i < 45; ++i) {
            final ItemStack stackInSlot = getStackInSlot(i);
            if (stackInSlot != null && stackInSlot.getItem() instanceof ItemPotion) {
                final ItemPotion itemPotion = (ItemPotion)stackInSlot.getItem();
                for (final PotionEffect effect : itemPotion.getEffects(stackInSlot.getMetadata())) {
                    final int amp = effect.getAmplifier();
                    if (effect.getPotionID() == effectToCheck.getPotionID() && amp > bestAmplifier) {
                        bestStack = stackInSlot;
                        bestAmplifier = amp;
                    }
                }
            }
        }
        return bestStack == stack;
    }
    
    public boolean isPlayerInRange(final double distance) {
        final Entity player = this.mc.thePlayer;
        final double x = player.posX;
        final double y = player.posY;
        final double z = player.posZ;
        for (final EntityPlayer entity : this.mc.theWorld.playerEntities) {
            if (!entity.isSpectator() && entity instanceof EntityOtherPlayerMP) {
                final double d1 = entity.getDistanceSq(x, y, z);
                if (d1 < distance * distance) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    static {
        VALID_POTIONS = new PotionType[] { PotionType.HEALTH, PotionType.REGEN, PotionType.SPEED };
    }
    
    private enum Requirements
    {
        BETTER_THAN_CURRENT((Requirement)new BetterThanCurrentRequirement()), 
        HEALTH_BELOW((Requirement)new HealthBelowRequirement());
        
        private final Requirement requirement;
        
        private Requirements(final Requirement requirement) {
            this.requirement = requirement;
        }
        
        public Requirement getRequirement() {
            return this.requirement;
        }
    }
    
    private enum PotionType
    {
        SPEED(Potion.moveSpeed.id, new Requirement[] { Requirements.BETTER_THAN_CURRENT.getRequirement() }), 
        REGEN(Potion.regeneration.id, new Requirement[] { Requirements.HEALTH_BELOW.getRequirement(), Requirements.BETTER_THAN_CURRENT.getRequirement() }), 
        HEALTH(Potion.heal.id, new Requirement[] { Requirements.HEALTH_BELOW.getRequirement() });
        
        private final int potionId;
        private final Requirement[] requirements;
        
        private PotionType(final int potionId, final Requirement[] requirements) {
            this.potionId = potionId;
            this.requirements = requirements;
        }
    }
    
    private static class HealthBelowRequirement implements Requirement
    {
        @Override
        public boolean test(final float healthTarget, final int currentAmplifier, final int potionId) {
            return Minecraft.getMinecraft().thePlayer.getHealth() < healthTarget;
        }
    }
    
    private static class BetterThanCurrentRequirement implements Requirement
    {
        @Override
        public boolean test(final float healthTarget, final int currentAmplifier, final int potionId) {
            final PotionEffect effect = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(potionId);
            return effect == null || effect.getAmplifier() < currentAmplifier;
        }
    }
    
    private interface Requirement
    {
        boolean test(final float p0, final int p1, final int p2);
    }
}
