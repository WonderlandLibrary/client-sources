package us.dev.direkt.command.handler.arguments;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * @author Foundry
 */
public final class ArgumentStack {
    private final String[] arguments;
    private int size;
    private int cursor;
    private int previous = -1;

    public ArgumentStack(String... arguments) {
        this.arguments = arguments;
        this.size = arguments.length;
    }

    public int getCursor() {
        return this.cursor;
    }

    public void setCursor(int index) {
        this.cursor = index < 0 ? 0 : (index > this.arguments.length ? this.arguments.length : index);
    }

    public boolean hasNext() {
        return this.cursor != this.size;
    }

    public String next() {
        if (this.cursor >= this.size) {
            throw new NoSuchElementException();
        }
        this.previous = this.cursor++;
        return this.arguments[this.previous];
    }

    public String remove() {
        if (this.previous < 0) {
            throw new IllegalStateException();
        }
        int index = this.previous;
        String oldValue = this.arguments[index];
        int numMoved = this.size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(this.arguments, index + 1, this.arguments, index, numMoved);
        }
        this.cursor = this.previous;
        this.previous = -1;
        this.arguments[--this.size] = null;
        return oldValue;
    }

    public boolean hasPrevious() {
        return this.cursor != 0;
    }

    public int nextIndex() {
        return this.cursor;
    }

    public int previousIndex() {
        return this.cursor - 1;
    }

    public String previous() {
        if (--this.cursor < 0) {
            throw new NoSuchElementException();
        }
        this.previous = this.cursor;
        return this.arguments[this.previous];
    }

    public ArgumentStack copy() {
        return new ArgumentStack(Arrays.copyOf(this.arguments, this.arguments.length - 1));
    }
}

