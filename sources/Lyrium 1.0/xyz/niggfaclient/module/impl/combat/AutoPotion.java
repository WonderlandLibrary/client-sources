// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.combat;

import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.module.impl.player.Scaffold;
import net.minecraft.item.ItemPotion;
import xyz.niggfaclient.property.impl.Representation;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.utils.other.TimerUtil;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "AutoPotion", description = "Throws potions for you", cat = Category.COMBAT)
public class AutoPotion extends Module
{
    private final DoubleProperty healthSet;
    private final DoubleProperty delaySet;
    private final Property<Boolean> showPotions;
    private final Property<Boolean> frog;
    private final Property<Boolean> speed;
    private final Property<Boolean> regen;
    private final Property<Boolean> healing;
    private final TimerUtil throwAgainTimer;
    int oldSlot;
    int stage;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    
    public AutoPotion() {
        this.healthSet = new DoubleProperty("Regen Health", 10.0, 2.0, 20.0, 1.0);
        this.delaySet = new DoubleProperty("Delay", 50.0, 200.0, 1000.0, 5.0, Representation.MILLISECONDS);
        this.showPotions = new Property<Boolean>("Show Potions..", false);
        this.frog = new Property<Boolean>("Frog", true, this.showPotions::getValue);
        this.speed = new Property<Boolean>("Speed", true, this.showPotions::getValue);
        this.regen = new Property<Boolean>("Regen", true, this.showPotions::getValue);
        this.healing = new Property<Boolean>("Healing", true, this.showPotions::getValue);
        this.throwAgainTimer = new TimerUtil();
        this.oldSlot = 0;
        this.stage = 0;
        int potionCounter;
        int i;
        ItemStack itemStack;
        int potState;
        int j;
        this.motionEventListener = (e -> {
            potionCounter = 0;
            for (i = 0; i < 9; ++i) {
                itemStack = this.mc.thePlayer.inventory.getStackInSlot(i);
                if (itemStack != null && itemStack.getItem() instanceof ItemPotion && (itemStack.getDisplayName().contains("Swiftness") || itemStack.getDisplayName().contains("Regeneration") || itemStack.getDisplayName().contains("Healing"))) {
                    ++potionCounter;
                }
            }
            this.setDisplayName("Auto Potion §7" + potionCounter);
            if (!ModuleManager.getModule(Scaffold.class).isEnabled()) {
                potState = -1;
                for (j = 0; j < 9; ++j) {
                    if (this.mc.thePlayer.inventory.getStackInSlot(j) != null && this.mc.thePlayer.inventory.getStackInSlot(j).getItem() instanceof ItemPotion) {
                        potState = j;
                        ++potionCounter;
                    }
                }
                if (potState != -1) {
                    if (((!this.mc.thePlayer.isPotionActive(Potion.moveSpeed) || this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getDuration() < 30) && this.isSwiftnessPot(this.mc.thePlayer.inventory.getStackInSlot(potState)) && this.throwAgainTimer.hasElapsed(this.delaySet.getValue().longValue())) || (!this.mc.thePlayer.isPotionActive(Potion.regeneration) && this.isHealingPot(this.mc.thePlayer.inventory.getStackInSlot(potState)) && this.mc.thePlayer.getHealth() < this.healthSet.getValue() && this.throwAgainTimer.hasElapsed(this.delaySet.getValue().longValue())) || (this.isRegenPot(this.mc.thePlayer.inventory.getStackInSlot(potState)) && this.mc.thePlayer.getHealth() < this.healthSet.getValue() && this.throwAgainTimer.hasElapsed(this.delaySet.getValue().longValue()))) {
                        e.setPitch(90.0f);
                        this.stage = 1;
                        this.throwAgainTimer.reset();
                    }
                    if (this.stage >= 1) {
                        switch (this.stage) {
                            case 1: {
                                e.setPitch(90.0f);
                                this.oldSlot = this.mc.thePlayer.inventory.currentItem;
                                this.mc.thePlayer.inventory.currentItem = potState;
                                ++this.stage;
                                break;
                            }
                            case 2: {
                                e.setPitch(90.0f);
                                PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getStackInSlot(potState)));
                                ++this.stage;
                                break;
                            }
                        }
                    }
                }
            }
        });
    }
    
    private boolean isSwiftnessPot(final ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemPotion && ((this.speed.getValue() && (stack.getDisplayName().contains("Swiftness") || stack.getDisplayName().contains("§1Swiftness"))) || (this.frog.getValue() && stack.getDisplayName().contains("Frog's")));
    }
    
    private boolean isHealingPot(final ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemPotion && this.healing.getValue() && stack.getDisplayName().contains("Healing");
    }
    
    private boolean isRegenPot(final ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemPotion && this.regen.getValue() && stack.getDisplayName().contains("Regeneration");
    }
}
