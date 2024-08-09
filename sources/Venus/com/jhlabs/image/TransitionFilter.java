/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class TransitionFilter
extends AbstractBufferedImageOp {
    private float transition = 0.0f;
    private BufferedImage destination;
    private String property;
    private Method method;
    protected BufferedImageOp filter;
    protected float minValue;
    protected float maxValue;

    private TransitionFilter() {
    }

    public TransitionFilter(BufferedImageOp bufferedImageOp, String string, float f, float f2) {
        this.filter = bufferedImageOp;
        this.property = string;
        this.minValue = f;
        this.maxValue = f2;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bufferedImageOp.getClass());
            PropertyDescriptor[] propertyDescriptorArray = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptorArray.length; ++i) {
                PropertyDescriptor propertyDescriptor = propertyDescriptorArray[i];
                if (!string.equals(propertyDescriptor.getName())) continue;
                this.method = propertyDescriptor.getWriteMethod();
                break;
            }
            if (this.method == null) {
                throw new IllegalArgumentException("No such property in object: " + string);
            }
        } catch (IntrospectionException introspectionException) {
            throw new IllegalArgumentException(introspectionException.toString());
        }
    }

    public void setTransition(float f) {
        this.transition = f;
    }

    public float getTransition() {
        return this.transition;
    }

    public void setDestination(BufferedImage bufferedImage) {
        this.destination = bufferedImage;
    }

    public BufferedImage getDestination() {
        return this.destination;
    }

    public void prepareFilter(float f) {
        try {
            this.method.invoke(this.filter, new Float(f));
        } catch (Exception exception) {
            throw new IllegalArgumentException("Error setting value for property: " + this.property);
        }
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        float f;
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        if (this.destination == null) {
            return bufferedImage2;
        }
        float f2 = 1.0f - this.transition;
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        if (this.transition != 1.0f) {
            f = this.minValue + this.transition * (this.maxValue - this.minValue);
            this.prepareFilter(f);
            graphics2D.drawImage(bufferedImage, this.filter, 0, 0);
        }
        if (this.transition != 0.0f) {
            graphics2D.setComposite(AlphaComposite.getInstance(3, this.transition));
            f = this.minValue + f2 * (this.maxValue - this.minValue);
            this.prepareFilter(f);
            graphics2D.drawImage(this.destination, this.filter, 0, 0);
        }
        graphics2D.dispose();
        return bufferedImage2;
    }

    public String toString() {
        return "Transitions/Transition...";
    }
}

