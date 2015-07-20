package ch.jass.model;


public enum Trumpf {

	SPADES(Color.SPADES), HEARTS(Color.HEARTS), DIAMONDS(Color.DIAMONDS), CLUBS(Color.CLUBS), TOPDOWN, BUTTOMUP;

	private Color color;

	private Trumpf() {
		color = null;
	}

	private Trumpf(final Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public static Trumpf getTrumpf(final Color color) {
		if (color == null) {
			throw new IllegalArgumentException("null as Color parameter not possible!");
		}

		for (Trumpf trumpf : values()) {
			if (color.equals(trumpf.color)) {
				return trumpf;
			}
		}
		throw new IllegalStateException("Color was not found: " + color);
	}

}
