/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.util.DomainType;
import org.apache.http.conn.util.PublicSuffixList;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public final class PublicSuffixListParser {
    public PublicSuffixList parse(Reader reader) throws IOException {
        String string;
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        BufferedReader bufferedReader = new BufferedReader(reader);
        while ((string = bufferedReader.readLine()) != null) {
            boolean bl;
            if (string.isEmpty() || string.startsWith("//")) continue;
            if (string.startsWith(".")) {
                string = string.substring(1);
            }
            if (bl = string.startsWith("!")) {
                string = string.substring(1);
            }
            if (bl) {
                arrayList2.add(string);
                continue;
            }
            arrayList.add(string);
        }
        return new PublicSuffixList(DomainType.UNKNOWN, arrayList, arrayList2);
    }

    public List<PublicSuffixList> parseByType(Reader reader) throws IOException {
        String string;
        ArrayList<PublicSuffixList> arrayList = new ArrayList<PublicSuffixList>(2);
        BufferedReader bufferedReader = new BufferedReader(reader);
        DomainType domainType = null;
        ArrayList<String> arrayList2 = null;
        ArrayList<String> arrayList3 = null;
        while ((string = bufferedReader.readLine()) != null) {
            boolean bl;
            if (string.isEmpty()) continue;
            if (string.startsWith("//")) {
                if (domainType == null) {
                    if (string.contains("===BEGIN ICANN DOMAINS===")) {
                        domainType = DomainType.ICANN;
                        continue;
                    }
                    if (!string.contains("===BEGIN PRIVATE DOMAINS===")) continue;
                    domainType = DomainType.PRIVATE;
                    continue;
                }
                if (!string.contains("===END ICANN DOMAINS===") && !string.contains("===END PRIVATE DOMAINS===")) continue;
                if (arrayList2 != null) {
                    arrayList.add(new PublicSuffixList(domainType, arrayList2, arrayList3));
                }
                domainType = null;
                arrayList2 = null;
                arrayList3 = null;
                continue;
            }
            if (domainType == null) continue;
            if (string.startsWith(".")) {
                string = string.substring(1);
            }
            if (bl = string.startsWith("!")) {
                string = string.substring(1);
            }
            if (bl) {
                if (arrayList3 == null) {
                    arrayList3 = new ArrayList<String>();
                }
                arrayList3.add(string);
                continue;
            }
            if (arrayList2 == null) {
                arrayList2 = new ArrayList<String>();
            }
            arrayList2.add(string);
        }
        return arrayList;
    }
}

