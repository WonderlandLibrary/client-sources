/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.ImFont;
import imgui.ImFontAtlas;
import imgui.ImVec2;
import imgui.binding.ImGuiStruct;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;

public final class ImGuiIO
extends ImGuiStruct {
    private ImFontAtlas imFontAtlas = new ImFontAtlas(0L);

    public ImGuiIO(long l) {
        super(l);
    }

    public native int getConfigFlags();

    public native void setConfigFlags(int var1);

    public void addConfigFlags(int n) {
        this.setConfigFlags(this.getConfigFlags() | n);
    }

    public void removeConfigFlags(int n) {
        this.setConfigFlags(this.getConfigFlags() & ~n);
    }

    public boolean hasConfigFlags(int n) {
        return (this.getConfigFlags() & n) != 0;
    }

    public native int getBackendFlags();

    public native void setBackendFlags(int var1);

    public void addBackendFlags(int n) {
        this.setBackendFlags(this.getBackendFlags() | n);
    }

    public void removeBackendFlags(int n) {
        this.setBackendFlags(this.getBackendFlags() & ~n);
    }

    public boolean hasBackendFlags(int n) {
        return (this.getBackendFlags() & n) != 0;
    }

    public native float getIniSavingRate();

    public native void setIniSavingRate(float var1);

    public native String getIniFilename();

    public native void setIniFilename(String var1);

    public native String getLogFilename();

    public native void setLogFilename(String var1);

    public native float getMouseDoubleClickTime();

    public native void setMouseDoubleClickTime(float var1);

    public native float getMouseDoubleClickMaxDist();

    public native void setMouseDoubleClickMaxDist(float var1);

    public native float getMouseDragThreshold();

    public native void setMouseDragThreshold(float var1);

    public native void getKeyMap(int[] var1);

    public native int getKeyMap(int var1);

    public native void setKeyMap(int var1, int var2);

    public native void setKeyMap(int[] var1);

    public native float getKeyRepeatDelay();

    public native void setKeyRepeatDelay(float var1);

    public native float getKeyRepeatRate();

    public native void setKeyRepeatRate(float var1);

    public ImFontAtlas getFonts() {
        this.imFontAtlas.ptr = this.nGetFonts();
        return this.imFontAtlas;
    }

    private native long nGetFonts();

    public void setFonts(ImFontAtlas imFontAtlas) {
        this.imFontAtlas = imFontAtlas;
        this.nSetFonts(imFontAtlas.ptr);
    }

    private native void nSetFonts(long var1);

    public native float getFontGlobalScale();

    public native void setFontGlobalScale(float var1);

    public native boolean getFontAllowUserScaling();

    public native void setFontAllowUserScaling(boolean var1);

    public void setFontDefault(ImFont imFont) {
        this.nSetFontDefault(imFont.ptr);
    }

    private native void nSetFontDefault(long var1);

    public native boolean getMouseDrawCursor();

    public native void setMouseDrawCursor(boolean var1);

    public native boolean getConfigMacOSXBehaviors();

    public native void setConfigMacOSXBehaviors(boolean var1);

    public native boolean getConfigInputTextCursorBlink();

    public native void setConfigInputTextCursorBlink(boolean var1);

    public native boolean getConfigDragClickToInputText();

    public native void setConfigDragClickToInputText(boolean var1);

    public native boolean getConfigWindowsResizeFromEdges();

    public native void setConfigWindowsResizeFromEdges(boolean var1);

    public native boolean getConfigWindowsMoveFromTitleBarOnly();

    public native void setConfigWindowsMoveFromTitleBarOnly(boolean var1);

    public native float getConfigMemoryCompactTimer();

    public native void setConfigMemoryCompactTimer(float var1);

    public native String getBackendPlatformName();

    public native void setBackendPlatformName(String var1);

    public native String getBackendRendererName();

    public native void setBackendRendererName(String var1);

    public native void setSetClipboardTextFn(ImStrConsumer var1);

    public native void setGetClipboardTextFn(ImStrSupplier var1);

    public ImVec2 getDisplaySize() {
        ImVec2 imVec2 = new ImVec2();
        this.getDisplaySize(imVec2);
        return imVec2;
    }

    public native void getDisplaySize(ImVec2 var1);

    public native float getDisplaySizeX();

    public native float getDisplaySizeY();

    public native void setDisplaySize(float var1, float var2);

    public ImVec2 getDisplayFramebufferScale() {
        ImVec2 imVec2 = new ImVec2();
        this.getDisplayFramebufferScale(imVec2);
        return imVec2;
    }

    public native void getDisplayFramebufferScale(ImVec2 var1);

    public native float getDisplayFramebufferScaleX();

    public native float getDisplayFramebufferScaleY();

    public native void setDisplayFramebufferScale(float var1, float var2);

    public native boolean getConfigDockingNoSplit();

    public native void setConfigDockingNoSplit(boolean var1);

    public native boolean getConfigDockingWithShift();

    public native void setConfigDockingWithShift(boolean var1);

    public native boolean getConfigDockingAlwaysTabBar();

    public native void setConfigDockingAlwaysTabBar(boolean var1);

    public native boolean getConfigDockingTransparentPayload();

    public native void setConfigDockingTransparentPayload(boolean var1);

    public native boolean getConfigViewportsNoAutoMerge();

    public native void setConfigViewportsNoAutoMerge(boolean var1);

    public native boolean getConfigViewportsNoTaskBarIcon();

    public native void setConfigViewportsNoTaskBarIcon(boolean var1);

    public native boolean getConfigViewportsNoDecoration();

    public native void setConfigViewportsNoDecoration(boolean var1);

    public native boolean getConfigViewportsNoDefaultParent();

    public native void setConfigViewportsNoDefaultParent(boolean var1);

    public native float getDeltaTime();

    public native void setDeltaTime(float var1);

    public ImVec2 getMousePos() {
        ImVec2 imVec2 = new ImVec2();
        this.getMousePos(imVec2);
        return imVec2;
    }

    public native void getMousePos(ImVec2 var1);

    public native float getMousePosX();

    public native float getMousePosY();

    public native void setMousePos(float var1, float var2);

    public native void getMouseDown(boolean[] var1);

    public native boolean getMouseDown(int var1);

    public native void setMouseDown(int var1, boolean var2);

    public native void setMouseDown(boolean[] var1);

    public native float getMouseWheel();

    public native void setMouseWheel(float var1);

    public native float getMouseWheelH();

    public native void setMouseWheelH(float var1);

    public native float getMouseHoveredViewport();

    public native void setMouseHoveredViewport(int var1);

    public native boolean getKeyCtrl();

    public native void setKeyCtrl(boolean var1);

    public native boolean getKeyShift();

    public native void setKeyShift(boolean var1);

    public native boolean getKeyAlt();

    public native void setKeyAlt(boolean var1);

    public native boolean getKeySuper();

    public native void setKeySuper(boolean var1);

    public native void getKeysDown(boolean[] var1);

    public native boolean getKeysDown(int var1);

    public native void setKeysDown(int var1, boolean var2);

    public native void setKeysDown(boolean[] var1);

    public native void getNavInputs(float[] var1);

    public native float getNavInputs(int var1);

    public native void setNavInputs(int var1, float var2);

    public native void setNavInputs(float[] var1);

    public native boolean getWantCaptureMouse();

    public native void setWantCaptureMouse(boolean var1);

    public native boolean getWantCaptureKeyboard();

    public native void setWantCaptureKeyboard(boolean var1);

    public native boolean getWantTextInput();

    public native void setWantTextInput(boolean var1);

    public native boolean getWantSetMousePos();

    public native void setWantSetMousePos(boolean var1);

    public native boolean getWantSaveIniSettings();

    public native void setWantSaveIniSettings(boolean var1);

    public native boolean getNavActive();

    public native void setNavActive(boolean var1);

    public native boolean getNavVisible();

    public native void setNavVisible(boolean var1);

    public native float getFramerate();

    public native void setFramerate(float var1);

    public native int getMetricsRenderVertices();

    public native void setMetricsRenderVertices(int var1);

    public native int getMetricsRenderIndices();

    public native void setMetricsRenderIndices(int var1);

    public native int getMetricsRenderWindows();

    public native void setMetricsRenderWindows(int var1);

    public native int getMetricsActiveWindows();

    public native void setMetricsActiveWindows(int var1);

    public native int getMetricsActiveAllocations();

    public native void setMetricsActiveAllocations(int var1);

    public ImVec2 getMouseDelta() {
        ImVec2 imVec2 = new ImVec2();
        this.getMouseDelta(imVec2);
        return imVec2;
    }

    public native void getMouseDelta(ImVec2 var1);

    public native float getMouseDeltaX();

    public native float getMouseDeltaY();

    public native void setMouseDelta(float var1, float var2);

    public native void addInputCharacter(int var1);

    public native void addInputCharacterUTF16(short var1);

    public native void addInputCharactersUTF8(String var1);

    public native void addFocusEvent(boolean var1);

    public native void clearInputCharacters();

    public native void clearInputKeys();
}

