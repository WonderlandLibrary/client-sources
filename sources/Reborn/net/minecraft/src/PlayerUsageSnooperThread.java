package net.minecraft.src;

import java.util.*;

class PlayerUsageSnooperThread extends TimerTask
{
    final PlayerUsageSnooper snooper;
    
    PlayerUsageSnooperThread(final PlayerUsageSnooper par1PlayerUsageSnooper) {
        this.snooper = par1PlayerUsageSnooper;
    }
    
    @Override
    public void run() {
        if (PlayerUsageSnooper.getStatsCollectorFor(this.snooper).isSnooperEnabled()) {
            final HashMap var1;
            synchronized (PlayerUsageSnooper.getSyncLockFor(this.snooper)) {
                var1 = new HashMap(PlayerUsageSnooper.getDataMapFor(this.snooper));
                var1.put("snooper_count", PlayerUsageSnooper.getSelfCounterFor(this.snooper));
            }
            // monitorexit(PlayerUsageSnooper.getSyncLockFor(this.snooper))
            HttpUtil.sendPost(PlayerUsageSnooper.getStatsCollectorFor(this.snooper).getLogAgent(), PlayerUsageSnooper.getServerUrlFor(this.snooper), var1, true);
        }
    }
}
