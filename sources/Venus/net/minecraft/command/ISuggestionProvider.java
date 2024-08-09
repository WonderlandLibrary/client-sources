/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.World;

public interface ISuggestionProvider {
    public Collection<String> getPlayerNames();

    default public Collection<String> getTargetedEntity() {
        return Collections.emptyList();
    }

    public Collection<String> getTeamNames();

    public Collection<ResourceLocation> getSoundResourceLocations();

    public Stream<ResourceLocation> getRecipeResourceLocations();

    public CompletableFuture<Suggestions> getSuggestionsFromServer(CommandContext<ISuggestionProvider> var1, SuggestionsBuilder var2);

    default public Collection<Coordinates> func_217294_q() {
        return Collections.singleton(Coordinates.DEFAULT_GLOBAL);
    }

    default public Collection<Coordinates> func_217293_r() {
        return Collections.singleton(Coordinates.DEFAULT_GLOBAL);
    }

    public Set<RegistryKey<World>> func_230390_p_();

    public DynamicRegistries func_241861_q();

    public boolean hasPermissionLevel(int var1);

    public static <T> void func_210512_a(Iterable<T> iterable, String string, Function<T, ResourceLocation> function, Consumer<T> consumer) {
        boolean bl = string.indexOf(58) > -1;
        for (T t : iterable) {
            ResourceLocation resourceLocation = function.apply(t);
            if (bl) {
                String string2 = resourceLocation.toString();
                if (!ISuggestionProvider.func_237256_a_(string, string2)) continue;
                consumer.accept(t);
                continue;
            }
            if (!ISuggestionProvider.func_237256_a_(string, resourceLocation.getNamespace()) && (!resourceLocation.getNamespace().equals("minecraft") || !ISuggestionProvider.func_237256_a_(string, resourceLocation.getPath()))) continue;
            consumer.accept(t);
        }
    }

    public static <T> void func_210511_a(Iterable<T> iterable, String string, String string2, Function<T, ResourceLocation> function, Consumer<T> consumer) {
        if (string.isEmpty()) {
            iterable.forEach(consumer);
        } else {
            String string3 = Strings.commonPrefix(string, string2);
            if (!string3.isEmpty()) {
                String string4 = string.substring(string3.length());
                ISuggestionProvider.func_210512_a(iterable, string4, function, consumer);
            }
        }
    }

    public static CompletableFuture<Suggestions> suggestIterable(Iterable<ResourceLocation> iterable, SuggestionsBuilder suggestionsBuilder, String string) {
        String string2 = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        ISuggestionProvider.func_210511_a(iterable, string2, string, ISuggestionProvider::lambda$suggestIterable$0, arg_0 -> ISuggestionProvider.lambda$suggestIterable$1(suggestionsBuilder, string, arg_0));
        return suggestionsBuilder.buildFuture();
    }

    public static CompletableFuture<Suggestions> suggestIterable(Iterable<ResourceLocation> iterable, SuggestionsBuilder suggestionsBuilder) {
        String string = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        ISuggestionProvider.func_210512_a(iterable, string, ISuggestionProvider::lambda$suggestIterable$2, arg_0 -> ISuggestionProvider.lambda$suggestIterable$3(suggestionsBuilder, arg_0));
        return suggestionsBuilder.buildFuture();
    }

    public static <T> CompletableFuture<Suggestions> func_210514_a(Iterable<T> iterable, SuggestionsBuilder suggestionsBuilder, Function<T, ResourceLocation> function, Function<T, Message> function2) {
        String string = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        ISuggestionProvider.func_210512_a(iterable, string, function, arg_0 -> ISuggestionProvider.lambda$func_210514_a$4(suggestionsBuilder, function, function2, arg_0));
        return suggestionsBuilder.buildFuture();
    }

    public static CompletableFuture<Suggestions> func_212476_a(Stream<ResourceLocation> stream, SuggestionsBuilder suggestionsBuilder) {
        return ISuggestionProvider.suggestIterable(stream::iterator, suggestionsBuilder);
    }

    public static <T> CompletableFuture<Suggestions> func_201725_a(Stream<T> stream, SuggestionsBuilder suggestionsBuilder, Function<T, ResourceLocation> function, Function<T, Message> function2) {
        return ISuggestionProvider.func_210514_a(stream::iterator, suggestionsBuilder, function, function2);
    }

