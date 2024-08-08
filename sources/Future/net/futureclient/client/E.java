package net.futureclient.client;

import java.util.StringTokenizer;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class E
{
    private String D;
    public List k;
    
    public E() {
        final String d = ",";
        super();
        this.D = d;
        this.k = new ArrayList();
    }
    
    public E(final String d, final String s) {
        final String d2 = ",";
        super();
        this.D = d2;
        this.k = new ArrayList();
        this.M(this.D = d, s, this.k);
    }
    
    public E(final String s) {
        final String d = ",";
        super();
        this.D = d;
        this.k = new ArrayList();
        this.M(s, this.D, this.k);
    }
    
    public E(final String s, final String s2, final boolean b) {
        final String d = ",";
        super();
        this.D = d;
        this.M(s, s2, this.k = new ArrayList(), b);
    }
    
    @Override
    public String toString() {
        return this.e(this.D);
    }
    
    public void b(final String s) {
        this.M(s, this.D, this.k);
    }
    
    public void e() {
        this.k.clear();
    }
    
    public String e(final String s) {
        final StringBuffer sb = new StringBuffer();
        int i = 0;
        int n = 0;
        while (i < this.k.size()) {
            if (n == 0) {
                sb.append(this.k.get(n));
            }
            else {
                sb.append(s);
                sb.append(this.k.get(n));
            }
            i = ++n;
        }
        return sb.toString();
    }
    
    public void e(final String d) {
        this.D = d;
    }
    
    public void M(final String s) {
        if (s == null) {
            return;
        }
        this.k.add(s.trim());
    }
    
    public void M(final String s, final String s2, final List list) {
        if (s == null || s2 == null) {
            return;
        }
        int i = 0;
        while (true) {
            do {
                final int n = i;
                final int index;
                if ((index = s.indexOf(s2, i)) == -1) {
                    final List<String> list2 = (List<String>)list;
                    list2.add(s.substring(n).trim());
                    return;
                }
                list.add(s.substring(n, index).trim());
                i = index + s2.length();
            } while (i != -1);
            final List<String> list2 = (List<String>)list;
            continue;
        }
    }
    
    public void M(final E e) {
        this.k.addAll(e.k);
    }
    
    public void M() {
        this.D = ",";
        this.k.clear();
    }
    
    public int M() {
        return this.k.size();
    }
    
    public void M(final String s, final String s2, final List list, final boolean b) {
        if (s == null || s2 == null) {
            return;
        }
        if (b) {
            final StringTokenizer stringTokenizer2;
            StringTokenizer stringTokenizer = stringTokenizer2 = new StringTokenizer(s, s2);
            while (stringTokenizer.hasMoreTokens()) {
                list.add((stringTokenizer = stringTokenizer2).nextToken().trim());
            }
        }
        else {
            this.M(s, s2, list);
        }
    }
    
    public static String M(String s) {
        final StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        final String string = new StringBuffer(stackTraceElement.getClassName()).append(stackTraceElement.getMethodName()).toString();
        final int n = string.length() - 1;
        final int n2 = 83;
        final int n3 = 84;
        final int length = (s = s).length();
        final char[] array = new char[length];
        int n4;
        int i = n4 = length - 1;
        final char[] array2 = array;
        final int n5 = n3;
        final int n6 = n2;
        int n7 = n;
        final int n8 = n;
        final String s2 = string;
        while (i >= 0) {
            final char[] array3 = array2;
            final int n9 = n6;
            final String s3 = s;
            final int n10 = n4--;
            array3[n10] = (char)(n9 ^ (s3.charAt(n10) ^ s2.charAt(n7)));
            if (n4 < 0) {
                break;
            }
            final char[] array4 = array2;
            final int n11 = n5;
            final String s4 = s;
            final int n12 = n4;
            final char c = (char)(n11 ^ (s4.charAt(n12) ^ s2.charAt(n7)));
            --n4;
            --n7;
            array4[n12] = c;
            if (n7 < 0) {
                n7 = n8;
            }
            i = n4;
        }
        return new String(array2);
    }
    
    public void M(final int n, final String s) {
        if (s == null) {
            return;
        }
        this.k.add(n, s.trim());
    }
    
    public void M(final String s, final String s2, final boolean b) {
        this.M(s, s2, this.k, b);
    }
    
    public String M(final int n) {
        return this.k.get(n);
    }
    
    public List M() {
        return this.k;
    }
    
    public String[] M() {
        return (String[])this.k.toArray();
    }
    
    public void M(final String s, final String s2) {
        this.M(s, s2, this.k);
    }
}
