package paulscode.sound;

public class CommandObject
{
    public static final int INITIALIZE = 1;
    public static final int LOAD_SOUND = 2;
    public static final int LOAD_DATA = 3;
    public static final int UNLOAD_SOUND = 4;
    public static final int QUEUE_SOUND = 5;
    public static final int DEQUEUE_SOUND = 6;
    public static final int FADE_OUT = 7;
    public static final int FADE_OUT_IN = 8;
    public static final int CHECK_FADE_VOLUMES = 9;
    public static final int NEW_SOURCE = 10;
    public static final int RAW_DATA_STREAM = 11;
    public static final int QUICK_PLAY = 12;
    public static final int SET_POSITION = 13;
    public static final int SET_VOLUME = 14;
    public static final int SET_PITCH = 15;
    public static final int SET_PRIORITY = 16;
    public static final int SET_LOOPING = 17;
    public static final int SET_ATTENUATION = 18;
    public static final int SET_DIST_OR_ROLL = 19;
    public static final int CHANGE_DOPPLER_FACTOR = 20;
    public static final int CHANGE_DOPPLER_VELOCITY = 21;
    public static final int SET_VELOCITY = 22;
    public static final int SET_LISTENER_VELOCITY = 23;
    public static final int PLAY = 24;
    public static final int FEED_RAW_AUDIO_DATA = 25;
    public static final int PAUSE = 26;
    public static final int STOP = 27;
    public static final int REWIND = 28;
    public static final int FLUSH = 29;
    public static final int CULL = 30;
    public static final int ACTIVATE = 31;
    public static final int SET_TEMPORARY = 32;
    public static final int REMOVE_SOURCE = 33;
    public static final int MOVE_LISTENER = 34;
    public static final int SET_LISTENER_POSITION = 35;
    public static final int TURN_LISTENER = 36;
    public static final int SET_LISTENER_ANGLE = 37;
    public static final int SET_LISTENER_ORIENTATION = 38;
    public static final int SET_MASTER_VOLUME = 39;
    public static final int NEW_LIBRARY = 40;
    public byte[] buffer;
    public int[] intArgs;
    public float[] floatArgs;
    public long[] longArgs;
    public boolean[] boolArgs;
    public String[] stringArgs;
    public Class[] classArgs;
    public Object[] objectArgs;
    public int Command;
    
    public CommandObject(final int command) {
        this.Command = command;
    }
    
    public CommandObject(final int command, final int n) {
        this.Command = command;
        (this.intArgs = new int[1])[0] = n;
    }
    
    public CommandObject(final int command, final Class clazz) {
        this.Command = command;
        (this.classArgs = new Class[1])[0] = clazz;
    }
    
    public CommandObject(final int command, final float n) {
        this.Command = command;
        (this.floatArgs = new float[1])[0] = n;
    }
    
    public CommandObject(final int command, final String s) {
        this.Command = command;
        (this.stringArgs = new String[1])[0] = s;
    }
    
    public CommandObject(final int command, final Object o) {
        this.Command = command;
        (this.objectArgs = new Object[1])[0] = o;
    }
    
    public CommandObject(final int command, final String s, final Object o) {
        this.Command = command;
        (this.stringArgs = new String[1])[0] = s;
        (this.objectArgs = new Object[1])[0] = o;
    }
    
    public CommandObject(final int command, final String s, final byte[] buffer) {
        this.Command = command;
        (this.stringArgs = new String[1])[0] = s;
        this.buffer = buffer;
    }
    
    public CommandObject(final int command, final String s, final Object o, final long n) {
        this.Command = command;
        (this.stringArgs = new String[1])[0] = s;
        (this.objectArgs = new Object[1])[0] = o;
        (this.longArgs = new long[1])[0] = n;
    }
    
    public CommandObject(final int command, final String s, final Object o, final long n, final long n2) {
        this.Command = command;
        (this.stringArgs = new String[1])[0] = s;
        (this.objectArgs = new Object[1])[0] = o;
        (this.longArgs = new long[2])[0] = n;
        this.longArgs[1] = n2;
    }
    
