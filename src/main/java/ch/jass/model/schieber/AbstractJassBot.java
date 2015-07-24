package ch.jass.model.schieber;

import java.util.List;

import ch.jass.model.Card;
import ch.jass.model.Color;
import ch.jass.model.SessionType;
import ch.jass.model.Trumpf;
import ch.jass.model.schieber.api.SchieberServerService;

public abstract class AbstractJassBot extends SchieberPlayer {

	public AbstractJassBot(final SchieberServerService schieberService, final String name) {
		super(schieberService, name);
	}

	@Override
	public void requestSessionChoice(final List<String> sessions) {
		// By default We always auto_join the session
		schieberService.sendChooseSession(SessionType.AUTO_JOIN, null);
	}

	@Override
	public void requestCard(final Color requestedColor) {
		Card card = chooseCard(requestedColor);
		schieberService.sendChooseCard(card);
	}

	/**
	 * If the Player is the first in the round the requested Color will be null
	 * 
	 * @param requestedColor
	 * @return Card
	 */
	public abstract Card chooseCard(Color requestedColor);

	@Override
	public void requestTrumpf(final boolean geschoben) {
		Trumpf trumpf = chooseTrumpf(geschoben);
		schieberService.sendChooseTrumpf(trumpf);
	}

	/**
	 * Return null means shifted.
	 * 
	 * @param geschoben
	 * @return Trumpf
	 */
	public abstract Trumpf chooseTrumpf(boolean geschoben);

}
