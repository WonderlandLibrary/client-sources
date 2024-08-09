/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BannerPatternItem
extends Item {
    private final BannerPattern pattern;

    public BannerPatternItem(BannerPattern bannerPattern, Item.Properties properties) {
        super(properties);
        this.pattern = bannerPattern;
    }

    public BannerPattern getBannerPattern() {
        return this.pattern;
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        list.add(this.func_219981_d_().mergeStyle(TextFormatting.GRAY));
    }

    public IFormattableTextComponent func_219981_d_() {
        return new TranslationTextComponent(this.getTranslationKey() + ".desc");
    }
}

