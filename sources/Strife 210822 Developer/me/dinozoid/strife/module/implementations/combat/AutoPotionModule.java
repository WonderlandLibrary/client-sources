package me.dinozoid.strife.module.implementations.combat;

import me.dinozoid.strife.alpine.event.EventState;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.network.PacketOutboundEvent;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.event.implementations.player.WindowClickEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.module.implementations.movement.SpeedModule;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.property.implementations.EnumProperty;
import me.dinozoid.strife.util.player.MovementUtil;
import me.dinozoid.strife.util.player.PlayerUtil;
import me.dinozoid.strife.util.system.TimerUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "AutoPotion", renderName = "AutoPotion", description = "Automatically uses potions.", aliases = "AutoPot", category = Category.COMBAT)
public class AutoPotionModule extends Module {

    private final EnumProperty<HealthMode> healthProperty = new EnumProperty("Health", HealthMode.HALF);

    private DoubleProperty delayProperty = new DoubleProperty("Throw Delay", 500, 0, 2500, 100, Property.Representation.MILLISECONDS);

    private int potionSlot = -1;
    private int waitTicks;
    private boolean throwing;
    private TimerUtil delayTimer = new TimerUtil();

    @Override
    public void onEnable() {
        super.onEnable();
        delayTimer.reset();
        potionSlot = -1;
    }

    @EventHandler
    private final Listener<WindowClickEvent> windowClickListener = new Listener<>(event -> delayTimer.reset());

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = new Listener<>(event -> {
        if(mc.thePlayer == null || mc.theWorld == null) return;
        Potion[] potions = Potion.potionTypes;
        if (event.state() == EventState.PRE) {
            if(waitTicks > 0) {
                waitTicks--;
                return;
            }
            for (int i = PlayerUtil.ONLY_HOT_BAR_BEGIN; i < PlayerUtil.END; i++) {
                ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack != null && stack.getItem() instanceof ItemPotion && ItemPotion.isSplash(stack.getItemDamage()) && KillAuraModule.instance().target() == null) {
                    for (Potion pot : potions) {
                        if (pot != null && !pot.isBadEffect() && PlayerUtil.potionHasEffect(stack, pot.id) && !mc.thePlayer.isPotionActive(pot.id)) {
                            potionSlot = i;
                            if (MovementUtil.isMoving() && MovementUtil.isOnGround()) event.pitch(58);
                            else if(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 2, mc.thePlayer.posZ).getBlock() instanceof BlockAir) {
                                event.pitch(-90);
                            }
                        }
                    }
                }
            }
        } else {
            if (potionSlot != -1 && (event.prevPitch() == 58 && MovementUtil.isMoving()) || (event.prevPitch() == -90 && !MovementUtil.isMoving() && MovementUtil.isOnGround())) {
                if (delayTimer.hasElapsed(delayProperty.value().longValue())) {
                    if(event.prevPitch() == -90 && !MovementUtil.isMoving()) {
                        mc.thePlayer.jump();
                        event.posY(mc.thePlayer.motionX = 0);
                        event.posZ(mc.thePlayer.motionZ = 0);
                    }
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(potionSlot - PlayerUtil.ONLY_HOT_BAR_BEGIN));
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    waitTicks = 6;
                    delayTimer.reset();
                    potionSlot = -1;
                }
            }
        }
    });

    private enum HealthMode {
        QUARTER, HALF, THREE_QUARTER, FULL
    }

}
