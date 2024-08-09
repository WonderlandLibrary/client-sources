/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.test;

import net.minecraft.test.ITestLogger;
import net.minecraft.test.TestTracker;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLogger
implements ITestLogger {
    private static final Logger field_229575_a_ = LogManager.getLogger();

    @Override
    public void func_225646_a_(TestTracker testTracker) {
        if (testTracker.func_229520_q_()) {
            field_229575_a_.error(testTracker.func_229510_c_() + " failed! " + Util.getMessage(testTracker.func_229519_n_()));
        } else {
            field_229575_a_.warn("(optional) " + testTracker.func_229510_c_() + " failed. " + Util.getMessage(testTracker.func_229519_n_()));
        }
    }
}

