/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class MapDecoration {
    private final Type type;
    private byte x;
    private byte y;
    private byte rotation;
    private final ITextComponent customName;

    public MapDecoration(Type type, byte by, byte by2, byte by3, @Nullable ITextComponent iTextComponent) {
        this.type = type;
        this.x = by;
        this.y = by2;
        this.rotation = by3;
        this.customName = iTextComponent;
    }

    public byte getImage() {
        return this.type.getIcon();
    }

    public Type getType() {
        return this.type;
    }

    public byte getX() {
        return this.x;
    }

    public byte getY() {
        return this.y;
    }

    public byte getRotation() {
        return this.rotation;
    }

    public boolean renderOnFrame() {
        return this.type.isRenderedOnFrame();
    }

    @Nullable
    public ITextComponent getCustomName() {
        return this.customName;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof MapDecoration)) {
            return true;
        }
        MapDecoration mapDecoration = (MapDecoration)object;
        if (this.type != mapDecoration.type) {
            return true;
        }
        if (this.rotation != mapDecoration.rotation) {
            return true;
        }
        if (this.x != mapDecoration.x) {
            return true;
        }
        if (this.y != mapDecoration.y) {
            return true;
        }
        return Objects.equals(this.customName, mapDecoration.customName);
    }

    public int hashCode() {
        int n = this.type.getIcon();
        n = 31 * n + this.x;
        n = 31 * n + this.y;
        n = 31 * n + this.rotation;
        return 31 * n + Objects.hashCode(this.customName);
    }

    public static enum Type {
        PLAYER(false),
        FRAME(true),
        RED_MARKER(false),
        BLUE_MARKER(false),
        TARGET_X(true),
        TARGET_POINT(true),
        PLAYER_OFF_MAP(false),
        PLAYER_OFF_LIMITS(false),
        MANSION(true, 5393476),
        MONUMENT(true, 3830373),
        BANNER_WHITE(true),
        BANNER_ORANGE(true),
        BANNER_MAGENTA(true),
        BANNER_LIGHT_BLUE(true),
        BANNER_YELLOW(true),
        BANNER_LIME(true),
        BANNER_PINK(true),
        BANNER_GRAY(true),
        BANNER_LIGHT_GRAY(true),
        BANNER_CYAN(true),
        BANNER_PURPLE(true),
        BANNER_BLUE(true),
        BANNER_BROWN(true),
        BANNER_GREEN(true),
        BANNER_RED(true),
        BANNER_BLACK(true),
        RED_X(true);

        private final byte icon = (byte)this.ordinal();
        private final boolean renderedOnFrame;
        private final int mapColor;

        private Type(boolean bl) {
            this(bl, -1);
        }

        private Type(boolean bl, int n2) {
            this.renderedOnFrame = bl;
            this.mapColor = n2;
        }

        public byte getIcon() {
            return this.icon;
        }

        public boolean isRenderedOnFrame() {
            return this.renderedOnFrame;
        }

        public boolean hasMapColor() {
            return this.mapColor >= 0;
        }

        public int getMapColor() {
            return this.mapColor;
        }

        public static Type byIcon(byte by) {
            return Type.values()[MathHelper.clamp(by, 0, Type.values().length - 1)];
        }
    }
}

