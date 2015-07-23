package ch.jass.model.api;

import java.util.List;
import java.util.Set;

import ch.jass.model.Card;
import ch.jass.model.Color;
import ch.jass.model.Trumpf;
import ch.jass.model.schieber.table.PlayerNumber;
import ch.jass.model.schieber.table.PlayerOnTable;

public interface PlayerCallback {

	String getName();

	void requestSessionChoice(List<String> sessions);

	void broadcastJoinedPlayer(PlayerOnTable playerOnTable);

	void dealCards(Set<Card> cards);

	void requestTrumpf(boolean geschoben);

	void broadcastTrumpfForGame(Trumpf trumpf);

	void broadcastTrumpfablePlayer(PlayerNumber actualTrumpfablePlayer);

	void requestCard(Color requestedColor);

	void requestCardRejected(Card card);

	void broadcastPlayedCard(Card card);
}
