package ch.jass.model;

public class Player {

	private final String name;

	/**
	 * Called from PlayerWithConnection
	 * 
	 * @param playerName
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
