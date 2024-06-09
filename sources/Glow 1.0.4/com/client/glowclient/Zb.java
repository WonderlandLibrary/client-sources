package com.client.glowclient;

import net.minecraftforge.fml.relauncher.*;
import mcp.*;
import javax.annotation.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

@SideOnly(Side.CLIENT)
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class zB extends RenderChunk
{
    public void rebuildChunk(final float p0, final float p1, final float p2, final ChunkCompileTaskGenerator p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock:()Ljava/util/concurrent/locks/ReentrantLock;
        //     5: invokevirtual   java/util/concurrent/locks/ReentrantLock.lock:()V
        //     8: aload           4
        //    10: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getStatus:()Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;
        //    13: getstatic       net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status.COMPILING:Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;
        //    16: if_acmpne       122
        //    19: aload_0        
        //    20: dup            
        //    21: invokevirtual   com/client/glowclient/zB.getPosition:()Lnet/minecraft/util/math/BlockPos;
        //    24: astore          5
        //    26: getfield        com/client/glowclient/zB.world:Lnet/minecraft/world/World;
        //    29: checkcast       Lcom/client/glowclient/XA;
        //    32: astore          6
        //    34: aload           5
        //    36: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //    39: iflt            76
        //    42: aload           5
        //    44: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //    47: iflt            76
        //    50: aload           5
        //    52: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //    55: aload           6
        //    57: invokevirtual   com/client/glowclient/XA.getWidth:()I
        //    60: if_icmpge       76
        //    63: aload           5
        //    65: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //    68: aload           6
        //    70: invokevirtual   com/client/glowclient/XA.getLength:()I
        //    73: if_icmplt       122
        //    76: new             Lnet/minecraft/client/renderer/chunk/SetVisibility;
        //    79: dup            
        //    80: invokespecial   net/minecraft/client/renderer/chunk/SetVisibility.<init>:()V
        //    83: dup            
        //    84: astore          5
        //    86: iconst_1       
        //    87: invokevirtual   net/minecraft/client/renderer/chunk/SetVisibility.setAllVisible:(Z)V
        //    90: new             Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //    93: dup            
        //    94: invokespecial   net/minecraft/client/renderer/chunk/CompiledChunk.<init>:()V
        //    97: astore          6
        //    99: aload           4
        //   101: aload           6
        //   103: dup            
        //   104: aload           5
        //   106: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.setVisibility:(Lnet/minecraft/client/renderer/chunk/SetVisibility;)V
        //   109: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.setCompiledChunk:(Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V
        //   112: aload           4
        //   114: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock:()Ljava/util/concurrent/locks/ReentrantLock;
        //   117: invokevirtual   java/util/concurrent/locks/ReentrantLock.unlock:()V
        //   120: return         
        //   121: athrow         
        //   122: aload           4
        //   124: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock:()Ljava/util/concurrent/locks/ReentrantLock;
        //   127: invokevirtual   java/util/concurrent/locks/ReentrantLock.unlock:()V
        //   130: aload_0        
        //   131: goto            149
        //   134: athrow         
        //   135: astore          5
        //   137: aload           5
        //   139: aload           4
        //   141: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock:()Ljava/util/concurrent/locks/ReentrantLock;
        //   144: invokevirtual   java/util/concurrent/locks/ReentrantLock.unlock:()V
        //   147: athrow         
        //   148: athrow         
        //   149: fload_1        
        //   150: fload_2        
        //   151: fload_3        
        //   152: aload           4
        //   154: invokespecial   net/minecraft/client/renderer/chunk/RenderChunk.rebuildChunk:(FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;)V
        //   157: return         
        //    StackMapTable: 00 07 FD 00 4C 07 00 2F 07 00 2D FF 00 2C 00 00 00 01 07 00 55 FF 00 00 00 05 07 00 02 02 02 02 07 00 0F 00 00 FF 00 0B 00 00 00 01 07 00 55 FF 00 00 00 05 07 00 02 02 02 02 07 00 0F 00 01 07 00 55 FF 00 0C 00 00 00 01 07 00 55 FF 00 00 00 05 07 00 02 02 02 02 07 00 0F 00 01 07 00 02
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type
        //  -----  -----  -----  -----  ----
        //  8      112    135    148    Any
        //  135    137    135    148    Any
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public zB(final World world, final RenderGlobal renderGlobal, final int n) {
        super(world, renderGlobal, n);
    }
    
    public ChunkCache createRegionRenderCache(final World world, final BlockPos blockPos, final BlockPos blockPos2, final int n) {
        return new EC(world, blockPos, blockPos2, n);
    }
}
