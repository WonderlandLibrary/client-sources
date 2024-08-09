/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.CubeCoordinateIterator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;

public class SectionPos
extends Vector3i {
    private SectionPos(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    public static SectionPos of(int n, int n2, int n3) {
        return new SectionPos(n, n2, n3);
    }

    public static SectionPos from(BlockPos blockPos) {
        return new SectionPos(SectionPos.toChunk(blockPos.getX()), SectionPos.toChunk(blockPos.getY()), SectionPos.toChunk(blockPos.getZ()));
    }

    public static SectionPos from(ChunkPos chunkPos, int n) {
        return new SectionPos(chunkPos.x, n, chunkPos.z);
    }

    public static SectionPos from(Entity entity2) {
        return new SectionPos(SectionPos.toChunk(MathHelper.floor(entity2.getPosX())), SectionPos.toChunk(MathHelper.floor(entity2.getPosY())), SectionPos.toChunk(MathHelper.floor(entity2.getPosZ())));
    }

    public static SectionPos from(long l) {
        return new SectionPos(SectionPos.extractX(l), SectionPos.extractY(l), SectionPos.extractZ(l));
    }

    public static long withOffset(long l, Direction direction) {
        return SectionPos.withOffset(l, direction.getXOffset(), direction.getYOffset(), direction.getZOffset());
    }

    public static long withOffset(long l, int n, int n2, int n3) {
        return SectionPos.asLong(SectionPos.extractX(l) + n, SectionPos.extractY(l) + n2, SectionPos.extractZ(l) + n3);
    }

    public static int toChunk(int n) {
        return n >> 4;
    }

    public static int mask(int n) {
        return n & 0xF;
    }

    public static short toRelativeOffset(BlockPos blockPos) {
        int n = SectionPos.mask(blockPos.getX());
        int n2 = SectionPos.mask(blockPos.getY());
        int n3 = SectionPos.mask(blockPos.getZ());
        return (short)(n << 8 | n3 << 4 | n2 << 0);
    }

    public static int func_243641_a(short s) {
        return s >>> 8 & 0xF;
    }

    public static int func_243642_b(short s) {
        return s >>> 0 & 0xF;
    }

    public static int func_243643_c(short s) {
        return s >>> 4 & 0xF;
    }

    public int func_243644_d(short s) {
        return this.getWorldStartX() + SectionPos.func_243641_a(s);
    }

    public int func_243645_e(short s) {
        return this.getWorldStartY() + SectionPos.func_243642_b(s);
    }

    public int func_243646_f(short s) {
        return this.getWorldStartZ() + SectionPos.func_243643_c(s);
    }

    public BlockPos func_243647_g(short s) {
        return new BlockPos(this.func_243644_d(s), this.func_243645_e(s), this.func_243646_f(s));
    }

    public static int toWorld(int n) {
        return n << 4;
    }

    public static int extractX(long l) {
        return (int)(l << 0 >> 42);
    }

    public static int extractY(long l) {
        return (int)(l << 44 >> 44);
    }

    public static int extractZ(long l) {
        return (int)(l << 22 >> 42);
    }

    public int getSectionX() {
        return this.getX();
    }

    public int getSectionY() {
        return this.getY();
    }

    public int getSectionZ() {
        return this.getZ();
    }

    public int getWorldStartX() {
        return this.getSectionX() << 4;
    }

    public int getWorldStartY() {
        return this.getSectionY() << 4;
    }

    public int getWorldStartZ() {
        return this.getSectionZ() << 4;
    }

    public int getWorldEndX() {
        return (this.getSectionX() << 4) + 15;
    }

    public int getWorldEndY() {
        return (this.getSectionY() << 4) + 15;
    }

    public int getWorldEndZ() {
        return (this.getSectionZ() << 4) + 15;
    }

    public static long worldToSection(long l) {
        return SectionPos.asLong(SectionPos.toChunk(BlockPos.unpackX(l)), SectionPos.toChunk(BlockPos.unpackY(l)), SectionPos.toChunk(BlockPos.unpackZ(l)));
    }

    public static long toSectionColumnPos(long l) {
        return l & 0xFFFFFFFFFFF00000L;
    }

    public BlockPos asBlockPos() {
        return new BlockPos(SectionPos.toWorld(this.getSectionX()), SectionPos.toWorld(this.getSectionY()), SectionPos.toWorld(this.getSectionZ()));
    }

    public BlockPos getCenter() {
        int n = 8;
        return this.asBlockPos().add(8, 8, 8);
    }

    public ChunkPos asChunkPos() {
        return new ChunkPos(this.getSectionX(), this.getSectionZ());
    }

    public static long asLong(int n, int n2, int n3) {
        long l = 0L;
        l |= ((long)n & 0x3FFFFFL) << 42;
        return (l |= ((long)n2 & 0xFFFFFL) << 0) | ((long)n3 & 0x3FFFFFL) << 20;
    }

    public long asLong() {
        return SectionPos.asLong(this.getSectionX(), this.getSectionY(), this.getSectionZ());
    }

    public Stream<BlockPos> allBlocksWithin() {
        return BlockPos.getAllInBox(this.getWorldStartX(), this.getWorldStartY(), this.getWorldStartZ(), this.getWorldEndX(), this.getWorldEndY(), this.getWorldEndZ());
    }

    public static Stream<SectionPos> getAllInBox(SectionPos sectionPos, int n) {
        int n2 = sectionPos.getSectionX();
        int n3 = sectionPos.getSectionY();
        int n4 = sectionPos.getSectionZ();
        return SectionPos.getAllInBox(n2 - n, n3 - n, n4 - n, n2 + n, n3 + n, n4 + n);
    }

    public static Stream<SectionPos> func_229421_b_(ChunkPos chunkPos, int n) {
        int n2 = chunkPos.x;
        int n3 = chunkPos.z;
        return SectionPos.getAllInBox(n2 - n, 0, n3 - n, n2 + n, 15, n3 + n);
    }

    public static Stream<SectionPos> getAllInBox(int n, int n2, int n3, int n4, int n5, int n6) {
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<SectionPos>((long)((n4 - n + 1) * (n5 - n2 + 1) * (n6 - n3 + 1)), 64, n, n2, n3, n4, n5, n6){
            final CubeCoordinateIterator field_218394_a;
            final int val$p_218168_0_;
            final int val$p_218168_1_;
            final int val$p_218168_2_;
            final int val$p_218168_3_;
            final int val$p_218168_4_;
            final int val$p_218168_5_;
            {
                this.val$p_218168_0_ = n2;
                this.val$p_218168_1_ = n3;
                this.val$p_218168_2_ = n4;
                this.val$p_218168_3_ = n5;
                this.val$p_218168_4_ = n6;
                this.val$p_218168_5_ = n7;
                super(l, n);
                this.field_218394_a = new CubeCoordinateIterator(this.val$p_218168_0_, this.val$p_218168_1_, this.val$p_218168_2_, this.val$p_218168_3_, this.val$p_218168_4_, this.val$p_218168_5_);
            }

            @Override
            public boolean tryAdvance(Consumer<? super SectionPos> consumer) {
                if (this.field_218394_a.hasNext()) {
                    consumer.accept(new SectionPos(this.field_218394_a.getX(), this.field_218394_a.getY(), this.field_218394_a.getZ()));
                    return false;
                }
                return true;
            }
        }, false);
    }
}

