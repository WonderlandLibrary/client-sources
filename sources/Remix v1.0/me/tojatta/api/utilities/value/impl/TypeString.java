package me.tojatta.api.utilities.value.impl;

import me.tojatta.api.utilities.value.*;
import java.lang.reflect.*;

public class TypeString extends Value<String>
{
    public TypeString(final String label, final Object object, final Field field) {
        super(label, object, field);
    }
}
