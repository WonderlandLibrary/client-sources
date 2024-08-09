/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITargetedTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponentFormatException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TranslationTextComponent
extends TextComponent
implements ITargetedTextComponent {
    private static final Object[] field_240753_d_ = new Object[0];
    private static final ITextProperties field_240754_e_ = ITextProperties.func_240652_a_("%");
    private static final ITextProperties field_240755_f_ = ITextProperties.func_240652_a_("null");
    private final String key;
    private final Object[] formatArgs;
    @Nullable
    private LanguageMap field_240756_i_;
    private final List<ITextProperties> children = Lists.newArrayList();
    private static final Pattern STRING_VARIABLE_PATTERN = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

    public TranslationTextComponent(String string) {
        this.key = string;
        this.formatArgs = field_240753_d_;
    }

    public TranslationTextComponent(String string, Object ... objectArray) {
        this.key = string;
        this.formatArgs = objectArray;
    }

    private void ensureInitialized() {
        LanguageMap languageMap = LanguageMap.getInstance();
        if (languageMap != this.field_240756_i_) {
            this.field_240756_i_ = languageMap;
            this.children.clear();
            String string = languageMap.func_230503_a_(this.key);
            try {
                this.func_240758_a_(string);
            } catch (TranslationTextComponentFormatException translationTextComponentFormatException) {
                this.children.clear();
                this.children.add(ITextProperties.func_240652_a_(string));
            }
        }
    }

    private void func_240758_a_(String string) {
        Matcher matcher = STRING_VARIABLE_PATTERN.matcher(string);
        try {
            int n = 0;
            int n2 = 0;
            while (matcher.find(n2)) {
                String string2;
                int n3 = matcher.start();
                int n4 = matcher.end();
                if (n3 > n2) {
                    string2 = string.substring(n2, n3);
                    if (string2.indexOf(37) != -1) {
                        throw new IllegalArgumentException();
                    }
                    this.children.add(ITextProperties.func_240652_a_(string2));
                }
                string2 = matcher.group(2);
                String string3 = string.substring(n3, n4);
                if ("%".equals(string2) && "%%".equals(string3)) {
                    this.children.add(field_240754_e_);
                } else {
                    int n5;
                    if (!"s".equals(string2)) {
                        throw new TranslationTextComponentFormatException(this, "Unsupported format: '" + string3 + "'");
                    }
                    String string4 = matcher.group(1);
                    int n6 = n5 = string4 != null ? Integer.parseInt(string4) - 1 : n++;
                    if (n5 < this.formatArgs.length) {
                        this.children.add(this.func_240757_a_(n5));
                    }
                }
                n2 = n4;
            }
            if (n2 < string.length()) {
                String string5 = string.substring(n2);
                if (string5.indexOf(37) != -1) {
                    throw new IllegalArgumentException();
                }
                this.children.add(ITextProperties.func_240652_a_(string5));
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new TranslationTextComponentFormatException(this, (Throwable)illegalArgumentException);
        }
    }

    private ITextProperties func_240757_a_(int n) {
        if (n >= this.formatArgs.length) {
            throw new TranslationTextComponentFormatException(this, n);
        }
        Object object = this.formatArgs[n];
        if (object instanceof ITextComponent) {
            return (ITextComponent)object;
        }
        return object == null ? field_240755_f_ : ITextProperties.func_240652_a_(object.toString());
    }

    @Override
    public TranslationTextComponent copyRaw() {
        return new TranslationTextComponent(this.key, this.formatArgs);
    }

    @Override
    public <T> Optional<T> func_230534_b_(ITextProperties.IStyledTextAcceptor<T> iStyledTextAcceptor, Style style) {
        this.ensureInitialized();
        for (ITextProperties iTextProperties : this.children) {
            Optional<T> optional = iTextProperties.getComponentWithStyle(iStyledTextAcceptor, style);
            if (!optional.isPresent()) continue;
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> func_230533_b_(ITextProperties.ITextAcceptor<T> iTextAcceptor) {
        this.ensureInitialized();
        for (ITextProperties iTextProperties : this.children) {
            Optional<T> optional = iTextProperties.getComponent(iTextAcceptor);
            if (!optional.isPresent()) continue;
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public IFormattableTextComponent func_230535_a_(@Nullable CommandSource commandSource, @Nullable Entity entity2, int n) throws CommandSyntaxException {
        Object[] objectArray = new Object[this.formatArgs.length];
        for (int i = 0; i < objectArray.length; ++i) {
            Object object = this.formatArgs[i];
            objectArray[i] = object instanceof ITextComponent ? TextComponentUtils.func_240645_a_(commandSource, (ITextComponent)object, entity2, n) : object;
        }
        return new TranslationTextComponent(this.key, objectArray);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof TranslationTextComponent)) {
            return true;
        }
        TranslationTextComponent translationTextComponent = (TranslationTextComponent)object;
        return Arrays.equals(this.formatArgs, translationTextComponent.formatArgs) && this.key.equals(translationTextComponent.key) && super.equals(object);
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + this.key.hashCode();
        return 31 * n + Arrays.hashCode(this.formatArgs);
    }

    @Override
    public String toString() {
        return "TranslatableComponent{key='" + this.key + "', args=" + Arrays.toString(this.formatArgs) + ", siblings=" + this.siblings + ", style=" + this.getStyle() + "}";
    }

    public String getKey() {
        return this.key;
    }

    public Object[] getFormatArgs() {
        return this.formatArgs;
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

