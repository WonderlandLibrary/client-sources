/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StructureBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.template.IntegrityProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

public class StructureBlockTileEntity
extends TileEntity {
    private ResourceLocation name;
    private String author = "";
    private String metadata = "";
    private BlockPos position = new BlockPos(0, 1, 0);
    private BlockPos size = BlockPos.ZERO;
    private Mirror mirror = Mirror.NONE;
    private Rotation rotation = Rotation.NONE;
    private StructureMode mode = StructureMode.DATA;
    private boolean ignoreEntities = true;
    private boolean powered;
    private boolean showAir;
    private boolean showBoundingBox = true;
    private float integrity = 1.0f;
    private long seed;

    public StructureBlockTileEntity() {
        super(TileEntityType.STRUCTURE_BLOCK);
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 96.0;
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        compoundNBT.putString("name", this.getName());
        compoundNBT.putString("author", this.author);
        compoundNBT.putString("metadata", this.metadata);
        compoundNBT.putInt("posX", this.position.getX());
        compoundNBT.putInt("posY", this.position.getY());
        compoundNBT.putInt("posZ", this.position.getZ());
        compoundNBT.putInt("sizeX", this.size.getX());
        compoundNBT.putInt("sizeY", this.size.getY());
        compoundNBT.putInt("sizeZ", this.size.getZ());
        compoundNBT.putString("rotation", this.rotation.toString());
        compoundNBT.putString("mirror", this.mirror.toString());
        compoundNBT.putString("mode", this.mode.toString());
        compoundNBT.putBoolean("ignoreEntities", this.ignoreEntities);
        compoundNBT.putBoolean("powered", this.powered);
        compoundNBT.putBoolean("showair", this.showAir);
        compoundNBT.putBoolean("showboundingbox", this.showBoundingBox);
        compoundNBT.putFloat("integrity", this.integrity);
        compoundNBT.putLong("seed", this.seed);
        return compoundNBT;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.setName(compoundNBT.getString("name"));
        this.author = compoundNBT.getString("author");
        this.metadata = compoundNBT.getString("metadata");
        int n = MathHelper.clamp(compoundNBT.getInt("posX"), -48, 48);
        int n2 = MathHelper.clamp(compoundNBT.getInt("posY"), -48, 48);
        int n3 = MathHelper.clamp(compoundNBT.getInt("posZ"), -48, 48);
        this.position = new BlockPos(n, n2, n3);
        int n4 = MathHelper.clamp(compoundNBT.getInt("sizeX"), 0, 48);
        int n5 = MathHelper.clamp(compoundNBT.getInt("sizeY"), 0, 48);
        int n6 = MathHelper.clamp(compoundNBT.getInt("sizeZ"), 0, 48);
        this.size = new BlockPos(n4, n5, n6);
        try {
            this.rotation = Rotation.valueOf(compoundNBT.getString("rotation"));
        } catch (IllegalArgumentException illegalArgumentException) {
            this.rotation = Rotation.NONE;
        }
        try {
            this.mirror = Mirror.valueOf(compoundNBT.getString("mirror"));
        } catch (IllegalArgumentException illegalArgumentException) {
            this.mirror = Mirror.NONE;
        }
        try {
            this.mode = StructureMode.valueOf(compoundNBT.getString("mode"));
        } catch (IllegalArgumentException illegalArgumentException) {
            this.mode = StructureMode.DATA;
        }
        this.ignoreEntities = compoundNBT.getBoolean("ignoreEntities");
        this.powered = compoundNBT.getBoolean("powered");
        this.showAir = compoundNBT.getBoolean("showair");
        this.showBoundingBox = compoundNBT.getBoolean("showboundingbox");
        this.integrity = compoundNBT.contains("integrity") ? compoundNBT.getFloat("integrity") : 1.0f;
        this.seed = compoundNBT.getLong("seed");
        this.updateBlockState();
    }

    private void updateBlockState() {
        BlockPos blockPos;
        BlockState blockState;
        if (this.world != null && (blockState = this.world.getBlockState(blockPos = this.getPos())).isIn(Blocks.STRUCTURE_BLOCK)) {
            this.world.setBlockState(blockPos, (BlockState)blockState.with(StructureBlock.MODE, this.mode), 1);
        }
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 7, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public boolean usedBy(PlayerEntity playerEntity) {
        if (!playerEntity.canUseCommandBlock()) {
            return true;
        }
        if (playerEntity.getEntityWorld().isRemote) {
            playerEntity.openStructureBlock(this);
        }
        return false;
    }

    public String getName() {
        return this.name == null ? "" : this.name.toString();
    }

    public String func_227014_f_() {
        return this.name == null ? "" : this.name.getPath();
    }

    public boolean hasName() {
        return this.name != null;
    }

    public void setName(@Nullable String string) {
        this.setName(StringUtils.isNullOrEmpty(string) ? null : ResourceLocation.tryCreate(string));
    }

    public void setName(@Nullable ResourceLocation resourceLocation) {
        this.name = resourceLocation;
    }

    public void createdBy(LivingEntity livingEntity) {
        this.author = livingEntity.getName().getString();
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public void setPosition(BlockPos blockPos) {
        this.position = blockPos;
    }

    public BlockPos getStructureSize() {
        return this.size;
    }

    public void setSize(BlockPos blockPos) {
        this.size = blockPos;
    }

    public Mirror getMirror() {
        return this.mirror;
    }

    public void setMirror(Mirror mirror) {
        this.mirror = mirror;
    }

    public Rotation getRotation() {
        return this.rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public String getMetadata() {
        return this.metadata;
    }

    public void setMetadata(String string) {
        this.metadata = string;
    }

    public StructureMode getMode() {
        return this.mode;
    }

    public void setMode(StructureMode structureMode) {
        this.mode = structureMode;
        BlockState blockState = this.world.getBlockState(this.getPos());
        if (blockState.isIn(Blocks.STRUCTURE_BLOCK)) {
            this.world.setBlockState(this.getPos(), (BlockState)blockState.with(StructureBlock.MODE, structureMode), 1);
        }
    }

    public void nextMode() {
        switch (1.$SwitchMap$net$minecraft$state$properties$StructureMode[this.getMode().ordinal()]) {
            case 1: {
                this.setMode(StructureMode.LOAD);
                break;
            }
            case 2: {
                this.setMode(StructureMode.CORNER);
                break;
            }
            case 3: {
                this.setMode(StructureMode.DATA);
                break;
            }
            case 4: {
                this.setMode(StructureMode.SAVE);
            }
        }
    }

    public boolean ignoresEntities() {
        return this.ignoreEntities;
    }

    public void setIgnoresEntities(boolean bl) {
        this.ignoreEntities = bl;
    }

    public float getIntegrity() {
        return this.integrity;
    }

    public void setIntegrity(float f) {
        this.integrity = f;
    }

    public long getSeed() {
        return this.seed;
    }

    public void setSeed(long l) {
        this.seed = l;
    }

    public boolean detectSize() {
        BlockPos blockPos;
        if (this.mode != StructureMode.SAVE) {
            return true;
        }
        BlockPos blockPos2 = this.getPos();
        int n = 80;
        BlockPos blockPos3 = new BlockPos(blockPos2.getX() - 80, 0, blockPos2.getZ() - 80);
        List<StructureBlockTileEntity> list = this.getNearbyCornerBlocks(blockPos3, blockPos = new BlockPos(blockPos2.getX() + 80, 255, blockPos2.getZ() + 80));
        List<StructureBlockTileEntity> list2 = this.filterRelatedCornerBlocks(list);
        if (list2.size() < 1) {
            return true;
        }
        MutableBoundingBox mutableBoundingBox = this.calculateEnclosingBoundingBox(blockPos2, list2);
        if (mutableBoundingBox.maxX - mutableBoundingBox.minX > 1 && mutableBoundingBox.maxY - mutableBoundingBox.minY > 1 && mutableBoundingBox.maxZ - mutableBoundingBox.minZ > 1) {
            this.position = new BlockPos(mutableBoundingBox.minX - blockPos2.getX() + 1, mutableBoundingBox.minY - blockPos2.getY() + 1, mutableBoundingBox.minZ - blockPos2.getZ() + 1);
            this.size = new BlockPos(mutableBoundingBox.maxX - mutableBoundingBox.minX - 1, mutableBoundingBox.maxY - mutableBoundingBox.minY - 1, mutableBoundingBox.maxZ - mutableBoundingBox.minZ - 1);
            this.markDirty();
            BlockState blockState = this.world.getBlockState(blockPos2);
            this.world.notifyBlockUpdate(blockPos2, blockState, blockState, 3);
            return false;
        }
        return true;
    }

    private List<StructureBlockTileEntity> filterRelatedCornerBlocks(List<StructureBlockTileEntity> list) {
        Predicate<StructureBlockTileEntity> predicate = this::lambda$filterRelatedCornerBlocks$0;
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    private List<StructureBlockTileEntity> getNearbyCornerBlocks(BlockPos blockPos, BlockPos blockPos2) {
        ArrayList<StructureBlockTileEntity> arrayList = Lists.newArrayList();
        for (BlockPos blockPos3 : BlockPos.getAllInBoxMutable(blockPos, blockPos2)) {
            TileEntity tileEntity;
            BlockState blockState = this.world.getBlockState(blockPos3);
            if (!blockState.isIn(Blocks.STRUCTURE_BLOCK) || (tileEntity = this.world.getTileEntity(blockPos3)) == null || !(tileEntity instanceof StructureBlockTileEntity)) continue;
            arrayList.add((StructureBlockTileEntity)tileEntity);
        }
        return arrayList;
    }

    private MutableBoundingBox calculateEnclosingBoundingBox(BlockPos blockPos, List<StructureBlockTileEntity> list) {
        MutableBoundingBox mutableBoundingBox;
        if (list.size() > 1) {
            BlockPos blockPos2 = list.get(0).getPos();
            mutableBoundingBox = new MutableBoundingBox(blockPos2, blockPos2);
        } else {
            mutableBoundingBox = new MutableBoundingBox(blockPos, blockPos);
        }
        for (StructureBlockTileEntity structureBlockTileEntity : list) {
            BlockPos blockPos3 = structureBlockTileEntity.getPos();
            if (blockPos3.getX() < mutableBoundingBox.minX) {
                mutableBoundingBox.minX = blockPos3.getX();
            } else if (blockPos3.getX() > mutableBoundingBox.maxX) {
                mutableBoundingBox.maxX = blockPos3.getX();
            }
            if (blockPos3.getY() < mutableBoundingBox.minY) {
                mutableBoundingBox.minY = blockPos3.getY();
            } else if (blockPos3.getY() > mutableBoundingBox.maxY) {
                mutableBoundingBox.maxY = blockPos3.getY();
            }
            if (blockPos3.getZ() < mutableBoundingBox.minZ) {
                mutableBoundingBox.minZ = blockPos3.getZ();
                continue;
            }
            if (blockPos3.getZ() <= mutableBoundingBox.maxZ) continue;
            mutableBoundingBox.maxZ = blockPos3.getZ();
        }
        return mutableBoundingBox;
    }

    public boolean save() {
        return this.save(false);
    }

    public boolean save(boolean bl) {
        if (this.mode == StructureMode.SAVE && !this.world.isRemote && this.name != null) {
            Template template;
            BlockPos blockPos = this.getPos().add(this.position);
            ServerWorld serverWorld = (ServerWorld)this.world;
            TemplateManager templateManager = serverWorld.getStructureTemplateManager();
            try {
                template = templateManager.getTemplateDefaulted(this.name);
            } catch (ResourceLocationException resourceLocationException) {
                return true;
            }
            template.takeBlocksFromWorld(this.world, blockPos, this.size, !this.ignoreEntities, Blocks.STRUCTURE_VOID);
            template.setAuthor(this.author);
            if (bl) {
                try {
                    return templateManager.writeToFile(this.name);
                } catch (ResourceLocationException resourceLocationException) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public boolean func_242687_a(ServerWorld serverWorld) {
        return this.func_242688_a(serverWorld, false);
    }

    private static Random func_214074_b(long l) {
        return l == 0L ? new Random(Util.milliTime()) : new Random(l);
    }

    public boolean func_242688_a(ServerWorld serverWorld, boolean bl) {
        if (this.mode == StructureMode.LOAD && this.name != null) {
            Template template;
            TemplateManager templateManager = serverWorld.getStructureTemplateManager();
            try {
                template = templateManager.getTemplate(this.name);
            } catch (ResourceLocationException resourceLocationException) {
                return true;
            }
            return template == null ? false : this.func_242689_a(serverWorld, bl, template);
        }
        return true;
    }

    public boolean func_242689_a(ServerWorld serverWorld, boolean bl, Template template) {
        Object object;
        BlockPos blockPos;
        boolean bl2;
        BlockPos blockPos2 = this.getPos();
        if (!StringUtils.isNullOrEmpty(template.getAuthor())) {
            this.author = template.getAuthor();
        }
        if (!(bl2 = this.size.equals(blockPos = template.getSize()))) {
            this.size = blockPos;
            this.markDirty();
            object = serverWorld.getBlockState(blockPos2);
            serverWorld.notifyBlockUpdate(blockPos2, (BlockState)object, (BlockState)object, 3);
        }
        if (bl && !bl2) {
            return true;
        }
        object = new PlacementSettings().setMirror(this.mirror).setRotation(this.rotation).setIgnoreEntities(this.ignoreEntities).setChunk(null);
        if (this.integrity < 1.0f) {
            ((PlacementSettings)object).clearProcessors().addProcessor(new IntegrityProcessor(MathHelper.clamp(this.integrity, 0.0f, 1.0f))).setRandom(StructureBlockTileEntity.func_214074_b(this.seed));
        }
        BlockPos blockPos3 = blockPos2.add(this.position);
        template.func_237144_a_(serverWorld, blockPos3, (PlacementSettings)object, StructureBlockTileEntity.func_214074_b(this.seed));
        return false;
    }

    public void unloadStructure() {
        if (this.name != null) {
            ServerWorld serverWorld = (ServerWorld)this.world;
            TemplateManager templateManager = serverWorld.getStructureTemplateManager();
            templateManager.remove(this.name);
        }
    }

    public boolean isStructureLoadable() {
        if (this.mode == StructureMode.LOAD && !this.world.isRemote && this.name != null) {
            ServerWorld serverWorld = (ServerWorld)this.world;
            TemplateManager templateManager = serverWorld.getStructureTemplateManager();
            try {
                return templateManager.getTemplate(this.name) != null;
            } catch (ResourceLocationException resourceLocationException) {
                return true;
            }
        }
        return true;
    }

    public boolean isPowered() {
        return this.powered;
    }

    public void setPowered(boolean bl) {
        this.powered = bl;
    }

    public boolean showsAir() {
        return this.showAir;
    }

    public void setShowAir(boolean bl) {
        this.showAir = bl;
    }

    public boolean showsBoundingBox() {
        return this.showBoundingBox;
    }

    public void setShowBoundingBox(boolean bl) {
        this.showBoundingBox = bl;
    }

    private boolean lambda$filterRelatedCornerBlocks$0(StructureBlockTileEntity structureBlockTileEntity) {
        return structureBlockTileEntity.mode == StructureMode.CORNER && Objects.equals(this.name, structureBlockTileEntity.name);
    }

    public static enum UpdateCommand {
        UPDATE_DATA,
        SAVE_AREA,
        LOAD_AREA,
        SCAN_AREA;

    }
}

