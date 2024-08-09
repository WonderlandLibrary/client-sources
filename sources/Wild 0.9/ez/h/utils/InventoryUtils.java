package ez.h.utils;

public class InventoryUtils
{
    static bib mc;
    
    public static void swap(final int n, final int n2) {
        InventoryUtils.mc.c.a(InventoryUtils.mc.h.bx.d, n, n2, afw.c, (aed)InventoryUtils.mc.h);
    }
    
    public static void drop(final int n) {
        InventoryUtils.mc.c.a(0, n, 1, afw.e, (aed)InventoryUtils.mc.h);
    }
    
    static {
        InventoryUtils.mc = bib.z();
    }
    
    public static void click(final int n, final int n2, final boolean b) {
        InventoryUtils.mc.c.a(InventoryUtils.mc.h.bx.d, n, n2, b ? afw.b : afw.a, (aed)InventoryUtils.mc.h);
    }
}
