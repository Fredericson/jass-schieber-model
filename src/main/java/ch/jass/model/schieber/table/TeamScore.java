package ch.jass.model.schieber.table;

public class TeamScore {

	private final int points;
	private final Team team;

	public TeamScore(final int points, final Team team) {
		this.points = points;
		this.team = team;
	}

	public int getPoints() {
		return points;
	}

	public Team getTeam() {
		return team;
	}
}
