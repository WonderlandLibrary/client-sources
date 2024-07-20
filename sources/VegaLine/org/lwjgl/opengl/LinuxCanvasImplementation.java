/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.awt.Canvas;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.AWTCanvasImplementation;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.LinuxAWTGLCanvasPeerInfo;
import org.lwjgl.opengl.PeerInfo;
import org.lwjgl.opengl.PixelFormat;

final class LinuxCanvasImplementation
implements AWTCanvasImplementation {
    LinuxCanvasImplementation() {
    }

    static int getScreenFromDevice(final GraphicsDevice device) throws LWJGLException {
        try {
            Method getScreen_method = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                @Override
                public Method run() throws Exception {
                    return device.getClass().getMethod("getScreen", new Class[0]);
                }
            });
            Integer screen = (Integer)getScreen_method.invoke(device, new Object[0]);
            return screen;
        } catch (Exception e) {
            throw new LWJGLException(e);
        }
    }

    private static int getVisualIDFromConfiguration(final GraphicsConfiguration configuration) throws LWJGLException {
        try {
            Method getVisual_method = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                @Override
                public Method run() throws Exception {
                    return configuration.getClass().getMethod("getVisual", new Class[0]);
                }
            });
            Integer visual = (Integer)getVisual_method.invoke(configuration, new Object[0]);
            return visual;
        } catch (Exception e) {
            throw new LWJGLException(e);
        }
    }

    public PeerInfo createPeerInfo(Canvas component, PixelFormat pixel_format, ContextAttribs attribs) throws LWJGLException {
        return new LinuxAWTGLCanvasPeerInfo(component);
    }

    public GraphicsConfiguration findConfiguration(GraphicsDevice device, PixelFormat pixel_format) throws LWJGLException {
        try {
            GraphicsConfiguration[] configurations;
            int screen = LinuxCanvasImplementation.getScreenFromDevice(device);
            int visual_id_matching_format = LinuxCanvasImplementation.findVisualIDFromFormat(screen, pixel_format);
            for (GraphicsConfiguration configuration : configurations = device.getConfigurations()) {
                int visual_id = LinuxCanvasImplementation.getVisualIDFromConfiguration(configuration);
                if (visual_id != visual_id_matching_format) continue;
                return configuration;
            }
        } catch (LWJGLException e) {
            LWJGLUtil.log("Got exception while trying to determine configuration: " + e);
        }
        return null;
    }

    /*
     * Exception decompiling
     */
    private static int findVisualIDFromFormat(int screen, PixelFormat pixel_format) throws LWJGLException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:538)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at async.DecompilerRunnable.cfrDecompilation(DecompilerRunnable.java:295)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:256)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:26)
         *     at java.util.concurrent.FutureTask.run(FutureTask.java:266)
         *     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         *     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         *     at java.lang.Thread.run(Thread.java:750)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static native int nFindVisualIDFromFormat(long var0, int var2, PixelFormat var3) throws LWJGLException;
}

