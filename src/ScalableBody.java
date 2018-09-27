package module9;

/**
 * ScalableBody class takes in a Body object and a Dimensions object. Allows
 * easy management of scaling drawings to the screen. The ScalableBody holds a
 * body objects as opposed to extending one.
 */
public class ScalableBody {
	// Spatial object.
	private final Body body;

	// A scale factor (height, width).
	private final Dimensions scaleFactor;

	public ScalableBody(final Body body, final Dimensions scaleFactor) {
		this.body = body;
		this.scaleFactor = scaleFactor;
	}

	// Retrieves Body body.
	public Body getBody() {
		return body;
	}

	// Retrieves Dimension scaleFactor.
	public Dimensions getScaleFactor() {
		return scaleFactor;
	}
}
