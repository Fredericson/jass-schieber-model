package ch.jass.model.schieber;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.jass.model.Card;
import ch.jass.model.Color;
import ch.jass.model.Player;
import ch.jass.model.Trumpf;
import ch.jass.model.schieber.api.SchieberMessageReceiver;
import ch.jass.model.schieber.table.PlayerNumber;
import ch.jass.model.schieber.table.SchieberStich;
import ch.jass.model.schieber.table.SchieberTableInfo;

public abstract class PlayerAction extends Player implements SchieberMessageReceiver {

	// Each Player has information about what is visible on the Table
	protected SchieberTableInfo schieberTableInfo;
	private final Set<Card> trumpfCards = new HashSet<Card>();
	private final Set<Card> otherCards = new HashSet<Card>();

	public PlayerAction(final String name) {
		super(name);
	}

	public SchieberTableInfo getJassTableInfo() {
		return schieberTableInfo;
	}

	@Override
	public void receiveTeams(final SchieberTableInfo schieberTableInfo) {
		this.schieberTableInfo = schieberTableInfo;
	}

	@Override
	public void receiveCards(final Set<Card> cards) {
		this.otherCards.addAll(cards);
	}

	@Override
	public void receiveTrumpfForGame(final Trumpf trumpf) {
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

	/**
	 * If the Player is the first in the round
	 * 
	 * @param requestedColor
	 * @return Card
	 */
	@Override
	public abstract Card chooseCard(Color requestedColor);

	/**
	 * Return null means shifted.
	 * 
	 * @param geschoben
	 * @return Trumpf
	 */
	@Override
	public abstract Trumpf chooseTrumpf(boolean geschoben);

	@Override
	public void receivePlayedCard(final Card card) {
		trumpfCards.remove(card);
		otherCards.remove(card);
		rememberPlayedCard(card);
		schieberTableInfo.setPlayedCard(card);
	}

	protected abstract void rememberPlayedCard(Card card);

	@Override
	public void receiveTrumpfablePlayer(final PlayerNumber actualTrumpfablePlayer) {
		this.schieberTableInfo.setActualTrumpfablePlayer(actualTrumpfablePlayer);
	}

	@Override
	public void receiveStich(final SchieberStich stich) {
		this.schieberTableInfo.setStich(stich);
	}
}
