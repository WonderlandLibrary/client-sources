package net.minecraft.src;

import net.minecraft.client.*;

public abstract class TaskLongRunning implements Runnable
{
    protected GuiScreenLongRunningTask field_96579_b;
    
    public void func_96574_a(final GuiScreenLongRunningTask par1GuiScreenLongRunningTask) {
        this.field_96579_b = par1GuiScreenLongRunningTask;
    }
    
    public void func_96575_a(final String par1Str) {
        this.field_96579_b.func_96209_a(par1Str);
    }
    
    public void func_96576_b(final String par1Str) {
        this.field_96579_b.func_96210_b(par1Str);
    }
    
    public Minecraft func_96578_b() {
        return this.field_96579_b.func_96208_g();
    }
    
    public boolean func_96577_c() {
        return this.field_96579_b.func_96207_h();
    }
    
    public void func_96573_a() {
    }
    
    public void func_96572_a(final GuiButton par1GuiButton) {
    }
    
    public void func_96571_d() {
    }
}
