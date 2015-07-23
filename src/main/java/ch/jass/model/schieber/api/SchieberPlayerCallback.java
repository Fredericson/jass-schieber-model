package ch.jass.model.schieber.api;

import ch.jass.model.api.PlayerCallback;
import ch.jass.model.schieber.table.SchieberScore;
import ch.jass.model.schieber.table.TeamScore;
import ch.jass.model.schieber.table.SchieberStich;
import ch.jass.model.schieber.table.SchieberTableInfo;

public interface SchieberPlayerCallback extends PlayerCallback {

	void broadcastTeams(SchieberTableInfo schieberTableInfo);

	void broadcastStich(SchieberStich stich);

	void broadcastGameFinished(SchieberScore pointsWonAllTeams);

	void broadcastWinnerTeam(TeamScore pointsWonByWinnerTeam);
}
