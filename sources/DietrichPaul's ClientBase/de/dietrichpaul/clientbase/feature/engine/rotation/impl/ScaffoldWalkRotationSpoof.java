package de.dietrichpaul.clientbase.feature.engine.rotation.impl;

import de.dietrichpaul.clientbase.feature.engine.rotation.RotationSpoof;
import de.dietrichpaul.clientbase.feature.hack.world.ScaffoldWalkHack;
import de.dietrichpaul.clientbase.property.impl.IntProperty;
import de.dietrichpaul.clientbase.util.math.MathUtil;
import de.dietrichpaul.clientbase.util.minecraft.BlockUtil;
import de.dietrichpaul.clientbase.util.minecraft.rtx.Raytrace;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class ScaffoldWalkRotationSpoof extends RotationSpoof {

    private ScaffoldWalkHack parent;

    private BlockPos blockPos;
    private Direction face;

    private boolean hasTarget;
    private int keepRotationLength;

    private IntProperty keepRotationProperty = new IntProperty("KeepRotations", 0, 0, 20);

    public ScaffoldWalkRotationSpoof(ScaffoldWalkHack hack) {
        super(hack);
        hack.addProperty(keepRotationProperty);
        parent = hack;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public Direction getFace() {
        return face;
    }

    @Override
    public void tick() {
        if (keepRotationLength > 0)
            keepRotationLength--;
    }

    @Override
    public boolean pickTarget() {
        hasTarget = false;

        BlockPos below = BlockPos.ofFloored(mc.player.getPos()).down();
        if (!BlockUtil.getBlockState(below).isAir()) return keepRotationLength != 0;

        blockPos = null;
        face = null;

        Vec3d camera = mc.player.getCameraPosVec(1.0F);
        double closest = Double.MAX_VALUE;

        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 0; y++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos start = below.add(x, y, z);
                    if (BlockUtil.getBlockState(start).isAir())
                        continue;


                    for (Direction direction : Direction.values()) {
                        BlockPos offset = start.offset(direction);

                        if (camera.squaredDistanceTo(Vec3d.ofCenter(offset)) >= camera.squaredDistanceTo(Vec3d.ofCenter(start)))
                            continue;

                        if (!BlockUtil.getBlockState(offset).isAir())
                            continue;

                        if (offset.getY() + 1 > mc.player.getY())
                            continue;

                        double dist = Vec3d.ofCenter(offset).distanceTo(Vec3d.ofCenter(below));
                        if (dist < closest) {
                            closest = dist;
                            blockPos = start;
                            face = direction;
                        }
                    }
                }
            }
        }



        hasTarget = blockPos != null;
        if (hasTarget)
            keepRotationLength = keepRotationProperty.getValue();
        return hasTarget || keepRotationLength != 0;
    }

    @Override
    public void rotate(float[] rotations, float[] prevRotations, boolean tick, float partialTicks) {
        BlockPos below = BlockPos.ofFloored(mc.player.getPos()).down();
        Vec3d camera = mc.player.getCameraPosVec(1.0F);
        double edgeDist = camera.squaredDistanceTo(Vec3d.ofCenter(blockPos)) - camera.squaredDistanceTo(Vec3d.ofCenter(below));
        if (hasTarget) {
            if (edgeDist >= 0.5) {
                Vec3d hitVec = Vec3d.ofCenter(blockPos).add(Vec3d.of(face.getVector()).multiply(0.5));
                MathUtil.getRotations(camera, hitVec, rotations);
            } else {
                HitResult crosshairTarget = mc.crosshairTarget;
                if (!(crosshairTarget instanceof BlockHitResult) || !((BlockHitResult) crosshairTarget).getBlockPos().equals(blockPos)) {
                    Vec3d hitVec = Vec3d.ofCenter(blockPos);
                    MathUtil.getRotations(camera, hitVec, rotations);
                } else {
                    rotations[0] = prevRotations[0];
                    rotations[1] = prevRotations[1];
                }
            }
        } else {
            rotations[0] = prevRotations[0];
            rotations[1] = prevRotations[1];
        }
    }

    @Override
    public boolean isToggled() {
        return parent.isToggled();
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Raytrace getTarget() {
        return null;
    }
}
