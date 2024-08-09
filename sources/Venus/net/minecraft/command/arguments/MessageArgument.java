/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.command.arguments.EntitySelectorParser;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MessageArgument
implements ArgumentType<Message> {
    private static final Collection<String> EXAMPLES = Arrays.asList("Hello world!", "foo", "@e", "Hello @p :)");

    public static MessageArgument message() {
        return new MessageArgument();
    }

    public static ITextComponent getMessage(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return commandContext.getArgument(string, Message.class).toComponent(commandContext.getSource(), commandContext.getSource().hasPermissionLevel(1));
    }

    @Override
    public Message parse(StringReader stringReader) throws CommandSyntaxException {
        return Message.parse(stringReader, true);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static class Message {
        private final String text;
        private final Part[] selectors;

        public Message(String string, Part[] partArray) {
            this.text = string;
            this.selectors = partArray;
        }

        public ITextComponent toComponent(CommandSource commandSource, boolean bl) throws CommandSyntaxException {
            if (this.selectors.length != 0 && bl) {
                StringTextComponent stringTextComponent = new StringTextComponent(this.text.substring(0, this.selectors[0].getStart()));
                int n = this.selectors[0].getStart();
                for (Part part : this.selectors) {
                    ITextComponent iTextComponent = part.toComponent(commandSource);
                    if (n < part.getStart()) {
                        stringTextComponent.appendString(this.text.substring(n, part.getStart()));
                    }
                    if (iTextComponent != null) {
                        stringTextComponent.append(iTextComponent);
                    }
                    n = part.getEnd();
                }
                if (n < this.text.length()) {
                    stringTextComponent.appendString(this.text.substring(n, this.text.length()));
                }
                return stringTextComponent;
            }
            return new StringTextComponent(this.text);
        }

        public static Message parse(StringReader stringReader, boolean bl) throws CommandSyntaxException {
            String string = stringReader.getString().substring(stringReader.getCursor(), stringReader.getTotalLength());
            if (!bl) {
                stringReader.setCursor(stringReader.getTotalLength());
                return new Message(string, new Part[0]);
            }
            ArrayList<Part> arrayList = Lists.newArrayList();
            int n = stringReader.getCursor();
            while (true) {
                EntitySelector entitySelector;
                int n2;
                block7: {
                    if (!stringReader.canRead()) {
                        return new Message(string, arrayList.toArray(new Part[arrayList.size()]));
                    }
                    if (stringReader.peek() == '@') {
                        n2 = stringReader.getCursor();
                        try {
                            EntitySelectorParser entitySelectorParser = new EntitySelectorParser(stringReader);
                            entitySelector = entitySelectorParser.parse();
                            break block7;
                        } catch (CommandSyntaxException commandSyntaxException) {
                            if (commandSyntaxException.getType() != EntitySelectorParser.SELECTOR_TYPE_MISSING && commandSyntaxException.getType() != EntitySelectorParser.UNKNOWN_SELECTOR_TYPE) {
                                throw commandSyntaxException;
                            }
                            stringReader.setCursor(n2 + 1);
                            continue;
                        }
                    }
                    stringReader.skip();
                    continue;
                }
                arrayList.add(new Part(n2 - n, stringReader.getCursor() - n, entitySelector));
            }
        }
    }

    public static class Part {
        private final int start;
        private final int end;
        private final EntitySelector selector;

        public Part(int n, int n2, EntitySelector entitySelector) {
            this.start = n;
            this.end = n2;
            this.selector = entitySelector;
        }

        public int getStart() {
            return this.start;
        }

        public int getEnd() {
            return this.end;
        }

        @Nullable
        public ITextComponent toComponent(CommandSource commandSource) throws CommandSyntaxException {
            return EntitySelector.joinNames(this.selector.select(commandSource));
        }
    }
}

