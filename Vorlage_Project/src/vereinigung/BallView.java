package vereinigung;

import javafx.scene.shape.Circle;

/**
 * Die Klasse BallView ist dafuer da, um den Ball als einen Kreis auf dem
 * Bildschirm anzuzeigen.
 * 
 * @author GestreifteRemulaner
 *
 */
public class BallView extends Circle {
	public Ball ballModel = new Ball(); // Die Daten des Balls

	/**
	 * Constructor fuer einen Ball.
	 * 
	 * @param x
	 *            Die X-Koordinate des Mittelpunktes.
	 * @param y
	 *            Die Y-Koordinate des Mittelpunktes.
	 * @param radius
	 *            Der Radius des Kreises.
	 */
	public BallView(double x, double y, double radius) {
		super(x, y, radius);
	}
}
