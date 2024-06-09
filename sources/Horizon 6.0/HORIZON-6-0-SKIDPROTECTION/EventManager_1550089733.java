package HORIZON-6-0-SKIDPROTECTION;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventManager_1550089733
{
    private static final Map<Class<? extends Event>, FlexibleArray<MethodData>> HorizonCode_Horizon_È;
    
    static {
        HorizonCode_Horizon_È = new HashMap<Class<? extends Event>, FlexibleArray<MethodData>>();
    }
    
    public static void HorizonCode_Horizon_È(final Object object) {
        Method[] var4;
        for (int var3 = (var4 = object.getClass().getDeclaredMethods()).length, var5 = 0; var5 < var3; ++var5) {
            final Method method = var4[var5];
            if (!HorizonCode_Horizon_È(method)) {
                HorizonCode_Horizon_È(method, object);
            }
        }
    }
    
    public static void HorizonCode_Horizon_È(final Object object, final Class<? extends Event> eventClass) {
        Method[] var5;
        for (int var4 = (var5 = object.getClass().getDeclaredMethods()).length, var6 = 0; var6 < var4; ++var6) {
            final Method method = var5[var6];
            if (!HorizonCode_Horizon_È(method, eventClass)) {
                HorizonCode_Horizon_È(method, object);
            }
        }
    }
    
    public static void Â(final Object object) {
        for (final FlexibleArray dataList : EventManager_1550089733.HorizonCode_Horizon_È.values()) {
            for (final MethodData data : dataList) {
                if (data.HorizonCode_Horizon_È.equals(object)) {
                    dataList.Â(data);
                }
            }
        }
        HorizonCode_Horizon_È(true);
    }
    
    public static void Â(final Object object, final Class<? extends Event> eventClass) {
        if (EventManager_1550089733.HorizonCode_Horizon_È.containsKey(eventClass)) {
            for (final MethodData data : EventManager_1550089733.HorizonCode_Horizon_È.get(eventClass)) {
                if (data.HorizonCode_Horizon_È.equals(object)) {
                    EventManager_1550089733.HorizonCode_Horizon_È.get(eventClass).Â(data);
                }
            }
            HorizonCode_Horizon_È(true);
        }
    }
    
    private static void HorizonCode_Horizon_È(final Method method, final Object object) {
        final Class indexClass = method.getParameterTypes()[0];
        final MethodData data = new MethodData(object, method, method.getAnnotation(Handler.class).HorizonCode_Horizon_È());
        if (!data.Â.isAccessible()) {
            data.Â.setAccessible(true);
        }
        if (EventManager_1550089733.HorizonCode_Horizon_È.containsKey(indexClass)) {
            if (!EventManager_1550089733.HorizonCode_Horizon_È.get(indexClass).Ý(data)) {
                EventManager_1550089733.HorizonCode_Horizon_È.get(indexClass).HorizonCode_Horizon_È(data);
                Ý(indexClass);
            }
        }
        else {
            EventManager_1550089733.HorizonCode_Horizon_È.put(indexClass, new FlexibleArray(data) {
                private static final long HorizonCode_Horizon_È = 666L;
                
                {
                    this.HorizonCode_Horizon_È(t);
                }
            });
        }
    }
    
    public static void HorizonCode_Horizon_È(final Class<? extends Event> indexClass) {
        final Iterator mapIterator = EventManager_1550089733.HorizonCode_Horizon_È.entrySet().iterator();
        while (mapIterator.hasNext()) {
            if (mapIterator.next().getKey().equals(indexClass)) {
                mapIterator.remove();
                break;
            }
        }
    }
    
    public static void HorizonCode_Horizon_È(final boolean onlyEmptyEntries) {
        final Iterator mapIterator = EventManager_1550089733.HorizonCode_Horizon_È.entrySet().iterator();
        while (mapIterator.hasNext()) {
            if (!onlyEmptyEntries || mapIterator.next().getValue().Ø­áŒŠá()) {
                mapIterator.remove();
            }
        }
    }
    
    private static void Ý(final Class<? extends Event> indexClass) {
        final FlexibleArray sortedList = new FlexibleArray();
        final byte[] var5 = Priority.Ó;
        for (int var6 = Priority.Ó.length, var7 = 0; var7 < var6; ++var7) {
            final byte priority = var5[var7];
            for (final MethodData data : EventManager_1550089733.HorizonCode_Horizon_È.get(indexClass)) {
                if (data.Ý == priority) {
                    sortedList.HorizonCode_Horizon_È(data);
                }
            }
        }
        EventManager_1550089733.HorizonCode_Horizon_È.put(indexClass, sortedList);
    }
    
    private static boolean HorizonCode_Horizon_È(final Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(Handler.class);
    }
    
    private static boolean HorizonCode_Horizon_È(final Method method, final Class<? extends Event> eventClass) {
        return HorizonCode_Horizon_È(method) || !method.getParameterTypes()[0].equals(eventClass);
    }
    
    public static FlexibleArray<MethodData> Â(final Class<? extends Event> clazz) {
        return EventManager_1550089733.HorizonCode_Horizon_È.get(clazz);
    }
}
