package org.bouncycastle.jcajce.provider.config;

import org.bouncycastle.util.*;
import java.util.*;
import java.security.*;

public class ProviderConfigurationPermission extends BasicPermission
{
    private final String actions;
    private final int permissionMask;
    
    public ProviderConfigurationPermission(final String par1Str) {
        super(par1Str);
        this.actions = "all";
        this.permissionMask = 15;
    }
    
    public ProviderConfigurationPermission(final String par1Str, final String par2Str) {
        super(par1Str, par2Str);
        this.actions = par2Str;
        this.permissionMask = this.calculateMask(par2Str);
    }
    
    private int calculateMask(final String par1Str) {
        final StringTokenizer var2 = new StringTokenizer(Strings.toLowerCase(par1Str), " ,");
        int var3 = 0;
        while (var2.hasMoreTokens()) {
            final String var4 = var2.nextToken();
            if (var4.equals("threadlocalecimplicitlyca")) {
                var3 |= 0x1;
            }
            else if (var4.equals("ecimplicitlyca")) {
                var3 |= 0x2;
            }
            else if (var4.equals("threadlocaldhdefaultparams")) {
                var3 |= 0x4;
            }
            else if (var4.equals("dhdefaultparams")) {
                var3 |= 0x8;
            }
            else {
                if (!var4.equals("all")) {
                    continue;
                }
                var3 |= 0xF;
            }
        }
        if (var3 == 0) {
            throw new IllegalArgumentException("unknown permissions passed to mask");
        }
        return var3;
    }
    
    @Override
    public String getActions() {
        return this.actions;
    }
    
    @Override
    public boolean implies(final Permission par1Permission) {
        if (!(par1Permission instanceof ProviderConfigurationPermission)) {
            return false;
        }
        if (!this.getName().equals(par1Permission.getName())) {
            return false;
        }
        final ProviderConfigurationPermission var2 = (ProviderConfigurationPermission)par1Permission;
        return (this.permissionMask & var2.permissionMask) == var2.permissionMask;
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (par1Obj == this) {
            return true;
        }
        if (!(par1Obj instanceof ProviderConfigurationPermission)) {
            return false;
        }
        final ProviderConfigurationPermission var2 = (ProviderConfigurationPermission)par1Obj;
        return this.permissionMask == var2.permissionMask && this.getName().equals(var2.getName());
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode() + this.permissionMask;
    }
}
