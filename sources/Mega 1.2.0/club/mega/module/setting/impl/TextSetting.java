package club.mega.module.setting.impl;

import club.mega.Mega;
import club.mega.module.Module;
import club.mega.module.setting.Setting;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

public class TextSetting extends Setting {

    private String text;

    public TextSetting(final String name, final Module parent, final String text, final boolean configurable, final Supplier<Boolean> visible) {
        super(name, parent, configurable, visible);
        setText(text);
    }

    public TextSetting(final String name, final Module parent, final String text, final boolean configurable) {
        this(name, parent, text, true, () -> true);
    }

    public TextSetting(final String name, final Module parent, final String text) {
        this(name, parent, text, true, () -> true);
    }

    public final String getRawText() {
        return text;
    }

    public final String getText() {
        return getRawText().replace("%fps%", String.valueOf(Minecraft.debugFPS)).replace("%name%", MC.thePlayer.getName()).replace("%user%", Mega.INSTANCE.getUserName()).replace("%version%", Mega.INSTANCE.getVersion()).replace("%clientname%", Mega.INSTANCE.getName()).replace("%devs%", Mega.INSTANCE.getDev());
    }

    public final void setText(final String text) {
        this.text = text;
    }

    public final void addChar(final char c) {
        text = text + c;
    }

    public final void remove() {
        if (!text.isEmpty())
        text = text.substring(0, text.length() - 1);
    }

}
