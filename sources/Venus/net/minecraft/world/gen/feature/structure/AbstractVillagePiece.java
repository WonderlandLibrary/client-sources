/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.EmptyJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbstractVillagePiece
extends StructurePiece {
    private static final Logger field_237000_d_ = LogManager.getLogger();
    protected final JigsawPiece jigsawPiece;
    protected BlockPos pos;
    private final int groundLevelDelta;
    protected final Rotation rotation;
    private final List<JigsawJunction> junctions = Lists.newArrayList();
    private final TemplateManager templateManager;

    public AbstractVillagePiece(TemplateManager templateManager, JigsawPiece jigsawPiece, BlockPos blockPos, int n, Rotation rotation, MutableBoundingBox mutableBoundingBox) {
        super(IStructurePieceType.field_242786_ad, 0);
        this.templateManager = templateManager;
        this.jigsawPiece = jigsawPiece;
        this.pos = blockPos;
        this.groundLevelDelta = n;
        this.rotation = rotation;
        this.boundingBox = mutableBoundingBox;
    }

    public AbstractVillagePiece(TemplateManager templateManager, CompoundNBT compoundNBT) {
        super(IStructurePieceType.field_242786_ad, compoundNBT);
        this.templateManager = templateManager;
        this.pos = new BlockPos(compoundNBT.getInt("PosX"), compoundNBT.getInt("PosY"), compoundNBT.getInt("PosZ"));
        this.groundLevelDelta = compoundNBT.getInt("ground_level_delta");
        this.jigsawPiece = JigsawPiece.field_236847_e_.parse(NBTDynamicOps.INSTANCE, compoundNBT.getCompound("pool_element")).resultOrPartial(field_237000_d_::error).orElse(EmptyJigsawPiece.INSTANCE);
        this.rotation = Rotation.valueOf(compoundNBT.getString("rotation"));
        this.boundingBox = this.jigsawPiece.getBoundingBox(templateManager, this.pos, this.rotation);
        ListNBT listNBT = compoundNBT.getList("junctions", 10);
        this.junctions.clear();
        listNBT.forEach(this::lambda$new$0);
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        compoundNBT.putInt("PosX", this.pos.getX());
        compoundNBT.putInt("PosY", this.pos.getY());
        compoundNBT.putInt("PosZ", this.pos.getZ());
        compoundNBT.putInt("ground_level_delta", this.groundLevelDelta);
        JigsawPiece.field_236847_e_.encodeStart(NBTDynamicOps.INSTANCE, this.jigsawPiece).resultOrPartial(field_237000_d_::error).ifPresent(arg_0 -> AbstractVillagePiece.lambda$readAdditional$1(compoundNBT, arg_0));
        compoundNBT.putString("rotation", this.rotation.name());
        ListNBT listNBT = new ListNBT();
        for (JigsawJunction jigsawJunction : this.junctions) {
            listNBT.add(jigsawJunction.func_236820_a_(NBTDynamicOps.INSTANCE).getValue());
        }
        compoundNBT.put("junctions", listNBT);
    }

    @Override
    public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        return this.func_237001_a_(iSeedReader, structureManager, chunkGenerator, random2, mutableBoundingBox, blockPos, true);
    }

    public boolean func_237001_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, BlockPos blockPos, boolean bl) {
        return this.jigsawPiece.func_230378_a_(this.templateManager, iSeedReader, structureManager, chunkGenerator, this.pos, blockPos, this.rotation, mutableBoundingBox, random2, bl);
    }

    @Override
    public void offset(int n, int n2, int n3) {
        super.offset(n, n2, n3);
        this.pos = this.pos.add(n, n2, n3);
    }

    @Override
    public Rotation getRotation() {
        return this.rotation;
    }

    public String toString() {
        return String.format("<%s | %s | %s | %s>", new Object[]{this.getClass().getSimpleName(), this.pos, this.rotation, this.jigsawPiece});
    }

    public JigsawPiece getJigsawPiece() {
        return this.jigsawPiece;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public int getGroundLevelDelta() {
        return this.groundLevelDelta;
    }

    public void addJunction(JigsawJunction jigsawJunction) {
        this.junctions.add(jigsawJunction);
    }

    public List<JigsawJunction> getJunctions() {
        return this.junctions;
    }

    private static void lambda$readAdditional$1(CompoundNBT compoundNBT, INBT iNBT) {
        compoundNBT.put("pool_element", iNBT);
    }

    private void lambda$new$0(INBT iNBT) {
        this.junctions.add(JigsawJunction.func_236819_a_(new Dynamic<INBT>(NBTDynamicOps.INSTANCE, iNBT)));
    }
}

