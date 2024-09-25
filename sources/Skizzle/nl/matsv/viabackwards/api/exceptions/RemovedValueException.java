/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.api.exceptions;

import java.io.IOException;

public class RemovedValueException
extends IOException {
    public static final RemovedValueException EX = new RemovedValueException(){

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    };
}

