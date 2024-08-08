package net.futureclient.client;

public enum Category
{
    private static final Category[] A;
    
    MOVEMENT("Movement"), 
    EXPLOITS("Exploits");
    
    private String M;
    
    RENDER("Render"), 
    COMBAT("Combat"), 
    MISCELLANEOUS("Miscellaneous"), 
    WORLD("World");
    
    private Category(final String m) {
        this.M = m;
    }
    
    static {
        A = new Category[] { Category.COMBAT, Category.EXPLOITS, Category.MISCELLANEOUS, Category.MOVEMENT, Category.RENDER, Category.WORLD };
    }
    
    public static Category[] values() {
        return Category.A.clone();
    }
    
    public static Category valueOf(final String s) {
        return Enum.<Category>valueOf((Class<Category>)Ve.class, s);
    }
    
    public String M() {
        return this.M;
    }
}
