package net.minecraft.src;

import java.io.*;
import java.util.*;

class TimerTaskMcoServerListUpdate extends TimerTask
{
    McoClient field_98262_a;
    final McoServerList field_98261_b;
    
    private TimerTaskMcoServerListUpdate(final McoServerList par1) {
        this.field_98261_b = par1;
        this.field_98262_a = new McoClient(McoServerList.func_100014_a(this.field_98261_b));
    }
    
    @Override
    public void run() {
        if (!McoServerList.func_98249_b(this.field_98261_b)) {
            this.func_98260_a();
        }
    }
    
    private void func_98260_a() {
        try {
            final List var1 = this.field_98262_a.func_96382_a().field_96430_d;
            this.func_101018_a(var1);
            McoServerList.func_98247_a(this.field_98261_b, var1);
        }
        catch (ExceptionMcoService exceptionMcoService) {}
        catch (IOException var2) {
            System.err.println(var2);
        }
    }
    
    private void func_101018_a(final List par1List) {
        Collections.sort((List<Object>)par1List, new TimerTaskMcoServerListUpdateComparator(this, McoServerList.func_100014_a(this.field_98261_b).username, null));
    }
    
    TimerTaskMcoServerListUpdate(final McoServerList par1, final McoServerListINNER1 par2) {
        this(par1);
    }
}
