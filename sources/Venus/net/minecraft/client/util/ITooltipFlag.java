/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

public interface ITooltipFlag {
    public boolean isAdvanced();

    public static enum TooltipFlags implements ITooltipFlag
    {
        NORMAL(false),
        ADVANCED(true);

        private final boolean isAdvanced;

        private TooltipFlags(boolean bl) {
            this.isAdvanced = bl;
        }

        @Override
        public boolean isAdvanced() {
            return this.isAdvanced;
        }
    }
}

