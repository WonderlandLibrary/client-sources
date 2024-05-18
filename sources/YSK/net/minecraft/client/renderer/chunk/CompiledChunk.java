package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.tileentity.*;
import com.google.common.collect.*;
import net.minecraft.util.*;

public class CompiledChunk
{
    private final boolean[] layersUsed;
    private WorldRenderer.State state;
    private SetVisibility setVisibility;
    private final boolean[] layersStarted;
    private boolean empty;
    public static final CompiledChunk DUMMY;
    private final List<TileEntity> tileEntities;
    
    public List<TileEntity> getTileEntities() {
        return this.tileEntities;
    }
    
    protected void setLayerUsed(final EnumWorldBlockLayer enumWorldBlockLayer) {
        this.empty = ("".length() != 0);
        this.layersUsed[enumWorldBlockLayer.ordinal()] = (" ".length() != 0);
    }
    
    public CompiledChunk() {
        this.layersUsed = new boolean[EnumWorldBlockLayer.values().length];
        this.layersStarted = new boolean[EnumWorldBlockLayer.values().length];
        this.empty = (" ".length() != 0);
        this.tileEntities = (List<TileEntity>)Lists.newArrayList();
        this.setVisibility = new SetVisibility();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean isVisible(final EnumFacing enumFacing, final EnumFacing enumFacing2) {
        return this.setVisibility.isVisible(enumFacing, enumFacing2);
    }
    
    static {
        DUMMY = new CompiledChunk() {
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (3 < 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            protected void setLayerUsed(final EnumWorldBlockLayer enumWorldBlockLayer) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public boolean isVisible(final EnumFacing enumFacing, final EnumFacing enumFacing2) {
                return "".length() != 0;
            }
            
            @Override
            public void setLayerStarted(final EnumWorldBlockLayer enumWorldBlockLayer) {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public void setLayerStarted(final EnumWorldBlockLayer enumWorldBlockLayer) {
        this.layersStarted[enumWorldBlockLayer.ordinal()] = (" ".length() != 0);
    }
    
    public void setVisibility(final SetVisibility setVisibility) {
        this.setVisibility = setVisibility;
    }
    
    public void addTileEntity(final TileEntity tileEntity) {
        this.tileEntities.add(tileEntity);
    }
    
    public WorldRenderer.State getState() {
        return this.state;
    }
    
    public boolean isLayerStarted(final EnumWorldBlockLayer enumWorldBlockLayer) {
        return this.layersStarted[enumWorldBlockLayer.ordinal()];
    }
    
    public boolean isEmpty() {
        return this.empty;
    }
    
    public boolean isLayerEmpty(final EnumWorldBlockLayer enumWorldBlockLayer) {
        int n;
        if (this.layersUsed[enumWorldBlockLayer.ordinal()]) {
            n = "".length();
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public void setState(final WorldRenderer.State state) {
        this.state = state;
    }
}
