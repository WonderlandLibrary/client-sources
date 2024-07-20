/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.text2speech;

import com.mojang.text2speech.Narrator;

public class NarratorDummy
implements Narrator {
    @Override
    public void say(String msg) {
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean active() {
        return false;
    }
}

