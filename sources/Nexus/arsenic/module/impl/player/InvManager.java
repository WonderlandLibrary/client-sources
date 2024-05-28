package arsenic.module.impl.player;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventDisplayGuiScreen;
import arsenic.event.impl.EventTick;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.PropertyInfo;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.module.property.impl.rangeproperty.RangeProperty;
import arsenic.module.property.impl.rangeproperty.RangeValue;
import arsenic.utils.interfaces.IWeapon;
import arsenic.utils.timer.Timer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.*;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "InvManager", category = ModuleCategory.Player)
public class InvManager extends Module {

    public final RangeProperty startDelay = new RangeProperty("StartDelay", new RangeValue(0, 500, 75, 150, 1));
    public final RangeProperty delay = new RangeProperty("Delay", new RangeValue(0, 500, 75, 150, 1));
    public final BooleanProperty closeOnFinish = new BooleanProperty("Close on finish", true);
    public final BooleanProperty dontDrop = new BooleanProperty("Don't Drop", true);

    @PropertyInfo(reliesOn = "Close on finish", value = "true")
    public final RangeProperty closeDelay = new RangeProperty("Close Delay", new RangeValue(0, 500, 75, 150, 1));

    private Timer timer = new Timer();
    private boolean shouldSteal;
    private List<Slot> path;

    private Runnable nextAction;

