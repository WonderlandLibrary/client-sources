/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;

@GwtIncompatible
abstract class LineBuffer {
    private StringBuilder line = new StringBuilder();
    private boolean sawReturn;

    LineBuffer() {
    }

    protected void add(char[] cArray, int n, int n2) throws IOException {
        int n3 = n;
        if (this.sawReturn && n2 > 0 && this.finishLine(cArray[n3] == '\n')) {
            ++n3;
        }
        int n4 = n3;
        int n5 = n + n2;
        while (n3 < n5) {
            switch (cArray[n3]) {
                case '\r': {
                    this.line.append(cArray, n4, n3 - n4);
                    this.sawReturn = true;
                    if (n3 + 1 < n5 && this.finishLine(cArray[n3 + 1] == '\n')) {
                        ++n3;
                    }
                    n4 = n3 + 1;
                    break;
                }
                case '\n': {
                    this.line.append(cArray, n4, n3 - n4);
                    this.finishLine(true);
                    n4 = n3 + 1;
                    break;
                }
            }
            ++n3;
        }
        this.line.append(cArray, n4, n + n2 - n4);
    }

    @CanIgnoreReturnValue
    private boolean finishLine(boolean bl) throws IOException {
        String string = this.sawReturn ? (bl ? "\r\n" : "\r") : (bl ? "\n" : "");
        this.handleLine(this.line.toString(), string);
        this.line = new StringBuilder();
        this.sawReturn = false;
        return bl;
    }

    protected void finish() throws IOException {
        if (this.sawReturn || this.line.length() > 0) {
            this.finishLine(false);
        }
    }

    protected abstract void handleLine(String var1, String var2) throws IOException;
}

