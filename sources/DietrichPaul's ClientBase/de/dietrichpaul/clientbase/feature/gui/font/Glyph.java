/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.gui.font;

import com.google.gson.JsonObject;

public class Glyph {

    private int unicode;
    private float advance;

    private float pLeft;
    private float pBottom;
    private float pRight;
    private float pTop;

    private float aLeft;
    private float aBottom;
    private float aRight;
    private float aTop;

    public static Glyph parse(JsonObject object) {
        Glyph glyph = new Glyph();
        glyph.unicode = object.get("unicode").getAsInt();
        glyph.advance = object.get("advance").getAsFloat();

        if (object.has("planeBounds")) {
            glyph.pLeft = object.get("planeBounds").getAsJsonObject().get("left").getAsFloat();
            glyph.pBottom = object.get("planeBounds").getAsJsonObject().get("bottom").getAsFloat();
            glyph.pRight = object.get("planeBounds").getAsJsonObject().get("right").getAsFloat();
            glyph.pTop = object.get("planeBounds").getAsJsonObject().get("top").getAsFloat();
        }

        if (object.has("atlasBounds")) {
            glyph.aLeft = object.get("atlasBounds").getAsJsonObject().get("left").getAsFloat();
            glyph.aBottom = object.get("atlasBounds").getAsJsonObject().get("bottom").getAsFloat();
            glyph.aRight = object.get("atlasBounds").getAsJsonObject().get("right").getAsFloat();
            glyph.aTop = object.get("atlasBounds").getAsJsonObject().get("top").getAsFloat();
        }

        return glyph;
    }

    public int getUnicode() {
        return unicode;
    }

    public float getAdvance() {
        return advance;
    }

    public float getpLeft() {
        return pLeft;
    }

    public float getpBottom() {
        return pBottom;
    }

    public float getpRight() {
        return pRight;
    }

    public float getpTop() {
        return pTop;
    }

    public float getaLeft() {
        return aLeft;
    }

    public float getaBottom() {
        return aBottom;
    }

    public float getaRight() {
        return aRight;
    }

    public float getaTop() {
        return aTop;
    }
}
