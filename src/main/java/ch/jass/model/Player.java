package ch.jass.model;

public class Player {

	private final String name;

	/**
	 * Represents a Player which is not yet ready to play on a table.
	 * 
	 * @param name
	 *            the name of the Player
	 */
	public Player(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
