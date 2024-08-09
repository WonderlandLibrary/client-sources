/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.command.arguments.NBTTagArgument;
import net.minecraft.command.impl.data.BlockDataAccessor;
import net.minecraft.command.impl.data.EntityDataAccessor;
import net.minecraft.command.impl.data.IDataAccessor;
import net.minecraft.command.impl.data.StorageAccessor;
import net.minecraft.nbt.CollectionNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

public class DataCommand {
    private static final SimpleCommandExceptionType NOTHING_CHANGED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.data.merge.failed"));
    private static final DynamicCommandExceptionType GET_INVALID_EXCEPTION = new DynamicCommandExceptionType(DataCommand::lambda$static$0);
    private static final DynamicCommandExceptionType GET_UNKNOWN_EXCEPTION = new DynamicCommandExceptionType(DataCommand::lambda$static$1);
    private static final SimpleCommandExceptionType field_218957_g = new SimpleCommandExceptionType(new TranslationTextComponent("commands.data.get.multiple"));
    private static final DynamicCommandExceptionType field_218958_h = new DynamicCommandExceptionType(DataCommand::lambda$static$2);
    private static final DynamicCommandExceptionType field_218959_i = new DynamicCommandExceptionType(DataCommand::lambda$static$3);
    private static final DynamicCommandExceptionType field_218960_j = new DynamicCommandExceptionType(DataCommand::lambda$static$4);
    public static final List<Function<String, IDataProvider>> DATA_PROVIDERS = ImmutableList.of(EntityDataAccessor.DATA_PROVIDER, BlockDataAccessor.DATA_PROVIDER, StorageAccessor.field_229833_a_);
    public static final List<IDataProvider> field_218955_b = DATA_PROVIDERS.stream().map(DataCommand::lambda$static$5).collect(ImmutableList.toImmutableList());
    public static final List<IDataProvider> field_218956_c = DATA_PROVIDERS.stream().map(DataCommand::lambda$static$6).collect(ImmutableList.toImmutableList());

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        LiteralArgumentBuilder literalArgumentBuilder = (LiteralArgumentBuilder)Commands.literal("data").requires(DataCommand::lambda$register$7);
        for (IDataProvider iDataProvider : field_218955_b) {
            ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(iDataProvider.createArgument(Commands.literal("merge"), arg_0 -> DataCommand.lambda$register$9(iDataProvider, arg_0)))).then(iDataProvider.createArgument(Commands.literal("get"), arg_0 -> DataCommand.lambda$register$13(iDataProvider, arg_0)))).then(iDataProvider.createArgument(Commands.literal("remove"), arg_0 -> DataCommand.lambda$register$15(iDataProvider, arg_0)))).then(DataCommand.func_218935_a(DataCommand::lambda$register$21));
        }
        commandDispatcher.register(literalArgumentBuilder);
    }

    private static int func_218944_a(int n, CompoundNBT compoundNBT, NBTPathArgument.NBTPath nBTPath, List<INBT> list) throws CommandSyntaxException {
        List<INBT> list2 = nBTPath.func_218073_a(compoundNBT, ListNBT::new);
        int n2 = 0;
        for (INBT iNBT : list2) {
            if (!(iNBT instanceof CollectionNBT)) {
                throw field_218958_h.create(iNBT);
            }
            boolean bl = false;
            CollectionNBT collectionNBT = (CollectionNBT)iNBT;
            int n3 = n < 0 ? collectionNBT.size() + n + 1 : n;
            for (INBT iNBT2 : list) {
                try {
                    if (!collectionNBT.addNBTByIndex(n3, iNBT2.copy())) continue;
                    ++n3;
                    bl = true;
                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw field_218960_j.create(n3);
                }
            }
            n2 += bl ? 1 : 0;
        }
        return n2;
    }

    private static ArgumentBuilder<CommandSource, ?> func_218935_a(BiConsumer<ArgumentBuilder<CommandSource, ?>, IModificationSourceArgumentBuilder> biConsumer) {
        LiteralArgumentBuilder<CommandSource> literalArgumentBuilder = Commands.literal("modify");
        for (IDataProvider iDataProvider : field_218955_b) {
            iDataProvider.createArgument(literalArgumentBuilder, arg_0 -> DataCommand.lambda$func_218935_a$28(biConsumer, iDataProvider, arg_0));
        }
        return literalArgumentBuilder;
    }

    private static int func_218933_a(CommandContext<CommandSource> commandContext, IDataProvider iDataProvider, IModificationType iModificationType, List<INBT> list) throws CommandSyntaxException {
        IDataAccessor iDataAccessor = iDataProvider.createAccessor(commandContext);
        NBTPathArgument.NBTPath nBTPath = NBTPathArgument.getNBTPath(commandContext, "targetPath");
        CompoundNBT compoundNBT = iDataAccessor.getData();
        int n = iModificationType.modify(commandContext, compoundNBT, nBTPath, list);
        if (n == 0) {
            throw NOTHING_CHANGED.create();
        }
        iDataAccessor.mergeData(compoundNBT);
        commandContext.getSource().sendFeedback(iDataAccessor.getModifiedMessage(), false);
        return n;
    }

    private static int remove(CommandSource commandSource, IDataAccessor iDataAccessor, NBTPathArgument.NBTPath nBTPath) throws CommandSyntaxException {
        CompoundNBT compoundNBT = iDataAccessor.getData();
        int n = nBTPath.func_218068_c(compoundNBT);
        if (n == 0) {
            throw NOTHING_CHANGED.create();
        }
        iDataAccessor.mergeData(compoundNBT);
        commandSource.sendFeedback(iDataAccessor.getModifiedMessage(), false);
        return n;
    }

    private static INBT func_218928_a(NBTPathArgument.NBTPath nBTPath, IDataAccessor iDataAccessor) throws CommandSyntaxException {
        List<INBT> list = nBTPath.func_218071_a(iDataAccessor.getData());
        Iterator iterator2 = list.iterator();
        INBT iNBT = (INBT)iterator2.next();
        if (iterator2.hasNext()) {
            throw field_218957_g.create();
        }
        return iNBT;
    }

    private static int get(CommandSource commandSource, IDataAccessor iDataAccessor, NBTPathArgument.NBTPath nBTPath) throws CommandSyntaxException {
        int n;
        INBT iNBT = DataCommand.func_218928_a(nBTPath, iDataAccessor);
        if (iNBT instanceof NumberNBT) {
            n = MathHelper.floor(((NumberNBT)iNBT).getDouble());
        } else if (iNBT instanceof CollectionNBT) {
            n = ((CollectionNBT)iNBT).size();
        } else if (iNBT instanceof CompoundNBT) {
            n = ((CompoundNBT)iNBT).size();
        } else {
            if (!(iNBT instanceof StringNBT)) {
                throw GET_UNKNOWN_EXCEPTION.create(nBTPath.toString());
            }
            n = iNBT.getString().length();
        }
        commandSource.sendFeedback(iDataAccessor.getQueryMessage(iNBT), true);
        return n;
    }

    private static int getScaled(CommandSource commandSource, IDataAccessor iDataAccessor, NBTPathArgument.NBTPath nBTPath, double d) throws CommandSyntaxException {
        INBT iNBT = DataCommand.func_218928_a(nBTPath, iDataAccessor);
        if (!(iNBT instanceof NumberNBT)) {
            throw GET_INVALID_EXCEPTION.create(nBTPath.toString());
        }
        int n = MathHelper.floor(((NumberNBT)iNBT).getDouble() * d);
        commandSource.sendFeedback(iDataAccessor.getGetMessage(nBTPath, d, n), true);
        return n;
    }

    private static int get(CommandSource commandSource, IDataAccessor iDataAccessor) throws CommandSyntaxException {
        commandSource.sendFeedback(iDataAccessor.getQueryMessage(iDataAccessor.getData()), true);
        return 0;
    }

    private static int merge(CommandSource commandSource, IDataAccessor iDataAccessor, CompoundNBT compoundNBT) throws CommandSyntaxException {
        CompoundNBT compoundNBT2;
        CompoundNBT compoundNBT3 = iDataAccessor.getData();
        if (compoundNBT3.equals(compoundNBT2 = compoundNBT3.copy().merge(compoundNBT))) {
            throw NOTHING_CHANGED.create();
        }
        iDataAccessor.mergeData(compoundNBT2);
        commandSource.sendFeedback(iDataAccessor.getModifiedMessage(), false);
        return 0;
    }

    private static ArgumentBuilder lambda$func_218935_a$28(BiConsumer biConsumer, IDataProvider iDataProvider, ArgumentBuilder argumentBuilder) {
        RequiredArgumentBuilder<CommandSource, NBTPathArgument.NBTPath> requiredArgumentBuilder = Commands.argument("targetPath", NBTPathArgument.nbtPath());
        for (IDataProvider iDataProvider2 : field_218956_c) {
            biConsumer.accept(requiredArgumentBuilder, arg_0 -> DataCommand.lambda$func_218935_a$25(iDataProvider2, iDataProvider, arg_0));
        }
        biConsumer.accept(requiredArgumentBuilder, arg_0 -> DataCommand.lambda$func_218935_a$27(iDataProvider, arg_0));
        return argumentBuilder.then(requiredArgumentBuilder);
    }

    private static ArgumentBuilder lambda$func_218935_a$27(IDataProvider iDataProvider, IModificationType iModificationType) {
        return Commands.literal("value").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("value", NBTTagArgument.func_218085_a()).executes(arg_0 -> DataCommand.lambda$func_218935_a$26(iDataProvider, iModificationType, arg_0)));
    }

    private static int lambda$func_218935_a$26(IDataProvider iDataProvider, IModificationType iModificationType, CommandContext commandContext) throws CommandSyntaxException {
        List<INBT> list = Collections.singletonList(NBTTagArgument.func_218086_a(commandContext, "value"));
        return DataCommand.func_218933_a(commandContext, iDataProvider, iModificationType, list);
    }

    private static ArgumentBuilder lambda$func_218935_a$25(IDataProvider iDataProvider, IDataProvider iDataProvider2, IModificationType iModificationType) {
        return iDataProvider.createArgument(Commands.literal("from"), arg_0 -> DataCommand.lambda$func_218935_a$24(iDataProvider, iDataProvider2, iModificationType, arg_0));
    }

    private static ArgumentBuilder lambda$func_218935_a$24(IDataProvider iDataProvider, IDataProvider iDataProvider2, IModificationType iModificationType, ArgumentBuilder argumentBuilder) {
        return ((ArgumentBuilder)argumentBuilder.executes(arg_0 -> DataCommand.lambda$func_218935_a$22(iDataProvider, iDataProvider2, iModificationType, arg_0))).then(Commands.argument("sourcePath", NBTPathArgument.nbtPath()).executes(arg_0 -> DataCommand.lambda$func_218935_a$23(iDataProvider, iDataProvider2, iModificationType, arg_0)));
    }

    private static int lambda$func_218935_a$23(IDataProvider iDataProvider, IDataProvider iDataProvider2, IModificationType iModificationType, CommandContext commandContext) throws CommandSyntaxException {
        IDataAccessor iDataAccessor = iDataProvider.createAccessor(commandContext);
        NBTPathArgument.NBTPath nBTPath = NBTPathArgument.getNBTPath(commandContext, "sourcePath");
        List<INBT> list = nBTPath.func_218071_a(iDataAccessor.getData());
        return DataCommand.func_218933_a(commandContext, iDataProvider2, iModificationType, list);
    }

    private static int lambda$func_218935_a$22(IDataProvider iDataProvider, IDataProvider iDataProvider2, IModificationType iModificationType, CommandContext commandContext) throws CommandSyntaxException {
        List<INBT> list = Collections.singletonList(iDataProvider.createAccessor(commandContext).getData());
        return DataCommand.func_218933_a(commandContext, iDataProvider2, iModificationType, list);
    }

    private static void lambda$register$21(ArgumentBuilder argumentBuilder, IModificationSourceArgumentBuilder iModificationSourceArgumentBuilder) {
        ((ArgumentBuilder)((ArgumentBuilder)((ArgumentBuilder)((ArgumentBuilder)argumentBuilder.then(Commands.literal("insert").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("index", IntegerArgumentType.integer()).then(iModificationSourceArgumentBuilder.create(DataCommand::lambda$register$16))))).then(Commands.literal("prepend").then(iModificationSourceArgumentBuilder.create(DataCommand::lambda$register$17)))).then(Commands.literal("append").then(iModificationSourceArgumentBuilder.create(DataCommand::lambda$register$18)))).then(Commands.literal("set").then(iModificationSourceArgumentBuilder.create(DataCommand::lambda$register$19)))).then(Commands.literal("merge").then(iModificationSourceArgumentBuilder.create(DataCommand::lambda$register$20)));
    }

    private static int lambda$register$20(CommandContext commandContext, CompoundNBT compoundNBT, NBTPathArgument.NBTPath nBTPath, List list) throws CommandSyntaxException {
        List<INBT> list2 = nBTPath.func_218073_a(compoundNBT, CompoundNBT::new);
        int n = 0;
        for (INBT iNBT : list2) {
            if (!(iNBT instanceof CompoundNBT)) {
                throw field_218959_i.create(iNBT);
            }
            CompoundNBT compoundNBT2 = (CompoundNBT)iNBT;
            CompoundNBT compoundNBT3 = compoundNBT2.copy();
            for (INBT iNBT2 : list) {
                if (!(iNBT2 instanceof CompoundNBT)) {
                    throw field_218959_i.create(iNBT2);
                }
                compoundNBT2.merge((CompoundNBT)iNBT2);
            }
            n += compoundNBT3.equals(compoundNBT2) ? 0 : 1;
        }
        return n;
    }

    private static int lambda$register$19(CommandContext commandContext, CompoundNBT compoundNBT, NBTPathArgument.NBTPath nBTPath, List list) throws CommandSyntaxException {
        return nBTPath.func_218076_b(compoundNBT, ((INBT)Iterables.getLast(list))::copy);
    }

    private static int lambda$register$18(CommandContext commandContext, CompoundNBT compoundNBT, NBTPathArgument.NBTPath nBTPath, List list) throws CommandSyntaxException {
        return DataCommand.func_218944_a(-1, compoundNBT, nBTPath, list);
    }

    private static int lambda$register$17(CommandContext commandContext, CompoundNBT compoundNBT, NBTPathArgument.NBTPath nBTPath, List list) throws CommandSyntaxException {
        return DataCommand.func_218944_a(0, compoundNBT, nBTPath, list);
    }

    private static int lambda$register$16(CommandContext commandContext, CompoundNBT compoundNBT, NBTPathArgument.NBTPath nBTPath, List list) throws CommandSyntaxException {
        int n = IntegerArgumentType.getInteger(commandContext, "index");
        return DataCommand.func_218944_a(n, compoundNBT, nBTPath, list);
    }

    private static ArgumentBuilder lambda$register$15(IDataProvider iDataProvider, ArgumentBuilder argumentBuilder) {
        return argumentBuilder.then(Commands.argument("path", NBTPathArgument.nbtPath()).executes(arg_0 -> DataCommand.lambda$register$14(iDataProvider, arg_0)));
    }

    private static int lambda$register$14(IDataProvider iDataProvider, CommandContext commandContext) throws CommandSyntaxException {
        return DataCommand.remove((CommandSource)commandContext.getSource(), iDataProvider.createAccessor(commandContext), NBTPathArgument.getNBTPath(commandContext, "path"));
    }

    private static ArgumentBuilder lambda$register$13(IDataProvider iDataProvider, ArgumentBuilder argumentBuilder) {
        return ((ArgumentBuilder)argumentBuilder.executes(arg_0 -> DataCommand.lambda$register$10(iDataProvider, arg_0))).then(((RequiredArgumentBuilder)Commands.argument("path", NBTPathArgument.nbtPath()).executes(arg_0 -> DataCommand.lambda$register$11(iDataProvider, arg_0))).then(Commands.argument("scale", DoubleArgumentType.doubleArg()).executes(arg_0 -> DataCommand.lambda$register$12(iDataProvider, arg_0))));
    }

    private static int lambda$register$12(IDataProvider iDataProvider, CommandContext commandContext) throws CommandSyntaxException {
        return DataCommand.getScaled((CommandSource)commandContext.getSource(), iDataProvider.createAccessor(commandContext), NBTPathArgument.getNBTPath(commandContext, "path"), DoubleArgumentType.getDouble(commandContext, "scale"));
    }

    private static int lambda$register$11(IDataProvider iDataProvider, CommandContext commandContext) throws CommandSyntaxException {
        return DataCommand.get((CommandSource)commandContext.getSource(), iDataProvider.createAccessor(commandContext), NBTPathArgument.getNBTPath(commandContext, "path"));
    }

    private static int lambda$register$10(IDataProvider iDataProvider, CommandContext commandContext) throws CommandSyntaxException {
        return DataCommand.get((CommandSource)commandContext.getSource(), iDataProvider.createAccessor(commandContext));
    }

    private static ArgumentBuilder lambda$register$9(IDataProvider iDataProvider, ArgumentBuilder argumentBuilder) {
        return argumentBuilder.then(Commands.argument("nbt", NBTCompoundTagArgument.nbt()).executes(arg_0 -> DataCommand.lambda$register$8(iDataProvider, arg_0)));
    }

    private static int lambda$register$8(IDataProvider iDataProvider, CommandContext commandContext) throws CommandSyntaxException {
        return DataCommand.merge((CommandSource)commandContext.getSource(), iDataProvider.createAccessor(commandContext), NBTCompoundTagArgument.getNbt(commandContext, "nbt"));
    }

    private static boolean lambda$register$7(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static IDataProvider lambda$static$6(Function function) {
        return (IDataProvider)function.apply("source");
    }

    private static IDataProvider lambda$static$5(Function function) {
        return (IDataProvider)function.apply("target");
    }

    private static Message lambda$static$4(Object object) {
        return new TranslationTextComponent("commands.data.modify.invalid_index", object);
    }

    private static Message lambda$static$3(Object object) {
        return new TranslationTextComponent("commands.data.modify.expected_object", object);
    }

    private static Message lambda$static$2(Object object) {
        return new TranslationTextComponent("commands.data.modify.expected_list", object);
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("commands.data.get.unknown", object);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("commands.data.get.invalid", object);
    }

    public static interface IDataProvider {
        public IDataAccessor createAccessor(CommandContext<CommandSource> var1) throws CommandSyntaxException;

        public ArgumentBuilder<CommandSource, ?> createArgument(ArgumentBuilder<CommandSource, ?> var1, Function<ArgumentBuilder<CommandSource, ?>, ArgumentBuilder<CommandSource, ?>> var2);
    }

    static interface IModificationType {
        public int modify(CommandContext<CommandSource> var1, CompoundNBT var2, NBTPathArgument.NBTPath var3, List<INBT> var4) throws CommandSyntaxException;
    }

    static interface IModificationSourceArgumentBuilder {
        public ArgumentBuilder<CommandSource, ?> create(IModificationType var1);
    }
}

