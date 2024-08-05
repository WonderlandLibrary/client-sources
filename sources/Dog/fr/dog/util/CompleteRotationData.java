package fr.dog.util;

import net.minecraft.util.MovingObjectPosition;

public class CompleteRotationData {
    public MovingObjectPosition movingObjectPosition;
    public float[] rotations;


    public CompleteRotationData(MovingObjectPosition position, float[] rotations) {
        this.rotations = rotations;
        this.movingObjectPosition = position;
    }
}
