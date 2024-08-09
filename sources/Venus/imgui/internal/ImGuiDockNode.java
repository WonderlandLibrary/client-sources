/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.internal;

import imgui.ImGuiWindowClass;
import imgui.ImVec2;
import imgui.binding.ImGuiStruct;
import imgui.internal.ImRect;

public final class ImGuiDockNode
extends ImGuiStruct {
    private static final ImGuiDockNode PARENT_NODE = new ImGuiDockNode(0L);
    private static final ImGuiDockNode CHILD_NODE_FIRST = new ImGuiDockNode(0L);
    private static final ImGuiDockNode CHILD_NODE_SECOND = new ImGuiDockNode(0L);
    private static final ImGuiWindowClass WINDOW_CLASS = new ImGuiWindowClass(0L);
    private static final ImGuiDockNode CENTRAL_NODE = new ImGuiDockNode(0L);
    private static final ImGuiDockNode ONLY_NODE_WITH_WINDOWS = new ImGuiDockNode(0L);
    private static final ImRect RECT = new ImRect();

    public ImGuiDockNode(long l) {
        super(l);
    }

    public native int getID();

    public native void setID(int var1);

    public native int getSharedFlags();

    public native void setSharedFlags(int var1);

    public void addSharedFlags(int n) {
        this.setSharedFlags(this.getSharedFlags() | n);
    }

    public void removeSharedFlags(int n) {
        this.setSharedFlags(this.getSharedFlags() & ~n);
    }

    public native int getLocalFlags();

    public native void setLocalFlags(int var1);

    public void addLocalFlags(int n) {
        this.setLocalFlags(this.getLocalFlags() | n);
    }

    public void removeLocalFlags(int n) {
        this.setLocalFlags(this.getLocalFlags() & ~n);
    }

    public native int getLocalFlagsInWindows();

    public native void setLocalFlagsInWindows(int var1);

    public void addLocalFlagsInWindows(int n) {
        this.setLocalFlagsInWindows(this.getLocalFlagsInWindows() | n);
    }

    public void removeLocalFlagsInWindows(int n) {
        this.setLocalFlagsInWindows(this.getLocalFlagsInWindows() & ~n);
    }

    public native int getMergedFlags();

    public ImGuiDockNode getParentNode() {
        ImGuiDockNode.PARENT_NODE.ptr = this.nGetParentNode();
        return PARENT_NODE;
    }

    private native long nGetParentNode();

    public void setParentNode(ImGuiDockNode imGuiDockNode) {
        this.nSetParentNode(imGuiDockNode.ptr);
    }

    private native void nSetParentNode(long var1);

    public ImGuiDockNode getChildNodeFirst() {
        ImGuiDockNode.CHILD_NODE_FIRST.ptr = this.nGetChildNodeFirst();
        return CHILD_NODE_FIRST;
    }

    private native long nGetChildNodeFirst();

    public void setChildNodeFirst(ImGuiDockNode imGuiDockNode) {
        this.nSetChildNodeFirst(imGuiDockNode.ptr);
    }

    private native void nSetChildNodeFirst(long var1);

    public ImGuiDockNode getChildNodeSecond() {
        ImGuiDockNode.CHILD_NODE_SECOND.ptr = this.nGetChildNodeSecond();
        return CHILD_NODE_SECOND;
    }

    private native long nGetChildNodeSecond();

    public void setChildNodeSecond(ImGuiDockNode imGuiDockNode) {
        this.nSetChildNodeSecond(imGuiDockNode.ptr);
    }

    private native void nSetChildNodeSecond(long var1);

    public ImGuiWindowClass getWindowClass() {
        ImGuiDockNode.WINDOW_CLASS.ptr = this.nGetWindowClass();
        return WINDOW_CLASS;
    }

    private native long nGetWindowClass();

    public void setWindowClass(ImGuiWindowClass imGuiWindowClass) {
        this.nSetWindowClass(imGuiWindowClass.ptr);
    }

    private native void nSetWindowClass(long var1);

    public ImVec2 getPos() {
        ImVec2 imVec2 = new ImVec2();
        this.getPos(imVec2);
        return imVec2;
    }

    public native void getPos(ImVec2 var1);

    public native float getPosX();

    public native float getPosY();

    public native void setPos(float var1, float var2);

    public ImVec2 getSize() {
        ImVec2 imVec2 = new ImVec2();
        this.getSize(imVec2);
        return imVec2;
    }

    public native void getSize(ImVec2 var1);

    public native float getSizeX();

    public native float getSizeY();

    public native void setSize(float var1, float var2);

    public ImVec2 getSizeRef() {
        ImVec2 imVec2 = new ImVec2();
        this.getSizeRef(imVec2);
        return imVec2;
    }

    public native void getSizeRef(ImVec2 var1);

    public native float getSizeRefX();

    public native float getSizeRefY();

    public native void setSizeRef(float var1, float var2);

    public native int getSplitAxis();

    public native void setSplitAxis(int var1);

    public native int getState();

    public native void setState(int var1);

    public ImGuiDockNode getCentralNode() {
        ImGuiDockNode.CENTRAL_NODE.ptr = this.nGetCentralNode();
        return PARENT_NODE;
    }

    private native long nGetCentralNode();

    public void setCentralNode(ImGuiDockNode imGuiDockNode) {
        this.nSetCentralNode(imGuiDockNode.ptr);
    }

    private native void nSetCentralNode(long var1);

    public ImGuiDockNode getOnlyNodeWithWindows() {
        ImGuiDockNode.ONLY_NODE_WITH_WINDOWS.ptr = this.nGetOnlyNodeWithWindows();
        return PARENT_NODE;
    }

    private native long nGetOnlyNodeWithWindows();

    public void setOnlyNodeWithWindows(ImGuiDockNode imGuiDockNode) {
        this.nSetOnlyNodeWithWindows(imGuiDockNode.ptr);
    }

    private native void nSetOnlyNodeWithWindows(long var1);

    public native int getLastFrameAlive();

    public native void setLastFrameAlive(int var1);

    public native int getLastFrameActive();

    public native void setLastFrameActive(int var1);

    public native int getLastFrameFocused();

    public native void setLastFrameFocused(int var1);

    public native int getLastFocusedNodeId();

    public native void setLastFocusedNodeId(int var1);

    public native int getSelectedTabId();

    public native void setSelectedTabId(int var1);

    public native int getWantCloseTabId();

    public native void setWantCloseTabId(int var1);

    public native int getAuthorityForPos();

    public native void setAuthorityForPos(int var1);

    public native int getAuthorityForSize();

    public native void setAuthorityForSize(int var1);

    public native int getAuthorityForViewport();

    public native void setAuthorityForViewport(int var1);

    public native boolean getIsVisible();

    public native void setIsVisible(boolean var1);

    public native boolean getIsFocused();

    public native void setIsFocused(boolean var1);

    public native boolean getHasCloseButton();

    public native void setHasCloseButton(boolean var1);

    public native boolean getHasWindowMenuButton();

    public native void setHasWindowMenuButton(boolean var1);

    public native boolean getWantCloseAll();

    public native void setWantCloseAll(boolean var1);

    public native boolean getWantLockSizeOnce();

    public native void setWantLockSizeOnce(boolean var1);

    public native boolean getWantMouseMove();

    public native void setWantMouseMove(boolean var1);

    public native boolean getWantHiddenTabBarUpdate();

    public native void setWantHiddenTabBarUpdate(boolean var1);

    public native boolean getWantHiddenTabBarToggle();

    public native void setWantHiddenTabBarToggle(boolean var1);

    public native boolean isRootNode();

    public native boolean isDockSpace();

    public native boolean isFloatingNode();

    public native boolean isCentralNode();

    public native boolean isHiddenTabBar();

    public native boolean isNoTabBar();

    public native boolean isSplitNode();

    public native boolean isLeafNode();

    public native boolean isEmpty();

    public ImRect rect() {
        this.nRect(ImGuiDockNode.RECT.min, ImGuiDockNode.RECT.max);
        return RECT;
    }

    private native void nRect(ImVec2 var1, ImVec2 var2);
}

