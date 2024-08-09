/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.util.AsciiString;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;

public class HttpMethod
implements Comparable<HttpMethod> {
    public static final HttpMethod OPTIONS = new HttpMethod("OPTIONS");
    public static final HttpMethod GET = new HttpMethod("GET");
    public static final HttpMethod HEAD = new HttpMethod("HEAD");
    public static final HttpMethod POST = new HttpMethod("POST");
    public static final HttpMethod PUT = new HttpMethod("PUT");
    public static final HttpMethod PATCH = new HttpMethod("PATCH");
    public static final HttpMethod DELETE = new HttpMethod("DELETE");
    public static final HttpMethod TRACE = new HttpMethod("TRACE");
    public static final HttpMethod CONNECT = new HttpMethod("CONNECT");
    private static final EnumNameMap<HttpMethod> methodMap = new EnumNameMap(new EnumNameMap.Node<HttpMethod>(OPTIONS.toString(), OPTIONS), new EnumNameMap.Node<HttpMethod>(GET.toString(), GET), new EnumNameMap.Node<HttpMethod>(HEAD.toString(), HEAD), new EnumNameMap.Node<HttpMethod>(POST.toString(), POST), new EnumNameMap.Node<HttpMethod>(PUT.toString(), PUT), new EnumNameMap.Node<HttpMethod>(PATCH.toString(), PATCH), new EnumNameMap.Node<HttpMethod>(DELETE.toString(), DELETE), new EnumNameMap.Node<HttpMethod>(TRACE.toString(), TRACE), new EnumNameMap.Node<HttpMethod>(CONNECT.toString(), CONNECT));
    private final AsciiString name;

    public static HttpMethod valueOf(String string) {
        HttpMethod httpMethod = methodMap.get(string);
        return httpMethod != null ? httpMethod : new HttpMethod(string);
    }

    public HttpMethod(String string) {
        string = ObjectUtil.checkNotNull(string, "name").trim();
        if (string.isEmpty()) {
            throw new IllegalArgumentException("empty name");
        }
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (!Character.isISOControl(c) && !Character.isWhitespace(c)) continue;
            throw new IllegalArgumentException("invalid character in name");
        }
        this.name = AsciiString.cached(string);
    }

    public String name() {
        return this.name.toString();
    }

    public AsciiString asciiName() {
        return this.name;
    }

    public int hashCode() {
        return this.name().hashCode();
    }

    public boolean equals(Object object) {
        if (!(object instanceof HttpMethod)) {
            return true;
        }
        HttpMethod httpMethod = (HttpMethod)object;
        return this.name().equals(httpMethod.name());
    }

    public String toString() {
        return this.name.toString();
    }

    @Override
    public int compareTo(HttpMethod httpMethod) {
        return this.name().compareTo(httpMethod.name());
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((HttpMethod)object);
    }

    private static final class EnumNameMap<T> {
        private final Node<T>[] values;
        private final int valuesMask;

        EnumNameMap(Node<T> ... nodeArray) {
            this.values = new Node[MathUtil.findNextPositivePowerOfTwo(nodeArray.length)];
            this.valuesMask = this.values.length - 1;
            for (Node<T> node : nodeArray) {
                int n = EnumNameMap.hashCode(node.key) & this.valuesMask;
                if (this.values[n] != null) {
                    throw new IllegalArgumentException("index " + n + " collision between values: [" + this.values[n].key + ", " + node.key + ']');
                }
                this.values[n] = node;
            }
        }

        T get(String string) {
            Node<T> node = this.values[EnumNameMap.hashCode(string) & this.valuesMask];
            return node == null || !node.key.equals(string) ? null : (T)node.value;
        }

        private static int hashCode(String string) {
            return string.hashCode() >>> 6;
        }

        private static final class Node<T> {
            final String key;
            final T value;

            Node(String string, T t) {
                this.key = string;
                this.value = t;
            }
        }
    }
}

