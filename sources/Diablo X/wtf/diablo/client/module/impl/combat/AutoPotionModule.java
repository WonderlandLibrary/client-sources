package wtf.diablo.client.module.impl.combat;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.MultiModeSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.mc.entity.EntityUtil;

//TODO: add delay
@ModuleMetaData(
        name = "Auto Potion",
        description = "Automatically uses potions when needed.",
        category = ModuleCategoryEnum.COMBAT)
public final class AutoPotionModule extends AbstractModule {
    private final MultiModeSetting<EnumAutoPotionMode> potions = new MultiModeSetting<>("Potions", EnumAutoPotionMode.values());
    private final NumberSetting<Integer> health = new NumberSetting<>("Health", 10, 1, 20, 1);

    public AutoPotionModule() {
        this.registerSettings(potions, health);
    }

    @EventHandler
    private final Listener<MotionEvent> updateListener = e -> {
        if (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)
            return;

        int slot = -1;
        for(int i = 0; i < 45; i++) {
            if(slot != -1)
                break;

            if(mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                if(mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemPotion) {
                    final ItemPotion potion = (ItemPotion) mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();

                    if (!ItemPotion.isSplash(mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItemDamage()))
                        continue;

                    if(potion.getEffects(mc.thePlayer.inventoryContainer.getSlot(i).getStack()) != null) {
                        for(final PotionEffect effect : potion.getEffects(mc.thePlayer.inventoryContainer.getSlot(i).getStack())) {
                            for (final EnumAutoPotionMode mode : potions.getValue()) {
                                switch (mode) {
                                    case HEALTH:
                                        if(effect.getPotionID() == Potion.heal.id) {
                                            if(mc.thePlayer.getHealth() <= health.getValue()) {
                                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 8, 2, mc.thePlayer);
                                                mc.playerController.updateController();
                                                slot = i;
                                            }
                                        }
                                        break;
                                    case SPEED:
                                        if(effect.getPotionID() == Potion.moveSpeed.id) {
                                            if(!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 8, 2, mc.thePlayer);
                                                mc.playerController.updateController();
                                                slot = i;
                                            }
                                        }
                                        break;
                                }
                            }

                        }
                    }
                }
            }
        }

        if (!mc.thePlayer.onGround)
            return;

        if (slot == -1)
            return;

        EntityUtil.rotate(e, mc.thePlayer.rotationYaw, 90);


        int currentSlot = mc.thePlayer.inventory.currentItem;

        mc.thePlayer.inventory.currentItem = 8;
        if (mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion) {
            mc.playerController.updateController();
            mc.getNetHandler().addToSendQueueNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(8)));
            mc.playerController.updateController();
        }
        mc.thePlayer.inventory.currentItem = currentSlot;


        //TODO: fix this because it wont swap the old item back
        //mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 8, slot, 2, mc.thePlayer);
    };

    public enum EnumAutoPotionMode implements IMode {
        HEALTH("Health"),
        SPEED("Speed"),
        REGEN("Regen");

        private final String name;

        EnumAutoPotionMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
