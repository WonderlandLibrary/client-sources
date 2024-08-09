/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.extension.implot;

import imgui.ImDrawList;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.extension.implot.ImPlotContext;
import imgui.extension.implot.ImPlotLimits;
import imgui.extension.implot.ImPlotPoint;
import imgui.extension.implot.ImPlotStyle;
import imgui.type.ImBoolean;
import imgui.type.ImDouble;

public final class ImPlot {
    private static final ImDrawList IM_DRAW_LIST = new ImDrawList(0L);
    private static final ImPlotContext IMPLOT_CONTEXT = new ImPlotContext(0L);
    private static final ImPlotPoint IMPLOT_POINT = new ImPlotPoint(0L);
    private static final ImPlotLimits IMPLOT_LIMITS = new ImPlotLimits(0L);
    private static final ImPlotStyle IMPLOT_STYLE = new ImPlotStyle(0L);

    private ImPlot() {
    }

    public static ImPlotContext createContext() {
        ImPlot.IMPLOT_CONTEXT.ptr = ImPlot.nCreateContext();
        return IMPLOT_CONTEXT;
    }

    private static native long nCreateContext();

    public static void destroyContext(ImPlotContext imPlotContext) {
        ImPlot.nDestroyContext(imPlotContext.ptr);
    }

    private static native void nDestroyContext(long var0);

    public static ImPlotContext getCurrentContext() {
        ImPlot.IMPLOT_CONTEXT.ptr = ImPlot.nGetCurrentContext();
        return IMPLOT_CONTEXT;
    }

    private static native long nGetCurrentContext();

    public static void setCurrentContext(ImPlotContext imPlotContext) {
        ImPlot.nSetCurrentContext(imPlotContext.ptr);
    }

    private static native void nSetCurrentContext(long var0);

    public static native boolean beginPlot(String var0);

    public static native boolean beginPlot(String var0, String var1, String var2);

    public static boolean beginPlot(String string, String string2, String string3, ImVec2 imVec2) {
        return ImPlot.nBeginPlot(string, string2, string3, imVec2.x, imVec2.y);
    }

    private static native boolean nBeginPlot(String var0, String var1, String var2, float var3, float var4);

    public static boolean beginPlot(String string, String string2, String string3, ImVec2 imVec2, int n, int n2, int n3) {
        return ImPlot.nBeginPlot(string, string2, string3, imVec2.x, imVec2.y, n, n2, n3);
    }

    private static native boolean nBeginPlot(String var0, String var1, String var2, float var3, float var4, int var5, int var6, int var7);

    public static boolean beginPlot(String string, String string2, String string3, ImVec2 imVec2, int n, int n2, int n3, int n4, int n5, String string4, String string5) {
        return ImPlot.nBeginPlot(string, string2, string3, imVec2.x, imVec2.y, n, n2, n3, n4, n5, string4, string5);
    }

    private static native boolean nBeginPlot(String var0, String var1, String var2, float var3, float var4, int var5, int var6, int var7, int var8, int var9, String var10, String var11);

    public static native void endPlot();

    private static <T extends Number> void convertArrays(T[] TArray, T[] TArray2, double[] dArray, double[] dArray2) {
        if (TArray.length != TArray2.length) {
            throw new IllegalArgumentException("Invalid length for arrays");
        }
        for (int i = 0; i < TArray.length; ++i) {
            dArray[i] = ((Number)TArray[i]).doubleValue();
            dArray2[i] = ((Number)TArray2[i]).doubleValue();
        }
    }

    private static <T extends Number> void convertArrays(T[] TArray, T[] TArray2, T[] TArray3, double[] dArray, double[] dArray2, double[] dArray3) {
        if (TArray.length != TArray2.length || TArray.length != TArray3.length) {
            throw new IllegalArgumentException("Invalid length for arrays");
        }
        for (int i = 0; i < TArray.length; ++i) {
            dArray[i] = ((Number)TArray[i]).doubleValue();
            dArray2[i] = ((Number)TArray2[i]).doubleValue();
            dArray3[i] = ((Number)TArray3[i]).doubleValue();
        }
    }

    public static <T extends Number> void plotLine(String string, T[] TArray, T[] TArray2) {
        ImPlot.plotLine((String)string, TArray, TArray2, (int)0);
    }

    public static <T extends Number> void plotLine(String string, T[] TArray, T[] TArray2, int n) {
        double[] dArray = new double[TArray.length];
        double[] dArray2 = new double[TArray2.length];
        ImPlot.convertArrays(TArray, TArray2, (double[])dArray, (double[])dArray2);
        ImPlot.nPlotLine(string, dArray, dArray2, dArray.length, n);
    }

