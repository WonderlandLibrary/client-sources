package xyz.mathax.mathaxclient.settings;

import xyz.mathax.mathaxclient.gui.widgets.input.WTextBox;
import xyz.mathax.mathaxclient.utils.settings.IVisible;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ProvidedStringSetting extends StringSetting {
    public final Supplier<String[]> supplier;

    public ProvidedStringSetting(String name, String description, String defaultValue, Consumer<String> onChanged, Consumer<Setting<String>> onModuleEnabled, IVisible visible, Class<? extends WTextBox.Renderer> renderer, boolean wide, Supplier<String[]> supplier) {
        super(name, description, defaultValue, onChanged, onModuleEnabled, visible, renderer, null, wide);

        this.supplier = supplier;
    }

    public static class Builder extends SettingBuilder<Builder, String, ProvidedStringSetting> {
        private Class<? extends WTextBox.Renderer> renderer;
        private Supplier<String[]> supplier;
        private boolean wide;

        public Builder() {
            super(null);
        }

        public Builder renderer(Class<? extends WTextBox.Renderer> renderer) {
            this.renderer = renderer;
            return this;
        }

        public Builder supplier(Supplier<String[]> supplier) {
            this.supplier = supplier;
            return this;
        }

        public Builder wide() {
            wide = true;
            return this;
        }


        @Override
        public ProvidedStringSetting build() {
            return new ProvidedStringSetting(name, description, defaultValue, onChanged, onModuleEnabled, visible, renderer, wide, supplier);
        }
    }
}
