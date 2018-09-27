package module9;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * SolarSystemView class extends JPanel and implements MouseDragListener. This
 * class deals with painting the solar system objects on screen, the interactive
 * mouse dragging, and toggling names feature.
 */
public class SolarSystemView extends JPanel implements MouseDragListener {
	// List of all bodies in solar system
	private final List<ScalableBody> bodies;

	// Initial zoom value.
	private double zoomValue = 10;

	// Toggle names boolean false (don't display names initially)
	private static boolean toggleNames = false;

	// Holds backgroundImage
	private BufferedImage backgroundImage;

	// Keeps track of last position of dragged mouse.
	private Vector previousMouseDragPosition;

	// Amount to offset objects due to dragging.
	private Vector viewportOffset = new Vector(0, 0);

	// DimensionFactory object to easily manage square dimensions.
	private final DimensionsFactory dimensionsFactory = new DimensionsFactory();

	public SolarSystemView(final List<ScalableBody> bodies) {
		this.bodies = bodies;

		// Adds mouse listener.
		addMouseListener(this);

		// Adds mouse motion listener.
		addMouseMotionListener(this);

		// Imports background images from github.
		try {
			backgroundImage = ImageIO.read(
					new URL("https://raw.githubusercontent.com/Usefulmaths/module9images/master/starbackground.png"));
		} catch (IOException e) {
			System.out.println("Unable to import background");
		}
	}

	// Everything that should be painted/ repainted to the screen
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Sets default background to black.
		setBackground(Color.BLACK);

		// Draws imported backgroundImage to screen.
		g.drawImage(backgroundImage, 0, 0, null);
		
		// Draws all bodies to screen.
		drawBodies(g);
	}

	// Takes into account scaling and relative offsets to draw bodies
	private void drawBodies(final Graphics g) {
		// Centre (x, y) of the SolarSystemView
		final int xCentre = getWidth() / 2;
		final int yCentre = getHeight() / 2;

		// For each ScalableBody in our solar system, calculates the scaled
		// positions and offsets.
		for (final ScalableBody scalableBody : bodies) {
			final Body body = scalableBody.getBody();

			// New x and new y for body position.
			final int x;
			final int y;

			// If the body is the moon, give it a special offset so its orbit is
			// viewable on the scales we're working with.
			if (body.name.equals("moon")) {

				//Find the earth body to offset the moon by filtering on the name "earth".
				final Body earth = bodies.stream().map(ScalableBody::getBody).filter(test -> test.name.equals("earth"))
						.findAny().orElseThrow(() -> new RuntimeException("Couldn't find earth body in bodies."));

				// Scales up the distance between earth and moon by 500 /
				// zoomValue
				final Vector offsetFromEarth = body.separationVector(earth).unitVector().multiply(500)
						.divide(zoomValue);

				// The initial position of the moon (scaled by shorten())
				final Vector drawPosition = new Vector(xCentre + shorten(body.position.getX()),
						yCentre + shorten(body.position.getY()));

				// Using offsetFromEarth to find the offSet position of the
				// moon.
				final Vector offsetPosition = drawPosition.add(offsetFromEarth);

				// Sets x and y of offetPosition for moon.
				x = (int) (offsetPosition.getX());
				y = (int) (offsetPosition.getY());
			} else {
				// Else just find the scaled position of the planet.
				x = xCentre + shorten(body.position.getX());
				y = yCentre + shorten(body.position.getY());
			}

			// If the body is an ImagedBody, call drawWithOffset (draw imported
			// image).
			if (body instanceof ImagedBody) {
				drawWithOffset(g, ((ImagedBody) body).getImage(), x, y,
						scalableBody.getScaleFactor().divide(zoomValue));
			}
			// Else, call drawOvalWithOffet (draw a default circle)
			else {
				drawOvalWithOffset(g, x, y, scalableBody.getScaleFactor().divide(zoomValue));
			}

			// Sets color of text to white and displays planets name at the
			// position of the planet.
			g.setColor(Color.WHITE);
			if (toggleNames && body.name != "asteroid") {
				drawName(g, body.name, x, y);
			}
		}
	}

	// Using offsets, draw name where the position of planet is.
	private void drawName(final Graphics g, final String name, final int x, final int y) {
		g.drawString(Character.toUpperCase(name.charAt(0)) + name.substring(1), x + (int) viewportOffset.getX(),
				y + (int) viewportOffset.getY());
	}

	// Using offsets and scalings, draw imported image.
	private void drawWithOffset(final Graphics g, final BufferedImage image, final int x, final int y,
			final Dimensions dimensions) {
		g.drawImage(image,
					x - dimensions.getWidth() / 2 + (int) viewportOffset.getX(),
					y - dimensions.getHeight() / 2 + (int) viewportOffset.getY(), 
					dimensions.getWidth(),
					dimensions.getHeight(), null
		);
	}

	// Using offsets and scalings, draw default filled circle.
	private void drawOvalWithOffset(final Graphics g, final int x, final int y, final Dimensions dimensions) {
		g.setColor(Color.YELLOW);
		g.fillOval(
				x - dimensions.getWidth() / 2 + (int) viewportOffset.getX(),
				y - dimensions.getHeight() / 2 + (int) viewportOffset.getY(),
				dimensions.getWidth(),
				dimensions.getHeight()
		);
	}

	// Normalises distances between planets down to an appropriate size for
	// display.
	private int shorten(double value) {
		return (int) (2500 / zoomValue * value / Constants.AU);
	}

	// Repaints the view.
	public void draw() {
		repaint();
	}

	// Sets zoomValue
	public void zoom(final int auPixels) {
		zoomValue = auPixels;
	}

	// Retrieves the position of the mouse on view when mouse is clicked.
	@Override
	public void mousePressed(MouseEvent e) {
		previousMouseDragPosition = new Vector(e.getX(), e.getY());
	}

	// Retrieves the position of mouse on view when mouse is dragged and
	// calculates the difference between them and where the mouse was initially
	// pressed.
	@Override
	public void mouseDragged(MouseEvent e) {
		final int x = e.getX();
		final int y = e.getY();

		final Vector delta = new Vector(x - previousMouseDragPosition.getX(), y - previousMouseDragPosition.getY());
		viewportOffset = viewportOffset.add(delta);
		previousMouseDragPosition = new Vector(x, y);
	}

	// When ItemListener radiobutton is pressed, toggleNames between true and
	// false to display names.
	public ItemListener toggleNames() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					toggleNames = true;
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					toggleNames = false;
				}
			}
		};
	}
}
