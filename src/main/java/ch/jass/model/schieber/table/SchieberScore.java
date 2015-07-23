package ch.jass.model.schieber.table;

public class SchieberScore {

	private int pointsWonTeam1 = 0;
	private int pointsWonTeam2 = 0;

	public void setTeamScore(final TeamScore teamScore) {
		if (teamScore.getTeam() == Team.TEAM_1) {
			this.pointsWonTeam1 = teamScore.getPoints();
		} else if (teamScore.getTeam() == Team.TEAM_2) {
			this.pointsWonTeam2 = teamScore.getPoints();
		}
	}

	public int getPointsWonTeam1() {
		return pointsWonTeam1;
	}

	public int getPointsWonTeam2() {
		return pointsWonTeam2;
	}

	public void add(final SchieberScore score) {
		this.pointsWonTeam1 += score.pointsWonTeam1;
		this.pointsWonTeam2 += score.pointsWonTeam2;
	}

	public void clear() {
		pointsWonTeam1 = 0;
		pointsWonTeam2 = 0;
	}
}
