/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.test.ITestCallback;
import net.minecraft.test.ITestLogger;
import net.minecraft.test.StructureHelper;
import net.minecraft.test.TestBatch;
import net.minecraft.test.TestBlockPosException;
import net.minecraft.test.TestCollection;
import net.minecraft.test.TestExecutor;
import net.minecraft.test.TestFunctionInfo;
import net.minecraft.test.TestLogger;
import net.minecraft.test.TestRegistry;
import net.minecraft.test.TestTracker;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.mutable.MutableInt;

public class TestUtils {
    public static ITestLogger field_229539_a_ = new TestLogger();

    public static void func_240553_a_(TestTracker testTracker, BlockPos blockPos, TestCollection testCollection) {
        testTracker.func_229501_a_();
        testCollection.func_229573_a_(testTracker);
        testTracker.func_229504_a_(new ITestCallback(){

            @Override
            public void func_225644_a_(TestTracker testTracker) {
                TestUtils.func_229559_b_(testTracker, Blocks.LIGHT_GRAY_STAINED_GLASS);
            }

            @Override
            public void func_225645_c_(TestTracker testTracker) {
                TestUtils.func_229559_b_(testTracker, testTracker.func_229520_q_() ? Blocks.RED_STAINED_GLASS : Blocks.ORANGE_STAINED_GLASS);
                TestUtils.func_229560_b_(testTracker, Util.getMessage(testTracker.func_229519_n_()));
                TestUtils.func_229563_c_(testTracker);
            }
        });
        testTracker.func_240543_a_(blockPos, 2);
    }

    public static Collection<TestTracker> func_240552_a_(Collection<TestBatch> collection, BlockPos blockPos, Rotation rotation, ServerWorld serverWorld, TestCollection testCollection, int n) {
        TestExecutor testExecutor = new TestExecutor(collection, blockPos, rotation, serverWorld, testCollection, n);
        testExecutor.func_229482_b_();
        return testExecutor.func_229476_a_();
    }

    public static Collection<TestTracker> func_240554_b_(Collection<TestFunctionInfo> collection, BlockPos blockPos, Rotation rotation, ServerWorld serverWorld, TestCollection testCollection, int n) {
        return TestUtils.func_240552_a_(TestUtils.func_229548_a_(collection), blockPos, rotation, serverWorld, testCollection, n);
    }

    public static Collection<TestBatch> func_229548_a_(Collection<TestFunctionInfo> collection) {
        HashMap hashMap = Maps.newHashMap();
        collection.forEach(arg_0 -> TestUtils.lambda$func_229548_a_$1(hashMap, arg_0));
        return hashMap.keySet().stream().flatMap(arg_0 -> TestUtils.lambda$func_229548_a_$3(hashMap, arg_0)).collect(Collectors.toList());
    }

    private static void func_229563_c_(TestTracker testTracker) {
        Throwable throwable = testTracker.func_229519_n_();
        String string = (testTracker.func_229520_q_() ? "" : "(optional) ") + testTracker.func_229510_c_() + " failed! " + Util.getMessage(throwable);
        TestUtils.func_229556_a_(testTracker.func_229514_g_(), testTracker.func_229520_q_() ? TextFormatting.RED : TextFormatting.YELLOW, string);
        if (throwable instanceof TestBlockPosException) {
            TestBlockPosException testBlockPosException = (TestBlockPosException)throwable;
            TestUtils.func_229554_a_(testTracker.func_229514_g_(), testBlockPosException.func_229459_c_(), testBlockPosException.func_229458_a_());
        }
        field_229539_a_.func_225646_a_(testTracker);
    }

