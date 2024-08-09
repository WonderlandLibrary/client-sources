/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Objects;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ILocationArgument;
import net.minecraft.command.arguments.LocationPart;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

public class LocalLocationArgument
implements ILocationArgument {
    private final double left;
    private final double up;
    private final double forwards;

    public LocalLocationArgument(double d, double d2, double d3) {
        this.left = d;
        this.up = d2;
        this.forwards = d3;
    }

    @Override
    public Vector3d getPosition(CommandSource commandSource) {
        Vector2f vector2f = commandSource.getRotation();
        Vector3d vector3d = commandSource.getEntityAnchorType().apply(commandSource);
        float f = MathHelper.cos((vector2f.y + 90.0f) * ((float)Math.PI / 180));
        float f2 = MathHelper.sin((vector2f.y + 90.0f) * ((float)Math.PI / 180));
        float f3 = MathHelper.cos(-vector2f.x * ((float)Math.PI / 180));
        float f4 = MathHelper.sin(-vector2f.x * ((float)Math.PI / 180));
        float f5 = MathHelper.cos((-vector2f.x + 90.0f) * ((float)Math.PI / 180));
        float f6 = MathHelper.sin((-vector2f.x + 90.0f) * ((float)Math.PI / 180));
        Vector3d vector3d2 = new Vector3d(f * f3, f4, f2 * f3);
        Vector3d vector3d3 = new Vector3d(f * f5, f6, f2 * f5);
        Vector3d vector3d4 = vector3d2.crossProduct(vector3d3).scale(-1.0);
        double d = vector3d2.x * this.forwards + vector3d3.x * this.up + vector3d4.x * this.left;
        double d2 = vector3d2.y * this.forwards + vector3d3.y * this.up + vector3d4.y * this.left;
        double d3 = vector3d2.z * this.forwards + vector3d3.z * this.up + vector3d4.z * this.left;
        return new Vector3d(vector3d.x + d, vector3d.y + d2, vector3d.z + d3);
    }

    @Override
    public Vector2f getRotation(CommandSource commandSource) {
        return Vector2f.ZERO;
    }

    @Override
    public boolean isXRelative() {
        return false;
    }

    @Override
    public boolean isYRelative() {
        return false;
    }

    @Override
    public boolean isZRelative() {
        return false;
    }

    public static LocalLocationArgument parse(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        double d = LocalLocationArgument.parseCoord(stringReader, n);
        if (stringReader.canRead() && stringReader.peek() == ' ') {
            stringReader.skip();
            double d2 = LocalLocationArgument.parseCoord(stringReader, n);
            if (stringReader.canRead() && stringReader.peek() == ' ') {
                stringReader.skip();
                double d3 = LocalLocationArgument.parseCoord(stringReader, n);
                return new LocalLocationArgument(d, d2, d3);
            }
            stringReader.setCursor(n);
            throw Vec3Argument.POS_INCOMPLETE.createWithContext(stringReader);
        }
        stringReader.setCursor(n);
        throw Vec3Argument.POS_INCOMPLETE.createWithContext(stringReader);
    }

    private static double parseCoord(StringReader stringReader, int n) throws CommandSyntaxException {
        if (!stringReader.canRead()) {
            throw LocationPart.EXPECTED_DOUBLE.createWithContext(stringReader);
        }
        if (stringReader.peek() != '^') {
            stringReader.setCursor(n);
            throw Vec3Argument.POS_MIXED_TYPES.createWithContext(stringReader);
        }
        stringReader.skip();
        return stringReader.canRead() && stringReader.peek() != ' ' ? stringReader.readDouble() : 0.0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof LocalLocationArgument)) {
            return true;
        }
        LocalLocationArgument localLocationArgument = (LocalLocationArgument)object;
        return this.left == localLocationArgument.left && this.up == localLocationArgument.up && this.forwards == localLocationArgument.forwards;
    }

    public int hashCode() {
        return Objects.hash(this.left, this.up, this.forwards);
    }
}

