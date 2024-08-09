/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.SuggestionContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandSuggestionHelper {
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("(\\s+)");
    private static final Style EMPTY_ERROR_STYLE = Style.EMPTY.setFormatting(TextFormatting.RED);
    private static final Style EMPTY_PASS_STYLE = Style.EMPTY.setFormatting(TextFormatting.GRAY);
    private static final List<Style> COMMAND_COLOR_STYLES = Stream.of(TextFormatting.AQUA, TextFormatting.YELLOW, TextFormatting.GREEN, TextFormatting.LIGHT_PURPLE, TextFormatting.GOLD).map(Style.EMPTY::setFormatting).collect(ImmutableList.toImmutableList());
    private final Minecraft mc;
    private final Screen screen;
    private final TextFieldWidget inputField;
    private final FontRenderer font;
    private final boolean commandsOnly;
    private final boolean hasCursor;
    private final int minAmountRendered;
    private final int maxAmountRendered;
    private final boolean isChat;
    private final int color;
    private final List<IReorderingProcessor> exceptionList = Lists.newArrayList();
    private int x;
    private int width;
    private ParseResults<ISuggestionProvider> parseResults;
    private CompletableFuture<com.mojang.brigadier.suggestion.Suggestions> suggestionsFuture;
    private Suggestions suggestions;
    private boolean autoSuggest;
    private boolean isApplyingSuggestion;

    public CommandSuggestionHelper(Minecraft minecraft, Screen screen, TextFieldWidget textFieldWidget, FontRenderer fontRenderer, boolean bl, boolean bl2, int n, int n2, boolean bl3, int n3) {
        this.mc = minecraft;
        this.screen = screen;
        this.inputField = textFieldWidget;
        this.font = fontRenderer;
        this.commandsOnly = bl;
        this.hasCursor = bl2;
        this.minAmountRendered = n;
        this.maxAmountRendered = n2;
        this.isChat = bl3;
        this.color = n3;
        textFieldWidget.setTextFormatter(this::getParsedSuggestion);
    }

    public void shouldAutoSuggest(boolean bl) {
        this.autoSuggest = bl;
        if (!bl) {
            this.suggestions = null;
        }
    }

    public boolean onKeyPressed(int n, int n2, int n3) {
        if (this.suggestions != null && this.suggestions.onKeyPressed(n, n2, n3)) {
            return false;
        }
        if (this.screen.getListener() == this.inputField && n == 258) {
            this.updateSuggestions(false);
            return false;
        }
        return true;
    }

    public boolean onScroll(double d) {
        return this.suggestions != null && this.suggestions.onScroll(MathHelper.clamp(d, -1.0, 1.0));
    }

    public boolean onClick(double d, double d2, int n) {
        return this.suggestions != null && this.suggestions.onClick((int)d, (int)d2, n);
    }

    public void updateSuggestions(boolean bl) {
        com.mojang.brigadier.suggestion.Suggestions suggestions;
        if (this.suggestionsFuture != null && this.suggestionsFuture.isDone() && !(suggestions = this.suggestionsFuture.join()).isEmpty()) {
            int n = 0;
            for (Suggestion suggestion : suggestions.getList()) {
                n = Math.max(n, this.font.getStringWidth(suggestion.getText()));
            }
            int n2 = MathHelper.clamp(this.inputField.func_195611_j(suggestions.getRange().getStart()), 0, this.inputField.func_195611_j(0) + this.inputField.getAdjustedWidth() - n);
            int n3 = this.isChat ? this.screen.height - 12 : 72;
            this.suggestions = new Suggestions(this, n2, n3, n, this.getSuggestions(suggestions), bl);
        }
    }

    private List<Suggestion> getSuggestions(com.mojang.brigadier.suggestion.Suggestions suggestions) {
        String string = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
        int n = CommandSuggestionHelper.getLastWhitespace(string);
        String string2 = string.substring(n).toLowerCase(Locale.ROOT);
        ArrayList<Suggestion> arrayList = Lists.newArrayList();
        ArrayList<Suggestion> arrayList2 = Lists.newArrayList();
        for (Suggestion suggestion : suggestions.getList()) {
            if (!suggestion.getText().startsWith(string2) && !suggestion.getText().startsWith("minecraft:" + string2)) {
                arrayList2.add(suggestion);
                continue;
            }
            arrayList.add(suggestion);
        }
        arrayList.addAll(arrayList2);
        return arrayList;
    }

    public void init() {
        boolean bl;
        String string = this.inputField.getText();
        if (this.parseResults != null && !this.parseResults.getReader().getString().equals(string)) {
            this.parseResults = null;
        }
        if (!this.isApplyingSuggestion) {
            this.inputField.setSuggestion(null);
            this.suggestions = null;
        }
        this.exceptionList.clear();
        StringReader stringReader = new StringReader(string);
        boolean bl2 = bl = stringReader.canRead() && stringReader.peek() == '/';
        if (bl) {
            stringReader.skip();
        }
        boolean bl3 = this.commandsOnly || bl;
        int n = this.inputField.getCursorPosition();
        if (bl3) {
            int n2;
            CommandDispatcher<ISuggestionProvider> commandDispatcher = this.mc.player.connection.getCommandDispatcher();
            if (this.parseResults == null) {
                this.parseResults = commandDispatcher.parse(stringReader, (ISuggestionProvider)this.mc.player.connection.getSuggestionProvider());
            }
            int n3 = n2 = this.hasCursor ? stringReader.getCursor() : 1;
            if (!(n < n2 || this.suggestions != null && this.isApplyingSuggestion)) {
                this.suggestionsFuture = commandDispatcher.getCompletionSuggestions(this.parseResults, n);
                this.suggestionsFuture.thenRun(this::lambda$init$0);
            }
        } else {
            String string2 = string.substring(0, n);
            int n4 = CommandSuggestionHelper.getLastWhitespace(string2);
            Collection<String> collection = this.mc.player.connection.getSuggestionProvider().getPlayerNames();
            this.suggestionsFuture = ISuggestionProvider.suggest(collection, new SuggestionsBuilder(string2, n4));
        }
    }

    private static int getLastWhitespace(String string) {
        if (Strings.isNullOrEmpty(string)) {
            return 1;
        }
        int n = 0;
        Matcher matcher = WHITESPACE_PATTERN.matcher(string);
        while (matcher.find()) {
            n = matcher.end();
        }
        return n;
    }

    private static IReorderingProcessor func_243255_a(CommandSyntaxException commandSyntaxException) {
        ITextComponent iTextComponent = TextComponentUtils.toTextComponent(commandSyntaxException.getRawMessage());
        String string = commandSyntaxException.getContext();
        return string == null ? iTextComponent.func_241878_f() : new TranslationTextComponent("command.context.parse_error", iTextComponent, commandSyntaxException.getCursor(), string).func_241878_f();
    }

    private void recompileSuggestions() {
        if (this.inputField.getCursorPosition() == this.inputField.getText().length()) {
            if (this.suggestionsFuture.join().isEmpty() && !this.parseResults.getExceptions().isEmpty()) {
                int n = 0;
                for (Map.Entry<CommandNode<ISuggestionProvider>, CommandSyntaxException> entry : this.parseResults.getExceptions().entrySet()) {
                    CommandSyntaxException commandSyntaxException = entry.getValue();
                    if (commandSyntaxException.getType() == CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect()) {
                        ++n;
                        continue;
                    }
                    this.exceptionList.add(CommandSuggestionHelper.func_243255_a(commandSyntaxException));
                }
                if (n > 0) {
                    this.exceptionList.add(CommandSuggestionHelper.func_243255_a(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().create()));
                }
            } else if (this.parseResults.getReader().canRead()) {
                this.exceptionList.add(CommandSuggestionHelper.func_243255_a(Commands.func_227481_a_(this.parseResults)));
            }
        }
        this.x = 0;
        this.width = this.screen.width;
        if (this.exceptionList.isEmpty()) {
            this.applyFormattingToCommand(TextFormatting.GRAY);
        }
        this.suggestions = null;
        if (this.autoSuggest && this.mc.gameSettings.autoSuggestCommands) {
            this.updateSuggestions(true);
        }
    }

    private void applyFormattingToCommand(TextFormatting textFormatting) {
        CommandContextBuilder<ISuggestionProvider> commandContextBuilder = this.parseResults.getContext();
        SuggestionContext<ISuggestionProvider> suggestionContext = commandContextBuilder.findSuggestionContext(this.inputField.getCursorPosition());
        Map<CommandNode<ISuggestionProvider>, String> map = this.mc.player.connection.getCommandDispatcher().getSmartUsage(suggestionContext.parent, this.mc.player.connection.getSuggestionProvider());
        ArrayList<IReorderingProcessor> arrayList = Lists.newArrayList();
        int n = 0;
        Style style = Style.EMPTY.setFormatting(textFormatting);
        for (Map.Entry<CommandNode<ISuggestionProvider>, String> entry : map.entrySet()) {
            if (entry.getKey() instanceof LiteralCommandNode) continue;
            arrayList.add(IReorderingProcessor.fromString(entry.getValue(), style));
            n = Math.max(n, this.font.getStringWidth(entry.getValue()));
        }
        if (!arrayList.isEmpty()) {
            this.exceptionList.addAll(arrayList);
            this.x = MathHelper.clamp(this.inputField.func_195611_j(suggestionContext.startPos), 0, this.inputField.func_195611_j(0) + this.inputField.getAdjustedWidth() - n);
            this.width = n;
        }
    }

    private IReorderingProcessor getParsedSuggestion(String string, int n) {
        return this.parseResults != null ? CommandSuggestionHelper.getFinalSuggestion(this.parseResults, string, n) : IReorderingProcessor.fromString(string, Style.EMPTY);
    }

    @Nullable
    private static String getMatchedSuggestionText(String string, String string2) {
        return string2.startsWith(string) ? string2.substring(string.length()) : null;
    }

    private static IReorderingProcessor getFinalSuggestion(ParseResults<ISuggestionProvider> parseResults, String string, int n) {
        int n2;
        ArrayList<IReorderingProcessor> arrayList = Lists.newArrayList();
        int n3 = 0;
        int n4 = -1;
        CommandContextBuilder<ISuggestionProvider> commandContextBuilder = parseResults.getContext().getLastChild();
        for (ParsedArgument<ISuggestionProvider, ?> parsedArgument : commandContextBuilder.getArguments().values()) {
            int n5;
            if (++n4 >= COMMAND_COLOR_STYLES.size()) {
                n4 = 0;
            }
            if ((n5 = Math.max(parsedArgument.getRange().getStart() - n, 0)) >= string.length()) break;
            int n6 = Math.min(parsedArgument.getRange().getEnd() - n, string.length());
            if (n6 <= 0) continue;
            arrayList.add(IReorderingProcessor.fromString(string.substring(n3, n5), EMPTY_PASS_STYLE));
            arrayList.add(IReorderingProcessor.fromString(string.substring(n5, n6), COMMAND_COLOR_STYLES.get(n4)));
            n3 = n6;
        }
        if (parseResults.getReader().canRead() && (n2 = Math.max(parseResults.getReader().getCursor() - n, 0)) < string.length()) {
            int n7 = Math.min(n2 + parseResults.getReader().getRemainingLength(), string.length());
            arrayList.add(IReorderingProcessor.fromString(string.substring(n3, n2), EMPTY_PASS_STYLE));
            arrayList.add(IReorderingProcessor.fromString(string.substring(n2, n7), EMPTY_ERROR_STYLE));
            n3 = n7;
        }
        arrayList.add(IReorderingProcessor.fromString(string.substring(n3), EMPTY_PASS_STYLE));
        return IReorderingProcessor.func_242241_a(arrayList);
    }

    public void drawSuggestionList(MatrixStack matrixStack, int n, int n2) {
        if (this.suggestions != null) {
            this.suggestions.drawSuggestions(matrixStack, n, n2);
        } else {
            int n3 = 0;
            for (IReorderingProcessor iReorderingProcessor : this.exceptionList) {
                int n4 = this.isChat ? this.screen.height - 14 - 13 - 12 * n3 : 72 + 12 * n3;
                AbstractGui.fill(matrixStack, this.x - 1, n4, this.x + this.width + 1, n4 + 12, this.color);
                this.font.func_238407_a_(matrixStack, iReorderingProcessor, this.x, n4 + 2, -1);
                ++n3;
            }
        }
    }

    public String getSuggestionMessage() {
        return this.suggestions != null ? "\n" + this.suggestions.getCurrentSuggestionMessage() : "";
    }

    private void lambda$init$0() {
        if (this.suggestionsFuture.isDone()) {
            this.recompileSuggestions();
        }
    }

    public class Suggestions {
        private final Rectangle2d suggestionRenderBox;
        private final String originalInputText;
        private final List<Suggestion> suggestions;
        private int lowestDisplayedSuggestionIndex;
        private int selectedIndex;
        private Vector2f lastMousePosition;
        private boolean changeSelectionOnNextTabInput;
        private int lastObtainedSuggestionMessageIndex;
        final CommandSuggestionHelper this$0;

        private Suggestions(CommandSuggestionHelper commandSuggestionHelper, int n, int n2, int n3, List<Suggestion> list, boolean bl) {
            this.this$0 = commandSuggestionHelper;
            this.lastMousePosition = Vector2f.ZERO;
            int n4 = n - 1;
            int n5 = commandSuggestionHelper.isChat ? n2 - 3 - Math.min(list.size(), commandSuggestionHelper.maxAmountRendered) * 12 : n2;
            this.suggestionRenderBox = new Rectangle2d(n4, n5, n3 + 1, Math.min(list.size(), commandSuggestionHelper.maxAmountRendered) * 12);
            this.originalInputText = commandSuggestionHelper.inputField.getText();
            this.lastObtainedSuggestionMessageIndex = bl ? -1 : 0;
            this.suggestions = list;
            this.selectSuggestion(0);
        }

        public void drawSuggestions(MatrixStack matrixStack, int n, int n2) {
            Message message;
            int n3;
            boolean bl;
            int n4 = Math.min(this.suggestions.size(), this.this$0.maxAmountRendered);
            int n5 = -5592406;
            boolean bl2 = this.lowestDisplayedSuggestionIndex > 0;
            boolean bl3 = this.suggestions.size() > this.lowestDisplayedSuggestionIndex + n4;
            boolean bl4 = bl2 || bl3;
            boolean bl5 = bl = this.lastMousePosition.x != (float)n || this.lastMousePosition.y != (float)n2;
            if (bl) {
                this.lastMousePosition = new Vector2f(n, n2);
            }
            if (bl4) {
                AbstractGui.fill(matrixStack, this.suggestionRenderBox.getX(), this.suggestionRenderBox.getY() - 1, this.suggestionRenderBox.getX() + this.suggestionRenderBox.getWidth(), this.suggestionRenderBox.getY(), this.this$0.color);
                AbstractGui.fill(matrixStack, this.suggestionRenderBox.getX(), this.suggestionRenderBox.getY() + this.suggestionRenderBox.getHeight(), this.suggestionRenderBox.getX() + this.suggestionRenderBox.getWidth(), this.suggestionRenderBox.getY() + this.suggestionRenderBox.getHeight() + 1, this.this$0.color);
                if (bl2) {
                    for (n3 = 0; n3 < this.suggestionRenderBox.getWidth(); ++n3) {
                        if (n3 % 2 != 0) continue;
                        AbstractGui.fill(matrixStack, this.suggestionRenderBox.getX() + n3, this.suggestionRenderBox.getY() - 1, this.suggestionRenderBox.getX() + n3 + 1, this.suggestionRenderBox.getY(), -1);
                    }
                }
                if (bl3) {
                    for (n3 = 0; n3 < this.suggestionRenderBox.getWidth(); ++n3) {
                        if (n3 % 2 != 0) continue;
                        AbstractGui.fill(matrixStack, this.suggestionRenderBox.getX() + n3, this.suggestionRenderBox.getY() + this.suggestionRenderBox.getHeight(), this.suggestionRenderBox.getX() + n3 + 1, this.suggestionRenderBox.getY() + this.suggestionRenderBox.getHeight() + 1, -1);
                    }
                }
            }
            n3 = 0;
            for (int i = 0; i < n4; ++i) {
                Suggestion suggestion = this.suggestions.get(i + this.lowestDisplayedSuggestionIndex);
                AbstractGui.fill(matrixStack, this.suggestionRenderBox.getX(), this.suggestionRenderBox.getY() + 12 * i, this.suggestionRenderBox.getX() + this.suggestionRenderBox.getWidth(), this.suggestionRenderBox.getY() + 12 * i + 12, this.this$0.color);
                if (n > this.suggestionRenderBox.getX() && n < this.suggestionRenderBox.getX() + this.suggestionRenderBox.getWidth() && n2 > this.suggestionRenderBox.getY() + 12 * i && n2 < this.suggestionRenderBox.getY() + 12 * i + 12) {
                    if (bl) {
                        this.selectSuggestion(i + this.lowestDisplayedSuggestionIndex);
                    }
                    n3 = 1;
                }
                this.this$0.font.drawStringWithShadow(matrixStack, suggestion.getText(), this.suggestionRenderBox.getX() + 1, this.suggestionRenderBox.getY() + 2 + 12 * i, i + this.lowestDisplayedSuggestionIndex == this.selectedIndex ? -256 : -5592406);
            }
            if (n3 != 0 && (message = this.suggestions.get(this.selectedIndex).getTooltip()) != null) {
                this.this$0.screen.renderTooltip(matrixStack, TextComponentUtils.toTextComponent(message), n, n2);
            }
        }

        public boolean onClick(int n, int n2, int n3) {
            if (!this.suggestionRenderBox.contains(n, n2)) {
                return true;
            }
            int n4 = (n2 - this.suggestionRenderBox.getY()) / 12 + this.lowestDisplayedSuggestionIndex;
            if (n4 >= 0 && n4 < this.suggestions.size()) {
                this.selectSuggestion(n4);
                this.applySuggestionToInput();
            }
            return false;
        }

        public boolean onScroll(double d) {
            int n;
            int n2 = (int)(this.this$0.mc.mouseHelper.getMouseX() * (double)this.this$0.mc.getMainWindow().getScaledWidth() / (double)this.this$0.mc.getMainWindow().getWidth());
            if (this.suggestionRenderBox.contains(n2, n = (int)(this.this$0.mc.mouseHelper.getMouseY() * (double)this.this$0.mc.getMainWindow().getScaledHeight() / (double)this.this$0.mc.getMainWindow().getHeight()))) {
                this.lowestDisplayedSuggestionIndex = MathHelper.clamp((int)((double)this.lowestDisplayedSuggestionIndex - d), 0, Math.max(this.suggestions.size() - this.this$0.maxAmountRendered, 0));
                return false;
            }
            return true;
        }

        public boolean onKeyPressed(int n, int n2, int n3) {
            if (n == 265) {
                this.changeSelection(-1);
                this.changeSelectionOnNextTabInput = false;
                return false;
            }
            if (n == 264) {
                this.changeSelection(1);
                this.changeSelectionOnNextTabInput = false;
                return false;
            }
            if (n == 258) {
                if (this.changeSelectionOnNextTabInput) {
                    this.changeSelection(Screen.hasShiftDown() ? -1 : 1);
                }
                this.applySuggestionToInput();
                return false;
            }
            if (n == 256) {
                this.clearSuggestions();
                return false;
            }
            return true;
        }

        public void changeSelection(int n) {
            this.selectSuggestion(this.selectedIndex + n);
            int n2 = this.lowestDisplayedSuggestionIndex;
            int n3 = this.lowestDisplayedSuggestionIndex + this.this$0.maxAmountRendered - 1;
            if (this.selectedIndex < n2) {
                this.lowestDisplayedSuggestionIndex = MathHelper.clamp(this.selectedIndex, 0, Math.max(this.suggestions.size() - this.this$0.maxAmountRendered, 0));
            } else if (this.selectedIndex > n3) {
                this.lowestDisplayedSuggestionIndex = MathHelper.clamp(this.selectedIndex + this.this$0.minAmountRendered - this.this$0.maxAmountRendered, 0, Math.max(this.suggestions.size() - this.this$0.maxAmountRendered, 0));
            }
        }

        public void selectSuggestion(int n) {
            this.selectedIndex = n;
            if (this.selectedIndex < 0) {
                this.selectedIndex += this.suggestions.size();
            }
            if (this.selectedIndex >= this.suggestions.size()) {
                this.selectedIndex -= this.suggestions.size();
            }
            Suggestion suggestion = this.suggestions.get(this.selectedIndex);
            this.this$0.inputField.setSuggestion(CommandSuggestionHelper.getMatchedSuggestionText(this.this$0.inputField.getText(), suggestion.apply(this.originalInputText)));
            if (NarratorChatListener.INSTANCE.isActive() && this.lastObtainedSuggestionMessageIndex != this.selectedIndex) {
                NarratorChatListener.INSTANCE.say(this.getCurrentSuggestionMessage());
            }
        }

        public void applySuggestionToInput() {
            Suggestion suggestion = this.suggestions.get(this.selectedIndex);
            this.this$0.isApplyingSuggestion = true;
            this.this$0.inputField.setText(suggestion.apply(this.originalInputText));
            int n = suggestion.getRange().getStart() + suggestion.getText().length();
            this.this$0.inputField.clampCursorPosition(n);
            this.this$0.inputField.setSelectionPos(n);
            this.selectSuggestion(this.selectedIndex);
            this.this$0.isApplyingSuggestion = false;
            this.changeSelectionOnNextTabInput = true;
        }

        private String getCurrentSuggestionMessage() {
            this.lastObtainedSuggestionMessageIndex = this.selectedIndex;
            Suggestion suggestion = this.suggestions.get(this.selectedIndex);
            Message message = suggestion.getTooltip();
            return message != null ? I18n.format("narration.suggestion.tooltip", this.selectedIndex + 1, this.suggestions.size(), suggestion.getText(), message.getString()) : I18n.format("narration.suggestion", this.selectedIndex + 1, this.suggestions.size(), suggestion.getText());
        }

        public void clearSuggestions() {
            this.this$0.suggestions = null;
        }
    }
}

