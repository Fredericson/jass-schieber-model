package ch.jass.model.schieber.table;

import ch.jass.model.Card;
import ch.jass.model.Rank;
import ch.jass.model.Trumpf;

public class SchieberTableInfo {

	private final PlayerOnTable[] players = new PlayerOnTable[4];

	private Trumpf actualTrumpf;
	private PlayerNumber actualTrumpfablePlayer;

	private PlayerNumber cardRequestedPlayerNumber;

	private int pointsTeam1;
	private int pointsTeam2;

	public PlayerOnTable getPlayer1() {
		return players[0];
	}

	public PlayerOnTable getPlayer2() {
		return players[1];
	}

	public PlayerOnTable getPlayer3() {
		return players[2];
	}

	public PlayerOnTable getPlayer4() {
		return players[3];
	}

	public void addTeam(final PlayerOnTable[] players) {
		addPlayer(players[0]);
		addPlayer(players[1]);
	}

	public void addPlayer(final PlayerOnTable player) {
		switch (player.getPlayerNumber()) {
		case 1:
			players[0] = player;
			break;
		case 2:
			players[1] = player;
			break;
		case 3:
			players[2] = player;
			break;
		case 4:
			players[3] = player;
			break;
		default:
			throw new IllegalStateException("Player " + player.getName() + " has invalid PlayerNumber: "
					+ player.getPlayerNumber());
		}
	}

	public int getPointsTeam1() {
		return pointsTeam1;
	}

	public void addPointsTeam1(final int points) {
		pointsTeam1 += points;
	}

	public int getPointsTeam2() {
		return pointsTeam2;
	}

	public void setPointsTeam2(final int points) {
		pointsTeam2 += points;
	}

	public Trumpf getActualTrumpf() {
		return actualTrumpf;
	}

	public void setActualTrumpf(final Trumpf actualTrumpf) {
		this.actualTrumpf = actualTrumpf;
	}

	public PlayerNumber getActualTrumpfablePlayer() {
		return actualTrumpfablePlayer;
	}

	public void setActualTrumpfablePlayer(final PlayerNumber actualTrumpfablePlayer) {
		this.actualTrumpfablePlayer = actualTrumpfablePlayer;
		this.cardRequestedPlayerNumber = PlayerNumber.PLAYER_1;
	}

	public PlayerNumber getCardRequestedPlayerNumber() {
		return cardRequestedPlayerNumber;
	}

	public void setPlayedCard(final Card card) {
		if (cardRequestedPlayerNumber == PlayerNumber.PLAYER_1) {
			players[0].setPlayedCard(card);
		} else if (cardRequestedPlayerNumber == PlayerNumber.PLAYER_2) {
			players[1].setPlayedCard(card);
		} else if (cardRequestedPlayerNumber == PlayerNumber.PLAYER_3) {
			players[2].setPlayedCard(card);
		} else if (cardRequestedPlayerNumber == PlayerNumber.PLAYER_4) {
			players[3].setPlayedCard(card);
		}
		this.cardRequestedPlayerNumber = PlayerNumber.next(cardRequestedPlayerNumber);
	}

	public void setStich(final SchieberStich stich) {
		this.cardRequestedPlayerNumber = stich.getWinner();
		this.pointsTeam1 += stich.getPointsTeam1();
		this.pointsTeam2 += stich.getPointsTeam2();
		removeAllPlayedCards();
	}

	private void removeAllPlayedCards() {
		for (PlayerOnTable player : players) {
			player.setPlayedCard(null);
		}
	}

	public Rank getHighestTrumpfRankOnTable() {
		Rank actualRank = null;
		for (PlayerOnTable player : players) {
			if (didPlayTrumpf(player)) {
				actualRank = getHigherRank(actualRank, player);
			}
		}
		return actualRank;
	}

	private boolean didPlayTrumpf(final PlayerOnTable player) {
		Card playedCard = player.getPlayedCard();
		if (playedCard == null) {
			return false;
		}
		return playedCard.getColor() == actualTrumpf.getColor();
	}

	private Rank getHigherRank(final Rank actualRank, final PlayerOnTable player) {
		if (player.getPlayedCard() == null) {
			return actualRank;
		}
		Rank playedRank = player.getPlayedCard().getRank();
		if (actualRank == null) {
			return playedRank;
		}
		if (playedRank.ordinal() > actualRank.ordinal()) {
			return playedRank;
		} else {
			return actualRank;
		}
	}
}
