package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventKey;
import fun.ellant.events.EventPacket;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.BindSetting;
import fun.ellant.functions.settings.impl.BooleanSetting;
import fun.ellant.functions.settings.impl.ModeListSetting;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.player.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FunctionRegister(name = "FTHelper", type = Category.PLAYER, desc = "Использует по нажатию клавиши предметы с FunTime")
public class GriefHelper extends Function {
    private final ModeListSetting mode = new ModeListSetting("Тип",
            new BooleanSetting("Использование по бинду", true),
            new BooleanSetting("Закрывать меню", true));

    private final BindSetting disorientationKey = new BindSetting("Кнопка дезорентации", -1)
            .setVisible(() -> mode.getValueByName("Использование по бинду").get());
    private final BindSetting shulkerKey = new BindSetting("Кнопка шалкера", -1)
            .setVisible(() -> mode.getValueByName("Использование по бинду").get());
    private final BindSetting trapKey = new BindSetting("Кнопка трапки", -1)
            .setVisible(() -> mode.getValueByName("Использование по бинду").get());
    private final BindSetting flameKey = new BindSetting("Кнопка смерча", -1)
            .setVisible(() -> mode.getValueByName("Использование по бинду").get());
    private final BindSetting blatantKey = new BindSetting("Кнопка явной пыли", -1)
            .setVisible(() -> mode.getValueByName("Использование по бинду").get());
    private final BindSetting bowKey = new BindSetting("Кнопка арбалета", -1)
            .setVisible(() -> mode.getValueByName("Использование по бинду").get());
    private final BindSetting otrigaKey = new BindSetting("Кнопка отрыги", -1)
            .setVisible(() -> mode.getValueByName("Использование по бинду").get());
    private final BindSetting serkaKey = new BindSetting("Кнопка серки", -1)
            .setVisible(() -> mode.getValueByName("Использование по бинду").get());
    private final BindSetting plastKey = new BindSetting("Кнопка пласта", -1)
            .setVisible(() -> mode.getValueByName("Использование по бинду").get());

    final StopWatch stopWatch = new StopWatch();
    InventoryUtil.Hand handUtil = new InventoryUtil.Hand();
    long delay;
    boolean vzrivpalochkaThrow, oglushenieThrow, vzrivtrapkaThrow, disorientationThrow, trapThrow, flameThrow, blatantThrow, serkaThrow, otrigaThrow, bowThrow, plastThrow, shulkerThrow;

    public GriefHelper() {
        addSettings(mode, disorientationKey, trapKey, flameKey, blatantKey, serkaKey, bowKey, otrigaKey, plastKey, shulkerKey);
    }

