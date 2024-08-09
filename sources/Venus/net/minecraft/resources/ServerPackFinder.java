/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import java.util.function.Consumer;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.VanillaPack;

public class ServerPackFinder
implements IPackFinder {
    private final VanillaPack field_195738_a = new VanillaPack("minecraft");

    @Override
    public void findPacks(Consumer<ResourcePackInfo> consumer, ResourcePackInfo.IFactory iFactory) {
        ResourcePackInfo resourcePackInfo = ResourcePackInfo.createResourcePack("vanilla", false, this::lambda$findPacks$0, iFactory, ResourcePackInfo.Priority.BOTTOM, IPackNameDecorator.BUILTIN);
        if (resourcePackInfo != null) {
            consumer.accept(resourcePackInfo);
        }
    }

    private IResourcePack lambda$findPacks$0() {
        return this.field_195738_a;
    }
}

