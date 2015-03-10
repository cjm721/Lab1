import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/**
 * A panel which looks like an eye.
 * The eye contains an eyeball that follows the mouse.
 * 
 * @author David Mutchler and many others before him.
 *         Created November 2004, updated August 2005 and September 2008.
 */
public class Eye extends JPanel implements MouseMotionListener {
	
	// Default radius for any Eye, in pixels.
	private static final int DEFAULT_RADIUS = 25;

	/**
	 * The eyeball inside this eye.
	 */
	protected EyeBall eyeBall;
	
	/**
	 * The color of this eye.
	 */
	protected Color eyeColor;
	
	/**
	 * Pixel radius of the eye.
	 */
	protected int eyeRadius;

	/**
	 * Construct an eye with the default color and size
	 * and construct the eye's eyeball with its default color.
	 */
	public Eye() {
		this(JavaEyes.DEFAULT_EYE_COLOR, JavaEyes.DEFAULT_EYEBALL_COLOR);
	}

	/**
	 * Construct an eye with the given color and default size
	 * and construct the eye's eyeball with its given color.
	 * 
	 * @param eyeColor Color of the eye.
	 * @param eyeBallColor Color of the eyeball.
	 */
	public Eye(Color eyeColor, Color eyeBallColor) {
		this.eyeColor = eyeColor;
		this.eyeBall = new EyeBall(eyeBallColor);

		this.eyeRadius = Eye.DEFAULT_RADIUS;
		this.setPreferredSize(new Dimension(
				2 * this.eyeRadius,
				2 * this.eyeRadius));
	}

	/**
	 * Ask this eye to look wherever the mouse is.
	 * 
	 * @param event The MouseEvent that occurred when the mouse was moved.
	 */
	@Override
	public void mouseMoved(MouseEvent event) {
		Point mousePoint = new Point(event.getX(), event.getY());
		this.look(mousePoint);
	}

	/**
	 * Do nothing when the user drags the mouse.
	 * 
	 * @param event The MouseEvent that occurred when the mouse was dragged.
	 */
	@Override
	public void mouseDragged(MouseEvent event) {
		// Do nothing
	}
	
	/**
	 * Ask this eye to look wherever the mouse is.
	 * To do so, tell the eyeball where to move, to follow the mouse.
	 * 
	 * @param mousePoint Position of the mouse, relative to the frame.
	 */
	protected void look(Point mousePoint) {
		int eyeX = this.getX() + this.eyeRadius; // Center of eye,
		int eyeY = this.getY() + this.eyeRadius; // relative to the frame

		int mouseX = mousePoint.x; // Mouse, relative to the frame
		int mouseY = mousePoint.y;

		double distance = Math.sqrt(((mouseX - eyeX) * (mouseX - eyeX))
								  + ((mouseY - eyeY) * (mouseY - eyeY)));
		double ratio = (this.eyeRadius - this.eyeBall.getRadius()) / distance;

		// Look toward the mouse.
		int eyeBallX = (int) (this.eyeRadius + ratio * (mouseX - eyeX)); 
		int eyeBallY = (int) (this.eyeRadius + ratio * (mouseY - eyeY));

		this.eyeBall.look(eyeBallX, eyeBallY); // Relative to eye
		this.repaint();
	}

	/**
	 * Draw this eye and its eyeball.
	 * 
	 * @param graphics Graphics object onto which to draw.
	 */
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		// Draw outline of eye.
		graphics.setColor(this.eyeColor);
		graphics.fillOval(0, 0, 2 * this.eyeRadius, 2 * this.eyeRadius);
		
		// Draw eyeball.
		this.eyeBall.draw(graphics);
	}
}