package net.futureclient.client;

import java.util.List;
import java.util.Map;
import java.io.StringReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

public class B
{
    public static final int H = 6;
    private LinkedList l;
    private int L;
    public static final int E = -1;
    public static final int A = 1;
    private j j;
    public static final int K = 2;
    public static final int M = 5;
    public static final int d = 3;
    private g a;
    public static final int D = 4;
    public static final int k = 0;
    
    public B() {
        super();
        this.a = new g((Reader)null);
        final int l = 0;
        this.j = null;
        this.L = l;
    }
    
    private void e() throws f, IOException {
        this.j = this.a.M();
        if (this.j == null) {
            this.j = new j(-1, null);
        }
    }
    
    public void M(final Reader reader) {
        this.a.M(reader);
        this.M();
    }
    
    public static String M(String s) {
        final char c = 'L';
        final char c2 = '\u0006';
        final int length = (s = s).length();
        final char[] array = new char[length];
        int n;
        int i = n = length - 1;
        final char[] array2 = array;
        final char c3 = c2;
        final char c4 = c;
        while (i >= 0) {
            final char[] array3 = array2;
            final String s2 = s;
            final int n2 = n;
            final char char1 = s2.charAt(n2);
            --n;
            array3[n2] = (char)(char1 ^ c4);
            if (n < 0) {
                break;
            }
            final char[] array4 = array2;
            final String s3 = s;
            final int n3 = n--;
            array4[n3] = (char)(s3.charAt(n3) ^ c3);
            i = n;
        }
        return new String(array2);
    }
    
    public Object M(final String s, final K k) throws f {
        final StringReader stringReader = new StringReader(s);
        try {
            return this.M(stringReader, k);
        }
        catch (IOException ex) {
            throw new f(-1, 2, ex);
        }
    }
    
    public void M(final String s, final h h) throws f {
        this.M(s, h, false);
    }
    
    public Object M(final String s) throws f {
        return this.M(s, (K)null);
    }
    
    public void M() {
        final LinkedList l = null;
        final int i = 0;
        this.j = null;
        this.L = i;
        this.l = l;
    }
    
    public void M(final Reader reader, final h h) throws IOException, f {
        this.M(reader, h, false);
    }
    
