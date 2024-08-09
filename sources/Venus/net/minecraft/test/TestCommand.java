/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.test;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockStateInput;
import net.minecraft.data.NBTToSNBTConverter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.test.ITestCallback;
import net.minecraft.test.StructureHelper;
import net.minecraft.test.TestArgArgument;
import net.minecraft.test.TestCollection;
import net.minecraft.test.TestFunctionInfo;
import net.minecraft.test.TestRegistry;
import net.minecraft.test.TestResultList;
import net.minecraft.test.TestTracker;
import net.minecraft.test.TestTypeArgument;
import net.minecraft.test.TestUtils;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.io.IOUtils;

public class TestCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("test").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("runthis").executes(TestCommand::lambda$register$0))).then(Commands.literal("runthese").executes(TestCommand::lambda$register$1))).then(((LiteralArgumentBuilder)Commands.literal("runfailed").executes(TestCommand::lambda$register$2)).then(((RequiredArgumentBuilder)Commands.argument("onlyRequiredTests", BoolArgumentType.bool()).executes(TestCommand::lambda$register$3)).then(((RequiredArgumentBuilder)Commands.argument("rotationSteps", IntegerArgumentType.integer()).executes(TestCommand::lambda$register$4)).then(Commands.argument("testsPerRow", IntegerArgumentType.integer()).executes(TestCommand::lambda$register$5)))))).then(Commands.literal("run").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("testName", TestArgArgument.func_229665_a_()).executes(TestCommand::lambda$register$6)).then(Commands.argument("rotationSteps", IntegerArgumentType.integer()).executes(TestCommand::lambda$register$7))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("runall").executes(TestCommand::lambda$register$8)).then(((RequiredArgumentBuilder)Commands.argument("testClassName", TestTypeArgument.func_229611_a_()).executes(TestCommand::lambda$register$9)).then(((RequiredArgumentBuilder)Commands.argument("rotationSteps", IntegerArgumentType.integer()).executes(TestCommand::lambda$register$10)).then(Commands.argument("testsPerRow", IntegerArgumentType.integer()).executes(TestCommand::lambda$register$11))))).then(((RequiredArgumentBuilder)Commands.argument("rotationSteps", IntegerArgumentType.integer()).executes(TestCommand::lambda$register$12)).then(Commands.argument("testsPerRow", IntegerArgumentType.integer()).executes(TestCommand::lambda$register$13))))).then(Commands.literal("export").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("testName", StringArgumentType.word()).executes(TestCommand::lambda$register$14)))).then(Commands.literal("exportthis").executes(TestCommand::lambda$register$15))).then(Commands.literal("import").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("testName", StringArgumentType.word()).executes(TestCommand::lambda$register$16)))).then(((LiteralArgumentBuilder)Commands.literal("pos").executes(TestCommand::lambda$register$17)).then(Commands.argument("var", StringArgumentType.word()).executes(TestCommand::lambda$register$18)))).then(Commands.literal("create").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("testName", StringArgumentType.word()).executes(TestCommand::lambda$register$19)).then(((RequiredArgumentBuilder)Commands.argument("width", IntegerArgumentType.integer()).executes(TestCommand::lambda$register$20)).then(Commands.argument("height", IntegerArgumentType.integer()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("depth", IntegerArgumentType.integer()).executes(TestCommand::lambda$register$21))))))).then(((LiteralArgumentBuilder)Commands.literal("clearall").executes(TestCommand::lambda$register$22)).then(Commands.argument("radius", IntegerArgumentType.integer()).executes(TestCommand::lambda$register$23))));
    }

    private static int func_229618_a_(CommandSource commandSource, String string, int n, int n2, int n3) {
        if (n <= 48 && n2 <= 48 && n3 <= 48) {
            ServerWorld serverWorld = commandSource.getWorld();
            BlockPos blockPos = new BlockPos(commandSource.getPos());
            BlockPos blockPos2 = new BlockPos(blockPos.getX(), commandSource.getWorld().getHeight(Heightmap.Type.WORLD_SURFACE, blockPos).getY(), blockPos.getZ() + 3);
            StructureHelper.func_229603_a_(string.toLowerCase(), blockPos2, new BlockPos(n, n2, n3), Rotation.NONE, serverWorld);
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n3; ++j) {
                    BlockPos blockPos3 = new BlockPos(blockPos2.getX() + i, blockPos2.getY() + 1, blockPos2.getZ() + j);
                    Block block = Blocks.POLISHED_ANDESITE;
                    BlockStateInput blockStateInput = new BlockStateInput(block.getDefaultState(), Collections.EMPTY_SET, null);
                    blockStateInput.place(serverWorld, blockPos3, 1);
                }
            }
            StructureHelper.func_240564_a_(blockPos2, new BlockPos(1, 0, -1), Rotation.NONE, serverWorld);
            return 1;
        }
        throw new IllegalArgumentException("The structure must be less than 48 blocks big in each axis");
    }

    private static int func_229617_a_(CommandSource commandSource, String string) throws CommandSyntaxException {
        ServerWorld serverWorld;
        BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult)commandSource.asPlayer().pick(10.0, 1.0f, true);
        BlockPos blockPos = blockRayTraceResult.getPos();
        Optional<BlockPos> optional = StructureHelper.func_229596_a_(blockPos, 15, serverWorld = commandSource.getWorld());
        if (!optional.isPresent()) {
            optional = StructureHelper.func_229596_a_(blockPos, 200, serverWorld);
        }
        if (!optional.isPresent()) {
            commandSource.sendErrorMessage(new StringTextComponent("Can't find a structure block that contains the targeted pos " + blockPos));
            return 1;
        }
        StructureBlockTileEntity structureBlockTileEntity = (StructureBlockTileEntity)serverWorld.getTileEntity(optional.get());
        BlockPos blockPos2 = blockPos.subtract(optional.get());
        String string2 = blockPos2.getX() + ", " + blockPos2.getY() + ", " + blockPos2.getZ();
        String string3 = structureBlockTileEntity.func_227014_f_();
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent(string2).setStyle(Style.EMPTY.setBold(true).setFormatting(TextFormatting.GREEN).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("Click to copy to clipboard"))).setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "final BlockPos " + string + " = new BlockPos(" + string2 + ");")));
        commandSource.sendFeedback(new StringTextComponent("Position relative to " + string3 + ": ").append(iFormattableTextComponent), true);
        DebugPacketSender.func_229752_a_(serverWorld, new BlockPos(blockPos), string2, -2147418368, 10000);
        return 0;
    }

    private static int func_229615_a_(CommandSource commandSource) {
        ServerWorld serverWorld;
        BlockPos blockPos = new BlockPos(commandSource.getPos());
        BlockPos blockPos2 = StructureHelper.func_229607_b_(blockPos, 15, serverWorld = commandSource.getWorld());
        if (blockPos2 == null) {
            TestCommand.func_229624_a_(serverWorld, "Couldn't find any structure block within 15 radius", TextFormatting.RED);
            return 1;
        }
        TestUtils.func_229552_a_(serverWorld);
        TestCommand.func_229623_a_(serverWorld, blockPos2, null);
        return 0;
    }

    private static int func_229629_b_(CommandSource commandSource) {
        ServerWorld serverWorld;
        BlockPos blockPos = new BlockPos(commandSource.getPos());
        Collection<BlockPos> collection = StructureHelper.func_229609_c_(blockPos, 200, serverWorld = commandSource.getWorld());
        if (collection.isEmpty()) {
            TestCommand.func_229624_a_(serverWorld, "Couldn't find any structure blocks within 200 block radius", TextFormatting.RED);
            return 0;
        }
        TestUtils.func_229552_a_(serverWorld);
        TestCommand.func_229634_c_(commandSource, "Running " + collection.size() + " tests...");
        TestResultList testResultList = new TestResultList();
        collection.forEach(arg_0 -> TestCommand.lambda$func_229629_b_$24(serverWorld, testResultList, arg_0));
        return 0;
    }

    private static void func_229623_a_(ServerWorld serverWorld, BlockPos blockPos, @Nullable TestResultList testResultList) {
        StructureBlockTileEntity structureBlockTileEntity = (StructureBlockTileEntity)serverWorld.getTileEntity(blockPos);
        String string = structureBlockTileEntity.func_227014_f_();
        TestFunctionInfo testFunctionInfo = TestRegistry.func_229538_e_(string);
        TestTracker testTracker = new TestTracker(testFunctionInfo, structureBlockTileEntity.getRotation(), serverWorld);
        if (testResultList != null) {
            testResultList.func_229579_a_(testTracker);
            testTracker.func_229504_a_(new Callback(serverWorld, testResultList));
        }
        TestCommand.func_229622_a_(testFunctionInfo, serverWorld);
        AxisAlignedBB axisAlignedBB = StructureHelper.func_229594_a_(structureBlockTileEntity);
        BlockPos blockPos2 = new BlockPos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        TestUtils.func_240553_a_(testTracker, blockPos2, TestCollection.field_229570_a_);
    }

    private static void func_229631_b_(ServerWorld serverWorld, TestResultList testResultList) {
        if (testResultList.func_229588_i_()) {
            TestCommand.func_229624_a_(serverWorld, "GameTest done! " + testResultList.func_229587_h_() + " tests were run", TextFormatting.WHITE);
            if (testResultList.func_229585_d_()) {
                TestCommand.func_229624_a_(serverWorld, testResultList.func_229578_a_() + " required tests failed :(", TextFormatting.RED);
            } else {
                TestCommand.func_229624_a_(serverWorld, "All required tests passed :)", TextFormatting.GREEN);
            }
            if (testResultList.func_229586_e_()) {
                TestCommand.func_229624_a_(serverWorld, testResultList.func_229583_b_() + " optional tests failed", TextFormatting.GRAY);
            }
        }
    }

    private static int func_229616_a_(CommandSource commandSource, int n) {
        ServerWorld serverWorld = commandSource.getWorld();
        TestUtils.func_229552_a_(serverWorld);
        BlockPos blockPos = new BlockPos(commandSource.getPos().x, (double)commandSource.getWorld().getHeight(Heightmap.Type.WORLD_SURFACE, new BlockPos(commandSource.getPos())).getY(), commandSource.getPos().z);
        TestUtils.func_229555_a_(serverWorld, blockPos, TestCollection.field_229570_a_, MathHelper.clamp(n, 0, 1024));
        return 0;
    }

    private static int func_229620_a_(CommandSource commandSource, TestFunctionInfo testFunctionInfo, int n) {
        ServerWorld serverWorld = commandSource.getWorld();
        BlockPos blockPos = new BlockPos(commandSource.getPos());
        int n2 = commandSource.getWorld().getHeight(Heightmap.Type.WORLD_SURFACE, blockPos).getY();
        BlockPos blockPos2 = new BlockPos(blockPos.getX(), n2, blockPos.getZ() + 3);
        TestUtils.func_229552_a_(serverWorld);
        TestCommand.func_229622_a_(testFunctionInfo, serverWorld);
        Rotation rotation = StructureHelper.func_240562_a_(n);
        TestTracker testTracker = new TestTracker(testFunctionInfo, rotation, serverWorld);
        TestUtils.func_240553_a_(testTracker, blockPos2, TestCollection.field_229570_a_);
        return 0;
    }

    private static void func_229622_a_(TestFunctionInfo testFunctionInfo, ServerWorld serverWorld) {
        Consumer<ServerWorld> consumer = TestRegistry.func_229536_c_(testFunctionInfo.func_229662_e_());
        if (consumer != null) {
            consumer.accept(serverWorld);
        }
    }

    private static int func_229633_c_(CommandSource commandSource, int n, int n2) {
        TestUtils.func_229552_a_(commandSource.getWorld());
        Collection<TestFunctionInfo> collection = TestRegistry.func_229529_a_();
        TestCommand.func_229634_c_(commandSource, "Running all " + collection.size() + " tests...");
        TestRegistry.func_240550_d_();
        TestCommand.func_229619_a_(commandSource, collection, n, n2);
        return 0;
    }

    private static int func_229630_b_(CommandSource commandSource, String string, int n, int n2) {
        Collection<TestFunctionInfo> collection = TestRegistry.func_229530_a_(string);
        TestUtils.func_229552_a_(commandSource.getWorld());
        TestCommand.func_229634_c_(commandSource, "Running " + collection.size() + " tests from " + string + "...");
        TestRegistry.func_240550_d_();
        TestCommand.func_229619_a_(commandSource, collection, n, n2);
        return 0;
    }

    private static int func_240574_a_(CommandSource commandSource, boolean bl, int n, int n2) {
        Collection collection = bl ? (Collection)TestRegistry.func_240549_c_().stream().filter(TestFunctionInfo::func_229661_d_).collect(Collectors.toList()) : TestRegistry.func_240549_c_();
        if (collection.isEmpty()) {
            TestCommand.func_229634_c_(commandSource, "No failed tests to rerun");
            return 1;
        }
        TestUtils.func_229552_a_(commandSource.getWorld());
        TestCommand.func_229634_c_(commandSource, "Rerunning " + collection.size() + " failed tests (" + (bl ? "only required tests" : "including optional tests") + ")");
        TestCommand.func_229619_a_(commandSource, collection, n, n2);
        return 0;
    }

    private static void func_229619_a_(CommandSource commandSource, Collection<TestFunctionInfo> collection, int n, int n2) {
        BlockPos blockPos = new BlockPos(commandSource.getPos());
        BlockPos blockPos2 = new BlockPos(blockPos.getX(), commandSource.getWorld().getHeight(Heightmap.Type.WORLD_SURFACE, blockPos).getY(), blockPos.getZ() + 3);
        ServerWorld serverWorld = commandSource.getWorld();
        Rotation rotation = StructureHelper.func_240562_a_(n);
        Collection<TestTracker> collection2 = TestUtils.func_240554_b_(collection, blockPos2, rotation, serverWorld, TestCollection.field_229570_a_, n2);
        TestResultList testResultList = new TestResultList(collection2);
        testResultList.func_240558_a_(new Callback(serverWorld, testResultList));
        testResultList.func_240556_a_(TestCommand::lambda$func_229619_a_$25);
    }

    private static void func_229634_c_(CommandSource commandSource, String string) {
        commandSource.sendFeedback(new StringTextComponent(string), true);
    }

    private static int func_240581_c_(CommandSource commandSource) {
        ServerWorld serverWorld;
        BlockPos blockPos = new BlockPos(commandSource.getPos());
        BlockPos blockPos2 = StructureHelper.func_229607_b_(blockPos, 15, serverWorld = commandSource.getWorld());
        if (blockPos2 == null) {
            TestCommand.func_229624_a_(serverWorld, "Couldn't find any structure block within 15 radius", TextFormatting.RED);
            return 1;
        }
        StructureBlockTileEntity structureBlockTileEntity = (StructureBlockTileEntity)serverWorld.getTileEntity(blockPos2);
        String string = structureBlockTileEntity.func_227014_f_();
        return TestCommand.func_229636_d_(commandSource, string);
    }

    private static int func_229636_d_(CommandSource commandSource, String string) {
        Path path = Paths.get(StructureHelper.field_229590_a_, new String[0]);
        ResourceLocation resourceLocation = new ResourceLocation("minecraft", string);
        Path path2 = commandSource.getWorld().getStructureTemplateManager().resolvePathStructures(resourceLocation, ".nbt");
        Path path3 = NBTToSNBTConverter.convertNBTToSNBT(path2, string, path);
        if (path3 == null) {
            TestCommand.func_229634_c_(commandSource, "Failed to export " + path2);
            return 0;
        }
        try {
            Files.createDirectories(path3.getParent(), new FileAttribute[0]);
        } catch (IOException iOException) {
            TestCommand.func_229634_c_(commandSource, "Could not create folder " + path3.getParent());
            iOException.printStackTrace();
            return 0;
        }
        TestCommand.func_229634_c_(commandSource, "Exported " + string + " to " + path3.toAbsolutePath());
        return 1;
    }

    private static int func_229638_e_(CommandSource commandSource, String string) {
        Path path = Paths.get(StructureHelper.field_229590_a_, string + ".snbt");
        ResourceLocation resourceLocation = new ResourceLocation("minecraft", string);
        Path path2 = commandSource.getWorld().getStructureTemplateManager().resolvePathStructures(resourceLocation, ".nbt");
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            String string2 = IOUtils.toString(bufferedReader);
            Files.createDirectories(path2.getParent(), new FileAttribute[0]);
            try (OutputStream outputStream = Files.newOutputStream(path2, new OpenOption[0]);){
                CompressedStreamTools.writeCompressed(JsonToNBT.getTagFromJson(string2), outputStream);
            }
            TestCommand.func_229634_c_(commandSource, "Imported to " + path2.toAbsolutePath());
            return 0;
        } catch (CommandSyntaxException | IOException exception) {
            System.err.println("Failed to load structure " + string);
            exception.printStackTrace();
            return 0;
        }
    }

    private static void func_229624_a_(ServerWorld serverWorld, String string, TextFormatting textFormatting) {
        serverWorld.getPlayers(TestCommand::lambda$func_229624_a_$26).forEach(arg_0 -> TestCommand.lambda$func_229624_a_$27(textFormatting, string, arg_0));
    }

    private static void lambda$func_229624_a_$27(TextFormatting textFormatting, String string, ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.sendMessage(new StringTextComponent(textFormatting + string), Util.DUMMY_UUID);
    }

    private static boolean lambda$func_229624_a_$26(ServerPlayerEntity serverPlayerEntity) {
        return false;
    }

    private static void lambda$func_229619_a_$25(TestTracker testTracker) {
        TestRegistry.func_240548_a_(testTracker.func_240546_u_());
    }

    private static void lambda$func_229629_b_$24(ServerWorld serverWorld, TestResultList testResultList, BlockPos blockPos) {
        TestCommand.func_229623_a_(serverWorld, blockPos, testResultList);
    }

    private static int lambda$register$23(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229616_a_((CommandSource)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "radius"));
    }

    private static int lambda$register$22(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229616_a_((CommandSource)commandContext.getSource(), 200);
    }

    private static int lambda$register$21(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229618_a_((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "testName"), IntegerArgumentType.getInteger(commandContext, "width"), IntegerArgumentType.getInteger(commandContext, "height"), IntegerArgumentType.getInteger(commandContext, "depth"));
    }

    private static int lambda$register$20(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229618_a_((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "testName"), IntegerArgumentType.getInteger(commandContext, "width"), IntegerArgumentType.getInteger(commandContext, "width"), IntegerArgumentType.getInteger(commandContext, "width"));
    }

    private static int lambda$register$19(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229618_a_((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "testName"), 5, 5, 5);
    }

    private static int lambda$register$18(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229617_a_((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "var"));
    }

    private static int lambda$register$17(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229617_a_((CommandSource)commandContext.getSource(), "pos");
    }

    private static int lambda$register$16(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229638_e_((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "testName"));
    }

    private static int lambda$register$15(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_240581_c_((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$14(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229636_d_((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "testName"));
    }

    private static int lambda$register$13(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229633_c_((CommandSource)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "rotationSteps"), IntegerArgumentType.getInteger(commandContext, "testsPerRow"));
    }

    private static int lambda$register$12(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229633_c_((CommandSource)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "rotationSteps"), 8);
    }

    private static int lambda$register$11(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229630_b_((CommandSource)commandContext.getSource(), TestTypeArgument.func_229612_a_(commandContext, "testClassName"), IntegerArgumentType.getInteger(commandContext, "rotationSteps"), IntegerArgumentType.getInteger(commandContext, "testsPerRow"));
    }

    private static int lambda$register$10(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229630_b_((CommandSource)commandContext.getSource(), TestTypeArgument.func_229612_a_(commandContext, "testClassName"), IntegerArgumentType.getInteger(commandContext, "rotationSteps"), 8);
    }

    private static int lambda$register$9(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229630_b_((CommandSource)commandContext.getSource(), TestTypeArgument.func_229612_a_(commandContext, "testClassName"), 0, 8);
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229633_c_((CommandSource)commandContext.getSource(), 0, 8);
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229620_a_((CommandSource)commandContext.getSource(), TestArgArgument.func_229666_a_(commandContext, "testName"), IntegerArgumentType.getInteger(commandContext, "rotationSteps"));
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229620_a_((CommandSource)commandContext.getSource(), TestArgArgument.func_229666_a_(commandContext, "testName"), 0);
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_240574_a_((CommandSource)commandContext.getSource(), BoolArgumentType.getBool(commandContext, "onlyRequiredTests"), IntegerArgumentType.getInteger(commandContext, "rotationSteps"), IntegerArgumentType.getInteger(commandContext, "testsPerRow"));
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_240574_a_((CommandSource)commandContext.getSource(), BoolArgumentType.getBool(commandContext, "onlyRequiredTests"), IntegerArgumentType.getInteger(commandContext, "rotationSteps"), 8);
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_240574_a_((CommandSource)commandContext.getSource(), BoolArgumentType.getBool(commandContext, "onlyRequiredTests"), 0, 8);
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_240574_a_((CommandSource)commandContext.getSource(), false, 0, 8);
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229629_b_((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$0(CommandContext commandContext) throws CommandSyntaxException {
        return TestCommand.func_229615_a_((CommandSource)commandContext.getSource());
    }

    static class Callback
    implements ITestCallback {
        private final ServerWorld field_229648_a_;
        private final TestResultList field_229649_b_;

        public Callback(ServerWorld serverWorld, TestResultList testResultList) {
            this.field_229648_a_ = serverWorld;
            this.field_229649_b_ = testResultList;
        }

        @Override
        public void func_225644_a_(TestTracker testTracker) {
        }

        @Override
        public void func_225645_c_(TestTracker testTracker) {
            TestCommand.func_229631_b_(this.field_229648_a_, this.field_229649_b_);
        }
    }
}

