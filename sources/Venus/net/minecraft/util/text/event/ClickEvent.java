/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text.event;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ClickEvent {
    private final Action action;
    private final String value;

    public ClickEvent(Action action, String string) {
        this.action = action;
        this.value = string;
    }

    public Action getAction() {
        return this.action;
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            ClickEvent clickEvent = (ClickEvent)object;
            if (this.action != clickEvent.action) {
                return true;
            }
            return this.value != null ? !this.value.equals(clickEvent.value) : clickEvent.value != null;
        }
        return true;
    }

    public String toString() {
        return "ClickEvent{action=" + this.action + ", value='" + this.value + "'}";
    }

    public int hashCode() {
        int n = this.action.hashCode();
        return 31 * n + (this.value != null ? this.value.hashCode() : 0);
    }

    public static enum Action {
        OPEN_URL("open_url", true),
        OPEN_FILE("open_file", false),
        RUN_COMMAND("run_command", true),
        SUGGEST_COMMAND("suggest_command", true),
        CHANGE_PAGE("change_page", true),
        COPY_TO_CLIPBOARD("copy_to_clipboard", true);

        private static final Map<String, Action> NAME_MAPPING;
        private final boolean allowedInChat;
        private final String canonicalName;

        private Action(String string2, boolean bl) {
            this.canonicalName = string2;
            this.allowedInChat = bl;
        }

        public boolean shouldAllowInChat() {
            return this.allowedInChat;
        }

        public String getCanonicalName() {
            return this.canonicalName;
        }

        public static Action getValueByCanonicalName(String string) {
            return NAME_MAPPING.get(string);
        }

        private static Action lambda$static$0(Action action) {
            return action;
        }

        static {
            NAME_MAPPING = Arrays.stream(Action.values()).collect(Collectors.toMap(Action::getCanonicalName, Action::lambda$static$0));
        }
    }
}

