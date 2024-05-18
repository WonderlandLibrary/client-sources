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

public class FontMetrics {

    private int emSize;
    private float lineHeight;
    private float ascender;
    private float descender;
    private float underlineY;
    private float underlineThickness;

    public FontMetrics(int emSize, float lineHeight, float ascender, float descender, float underlineY, float underlineThickness) {
        this.emSize = emSize;
        this.lineHeight = lineHeight;
        this.ascender = ascender;
        this.descender = descender;
        this.underlineY = underlineY;
        this.underlineThickness = underlineThickness;
    }

    private FontMetrics() {
    }

    public static FontMetrics parse(JsonObject object) {
        FontMetrics metrics = new FontMetrics();
        metrics.emSize = object.get("emSize").getAsInt();
        metrics.lineHeight = object.get("lineHeight").getAsFloat();
        metrics.ascender = object.get("ascender").getAsFloat();
        metrics.descender = object.get("descender").getAsFloat();
        metrics.underlineY = object.get("underlineY").getAsFloat();
        metrics.underlineThickness = object.get("underlineThickness").getAsFloat();
        return metrics;
    }

    public int getEmSize() {
        return emSize;
    }

    public float getLineHeight() {
        return lineHeight;
    }

    public float getAscender() {
        return ascender;
    }

    public float getDescender() {
        return descender;
    }

    public float getUnderlineY() {
        return underlineY;
    }

    public float getUnderlineThickness() {
        return underlineThickness;
    }
}
