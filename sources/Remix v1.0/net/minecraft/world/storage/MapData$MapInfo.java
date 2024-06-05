package net.minecraft.world.storage;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import java.util.*;

public class MapInfo
{
    public final EntityPlayer entityplayerObj;
    public int field_82569_d;
    private boolean field_176105_d;
    private int field_176106_e;
    private int field_176103_f;
    private int field_176104_g;
    private int field_176108_h;
    private int field_176109_i;
    
    public MapInfo(final EntityPlayer p_i2138_2_) {
        this.field_176105_d = true;
        this.field_176106_e = 0;
        this.field_176103_f = 0;
        this.field_176104_g = 127;
        this.field_176108_h = 127;
        this.entityplayerObj = p_i2138_2_;
    }
    
    public Packet func_176101_a(final ItemStack p_176101_1_) {
        if (this.field_176105_d) {
            this.field_176105_d = false;
            return new S34PacketMaps(p_176101_1_.getMetadata(), MapData.this.scale, MapData.this.playersVisibleOnMap.values(), MapData.this.colors, this.field_176106_e, this.field_176103_f, this.field_176104_g + 1 - this.field_176106_e, this.field_176108_h + 1 - this.field_176103_f);
        }
        return (this.field_176109_i++ % 5 == 0) ? new S34PacketMaps(p_176101_1_.getMetadata(), MapData.this.scale, MapData.this.playersVisibleOnMap.values(), MapData.this.colors, 0, 0, 0, 0) : null;
    }
    
    public void func_176102_a(final int p_176102_1_, final int p_176102_2_) {
        if (this.field_176105_d) {
            this.field_176106_e = Math.min(this.field_176106_e, p_176102_1_);
            this.field_176103_f = Math.min(this.field_176103_f, p_176102_2_);
            this.field_176104_g = Math.max(this.field_176104_g, p_176102_1_);
            this.field_176108_h = Math.max(this.field_176108_h, p_176102_2_);
        }
        else {
            this.field_176105_d = true;
            this.field_176106_e = p_176102_1_;
            this.field_176103_f = p_176102_2_;
            this.field_176104_g = p_176102_1_;
            this.field_176108_h = p_176102_2_;
        }
    }
}
