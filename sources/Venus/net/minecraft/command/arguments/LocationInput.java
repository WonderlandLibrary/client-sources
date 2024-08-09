/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ILocationArgument;
import net.minecraft.command.arguments.LocationPart;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

public class LocationInput
implements ILocationArgument {
    private final LocationPart x;
    private final LocationPart y;
    private final LocationPart z;

    public LocationInput(LocationPart locationPart, LocationPart locationPart2, LocationPart locationPart3) {
        this.x = locationPart;
        this.y = locationPart2;
        this.z = locationPart3;
    }

    @Override
    public Vector3d getPosition(CommandSource commandSource) {
        Vector3d vector3d = commandSource.getPos();
        return new Vector3d(this.x.get(vector3d.x), this.y.get(vector3d.y), this.z.get(vector3d.z));
    }

    @Override
    public Vector2f getRotation(CommandSource commandSource) {
        Vector2f vector2f = commandSource.getRotation();
        return new Vector2f((float)this.x.get(vector2f.x), (float)this.y.get(vector2f.y));
    }

    @Override
    public boolean isXRelative() {
        return this.x.isRelative();
    }

    @Override
    public boolean isYRelative() {
        return this.y.isRelative();
    }

    @Override
    public boolean isZRelative() {
        return this.z.isRelative();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof LocationInput)) {
            return true;
        }
        LocationInput locationInput = (LocationInput)object;
        if (!this.x.equals(locationInput.x)) {
            return true;
        }
        return !this.y.equals(locationInput.y) ? false : this.z.equals(locationInput.z);
    }

    public static LocationInput parseInt(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        LocationPart locationPart = LocationPart.parseInt(stringReader);
        if (stringReader.canRead() && stringReader.peek() == ' ') {
            stringReader.skip();
            LocationPart locationPart2 = LocationPart.parseInt(stringReader);
            if (stringReader.canRead() && stringReader.peek() == ' ') {
                stringReader.skip();
                LocationPart locationPart3 = LocationPart.parseInt(stringReader);
                return new LocationInput(locationPart, locationPart2, locationPart3);
            }
            stringReader.setCursor(n);
            throw Vec3Argument.POS_INCOMPLETE.createWithContext(stringReader);
        }
        stringReader.setCursor(n);
        throw Vec3Argument.POS_INCOMPLETE.createWithContext(stringReader);
    }

    public static LocationInput parseDouble(StringReader stringReader, boolean bl) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        LocationPart locationPart = LocationPart.parseDouble(stringReader, bl);
        if (stringReader.canRead() && stringReader.peek() == ' ') {
            stringReader.skip();
            LocationPart locationPart2 = LocationPart.parseDouble(stringReader, false);
            if (stringReader.canRead() && stringReader.peek() == ' ') {
                stringReader.skip();
                LocationPart locationPart3 = LocationPart.parseDouble(stringReader, bl);
                return new LocationInput(locationPart, locationPart2, locationPart3);
            }
            stringReader.setCursor(n);
            throw Vec3Argument.POS_INCOMPLETE.createWithContext(stringReader);
        }
        stringReader.setCursor(n);
        throw Vec3Argument.POS_INCOMPLETE.createWithContext(stringReader);
    }

    public static LocationInput current() {
        return new LocationInput(new LocationPart(true, 0.0), new LocationPart(true, 0.0), new LocationPart(true, 0.0));
    }

    public int hashCode() {
        int n = this.x.hashCode();
        n = 31 * n + this.y.hashCode();
        return 31 * n + this.z.hashCode();
    }
}

