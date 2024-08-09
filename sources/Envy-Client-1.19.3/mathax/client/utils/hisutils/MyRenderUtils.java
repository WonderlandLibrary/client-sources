package mathax.client.utils.hisutils;

import mathax.client.events.render.Render3DEvent;
import mathax.client.utils.render.color.Color;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class MyRenderUtils {
    public static void renderQuad(BlockPos block, Direction direction, Render3DEvent event, Color color) {
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();

        switch (direction) {
            case DOWN -> event.renderer.quadHorizontal(x, y, z, x+1, z+1, color);
            case UP -> event.renderer.quadHorizontal(x, y+1, z, x+1, z+1, color);

            case NORTH -> event.renderer.quadVertical(x,y,z,x+1,y+1,z,color);
            case SOUTH -> event.renderer.quadVertical(x,y,z+1,x+1,y+1,z+1,color);

            case WEST -> event.renderer.quadVertical(x,y,z,x,y+1,z+1,color);
            case EAST -> event.renderer.quadVertical(x+1,y,z,x+1,y+1,z+1,color);
        }
    }
}
