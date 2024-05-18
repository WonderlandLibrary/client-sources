package net.minecraft.command;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.scoreboard.*;
import net.minecraft.nbt.*;

public class CommandResultStats
{
    private static final int NUM_RESULT_TYPES;
    private static final String[] STRING_RESULT_TYPES;
    private static final String[] I;
    private String[] field_179675_c;
    private String[] field_179673_d;
    
    public void func_179672_a(final ICommandSender commandSender, final Type type, final int scorePoints) {
        final String s = this.field_179675_c[type.getTypeID()];
        if (s != null) {
            final ICommandSender commandSender2 = new ICommandSender(this, commandSender) {
                final CommandResultStats this$0;
                private final ICommandSender val$sender;
                
                @Override
                public void addChatMessage(final IChatComponent chatComponent) {
                    this.val$sender.addChatMessage(chatComponent);
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
                        if (0 < 0) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public BlockPos getPosition() {
                    return this.val$sender.getPosition();
                }
                
                @Override
                public Entity getCommandSenderEntity() {
                    return this.val$sender.getCommandSenderEntity();
                }
                
                @Override
                public Vec3 getPositionVector() {
                    return this.val$sender.getPositionVector();
                }
                
                @Override
                public String getName() {
                    return this.val$sender.getName();
                }
                
                @Override
                public boolean canCommandSenderUseCommand(final int n, final String s) {
                    return " ".length() != 0;
                }
                
                @Override
                public World getEntityWorld() {
                    return this.val$sender.getEntityWorld();
                }
                
                @Override
                public boolean sendCommandFeedback() {
                    return this.val$sender.sendCommandFeedback();
                }
                
                @Override
                public void setCommandStat(final Type type, final int n) {
                    this.val$sender.setCommandStat(type, n);
                }
                
                @Override
                public IChatComponent getDisplayName() {
                    return this.val$sender.getDisplayName();
                }
            };
            String entityName;
            try {
                entityName = CommandBase.getEntityName(commandSender2, s);
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            catch (EntityNotFoundException ex) {
                return;
            }
            final String s2 = this.field_179673_d[type.getTypeID()];
            if (s2 != null) {
                final Scoreboard scoreboard = commandSender.getEntityWorld().getScoreboard();
                final ScoreObjective objective = scoreboard.getObjective(s2);
                if (objective != null && scoreboard.entityHasObjective(entityName, objective)) {
                    scoreboard.getValueFromObjective(entityName, objective).setScorePoints(scorePoints);
                }
            }
        }
    }
    
    static {
        I();
        NUM_RESULT_TYPES = Type.values().length;
        STRING_RESULT_TYPES = new String[CommandResultStats.NUM_RESULT_TYPES];
    }
    
    public void readStatsFromNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey(CommandResultStats.I["".length()], 0x11 ^ 0x1B)) {
            final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag(CommandResultStats.I[" ".length()]);
            final Type[] values;
            final int length = (values = Type.values()).length;
            int i = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (i < length) {
                final Type type = values[i];
                final String string = String.valueOf(type.getTypeName()) + CommandResultStats.I["  ".length()];
                final String string2 = String.valueOf(type.getTypeName()) + CommandResultStats.I["   ".length()];
                if (compoundTag.hasKey(string, 0x2F ^ 0x27) && compoundTag.hasKey(string2, 0xAA ^ 0xA2)) {
                    func_179667_a(this, type, compoundTag.getString(string), compoundTag.getString(string2));
                }
                ++i;
            }
        }
    }
    
    public void func_179671_a(final CommandResultStats commandResultStats) {
        final Type[] values;
        final int length = (values = Type.values()).length;
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < length) {
            final Type type = values[i];
            func_179667_a(this, type, commandResultStats.field_179675_c[type.getTypeID()], commandResultStats.field_179673_d[type.getTypeID()]);
            ++i;
        }
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public CommandResultStats() {
        this.field_179675_c = CommandResultStats.STRING_RESULT_TYPES;
        this.field_179673_d = CommandResultStats.STRING_RESULT_TYPES;
    }
    
    private static void I() {
        (I = new String[0x79 ^ 0x7E])["".length()] = I("\u0001,\u0007\t\u0014,'9\u0010\u001460", "BCjdu");
        CommandResultStats.I[" ".length()] = I("5\t#\u0015&\u0018\u0002\u001d\f&\u0002\u0015", "vfNxG");
        CommandResultStats.I["  ".length()] = I("\f\u0002=1", "BcPTn");
        CommandResultStats.I["   ".length()] = I("\f:$\u000e-718\u000e", "CXNkN");
        CommandResultStats.I[0xBA ^ 0xBE] = I("\u001a ;\u0014", "TAVqS");
        CommandResultStats.I[0xB2 ^ 0xB7] = I(":)32\u0019\u0001\"/2", "uKYWz");
        CommandResultStats.I[0x56 ^ 0x50] = I("\n\u0002\u0001\u0001.'\t?\u0018.=\u001e", "ImllO");
    }
    
