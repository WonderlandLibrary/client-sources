package HORIZON-6-0-SKIDPROTECTION;

public enum Category
{
    HorizonCode_Horizon_È("NONE", 0), 
    Â("COMBAT", 1), 
    Ý("DISPLAY", 2), 
    Ø­áŒŠá("PLAYER", 3), 
    Âµá€("SERVER", 4), 
    Ó("MOVEMENT", 5), 
    à("MINIGAMES", 6);
    
    static {
        Ø = new Category[] { Category.HorizonCode_Horizon_È, Category.Â, Category.Ý, Category.Ø­áŒŠá, Category.Âµá€, Category.Ó, Category.à };
    }
    
    private Category(final String s, final int n) {
    }
}
