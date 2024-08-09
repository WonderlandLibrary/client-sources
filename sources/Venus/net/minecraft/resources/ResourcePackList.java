/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackInfo;

public class ResourcePackList
implements AutoCloseable {
    private final Set<IPackFinder> packFinders;
    private Map<String, ResourcePackInfo> packNameToInfo = ImmutableMap.of();
    private List<ResourcePackInfo> enabled = ImmutableList.of();
    private final ResourcePackInfo.IFactory packInfoFactory;

    public ResourcePackList(ResourcePackInfo.IFactory iFactory, IPackFinder ... iPackFinderArray) {
        this.packInfoFactory = iFactory;
        this.packFinders = ImmutableSet.copyOf(iPackFinderArray);
    }

    public ResourcePackList(IPackFinder ... iPackFinderArray) {
        this(ResourcePackInfo::new, iPackFinderArray);
    }

    public void reloadPacksFromFinders() {
        List list = this.enabled.stream().map(ResourcePackInfo::getName).collect(ImmutableList.toImmutableList());
        this.close();
        this.packNameToInfo = this.func_232624_g_();
        this.enabled = this.func_232618_b_(list);
    }

    private Map<String, ResourcePackInfo> func_232624_g_() {
        TreeMap treeMap = Maps.newTreeMap();
        for (IPackFinder iPackFinder : this.packFinders) {
            iPackFinder.findPacks(arg_0 -> ResourcePackList.lambda$func_232624_g_$0(treeMap, arg_0), this.packInfoFactory);
        }
        return ImmutableMap.copyOf(treeMap);
    }

    public void setEnabledPacks(Collection<String> collection) {
        this.enabled = this.func_232618_b_(collection);
    }

    private List<ResourcePackInfo> func_232618_b_(Collection<String> collection) {
        List list = this.func_232620_c_(collection).collect(Collectors.toList());
        for (ResourcePackInfo resourcePackInfo : this.packNameToInfo.values()) {
            if (!resourcePackInfo.isAlwaysEnabled() || list.contains(resourcePackInfo)) continue;
            resourcePackInfo.getPriority().insert(list, resourcePackInfo, Functions.identity(), true);
        }
        return ImmutableList.copyOf(list);
    }

    private Stream<ResourcePackInfo> func_232620_c_(Collection<String> collection) {
        return collection.stream().map(this.packNameToInfo::get).filter(Objects::nonNull);
    }

    public Collection<String> func_232616_b_() {
        return this.packNameToInfo.keySet();
    }

    public Collection<ResourcePackInfo> getAllPacks() {
        return this.packNameToInfo.values();
    }

    public Collection<String> func_232621_d_() {
        return this.enabled.stream().map(ResourcePackInfo::getName).collect(ImmutableSet.toImmutableSet());
    }

    public Collection<ResourcePackInfo> getEnabledPacks() {
        return this.enabled;
    }

    @Nullable
    public ResourcePackInfo getPackInfo(String string) {
        return this.packNameToInfo.get(string);
    }

    @Override
    public void close() {
        this.packNameToInfo.values().forEach(ResourcePackInfo::close);
    }

    public boolean func_232617_b_(String string) {
        return this.packNameToInfo.containsKey(string);
    }

    public List<IResourcePack> func_232623_f_() {
        return this.enabled.stream().map(ResourcePackInfo::getResourcePack).collect(ImmutableList.toImmutableList());
    }

    private static void lambda$func_232624_g_$0(Map map, ResourcePackInfo resourcePackInfo) {
        ResourcePackInfo resourcePackInfo2 = map.put(resourcePackInfo.getName(), resourcePackInfo);
    }
}

