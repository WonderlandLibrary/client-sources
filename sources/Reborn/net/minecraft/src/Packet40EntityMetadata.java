package net.minecraft.src;

import java.util.*;
import java.io.*;

public class Packet40EntityMetadata extends Packet
{
    public int entityId;
    private List metadata;
    
    public Packet40EntityMetadata() {
    }
    
    public Packet40EntityMetadata(final int par1, final DataWatcher par2DataWatcher, final boolean par3) {
        this.entityId = par1;
        if (par3) {
            this.metadata = par2DataWatcher.getAllWatched();
        }
        else {
            this.metadata = par2DataWatcher.unwatchAndReturnAllWatched();
        }
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.metadata = DataWatcher.readWatchableObjects(par1DataInputStream);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        DataWatcher.writeObjectsInListToStream(this.metadata, par1DataOutputStream);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleEntityMetadata(this);
    }
    
    @Override
    public int getPacketSize() {
        return 5;
    }
    
    public List getMetadata() {
        return this.metadata;
    }
}