    public static void plotLine(String string, double[] dArray, double[] dArray2, int n, int n2) {
        ImPlot.nPlotLine(string, dArray, dArray2, n, n2);
    }

    private static native void nPlotLine(String var0, double[] var1, double[] var2, int var3, int var4);

    public static <T extends Number> void plotScatter(String string, T[] TArray, T[] TArray2) {
        ImPlot.plotScatter((String)string, TArray, TArray2, (int)0);
    }

    public static <T extends Number> void plotScatter(String string, T[] TArray, T[] TArray2, int n) {
        double[] dArray = new double[TArray.length];
        double[] dArray2 = new double[TArray2.length];
        ImPlot.convertArrays(TArray, TArray2, (double[])dArray, (double[])dArray2);
        ImPlot.nPlotScatter(string, dArray, dArray2, dArray.length, n);
    }

    public static void plotScatter(String string, double[] dArray, double[] dArray2, int n, int n2) {
        ImPlot.nPlotScatter(string, dArray, dArray2, n, n2);
    }

    private static native void nPlotScatter(String var0, double[] var1, double[] var2, int var3, int var4);

    public static <T extends Number> void plotStairs(String string, T[] TArray, T[] TArray2) {
        ImPlot.plotStairs((String)string, TArray, TArray2, (int)0);
    }

    public static <T extends Number> void plotStairs(String string, T[] TArray, T[] TArray2, int n) {
        double[] dArray = new double[TArray.length];
        double[] dArray2 = new double[TArray2.length];
        ImPlot.convertArrays(TArray, TArray2, (double[])dArray, (double[])dArray2);
        ImPlot.nPlotStairs(string, dArray, dArray2, dArray.length, n);
    }

    public static void plotStairs(String string, double[] dArray, double[] dArray2, int n, int n2) {
        ImPlot.nPlotStairs(string, dArray, dArray2, n, n2);
    }

    private static native void nPlotStairs(String var0, double[] var1, double[] var2, int var3, int var4);

    public static <T extends Number> void plotShaded(String string, T[] TArray, T[] TArray2, int n) {
        ImPlot.plotShaded((String)string, TArray, TArray2, (int)n, (int)0);
    }

    public static <T extends Number> void plotShaded(String string, T[] TArray, T[] TArray2, T[] TArray3) {
        ImPlot.plotShaded((String)string, TArray, TArray2, TArray3, (int)0);
    }

    public static <T extends Number> void plotShaded(String string, T[] TArray, T[] TArray2, int n, int n2) {
        double[] dArray = new double[TArray.length];
        double[] dArray2 = new double[TArray2.length];
        ImPlot.convertArrays(TArray, TArray2, (double[])dArray, (double[])dArray2);
        ImPlot.nPlotShaded(string, dArray, dArray2, dArray.length, n, n2);
    }

    public static <T extends Number> void plotShaded(String string, T[] TArray, T[] TArray2, T[] TArray3, int n) {
        double[] dArray = new double[TArray.length];
        double[] dArray2 = new double[TArray2.length];
        double[] dArray3 = new double[TArray3.length];
        ImPlot.convertArrays(TArray, TArray2, TArray3, (double[])dArray, (double[])dArray2, (double[])dArray3);
        ImPlot.nPlotShaded(string, dArray, dArray2, dArray3, dArray.length, n);
    }

    public static void plotShaded(String string, double[] dArray, double[] dArray2, int n, int n2, int n3) {
        ImPlot.nPlotShaded(string, dArray, dArray2, n, n2, n3);
    }

    private static native void nPlotShaded(String var0, double[] var1, double[] var2, int var3, int var4, int var5);

    public static void plotShaded(String string, double[] dArray, double[] dArray2, double[] dArray3, int n, int n2) {
        ImPlot.nPlotShaded(string, dArray, dArray2, dArray3, n, n2);
    }

    private static native void nPlotShaded(String var0, double[] var1, double[] var2, double[] var3, int var4, int var5);

    public static <T extends Number> void plotBars(String string, T[] TArray, T[] TArray2) {
        ImPlot.plotBars((String)string, TArray, TArray2, (float)0.67f, (int)0);
    }

    public static <T extends Number> void plotBars(String string, T[] TArray, T[] TArray2, float f) {
        ImPlot.plotBars((String)string, TArray, TArray2, (float)f, (int)0);
    }

