/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.viaversion.viaversion.libs.kyori.adventure.inventory;

import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.inventory.BookImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ApiStatus.NonExtendable
public interface Book
extends Buildable<Book, Builder>,
Examinable {
    @NotNull
    public static Book book(@NotNull Component component, @NotNull Component component2, @NotNull Collection<Component> collection) {
        return new BookImpl(component, component2, new ArrayList<Component>(collection));
    }

    @NotNull
    public static Book book(@NotNull Component component, @NotNull Component component2, @NotNull @NotNull Component @NotNull ... componentArray) {
        return Book.book(component, component2, Arrays.asList(componentArray));
    }

    @NotNull
    public static Builder builder() {
        return new BookImpl.BuilderImpl();
    }

    @NotNull
    public Component title();

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public Book title(@NotNull Component var1);

    @NotNull
    public Component author();

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public Book author(@NotNull Component var1);

    public @Unmodifiable @NotNull List<Component> pages();

    @Contract(value="_ -> new", pure=true)
    @NotNull
    default public Book pages(@NotNull @NotNull Component @NotNull ... componentArray) {
        return this.pages(Arrays.asList(componentArray));
    }

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public Book pages(@NotNull List<Component> var1);

    @Override
    @NotNull
    default public Builder toBuilder() {
        return Book.builder().title(this.title()).author(this.author()).pages(this.pages());
    }

    @Override
    @NotNull
    default public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static interface Builder
    extends AbstractBuilder<Book>,
    Buildable.Builder<Book> {
        @Contract(value="_ -> this")
        @NotNull
        public Builder title(@NotNull Component var1);

        @Contract(value="_ -> this")
        @NotNull
        public Builder author(@NotNull Component var1);

        @Contract(value="_ -> this")
        @NotNull
        public Builder addPage(@NotNull Component var1);

        @Contract(value="_ -> this")
        @NotNull
        public Builder pages(@NotNull @NotNull Component @NotNull ... var1);

        @Contract(value="_ -> this")
        @NotNull
        public Builder pages(@NotNull Collection<Component> var1);

        @Override
        @NotNull
        public Book build();

        @Override
        @NotNull
        default public Object build() {
            return this.build();
        }
    }
}

