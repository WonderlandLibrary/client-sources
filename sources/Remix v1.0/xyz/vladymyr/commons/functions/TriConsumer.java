package xyz.vladymyr.commons.functions;

@FunctionalInterface
public interface TriConsumer<A, B, C>
{
    void accept(final A p0, final B p1, final C p2);
}
