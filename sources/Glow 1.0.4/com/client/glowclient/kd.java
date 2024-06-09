package com.client.glowclient;

import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import java.util.*;

public class kd implements IMessage, IMessageHandler<kd, IMessage>
{
    public int M;
    public short[][][] G;
    public int d;
    public int L;
    public List<TileEntity> A;
    public byte[][][] B;
    public List<Entity> b;
    
    public IMessage onMessage(final IMessage message, final MessageContext messageContext) {
        return this.onMessage((kd)message, messageContext);
    }
    
    public kd(final J j, final int m, final int d, final int l) {
        final int n = 16;
        super();
        this.M = m;
        this.d = d;
        this.L = l;
        final int n2 = 16;
        this.G = new short[n][n2][n2];
        final int n3 = 16;
        this.B = new byte[n3][n3][n3];
        this.A = new ArrayList<TileEntity>();
        this.b = new ArrayList<Entity>();
        final wc wc = new wc();
        int n4;
        int i = n4 = 0;
        while (i < 16) {
            int n5;
            int k = n5 = 0;
            while (k < 16) {
                int n7;
                int n6 = n7 = 0;
                while (n6 < 16) {
                    wc.set(m + n4, d + n5, l + n7);
                    final IBlockState m2;
                    final Block block = (m2 = j.M((BlockPos)wc)).getBlock();
                    final int idForObject = Block.REGISTRY.getIDForObject((Object)block);
                    final wc wc2 = wc;
                    this.G[n4][n5][n7] = (short)idForObject;
                    this.B[n4][n5][n7] = (byte)block.getMetaFromState(m2);
                    final TileEntity m3 = j.M((BlockPos)wc2);
                    if (m3 != null) {
                        this.A.add(m3);
                    }
                    n6 = ++n7;
                }
                k = ++n5;
            }
            i = ++n4;
        }
    }
    
    public void fromBytes(final ByteBuf byteBuf) {
        final int n = 16;
        this.M = byteBuf.readShort();
        this.d = byteBuf.readShort();
        this.L = byteBuf.readShort();
        final int n2 = 16;
        this.G = new short[n][n2][n2];
        final int n3 = 16;
        this.B = new byte[n3][n3][n3];
        final int i = 0;
        this.A = new ArrayList<TileEntity>();
        this.b = new ArrayList<Entity>();
        int n4 = i;
        while (i < 16) {
            int n5;
            int j = n5 = 0;
            while (j < 16) {
                int n6;
                int k = n6 = 0;
                while (k < 16) {
                    this.G[n4][n5][n6] = byteBuf.readShort();
                    final byte[] array = this.B[n4][n5];
                    final int n7 = n6;
                    final byte byte1 = byteBuf.readByte();
                    ++n6;
                    array[n7] = byte1;
                    k = n6;
                }
                j = ++n5;
            }
            ++n4;
        }
        this.A = KA.M(ByteBufUtils.readTag(byteBuf), this.A);
        this.b = KA.D(ByteBufUtils.readTag(byteBuf), this.b);
    }
    
    public kd() {
        super();
    }
    
    private void M(final J j) {
        final wc wc = new wc();
        int n;
        int i = n = 0;
        while (i < 16) {
            int n2;
            int k = n2 = 0;
            while (k < 16) {
                int n3;
                int l = n3 = 0;
                while (l < 16) {
                    final short n4 = this.G[n][n2][n3];
                    final byte b = this.B[n][n2][n3];
                    final Block block = (Block)Block.REGISTRY.getObjectById((int)n4);
                    wc.set(this.M + n, this.d + n2, this.L + n3);
                    final wc wc2 = wc;
                    final Block block2 = block;
                    ++n3;
                    j.M(wc2, block2.getStateFromMeta((int)b));
                    l = n3;
                }
                k = ++n2;
            }
            i = ++n;
        }
        final Iterator<TileEntity> iterator2;
        Iterator<TileEntity> iterator = iterator2 = this.A.iterator();
        while (iterator.hasNext()) {
            final TileEntity tileEntity = iterator2.next();
            iterator = iterator2;
            j.M(tileEntity.getPos(), tileEntity);
        }
    }
    
    public IMessage onMessage(final kd kd, final MessageContext messageContext) {
        kd.M(QC.B.A);
        return (IMessage)new hc(kd.M, kd.d, kd.L);
    }
    
    public void toBytes(final ByteBuf byteBuf) {
        byteBuf.writeShort(this.M);
        byteBuf.writeShort(this.d);
        byteBuf.writeShort(this.L);
        int i;
        int n = i = 0;
        while (i < 16) {
            int n2;
            int j = n2 = 0;
            while (j < 16) {
                int n3;
                int k = n3 = 0;
                while (k < 16) {
                    byteBuf.writeShort((int)this.G[n][n2][n3]);
                    byteBuf.writeByte((int)this.B[n][n2][n3++]);
                    k = n3;
                }
                j = ++n2;
            }
            i = ++n;
        }
        ByteBufUtils.writeTag(byteBuf, KA.M(this.A));
        ByteBufUtils.writeTag(byteBuf, KA.D(this.b));
    }
}
