package i.gishreloaded.protection.annotation;

public @interface Protect {
    Type value();

    enum Type {
        VIRTUALIZATION,
        ULTRA
    }
}
