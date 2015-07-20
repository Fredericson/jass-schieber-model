package ch.jass.model.schieber.table;


public class SchieberStich {

	private final PlayerNumber winner;

	public SchieberStich(final PlayerNumber winner) {
		this.winner = winner;
	}

	public PlayerNumber getWinner() {
		return winner;
	}

	private int pointsTeam1;

	public int getPointsTeam1() {
		return pointsTeam1;
	}

	public void setPoints(final int pointsTeam, final Team team) {
		if (team == Team.TEAM_1) {
			this.pointsTeam1 = pointsTeam;
		} else if (team == Team.TEAM_2) {
			this.pointsTeam2 = pointsTeam;
		}
	}

	private int pointsTeam2;

	public int getPointsTeam2() {
		return pointsTeam2;
	}
}
