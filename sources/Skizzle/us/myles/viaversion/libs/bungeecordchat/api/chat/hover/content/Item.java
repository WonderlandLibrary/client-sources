/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content;

import us.myles.viaversion.libs.bungeecordchat.api.chat.HoverEvent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.ItemTag;
import us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content.Content;

public class Item
extends Content {
    private String id;
    private int count = -1;
    private ItemTag tag;

    @Override
    public HoverEvent.Action requiredAction() {
        return HoverEvent.Action.SHOW_ITEM;
    }

    public String getId() {
        return this.id;
    }

    public int getCount() {
        return this.count;
    }

    public ItemTag getTag() {
        return this.tag;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTag(ItemTag tag) {
        this.tag = tag;
    }

    public Item(String id, int count, ItemTag tag) {
        this.id = id;
        this.count = count;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Item(id=" + this.getId() + ", count=" + this.getCount() + ", tag=" + this.getTag() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        Item other = (Item)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        if (this.getCount() != other.getCount()) {
            return false;
        }
        ItemTag this$tag = this.getTag();
        ItemTag other$tag = other.getTag();
        return !(this$tag == null ? other$tag != null : !((Object)this$tag).equals(other$tag));
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof Item;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        result = result * 59 + this.getCount();
        ItemTag $tag = this.getTag();
        result = result * 59 + ($tag == null ? 43 : ((Object)$tag).hashCode());
        return result;
    }
}

