/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.scoreboard;

import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.HoverEvent;

public class ScoreObjective {
    private final Scoreboard scoreboard;
    private final String name;
    private final ScoreCriteria objectiveCriteria;
    private ITextComponent displayName;
    private ITextComponent field_237496_e_;
    private ScoreCriteria.RenderType renderType;

    public ScoreObjective(Scoreboard scoreboard, String string, ScoreCriteria scoreCriteria, ITextComponent iTextComponent, ScoreCriteria.RenderType renderType) {
        this.scoreboard = scoreboard;
        this.name = string;
        this.objectiveCriteria = scoreCriteria;
        this.displayName = iTextComponent;
        this.field_237496_e_ = this.func_237498_g_();
        this.renderType = renderType;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public String getName() {
        return this.name;
    }

    public ScoreCriteria getCriteria() {
        return this.objectiveCriteria;
    }

    public ITextComponent getDisplayName() {
        return this.displayName;
    }

    private ITextComponent func_237498_g_() {
        return TextComponentUtils.wrapWithSquareBrackets(this.displayName.deepCopy().modifyStyle(this::lambda$func_237498_g_$0));
    }

    public ITextComponent func_197890_e() {
        return this.field_237496_e_;
    }

    public void setDisplayName(ITextComponent iTextComponent) {
        this.displayName = iTextComponent;
        this.field_237496_e_ = this.func_237498_g_();
        this.scoreboard.onObjectiveChanged(this);
    }

    public ScoreCriteria.RenderType getRenderType() {
        return this.renderType;
    }

    public void setRenderType(ScoreCriteria.RenderType renderType) {
        this.renderType = renderType;
        this.scoreboard.onObjectiveChanged(this);
    }

    private Style lambda$func_237498_g_$0(Style style) {
        return style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent(this.name)));
    }
}

