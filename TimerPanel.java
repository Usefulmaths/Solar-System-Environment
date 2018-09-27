package module9;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * TimerPanel class extends JPanel and allows us to set up a display for the
 * time elapsed since the start of opening the application.
 */
public class TimerPanel extends JPanel {
	// timerLabel used to display time elapsed.
	private final JLabel timerLabel;

	public TimerPanel(final String initialTime) {
		super(new GridLayout());

		// Add JLabel to TimerPanel text.
		add(new JLabel("Time elapsed: "));

		// Add JLabel to TimerPanel time text.
		timerLabel = new JLabel(initialTime);
		add(timerLabel);
	}

	// Method sets the timerLabel text to the time passed through as a
	// parameter.
	public void setTimeIndicator(final String time) {
		timerLabel.setText(time);
	}
}
