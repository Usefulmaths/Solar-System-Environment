package module9;

import static module9.Constants.MASS_EARTH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

/**
 * SolarSystem class handles the main method which sets up what objects will be
 * present in the solar system and the container they will be displayed on. It
 * is also used to calculate the total force on all individual bodies due to all
 * other bodies within a solar system. It is also responsible for handling and
 * calculating the change in force each tick of the timer.
 */
public class SolarSystem {

	// A list to hold all bodies that exist within this solar system.
	private final List<Body> bodies;

	// Amount of seconds that pass with each tick of the timer.
	private final int secondsPerTick;

	// The amount of ticks that have elapsed since starting the application.
	private double elapsedTicks = 0;

	public static void main(String[] args) {
		
		// JOptionPane to give a description of the application. 
		JOptionPane.showMessageDialog(null, "Hello!\nWelcome to this solar system simulator.\nClick and drag to move the viewport around, and drag the slider to zoom.\nHave fun!");

		// This object deals with the setting up of the spatial objects, their
		// initial conditions etc. Also sets up how the container is going to be
		// displayed.
		final SolarSystemCreator solarSystemCreator = new SolarSystemCreator();

		// Holds all bodies within solar system and their scale factor (image
		// display purposes)
		final List<ScalableBody> scalableBodies = new ArrayList<>();

		// Central body of the solar system. Sole purpose to create stable
		// orbits for asteroids.
		final Body centralBody = new Body("sun", 333054 * MASS_EARTH, new Vector(0, 0), new Vector(0, 0));

		// Uses methods within the SolarSystemCreator class in order to set up a
		// simple solar system (planets, comets)
		final List<ScalableBody> simpleSolarSystem = solarSystemCreator.createSimpleSolarSystem(centralBody);

		// Uses methods within the SolarSystemCreator class in order to set up
		// the asteroid belt.
		final List<ScalableBody> asteroids = solarSystemCreator.addAsteroidBelt(150, centralBody);

		// Adds both the simpleSolarSystem & asteroid belt bodies to the total
		// list of solar system.
		scalableBodies.addAll(simpleSolarSystem);
		scalableBodies.addAll(asteroids);

		// Amount of seconds in a day.
		final int secondsInDay = 60 * 60 * 24;

		// Retrieves just the bodies of the scalableBodies list (without the
		// dimensions)
		final List<Body> justTheBodies = scalableBodies.stream()
				.map(ScalableBody::getBody)
				.collect(Collectors.toList());

		// Instantiates SolarSystem object to set bodies list and
		// secondsPerTick.
		final SolarSystem solarSystem = new SolarSystem(justTheBodies, secondsInDay);

		// Uses methods in SolarSystemCreator to set up container with our
		// solarSystem.
		final Container container = solarSystemCreator.setupContainer(solarSystem, scalableBodies);

		// Uses methods in SolarSystemCreator to start the timer for this
		// solarSystem.
		solarSystemCreator.startTimers(solarSystem, container);
	}

	// Constructor to set list of bodies and secondsPerTick.
	public SolarSystem(final List<Body> bodies, final int secondsPerTick) {
		this.bodies = bodies;
		this.secondsPerTick = secondsPerTick;
	}

	// Calculates the total force felt on a body by all the other bodies in the
	// solarSystem.
	public Vector calculateTotalForce(final Body body) {
		return bodies.stream()
				.filter(test -> test != body)
				.map(body::calculateForce)
				.reduce(
						Vector.VECTOR_ZERO,
						Vector::add
				);
	}

	// Each tick calculates the force on each individual body by every other
	// body in the solar system.
	public void tick() {
		elapsedTicks++;
		final Map<Body, Vector> forcesToApply = new HashMap<>();

		bodies.forEach(body -> {
			final Vector totalForce = calculateTotalForce(body);
			forcesToApply.put(body, totalForce);
		});

		forcesToApply.entrySet()
		.forEach(entrySet -> {
			entrySet.getKey().incrementMovement(entrySet.getValue(), secondsPerTick);
		});
	}

	// Retrieves the list of bodies in solar system.
	public List<Body> getBodies() {
		return bodies;
	}

	// Retrieves secondsPerTick.
	public int getSecondsPerTick() {
		return secondsPerTick;
	}

	// Retrieves the amount of ticks elapsed since start.
	public double getElapsedTicks() {
		return elapsedTicks;
	}
}
