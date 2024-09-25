/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.api.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import us.myles.viaversion.libs.bungeecordchat.api.ChatColor;
import us.myles.viaversion.libs.bungeecordchat.api.chat.BaseComponent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.ClickEvent;

public final class TextComponent
extends BaseComponent {
    private static final Pattern url = Pattern.compile("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,4})(/\\S*)?$");
    private String text;

    public static BaseComponent[] fromLegacyText(String message) {
        return TextComponent.fromLegacyText(message, ChatColor.WHITE);
    }

    public static BaseComponent[] fromLegacyText(String message, ChatColor defaultColor) {
        ArrayList<TextComponent> components = new ArrayList<TextComponent>();
        StringBuilder builder = new StringBuilder();
        TextComponent component = new TextComponent();
        Matcher matcher = url.matcher(message);
        for (int i = 0; i < message.length(); ++i) {
            TextComponent old;
            char c = message.charAt(i);
            if (c == '\u00a7') {
                ChatColor format;
                if (++i >= message.length()) break;
                c = message.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    c = (char)(c + 32);
                }
                if (c == 'x' && i + 12 < message.length()) {
                    StringBuilder hex = new StringBuilder("#");
                    for (int j = 0; j < 6; ++j) {
                        hex.append(message.charAt(i + 2 + j * 2));
                    }
                    try {
                        format = ChatColor.of(hex.toString());
                    }
                    catch (IllegalArgumentException ex) {
                        format = null;
                    }
                    i += 12;
                } else {
                    format = ChatColor.getByChar(c);
                }
                if (format == null) continue;
                if (builder.length() > 0) {
                    old = component;
                    component = new TextComponent(old);
                    old.setText(builder.toString());
                    builder = new StringBuilder();
                    components.add(old);
                }
                if (format == ChatColor.BOLD) {
                    component.setBold(true);
                    continue;
                }
                if (format == ChatColor.ITALIC) {
                    component.setItalic(true);
                    continue;
                }
                if (format == ChatColor.UNDERLINE) {
                    component.setUnderlined(true);
                    continue;
                }
                if (format == ChatColor.STRIKETHROUGH) {
                    component.setStrikethrough(true);
                    continue;
                }
                if (format == ChatColor.MAGIC) {
                    component.setObfuscated(true);
                    continue;
                }
                if (format == ChatColor.RESET) {
                    format = defaultColor;
                    component = new TextComponent();
                    component.setColor(format);
                    continue;
                }
                component = new TextComponent();
                component.setColor(format);
                continue;
            }
            int pos = message.indexOf(32, i);
            if (pos == -1) {
                pos = message.length();
            }
            if (matcher.region(i, pos).find()) {
                if (builder.length() > 0) {
                    old = component;
                    component = new TextComponent(old);
                    old.setText(builder.toString());
                    builder = new StringBuilder();
                    components.add(old);
                }
                old = component;
                component = new TextComponent(old);
                String urlString = message.substring(i, pos);
                component.setText(urlString);
                component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, urlString.startsWith("http") ? urlString : "http://" + urlString));
                components.add(component);
                i += pos - i - 1;
                component = old;
                continue;
            }
            builder.append(c);
        }
        component.setText(builder.toString());
        components.add(component);
        return components.toArray(new BaseComponent[components.size()]);
    }

    public TextComponent() {
        this.text = "";
    }

    public TextComponent(TextComponent textComponent) {
        super(textComponent);
        this.setText(textComponent.getText());
    }

    public TextComponent(BaseComponent ... extras) {
        this();
        if (extras.length == 0) {
            return;
        }
        this.setExtra(new ArrayList<BaseComponent>(Arrays.asList(extras)));
    }

    @Override
    public TextComponent duplicate() {
        return new TextComponent(this);
    }

    @Override
    protected void toPlainText(StringBuilder builder) {
        builder.append(this.text);
        super.toPlainText(builder);
    }

    @Override
    protected void toLegacyText(StringBuilder builder) {
        this.addFormat(builder);
        builder.append(this.text);
        super.toLegacyText(builder);
    }

    @Override
    public String toString() {
        return String.format("TextComponent{text=%s, %s}", this.text, super.toString());
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextComponent(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TextComponent)) {
            return false;
        }
        TextComponent other = (TextComponent)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$text = this.getText();
        String other$text = other.getText();
        return !(this$text == null ? other$text != null : !this$text.equals(other$text));
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof TextComponent;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $text = this.getText();
        result = result * 59 + ($text == null ? 43 : $text.hashCode());
        return result;
    }
}

