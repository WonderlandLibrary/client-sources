package org.lwjglx.openal;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.MemoryUtil;
import org.lwjglx.LWJGLException;
import org.lwjglx.Sys;

import java.nio.IntBuffer;

public class AL {

    static long alContext;
    static ALCdevice alcDevice;

    private static boolean created = false;

    static {
        Sys.initialize(); // init using dummy sys method
    }

    public static void create() throws LWJGLException {
        if (alContext == MemoryUtil.NULL) {
            //ALDevice alDevice = ALDevice.create();
            long alDevice = ALC10.alcOpenDevice(ALC10.alcGetString(0, org.lwjglx.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER));
            if (alDevice == MemoryUtil.NULL) {
                throw new LWJGLException("Cannot open the device");
            }

            IntBuffer attribs = BufferUtils.createIntBuffer(16);

            attribs.put(org.lwjgl.openal.ALC10.ALC_REFRESH);
            attribs.put(60);

            attribs.put(org.lwjgl.openal.ALC10.ALC_SYNC);
            attribs.put(org.lwjgl.openal.ALC10.ALC_FALSE);

            attribs.put(0);
            attribs.flip();

            long contextHandle = org.lwjgl.openal.ALC10.alcCreateContext(alDevice, attribs);
            ALC10.alcMakeContextCurrent(contextHandle);
            //alContext = new ALContext(alDevice, contextHandle);
            alContext = ALC10.alcCreateContext(contextHandle, (IntBuffer) null);
            alcDevice = new ALCdevice(contextHandle);

            ALCCapabilities alcCapabilities = ALC.createCapabilities(alDevice);
            ALCapabilities alCapabilities = org.lwjgl.openal.AL.createCapabilities(alcCapabilities);
            created = true;
        }
    }

    public static boolean isCreated() {
        return created;
    }

    public static void destroy() {
        //alContext.destroy();
        alContext = -1;
        alcDevice = null;
        created = false;
    }

    public static ALCdevice getDevice() {
        return alcDevice;
    }

}
