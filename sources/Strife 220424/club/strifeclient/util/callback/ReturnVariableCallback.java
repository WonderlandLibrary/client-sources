package club.strifeclient.util.callback;

public interface ReturnVariableCallback<T> extends VariableCallback<T> {
    T getCallback();
}
