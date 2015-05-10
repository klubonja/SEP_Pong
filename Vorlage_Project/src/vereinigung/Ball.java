package vereinigung;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Die Klasse Ball repraesentiert das Model von dem Ball. Sie enthaelt die
 * Koordinaten
 * 
 * @author GestreifteRemulaner
 *
 */
public class Ball {
	private DoubleProperty xSpeed = new SimpleDoubleProperty(5);
	private DoubleProperty ySpeed = new SimpleDoubleProperty(1);
	private SimpleDoubleProperty centerX = new SimpleDoubleProperty(480);
	private SimpleDoubleProperty centerY = new SimpleDoubleProperty(365);

	/**
	 * Setter fuer die Geschwindigkeit des Balls auf der X-Koordinate.
	 * 
	 * @param xSpeed
	 *            Ein Double.
	 */
	public void setXSpeed(double xSpeed) {
		this.xSpeed.set(xSpeed);
	}

	/**
	 * Getter fuer die Geschwindigkeit des Balls auf der X-Koordinate.
	 * 
	 * @return Die Geschwindigkeit des Balls auf der X-Koordinate.
	 */
	public double getXSpeed() {
		return this.xSpeed.get();
	}

	/**
	 * Setter fuer die Geschwindigkeit des Balls auf der Y-Koordinate.
	 * 
	 * @param Ein
	 *            Double.
	 */
	public void setYSpeed(double ySpeed) {
		this.ySpeed.set(ySpeed);
	}

	/**
	 * Getter fuer die Geschwindigkeit des Balls auf der Y-Koordinate.
	 * 
	 * @return Die Geschwindigkeit des Balls auf der Y-Koordinate.
	 */
	public double getYSpeed() {
		return this.ySpeed.get();
	}

	/**
	 * Getter fuer die X-Koordinate des Mittelpunktes des Balls.
	 * 
	 * @return Die X-Koordinate des Mittelpunktes des Balls.
	 */
	public SimpleDoubleProperty getCenterXProperty() {
		return centerX;
	}

	/**
	 * Getter fuer die Y-Koordinate des Mittelpunktes des Balls.
	 * 
	 * @return Die Y-Koordinate des Mittelpunktes des Balls.
	 */
	public SimpleDoubleProperty getCenterYProperty() {
		return centerY;
	}
}
