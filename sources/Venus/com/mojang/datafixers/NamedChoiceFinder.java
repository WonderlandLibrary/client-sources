/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Tag;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Either;
import java.util.Objects;

final class NamedChoiceFinder<FT>
implements OpticFinder<FT> {
    private final String name;
    private final Type<FT> type;

    public NamedChoiceFinder(String string, Type<FT> type) {
        this.name = string;
        this.type = type;
    }

    @Override
    public Type<FT> type() {
        return this.type;
    }

    @Override
    public <A, FR> Either<TypedOptic<A, ?, FT, FR>, Type.FieldNotFoundException> findType(Type<A> type, Type<FR> type2, boolean bl) {
        return type.findTypeCached(this.type, type2, new Matcher<FT, FR>(this.name, this.type, type2), bl);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof NamedChoiceFinder)) {
            return true;
        }
        NamedChoiceFinder namedChoiceFinder = (NamedChoiceFinder)object;
        return Objects.equals(this.name, namedChoiceFinder.name) && Objects.equals(this.type, namedChoiceFinder.type);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.type);
    }

    private static class Matcher<FT, FR>
    implements Type.TypeMatcher<FT, FR> {
        private final Type<FR> resultType;
        private final String name;
        private final Type<FT> type;

        public Matcher(String string, Type<FT> type, Type<FR> type2) {
            this.resultType = type2;
            this.name = string;
            this.type = type;
        }

        @Override
        public <S> Either<TypedOptic<S, ?, FT, FR>, Type.FieldNotFoundException> match(Type<S> type) {
            if (type instanceof TaggedChoice.TaggedChoiceType) {
                TaggedChoice.TaggedChoiceType taggedChoiceType = (TaggedChoice.TaggedChoiceType)type;
                Type<?> type2 = taggedChoiceType.types().get(this.name);
                if (type2 != null) {
                    if (!Objects.equals(this.type, type2)) {
                        return Either.right(new Type.FieldNotFoundException(String.format("Type error for choice type \"%s\": expected type: %s, actual type: %s)", this.name, type, type2)));
                    }
                    return Either.left(TypedOptic.tagged(taggedChoiceType, this.name, this.type, this.resultType));
                }
                return Either.right(new Type.Continue());
            }
            if (type instanceof Tag.TagType) {
                return Either.right(new Type.FieldNotFoundException("in tag"));
            }
            return Either.right(new Type.Continue());
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            Matcher matcher = (Matcher)object;
            return Objects.equals(this.resultType, matcher.resultType) && Objects.equals(this.name, matcher.name) && Objects.equals(this.type, matcher.type);
        }

        public int hashCode() {
            return Objects.hash(this.resultType, this.name, this.type);
        }
    }
}

