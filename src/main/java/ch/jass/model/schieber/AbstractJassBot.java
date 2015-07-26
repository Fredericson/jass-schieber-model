package ch.jass.model.schieber;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.jass.model.Card;
import ch.jass.model.Color;
import ch.jass.model.SessionType;
import ch.jass.model.Trumpf;
import ch.jass.model.schieber.api.SchieberServerService;

public abstract class AbstractJassBot extends SchieberPlayer {

	private final static Logger LOGGER = Logger.getLogger(AbstractJassBot.class.getName());

	public AbstractJassBot(final SchieberServerService schieberService, final String name) {
		super(schieberService, name);
	}

	@Override
	public void requestCardRejected(final Card card) {
		LOGGER.log(Level.SEVERE, "jassbot " + getName()
				+ ": I played a wrong card and I don't know what I can do know.");
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
	 *            the color the player has play
	 * @return Card the chosen Card to play
	 */
	public abstract Card chooseCard(Color requestedColor);

	@Override
	public void requestTrumpf(final boolean geschoben) {
		Trumpf trumpf = chooseTrumpf(geschoben);
		schieberService.sendChooseTrumpf(trumpf);
	}

	/**
	 * Return null means geschoben.
	 * 
	 * @param geschoben
	 *            indicate if the team mate did shift the trumpf.
	 * @return Trumpf
	 */
	public abstract Trumpf chooseTrumpf(boolean geschoben);

}
