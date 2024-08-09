package xyz.mathax.mathaxclient.systems.modules.movement;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.mixin.AbstractBlockAccessor;
import xyz.mathax.mathaxclient.mixininterface.IVec3d;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;

public class Anchor extends Module {
    private final BlockPos.Mutable blockPos = new BlockPos.Mutable();

    private boolean wasInHole;
    private boolean foundHole;
    private int holeX, holeZ;

    public boolean cancelJump;

    public boolean controlMovement;
    public double deltaX, deltaZ;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Integer> maxHeightSetting = generalSettings.add(new IntSetting.Builder()
            .name("Max height")
            .description("The maximum height Anchor will work at.")
            .defaultValue(10)
            .range(0, 255)
            .sliderRange(0, 20)
            .build()
    );

    private final Setting<Integer> minPitchSetting = generalSettings.add(new IntSetting.Builder()
            .name("Min pitch")
            .description("The minimum pitch at which anchor will work.")
            .defaultValue(0)
            .range(-90, 90)
            .sliderRange(-90, 90)
            .build()
    );

    private final Setting<Boolean> cancelMoveSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Cancel jump in hole")
            .description("Prevents you from jumping when Anchor is active and Min Pitch is met.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> pullSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Pull")
            .description("The pull strength of Anchor.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Double> pullSpeedSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Pull speed")
            .description("How fast to pull towards the hole in blocks per second.")
            .defaultValue(0.3)
            .min(0)
            .sliderRange(0, 5)
            .build()
    );

    public Anchor(Category category) {
        super(category, "Anchor", "Helps you get into holes by stopping your movement completely over a hole.");
    }

    @Override
    public void onEnable() {
        wasInHole = false;
        holeX = holeZ = 0;
    }

    @EventHandler
    private void onPreTick(TickEvent.Pre event) {
        cancelJump = foundHole && cancelMoveSetting.get() && mc.player.getPitch() >= minPitchSetting.get();
    }

    @EventHandler
    private void onPostTick(TickEvent.Post event) {
        controlMovement = false;

        int x = MathHelper.floor(mc.player.getX());
        int y = MathHelper.floor(mc.player.getY());
        int z = MathHelper.floor(mc.player.getZ());
        if (isHole(x, y, z)) {
            wasInHole = true;
            holeX = x;
            holeZ = z;
            return;
        }

        if (wasInHole && holeX == x && holeZ == z) {
            return;
        } else if (wasInHole) {
            wasInHole = false;
        }

        if (mc.player.getPitch() < minPitchSetting.get()) {
            return;
        }

        foundHole = false;
        double holeX = 0;
        double holeZ = 0;
        for (int i = 0; i < maxHeightSetting.get(); i++) {
            y--;
            if (y <= 0 || !isAir(x, y, z)) {
                break;
            }

            if (isHole(x, y, z)) {
                foundHole = true;
                holeX = x + 0.5;
                holeZ = z + 0.5;
                break;
            }
        }

        if (foundHole) {
            controlMovement = true;
            deltaX = Utils.clamp(holeX - mc.player.getX(), -0.05, 0.05);
            deltaZ = Utils.clamp(holeZ - mc.player.getZ(), -0.05, 0.05);
            ((IVec3d) mc.player.getVelocity()).set(deltaX, mc.player.getVelocity().y - (pullSetting.get() ? pullSpeedSetting.get() : 0), deltaZ);
        }
    }

    private boolean isHole(int x, int y, int z) {
        return isHoleBlock(x, y - 1, z) && isHoleBlock(x + 1, y, z) && isHoleBlock(x - 1, y, z) && isHoleBlock(x, y, z + 1) && isHoleBlock(x, y, z - 1);
    }

    private boolean isHoleBlock(int x, int y, int z) {
        blockPos.set(x, y, z);
        Block block = mc.world.getBlockState(blockPos).getBlock();
        return block == Blocks.BEDROCK || block == Blocks.OBSIDIAN || block == Blocks.CRYING_OBSIDIAN;
    }

    private boolean isAir(int x, int y, int z) {
        blockPos.set(x, y, z);
        return !((AbstractBlockAccessor) mc.world.getBlockState(blockPos).getBlock()).isCollidable();
    }
}