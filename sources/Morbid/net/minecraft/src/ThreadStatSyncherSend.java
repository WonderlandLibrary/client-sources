package net.minecraft.src;

import java.util.*;

class ThreadStatSyncherSend extends Thread
{
    final Map field_77483_a;
    final StatsSyncher syncher;
    
    ThreadStatSyncherSend(final StatsSyncher par1StatsSyncher, final Map par2Map) {
        this.syncher = par1StatsSyncher;
        this.field_77483_a = par2Map;
    }
    
    @Override
    public void run() {
        try {
            StatsSyncher.func_77414_a(this.syncher, this.field_77483_a, StatsSyncher.getUnsentDataFile(this.syncher), StatsSyncher.getUnsentTempFile(this.syncher), StatsSyncher.getUnsentOldFile(this.syncher));
        }
        catch (Exception var5) {
            var5.printStackTrace();
            return;
        }
        finally {
            StatsSyncher.setBusy(this.syncher, false);
        }
        StatsSyncher.setBusy(this.syncher, false);
    }
}
