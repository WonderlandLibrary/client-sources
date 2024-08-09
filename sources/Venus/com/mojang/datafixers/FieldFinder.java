/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.Proj1;
import com.mojang.datafixers.optics.profunctors.Cartesian;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Tag;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import javax.annotation.Nullable;

public final class FieldFinder<FT>
implements OpticFinder<FT> {
    @Nullable
    private final String name;
    private final Type<FT> type;

    public FieldFinder(@Nullable String string, Type<FT> type) {
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
        if (!(object instanceof FieldFinder)) {
            return true;
        }
        FieldFinder fieldFinder = (FieldFinder)object;
        return Objects.equals(this.name, fieldFinder.name) && Objects.equals(this.type, fieldFinder.type);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.type);
    }

    private static final class Matcher<FT, FR>
    implements Type.TypeMatcher<FT, FR> {
        private final Type<FR> resultType;
        @Nullable
        private final String name;
        private final Type<FT> type;

        public Matcher(@Nullable String string, Type<FT> type, Type<FR> type2) {
            this.resultType = type2;
            this.name = string;
            this.type = type;
        }

        @Override
        public <S> Either<TypedOptic<S, ?, FT, FR>, Type.FieldNotFoundException> match(Type<S> type) {
            TaggedChoice.TaggedChoiceType taggedChoiceType;
            if (this.name == null && this.type.equals(type, true, true)) {
                return Either.left(new TypedOptic<S, FR, S, FR>(Profunctor.Mu.TYPE_TOKEN, type, this.resultType, type, this.resultType, Optics.id()));
            }
            if (type instanceof Tag.TagType) {
                Tag.TagType tagType = (Tag.TagType)type;
                if (!Objects.equals(tagType.name(), this.name)) {
                    return Either.right(new Type.FieldNotFoundException(String.format("Not found: \"%s\" (in type: %s)", this.name, type)));
                }
                if (!Objects.equals(this.type, tagType.element())) {
                    return Either.right(new Type.FieldNotFoundException(String.format("Type error for field \"%s\": expected type: %s, actual type: %s)", this.name, this.type, tagType.element())));
                }
                return Either.left(new TypedOptic(Profunctor.Mu.TYPE_TOKEN, tagType, DSL.field(tagType.name(), this.resultType), this.type, this.resultType, Optics.id()));
            }
            if (type instanceof TaggedChoice.TaggedChoiceType && Objects.equals(this.name, (taggedChoiceType = (TaggedChoice.TaggedChoiceType)type).getName())) {
                if (!Objects.equals(this.type, taggedChoiceType.getKeyType())) {
                    return Either.right(new Type.FieldNotFoundException(String.format("Type error for field \"%s\": expected type: %s, actual type: %s)", this.name, this.type, taggedChoiceType.getKeyType())));
                }
                if (!Objects.equals(this.type, this.resultType)) {
                    return Either.right(new Type.FieldNotFoundException("TaggedChoiceType key type change is unsupported."));
                }
                return Either.left(this.capChoice(taggedChoiceType));
            }
            return Either.right(new Type.Continue());
        }

        private <V> TypedOptic<Pair<FT, V>, ?, FT, FT> capChoice(Type<?> type) {
            return new TypedOptic(Cartesian.Mu.TYPE_TOKEN, type, type, this.type, this.type, new Proj1());
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