    private final Runnable closeAction = () -> {
        if(closeOnFinish.getValue()) {
            mc.thePlayer.closeScreen();
            mc.currentScreen = null;
        }
    };
    private final Runnable stealAction = () -> {
        if(!path.isEmpty()) {
            Slot slot = path.remove(0);
            if(slot.slot < 0) {
                getStealAction().run();
                return;
            }
            if(dontDrop.getValue() && slot.mode == 4) {
                getStealAction().run();
                return;
            }
            mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, slot.slot, slot.button, slot.mode, mc.thePlayer);
            timer.setCooldown((int) delay.getValue().getRandomInRange());
            nextAction = getStealAction();
        } else {
            timer.setCooldown((int) closeDelay.getValue().getRandomInRange());
            nextAction = closeAction;
        }
    };


    //smh java trying to stop me from being an idiot >:(
    private Runnable getStealAction() {
        return stealAction;
    }

    //HEALTH WARNING INCOMING

    @EventLink
    public final Listener<EventDisplayGuiScreen> guiDisplayListener = event -> {
        shouldSteal = false;
        if(mc.thePlayer == null || event.getGuiScreen() == null || mc.thePlayer.openContainer == null)
            return;
        if(mc.thePlayer.openContainer != mc.thePlayer.inventoryContainer || !(event.getGuiScreen() instanceof GuiContainer))
            return;

        ContainerPlayer container = (ContainerPlayer) mc.thePlayer.openContainer;
        path = generatePath(container);
        shouldSteal = true;
        timer.start();
        timer.setCooldown((int) startDelay.getValue().getRandomInRange());
        nextAction = stealAction;
    };

    @EventLink
    public final Listener<EventTick> tickListener = event -> {
        if(!shouldSteal)
            return;
        if(!timer.firstFinish())
            return;
        nextAction.run();
        timer.start();
    };

    @Override
    protected void onDisable() {
        shouldSteal = false;
    }


    public List<Slot> generatePath(ContainerPlayer inv) {
        ArrayList<Slot> slots = new ArrayList<>();
        for(ItemType itemType : ItemType.values()) {
            itemType.clear();
        }

        for (int i = 0; i < inv.getInventory().size(); i++) {
            ItemStack stack = inv.getInventory().get(i);
            if(stack == null)
                continue;
            Item item = stack.getItem();
            boolean relevant = false;
            for(ItemType itemType : ItemType.values()) {
                if(itemType.isItem(item)) {
                    relevant = true;
                    Slot itemSlot = itemType.getSlot(i);
                    if(itemSlot.value > itemType.getBestItem(itemSlot.type).value) {
                        Slot prevBestItem = itemType.getBestItem(itemSlot.type);
                        prevBestItem.setActionDrop();
                        slots.add(prevBestItem);
                        itemType.setBestItem(itemSlot, itemSlot.type);
                    } else {
                        itemSlot.setActionDrop();
                        slots.add(itemSlot);
                    }
                    break;
                }
            }
            if(!relevant) {
                Slot itemSlot = new Slot(i);
                itemSlot.setActionDrop();
                slots.add(itemSlot);
            }
        }

        //puts each best item into the correct slot
        for(ItemType itemType : ItemType.values()) {
            for(Slot slot : itemType.getBestItems()) {
                if(slot.slot < 0)
                    continue;
                if(!slot.isInPrefferedSlot()) {
                    for(ItemType itemType2 : ItemType.values()) {
                        Slot slot2 = itemType2.getBestItem(0);
                        if(slot2.slot == slot.getPrefferedSlot()) {
                            slot2.slot = slot.slot;
                        }
                    }
                    slot.setActionMoveToPrefferedSlot();
                    slots.add(slot);
                }
            }
        }

        return slots;
    }

    private static class Slot {
        int slot;
        float value;
        int mode = 1;
        int button = 0;
        int type = 0;

        public Slot(int s) {
            this.slot = s;
        }

        public void setActionDrop() {
            button = 1;
            mode = 4;
        }

        public void setActionMoveToPrefferedSlot() {
            mode = 2;
            button = getPrefferedSlot() - 1;
        }

        public boolean isInPrefferedSlot() {
            return slot == getPrefferedSlot() + 35;
        }

        protected int getPrefferedSlot() {
            return 5;
        }
        private static class Armor extends Slot {
            public Armor(int s) {
                super(s);
                setValues();
            }

            @Override
            public void setActionMoveToPrefferedSlot() {
                // keeps action as shift click
            }

            public void setValues() {
                if (slot < 0)
                    return;

                ItemStack itemStack = mc.thePlayer.openContainer.getSlot(slot).getStack();
                Item is = itemStack.getItem();
                if (!(is instanceof ItemArmor))
                    return;

                ItemArmor as = (ItemArmor) is;
                float pl;
                try {
                    pl = EnchantmentHelper.getEnchantments(itemStack).get(0);
                } catch(Exception e) {
                    pl = 0;
                }
                value = as.damageReduceAmount + (float) (pl * 0.6);
                type = as.armorType;
            }

            @Override
            public boolean isInPrefferedSlot() {
                return slot == 5 + type;
            }
        }

        private static class Sword extends Slot {
            public Sword(int s) {
                super(s);
                if(s < 0)
                    return;
                ItemStack itemStack = mc.thePlayer.openContainer.getSlot(slot).getStack();
                value = ((IWeapon)itemStack.getItem()).getAttackDamage();
                float sharpLevel;
                try {
                    sharpLevel = EnchantmentHelper.getEnchantments(itemStack).get(16);
                } catch(Exception e) {
                    sharpLevel = 0;
                }
                value += (1.25 * sharpLevel);
            }

            @Override
            protected int getPrefferedSlot() {
                return 1;
            }
        }

        private static class Stack extends Slot {
            public Stack(int s) {
                super(s);
                if(s < 0)
                    return;
                value = mc.thePlayer.openContainer.getSlot(s).getStack().stackSize;
            }

            private static class Block extends Stack {

                public Block(int s) {
                    super(s);
                }

                @Override
                protected int getPrefferedSlot() {
                    return 3;
                }
            }

            private static class Projectile extends Stack {

                public Projectile(int s) {
                    super(s);
                }

                @Override
                protected int getPrefferedSlot() {
                    return 2;
                }
            }
        }

        private static class Other extends Slot {

            public Other(int s) {
                super(s);
            }

            @Override
            public void setActionDrop() {
                if(isInPrefferedSlot())
                    slot = -1;

                //shift click is default, so it keeps the action as shift click
            }

            @Override
            public void setActionMoveToPrefferedSlot() {
                //shift click is default so it keeps the action as shift click
            }

            @Override
            public boolean isInPrefferedSlot() {
                return slot > 35 && slot < 45;
            }
        }

    }

    public enum ItemType {
        ARMOR {
            @Override
            public void clear() {
                bestItems = new Slot[]{new Slot.Armor(-1), new Slot.Armor(-1), new Slot.Armor(-1), new Slot.Armor(-1)};
            }

            @Override
            public boolean isItem(Item item) {
                return item instanceof ItemArmor;
            }

            @Override
            public Slot getSlot(int slot) {
                return new Slot.Armor(slot);
            }
        },
        SWORD {
            @Override
            public boolean isItem(Item item) {
                return item instanceof IWeapon;
            }

            @Override
            public Slot getSlot(int slot) {
                return new Slot.Sword(slot);
            }
        },
        BLOCK {
            @Override
            public boolean isItem(Item item) {
                return item instanceof ItemBlock;
            }

            @Override
            public Slot getSlot(int slot) {
                return new Slot.Stack.Block(slot);
            }
        },
        PROJECTILE {
            @Override
            public boolean isItem(Item item) {
                return item instanceof ItemEgg || item instanceof ItemEnderPearl;
            }
            @Override
            public Slot getSlot(int slot) {
                return new Slot.Stack.Projectile(slot);
            }
        },

        OTHER {
            @Override
            public boolean isItem(Item item) {
                return item instanceof ItemAppleGold || item instanceof ItemFood;
            }
            @Override
            public Slot getSlot(int slot) {
                return new Slot.Other(slot);
            }
        };

        protected Slot[] bestItems;

        public void setBestItem(Slot bestItem, int i) {
            this.bestItems[i] = bestItem;
        }

        public Slot getBestItem(int i) {
            return bestItems[i];
        }

        public Slot[] getBestItems() {
            return bestItems;
        }

        public void clear() {
            bestItems = new Slot[]{new Slot(-1)};
        }

        public abstract boolean isItem(Item item);

        public abstract Slot getSlot(int slot);

    }
}