    public static <T extends Number> void plotBars(String string, T[] TArray, T[] TArray2, float f, int n) {
        double[] dArray = new double[TArray.length];
        double[] dArray2 = new double[TArray2.length];
        ImPlot.convertArrays(TArray, TArray2, (double[])dArray, (double[])dArray2);
        ImPlot.nPlotBars(string, dArray, dArray2, dArray.length, f, n);
    }

    public static void plotBars(String string, double[] dArray, double[] dArray2, int n, float f, int n2) {
        ImPlot.nPlotBars(string, dArray, dArray2, n, f, n2);
    }

    private static native void nPlotBars(String var0, double[] var1, double[] var2, int var3, float var4, int var5);

    public static <T extends Number> void plotBarsH(String string, T[] TArray, T[] TArray2) {
        ImPlot.plotBarsH((String)string, TArray, TArray2, (float)0.67f, (int)0);
    }

    public static <T extends Number> void plotBarsH(String string, T[] TArray, T[] TArray2, float f) {
        ImPlot.plotBarsH((String)string, TArray, TArray2, (float)f, (int)0);
    }

    public static <T extends Number> void plotBarsH(String string, T[] TArray, T[] TArray2, float f, int n) {
        double[] dArray = new double[TArray.length];
        double[] dArray2 = new double[TArray2.length];
        ImPlot.convertArrays(TArray, TArray2, (double[])dArray, (double[])dArray2);
        ImPlot.nPlotBarsH(string, dArray, dArray2, dArray.length, f, n);
    }

    public static void plotBarsH(String string, double[] dArray, double[] dArray2, int n, float f, int n2) {
        ImPlot.nPlotBarsH(string, dArray, dArray2, n, f, n2);
    }

    private static native void nPlotBarsH(String var0, double[] var1, double[] var2, int var3, float var4, int var5);

    public static <T extends Number> void plotErrorBars(String string, T[] TArray, T[] TArray2, T[] TArray3) {
        ImPlot.plotErrorBars((String)string, TArray, TArray2, TArray3, (int)0);
    }

    public static <T extends Number> void plotErrorBars(String string, T[] TArray, T[] TArray2, T[] TArray3, int n) {
        double[] dArray = new double[TArray.length];
        double[] dArray2 = new double[TArray2.length];
        double[] dArray3 = new double[TArray3.length];
        ImPlot.convertArrays(TArray, TArray2, (double[])dArray, (double[])dArray2);
        ImPlot.convertArrays(TArray, TArray3, (double[])dArray, (double[])dArray3);
        ImPlot.nPlotErrorBars(string, dArray, dArray2, dArray3, dArray.length, n);
    }

    public static void plotErrorBars(String string, double[] dArray, double[] dArray2, double[] dArray3, int n, int n2) {
        ImPlot.nPlotErrorBars(string, dArray, dArray2, dArray3, n, n2);
    }

    private static native void nPlotErrorBars(String var0, double[] var1, double[] var2, double[] var3, int var4, int var5);

    public static <T extends Number> void plotErrorBarsH(String string, T[] TArray, T[] TArray2, T[] TArray3) {
        ImPlot.plotErrorBarsH((String)string, TArray, TArray2, TArray3, (int)0);
    }

    public static <T extends Number> void plotErrorBarsH(String string, T[] TArray, T[] TArray2, T[] TArray3, int n) {
        double[] dArray = new double[TArray.length];
        double[] dArray2 = new double[TArray2.length];
        double[] dArray3 = new double[TArray3.length];
        ImPlot.convertArrays(TArray, TArray2, (double[])dArray, (double[])dArray2);
        ImPlot.convertArrays(TArray, TArray3, (double[])dArray, (double[])dArray3);
        ImPlot.nPlotErrorBarsH(string, dArray, dArray2, dArray3, dArray.length, n);
    }

    public static void plotErrorBarsH(String string, double[] dArray, double[] dArray2, double[] dArray3, int n, int n2) {
        ImPlot.nPlotErrorBarsH(string, dArray, dArray2, dArray3, n, n2);
    }

    private static native void nPlotErrorBarsH(String var0, double[] var1, double[] var2, double[] var3, int var4, int var5);

    public static <T extends Number> void plotStems(String string, T[] TArray, int n) {
        ImPlot.plotStems((String)string, TArray, (int)n, (int)0);
    }

    public static <T extends Number> void plotStems(String string, T[] TArray, int n, int n2) {
        double[] dArray = new double[TArray.length];
        for (int i = 0; i < TArray.length; ++i) {
            dArray[i] = ((Number)TArray[i]).doubleValue();
        }
        ImPlot.nPlotStems(string, dArray, dArray.length, n, n2);
    }

