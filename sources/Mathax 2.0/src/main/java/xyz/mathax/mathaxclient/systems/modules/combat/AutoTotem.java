package xyz.mathax.mathaxclient.systems.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.eventbus.EventPriority;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;

public class AutoTotem extends Module {
    public boolean locked;

    private int totems, ticks;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Mode> modeSetting = generalSettings.add(new EnumSetting.Builder<Mode>()
            .name("Mode")
            .description("Determines when to hold a totem, strict will always hold.")
            .defaultValue(Mode.Smart)
            .build()
    );

    private final Setting<Integer> delaySetting = generalSettings.add(new IntSetting.Builder()
            .name("Delay")
            .description("The ticks between slot movements.")
            .defaultValue(0)
            .min(0)
            .range(0, 5)
            .build()
    );

    private final Setting<Integer> healthSetting = generalSettings.add(new IntSetting.Builder()
            .name("health")
            .description("The health to hold a totem at.")
            .defaultValue(10)
            .range(0, 36)
            .sliderRange(0, 36)
            .visible(() -> modeSetting.get() == Mode.Smart)
            .build()
    );

    private final Setting<Boolean> elytraSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Elytra")
            .description("Will always hold a totem when flying with elytra.")
            .defaultValue(true)
            .visible(() -> modeSetting.get() == Mode.Smart)
            .build()
    );

    private final Setting<Boolean> fallSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Fall")
            .description("Will hold a totem when fall damage could kill you.")
            .defaultValue(true)
            .visible(() -> modeSetting.get() == Mode.Smart)
            .build()
    );

    private final Setting<Boolean> explosionSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Explosion")
            .description("Will hold a totem when explosion damage could kill you.")
            .defaultValue(true)
            .visible(() -> modeSetting.get() == Mode.Smart)
            .build()
    );

    public AutoTotem(Category category) {
        super(category, "Auto Totem", "Automatically equips a totem in your offhand.");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onTick(TickEvent.Pre event) {
        FindItemResult result = InvUtils.find(Items.TOTEM_OF_UNDYING);
        totems = result.count();

        if (totems <= 0) {
            locked = false;
        } else if (ticks >= delaySetting.get()) {
            boolean low = mc.player.getHealth() + mc.player.getAbsorptionAmount() - PlayerUtils.possibleHealthReductions(explosionSetting.get(), fallSetting.get()) <= healthSetting.get();
            boolean ely = elytraSetting.get() && mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA && mc.player.isFallFlying();
            locked = modeSetting.get() == Mode.Strict || (modeSetting.get() == Mode.Smart && (low || ely));
            if (locked && mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
                InvUtils.move().from(result.slot()).toOffhand();
            }

            ticks = 0;

            return;
        }

        ticks++;
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onReceivePacket(PacketEvent.Receive event) {
        if (!(event.packet instanceof EntityStatusS2CPacket packet)) {
            return;
        }

        if (packet.getStatus() != 35) {
            return;
        }

        Entity entity = packet.getEntity(mc.world);
        if (entity == null || !(entity.equals(mc.player))) {
            return;
        }

        ticks = 0;
    }

    public boolean isLocked() {
        return isEnabled() && locked;
    }

    @Override
    public String getInfoString() {
        return String.valueOf(totems);
    }

    public enum Mode {
        Smart,
        Strict
    }
}