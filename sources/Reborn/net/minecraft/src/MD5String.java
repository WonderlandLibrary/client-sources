package net.minecraft.src;

import java.math.*;
import java.security.*;

public class MD5String
{
    private String salt;
    
    public MD5String(final String par1Str) {
        this.salt = par1Str;
    }
    
    public String getMD5String(final String par1Str) {
        try {
            final String var2 = String.valueOf(this.salt) + par1Str;
            final MessageDigest var3 = MessageDigest.getInstance("MD5");
            var3.update(var2.getBytes(), 0, var2.length());
            return new BigInteger(1, var3.digest()).toString(16);
        }
        catch (NoSuchAlgorithmException var4) {
            throw new RuntimeException(var4);
        }
    }
}