    private static void func_229559_b_(TestTracker testTracker, Block block) {
        ServerWorld serverWorld = testTracker.func_229514_g_();
        BlockPos blockPos = testTracker.func_229512_d_();
        BlockPos blockPos2 = new BlockPos(-1, -1, -1);
        BlockPos blockPos3 = Template.getTransformedPos(blockPos.add(blockPos2), Mirror.NONE, testTracker.func_240545_t_(), blockPos);
        serverWorld.setBlockState(blockPos3, Blocks.BEACON.getDefaultState().rotate(testTracker.func_240545_t_()));
        BlockPos blockPos4 = blockPos3.add(0, 1, 0);
        serverWorld.setBlockState(blockPos4, block.getDefaultState());
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                BlockPos blockPos5 = blockPos3.add(i, -1, j);
                serverWorld.setBlockState(blockPos5, Blocks.IRON_BLOCK.getDefaultState());
            }
        }
    }

    private static void func_229560_b_(TestTracker testTracker, String string) {
        ServerWorld serverWorld = testTracker.func_229514_g_();
        BlockPos blockPos = testTracker.func_229512_d_();
        BlockPos blockPos2 = new BlockPos(-1, 1, -1);
        BlockPos blockPos3 = Template.getTransformedPos(blockPos.add(blockPos2), Mirror.NONE, testTracker.func_240545_t_(), blockPos);
        serverWorld.setBlockState(blockPos3, Blocks.LECTERN.getDefaultState().rotate(testTracker.func_240545_t_()));
        BlockState blockState = serverWorld.getBlockState(blockPos3);
        ItemStack itemStack = TestUtils.func_229546_a_(testTracker.func_229510_c_(), testTracker.func_229520_q_(), string);
        LecternBlock.tryPlaceBook(serverWorld, blockPos3, blockState, itemStack);
    }

    private static ItemStack func_229546_a_(String string, boolean bl, String string2) {
        ItemStack itemStack = new ItemStack(Items.WRITABLE_BOOK);
        ListNBT listNBT = new ListNBT();
        StringBuffer stringBuffer = new StringBuffer();
        Arrays.stream(string.split("\\.")).forEach(arg_0 -> TestUtils.lambda$func_229546_a_$4(stringBuffer, arg_0));
        if (!bl) {
            stringBuffer.append("(optional)\n");
        }
        stringBuffer.append("-------------------\n");
        listNBT.add(StringNBT.valueOf(stringBuffer.toString() + string2));
        itemStack.setTagInfo("pages", listNBT);
        return itemStack;
    }

    private static void func_229556_a_(ServerWorld serverWorld, TextFormatting textFormatting, String string) {
        serverWorld.getPlayers(TestUtils::lambda$func_229556_a_$5).forEach(arg_0 -> TestUtils.lambda$func_229556_a_$6(string, textFormatting, arg_0));
    }

    public static void func_229552_a_(ServerWorld serverWorld) {
        DebugPacketSender.func_229751_a_(serverWorld);
    }

    private static void func_229554_a_(ServerWorld serverWorld, BlockPos blockPos, String string) {
        DebugPacketSender.func_229752_a_(serverWorld, blockPos, string, -2130771968, Integer.MAX_VALUE);
    }

    public static void func_229555_a_(ServerWorld serverWorld, BlockPos blockPos, TestCollection testCollection, int n) {
        testCollection.func_229572_a_();
        BlockPos blockPos2 = blockPos.add(-n, 0, -n);
        BlockPos blockPos3 = blockPos.add(n, 0, n);
        BlockPos.getAllInBox(blockPos2, blockPos3).filter(arg_0 -> TestUtils.lambda$func_229555_a_$7(serverWorld, arg_0)).forEach(arg_0 -> TestUtils.lambda$func_229555_a_$8(serverWorld, arg_0));
    }

    private static void lambda$func_229555_a_$8(ServerWorld serverWorld, BlockPos blockPos) {
        StructureBlockTileEntity structureBlockTileEntity = (StructureBlockTileEntity)serverWorld.getTileEntity(blockPos);
        BlockPos blockPos2 = structureBlockTileEntity.getPos();
        MutableBoundingBox mutableBoundingBox = StructureHelper.func_240568_b_(structureBlockTileEntity);
        StructureHelper.func_229595_a_(mutableBoundingBox, blockPos2.getY(), serverWorld);
    }

    private static boolean lambda$func_229555_a_$7(ServerWorld serverWorld, BlockPos blockPos) {
        return serverWorld.getBlockState(blockPos).isIn(Blocks.STRUCTURE_BLOCK);
    }

    private static void lambda$func_229556_a_$6(String string, TextFormatting textFormatting, ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.sendMessage(new StringTextComponent(string).mergeStyle(textFormatting), Util.DUMMY_UUID);
    }

    private static boolean lambda$func_229556_a_$5(ServerPlayerEntity serverPlayerEntity) {
        return false;
    }

    private static void lambda$func_229546_a_$4(StringBuffer stringBuffer, String string) {
        stringBuffer.append(string).append('\n');
    }

    private static Stream lambda$func_229548_a_$3(Map map, String string) {
        Collection collection = (Collection)map.get(string);
        Consumer<ServerWorld> consumer = TestRegistry.func_229536_c_(string);
        MutableInt mutableInt = new MutableInt();
        return Streams.stream(Iterables.partition(collection, 100)).map(arg_0 -> TestUtils.lambda$func_229548_a_$2(string, mutableInt, collection, consumer, arg_0));
    }

    private static TestBatch lambda$func_229548_a_$2(String string, MutableInt mutableInt, Collection collection, Consumer consumer, List list) {
        return new TestBatch(string + ":" + mutableInt.incrementAndGet(), collection, consumer);
    }

    private static void lambda$func_229548_a_$1(Map map, TestFunctionInfo testFunctionInfo) {
        String string = testFunctionInfo.func_229662_e_();
        Collection collection = map.computeIfAbsent(string, TestUtils::lambda$func_229548_a_$0);
        collection.add(testFunctionInfo);
    }

    private static Collection lambda$func_229548_a_$0(String string) {
        return Lists.newArrayList();
    }
}

