package mathax.client.systems.modules.chat;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.EnumSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.config.Config;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.misc.NotificationMode;
import mathax.client.utils.render.ToastSystem;
import mathax.client.utils.render.color.Color;
import net.minecraft.item.Items;

public class StayHydrated extends Module {
    private static final int BLUE = Color.fromRGBA(0, 128, 255, 255);

    private boolean menuCounting, notifyOnJoin, count;
    private int ticks = 0;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    //General

    private final Setting<NotificationMode> mode = sgGeneral.add(new EnumSetting.Builder<NotificationMode>()
        .name("mode")
        .description("Determines how to notify you when its time to drink.")
        .defaultValue(NotificationMode.Both)
        .build()
    );

    private final Setting<Double> delay = sgGeneral.add(new DoubleSetting.Builder()
        .name("delay")
        .description("Delay between drinking notifications in minutes.")
        .defaultValue(120)
        .min(1)
        .sliderRange(5, 180)
        .build()
    );

    public StayHydrated() {
        super(Categories.Chat, Items.WATER_BUCKET, "stay-hydrated", "Notifies you when its time to drink. #StayHydrated", true);
    }

    @EventHandler
    public boolean onActivate() {
        ticks = 0;
        return false;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (count) ticks++;

        if (notifyOnJoin && mc.world != null) {
            notifyOnJoin = false;
            ticks = 0;
            sendNotification();
            return;
        }

        if (mc.world == null) {
            menuCounting = true;
            if (ticks == (delay.get() * 20) * 60) {
                notifyOnJoin = true;
                count = false;
            }

            return;
        } else {
            count = true;
            menuCounting = false;
        }

        if (ticks > (delay.get() * 20) * 60) {
            sendNotification();
            ticks = 0;
        }
    }

    private void sendNotification() {
        switch (mode.get()) {
            case Chat -> info("Its time to drink! #StayHydrated");
            case Toast -> mc.getToastManager().add(new ToastSystem(Items.WATER_BUCKET, BLUE, "Stay Hydrated", null, "Its time to drink!", Config.get().toastDuration.get()));
            case Both -> {
                info("Its time to drink! #StayHydrated");
                mc.getToastManager().add(new ToastSystem(Items.WATER_BUCKET, BLUE, "Stay Hydrated", null, "Its time to drink!", Config.get().toastDuration.get()));
            }
        }
    }
}
