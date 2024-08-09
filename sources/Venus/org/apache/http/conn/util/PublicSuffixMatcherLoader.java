/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.util.DomainType;
import org.apache.http.conn.util.PublicSuffixList;
import org.apache.http.conn.util.PublicSuffixListParser;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.SAFE)
public final class PublicSuffixMatcherLoader {
    private static volatile PublicSuffixMatcher DEFAULT_INSTANCE;

    private static PublicSuffixMatcher load(InputStream inputStream) throws IOException {
        List<PublicSuffixList> list = new PublicSuffixListParser().parseByType(new InputStreamReader(inputStream, Consts.UTF_8));
        return new PublicSuffixMatcher(list);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static PublicSuffixMatcher load(URL uRL) throws IOException {
        Args.notNull(uRL, "URL");
        InputStream inputStream = uRL.openStream();
        try {
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(inputStream);
            return publicSuffixMatcher;
        } finally {
            inputStream.close();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static PublicSuffixMatcher load(File file) throws IOException {
        Args.notNull(file, "File");
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(fileInputStream);
            return publicSuffixMatcher;
        } finally {
            ((InputStream)fileInputStream).close();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static PublicSuffixMatcher getDefault() {
        if (DEFAULT_INSTANCE != null) return DEFAULT_INSTANCE;
        Class<PublicSuffixMatcherLoader> clazz = PublicSuffixMatcherLoader.class;
        synchronized (PublicSuffixMatcherLoader.class) {
            if (DEFAULT_INSTANCE != null) return DEFAULT_INSTANCE;
            URL uRL = PublicSuffixMatcherLoader.class.getResource("/mozilla/public-suffix-list.txt");
            if (uRL != null) {
                try {
                    DEFAULT_INSTANCE = PublicSuffixMatcherLoader.load(uRL);
                } catch (IOException iOException) {
                    Log log2 = LogFactory.getLog(PublicSuffixMatcherLoader.class);
                    if (!log2.isWarnEnabled()) return DEFAULT_INSTANCE;
                    log2.warn("Failure loading public suffix list from default resource", iOException);
                }
            } else {
                DEFAULT_INSTANCE = new PublicSuffixMatcher(DomainType.ICANN, Arrays.asList("com"), null);
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return DEFAULT_INSTANCE;
        }
    }
}

