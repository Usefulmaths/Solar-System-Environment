package module9;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Container class deals with all the views found in the project. The drawing of
 * the spatial entities, the timer, zoom slider, and toggle name radio button.
 * 
 * Extends JFrame as it is the main frame in the application.
 */
public class Container extends JFrame {

	// All the bodies that exist in this solar system.
	private final List<ScalableBody> bodies;

	// SolarSystemView that deals with drawing of bodies.
	private SolarSystemView solarSystemView;

	// TimePanel that deals with drawing the time.
	private TimerPanel timerPanel;

	public Container(final String title, final SolarSystem solarSystem, final List<ScalableBody> bodies) {
		super(title);

		this.bodies = bodies;
		setupViews(solarSystem);
	}

	// A method to draw the SolarSystemView. The panels repaint themselves on
	// updated values.
	public void draw() {
		solarSystemView.draw();
	}

	// A method to set the text for time in TimerPanel.
	public void setTimeIndicator(final String time) {
		timerPanel.setTimeIndicator(time);
	}

	// Creates the views for SolarSystemView, TimerPanel.
	private void setupViews(final SolarSystem solarSystem) {
		// Adds solarSystemView to the container.
		this.solarSystemView = new SolarSystemView(bodies);
		add(solarSystemView);

		// Adds widget JPanel to the container.
		final JPanel widgets = new JPanel(new FlowLayout(FlowLayout.CENTER));
		add(widgets, BorderLayout.SOUTH);

		// Adds timerPanel to the widgets JPanel.
		timerPanel = new TimerPanel(Double.toString(solarSystem.getElapsedTicks() * solarSystem.getSecondsPerTick()));
		widgets.add(timerPanel);

		// Adds zoomPanel slider to the widgets JPanel.
		final ZoomPanel zoomPanel = new ZoomPanel(solarSystemView::zoom);
		final JLabel zoomLabel = new JLabel("Zoom: ");
		widgets.add(zoomLabel);
		widgets.add(zoomPanel);

		// Adds radioButton to toggles name to the widget JPanel.
		final JRadioButton toggleRadioButton = new JRadioButton();
		final JLabel nameLabel = new JLabel("Toggle Names: ");
		final ItemListener toggleNames = solarSystemView.toggleNames();
		toggleRadioButton.addItemListener(toggleNames);
		widgets.add(nameLabel);
		widgets.add(toggleRadioButton);
	}
}