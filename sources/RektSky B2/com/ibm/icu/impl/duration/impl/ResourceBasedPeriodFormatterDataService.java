package com.ibm.icu.impl.duration.impl;

import com.ibm.icu.impl.*;
import java.util.*;
import java.io.*;
import com.ibm.icu.util.*;

public class ResourceBasedPeriodFormatterDataService extends PeriodFormatterDataService
{
    private Collection<String> availableLocales;
    private PeriodFormatterData lastData;
    private String lastLocale;
    private Map<String, PeriodFormatterData> cache;
    private static final String PATH = "data/";
    private static final ResourceBasedPeriodFormatterDataService singleton;
    
    public static ResourceBasedPeriodFormatterDataService getInstance() {
        return ResourceBasedPeriodFormatterDataService.singleton;
    }
    
    private ResourceBasedPeriodFormatterDataService() {
        this.lastData = null;
        this.lastLocale = null;
        this.cache = new HashMap<String, PeriodFormatterData>();
        final List<String> localeNames = new ArrayList<String>();
        final InputStream is = ICUData.getRequiredStream(this.getClass(), "data/index.txt");
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String string = null;
            while (null != (string = br.readLine())) {
                string = string.trim();
                if (!string.startsWith("#")) {
                    if (string.length() == 0) {
                        continue;
                    }
                    localeNames.add(string);
                }
            }
            br.close();
        }
        catch (IOException e) {
            throw new IllegalStateException("IO Error reading data/index.txt: " + e.toString());
        }
        finally {
            try {
                is.close();
            }
            catch (IOException ex) {}
        }
        this.availableLocales = (Collection<String>)Collections.unmodifiableList((List<?>)localeNames);
    }
    
    @Override
    public PeriodFormatterData get(String localeName) {
        final int x = localeName.indexOf(64);
        if (x != -1) {
            localeName = localeName.substring(0, x);
        }
        synchronized (this) {
            if (this.lastLocale != null && this.lastLocale.equals(localeName)) {
                return this.lastData;
            }
            PeriodFormatterData ld = this.cache.get(localeName);
            if (ld == null) {
                String ln = localeName;
                while (!this.availableLocales.contains(ln)) {
                    final int ix = ln.lastIndexOf("_");
                    if (ix > -1) {
                        ln = ln.substring(0, ix);
                    }
                    else {
                        if ("test".equals(ln)) {
                            ln = null;
                            break;
                        }
                        ln = "test";
                    }
                }
                if (ln == null) {
                    throw new MissingResourceException("Duration data not found for  " + localeName, "data/", localeName);
                }
                final String name = "data/pfd_" + ln + ".xml";
                try {
                    final InputStreamReader reader = new InputStreamReader(ICUData.getRequiredStream(this.getClass(), name), "UTF-8");
                    final DataRecord dr = DataRecord.read(ln, new XMLRecordReader(reader));
                    reader.close();
                    if (dr != null) {
                        ld = new PeriodFormatterData(localeName, dr);
                    }
                }
                catch (UnsupportedEncodingException e2) {
                    throw new MissingResourceException("Unhandled encoding for resource " + name, name, "");
                }
                catch (IOException e) {
                    throw new ICUUncheckedIOException("Failed to close() resource " + name, e);
                }
                this.cache.put(localeName, ld);
            }
            this.lastData = ld;
            this.lastLocale = localeName;
            return ld;
        }
    }
    
    @Override
    public Collection<String> getAvailableLocales() {
        return this.availableLocales;
    }
    
    static {
        singleton = new ResourceBasedPeriodFormatterDataService();
    }
}
