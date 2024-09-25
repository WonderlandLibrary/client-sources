/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.api.chat;

import java.util.ArrayList;
import java.util.List;
import us.myles.viaversion.libs.bungeecordchat.api.ChatColor;
import us.myles.viaversion.libs.bungeecordchat.api.chat.ClickEvent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.ComponentBuilder;
import us.myles.viaversion.libs.bungeecordchat.api.chat.HoverEvent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.TextComponent;

public abstract class BaseComponent {
    BaseComponent parent;
    private ChatColor color;
    private String font;
    private Boolean bold;
    private Boolean italic;
    private Boolean underlined;
    private Boolean strikethrough;
    private Boolean obfuscated;
    private String insertion;
    private List<BaseComponent> extra;
    private ClickEvent clickEvent;
    private HoverEvent hoverEvent;

    @Deprecated
    public BaseComponent() {
    }

    BaseComponent(BaseComponent old) {
        this.copyFormatting(old, ComponentBuilder.FormatRetention.ALL, true);
        if (old.getExtra() != null) {
            for (BaseComponent extra : old.getExtra()) {
                this.addExtra(extra.duplicate());
            }
        }
    }

    public void copyFormatting(BaseComponent component) {
        this.copyFormatting(component, ComponentBuilder.FormatRetention.ALL, true);
    }

    public void copyFormatting(BaseComponent component, boolean replace) {
        this.copyFormatting(component, ComponentBuilder.FormatRetention.ALL, replace);
    }

    public void copyFormatting(BaseComponent component, ComponentBuilder.FormatRetention retention, boolean replace) {
        if (retention == ComponentBuilder.FormatRetention.EVENTS || retention == ComponentBuilder.FormatRetention.ALL) {
            if (replace || this.clickEvent == null) {
                this.setClickEvent(component.getClickEvent());
            }
            if (replace || this.hoverEvent == null) {
                this.setHoverEvent(component.getHoverEvent());
            }
        }
        if (retention == ComponentBuilder.FormatRetention.FORMATTING || retention == ComponentBuilder.FormatRetention.ALL) {
            if (replace || this.color == null) {
                this.setColor(component.getColorRaw());
            }
            if (replace || this.font == null) {
                this.setFont(component.getFontRaw());
            }
            if (replace || this.bold == null) {
                this.setBold(component.isBoldRaw());
            }
            if (replace || this.italic == null) {
                this.setItalic(component.isItalicRaw());
            }
            if (replace || this.underlined == null) {
                this.setUnderlined(component.isUnderlinedRaw());
            }
            if (replace || this.strikethrough == null) {
                this.setStrikethrough(component.isStrikethroughRaw());
            }
            if (replace || this.obfuscated == null) {
                this.setObfuscated(component.isObfuscatedRaw());
            }
            if (replace || this.insertion == null) {
                this.setInsertion(component.getInsertion());
            }
        }
    }

    public void retain(ComponentBuilder.FormatRetention retention) {
        if (retention == ComponentBuilder.FormatRetention.FORMATTING || retention == ComponentBuilder.FormatRetention.NONE) {
            this.setClickEvent(null);
            this.setHoverEvent(null);
        }
        if (retention == ComponentBuilder.FormatRetention.EVENTS || retention == ComponentBuilder.FormatRetention.NONE) {
            this.setColor(null);
            this.setBold(null);
            this.setItalic(null);
            this.setUnderlined(null);
            this.setStrikethrough(null);
            this.setObfuscated(null);
            this.setInsertion(null);
        }
    }

    public abstract BaseComponent duplicate();

    @Deprecated
    public BaseComponent duplicateWithoutFormatting() {
        BaseComponent component = this.duplicate();
        component.retain(ComponentBuilder.FormatRetention.NONE);
        return component;
    }

    public static String toLegacyText(BaseComponent ... components) {
        StringBuilder builder = new StringBuilder();
        for (BaseComponent msg : components) {
            builder.append(msg.toLegacyText());
        }
        return builder.toString();
    }

    public static String toPlainText(BaseComponent ... components) {
        StringBuilder builder = new StringBuilder();
        for (BaseComponent msg : components) {
            builder.append(msg.toPlainText());
        }
        return builder.toString();
    }

    public ChatColor getColor() {
        if (this.color == null) {
            if (this.parent == null) {
                return ChatColor.WHITE;
            }
            return this.parent.getColor();
        }
        return this.color;
    }