    public static void plotStems(String string, double[] dArray, int n, int n2, int n3) {
        ImPlot.nPlotStems(string, dArray, n, n2, n3);
    }

    private static native void nPlotStems(String var0, double[] var1, int var2, int var3, int var4);

    public static <T extends Number> void plotVLines(String string, T[] TArray) {
        ImPlot.plotVLines((String)string, TArray, (int)0);
    }

    public static <T extends Number> void plotVLines(String string, T[] TArray, int n) {
        double[] dArray = new double[TArray.length];
        for (int i = 0; i < TArray.length; ++i) {
            dArray[i] = ((Number)TArray[i]).doubleValue();
        }
        ImPlot.nPlotVLines(string, dArray, dArray.length, n);
    }

    public static void plotVLines(String string, double[] dArray, int n, int n2) {
        ImPlot.nPlotVLines(string, dArray, n, n2);
    }

    private static native void nPlotVLines(String var0, double[] var1, int var2, int var3);

    public static <T extends Number> void plotHLines(String string, T[] TArray) {
        ImPlot.plotHLines((String)string, TArray, (int)0);
    }

    public static <T extends Number> void plotHLines(String string, T[] TArray, int n) {
        double[] dArray = new double[TArray.length];
        for (int i = 0; i < TArray.length; ++i) {
            dArray[i] = ((Number)TArray[i]).doubleValue();
        }
        ImPlot.nPlotHLines(string, dArray, dArray.length, n);
    }

    public static void plotHLines(String string, double[] dArray, int n, int n2) {
        ImPlot.nPlotHLines(string, dArray, n, n2);
    }

    private static native void nPlotHLines(String var0, double[] var1, int var2, int var3);

    public static <T extends Number> void plotPieChart(String[] stringArray, T[] TArray, double d, double d2, double d3) {
        double[] dArray = new double[TArray.length];
        for (int i = 0; i < TArray.length; ++i) {
            dArray[i] = ((Number)TArray[i]).doubleValue();
        }
        String string = "";
        boolean bl = true;
        int n = 0;
        for (String string2 : stringArray) {
            if (bl) {
                bl = false;
                continue;
            }
            string = string + "\n";
            if (string2.length() > n) {
                n = string2.length();
            }
            string = string + string2.replace("\n", "");
        }
        ImPlot.nPlotPieChart(string, n, dArray, dArray.length, d, d2, d3);
    }

    public static void plotPieChart(String string, int n, double[] dArray, int n2, double d, double d2, double d3) {
        ImPlot.nPlotPieChart(string, n, dArray, n2, d, d2, d3);
    }

    private static native void nPlotPieChart(String var0, int var1, double[] var2, int var3, double var4, double var6, double var8);

    public static <T extends Number> void plotHeatmap(String string, T[][] TArray, int n) {
        double[] dArray = new double[TArray.length * TArray[0].length];
        int n2 = 0;
        T[][] TArray2 = TArray;
        int n3 = TArray2.length;
        for (int i = 0; i < n3; ++i) {
            T[] TArray3;
            for (T t : TArray3 = TArray2[i]) {
                dArray[n2++] = ((Number)t).doubleValue();
            }
        }
        ImPlot.nPlotHeatmap(string, dArray, TArray[0].length, TArray.length);
    }

    public static void plotHeatmap(String string, double[] dArray, int n, int n2) {
        ImPlot.nPlotHeatmap(string, dArray, n, n2);
    }

    private static native void nPlotHeatmap(String var0, double[] var1, int var2, int var3);

    public static <T extends Number> double plotHistogram(String string, T[] TArray) {
        double[] dArray = new double[TArray.length];
        for (int i = 0; i < TArray.length; ++i) {
            dArray[i] = ((Number)TArray[i]).doubleValue();
        }
        return ImPlot.nPlotHistogram(string, dArray, dArray.length);
    }

    public static double plotHistogram(String string, double[] dArray, int n) {
        return ImPlot.nPlotHistogram(string, dArray, n);
    }

    private static native double nPlotHistogram(String var0, double[] var1, int var2);

    public static <T extends Number> double plotHistogram2D(String string, T[] TArray, T[] TArray2) {
        double[] dArray = new double[TArray.length];
        double[] dArray2 = new double[TArray2.length];
        ImPlot.convertArrays(TArray, TArray2, (double[])dArray, (double[])dArray2);
        return ImPlot.nPlotHistogram2D(string, dArray, dArray2, dArray.length);
    }

