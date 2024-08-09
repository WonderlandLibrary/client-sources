/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public enum RemovalCause {
    EXPLICIT{

        @Override
        boolean wasEvicted() {
            return true;
        }
    }
    ,
    REPLACED{

        @Override
        boolean wasEvicted() {
            return true;
        }
    }
    ,
    COLLECTED{

        @Override
        boolean wasEvicted() {
            return false;
        }
    }
    ,
    EXPIRED{

        @Override
        boolean wasEvicted() {
            return false;
        }
    }
    ,
    SIZE{

        @Override
        boolean wasEvicted() {
            return false;
        }
    };


    private RemovalCause() {
    }

    abstract boolean wasEvicted();

    RemovalCause(1 var3_3) {
        this();
    }
}

