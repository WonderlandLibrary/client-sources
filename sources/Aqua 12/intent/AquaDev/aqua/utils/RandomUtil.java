// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil
{
    public static RandomUtil instance;
    
    public int nextInt(final int origin, final int bound) {
        if (origin == bound) {
            return origin;
        }
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }
    
    public int nextInt(final double origin, final double bound) {
        if ((int)origin == (int)bound) {
            return (int)origin;
        }
        return ThreadLocalRandom.current().nextInt((int)origin, (int)bound);
    }
    
    public long nextLong(final long origin, final long bound) {
        if (origin == bound) {
            return origin;
        }
        return ThreadLocalRandom.current().nextLong(origin, bound);
    }
    
    public long nextLong(final double origin, final double bound) {
        if ((long)origin == (long)bound) {
            return (long)origin;
        }
        return ThreadLocalRandom.current().nextLong((long)origin, (long)bound);
    }
    
    public float nextFloat(final double origin, final double bound) {
        if (origin == bound) {
            return (float)origin;
        }
        return (float)ThreadLocalRandom.current().nextDouble((float)origin, (float)bound);
    }
    
    public float nextFloat(final float origin, final float bound) {
        if (origin == bound) {
            return origin;
        }
        return (float)ThreadLocalRandom.current().nextDouble(origin, bound);
    }
    
    public double nextDouble(final double origin, final double bound) {
        if (origin == bound) {
            return origin;
        }
        return ThreadLocalRandom.current().nextDouble(origin, bound);
    }
    
    public double nextSecureInt(final int origin, final int bound) {
        if (origin == bound) {
            return origin;
        }
        final SecureRandom secureRandom = new SecureRandom();
        final int difference = bound - origin;
        return origin + secureRandom.nextInt(difference);
    }
    
    public double nextSecureInt(final double origin, final double bound) {
        if ((int)origin == (int)bound) {
            return origin;
        }
        final SecureRandom secureRandom = new SecureRandom();
        final int difference = (int)bound - (int)origin;
        return origin + secureRandom.nextInt(difference);
    }
    
    public double nextSecureDouble(final double origin, final double bound) {
        if (origin == bound) {
            return origin;
        }
        final SecureRandom secureRandom = new SecureRandom();
        final double difference = bound - origin;
        return origin + secureRandom.nextDouble() * difference;
    }
    
    public float nextSecureFloat(final double origin, final double bound) {
        if (origin == bound) {
            return (float)origin;
        }
        final SecureRandom secureRandom = new SecureRandom();
        final float difference = (float)(bound - origin);
        return (float)(origin + secureRandom.nextFloat() * difference);
    }
    
    public double randomSin() {
        return Math.sin(RandomUtil.instance.nextDouble(0.0, 6.283185307179586));
    }
    
    static {
        RandomUtil.instance = new RandomUtil();
    }
}
