/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.extension.nodeditor;

import imgui.ImDrawList;
import imgui.ImVec2;
import imgui.extension.nodeditor.NodeEditorConfig;
import imgui.extension.nodeditor.NodeEditorContext;
import imgui.extension.nodeditor.NodeEditorStyle;
import imgui.type.ImLong;

public final class NodeEditor {
    private static final ImDrawList HINT_FOREGROUND_DRAW_LIST = new ImDrawList(0L);
    private static final ImDrawList HINT_BACKGROUND_DRAW_LIST = new ImDrawList(0L);
    private static final ImDrawList NODE_BACKGROUND_DRAW_LIST = new ImDrawList(0L);
    private static final NodeEditorStyle STYLE = new NodeEditorStyle(0L);

    private NodeEditor() {
    }

    public static NodeEditorContext createEditor() {
        return new NodeEditorContext();
    }

    public static NodeEditorContext createEditor(NodeEditorConfig nodeEditorConfig) {
        return new NodeEditorContext(nodeEditorConfig);
    }

    public static void destroyEditor(NodeEditorContext nodeEditorContext) {
        nodeEditorContext.destroy();
    }

    public static void setCurrentEditor(NodeEditorContext nodeEditorContext) {
        NodeEditor.nSetCurrentEditor(nodeEditorContext.ptr);
    }

    private static native void nSetCurrentEditor(long var0);

    public static native void begin(String var0);

    public static native void beginNode(long var0);

    public static native void group(float var0, float var1);

    public static native boolean beginGroupHint(long var0);

    public static native void beginPin(long var0, int var2);

    public static native void endGroupHint();

    public static native void endPin();

    public static native void endNode();

    public static native void end();

    public static native float getScreenSizeX();

    public static native float getScreenSizeY();

    public static native float toCanvasX(float var0);

    public static native float toCanvasY(float var0);

    public static native float toScreenX(float var0);

    public static native float toScreenY(float var0);

    public static NodeEditorStyle getStyle() {
        NodeEditor.STYLE.ptr = NodeEditor.nGetStyle();
        return STYLE;
    }

    private static native long nGetStyle();

    public static native String getStyleColorName(int var0);

    public static native void pushStyleColor(int var0, float var1, float var2, float var3, float var4);

    public static native void popStyleColor(int var0);

    public static native void pushStyleVar(int var0, float var1);

    public static native void pushStyleVar(int var0, float var1, float var2);

    public static native void pushStyleVar(int var0, float var1, float var2, float var3, float var4);

    public static native void popStyleVar(int var0);

    public static native float getGroupMinX();

    public static native float getGroupMinY();

    public static native float getGroupMaxX();

    public static native float getGroupMaxY();

    public static ImDrawList getHintForegroundDrawList() {
        NodeEditor.HINT_FOREGROUND_DRAW_LIST.ptr = NodeEditor.nGetHintForegroundDrawList();
        return HINT_FOREGROUND_DRAW_LIST;
    }

    public static ImDrawList getHintBackgroundDrawList() {
        NodeEditor.HINT_BACKGROUND_DRAW_LIST.ptr = NodeEditor.nGetHintBackgroundDrawList();
        return HINT_BACKGROUND_DRAW_LIST;
    }

    public static ImDrawList getNodeBackgroundDrawList(long l) {
        NodeEditor.NODE_BACKGROUND_DRAW_LIST.ptr = NodeEditor.nGetNodeBackgroundDrawList(l);
        return NODE_BACKGROUND_DRAW_LIST;
    }

    private static native long nGetHintForegroundDrawList();

    private static native long nGetHintBackgroundDrawList();

    private static native long nGetNodeBackgroundDrawList(long var0);

    public static native long getDoubleClickedNode();

    public static native long getDoubleClickedPin();

    public static native long getDoubleClickedLink();

    public static native boolean isBackgroundClicked();

    public static native boolean isBackgroundDoubleClicked();

    public static native boolean pinHadAnyLinks(long var0);

    public static native float getCurrentZoom();

    public static native void pinRect(float var0, float var1, float var2, float var3);

    public static native void pinPivotRect(float var0, float var1, float var2, float var3);

    public static native void pinPivotSize(float var0, float var1);

    public static native void pinPivotScale(float var0, float var1);

    public static native void pinPivotAlignment(float var0, float var1);

    public static boolean showNodeContextMenu(ImLong imLong) {
        return NodeEditor.nShowNodeContextMenu(imLong.getData());
    }

