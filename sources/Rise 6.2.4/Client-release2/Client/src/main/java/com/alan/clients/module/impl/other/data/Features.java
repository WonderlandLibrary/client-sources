package com.alan.clients.module.impl.other.data;

import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.util.vector.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.Optional;
import java.util.function.Supplier;

import static com.alan.clients.module.impl.other.data.Data.eStates;
import static com.alan.clients.module.impl.other.data.Data.pStates;
import static com.alan.clients.module.impl.other.data.Equations.*;

public enum Features {
    ID(() -> (double) pStates.get().getId()),

    PLAYER_X(() -> pStates.get().gPX()),

    PLAYER_Y(() -> pStates.get().gPY()),

    PLAYER_Z(() -> pStates.get().gPZ()),

    PLAYER_YAW(() -> pStates.get().gRY()),

    PLAYER_PITCH(() -> pStates.get().gRP()),

    TARGET_X(() -> eStates.get().gPX()),

    TARGET_Y(() -> eStates.get().gPY()),

    TARGET_Z(() -> eStates.get().gPZ()),

    TARGET_YAW(() -> eStates.get().gRY()),

    TARGET_PITCH(() -> eStates.get().gRP()),

    DIFFERENCE_X(() -> DIFFERENCE.run(pStates.get().gPX(), eStates.get().gPX())),

    DIFFERENCE_Y(() -> DIFFERENCE.run(pStates.get().gPY(), eStates.get().gPY())),

    DIFFERENCE_Z(() -> DIFFERENCE.run(pStates.get().gPZ(), eStates.get().gPZ())),

    PLAYER_PERFECT_YAW(() -> (double) RotationUtil.calculate(new Vector3d(pStates.get().gPX(), 0, pStates.get().gPZ()),
            new Vector3d(eStates.get().gPX(), 0, eStates.get().gPZ())).getX()),

    PLAYER_PERFECT_PITCH(() -> (double) RotationUtil.calculate(new Vector3d(pStates.get().gPX(), pStates.get().gPY(), pStates.get().gPZ()),
            new Vector3d(eStates.get().gPX(), eStates.get().gPY(), eStates.get().gPZ())).getY()),

    PLAYER_DELTA_X(() -> pStates.get().gPX() - pStates.get(1).gPX()),

    PLAYER_DELTA_Y(() -> pStates.get().gPY() - pStates.get(1).gPY()),

    PLAYER_DELTA_Z(() -> pStates.get().gPZ() - pStates.get(1).gPZ()),

    PLAYER_DELTA_YAW(() -> WRAPPED_TO_180_DISTANCE.run(pStates.get().gRY(), pStates.get(1).gRY())),

    PLAYER_DELTA_PITCH(() -> DIFFERENCE.run(pStates.get().gRP(), pStates.get(1).gRP())),

    PLAYER_YAW_DIFFERENCE_FROM_PERFECT_YAW(() -> WRAPPED_TO_180_DISTANCE.run(pStates.get().gRY(), PLAYER_PERFECT_YAW.run())),

    ENEMY_YAW_DIFFERENCE_FROM_PERFECT_YAW(() -> PLAYER_YAW_DIFFERENCE_FROM_PERFECT_YAW.run(true)),

    PLAYER_PITCH_DIFFERENCE_FROM_PERFECT_PITCH(() -> DIFFERENCE.run(pStates.get().gRP(), PLAYER_PERFECT_PITCH.run())),

    ENEMY_PITCH_DIFFERENCE_FROM_PERFECT_PITCH(() -> PLAYER_PITCH_DIFFERENCE_FROM_PERFECT_PITCH.run(true)),

    HORIZONTAL_DISTANCE(() -> EUCLIDEAN_DISTANCE.run(eStates.get().gPX() - pStates.get().gPX(),
            eStates.get().gPZ() - pStates.get().gPZ())),

    VERTICAL_DISTANCE(() -> DIFFERENCE.run(eStates.get().gPY(), pStates.get().gPY())),

    PLAYER_CONTRIBUTION_TO_DELTA_HORIZONTAL_DISTANCE(() -> HORIZONTAL_DISTANCE.run() - EUCLIDEAN_DISTANCE.run(eStates.get().gPX() -
            pStates.get(1).gPX(), eStates.get().gPZ() - pStates.get(1).gPZ())),

