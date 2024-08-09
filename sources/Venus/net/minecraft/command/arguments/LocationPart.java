/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.util.text.TranslationTextComponent;

public class LocationPart {
    public static final SimpleCommandExceptionType EXPECTED_DOUBLE = new SimpleCommandExceptionType(new TranslationTextComponent("argument.pos.missing.double"));
    public static final SimpleCommandExceptionType EXPECTED_INT = new SimpleCommandExceptionType(new TranslationTextComponent("argument.pos.missing.int"));
    private final boolean relative;
    private final double value;

    public LocationPart(boolean bl, double d) {
        this.relative = bl;
        this.value = d;
    }

    public double get(double d) {
        return this.relative ? this.value + d : this.value;
    }

    public static LocationPart parseDouble(StringReader stringReader, boolean bl) throws CommandSyntaxException {
        if (stringReader.canRead() && stringReader.peek() == '^') {
            throw Vec3Argument.POS_MIXED_TYPES.createWithContext(stringReader);
        }
        if (!stringReader.canRead()) {
            throw EXPECTED_DOUBLE.createWithContext(stringReader);
        }
        boolean bl2 = LocationPart.isRelative(stringReader);
        int n = stringReader.getCursor();
        double d = stringReader.canRead() && stringReader.peek() != ' ' ? stringReader.readDouble() : 0.0;
        String string = stringReader.getString().substring(n, stringReader.getCursor());
        if (bl2 && string.isEmpty()) {
            return new LocationPart(true, 0.0);
        }
        if (!string.contains(".") && !bl2 && bl) {
            d += 0.5;
        }
        return new LocationPart(bl2, d);
    }

    public static LocationPart parseInt(StringReader stringReader) throws CommandSyntaxException {
        if (stringReader.canRead() && stringReader.peek() == '^') {
            throw Vec3Argument.POS_MIXED_TYPES.createWithContext(stringReader);
        }
        if (!stringReader.canRead()) {
            throw EXPECTED_INT.createWithContext(stringReader);
        }
        boolean bl = LocationPart.isRelative(stringReader);
        double d = stringReader.canRead() && stringReader.peek() != ' ' ? (bl ? stringReader.readDouble() : (double)stringReader.readInt()) : 0.0;
        return new LocationPart(bl, d);
    }

    public static boolean isRelative(StringReader stringReader) {
        boolean bl;
        if (stringReader.peek() == '~') {
            bl = true;
            stringReader.skip();
        } else {
            bl = false;
        }
        return bl;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof LocationPart)) {
            return true;
        }
        LocationPart locationPart = (LocationPart)object;
        if (this.relative != locationPart.relative) {
            return true;
        }
        return Double.compare(locationPart.value, this.value) == 0;
    }

    public int hashCode() {
        int n = this.relative ? 1 : 0;
        long l = Double.doubleToLongBits(this.value);
        return 31 * n + (int)(l ^ l >>> 32);
    }

    public boolean isRelative() {
        return this.relative;
    }
}

