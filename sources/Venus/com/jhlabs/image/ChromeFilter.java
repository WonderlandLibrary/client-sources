/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.LightFilter;
import com.jhlabs.image.TransferFilter;
import java.awt.image.BufferedImage;

public class ChromeFilter
extends LightFilter {
    private float amount = 0.5f;
    private float exposure = 1.0f;

    public void setAmount(float f) {
        this.amount = f;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setExposure(float f) {
        this.exposure = f;
    }

    public float getExposure() {
        return this.exposure;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        this.setColorSource(1);
        bufferedImage2 = super.filter(bufferedImage, bufferedImage2);
        TransferFilter transferFilter = new TransferFilter(this){
            final ChromeFilter this$0;
            {
                this.this$0 = chromeFilter;
            }

            @Override
            protected float transferFunction(float f) {
                f += this.this$0.amount * (float)Math.sin((double)(f * 2.0f) * Math.PI);
                return 1.0f - (float)Math.exp(-f * this.this$0.exposure);
            }
        };
        return transferFilter.filter(bufferedImage2, bufferedImage2);
    }

    @Override
    public String toString() {
        return "Effects/Chrome...";
    }
}