    ENEMY_CONTRIBUTION_TO_DELTA_HORIZONTAL_DISTANCE(() -> PLAYER_CONTRIBUTION_TO_DELTA_HORIZONTAL_DISTANCE.run(true)),

    PLAYER_CONTRIBUTION_TO_DELTA_VERTICAL_DISTANCE(() -> DIFFERENCE.run(DIFFERENCE.run(eStates.get().gPY(), pStates.get(1).gPY()),
            VERTICAL_DISTANCE.run())),

    ENEMY_CONTRIBUTION_TO_DELTA_VERTICAL_DISTANCE(() -> PLAYER_CONTRIBUTION_TO_DELTA_VERTICAL_DISTANCE.run(true)),

    ANGLE_OF_CROSS_HAIR_TO_ENEMY(() -> MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(PLAYER_YAW_DIFFERENCE_FROM_PERFECT_YAW.run(),
            PLAYER_PITCH_DIFFERENCE_FROM_PERFECT_PITCH.run())))),

    DELTA_HORIZONTAL_ANGLE_TO_ENEMY(() -> WRAPPED_TO_180_DISTANCE.run(PLAYER_PERFECT_YAW.run(), PLAYER_PERFECT_YAW.run(1))),

    DELTA_VERTICAL_ANGLE_TO_ENEMY(() -> DIFFERENCE.run(PLAYER_PERFECT_PITCH.run(), PLAYER_PERFECT_PITCH.run(1))),

    PLAYER_HORIZONTAL_DELTA(() -> EUCLIDEAN_DISTANCE.run(DIFFERENCE.run(pStates.get().gPX(), pStates.get(1).gPX()),
            DIFFERENCE.run(pStates.get().gPZ(), pStates.get(1).gPZ()))),

    PLAYER_HORIZONTAL_MOVEMENT_ANGLE(() -> (double) (MathHelper.wrapAngleTo180_float((float) (360 - Math.atan2(PLAYER_DELTA_X.run(),
            PLAYER_DELTA_Z.run()) * (180 / Math.PI) - 180)) + 180)),

    PLAYER_FORWARD_PERCENTAGE(() -> Math.min(Math.abs(WRAPPED_TO_180_DISTANCE.run(pStates.get().gRY(), PLAYER_HORIZONTAL_MOVEMENT_ANGLE.run() - 90)),
            Math.abs(WRAPPED_TO_180_DISTANCE.run(pStates.get().gRY(), PLAYER_HORIZONTAL_MOVEMENT_ANGLE.run() + 90))) / 90 * 100),

    PLAYER_FORWARD_DELTA(() -> (PLAYER_HORIZONTAL_DELTA.run() * PLAYER_FORWARD_PERCENTAGE.run() / 100) *
            (Math.abs(WRAPPED_TO_180_DISTANCE.run(pStates.get().gRY(), PLAYER_HORIZONTAL_MOVEMENT_ANGLE.run())) > 90 ? -1 : 1)),

    PLAYER_LATERAL_DELTA(() -> PLAYER_HORIZONTAL_DELTA.run() * (1 - PLAYER_FORWARD_PERCENTAGE.run() / 100) *
            (Math.abs(WRAPPED_TO_180_DISTANCE.run(pStates.get().gRY() + 90, PLAYER_HORIZONTAL_MOVEMENT_ANGLE.run())) > 90 ? -1 : 1)),

    ENEMY_FORWARD_DELTA(() -> PLAYER_FORWARD_DELTA.run(true)),

    ENEMY_LATERAL_DELTA(() -> PLAYER_LATERAL_DELTA.run(true)),

    PLAYER_CONTRIBUTION_TO_DELTA_HORIZONTAL_ANGLE_TO_ENEMY(() -> WRAPPED_TO_180_DISTANCE.run(PLAYER_PERFECT_YAW.run(), (double) RotationUtil.calculate(new Vector3d(pStates.get(1).gPX(), pStates.get(1).gPY(), pStates.get(1).gPZ()),
            new Vector3d(eStates.get().gPX(), eStates.get().gPY(), eStates.get().gPZ())).getX())),

    ENEMY_CONTRIBUTION_TO_DELTA_HORIZONTAL_ANGLE_TO_PLAYER(() -> PLAYER_CONTRIBUTION_TO_DELTA_HORIZONTAL_ANGLE_TO_ENEMY.run(true)),

    PLAYER_CONTRIBUTION_TO_DELTA_VERTICAL_ANGLE_TO_ENEMY(() -> DIFFERENCE.run(PLAYER_PERFECT_PITCH.run(), (double) RotationUtil.calculate(new Vector3d(pStates.get(1).gPX(), pStates.get(1).gPY(), pStates.get(1).gPZ()),
            new Vector3d(eStates.get().gPX(), eStates.get().gPY(), eStates.get().gPZ())).getY())),

    ENEMY_CONTRIBUTION_TO_DELTA_VERTICAL_ANGLE_TO_PLAYER(() -> PLAYER_CONTRIBUTION_TO_DELTA_VERTICAL_ANGLE_TO_ENEMY.run(true) * -1),

    PLAYER_DELTA_ROTATION(() -> EUCLIDEAN_DISTANCE.run(PLAYER_DELTA_YAW.run(), PLAYER_DELTA_PITCH.run())),

    PLAYER_ROTATION_DISTANCE_FROM_PERFECT_ROTATIONS(() -> EUCLIDEAN_DISTANCE.run(PLAYER_YAW_DIFFERENCE_FROM_PERFECT_YAW.run(), PLAYER_PITCH_DIFFERENCE_FROM_PERFECT_PITCH.run())),

    PLAYER_DELTA_ROTATION_DISTANCE_FROM_PERFECT_ROTATIONS(() -> DIFFERENCE.run(PLAYER_ROTATION_DISTANCE_FROM_PERFECT_ROTATIONS.run(), PLAYER_ROTATION_DISTANCE_FROM_PERFECT_ROTATIONS.run(1))),

    PLAYER_CONTRIBUTION_TO_DELTA_ROTATION_DISTANCE_FROM_PERFECT_ROTATIONS(() -> DIFFERENCE.run(PLAYER_ROTATION_DISTANCE_FROM_PERFECT_ROTATIONS.run(),
            EUCLIDEAN_DISTANCE.run(WRAPPED_TO_180_DISTANCE.run(pStates.get(1).gRY(), (double) RotationUtil.calculate(new Vector3d(pStates.get(1).gPX(), 0, pStates.get(1).gPZ()),
                    new Vector3d(eStates.get().gPX(), 0, eStates.get().gPZ())).getX()), DIFFERENCE.run(pStates.get(1).gRP(), (double) RotationUtil.calculate(new Vector3d(pStates.get(1).gPX(), pStates.get(1).gPY(), pStates.get(1).gPZ()),
                    new Vector3d(eStates.get().gPX(), eStates.get().gPY(), eStates.get().gPZ())).getY())))),

    ENEMY_CONTRIBUTION_TO_DELTA_ROTATION_DISTANCE_FROM_PERFECT_ROTATIONS(() -> PLAYER_CONTRIBUTION_TO_DELTA_ROTATION_DISTANCE_FROM_PERFECT_ROTATIONS.run(true) * -1),

    PLAYER_CLICKED(() -> (double) (pStates.get().isClick() ? 1 : 0)),

    PLAYER_LAST_CLICK(() -> {
        Optional<Entity> optional = pStates.stream().filter(s -> s.isClick()).findFirst();
        return (double) (optional.map(entity -> pStates.size() - pStates.indexOf(entity) - 1).orElse(pStates.getMaxSize()));
    });

    private final Supplier<Double> function;

    Features(Supplier<Double> function) {
        this.function = function;
    }

    public Double run() {
        return function.get();
    }

    public Double run(int slide) {
        return run(slide, false);
    }

    public Double run(boolean inverse) {
        return run(0, inverse);
    }

    public Double run(int slide, boolean inverse) {
        States<Entity> pStates = Data.pStates.clone();
        States<Entity> eStates = Data.eStates.clone();

        if (inverse) {
            Data.pStates = eStates;
            Data.eStates = pStates;
        }

        for (int i = 0; i < slide; i++) {
            Data.pStates.removeLast();
            Data.eStates.removeLast();
        }

        Double value = function.get();

        Data.pStates = pStates;
        Data.eStates = eStates;

        return value;
    }
}