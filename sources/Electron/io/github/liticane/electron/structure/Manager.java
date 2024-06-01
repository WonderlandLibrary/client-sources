package io.github.liticane.electron.structure;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Manager <T> {
    private final List<T> members = new ArrayList<>();

    public abstract void init();

    @SafeVarargs
    public final void register(T... members) {
        this.members.addAll(Arrays.asList(members));
    }

    @SafeVarargs
    public final void unregister(T ... members) {
        this.members.removeAll(Arrays.asList(members));
    }

    public void register(T members) {
        this.members.add(members);
    }

    public void unregister(T members) {
        this.members.remove(members);
    }

    public T getMemberBy(Predicate<? super T> predicate) {
        return this.members.stream().filter(predicate)
                .findFirst().orElse(null);
    }

    public List<T> getMembersBy(Predicate<? super T> predicate) {
        return this.members.stream().filter(predicate)
                .collect(Collectors.toList());
    }

    public List<T> getMembers() {
        return new ArrayList<>(this.members);
    }
}