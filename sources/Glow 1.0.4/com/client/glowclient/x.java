package com.client.glowclient;

import net.minecraft.enchantment.*;
import java.util.*;
import com.google.common.collect.*;

public class X
{
    private final int A;
    private final Enchantment B;
    private static final Map<Integer, String> b;
    
    @Override
    public String toString() {
        return this.B.getTranslatedName(this.A);
    }
    
    public int M() {
        return this.A;
    }
    
    public Enchantment M() {
        return this.B;
    }
    
    public X(final Enchantment b, final int a) {
        super();
        this.B = b;
        this.A = a;
    }
    
    static {
        (b = Maps.newHashMap()).put(0, "pro");
        X.b.put(1, "fip");
        X.b.put(2, "fea");
        X.b.put(3, "bla");
        X.b.put(4, "prp");
        X.b.put(5, "res");
        X.b.put(6, "aqu");
        X.b.put(7, "tho");
        X.b.put(8, "dep");
        X.b.put(9, "fro");
        X.b.put(10, "§cbin");
        X.b.put(16, "sha");
        X.b.put(17, "smi");
        X.b.put(18, "boa");
        X.b.put(19, "kno");
        X.b.put(20, "fir");
        X.b.put(21, "loo");
        X.b.put(22, "swe");
        X.b.put(32, "eff");
        X.b.put(33, "sil");
        X.b.put(34, "unb");
        X.b.put(35, "for");
        X.b.put(48, "pow");
        X.b.put(49, "pun");
        X.b.put(50, "fla");
        X.b.put(51, "inf");
        X.b.put(61, "los");
        X.b.put(62, "lur");
        X.b.put(70, "men");
        X.b.put(71, "§cvan");
    }
    
    public String M() {
        final int enchantmentID = Enchantment.getEnchantmentID(this.B);
        if (!X.b.containsKey(enchantmentID)) {
            return this.toString();
        }
        if (this.B.getMaxLevel() <= 1) {
            return X.b.get(enchantmentID);
        }
        return X.b.get(enchantmentID) + " §b" + this.A;
    }
    
    public X(final int n, final int n2) {
        this(Enchantment.getEnchantmentByID(n), n2);
    }
}
