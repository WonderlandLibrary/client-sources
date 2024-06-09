package org.lwjglx;

/**
 * Base PointerWrapper implementation.
 *
 * @author Spasi
 */
public abstract class PointerWrapperAbstract implements PointerWrapper {

	protected final long pointer;

	protected PointerWrapperAbstract(final long pointer) {
		this.pointer = pointer;
	}

	/**
	 * Returns true if this object represents a valid pointer.
	 * The pointer might be invalid because it is NULL or because
	 * some other action has deleted the object that this pointer
	 * represents.
	 *
	 * @return true if the pointer is valid
	 */
	public boolean isValid() {
		return pointer != 0;
	}

	/**
	 * Checks if the pointer is valid and throws an IllegalStateException if
	 * it is not. This method is a NO-OP, unless the org.lwjgl.util.Debug
	 * property has been set to true.
	 */
	public final void checkValid() {
		if ( LWJGLUtil.DEBUG && !isValid() )
			throw new IllegalStateException("This " + getClass().getSimpleName() + " pointer is not valid.");
	}

	public final long getPointer() {
		checkValid();
		return pointer;
	}

	public boolean equals(final Object o) {
		if ( this == o ) return true;
		if ( !(o instanceof PointerWrapperAbstract) ) return false;

		final PointerWrapperAbstract that = (PointerWrapperAbstract)o;

		if ( pointer != that.pointer ) return false;

		return true;
	}

	public int hashCode() {
		return (int)(pointer ^ (pointer >>> 32));
	}

	public String toString() {
		return getClass().getSimpleName() + " pointer (0x" + Long.toHexString(pointer).toUpperCase() + ")";
	}
}