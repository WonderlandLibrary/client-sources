/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content;

import java.util.Arrays;
import us.myles.viaversion.libs.bungeecordchat.api.chat.BaseComponent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.HoverEvent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content.Content;

public class Text
extends Content {
    private final Object value;

    public Text(BaseComponent[] value) {
        this.value = value;
    }

    public Text(String value) {
        this.value = value;
    }

    @Override
    public HoverEvent.Action requiredAction() {
        return HoverEvent.Action.SHOW_TEXT;
    }

    @Override
    public boolean equals(Object o) {
        if (this.value instanceof BaseComponent[]) {
            return o instanceof Text && ((Text)o).value instanceof BaseComponent[] && Arrays.equals((BaseComponent[])this.value, (BaseComponent[])((Text)o).value);
        }
        return this.value.equals(o);
    }

    @Override
    public int hashCode() {
        return this.value instanceof BaseComponent[] ? Arrays.hashCode((BaseComponent[])this.value) : this.value.hashCode();
    }

    public Object getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Text(value=" + this.getValue() + ")";
    }
}

