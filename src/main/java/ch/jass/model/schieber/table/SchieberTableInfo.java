package ch.jass.model.schieber.table;

import java.util.ArrayList;
import java.util.List;

import ch.jass.model.Card;
import ch.jass.model.Color;
import ch.jass.model.Rank;
import ch.jass.model.Trumpf;
import ch.jass.model.TrumpfRank;

public class SchieberTableInfo {

	private final PlayerOnTable[] players = new PlayerOnTable[4];

	private Trumpf actualTrumpf;
	private PlayerNumber actualTrumpfablePlayer;

	private PlayerNumber cardRequestedPlayerNumber;

	private final SchieberScore allStichThisRound = new SchieberScore();
	private final SchieberScore allCompletedGames = new SchieberScore();

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

	public Color getActualTrumpfColor() {
		if (actualTrumpf.getColor() == null) {
			return null;
		}
		return actualTrumpf.getColor();
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
		this.cardRequestedPlayerNumber = actualTrumpfablePlayer;
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

	public void setCompletedGameScore(final SchieberScore score) {
		this.allCompletedGames.add(score);
		this.allStichThisRound.clear();
	}

	public void setStich(final SchieberStich stich) {
		this.cardRequestedPlayerNumber = stich.getWinner();
		this.allStichThisRound.add(stich);
		removeAllPlayedCards();
	}

	private void removeAllPlayedCards() {
		for (PlayerOnTable player : players) {
			player.setPlayedCard(null);
		}
	}

	public List<Card> getAllPlayedCards() {
		List<Card> allPlayedCards = new ArrayList<Card>();
		for (PlayerOnTable player : players) {
			if (player.getPlayedCard() != null) {
				allPlayedCards.add(player.getPlayedCard());
			}
		}
		return allPlayedCards;
	}

	public TrumpfRank getHighestTrumpfRankOnTable() {
		TrumpfRank actualHighestTrumpfRank = null;
		for (PlayerOnTable player : players) {
			if (didPlayTrumpf(player)) {
				Rank rankFromPlayedCard = player.getPlayedCard().getRank();
				TrumpfRank fromPlayer = TrumpfRank.getTrumpfRank(rankFromPlayedCard);
				actualHighestTrumpfRank = getHigherTrumpfRank(actualHighestTrumpfRank, fromPlayer);
			}
		}
		return actualHighestTrumpfRank;
	}

	private boolean didPlayTrumpf(final PlayerOnTable player) {
		Card playedCard = player.getPlayedCard();
		if (playedCard == null) {
			return false;
		}
		return playedCard.getColor() == actualTrumpf.getColor();
	}

	private TrumpfRank getHigherTrumpfRank(final TrumpfRank actualHighestTrumpfRank,
			final TrumpfRank trumpfRankFromPlayer) {
		if (trumpfRankFromPlayer == null) {
			throw new IllegalStateException("When the player die play trumpf is Rank should not be null!");
		}
		if (actualHighestTrumpfRank == null) {
			return trumpfRankFromPlayer;
		}
		if (actualHighestTrumpfRank.ordinal() > trumpfRankFromPlayer.ordinal()) {
			return actualHighestTrumpfRank;
		} else {
			return trumpfRankFromPlayer;
		}
	}
}