    public static double plotHistogram2D(String string, double[] dArray, double[] dArray2, int n) {
        return ImPlot.nPlotHistogram2D(string, dArray, dArray2, n);
    }

    private static native double nPlotHistogram2D(String var0, double[] var1, double[] var2, int var3);

    public static <T extends Number> void plotDigital(String string, T[] TArray, T[] TArray2) {
        double[] dArray = new double[TArray.length];
        double[] dArray2 = new double[TArray2.length];
        ImPlot.convertArrays(TArray, TArray2, (double[])dArray, (double[])dArray2);
        ImPlot.nPlotDigital(string, dArray, dArray2, dArray.length);
    }

    public static void plotDigital(String string, double[] dArray, double[] dArray2, int n) {
        ImPlot.nPlotDigital(string, dArray, dArray2, n);
    }

    private static native void nPlotDigital(String var0, double[] var1, double[] var2, int var3);

    public static void plotText(String string, double d, double d2) {
        ImPlot.plotText(string, d, d2, false);
    }

    public static native void plotText(String var0, double var1, double var3, boolean var5);

    public static native void plotDummy(String var0);

    public static native void setNextPlotLimits(double var0, double var2, double var4, double var6, int var8);

    public static native void setNextPlotLimitsX(double var0, double var2, int var4);

    public static native void setNextPlotLimitsY(double var0, double var2, int var4);

    public static void linkNextPlotLimits(ImDouble imDouble, ImDouble imDouble2, ImDouble imDouble3, ImDouble imDouble4) {
        ImPlot.linkNextPlotLimits(imDouble, imDouble2, imDouble3, imDouble4, null, null, null, null);
    }

    public static void linkNextPlotLimits(ImDouble imDouble, ImDouble imDouble2, ImDouble imDouble3, ImDouble imDouble4, ImDouble imDouble5, ImDouble imDouble6) {
        ImPlot.linkNextPlotLimits(imDouble, imDouble2, imDouble3, imDouble4, imDouble5, imDouble6, null, null);
    }

    public static void linkNextPlotLimits(ImDouble imDouble, ImDouble imDouble2, ImDouble imDouble3, ImDouble imDouble4, ImDouble imDouble5, ImDouble imDouble6, ImDouble imDouble7, ImDouble imDouble8) {
        ImPlot.nLinkNextPlotLimits(imDouble.getData(), imDouble2.getData(), imDouble3.getData(), imDouble4.getData(), imDouble5.getData(), imDouble6.getData(), imDouble7.getData(), imDouble8.getData());
    }

    private static native void nLinkNextPlotLimits(double[] var0, double[] var1, double[] var2, double[] var3, double[] var4, double[] var5, double[] var6, double[] var7);

    public static void fitNextPlotAxes() {
        ImPlot.fitNextPlotAxes(true, true, true, true);
    }

    public static void fitNextPlotAxes(boolean bl, boolean bl2) {
        ImPlot.fitNextPlotAxes(bl, bl2, true, true);
    }

    public static native void fitNextPlotAxes(boolean var0, boolean var1, boolean var2, boolean var3);

    public static void setNextPlotTicksX(double d, double d2, int n) {
        ImPlot.setNextPlotTicksX(d, d2, n, null, false);
    }

    public static void setNextPlotTicksX(double d, double d2, int n, String[] stringArray, boolean bl) {
        String[] stringArray2 = new String[4];
        for (int i = 0; i < 4; ++i) {
            stringArray2[i] = stringArray != null && i < stringArray.length ? stringArray[i] : null;
        }
        ImPlot.nSetNextPlotTicksX(d, d2, n, stringArray2[0], stringArray2[5], stringArray2[5], stringArray2[5], bl);
    }

    public static void setNextPlotTicksX(double d, double d2, int n, String string, String string2, String string3, String string4, boolean bl) {
        ImPlot.nSetNextPlotTicksX(d, d2, n, string, string2, string3, string4, bl);
    }

    private static native void nSetNextPlotTicksX(double var0, double var2, int var4, String var5, String var6, String var7, String var8, boolean var9);

    public static void setNextPlotTicksY(double d, double d2, int n) {
        ImPlot.setNextPlotTicksY(d, d2, n, null, false, 0);
    }

    public static void setNextPlotTicksY(double d, double d2, int n, String[] stringArray, boolean bl) {
        ImPlot.setNextPlotTicksY(d, d2, n, stringArray, bl, 0);
    }

