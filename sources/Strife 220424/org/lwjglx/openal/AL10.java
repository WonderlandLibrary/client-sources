package org.lwjglx.openal;

public class AL10 {
	
	public static final int AL_NO_ERROR = org.lwjgl.openal.AL10.AL_NO_ERROR;
	public static final int AL_BUFFER = org.lwjgl.openal.AL10.AL_BUFFER;
	public static final int AL_FORMAT_MONO8 = org.lwjgl.openal.AL10.AL_FORMAT_MONO8;
	public static final int AL_FORMAT_MONO16 = org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
	public static final int AL_FORMAT_STEREO8 = org.lwjgl.openal.AL10.AL_FORMAT_STEREO8;
	public static final int AL_FORMAT_STEREO16 = org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
	public static final int AL_GAIN = org.lwjgl.openal.AL10.AL_GAIN;
	public static final int AL_INVALID_OPERATION = org.lwjgl.openal.AL10.AL_INVALID_OPERATION;
	public static final int AL_INVALID_VALUE = org.lwjgl.openal.AL10.AL_INVALID_VALUE;
	public static final int AL_INVALID_ENUM = org.lwjgl.openal.AL10.AL_INVALID_ENUM;
	public static final int AL_INVALID_NAME = org.lwjgl.openal.AL10.AL_INVALID_NAME;
	public static final int AL_INVERSE_DISTANCE_CLAMPED = org.lwjgl.openal.AL10.AL_INVERSE_DISTANCE_CLAMPED;
	public static final int AL_ORIENTATION = org.lwjgl.openal.AL10.AL_ORIENTATION;
	public static final int AL_SOURCE_STATE = org.lwjgl.openal.AL10.AL_SOURCE_STATE;
	public static final int AL_PLAYING = org.lwjgl.openal.AL10.AL_PLAYING;
	public static final int AL_LOOPING = org.lwjgl.openal.AL10.AL_LOOPING;
	public static final int AL_TRUE = org.lwjgl.openal.AL10.AL_TRUE;
	public static final int AL_REFERENCE_DISTANCE = org.lwjgl.openal.AL10.AL_REFERENCE_DISTANCE;
	public static final int AL_ROLLOFF_FACTOR = org.lwjgl.openal.AL10.AL_ROLLOFF_FACTOR;
	public static final int AL_POSITION = org.lwjgl.openal.AL10.AL_POSITION;
	
	public static void alListener(int pname, java.nio.FloatBuffer value) {
		org.lwjgl.openal.AL10.alListenerfv(pname, value);
	}
	
	public static void alGenBuffers(java.nio.IntBuffer buffers) {
		org.lwjgl.openal.AL10.alGenBuffers(buffers);
	}
	
	public static void alGenSources(java.nio.IntBuffer sources) {
		org.lwjgl.openal.AL10.alGenSources(sources);
	}
	
	public static void alBufferData(int buffer, int format, java.nio.ByteBuffer data, int freq) {
		org.lwjgl.openal.AL10.alBufferData(buffer, format, data, freq);
	}
	
	public static void alDistanceModel(int value) {
		org.lwjgl.openal.AL10.alDistanceModel(value);
	}
	
	public static void alSourcei(int source, int pname, int value) {
		org.lwjgl.openal.AL10.alSourcei(source, pname, value);
	}
	
	public static int alGetSourcei(int source, int pname) {
		return org.lwjgl.openal.AL10.alGetSourcei(source, pname);
	}
	
	public static String alGetString(int pname) {
		return org.lwjgl.openal.AL10.alGetString(pname);
	}
	
	public static int alGetError() {
		return org.lwjgl.openal.AL10.alGetError();
	}
	
	public static void alListenerf(int pname, float value) {
		org.lwjgl.openal.AL10.alListenerf(pname, value);
	}
	
	public static void alSourcef(int source, int pname, float value) {
		org.lwjgl.openal.AL10.alSourcef(source, pname, value);
	}
	
	public static void alSourcePlay(int source) {
		org.lwjgl.openal.AL10.alSourcePlay(source);
	}
	
	public static void alSourceStop(java.nio.IntBuffer sources) {
		org.lwjgl.openal.AL10.alSourceStopv(sources);
	}
	
	public static void alDeleteSources(java.nio.IntBuffer sources) {
		org.lwjgl.openal.AL10.alDeleteSources(sources);
	}
	
	public static void alDeleteBuffers(java.nio.IntBuffer buffers) {
		org.lwjgl.openal.AL10.alDeleteBuffers(buffers);
	}
	
	public static void alListener3f(int pname, float v1, float v2, float v3) {
		org.lwjgl.openal.AL10.alListener3f(pname, v1, v2, v3);
	}
	
	public static void alSource3f(int source, int pname, float v1, float v2, float v3) {
		org.lwjgl.openal.AL10.alSource3f(source, pname, v1, v2, v3);
	}
	
	public static void alSourceStop(int source) {
		org.lwjgl.openal.AL10.alSourceStop(source);
	}

}