    public static CompletableFuture<Suggestions> func_209000_a(String string, Collection<Coordinates> collection, SuggestionsBuilder suggestionsBuilder, Predicate<String> predicate) {
        ArrayList<String> arrayList;
        block4: {
            String[] stringArray;
            block5: {
                block3: {
                    arrayList = Lists.newArrayList();
                    if (!Strings.isNullOrEmpty(string)) break block3;
                    for (Coordinates coordinates : collection) {
                        String string2 = coordinates.x + " " + coordinates.y + " " + coordinates.z;
                        if (!predicate.test(string2)) continue;
                        arrayList.add(coordinates.x);
                        arrayList.add(coordinates.x + " " + coordinates.y);
                        arrayList.add(string2);
                    }
                    break block4;
                }
                stringArray = string.split(" ");
                if (stringArray.length != 1) break block5;
                for (Coordinates coordinates : collection) {
                    String string3 = stringArray[0] + " " + coordinates.y + " " + coordinates.z;
                    if (!predicate.test(string3)) continue;
                    arrayList.add(stringArray[0] + " " + coordinates.y);
                    arrayList.add(string3);
                }
                break block4;
            }
            if (stringArray.length != 2) break block4;
            for (Coordinates coordinates : collection) {
                String string4 = stringArray[0] + " " + stringArray[5] + " " + coordinates.z;
                if (!predicate.test(string4)) continue;
                arrayList.add(string4);
            }
        }
        return ISuggestionProvider.suggest(arrayList, suggestionsBuilder);
    }

    public static CompletableFuture<Suggestions> func_211269_a(String string, Collection<Coordinates> collection, SuggestionsBuilder suggestionsBuilder, Predicate<String> predicate) {
        ArrayList<String> arrayList;
        block3: {
            block2: {
                arrayList = Lists.newArrayList();
                if (!Strings.isNullOrEmpty(string)) break block2;
                for (Coordinates coordinates : collection) {
                    String string2 = coordinates.x + " " + coordinates.z;
                    if (!predicate.test(string2)) continue;
                    arrayList.add(coordinates.x);
                    arrayList.add(string2);
                }
                break block3;
            }
            String[] stringArray = string.split(" ");
            if (stringArray.length != 1) break block3;
            for (Coordinates coordinates : collection) {
                String string3 = stringArray[0] + " " + coordinates.z;
                if (!predicate.test(string3)) continue;
                arrayList.add(string3);
            }
        }
        return ISuggestionProvider.suggest(arrayList, suggestionsBuilder);
    }

    public static CompletableFuture<Suggestions> suggest(Iterable<String> iterable, SuggestionsBuilder suggestionsBuilder) {
        String string = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        for (String string2 : iterable) {
            if (!ISuggestionProvider.func_237256_a_(string, string2.toLowerCase(Locale.ROOT))) continue;
            suggestionsBuilder.suggest(string2);
        }
        return suggestionsBuilder.buildFuture();
    }

    public static CompletableFuture<Suggestions> suggest(Stream<String> stream, SuggestionsBuilder suggestionsBuilder) {
        String string = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        stream.filter(arg_0 -> ISuggestionProvider.lambda$suggest$5(string, arg_0)).forEach(suggestionsBuilder::suggest);
        return suggestionsBuilder.buildFuture();
    }

    public static CompletableFuture<Suggestions> suggest(String[] stringArray, SuggestionsBuilder suggestionsBuilder) {
        String string = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        for (String string2 : stringArray) {
            if (!ISuggestionProvider.func_237256_a_(string, string2.toLowerCase(Locale.ROOT))) continue;
            suggestionsBuilder.suggest(string2);
        }
        return suggestionsBuilder.buildFuture();
    }

    public static boolean func_237256_a_(String string, String string2) {
        int n = 0;
        while (!string2.startsWith(string, n)) {
            if ((n = string2.indexOf(95, n)) < 0) {
                return true;
            }
            ++n;
        }
        return false;
    }

    private static boolean lambda$suggest$5(String string, String string2) {
        return ISuggestionProvider.func_237256_a_(string, string2.toLowerCase(Locale.ROOT));
    }

    private static void lambda$func_210514_a$4(SuggestionsBuilder suggestionsBuilder, Function function, Function function2, Object object) {
        suggestionsBuilder.suggest(((ResourceLocation)function.apply(object)).toString(), (Message)function2.apply(object));
    }

    private static void lambda$suggestIterable$3(SuggestionsBuilder suggestionsBuilder, ResourceLocation resourceLocation) {
        suggestionsBuilder.suggest(resourceLocation.toString());
    }

    private static ResourceLocation lambda$suggestIterable$2(ResourceLocation resourceLocation) {
        return resourceLocation;
    }

    private static void lambda$suggestIterable$1(SuggestionsBuilder suggestionsBuilder, String string, ResourceLocation resourceLocation) {
        suggestionsBuilder.suggest(string + resourceLocation);
    }

    private static ResourceLocation lambda$suggestIterable$0(ResourceLocation resourceLocation) {
        return resourceLocation;
    }

    public static class Coordinates {
        public static final Coordinates DEFAULT_LOCAL = new Coordinates("^", "^", "^");
        public static final Coordinates DEFAULT_GLOBAL = new Coordinates("~", "~", "~");
        public final String x;
        public final String y;
        public final String z;

        public Coordinates(String string, String string2, String string3) {
            this.x = string;
            this.y = string2;
            this.z = string3;
        }
    }
}

