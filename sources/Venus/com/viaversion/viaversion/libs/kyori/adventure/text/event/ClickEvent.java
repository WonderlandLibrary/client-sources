/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import com.viaversion.viaversion.libs.kyori.adventure.audience.Audience;
import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickCallback;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickCallbackInternals;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickCallbackOptionsImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.net.URL;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ClickEvent
implements Examinable,
StyleBuilderApplicable {
    private final Action action;
    private final String value;

    @NotNull
    public static ClickEvent openUrl(@NotNull String string) {
        return new ClickEvent(Action.OPEN_URL, string);
    }

    @NotNull
    public static ClickEvent openUrl(@NotNull URL uRL) {
        return ClickEvent.openUrl(uRL.toExternalForm());
    }

    @NotNull
    public static ClickEvent openFile(@NotNull String string) {
        return new ClickEvent(Action.OPEN_FILE, string);
    }

    @NotNull
    public static ClickEvent runCommand(@NotNull String string) {
        return new ClickEvent(Action.RUN_COMMAND, string);
    }

    @NotNull
    public static ClickEvent suggestCommand(@NotNull String string) {
        return new ClickEvent(Action.SUGGEST_COMMAND, string);
    }

    @NotNull
    public static ClickEvent changePage(@NotNull String string) {
        return new ClickEvent(Action.CHANGE_PAGE, string);
    }

    @NotNull
    public static ClickEvent changePage(int n) {
        return ClickEvent.changePage(String.valueOf(n));
    }

    @NotNull
    public static ClickEvent copyToClipboard(@NotNull String string) {
        return new ClickEvent(Action.COPY_TO_CLIPBOARD, string);
    }

    @NotNull
    public static ClickEvent callback(@NotNull ClickCallback<Audience> clickCallback) {
        return ClickCallbackInternals.PROVIDER.create(Objects.requireNonNull(clickCallback, "function"), ClickCallbackOptionsImpl.DEFAULT);
    }

    @NotNull
    public static ClickEvent callback(@NotNull ClickCallback<Audience> clickCallback, @NotNull ClickCallback.Options options) {
        return ClickCallbackInternals.PROVIDER.create(Objects.requireNonNull(clickCallback, "function"), Objects.requireNonNull(options, "options"));
    }

    @NotNull
    public static ClickEvent callback(@NotNull ClickCallback<Audience> clickCallback, @NotNull @NotNull Consumer<@NotNull ClickCallback.Options.Builder> consumer) {
        return ClickCallbackInternals.PROVIDER.create(Objects.requireNonNull(clickCallback, "function"), (ClickCallback.Options)AbstractBuilder.configureAndBuild(ClickCallback.Options.builder(), Objects.requireNonNull(consumer, "optionsBuilder")));
    }

    @NotNull
    public static ClickEvent clickEvent(@NotNull Action action, @NotNull String string) {
        return new ClickEvent(action, string);
    }

    private ClickEvent(@NotNull Action action, @NotNull String string) {
        this.action = Objects.requireNonNull(action, "action");
        this.value = Objects.requireNonNull(string, "value");
    }

    @NotNull
    public Action action() {
        return this.action;
    }

    @NotNull
    public String value() {
        return this.value;
    }

    @Override
    public void styleApply( @NotNull Style.Builder builder) {
        builder.clickEvent(this);
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ClickEvent clickEvent = (ClickEvent)object;
        return this.action == clickEvent.action && Objects.equals(this.value, clickEvent.value);
    }

    public int hashCode() {
        int n = this.action.hashCode();
        n = 31 * n + this.value.hashCode();
        return n;
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("action", (Object)this.action), ExaminableProperty.of("value", this.value));
    }

    public String toString() {
        return Internals.toString(this);
    }

    public static enum Action {
        OPEN_URL("open_url", true),
        OPEN_FILE("open_file", false),
        RUN_COMMAND("run_command", true),
        SUGGEST_COMMAND("suggest_command", true),
        CHANGE_PAGE("change_page", true),
        COPY_TO_CLIPBOARD("copy_to_clipboard", true);

        public static final Index<String, Action> NAMES;
        private final String name;
        private final boolean readable;

        private Action(String string2, boolean bl) {
            this.name = string2;
            this.readable = bl;
        }

        public boolean readable() {
            return this.readable;
        }

        @NotNull
        public String toString() {
            return this.name;
        }

        private static String lambda$static$0(Action action) {
            return action.name;
        }

        static {
            NAMES = Index.create(Action.class, Action::lambda$static$0);
        }
    }
}

