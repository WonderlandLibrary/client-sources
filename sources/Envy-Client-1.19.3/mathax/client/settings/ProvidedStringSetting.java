package mathax.client.settings;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ProvidedStringSetting extends StringSetting {
    public final Supplier<String[]> supplier;

    public ProvidedStringSetting(String name, String description, String defaultValue, Consumer<String> onChanged, Consumer<Setting<String>> onModuleActivated, IVisible visible, Supplier<String[]> supplier) {
        super(name, description, defaultValue, onChanged, onModuleActivated, visible);

        this.supplier = supplier;
    }

    public static class Builder extends SettingBuilder<mathax.client.settings.ProvidedStringSetting.Builder, String, ProvidedStringSetting> {
        private Supplier<String[]> supplier;

        public Builder() {
            super(null);
        }

        public mathax.client.settings.ProvidedStringSetting.Builder supplier(Supplier<String[]> supplier) {
            this.supplier = supplier;
            return this;
        }

        @Override
        public ProvidedStringSetting build() {
            return new ProvidedStringSetting(name, description, defaultValue, onChanged, onModuleActivated, visible, supplier);
        }
    }
}
