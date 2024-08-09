/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.assertion;

public abstract class ImAssertCallback {
    public void imAssert(String string, int n, String string2) {
        try {
            this.imAssertCallback(string, n, string2);
        } catch (Exception exception) {
            System.err.println("WARNING: Exception thrown in Dear ImGui Assertion Callback!");
            System.err.println("Dear ImGui Assertion Failed: " + string);
            System.err.println("Assertion Located At: " + string2 + ":" + n);
            exception.printStackTrace();
        }
        System.exit(1);
    }

    public abstract void imAssertCallback(String var1, int var2, String var3);
}

