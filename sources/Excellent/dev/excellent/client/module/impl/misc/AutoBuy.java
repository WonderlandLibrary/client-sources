package dev.excellent.client.module.impl.misc;

import com.mojang.datafixers.util.Pair;
import dev.excellent.api.event.impl.input.KeyboardPressEvent;
import dev.excellent.api.event.impl.input.MouseInputEvent;
import dev.excellent.api.event.impl.other.ScoreBoardEvent;
import dev.excellent.api.event.impl.other.WorldChangeEvent;
import dev.excellent.api.event.impl.other.WorldLoadEvent;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.misc.autobuy.entity.AutoBuyItem;
import dev.excellent.client.module.impl.misc.autobuy.entity.DonateItem;
import dev.excellent.client.module.impl.misc.autobuy.screen.AutoBuyScreen;
import dev.excellent.client.notification.NotificationType;
import dev.excellent.client.script.ScriptConstructor;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.keyboard.Keyboard;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.render.text.TextUtils;
import dev.excellent.impl.util.time.Profiler;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.KeyValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.mode.SubMode;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@ModuleInfo(name = "Auto Buy", description = "Автоматическое скупание предметов с аукциона по вашим ценам.", category = Category.MISC)
public class AutoBuy extends Module {
    public static Singleton<AutoBuy> singleton = Singleton.create(() -> Module.link(AutoBuy.class));
    private final AutoBuyScreen autoBuyScreen = new AutoBuyScreen();
    private final KeyValue screenKey = new KeyValue("Меню", this, Keyboard.KEY_NONE.keyCode);
    private final ModeValue server = new ModeValue("Сервер", this)
            .add(
                    new SubMode("FunTime")
            );
    @Setter
    public boolean active = false;
    private final TimerUtil updateTimer = TimerUtil.create();
    private final Pattern funTimePattern = Pattern.compile("\\[☃] Аукционы \\[\\d+/\\d+]");
    private final Pattern funTimePricePattern = Pattern.compile("\\$(\\d+(?:\\s\\d{3})*(?:\\.\\d{2})?)");

    private final String AH_COMMAND = "/ah";
    private final String DON_ITEM = "don-item";
    private final String UPDATE_SLOT_NAME = "[⟲] Обновить";
    private final int UPDATE_SLOT = 49;
    private int UPDATE_DELAY = 350;
    private final int COMMAND_DELAY = 50;

    private final ScriptConstructor buyScript = ScriptConstructor.create();
    Profiler profiler = new Profiler();

    @Override
    public void toggle() {
        super.toggle();
        setActive(false);
    }

    @Setter
    private int balance = -1;

    public static boolean matchEnchants(Map<Enchantment, Boolean> enchants, ListNBT enchantmentTagList) {
        List<String> enchantList = new ArrayList<>();
        List<String> selectedEnchantments = new ArrayList<>();

        for (int i = 0; i < enchantmentTagList.size(); i++) {
            String stackItemEnchant = enchantmentTagList.getCompound(i).getString("id").replaceAll("minecraft:", "");
            enchantList.add(stackItemEnchant);
        }
        for (Map.Entry<Enchantment, Boolean> ench : enchants.entrySet()) {
            String buyItemEnchant = ench.getKey().getName().replaceAll("enchantment.minecraft.", "");
            if (ench.getValue()) {
                selectedEnchantments.add(buyItemEnchant);
            }
        }
        return TextUtils.containsAll(enchantList, selectedEnchantments);
    }

    private final Listener<Render2DEvent> onRender = event -> {
        buyScript.update();

        if (!isActive() || !(mc.currentScreen instanceof ContainerScreen<?> container)) return;
        String title = container.getTitle().getString();
        if (isAuction(title)) {
            if (mc.player.inventory.mainInventory.stream().noneMatch(stack -> stack.getItem().equals(Items.AIR))) {
                excellent.getNotificationManager().register("Ваш инвентарь полон, освободите его чтобы использовать AutoBuy.", NotificationType.INFO, 3000);
                setActive(false);
                return;
            }

            if (isFunTime()) {
                processAuction(container);
            }
        } else if (title.equals("[☃] Подтверждение покупки") || title.contains("Подозрительная цена!")) {
            processAcceptBuy(container);
        }
    };

    private final Listener<ScoreBoardEvent> onScoreBoard = event -> {
        if (!isActive() || !(mc.currentScreen instanceof ContainerScreen<?>)) return;
        if (!event.getList().isEmpty()) {
            for (Pair<Score, ITextComponent> pair : event.getList()) {
                String component = TextFormatting.getTextWithoutFormattingCodes(pair.getSecond().getString());
                if (component.contains("Монет:")) {
                    String[] splitted = component.split(":");
                    if (splitted[1] != null) {
                        try {
                            int balance = Integer.parseInt(splitted[1].replaceAll(" ", ""));
                            setBalance(balance);
                            break;
                        } catch (Exception ignored) {
                            setBalance(0);
                        }
                    } else {
                        setBalance(0);
                    }
                }
            }
        } else {
            setBalance(0);
        }
    };

