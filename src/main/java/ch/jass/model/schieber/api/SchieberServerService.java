package ch.jass.model.schieber.api;

import ch.jass.model.Card;
import ch.jass.model.SessionType;
import ch.jass.model.Trumpf;

public interface SchieberServerService {

	public void sendChooseSession(final SessionType sessionType, final String existingSession);

	/**
	 * Null means geschoben.
	 * 
	 * @param trumpf
	 */
	public void sendChooseTrumpf(final Trumpf trumpf);

	public void sendChooseCard(final Card card);
}
