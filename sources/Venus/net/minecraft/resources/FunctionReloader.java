/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import net.minecraft.command.CommandSource;
import net.minecraft.command.FunctionObject;
import net.minecraft.command.ICommandSource;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.TagCollectionReader;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FunctionReloader
implements IFutureReloadListener {
    private static final Logger field_240924_a_ = LogManager.getLogger();
    private static final int field_240925_b_ = 10;
    private static final int field_240926_c_ = 11;
    private volatile Map<ResourceLocation, FunctionObject> field_240927_d_ = ImmutableMap.of();
    private final TagCollectionReader<FunctionObject> field_244357_e = new TagCollectionReader(this::func_240940_a_, "tags/functions", "function");
    private volatile ITagCollection<FunctionObject> field_240928_e_ = ITagCollection.getEmptyTagCollection();
    private final int field_240929_f_;
    private final CommandDispatcher<CommandSource> field_240930_g_;

    public Optional<FunctionObject> func_240940_a_(ResourceLocation resourceLocation) {
        return Optional.ofNullable(this.field_240927_d_.get(resourceLocation));
    }

    public Map<ResourceLocation, FunctionObject> func_240931_a_() {
        return this.field_240927_d_;
    }

    public ITagCollection<FunctionObject> func_240942_b_() {
        return this.field_240928_e_;
    }

    public ITag<FunctionObject> func_240943_b_(ResourceLocation resourceLocation) {
        return this.field_240928_e_.getTagByID(resourceLocation);
    }

    public FunctionReloader(int n, CommandDispatcher<CommandSource> commandDispatcher) {
        this.field_240929_f_ = n;
        this.field_240930_g_ = commandDispatcher;
    }

    @Override
    public CompletableFuture<Void> reload(IFutureReloadListener.IStage iStage, IResourceManager iResourceManager, IProfiler iProfiler, IProfiler iProfiler2, Executor executor, Executor executor2) {
        CompletableFuture<Map<ResourceLocation, ITag.Builder>> completableFuture = this.field_244357_e.readTagsFromManager(iResourceManager, executor);
        CompletionStage completionStage = CompletableFuture.supplyAsync(() -> FunctionReloader.lambda$reload$1(iResourceManager), executor).thenCompose(arg_0 -> this.lambda$reload$4(iResourceManager, executor, arg_0));
        return ((CompletableFuture)((CompletableFuture)completableFuture.thenCombine(completionStage, Pair::of)).thenCompose(iStage::markCompleteAwaitingOthers)).thenAcceptAsync(this::lambda$reload$7, executor2);
    }

    private static List<String> func_240934_a_(IResourceManager iResourceManager, ResourceLocation resourceLocation) {
        List<String> list;
        block8: {
            IResource iResource = iResourceManager.getResource(resourceLocation);
            try {
                list = IOUtils.readLines(iResource.getInputStream(), StandardCharsets.UTF_8);
                if (iResource == null) break block8;
            } catch (Throwable throwable) {
                try {
                    if (iResource != null) {
                        try {
                            iResource.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                } catch (IOException iOException) {
                    throw new CompletionException(iOException);
                }
            }
            iResource.close();
        }
        return list;
    }

    private void lambda$reload$7(Pair pair) {
        Map map = (Map)pair.getSecond();
        ImmutableMap.Builder builder = ImmutableMap.builder();
        map.forEach((arg_0, arg_1) -> FunctionReloader.lambda$reload$6(builder, arg_0, arg_1));
        this.field_240927_d_ = builder.build();
        this.field_240928_e_ = this.field_244357_e.buildTagCollectionFromMap((Map)pair.getFirst());
    }

    private static void lambda$reload$6(ImmutableMap.Builder builder, ResourceLocation resourceLocation, CompletableFuture completableFuture) {
        ((CompletableFuture)completableFuture.handle((arg_0, arg_1) -> FunctionReloader.lambda$reload$5(resourceLocation, builder, arg_0, arg_1))).join();
    }

    private static Object lambda$reload$5(ResourceLocation resourceLocation, ImmutableMap.Builder builder, FunctionObject functionObject, Throwable throwable) {
        if (throwable != null) {
            field_240924_a_.error("Failed to load function {}", (Object)resourceLocation, (Object)throwable);
        } else {
            builder.put(resourceLocation, functionObject);
        }
        return null;
    }

    private CompletionStage lambda$reload$4(IResourceManager iResourceManager, Executor executor, Collection collection) {
        HashMap<ResourceLocation, CompletableFuture<FunctionObject>> hashMap = Maps.newHashMap();
        CommandSource commandSource = new CommandSource(ICommandSource.DUMMY, Vector3d.ZERO, Vector2f.ZERO, null, this.field_240929_f_, "", StringTextComponent.EMPTY, null, null);
        for (ResourceLocation resourceLocation : collection) {
            String string = resourceLocation.getPath();
            ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), string.substring(field_240925_b_, string.length() - field_240926_c_));
            hashMap.put(resourceLocation2, CompletableFuture.supplyAsync(() -> this.lambda$reload$2(iResourceManager, resourceLocation, resourceLocation2, commandSource), executor));
        }
        CompletableFuture[] completableFutureArray = hashMap.values().toArray(new CompletableFuture[0]);
        return CompletableFuture.allOf(completableFutureArray).handle((arg_0, arg_1) -> FunctionReloader.lambda$reload$3(hashMap, arg_0, arg_1));
    }

    private static Map lambda$reload$3(Map map, Void void_, Throwable throwable) {
        return map;
    }

    private FunctionObject lambda$reload$2(IResourceManager iResourceManager, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, CommandSource commandSource) {
        List<String> list = FunctionReloader.func_240934_a_(iResourceManager, resourceLocation);
        return FunctionObject.func_237140_a_(resourceLocation2, this.field_240930_g_, commandSource, list);
    }

    private static Collection lambda$reload$1(IResourceManager iResourceManager) {
        return iResourceManager.getAllResourceLocations("functions", FunctionReloader::lambda$reload$0);
    }

    private static boolean lambda$reload$0(String string) {
        return string.endsWith(".mcfunction");
    }
}

