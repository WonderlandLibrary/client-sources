/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.test;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.test.ITestCallback;
import net.minecraft.test.StructureHelper;
import net.minecraft.test.TestFunctionInfo;
import net.minecraft.test.TestList;
import net.minecraft.test.TestTimeoutException;
import net.minecraft.test.TestTrackerHolder;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class TestTracker {
    private final TestFunctionInfo field_229488_a_;
    @Nullable
    private BlockPos field_229489_b_;
    private final ServerWorld field_229490_c_;
    private final Collection<ITestCallback> field_229491_d_ = Lists.newArrayList();
    private final int field_229492_e_;
    private final Collection<TestList> field_229493_f_ = Lists.newCopyOnWriteArrayList();
    private Object2LongMap<Runnable> field_229494_g_ = new Object2LongOpenHashMap<Runnable>();
    private long field_229495_h_;
    private long field_229496_i_;
    private boolean field_229497_j_ = false;
    private final Stopwatch field_229498_k_ = Stopwatch.createUnstarted();
    private boolean field_229499_l_ = false;
    private final Rotation field_240541_m_;
    @Nullable
    private Throwable field_229500_m_;

    public TestTracker(TestFunctionInfo testFunctionInfo, Rotation rotation, ServerWorld serverWorld) {
        this.field_229488_a_ = testFunctionInfo;
        this.field_229490_c_ = serverWorld;
        this.field_229492_e_ = testFunctionInfo.func_229660_c_();
        this.field_240541_m_ = testFunctionInfo.func_240590_g_().add(rotation);
    }

    void func_229503_a_(BlockPos blockPos) {
        this.field_229489_b_ = blockPos;
    }

    void func_229501_a_() {
        this.field_229495_h_ = this.field_229490_c_.getGameTime() + 1L + this.field_229488_a_.func_229663_f_();
        this.field_229498_k_.start();
    }

    public void func_229507_b_() {
        if (!this.func_229518_k_()) {
            this.field_229496_i_ = this.field_229490_c_.getGameTime() - this.field_229495_h_;
            if (this.field_229496_i_ >= 0L) {
                if (this.field_229496_i_ == 0L) {
                    this.func_229523_t_();
                }
                Iterator iterator2 = this.field_229494_g_.object2LongEntrySet().iterator();
                while (iterator2.hasNext()) {
                    Object2LongMap.Entry entry = (Object2LongMap.Entry)iterator2.next();
                    if (entry.getLongValue() > this.field_229496_i_) continue;
                    try {
                        ((Runnable)entry.getKey()).run();
                    } catch (Exception exception) {
                        this.func_229506_a_(exception);
                    }
                    iterator2.remove();
                }
                if (this.field_229496_i_ > (long)this.field_229492_e_) {
                    if (this.field_229493_f_.isEmpty()) {
                        this.func_229506_a_(new TestTimeoutException("Didn't succeed or fail within " + this.field_229488_a_.func_229660_c_() + " ticks"));
                    } else {
                        this.field_229493_f_.forEach(this::lambda$func_229507_b_$0);
                        if (this.field_229500_m_ == null) {
                            this.func_229506_a_(new TestTimeoutException("No sequences finished"));
                        }
                    }
                } else {
                    this.field_229493_f_.forEach(this::lambda$func_229507_b_$1);
                }
            }
        }
    }

    private void func_229523_t_() {
        if (this.field_229497_j_) {
            throw new IllegalStateException("Test already started");
        }
        this.field_229497_j_ = true;
        try {
            this.field_229488_a_.func_229658_a_(new TestTrackerHolder(this));
        } catch (Exception exception) {
            this.func_229506_a_(exception);
        }
    }

    public String func_229510_c_() {
        return this.field_229488_a_.func_229657_a_();
    }

    public BlockPos func_229512_d_() {
        return this.field_229489_b_;
    }

    public ServerWorld func_229514_g_() {
        return this.field_229490_c_;
    }

    public boolean func_229515_h_() {
        return this.field_229499_l_ && this.field_229500_m_ == null;
    }

    public boolean func_229516_i_() {
        return this.field_229500_m_ != null;
    }

    public boolean func_229517_j_() {
        return this.field_229497_j_;
    }

    public boolean func_229518_k_() {
        return this.field_229499_l_;
    }

    private void func_229525_v_() {
        if (!this.field_229499_l_) {
            this.field_229499_l_ = true;
            this.field_229498_k_.stop();
        }
    }

    public void func_229506_a_(Throwable throwable) {
        this.func_229525_v_();
        this.field_229500_m_ = throwable;
        this.field_229491_d_.forEach(this::lambda$func_229506_a_$2);
    }

    @Nullable
    public Throwable func_229519_n_() {
        return this.field_229500_m_;
    }

    public String toString() {
        return this.func_229510_c_();
    }

    public void func_229504_a_(ITestCallback iTestCallback) {
        this.field_229491_d_.add(iTestCallback);
    }

    public void func_240543_a_(BlockPos blockPos, int n) {
        StructureBlockTileEntity structureBlockTileEntity = StructureHelper.func_240565_a_(this.func_229522_s_(), blockPos, this.func_240545_t_(), n, this.field_229490_c_, false);
        this.func_229503_a_(structureBlockTileEntity.getPos());
        structureBlockTileEntity.setName(this.func_229510_c_());
        StructureHelper.func_240564_a_(this.field_229489_b_, new BlockPos(1, 0, -1), this.func_240545_t_(), this.field_229490_c_);
        this.field_229491_d_.forEach(this::lambda$func_240543_a_$3);
    }

    public boolean func_229520_q_() {
        return this.field_229488_a_.func_229661_d_();
    }

    public boolean func_229521_r_() {
        return !this.field_229488_a_.func_229661_d_();
    }

    public String func_229522_s_() {
        return this.field_229488_a_.func_229659_b_();
    }

    public Rotation func_240545_t_() {
        return this.field_240541_m_;
    }

    public TestFunctionInfo func_240546_u_() {
        return this.field_229488_a_;
    }

    private void lambda$func_240543_a_$3(ITestCallback iTestCallback) {
        iTestCallback.func_225644_a_(this);
    }

    private void lambda$func_229506_a_$2(ITestCallback iTestCallback) {
        iTestCallback.func_225645_c_(this);
    }

    private void lambda$func_229507_b_$1(TestList testList) {
        testList.func_229567_a_(this.field_229496_i_);
    }

    private void lambda$func_229507_b_$0(TestList testList) {
        testList.func_229568_b_(this.field_229496_i_);
    }
}

