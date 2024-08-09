/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.scoreboard;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class Team {
    public boolean isSameTeam(@Nullable Team team) {
        if (team == null) {
            return true;
        }
        return this == team;
    }

    public abstract String getName();

    public abstract IFormattableTextComponent func_230427_d_(ITextComponent var1);

    public abstract boolean getSeeFriendlyInvisiblesEnabled();

    public abstract boolean getAllowFriendlyFire();

    public abstract Visible getNameTagVisibility();

    public abstract TextFormatting getColor();

    public abstract Collection<String> getMembershipCollection();

    public abstract Visible getDeathMessageVisibility();

    public abstract CollisionRule getCollisionRule();

    public static enum Visible {
        ALWAYS("always", 0),
        NEVER("never", 1),
        HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
        HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);

        private static final Map<String, Visible> nameMap;
        public final String internalName;
        public final int id;

        @Nullable
        public static Visible getByName(String string) {
            return nameMap.get(string);
        }

        private Visible(String string2, int n2) {
            this.internalName = string2;
            this.id = n2;
        }

        public ITextComponent getDisplayName() {
            return new TranslationTextComponent("team.visibility." + this.internalName);
        }

        private static Visible lambda$static$1(Visible visible) {
            return visible;
        }

        private static String lambda$static$0(Visible visible) {
            return visible.internalName;
        }

        static {
            nameMap = Arrays.stream(Visible.values()).collect(Collectors.toMap(Visible::lambda$static$0, Visible::lambda$static$1));
        }
    }

    public static enum CollisionRule {
        ALWAYS("always", 0),
        NEVER("never", 1),
        PUSH_OTHER_TEAMS("pushOtherTeams", 2),
        PUSH_OWN_TEAM("pushOwnTeam", 3);

        private static final Map<String, CollisionRule> nameMap;
        public final String name;
        public final int id;

        @Nullable
        public static CollisionRule getByName(String string) {
            return nameMap.get(string);
        }

        private CollisionRule(String string2, int n2) {
            this.name = string2;
            this.id = n2;
        }

        public ITextComponent getDisplayName() {
            return new TranslationTextComponent("team.collision." + this.name);
        }

        private static CollisionRule lambda$static$1(CollisionRule collisionRule) {
            return collisionRule;
        }

        private static String lambda$static$0(CollisionRule collisionRule) {
            return collisionRule.name;
        }

        static {
            nameMap = Arrays.stream(CollisionRule.values()).collect(Collectors.toMap(CollisionRule::lambda$static$0, CollisionRule::lambda$static$1));
        }
    }
}

