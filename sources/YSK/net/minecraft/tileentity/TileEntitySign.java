package net.minecraft.tileentity;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.command.*;
import com.google.gson.*;
import net.minecraft.event.*;
import net.minecraft.server.*;
import net.minecraft.util.*;

public class TileEntitySign extends TileEntity
{
    private final CommandResultStats stats;
    private boolean isEditable;
    public final IChatComponent[] signText;
    private static final String[] I;
    private EntityPlayer player;
    public int lineBeingEdited;
    
    public TileEntitySign() {
        final IChatComponent[] signText = new IChatComponent[0x70 ^ 0x74];
        signText["".length()] = new ChatComponentText(TileEntitySign.I["".length()]);
        signText[" ".length()] = new ChatComponentText(TileEntitySign.I[" ".length()]);
        signText["  ".length()] = new ChatComponentText(TileEntitySign.I["  ".length()]);
        signText["   ".length()] = new ChatComponentText(TileEntitySign.I["   ".length()]);
        this.signText = signText;
        this.lineBeingEdited = -" ".length();
        this.isEditable = (" ".length() != 0);
        this.stats = new CommandResultStats();
    }
    
    public boolean getIsEditable() {
        return this.isEditable;
    }
    
    public void setPlayer(final EntityPlayer player) {
        this.player = player;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    public CommandResultStats getStats() {
        return this.stats;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public void setEditable(final boolean isEditable) {
        if (!(this.isEditable = isEditable)) {
            this.player = null;
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        int i = "".length();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (i < (0x13 ^ 0x17)) {
            nbtTagCompound.setString(TileEntitySign.I[0x88 ^ 0x8C] + (i + " ".length()), IChatComponent.Serializer.componentToJson(this.signText[i]));
            ++i;
        }
        this.stats.writeStatsToNBT(nbtTagCompound);
    }
    
    private static void I() {
        (I = new String[0x5D ^ 0x5B])["".length()] = I("", "ageGK");
        TileEntitySign.I[" ".length()] = I("", "veAOG");
        TileEntitySign.I["  ".length()] = I("", "XNOpw");
        TileEntitySign.I["   ".length()] = I("", "HJBjh");
        TileEntitySign.I[0x66 ^ 0x62] = I("0\u00147.", "dqOZp");
        TileEntitySign.I[0x67 ^ 0x62] = I("\f!>\u0001", "XDFuj");
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final IChatComponent[] array = new IChatComponent[0x35 ^ 0x31];
        System.arraycopy(this.signText, "".length(), array, "".length(), 0x38 ^ 0x3C);
        return new S33PacketUpdateSign(this.worldObj, this.pos, array);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.isEditable = ("".length() != 0);
        super.readFromNBT(nbtTagCompound);
        final ICommandSender commandSender = new ICommandSender(this) {
            final TileEntitySign this$0;
            private static final String[] I;
            
            static {
                I();
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.pos.getX() + 0.5, this.this$0.pos.getY() + 0.5, this.this$0.pos.getZ() + 0.5);
            }
            
            @Override
            public IChatComponent getDisplayName() {
                return new ChatComponentText(this.getName());
            }
            
            @Override
            public boolean sendCommandFeedback() {
                return "".length() != 0;
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\u0011\u0002\u000e>", "BkiPZ");
            }
            
            @Override
            public void setCommandStat(final CommandResultStats.Type type, final int n) {
            }
            
            @Override
            public World getEntityWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public boolean canCommandSenderUseCommand(final int n, final String s) {
                return " ".length() != 0;
            }
            
            @Override
            public BlockPos getPosition() {
                return this.this$0.pos;
            }
            
            @Override
            public String getName() {
                return TileEntitySign$1.I["".length()];
            }
            
            @Override
            public void addChatMessage(final IChatComponent chatComponent) {
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return null;
            }
        };
        int i = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i < (0xA2 ^ 0xA6)) {
            final String string = nbtTagCompound.getString(TileEntitySign.I[0x62 ^ 0x67] + (i + " ".length()));
            try {
                final IChatComponent jsonToComponent = IChatComponent.Serializer.jsonToComponent(string);
                try {
                    this.signText[i] = ChatComponentProcessor.processComponent(commandSender, jsonToComponent, null);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                catch (CommandException ex) {
                    this.signText[i] = jsonToComponent;
                    "".length();
                    if (1 < -1) {
                        throw null;
                    }
                }
            }
            catch (JsonParseException ex2) {
                this.signText[i] = new ChatComponentText(string);
            }
            ++i;
        }
        this.stats.readStatsFromNBT(nbtTagCompound);
    }
    
    public boolean executeCommand(final EntityPlayer entityPlayer) {
        final ICommandSender commandSender = new ICommandSender(this, entityPlayer) {
            final TileEntitySign this$0;
            private final EntityPlayer val$playerIn;
            
            @Override
            public void addChatMessage(final IChatComponent chatComponent) {
            }
            
            @Override
            public boolean canCommandSenderUseCommand(final int n, final String s) {
                if (n <= "  ".length()) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
            
            @Override
            public BlockPos getPosition() {
                return this.this$0.pos;
            }
            
            @Override
            public void setCommandStat(final CommandResultStats.Type type, final int n) {
                TileEntitySign.access$0(this.this$0).func_179672_a(this, type, n);
            }
            
            @Override
            public World getEntityWorld() {
                return this.val$playerIn.getEntityWorld();
            }
            
            @Override
            public String getName() {
                return this.val$playerIn.getName();
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public IChatComponent getDisplayName() {
                return this.val$playerIn.getDisplayName();
            }
            
            @Override
            public boolean sendCommandFeedback() {
                return "".length() != 0;
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.pos.getX() + 0.5, this.this$0.pos.getY() + 0.5, this.this$0.pos.getZ() + 0.5);
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return this.val$playerIn;
            }
        };
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < this.signText.length) {
            ChatStyle chatStyle;
            if (this.signText[i] == null) {
                chatStyle = null;
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
            else {
                chatStyle = this.signText[i].getChatStyle();
            }
            final ChatStyle chatStyle2 = chatStyle;
            if (chatStyle2 != null && chatStyle2.getChatClickEvent() != null) {
                final ClickEvent chatClickEvent = chatStyle2.getChatClickEvent();
                if (chatClickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                    MinecraftServer.getServer().getCommandManager().executeCommand(commandSender, chatClickEvent.getValue());
                }
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    static CommandResultStats access$0(final TileEntitySign tileEntitySign) {
        return tileEntitySign.stats;
    }
    
    @Override
    public boolean func_183000_F() {
        return " ".length() != 0;
    }
}
