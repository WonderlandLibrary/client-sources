/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import net.minecraft.util.Direction;
import net.minecraft.util.Util;

public enum FaceDirection {
    DOWN(new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX)),
    UP(new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX), new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX)),
    NORTH(new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX), new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX)),
    SOUTH(new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX)),
    WEST(new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX), new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX)),
    EAST(new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX));

    private static final FaceDirection[] FACINGS;
    private final VertexInformation[] vertexInfos;

    public static FaceDirection getFacing(Direction direction) {
        return FACINGS[direction.getIndex()];
    }

    private FaceDirection(VertexInformation ... vertexInformationArray) {
        this.vertexInfos = vertexInformationArray;
    }

    public VertexInformation getVertexInformation(int n) {
        return this.vertexInfos[n];
    }

    private static void lambda$static$0(FaceDirection[] faceDirectionArray) {
        faceDirectionArray[Constants.DOWN_INDEX] = DOWN;
        faceDirectionArray[Constants.UP_INDEX] = UP;
        faceDirectionArray[Constants.NORTH_INDEX] = NORTH;
        faceDirectionArray[Constants.SOUTH_INDEX] = SOUTH;
        faceDirectionArray[Constants.WEST_INDEX] = WEST;
        faceDirectionArray[Constants.EAST_INDEX] = EAST;
    }

    static {
        FACINGS = Util.make(new FaceDirection[6], FaceDirection::lambda$static$0);
    }

    public static class VertexInformation {
        public final int xIndex;
        public final int yIndex;
        public final int zIndex;

        private VertexInformation(int n, int n2, int n3) {
            this.xIndex = n;
            this.yIndex = n2;
            this.zIndex = n3;
        }
    }

    public static final class Constants {
        public static final int SOUTH_INDEX = Direction.SOUTH.getIndex();
        public static final int UP_INDEX = Direction.UP.getIndex();
        public static final int EAST_INDEX = Direction.EAST.getIndex();
        public static final int NORTH_INDEX = Direction.NORTH.getIndex();
        public static final int DOWN_INDEX = Direction.DOWN.getIndex();
        public static final int WEST_INDEX = Direction.WEST.getIndex();
    }
}

