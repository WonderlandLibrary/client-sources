package net.minecraft.src;

public class Achievement extends StatBase
{
    public final int displayColumn;
    public final int displayRow;
    public final Achievement parentAchievement;
    private final String achievementDescription;
    private IStatStringFormat statStringFormatter;
    public final ItemStack theItemStack;
    private boolean isSpecial;
    
    public Achievement(final int par1, final String par2Str, final int par3, final int par4, final Item par5Item, final Achievement par6Achievement) {
        this(par1, par2Str, par3, par4, new ItemStack(par5Item), par6Achievement);
    }
    
    public Achievement(final int par1, final String par2Str, final int par3, final int par4, final Block par5Block, final Achievement par6Achievement) {
        this(par1, par2Str, par3, par4, new ItemStack(par5Block), par6Achievement);
    }
    
    public Achievement(final int par1, final String par2Str, final int par3, final int par4, final ItemStack par5ItemStack, final Achievement par6Achievement) {
        super(5242880 + par1, "achievement." + par2Str);
        this.theItemStack = par5ItemStack;
        this.achievementDescription = "achievement." + par2Str + ".desc";
        this.displayColumn = par3;
        this.displayRow = par4;
        if (par3 < AchievementList.minDisplayColumn) {
            AchievementList.minDisplayColumn = par3;
        }
        if (par4 < AchievementList.minDisplayRow) {
            AchievementList.minDisplayRow = par4;
        }
        if (par3 > AchievementList.maxDisplayColumn) {
            AchievementList.maxDisplayColumn = par3;
        }
        if (par4 > AchievementList.maxDisplayRow) {
            AchievementList.maxDisplayRow = par4;
        }
        this.parentAchievement = par6Achievement;
    }
    
    public Achievement setIndependent() {
        this.isIndependent = true;
        return this;
    }
    
    public Achievement setSpecial() {
        this.isSpecial = true;
        return this;
    }
    
    public Achievement registerAchievement() {
        super.registerStat();
        AchievementList.achievementList.add(this);
        return this;
    }
    
    @Override
    public boolean isAchievement() {
        return true;
    }
    
    public String getDescription() {
        return (this.statStringFormatter != null) ? this.statStringFormatter.formatString(StatCollector.translateToLocal(this.achievementDescription)) : StatCollector.translateToLocal(this.achievementDescription);
    }
    
    public Achievement setStatStringFormatter(final IStatStringFormat par1IStatStringFormat) {
        this.statStringFormatter = par1IStatStringFormat;
        return this;
    }
    
    public boolean getSpecial() {
        return this.isSpecial;
    }
    
    @Override
    public StatBase registerStat() {
        return this.registerAchievement();
    }
    
    @Override
    public StatBase initIndependentStat() {
        return this.setIndependent();
    }
}
