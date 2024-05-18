package net.minecraft.command.server;

import java.text.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.server.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.command.*;
import net.minecraft.crash.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import io.netty.buffer.*;

public abstract class CommandBlockLogic implements ICommandSender
{
    private boolean trackOutput;
    private static final SimpleDateFormat timestampFormat;
    private static final String[] I;
    private IChatComponent lastOutput;
    private final CommandResultStats resultStats;
    private String commandStored;
    private int successCount;
    private String customName;
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        if (this.trackOutput && this.getEntityWorld() != null && !this.getEntityWorld().isRemote) {
            this.lastOutput = new ChatComponentText(CommandBlockLogic.I[0x53 ^ 0x47] + CommandBlockLogic.timestampFormat.format(new Date()) + CommandBlockLogic.I[0xBB ^ 0xAE]).appendSibling(chatComponent);
            this.updateCommand();
        }
    }
    
    @Override
    public void setCommandStat(final CommandResultStats.Type type, final int n) {
        this.resultStats.func_179672_a(this, type, n);
    }
    
    @Override
    public String getName() {
        return this.customName;
    }
    
    public void setTrackOutput(final boolean trackOutput) {
        this.trackOutput = trackOutput;
    }
    
    public int getSuccessCount() {
        return this.successCount;
    }
    
    public IChatComponent getLastOutput() {
        return this.lastOutput;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(this.getName());
    }
    
    public CommandBlockLogic() {
        this.trackOutput = (" ".length() != 0);
        this.lastOutput = null;
        this.commandStored = CommandBlockLogic.I[" ".length()];
        this.customName = CommandBlockLogic.I["  ".length()];
        this.resultStats = new CommandResultStats();
    }
    
    public abstract void updateCommand();
    
    public void trigger(final World world) {
        if (world.isRemote) {
            this.successCount = "".length();
        }
        final MinecraftServer server = MinecraftServer.getServer();
        if (server != null && server.isAnvilFileSet() && server.isCommandBlockEnabled()) {
            final ICommandManager commandManager = server.getCommandManager();
            try {
                this.lastOutput = null;
                this.successCount = commandManager.executeCommand(this, this.commandStored);
                "".length();
                if (1 < -1) {
                    throw null;
                }
                return;
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, CommandBlockLogic.I[0x73 ^ 0x63]);
                final CrashReportCategory category = crashReport.makeCategory(CommandBlockLogic.I[0x48 ^ 0x59]);
                category.addCrashSectionCallable(CommandBlockLogic.I[0x25 ^ 0x37], new Callable<String>(this) {
                    final CommandBlockLogic this$0;
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
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
                            if (3 >= 4) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    @Override
                    public String call() throws Exception {
                        return this.this$0.getCommand();
                    }
                });
                category.addCrashSectionCallable(CommandBlockLogic.I[0x96 ^ 0x85], new Callable<String>(this) {
                    final CommandBlockLogic this$0;
                    
                    @Override
                    public String call() throws Exception {
                        return this.this$0.getName();
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
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
                            if (1 <= 0) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                });
                throw new ReportedException(crashReport);
            }
        }
        this.successCount = "".length();
    }
    
    public boolean tryOpenEditCommandBlock(final EntityPlayer entityPlayer) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            return "".length() != 0;
        }
        if (entityPlayer.getEntityWorld().isRemote) {
            entityPlayer.openEditCommandBlock(this);
        }
        return " ".length() != 0;
    }
    
    public void readDataFromNBT(final NBTTagCompound nbtTagCompound) {
        this.commandStored = nbtTagCompound.getString(CommandBlockLogic.I[0x24 ^ 0x2C]);
        this.successCount = nbtTagCompound.getInteger(CommandBlockLogic.I[0x75 ^ 0x7C]);
        if (nbtTagCompound.hasKey(CommandBlockLogic.I[0x46 ^ 0x4C], 0xBA ^ 0xB2)) {
            this.customName = nbtTagCompound.getString(CommandBlockLogic.I[0xF ^ 0x4]);
        }
        if (nbtTagCompound.hasKey(CommandBlockLogic.I[0x6E ^ 0x62], " ".length())) {
            this.trackOutput = nbtTagCompound.getBoolean(CommandBlockLogic.I[0x40 ^ 0x4D]);
        }
        if (nbtTagCompound.hasKey(CommandBlockLogic.I[0x91 ^ 0x9F], 0x8 ^ 0x0) && this.trackOutput) {
            this.lastOutput = IChatComponent.Serializer.jsonToComponent(nbtTagCompound.getString(CommandBlockLogic.I[0x49 ^ 0x46]));
        }
        this.resultStats.readStatsFromNBT(nbtTagCompound);
    }
    
    @Override
    public boolean sendCommandFeedback() {
        final MinecraftServer server = MinecraftServer.getServer();
        if (server != null && server.isAnvilFileSet() && !server.worldServers["".length()].getGameRules().getBoolean(CommandBlockLogic.I[0x6E ^ 0x78])) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void setName(final String customName) {
        this.customName = customName;
    }
    
    public void writeDataToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setString(CommandBlockLogic.I["   ".length()], this.commandStored);
        nbtTagCompound.setInteger(CommandBlockLogic.I[0x2A ^ 0x2E], this.successCount);
        nbtTagCompound.setString(CommandBlockLogic.I[0x2D ^ 0x28], this.customName);
        nbtTagCompound.setBoolean(CommandBlockLogic.I[0x36 ^ 0x30], this.trackOutput);
        if (this.lastOutput != null && this.trackOutput) {
            nbtTagCompound.setString(CommandBlockLogic.I[0x15 ^ 0x12], IChatComponent.Serializer.componentToJson(this.lastOutput));
        }
        this.resultStats.writeStatsToNBT(nbtTagCompound);
    }
    
    public CommandResultStats getCommandResultStats() {
        return this.resultStats;
    }
    
    public String getCommand() {
        return this.commandStored;
    }
    
    public void setLastOutput(final IChatComponent lastOutput) {
        this.lastOutput = lastOutput;
    }
    
    public abstract void func_145757_a(final ByteBuf p0);
    
    private static void I() {
        (I = new String[0x68 ^ 0x7F])["".length()] = I("'\u0003j$5U8#", "oKPIX");
        CommandBlockLogic.I[" ".length()] = I("", "czCgE");
        CommandBlockLogic.I["  ".length()] = I("#", "cFSAm");
        CommandBlockLogic.I["   ".length()] = I("\n \"72'+", "IOOZS");
        CommandBlockLogic.I[0x52 ^ 0x56] = I("\u00127\u0006\u0012\n21&\u001e\u001a/6", "ABeqo");
        CommandBlockLogic.I[0x39 ^ 0x3C] = I("5\u0005\u0016\u0011%\u001b>\u0004\b/", "vpeeJ");
        CommandBlockLogic.I[0xBF ^ 0xB9] = I("3\u0013\r\u0000=(\u0014\u0018\u0013#\u0013", "galcV");
        CommandBlockLogic.I[0x7E ^ 0x79] = I("8\u0012\u0011\u0015\u001b\u0001\u0007\u0012\u0014 ", "tsbaT");
        CommandBlockLogic.I[0x97 ^ 0x9F] = I("\u00058\f\u001a\n(3", "FWawk");
        CommandBlockLogic.I[0xAD ^ 0xA4] = I("\u001890*<8?\u0010&,%8", "KLSIY");
        CommandBlockLogic.I[0x42 ^ 0x48] = I("1\u001c\u00179:\u001f'\u0005 0", "ridMU");
        CommandBlockLogic.I[0x8D ^ 0x86] = I("\u001a&$#:4\u001d6:0", "YSWWU");
        CommandBlockLogic.I[0x8 ^ 0x4] = I("\u001a\u0014)\u001b\u001a\u0001\u0013<\b\u0004:", "NfHxq");
        CommandBlockLogic.I[0x3F ^ 0x32] = I("\u0003\u0006#9\u0011\u0018\u00016*\u000f#", "WtBZz");
        CommandBlockLogic.I[0x83 ^ 0x8D] = I(";(\u0000\u0015\u0018\u0002=\u0003\u0014#", "wIsaW");
        CommandBlockLogic.I[0x21 ^ 0x2E] = I("\u001f\r%>\u001e&\u0018&?%", "SlVJQ");
        CommandBlockLogic.I[0x70 ^ 0x60] = I("\u0013\n/7\u0014\"\u001b$3A5\u001d'9\u00008\u0016j6\r9\u0011!", "VrJTa");
        CommandBlockLogic.I[0x0 ^ 0x11] = I("\u0019\u0018\n\u0003\u00074\u0013G\u001a\tz\u0015\u0002N\u0003\"\u0012\u0004\u001b\u0012?\u0013", "Zwgnf");
        CommandBlockLogic.I[0x71 ^ 0x63] = I("\u0004\u0015\u0018,\r)\u001e", "GzuAl");
        CommandBlockLogic.I[0x85 ^ 0x96] = I("\u0016\u001b\u000e.", "XzcKU");
        CommandBlockLogic.I[0x9A ^ 0x8E] = I("?", "diicd");
        CommandBlockLogic.I[0x1E ^ 0xB] = I("\u001ai", "GIcXN");
        CommandBlockLogic.I[0x54 ^ 0x42] = I("\u0010\u001e(,'\u001d\u0015\u0007-)\u0010\u001a\n42\u0003\u00041", "sqEAF");
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        if (n <= "  ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
        timestampFormat = new SimpleDateFormat(CommandBlockLogic.I["".length()]);
    }
    
    public boolean shouldTrackOutput() {
        return this.trackOutput;
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setCommand(final String commandStored) {
        this.commandStored = commandStored;
        this.successCount = "".length();
    }
    
    public abstract int func_145751_f();
}
