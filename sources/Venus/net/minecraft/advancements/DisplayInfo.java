/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;

public class DisplayInfo {
    private final ITextComponent title;
    private final ITextComponent description;
    private final ItemStack icon;
    private final ResourceLocation background;
    private final FrameType frame;
    private final boolean showToast;
    private final boolean announceToChat;
    private final boolean hidden;
    private float x;
    private float y;

    public DisplayInfo(ItemStack itemStack, ITextComponent iTextComponent, ITextComponent iTextComponent2, @Nullable ResourceLocation resourceLocation, FrameType frameType, boolean bl, boolean bl2, boolean bl3) {
        this.title = iTextComponent;
        this.description = iTextComponent2;
        this.icon = itemStack;
        this.background = resourceLocation;
        this.frame = frameType;
        this.showToast = bl;
        this.announceToChat = bl2;
        this.hidden = bl3;
    }

    public void setPosition(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public ITextComponent getTitle() {
        return this.title;
    }

    public ITextComponent getDescription() {
        return this.description;
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    @Nullable
    public ResourceLocation getBackground() {
        return this.background;
    }

    public FrameType getFrame() {
        return this.frame;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public boolean shouldShowToast() {
        return this.showToast;
    }

    public boolean shouldAnnounceToChat() {
        return this.announceToChat;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public static DisplayInfo deserialize(JsonObject jsonObject) {
        IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.getComponentFromJson(jsonObject.get("title"));
        IFormattableTextComponent iFormattableTextComponent2 = ITextComponent.Serializer.getComponentFromJson(jsonObject.get("description"));
        if (iFormattableTextComponent != null && iFormattableTextComponent2 != null) {
            ItemStack itemStack = DisplayInfo.deserializeIcon(JSONUtils.getJsonObject(jsonObject, "icon"));
            ResourceLocation resourceLocation = jsonObject.has("background") ? new ResourceLocation(JSONUtils.getString(jsonObject, "background")) : null;
            FrameType frameType = jsonObject.has("frame") ? FrameType.byName(JSONUtils.getString(jsonObject, "frame")) : FrameType.TASK;
            boolean bl = JSONUtils.getBoolean(jsonObject, "show_toast", true);
            boolean bl2 = JSONUtils.getBoolean(jsonObject, "announce_to_chat", true);
            boolean bl3 = JSONUtils.getBoolean(jsonObject, "hidden", false);
            return new DisplayInfo(itemStack, iFormattableTextComponent, iFormattableTextComponent2, resourceLocation, frameType, bl, bl2, bl3);
        }
        throw new JsonSyntaxException("Both title and description must be set");
    }

    private static ItemStack deserializeIcon(JsonObject jsonObject) {
        if (!jsonObject.has("item")) {
            throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
        }
        Item item = JSONUtils.getItem(jsonObject, "item");
        if (jsonObject.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        }
        ItemStack itemStack = new ItemStack(item);
        if (jsonObject.has("nbt")) {
            try {
                CompoundNBT compoundNBT = JsonToNBT.getTagFromJson(JSONUtils.getString(jsonObject.get("nbt"), "nbt"));
                itemStack.setTag(compoundNBT);
            } catch (CommandSyntaxException commandSyntaxException) {
                throw new JsonSyntaxException("Invalid nbt tag: " + commandSyntaxException.getMessage());
            }
        }
        return itemStack;
    }

    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeTextComponent(this.title);
        packetBuffer.writeTextComponent(this.description);
        packetBuffer.writeItemStack(this.icon);
        packetBuffer.writeEnumValue(this.frame);
        int n = 0;
        if (this.background != null) {
            n |= 1;
        }
        if (this.showToast) {
            n |= 2;
        }
        if (this.hidden) {
            n |= 4;
        }
        packetBuffer.writeInt(n);
        if (this.background != null) {
            packetBuffer.writeResourceLocation(this.background);
        }
        packetBuffer.writeFloat(this.x);
        packetBuffer.writeFloat(this.y);
    }

    public static DisplayInfo read(PacketBuffer packetBuffer) {
        ITextComponent iTextComponent = packetBuffer.readTextComponent();
        ITextComponent iTextComponent2 = packetBuffer.readTextComponent();
        ItemStack itemStack = packetBuffer.readItemStack();
        FrameType frameType = packetBuffer.readEnumValue(FrameType.class);
        int n = packetBuffer.readInt();
        ResourceLocation resourceLocation = (n & 1) != 0 ? packetBuffer.readResourceLocation() : null;
        boolean bl = (n & 2) != 0;
        boolean bl2 = (n & 4) != 0;
        DisplayInfo displayInfo = new DisplayInfo(itemStack, iTextComponent, iTextComponent2, resourceLocation, frameType, bl, false, bl2);
        displayInfo.setPosition(packetBuffer.readFloat(), packetBuffer.readFloat());
        return displayInfo;
    }

    public JsonElement serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("icon", this.serializeIcon());
        jsonObject.add("title", ITextComponent.Serializer.toJsonTree(this.title));
        jsonObject.add("description", ITextComponent.Serializer.toJsonTree(this.description));
        jsonObject.addProperty("frame", this.frame.getName());
        jsonObject.addProperty("show_toast", this.showToast);
        jsonObject.addProperty("announce_to_chat", this.announceToChat);
        jsonObject.addProperty("hidden", this.hidden);
        if (this.background != null) {
            jsonObject.addProperty("background", this.background.toString());
        }
        return jsonObject;
    }

    private JsonObject serializeIcon() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("item", Registry.ITEM.getKey(this.icon.getItem()).toString());
        if (this.icon.hasTag()) {
            jsonObject.addProperty("nbt", this.icon.getTag().toString());
        }
        return jsonObject;
    }
}

