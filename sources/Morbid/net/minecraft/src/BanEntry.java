package net.minecraft.src;

import java.util.*;
import java.util.regex.*;
import net.minecraft.server.*;
import java.text.*;

public class BanEntry
{
    public static final SimpleDateFormat dateFormat;
    private final String username;
    private Date banStartDate;
    private String bannedBy;
    private Date banEndDate;
    private String reason;
    
    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    }
    
    public BanEntry(final String par1Str) {
        this.banStartDate = new Date();
        this.bannedBy = "(Unknown)";
        this.banEndDate = null;
        this.reason = "Banned by an operator.";
        this.username = par1Str;
    }
    
    public String getBannedUsername() {
        return this.username;
    }
    
    public Date getBanStartDate() {
        return this.banStartDate;
    }
    
    public void setBanStartDate(final Date par1Date) {
        this.banStartDate = ((par1Date != null) ? par1Date : new Date());
    }
    
    public String getBannedBy() {
        return this.bannedBy;
    }
    
    public void setBannedBy(final String par1Str) {
        this.bannedBy = par1Str;
    }
    
    public Date getBanEndDate() {
        return this.banEndDate;
    }
    
    public void setBanEndDate(final Date par1Date) {
        this.banEndDate = par1Date;
    }
    
    public boolean hasBanExpired() {
        return this.banEndDate != null && this.banEndDate.before(new Date());
    }
    
    public String getBanReason() {
        return this.reason;
    }
    
    public void setBanReason(final String par1Str) {
        this.reason = par1Str;
    }
    
    public String buildBanString() {
        final StringBuilder var1 = new StringBuilder();
        var1.append(this.getBannedUsername());
        var1.append("|");
        var1.append(BanEntry.dateFormat.format(this.getBanStartDate()));
        var1.append("|");
        var1.append(this.getBannedBy());
        var1.append("|");
        var1.append((this.getBanEndDate() == null) ? "Forever" : BanEntry.dateFormat.format(this.getBanEndDate()));
        var1.append("|");
        var1.append(this.getBanReason());
        return var1.toString();
    }
    
    public static BanEntry parse(final String par0Str) {
        if (par0Str.trim().length() < 2) {
            return null;
        }
        final String[] var1 = par0Str.trim().split(Pattern.quote("|"), 5);
        final BanEntry var2 = new BanEntry(var1[0].trim());
        final byte var3 = 0;
        int var4 = var1.length;
        int var5 = var3 + 1;
        if (var4 <= var5) {
            return var2;
        }
        try {
            var2.setBanStartDate(BanEntry.dateFormat.parse(var1[var5].trim()));
        }
        catch (ParseException var6) {
            MinecraftServer.getServer().getLogAgent().logWarningException("Could not read creation date format for ban entry '" + var2.getBannedUsername() + "' (was: '" + var1[var5] + "')", var6);
        }
        var4 = var1.length;
        ++var5;
        if (var4 <= var5) {
            return var2;
        }
        var2.setBannedBy(var1[var5].trim());
        var4 = var1.length;
        ++var5;
        if (var4 <= var5) {
            return var2;
        }
        try {
            final String var7 = var1[var5].trim();
            if (!var7.equalsIgnoreCase("Forever") && var7.length() > 0) {
                var2.setBanEndDate(BanEntry.dateFormat.parse(var7));
            }
        }
        catch (ParseException var8) {
            MinecraftServer.getServer().getLogAgent().logWarningException("Could not read expiry date format for ban entry '" + var2.getBannedUsername() + "' (was: '" + var1[var5] + "')", var8);
        }
        var4 = var1.length;
        ++var5;
        if (var4 <= var5) {
            return var2;
        }
        var2.setBanReason(var1[var5].trim());
        return var2;
    }
}
