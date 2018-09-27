package module9;

/**
 * Dimensions class deals with the height and width. Used when scaling images.
 */
public class Dimensions {

	// Width
	private final int width;
	// Height
	private final int height;

	public Dimensions(final int width, final int height) {
		this.width = width;
		this.height = height;
	}

	// Retrieves width.
	public int getWidth() {
		return width;
	}

	// Retrieves height.
	public int getHeight() {
		return height;
	}

	// Returns a new Dimensions object that is the original one with it's
	// components divided by number.
	public Dimensions divide(final double number) {
		return new Dimensions((int) (width / number), (int) (height / number));
	}
}
