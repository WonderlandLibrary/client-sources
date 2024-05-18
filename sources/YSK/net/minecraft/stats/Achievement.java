package net.minecraft.stats;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class Achievement extends StatBase
{
    public final ItemStack theItemStack;
    public final Achievement parentAchievement;
    private final String achievementDescription;
    private static final String[] I;
    private boolean isSpecial;
    public final int displayRow;
    private IStatStringFormat statStringFormatter;
    public final int displayColumn;
    
    public Achievement setSpecial() {
        this.isSpecial = (" ".length() != 0);
        return this;
    }
    
    @Override
    public IChatComponent getStatName() {
        final IChatComponent statName = super.getStatName();
        final ChatStyle chatStyle = statName.getChatStyle();
        EnumChatFormatting color;
        if (this.getSpecial()) {
            color = EnumChatFormatting.DARK_PURPLE;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            color = EnumChatFormatting.GREEN;
        }
        chatStyle.setColor(color);
        return statName;
    }
    
    @Override
    public StatBase func_150953_b(final Class clazz) {
        return this.func_150953_b((Class<? extends IJsonSerializable>)clazz);
    }
    
    @Override
    public boolean isAchievement() {
        return " ".length() != 0;
    }
    
    @Override
    public Achievement func_150953_b(final Class<? extends IJsonSerializable> clazz) {
        return (Achievement)super.func_150953_b(clazz);
    }
    
    @Override
    public StatBase initIndependentStat() {
        return this.initIndependentStat();
    }
    
    @Override
    public Achievement registerStat() {
        super.registerStat();
        AchievementList.achievementList.add(this);
        return this;
    }
    
    @Override
    public Achievement initIndependentStat() {
        this.isIndependent = (" ".length() != 0);
        return this;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("6\u001a\u0012\u00136!\u001c\u0017\u001f=#W", "WyzzS");
        Achievement.I[" ".length()] = I("7$\u0019\u001b\b \"\u001c\u0017\u0003\"i", "VGqrm");
        Achievement.I["  ".length()] = I("k0'=\u0011", "ETBNr");
    }
    
    public String getDescription() {
        String s;
        if (this.statStringFormatter != null) {
            s = this.statStringFormatter.formatString(StatCollector.translateToLocal(this.achievementDescription));
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else {
            s = StatCollector.translateToLocal(this.achievementDescription);
        }
        return s;
    }
    
    public Achievement setStatStringFormatter(final IStatStringFormat statStringFormatter) {
        this.statStringFormatter = statStringFormatter;
        return this;
    }
    
    public boolean getSpecial() {
        return this.isSpecial;
    }
    
    static {
        I();
    }
    
    public Achievement(final String s, final String s2, final int maxDisplayColumn, final int maxDisplayRow, final ItemStack theItemStack, final Achievement parentAchievement) {
        super(s, new ChatComponentTranslation(Achievement.I["".length()] + s2, new Object["".length()]));
        this.theItemStack = theItemStack;
        this.achievementDescription = Achievement.I[" ".length()] + s2 + Achievement.I["  ".length()];
        this.displayColumn = maxDisplayColumn;
        this.displayRow = maxDisplayRow;
        if (maxDisplayColumn < AchievementList.minDisplayColumn) {
            AchievementList.minDisplayColumn = maxDisplayColumn;
        }
        if (maxDisplayRow < AchievementList.minDisplayRow) {
            AchievementList.minDisplayRow = maxDisplayRow;
        }
        if (maxDisplayColumn > AchievementList.maxDisplayColumn) {
            AchievementList.maxDisplayColumn = maxDisplayColumn;
        }
        if (maxDisplayRow > AchievementList.maxDisplayRow) {
            AchievementList.maxDisplayRow = maxDisplayRow;
        }
        this.parentAchievement = parentAchievement;
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public StatBase registerStat() {
        return this.registerStat();
    }
    
    public Achievement(final String s, final String s2, final int n, final int n2, final Item item, final Achievement achievement) {
        this(s, s2, n, n2, new ItemStack(item), achievement);
    }
    
    public Achievement(final String s, final String s2, final int n, final int n2, final Block block, final Achievement achievement) {
        this(s, s2, n, n2, new ItemStack(block), achievement);
    }
}