    private static native boolean nShowNodeContextMenu(long[] var0);

    public static boolean showPinContextMenu(ImLong imLong) {
        return NodeEditor.nShowPinContextMenu(imLong.getData());
    }

    private static native boolean nShowPinContextMenu(long[] var0);

    public static boolean showLinkContextMenu(ImLong imLong) {
        return NodeEditor.nShowLinkContextMenu(imLong.getData());
    }

    private static native boolean nShowLinkContextMenu(long[] var0);

    public static native long getNodeWithContextMenu();

    public static native long getPinWithContextMenu();

    public static native long getLinkWithContextMenu();

    public static native boolean showBackgroundContextMenu();

    public static native void restoreNodeState(long var0);

    public static native void suspend();

    public static native void resume();

    public static native boolean isSuspended();

    public static native boolean isActive();

    public static native void setNodePosition(long var0, float var2, float var3);

    public static void link(long l, long l2, long l3) {
        NodeEditor.link(l, l2, l3, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static native void link(long var0, long var2, long var4, float var6, float var7, float var8, float var9, float var10);

    public static native void flow(long var0);

    public static boolean beginCreate() {
        return NodeEditor.beginCreate(1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static native boolean beginCreate(float var0, float var1, float var2, float var3, float var4);

    public static boolean queryNewLink(ImLong imLong, ImLong imLong2) {
        return NodeEditor.nQueryNewLink(imLong.getData(), imLong2.getData(), 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static boolean queryNewLink(ImLong imLong, ImLong imLong2, float f, float f2, float f3, float f4, float f5) {
        return NodeEditor.nQueryNewLink(imLong.getData(), imLong2.getData(), f, f2, f3, f4, f5);
    }

    private static native boolean nQueryNewLink(long[] var0, long[] var1, float var2, float var3, float var4, float var5, float var6);

    public static boolean acceptNewItem() {
        return NodeEditor.acceptNewItem(1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static native boolean acceptNewItem(float var0, float var1, float var2, float var3, float var4);

    public static void rejectNewItem() {
        NodeEditor.rejectNewItem(1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static native void rejectNewItem(float var0, float var1, float var2, float var3, float var4);

    public static native void endCreate();

    public static native boolean beginDelete();

    public static boolean queryDeletedLink(ImLong imLong, ImLong imLong2, ImLong imLong3) {
        return NodeEditor.nQueryDeletedLink(imLong.getData(), imLong2.getData(), imLong3.getData());
    }

    private static native boolean nQueryDeletedLink(long[] var0, long[] var1, long[] var2);

    public static boolean queryDeletedNode(ImLong imLong) {
        return NodeEditor.nQueryDeletedNode(imLong.getData());
    }

    private static native boolean nQueryDeletedNode(long[] var0);

    public static native boolean acceptDeletedItem();

    public static native void rejectDeletedItem();

    public static native void endDelete();

    public static native void navigateToContent(float var0);

    public static native void navigateToSelection(boolean var0, float var1);

    public static native void getNodePosition(long var0, ImVec2 var2);

    public static native float getNodePositionX(long var0);

    public static native float getNodePositionY(long var0);

    public static native float getNodeSizeX(long var0);

    public static native float getNodeSizeY(long var0);

    public static native void centerNodeOnScreen(long var0);

    public static native boolean hasSelectionChanged();

    public static native int getSelectedObjectCount();

    public static native int getSelectedNodes(long[] var0, int var1);

    public static native int getSelectedLinks(long[] var0, int var1);

    public static native void clearSelection();

    public static native void selectNode(long var0, boolean var2);

    public static native void selectLink(long var0, boolean var2);

    public static native void deselectNode(long var0);

    public static native void deselectLink(long var0);

    public static native boolean deleteNode(long var0);

    public static native boolean deleteLink(long var0);

    public static native void enableShortcuts(boolean var0);

    public static native boolean areShortcutsEnabled();

    public static native boolean beginShortcut();

    public static native boolean acceptCut();

    public static native boolean acceptCopy();

    public static native boolean acceptPaste();

    public static native boolean acceptDuplicate();

    public static native boolean acceptCreateNode();

    public static native int getActionContextSize();

    public static native int getActionContextNodes(long[] var0, int var1);

    public static native int getActionContextLinks(long[] var0, int var1);

    public static native void endShortcut();
}

