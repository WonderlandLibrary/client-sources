package net.minecraft.tileentity;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.command.*;
import com.google.gson.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.event.*;
import net.minecraft.server.*;
import net.minecraft.util.*;

public class TileEntitySign extends TileEntity
{
    public final IChatComponent[] signText;
    private final CommandResultStats field_174883_i;
    public int lineBeingEdited;
    private boolean isEditable;
    private EntityPlayer field_145917_k;
    
    public TileEntitySign() {
        this.signText = new IChatComponent[] { new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("") };
        this.field_174883_i = new CommandResultStats();
        this.lineBeingEdited = -1;
        this.isEditable = true;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);
        for (int var2 = 0; var2 < 4; ++var2) {
            final String var3 = IChatComponent.Serializer.componentToJson(this.signText[var2]);
            compound.setString("Text" + (var2 + 1), var3);
        }
        this.field_174883_i.func_179670_b(compound);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        this.isEditable = false;
        super.readFromNBT(compound);
        final ICommandSender var2 = new ICommandSender() {
            @Override
            public String getName() {
                return "Sign";
            }
            
            @Override
            public IChatComponent getDisplayName() {
                return new ChatComponentText(this.getName());
            }
            
            @Override
            public void addChatMessage(final IChatComponent message) {
            }
            
            @Override
            public boolean canCommandSenderUseCommand(final int permissionLevel, final String command) {
                return true;
            }
            
            @Override
            public BlockPos getPosition() {
                return TileEntitySign.this.pos;
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(TileEntitySign.this.pos.getX() + 0.5, TileEntitySign.this.pos.getY() + 0.5, TileEntitySign.this.pos.getZ() + 0.5);
            }
            
            @Override
            public World getEntityWorld() {
                return TileEntitySign.this.worldObj;
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return null;
            }
            
            @Override
            public boolean sendCommandFeedback() {
                return false;
            }
            
            @Override
            public void func_174794_a(final CommandResultStats.Type p_174794_1_, final int p_174794_2_) {
            }
        };
        for (int var3 = 0; var3 < 4; ++var3) {
            final String var4 = compound.getString("Text" + (var3 + 1));
            try {
                final IChatComponent var5 = IChatComponent.Serializer.jsonToComponent(var4);
                try {
                    this.signText[var3] = ChatComponentProcessor.func_179985_a(var2, var5, null);
                }
                catch (CommandException var6) {
                    this.signText[var3] = var5;
                }
            }
            catch (JsonParseException var7) {
                this.signText[var3] = new ChatComponentText(var4);
            }
        }
        this.field_174883_i.func_179668_a(compound);
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final IChatComponent[] var1 = new IChatComponent[4];
        System.arraycopy(this.signText, 0, var1, 0, 4);
        return new S33PacketUpdateSign(this.worldObj, this.pos, var1);
    }
    
    public boolean getIsEditable() {
        return this.isEditable;
    }
    
    public void setEditable(final boolean p_145913_1_) {
        if (!(this.isEditable = p_145913_1_)) {
            this.field_145917_k = null;
        }
    }
    
    public void func_145912_a(final EntityPlayer p_145912_1_) {
        this.field_145917_k = p_145912_1_;
    }
    
    public EntityPlayer func_145911_b() {
        return this.field_145917_k;
    }
    
    public boolean func_174882_b(final EntityPlayer p_174882_1_) {
        final ICommandSender var2 = new ICommandSender() {
            @Override
            public String getName() {
                return p_174882_1_.getName();
            }
            
            @Override
            public IChatComponent getDisplayName() {
                return p_174882_1_.getDisplayName();
            }
            
            @Override
            public void addChatMessage(final IChatComponent message) {
            }
            
            @Override
            public boolean canCommandSenderUseCommand(final int permissionLevel, final String command) {
                return true;
            }
            
            @Override
            public BlockPos getPosition() {
                return TileEntitySign.this.pos;
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(TileEntitySign.this.pos.getX() + 0.5, TileEntitySign.this.pos.getY() + 0.5, TileEntitySign.this.pos.getZ() + 0.5);
            }
            
            @Override
            public World getEntityWorld() {
                return p_174882_1_.getEntityWorld();
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return p_174882_1_;
            }
            
            @Override
            public boolean sendCommandFeedback() {
                return false;
            }
            
            @Override
            public void func_174794_a(final CommandResultStats.Type p_174794_1_, final int p_174794_2_) {
                TileEntitySign.this.field_174883_i.func_179672_a(this, p_174794_1_, p_174794_2_);
            }
        };
        for (int var3 = 0; var3 < this.signText.length; ++var3) {
            final ChatStyle var4 = (this.signText[var3] == null) ? null : this.signText[var3].getChatStyle();
            if (var4 != null && var4.getChatClickEvent() != null) {
                final ClickEvent var5 = var4.getChatClickEvent();
                if (var5.getAction() == ClickEvent.Action.RUN_COMMAND) {
                    MinecraftServer.getServer().getCommandManager().executeCommand(var2, var5.getValue());
                }
            }
        }
        return true;
    }
    
    public CommandResultStats func_174880_d() {
        return this.field_174883_i;
    }
}
