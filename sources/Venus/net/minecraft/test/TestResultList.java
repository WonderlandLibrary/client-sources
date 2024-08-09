/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.test;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.test.ITestCallback;
import net.minecraft.test.TestTracker;

public class TestResultList {
    private final Collection<TestTracker> field_229576_a_ = Lists.newArrayList();
    @Nullable
    private Collection<ITestCallback> field_240555_b_ = Lists.newArrayList();

    public TestResultList() {
    }

    public TestResultList(Collection<TestTracker> collection) {
        this.field_229576_a_.addAll(collection);
    }

    public void func_229579_a_(TestTracker testTracker) {
        this.field_229576_a_.add(testTracker);
        this.field_240555_b_.forEach(testTracker::func_229504_a_);
    }

    public void func_240558_a_(ITestCallback iTestCallback) {
        this.field_240555_b_.add(iTestCallback);
        this.field_229576_a_.forEach(arg_0 -> TestResultList.lambda$func_240558_a_$0(iTestCallback, arg_0));
    }

    public void func_240556_a_(Consumer<TestTracker> consumer) {
        this.func_240558_a_(new ITestCallback(){
            final Consumer val$p_240556_1_;
            final TestResultList this$0;
            {
                this.this$0 = testResultList;
                this.val$p_240556_1_ = consumer;
            }

            @Override
            public void func_225644_a_(TestTracker testTracker) {
            }

            @Override
            public void func_225645_c_(TestTracker testTracker) {
                this.val$p_240556_1_.accept(testTracker);
            }
        });
    }

    public int func_229578_a_() {
        return (int)this.field_229576_a_.stream().filter(TestTracker::func_229516_i_).filter(TestTracker::func_229520_q_).count();
    }

    public int func_229583_b_() {
        return (int)this.field_229576_a_.stream().filter(TestTracker::func_229516_i_).filter(TestTracker::func_229521_r_).count();
    }

    public int func_229584_c_() {
        return (int)this.field_229576_a_.stream().filter(TestTracker::func_229518_k_).count();
    }

    public boolean func_229585_d_() {
        return this.func_229578_a_() > 0;
    }

    public boolean func_229586_e_() {
        return this.func_229583_b_() > 0;
    }

    public int func_229587_h_() {
        return this.field_229576_a_.size();
    }

    public boolean func_229588_i_() {
        return this.func_229584_c_() == this.func_229587_h_();
    }

    public String func_229589_j_() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('[');
        this.field_229576_a_.forEach(arg_0 -> TestResultList.lambda$func_229589_j_$1(stringBuffer, arg_0));
        stringBuffer.append(']');
        return stringBuffer.toString();
    }

    public String toString() {
        return this.func_229589_j_();
    }

    private static void lambda$func_229589_j_$1(StringBuffer stringBuffer, TestTracker testTracker) {
        if (!testTracker.func_229517_j_()) {
            stringBuffer.append(' ');
        } else if (testTracker.func_229515_h_()) {
            stringBuffer.append('+');
        } else if (testTracker.func_229516_i_()) {
            stringBuffer.append(testTracker.func_229520_q_() ? (char)'X' : 'x');
        } else {
            stringBuffer.append('_');
        }
    }

    private static void lambda$func_240558_a_$0(ITestCallback iTestCallback, TestTracker testTracker) {
        testTracker.func_229504_a_(iTestCallback);
    }
}

