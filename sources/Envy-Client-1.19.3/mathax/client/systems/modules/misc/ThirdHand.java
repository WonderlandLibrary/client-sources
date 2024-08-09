package mathax.client.systems.modules.misc;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.mathax.MouseButtonEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.ItemListSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Utils;
import mathax.client.utils.misc.input.KeyAction;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.InvUtils;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.item.Items.OBSIDIAN;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;

public class ThirdHand extends Module {

    //region settings
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<Item>> useditem = sgGeneral.add(new ItemListSetting.Builder()
        .name("used item")
        .description("when you try to use this item it will use other item instead")
        .defaultValue(new ArrayList<>())
        .build()
    );

    private final Setting<Boolean> notify = sgGeneral.add(new BoolSetting.Builder()
        .name("notify")
        .description("Notifies you when you do not have the specified item in your hotbar.")
        .defaultValue(true)
        .build()
    );
    //endregion

    public ThirdHand() {
        super(Categories.Misc, Items.BEDROCK, "Third-Hand", "places obi instead of other items.");
    }

    private int swich = -1;

    @EventHandler
    private void onMouseButton(MouseButtonEvent event) {
        if (event.action != KeyAction.Press || event.button != GLFW_MOUSE_BUTTON_RIGHT || mc.currentScreen != null) return;
        if (mc.player == null || mc.world == null || mc.interactionManager == null || !useditem.get().contains(mc.player.getMainHandStack().getItem())) return;
        FindItemResult result = InvUtils.findInHotbar(OBSIDIAN);

        if (!result.found()) {
            if (notify.get()) warning("Unable to find specified item.");
            return;
        }
        event.cancel();


        swich = mc.player.getInventory().selectedSlot;

        InvUtils.swap(result.slot(), false);

        Utils.rightClick();

    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (swich != -1) {
            InvUtils.swap(swich, false);
            swich = -1;
        }
    }
}
