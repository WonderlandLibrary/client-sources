package net.minecraft.tileentity;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class TileEntityStructure extends TileEntity
{
    private String name = "";
    private String author = "";
    private String metadata = "";
    private BlockPos position = new BlockPos(0, 1, 0);
    private BlockPos size = BlockPos.ORIGIN;
    private Mirror mirror = Mirror.NONE;
    private Rotation rotation = Rotation.NONE;
    private TileEntityStructure.Mode mode = TileEntityStructure.Mode.DATA;
    private boolean ignoreEntities = true;
    private boolean field_189727_n;
    private boolean field_189728_o;
    private boolean field_189729_p = true;
    private float field_189730_q = 1.0F;
    private long field_189731_r = 0L;

    public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_)
    {
        super.func_189515_b(p_189515_1_);
        p_189515_1_.setString("name", this.name);
        p_189515_1_.setString("author", this.author);
        p_189515_1_.setString("metadata", this.metadata);
        p_189515_1_.setInteger("posX", this.position.getX());
        p_189515_1_.setInteger("posY", this.position.getY());
        p_189515_1_.setInteger("posZ", this.position.getZ());
        p_189515_1_.setInteger("sizeX", this.size.getX());
        p_189515_1_.setInteger("sizeY", this.size.getY());
        p_189515_1_.setInteger("sizeZ", this.size.getZ());
        p_189515_1_.setString("rotation", this.rotation.toString());
        p_189515_1_.setString("mirror", this.mirror.toString());
        p_189515_1_.setString("mode", this.mode.toString());
        p_189515_1_.setBoolean("ignoreEntities", this.ignoreEntities);
        p_189515_1_.setBoolean("powered", this.field_189727_n);
        p_189515_1_.setBoolean("showair", this.field_189728_o);
        p_189515_1_.setBoolean("showboundingbox", this.field_189729_p);
        p_189515_1_.setFloat("integrity", this.field_189730_q);
        p_189515_1_.setLong("seed", this.field_189731_r);
        return p_189515_1_;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.setName(compound.getString("name"));
        this.author = compound.getString("author");
        this.metadata = compound.getString("metadata");
        int i = MathHelper.clamp_int(compound.getInteger("posX"), -32, 32);
        int j = MathHelper.clamp_int(compound.getInteger("posY"), -32, 32);
        int k = MathHelper.clamp_int(compound.getInteger("posZ"), -32, 32);
        this.position = new BlockPos(i, j, k);
        int l = MathHelper.clamp_int(compound.getInteger("sizeX"), 0, 32);
        int i1 = MathHelper.clamp_int(compound.getInteger("sizeY"), 0, 32);
        int j1 = MathHelper.clamp_int(compound.getInteger("sizeZ"), 0, 32);
        this.size = new BlockPos(l, i1, j1);

        try
        {
            this.rotation = Rotation.valueOf(compound.getString("rotation"));
        }
        catch (IllegalArgumentException var11)
        {
            this.rotation = Rotation.NONE;
        }

        try
        {
            this.mirror = Mirror.valueOf(compound.getString("mirror"));
        }
        catch (IllegalArgumentException var10)
        {
            this.mirror = Mirror.NONE;
        }

        try
        {
            this.mode = TileEntityStructure.Mode.valueOf(compound.getString("mode"));
        }
        catch (IllegalArgumentException var9)
        {
            this.mode = TileEntityStructure.Mode.DATA;
        }

        this.ignoreEntities = compound.getBoolean("ignoreEntities");
        this.field_189727_n = compound.getBoolean("powered");
        this.field_189728_o = compound.getBoolean("showair");
        this.field_189729_p = compound.getBoolean("showboundingbox");

        if (compound.hasKey("integrity"))
        {
            this.field_189730_q = compound.getFloat("integrity");
        }
        else
        {
            this.field_189730_q = 1.0F;
        }

        this.field_189731_r = compound.getLong("seed");
        this.func_189704_J();
    }

    private void func_189704_J()
    {
        if (this.worldObj != null)
        {
            BlockPos blockpos = this.getPos();
            IBlockState iblockstate = this.worldObj.getBlockState(blockpos);

            if (iblockstate.getBlock() == Blocks.STRUCTURE_BLOCK)
            {
                this.worldObj.setBlockState(blockpos, iblockstate.withProperty(BlockStructure.MODE, this.mode), 2);
            }
        }
    }

    @Nullable
    public SPacketUpdateTileEntity func_189518_D_()
    {
        return new SPacketUpdateTileEntity(this.pos, 7, this.func_189517_E_());
    }

    public NBTTagCompound func_189517_E_()
    {
        return this.func_189515_b(new NBTTagCompound());
    }

    public boolean func_189701_a(EntityPlayer p_189701_1_)
    {
        if (!p_189701_1_.func_189808_dh())
        {
            return false;
        }
        else
        {
            if (p_189701_1_.getEntityWorld().isRemote)
            {
                p_189701_1_.func_189807_a(this);
            }

            return true;
        }
    }

    public String func_189715_d()
    {
        return this.name;
    }

    public void setName(String nameIn)
    {
        String s = nameIn;

        for (char c0 : ChatAllowedCharacters.field_189861_b)
        {
            s = s.replace(c0, '_');
        }

        this.name = s;
    }

    public void func_189720_a(EntityLivingBase p_189720_1_)
    {
        if (!StringUtils.isNullOrEmpty(p_189720_1_.getName()))
        {
            this.author = p_189720_1_.getName();
        }
    }

    public BlockPos func_189711_e()
    {
        return this.position;
    }

    public void setPosition(BlockPos posIn)
    {
        this.position = posIn;
    }

    public BlockPos func_189717_g()
    {
        return this.size;
    }

    public void setSize(BlockPos sizeIn)
    {
        this.size = sizeIn;
    }

    public Mirror func_189716_h()
    {
        return this.mirror;
    }

    public void setMirror(Mirror mirrorIn)
    {
        this.mirror = mirrorIn;
    }

    public Rotation func_189726_i()
    {
        return this.rotation;
    }

    public void setRotation(Rotation rotationIn)
    {
        this.rotation = rotationIn;
    }

    public String func_189708_j()
    {
        return this.metadata;
    }

    public void setMetadata(String metadataIn)
    {
        this.metadata = metadataIn;
    }

    public TileEntityStructure.Mode func_189700_k()
    {
        return this.mode;
    }

    public void setMode(TileEntityStructure.Mode modeIn)
    {
        this.mode = modeIn;
        IBlockState iblockstate = this.worldObj.getBlockState(this.getPos());

        if (iblockstate.getBlock() == Blocks.STRUCTURE_BLOCK)
        {
            this.worldObj.setBlockState(this.getPos(), iblockstate.withProperty(BlockStructure.MODE, modeIn), 2);
        }
    }

    public void func_189724_l()
    {
        switch (this.func_189700_k())
        {
            case SAVE:
                this.setMode(TileEntityStructure.Mode.LOAD);
                break;

            case LOAD:
                this.setMode(TileEntityStructure.Mode.CORNER);
                break;

            case CORNER:
                this.setMode(TileEntityStructure.Mode.DATA);
                break;

            case DATA:
                this.setMode(TileEntityStructure.Mode.SAVE);
        }
    }

    public boolean func_189713_m()
    {
        return this.ignoreEntities;
    }

    public void setIgnoresEntities(boolean ignoreEntitiesIn)
    {
        this.ignoreEntities = ignoreEntitiesIn;
    }

    public float func_189702_n()
    {
        return this.field_189730_q;
    }

    public void func_189718_a(float p_189718_1_)
    {
        this.field_189730_q = p_189718_1_;
    }

    public long func_189719_o()
    {
        return this.field_189731_r;
    }

    public void func_189725_a(long p_189725_1_)
    {
        this.field_189731_r = p_189725_1_;
    }

    public boolean detectSize()
    {
        if (this.mode != TileEntityStructure.Mode.SAVE)
        {
            return false;
        }
        else
        {
            BlockPos blockpos = this.getPos();
            int i = 80;
            BlockPos blockpos1 = new BlockPos(blockpos.getX() - 80, 0, blockpos.getZ() - 80);
            BlockPos blockpos2 = new BlockPos(blockpos.getX() + 80, 255, blockpos.getZ() + 80);
            List<TileEntityStructure> list = this.getNearbyCornerBlocks(blockpos1, blockpos2);
            List<TileEntityStructure> list1 = this.filterRelatedCornerBlocks(list);

            if (list1.size() < 1)
            {
                return false;
            }
            else
            {
                StructureBoundingBox structureboundingbox = this.calculateEnclosingBoundingBox(blockpos, list1);

                if (structureboundingbox.maxX - structureboundingbox.minX > 1 && structureboundingbox.maxY - structureboundingbox.minY > 1 && structureboundingbox.maxZ - structureboundingbox.minZ > 1)
                {
                    this.position = new BlockPos(structureboundingbox.minX - blockpos.getX() + 1, structureboundingbox.minY - blockpos.getY() + 1, structureboundingbox.minZ - blockpos.getZ() + 1);
                    this.size = new BlockPos(structureboundingbox.maxX - structureboundingbox.minX - 1, structureboundingbox.maxY - structureboundingbox.minY - 1, structureboundingbox.maxZ - structureboundingbox.minZ - 1);
                    this.markDirty();
                    IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
                    this.worldObj.notifyBlockUpdate(blockpos, iblockstate, iblockstate, 3);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
    }

    private List<TileEntityStructure> filterRelatedCornerBlocks(List<TileEntityStructure> p_184415_1_)
    {
        Iterable<TileEntityStructure> iterable = Iterables.filter(p_184415_1_, new Predicate<TileEntityStructure>()
        {
            public boolean apply(@Nullable TileEntityStructure p_apply_1_)
            {
                return p_apply_1_.mode == TileEntityStructure.Mode.CORNER && TileEntityStructure.this.name.equals(p_apply_1_.name);
            }
        });
        return Lists.newArrayList(iterable);
    }

    private List<TileEntityStructure> getNearbyCornerBlocks(BlockPos p_184418_1_, BlockPos p_184418_2_)
    {
        List<TileEntityStructure> list = Lists.<TileEntityStructure>newArrayList();

        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(p_184418_1_, p_184418_2_))
        {
            IBlockState iblockstate = this.worldObj.getBlockState(blockpos$mutableblockpos);

            if (iblockstate.getBlock() == Blocks.STRUCTURE_BLOCK)
            {
                TileEntity tileentity = this.worldObj.getTileEntity(blockpos$mutableblockpos);

                if (tileentity != null && tileentity instanceof TileEntityStructure)
                {
                    list.add((TileEntityStructure)tileentity);
                }
            }
        }

        return list;
    }

    private StructureBoundingBox calculateEnclosingBoundingBox(BlockPos p_184416_1_, List<TileEntityStructure> p_184416_2_)
    {
        StructureBoundingBox structureboundingbox;

        if (p_184416_2_.size() > 1)
        {
            BlockPos blockpos = ((TileEntityStructure)p_184416_2_.get(0)).getPos();
            structureboundingbox = new StructureBoundingBox(blockpos, blockpos);
        }
        else
        {
            structureboundingbox = new StructureBoundingBox(p_184416_1_, p_184416_1_);
        }

        for (TileEntityStructure tileentitystructure : p_184416_2_)
        {
            BlockPos blockpos1 = tileentitystructure.getPos();

            if (blockpos1.getX() < structureboundingbox.minX)
            {
                structureboundingbox.minX = blockpos1.getX();
            }
            else if (blockpos1.getX() > structureboundingbox.maxX)
            {
                structureboundingbox.maxX = blockpos1.getX();
            }

            if (blockpos1.getY() < structureboundingbox.minY)
            {
                structureboundingbox.minY = blockpos1.getY();
            }
            else if (blockpos1.getY() > structureboundingbox.maxY)
            {
                structureboundingbox.maxY = blockpos1.getY();
            }

            if (blockpos1.getZ() < structureboundingbox.minZ)
            {
                structureboundingbox.minZ = blockpos1.getZ();
            }
            else if (blockpos1.getZ() > structureboundingbox.maxZ)
            {
                structureboundingbox.maxZ = blockpos1.getZ();
            }
        }

        return structureboundingbox;
    }

    public void func_189705_a(ByteBuf p_189705_1_)
    {
        p_189705_1_.writeInt(this.pos.getX());
        p_189705_1_.writeInt(this.pos.getY());
        p_189705_1_.writeInt(this.pos.getZ());
    }

    public boolean save()
    {
        return this.func_189712_b(true);
    }

    public boolean func_189712_b(boolean p_189712_1_)
    {
        if (this.mode == TileEntityStructure.Mode.SAVE && !this.worldObj.isRemote && !StringUtils.isNullOrEmpty(this.name))
        {
            BlockPos blockpos = this.getPos().add(this.position);
            WorldServer worldserver = (WorldServer)this.worldObj;
            MinecraftServer minecraftserver = this.worldObj.getMinecraftServer();
            TemplateManager templatemanager = worldserver.getStructureTemplateManager();
            Template template = templatemanager.getTemplate(minecraftserver, new ResourceLocation(this.name));
            template.takeBlocksFromWorld(this.worldObj, blockpos, this.size, !this.ignoreEntities, Blocks.field_189881_dj);
            template.setAuthor(this.author);
            return !p_189712_1_ || templatemanager.writeTemplate(minecraftserver, new ResourceLocation(this.name));
        }
        else
        {
            return false;
        }
    }

    public boolean load()
    {
        return this.func_189714_c(true);
    }

    public boolean func_189714_c(boolean p_189714_1_)
    {
        if (this.mode == TileEntityStructure.Mode.LOAD && !this.worldObj.isRemote && !StringUtils.isNullOrEmpty(this.name))
        {
            BlockPos blockpos = this.getPos();
            BlockPos blockpos1 = blockpos.add(this.position);
            WorldServer worldserver = (WorldServer)this.worldObj;
            MinecraftServer minecraftserver = this.worldObj.getMinecraftServer();
            TemplateManager templatemanager = worldserver.getStructureTemplateManager();
            Template template = templatemanager.func_189942_b(minecraftserver, new ResourceLocation(this.name));

            if (template == null)
            {
                return false;
            }
            else
            {
                if (!StringUtils.isNullOrEmpty(template.getAuthor()))
                {
                    this.author = template.getAuthor();
                }

                BlockPos blockpos2 = template.getSize();
                boolean flag = this.size.equals(blockpos2);

                if (!flag)
                {
                    this.size = blockpos2;
                    this.markDirty();
                    IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
                    this.worldObj.notifyBlockUpdate(blockpos, iblockstate, iblockstate, 3);
                }

                if (p_189714_1_ && !flag)
                {
                    return false;
                }
                else
                {
                    PlacementSettings placementsettings = (new PlacementSettings()).setMirror(this.mirror).setRotation(this.rotation).setIgnoreEntities(this.ignoreEntities).setChunk((ChunkPos)null).setReplacedBlock((Block)null).setIgnoreStructureBlock(false);

                    if (this.field_189730_q < 1.0F)
                    {
                        placementsettings.func_189946_a(MathHelper.clamp_float(this.field_189730_q, 0.0F, 1.0F)).func_189949_a(Long.valueOf(this.field_189731_r));
                    }

                    template.addBlocksToWorldChunk(this.worldObj, blockpos1, placementsettings);
                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }

    public void func_189706_E()
    {
        WorldServer worldserver = (WorldServer)this.worldObj;
        TemplateManager templatemanager = worldserver.getStructureTemplateManager();
        templatemanager.func_189941_a(new ResourceLocation(this.name));
    }

    public boolean func_189709_F()
    {
        if (this.mode == TileEntityStructure.Mode.LOAD && !this.worldObj.isRemote)
        {
            WorldServer worldserver = (WorldServer)this.worldObj;
            MinecraftServer minecraftserver = this.worldObj.getMinecraftServer();
            TemplateManager templatemanager = worldserver.getStructureTemplateManager();
            return templatemanager.func_189942_b(minecraftserver, new ResourceLocation(this.name)) != null;
        }
        else
        {
            return false;
        }
    }

    public boolean func_189722_G()
    {
        return this.field_189727_n;
    }

    public void func_189723_d(boolean p_189723_1_)
    {
        this.field_189727_n = p_189723_1_;
    }

    public boolean func_189707_H()
    {
        return this.field_189728_o;
    }

    public void func_189703_e(boolean p_189703_1_)
    {
        this.field_189728_o = p_189703_1_;
    }

    public boolean func_189721_I()
    {
        return this.field_189729_p;
    }

    public void func_189710_f(boolean p_189710_1_)
    {
        this.field_189729_p = p_189710_1_;
    }

    @Nullable

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    public ITextComponent getDisplayName()
    {
        return new TextComponentTranslation("structure_block.hover." + this.mode.modeName, new Object[] {this.mode == TileEntityStructure.Mode.DATA ? this.metadata : this.name});
    }

    public static enum Mode implements IStringSerializable
    {
        SAVE("save", 0),
        LOAD("load", 1),
        CORNER("corner", 2),
        DATA("data", 3);

        private static final TileEntityStructure.Mode[] MODES = new TileEntityStructure.Mode[values().length];
        private final String modeName;
        private final int modeId;

        private Mode(String modeNameIn, int modeIdIn)
        {
            this.modeName = modeNameIn;
            this.modeId = modeIdIn;
        }

        public String getName()
        {
            return this.modeName;
        }

        public int getModeId()
        {
            return this.modeId;
        }

        public static TileEntityStructure.Mode getById(int id)
        {
            return id >= 0 && id < MODES.length ? MODES[id] : MODES[0];
        }

        static {
            for (TileEntityStructure.Mode tileentitystructure$mode : values())
            {
                MODES[tileentitystructure$mode.getModeId()] = tileentitystructure$mode;
            }
        }
    }
}
