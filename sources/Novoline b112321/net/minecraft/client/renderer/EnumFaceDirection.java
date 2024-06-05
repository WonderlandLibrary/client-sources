package net.minecraft.client.renderer;

import net.minecraft.client.renderer.EnumFaceDirection$Constants;
import net.minecraft.client.renderer.EnumFaceDirection$VertexInformation;
import net.minecraft.util.EnumFacing;

public enum EnumFaceDirection {
   DOWN(new EnumFaceDirection$VertexInformation[]{new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.WEST_INDEX, EnumFaceDirection$Constants.DOWN_INDEX, EnumFaceDirection$Constants.SOUTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.WEST_INDEX, EnumFaceDirection$Constants.DOWN_INDEX, EnumFaceDirection$Constants.NORTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.EAST_INDEX, EnumFaceDirection$Constants.DOWN_INDEX, EnumFaceDirection$Constants.NORTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.EAST_INDEX, EnumFaceDirection$Constants.DOWN_INDEX, EnumFaceDirection$Constants.SOUTH_INDEX)}),
   UP(new EnumFaceDirection$VertexInformation[]{new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.WEST_INDEX, EnumFaceDirection$Constants.UP_INDEX, EnumFaceDirection$Constants.NORTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.WEST_INDEX, EnumFaceDirection$Constants.UP_INDEX, EnumFaceDirection$Constants.SOUTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.EAST_INDEX, EnumFaceDirection$Constants.UP_INDEX, EnumFaceDirection$Constants.SOUTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.EAST_INDEX, EnumFaceDirection$Constants.UP_INDEX, EnumFaceDirection$Constants.NORTH_INDEX)}),
   NORTH(new EnumFaceDirection$VertexInformation[]{new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.EAST_INDEX, EnumFaceDirection$Constants.UP_INDEX, EnumFaceDirection$Constants.NORTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.EAST_INDEX, EnumFaceDirection$Constants.DOWN_INDEX, EnumFaceDirection$Constants.NORTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.WEST_INDEX, EnumFaceDirection$Constants.DOWN_INDEX, EnumFaceDirection$Constants.NORTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.WEST_INDEX, EnumFaceDirection$Constants.UP_INDEX, EnumFaceDirection$Constants.NORTH_INDEX)}),
   SOUTH(new EnumFaceDirection$VertexInformation[]{new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.WEST_INDEX, EnumFaceDirection$Constants.UP_INDEX, EnumFaceDirection$Constants.SOUTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.WEST_INDEX, EnumFaceDirection$Constants.DOWN_INDEX, EnumFaceDirection$Constants.SOUTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.EAST_INDEX, EnumFaceDirection$Constants.DOWN_INDEX, EnumFaceDirection$Constants.SOUTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.EAST_INDEX, EnumFaceDirection$Constants.UP_INDEX, EnumFaceDirection$Constants.SOUTH_INDEX)}),
   WEST(new EnumFaceDirection$VertexInformation[]{new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.WEST_INDEX, EnumFaceDirection$Constants.UP_INDEX, EnumFaceDirection$Constants.NORTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.WEST_INDEX, EnumFaceDirection$Constants.DOWN_INDEX, EnumFaceDirection$Constants.NORTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.WEST_INDEX, EnumFaceDirection$Constants.DOWN_INDEX, EnumFaceDirection$Constants.SOUTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.WEST_INDEX, EnumFaceDirection$Constants.UP_INDEX, EnumFaceDirection$Constants.SOUTH_INDEX)}),
   EAST(new EnumFaceDirection$VertexInformation[]{new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.EAST_INDEX, EnumFaceDirection$Constants.UP_INDEX, EnumFaceDirection$Constants.SOUTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.EAST_INDEX, EnumFaceDirection$Constants.DOWN_INDEX, EnumFaceDirection$Constants.SOUTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.EAST_INDEX, EnumFaceDirection$Constants.DOWN_INDEX, EnumFaceDirection$Constants.NORTH_INDEX), new EnumFaceDirection$VertexInformation(EnumFaceDirection$Constants.EAST_INDEX, EnumFaceDirection$Constants.UP_INDEX, EnumFaceDirection$Constants.NORTH_INDEX)});

   private static final EnumFaceDirection[] facings = new EnumFaceDirection[6];
   private final EnumFaceDirection$VertexInformation[] vertexInfos;
   private static final EnumFaceDirection[] $VALUES = new EnumFaceDirection[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};

   public static EnumFaceDirection getFacing(EnumFacing var0) {
      return facings[var0.getIndex()];
   }

   private EnumFaceDirection(EnumFaceDirection$VertexInformation[] var3) {
      this.vertexInfos = var3;
   }

   public EnumFaceDirection$VertexInformation func_179025_a(int var1) {
      return this.vertexInfos[var1];
   }

   static {
      facings[EnumFaceDirection$Constants.DOWN_INDEX] = DOWN;
      facings[EnumFaceDirection$Constants.UP_INDEX] = UP;
      facings[EnumFaceDirection$Constants.NORTH_INDEX] = NORTH;
      facings[EnumFaceDirection$Constants.SOUTH_INDEX] = SOUTH;
      facings[EnumFaceDirection$Constants.WEST_INDEX] = WEST;
      facings[EnumFaceDirection$Constants.EAST_INDEX] = EAST;
   }
}
