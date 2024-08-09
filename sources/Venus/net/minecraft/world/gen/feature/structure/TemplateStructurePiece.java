/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TemplateStructurePiece
extends StructurePiece {
    private static final Logger LOGGER = LogManager.getLogger();
    protected Template template;
    protected PlacementSettings placeSettings;
    protected BlockPos templatePosition;

    public TemplateStructurePiece(IStructurePieceType iStructurePieceType, int n) {
        super(iStructurePieceType, n);
    }

    public TemplateStructurePiece(IStructurePieceType iStructurePieceType, CompoundNBT compoundNBT) {
        super(iStructurePieceType, compoundNBT);
        this.templatePosition = new BlockPos(compoundNBT.getInt("TPX"), compoundNBT.getInt("TPY"), compoundNBT.getInt("TPZ"));
    }

    protected void setup(Template template, BlockPos blockPos, PlacementSettings placementSettings) {
        this.template = template;
        this.setCoordBaseMode(Direction.NORTH);
        this.templatePosition = blockPos;
        this.placeSettings = placementSettings;
        this.boundingBox = template.getMutableBoundingBox(placementSettings, blockPos);
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        compoundNBT.putInt("TPX", this.templatePosition.getX());
        compoundNBT.putInt("TPY", this.templatePosition.getY());
        compoundNBT.putInt("TPZ", this.templatePosition.getZ());
    }

    @Override
    public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        this.placeSettings.setBoundingBox(mutableBoundingBox);
        this.boundingBox = this.template.getMutableBoundingBox(this.placeSettings, this.templatePosition);
        if (this.template.func_237146_a_(iSeedReader, this.templatePosition, blockPos, this.placeSettings, random2, 1)) {
            Object object;
            for (Template.BlockInfo blockInfo : this.template.func_215381_a(this.templatePosition, this.placeSettings, Blocks.STRUCTURE_BLOCK)) {
                if (blockInfo.nbt == null || (object = StructureMode.valueOf(blockInfo.nbt.getString("mode"))) != StructureMode.DATA) continue;
                this.handleDataMarker(blockInfo.nbt.getString("metadata"), blockInfo.pos, iSeedReader, random2, mutableBoundingBox);
            }
            for (Template.BlockInfo blockInfo : this.template.func_215381_a(this.templatePosition, this.placeSettings, Blocks.JIGSAW)) {
                if (blockInfo.nbt == null) continue;
                object = blockInfo.nbt.getString("final_state");
                BlockStateParser blockStateParser = new BlockStateParser(new StringReader((String)object), false);
                BlockState blockState = Blocks.AIR.getDefaultState();
                try {
                    blockStateParser.parse(false);
                    BlockState blockState2 = blockStateParser.getState();
                    if (blockState2 != null) {
                        blockState = blockState2;
                    } else {
                        LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", object, (Object)blockInfo.pos);
                    }
                } catch (CommandSyntaxException commandSyntaxException) {
                    LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", object, (Object)blockInfo.pos);
                }
                iSeedReader.setBlockState(blockInfo.pos, blockState, 3);
            }
        }
        return false;
    }

    protected abstract void handleDataMarker(String var1, BlockPos var2, IServerWorld var3, Random var4, MutableBoundingBox var5);

    @Override
    public void offset(int n, int n2, int n3) {
        super.offset(n, n2, n3);
        this.templatePosition = this.templatePosition.add(n, n2, n3);
    }

    @Override
    public Rotation getRotation() {
        return this.placeSettings.getRotation();
    }
}

