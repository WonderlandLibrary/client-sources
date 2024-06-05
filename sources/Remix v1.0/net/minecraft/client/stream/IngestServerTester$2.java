package net.minecraft.client.stream;

import tv.twitch.broadcast.*;

class IngestServerTester$2 implements IStatCallbacks {
    public void statCallback(final StatType p_statCallback_1_, final long p_statCallback_2_) {
        switch (SwitchStatType.field_176003_a[p_statCallback_1_.ordinal()]) {
            case 1: {
                IngestServerTester.this.field_153051_i = RTMPState.lookupValue((int)p_statCallback_2_);
                break;
            }
            case 2: {
                IngestServerTester.this.field_153050_h = p_statCallback_2_;
                break;
            }
        }
    }
}