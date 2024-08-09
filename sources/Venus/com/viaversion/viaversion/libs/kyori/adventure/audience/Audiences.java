/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.audience;

import com.viaversion.viaversion.libs.kyori.adventure.audience.Audience;
import com.viaversion.viaversion.libs.kyori.adventure.audience.ForwardingAudience;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public final class Audiences {
    static final Collector<? super Audience, ?, ForwardingAudience> COLLECTOR = Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new), Audiences::lambda$static$0);

    private Audiences() {
    }

    @NotNull
    public static Consumer<? super Audience> sendingMessage(@NotNull ComponentLike componentLike) {
        return arg_0 -> Audiences.lambda$sendingMessage$1(componentLike, arg_0);
    }

    private static void lambda$sendingMessage$1(ComponentLike componentLike, Audience audience) {
        audience.sendMessage(componentLike);
    }

    private static ForwardingAudience lambda$static$0(ArrayList arrayList) {
        return Audience.audience(Collections.unmodifiableCollection(arrayList));
    }
}