    public void M(final Reader reader, final h h, final boolean b) throws IOException, f {
        B b2;
        if (!b) {
            this.M(reader);
            b2 = this;
            this.l = new LinkedList();
        }
        else {
            if (this.l == null) {
                this.M(reader);
                this.l = new LinkedList();
            }
            b2 = this;
        }
        final LinkedList l = b2.l;
        try {
            do {
                B b3 = null;
                Label_0924: {
                    Label_0923: {
                        switch (this.L) {
                            case 0:
                                h.e();
                                this.e();
                                switch (this.j.A) {
                                    case 0:
                                        this.L = 1;
                                        l.addFirst(new Integer(this.L));
                                        if (!h.M(this.j.a)) {
                                            return;
                                        }
                                        break Label_0923;
                                    case 1:
                                        this.L = 2;
                                        l.addFirst(new Integer(this.L));
                                        if (!h.M()) {
                                            return;
                                        }
                                        break Label_0923;
                                    case 3:
                                        this.L = 3;
                                        l.addFirst(new Integer(this.L));
                                        if (!h.C()) {
                                            return;
                                        }
                                        break Label_0923;
                                    default:
                                        b3 = this;
                                        this.L = -1;
                                        break Label_0924;
                                }
                                break;
                            case 1:
                                this.e();
                                if (this.j.A == -1) {
                                    final int i = 6;
                                    h.M();
                                    this.L = i;
                                    return;
                                }
                                this.L = -1;
                                throw new f(this.M(), 1, this.j);
                            case 2:
                                this.e();
                                switch (this.j.A) {
                                    case 5:
                                        break Label_0923;
                                    case 0: {
                                        if (!(this.j.a instanceof String)) {
                                            b3 = this;
                                            this.L = -1;
                                            break Label_0924;
                                        }
                                        final String s = (String)this.j.a;
                                        final LinkedList<Integer> list = (LinkedList<Integer>)l;
                                        this.L = 4;
                                        list.addFirst(new Integer(this.L));
                                        if (!h.M(s)) {
                                            return;
                                        }
                                        break Label_0923;
                                    }
                                    case 2: {
                                        h h2;
                                        if (l.size() > 1) {
                                            h2 = h;
                                            l.removeFirst();
                                            this.L = this.M(l);
                                        }
                                        else {
                                            this.L = 1;
                                            h2 = h;
                                        }
                                        if (!h2.B()) {
                                            return;
                                        }
                                        break Label_0923;
                                    }
                                    default:
                                        b3 = this;
                                        this.L = -1;
                                        break Label_0924;
                                }
                                break;
                            case 4:
                                this.e();
                                switch (this.j.A) {
                                    case 6:
                                        break Label_0923;
                                    case 0:
                                        l.removeFirst();
                                        this.L = this.M(l);
                                        if (!h.M(this.j.a)) {
                                            return;
                                        }
                                        if (!h.b()) {
                                            return;
                                        }
                                        break Label_0923;
                                    case 3: {
                                        l.removeFirst();
                                        final int j = 3;
                                        final LinkedList<Integer> list2 = (LinkedList<Integer>)l;
                                        list2.addFirst(new Integer(5));
                                        this.L = j;
                                        list2.addFirst(new Integer(this.L));
                                        if (!h.C()) {
                                            return;
                                        }
                                        break Label_0923;
                                    }
                                    case 1: {
                                        l.removeFirst();
                                        final int k = 2;
                                        final LinkedList<Integer> list3 = (LinkedList<Integer>)l;
                                        list3.addFirst(new Integer(5));
                                        this.L = k;
                                        list3.addFirst(new Integer(this.L));
                                        if (!h.M()) {
                                            return;
                                        }
                                        break Label_0923;
                                    }
                                    default:
                                        b3 = this;
                                        this.L = -1;
                                        break Label_0924;
                                }
                                break;
                            case 5:
                                l.removeFirst();
                                this.L = this.M(l);
                                if (!h.b()) {
                                    return;
                                }
                                break;
                            case 3:
                                this.e();
                                switch (this.j.A) {
                                    case 5:
                                        break Label_0923;
                                    case 0:
                                        if (!h.M(this.j.a)) {
                                            return;
                                        }
                                        break Label_0923;
                                    case 4: {
                                        h h3;
                                        if (l.size() > 1) {
                                            h3 = h;
                                            l.removeFirst();
                                            this.L = this.M(l);
                                        }
                                        else {
                                            this.L = 1;
                                            h3 = h;
                                        }
                                        if (!h3.e()) {
                                            return;
                                        }
                                        break Label_0923;
                                    }
                                    case 1:
                                        this.L = 2;
                                        l.addFirst(new Integer(this.L));
                                        if (!h.M()) {
                                            return;
                                        }
                                        break Label_0923;
                                    case 3:
                                        this.L = 3;
                                        l.addFirst(new Integer(this.L));
                                        if (!h.C()) {
                                            return;
                                        }
                                        break Label_0923;
                                    default:
                                        this.L = -1;
                                        break Label_0923;
                                }
                                break;
                            case 6:
                                return;
                            case -1:
                                throw new f(this.M(), 1, this.j);
                        }
                    }
                    b3 = this;
                }
                if (b3.L == -1) {
                    throw new f(this.M(), 1, this.j);
                }
            } while (this.j.A != -1);
        }
        catch (IOException ex2) {
            final IOException ex = ex2;
            this.L = -1;
            throw ex;
        }
        catch (f f2) {
            final f f = f2;
            this.L = -1;
            throw f;
        }
        catch (RuntimeException ex4) {
            final RuntimeException ex3 = ex4;
            this.L = -1;
            throw ex3;
        }
        catch (Error error2) {
            final Error error = error2;
            this.L = -1;
            throw error;
        }
        this.L = -1;
        throw new f(this.M(), 1, this.j);
    }
    
