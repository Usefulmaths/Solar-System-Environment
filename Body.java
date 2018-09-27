package module9;

/**
 * Body class handles all spatial entities. Holds an entity's name, mass,
 * position, and velocity. Used to find separation and force between two bodies
 * and reacts to a force (change in position and velocity).
 */
public class Body {

	// Name of body.
	protected final String name;

	// Mass of body.
	protected final double mass;

	// Position vector of body relative to (0, 0).
	protected Vector position;

	// Velocity vector of body.
	protected Vector velocity;

	// Constructor to set name, mass, initial position, and initial velocity of
	// body.
	public Body(final String name, final double mass, final Vector position, final Vector velocity) {
		this.name = name;
		this.mass = mass;
		this.position = position;
		this.velocity = velocity;
	}

	// Calculates the position vector between two bodies.
	public Vector separationVector(final Body other) {
		return new Vector(other.position.getX() - this.position.getX(), other.position.getY() - this.position.getY());
	}

	// Calculates the force vector between two bodies using Newton's law of
	// universal gravitation.
	public final Vector calculateForce(Body other) {
		final double force = Constants.G * other.mass * this.mass / Math.pow(separationVector(other).magnitude(), 2);

		final double forceX = force * Math.cos(separationVector(other).angle());
		final double forceY = force * Math.sin(separationVector(other).angle());

		return new Vector(forceX, forceY);
	}

	// Uses the total amount of force acting on this body by all the other
	// bodies to find an change in velocity and position using Newton's second
	// law.
	public void incrementMovement(final Vector overallForce, final double timeStep) {
		final Vector acceleration = overallForce.divide(this.mass);

		final Vector changeVelocity = acceleration.multiply(timeStep);
		this.velocity = this.velocity.add(changeVelocity);

		final Vector changePosition = this.velocity.multiply(timeStep);
		this.position = this.position.add(changePosition);
	}

	// Retrieves name of body.
	public String getName() {
		return name;
	}

	// Retrieves mass of body.
	public double getMass() {
		return mass;
	}

	// Retrieves position vector of body.
	public Vector getPosition() {
		return position;
	}

	// Retrieves velocity vector of body.
	public Vector getVelocity() {
		return velocity;
	}
}