package net.java.games.input;

public static final class PortType
{
    private final String name;
    public static final PortType UNKNOWN;
    public static final PortType USB;
    public static final PortType GAME;
    public static final PortType NETWORK;
    public static final PortType SERIAL;
    public static final PortType I8042;
    public static final PortType PARALLEL;
    
    protected PortType(final String name) {
        this.name = name;
    }
    
    public String toString() {
        return this.name;
    }
    
    static {
        UNKNOWN = new PortType("Unknown");
        USB = new PortType("USB port");
        GAME = new PortType("Game port");
        NETWORK = new PortType("Network port");
        SERIAL = new PortType("Serial port");
        I8042 = new PortType("i8042 (PS/2)");
        PARALLEL = new PortType("Parallel port");
    }
}
