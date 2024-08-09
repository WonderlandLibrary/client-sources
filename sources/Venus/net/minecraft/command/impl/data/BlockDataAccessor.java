/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.command.impl.data.DataCommand;
import net.minecraft.command.impl.data.IDataAccessor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BlockDataAccessor
implements IDataAccessor {
    private static final SimpleCommandExceptionType DATA_BLOCK_INVALID_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.data.block.invalid"));
    public static final Function<String, DataCommand.IDataProvider> DATA_PROVIDER = BlockDataAccessor::lambda$static$0;
    private final TileEntity tileEntity;
    private final BlockPos pos;

    public BlockDataAccessor(TileEntity tileEntity, BlockPos blockPos) {
        this.tileEntity = tileEntity;
        this.pos = blockPos;
    }

    @Override
    public void mergeData(CompoundNBT compoundNBT) {
        compoundNBT.putInt("x", this.pos.getX());
        compoundNBT.putInt("y", this.pos.getY());
        compoundNBT.putInt("z", this.pos.getZ());
        BlockState blockState = this.tileEntity.getWorld().getBlockState(this.pos);
        this.tileEntity.read(blockState, compoundNBT);
        this.tileEntity.markDirty();
        this.tileEntity.getWorld().notifyBlockUpdate(this.pos, blockState, blockState, 3);
    }

    @Override
    public CompoundNBT getData() {
        return this.tileEntity.write(new CompoundNBT());
    }

    @Override
    public ITextComponent getModifiedMessage() {
        return new TranslationTextComponent("commands.data.block.modified", this.pos.getX(), this.pos.getY(), this.pos.getZ());
    }

    @Override
    public ITextComponent getQueryMessage(INBT iNBT) {
        return new TranslationTextComponent("commands.data.block.query", this.pos.getX(), this.pos.getY(), this.pos.getZ(), iNBT.toFormattedComponent());
    }

    @Override
    public ITextComponent getGetMessage(NBTPathArgument.NBTPath nBTPath, double d, int n) {
        return new TranslationTextComponent("commands.data.block.get", nBTPath, this.pos.getX(), this.pos.getY(), this.pos.getZ(), String.format(Locale.ROOT, "%.2f", d), n);
    }

    private static DataCommand.IDataProvider lambda$static$0(String string) {
        return new DataCommand.IDataProvider(string){
            final String val$p_218923_0_;
            {
                this.val$p_218923_0_ = string;
            }

            @Override
            public IDataAccessor createAccessor(CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
                BlockPos blockPos = BlockPosArgument.getLoadedBlockPos(commandContext, this.val$p_218923_0_ + "Pos");
                TileEntity tileEntity = commandContext.getSource().getWorld().getTileEntity(blockPos);
                if (tileEntity == null) {
                    throw DATA_BLOCK_INVALID_EXCEPTION.create();
                }
                return new BlockDataAccessor(tileEntity, blockPos);
            }

            @Override
            public ArgumentBuilder<CommandSource, ?> createArgument(ArgumentBuilder<CommandSource, ?> argumentBuilder, Function<ArgumentBuilder<CommandSource, ?>, ArgumentBuilder<CommandSource, ?>> function) {
                return argumentBuilder.then((ArgumentBuilder<CommandSource, ?>)Commands.literal("block").then(function.apply(Commands.argument(this.val$p_218923_0_ + "Pos", BlockPosArgument.blockPos()))));
            }
        };
    }
}

