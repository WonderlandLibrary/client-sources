/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MqttSubAckPayload {
    private final List<Integer> grantedQoSLevels;

    public MqttSubAckPayload(int ... nArray) {
        if (nArray == null) {
            throw new NullPointerException("grantedQoSLevels");
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>(nArray.length);
        for (int n : nArray) {
            arrayList.add(n);
        }
        this.grantedQoSLevels = Collections.unmodifiableList(arrayList);
    }

    public MqttSubAckPayload(Iterable<Integer> iterable) {
        if (iterable == null) {
            throw new NullPointerException("grantedQoSLevels");
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (Integer n : iterable) {
            if (n == null) break;
            arrayList.add(n);
        }
        this.grantedQoSLevels = Collections.unmodifiableList(arrayList);
    }

    public List<Integer> grantedQoSLevels() {
        return this.grantedQoSLevels;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "grantedQoSLevels=" + this.grantedQoSLevels + ']';
    }
}

