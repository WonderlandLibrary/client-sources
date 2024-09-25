/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.api.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import us.myles.viaversion.libs.bungeecordchat.api.chat.BaseComponent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.TextComponent;
import us.myles.viaversion.libs.bungeecordchat.chat.TranslationRegistry;

public final class TranslatableComponent
extends BaseComponent {
    private final Pattern format = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
    private String translate;
    private List<BaseComponent> with;

    public TranslatableComponent(TranslatableComponent original) {
        super(original);
        this.setTranslate(original.getTranslate());
        if (original.getWith() != null) {
            ArrayList<BaseComponent> temp = new ArrayList<BaseComponent>();
            for (BaseComponent baseComponent : original.getWith()) {
                temp.add(baseComponent.duplicate());
            }
            this.setWith(temp);
        }
    }

    public TranslatableComponent(String translate, Object ... with) {
        this.setTranslate(translate);
        if (with != null && with.length != 0) {
            ArrayList<BaseComponent> temp = new ArrayList<BaseComponent>();
            for (Object w : with) {
                if (w instanceof BaseComponent) {
                    temp.add((BaseComponent)w);
                    continue;
                }
                temp.add(new TextComponent(String.valueOf(w)));
            }
            this.setWith(temp);
        }
    }

    @Override
    public TranslatableComponent duplicate() {
        return new TranslatableComponent(this);
    }

    public void setWith(List<BaseComponent> components) {
        for (BaseComponent component : components) {
            component.parent = this;
        }
        this.with = components;
    }

    public void addWith(String text) {
        this.addWith(new TextComponent(text));
    }

    public void addWith(BaseComponent component) {
        if (this.with == null) {
            this.with = new ArrayList<BaseComponent>();
        }
        component.parent = this;
        this.with.add(component);
    }

    @Override
    protected void toPlainText(StringBuilder builder) {
        this.convert(builder, false);
        super.toPlainText(builder);
    }

    @Override
    protected void toLegacyText(StringBuilder builder) {
        this.convert(builder, true);
        super.toLegacyText(builder);
    }

    private void convert(StringBuilder builder, boolean applyFormat) {
        String trans = TranslationRegistry.INSTANCE.translate(this.translate);
        Matcher matcher = this.format.matcher(trans);
        int position = 0;
        int i = 0;
        while (matcher.find(position)) {
            int pos = matcher.start();
            if (pos != position) {
                if (applyFormat) {
                    this.addFormat(builder);
                }
                builder.append(trans.substring(position, pos));
            }
            position = matcher.end();
            String formatCode = matcher.group(2);
            switch (formatCode.charAt(0)) {
                case 'd': 
                case 's': {
                    String withIndex = matcher.group(1);
                    BaseComponent withComponent = this.with.get(withIndex != null ? Integer.parseInt(withIndex) - 1 : i++);
                    if (applyFormat) {
                        withComponent.toLegacyText(builder);
                        break;
                    }
                    withComponent.toPlainText(builder);
                    break;
                }
                case '%': {
                    if (applyFormat) {
                        this.addFormat(builder);
                    }
                    builder.append('%');
                }
            }
        }
        if (trans.length() != position) {
            if (applyFormat) {
                this.addFormat(builder);
            }
            builder.append(trans.substring(position, trans.length()));
        }
    }

    public Pattern getFormat() {
        return this.format;
    }

    public String getTranslate() {
        return this.translate;
    }

    public List<BaseComponent> getWith() {
        return this.with;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    @Override
    public String toString() {
        return "TranslatableComponent(format=" + this.getFormat() + ", translate=" + this.getTranslate() + ", with=" + this.getWith() + ")";
    }

    public TranslatableComponent() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TranslatableComponent)) {
            return false;
        }
        TranslatableComponent other = (TranslatableComponent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Pattern this$format = this.getFormat();
        Pattern other$format = other.getFormat();
        if (this$format == null ? other$format != null : !this$format.equals(other$format)) {
            return false;
        }
        String this$translate = this.getTranslate();
        String other$translate = other.getTranslate();
        if (this$translate == null ? other$translate != null : !this$translate.equals(other$translate)) {
            return false;
        }
        List<BaseComponent> this$with = this.getWith();
        List<BaseComponent> other$with = other.getWith();
        return !(this$with == null ? other$with != null : !((Object)this$with).equals(other$with));
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof TranslatableComponent;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        Pattern $format = this.getFormat();
        result = result * 59 + ($format == null ? 43 : $format.hashCode());
        String $translate = this.getTranslate();
        result = result * 59 + ($translate == null ? 43 : $translate.hashCode());
        List<BaseComponent> $with = this.getWith();
        result = result * 59 + ($with == null ? 43 : ((Object)$with).hashCode());
        return result;
    }
}

