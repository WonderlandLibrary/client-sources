/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package us.myles.viaversion.libs.bungeecordchat.api.chat;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import us.myles.viaversion.libs.bungeecordchat.api.chat.BaseComponent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.TextComponent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content.Content;
import us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content.Entity;
import us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content.Item;
import us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content.Text;
import us.myles.viaversion.libs.bungeecordchat.chat.ComponentSerializer;

public final class HoverEvent {
    private final Action action;
    private final List<Content> contents;
    private boolean legacy = false;

    public HoverEvent(Action action, Content ... contents) {
        Preconditions.checkArgument((contents.length != 0 ? 1 : 0) != 0, (Object)"Must contain at least one content");
        this.action = action;
        this.contents = new ArrayList<Content>();
        for (Content it : contents) {
            this.addContent(it);
        }
    }

    @Deprecated
    public HoverEvent(Action action, BaseComponent[] value) {
        this.action = action;
        this.contents = new ArrayList<Text>(Collections.singletonList(new Text(value)));
        this.legacy = true;
    }

    @Deprecated
    public BaseComponent[] getValue() {
        Content content = this.contents.get(0);
        if (content instanceof Text && ((Text)content).getValue() instanceof BaseComponent[]) {
            return (BaseComponent[])((Text)content).getValue();
        }
        TextComponent component = new TextComponent(ComponentSerializer.toString(content));
        return new BaseComponent[]{component};
    }

    public void addContent(Content content) throws UnsupportedOperationException {
        Preconditions.checkArgument((!this.legacy || this.contents.size() == 0 ? 1 : 0) != 0, (Object)"Legacy HoverEvent may not have more than one content");
        content.assertAction(this.action);
        this.contents.add(content);
    }

    public static Class<?> getClass(Action action, boolean array) {
        Preconditions.checkArgument((action != null ? 1 : 0) != 0, (Object)"action");
        switch (action) {
            case SHOW_TEXT: {
                return array ? Text[].class : Text.class;
            }
            case SHOW_ENTITY: {
                return array ? Entity[].class : Entity.class;
            }
            case SHOW_ITEM: {
                return array ? Item[].class : Item.class;
            }
        }
        throw new UnsupportedOperationException("Action '" + action.name() + " not supported");
    }

    public Action getAction() {
        return this.action;
    }

    public List<Content> getContents() {
        return this.contents;
    }

    public boolean isLegacy() {
        return this.legacy;
    }

    public String toString() {
        return "HoverEvent(action=" + (Object)((Object)this.getAction()) + ", contents=" + this.getContents() + ", legacy=" + this.isLegacy() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HoverEvent)) {
            return false;
        }
        HoverEvent other = (HoverEvent)o;
        Action this$action = this.getAction();
        Action other$action = other.getAction();
        if (this$action == null ? other$action != null : !((Object)((Object)this$action)).equals((Object)other$action)) {
            return false;
        }
        List<Content> this$contents = this.getContents();
        List<Content> other$contents = other.getContents();
        if (this$contents == null ? other$contents != null : !((Object)this$contents).equals(other$contents)) {
            return false;
        }
        return this.isLegacy() == other.isLegacy();
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Action $action = this.getAction();
        result = result * 59 + ($action == null ? 43 : ((Object)((Object)$action)).hashCode());
        List<Content> $contents = this.getContents();
        result = result * 59 + ($contents == null ? 43 : ((Object)$contents).hashCode());
        result = result * 59 + (this.isLegacy() ? 79 : 97);
        return result;
    }

    public HoverEvent(Action action, List<Content> contents) {
        this.action = action;
        this.contents = contents;
    }

    public void setLegacy(boolean legacy) {
        this.legacy = legacy;
    }

    public static enum Action {
        SHOW_TEXT,
        SHOW_ITEM,
        SHOW_ENTITY,
        SHOW_ACHIEVEMENT;

    }
}

