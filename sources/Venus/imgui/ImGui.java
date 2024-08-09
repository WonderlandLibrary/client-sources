/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.ImDrawData;
import imgui.ImDrawList;
import imgui.ImFont;
import imgui.ImFontAtlas;
import imgui.ImGuiIO;
import imgui.ImGuiPlatformIO;
import imgui.ImGuiStorage;
import imgui.ImGuiStyle;
import imgui.ImGuiViewport;
import imgui.ImGuiWindowClass;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.assertion.ImAssertCallback;
import imgui.callback.ImGuiInputTextCallback;
import imgui.internal.ImGuiContext;
import imgui.type.ImBoolean;
import imgui.type.ImDouble;
import imgui.type.ImFloat;
import imgui.type.ImInt;
import imgui.type.ImLong;
import imgui.type.ImShort;
import imgui.type.ImString;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.ref.WeakReference;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Optional;
import java.util.Properties;

public class ImGui {
    private static final String LIB_PATH_PROP = "imgui.library.path";
    private static final String LIB_NAME_PROP = "imgui.library.name";
    private static final String LIB_NAME_DEFAULT = System.getProperty("os.arch").contains("64") ? "imgui-java64" : "imgui-java";
    private static final String LIB_TMP_DIR_PREFIX = "imgui-java-natives";
    private static final ImGuiContext IMGUI_CONTEXT;
    private static final ImGuiIO IMGUI_IO;
    private static final ImDrawList WINDOW_DRAW_LIST;
    private static final ImDrawList BACKGROUND_DRAW_LIST;
    private static final ImDrawList FOREGROUND_DRAW_LIST;
    private static final ImGuiStorage IMGUI_STORAGE;
    private static final ImGuiViewport WINDOW_VIEWPORT;
    private static final ImGuiViewport FIND_VIEWPORT;
    private static final ImDrawData DRAW_DATA;
    private static final ImFont FONT;
    private static final ImGuiStyle STYLE;
    private static final ImGuiViewport MAIN_VIEWPORT;
    private static final ImGuiPlatformIO PLATFORM_IO;
    private static WeakReference<Object> payloadRef;
    private static final byte[] PAYLOAD_PLACEHOLDER_DATA;

    private static String resolveFullLibName() {
        String string;
        String string2;
        boolean bl = System.getProperty("os.name").toLowerCase().contains("win");
        boolean bl2 = System.getProperty("os.name").toLowerCase().contains("mac");
        if (bl) {
            string2 = "";
            string = ".dll";
        } else if (bl2) {
            string2 = "lib";
            string = ".dylib";
        } else {
            string2 = "lib";
            string = ".so";
        }
        return System.getProperty(LIB_NAME_PROP, string2 + LIB_NAME_DEFAULT + string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String tryLoadFromClasspath(String string) {
        try (InputStream inputStream = ImGui.class.getClassLoader().getResourceAsStream("io/imgui/java/native-bin/" + string);){
            Path path;
            block21: {
                if (inputStream == null) {
                    String string2 = null;
                    return string2;
                }
                String string3 = ImGui.getVersionString().orElse("undefined");
                Path path2 = Paths.get(System.getProperty("java.io.tmpdir"), new String[0]).resolve(LIB_TMP_DIR_PREFIX).resolve(string3);
                if (!Files.exists(path2, new LinkOption[0])) {
                    Files.createDirectories(path2, new FileAttribute[0]);
                }
                path = path2.resolve(string);
                try {
                    Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
                } catch (AccessDeniedException accessDeniedException) {
                    if (Files.exists(path, new LinkOption[0])) break block21;
                    throw accessDeniedException;
                }
            }
            String string4 = path.toAbsolutePath().toString();
            return string4;
        } catch (IOException iOException) {
            throw new UncheckedIOException(iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Optional<String> getVersionString() {
        Properties properties = new Properties();
        try (InputStream inputStream = ImGui.class.getResourceAsStream("/imgui/imgui-java.properties");){
            if (inputStream == null) return Optional.empty();
            properties.load(inputStream);
            Optional<String> optional = Optional.of(properties.get("imgui.java.version").toString());
            return optional;
        } catch (IOException iOException) {
            throw new UncheckedIOException(iOException);
        }
    }

    public static void init() {
    }

    private static native void nInitJni();

    public static ImGuiContext createContext() {
        ImGui.IMGUI_CONTEXT.ptr = ImGui.nCreateContext();
        return IMGUI_CONTEXT;
    }

    private static native long nCreateContext();

    public static ImGuiContext createContext(ImFontAtlas imFontAtlas) {
        ImGui.IMGUI_CONTEXT.ptr = ImGui.nCreateContext(imFontAtlas.ptr);
        return IMGUI_CONTEXT;
    }

    private static native long nCreateContext(long var0);

    public static native void destroyContext();

    public static void destroyContext(ImGuiContext imGuiContext) {
        ImGui.nDestroyContext(imGuiContext.ptr);
    }

    private static native void nDestroyContext(long var0);

    public static ImGuiContext getCurrentContext() {
        ImGui.IMGUI_CONTEXT.ptr = ImGui.nGetCurrentContext();
        return IMGUI_CONTEXT;
    }

    private static native long nGetCurrentContext();

    public static void setCurrentContext(ImGuiContext imGuiContext) {
        ImGui.nSetCurrentContext(imGuiContext.ptr);
    }

    private static native void nSetCurrentContext(long var0);

    public static native void setAssertCallback(ImAssertCallback var0);

    public static ImGuiIO getIO() {
        ImGui.IMGUI_IO.ptr = ImGui.nGetIO();
        return IMGUI_IO;
    }

    private static native long nGetIO();

    public static ImGuiStyle getStyle() {
        ImGui.STYLE.ptr = ImGui.nGetStyle();
        return STYLE;
    }

    private static native long nGetStyle();

    public static native void newFrame();

    public static native void endFrame();

    public static native void render();

    public static ImDrawData getDrawData() {
        ImGui.DRAW_DATA.ptr = ImGui.nGetDrawData();
        return DRAW_DATA;
    }

    private static native long nGetDrawData();

    public static native void showDemoWindow();

    public static void showDemoWindow(ImBoolean imBoolean) {
        ImGui.nShowDemoWindow(imBoolean.getData());
    }

    private static native void nShowDemoWindow(boolean[] var0);

    public static void showMetricsWindow(ImBoolean imBoolean) {
        ImGui.nShowMetricsWindow(imBoolean.getData());
    }

    public static native void showMetricsWindow();

    private static native void nShowMetricsWindow(boolean[] var0);

    public static void showStackToolWindow(ImBoolean imBoolean) {
        ImGui.nShowMetricsWindow(imBoolean.getData());
    }

    public static native void showStackToolWindow();

    private static native void nShowStackToolWindow(boolean[] var0);

    public static native void showAboutWindow();

    public static void showAboutWindow(ImBoolean imBoolean) {
        ImGui.nShowAboutWindow(imBoolean.getData());
    }

    private static native void nShowAboutWindow(boolean[] var0);

    public static native void showStyleEditor();

    public static void showStyleEditor(ImGuiStyle imGuiStyle) {
        ImGui.nShowStyleEditor(imGuiStyle.ptr);
    }

    private static native void nShowStyleEditor(long var0);

    public static native boolean showStyleSelector(String var0);

    public static native void showFontSelector(String var0);

    public static native void showUserGuide();

    public static native String getVersion();

    public static native void styleColorsDark();

    public static void styleColorsDark(ImGuiStyle imGuiStyle) {
        ImGui.nStyleColorsDark(imGuiStyle.ptr);
    }

    private static native void nStyleColorsDark(long var0);

    public static native void styleColorsLight();

    public static void styleColorsLight(ImGuiStyle imGuiStyle) {
        ImGui.nStyleColorsLight(imGuiStyle.ptr);
    }

    private static native void nStyleColorsLight(long var0);

    public static native void styleColorsClassic();

    public static void styleColorsClassic(ImGuiStyle imGuiStyle) {
        ImGui.nStyleColorsClassic(imGuiStyle.ptr);
    }

    private static native void nStyleColorsClassic(long var0);

    public static native boolean begin(String var0);

    public static boolean begin(String string, ImBoolean imBoolean) {
        return ImGui.nBegin(string, imBoolean.getData(), 0);
    }

    public static boolean begin(String string, int n) {
        return ImGui.nBegin(string, n);
    }

    public static boolean begin(String string, ImBoolean imBoolean, int n) {
        return ImGui.nBegin(string, imBoolean.getData(), n);
    }

    private static native boolean nBegin(String var0, int var1);

    private static native boolean nBegin(String var0, boolean[] var1, int var2);

    public static native void end();

    public static native boolean beginChild(String var0);

    public static native boolean beginChild(String var0, float var1, float var2);

    public static native boolean beginChild(String var0, float var1, float var2, boolean var3);

    public static native boolean beginChild(String var0, float var1, float var2, boolean var3, int var4);

    public static native boolean beginChild(int var0);

    public static native boolean beginChild(int var0, float var1, float var2, boolean var3);

    public static native boolean beginChild(int var0, float var1, float var2, boolean var3, int var4);

    public static native void endChild();

    public static native boolean isWindowAppearing();

    public static native boolean isWindowCollapsed();

    public static native boolean isWindowFocused();

    public static native boolean isWindowFocused(int var0);

    public static native boolean isWindowHovered();

    public static native boolean isWindowHovered(int var0);

    public static ImDrawList getWindowDrawList() {
        ImGui.WINDOW_DRAW_LIST.ptr = ImGui.nGetWindowDrawList();
        return WINDOW_DRAW_LIST;
    }

    private static native long nGetWindowDrawList();

    public static native float getWindowDpiScale();

    public static ImVec2 getWindowPos() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getWindowPos(imVec2);
        return imVec2;
    }

    public static native void getWindowPos(ImVec2 var0);

    public static native float getWindowPosX();

    public static native float getWindowPosY();

    public static ImVec2 getWindowSize() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getWindowSize(imVec2);
        return imVec2;
    }

    public static native void getWindowSize(ImVec2 var0);

    public static native float getWindowSizeX();

    public static native float getWindowSizeY();

    public static native float getWindowWidth();

    public static native float getWindowHeight();

    public static ImGuiViewport getWindowViewport() {
        ImGui.WINDOW_VIEWPORT.ptr = ImGui.nGetWindowViewport();
        return WINDOW_VIEWPORT;
    }

    private static native long nGetWindowViewport();

    public static native void setNextWindowPos(float var0, float var1);

    public static native void setNextWindowPos(float var0, float var1, int var2);

    public static native void setNextWindowPos(float var0, float var1, int var2, float var3, float var4);

    public static native void setNextWindowSize(float var0, float var1);

    public static native void setNextWindowSize(float var0, float var1, int var2);

    public static native void setNextWindowSizeConstraints(float var0, float var1, float var2, float var3);

    public static native void setNextWindowContentSize(float var0, float var1);

    public static native void setNextWindowCollapsed(boolean var0);

    public static native void setNextWindowCollapsed(boolean var0, int var1);

    public static native void setNextWindowFocus();

    public static native void setNextWindowBgAlpha(float var0);

    public static native void setNextWindowViewport(int var0);

    public static native void setWindowPos(float var0, float var1);

    public static native void setWindowPos(float var0, float var1, int var2);

    public static native void setWindowSize(float var0, float var1);

    public static native void setWindowSize(float var0, float var1, int var2);

    public static native void setWindowCollapsed(boolean var0);

    public static native void setWindowCollapsed(boolean var0, int var1);

    public static native void setWindowFocus();

    public native void setWindowFontScale(float var1);

    public static native void setWindowPos(String var0, float var1, float var2);

    public static native void setWindowPos(String var0, float var1, float var2, int var3);

    public static native void setWindowSize(String var0, float var1, float var2);

    public static native void setWindowSize(String var0, float var1, float var2, int var3);

    public static native void setWindowCollapsed(String var0, boolean var1);

    public static native void setWindowCollapsed(String var0, boolean var1, int var2);

    public static native void setWindowFocus(String var0);

    public static ImVec2 getContentRegionAvail() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getContentRegionAvail(imVec2);
        return imVec2;
    }

    public static native void getContentRegionAvail(ImVec2 var0);

    public static native float getContentRegionAvailX();

    public static native float getContentRegionAvailY();

    public static ImVec2 getContentRegionMax() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getContentRegionMax(imVec2);
        return imVec2;
    }

    public static native void getContentRegionMax(ImVec2 var0);

    public static native float getContentRegionMaxX();

    public static native float getContentRegionMaxY();

    public static ImVec2 getWindowContentRegionMin() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getWindowContentRegionMin(imVec2);
        return imVec2;
    }

    public static native void getWindowContentRegionMin(ImVec2 var0);

    public static native float getWindowContentRegionMinX();

    public static native float getWindowContentRegionMinY();