    public static void setNextPlotTicksY(double d, double d2, int n, String[] stringArray, boolean bl, int n2) {
        String[] stringArray2 = new String[4];
        for (int i = 0; i < 4; ++i) {
            stringArray2[i] = stringArray != null && i < stringArray.length ? stringArray[i] : null;
        }
        ImPlot.nSetNextPlotTicksY(d, d2, n, stringArray2[0], stringArray2[5], stringArray2[5], stringArray2[5], bl, n2);
    }

    public static void setNextPlotTicksY(double d, double d2, int n, String string, String string2, String string3, String string4, boolean bl, int n2) {
        ImPlot.nSetNextPlotTicksY(d, d2, n, string, string2, string3, string4, bl, n2);
    }

    private static native void nSetNextPlotTicksY(double var0, double var2, int var4, String var5, String var6, String var7, String var8, boolean var9, int var10);

    public static native void setNextPlotFormatX(String var0);

    public static void setNextPlotFormatY(String string) {
        ImPlot.setNextPlotFormatY(string, 0);
    }

    public static native void setNextPlotFormatY(String var0, int var1);

    public static native void setPlotYAxis(int var0);

    public static void hideNextItem() {
        ImPlot.hideNextItem(true, 2);
    }

    public static native void hideNextItem(boolean var0, int var1);

    public static ImPlotPoint pixelsToPlot(ImVec2 imVec2, int n) {
        ImPlot.IMPLOT_POINT.ptr = ImPlot.nPixelsToPlot(imVec2.x, imVec2.y, n);
        return IMPLOT_POINT;
    }

    public static ImPlotPoint pixelsToPlot(float f, float f2, int n) {
        ImPlot.IMPLOT_POINT.ptr = ImPlot.nPixelsToPlot(f, f2, n);
        return IMPLOT_POINT;
    }

    private static native long nPixelsToPlot(float var0, float var1, int var2);

    public static ImVec2 plotToPixels(ImPlotPoint imPlotPoint, int n) {
        return ImPlot.plotToPixels(imPlotPoint.getX(), imPlotPoint.getY(), n);
    }

    public static ImVec2 plotToPixels(double d, double d2, int n) {
        ImVec2 imVec2 = new ImVec2();
        ImPlot.nPlotToPixels(d, d2, n, imVec2);
        return imVec2;
    }

    private static native void nPlotToPixels(double var0, double var2, int var4, ImVec2 var5);

    public static ImVec2 getPlotPos() {
        ImVec2 imVec2 = new ImVec2();
        ImPlot.getPlotPos(imVec2);
        return imVec2;
    }

    public static native void getPlotPos(ImVec2 var0);

    public static ImVec2 getPlotSize() {
        ImVec2 imVec2 = new ImVec2();
        ImPlot.getPlotSize(imVec2);
        return imVec2;
    }

    public static native void getPlotSize(ImVec2 var0);

    public static native boolean isPlotHovered();

    public static native boolean isPlotXAxisHovered();

    public static native boolean isPlotYAxisHovered(int var0);

    public static ImPlotPoint getPlotMousePos(int n) {
        ImPlot.IMPLOT_POINT.ptr = ImPlot.nGetPlotMousePos(n);
        return IMPLOT_POINT;
    }

    private static native long nGetPlotMousePos(int var0);

    public static ImPlotLimits getPlotLimits(int n) {
        ImPlot.IMPLOT_LIMITS.ptr = ImPlot.nGetPlotLimits(n);
        return IMPLOT_LIMITS;
    }

    private static native long nGetPlotLimits(int var0);

    public static native boolean isPlotSelected();

    public static ImPlotLimits getPlotSelection(int n) {
        ImPlot.IMPLOT_LIMITS.ptr = ImPlot.nGetPlotSelection(n);
        return IMPLOT_LIMITS;
    }

    private static native long nGetPlotSelection(int var0);

    public static native boolean isPlotQueried();

    public static ImPlotLimits getPlotQuery(int n) {
        ImPlot.IMPLOT_LIMITS.ptr = ImPlot.nGetPlotQuery(n);
        return IMPLOT_LIMITS;
    }

    private static native long nGetPlotQuery(int var0);

    public static void setPlotQuery(ImPlotLimits imPlotLimits, int n) {
        ImPlot.nSetPlotQuery(imPlotLimits.ptr, n);
    }

    private static native void nSetPlotQuery(long var0, int var2);

