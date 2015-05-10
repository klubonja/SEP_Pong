package vereinigung;

/**
 * Die Klasse Paddle repraesentiert das Model von dem Spieler und enthaelt die
 * Koordinaten und die Geschwindigkeit des Schlaegers.
 * 
 * @author GestreifteRemulaner
 */
public class Paddle {
	private int x, y;
	public final int PADDLE_SPEED = 10;

	/**
	 * Getter fuer die X-Koordinate.
	 * 
	 * @return Die X-Koordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter fuer die X-Koordinate.
	 * 
	 * @param x
	 *            Ein Integer.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Getter fuer die Y-Koordinate.
	 * 
	 * @return Die Y-Koordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter fuer die Y-Koordinate.
	 * 
	 * @param y
	 *            Ein Integer.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Getter fuer die Geschwindigkeit des Schlaegers.
	 * 
	 * @return Die Geschwindigkeit des Schlaegers.
	 */
	public int getSpeed() {
		return PADDLE_SPEED;
	}
}
