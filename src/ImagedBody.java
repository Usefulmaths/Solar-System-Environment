package module9;

import java.awt.image.BufferedImage;

/**
 * ImagedBody class extends Body. Inherits all the properties of body and adds
 * an additional BufferedImage property so is able to be displayed on screen by
 * an image imported from a URL.
 */
public class ImagedBody extends Body {
	// Holds an image, ready for displaying on screen.
	private BufferedImage image;

	public ImagedBody(final String name, final double mass, final Vector position, final Vector velocity,
			final BufferedImage image) {
		super(name, mass, position, velocity);
		this.image = image;
	}

	// Retrieves image from body.
	public BufferedImage getImage() {
		return image;
	}
}
