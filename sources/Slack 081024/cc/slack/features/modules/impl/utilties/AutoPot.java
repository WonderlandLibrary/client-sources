package cc.slack.features.modules.impl.utilties;

import cc.slack.start.Slack;
import cc.slack.events.State;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.combat.KillAura;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.other.TimeUtil;
import cc.slack.utils.player.InventoryUtil;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@ModuleInfo(
        name = "AutoPot",
        category = Category.UTILITIES
)
public class AutoPot extends Module {


    public static class ItemPot {
        private final ItemStack itemStack;
        private int slot;
        private final boolean soup;

        public ItemPot(ItemStack itemStack, int slot, boolean isSoup) {
            this.itemStack = itemStack;
            this.slot = slot;
            this.soup = isSoup;
        }

        public int getSlot() {
            return slot;
        }

        public void setSlot(int slot) {
            this.slot = slot;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

        public boolean isSoup() {
            return soup;
        }
    }

    private final NumberValue<Double> healthValue = new NumberValue<>("Health", 10.0D, 1.0D, 20.0D, 0.5D);
    private final NumberValue<Integer> delayValue = new NumberValue<>("Delay", 100, 50, 1000, 50);

    private final BooleanValue healthpotion = new BooleanValue("Health Potion", true);
    private final BooleanValue speedpotion = new BooleanValue("Speed Potion", true);
    private final BooleanValue soupvalue = new BooleanValue("Soup", false);

    // Checks
    private final BooleanValue noAura = new BooleanValue("No KillAura", true);
    private final BooleanValue noMove = new BooleanValue("No Move", false);

    private final TimeUtil timer = new TimeUtil();

    public AutoPot() {
        addSettings(healthValue, delayValue, healthpotion, speedpotion, soupvalue, noAura, noMove);
    }

    @Listen
    public void onMotion (MotionEvent event) {
        if(!MovementUtil.isOnGround(0.22)) return;
        if (noAura.getValue() && Slack.getInstance().getModuleManager().getInstance(KillAura.class).isToggle()) return;
        if (noMove.getValue() && MovementUtil.isMoving()) return;

        ItemPot healthPotion = healthValue.getValue() >= mc.thePlayer.getHealth() && (healthpotion.getValue() || soupvalue.getValue()) ? getHealingPotion() : !mc.thePlayer.isPotionActive(Potion.moveSpeed) && speedpotion.getValue() ? getSpeedPotion() : null;
        if((event.getState() == State.PRE) && !mc.thePlayer.capabilities.allowFlying) {
            if(healthPotion == null || !timer.hasReached(delayValue.getValue()))
                return;
            if(mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel")) {
                PacketUtil.sendNoEvent(new C03PacketPlayer.C05PacketPlayerLook(event.getYaw(), 90, event.isGround()));
            } else {
                event.setPitch(90);
            }
        }
        if(!(event.getState() == State.PRE) && !mc.thePlayer.capabilities.allowFlying) {
            if(healthPotion == null || !timer.hasReached(delayValue.getValue()))
                return;

            if(healthPotion.getSlot() < 36) {
                InventoryUtil.swap(healthPotion.getSlot(), 5);
                healthPotion.setSlot(41);
            }

            mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(healthPotion.getSlot() - 36));
            mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
            mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            timer.reset();
        }
    }


    public static ItemPot getHealingPotion() {
        Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.resetColor();
        for(int i = 9; i < 45; ++i) {
            if(InventoryUtil.getSlot(i).getHasStack()) {
                ItemStack itemStack = InventoryUtil.getSlot(i).getStack();
                if(itemStack.getItem() instanceof ItemPotion) {
                    ItemPotion itemPotion = (ItemPotion) itemStack.getItem();

                    if(!ItemPotion.isSplash(itemStack.getItemDamage()))
                        continue;

                    for (PotionEffect effect : itemPotion.getEffects(itemStack)) {
                        if(effect.getPotionID() == Potion.heal.getId() || (effect.getPotionID() == Potion.regeneration.getId() && !mc.thePlayer.isPotionActive(Potion.regeneration))) {
                            return new ItemPot(itemStack, i, false);
                        }
                    }
                }

                BooleanValue b = Slack.getInstance().getModuleManager().getInstance(AutoPot.class).soupvalue;

                if((itemStack.getItem() instanceof ItemSoup || itemStack.getItem() instanceof ItemSkull) && b.getValue()) {
                    return new ItemPot(itemStack, i, true);
                }
            }
        }
        return null;
    }

    private ItemPot getSpeedPotion() {
        for(int i = 9; i < 45; ++i) {
            if(InventoryUtil.getSlot(i).getHasStack()) {
                ItemStack itemStack = InventoryUtil.getSlot(i).getStack();
                if(itemStack.getItem() instanceof ItemPotion) {
                    ItemPotion itemPotion = (ItemPotion) itemStack.getItem();

                    if(!ItemPotion.isSplash(itemStack.getItemDamage()))
                        continue;

                    for (PotionEffect effect : itemPotion.getEffects(itemStack)) {
                        if(effect.getPotionID() == Potion.moveSpeed.getId()) {
                            return new ItemPot(itemStack, i, false);
                        }
                    }
                }
            }
        }
        return null;
    }


}
