package net.minecraft.src;

import net.minecraft.server.*;

public class TileEntityCommandBlock extends TileEntity implements ICommandSender
{
    private int succesCount;
    private String command;
    private String commandSenderName;
    
    public TileEntityCommandBlock() {
        this.succesCount = 0;
        this.command = "";
        this.commandSenderName = "@";
    }
    
    public void setCommand(final String par1Str) {
        this.command = par1Str;
        this.onInventoryChanged();
    }
    
    public String getCommand() {
        return this.command;
    }
    
    public int executeCommandOnPowered(final World par1World) {
        if (par1World.isRemote) {
            return 0;
        }
        final MinecraftServer var2 = MinecraftServer.getServer();
        if (var2 != null && var2.isCommandBlockEnabled()) {
            final ICommandManager var3 = var2.getCommandManager();
            return var3.executeCommand(this, this.command);
        }
        return 0;
    }
    
    @Override
    public String getCommandSenderName() {
        return this.commandSenderName;
    }
    
    public void setCommandSenderName(final String par1Str) {
        this.commandSenderName = par1Str;
    }
    
    @Override
    public void sendChatToPlayer(final String par1Str) {
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int par1, final String par2Str) {
        return par1 <= 2;
    }
    
    @Override
    public String translateString(final String par1Str, final Object... par2ArrayOfObj) {
        return par1Str;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setString("Command", this.command);
        par1NBTTagCompound.setInteger("SuccessCount", this.succesCount);
        par1NBTTagCompound.setString("CustomName", this.commandSenderName);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.command = par1NBTTagCompound.getString("Command");
        this.succesCount = par1NBTTagCompound.getInteger("SuccessCount");
        if (par1NBTTagCompound.hasKey("CustomName")) {
            this.commandSenderName = par1NBTTagCompound.getString("CustomName");
        }
    }
    
    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(this.xCoord, this.yCoord, this.zCoord);
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, var1);
    }
    
    public int func_96103_d() {
        return this.succesCount;
    }
    
    public void func_96102_a(final int par1) {
        this.succesCount = par1;
    }
}
