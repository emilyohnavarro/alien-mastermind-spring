package alienmastermind;

public class Rocket {
	private int fill;

	public static final int BLUE = 1;
	public static final int WHITE = 2;
	public static final int EMPTY = 3;

	public Rocket(int fill) {
		this.fill = fill;
	}

	public int getFill() {
		return fill;
	}

	@Override
	public String toString() {
		return "fill: " + fill;
	}
}