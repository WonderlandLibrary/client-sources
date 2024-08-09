/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.Style;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class TextComponent
implements IFormattableTextComponent {
    protected final List<ITextComponent> siblings = Lists.newArrayList();
    private IReorderingProcessor field_244278_d = IReorderingProcessor.field_242232_a;
    @Nullable
    private LanguageMap field_244279_e;
    private Style style = Style.EMPTY;

    @Override
    public IFormattableTextComponent append(ITextComponent iTextComponent) {
        this.siblings.add(iTextComponent);
        return this;
    }

    @Override
    public String getUnformattedComponentText() {
        return "";
    }

    @Override
    public List<ITextComponent> getSiblings() {
        return this.siblings;
    }

    @Override
    public IFormattableTextComponent setStyle(Style style) {
        this.style = style;
        return this;
    }

    @Override
    public Style getStyle() {
        return this.style;
    }

    @Override
    public abstract TextComponent copyRaw();

    @Override
    public final IFormattableTextComponent deepCopy() {
        TextComponent textComponent = this.copyRaw();
        textComponent.siblings.addAll(this.siblings);
        textComponent.setStyle(this.style);
        return textComponent;
    }

    @Override
    public IReorderingProcessor func_241878_f() {
        LanguageMap languageMap = LanguageMap.getInstance();
        if (this.field_244279_e != languageMap) {
            this.field_244278_d = languageMap.func_241870_a(this);
            this.field_244279_e = languageMap;
        }
        return this.field_244278_d;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof TextComponent)) {
            return true;
        }
        TextComponent textComponent = (TextComponent)object;
        return this.siblings.equals(textComponent.siblings) && Objects.equals(this.getStyle(), textComponent.getStyle());
    }

    public int hashCode() {
        return Objects.hash(this.getStyle(), this.siblings);
    }

    public String toString() {
        return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + "}";
    }

    @Override
    public IFormattableTextComponent copyRaw() {
        return this.copyRaw();
    }
}

