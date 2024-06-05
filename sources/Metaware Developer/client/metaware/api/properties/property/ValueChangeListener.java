package client.metaware.api.properties.property;

public interface ValueChangeListener<T> {
    void onValueChange(T oldValue, T value);
}