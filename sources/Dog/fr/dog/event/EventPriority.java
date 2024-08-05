package fr.dog.event;

public class EventPriority {
    public static final byte VERY_LOW = 4;
    public static final byte LOW = 3;
    public static final byte NORMAL = 2;
    public static final byte HIGH = 1;
    public static final byte VERY_HIGH = 0;

    public static final byte[] VALUE_ARRAY = new byte[] {
            VERY_HIGH,
            HIGH,
            NORMAL,
            LOW,
            VERY_LOW
    };
}