    @Subscribe
    private void onKey(EventKey e) {
        if (e.getKey() == disorientationKey.get()) {
            disorientationThrow = true;
        }
        if (e.getKey() == shulkerKey.get()) {
            shulkerThrow = true;
        }
        if (e.getKey() == trapKey.get()) {
            trapThrow = true;
        }
        if (e.getKey() == flameKey.get()) {
            flameThrow = true;
        }
        if (e.getKey() == blatantKey.get()) {
            blatantThrow = true;
        }
        if (e.getKey() == otrigaKey.get()) {
            otrigaThrow = true;
        }
        if (e.getKey() == serkaKey.get()) {
            serkaThrow = true;
        }
        if (e.getKey() == bowKey.get()) {
            bowThrow = true;
        }
        if (e.getKey() == plastKey.get()) {
            plastThrow = true;
        }
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (oglushenieThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            int hbSlot = getItemForName("оглушение", true);
            int invSlot = getItemForName("оглушение", false);

            if (invSlot == -1 && hbSlot == -1) {
                print("Оглушение не найдена!");
                oglushenieThrow = false;
                return;
            }

            if (!mc.player.getCooldownTracker().hasCooldown(Items.NETHER_STAR)) {
                print("Заюзал оглушение!");
                int slot = findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    mc.playerController.pickItem(slot);
                }
            }
            oglushenieThrow = false;
        }
        if (disorientationThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            int hbSlot = getItemForName("дезориентация", true);
            int invSlot = getItemForName("дезориентация", false);

            if (invSlot == -1 && hbSlot == -1) {
                print("Дезориентация не найдена!");
                disorientationThrow = false;
                return;
            }

            if (!mc.player.getCooldownTracker().hasCooldown(Items.ENDER_EYE)) {
                print("Заюзал дезориентацию!");
                int slot = findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    mc.playerController.pickItem(slot);
                }
            }
            disorientationThrow = false;
        }
        if (vzrivtrapkaThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            int hbSlot = getItemForName("взрывная трапка", true);
            int invSlot = getItemForName("взрывная трапка", false);

            if (invSlot == -1 && hbSlot == -1) {
                print("Взрывная трапка не найдена!");
                disorientationThrow = false;
                return;
            }

            if (!mc.player.getCooldownTracker().hasCooldown(Items.PRISMARINE_SHARD)) {
                print("Заюзал взрывную трапку!");
                int slot = findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    mc.playerController.pickItem(slot);
                }
            }
            vzrivtrapkaThrow = false;
        }
        if (shulkerThrow) {
            // САЛАТ СПАСАЙ
            int hbSlot = getItemForName("ящик", true); // мега поиск ящика
            int invSlot = getItemForName("ящик", false); // мега поиск ящика


            if (invSlot == -1 && hbSlot == -1) {
                print("Шалкер не найден!");
                trapThrow = false;
                return;
            }

            if (!mc.player.getCooldownTracker().hasCooldown(Items.SHULKER_BOX)) {
                print("Открыл шалкер!");
                int old = mc.player.inventory.currentItem;

                int slot = findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    mc.playerController.pickItem(slot);
                }
                if (InventoryUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                    mc.player.inventory.currentItem = old;
                }
            }
            shulkerThrow = false;
        }

        if (trapThrow) {
            int hbSlot = getItemForName("трапка", true);
            int invSlot = getItemForName("трапка", false);


            if (invSlot == -1 && hbSlot == -1) {
                print("Трапка не найдена");
                trapThrow = false;
                return;
            }

            if (!mc.player.getCooldownTracker().hasCooldown(Items.NETHERITE_SCRAP)) {
                print("Заюзал трапку!");
                int old = mc.player.inventory.currentItem;

                int slot = findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    mc.playerController.pickItem(slot);
                }
                if (InventoryUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                    mc.player.inventory.currentItem = old;
                }
            }
            trapThrow = false;
        }
        if (flameThrow) {
            int hbSlot = getItemForName("огненный", true);
            int invSlot = getItemForName("огненный", false);

            if (invSlot == -1 && hbSlot == -1) {
                print("Огненный смерч не найден");
                flameThrow = false;
                return;
            }

            if (!mc.player.getCooldownTracker().hasCooldown(Items.FIRE_CHARGE)) {
                print("Заюзал огненный смерч!");
                int old = mc.player.inventory.currentItem;

                int slot = findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    mc.playerController.pickItem(slot);
                }
                if (InventoryUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                    mc.player.inventory.currentItem = old;
                }
            }
            flameThrow = false;
        }
        if (bowThrow) {
            int hbSlot = getItemForName("арбалет", true);
            int invSlot = getItemForName("арбалет", false);

            if (invSlot == -1 && hbSlot == -1) {
                print("Арбалет не найден");
                bowThrow = false;
                return;
            }

            if (!mc.player.getCooldownTracker().hasCooldown(Items.CROSSBOW)) {
                print("Заюзал арбалет!");
                int old = mc.player.inventory.currentItem;

                int slot = findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    mc.playerController.pickItem(slot);
                }
                if (InventoryUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                    mc.player.inventory.currentItem = old;
                }
            }
            bowThrow = false;
        }
        if (serkaThrow) {
            int hbSlot = getItemForName("серная", true);
            int invSlot = getItemForName("серная", false);

            if (invSlot == -1 && hbSlot == -1) {
                print("Серка не найдена");
                serkaThrow = false;
                return;
            }

            if (!mc.player.getCooldownTracker().hasCooldown(Items.SPLASH_POTION)) {
                print("Заюзал серку!");
                int old = mc.player.inventory.currentItem;

                int slot = findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    mc.playerController.pickItem(slot);
                }
                if (InventoryUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                    mc.player.inventory.currentItem = old;
                }
            }
            serkaThrow = false;
        }
        if (otrigaThrow) {
            int hbSlot = getItemForName("отрыжки", true);
            int invSlot = getItemForName("отрыжки", false);

            if (invSlot == -1 && hbSlot == -1) {
                print("Отрыга не найдена");
                otrigaThrow = false;
                return;
            }

            if (!mc.player.getCooldownTracker().hasCooldown(Items.SPLASH_POTION)) {
                print("Заюзал отрыгу!");
                int old = mc.player.inventory.currentItem;

                int slot = findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    mc.playerController.pickItem(slot);
                }
                if (InventoryUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                    mc.player.inventory.currentItem = old;
                }
            }
            otrigaThrow = false;
        }
        if (plastThrow) {
            int hbSlot = getItemForName("пласт", true);
            int invSlot = getItemForName("пласт", false);

            if (invSlot == -1 && hbSlot == -1) {
                print("Пласт не найден");
                plastThrow = false;
                return;
            }

            if (!mc.player.getCooldownTracker().hasCooldown(Items.DRIED_KELP)) {
                print("Заюзал пласт!");
                int old = mc.player.inventory.currentItem;

                int slot = findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    mc.playerController.pickItem(slot);
                }
                if (InventoryUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                    mc.player.inventory.currentItem = old;
                }
            }
            plastThrow = false;
        }
        if (blatantThrow) {
            int hbSlot = getItemForName("явная", true);
            int invSlot = getItemForName("явная", false);

            if (invSlot == -1 && hbSlot == -1) {
                print("Явная пыль не найдена");
                blatantThrow = false;
                return;
            }

            if (!mc.player.getCooldownTracker().hasCooldown(Items.TNT)) {
                print("Заюзал явную пыль!");
                int old = mc.player.inventory.currentItem;

                int slot = findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    mc.playerController.pickItem(slot);
                }
                if (InventoryUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                    mc.player.inventory.currentItem = old;
                }
            }
            blatantThrow = false;
        }
        this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
    }


    private int findAndTrowItem(int hbSlot, int invSlot) {
        if (hbSlot != -1) {
            this.handUtil.setOriginalSlot(mc.player.inventory.currentItem);
            mc.player.connection.sendPacket(new CHeldItemChangePacket(hbSlot));
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return hbSlot;
        }
        if (invSlot != -1) {
            handUtil.setOriginalSlot(mc.player.inventory.currentItem);
            mc.playerController.pickItem(invSlot);
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return invSlot;
        }
        return -1;
    }

    @Override
    public void onDisable() {
        disorientationThrow = false;
        trapThrow = false;
        flameThrow = false;
        blatantThrow = false;
        plastThrow = false;
        otrigaThrow = false;
        serkaThrow = false;
        bowThrow = false;
        delay = 0;
        super.onDisable();
    }

    private int getItemForName(String name, boolean inHotBar) {
        int firstSlot = inHotBar ? 0 : 9;
        int lastSlot = inHotBar ? 9 : 36;
        for (int i = firstSlot; i < lastSlot; i++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);

            if (itemStack.getItem() instanceof AirItem) {
                continue;
            }

            String displayName = TextFormatting.getTextWithoutFormattingCodes(itemStack.getDisplayName().getString());
            if (displayName != null && displayName.toLowerCase().contains(name)) {
                return i;
            }
        }
        return -1;
    }
}