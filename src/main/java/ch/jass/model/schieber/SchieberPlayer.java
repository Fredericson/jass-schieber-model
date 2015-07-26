package ch.jass.model.schieber;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.jass.model.Card;
import ch.jass.model.Color;
import ch.jass.model.Player;
import ch.jass.model.Trumpf;
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
	private final Set<Card> trumpfCards = new HashSet<Card>();
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
		trumpfCards.addAll(getOtherCards(trumpfColor));
		otherCards.removeAll(trumpfCards);
	}

	public Card getHighestTrumpfCard() {
		Card highest = null;
		for (Card card : trumpfCards) {
			if (highest == null || card.getRank().ordinal() > highest.getRank().ordinal()) {
				highest = card;
			}
		}
		return highest;
	}

	public Set<Card> getTrumpfCards() {
		return Collections.unmodifiableSet(trumpfCards);
	}

	protected Set<Card> getOtherCards() {
		return Collections.unmodifiableSet(otherCards);
	}

	public Set<Card> getAllCards() {
		Set<Card> cards = new HashSet<Card>(trumpfCards);
		cards.addAll(otherCards);
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
		trumpfCards.remove(card);
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
