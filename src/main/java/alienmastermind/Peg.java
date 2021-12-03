package alienmastermind;

public class Peg {
	private int color;
	
	public static final int RED = 1;
	public static final int YELLOW = 2;
	public static final int GREEN = 3;
	public static final int PURPLE = 4;
	public static final int BLUE = 5;
	public static final int ORANGE = 6;
	public static final int PINK = 7;
	public static final int AQUA = 8;
	public static final int EMPTY = 9;

	public Peg(int color1) {
		color = color1;
	}

	public int getColor() {
		return color;
	}
}