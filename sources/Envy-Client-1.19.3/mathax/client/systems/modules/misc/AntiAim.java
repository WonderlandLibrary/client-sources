package mathax.client.systems.modules.misc;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.combat.BedAura;
import mathax.client.systems.modules.player.EXPThrower;
import mathax.client.utils.Jebus.Interactions;
import mathax.client.utils.player.Rotations;
import net.minecraft.item.BowItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.Items;

public class AntiAim extends Module {
    private final SettingGroup sgDefault = settings.getDefaultGroup();

    private final Setting<Mode> antiDesync = sgDefault.add(new EnumSetting.Builder<Mode>().name("anti-desync").description("Stops spinning on some triggers.").defaultValue(Mode.All).build());

    private final Setting<Boolean> yaw = sgDefault.add(new BoolSetting.Builder().name("yaw").description("Spin around.").defaultValue(true).build());
    private final Setting<Integer> ySpeed = sgDefault.add(new IntSetting.Builder().name("yaw-speed").description("The speed at which you rotate.").defaultValue(5).range(1, 100).visible(yaw::get).build());
    private final Setting<Boolean> pitch = sgDefault.add(new BoolSetting.Builder().name("pitch").description("Spin around.").defaultValue(false).build());
    private final Setting<Integer> pSpeed = sgDefault.add(new IntSetting.Builder().name("speed").description("The speed at which you rotate.").defaultValue(5).range(1, 100).visible(pitch::get).build());

    public AntiAim() {
        super(Categories.Misc, Items.CARVED_PUMPKIN, "anti-aim", "Makes your head spin around");
    }

    private short count = 0;
    private short yCount = 0;
    private short pCount = 0;

    @Override
    public boolean onActivate() {
        count = 0;
        return false;
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        assert mc.player != null;

        if (antiDesync.get() != Mode.None && (shouldStop() || (antiDesync.get() == Mode.All && Interactions.isInElytra()))) return;

        yCount += ySpeed.get();
        if (yCount > 180) yCount = -180;

        if (pitch.get()) {
            count++;

            if (count <= pSpeed.get()) pCount = 90;
            if (count > pSpeed.get()) pCount = -90;
            if (count >= pSpeed.get() + pSpeed.get()) count = 0;
        }

        Rotations.rotate(yaw.get() ? yCount : mc.player.getYaw(), yaw.get() ? pCount : mc.player.getPitch());
    }

    private boolean shouldStop() {
        return Modules.get().isActive(EXPThrower.class) ||
            Modules.get().isActive(BedAura.class) ||
            mc.player.getMainHandStack().getItem() instanceof ExperienceBottleItem ||
            mc.player.getOffHandStack().getItem() instanceof ExperienceBottleItem ||
            mc.player.getMainHandStack().getItem() instanceof EnderPearlItem ||
            mc.player.getOffHandStack().getItem() instanceof EnderPearlItem ||
            mc.player.getMainHandStack().getItem() instanceof ExperienceBottleItem ||
            mc.player.getOffHandStack().getItem() instanceof ExperienceBottleItem ||
            mc.player.getMainHandStack().getItem() instanceof BowItem ||
            mc.player.getOffHandStack().getItem() instanceof BowItem;
    }

    public enum Mode {
        All, ExceptElytra, None
    }
}
