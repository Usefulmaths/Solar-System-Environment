package module9;

import java.util.function.IntConsumer;

import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * ZoomPanel class extends JPanel and allows us to set up a zooming slider
 * feature to the application.
 */
public class ZoomPanel extends JPanel {

	// Constructor accepts an IntConsumer which accepts the value of the
	// slider whenever it has a new value. The consumers updates the zoom in the
	// SolarSystemView zoom.
	public ZoomPanel(final IntConsumer consumer) {
		final JSlider slider = new JSlider(100, 10000, 1000);
		slider.addChangeListener(e -> consumer.accept(slider.getValue() / 100));
		// Adds slider to the panel.
		add(slider);
	}
}
