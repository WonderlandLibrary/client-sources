/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.scoreboard;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;

public class ScorePlayerTeam
extends Team {
    private final Scoreboard scoreboard;
    private final String name;
    private final Set<String> membershipSet = Sets.newHashSet();
    private ITextComponent displayName;
    private ITextComponent prefix = StringTextComponent.EMPTY;
    private ITextComponent suffix = StringTextComponent.EMPTY;
    private boolean allowFriendlyFire = true;
    private boolean canSeeFriendlyInvisibles = true;
    private Team.Visible nameTagVisibility = Team.Visible.ALWAYS;
    private Team.Visible deathMessageVisibility = Team.Visible.ALWAYS;
    private TextFormatting color = TextFormatting.RESET;
    private Team.CollisionRule collisionRule = Team.CollisionRule.ALWAYS;
    private final Style field_237499_m_;

    public ScorePlayerTeam(Scoreboard scoreboard, String string) {
        this.scoreboard = scoreboard;
        this.name = string;
        this.displayName = new StringTextComponent(string);
        this.field_237499_m_ = Style.EMPTY.setInsertion(string).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent(string)));
    }

    @Override
    public String getName() {
        return this.name;
    }

    public ITextComponent getDisplayName() {
        return this.displayName;
    }

    public IFormattableTextComponent func_237501_d_() {
        IFormattableTextComponent iFormattableTextComponent = TextComponentUtils.wrapWithSquareBrackets(this.displayName.deepCopy().mergeStyle(this.field_237499_m_));
        TextFormatting textFormatting = this.getColor();
        if (textFormatting != TextFormatting.RESET) {
            iFormattableTextComponent.mergeStyle(textFormatting);
        }
        return iFormattableTextComponent;
    }

    public void setDisplayName(ITextComponent iTextComponent) {
        if (iTextComponent == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.displayName = iTextComponent;
        this.scoreboard.onTeamChanged(this);
    }

    public void setPrefix(@Nullable ITextComponent iTextComponent) {
        this.prefix = iTextComponent == null ? StringTextComponent.EMPTY : iTextComponent;
        this.scoreboard.onTeamChanged(this);
    }

    public ITextComponent getPrefix() {
        return this.prefix;
    }

    public void setSuffix(@Nullable ITextComponent iTextComponent) {
        this.suffix = iTextComponent == null ? StringTextComponent.EMPTY : iTextComponent;
        this.scoreboard.onTeamChanged(this);
    }

    public ITextComponent getSuffix() {
        return this.suffix;
    }

    @Override
    public Collection<String> getMembershipCollection() {
        return this.membershipSet;
    }

    @Override
    public IFormattableTextComponent func_230427_d_(ITextComponent iTextComponent) {
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent("").append(this.prefix).append(iTextComponent).append(this.suffix);
        TextFormatting textFormatting = this.getColor();
        if (textFormatting != TextFormatting.RESET) {
            iFormattableTextComponent.mergeStyle(textFormatting);
        }
        return iFormattableTextComponent;
    }

    public static IFormattableTextComponent formatPlayerName(@Nullable Team team, ITextComponent iTextComponent) {
        return team == null ? iTextComponent.deepCopy() : team.func_230427_d_(iTextComponent);
    }

    @Override
    public boolean getAllowFriendlyFire() {
        return this.allowFriendlyFire;
    }

    public void setAllowFriendlyFire(boolean bl) {
        this.allowFriendlyFire = bl;
        this.scoreboard.onTeamChanged(this);
    }

    @Override
    public boolean getSeeFriendlyInvisiblesEnabled() {
        return this.canSeeFriendlyInvisibles;
    }

    public void setSeeFriendlyInvisiblesEnabled(boolean bl) {
        this.canSeeFriendlyInvisibles = bl;
        this.scoreboard.onTeamChanged(this);
    }

    @Override
    public Team.Visible getNameTagVisibility() {
        return this.nameTagVisibility;
    }

    @Override
    public Team.Visible getDeathMessageVisibility() {
        return this.deathMessageVisibility;
    }

    public void setNameTagVisibility(Team.Visible visible) {
        this.nameTagVisibility = visible;
        this.scoreboard.onTeamChanged(this);
    }

    public void setDeathMessageVisibility(Team.Visible visible) {
        this.deathMessageVisibility = visible;
        this.scoreboard.onTeamChanged(this);
    }

    @Override
    public Team.CollisionRule getCollisionRule() {
        return this.collisionRule;
    }

    public void setCollisionRule(Team.CollisionRule collisionRule) {
        this.collisionRule = collisionRule;
        this.scoreboard.onTeamChanged(this);
    }

    public int getFriendlyFlags() {
        int n = 0;
        if (this.getAllowFriendlyFire()) {
            n |= 1;
        }
        if (this.getSeeFriendlyInvisiblesEnabled()) {
            n |= 2;
        }
        return n;
    }

    public void setFriendlyFlags(int n) {
        this.setAllowFriendlyFire((n & 1) > 0);
        this.setSeeFriendlyInvisiblesEnabled((n & 2) > 0);
    }

    public void setColor(TextFormatting textFormatting) {
        this.color = textFormatting;
        this.scoreboard.onTeamChanged(this);
    }

    @Override
    public TextFormatting getColor() {
        return this.color;
    }
}