    public static void annotate(double d, double d2, ImVec2 imVec2, String ... stringArray) {
        ImPlot.annotate(d, d2, imVec2, new ImVec4(0.0f, 0.0f, 0.0f, 0.0f), stringArray);
    }

    public static void annotate(double d, double d2, ImVec2 imVec2, ImVec4 imVec4, String ... stringArray) {
        ImPlot.nAnnotate(d, d2, imVec2.x, imVec2.y, imVec4.w, imVec4.x, imVec4.y, imVec4.z, stringArray.length > 0 ? stringArray[0] : null, stringArray.length > 1 ? stringArray[5] : null, stringArray.length > 2 ? stringArray[5] : null, stringArray.length > 3 ? stringArray[5] : null, stringArray.length > 4 ? stringArray[5] : null);
    }

    private static native void nAnnotate(double var0, double var2, float var4, float var5, float var6, float var7, float var8, float var9, String var10, String var11, String var12, String var13, String var14);

    public static void annotateClamped(double d, double d2, ImVec2 imVec2, String ... stringArray) {
        ImPlot.annotateClamped(d, d2, imVec2, new ImVec4(0.0f, 0.0f, 0.0f, 0.0f), stringArray);
    }

    public static void annotateClamped(double d, double d2, ImVec2 imVec2, ImVec4 imVec4, String ... stringArray) {
        ImPlot.nAnnotateClamped(d, d2, imVec2.x, imVec2.y, imVec4.w, imVec4.x, imVec4.y, imVec4.z, stringArray.length > 0 ? stringArray[0] : null, stringArray.length > 1 ? stringArray[5] : null, stringArray.length > 2 ? stringArray[5] : null, stringArray.length > 3 ? stringArray[5] : null, stringArray.length > 4 ? stringArray[5] : null);
    }

    private static native void nAnnotateClamped(double var0, double var2, float var4, float var5, float var6, float var7, float var8, float var9, String var10, String var11, String var12, String var13, String var14);

    public static boolean dragLineX(String string, double d, boolean bl, ImVec4 imVec4, float f) {
        return ImPlot.nDragLineX(string, d, bl, imVec4.w, imVec4.x, imVec4.y, imVec4.z, f);
    }

    private static native boolean nDragLineX(String var0, double var1, boolean var3, float var4, float var5, float var6, float var7, float var8);

    public static boolean dragLineY(String string, double d, boolean bl, ImVec4 imVec4, float f) {
        return ImPlot.nDragLineY(string, d, bl, imVec4.w, imVec4.x, imVec4.y, imVec4.z, f);
    }

    private static native boolean nDragLineY(String var0, double var1, boolean var3, float var4, float var5, float var6, float var7, float var8);

    public static boolean dragPoint(String string, double d, double d2, boolean bl, ImVec4 imVec4, float f) {
        return ImPlot.nDragPoint(string, d, d2, bl, imVec4.w, imVec4.x, imVec4.y, imVec4.z, f);
    }

    private static native boolean nDragPoint(String var0, double var1, double var3, boolean var5, float var6, float var7, float var8, float var9, float var10);

    public static native void setLegendLocation(int var0, int var1, boolean var2);

    public static native void setMousePosLocation(int var0);

    public static native boolean isLegendEntryHovered(String var0);

    public static boolean beginLegendPopup(String string) {
        return ImPlot.beginLegendPopup(string, 1);
    }

    public static native boolean beginLegendPopup(String var0, int var1);

    public static native void endLegendPopup();

    public static native boolean beginDragDropTarget();

    public static native boolean beginDragDropTargetX();

    public static native boolean beginDragDropTargetY(int var0);

    public static native boolean beginDragDropTargetLegend();

    public static native void endDragDropTarget();

    public static native boolean beginDragDropSource(int var0, int var1);

    public static native boolean beginDragDropSourceX(int var0, int var1);

    public static native boolean beginDragDropSourceY(int var0, int var1, int var2);

    public static native boolean beginDragDropSourceItem(String var0, int var1);

    public static native void endDragDropSource();

    public static ImPlotStyle getStyle() {
        ImPlot.IMPLOT_STYLE.ptr = ImPlot.nGetStyle();
        return IMPLOT_STYLE;
    }

    private static native long nGetStyle();

    public static native void styleColorsAuto();

    public static native void styleColorsClassic();

    public static native void styleColorsDark();

    public static native void styleColorsLight();

    public static native void pushStyleColor(int var0, long var1);

    public static void pushStyleColor(int n, ImVec4 imVec4) {
        ImPlot.nPushStyleColor(n, imVec4.w, imVec4.x, imVec4.y, imVec4.z);
    }

