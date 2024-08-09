/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Orientation;

public enum Rotation {
    NONE(Orientation.IDENTITY),
    CLOCKWISE_90(Orientation.ROT_90_Y_NEG),
    CLOCKWISE_180(Orientation.ROT_180_FACE_XZ),
    COUNTERCLOCKWISE_90(Orientation.ROT_90_Y_POS);

    private final Orientation orientation;

    private Rotation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Rotation add(Rotation rotation) {
        switch (1.$SwitchMap$net$minecraft$util$Rotation[rotation.ordinal()]) {
            case 3: {
                switch (1.$SwitchMap$net$minecraft$util$Rotation[this.ordinal()]) {
                    case 1: {
                        return CLOCKWISE_180;
                    }
                    case 2: {
                        return COUNTERCLOCKWISE_90;
                    }
                    case 3: {
                        return NONE;
                    }
                    case 4: {
                        return CLOCKWISE_90;
                    }
                }
            }
            case 4: {
                switch (1.$SwitchMap$net$minecraft$util$Rotation[this.ordinal()]) {
                    case 1: {
                        return COUNTERCLOCKWISE_90;
                    }
                    case 2: {
                        return NONE;
                    }
                    case 3: {
                        return CLOCKWISE_90;
                    }
                    case 4: {
                        return CLOCKWISE_180;
                    }
                }
            }
            case 2: {
                switch (1.$SwitchMap$net$minecraft$util$Rotation[this.ordinal()]) {
                    case 1: {
                        return CLOCKWISE_90;
                    }
                    case 2: {
                        return CLOCKWISE_180;
                    }
                    case 3: {
                        return COUNTERCLOCKWISE_90;
                    }
                    case 4: {
                        return NONE;
                    }
                }
            }
        }
        return this;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public Direction rotate(Direction direction) {
        if (direction.getAxis() == Direction.Axis.Y) {
            return direction;
        }
        switch (1.$SwitchMap$net$minecraft$util$Rotation[this.ordinal()]) {
            case 2: {
                return direction.rotateY();
            }
            case 3: {
                return direction.getOpposite();
            }
            case 4: {
                return direction.rotateYCCW();
            }
        }
        return direction;
    }

    public int rotate(int n, int n2) {
        switch (1.$SwitchMap$net$minecraft$util$Rotation[this.ordinal()]) {
            case 2: {
                return (n + n2 / 4) % n2;
            }
            case 3: {
                return (n + n2 / 2) % n2;
            }
            case 4: {
                return (n + n2 * 3 / 4) % n2;
            }
        }
        return n;
    }

    public static Rotation randomRotation(Random random2) {
        return Util.getRandomObject(Rotation.values(), random2);
    }

    public static List<Rotation> shuffledRotations(Random random2) {
        ArrayList<Rotation> arrayList = Lists.newArrayList(Rotation.values());
        Collections.shuffle(arrayList, random2);
        return arrayList;
    }
}