    public CommandObject(final int command, final String s, final String s2) {
        this.Command = command;
        (this.stringArgs = new String[2])[0] = s;
        this.stringArgs[1] = s2;
    }
    
    public CommandObject(final int command, final String s, final int n) {
        this.Command = command;
        this.intArgs = new int[1];
        this.stringArgs = new String[1];
        this.intArgs[0] = n;
        this.stringArgs[0] = s;
    }
    
    public CommandObject(final int command, final String s, final float n) {
        this.Command = command;
        this.floatArgs = new float[1];
        this.stringArgs = new String[1];
        this.floatArgs[0] = n;
        this.stringArgs[0] = s;
    }
    
    public CommandObject(final int command, final String s, final boolean b) {
        this.Command = command;
        this.boolArgs = new boolean[1];
        this.stringArgs = new String[1];
        this.boolArgs[0] = b;
        this.stringArgs[0] = s;
    }
    
    public CommandObject(final int command, final float n, final float n2, final float n3) {
        this.Command = command;
        (this.floatArgs = new float[3])[0] = n;
        this.floatArgs[1] = n2;
        this.floatArgs[2] = n3;
    }
    
    public CommandObject(final int command, final String s, final float n, final float n2, final float n3) {
        this.Command = command;
        this.floatArgs = new float[3];
        this.stringArgs = new String[1];
        this.floatArgs[0] = n;
        this.floatArgs[1] = n2;
        this.floatArgs[2] = n3;
        this.stringArgs[0] = s;
    }
    
    public CommandObject(final int command, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.Command = command;
        (this.floatArgs = new float[6])[0] = n;
        this.floatArgs[1] = n2;
        this.floatArgs[2] = n3;
        this.floatArgs[3] = n4;
        this.floatArgs[4] = n5;
        this.floatArgs[5] = n6;
    }
    
    public CommandObject(final int command, final boolean b, final boolean b2, final boolean b3, final String s, final Object o, final float n, final float n2, final float n3, final int n4, final float n5) {
        this.Command = command;
        this.intArgs = new int[1];
        this.floatArgs = new float[4];
        this.boolArgs = new boolean[3];
        this.stringArgs = new String[1];
        this.objectArgs = new Object[1];
        this.intArgs[0] = n4;
        this.floatArgs[0] = n;
        this.floatArgs[1] = n2;
        this.floatArgs[2] = n3;
        this.floatArgs[3] = n5;
        this.boolArgs[0] = b;
        this.boolArgs[1] = b2;
        this.boolArgs[2] = b3;
        this.stringArgs[0] = s;
        this.objectArgs[0] = o;
    }
    
    public CommandObject(final int command, final boolean b, final boolean b2, final boolean b3, final String s, final Object o, final float n, final float n2, final float n3, final int n4, final float n5, final boolean b4) {
        this.Command = command;
        this.intArgs = new int[1];
        this.floatArgs = new float[4];
        this.boolArgs = new boolean[4];
        this.stringArgs = new String[1];
        this.objectArgs = new Object[1];
        this.intArgs[0] = n4;
        this.floatArgs[0] = n;
        this.floatArgs[1] = n2;
        this.floatArgs[2] = n3;
        this.floatArgs[3] = n5;
        this.boolArgs[0] = b;
        this.boolArgs[1] = b2;
        this.boolArgs[2] = b3;
        this.boolArgs[3] = b4;
        this.stringArgs[0] = s;
        this.objectArgs[0] = o;
    }
    
    public CommandObject(final int command, final Object o, final boolean b, final String s, final float n, final float n2, final float n3, final int n4, final float n5) {
        this.Command = command;
        this.intArgs = new int[1];
        this.floatArgs = new float[4];
        this.boolArgs = new boolean[1];
        this.stringArgs = new String[1];
        this.objectArgs = new Object[1];
        this.intArgs[0] = n4;
        this.floatArgs[0] = n;
        this.floatArgs[1] = n2;
        this.floatArgs[2] = n3;
        this.floatArgs[3] = n5;
        this.boolArgs[0] = b;
        this.stringArgs[0] = s;
        this.objectArgs[0] = o;
    }
}
