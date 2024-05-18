package net.minecraft.src;

import java.util.*;

public class EnchantmentNameParts
{
    public static final EnchantmentNameParts instance;
    private Random rand;
    private String[] wordList;
    
    static {
        instance = new EnchantmentNameParts();
    }
    
    public EnchantmentNameParts() {
        this.rand = new Random();
        this.wordList = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale ".split(" ");
    }
    
    public String generateRandomEnchantName() {
        final int var1 = this.rand.nextInt(2) + 3;
        String var2 = "";
        for (int var3 = 0; var3 < var1; ++var3) {
            if (var3 > 0) {
                var2 = String.valueOf(var2) + " ";
            }
            var2 = String.valueOf(var2) + this.wordList[this.rand.nextInt(this.wordList.length)];
        }
        return var2;
    }
    
    public void setRandSeed(final long par1) {
        this.rand.setSeed(par1);
    }
}