    public void writeStatsToNBT(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        final Type[] values;
        final int length = (values = Type.values()).length;
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < length) {
            final Type type = values[i];
            final String s = this.field_179675_c[type.getTypeID()];
            final String s2 = this.field_179673_d[type.getTypeID()];
            if (s != null && s2 != null) {
                nbtTagCompound2.setString(String.valueOf(type.getTypeName()) + CommandResultStats.I[0xBE ^ 0xBA], s);
                nbtTagCompound2.setString(String.valueOf(type.getTypeName()) + CommandResultStats.I[0x3 ^ 0x6], s2);
            }
            ++i;
        }
        if (!nbtTagCompound2.hasNoTags()) {
            nbtTagCompound.setTag(CommandResultStats.I[0x77 ^ 0x71], nbtTagCompound2);
        }
    }
    
    public static void func_179667_a(final CommandResultStats commandResultStats, final Type type, final String s, final String s2) {
        if (s != null && s.length() != 0 && s2 != null && s2.length() != 0) {
            if (commandResultStats.field_179675_c == CommandResultStats.STRING_RESULT_TYPES || commandResultStats.field_179673_d == CommandResultStats.STRING_RESULT_TYPES) {
                commandResultStats.field_179675_c = new String[CommandResultStats.NUM_RESULT_TYPES];
                commandResultStats.field_179673_d = new String[CommandResultStats.NUM_RESULT_TYPES];
            }
            commandResultStats.field_179675_c[type.getTypeID()] = s;
            commandResultStats.field_179673_d[type.getTypeID()] = s2;
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            func_179669_a(commandResultStats, type);
        }
    }
    
    private static void func_179669_a(final CommandResultStats commandResultStats, final Type type) {
        if (commandResultStats.field_179675_c != CommandResultStats.STRING_RESULT_TYPES && commandResultStats.field_179673_d != CommandResultStats.STRING_RESULT_TYPES) {
            commandResultStats.field_179675_c[type.getTypeID()] = null;
            commandResultStats.field_179673_d[type.getTypeID()] = null;
            int n = " ".length();
            final Type[] values;
            final int length = (values = Type.values()).length;
            int i = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (i < length) {
                final Type type2 = values[i];
                if (commandResultStats.field_179675_c[type2.getTypeID()] != null && commandResultStats.field_179673_d[type2.getTypeID()] != null) {
                    n = "".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
            if (n != 0) {
                commandResultStats.field_179675_c = CommandResultStats.STRING_RESULT_TYPES;
                commandResultStats.field_179673_d = CommandResultStats.STRING_RESULT_TYPES;
            }
        }
    }
    
    public enum Type
    {
        private static final Type[] ENUM$VALUES;
        
        AFFECTED_ENTITIES(Type.I[0xBB ^ 0xBF], "  ".length(), "  ".length(), Type.I[0x1A ^ 0x1F]), 
        AFFECTED_BLOCKS(Type.I["  ".length()], " ".length(), " ".length(), Type.I["   ".length()]), 
        SUCCESS_COUNT(Type.I["".length()], "".length(), "".length(), Type.I[" ".length()]);
        
        final String typeName;
        
        AFFECTED_ITEMS(Type.I[0x77 ^ 0x71], "   ".length(), "   ".length(), Type.I[0xB1 ^ 0xB6]), 
        QUERY_RESULT(Type.I[0x60 ^ 0x68], 0x74 ^ 0x70, 0x2E ^ 0x2A, Type.I[0x6C ^ 0x65]);
        
        private static final String[] I;
        final int typeID;
        
        public static Type getTypeByName(final String s) {
            final Type[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
            while (i < length) {
                final Type type = values[i];
                if (type.getTypeName().equals(s)) {
                    return type;
                }
                ++i;
            }
            return null;
        }
        
        public String getTypeName() {
            return this.typeName;
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
                if (1 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[0xAA ^ 0xA0])["".length()] = I("5:*\u0019\u00115<6\u0019\u001b3!=", "foiZT");
            Type.I[" ".length()] = I("\u0016%0\n06#\u0010\u0006 +$", "EPSiU");
            Type.I["  ".length()] = I("\u0004+\u0002.\u000f\u0011(\u00004\u000e\t\"\u0007 \u001f", "EmDkL");
            Type.I["   ".length()] = I("\u000b\u00163-)>\u00151\n&%\u0013>;", "JpUHJ");
            Type.I[0x10 ^ 0x14] = I("\u0011\u00051\u0013!\u0004\u00063\t'\u001e\u0017>\u0002+\u0015\u0010", "PCwVb");
            Type.I[0x2B ^ 0x2E] = I("6,4\u0016\u0007\u0003/66\n\u0003#&\u001a\u0001\u0004", "wJRsd");
            Type.I[0xBD ^ 0xBB] = I("2(\u0004$\u0006'+\u0006>\f'+\u000f2", "snBaE");
            Type.I[0x5B ^ 0x5C] = I(")4(-\u0019\u001c7*\u0001\u000e\r?=", "hRNHz");
            Type.I[0xB3 ^ 0xBB] = I("\u0012\u0006\u0012;\u0000\u001c\u0001\u0012:\f\u000f\u0007", "CSWiY");
            Type.I[0x9E ^ 0x97] = I("<\u0004\u0007\u0018;?\u0014\u0011\u001f.\u0019", "mqbjB");
        }
        
        public int getTypeID() {
            return this.typeID;
        }
        
        public static String[] getTypeNames() {
            final String[] array = new String[values().length];
            int length = "".length();
            final Type[] values;
            final int length2 = (values = values()).length;
            int i = "".length();
            "".length();
            if (3 == 4) {
                throw null;
            }
            while (i < length2) {
                array[length++] = values[i].getTypeName();
                ++i;
            }
            return array;
        }
        
        static {
            I();
            final Type[] enum$VALUES = new Type[0x97 ^ 0x92];
            enum$VALUES["".length()] = Type.SUCCESS_COUNT;
            enum$VALUES[" ".length()] = Type.AFFECTED_BLOCKS;
            enum$VALUES["  ".length()] = Type.AFFECTED_ENTITIES;
            enum$VALUES["   ".length()] = Type.AFFECTED_ITEMS;
            enum$VALUES[0xA0 ^ 0xA4] = Type.QUERY_RESULT;
            ENUM$VALUES = enum$VALUES;
        }
        
        private Type(final String s, final int n, final int typeID, final String typeName) {
            this.typeID = typeID;
            this.typeName = typeName;
        }
    }
}
