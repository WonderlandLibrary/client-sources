package cn.Hanabi.modules;

public enum Category
{
    COMBAT("Combat"), 
    MOVEMENT("Movement"), 
    PLAYER("Player"), 
    RENDER("Render"), 
    WORLD("World"), 
    GHOST("Ghost");
    
    private String name;
    private static final Category[] $VALUES;
    
    private Category(final String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    static {
        $VALUES = new Category[] { Category.COMBAT, Category.MOVEMENT, Category.PLAYER, Category.RENDER, Category.WORLD, Category.GHOST };
    }
}
