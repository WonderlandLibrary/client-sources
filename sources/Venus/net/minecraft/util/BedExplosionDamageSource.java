/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class BedExplosionDamageSource
extends DamageSource {
    protected BedExplosionDamageSource() {
        super("badRespawnPoint");
        this.setDifficultyScaled();
        this.setExplosion();
    }

    @Override
    public ITextComponent getDeathMessage(LivingEntity livingEntity) {
        IFormattableTextComponent iFormattableTextComponent = TextComponentUtils.wrapWithSquareBrackets(new TranslationTextComponent("death.attack.badRespawnPoint.link")).modifyStyle(BedExplosionDamageSource::lambda$getDeathMessage$0);
        return new TranslationTextComponent("death.attack.badRespawnPoint.message", livingEntity.getDisplayName(), iFormattableTextComponent);
    }

    private static Style lambda$getDeathMessage$0(Style style) {
        return style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://bugs.mojang.com/browse/MCPE-28723")).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("MCPE-28723")));
    }
}

