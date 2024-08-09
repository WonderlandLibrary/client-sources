package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.IntSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class AutoClip extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> horizontalRange = sgGeneral.add(new IntSetting.Builder()
        .name("range")
        .description("Horizontal auto clip range.")
        .defaultValue(10)
        .sliderRange(2, 10)
        .build()
    );

    public AutoClip() {
        super(Categories.Movement, Items.SPRUCE_BOAT, "auto-clip", "Automatically clips through blocks if you collide");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!mc.player.horizontalCollision) return;
        int tries = 0;
        for (int i = 0; i < horizontalRange.get(); i++) {
            tries++;
            Vec3d forward = Vec3d.fromPolar(0, mc.player.getYaw()).normalize();

            Vec3d pos = new Vec3d(mc.player.getX() + forward.x * tries, mc.player.getY(), mc.player.getZ() + forward.z * tries);
            Block blockFront = checkBlock(mc.player.getX() + forward.x * 1, mc.player.getY(), mc.player.getZ() + forward.z * 1);
            Block block1 = checkBlock(pos.getX(), mc.player.getY() + 1, pos.getZ());
            Block block = checkBlock(pos.getX(), mc.player.getY(), pos.getZ());

            if (block == Blocks.AIR && block1 == Blocks.AIR && blockFront != Blocks.AIR) {
                mc.player.setPosition(pos);
                break;
            }
        }
    }
    private Block checkBlock(double x, double y, double z) {
        Block block = mc.world.getBlockState(new BlockPos(new Vec3i((int) x, (int) y, (int) z))).getBlock();
        return block;
    }
}
