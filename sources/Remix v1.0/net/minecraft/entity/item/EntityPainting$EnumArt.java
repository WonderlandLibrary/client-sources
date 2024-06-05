package net.minecraft.entity.item;

public enum EnumArt
{
    KEBAB("KEBAB", 0, "Kebab", 16, 16, 0, 0), 
    AZTEC("AZTEC", 1, "Aztec", 16, 16, 16, 0), 
    ALBAN("ALBAN", 2, "Alban", 16, 16, 32, 0), 
    AZTEC_2("AZTEC_2", 3, "Aztec2", 16, 16, 48, 0), 
    BOMB("BOMB", 4, "Bomb", 16, 16, 64, 0), 
    PLANT("PLANT", 5, "Plant", 16, 16, 80, 0), 
    WASTELAND("WASTELAND", 6, "Wasteland", 16, 16, 96, 0), 
    POOL("POOL", 7, "Pool", 32, 16, 0, 32), 
    COURBET("COURBET", 8, "Courbet", 32, 16, 32, 32), 
    SEA("SEA", 9, "Sea", 32, 16, 64, 32), 
    SUNSET("SUNSET", 10, "Sunset", 32, 16, 96, 32), 
    CREEBET("CREEBET", 11, "Creebet", 32, 16, 128, 32), 
    WANDERER("WANDERER", 12, "Wanderer", 16, 32, 0, 64), 
    GRAHAM("GRAHAM", 13, "Graham", 16, 32, 16, 64), 
    MATCH("MATCH", 14, "Match", 32, 32, 0, 128), 
    BUST("BUST", 15, "Bust", 32, 32, 32, 128), 
    STAGE("STAGE", 16, "Stage", 32, 32, 64, 128), 
    VOID("VOID", 17, "Void", 32, 32, 96, 128), 
    SKULL_AND_ROSES("SKULL_AND_ROSES", 18, "SkullAndRoses", 32, 32, 128, 128), 
    WITHER("WITHER", 19, "Wither", 32, 32, 160, 128), 
    FIGHTERS("FIGHTERS", 20, "Fighters", 64, 32, 0, 96), 
    POINTER("POINTER", 21, "Pointer", 64, 64, 0, 192), 
    PIGSCENE("PIGSCENE", 22, "Pigscene", 64, 64, 64, 192), 
    BURNING_SKULL("BURNING_SKULL", 23, "BurningSkull", 64, 64, 128, 192), 
    SKELETON("SKELETON", 24, "Skeleton", 64, 48, 192, 64), 
    DONKEY_KONG("DONKEY_KONG", 25, "DonkeyKong", 64, 48, 192, 112);
    
    public static final int field_180001_A;
    private static final EnumArt[] $VALUES;
    public final String title;
    public final int sizeX;
    public final int sizeY;
    public final int offsetX;
    public final int offsetY;
    
    private EnumArt(final String p_i1598_1_, final int p_i1598_2_, final String p_i1598_3_, final int p_i1598_4_, final int p_i1598_5_, final int p_i1598_6_, final int p_i1598_7_) {
        this.title = p_i1598_3_;
        this.sizeX = p_i1598_4_;
        this.sizeY = p_i1598_5_;
        this.offsetX = p_i1598_6_;
        this.offsetY = p_i1598_7_;
    }
    
    static {
        field_180001_A = "SkullAndRoses".length();
        $VALUES = new EnumArt[] { EnumArt.KEBAB, EnumArt.AZTEC, EnumArt.ALBAN, EnumArt.AZTEC_2, EnumArt.BOMB, EnumArt.PLANT, EnumArt.WASTELAND, EnumArt.POOL, EnumArt.COURBET, EnumArt.SEA, EnumArt.SUNSET, EnumArt.CREEBET, EnumArt.WANDERER, EnumArt.GRAHAM, EnumArt.MATCH, EnumArt.BUST, EnumArt.STAGE, EnumArt.VOID, EnumArt.SKULL_AND_ROSES, EnumArt.WITHER, EnumArt.FIGHTERS, EnumArt.POINTER, EnumArt.PIGSCENE, EnumArt.BURNING_SKULL, EnumArt.SKELETON, EnumArt.DONKEY_KONG };
    }
}
