/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import net.optifine.Log;
import net.optifine.reflect.IFieldLocator;

public class FieldLocatorTypes
implements IFieldLocator {
    private Field field = null;

    public FieldLocatorTypes(Class clazz, Class[] classArray, Class clazz2, Class[] classArray2, String string) {
        Field[] fieldArray = clazz.getDeclaredFields();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < fieldArray.length; ++i) {
            Field field = fieldArray[i];
            arrayList.add(field.getType());
        }
        ArrayList<Class> arrayList2 = new ArrayList<Class>();
        arrayList2.addAll(Arrays.asList(classArray));
        arrayList2.add(clazz2);
        arrayList2.addAll(Arrays.asList(classArray2));
        int n = Collections.indexOfSubList(arrayList, arrayList2);
        if (n < 0) {
            Log.log("(Reflector) Field not found: " + string);
        } else {
            int n2 = Collections.indexOfSubList(arrayList.subList(n + 1, arrayList.size()), arrayList2);
            if (n2 >= 0) {
                Log.log("(Reflector) More than one match found for field: " + string);
            } else {
                int n3 = n + classArray.length;
                this.field = fieldArray[n3];
            }
        }
    }

    @Override
    public Field getField() {
        return this.field;
    }
}

