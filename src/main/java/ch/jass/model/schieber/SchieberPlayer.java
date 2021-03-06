package ch.jass.model.schieber;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.jass.model.Card;
import ch.jass.model.Color;
import ch.jass.model.Player;
import ch.jass.model.Trumpf;
import ch.jass.model.TrumpfRank;
import ch.jass.model.schieber.api.SchieberPlayerCallback;
import ch.jass.model.schieber.api.SchieberServerService;
import ch.jass.model.schieber.table.PlayerNumber;
import ch.jass.model.schieber.table.PlayerOnTable;
import ch.jass.model.schieber.table.SchieberScore;
import ch.jass.model.schieber.table.SchieberStich;
import ch.jass.model.schieber.table.SchieberTableInfo;
import ch.jass.model.schieber.table.TeamScore;

public abstract class SchieberPlayer extends Player implements SchieberPlayerCallback {

	// Connection to communicate with Server
	protected final SchieberServerService schieberService;

	// Each Player has information about what is visible on the Table
	protected SchieberTableInfo schieberTableInfo = new SchieberTableInfo();
	private final Set<TrumpfRank> trumpfCards = new HashSet<TrumpfRank>();
	private final Set<Card> otherCards = new HashSet<Card>();

	public SchieberPlayer(final SchieberServerService schieberService, final String name) {
		super(name);
		this.schieberService = schieberService;
	}

	public void connectToServer() {
		this.schieberService.connectToServer(this);
	}

	public SchieberTableInfo getSchieberTableInfo() {
		return schieberTableInfo;
	}

	@Override
	public void broadcastJoinedPlayer(final PlayerOnTable playerOnTable) {
		schieberTableInfo.addPlayer(playerOnTable);
	}

	@Override
	public void broadcastTeams(final SchieberTableInfo schieberTableInfo) {
		this.schieberTableInfo = schieberTableInfo;
	}

	@Override
	public void dealCards(final Set<Card> cards) {
		this.otherCards.addAll(cards);
	}

	@Override
	public void broadcastTrumpfForGame(final Trumpf trumpf) {
		schieberTableInfo.setActualTrumpf(trumpf);
		Color trumpfColor = trumpf.getColor();
		if (trumpfColor == null) {
			return;
		}
		Set<Card> trumpfCards = getOtherCards(trumpfColor);
		this.trumpfCards.addAll(getTrumpfRanks(trumpfCards));
		otherCards.removeAll(trumpfCards);
	}

	public boolean canPlayTrumpf() {
		if (trumpfCards.size() > 0 && isOverTrumpfPossible()) {
			return true;
		} else {
			return false;
		}
	}

	private Set<TrumpfRank> getTrumpfRanks(final Set<Card> cards) {
		Set<TrumpfRank> trumpfCards = new HashSet<TrumpfRank>();
		for (Card card : cards) {
			trumpfCards.add(card.getTrumpfRank());
		}
		return trumpfCards;
	}

	private boolean isOverTrumpfPossible() {
		TrumpfRank highestTrumpfRankOnTable = getSchieberTableInfo().getHighestTrumpfRankOnTable();
		if (highestTrumpfRankOnTable == null) {
			return true;
		}
		TrumpfRank myHighestTrumpfRank = getHighestTrumpfRank();
		if (myHighestTrumpfRank.ordinal() > highestTrumpfRankOnTable.ordinal()) {
			return true;
		} else {
			return false;
		}
	}

	private TrumpfRank getHighestTrumpfRank() {
		TrumpfRank highest = null;
		for (TrumpfRank card : trumpfCards) {
			if (highest == null || card.getRank().ordinal() > highest.getRank().ordinal()) {
				highest = card;
			}
		}
		return highest;
	}

	public Card getHighestTrumpfCard() {
		TrumpfRank highest = getHighestTrumpfRank();
		if (highest == null) {
			return null;
		} else {
			return Card.getCard(schieberTableInfo.getActualTrumpf().getColor(), highest);
		}
	}

	public Set<Card> getTrumpfCards() {
		Set<Card> trumpfCards = new HashSet<Card>();
		for (TrumpfRank trumpfRank : this.trumpfCards) {
			trumpfCards.add(Card.getCard(schieberTableInfo.getActualTrumpf().getColor(), trumpfRank));
		}
		return trumpfCards;
	}

	private Set<Card> getMappedTrumpfCards() {
		Set<Card> mappedTrumpfCards = new HashSet<Card>();
		Trumpf trumpf = schieberTableInfo.getActualTrumpf();
		if (trumpf.getColor() == null) {
			return mappedTrumpfCards;
		}
		for (TrumpfRank trumpfRank : trumpfCards) {
			mappedTrumpfCards.add(Card.getCard(trumpf.getColor(), trumpfRank));
		}
		return mappedTrumpfCards;
	}

	protected Set<Card> getOtherCards() {
		return Collections.unmodifiableSet(otherCards);
	}

	public Set<Card> getAllCards() {
		Set<Card> cards = new HashSet<Card>(otherCards);
		cards.addAll(getMappedTrumpfCards());
		return cards;
	}

	protected Set<Card> getOtherCards(final Color requestedColor) {
		Set<Card> cardsForRequestedColor = new HashSet<Card>();
		for (Card card : otherCards) {
			if (requestedColor == card.getColor()) {
				cardsForRequestedColor.add(card);
			}
		}
		return cardsForRequestedColor;
	}

	@Override
	public void broadcastPlayedCard(final Card card) {
		if (card.getColor().equals(schieberTableInfo.getActualTrumpfColor())) {
			trumpfCards.remove(card.getTrumpfRank());
		}
		otherCards.remove(card);
		rememberPlayedCard(card);
		schieberTableInfo.setPlayedCard(card);
	}

	protected abstract void rememberPlayedCard(Card card);

	@Override
	public void broadcastTrumpfablePlayer(final PlayerNumber actualTrumpfablePlayer) {
		this.schieberTableInfo.setActualTrumpfablePlayer(actualTrumpfablePlayer);
	}

	@Override
	public void broadcastStich(final SchieberStich stich) {
		this.schieberTableInfo.setStich(stich);
	}

	@Override
	public void broadcastGameFinished(final SchieberScore pointsWonAllTeams) {
		// Not yet parsed in Backend
	}

	@Override
	public void broadcastWinnerTeam(final TeamScore pointsWonByWinnerTeam) {
		// Not yet parsed in Backend
	}
}