    private static native void nPushStyleColor(int var0, float var1, float var2, float var3, float var4);

    public static void popStyleColor() {
        ImPlot.popStyleColor(1);
    }

    public static native void popStyleColor(int var0);

    public static native void pushStyleVar(int var0, float var1);

    public static native void pushStyleVar(int var0, int var1);

    public static void pushStyleVar(int n, ImVec2 imVec2) {
        ImPlot.nPushStyleVar(n, imVec2.x, imVec2.y);
    }

    private static native void nPushStyleVar(int var0, float var1, float var2);

    public static void popStyleVar() {
        ImPlot.popStyleVar(1);
    }

    public static native void popStyleVar(int var0);

    public static ImVec4 getLastItemColor() {
        return new ImVec4(ImPlot.nGetLastItemColorS(0), ImPlot.nGetLastItemColorS(1), ImPlot.nGetLastItemColorS(2), ImPlot.nGetLastItemColorS(3));
    }

    private static native float nGetLastItemColorS(int var0);

    public static native String getStyleColorName(int var0);

    public static native String getMarkerName(int var0);

    public static int addColormap(String string, ImVec4[] imVec4Array) {
        float[] fArray = new float[imVec4Array.length];
        float[] fArray2 = new float[imVec4Array.length];
        float[] fArray3 = new float[imVec4Array.length];
        float[] fArray4 = new float[imVec4Array.length];
        for (int i = 0; i < imVec4Array.length; ++i) {
            fArray[i] = imVec4Array[i].w;
            fArray2[i] = imVec4Array[i].x;
            fArray3[i] = imVec4Array[i].y;
            fArray4[i] = imVec4Array[i].z;
        }
        return ImPlot.nAddColormap(string, fArray, fArray2, fArray3, fArray4, imVec4Array.length);
    }

    private static native int nAddColormap(String var0, float[] var1, float[] var2, float[] var3, float[] var4, int var5);

    public static native int getColormapCount();

    public static native String getColormapName(int var0);

    public static native int getColormapIndex(String var0);

    public static native void pushColormap(int var0);

    public static native void pushColormap(String var0);

    public static void popColormap() {
        ImPlot.popColormap(1);
    }

    public static native void popColormap(int var0);

    public static ImVec4 nextColormapColor() {
        ImVec4 imVec4 = new ImVec4();
        ImPlot.nNextColormapColor(imVec4);
        return imVec4;
    }

    private static native void nNextColormapColor(ImVec4 var0);

    public static native int getColormapSize(int var0);

    public static ImVec4 getColormapColor(int n, int n2) {
        ImVec4 imVec4 = new ImVec4();
        ImPlot.nGetColormapColor(n, n2, imVec4);
        return imVec4;
    }

    private static native void nGetColormapColor(int var0, int var1, ImVec4 var2);

    public static ImVec4 sampleColormap(float f, int n) {
        ImVec4 imVec4 = new ImVec4();
        ImPlot.nSampleColormap(f, n, imVec4);
        return imVec4;
    }

    private static native void nSampleColormap(float var0, int var1, ImVec4 var2);

    public static native void colormapScale(String var0, double var1, double var3, int var5);

    public static native boolean colormapSlider(String var0, float var1, int var2);

    public static native boolean colormapButton(String var0, int var1);

    public static void bustColorCache() {
        ImPlot.bustColorCache(null);
    }

    public static native void bustColorCache(String var0);

    public static void itemIcon(ImVec4 imVec4) {
        ImPlot.nItemIcon(imVec4.w, imVec4.x, imVec4.y, imVec4.z);
    }

    private static native void nItemIcon(double var0, double var2, double var4, double var6);

    public static native void colormapIcon(int var0);

    public static ImDrawList getPlotDrawList() {
        ImPlot.IM_DRAW_LIST.ptr = ImPlot.nGetPlotDrawList();
        return IM_DRAW_LIST;
    }

    private static native long nGetPlotDrawList();

    public static native void pushPlotClipRect(float var0);

    public static native void popPlotClipRect();

    public static native boolean showStyleSelector(String var0);

    public static native boolean showColormapSelector(String var0);

    public static native void showStyleEditor();

    public static native void showUserGuide();

    public static native void showMetricsWindow();

    public static void showDemoWindow(ImBoolean imBoolean) {
        ImPlot.nShowDemoWindow(imBoolean.getData());
    }

    private static native void nShowDemoWindow(boolean[] var0);
}

