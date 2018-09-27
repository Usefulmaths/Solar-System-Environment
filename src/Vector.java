package module9;

/**
 * A vector class to enable mathematical manipulation of vectors.
 */
public class Vector {

	// A vector with all components equal to zero.
	public final static Vector VECTOR_ZERO = new Vector(0, 0);

	// x component of vector
	private final double x;

	// y component of vector
	private final double y;

	public Vector(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	// Creates new Vector that is the addition of this vector & another vector.
	public Vector add(final Vector other) {
		return new Vector(this.x + other.x, this.y + other.y);
	}

	// Creates new Vector that is the subtraction of this vector & another
	// vector.
	public Vector subtract(final Vector other) {
		return new Vector(this.x - other.x, this.y - other.y);
	}

	// Creates new vector that is this vector multiplied by a scalar.
	public Vector multiply(final double number) {
		return new Vector(this.x * number, this.y * number);
	}

	// Creates new vector that is this vector divided by a scalar.
	public Vector divide(final double number) {
		return new Vector(this.x / number, this.y / number);
	}

	// Magnitude of a vector.
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	// Polar coordinate angle of vector.
	public double angle() {
		return Math.atan2(y, x);
	}

	// Creates a new vector that is the unitVector of this vector.
	public Vector unitVector() {
		return this.divide(this.magnitude());
	}

	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + "]";
	}

	// Retrieves the x component of the vector.
	public double getX() {
		return x;
	}

	// Retrieves the y component of the vector.
	public double getY() {
		return y;
	}
}
