package xyz.mathax.mathaxclient.renderer;

import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.world.MatHaxDirection;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class Renderer3D {
    public final Mesh lines = new ShaderMesh(Shaders.POS_COLOR, DrawMode.Lines, Mesh.Attrib.Vec3, Mesh.Attrib.Color);
    public final Mesh triangles = new ShaderMesh(Shaders.POS_COLOR, DrawMode.Triangles, Mesh.Attrib.Vec3, Mesh.Attrib.Color);

    public void begin() {
        lines.begin();
        triangles.begin();
    }

    public void end() {
        lines.end();
        triangles.end();
    }

    public void render(MatrixStack matrixStack) {
        lines.render(matrixStack);
        triangles.render(matrixStack);
    }

    // Lines

    public void line(double x1, double y1, double z1, double x2, double y2, double z2, Color color1, Color color2) {
        int i1 = lines.vec3(x1, y1, z1).color(color1).next();
        int i2 = lines.vec3(x2, y2, z2).color(color2).next();
        lines.line(i1, i2);
    }

    public void line(double x1, double y1, double z1, double x2, double y2, double z2, Color color) {
        line(x1, y1, z1, x2, y2, z2, color, color);
    }

    public void boxLines(double x1, double y1, double z1, double x2, double y2, double z2, Color color, int excludeDir) {
        int blb = lines.vec3(x1, y1, z1).color(color).next();
        int blf = lines.vec3(x1, y1, z2).color(color).next();
        int brb = lines.vec3(x2, y1, z1).color(color).next();
        int brf = lines.vec3(x2, y1, z2).color(color).next();
        int tlb = lines.vec3(x1, y2, z1).color(color).next();
        int tlf = lines.vec3(x1, y2, z2).color(color).next();
        int trb = lines.vec3(x2, y2, z1).color(color).next();
        int trf = lines.vec3(x2, y2, z2).color(color).next();

        if (excludeDir == 0) {
            // Bottom to top
            lines.line(blb, tlb);
            lines.line(blf, tlf);
            lines.line(brb, trb);
            lines.line(brf, trf);

            // Bottom loop
            lines.line(blb, blf);
            lines.line(brb, brf);
            lines.line(blb, brb);
            lines.line(blf, brf);

            // Top loop
            lines.line(tlb, tlf);
            lines.line(trb, trf);
            lines.line(tlb, trb);
            lines.line(tlf, trf);
        }
        else {
            // Bottom to top
            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.WEST) && MatHaxDirection.isNot(excludeDir, MatHaxDirection.NORTH)) {
                lines.line(blb, tlb);
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.WEST) && MatHaxDirection.isNot(excludeDir, MatHaxDirection.SOUTH)) {
                lines.line(blf, tlf);
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.EAST) && MatHaxDirection.isNot(excludeDir, MatHaxDirection.NORTH)) {
                lines.line(brb, trb);
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.EAST) && MatHaxDirection.isNot(excludeDir, MatHaxDirection.SOUTH)) {
                lines.line(brf, trf);
            }

            // Bottom loop
            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.WEST) && MatHaxDirection.isNot(excludeDir, MatHaxDirection.DOWN)) {
                lines.line(blb, blf);
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.EAST) && MatHaxDirection.isNot(excludeDir, MatHaxDirection.DOWN)) {
                lines.line(brb, brf);
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.NORTH) && MatHaxDirection.isNot(excludeDir, MatHaxDirection.DOWN)) {
                lines.line(blb, brb);
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.SOUTH) && MatHaxDirection.isNot(excludeDir, MatHaxDirection.DOWN)) {
                lines.line(blf, brf);
            }

            // Top loop
            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.WEST) && MatHaxDirection.isNot(excludeDir, MatHaxDirection.UP)) {
                lines.line(tlb, tlf);
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.EAST) && MatHaxDirection.isNot(excludeDir, MatHaxDirection.UP)) {
                lines.line(trb, trf);
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.NORTH) && MatHaxDirection.isNot(excludeDir, MatHaxDirection.UP)) {
                lines.line(tlb, trb);
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.SOUTH) && MatHaxDirection.isNot(excludeDir, MatHaxDirection.UP)) {
                lines.line(tlf, trf);
            }
        }

        lines.growIfNeeded();
    }

    public void blockLines(int x, int y, int z, Color color, int excludeDir) {
        boxLines(x, y, z, x + 1, y + 1, z + 1, color, excludeDir);
    }

    // Quads

    public void quad(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4, Color topLeft, Color topRight, Color bottomRight, Color bottomLeft) {
        int i1 = triangles.vec3(x1, y1, z1).color(bottomLeft).next();
        int i2 = triangles.vec3(x2, y2, z2).color(topLeft).next();
        int i3 = triangles.vec3(x3, y3, z3).color(topRight).next();
        int i4 = triangles.vec3(x4, y4, z4).color(bottomRight).next();
        triangles.quad(i1, i2, i3, i4);
    }

    public void quad(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4, Color color) {
        quad(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, color, color, color, color);
    }

    public void quadVertical(double x1, double y1, double z1, double x2, double y2, double z2, Color color) {
        quad(x1, y1, z1, x1, y2, z1, x2, y2, z2, x2, y1, z2, color);
    }

    public void quadHorizontal(double x1, double y, double z1, double x2, double z2, Color color) {
        quad(x1, y, z1, x1, y, z2, x2, y, z2, x2, y, z1, color);
    }

    public void gradientQuadVertical(double x1, double y1, double z1, double x2, double y2, double z2, Color topColor, Color bottomColor) {
        quad(x1, y1, z1, x1, y2, z1, x2, y2, z2, x2, y1, z2, topColor, topColor, bottomColor, bottomColor);
    }

    // Sides

    public void side(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4, Color sideColor, Color lineColor, ShapeMode mode) {
        if (mode.lines()) {
            int i1 = lines.vec3(x1, y1, z1).color(lineColor).next();
            int i2 = lines.vec3(x2, y2, z2).color(lineColor).next();
            int i3 = lines.vec3(x3, y3, z3).color(lineColor).next();
            int i4 = lines.vec3(x4, y4, z4).color(lineColor).next();

            lines.line(i1, i2);
            lines.line(i2, i3);
            lines.line(i3, i4);
            lines.line(i4, i1);
        }

        if (mode.sides()) {
            quad(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, sideColor);
        }
    }

    public void sideVertical(double x1, double y1, double z1, double x2, double y2, double z2, Color sideColor, Color lineColor, ShapeMode mode) {
        side(x1, y1, z1, x1, y2, z1, x2, y2, z2, x2, y1, z2, sideColor, lineColor, mode);
    }

    public void sideHorizontal(double x1, double y, double z1, double x2, double z2, Color sideColor, Color lineColor, ShapeMode mode) {
        side(x1, y, z1, x1, y, z2, x2, y, z2, x2, y, z1, sideColor, lineColor, mode);
    }

    // Boxes

    public void boxSides(double x1, double y1, double z1, double x2, double y2, double z2, Color color, int excludeDir) {
        int blb = triangles.vec3(x1, y1, z1).color(color).next();
        int blf = triangles.vec3(x1, y1, z2).color(color).next();
        int brb = triangles.vec3(x2, y1, z1).color(color).next();
        int brf = triangles.vec3(x2, y1, z2).color(color).next();
        int tlb = triangles.vec3(x1, y2, z1).color(color).next();
        int tlf = triangles.vec3(x1, y2, z2).color(color).next();
        int trb = triangles.vec3(x2, y2, z1).color(color).next();
        int trf = triangles.vec3(x2, y2, z2).color(color).next();

        if (excludeDir == 0) {
            // Bottom to top
            triangles.quad(blb, blf, tlf, tlb);
            triangles.quad(brb, trb, trf, brf);
            triangles.quad(blb, tlb, trb, brb);
            triangles.quad(blf, brf, trf, tlf);

            // Bottom
            triangles.quad(blb, brb, brf, blf);

            // Top
            triangles.quad(tlb, tlf, trf, trb);
        }
        else {
            // Bottom to top
            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.WEST)) {
                triangles.quad(blb, blf, tlf, tlb);
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.EAST)) {
                triangles.quad(brb, trb, trf, brf);
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.NORTH)) {
                triangles.quad(blb, tlb, trb, brb);
            }

            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.SOUTH)) {
                triangles.quad(blf, brf, trf, tlf);
            }

            // Bottom
            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.DOWN)) {
                triangles.quad(blb, brb, brf, blf);
            }

            // Top
            if (MatHaxDirection.isNot(excludeDir, MatHaxDirection.UP)) {
                triangles.quad(tlb, tlf, trf, trb);
            }
        }

        triangles.growIfNeeded();
    }

    public void blockSides(int x, int y, int z, Color color, int excludeDir) {
        boxSides(x, y, z, x + 1, y + 1, z + 1, color, excludeDir);
    }

    public void box(double x1, double y1, double z1, double x2, double y2, double z2, Color sideColor, Color lineColor, ShapeMode mode, int excludeDir) {
        if (mode.lines()) {
            boxLines(x1, y1, z1, x2, y2, z2, lineColor, excludeDir);
        }

        if (mode.sides()) {
            boxSides(x1, y1, z1, x2, y2, z2, sideColor, excludeDir);
        }
    }

    public void box(BlockPos pos, Color sideColor, Color lineColor, ShapeMode mode, int excludeDir) {
        if (mode.lines()) {
            boxLines(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1, lineColor, excludeDir);
        }

        if (mode.sides()) {
            boxSides(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1, sideColor, excludeDir);
        }
    }

    public void box(Box box, Color sideColor, Color lineColor, ShapeMode mode, int excludeDir) {
        if (mode.lines()) {
            boxLines(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, lineColor, excludeDir);
        }

        if (mode.sides()) {
            boxSides(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, sideColor, excludeDir);
        }
    }
}
