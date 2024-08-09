/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.unsupported;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
import com.viaversion.viaversion.unsupported.UnsupportedMethods;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class UnsupportedServerSoftware
implements UnsupportedSoftware {
    private final String name;
    private final List<String> classNames;
    private final List<UnsupportedMethods> methods;
    private final String reason;

    public UnsupportedServerSoftware(String string, List<String> list, List<UnsupportedMethods> list2, String string2) {
        Preconditions.checkNotNull(string);
        Preconditions.checkNotNull(string2);
        Preconditions.checkArgument(!list.isEmpty() || !list2.isEmpty());
        this.name = string;
        this.classNames = Collections.unmodifiableList(list);
        this.methods = Collections.unmodifiableList(list2);
        this.reason = string2;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public final @Nullable String match() {
        for (String object : this.classNames) {
            try {
                Class.forName(object);
                return this.name;
            } catch (ClassNotFoundException classNotFoundException) {
            }
        }
        for (UnsupportedMethods unsupportedMethods : this.methods) {
            if (!unsupportedMethods.findMatch()) continue;
            return this.name;
        }
        return null;
    }

    public static final class Reason {
        public static final String DANGEROUS_SERVER_SOFTWARE = "You are using server software that - outside of possibly breaking ViaVersion - can also cause severe damage to your server's integrity as a whole.";
        public static final String BREAKING_PROXY_SOFTWARE = "You are using proxy software that intentionally breaks ViaVersion. Please use another proxy software or move ViaVersion to each backend server instead of the proxy.";
    }

    public static final class Builder {
        private final List<String> classNames = new ArrayList<String>();
        private final List<UnsupportedMethods> methods = new ArrayList<UnsupportedMethods>();
        private String name;
        private String reason;

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder reason(String string) {
            this.reason = string;
            return this;
        }

        public Builder addMethod(String string, String string2) {
            this.methods.add(new UnsupportedMethods(string, Collections.singleton(string2)));
            return this;
        }

        public Builder addMethods(String string, String ... stringArray) {
            this.methods.add(new UnsupportedMethods(string, new HashSet<String>(Arrays.asList(stringArray))));
            return this;
        }

        public Builder addClassName(String string) {
            this.classNames.add(string);
            return this;
        }

        public UnsupportedSoftware build() {
            return new UnsupportedServerSoftware(this.name, this.classNames, this.methods, this.reason);
        }
    }
}

