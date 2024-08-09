/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class OperationArgument
implements ArgumentType<IOperation> {
    private static final Collection<String> EXAMPLES = Arrays.asList("=", ">", "<");
    private static final SimpleCommandExceptionType OPERATION_INVALID = new SimpleCommandExceptionType(new TranslationTextComponent("arguments.operation.invalid"));
    private static final SimpleCommandExceptionType OPERATION_DIVIDE_BY_ZERO = new SimpleCommandExceptionType(new TranslationTextComponent("arguments.operation.div0"));

    public static OperationArgument operation() {
        return new OperationArgument();
    }

    public static IOperation getOperation(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return commandContext.getArgument(string, IOperation.class);
    }

    @Override
    public IOperation parse(StringReader stringReader) throws CommandSyntaxException {
        if (!stringReader.canRead()) {
            throw OPERATION_INVALID.create();
        }
        int n = stringReader.getCursor();
        while (stringReader.canRead() && stringReader.peek() != ' ') {
            stringReader.skip();
        }
        return OperationArgument.parseOperation(stringReader.getString().substring(n, stringReader.getCursor()));
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return ISuggestionProvider.suggest(new String[]{"=", "+=", "-=", "*=", "/=", "%=", "<", ">", "><"}, suggestionsBuilder);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    private static IOperation parseOperation(String string) throws CommandSyntaxException {
        return string.equals("><") ? OperationArgument::lambda$parseOperation$0 : OperationArgument.parseOperation0(string);
    }

    private static IIntOperation parseOperation0(String string) throws CommandSyntaxException {
        int n = -1;
        switch (string.hashCode()) {
            case 60: {
                if (!string.equals("<")) break;
                n = 6;
                break;
            }
            case 61: {
                if (!string.equals("=")) break;
                n = 0;
                break;
            }
            case 62: {
                if (!string.equals(">")) break;
                n = 7;
                break;
            }
            case 1208: {
                if (!string.equals("%=")) break;
                n = 5;
                break;
            }
            case 1363: {
                if (!string.equals("*=")) break;
                n = 3;
                break;
            }
            case 1394: {
                if (!string.equals("+=")) break;
                n = 1;
                break;
            }
            case 1456: {
                if (!string.equals("-=")) break;
                n = 2;
                break;
            }
            case 1518: {
                if (!string.equals("/=")) break;
                n = 4;
            }
        }
        switch (n) {
            case 0: {
                return OperationArgument::lambda$parseOperation0$1;
            }
            case 1: {
                return OperationArgument::lambda$parseOperation0$2;
            }
            case 2: {
                return OperationArgument::lambda$parseOperation0$3;
            }
            case 3: {
                return OperationArgument::lambda$parseOperation0$4;
            }
            case 4: {
                return OperationArgument::lambda$parseOperation0$5;
            }
            case 5: {
                return OperationArgument::lambda$parseOperation0$6;
            }
            case 6: {
                return Math::min;
            }
            case 7: {
                return Math::max;
            }
        }
        throw OPERATION_INVALID.create();
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static int lambda$parseOperation0$6(int n, int n2) throws CommandSyntaxException {
        if (n2 == 0) {
            throw OPERATION_DIVIDE_BY_ZERO.create();
        }
        return MathHelper.normalizeAngle(n, n2);
    }

    private static int lambda$parseOperation0$5(int n, int n2) throws CommandSyntaxException {
        if (n2 == 0) {
            throw OPERATION_DIVIDE_BY_ZERO.create();
        }
        return MathHelper.intFloorDiv(n, n2);
    }

    private static int lambda$parseOperation0$4(int n, int n2) throws CommandSyntaxException {
        return n * n2;
    }

    private static int lambda$parseOperation0$3(int n, int n2) throws CommandSyntaxException {
        return n - n2;
    }

    private static int lambda$parseOperation0$2(int n, int n2) throws CommandSyntaxException {
        return n + n2;
    }

    private static int lambda$parseOperation0$1(int n, int n2) throws CommandSyntaxException {
        return n2;
    }

    private static void lambda$parseOperation$0(Score score, Score score2) throws CommandSyntaxException {
        int n = score.getScorePoints();
        score.setScorePoints(score2.getScorePoints());
        score2.setScorePoints(n);
    }

    @FunctionalInterface
    public static interface IOperation {
        public void apply(Score var1, Score var2) throws CommandSyntaxException;
    }

    @FunctionalInterface
    static interface IIntOperation
    extends IOperation {
        public int apply(int var1, int var2) throws CommandSyntaxException;

        @Override
        default public void apply(Score score, Score score2) throws CommandSyntaxException {
            score.setScorePoints(this.apply(score.getScorePoints(), score2.getScorePoints()));
        }
    }
}

