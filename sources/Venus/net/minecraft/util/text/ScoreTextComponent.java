/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.command.arguments.EntitySelectorParser;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITargetedTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ScoreTextComponent
extends TextComponent
implements ITargetedTextComponent {
    private final String name;
    @Nullable
    private final EntitySelector selector;
    private final String objective;

    @Nullable
    private static EntitySelector func_240707_c_(String string) {
        try {
            return new EntitySelectorParser(new StringReader(string)).parse();
        } catch (CommandSyntaxException commandSyntaxException) {
            return null;
        }
    }

    public ScoreTextComponent(String string, String string2) {
        this(string, ScoreTextComponent.func_240707_c_(string), string2);
    }

    private ScoreTextComponent(String string, @Nullable EntitySelector entitySelector, String string2) {
        this.name = string;
        this.selector = entitySelector;
        this.objective = string2;
    }

    public String getName() {
        return this.name;
    }

    public String getObjective() {
        return this.objective;
    }

    private String func_240705_a_(CommandSource commandSource) throws CommandSyntaxException {
        List<? extends Entity> list;
        if (this.selector != null && !(list = this.selector.select(commandSource)).isEmpty()) {
            if (list.size() != 1) {
                throw EntityArgument.TOO_MANY_ENTITIES.create();
            }
            return list.get(0).getScoreboardName();
        }
        return this.name;
    }

    private String func_240706_a_(String string, CommandSource commandSource) {
        ScoreObjective scoreObjective;
        ServerScoreboard serverScoreboard;
        MinecraftServer minecraftServer = commandSource.getServer();
        if (minecraftServer != null && (serverScoreboard = minecraftServer.getScoreboard()).entityHasObjective(string, scoreObjective = serverScoreboard.getObjective(this.objective))) {
            Score score = serverScoreboard.getOrCreateScore(string, scoreObjective);
            return Integer.toString(score.getScorePoints());
        }
        return "";
    }

    @Override
    public ScoreTextComponent copyRaw() {
        return new ScoreTextComponent(this.name, this.selector, this.objective);
    }

    @Override
    public IFormattableTextComponent func_230535_a_(@Nullable CommandSource commandSource, @Nullable Entity entity2, int n) throws CommandSyntaxException {
        if (commandSource == null) {
            return new StringTextComponent("");
        }
        String string = this.func_240705_a_(commandSource);
        String string2 = entity2 != null && string.equals("*") ? entity2.getScoreboardName() : string;
        return new StringTextComponent(this.func_240706_a_(string2, commandSource));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof ScoreTextComponent)) {
            return true;
        }
        ScoreTextComponent scoreTextComponent = (ScoreTextComponent)object;
        return this.name.equals(scoreTextComponent.name) && this.objective.equals(scoreTextComponent.objective) && super.equals(object);
    }

    @Override
    public String toString() {
        return "ScoreComponent{name='" + this.name + "'objective='" + this.objective + "', siblings=" + this.siblings + ", style=" + this.getStyle() + "}";
    }

    @Override
    public TextComponent copyRaw() {
        return this.copyRaw();
    }

    @Override
    public IFormattableTextComponent copyRaw() {
        return this.copyRaw();
    }
}

