/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceJavaToLua;
import mpp.venusfr.scripts.interpreter.compiler.jse.JavaConstructor;
import mpp.venusfr.scripts.interpreter.compiler.jse.JavaInstance;
import mpp.venusfr.scripts.interpreter.compiler.jse.JavaMethod;

class JavaClass
extends JavaInstance
implements CoerceJavaToLua.Coercion {
    static final Map classes = Collections.synchronizedMap(new HashMap());
    static final LuaValue NEW = JavaClass.valueOf("new");
    Map fields;
    Map methods;
    Map innerclasses;

    static JavaClass forClass(Class clazz) {
        JavaClass javaClass = (JavaClass)classes.get(clazz);
        if (javaClass == null) {
            javaClass = new JavaClass(clazz);
            classes.put(clazz, javaClass);
        }
        return javaClass;
    }

    JavaClass(Class clazz) {
        super(clazz);
        this.jclass = this;
    }

    @Override
    public LuaValue coerce(Object object) {
        return this;
    }

    Field getField(LuaValue luaValue) {
        if (this.fields == null) {
            HashMap<LuaString, Field> hashMap = new HashMap<LuaString, Field>();
            Field[] fieldArray = ((Class)this.m_instance).getFields();
            for (int i = 0; i < fieldArray.length; ++i) {
                Field field = fieldArray[i];
                if (!Modifier.isPublic(field.getModifiers())) continue;
                hashMap.put(JavaClass.valueOf(field.getName()), field);
                try {
                    if (field.isAccessible()) continue;
                    field.setAccessible(false);
                    continue;
                } catch (SecurityException securityException) {
                    // empty catch block
                }
            }
            this.fields = hashMap;
        }
        return (Field)this.fields.get(luaValue);
    }

    LuaValue getMethod(LuaValue luaValue) {
        if (this.methods == null) {
            Object object;
            Constructor<?>[] constructorArray;
            HashMap<Object, ArrayList<JavaMethod>> hashMap = new HashMap<Object, ArrayList<JavaMethod>>();
            Method[] methodArray = ((Class)this.m_instance).getMethods();
            for (int i = 0; i < methodArray.length; ++i) {
                constructorArray = methodArray[i];
                if (!Modifier.isPublic(constructorArray.getModifiers())) continue;
                object = constructorArray.getName();
                ArrayList<JavaMethod> arrayList = (ArrayList<JavaMethod>)hashMap.get(object);
                if (arrayList == null) {
                    arrayList = new ArrayList<JavaMethod>();
                    hashMap.put(object, arrayList);
                }
                arrayList.add(JavaMethod.forMethod(constructorArray));
            }
            HashMap<LuaValue, Object> hashMap2 = new HashMap<LuaValue, Object>();
            constructorArray = ((Class)this.m_instance).getConstructors();
            object = new ArrayList();
            for (int i = 0; i < constructorArray.length; ++i) {
                if (!Modifier.isPublic(constructorArray[i].getModifiers())) continue;
                object.add(JavaConstructor.forConstructor(constructorArray[i]));
            }
            switch (object.size()) {
                case 0: {
                    break;
                }
                case 1: {
                    hashMap2.put(NEW, object.get(0));
                    break;
                }
                default: {
                    hashMap2.put(NEW, JavaConstructor.forConstructors(object.toArray(new JavaConstructor[object.size()])));
                }
            }
            for (Map.Entry entry : hashMap.entrySet()) {
                String string = (String)entry.getKey();
                List list = (List)entry.getValue();
                hashMap2.put(JavaClass.valueOf(string), list.size() == 1 ? list.get(0) : JavaMethod.forMethods(list.toArray(new JavaMethod[list.size()])));
            }
            this.methods = hashMap2;
        }
        return (LuaValue)this.methods.get(luaValue);
    }

    Class getInnerClass(LuaValue luaValue) {
        if (this.innerclasses == null) {
            HashMap hashMap = new HashMap();
            Class<?>[] classArray = ((Class)this.m_instance).getClasses();
            for (int i = 0; i < classArray.length; ++i) {
                Class<?> clazz = classArray[i];
                String string = clazz.getName();
                String string2 = string.substring(Math.max(string.lastIndexOf(36), string.lastIndexOf(46)) + 1);
                hashMap.put(JavaClass.valueOf(string2), clazz);
            }
            this.innerclasses = hashMap;
        }
        return (Class)this.innerclasses.get(luaValue);
    }

    public LuaValue getConstructor() {
        return this.getMethod(NEW);
    }
}

