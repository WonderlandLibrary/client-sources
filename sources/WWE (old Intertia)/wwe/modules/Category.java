package wwe.modules;

import wwe.*;

public enum Category
{
    COMBAT(-4390912), 
    PLAYER(-15041370), 
    MOVEMENT(-16403940), 
    RENDER(-1935089), 
    EXPLOITS(-8158847), 
    WORLD(-10092339), 
    GUI(-16777216);
    
    public int YihZ;
    private static final Category[] ZvFG;
    
    public static Category[] fgCR() {
        return Category.ZvFG.clone();
    }
    
    public static Category OkzX(final String s) {
        return Enum.valueOf((Class<Category>)AzzV.class, s);
    }
    
    private Category(final int yihZ) {
        this.YihZ = yihZ;
    }
    
    static {
        ZvFG = new Category[] { Category.COMBAT, Category.PLAYER, Category.MOVEMENT, Category.RENDER, Category.EXPLOITS, Category.WORLD, Category.GUI };
    }
}
