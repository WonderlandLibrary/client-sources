package com.boomer.client.utils.value.impl;

import com.boomer.client.utils.value.Value;

/**
 * made by Xen for BoomerWare
 *
 * @since 7/21/2019
 **/
public class StringValue extends Value<String> {

    public StringValue(String label, String value) {
        super(label, value);
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
