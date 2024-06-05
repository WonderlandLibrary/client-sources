package net.minecraft.profiler;

import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;

class PlayerUsageSnooper$1 extends TimerTask {
    @Override
    public void run() {
        if (PlayerUsageSnooper.access$000(PlayerUsageSnooper.this).isSnooperEnabled()) {
            final HashMap var1;
            synchronized (PlayerUsageSnooper.access$100(PlayerUsageSnooper.this)) {
                var1 = Maps.newHashMap(PlayerUsageSnooper.access$200(PlayerUsageSnooper.this));
                if (PlayerUsageSnooper.access$300(PlayerUsageSnooper.this) == 0) {
                    var1.putAll(PlayerUsageSnooper.access$400(PlayerUsageSnooper.this));
                }
                var1.put("snooper_count", PlayerUsageSnooper.access$308(PlayerUsageSnooper.this));
                var1.put("snooper_token", PlayerUsageSnooper.access$500(PlayerUsageSnooper.this));
            }
            HttpUtil.postMap(PlayerUsageSnooper.access$600(PlayerUsageSnooper.this), var1, true);
        }
    }
}