    public ChatColor getColorRaw() {
        return this.color;
    }

    public String getFont() {
        if (this.font == null) {
            if (this.parent == null) {
                return null;
            }
            return this.parent.getFont();
        }
        return this.font;
    }

    public String getFontRaw() {
        return this.font;
    }

    public boolean isBold() {
        if (this.bold == null) {
            return this.parent != null && this.parent.isBold();
        }
        return this.bold;
    }

    public Boolean isBoldRaw() {
        return this.bold;
    }

    public boolean isItalic() {
        if (this.italic == null) {
            return this.parent != null && this.parent.isItalic();
        }
        return this.italic;
    }

    public Boolean isItalicRaw() {
        return this.italic;
    }

    public boolean isUnderlined() {
        if (this.underlined == null) {
            return this.parent != null && this.parent.isUnderlined();
        }
        return this.underlined;
    }

    public Boolean isUnderlinedRaw() {
        return this.underlined;
    }

    public boolean isStrikethrough() {
        if (this.strikethrough == null) {
            return this.parent != null && this.parent.isStrikethrough();
        }
        return this.strikethrough;
    }

    public Boolean isStrikethroughRaw() {
        return this.strikethrough;
    }

    public boolean isObfuscated() {
        if (this.obfuscated == null) {
            return this.parent != null && this.parent.isObfuscated();
        }
        return this.obfuscated;
    }

    public Boolean isObfuscatedRaw() {
        return this.obfuscated;
    }

    public void setExtra(List<BaseComponent> components) {
        for (BaseComponent component : components) {
            component.parent = this;
        }
        this.extra = components;
    }

    public void addExtra(String text) {
        this.addExtra(new TextComponent(text));
    }

    public void addExtra(BaseComponent component) {
        if (this.extra == null) {
            this.extra = new ArrayList<BaseComponent>();
        }
        component.parent = this;
        this.extra.add(component);
    }

    public boolean hasFormatting() {
        return this.color != null || this.font != null || this.bold != null || this.italic != null || this.underlined != null || this.strikethrough != null || this.obfuscated != null || this.insertion != null || this.hoverEvent != null || this.clickEvent != null;
    }

    public String toPlainText() {
        StringBuilder builder = new StringBuilder();
        this.toPlainText(builder);
        return builder.toString();
    }

    void toPlainText(StringBuilder builder) {
        if (this.extra != null) {
            for (BaseComponent e : this.extra) {
                e.toPlainText(builder);
            }
        }
    }

    public String toLegacyText() {
        StringBuilder builder = new StringBuilder();
        this.toLegacyText(builder);
        return builder.toString();
    }

    void toLegacyText(StringBuilder builder) {
        if (this.extra != null) {
            for (BaseComponent e : this.extra) {
                e.toLegacyText(builder);
            }
        }
    }

    void addFormat(StringBuilder builder) {
        builder.append(this.getColor());
        if (this.isBold()) {
            builder.append(ChatColor.BOLD);
        }
        if (this.isItalic()) {
            builder.append(ChatColor.ITALIC);
        }
        if (this.isUnderlined()) {
            builder.append(ChatColor.UNDERLINE);
        }
        if (this.isStrikethrough()) {
            builder.append(ChatColor.STRIKETHROUGH);
        }
        if (this.isObfuscated()) {
            builder.append(ChatColor.MAGIC);
        }
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic;
    }

    public void setUnderlined(Boolean underlined) {
        this.underlined = underlined;
    }

    public void setStrikethrough(Boolean strikethrough) {
        this.strikethrough = strikethrough;
    }

