package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.World;

public class TileEntitySign extends TileEntity
{
    public final ITextComponent[] signText = new ITextComponent[] {new TextComponentString(""), new TextComponentString(""), new TextComponentString(""), new TextComponentString("")};

    /**
     * The index of the line currently being edited. Only used on client side, but defined on both. Note this is only
     * really used when the > < are going to be visible.
     */
    public int lineBeingEdited = -1;
    private boolean isEditable = true;
    private EntityPlayer player;
    private final CommandResultStats stats = new CommandResultStats();

    public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_)
    {
        super.func_189515_b(p_189515_1_);

        for (int i = 0; i < 4; ++i)
        {
            String s = ITextComponent.Serializer.componentToJson(this.signText[i]);
            p_189515_1_.setString("Text" + (i + 1), s);
        }

        this.stats.writeStatsToNBT(p_189515_1_);
        return p_189515_1_;
    }

    protected void func_190201_b(World p_190201_1_)
    {
        this.setWorldObj(p_190201_1_);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        this.isEditable = false;
        super.readFromNBT(compound);
        ICommandSender icommandsender = new ICommandSender()
        {
            public String getName()
            {
                return "Sign";
            }
            public ITextComponent getDisplayName()
            {
                return new TextComponentString(this.getName());
            }
            public void addChatMessage(ITextComponent component)
            {
            }
            public boolean canCommandSenderUseCommand(int permLevel, String commandName)
            {
                return true;
            }
            public BlockPos getPosition()
            {
                return TileEntitySign.this.pos;
            }
            public Vec3d getPositionVector()
            {
                return new Vec3d((double)TileEntitySign.this.pos.getX() + 0.5D, (double)TileEntitySign.this.pos.getY() + 0.5D, (double)TileEntitySign.this.pos.getZ() + 0.5D);
            }
            public World getEntityWorld()
            {
                return TileEntitySign.this.worldObj;
            }
            public Entity getCommandSenderEntity()
            {
                return null;
            }
            public boolean sendCommandFeedback()
            {
                return false;
            }
            public void setCommandStat(CommandResultStats.Type type, int amount)
            {
            }
            public MinecraftServer getServer()
            {
                return TileEntitySign.this.worldObj.getMinecraftServer();
            }
        };

        for (int i = 0; i < 4; ++i)
        {
            String s = compound.getString("Text" + (i + 1));
            ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(s);

            try
            {
                this.signText[i] = TextComponentUtils.processComponent(icommandsender, itextcomponent, (Entity)null);
            }
            catch (CommandException var7)
            {
                this.signText[i] = itextcomponent;
            }
        }

        this.stats.readStatsFromNBT(compound);
    }

    @Nullable
    public SPacketUpdateTileEntity func_189518_D_()
    {
        return new SPacketUpdateTileEntity(this.pos, 9, this.func_189517_E_());
    }

    public NBTTagCompound func_189517_E_()
    {
        return this.func_189515_b(new NBTTagCompound());
    }

    public boolean onlyOpsCanSetNbt()
    {
        return true;
    }

    public boolean getIsEditable()
    {
        return this.isEditable;
    }

    /**
     * Sets the sign's isEditable flag to the specified parameter.
     */
    public void setEditable(boolean isEditableIn)
    {
        this.isEditable = isEditableIn;

        if (!isEditableIn)
        {
            this.player = null;
        }
    }

    public void setPlayer(EntityPlayer playerIn)
    {
        this.player = playerIn;
    }

    public EntityPlayer getPlayer()
    {
        return this.player;
    }

    public boolean executeCommand(final EntityPlayer playerIn)
    {
        ICommandSender icommandsender = new ICommandSender()
        {
            public String getName()
            {
                return playerIn.getName();
            }
            public ITextComponent getDisplayName()
            {
                return playerIn.getDisplayName();
            }
            public void addChatMessage(ITextComponent component)
            {
            }
            public boolean canCommandSenderUseCommand(int permLevel, String commandName)
            {
                return permLevel <= 2;
            }
            public BlockPos getPosition()
            {
                return TileEntitySign.this.pos;
            }
            public Vec3d getPositionVector()
            {
                return new Vec3d((double)TileEntitySign.this.pos.getX() + 0.5D, (double)TileEntitySign.this.pos.getY() + 0.5D, (double)TileEntitySign.this.pos.getZ() + 0.5D);
            }
            public World getEntityWorld()
            {
                return playerIn.getEntityWorld();
            }
            public Entity getCommandSenderEntity()
            {
                return playerIn;
            }
            public boolean sendCommandFeedback()
            {
                return false;
            }
            public void setCommandStat(CommandResultStats.Type type, int amount)
            {
                if (TileEntitySign.this.worldObj != null && !TileEntitySign.this.worldObj.isRemote)
                {
                    TileEntitySign.this.stats.setCommandStatForSender(TileEntitySign.this.worldObj.getMinecraftServer(), this, type, amount);
                }
            }
            public MinecraftServer getServer()
            {
                return playerIn.getServer();
            }
        };

        for (ITextComponent itextcomponent : this.signText)
        {
            Style style = itextcomponent == null ? null : itextcomponent.getStyle();

            if (style != null && style.getClickEvent() != null)
            {
                ClickEvent clickevent = style.getClickEvent();

                if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
                {
                    playerIn.getServer().getCommandManager().executeCommand(icommandsender, clickevent.getValue());
                }
            }
        }

        return true;
    }

    public CommandResultStats getStats()
    {
        return this.stats;
    }
}
