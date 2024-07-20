/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.changelogs;

import ru.govno.client.changelogs.ChangelogType;

public class ChangeLog {
    private String changeName;
    private final ChangelogType type;

    public ChangeLog(String name, ChangelogType type2) {
        this.changeName = name;
        this.type = type2;
        switch (type2) {
            case NONE: {
                this.changeName = ": " + this.changeName;
                break;
            }
            case ADD: {
                this.changeName = "\u00a7r added \u00a77" + this.changeName;
                break;
            }
            case DELETE: {
                this.changeName = "\u00a7r deleted \u00a77" + this.changeName;
                break;
            }
            case IMPROVED: {
                this.changeName = "\u00a7r improved \u00a77" + this.changeName;
                break;
            }
            case FIXED: {
                this.changeName = "\u00a7r fixed \u00a77" + this.changeName;
                break;
            }
            case PROTOTYPE: {
                this.changeName = "\u00a7r prototype \u00a77" + this.changeName;
                break;
            }
            case NEW: {
                this.changeName = "\u00a7r new \u00a77" + this.changeName;
            }
        }
    }

    public String getChangeName() {
        return this.changeName;
    }

    public ChangelogType getType() {
        return this.type;
    }
}

