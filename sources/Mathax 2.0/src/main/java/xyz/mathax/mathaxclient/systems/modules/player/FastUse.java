package xyz.mathax.mathaxclient.systems.modules.player;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.mixin.MinecraftClientAccessor;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

import java.util.List;

public class FastUse extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Mode> modeSetting = generalSettings.add(new EnumSetting.Builder<Mode>()
            .name("Mode")
            .description("Which items to fast use.")
            .defaultValue(Mode.All)
            .build()
    );

    private final Setting<List<Item>> itemsSetting = generalSettings.add(new ItemListSetting.Builder()
            .name("Items")
            .description("Which items should fast place work on in \"Some\" mode.")
            .visible(() -> modeSetting.get() == Mode.Some)
            .build()
    );

    private final Setting<Boolean> blocksSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Blocks")
            .description("Fast-places blocks if the mode is \"Some\" mode.")
            .visible(() -> modeSetting.get() == Mode.Some)
            .defaultValue(false)
            .build()
    );

    private final Setting<Integer> cooldownSetting = generalSettings.add(new IntSetting.Builder()
            .name("Cooldown")
            .description("Fast Use cooldown in ticks.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 4)
            .build()
    );

    public FastUse(Category category) {
        super(category, "Fast Use", "Allows you to use items at very high speeds.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        int cooldownTicks = Math.min(((MinecraftClientAccessor) mc).getItemUseCooldown(), cooldownSetting.get());
        if (modeSetting.get() == Mode.All || shouldWorkSome()) {
            ((MinecraftClientAccessor) mc).setItemUseCooldown(cooldownTicks);
        }
    }

    private boolean shouldWorkSome() {
        if (shouldWorkSome(mc.player.getMainHandStack())) {
            return true;
        }

        return shouldWorkSome(mc.player.getOffHandStack());
    }

    private boolean shouldWorkSome(ItemStack itemStack) {
        return (blocksSetting.get() && itemStack.getItem() instanceof BlockItem) || itemsSetting.get().contains(itemStack.getItem());
    }

    public enum Mode {
        All("All"),
        Some("Some");

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
