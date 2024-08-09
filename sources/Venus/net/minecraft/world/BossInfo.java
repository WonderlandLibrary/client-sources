/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.UUID;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public abstract class BossInfo {
    private final UUID uniqueId;
    protected ITextComponent name;
    protected float percent;
    protected Color color;
    protected Overlay overlay;
    protected boolean darkenSky;
    protected boolean playEndBossMusic;
    protected boolean createFog;

    public BossInfo(UUID uUID, ITextComponent iTextComponent, Color color, Overlay overlay) {
        this.uniqueId = uUID;
        this.name = iTextComponent;
        this.color = color;
        this.overlay = overlay;
        this.percent = 1.0f;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public ITextComponent getName() {
        return this.name;
    }

    public void setName(ITextComponent iTextComponent) {
        this.name = iTextComponent;
    }

    public float getPercent() {
        return this.percent;
    }

    public void setPercent(float f) {
        this.percent = f;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Overlay getOverlay() {
        return this.overlay;
    }

    public void setOverlay(Overlay overlay) {
        this.overlay = overlay;
    }

    public boolean shouldDarkenSky() {
        return this.darkenSky;
    }

    public BossInfo setDarkenSky(boolean bl) {
        this.darkenSky = bl;
        return this;
    }

    public boolean shouldPlayEndBossMusic() {
        return this.playEndBossMusic;
    }

    public BossInfo setPlayEndBossMusic(boolean bl) {
        this.playEndBossMusic = bl;
        return this;
    }

    public BossInfo setCreateFog(boolean bl) {
        this.createFog = bl;
        return this;
    }

    public boolean shouldCreateFog() {
        return this.createFog;
    }

    public static enum Color {
        PINK("pink", TextFormatting.RED),
        BLUE("blue", TextFormatting.BLUE),
        RED("red", TextFormatting.DARK_RED),
        GREEN("green", TextFormatting.GREEN),
        YELLOW("yellow", TextFormatting.YELLOW),
        PURPLE("purple", TextFormatting.DARK_BLUE),
        WHITE("white", TextFormatting.WHITE);

        private final String name;
        private final TextFormatting formatting;

        private Color(String string2, TextFormatting textFormatting) {
            this.name = string2;
            this.formatting = textFormatting;
        }

        public TextFormatting getFormatting() {
            return this.formatting;
        }

        public String getName() {
            return this.name;
        }

        public static Color byName(String string) {
            for (Color color : Color.values()) {
                if (!color.name.equals(string)) continue;
                return color;
            }
            return WHITE;
        }
    }

    public static enum Overlay {
        PROGRESS("progress"),
        NOTCHED_6("notched_6"),
        NOTCHED_10("notched_10"),
        NOTCHED_12("notched_12"),
        NOTCHED_20("notched_20");

        private final String name;

        private Overlay(String string2) {
            this.name = string2;
        }

        public String getName() {
            return this.name;
        }

        public static Overlay byName(String string) {
            for (Overlay overlay : Overlay.values()) {
                if (!overlay.name.equals(string)) continue;
                return overlay;
            }
            return PROGRESS;
        }
    }
}