    public void setObfuscated(Boolean obfuscated) {
        this.obfuscated = obfuscated;
    }

    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }

    public void setClickEvent(ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

    public void setHoverEvent(HoverEvent hoverEvent) {
        this.hoverEvent = hoverEvent;
    }

    public String toString() {
        return "BaseComponent(color=" + this.getColor() + ", font=" + this.getFont() + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", strikethrough=" + this.strikethrough + ", obfuscated=" + this.obfuscated + ", insertion=" + this.getInsertion() + ", extra=" + this.getExtra() + ", clickEvent=" + this.getClickEvent() + ", hoverEvent=" + this.getHoverEvent() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseComponent)) {
            return false;
        }
        BaseComponent other = (BaseComponent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ChatColor this$color = this.getColor();
        ChatColor other$color = other.getColor();
        if (this$color == null ? other$color != null : !((Object)this$color).equals(other$color)) {
            return false;
        }
        String this$font = this.getFont();
        String other$font = other.getFont();
        if (this$font == null ? other$font != null : !this$font.equals(other$font)) {
            return false;
        }
        Boolean this$bold = this.bold;
        Boolean other$bold = other.bold;
        if (this$bold == null ? other$bold != null : !((Object)this$bold).equals(other$bold)) {
            return false;
        }
        Boolean this$italic = this.italic;
        Boolean other$italic = other.italic;
        if (this$italic == null ? other$italic != null : !((Object)this$italic).equals(other$italic)) {
            return false;
        }
        Boolean this$underlined = this.underlined;
        Boolean other$underlined = other.underlined;
        if (this$underlined == null ? other$underlined != null : !((Object)this$underlined).equals(other$underlined)) {
            return false;
        }
        Boolean this$strikethrough = this.strikethrough;
        Boolean other$strikethrough = other.strikethrough;
        if (this$strikethrough == null ? other$strikethrough != null : !((Object)this$strikethrough).equals(other$strikethrough)) {
            return false;
        }
        Boolean this$obfuscated = this.obfuscated;
        Boolean other$obfuscated = other.obfuscated;
        if (this$obfuscated == null ? other$obfuscated != null : !((Object)this$obfuscated).equals(other$obfuscated)) {
            return false;
        }
        String this$insertion = this.getInsertion();
        String other$insertion = other.getInsertion();
        if (this$insertion == null ? other$insertion != null : !this$insertion.equals(other$insertion)) {
            return false;
        }
        List<BaseComponent> this$extra = this.getExtra();
        List<BaseComponent> other$extra = other.getExtra();
        if (this$extra == null ? other$extra != null : !((Object)this$extra).equals(other$extra)) {
            return false;
        }
        ClickEvent this$clickEvent = this.getClickEvent();
        ClickEvent other$clickEvent = other.getClickEvent();
        if (this$clickEvent == null ? other$clickEvent != null : !((Object)this$clickEvent).equals(other$clickEvent)) {
            return false;
        }
        HoverEvent this$hoverEvent = this.getHoverEvent();
        HoverEvent other$hoverEvent = other.getHoverEvent();
        return !(this$hoverEvent == null ? other$hoverEvent != null : !((Object)this$hoverEvent).equals(other$hoverEvent));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseComponent;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ChatColor $color = this.getColor();
        result = result * 59 + ($color == null ? 43 : ((Object)$color).hashCode());
        String $font = this.getFont();
        result = result * 59 + ($font == null ? 43 : $font.hashCode());
        Boolean $bold = this.bold;
        result = result * 59 + ($bold == null ? 43 : ((Object)$bold).hashCode());
        Boolean $italic = this.italic;
        result = result * 59 + ($italic == null ? 43 : ((Object)$italic).hashCode());
        Boolean $underlined = this.underlined;
        result = result * 59 + ($underlined == null ? 43 : ((Object)$underlined).hashCode());
        Boolean $strikethrough = this.strikethrough;
        result = result * 59 + ($strikethrough == null ? 43 : ((Object)$strikethrough).hashCode());
        Boolean $obfuscated = this.obfuscated;
        result = result * 59 + ($obfuscated == null ? 43 : ((Object)$obfuscated).hashCode());
        String $insertion = this.getInsertion();
        result = result * 59 + ($insertion == null ? 43 : $insertion.hashCode());
        List<BaseComponent> $extra = this.getExtra();
        result = result * 59 + ($extra == null ? 43 : ((Object)$extra).hashCode());
        ClickEvent $clickEvent = this.getClickEvent();
        result = result * 59 + ($clickEvent == null ? 43 : ((Object)$clickEvent).hashCode());
        HoverEvent $hoverEvent = this.getHoverEvent();
        result = result * 59 + ($hoverEvent == null ? 43 : ((Object)$hoverEvent).hashCode());
        return result;
    }

    public String getInsertion() {
        return this.insertion;
    }

    public List<BaseComponent> getExtra() {
        return this.extra;
    }

    public ClickEvent getClickEvent() {
        return this.clickEvent;
    }

    public HoverEvent getHoverEvent() {
        return this.hoverEvent;
    }
}

