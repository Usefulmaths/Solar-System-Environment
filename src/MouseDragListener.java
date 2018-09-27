package module9;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Combines MouseListener and MouseMotionListener and provides default (empty)
 * implementations of the methods that are not required to observe Drag events
 * (i.e., {@code mousePressed} and {@code mouseDragged}).
 */
public interface MouseDragListener extends MouseListener, MouseMotionListener {
	@Override
	default void mouseMoved(MouseEvent e) {
	}

	@Override
	default void mouseClicked(MouseEvent e) {
	}

	@Override
	default void mouseReleased(MouseEvent e) {
	}

	@Override
	default void mouseEntered(MouseEvent e) {
	}

	@Override
	default void mouseExited(MouseEvent e) {
	}
}
