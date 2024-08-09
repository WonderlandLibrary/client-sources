/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.command.impl.data.DataCommand;
import net.minecraft.command.impl.data.IDataAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class EntityDataAccessor
implements IDataAccessor {
    private static final SimpleCommandExceptionType DATA_ENTITY_INVALID = new SimpleCommandExceptionType(new TranslationTextComponent("commands.data.entity.invalid"));
    public static final Function<String, DataCommand.IDataProvider> DATA_PROVIDER = EntityDataAccessor::lambda$static$0;
    private final Entity entity;

    public EntityDataAccessor(Entity entity2) {
        this.entity = entity2;
    }

    @Override
    public void mergeData(CompoundNBT compoundNBT) throws CommandSyntaxException {
        if (this.entity instanceof PlayerEntity) {
            throw DATA_ENTITY_INVALID.create();
        }
        UUID uUID = this.entity.getUniqueID();
        this.entity.read(compoundNBT);
        this.entity.setUniqueId(uUID);
    }

    @Override
    public CompoundNBT getData() {
        return NBTPredicate.writeToNBTWithSelectedItem(this.entity);
    }

    @Override
    public ITextComponent getModifiedMessage() {
        return new TranslationTextComponent("commands.data.entity.modified", this.entity.getDisplayName());
    }

    @Override
    public ITextComponent getQueryMessage(INBT iNBT) {
        return new TranslationTextComponent("commands.data.entity.query", this.entity.getDisplayName(), iNBT.toFormattedComponent());
    }

    @Override
    public ITextComponent getGetMessage(NBTPathArgument.NBTPath nBTPath, double d, int n) {
        return new TranslationTextComponent("commands.data.entity.get", nBTPath, this.entity.getDisplayName(), String.format(Locale.ROOT, "%.2f", d), n);
    }

    private static DataCommand.IDataProvider lambda$static$0(String string) {
        return new DataCommand.IDataProvider(string){
            final String val$p_218922_0_;
            {
                this.val$p_218922_0_ = string;
            }

            @Override
            public IDataAccessor createAccessor(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
                return new EntityDataAccessor(EntityArgument.getEntity(commandContext, this.val$p_218922_0_));
            }

            @Override
            public ArgumentBuilder<CommandSource, ?> createArgument(ArgumentBuilder<CommandSource, ?> argumentBuilder, Function<ArgumentBuilder<CommandSource, ?>, ArgumentBuilder<CommandSource, ?>> function) {
                return argumentBuilder.then((ArgumentBuilder<CommandSource, ?>)Commands.literal("entity").then(function.apply(Commands.argument(this.val$p_218922_0_, EntityArgument.entity()))));
            }
        };
    }
}