    private int M(final LinkedList list) {
        if (list.size() == 0) {
            return -1;
        }
        return list.getFirst();
    }
    
    public Object M(final Reader reader) throws IOException, f {
        return this.M(reader, (K)null);
    }
    
    private Map M(final K k) {
        if (k == null) {
            return (Map)new m();
        }
        final Map m;
        if ((m = k.M()) == null) {
            return (Map)new m();
        }
        return m;
    }
    
    public Object M(final Reader reader, final K k) throws IOException, f {
        this.M(reader);
        final LinkedList<Integer> list = new LinkedList<Integer>();
        final LinkedList<Map<String, Object>> list2 = new LinkedList<Map<String, Object>>();
        try {
            do {
                this.e();
                B b = null;
                Label_0949: {
                    Label_0948: {
                        switch (this.L) {
                            case 0:
                                switch (this.j.A) {
                                    case 0: {
                                        b = this;
                                        final LinkedList<Map<String, Object>> list3 = list2;
                                        final LinkedList<Integer> list4 = list;
                                        this.L = 1;
                                        list4.addFirst(new Integer(this.L));
                                        list3.addFirst((Map<String, Object>)this.j.a);
                                        break Label_0949;
                                    }
                                    case 1: {
                                        b = this;
                                        final LinkedList<Map<String, Object>> list5 = list2;
                                        final LinkedList<Integer> list6 = list;
                                        this.L = 2;
                                        list6.addFirst(new Integer(this.L));
                                        list5.addFirst(this.M(k));
                                        break Label_0949;
                                    }
                                    case 3: {
                                        b = this;
                                        final LinkedList<Map<String, Object>> list7 = list2;
                                        final LinkedList<Integer> list8 = list;
                                        this.L = 3;
                                        list8.addFirst(new Integer(this.L));
                                        list7.addFirst((Map<String, Object>)this.M(k));
                                        break Label_0949;
                                    }
                                    default:
                                        b = this;
                                        this.L = -1;
                                        break Label_0949;
                                }
                                break;
                            case 1:
                                if (this.j.A == -1) {
                                    return list2.removeFirst();
                                }
                                throw new f(this.M(), 1, this.j);
                            case 2:
                                switch (this.j.A) {
                                    case 5:
                                        break Label_0948;
                                    case 0:
                                        if (this.j.a instanceof String) {
                                            final String s = (String)this.j.a;
                                            final LinkedList<Integer> list9 = list;
                                            list2.addFirst((Map<String, Object>)s);
                                            b = this;
                                            this.L = 4;
                                            list9.addFirst(new Integer(this.L));
                                            break Label_0949;
                                        }
                                        this.L = -1;
                                        b = this;
                                        break Label_0949;
                                    case 2:
                                        if (list2.size() > 1) {
                                            list.removeFirst();
                                            list2.removeFirst();
                                            b = this;
                                            this.L = this.M(list);
                                            break Label_0949;
                                        }
                                        b = this;
                                        this.L = 1;
                                        break Label_0949;
                                    default:
                                        b = this;
                                        this.L = -1;
                                        break Label_0949;
                                }
                                break;
                            case 4:
                                switch (this.j.A) {
                                    case 6:
                                        break Label_0948;
                                    case 0: {
                                        list.removeFirst();
                                        final String s2 = (String)list2.removeFirst();
                                        final Map<String, Object> map = list2.getFirst();
                                        final String s3 = s2;
                                        b = this;
                                        map.put(s3, this.j.a);
                                        this.L = this.M(list);
                                        break Label_0949;
                                    }
                                    case 3: {
                                        list.removeFirst();
                                        final String s4 = (String)list2.removeFirst();
                                        final Map<String, Object> map2 = list2.getFirst();
                                        b = this;
                                        final List m = this.M(k);
                                        map2.put(s4, m);
                                        final LinkedList<Map<String, Object>> list10 = list2;
                                        final Map<String, Object> map3 = (Map<String, Object>)m;
                                        final LinkedList<Integer> list11 = list;
                                        this.L = 3;
                                        list11.addFirst(new Integer(this.L));
                                        list10.addFirst(map3);
                                        break Label_0949;
                                    }
                                    case 1: {
                                        list.removeFirst();
                                        final String s5 = (String)list2.removeFirst();
                                        final Map<String, Object> map4 = list2.getFirst();
                                        b = this;
                                        final Map i = this.M(k);
                                        map4.put(s5, i);
                                        final LinkedList<Map<String, Object>> list12 = list2;
                                        final Map<String, Object> map5 = (Map<String, Object>)i;
                                        final LinkedList<Integer> list13 = list;
                                        this.L = 2;
                                        list13.addFirst(new Integer(this.L));
                                        list12.addFirst(map5);
                                        break Label_0949;
                                    }
                                    default:
                                        b = this;
                                        this.L = -1;
                                        break Label_0949;
                                }
                                break;
                            case 3:
                                switch (this.j.A) {
                                    case 5:
                                        break Label_0948;
                                    case 0: {
                                        final List<Object> list14 = (List<Object>)list2.getFirst();
                                        b = this;
                                        list14.add(this.j.a);
                                        break Label_0949;
                                    }
                                    case 4:
                                        if (list2.size() > 1) {
                                            list.removeFirst();
                                            list2.removeFirst();
                                            b = this;
                                            this.L = this.M(list);
                                            break Label_0949;
                                        }
                                        b = this;
                                        this.L = 1;
                                        break Label_0949;
                                    case 1: {
                                        final List<Object> list15 = (List<Object>)list2.getFirst();
                                        b = this;
                                        final Map j = this.M(k);
                                        list15.add((List<Object>)j);
                                        final LinkedList<Map<String, Object>> list16 = list2;
                                        final Map<String, Object> map6 = (Map<String, Object>)j;
                                        final LinkedList<Integer> list17 = list;
                                        this.L = 2;
                                        list17.addFirst(new Integer(this.L));
                                        list16.addFirst(map6);
                                        break Label_0949;
                                    }
                                    case 3: {
                                        final List<Object> list18 = (List<Object>)list2.getFirst();
                                        b = this;
                                        final List l = this.M(k);
                                        list18.add(l);
                                        final LinkedList<Map<String, Object>> list19 = list2;
                                        final Map<String, Object> map7 = (Map<String, Object>)l;
                                        final LinkedList<Integer> list20 = list;
                                        this.L = 3;
                                        list20.addFirst(new Integer(this.L));
                                        list19.addFirst(map7);
                                        break Label_0949;
                                    }
                                    default:
                                        b = this;
                                        this.L = -1;
                                        break Label_0949;
                                }
                                break;
                            case -1:
                                throw new f(this.M(), 1, this.j);
                        }
                    }
                    b = this;
                }
                if (b.L == -1) {
                    throw new f(this.M(), 1, this.j);
                }
            } while (this.j.A != -1);
        }
        catch (IOException ex) {
            throw ex;
        }
        throw new f(this.M(), 1, this.j);
    }
    
    public void M(final String s, final h h, final boolean b) throws f {
        final StringReader stringReader = new StringReader(s);
        try {
            this.M(stringReader, h, b);
        }
        catch (IOException ex) {
            throw new f(-1, 2, ex);
        }
    }
    
    public int M() {
        return this.a.b();
    }
    
    private List M(final K k) {
        if (k == null) {
            return (List)new e();
        }
        final List m;
        if ((m = k.M()) == null) {
            return (List)new e();
        }
        return m;
    }
}
