package ch.jass.model.schieber.table;

public enum PlayerNumber {
	PLAYER_1, PLAYER_2, PLAYER_3, PLAYER_4;

	public static PlayerNumber next(final PlayerNumber actualPlayerNumber) {

		if (actualPlayerNumber == PLAYER_1) {
			return PLAYER_2;
		} else if (actualPlayerNumber == PLAYER_2) {
			return PLAYER_3;
		} else if (actualPlayerNumber == PLAYER_3) {
			return PLAYER_4;
		} else if (actualPlayerNumber == PLAYER_4) {
			return PLAYER_1;
		}
		// TODO Auto-generated method stub
		throw new IllegalArgumentException("Wrong Argument!");
	}
}
