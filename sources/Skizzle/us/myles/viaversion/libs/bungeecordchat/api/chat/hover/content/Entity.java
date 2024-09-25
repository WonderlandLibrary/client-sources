/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  lombok.NonNull
 */
package us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content;

import lombok.NonNull;
import us.myles.viaversion.libs.bungeecordchat.api.chat.BaseComponent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.HoverEvent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content.Content;

public class Entity
extends Content {
    private String type;
    @NonNull
    private String id;
    private BaseComponent name;

    @Override
    public HoverEvent.Action requiredAction() {
        return HoverEvent.Action.SHOW_ENTITY;
    }

    public String getType() {
        return this.type;
    }

    @NonNull
    public String getId() {
        return this.id;
    }

    public BaseComponent getName() {
        return this.name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(@NonNull String id) {
        if (id == null) {
            throw new NullPointerException("id is marked non-null but is null");
        }
        this.id = id;
    }

    public void setName(BaseComponent name) {
        this.name = name;
    }

    public Entity(String type, @NonNull String id, BaseComponent name) {
        if (id == null) {
            throw new NullPointerException("id is marked non-null but is null");
        }
        this.type = type;
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Entity(type=" + this.getType() + ", id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Entity)) {
            return false;
        }
        Entity other = (Entity)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        BaseComponent this$name = this.getName();
        BaseComponent other$name = other.getName();
        return !(this$name == null ? other$name != null : !((Object)this$name).equals(other$name));
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof Entity;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        BaseComponent $name = this.getName();
        result = result * 59 + ($name == null ? 43 : ((Object)$name).hashCode());
        return result;
    }
}

