/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.test.ITestCallback;
import net.minecraft.test.StructureHelper;
import net.minecraft.test.TestBatch;
import net.minecraft.test.TestCollection;
import net.minecraft.test.TestFunctionInfo;
import net.minecraft.test.TestResultList;
import net.minecraft.test.TestTracker;
import net.minecraft.test.TestUtils;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestExecutor {
    private static final Logger field_229466_a_ = LogManager.getLogger();
    private final BlockPos field_229467_b_;
    private final ServerWorld field_229468_c_;
    private final TestCollection field_229469_d_;
    private final int field_240536_e_;
    private final List<TestTracker> field_229470_e_ = Lists.newArrayList();
    private final Map<TestTracker, BlockPos> field_240537_g_ = Maps.newHashMap();
    private final List<Pair<TestBatch, Collection<TestTracker>>> field_229471_f_ = Lists.newArrayList();
    private TestResultList field_229472_g_;
    private int field_229473_h_ = 0;
    private BlockPos.Mutable field_229474_i_;

    public TestExecutor(Collection<TestBatch> collection, BlockPos blockPos, Rotation rotation, ServerWorld serverWorld, TestCollection testCollection, int n) {
        this.field_229474_i_ = blockPos.toMutable();
        this.field_229467_b_ = blockPos;
        this.field_229468_c_ = serverWorld;
        this.field_229469_d_ = testCollection;
        this.field_240536_e_ = n;
        collection.forEach(arg_0 -> this.lambda$new$0(rotation, serverWorld, arg_0));
    }

    public List<TestTracker> func_229476_a_() {
        return this.field_229470_e_;
    }

    public void func_229482_b_() {
        this.func_229477_a_(0);
    }

    private void func_229477_a_(int n) {
        this.field_229473_h_ = n;
        this.field_229472_g_ = new TestResultList();
        if (n < this.field_229471_f_.size()) {
            Pair<TestBatch, Collection<TestTracker>> pair = this.field_229471_f_.get(this.field_229473_h_);
            TestBatch testBatch = pair.getFirst();
            Collection<TestTracker> collection = pair.getSecond();
            this.func_229480_a_(collection);
            testBatch.func_229464_a_(this.field_229468_c_);
            String string = testBatch.func_229463_a_();
            field_229466_a_.info("Running test batch '" + string + "' (" + collection.size() + " tests)...");
            collection.forEach(this::lambda$func_229477_a_$1);
        }
    }

    private void func_229479_a_(TestTracker testTracker) {
        if (this.field_229472_g_.func_229588_i_()) {
            this.func_229477_a_(this.field_229473_h_ + 1);
        }
    }

    private void func_229480_a_(Collection<TestTracker> collection) {
        int n = 0;
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(this.field_229474_i_);
        for (TestTracker testTracker : collection) {
            BlockPos blockPos = new BlockPos(this.field_229474_i_);
            StructureBlockTileEntity structureBlockTileEntity = StructureHelper.func_240565_a_(testTracker.func_229522_s_(), blockPos, testTracker.func_240545_t_(), 2, this.field_229468_c_, true);
            AxisAlignedBB axisAlignedBB2 = StructureHelper.func_229594_a_(structureBlockTileEntity);
            testTracker.func_229503_a_(structureBlockTileEntity.getPos());
            this.field_240537_g_.put(testTracker, new BlockPos(this.field_229474_i_));
            axisAlignedBB = axisAlignedBB.union(axisAlignedBB2);
            this.field_229474_i_.move((int)axisAlignedBB2.getXSize() + 5, 0, 0);
            if (n++ % this.field_240536_e_ != this.field_240536_e_ - 1) continue;
            this.field_229474_i_.move(0, 0, (int)axisAlignedBB.getZSize() + 6);
            this.field_229474_i_.setX(this.field_229467_b_.getX());
            axisAlignedBB = new AxisAlignedBB(this.field_229474_i_);
        }
    }

    private void lambda$func_229477_a_$1(TestTracker testTracker) {
        this.field_229472_g_.func_229579_a_(testTracker);
        this.field_229472_g_.func_240558_a_(new ITestCallback(this){
            final TestExecutor this$0;
            {
                this.this$0 = testExecutor;
            }

            @Override
            public void func_225644_a_(TestTracker testTracker) {
            }

            @Override
            public void func_225645_c_(TestTracker testTracker) {
                this.this$0.func_229479_a_(testTracker);
            }
        });
        BlockPos blockPos = this.field_240537_g_.get(testTracker);
        TestUtils.func_240553_a_(testTracker, blockPos, this.field_229469_d_);
    }

    private void lambda$new$0(Rotation rotation, ServerWorld serverWorld, TestBatch testBatch) {
        ArrayList<TestTracker> arrayList = Lists.newArrayList();
        for (TestFunctionInfo testFunctionInfo : testBatch.func_229465_b_()) {
            TestTracker testTracker = new TestTracker(testFunctionInfo, rotation, serverWorld);
            arrayList.add(testTracker);
            this.field_229470_e_.add(testTracker);
        }
        this.field_229471_f_.add(Pair.of(testBatch, arrayList));
    }
}