    public static ImVec2 getWindowContentRegionMax() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getWindowContentRegionMax(imVec2);
        return imVec2;
    }

    public static native void getWindowContentRegionMax(ImVec2 var0);

    public static native float getWindowContentRegionMaxX();

    public static native float getWindowContentRegionMaxY();

    public static native float getScrollX();

    public static native float getScrollY();

    public static native void setScrollX(float var0);

    public static native void setScrollY(float var0);

    public static native float getScrollMaxX();

    public static native float getScrollMaxY();

    public static native void setScrollHereX();

    public static native void setScrollHereX(float var0);

    public static native void setScrollHereY();

    public static native void setScrollHereY(float var0);

    public static native void setScrollFromPosX(float var0);

    public static native void setScrollFromPosX(float var0, float var1);

    public static native void setScrollFromPosY(float var0);

    public static native void setScrollFromPosY(float var0, float var1);

    public static void pushFont(ImFont imFont) {
        ImGui.nPushFont(imFont.ptr);
    }

    private static native void nPushFont(long var0);

    public static native void popFont();

    public static native void pushStyleColor(int var0, float var1, float var2, float var3, float var4);

    public static native void pushStyleColor(int var0, int var1, int var2, int var3, int var4);

    public static native void pushStyleColor(int var0, int var1);

    public static native void popStyleColor();

    public static native void popStyleColor(int var0);

    public static native void pushStyleVar(int var0, float var1);

    public static native void pushStyleVar(int var0, float var1, float var2);

    public static native void popStyleVar();

    public static native void popStyleVar(int var0);

    public static native void pushAllowKeyboardFocus(boolean var0);

    public static native void popAllowKeyboardFocus();

    public static native void pushButtonRepeat(boolean var0);

    public static native void popButtonRepeat();

    public static native void pushItemWidth(float var0);

    public static native void popItemWidth();

    public static native void setNextItemWidth(float var0);

    public static native float calcItemWidth();

    public static native void pushTextWrapPos();

    public static native void pushTextWrapPos(float var0);

    public static native void popTextWrapPos();

    public static ImFont getFont() {
        ImGui.FONT.ptr = ImGui.nGetFont();
        return FONT;
    }

    private static native long nGetFont();

    public static native int getFontSize();

    public static ImVec2 getFontTexUvWhitePixel() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getFontTexUvWhitePixel(imVec2);
        return imVec2;
    }

    public static native void getFontTexUvWhitePixel(ImVec2 var0);

    public static native float getFontTexUvWhitePixelX();

    public static native float getFontTexUvWhitePixelY();

    public static native int getColorU32(int var0);

    public static native int getColorU32(int var0, float var1);

    public static native int getColorU32(float var0, float var1, float var2, float var3);

    public static native int getColorU32i(int var0);

    public ImVec4 getStyleColorVec4(int n) {
        ImVec4 imVec4 = new ImVec4();
        ImGui.getStyleColorVec4(n, imVec4);
        return imVec4;
    }

    public static native void getStyleColorVec4(int var0, ImVec4 var1);

    public static native void separator();

    public static native void sameLine();

    public static native void sameLine(float var0);

    public static native void sameLine(float var0, float var1);

    public static native void newLine();

    public static native void spacing();

    public static native void dummy(float var0, float var1);

    public static native void indent();

    public static native void indent(float var0);

    public static native void unindent();

    public static native void unindent(float var0);

    public static native void beginGroup();

    public static native void endGroup();

    public static ImVec2 getCursorPos() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getCursorPos(imVec2);
        return imVec2;
    }

    public static native void getCursorPos(ImVec2 var0);

    public static native float getCursorPosX();

    public static native float getCursorPosY();

    public static native void setCursorPos(float var0, float var1);

    public static native void setCursorPosX(float var0);

    public static native void setCursorPosY(float var0);

    public static ImVec2 getCursorStartPos() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getCursorStartPos(imVec2);
        return imVec2;
    }

    public static native void getCursorStartPos(ImVec2 var0);

    public static native float getCursorStartPosX();

    public static native float getCursorStartPosY();

    public static ImVec2 getCursorScreenPos() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getCursorScreenPos(imVec2);
        return imVec2;
    }

    public static native void getCursorScreenPos(ImVec2 var0);

    public static native float getCursorScreenPosX();

    public static native float getCursorScreenPosY();

    public static native void setCursorScreenPos(float var0, float var1);

    public static native void alignTextToFramePadding();

    public static native float getTextLineHeight();

    public static native float getTextLineHeightWithSpacing();

    public static native float getFrameHeight();

    public static native float getFrameHeightWithSpacing();

    public static native void pushID(String var0);

    public static native void pushID(String var0, String var1);

    public static native void pushID(long var0);

    public static native void pushID(int var0);

    public static native void popID();

    public static native int getID(String var0);

    public static native int getID(String var0, String var1);

    public static native int getID(long var0);

    public static native void textUnformatted(String var0);

    public static native void text(String var0);

    public static native void textColored(float var0, float var1, float var2, float var3, String var4);

    public static native void textColored(int var0, int var1, int var2, int var3, String var4);

    public static native void textColored(int var0, String var1);

    public static native void textDisabled(String var0);

    public static native void textWrapped(String var0);

    public static native void labelText(String var0, String var1);

    public static native void bulletText(String var0);

    public static native boolean button(String var0);

    public static native boolean button(String var0, float var1, float var2);

    public static native boolean smallButton(String var0);

    public static native boolean invisibleButton(String var0, float var1, float var2);

    public static native boolean invisibleButton(String var0, float var1, float var2, int var3);

    public static native boolean arrowButton(String var0, int var1);

    public static native void image(int var0, float var1, float var2);

    public static native void image(int var0, float var1, float var2, float var3, float var4);

    public static native void image(int var0, float var1, float var2, float var3, float var4, float var5, float var6);

    public static native void image(int var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10);

    public static native void image(int var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14);

    public static native boolean imageButton(int var0, float var1, float var2);

    public static native boolean imageButton(int var0, float var1, float var2, float var3, float var4);

    public static native boolean imageButton(int var0, float var1, float var2, float var3, float var4, float var5, float var6);

    public static native boolean imageButton(int var0, float var1, float var2, float var3, float var4, float var5, float var6, int var7);

    public static native boolean imageButton(int var0, float var1, float var2, float var3, float var4, float var5, float var6, int var7, float var8, float var9, float var10, float var11);

    public static native boolean imageButton(int var0, float var1, float var2, float var3, float var4, float var5, float var6, int var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14, float var15);

    public static native boolean checkbox(String var0, boolean var1);

    public static boolean checkbox(String string, ImBoolean imBoolean) {
        return ImGui.nCheckbox(string, imBoolean.getData());
    }

    private static native boolean nCheckbox(String var0, boolean[] var1);

    public static boolean checkboxFlags(String string, ImInt imInt, int n) {
        return ImGui.nCheckboxFlags(string, imInt.getData(), n);
    }

    private static native boolean nCheckboxFlags(String var0, int[] var1, int var2);

    public static native boolean radioButton(String var0, boolean var1);

    public static boolean radioButton(String string, ImInt imInt, int n) {
        return ImGui.nRadioButton(string, imInt.getData(), n);
    }

    private static native boolean nRadioButton(String var0, int[] var1, int var2);

    public static native void progressBar(float var0);

    public static native void progressBar(float var0, float var1, float var2);

    public static native void progressBar(float var0, float var1, float var2, String var3);

    public static native void bullet();

    public static native boolean beginCombo(String var0, String var1);

    public static native boolean beginCombo(String var0, String var1, int var2);

    public static native void endCombo();

    public static boolean combo(String string, ImInt imInt, String[] stringArray) {
        return ImGui.nCombo(string, imInt.getData(), stringArray, stringArray.length, -1);
    }

    public static boolean combo(String string, ImInt imInt, String[] stringArray, int n) {
        return ImGui.nCombo(string, imInt.getData(), stringArray, stringArray.length, n);
    }

    private static native boolean nCombo(String var0, int[] var1, String[] var2, int var3, int var4);

    public static boolean combo(String string, ImInt imInt, String string2) {
        return ImGui.nCombo(string, imInt.getData(), string2, -1);
    }

    public static boolean combo(String string, ImInt imInt, String string2, int n) {
        return ImGui.nCombo(string, imInt.getData(), string2, n);
    }

    private static native boolean nCombo(String var0, int[] var1, String var2, int var3);

    public static native boolean dragFloat(String var0, float[] var1);

    public static native boolean dragFloat(String var0, float[] var1, float var2);

    public static native boolean dragFloat(String var0, float[] var1, float var2, float var3, float var4);

    public static native boolean dragFloat(String var0, float[] var1, float var2, float var3, float var4, String var5);

    public static native boolean dragFloat(String var0, float[] var1, float var2, float var3, float var4, String var5, int var6);

    public static native boolean dragFloat2(String var0, float[] var1);

    public static native boolean dragFloat2(String var0, float[] var1, float var2);

    public static native boolean dragFloat2(String var0, float[] var1, float var2, float var3);

    public static native boolean dragFloat2(String var0, float[] var1, float var2, float var3, float var4);

    public static native boolean dragFloat2(String var0, float[] var1, float var2, float var3, float var4, String var5);

    public static native boolean dragFloat2(String var0, float[] var1, float var2, float var3, float var4, String var5, int var6);

    public static native boolean dragFloat3(String var0, float[] var1);

    public static native boolean dragFloat3(String var0, float[] var1, float var2);

    public static native boolean dragFloat3(String var0, float[] var1, float var2, float var3);

    public static native boolean dragFloat3(String var0, float[] var1, float var2, float var3, float var4);

    public static native boolean dragFloat3(String var0, float[] var1, float var2, float var3, float var4, String var5);

    public static native boolean dragFloat3(String var0, float[] var1, float var2, float var3, float var4, String var5, int var6);

    public static native boolean dragFloat4(String var0, float[] var1);

    public static native boolean dragFloat4(String var0, float[] var1, float var2);

    public static native boolean dragFloat4(String var0, float[] var1, float var2, float var3);

    public static native boolean dragFloat4(String var0, float[] var1, float var2, float var3, float var4);

    public static native boolean dragFloat4(String var0, float[] var1, float var2, float var3, float var4, String var5);

    public static native boolean dragFloat4(String var0, float[] var1, float var2, float var3, float var4, String var5, int var6);

    public static native boolean dragFloatRange2(String var0, float[] var1, float[] var2);

    public static native boolean dragFloatRange2(String var0, float[] var1, float[] var2, float var3);

    public static native boolean dragFloatRange2(String var0, float[] var1, float[] var2, float var3, float var4);

    public static native boolean dragFloatRange2(String var0, float[] var1, float[] var2, float var3, float var4, float var5);

    public static native boolean dragFloatRange2(String var0, float[] var1, float[] var2, float var3, float var4, float var5, String var6);

    public static native boolean dragFloatRange2(String var0, float[] var1, float[] var2, float var3, float var4, float var5, String var6, String var7);

    public static native boolean dragFloatRange2(String var0, float[] var1, float[] var2, float var3, float var4, float var5, String var6, String var7, int var8);

    public static native boolean dragInt(String var0, int[] var1);

    public static native boolean dragInt(String var0, int[] var1, float var2);

    public static native boolean dragInt(String var0, int[] var1, float var2, float var3);

    public static native boolean dragInt(String var0, int[] var1, float var2, float var3, float var4);

    public static native boolean dragInt(String var0, int[] var1, float var2, float var3, float var4, String var5);

    public static native boolean dragInt2(String var0, int[] var1);

    public static native boolean dragInt2(String var0, int[] var1, float var2);

    public static native boolean dragInt2(String var0, int[] var1, float var2, float var3);

    public static native boolean dragInt2(String var0, int[] var1, float var2, float var3, float var4);

    public static native boolean dragInt2(String var0, int[] var1, float var2, float var3, float var4, String var5);

    public static native boolean dragInt3(String var0, int[] var1);

    public static native boolean dragInt3(String var0, int[] var1, float var2);

    public static native boolean dragInt3(String var0, int[] var1, float var2, float var3);

    public static native boolean dragInt3(String var0, int[] var1, float var2, float var3, float var4);

    public static native boolean dragInt3(String var0, int[] var1, float var2, float var3, float var4, String var5);

    public static native boolean dragInt4(String var0, int[] var1);

    public static native boolean dragInt4(String var0, int[] var1, float var2);

    public static native boolean dragInt4(String var0, int[] var1, float var2, float var3);

    public static native boolean dragInt4(String var0, int[] var1, float var2, float var3, float var4);

    public static native boolean dragInt4(String var0, int[] var1, float var2, float var3, float var4, String var5);

    public static native boolean dragIntRange2(String var0, int[] var1, int[] var2);

    public static native boolean dragIntRange2(String var0, int[] var1, int[] var2, float var3);

    public static native boolean dragIntRange2(String var0, int[] var1, int[] var2, float var3, float var4);

    public static native boolean dragIntRange2(String var0, int[] var1, int[] var2, float var3, float var4, float var5);

    public static native boolean dragIntRange2(String var0, int[] var1, int[] var2, float var3, float var4, float var5, String var6);

    public static native boolean dragIntRange2(String var0, int[] var1, int[] var2, float var3, float var4, float var5, String var6, String var7);

    public static boolean dragScalar(String string, int n, ImInt imInt, float f) {
        return ImGui.nDragScalar(string, n, imInt.getData(), f);
    }

    private static native boolean nDragScalar(String var0, int var1, int[] var2, float var3);

    public static boolean dragScalar(String string, int n, ImInt imInt, float f, int n2) {
        return ImGui.nDragScalar(string, n, imInt.getData(), f, n2);
    }

    private static native boolean nDragScalar(String var0, int var1, int[] var2, float var3, int var4);

    public static boolean dragScalar(String string, int n, ImInt imInt, float f, int n2, int n3) {
        return ImGui.nDragScalar(string, n, imInt.getData(), f, n2, n3);
    }

    private static native boolean nDragScalar(String var0, int var1, int[] var2, float var3, int var4, int var5);

    public static boolean dragScalar(String string, int n, ImInt imInt, float f, int n2, int n3, String string2) {
        return ImGui.nDragScalar(string, n, imInt.getData(), f, n2, n3, string2, 0);
    }

    public static boolean dragScalar(String string, int n, ImInt imInt, float f, int n2, int n3, String string2, int n4) {
        return ImGui.nDragScalar(string, n, imInt.getData(), f, n2, n3, string2, n4);
    }

    private static native boolean nDragScalar(String var0, int var1, int[] var2, float var3, int var4, int var5, String var6, int var7);

    public static boolean dragScalar(String string, int n, ImFloat imFloat, float f) {
        return ImGui.nDragScalar(string, n, imFloat.getData(), f);
    }

    private static native boolean nDragScalar(String var0, int var1, float[] var2, float var3);

    public static boolean dragScalar(String string, int n, ImFloat imFloat, float f, float f2) {
        return ImGui.nDragScalar(string, n, imFloat.getData(), f, f2);
    }

    private static native boolean nDragScalar(String var0, int var1, float[] var2, float var3, float var4);

    public static boolean dragScalar(String string, int n, ImFloat imFloat, float f, float f2, float f3) {
        return ImGui.nDragScalar(string, n, imFloat.getData(), f, f2, f3);
    }

    private static native boolean nDragScalar(String var0, int var1, float[] var2, float var3, float var4, float var5);

    public static boolean dragScalar(String string, int n, ImFloat imFloat, float f, float f2, float f3, String string2) {
        return ImGui.nDragScalar(string, n, imFloat.getData(), f, f2, f3, string2, 0);
    }

    public static boolean dragScalar(String string, int n, ImFloat imFloat, float f, float f2, float f3, String string2, int n2) {
        return ImGui.nDragScalar(string, n, imFloat.getData(), f, f2, f3, string2, n2);
    }

    private static native boolean nDragScalar(String var0, int var1, float[] var2, float var3, float var4, float var5, String var6, int var7);

    public static boolean dragScalar(String string, int n, ImDouble imDouble, float f) {
        return ImGui.nDragScalar(string, n, imDouble.getData(), f);
    }

    private static native boolean nDragScalar(String var0, int var1, double[] var2, float var3);

    public static boolean dragScalar(String string, int n, ImDouble imDouble, float f, double d) {
        return ImGui.nDragScalar(string, n, imDouble.getData(), f, d);
    }

    private static native boolean nDragScalar(String var0, int var1, double[] var2, float var3, double var4);

    public static boolean dragScalar(String string, int n, ImDouble imDouble, float f, double d, double d2) {
        return ImGui.nDragScalar(string, n, imDouble.getData(), f, d, d2);
    }

    private static native boolean nDragScalar(String var0, int var1, double[] var2, float var3, double var4, double var6);

    public static boolean dragScalar(String string, int n, ImDouble imDouble, float f, double d, double d2, String string2) {
        return ImGui.nDragScalar(string, n, imDouble.getData(), f, d, d2, string2, 0);
    }

    public static boolean dragScalar(String string, int n, ImDouble imDouble, float f, double d, double d2, String string2, int n2) {
        return ImGui.nDragScalar(string, n, imDouble.getData(), f, d, d2, string2, n2);
    }

    private static native boolean nDragScalar(String var0, int var1, double[] var2, float var3, double var4, double var6, String var8, int var9);

    public static boolean dragScalar(String string, int n, ImLong imLong, float f) {
        return ImGui.nDragScalar(string, n, imLong.getData(), f);
    }

    private static native boolean nDragScalar(String var0, int var1, long[] var2, float var3);

    public static boolean dragScalar(String string, int n, ImLong imLong, float f, long l) {
        return ImGui.nDragScalar(string, n, imLong.getData(), f, l);
    }

    private static native boolean nDragScalar(String var0, int var1, long[] var2, float var3, long var4);

    public static boolean dragScalar(String string, int n, ImLong imLong, float f, long l, long l2) {
        return ImGui.nDragScalar(string, n, imLong.getData(), f, l, l2);
    }

    private static native boolean nDragScalar(String var0, int var1, long[] var2, float var3, long var4, long var6);

    public static boolean dragScalar(String string, int n, ImLong imLong, float f, long l, long l2, String string2) {
        return ImGui.nDragScalar(string, n, imLong.getData(), f, l, l2, string2, 0);
    }

    public static boolean dragScalar(String string, int n, ImLong imLong, float f, long l, long l2, String string2, int n2) {
        return ImGui.nDragScalar(string, n, imLong.getData(), f, l, l2, string2, n2);
    }

    private static native boolean nDragScalar(String var0, int var1, long[] var2, float var3, long var4, long var6, String var8, int var9);

    public static boolean dragScalar(String string, int n, ImShort imShort, float f) {
        return ImGui.nDragScalar(string, n, imShort.getData(), f);
    }

    private static native boolean nDragScalar(String var0, int var1, short[] var2, float var3);

    public static boolean dragScalar(String string, int n, ImShort imShort, float f, short s) {
        return ImGui.nDragScalar(string, n, imShort.getData(), f, s);
    }

    private static native boolean nDragScalar(String var0, int var1, short[] var2, float var3, short var4);

    public static boolean dragScalar(String string, int n, ImShort imShort, float f, short s, short s2) {
        return ImGui.nDragScalar(string, n, imShort.getData(), f, s, s2);
    }

    private static native boolean nDragScalar(String var0, int var1, short[] var2, float var3, short var4, short var5);

    public static boolean dragScalar(String string, int n, ImShort imShort, float f, short s, short s2, String string2) {
        return ImGui.nDragScalar(string, n, imShort.getData(), f, s, s2, string2, 0);
    }

    public static boolean dragScalar(String string, int n, ImShort imShort, float f, short s, short s2, String string2, int n2) {
        return ImGui.nDragScalar(string, n, imShort.getData(), f, s, s2, string2, n2);
    }

    private static native boolean nDragScalar(String var0, int var1, short[] var2, float var3, short var4, short var5, String var6, int var7);

    public static boolean dragScalarN(String string, int n, ImInt imInt, int n2, float f) {
        return ImGui.nDragScalarN(string, n, imInt.getData(), n2, f);
    }

    private static native boolean nDragScalarN(String var0, int var1, int[] var2, int var3, float var4);

    public static boolean dragScalarN(String string, int n, ImInt imInt, int n2, float f, int n3) {
        return ImGui.nDragScalarN(string, n, imInt.getData(), n2, f, n3);
    }

    private static native boolean nDragScalarN(String var0, int var1, int[] var2, int var3, float var4, int var5);

    public static boolean dragScalarN(String string, int n, ImInt imInt, int n2, float f, int n3, int n4) {
        return ImGui.nDragScalarN(string, n, imInt.getData(), n2, f, n3, n4);
    }

    private static native boolean nDragScalarN(String var0, int var1, int[] var2, int var3, float var4, int var5, int var6);

    public static boolean dragScalarN(String string, int n, ImInt imInt, int n2, float f, int n3, int n4, String string2) {
        return ImGui.nDragScalarN(string, n, imInt.getData(), n2, f, n3, n4, string2, 0);
    }

    public static boolean dragScalarN(String string, int n, ImInt imInt, int n2, float f, int n3, int n4, String string2, int n5) {
        return ImGui.nDragScalarN(string, n, imInt.getData(), n2, f, n3, n4, string2, n5);
    }

    private static native boolean nDragScalarN(String var0, int var1, int[] var2, int var3, float var4, int var5, int var6, String var7, int var8);

    public static boolean dragScalarN(String string, int n, ImFloat imFloat, int n2, float f) {
        return ImGui.nDragScalarN(string, n, imFloat.getData(), n2, f);
    }

    private static native boolean nDragScalarN(String var0, int var1, float[] var2, int var3, float var4);

    public static boolean dragScalarN(String string, int n, ImFloat imFloat, int n2, float f, float f2) {
        return ImGui.nDragScalarN(string, n, imFloat.getData(), n2, f, f2);
    }

    private static native boolean nDragScalarN(String var0, int var1, float[] var2, int var3, float var4, float var5);

    public static boolean dragScalarN(String string, int n, ImFloat imFloat, int n2, float f, float f2, float f3) {
        return ImGui.nDragScalarN(string, n, imFloat.getData(), n2, f, f2, f3);
    }

    private static native boolean nDragScalarN(String var0, int var1, float[] var2, int var3, float var4, float var5, float var6);

    public static boolean dragScalarN(String string, int n, ImFloat imFloat, int n2, float f, float f2, float f3, String string2) {
        return ImGui.nDragScalarN(string, n, imFloat.getData(), n2, f, f2, f3, string2, 0);
    }

    public static boolean dragScalarN(String string, int n, ImFloat imFloat, int n2, float f, float f2, float f3, String string2, int n3) {
        return ImGui.nDragScalarN(string, n, imFloat.getData(), n2, f, f2, f3, string2, n3);
    }

    private static native boolean nDragScalarN(String var0, int var1, float[] var2, int var3, float var4, float var5, float var6, String var7, int var8);

    public static boolean dragScalarN(String string, int n, ImDouble imDouble, int n2, float f) {
        return ImGui.nDragScalarN(string, n, imDouble.getData(), n2, f);
    }

    private static native boolean nDragScalarN(String var0, int var1, double[] var2, int var3, float var4);

    public static boolean dragScalarN(String string, int n, ImDouble imDouble, int n2, float f, double d) {
        return ImGui.nDragScalarN(string, n, imDouble.getData(), n2, f, d);
    }

    private static native boolean nDragScalarN(String var0, int var1, double[] var2, int var3, float var4, double var5);

    public static boolean dragScalarN(String string, int n, ImDouble imDouble, int n2, float f, double d, double d2) {
        return ImGui.nDragScalarN(string, n, imDouble.getData(), n2, f, d, d2);
    }

    private static native boolean nDragScalarN(String var0, int var1, double[] var2, int var3, float var4, double var5, double var7);

    public static boolean dragScalarN(String string, int n, ImDouble imDouble, int n2, float f, double d, double d2, String string2) {
        return ImGui.nDragScalarN(string, n, imDouble.getData(), n2, f, d, d2, string2, 0);
    }

    public static boolean dragScalarN(String string, int n, ImDouble imDouble, int n2, float f, double d, double d2, String string2, int n3) {
        return ImGui.nDragScalarN(string, n, imDouble.getData(), n2, f, d, d2, string2, n3);
    }

    private static native boolean nDragScalarN(String var0, int var1, double[] var2, int var3, float var4, double var5, double var7, String var9, int var10);

    public static boolean dragScalarN(String string, int n, ImLong imLong, int n2, float f) {
        return ImGui.nDragScalarN(string, n, imLong.getData(), n2, f);
    }

    private static native boolean nDragScalarN(String var0, int var1, long[] var2, int var3, float var4);

    public static boolean dragScalarN(String string, int n, ImLong imLong, int n2, float f, long l) {
        return ImGui.nDragScalarN(string, n, imLong.getData(), n2, f, l);
    }

    private static native boolean nDragScalarN(String var0, int var1, long[] var2, int var3, float var4, long var5);

    public static boolean dragScalarN(String string, int n, ImLong imLong, int n2, float f, long l, long l2) {
        return ImGui.nDragScalarN(string, n, imLong.getData(), n2, f, l, l2);
    }

    private static native boolean nDragScalarN(String var0, int var1, long[] var2, int var3, float var4, long var5, long var7);

    public static boolean dragScalarN(String string, int n, ImLong imLong, int n2, float f, long l, long l2, String string2) {
        return ImGui.nDragScalarN(string, n, imLong.getData(), n2, f, l, l2, string2, 0);
    }

    public static boolean dragScalarN(String string, int n, ImLong imLong, int n2, float f, long l, long l2, String string2, int n3) {
        return ImGui.nDragScalarN(string, n, imLong.getData(), n2, f, l, l2, string2, n3);
    }

    private static native boolean nDragScalarN(String var0, int var1, long[] var2, int var3, float var4, long var5, long var7, String var9, int var10);

    public static boolean dragScalarN(String string, int n, ImShort imShort, int n2, float f) {
        return ImGui.nDragScalarN(string, n, imShort.getData(), n2, f);
    }

    private static native boolean nDragScalarN(String var0, int var1, short[] var2, int var3, float var4);

    public static boolean dragScalarN(String string, int n, ImShort imShort, int n2, float f, short s) {
        return ImGui.nDragScalarN(string, n, imShort.getData(), n2, f, s);
    }

    private static native boolean nDragScalarN(String var0, int var1, short[] var2, int var3, float var4, short var5);

    public static boolean dragScalarN(String string, int n, ImShort imShort, int n2, float f, short s, short s2) {
        return ImGui.nDragScalarN(string, n, imShort.getData(), n2, f, s, s2);
    }

    private static native boolean nDragScalarN(String var0, int var1, short[] var2, int var3, float var4, short var5, short var6);

    public static boolean dragScalarN(String string, int n, ImShort imShort, int n2, float f, short s, short s2, String string2) {
        return ImGui.nDragScalarN(string, n, imShort.getData(), n2, f, s, s2, string2, 0);
    }

    public static boolean dragScalarN(String string, int n, ImShort imShort, int n2, float f, short s, short s2, String string2, int n3) {
        return ImGui.nDragScalarN(string, n, imShort.getData(), n2, f, s, s2, string2, n3);
    }

    private static native boolean nDragScalarN(String var0, int var1, short[] var2, int var3, float var4, short var5, short var6, String var7, int var8);

    public static native boolean sliderFloat(String var0, float[] var1, float var2, float var3);

    public static native boolean sliderFloat(String var0, float[] var1, float var2, float var3, String var4);

    public static native boolean sliderFloat(String var0, float[] var1, float var2, float var3, String var4, int var5);

    public static native boolean sliderFloat2(String var0, float[] var1, float var2, float var3);

    public static native boolean sliderFloat2(String var0, float[] var1, float var2, float var3, String var4);

    public static native boolean sliderFloat2(String var0, float[] var1, float var2, float var3, String var4, int var5);

    public static native boolean sliderFloat3(String var0, float[] var1, float var2, float var3);

    public static native boolean sliderFloat3(String var0, float[] var1, float var2, float var3, String var4);

    public static native boolean sliderFloat3(String var0, float[] var1, float var2, float var3, String var4, int var5);

    public static native boolean sliderFloat4(String var0, float[] var1, float var2, float var3);

    public static native boolean sliderFloat4(String var0, float[] var1, float var2, float var3, String var4);

    public static native boolean sliderFloat4(String var0, float[] var1, float var2, float var3, String var4, int var5);

    public static native boolean sliderAngle(String var0, float[] var1);

    public static native boolean sliderAngle(String var0, float[] var1, float var2);

    public static native boolean sliderAngle(String var0, float[] var1, float var2, float var3);

    public static native boolean sliderAngle(String var0, float[] var1, float var2, float var3, String var4);

    public static native boolean sliderInt(String var0, int[] var1, int var2, int var3);

    public static native boolean sliderInt(String var0, int[] var1, int var2, int var3, String var4);

    public static native boolean sliderInt2(String var0, int[] var1, int var2, int var3);

    public static native boolean sliderInt2(String var0, int[] var1, int var2, int var3, String var4);

    public static native boolean sliderInt3(String var0, int[] var1, int var2, int var3);

    public static native boolean sliderInt3(String var0, int[] var1, int var2, int var3, String var4);

    public static native boolean sliderInt4(String var0, int[] var1, int var2, int var3);

    public static native boolean sliderInt4(String var0, int[] var1, int var2, int var3, String var4);

    public static boolean sliderScalar(String string, int n, ImInt imInt, int n2, int n3) {
        return ImGui.nSliderScalar(string, n, imInt.getData(), n2, n3);
    }

    private static native boolean nSliderScalar(String var0, int var1, int[] var2, int var3, int var4);

    public static boolean sliderScalar(String string, int n, ImInt imInt, int n2, int n3, String string2) {
        return ImGui.nSliderScalar(string, n, imInt.getData(), n2, n3, string2, 0);
    }

    public static boolean sliderScalar(String string, int n, ImInt imInt, int n2, int n3, String string2, int n4) {
        return ImGui.nSliderScalar(string, n, imInt.getData(), n2, n3, string2, n4);
    }

    private static native boolean nSliderScalar(String var0, int var1, int[] var2, int var3, int var4, String var5, int var6);

    public static boolean sliderScalar(String string, int n, ImFloat imFloat, float f, float f2) {
        return ImGui.nSliderScalar(string, n, imFloat.getData(), f, f2);
    }

    private static native boolean nSliderScalar(String var0, int var1, float[] var2, float var3, float var4);

    public static boolean sliderScalar(String string, int n, ImFloat imFloat, float f, float f2, String string2) {
        return ImGui.nSliderScalar(string, n, imFloat.getData(), f, f2, string2, 0);
    }

    public static boolean sliderScalar(String string, int n, ImFloat imFloat, float f, float f2, String string2, int n2) {
        return ImGui.nSliderScalar(string, n, imFloat.getData(), f, f2, string2, n2);
    }

    private static native boolean nSliderScalar(String var0, int var1, float[] var2, float var3, float var4, String var5, int var6);

    public static boolean sliderScalar(String string, int n, ImLong imLong, long l, long l2) {
        return ImGui.nSliderScalar(string, n, imLong.getData(), l, l2);
    }

    private static native boolean nSliderScalar(String var0, int var1, long[] var2, long var3, long var5);

    public static boolean sliderScalar(String string, int n, ImLong imLong, long l, long l2, String string2) {
        return ImGui.nSliderScalar(string, n, imLong.getData(), l, l2, string2, 0);
    }

    public static boolean sliderScalar(String string, int n, ImLong imLong, long l, long l2, String string2, int n2) {
        return ImGui.nSliderScalar(string, n, imLong.getData(), l, l2, string2, n2);
    }

    private static native boolean nSliderScalar(String var0, int var1, long[] var2, long var3, long var5, String var7, int var8);

    public static boolean sliderScalar(String string, int n, ImDouble imDouble, double d, double d2) {
        return ImGui.nSliderScalar(string, n, imDouble.getData(), d, d2);
    }

    private static native boolean nSliderScalar(String var0, int var1, double[] var2, double var3, double var5);

    public static boolean sliderScalar(String string, int n, ImDouble imDouble, double d, double d2, String string2) {
        return ImGui.nSliderScalar(string, n, imDouble.getData(), d, d2, string2, 0);
    }

    public static boolean sliderScalar(String string, int n, ImDouble imDouble, double d, double d2, String string2, int n2) {
        return ImGui.nSliderScalar(string, n, imDouble.getData(), d, d2, string2, n2);
    }

    private static native boolean nSliderScalar(String var0, int var1, double[] var2, double var3, double var5, String var7, int var8);

    public static boolean sliderScalar(String string, int n, ImShort imShort, short s, short s2) {
        return ImGui.nSliderScalar(string, n, imShort.getData(), s, s2);
    }

    private static native boolean nSliderScalar(String var0, int var1, short[] var2, short var3, short var4);

    public static boolean sliderScalar(String string, int n, ImShort imShort, short s, short s2, String string2) {
        return ImGui.nSliderScalar(string, n, imShort.getData(), s, s2, string2, 0);
    }

    public static boolean sliderScalar(String string, int n, ImShort imShort, short s, short s2, String string2, int n2) {
        return ImGui.nSliderScalar(string, n, imShort.getData(), s, s2, string2, n2);
    }

    private static native boolean nSliderScalar(String var0, int var1, short[] var2, short var3, short var4, String var5, int var6);

    public static boolean sliderScalarN(String string, int n, int n2, ImInt imInt, int n3, int n4) {
        return ImGui.nSliderScalarN(string, n, n2, imInt.getData(), n3, n4);
    }

    private static native boolean nSliderScalarN(String var0, int var1, int var2, int[] var3, int var4, int var5);

    public static boolean sliderScalarN(String string, int n, int n2, ImInt imInt, int n3, int n4, String string2) {
        return ImGui.nSliderScalarN(string, n, n2, imInt.getData(), n3, n4, string2, 0);
    }

    public static boolean sliderScalarN(String string, int n, int n2, ImInt imInt, int n3, int n4, String string2, int n5) {
        return ImGui.nSliderScalarN(string, n, n2, imInt.getData(), n3, n4, string2, n5);
    }

    private static native boolean nSliderScalarN(String var0, int var1, int var2, int[] var3, int var4, int var5, String var6, int var7);

    public static boolean sliderScalarN(String string, int n, int n2, ImFloat imFloat, float f, float f2) {
        return ImGui.nSliderScalarN(string, n, n2, imFloat.getData(), f, f2);
    }

    private static native boolean nSliderScalarN(String var0, int var1, int var2, float[] var3, float var4, float var5);

    public static boolean sliderScalarN(String string, int n, int n2, ImFloat imFloat, float f, float f2, String string2) {
        return ImGui.nSliderScalarN(string, n, n2, imFloat.getData(), f, f2, string2, 0);
    }

    public static boolean sliderScalarN(String string, int n, int n2, ImFloat imFloat, float f, float f2, String string2, int n3) {
        return ImGui.nSliderScalarN(string, n, n2, imFloat.getData(), f, f2, string2, n3);
    }

    private static native boolean nSliderScalarN(String var0, int var1, int var2, float[] var3, float var4, float var5, String var6, int var7);

    public static boolean sliderScalarN(String string, int n, int n2, ImLong imLong, long l, long l2) {
        return ImGui.nSliderScalarN(string, n, n2, imLong.getData(), l, l2);
    }

    private static native boolean nSliderScalarN(String var0, int var1, int var2, long[] var3, long var4, long var6);

    public static boolean sliderScalarN(String string, int n, int n2, ImLong imLong, long l, long l2, String string2) {
        return ImGui.nSliderScalarN(string, n, n2, imLong.getData(), l, l2, string2, 0);
    }

    public static boolean sliderScalarN(String string, int n, int n2, ImLong imLong, long l, long l2, String string2, int n3) {
        return ImGui.nSliderScalarN(string, n, n2, imLong.getData(), l, l2, string2, n3);
    }

    private static native boolean nSliderScalarN(String var0, int var1, int var2, long[] var3, long var4, long var6, String var8, int var9);

    public static boolean sliderScalarN(String string, int n, int n2, ImDouble imDouble, double d, double d2) {
        return ImGui.nSliderScalarN(string, n, n2, imDouble.getData(), d, d2);
    }

    private static native boolean nSliderScalarN(String var0, int var1, int var2, double[] var3, double var4, double var6);

    public static boolean sliderScalarN(String string, int n, int n2, ImDouble imDouble, double d, double d2, String string2) {
        return ImGui.nSliderScalarN(string, n, n2, imDouble.getData(), d, d2, string2, 0);
    }

    public static boolean sliderScalarN(String string, int n, int n2, ImDouble imDouble, double d, double d2, String string2, int n3) {
        return ImGui.nSliderScalarN(string, n, n2, imDouble.getData(), d, d2, string2, n3);
    }

    private static native boolean nSliderScalarN(String var0, int var1, int var2, double[] var3, double var4, double var6, String var8, int var9);

    public static boolean sliderScalarN(String string, int n, int n2, ImShort imShort, short s, short s2) {
        return ImGui.nSliderScalarN(string, n, n2, imShort.getData(), s, s2);
    }

    private static native boolean nSliderScalarN(String var0, int var1, int var2, short[] var3, short var4, short var5);

    public static boolean sliderScalarN(String string, int n, int n2, ImShort imShort, short s, short s2, String string2) {
        return ImGui.nSliderScalarN(string, n, n2, imShort.getData(), s, s2, string2, 0);
    }

    public static boolean sliderScalarN(String string, int n, int n2, ImShort imShort, short s, short s2, String string2, int n3) {
        return ImGui.nSliderScalarN(string, n, n2, imShort.getData(), s, s2, string2, n3);
    }

    private static native boolean nSliderScalarN(String var0, int var1, int var2, short[] var3, short var4, short var5, String var6, int var7);

    public static native boolean vSliderFloat(String var0, float var1, float var2, float[] var3, float var4, float var5);

    public static native boolean vSliderFloat(String var0, float var1, float var2, float[] var3, float var4, float var5, String var6);

    public static native boolean vSliderFloat(String var0, float var1, float var2, float[] var3, float var4, float var5, String var6, int var7);

    public static native boolean vSliderInt(String var0, float var1, float var2, int[] var3, int var4, int var5);

    public static native boolean vSliderInt(String var0, float var1, float var2, int[] var3, int var4, int var5, String var6);

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImInt imInt, int n2, int n3) {
        return ImGui.nVSliderScalar(string, f, f2, n, imInt.getData(), n2, n3);
    }

    private static native boolean nVSliderScalar(String var0, float var1, float var2, int var3, int[] var4, int var5, int var6);

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImInt imInt, int n2, int n3, String string2) {
        return ImGui.nVSliderScalar(string, f, f2, n, imInt.getData(), n2, n3, string2, 0);
    }

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImInt imInt, int n2, int n3, String string2, int n4) {
        return ImGui.nVSliderScalar(string, f, f2, n, imInt.getData(), n2, n3, string2, n4);
    }

    private static native boolean nVSliderScalar(String var0, float var1, float var2, int var3, int[] var4, int var5, int var6, String var7, int var8);

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImFloat imFloat, float f3, float f4) {
        return ImGui.nVSliderScalar(string, f, f2, n, imFloat.getData(), f3, f4);
    }

    private static native boolean nVSliderScalar(String var0, float var1, float var2, int var3, float[] var4, float var5, float var6);

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImFloat imFloat, float f3, float f4, String string2) {
        return ImGui.nVSliderScalar(string, f, f2, n, imFloat.getData(), f3, f4, string2, 0);
    }

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImFloat imFloat, float f3, float f4, String string2, int n2) {
        return ImGui.nVSliderScalar(string, f, f2, n, imFloat.getData(), f3, f4, string2, n2);
    }

    private static native boolean nVSliderScalar(String var0, float var1, float var2, int var3, float[] var4, float var5, float var6, String var7, int var8);

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImLong imLong, long l, long l2) {
        return ImGui.nVSliderScalar(string, f, f2, n, imLong.getData(), l, l2);
    }

    private static native boolean nVSliderScalar(String var0, float var1, float var2, int var3, long[] var4, long var5, long var7);

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImLong imLong, long l, long l2, String string2) {
        return ImGui.nVSliderScalar(string, f, f2, n, imLong.getData(), l, l2, string2, 0);
    }

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImLong imLong, long l, long l2, String string2, int n2) {
        return ImGui.nVSliderScalar(string, f, f2, n, imLong.getData(), l, l2, string2, n2);
    }

    private static native boolean nVSliderScalar(String var0, float var1, float var2, int var3, long[] var4, long var5, long var7, String var9, int var10);

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImDouble imDouble, double d, double d2) {
        return ImGui.nVSliderScalar(string, f, f2, n, imDouble.getData(), d, d2);
    }

    private static native boolean nVSliderScalar(String var0, float var1, float var2, int var3, double[] var4, double var5, double var7);

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImDouble imDouble, double d, double d2, String string2) {
        return ImGui.nVSliderScalar(string, f, f2, n, imDouble.getData(), d, d2, string2, 0);
    }

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImDouble imDouble, double d, double d2, String string2, int n2) {
        return ImGui.nVSliderScalar(string, f, f2, n, imDouble.getData(), d, d2, string2, n2);
    }

    private static native boolean nVSliderScalar(String var0, float var1, float var2, int var3, double[] var4, double var5, double var7, String var9, int var10);

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImShort imShort, short s, short s2) {
        return ImGui.nVSliderScalar(string, f, f2, n, imShort.getData(), s, s2);
    }

    private static native boolean nVSliderScalar(String var0, float var1, float var2, int var3, short[] var4, short var5, short var6);

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImShort imShort, short s, short s2, String string2) {
        return ImGui.nVSliderScalar(string, f, f2, n, imShort.getData(), s, s2, string2, 0);
    }

    public static boolean vSliderScalar(String string, float f, float f2, int n, ImShort imShort, short s, short s2, String string2, int n2) {
        return ImGui.nVSliderScalar(string, f, f2, n, imShort.getData(), s, s2, string2, n2);
    }

    private static native boolean nVSliderScalar(String var0, float var1, float var2, int var3, short[] var4, short var5, short var6, String var7, int var8);

    private static native void nInitInputTextData();

    public static boolean inputText(String string, ImString imString) {
        return ImGui.preInputText(false, string, null, imString);
    }

    public static boolean inputText(String string, ImString imString, int n) {
        return ImGui.preInputText(false, string, null, imString, 0.0f, 0.0f, n);
    }

    public static boolean inputText(String string, ImString imString, int n, ImGuiInputTextCallback imGuiInputTextCallback) {
        return ImGui.preInputText(false, string, null, imString, 0.0f, 0.0f, n, imGuiInputTextCallback);
    }

    public static boolean inputTextMultiline(String string, ImString imString) {
        return ImGui.preInputText(true, string, null, imString);
    }

    public static boolean inputTextMultiline(String string, ImString imString, float f, float f2) {
        return ImGui.preInputText(true, string, null, imString, f, f2);
    }

    public static boolean inputTextMultiline(String string, ImString imString, int n) {
        return ImGui.preInputText(true, string, null, imString, 0.0f, 0.0f, n);
    }

    public static boolean inputTextMultiline(String string, ImString imString, int n, ImGuiInputTextCallback imGuiInputTextCallback) {
        return ImGui.preInputText(true, string, null, imString, 0.0f, 0.0f, n, imGuiInputTextCallback);
    }

    public static boolean inputTextMultiline(String string, ImString imString, float f, float f2, int n) {
        return ImGui.preInputText(true, string, null, imString, f, f2, n);
    }

    public static boolean inputTextMultiline(String string, ImString imString, float f, float f2, int n, ImGuiInputTextCallback imGuiInputTextCallback) {
        return ImGui.preInputText(true, string, null, imString, f, f2, n, imGuiInputTextCallback);
    }

    public static boolean inputTextWithHint(String string, String string2, ImString imString) {
        return ImGui.preInputText(false, string, string2, imString);
    }

    public static boolean inputTextWithHint(String string, String string2, ImString imString, int n) {
        return ImGui.preInputText(false, string, string2, imString, 0.0f, 0.0f, n);
    }

    public static boolean inputTextWithHint(String string, String string2, ImString imString, int n, ImGuiInputTextCallback imGuiInputTextCallback) {
        return ImGui.preInputText(false, string, string2, imString, 0.0f, 0.0f, n, imGuiInputTextCallback);
    }

    private static boolean preInputText(boolean bl, String string, String string2, ImString imString) {
        return ImGui.preInputText(bl, string, string2, imString, 0.0f, 0.0f);
    }

    private static boolean preInputText(boolean bl, String string, String string2, ImString imString, float f, float f2) {
        return ImGui.preInputText(bl, string, string2, imString, f, f2, 0);
    }

    private static boolean preInputText(boolean bl, String string, String string2, ImString imString, float f, float f2, int n) {
        return ImGui.preInputText(bl, string, string2, imString, f, f2, n, null);
    }

    private static boolean preInputText(boolean bl, String string, String string2, ImString imString, float f, float f2, int n, ImGuiInputTextCallback imGuiInputTextCallback) {
        String string3;
        ImString.InputData inputData = imString.inputData;
        if (inputData.isResizable) {
            n |= 0x40000;
        }
        if (!inputData.allowedChars.isEmpty()) {
            n |= 0x200;
        }
        if ((string3 = string2) == null) {
            string3 = "";
        }
        return ImGui.nInputText(bl, string2 != null, string, string3, imString, imString.getData(), imString.getData().length, f, f2, n, inputData, inputData.allowedChars, imGuiInputTextCallback);
    }

    private static native boolean nInputText(boolean var0, boolean var1, String var2, String var3, ImString var4, byte[] var5, int var6, float var7, float var8, int var9, ImString.InputData var10, String var11, ImGuiInputTextCallback var12);

    public static boolean inputFloat(String string, ImFloat imFloat) {
        return ImGui.nInputFloat(string, imFloat.getData(), 0.0f, 0.0f, "%.3f", 0);
    }

    public static boolean inputFloat(String string, ImFloat imFloat, float f) {
        return ImGui.nInputFloat(string, imFloat.getData(), f, 0.0f, "%.3f", 0);
    }

    public static boolean inputFloat(String string, ImFloat imFloat, float f, float f2) {
        return ImGui.nInputFloat(string, imFloat.getData(), f, f2, "%.3f", 0);
    }

    public static boolean inputFloat(String string, ImFloat imFloat, float f, float f2, String string2) {
        return ImGui.nInputFloat(string, imFloat.getData(), f, f2, string2, 0);
    }

    public static boolean inputFloat(String string, ImFloat imFloat, float f, float f2, String string2, int n) {
        return ImGui.nInputFloat(string, imFloat.getData(), f, f2, string2, n);
    }

    private static native boolean nInputFloat(String var0, float[] var1, float var2, float var3, String var4, int var5);

    public static native boolean inputFloat2(String var0, float[] var1);

    public static native boolean inputFloat2(String var0, float[] var1, String var2);

    public static native boolean inputFloat2(String var0, float[] var1, String var2, int var3);

    public static native boolean inputFloat3(String var0, float[] var1);

    public static native boolean inputFloat3(String var0, float[] var1, String var2);

    public static native boolean inputFloat3(String var0, float[] var1, String var2, int var3);

    public static native boolean inputFloat4(String var0, float[] var1);

    public static native boolean inputFloat4(String var0, float[] var1, String var2);

    public static native boolean inputFloat4(String var0, float[] var1, String var2, int var3);

    public static boolean inputInt(String string, ImInt imInt) {
        return ImGui.nInputInt(string, imInt.getData(), 1, 100, 0);
    }

    public static boolean inputInt(String string, ImInt imInt, int n) {
        return ImGui.nInputInt(string, imInt.getData(), n, 100, 0);
    }

    public static boolean inputInt(String string, ImInt imInt, int n, int n2) {
        return ImGui.nInputInt(string, imInt.getData(), n, n2, 0);
    }

    public static boolean inputInt(String string, ImInt imInt, int n, int n2, int n3) {
        return ImGui.nInputInt(string, imInt.getData(), n, n2, n3);
    }

    private static native boolean nInputInt(String var0, int[] var1, int var2, int var3, int var4);

    public static native boolean inputInt2(String var0, int[] var1);

    public static native boolean inputInt2(String var0, int[] var1, int var2);

    public static native boolean inputInt3(String var0, int[] var1);

    public static native boolean inputInt3(String var0, int[] var1, int var2);

    public static native boolean inputInt4(String var0, int[] var1);

    public static native boolean inputInt4(String var0, int[] var1, int var2);

    public static boolean inputDouble(String string, ImDouble imDouble) {
        return ImGui.nInputDouble(string, imDouble.getData(), 0.0, 0.0, "%.6f", 0);
    }

    public static boolean inputDouble(String string, ImDouble imDouble, double d) {
        return ImGui.nInputDouble(string, imDouble.getData(), d, 0.0, "%.6f", 0);
    }

    public static boolean inputDouble(String string, ImDouble imDouble, double d, double d2) {
        return ImGui.nInputDouble(string, imDouble.getData(), d, d2, "%.6f", 0);
    }

    public static boolean inputDouble(String string, ImDouble imDouble, double d, double d2, String string2) {
        return ImGui.nInputDouble(string, imDouble.getData(), d, d2, string2, 0);
    }

    public static boolean inputDouble(String string, ImDouble imDouble, double d, double d2, String string2, int n) {
        return ImGui.nInputDouble(string, imDouble.getData(), d, d2, string2, n);
    }

    private static native boolean nInputDouble(String var0, double[] var1, double var2, double var4, String var6, int var7);

    public static boolean inputScalar(String string, int n, ImInt imInt) {
        return ImGui.nInputScalar(string, n, imInt.getData());
    }

    private static native boolean nInputScalar(String var0, int var1, int[] var2);

    public static boolean inputScalar(String string, int n, ImInt imInt, int n2) {
        return ImGui.nInputScalar(string, n, imInt.getData(), n2);
    }

    private static native boolean nInputScalar(String var0, int var1, int[] var2, int var3);

    public static boolean inputScalar(String string, int n, ImInt imInt, int n2, int n3) {
        return ImGui.nInputScalar(string, n, imInt.getData(), n2, n3);
    }

    private static native boolean nInputScalar(String var0, int var1, int[] var2, int var3, int var4);

    public static boolean inputScalar(String string, int n, ImInt imInt, int n2, int n3, String string2) {
        return ImGui.nInputScalar(string, n, imInt.getData(), n2, n3, string2);
    }

    private static native boolean nInputScalar(String var0, int var1, int[] var2, int var3, int var4, String var5);

    public static boolean inputScalar(String string, int n, ImInt imInt, int n2, int n3, String string2, int n4) {
        return ImGui.nInputScalar(string, n, imInt.getData(), n2, n3, string2, n4);
    }

    private static native boolean nInputScalar(String var0, int var1, int[] var2, int var3, int var4, String var5, int var6);

    public static boolean inputScalar(String string, int n, ImFloat imFloat) {
        return ImGui.nInputScalar(string, n, imFloat.getData());
    }

    private static native boolean nInputScalar(String var0, int var1, float[] var2);

    public static boolean inputScalar(String string, int n, ImFloat imFloat, float f) {
        return ImGui.nInputScalar(string, n, imFloat.getData(), f);
    }

    private static native boolean nInputScalar(String var0, int var1, float[] var2, float var3);

    public static boolean inputScalar(String string, int n, ImFloat imFloat, float f, float f2) {
        return ImGui.nInputScalar(string, n, imFloat.getData(), f, f2);
    }

    private static native boolean nInputScalar(String var0, int var1, float[] var2, float var3, float var4);

    public static boolean inputScalar(String string, int n, ImFloat imFloat, float f, float f2, String string2) {
        return ImGui.nInputScalar(string, n, imFloat.getData(), f, f2, string2);
    }

    private static native boolean nInputScalar(String var0, int var1, float[] var2, float var3, float var4, String var5);

    public static boolean inputScalar(String string, int n, ImFloat imFloat, float f, float f2, String string2, int n2) {
        return ImGui.nInputScalar(string, n, imFloat.getData(), f, f2, string2, n2);
    }

    private static native boolean nInputScalar(String var0, int var1, float[] var2, float var3, float var4, String var5, int var6);

    public static boolean inputScalar(String string, int n, ImLong imLong) {
        return ImGui.nInputScalar(string, n, imLong.getData());
    }

    private static native boolean nInputScalar(String var0, int var1, long[] var2);

    public static boolean inputScalar(String string, int n, ImLong imLong, long l) {
        return ImGui.nInputScalar(string, n, imLong.getData(), l);
    }

    private static native boolean nInputScalar(String var0, int var1, long[] var2, long var3);

    public static boolean inputScalar(String string, int n, ImLong imLong, long l, long l2) {
        return ImGui.nInputScalar(string, n, imLong.getData(), l, l2);
    }

    private static native boolean nInputScalar(String var0, int var1, long[] var2, long var3, long var5);

    public static boolean inputScalar(String string, int n, ImLong imLong, long l, long l2, String string2) {
        return ImGui.nInputScalar(string, n, imLong.getData(), l, l2, string2);
    }

    private static native boolean nInputScalar(String var0, int var1, long[] var2, long var3, long var5, String var7);

    public static boolean inputScalar(String string, int n, ImLong imLong, long l, long l2, String string2, int n2) {
        return ImGui.nInputScalar(string, n, imLong.getData(), l, l2, string2, n2);
    }

    private static native boolean nInputScalar(String var0, int var1, long[] var2, long var3, long var5, String var7, int var8);

    public static boolean inputScalar(String string, int n, ImDouble imDouble) {
        return ImGui.nInputScalar(string, n, imDouble.getData());
    }

    private static native boolean nInputScalar(String var0, int var1, double[] var2);

    public static boolean inputScalar(String string, int n, ImDouble imDouble, double d) {
        return ImGui.nInputScalar(string, n, imDouble.getData(), d);
    }

    private static native boolean nInputScalar(String var0, int var1, double[] var2, double var3);

    public static boolean inputScalar(String string, int n, ImDouble imDouble, double d, double d2) {
        return ImGui.nInputScalar(string, n, imDouble.getData(), d, d2);
    }

    private static native boolean nInputScalar(String var0, int var1, double[] var2, double var3, double var5);

    public static boolean inputScalar(String string, int n, ImDouble imDouble, double d, double d2, String string2) {
        return ImGui.nInputScalar(string, n, imDouble.getData(), d, d2, string2);
    }

    private static native boolean nInputScalar(String var0, int var1, double[] var2, double var3, double var5, String var7);

    public static boolean inputScalar(String string, int n, ImDouble imDouble, double d, double d2, String string2, int n2) {
        return ImGui.nInputScalar(string, n, imDouble.getData(), d, d2, string2, n2);
    }

    private static native boolean nInputScalar(String var0, int var1, double[] var2, double var3, double var5, String var7, int var8);

    public static boolean inputScalar(String string, int n, ImShort imShort) {
        return ImGui.nInputScalar(string, n, imShort.getData());
    }

    private static native boolean nInputScalar(String var0, int var1, short[] var2);

    public static boolean inputScalar(String string, int n, ImShort imShort, short s) {
        return ImGui.nInputScalar(string, n, imShort.getData(), s);
    }

    private static native boolean nInputScalar(String var0, int var1, short[] var2, short var3);

    public static boolean inputScalar(String string, int n, ImShort imShort, short s, short s2) {
        return ImGui.nInputScalar(string, n, imShort.getData(), s, s2);
    }

    private static native boolean nInputScalar(String var0, int var1, short[] var2, short var3, short var4);

    public static boolean inputScalar(String string, int n, ImShort imShort, short s, short s2, String string2) {
        return ImGui.nInputScalar(string, n, imShort.getData(), s, s2, string2);
    }

    private static native boolean nInputScalar(String var0, int var1, short[] var2, short var3, short var4, String var5);

    public static boolean inputScalar(String string, int n, ImShort imShort, short s, short s2, String string2, int n2) {
        return ImGui.nInputScalar(string, n, imShort.getData(), s, s2, string2, n2);
    }

    private static native boolean nInputScalar(String var0, int var1, short[] var2, short var3, short var4, String var5, int var6);

    public static boolean inputScalarN(String string, int n, ImInt imInt, int n2) {
        return ImGui.nInputScalarN(string, n, imInt.getData(), n2);
    }

    private static native boolean nInputScalarN(String var0, int var1, int[] var2, int var3);

    public static boolean inputScalarN(String string, int n, ImInt imInt, int n2, int n3) {
        return ImGui.nInputScalarN(string, n, imInt.getData(), n2, n3);
    }

    private static native boolean nInputScalarN(String var0, int var1, int[] var2, int var3, int var4);

    public static boolean inputScalarN(String string, int n, ImInt imInt, int n2, int n3, int n4) {
        return ImGui.nInputScalarN(string, n, imInt.getData(), n2, n3, n4);
    }

    private static native boolean nInputScalarN(String var0, int var1, int[] var2, int var3, int var4, int var5);

    public static boolean inputScalarN(String string, int n, ImInt imInt, int n2, int n3, int n4, String string2) {
        return ImGui.nInputScalarN(string, n, imInt.getData(), n2, n3, n4, string2);
    }

    private static native boolean nInputScalarN(String var0, int var1, int[] var2, int var3, int var4, int var5, String var6);

    public static boolean inputScalarN(String string, int n, ImInt imInt, int n2, int n3, int n4, String string2, int n5) {
        return ImGui.nInputScalarN(string, n, imInt.getData(), n2, n3, n4, string2, n5);
    }

    private static native boolean nInputScalarN(String var0, int var1, int[] var2, int var3, int var4, int var5, String var6, int var7);

    public static boolean inputScalarN(String string, int n, ImFloat imFloat, int n2) {
        return ImGui.nInputScalarN(string, n, imFloat.getData(), n2);
    }

    private static native boolean nInputScalarN(String var0, int var1, float[] var2, int var3);

    public static boolean inputScalarN(String string, int n, ImFloat imFloat, int n2, float f) {
        return ImGui.nInputScalarN(string, n, imFloat.getData(), n2, f);
    }

    private static native boolean nInputScalarN(String var0, int var1, float[] var2, int var3, float var4);

    public static boolean inputScalarN(String string, int n, ImFloat imFloat, int n2, float f, float f2) {
        return ImGui.nInputScalarN(string, n, imFloat.getData(), n2, f, f2);
    }

    private static native boolean nInputScalarN(String var0, int var1, float[] var2, int var3, float var4, float var5);

    public static boolean inputScalarN(String string, int n, ImFloat imFloat, int n2, float f, float f2, String string2) {
        return ImGui.nInputScalarN(string, n, imFloat.getData(), n2, f, f2, string2);
    }

    private static native boolean nInputScalarN(String var0, int var1, float[] var2, int var3, float var4, float var5, String var6);

    public static boolean inputScalarN(String string, int n, ImFloat imFloat, int n2, float f, float f2, String string2, int n3) {
        return ImGui.nInputScalarN(string, n, imFloat.getData(), n2, f, f2, string2, n3);
    }

    private static native boolean nInputScalarN(String var0, int var1, float[] var2, int var3, float var4, float var5, String var6, int var7);

    public static boolean inputScalarN(String string, int n, ImLong imLong, int n2) {
        return ImGui.nInputScalarN(string, n, imLong.getData(), n2);
    }

    private static native boolean nInputScalarN(String var0, int var1, long[] var2, int var3);

    public static boolean inputScalarN(String string, int n, ImLong imLong, int n2, long l) {
        return ImGui.nInputScalarN(string, n, imLong.getData(), n2, l);
    }

    private static native boolean nInputScalarN(String var0, int var1, long[] var2, int var3, long var4);

    public static boolean inputScalarN(String string, int n, ImLong imLong, int n2, long l, long l2) {
        return ImGui.nInputScalarN(string, n, imLong.getData(), n2, l, l2);
    }

    private static native boolean nInputScalarN(String var0, int var1, long[] var2, int var3, long var4, long var6);

    public static boolean inputScalarN(String string, int n, ImLong imLong, int n2, long l, long l2, String string2) {
        return ImGui.nInputScalarN(string, n, imLong.getData(), n2, l, l2, string2);
    }

    private static native boolean nInputScalarN(String var0, int var1, long[] var2, int var3, long var4, long var6, String var8);

    public static boolean inputScalarN(String string, int n, ImLong imLong, int n2, long l, long l2, String string2, int n3) {
        return ImGui.nInputScalarN(string, n, imLong.getData(), n2, l, l2, string2, n3);
    }

    private static native boolean nInputScalarN(String var0, int var1, long[] var2, int var3, long var4, long var6, String var8, int var9);

    public static boolean inputScalarN(String string, int n, ImDouble imDouble, int n2) {
        return ImGui.nInputScalarN(string, n, imDouble.getData(), n2);
    }

    private static native boolean nInputScalarN(String var0, int var1, double[] var2, int var3);

    public static boolean inputScalarN(String string, int n, ImDouble imDouble, int n2, double d) {
        return ImGui.nInputScalarN(string, n, imDouble.getData(), n2, d);
    }

    private static native boolean nInputScalarN(String var0, int var1, double[] var2, int var3, double var4);

    public static boolean inputScalarN(String string, int n, ImDouble imDouble, int n2, double d, double d2) {
        return ImGui.nInputScalarN(string, n, imDouble.getData(), n2, d, d2);
    }

    private static native boolean nInputScalarN(String var0, int var1, double[] var2, int var3, double var4, double var6);

    public static boolean inputScalarN(String string, int n, ImDouble imDouble, int n2, double d, double d2, String string2) {
        return ImGui.nInputScalarN(string, n, imDouble.getData(), n2, d, d2, string2);
    }

    private static native boolean nInputScalarN(String var0, int var1, double[] var2, int var3, double var4, double var6, String var8);

    public static boolean inputScalarN(String string, int n, ImDouble imDouble, int n2, double d, double d2, String string2, int n3) {
        return ImGui.nInputScalarN(string, n, imDouble.getData(), n2, d, d2, string2, n3);
    }

    private static native boolean nInputScalarN(String var0, int var1, double[] var2, int var3, double var4, double var6, String var8, int var9);

    public static boolean inputScalarN(String string, int n, ImShort imShort, int n2) {
        return ImGui.nInputScalarN(string, n, imShort.getData(), n2);
    }

    private static native boolean nInputScalarN(String var0, int var1, short[] var2, int var3);

    public static boolean inputScalarN(String string, int n, ImShort imShort, int n2, short s) {
        return ImGui.nInputScalarN(string, n, imShort.getData(), n2, s);
    }

    private static native boolean nInputScalarN(String var0, int var1, short[] var2, int var3, short var4);

    public static boolean inputScalarN(String string, int n, ImShort imShort, int n2, short s, short s2) {
        return ImGui.nInputScalarN(string, n, imShort.getData(), n2, s, s2);
    }

    private static native boolean nInputScalarN(String var0, int var1, short[] var2, int var3, short var4, short var5);

    public static boolean inputScalarN(String string, int n, ImShort imShort, int n2, short s, short s2, String string2) {
        return ImGui.nInputScalarN(string, n, imShort.getData(), n2, s, s2, string2);
    }

    private static native boolean nInputScalarN(String var0, int var1, short[] var2, int var3, short var4, short var5, String var6);

    public static boolean inputScalarN(String string, int n, ImShort imShort, int n2, short s, short s2, String string2, int n3) {
        return ImGui.nInputScalarN(string, n, imShort.getData(), n2, s, s2, string2, n3);
    }

    private static native boolean nInputScalarN(String var0, int var1, short[] var2, int var3, short var4, short var5, String var6, int var7);

    public static native boolean colorEdit3(String var0, float[] var1);

    public static native boolean colorEdit3(String var0, float[] var1, int var2);

    public static native boolean colorEdit4(String var0, float[] var1);

    public static native boolean colorEdit4(String var0, float[] var1, int var2);

    public static native boolean colorPicker3(String var0, float[] var1);

    public static native boolean colorPicker3(String var0, float[] var1, int var2);

    public static native boolean colorPicker4(String var0, float[] var1);

    public static native boolean colorPicker4(String var0, float[] var1, int var2);

    public static native boolean colorPicker4(String var0, float[] var1, int var2, float var3);

    public static native boolean colorButton(String var0, float[] var1);

    public static native boolean colorButton(String var0, float[] var1, int var2);

    public static native boolean colorButton(String var0, float[] var1, int var2, float var3, float var4);

    public static native void setColorEditOptions(int var0);

    public static native boolean treeNode(String var0);

    public static native boolean treeNode(String var0, String var1);

    public static native boolean treeNode(long var0, String var2);

    public static native boolean treeNodeEx(String var0);

    public static native boolean treeNodeEx(String var0, int var1);

    public static native boolean treeNodeEx(String var0, int var1, String var2);

    public static native boolean treeNodeEx(long var0, int var2, String var3);

    public static native void treePush();

    public static native void treePush(String var0);

    public static native void treePush(long var0);

    public static native void treePop();

    public static native float getTreeNodeToLabelSpacing();

    public static native boolean collapsingHeader(String var0);

    public static native boolean collapsingHeader(String var0, int var1);

    public static boolean collapsingHeader(String string, ImBoolean imBoolean) {
        return ImGui.nCollapsingHeader(string, imBoolean.getData(), 0);
    }

    public static boolean collapsingHeader(String string, ImBoolean imBoolean, int n) {
        return ImGui.nCollapsingHeader(string, imBoolean.getData(), n);
    }

    private static native boolean nCollapsingHeader(String var0, boolean[] var1, int var2);

    public static native void setNextItemOpen(boolean var0);

    public static native void setNextItemOpen(boolean var0, int var1);

    public static native boolean selectable(String var0);

    public static native boolean selectable(String var0, boolean var1);

    public static native boolean selectable(String var0, boolean var1, int var2);

    public static native boolean selectable(String var0, boolean var1, int var2, float var3, float var4);

    public static boolean selectable(String string, ImBoolean imBoolean) {
        return ImGui.nSelectable(string, imBoolean.getData(), 0, 0.0f, 0.0f);
    }

    public static boolean selectable(String string, ImBoolean imBoolean, int n) {
        return ImGui.nSelectable(string, imBoolean.getData(), n, 0.0f, 0.0f);
    }

    public static boolean selectable(String string, ImBoolean imBoolean, int n, float f, float f2) {
        return ImGui.nSelectable(string, imBoolean.getData(), n, f, f2);
    }

    private static native boolean nSelectable(String var0, boolean[] var1, int var2, float var3, float var4);

    public static native boolean beginListBox(String var0);

    public static native boolean beginListBox(String var0, float var1, float var2);

    public static native void endListBox();

    public static void listBox(String string, ImInt imInt, String[] stringArray) {
        ImGui.nListBox(string, imInt.getData(), stringArray, stringArray.length, -1);
    }

    public static void listBox(String string, ImInt imInt, String[] stringArray, int n) {
        ImGui.nListBox(string, imInt.getData(), stringArray, stringArray.length, n);
    }

    private static native boolean nListBox(String var0, int[] var1, String[] var2, int var3, int var4);

    public static native void plotLines(String var0, float[] var1, int var2);

    public static native void plotLines(String var0, float[] var1, int var2, int var3);

    public static native void plotLines(String var0, float[] var1, int var2, int var3, String var4);

    public static native void plotLines(String var0, float[] var1, int var2, int var3, String var4, float var5);

    public static native void plotLines(String var0, float[] var1, int var2, int var3, String var4, float var5, float var6);

    public static native void plotLines(String var0, float[] var1, int var2, int var3, String var4, float var5, float var6, float var7, float var8);

    public static native void plotLines(String var0, float[] var1, int var2, int var3, String var4, float var5, float var6, float var7, float var8, int var9);

    public static native void plotHistogram(String var0, float[] var1, int var2);

    public static native void plotHistogram(String var0, float[] var1, int var2, int var3);

    public static native void plotHistogram(String var0, float[] var1, int var2, int var3, String var4);

    public static native void plotHistogram(String var0, float[] var1, int var2, int var3, String var4, float var5);

    public static native void plotHistogram(String var0, float[] var1, int var2, int var3, String var4, float var5, float var6);

    public static native void plotHistogram(String var0, float[] var1, int var2, int var3, String var4, float var5, float var6, float var7, float var8);

    public static native void plotHistogram(String var0, float[] var1, int var2, int var3, String var4, float var5, float var6, float var7, float var8, int var9);

    public static native void value(String var0, boolean var1);

    public static native void value(String var0, int var1);

    public static native void value(String var0, long var1);

    public static native void value(String var0, float var1);

    public static native void value(String var0, float var1, String var2);

    public static native boolean beginMenuBar();

    public static native void endMenuBar();

    public static native boolean beginMainMenuBar();

    public static native void endMainMenuBar();

    public static native boolean beginMenu(String var0);

    public static native boolean beginMenu(String var0, boolean var1);

    public static native void endMenu();

    public static native boolean menuItem(String var0);

    public static boolean menuItem(String string, String string2) {
        return ImGui.nMenuItem(string, string2, false, true);
    }

    public static boolean menuItem(String string, String string2, boolean bl) {
        return ImGui.nMenuItem(string, string2, bl, true);
    }

    public static boolean menuItem(String string, String string2, boolean bl, boolean bl2) {
        return ImGui.nMenuItem(string, string2, bl, bl2);
    }

    public static boolean menuItem(String string, String string2, ImBoolean imBoolean) {
        return ImGui.nMenuItem(string, string2, imBoolean.getData(), true);
    }

    public static boolean menuItem(String string, String string2, ImBoolean imBoolean, boolean bl) {
        return ImGui.nMenuItem(string, string2, imBoolean.getData(), bl);
    }

    private static native boolean nMenuItem(String var0, String var1, boolean var2, boolean var3);

    private static native boolean nMenuItem(String var0, String var1, boolean[] var2, boolean var3);

    public static native void beginTooltip();

    public static native void endTooltip();

    public static native void setTooltip(String var0);

    public static native boolean beginPopup(String var0);

    public static native boolean beginPopup(String var0, int var1);

    public static native boolean beginPopupModal(String var0);

    public static boolean beginPopupModal(String string, ImBoolean imBoolean) {
        return ImGui.nBeginPopupModal(string, imBoolean.getData(), 0);
    }

    public static boolean beginPopupModal(String string, int n) {
        return ImGui.nBeginPopupModal(string, n);
    }

    public static boolean beginPopupModal(String string, ImBoolean imBoolean, int n) {
        return ImGui.nBeginPopupModal(string, imBoolean.getData(), n);
    }

    private static native boolean nBeginPopupModal(String var0, int var1);

    private static native boolean nBeginPopupModal(String var0, boolean[] var1, int var2);

    public static native void endPopup();

    public static native void openPopup(String var0);

    public static native void openPopup(String var0, int var1);

    public static native void openPopupOnItemClick();

    public static native void openPopupOnItemClick(String var0);

    public static native void openPopupOnItemClick(int var0);

    public static native void openPopupOnItemClick(String var0, int var1);

    public static native void closeCurrentPopup();

    public static native boolean beginPopupContextItem();

    public static native boolean beginPopupContextItem(String var0);

    public static native boolean beginPopupContextItem(int var0);

    public static native boolean beginPopupContextItem(String var0, int var1);

    public static native boolean beginPopupContextWindow();

    public static native boolean beginPopupContextWindow(String var0);

    public static native boolean beginPopupContextWindow(int var0);

    public static native boolean beginPopupContextWindow(String var0, int var1);

    public static native boolean beginPopupContextVoid();

    public static native boolean beginPopupContextVoid(String var0);

    public static native boolean beginPopupContextVoid(int var0);

    public static native boolean beginPopupContextVoid(String var0, int var1);

    public static native boolean isPopupOpen(String var0);

    public static native boolean isPopupOpen(String var0, int var1);

    public static native boolean beginTable(String var0, int var1);

    public static native boolean beginTable(String var0, int var1, int var2);

    public static native boolean beginTable(String var0, int var1, int var2, float var3, float var4);

    public static native boolean beginTable(String var0, int var1, int var2, float var3, float var4, float var5);

    public static native void endTable();

    public static native void tableNextRow();

    public static native void tableNextRow(int var0);

    public static native void tableNextRow(int var0, float var1);

    public static native boolean tableNextColumn();

    public static native boolean tableSetColumnIndex(int var0);

    public static native void tableSetupColumn(String var0);

    public static native void tableSetupColumn(String var0, int var1);

    public static native void tableSetupColumn(String var0, int var1, float var2);

    public static native void tableSetupColumn(String var0, int var1, float var2, int var3);

    public static native void tableSetupScrollFreeze(int var0, int var1);

    public static native void tableHeadersRow();

    public static native void tableHeader(String var0);

    public static native int tableGetColumnCount();

    public static native int tableGetColumnIndex();

    public static native int tableGetRowIndex();

    public static native String tableGetColumnName();

    public static native String tableGetColumnName(int var0);

    public static native int tableGetColumnFlags();

    public static native int tableGetColumnFlags(int var0);

    public static native void tableSetBgColor(int var0, int var1);

    public static native void tableSetBgColor(int var0, int var1, int var2);

    public static native void columns();

    public static native void columns(int var0);

    public static native void columns(int var0, String var1);

    public static native void columns(int var0, String var1, boolean var2);

    public static native void nextColumn();

    public static native int getColumnIndex();

    public static native float getColumnWidth();

    public static native float getColumnWidth(int var0);

    public static native void setColumnWidth(int var0, float var1);

    public static native float getColumnOffset();

    public static native float getColumnOffset(int var0);

    public static native void setColumnOffset(int var0, float var1);

    public static native int getColumnsCount();

    public static native boolean beginTabBar(String var0);

    public static native boolean beginTabBar(String var0, int var1);

    public static native void endTabBar();

    public static native boolean beginTabItem(String var0);

    public static boolean beginTabItem(String string, ImBoolean imBoolean) {
        return ImGui.nBeginTabItem(string, imBoolean.getData(), 0);
    }

    public static boolean beginTabItem(String string, int n) {
        return ImGui.nBeginTabItem(string, n);
    }

    public static boolean beginTabItem(String string, ImBoolean imBoolean, int n) {
        return ImGui.nBeginTabItem(string, imBoolean.getData(), n);
    }

    private static native boolean nBeginTabItem(String var0, int var1);

    private static native boolean nBeginTabItem(String var0, boolean[] var1, int var2);

    public static native void endTabItem();

    public static native boolean tabItemButton(String var0);

    public static native boolean tabItemButton(String var0, int var1);

    public static native void setTabItemClosed(String var0);

    public static int dockSpace(int n) {
        return ImGui.nDockSpace(n, 0.0f, 0.0f, 0, 0L);
    }

    public static int dockSpace(int n, float f, float f2) {
        return ImGui.nDockSpace(n, f, f2, 0, 0L);
    }

    public static int dockSpace(int n, float f, float f2, int n2) {
        return ImGui.nDockSpace(n, f, f2, n2, 0L);
    }

    public static int dockSpace(int n, float f, float f2, int n2, ImGuiWindowClass imGuiWindowClass) {
        return ImGui.nDockSpace(n, f, f2, n2, imGuiWindowClass.ptr);
    }

    private static native int nDockSpace(int var0, float var1, float var2, int var3, long var4);

    public static int dockSpaceOverViewport() {
        return ImGui.nDockSpaceOverViewport(0L, 0, 0L);
    }

    public static int dockSpaceOverViewport(ImGuiViewport imGuiViewport) {
        return ImGui.nDockSpaceOverViewport(imGuiViewport.ptr, 0, 0L);
    }

    public static int dockSpaceOverViewport(ImGuiViewport imGuiViewport, int n) {
        return ImGui.nDockSpaceOverViewport(imGuiViewport.ptr, n, 0L);
    }

    public static int dockSpaceOverViewport(ImGuiViewport imGuiViewport, int n, ImGuiWindowClass imGuiWindowClass) {
        return ImGui.nDockSpaceOverViewport(imGuiViewport.ptr, n, imGuiWindowClass.ptr);
    }

    private static native int nDockSpaceOverViewport(long var0, int var2, long var3);

    public static native void setNextWindowDockID(int var0);

    public static native void setNextWindowDockID(int var0, int var1);

    public static void setNextWindowClass(ImGuiWindowClass imGuiWindowClass) {
        ImGui.nSetNextWindowClass(imGuiWindowClass.ptr);
    }

    private static native void nSetNextWindowClass(long var0);

    public static native int getWindowDockID();

    public static native boolean isWindowDocked();

    public static native void logToTTY();

    public static native void logToTTY(int var0);

    public static native void logToFile();

    public static native void logToFile(int var0);

    public static native void logToFile(int var0, String var1);

    public static native void logToClipboard();

    public static native void logToClipboard(int var0);

    public static native void logFinish();

    public static native void logButtons();

    public static native void logText(String var0);

    public static native boolean beginDragDropSource();

    public static native boolean beginDragDropSource(int var0);

    public static boolean setDragDropPayload(String string, Object object) {
        return ImGui.setDragDropPayload(string, object, 0);
    }

    public static boolean setDragDropPayload(String string, Object object, int n) {
        if (payloadRef == null || payloadRef.get() != object) {
            payloadRef = new WeakReference<Object>(object);
        }
        return ImGui.nSetDragDropPayload(string, PAYLOAD_PLACEHOLDER_DATA, 1, n);
    }

    public static boolean setDragDropPayload(Object object) {
        return ImGui.setDragDropPayload(object, 0);
    }

    public static boolean setDragDropPayload(Object object, int n) {
        return ImGui.setDragDropPayload(String.valueOf(object.getClass().hashCode()), object, n);
    }

    private static native boolean nSetDragDropPayload(String var0, byte[] var1, int var2, int var3);

    public static native void endDragDropSource();

    public static native boolean beginDragDropTarget();

    public static <T> T acceptDragDropPayload(String string) {
        return ImGui.acceptDragDropPayload(string, 0);
    }

    public static <T> T acceptDragDropPayload(String string, Class<T> clazz) {
        return ImGui.acceptDragDropPayload(string, 0, clazz);
    }

    public static <T> T acceptDragDropPayload(String string, int n) {
        return ImGui.acceptDragDropPayload(string, n, null);
    }

    public static <T> T acceptDragDropPayload(String string, int n, Class<T> clazz) {
        Object t;
        if (payloadRef != null && ImGui.nAcceptDragDropPayload(string, n) && (t = payloadRef.get()) != null && (clazz == null || t.getClass().isAssignableFrom(clazz))) {
            return t;
        }
        return null;
    }

    public static <T> T acceptDragDropPayload(Class<T> clazz) {
        return ImGui.acceptDragDropPayload(String.valueOf(clazz.hashCode()), 0, clazz);
    }

    public static <T> T acceptDragDropPayload(Class<T> clazz, int n) {
        return ImGui.acceptDragDropPayload(String.valueOf(clazz.hashCode()), n, clazz);
    }

    private static native boolean nAcceptDragDropPayload(String var0, int var1);

    public static native void endDragDropTarget();

    public static <T> T getDragDropPayload() {
        Object t;
        if (payloadRef != null && ImGui.nHasDragDropPayload() && (t = payloadRef.get()) != null) {
            return t;
        }
        return null;
    }

    public static <T> T getDragDropPayload(String string) {
        Object t;
        if (payloadRef != null && ImGui.nHasDragDropPayload(string) && (t = payloadRef.get()) != null) {
            return t;
        }
        return null;
    }

    public static <T> T getDragDropPayload(Class<T> clazz) {
        return ImGui.getDragDropPayload(String.valueOf(clazz.hashCode()));
    }

    private static native boolean nHasDragDropPayload();

    private static native boolean nHasDragDropPayload(String var0);

    public static void beginDisabled() {
        ImGui.beginDisabled(true);
    }

    public static native void beginDisabled(boolean var0);

    public static native void endDisabled();

    public static native void pushClipRect(float var0, float var1, float var2, float var3, boolean var4);

    public static native void popClipRect();

    public static native void setItemDefaultFocus();

    public static native void setKeyboardFocusHere();

    public static native void setKeyboardFocusHere(int var0);

    public static native boolean isItemHovered();

    public static native boolean isItemHovered(int var0);

    public static native boolean isItemActive();

    public static native boolean isItemFocused();

    public static native boolean isItemClicked();

    public static native boolean isItemClicked(int var0);

    public static native boolean isItemVisible();

    public static native boolean isItemEdited();

    public static native boolean isItemActivated();

    public static native boolean isItemDeactivated();

    public static native boolean isItemDeactivatedAfterEdit();

    public static native boolean isItemToggledOpen();

    public static native boolean isAnyItemHovered();

    public static native boolean isAnyItemActive();

    public static native boolean isAnyItemFocused();

    public static ImVec2 getItemRectMin() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getItemRectMin(imVec2);
        return imVec2;
    }

    public static native void getItemRectMin(ImVec2 var0);

    public static native float getItemRectMinX();

    public static native float getItemRectMinY();

    public static ImVec2 getItemRectMax() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getItemRectMax(imVec2);
        return imVec2;
    }

    public static native void getItemRectMax(ImVec2 var0);

    public static native float getItemRectMaxX();

    public static native float getItemRectMaxY();

    public static ImVec2 getItemRectSize() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getItemRectSize(imVec2);
        return imVec2;
    }

    public static native void getItemRectSize(ImVec2 var0);

    public static native float getItemRectSizeX();

    public static native float getItemRectSizeY();

    public static native void setItemAllowOverlap();

    public static ImGuiViewport getMainViewport() {
        ImGui.MAIN_VIEWPORT.ptr = ImGui.nGetMainViewport();
        return MAIN_VIEWPORT;
    }

    private static native long nGetMainViewport();

    public static native boolean isRectVisible(float var0, float var1);

    public static native boolean isRectVisible(float var0, float var1, float var2, float var3);

    public static native double getTime();

    public static native int getFrameCount();

    public static ImDrawList getBackgroundDrawList() {
        ImGui.BACKGROUND_DRAW_LIST.ptr = ImGui.nGetBackgroundDrawList();
        return BACKGROUND_DRAW_LIST;
    }

    private static native long nGetBackgroundDrawList();

    public static ImDrawList getForegroundDrawList() {
        ImGui.FOREGROUND_DRAW_LIST.ptr = ImGui.nGetForegroundDrawList();
        return FOREGROUND_DRAW_LIST;
    }

    private static native long nGetForegroundDrawList();

    public static ImDrawList getBackgroundDrawList(ImGuiViewport imGuiViewport) {
        ImGui.BACKGROUND_DRAW_LIST.ptr = ImGui.nGetBackgroundDrawList(imGuiViewport.ptr);
        return BACKGROUND_DRAW_LIST;
    }

    private static native long nGetBackgroundDrawList(long var0);

    public static ImDrawList getForegroundDrawList(ImGuiViewport imGuiViewport) {
        ImGui.BACKGROUND_DRAW_LIST.ptr = ImGui.nGetForegroundDrawList(imGuiViewport.ptr);
        return BACKGROUND_DRAW_LIST;
    }

    private static native long nGetForegroundDrawList(long var0);

    public static native String getStyleColorName(int var0);

    public static void setStateStorage(ImGuiStorage imGuiStorage) {
        ImGui.nSetStateStorage(imGuiStorage.ptr);
    }

    private static native void nSetStateStorage(long var0);

    public static ImGuiStorage getStateStorage() {
        ImGui.IMGUI_STORAGE.ptr = ImGui.nGetStateStorage();
        return IMGUI_STORAGE;
    }

    private static native long nGetStateStorage();

    public static native boolean beginChildFrame(int var0, float var1, float var2);

    public static native boolean beginChildFrame(int var0, float var1, float var2, int var3);

    public static native void endChildFrame();

    public static ImVec2 calcTextSize(String string) {
        ImVec2 imVec2 = new ImVec2();
        ImGui.calcTextSize(imVec2, string);
        return imVec2;
    }

    public static ImVec2 calcTextSize(String string, boolean bl) {
        ImVec2 imVec2 = new ImVec2();
        ImGui.calcTextSize(imVec2, string, bl);
        return imVec2;
    }

    public static ImVec2 calcTextSize(String string, boolean bl, float f) {
        ImVec2 imVec2 = new ImVec2();
        ImGui.calcTextSize(imVec2, string, bl, f);
        return imVec2;
    }

    public static native void calcTextSize(ImVec2 var0, String var1);

    public static native void calcTextSize(ImVec2 var0, String var1, boolean var2);

    public static native void calcTextSize(ImVec2 var0, String var1, float var2);

    public static native void calcTextSize(ImVec2 var0, String var1, boolean var2, float var3);

    public final ImVec4 colorConvertU32ToFloat4(int n) {
        ImVec4 imVec4 = new ImVec4();
        ImGui.colorConvertU32ToFloat4(n, imVec4);
        return imVec4;
    }

    public static native void colorConvertU32ToFloat4(int var0, ImVec4 var1);

    public static native int colorConvertFloat4ToU32(float var0, float var1, float var2, float var3);

    public static native void colorConvertRGBtoHSV(float[] var0, float[] var1);

    public static native void colorConvertHSVtoRGB(float[] var0, float[] var1);

    public static native int getKeyIndex(int var0);

    public static native boolean isKeyDown(int var0);

    public static native boolean isKeyPressed(int var0);

    public static native boolean isKeyPressed(int var0, boolean var1);

    public static native boolean isKeyReleased(int var0);

    public static native boolean getKeyPressedAmount(int var0, float var1, float var2);

    public static native void captureKeyboardFromApp();

    public static native void captureKeyboardFromApp(boolean var0);

    public static native boolean isMouseDown(int var0);

    public static native boolean isAnyMouseDown();

    public static native boolean isMouseClicked(int var0);

    public static native boolean isMouseClicked(int var0, boolean var1);

    public static native boolean isMouseDoubleClicked(int var0);

    public static native int getMouseClickedCount(int var0);

    public static native boolean isMouseReleased(int var0);

    public static native boolean isMouseDragging(int var0);

    public static native boolean isMouseDragging(int var0, float var1);

    public static native boolean isMouseHoveringRect(float var0, float var1, float var2, float var3);

    public static native boolean isMouseHoveringRect(float var0, float var1, float var2, float var3, boolean var4);

    public static native boolean isMousePosValid();

    public static native boolean isMousePosValid(float var0, float var1);

    public static ImVec2 getMousePos() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getMousePos(imVec2);
        return imVec2;
    }

    public static native void getMousePos(ImVec2 var0);

    public static native float getMousePosX();

    public static native float getMousePosY();

    public static ImVec2 getMousePosOnOpeningCurrentPopup() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getMousePosOnOpeningCurrentPopup(imVec2);
        return imVec2;
    }

    public static native void getMousePosOnOpeningCurrentPopup(ImVec2 var0);

    public static native float getMousePosOnOpeningCurrentPopupX();

    public static native float getMousePosOnOpeningCurrentPopupY();

    public static ImVec2 getMouseDragDelta() {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getMouseDragDelta(imVec2);
        return imVec2;
    }

    public static native void getMouseDragDelta(ImVec2 var0);

    public static native float getMouseDragDeltaX();

    public static native float getMouseDragDeltaY();

    public static ImVec2 getMouseDragDelta(int n) {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getMouseDragDelta(imVec2, n);
        return imVec2;
    }

    public static native void getMouseDragDelta(ImVec2 var0, int var1);

    public static native float getMouseDragDeltaX(int var0);

    public static native float getMouseDragDeltaY(int var0);

    public static ImVec2 getMouseDragDelta(int n, float f) {
        ImVec2 imVec2 = new ImVec2();
        ImGui.getMouseDragDelta(imVec2, n, f);
        return imVec2;
    }

    public static native void getMouseDragDelta(ImVec2 var0, int var1, float var2);

    public static native float getMouseDragDeltaX(int var0, float var1);

    public static native float getMouseDragDeltaY(int var0, float var1);

    public static native void resetMouseDragDelta();

    public static native void resetMouseDragDelta(int var0);

    public static native int getMouseCursor();

    public static native void setMouseCursor(int var0);

    public static native void captureMouseFromApp();

    public static native void captureMouseFromApp(boolean var0);

    public static native String getClipboardText();

    public static native void setClipboardText(String var0);

    public static native void loadIniSettingsFromDisk(String var0);

    public static native void loadIniSettingsFromMemory(String var0);

    public static native void loadIniSettingsFromMemory(String var0, int var1);

    public static native void saveIniSettingsToDisk(String var0);

    public static native String saveIniSettingsToMemory();

    public static native String saveIniSettingsToMemory(long var0);

    public static ImGuiPlatformIO getPlatformIO() {
        ImGui.PLATFORM_IO.ptr = ImGui.nGetPlatformIO();
        return PLATFORM_IO;
    }

    private static native long nGetPlatformIO();

    public static native void updatePlatformWindows();

    public static native void renderPlatformWindowsDefault();

    public static native void destroyPlatformWindows();

    public static ImGuiViewport findViewportByID(int n) {
        ImGui.FIND_VIEWPORT.ptr = ImGui.nFindViewportByID(n);
        return FIND_VIEWPORT;
    }

    private static native long nFindViewportByID(int var0);

    public static ImGuiViewport findViewportByPlatformHandle(long l) {
        ImGui.FIND_VIEWPORT.ptr = ImGui.nFindViewportByPlatformHandle(l);
        return FIND_VIEWPORT;
    }

    private static native long nFindViewportByPlatformHandle(long var0);

    static {
        String string = System.getProperty(LIB_PATH_PROP);
        String string2 = System.getProperty(LIB_NAME_PROP, LIB_NAME_DEFAULT);
        String string3 = ImGui.resolveFullLibName();
        if (string != null) {
            System.load(Paths.get(string, new String[0]).resolve(string3).toAbsolutePath().toString());
        } else {
            try {
                System.loadLibrary(string2);
            } catch (Error | Exception throwable) {
                String string4 = ImGui.tryLoadFromClasspath(string3);
                if (string4 != null) {
                    System.load(string4);
                }
                throw throwable;
            }
        }
        IMGUI_CONTEXT = new ImGuiContext(0L);
        IMGUI_IO = new ImGuiIO(0L);
        WINDOW_DRAW_LIST = new ImDrawList(0L);
        BACKGROUND_DRAW_LIST = new ImDrawList(0L);
        FOREGROUND_DRAW_LIST = new ImDrawList(0L);
        IMGUI_STORAGE = new ImGuiStorage(0L);
        WINDOW_VIEWPORT = new ImGuiViewport(0L);
        FIND_VIEWPORT = new ImGuiViewport(0L);
        DRAW_DATA = new ImDrawData(0L);
        FONT = new ImFont(0L);
        STYLE = new ImGuiStyle(0L);
        MAIN_VIEWPORT = new ImGuiViewport(0L);
        PLATFORM_IO = new ImGuiPlatformIO(0L);
        ImGui.nInitJni();
        ImFontAtlas.nInit();
        ImGuiPlatformIO.init();
        ImGui.nInitInputTextData();
        ImGui.setAssertCallback(new ImAssertCallback(){

            @Override
            public void imAssertCallback(String string, int n, String string2) {
                System.err.println("Dear ImGui Assertion Failed: " + string);
                System.err.println("Assertion Located At: " + string2 + ":" + n);
                Thread.dumpStack();
            }
        });
        payloadRef = null;
        PAYLOAD_PLACEHOLDER_DATA = new byte[1];
    }
}

