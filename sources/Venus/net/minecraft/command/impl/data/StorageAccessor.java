/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.impl.data.DataCommand;
import net.minecraft.command.impl.data.IDataAccessor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.storage.CommandStorage;

public class StorageAccessor
implements IDataAccessor {
    private static final SuggestionProvider<CommandSource> field_229834_b_ = StorageAccessor::lambda$static$0;
    public static final Function<String, DataCommand.IDataProvider> field_229833_a_ = StorageAccessor::lambda$static$1;
    private final CommandStorage field_229835_c_;
    private final ResourceLocation field_229836_d_;

    private static CommandStorage func_229840_b_(CommandContext<CommandSource> commandContext) {
        return commandContext.getSource().getServer().func_229735_aN_();
    }

    private StorageAccessor(CommandStorage commandStorage, ResourceLocation resourceLocation) {
        this.field_229835_c_ = commandStorage;
        this.field_229836_d_ = resourceLocation;
    }

    @Override
    public void mergeData(CompoundNBT compoundNBT) {
        this.field_229835_c_.setData(this.field_229836_d_, compoundNBT);
    }

    @Override
    public CompoundNBT getData() {
        return this.field_229835_c_.getData(this.field_229836_d_);
    }

    @Override
    public ITextComponent getModifiedMessage() {
        return new TranslationTextComponent("commands.data.storage.modified", this.field_229836_d_);
    }

    @Override
    public ITextComponent getQueryMessage(INBT iNBT) {
        return new TranslationTextComponent("commands.data.storage.query", this.field_229836_d_, iNBT.toFormattedComponent());
    }

    @Override
    public ITextComponent getGetMessage(NBTPathArgument.NBTPath nBTPath, double d, int n) {
        return new TranslationTextComponent("commands.data.storage.get", nBTPath, this.field_229836_d_, String.format(Locale.ROOT, "%.2f", d), n);
    }

    private static DataCommand.IDataProvider lambda$static$1(String string) {
        return new DataCommand.IDataProvider(string){
            final String val$p_229839_0_;
            {
                this.val$p_229839_0_ = string;
            }

            @Override
            public IDataAccessor createAccessor(CommandContext<CommandSource> commandContext) {
                return new StorageAccessor(StorageAccessor.func_229840_b_(commandContext), ResourceLocationArgument.getResourceLocation(commandContext, this.val$p_229839_0_));
            }

            @Override
            public ArgumentBuilder<CommandSource, ?> createArgument(ArgumentBuilder<CommandSource, ?> argumentBuilder, Function<ArgumentBuilder<CommandSource, ?>, ArgumentBuilder<CommandSource, ?>> function) {
                return argumentBuilder.then((ArgumentBuilder<CommandSource, ?>)Commands.literal("storage").then(function.apply(Commands.argument(this.val$p_229839_0_, ResourceLocationArgument.resourceLocation()).suggests(field_229834_b_))));
            }
        };
    }

    private static CompletableFuture lambda$static$0(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.func_212476_a(StorageAccessor.func_229840_b_(commandContext).getSavedDataKeys(), suggestionsBuilder);
    }
}

