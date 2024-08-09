/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TextRenderingUtils {
    @VisibleForTesting
    protected static List<String> func_225223_a(String string) {
        return Arrays.asList(string.split("\\n"));
    }

    public static List<Line> func_225224_a(String string, LineSegment ... lineSegmentArray) {
        return TextRenderingUtils.func_225225_a(string, Arrays.asList(lineSegmentArray));
    }

    private static List<Line> func_225225_a(String string, List<LineSegment> list) {
        List<String> list2 = TextRenderingUtils.func_225223_a(string);
        return TextRenderingUtils.func_225222_a(list2, list);
    }

    private static List<Line> func_225222_a(List<String> list, List<LineSegment> list2) {
        int n = 0;
        ArrayList<Line> arrayList = Lists.newArrayList();
        for (String string : list) {
            ArrayList<LineSegment> arrayList2 = Lists.newArrayList();
            for (String string2 : TextRenderingUtils.func_225226_a(string, "%link")) {
                if ("%link".equals(string2)) {
                    arrayList2.add(list2.get(n++));
                    continue;
                }
                arrayList2.add(LineSegment.func_225218_a(string2));
            }
            arrayList.add(new Line(arrayList2));
        }
        return arrayList;
    }

    public static List<String> func_225226_a(String string, String string2) {
        int n;
        if (string2.isEmpty()) {
            throw new IllegalArgumentException("Delimiter cannot be the empty string");
        }
        ArrayList<String> arrayList = Lists.newArrayList();
        int n2 = 0;
        while ((n = string.indexOf(string2, n2)) != -1) {
            if (n > n2) {
                arrayList.add(string.substring(n2, n));
            }
            arrayList.add(string2);
            n2 = n + string2.length();
        }
        if (n2 < string.length()) {
            arrayList.add(string.substring(n2));
        }
        return arrayList;
    }

    public static class LineSegment {
        private final String field_225219_a;
        private final String field_225220_b;
        private final String field_225221_c;

        private LineSegment(String string) {
            this.field_225219_a = string;
            this.field_225220_b = null;
            this.field_225221_c = null;
        }

        private LineSegment(String string, String string2, String string3) {
            this.field_225219_a = string;
            this.field_225220_b = string2;
            this.field_225221_c = string3;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                LineSegment lineSegment = (LineSegment)object;
                return Objects.equals(this.field_225219_a, lineSegment.field_225219_a) && Objects.equals(this.field_225220_b, lineSegment.field_225220_b) && Objects.equals(this.field_225221_c, lineSegment.field_225221_c);
            }
            return true;
        }

        public int hashCode() {
            return Objects.hash(this.field_225219_a, this.field_225220_b, this.field_225221_c);
        }

        public String toString() {
            return "Segment{fullText='" + this.field_225219_a + "', linkTitle='" + this.field_225220_b + "', linkUrl='" + this.field_225221_c + "'}";
        }

        public String func_225215_a() {
            return this.func_225217_b() ? this.field_225220_b : this.field_225219_a;
        }

        public boolean func_225217_b() {
            return this.field_225220_b != null;
        }

        public String func_225216_c() {
            if (!this.func_225217_b()) {
                throw new IllegalStateException("Not a link: " + this);
            }
            return this.field_225221_c;
        }

        public static LineSegment func_225214_a(String string, String string2) {
            return new LineSegment(null, string, string2);
        }

        @VisibleForTesting
        protected static LineSegment func_225218_a(String string) {
            return new LineSegment(string);
        }
    }

    public static class Line {
        public final List<LineSegment> field_225213_a;

        Line(List<LineSegment> list) {
            this.field_225213_a = list;
        }

        public String toString() {
            return "Line{segments=" + this.field_225213_a + "}";
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                Line line = (Line)object;
                return Objects.equals(this.field_225213_a, line.field_225213_a);
            }
            return true;
        }

        public int hashCode() {
            return Objects.hash(this.field_225213_a);
        }
    }
}

