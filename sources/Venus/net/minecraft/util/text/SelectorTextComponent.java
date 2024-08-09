/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.command.arguments.EntitySelectorParser;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITargetedTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SelectorTextComponent
extends TextComponent
implements ITargetedTextComponent {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String selector;
    @Nullable
    private final EntitySelector field_197670_d;

    public SelectorTextComponent(String string) {
        this.selector = string;
        EntitySelector entitySelector = null;
        try {
            EntitySelectorParser entitySelectorParser = new EntitySelectorParser(new StringReader(string));
            entitySelector = entitySelectorParser.parse();
        } catch (CommandSyntaxException commandSyntaxException) {
            LOGGER.warn("Invalid selector component: {}", (Object)string, (Object)commandSyntaxException.getMessage());
        }
        this.field_197670_d = entitySelector;
    }

    public String getSelector() {
        return this.selector;
    }

    @Override
    public IFormattableTextComponent func_230535_a_(@Nullable CommandSource commandSource, @Nullable Entity entity2, int n) throws CommandSyntaxException {
        return commandSource != null && this.field_197670_d != null ? EntitySelector.joinNames(this.field_197670_d.select(commandSource)) : new StringTextComponent("");
    }

    @Override
    public String getUnformattedComponentText() {
        return this.selector;
    }

    @Override
    public SelectorTextComponent copyRaw() {
        return new SelectorTextComponent(this.selector);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof SelectorTextComponent)) {
            return true;
        }
        SelectorTextComponent selectorTextComponent = (SelectorTextComponent)object;
        return this.selector.equals(selectorTextComponent.selector) && super.equals(object);
    }

    @Override
    public String toString() {
        return "SelectorComponent{pattern='" + this.selector + "', siblings=" + this.siblings + ", style=" + this.getStyle() + "}";
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

