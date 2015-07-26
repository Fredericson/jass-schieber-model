package ch.jass.model.schieber.api;

import ch.jass.model.Card;
import ch.jass.model.SessionType;
import ch.jass.model.Trumpf;

public interface SchieberServerService {

	public void connectToServer(final SchieberPlayerCallback schieberPlayerCallback);

	public void sendChooseSession(final SessionType sessionType, final String existingSession);

	/**
	 * Null means geschoben.
	 * 
	 * @param trumpf
	 *            sends the chosen trumpf to the server.
	 */
	public void sendChooseTrumpf(final Trumpf trumpf);

	public void sendChooseCard(final Card card);
}
