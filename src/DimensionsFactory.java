package module9;

/**
 * A factory for conveniently creating Dimensions objects.
 */
public class DimensionsFactory {

	/**
	 * Creates a square object with all sides equal to {@code length}.
	 * 
	 * @param length
	 *            the length of each side.
	 * @return the square dimensions.
	 */
	public Dimensions square(final int length) {
		return new Dimensions(length, length);
	}
}
