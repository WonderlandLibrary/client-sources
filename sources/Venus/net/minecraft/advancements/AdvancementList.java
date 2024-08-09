/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementList {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<ResourceLocation, Advancement> advancements = Maps.newHashMap();
    private final Set<Advancement> roots = Sets.newLinkedHashSet();
    private final Set<Advancement> nonRoots = Sets.newLinkedHashSet();
    private IListener listener;

    private void remove(Advancement advancement) {
        for (Advancement advancement2 : advancement.getChildren()) {
            this.remove(advancement2);
        }
        LOGGER.info("Forgot about advancement {}", (Object)advancement.getId());
        this.advancements.remove(advancement.getId());
        if (advancement.getParent() == null) {
            this.roots.remove(advancement);
            if (this.listener != null) {
                this.listener.rootAdvancementRemoved(advancement);
            }
        } else {
            this.nonRoots.remove(advancement);
            if (this.listener != null) {
                this.listener.nonRootAdvancementRemoved(advancement);
            }
        }
    }

    public void removeAll(Set<ResourceLocation> set) {
        for (ResourceLocation resourceLocation : set) {
            Advancement advancement = this.advancements.get(resourceLocation);
            if (advancement == null) {
                LOGGER.warn("Told to remove advancement {} but I don't know what that is", (Object)resourceLocation);
                continue;
            }
            this.remove(advancement);
        }
    }

    public void loadAdvancements(Map<ResourceLocation, Advancement.Builder> map) {
        Function<ResourceLocation, Advancement> function = Functions.forMap(this.advancements, null);
        while (!map.isEmpty()) {
            boolean bl = false;
            Iterator<Map.Entry<ResourceLocation, Advancement.Builder>> iterator2 = map.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry<ResourceLocation, Advancement.Builder> entry = iterator2.next();
                ResourceLocation object = entry.getKey();
                Advancement.Builder builder = (Advancement.Builder)entry.getValue();
                if (!builder.resolveParent(function)) continue;
                Advancement advancement = builder.build(object);
                this.advancements.put(object, advancement);
                bl = true;
                iterator2.remove();
                if (advancement.getParent() == null) {
                    this.roots.add(advancement);
                    if (this.listener == null) continue;
                    this.listener.rootAdvancementAdded(advancement);
                    continue;
                }
                this.nonRoots.add(advancement);
                if (this.listener == null) continue;
                this.listener.nonRootAdvancementAdded(advancement);
            }
            if (bl) continue;
            for (Map.Entry entry : map.entrySet()) {
                LOGGER.error("Couldn't load advancement {}: {}", entry.getKey(), entry.getValue());
            }
        }
        LOGGER.info("Loaded {} advancements", (Object)this.advancements.size());
    }

    public void clear() {
        this.advancements.clear();
        this.roots.clear();
        this.nonRoots.clear();
        if (this.listener != null) {
            this.listener.advancementsCleared();
        }
    }

    public Iterable<Advancement> getRoots() {
        return this.roots;
    }

    public Collection<Advancement> getAll() {
        return this.advancements.values();
    }

    @Nullable
    public Advancement getAdvancement(ResourceLocation resourceLocation) {
        return this.advancements.get(resourceLocation);
    }

    public void setListener(@Nullable IListener iListener) {
        this.listener = iListener;
        if (iListener != null) {
            for (Advancement advancement : this.roots) {
                iListener.rootAdvancementAdded(advancement);
            }
            for (Advancement advancement : this.nonRoots) {
                iListener.nonRootAdvancementAdded(advancement);
            }
        }
    }

    public static interface IListener {
        public void rootAdvancementAdded(Advancement var1);

        public void rootAdvancementRemoved(Advancement var1);

        public void nonRootAdvancementAdded(Advancement var1);

        public void nonRootAdvancementRemoved(Advancement var1);

        public void advancementsCleared();
    }
}

