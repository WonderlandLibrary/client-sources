/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.awt.Canvas;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.PeerInfo;
import org.lwjgl.opengl.PixelFormat;

interface AWTCanvasImplementation {
    public PeerInfo createPeerInfo(Canvas var1, PixelFormat var2, ContextAttribs var3) throws LWJGLException;

    public GraphicsConfiguration findConfiguration(GraphicsDevice var1, PixelFormat var2) throws LWJGLException;
}

