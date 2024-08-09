/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.render.font;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public final class FontData {
    @SerializedName(value="atlas")
    private AtlasData atlas;
    @SerializedName(value="metrics")
    private MetricsData metrics;
    @SerializedName(value="glyphs")
    private List<GlyphData> glyphs;
    @SerializedName(value="kerning")
    private List<KerningData> kernings;

    public AtlasData atlas() {
        return this.atlas;
    }

    public MetricsData metrics() {
        return this.metrics;
    }

    public List<GlyphData> glyphs() {
        return this.glyphs;
    }

    public List<KerningData> kernings() {
        return this.kernings;
    }

    public static final class AtlasData {
        @SerializedName(value="distanceRange")
        private float range;
        @SerializedName(value="width")
        private float width;
        @SerializedName(value="height")
        private float height;

        public float range() {
            return this.range;
        }

        public float width() {
            return this.width;
        }

        public float height() {
            return this.height;
        }
    }

    public static final class MetricsData {
        @SerializedName(value="lineHeight")
        private float lineHeight;
        @SerializedName(value="ascender")
        private float ascender;
        @SerializedName(value="descender")
        private float descender;

        public float lineHeight() {
            return this.lineHeight;
        }

        public float ascender() {
            return this.ascender;
        }

        public float descender() {
            return this.descender;
        }

        public float baselineHeight() {
            return this.lineHeight + this.descender;
        }
    }

    public static final class KerningData {
        @SerializedName(value="unicode1")
        private int leftChar;
        @SerializedName(value="unicode2")
        private int rightChar;
        @SerializedName(value="advance")
        private float advance;

        public int leftChar() {
            return this.leftChar;
        }

        public int rightChar() {
            return this.rightChar;
        }

        public float advance() {
            return this.advance;
        }
    }

    public static final class BoundsData {
        @SerializedName(value="left")
        private float left;
        @SerializedName(value="top")
        private float top;
        @SerializedName(value="right")
        private float right;
        @SerializedName(value="bottom")
        private float bottom;

        public float left() {
            return this.left;
        }

        public float top() {
            return this.top;
        }

        public float right() {
            return this.right;
        }

        public float bottom() {
            return this.bottom;
        }
    }

    public static final class GlyphData {
        @SerializedName(value="unicode")
        private int unicode;
        @SerializedName(value="advance")
        private float advance;
        @SerializedName(value="planeBounds")
        private BoundsData planeBounds;
        @SerializedName(value="atlasBounds")
        private BoundsData atlasBounds;

        public int unicode() {
            return this.unicode;
        }

        public float advance() {
            return this.advance;
        }

        public BoundsData planeBounds() {
            return this.planeBounds;
        }

        public BoundsData atlasBounds() {
            return this.atlasBounds;
        }
    }
}

