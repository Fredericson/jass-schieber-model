package ch.jass.model.schieber.table;

public class SchieberStich extends SchieberScore {

	private final PlayerNumber winner;

	public SchieberStich(final PlayerNumber winner) {
		this.winner = winner;
	}

	public PlayerNumber getWinner() {
		return winner;
	}
}
