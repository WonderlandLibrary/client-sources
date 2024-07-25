package club.bluezenith.events;

import java.lang.reflect.Method;
import java.util.Objects;

public class Subscription {

    private final Object parent;
    private final Method method;

    public Subscription(Object parent, Method method) {
        this.parent = parent;
        this.method = method;
    }

    public Object getParent() {
        return parent;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(parent, that.parent) && Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, method);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "parent=" + parent +
                ", method=" + method +
                '}';
    }
}
