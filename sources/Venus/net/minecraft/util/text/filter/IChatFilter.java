/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text.filter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface IChatFilter {
    public void func_244800_a();

    public void func_244434_b();

    public CompletableFuture<Optional<String>> func_244432_a(String var1);

    public CompletableFuture<Optional<List<String>>> func_244433_a(List<String> var1);
}

