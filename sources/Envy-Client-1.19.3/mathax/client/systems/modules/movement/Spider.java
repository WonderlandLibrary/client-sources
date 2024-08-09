package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Set;

public class Spider extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
        .name("climb-speed")
        .description("The speed you go up blocks.")
        .defaultValue(0.2)
        .min(0.0)
        .sliderRange(0.0, 1.0)
        .build()
    );

    private final Setting<Boolean> step = sgGeneral.add(new BoolSetting.Builder()
        .name("step")
        .description("Whether or not to use step.")
        .defaultValue(false)
        .build()
    );


    public Spider() {
        super(Categories.Movement, Items.SPIDER_EYE, "spider", "Allows you to climb walls like a spider.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!mc.player.horizontalCollision) return;
        if (!step.get()) {
            Vec3d velocity = mc.player.getVelocity();
            if (velocity.y >= 0.2) return;

            mc.player.setVelocity(velocity.x, speed.get(), velocity.z);
        }
        if (step.get()) {
            BlockPos playerPos = mc.player.getBlockPos();
            BlockPos blockPosInFront = playerPos.offset(mc.player.getHorizontalFacing());
            if (!mc.player.horizontalCollision) return;
            if (mc.world.getBlockState(blockPosInFront).getBlock() != Blocks.AIR) {
                BlockPos blockAbovePos = blockPosInFront.up();
                if (mc.world.getBlockState(blockAbovePos).getBlock() == Blocks.AIR) {
                    Vec3d velocity = mc.player.getVelocity();
                    if (velocity.y >= 0.2) return;

                    mc.player.setVelocity(velocity.x, speed.get(), velocity.z);
                }
            }
        }
    }
}
