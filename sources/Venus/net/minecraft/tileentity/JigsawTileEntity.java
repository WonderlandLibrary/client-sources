/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.JigsawBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

public class JigsawTileEntity
extends TileEntity {
    private ResourceLocation field_235658_a_ = new ResourceLocation("empty");
    private ResourceLocation field_235659_b_ = new ResourceLocation("empty");
    private ResourceLocation field_235660_c_ = new ResourceLocation("empty");
    private OrientationType field_235661_g_ = OrientationType.ROLLABLE;
    private String finalState = "minecraft:air";

    public JigsawTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public JigsawTileEntity() {
        this(TileEntityType.JIGSAW);
    }

    public ResourceLocation func_235668_d_() {
        return this.field_235658_a_;
    }

    public ResourceLocation func_235669_f_() {
        return this.field_235659_b_;
    }

    public ResourceLocation func_235670_g_() {
        return this.field_235660_c_;
    }

    public String getFinalState() {
        return this.finalState;
    }

    public OrientationType func_235671_j_() {
        return this.field_235661_g_;
    }

    public void func_235664_a_(ResourceLocation resourceLocation) {
        this.field_235658_a_ = resourceLocation;
    }

    public void func_235666_b_(ResourceLocation resourceLocation) {
        this.field_235659_b_ = resourceLocation;
    }

    public void func_235667_c_(ResourceLocation resourceLocation) {
        this.field_235660_c_ = resourceLocation;
    }

    public void setFinalState(String string) {
        this.finalState = string;
    }

    public void func_235662_a_(OrientationType orientationType) {
        this.field_235661_g_ = orientationType;
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        compoundNBT.putString("name", this.field_235658_a_.toString());
        compoundNBT.putString("target", this.field_235659_b_.toString());
        compoundNBT.putString("pool", this.field_235660_c_.toString());
        compoundNBT.putString("final_state", this.finalState);
        compoundNBT.putString("joint", this.field_235661_g_.getString());
        return compoundNBT;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.field_235658_a_ = new ResourceLocation(compoundNBT.getString("name"));
        this.field_235659_b_ = new ResourceLocation(compoundNBT.getString("target"));
        this.field_235660_c_ = new ResourceLocation(compoundNBT.getString("pool"));
        this.finalState = compoundNBT.getString("final_state");
        this.field_235661_g_ = OrientationType.func_235673_a_(compoundNBT.getString("joint")).orElseGet(() -> JigsawTileEntity.lambda$read$0(blockState));
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 12, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public void func_235665_a_(ServerWorld serverWorld, int n, boolean bl) {
        ChunkGenerator chunkGenerator = serverWorld.getChunkProvider().getChunkGenerator();
        TemplateManager templateManager = serverWorld.getStructureTemplateManager();
        StructureManager structureManager = serverWorld.func_241112_a_();
        Random random2 = serverWorld.getRandom();
        BlockPos blockPos = this.getPos();
        ArrayList<AbstractVillagePiece> arrayList = Lists.newArrayList();
        Template template = new Template();
        template.takeBlocksFromWorld(serverWorld, blockPos, new BlockPos(1, 1, 1), false, null);
        SingleJigsawPiece singleJigsawPiece = new SingleJigsawPiece(template);
        AbstractVillagePiece abstractVillagePiece = new AbstractVillagePiece(templateManager, singleJigsawPiece, blockPos, 1, Rotation.NONE, new MutableBoundingBox(blockPos, blockPos));
        JigsawManager.func_242838_a(serverWorld.func_241828_r(), abstractVillagePiece, n, AbstractVillagePiece::new, chunkGenerator, templateManager, arrayList, random2);
        for (AbstractVillagePiece abstractVillagePiece2 : arrayList) {
            abstractVillagePiece2.func_237001_a_(serverWorld, structureManager, chunkGenerator, random2, MutableBoundingBox.func_236990_b_(), blockPos, bl);
        }
    }

    private static OrientationType lambda$read$0(BlockState blockState) {
        return JigsawBlock.getConnectingDirection(blockState).getAxis().isHorizontal() ? OrientationType.ALIGNED : OrientationType.ROLLABLE;
    }

    public static enum OrientationType implements IStringSerializable
    {
        ROLLABLE("rollable"),
        ALIGNED("aligned");

        private final String field_235672_c_;

        private OrientationType(String string2) {
            this.field_235672_c_ = string2;
        }

        @Override
        public String getString() {
            return this.field_235672_c_;
        }

        public static Optional<OrientationType> func_235673_a_(String string) {
            return Arrays.stream(OrientationType.values()).filter(arg_0 -> OrientationType.lambda$func_235673_a_$0(string, arg_0)).findFirst();
        }

        private static boolean lambda$func_235673_a_$0(String string, OrientationType orientationType) {
            return orientationType.getString().equals(string);
        }
    }
}

