/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package us.myles.viaversion.libs.bungeecordchat.api.chat;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import us.myles.viaversion.libs.bungeecordchat.api.ChatColor;
import us.myles.viaversion.libs.bungeecordchat.api.chat.BaseComponent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.ClickEvent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.HoverEvent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.TextComponent;

public final class ComponentBuilder {
    private int cursor = -1;
    private final List<BaseComponent> parts = new ArrayList<BaseComponent>();
    private BaseComponent dummy;

    private ComponentBuilder(BaseComponent[] parts) {
        for (BaseComponent baseComponent : parts) {
            this.parts.add(baseComponent.duplicate());
        }
        this.resetCursor();
    }

    public ComponentBuilder(ComponentBuilder original) {
        this(original.parts.toArray(new BaseComponent[original.parts.size()]));
    }

    public ComponentBuilder(String text) {
        this(new TextComponent(text));
    }

    public ComponentBuilder(BaseComponent component) {
        this(new BaseComponent[]{component});
    }

    private BaseComponent getDummy() {
        if (this.dummy == null) {
            this.dummy = new BaseComponent(){

                @Override
                public BaseComponent duplicate() {
                    return this;
                }
            };
        }
        return this.dummy;
    }

    public ComponentBuilder resetCursor() {
        this.cursor = this.parts.size() - 1;
        return this;
    }

    public ComponentBuilder setCursor(int pos) throws IndexOutOfBoundsException {
        if (this.cursor != pos && (pos < 0 || pos >= this.parts.size())) {
            throw new IndexOutOfBoundsException("Cursor out of bounds (expected between 0 + " + (this.parts.size() - 1) + ")");
        }
        this.cursor = pos;
        return this;
    }

    public ComponentBuilder append(BaseComponent component) {
        return this.append(component, FormatRetention.ALL);
    }

    public ComponentBuilder append(BaseComponent component, FormatRetention retention) {
        BaseComponent previous;
        BaseComponent baseComponent = previous = this.parts.isEmpty() ? null : this.parts.get(this.parts.size() - 1);
        if (previous == null) {
            previous = this.dummy;
            this.dummy = null;
        }
        if (previous != null) {
            component.copyFormatting(previous, retention, false);
        }
        this.parts.add(component);
        this.resetCursor();
        return this;
    }

    public ComponentBuilder append(BaseComponent[] components) {
        return this.append(components, FormatRetention.ALL);
    }

    public ComponentBuilder append(BaseComponent[] components, FormatRetention retention) {
        Preconditions.checkArgument((components.length != 0 ? 1 : 0) != 0, (Object)"No components to append");
        for (BaseComponent component : components) {
            this.append(component, retention);
        }
        return this;
    }

    public ComponentBuilder append(String text) {
        return this.append(text, FormatRetention.ALL);
    }

    public ComponentBuilder appendLegacy(String text) {
        return this.append(TextComponent.fromLegacyText(text));
    }

    public ComponentBuilder append(String text, FormatRetention retention) {
        return this.append(new TextComponent(text), retention);
    }

    public ComponentBuilder append(Joiner joiner) {
        return joiner.join(this, FormatRetention.ALL);
    }

    public ComponentBuilder append(Joiner joiner, FormatRetention retention) {
        return joiner.join(this, retention);
    }

    public void removeComponent(int pos) throws IndexOutOfBoundsException {
        if (this.parts.remove(pos) != null) {
            this.resetCursor();
        }
    }

    public BaseComponent getComponent(int pos) throws IndexOutOfBoundsException {
        return this.parts.get(pos);
    }

    public BaseComponent getCurrentComponent() {
        return this.cursor == -1 ? this.getDummy() : this.parts.get(this.cursor);
    }

    public ComponentBuilder color(ChatColor color) {
        this.getCurrentComponent().setColor(color);
        return this;
    }

    public ComponentBuilder font(String font) {
        this.getCurrentComponent().setFont(font);
        return this;
    }

    public ComponentBuilder bold(boolean bold) {
        this.getCurrentComponent().setBold(bold);
        return this;
    }

    public ComponentBuilder italic(boolean italic) {
        this.getCurrentComponent().setItalic(italic);
        return this;
    }

    public ComponentBuilder underlined(boolean underlined) {
        this.getCurrentComponent().setUnderlined(underlined);
        return this;
    }

    public ComponentBuilder strikethrough(boolean strikethrough) {
        this.getCurrentComponent().setStrikethrough(strikethrough);
        return this;
    }

    public ComponentBuilder obfuscated(boolean obfuscated) {
        this.getCurrentComponent().setObfuscated(obfuscated);
        return this;
    }

    public ComponentBuilder insertion(String insertion) {
        this.getCurrentComponent().setInsertion(insertion);
        return this;
    }

    public ComponentBuilder event(ClickEvent clickEvent) {
        this.getCurrentComponent().setClickEvent(clickEvent);
        return this;
    }

    public ComponentBuilder event(HoverEvent hoverEvent) {
        this.getCurrentComponent().setHoverEvent(hoverEvent);
        return this;
    }

    public ComponentBuilder reset() {
        return this.retain(FormatRetention.NONE);
    }

    public ComponentBuilder retain(FormatRetention retention) {
        this.getCurrentComponent().retain(retention);
        return this;
    }

    public BaseComponent[] create() {
        BaseComponent[] cloned = new BaseComponent[this.parts.size()];
        int i = 0;
        for (BaseComponent part : this.parts) {
            cloned[i++] = part.duplicate();
        }
        return cloned;
    }

    public ComponentBuilder() {
    }

    public int getCursor() {
        return this.cursor;
    }

    public List<BaseComponent> getParts() {
        return this.parts;
    }

    public static interface Joiner {
        public ComponentBuilder join(ComponentBuilder var1, FormatRetention var2);
    }

    public static enum FormatRetention {
        NONE,
        FORMATTING,
        EVENTS,
        ALL;

    }
}

