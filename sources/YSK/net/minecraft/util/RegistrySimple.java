package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import org.apache.commons.lang3.*;

public class RegistrySimple<K, V> implements IRegistry<K, V>
{
    private static final Logger logger;
    protected final Map<K, V> registryObjects;
    private static final String[] I;
    
    public boolean containsKey(final K k) {
        return this.registryObjects.containsKey(k);
    }
    
    @Override
    public Iterator<V> iterator() {
        return this.registryObjects.values().iterator();
    }
    
    public Set<K> getKeys() {
        return Collections.unmodifiableSet((Set<? extends K>)this.registryObjects.keySet());
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(";<\u00149\u0001\u001dx\u0014%\u001f\u00161\u00131\u001b\u001fx\u001b5\u0016Z\u007f", "zXpPo");
        RegistrySimple.I[" ".length()] = I("TI\u001d y\u0001\f\u000e&*\u0007\u001b\u0010", "siiOY");
    }
    
    protected Map<K, V> createUnderlyingMap() {
        return (Map<K, V>)Maps.newHashMap();
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    @Override
    public V getObject(final K k) {
        return this.registryObjects.get(k);
    }
    
    public RegistrySimple() {
        this.registryObjects = this.createUnderlyingMap();
    }
    
    @Override
    public void putObject(final K k, final V v) {
        Validate.notNull((Object)k);
        Validate.notNull((Object)v);
        if (this.registryObjects.containsKey(k)) {
            RegistrySimple.logger.debug(RegistrySimple.I["".length()] + k + RegistrySimple.I[" ".length()]);
        }
        this.registryObjects.put(k, v);
    }
}
