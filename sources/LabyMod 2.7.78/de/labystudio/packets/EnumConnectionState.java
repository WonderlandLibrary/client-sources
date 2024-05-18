package de.labystudio.packets;

public enum EnumConnectionState
{
    HELLO(-1),
    LOGIN(0),
    PLAY(1),
    ALL(2),
    OFFLINE(3);

    private int number;

    private EnumConnectionState(int t)
    {
        this.number = t;
    }

    public int getConnectionStateId()
    {
        return this.number;
    }
}
