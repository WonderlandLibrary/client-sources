package lunadevs.luna.utils.Values;

public class ValueUtils
{
  public static <T> int indexOf(T object, T[] array)
  {
    for (int i = 0; i < array.length; i++) {
      if (array[i].equals(object)) {
        return i;
      }
    }
    return -1;
  }
  
  public static <T> boolean arrayContains(T object, T[] array)
  {
    return indexOf(object, array) != -1;
  }
  
  public static <T> T valueFrom(T object, T[] array)
  {
    int index = indexOf(object, array);
    if (index != -1) {
      return (T)array[index];
    }
    if (array.length > 0) {
      return (T)array[0];
    }
    return object;
  }
  
  public static Number clamp_number(Number value, Number min, Number max)
  {
    value = Double.valueOf(Math.max(min.doubleValue(), value.doubleValue()));
    value = Double.valueOf(Math.min(max.doubleValue(), value.doubleValue()));
    return value;
  }
}
