/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.SimpleTimeZone;
import java.io.Serializable;

final class STZInfo
implements Serializable {
    private static final long serialVersionUID = -7849612037842370168L;
    int sy = -1;
    int sm = -1;
    int sdwm;
    int sdw;
    int st;
    int sdm;
    boolean sa;
    int em = -1;
    int edwm;
    int edw;
    int et;
    int edm;
    boolean ea;

    STZInfo() {
    }

    void setStart(int n, int n2, int n3, int n4, int n5, boolean bl) {
        this.sm = n;
        this.sdwm = n2;
        this.sdw = n3;
        this.st = n4;
        this.sdm = n5;
        this.sa = bl;
    }

    void setEnd(int n, int n2, int n3, int n4, int n5, boolean bl) {
        this.em = n;
        this.edwm = n2;
        this.edw = n3;
        this.et = n4;
        this.edm = n5;
        this.ea = bl;
    }

    void applyTo(SimpleTimeZone simpleTimeZone) {
        if (this.sy != -1) {
            simpleTimeZone.setStartYear(this.sy);
        }
        if (this.sm != -1) {
            if (this.sdm == -1) {
                simpleTimeZone.setStartRule(this.sm, this.sdwm, this.sdw, this.st);
            } else if (this.sdw == -1) {
                simpleTimeZone.setStartRule(this.sm, this.sdm, this.st);
            } else {
                simpleTimeZone.setStartRule(this.sm, this.sdm, this.sdw, this.st, this.sa);
            }
        }
        if (this.em != -1) {
            if (this.edm == -1) {
                simpleTimeZone.setEndRule(this.em, this.edwm, this.edw, this.et);
            } else if (this.edw == -1) {
                simpleTimeZone.setEndRule(this.em, this.edm, this.et);
            } else {
                simpleTimeZone.setEndRule(this.em, this.edm, this.edw, this.et, this.ea);
            }
        }
    }
}

