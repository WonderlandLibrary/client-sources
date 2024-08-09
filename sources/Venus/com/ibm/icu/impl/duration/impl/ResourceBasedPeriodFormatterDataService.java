/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration.impl;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.duration.impl.DataRecord;
import com.ibm.icu.impl.duration.impl.PeriodFormatterData;
import com.ibm.icu.impl.duration.impl.PeriodFormatterDataService;
import com.ibm.icu.impl.duration.impl.XMLRecordReader;
import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

public class ResourceBasedPeriodFormatterDataService
extends PeriodFormatterDataService {
    private Collection<String> availableLocales;
    private PeriodFormatterData lastData = null;
    private String lastLocale = null;
    private Map<String, PeriodFormatterData> cache = new HashMap<String, PeriodFormatterData>();
    private static final String PATH = "data/";
    private static final ResourceBasedPeriodFormatterDataService singleton = new ResourceBasedPeriodFormatterDataService();

    public static ResourceBasedPeriodFormatterDataService getInstance() {
        return singleton;
    }

    private ResourceBasedPeriodFormatterDataService() {
        ArrayList<String> arrayList = new ArrayList<String>();
        InputStream inputStream = ICUData.getRequiredStream(this.getClass(), "data/index.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String string = null;
            while (null != (string = bufferedReader.readLine())) {
                if ((string = string.trim()).startsWith("#") || string.length() == 0) continue;
                arrayList.add(string);
            }
            bufferedReader.close();
        } catch (IOException iOException) {
            throw new IllegalStateException("IO Error reading data/index.txt: " + iOException.toString());
        } finally {
            try {
                inputStream.close();
            } catch (IOException iOException) {}
        }
        this.availableLocales = Collections.unmodifiableList(arrayList);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public PeriodFormatterData get(String string) {
        int n = string.indexOf(64);
        if (n != -1) {
            string = string.substring(0, n);
        }
        ResourceBasedPeriodFormatterDataService resourceBasedPeriodFormatterDataService = this;
        synchronized (resourceBasedPeriodFormatterDataService) {
            if (this.lastLocale != null && this.lastLocale.equals(string)) {
                return this.lastData;
            }
            PeriodFormatterData periodFormatterData = this.cache.get(string);
            if (periodFormatterData == null) {
                String string2 = string;
                while (!this.availableLocales.contains(string2)) {
                    int n2 = string2.lastIndexOf("_");
                    if (n2 > -1) {
                        string2 = string2.substring(0, n2);
                        continue;
                    }
                    if (!"test".equals(string2)) {
                        string2 = "test";
                        continue;
                    }
                    string2 = null;
                    break;
                }
                if (string2 == null) throw new MissingResourceException("Duration data not found for  " + string, PATH, string);
                String string3 = "data/pfd_" + string2 + ".xml";
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(ICUData.getRequiredStream(this.getClass(), string3), "UTF-8");
                    DataRecord dataRecord = DataRecord.read(string2, new XMLRecordReader(inputStreamReader));
                    inputStreamReader.close();
                    if (dataRecord != null) {
                        periodFormatterData = new PeriodFormatterData(string, dataRecord);
                    }
                } catch (UnsupportedEncodingException unsupportedEncodingException) {
                    throw new MissingResourceException("Unhandled encoding for resource " + string3, string3, "");
                } catch (IOException iOException) {
                    throw new ICUUncheckedIOException("Failed to close() resource " + string3, iOException);
                }
                this.cache.put(string, periodFormatterData);
            }
            this.lastData = periodFormatterData;
            this.lastLocale = string;
            return periodFormatterData;
        }
    }

    @Override
    public Collection<String> getAvailableLocales() {
        return this.availableLocales;
    }
}

