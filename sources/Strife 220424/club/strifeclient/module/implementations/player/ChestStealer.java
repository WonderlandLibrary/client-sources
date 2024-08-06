package club.strifeclient.module.implementations.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.event.implementations.rendering.Render2DEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.setting.implementations.ModeSetting;
import club.strifeclient.util.player.InventoryUtil;
import club.strifeclient.util.system.Stopwatch;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.lwjglx.input.Mouse;

import java.util.function.Supplier;

@ModuleInfo(name = "ChestStealer", description = "Automatically steal items from chests.", aliases = "Stealer", category = Category.PLAYER)
public final class ChestStealer extends Module {

    private final ModeSetting<ScreenMode> screenModeSetting = new ModeSetting<>("Screen Mode", ScreenMode.GUI);
    private final DoubleSetting delaySetting = new DoubleSetting("Delay", 20, 0, 1000, 10);
    private final DoubleSetting startDelaySetting = new DoubleSetting("Start Delay", 0, 0, 1000, 10);
    private final DoubleSetting closeDelaySetting = new DoubleSetting("Close Delay", 20, 0, 1000, 10);
    private final BooleanSetting closeSetting = new BooleanSetting("Close", true);
    private final BooleanSetting checkTitleSetting = new BooleanSetting("Check Title", true);
    private final BooleanSetting smartSetting = new BooleanSetting("Smart", true);

    private final Stopwatch delayTimer = new Stopwatch();
    private final Stopwatch openTimer = new Stopwatch();
    private final Stopwatch closeTimer = new Stopwatch();

    public ChestStealer() {
        closeDelaySetting.setDependency(closeSetting::getValue);
    }

    @Override
    protected void onEnable() {
        delayTimer.reset();
        openTimer.reset();
        closeTimer.reset();
        super.onEnable();
    }

    @EventHandler
    private final Listener<Render2DEvent> render2DEventListener = e -> {
        if(mc.thePlayer.openContainer instanceof ContainerChest) {
            if (screenModeSetting.getValue() == ScreenMode.ROTATE || screenModeSetting.getValue() == ScreenMode.SILENT) {
                if(!Mouse.isGrabbed()) {
                    mc.inGameHasFocus = true;
                    mc.mouseHelper.grabMouseCursor();
                }
            }
        }
    };

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        if (mc.thePlayer.openContainer instanceof ContainerChest) {
            final ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
            final IInventory lower = chest.getLowerChestInventory();
            final boolean shouldBeOpen = (!InventoryUtil.isChestEmpty(lower) || smartSetting.getValue()) &&
                    !InventoryUtil.isInventoryFull();

            if (closeSetting.getValue() && closeTimer.hasElapsed(closeDelaySetting.getLong()) && !shouldBeOpen)
                mc.thePlayer.closeScreen();
            if(!shouldBeOpen && !openTimer.hasElapsed(startDelaySetting.getLong()))
                return;

            if (checkName(lower) || !checkTitleSetting.getValue()) {
               for (int i = 0; i < lower.getSizeInventory(); i++) {
                   if (delayTimer.hasElapsed(delaySetting.getLong())) {
                       final ItemStack itemStack = lower.getStackInSlot(i);
                       if (itemStack != null) {
                           InventoryUtil.windowClick(chest.windowId, i, 0, InventoryUtil.ClickType.SHIFT_CLICK);
                           delayTimer.reset();
                       }
                   }
               }
            }
        }
    };

    private boolean checkName(IInventory lower) {
        final String lowerDisplayName = lower.getDisplayName().getFormattedText();
        return lowerDisplayName.contains("Chest") || lowerDisplayName.contains(I18n.format("container.chest")) || lowerDisplayName.contains("LOW");
    }

    @Override
    public Supplier<Object> getSuffix() {
        return delaySetting::getInt;
    }

    public enum ScreenMode implements SerializableEnum {
        GUI("GUI"), ROTATE("Rotate"), SILENT("Silent");

        final String name;

        ScreenMode(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
