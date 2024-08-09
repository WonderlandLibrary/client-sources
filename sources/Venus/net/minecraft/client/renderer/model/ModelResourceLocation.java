/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import java.util.Locale;
import net.minecraft.util.ResourceLocation;

public class ModelResourceLocation
extends ResourceLocation {
    private final String variant;

    protected ModelResourceLocation(String[] stringArray) {
        super(stringArray);
        this.variant = stringArray[0].toLowerCase(Locale.ROOT);
    }

    public ModelResourceLocation(String string) {
        this(ModelResourceLocation.parsePathString(string));
    }

    public ModelResourceLocation(ResourceLocation resourceLocation, String string) {
        this(resourceLocation.toString(), string);
    }

    public ModelResourceLocation(String string, String string2) {
        this(ModelResourceLocation.parsePathString(string + "#" + string2));
    }

    protected static String[] parsePathString(String string) {
        String[] stringArray = new String[]{null, string, ""};
        int n = string.indexOf(35);
        String string2 = string;
        if (n >= 0) {
            stringArray[2] = string.substring(n + 1, string.length());
            if (n > 1) {
                string2 = string.substring(0, n);
            }
        }
        System.arraycopy(ResourceLocation.decompose(string2, ':'), 0, stringArray, 0, 2);
        return stringArray;
    }

    public String getVariant() {
        return this.variant;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof ModelResourceLocation && super.equals(object)) {
            ModelResourceLocation modelResourceLocation = (ModelResourceLocation)object;
            return this.variant.equals(modelResourceLocation.variant);
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + this.variant.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + "#" + this.variant;
    }
}