    private void processAcceptBuy(ContainerScreen<?> container) {
        if (container.getContainer() instanceof ChestContainer chest) {
            mc.playerController.windowClick(chest.windowId, 0, 0, ClickType.QUICK_MOVE, mc.player);
            updateTimer.reset();
            ChatUtil.sendText(AH_COMMAND);
        }
    }

    private void processAuction(ContainerScreen<?> container) {
        if (container.getContainer() instanceof ChestContainer chest) {
            for (int slot = 0; slot < 44; slot++) {
                ItemStack stack = chest.getSlot(slot).getStack();
                if (!stack.getItem().equals(Items.AIR)) {
                    for (AutoBuyItem autoBuyItem : excellent.getAutoBuyManager().getSelectedItems()) {
                        if (autoBuyItem instanceof DonateItem donate) {
                            if (stack.getItem().equals(donate.getItemStack().getItem()) && stack.hasTag() && stack.getTag().contains(DON_ITEM) && stack.getTag().getString(DON_ITEM).equals(donate.getTag())) {
                                if (stack.getItem().equals(autoBuyItem.getItemStack().getItem()) && (autoBuyItem.getEnchants().isEmpty() || matchEnchants(autoBuyItem.getEnchants(), stack.getEnchantmentTagList()))) {
                                    if (getPrice(stack) != -1 && getPrice(stack) <= autoBuyItem.getPrice() && getBalance() >= (getPrice(stack) * stack.getCount())) {
                                        if (buyScript.isFinished()) {
                                            int finalSlot = slot;
                                            buyScript.cleanup()
                                                    .addStep(1000, () -> {
                                                        mc.playerController.windowClick(chest.windowId, finalSlot, 0, ClickType.QUICK_MOVE, mc.player);
                                                    })
                                                    .addStep(1000, () -> {
                                                    });
                                        }
                                    }
                                }
                            }
                        } else {
                            if (stack.getItem().equals(autoBuyItem.getItemStack().getItem()) && (autoBuyItem.getEnchants().isEmpty() || matchEnchants(autoBuyItem.getEnchants(), stack.getEnchantmentTagList()))) {
                                if (getPrice(stack) != -1 && getPrice(stack) <= autoBuyItem.getPrice() && getBalance() >= (getPrice(stack) * stack.getCount())) {
                                    if (buyScript.isFinished()) {
                                        int finalSlot = slot;
                                        buyScript.cleanup()
                                                .addStep(1000, () -> {
                                                    mc.playerController.windowClick(chest.windowId, finalSlot, 0, ClickType.QUICK_MOVE, mc.player);
                                                })
                                                .addStep(1000, () -> {
                                                });
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (updateTimer.hasReached(UPDATE_DELAY) && buyScript.isFinished() && chest.getSlot(UPDATE_SLOT).getStack().getDisplayName().getString().equals(UPDATE_SLOT_NAME)) {
                mc.playerController.windowClick(chest.windowId, UPDATE_SLOT, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(chest.windowId, UPDATE_SLOT - 1, 0, ClickType.PICKUP, mc.player);
                UPDATE_DELAY = 350;
                updateTimer.reset();
            }
        }

    }

    private final Listener<WorldChangeEvent> onWorldChange = event -> setActive(false);
    private final Listener<WorldLoadEvent> onWorldLoad = event -> setActive(false);
    private final Listener<PacketEvent> onPacket = event -> {
        if (!isActive()) return;

        IPacket<?> packet = event.getPacket();

        if (event.isReceive()) {
            if (isFunTime() && isActive()) {
                if (packet instanceof SPlaySoundEffectPacket wrapper) {
//                    profiler.stop();
//                    ChatUtil.addText("Время выполнения: " + profiler.getLastTimeInMillis());
//                    profiler.reset();
//                    profiler.start();
                    if (wrapper.getSound().getName().getPath().equals("entity.ender_eye.launch")) {
                        if (mc.currentScreen instanceof ContainerScreen<?> container
                                && isAuction(container.getTitle().getString())) {
                            event.cancel();
                        }
                    }
                }
            }
        }
    };

    public int getPrice(ItemStack stack) {
        if (isFunTime()) {
            Matcher matcher = funTimePricePattern.matcher(stack.getOrCreateChildTag("display").getString());
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1).replaceAll("[^\\d.]", "")) / stack.getCount();
            }
        }
        return -1;
    }

    private boolean isFunTime() {
        return server.is("FunTime");
    }


    public boolean isAuction(String title) {
        if (title.toLowerCase().contains("хранилище")) return true;
        if (title.toLowerCase().contains("поиск:")) return true;
        if (isFunTime()) {
            return matchesPattern(title, funTimePattern);
        }
        return false;
    }

    private boolean matchesPattern(String title, Pattern pattern) {
        Matcher matcher = pattern.matcher(title);
        return matcher.find();
    }

    private final Listener<MouseInputEvent> onMouseInput = event -> {
        if (screenKey.getValue() == event.getMouseButton()) {
            openMenu();
        }
    };
    private final Listener<KeyboardPressEvent> onKeyInput = event -> {
        if (screenKey.getValue() == event.getKeyCode()) {
            openMenu();
        }
    };

    private void openMenu() {
        mc.displayScreen(autoBuyScreen);
    }
}