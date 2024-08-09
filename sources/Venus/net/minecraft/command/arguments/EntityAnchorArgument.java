/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class EntityAnchorArgument
implements ArgumentType<Type> {
    private static final Collection<String> EXMAPLES = Arrays.asList("eyes", "feet");
    private static final DynamicCommandExceptionType ANCHOR_INVALID = new DynamicCommandExceptionType(EntityAnchorArgument::lambda$static$0);

    public static Type getEntityAnchor(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, Type.class);
    }

    public static EntityAnchorArgument entityAnchor() {
        return new EntityAnchorArgument();
    }

    @Override
    public Type parse(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        String string = stringReader.readUnquotedString();
        Type type = Type.getByName(string);
        if (type == null) {
            stringReader.setCursor(n);
            throw ANCHOR_INVALID.createWithContext(stringReader, string);
        }
        return type;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return ISuggestionProvider.suggest(Type.BY_NAME.keySet(), suggestionsBuilder);
    }

    @Override
    public Collection<String> getExamples() {
        return EXMAPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("argument.anchor.invalid", object);
    }

    public static enum Type {
        FEET("feet", Type::lambda$static$0),
        EYES("eyes", Type::lambda$static$1);

        private static final Map<String, Type> BY_NAME;
        private final String name;
        private final BiFunction<Vector3d, Entity, Vector3d> offsetFunc;

        private Type(String string2, BiFunction<Vector3d, Entity, Vector3d> biFunction) {
            this.name = string2;
            this.offsetFunc = biFunction;
        }

        @Nullable
        public static Type getByName(String string) {
            return BY_NAME.get(string);
        }

        public Vector3d apply(Entity entity2) {
            return this.offsetFunc.apply(entity2.getPositionVec(), entity2);
        }

        public Vector3d apply(CommandSource commandSource) {
            Entity entity2 = commandSource.getEntity();
            return entity2 == null ? commandSource.getPos() : this.offsetFunc.apply(commandSource.getPos(), entity2);
        }

        private static void lambda$static$2(HashMap hashMap) {
            for (Type type : Type.values()) {
                hashMap.put(type.name, type);
            }
        }

        private static Vector3d lambda$static$1(Vector3d vector3d, Entity entity2) {
            return new Vector3d(vector3d.x, vector3d.y + (double)entity2.getEyeHeight(), vector3d.z);
        }

        private static Vector3d lambda$static$0(Vector3d vector3d, Entity entity2) {
            return vector3d;
        }

        static {
            BY_NAME = Util.make(Maps.newHashMap(), Type::lambda$static$2);
        }
    }
}

