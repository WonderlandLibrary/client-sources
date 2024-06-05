package Focus.Beta.IMPL.set;

public class Color <T extends java.awt.Color> extends Value<T>{
    private T value;
    public Color(String displayName, String name, T value) {
        super(displayName, name);
        value = value;
    }

  public T getARGB(){
      return  this.value;
  }
}
