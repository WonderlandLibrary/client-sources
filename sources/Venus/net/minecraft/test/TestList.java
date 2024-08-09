/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.test;

import java.util.Iterator;
import java.util.List;
import net.minecraft.test.TestRuntimeException;
import net.minecraft.test.TestTickResult;
import net.minecraft.test.TestTracker;

public class TestList {
    private final TestTracker field_229564_a_ = null;
    private final List<TestTickResult> field_229565_b_ = null;
    private long field_229566_c_;

    public void func_229567_a_(long l) {
        try {
            this.func_229569_c_(l);
        } catch (Exception exception) {
            // empty catch block
        }
    }

    public void func_229568_b_(long l) {
        try {
            this.func_229569_c_(l);
        } catch (Exception exception) {
            this.field_229564_a_.func_229506_a_(exception);
        }
    }

    private void func_229569_c_(long l) {
        Iterator<TestTickResult> iterator2 = this.field_229565_b_.iterator();
        while (iterator2.hasNext()) {
            TestTickResult testTickResult = iterator2.next();
            testTickResult.field_229486_b_.run();
            iterator2.remove();
            long l2 = l - this.field_229566_c_;
            long l3 = this.field_229566_c_;
            this.field_229566_c_ = l;
            if (testTickResult.field_229485_a_ == null || testTickResult.field_229485_a_ == l2) continue;
            this.field_229564_a_.func_229506_a_(new TestRuntimeException("Succeeded in invalid tick: expected " + (l3 + testTickResult.field_229485_a_) + ", but current tick is " + l));
            break;
        }
    }

    private TestList() {
        throw new RuntimeException("Synthetic constructor added by MCP, do not call");
    }
}

