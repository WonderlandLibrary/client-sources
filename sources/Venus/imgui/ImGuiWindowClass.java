/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.binding.ImGuiStructDestroyable;

public final class ImGuiWindowClass
extends ImGuiStructDestroyable {
    public ImGuiWindowClass() {
    }

    public ImGuiWindowClass(long l) {
        super(l);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public native int geClassId();

    public native void setClassId(int var1);

    public native int getParentViewportId();

    public native void setParentViewportId(int var1);

    public native int getViewportFlagsOverrideSet();

    public native void setViewportFlagsOverrideSet(int var1);

    public native int getViewportFlagsOverrideClear();

    public native void setViewportFlagsOverrideClear(int var1);

    public native int getTabItemFlagsOverrideSet();

    public native void setTabItemFlagsOverrideSet(int var1);

    public native int getDockNodeFlagsOverrideSet();

    public native void setDockNodeFlagsOverrideSet(int var1);

    public void addDockNodeFlagsOverrideSet(int n) {
        this.setDockNodeFlagsOverrideSet(this.getDockNodeFlagsOverrideSet() | n);
    }

    public void removeDockNodeFlagsOverrideSet(int n) {
        this.setDockNodeFlagsOverrideSet(this.getDockNodeFlagsOverrideSet() & ~n);
    }

    public boolean hasDockNodeFlagsOverrideSet(int n) {
        return (this.getDockNodeFlagsOverrideSet() & n) != 0;
    }

    public native boolean getDockingAlwaysTabBar();

    public native void setDockingAlwaysTabBar(boolean var1);

    public native boolean getDockingAllowUnclassed();

    public native void setDockingAllowUnclassed(boolean var1);
}

