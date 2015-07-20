package ch.jass.model.schieber.table;

import ch.jass.model.Card;
import ch.jass.model.Player;

public class PlayerOnTable extends Player {

	private int playerNumber;
	private Team team;
	private Card playedCard;

	public PlayerOnTable(final String playerName, final Team team, final int playerNumber) {
		super(playerName);
		this.team = team;
		this.playerNumber = playerNumber;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(final Team team) {
		this.team = team;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(final int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public void setPlayedCard(final Card card) {
		this.playedCard = card;
	}

	public Card getPlayedCard() {
		return playedCard;
	}

	@Override
	public String toString() {
		return getName() + " " + team;
	}
}
