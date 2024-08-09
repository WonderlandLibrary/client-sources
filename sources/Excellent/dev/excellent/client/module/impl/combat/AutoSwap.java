package dev.excellent.client.module.impl.combat;

import dev.excellent.api.event.impl.input.KeyboardPressEvent;
import dev.excellent.api.event.impl.input.MouseInputEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.script.ScriptConstructor;
import dev.excellent.impl.util.keyboard.Keyboard;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.util.tuples.Pair;
import dev.excellent.impl.value.impl.KeyValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

@ModuleInfo(name = "Auto Swap", description = "Переключает предметы в левой руке с использованием заданной клавиши", category = Category.COMBAT)
public class AutoSwap extends Module {

    private final KeyValue key = new KeyValue("Свап", this, Keyboard.KEY_NONE.keyCode);
    private final ModeValue mode = new ModeValue("Режим", this)
            .add(
                    new SubMode("Шар & Щит"),
                    new SubMode("Тотем & Шар"),
                    new SubMode("Щит & Тотем"),
                    new SubMode("Шар & Гепл"),
                    new SubMode("Тотем & Гепл"),
                    new SubMode("Щит & Гепл")
            );
    private final ScriptConstructor scriptConstructor = ScriptConstructor.create();

    private Pair<Item, Item> getSwapPair() {
        if (mode.is("Шар & Щит")) {
            return Pair.of(Items.PLAYER_HEAD, Items.SHIELD);
        }
        if (mode.is("Тотем & Шар")) {
            return Pair.of(Items.TOTEM_OF_UNDYING, Items.PLAYER_HEAD);
        }
        if (mode.is("Щит & Тотем")) {
            return Pair.of(Items.SHIELD, Items.TOTEM_OF_UNDYING);
        }
        if (mode.is("Шар & Гепл")) {
            return Pair.of(Items.PLAYER_HEAD, Items.GOLDEN_APPLE);
        }
        if (mode.is("Тотем & Гепл")) {
            return Pair.of(Items.TOTEM_OF_UNDYING, Items.GOLDEN_APPLE);
        }
        if (mode.is("Щит & Гепл")) {
            return Pair.of(Items.SHIELD, Items.GOLDEN_APPLE);
        }
        return Pair.of(Items.AIR, Items.AIR);
    }

    private final Listener<MouseInputEvent> onMouseInput = event -> {
        if (key.getValue() == event.getMouseButton()) {
            swap();
        }
    };
    private final Listener<KeyboardPressEvent> onKeyboardInput = event -> {
        if (key.getValue() == event.getKeyCode()) {
            swap();
        }
    };
    private final Listener<UpdateEvent> onUpdate = event -> scriptConstructor.update();

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }

    private void swap() {
        scriptConstructor.cleanup().addStep(0, () -> {
            boolean handEmpty = (mc.player.getHeldItemOffhand().getItem() instanceof AirItem);

            int firstItemSlot = PlayerUtil.findItemSlot(getSwapPair().getFirst());
            int secondItemSlot = PlayerUtil.findItemSlot(getSwapPair().getSecond());

            int toSwapSlot = 45;
            if (firstItemSlot != -1 || secondItemSlot != -1) {
                if (secondItemSlot == -1) {
                    mc.playerController.windowClick(0, firstItemSlot, 0, ClickType.PICKUP, mc.player);
                } else {
                    mc.playerController.windowClick(0, secondItemSlot, 0, ClickType.PICKUP, mc.player);
                }
                mc.playerController.windowClick(0, toSwapSlot, 0, ClickType.PICKUP, mc.player);
                if (!handEmpty) {
                    if (secondItemSlot == -1) {
                        mc.playerController.windowClick(0, firstItemSlot, 0, ClickType.PICKUP, mc.player);
                    } else {
                        mc.playerController.windowClick(0, secondItemSlot, 0, ClickType.PICKUP, mc.player);
                    }
                }
            }
        });
    }

}
