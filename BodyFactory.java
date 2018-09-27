package module9;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * BodyFactory class allows us to create new Body objects, either Body or
 * ImagedBody, depending on whether it is possible to retrieve an image based on the a URL and the filename.
 */
public class BodyFactory {

	public static Body create(final String name, final double mass, Vector position, Vector velocity, String filename) {

		// Tries to retrieve an image from URL, if it is successful, creates an
		// ImagedBody object with that image.
		try {
			System.out.println("Requesting image: " + filename);
			BufferedImage image = ImageIO.read(new URL(filename));
			return new ImagedBody(name, mass, position, velocity, image);
		}
		// If unsuccessful, creates a Body object instead (same parameters, no
		// image).
		catch (IOException e) {
			return new Body(name, mass, position, velocity);
		}
	}
}
