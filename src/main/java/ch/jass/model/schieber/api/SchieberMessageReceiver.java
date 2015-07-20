package ch.jass.model.schieber.api;

import java.util.Set;

import ch.jass.model.Card;
import ch.jass.model.Color;
import ch.jass.model.Trumpf;
import ch.jass.model.schieber.table.PlayerNumber;
import ch.jass.model.schieber.table.SchieberStich;
import ch.jass.model.schieber.table.SchieberTableInfo;

public interface SchieberMessageReceiver {

	void receiveTeams(SchieberTableInfo schieberTableInfo);

	Card chooseCard(Color requestedColor);

	void receiveCards(Set<Card> cards);

	void receiveTrumpfForGame(Trumpf trumpf);

	Trumpf chooseTrumpf(boolean geschoben);

	void receivePlayedCard(Card card);

	void receiveTrumpfablePlayer(PlayerNumber actualTrumpfablePlayer);

	void receiveStich(SchieberStich stich);

}
