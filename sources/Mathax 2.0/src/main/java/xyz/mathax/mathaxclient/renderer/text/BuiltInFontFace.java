package xyz.mathax.mathaxclient.renderer.text;

import xyz.mathax.mathaxclient.utils.text.FontUtils;

import java.io.InputStream;

public class BuiltInFontFace extends FontFace {
    private final String name;

    public BuiltInFontFace(FontInfo info, String name) {
        super(info);

        this.name = name;
    }

    @Override
    public InputStream toStream() {
        InputStream in = FontUtils.stream(name);
        if (in == null) {
            throw new RuntimeException("Failed to load builtin font " + name + ".");
        }

        return in;
    }

    @Override
    public String toString() {
        return super.toString() + " (builtin)";
    }
}