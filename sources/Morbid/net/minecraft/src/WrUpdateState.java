package net.minecraft.src;

import java.util.*;

public class WrUpdateState
{
    public ChunkCache chunkcache;
    public RenderBlocks renderblocks;
    public HashSet setOldEntityRenders;
    public int renderPass;
    public int y;
    public boolean flag;
    public boolean hasRenderedBlocks;
    public boolean hasGlList;
    
    public WrUpdateState() {
        this.chunkcache = null;
        this.renderblocks = null;
        this.setOldEntityRenders = null;
        this.renderPass = 0;
        this.y = 0;
        this.flag = false;
        this.hasRenderedBlocks = false;
        this.hasGlList = false;
    }
}
