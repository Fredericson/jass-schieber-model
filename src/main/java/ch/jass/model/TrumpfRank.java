package ch.jass.model;

public enum TrumpfRank {
	SIX(Rank.SIX), SEVEN(Rank.SEVEN), EIGHT(Rank.EIGHT), TEN(Rank.TEN),
	QUEEN(Rank.QUEEN), KING(Rank.KING), ACE(Rank.ACE), NELL(Rank.NINE), BUUR(Rank.JACK);

	private final Rank rank;

	private TrumpfRank(final Rank rank) {
		this.rank = rank;
	}

	public Rank getRank() {
		return rank;
	}

	public static TrumpfRank getTrumpfRank(final Rank rank) {
		for (TrumpfRank trumpfRank : TrumpfRank.values()) {
			if (trumpfRank.rank.equals(rank)) {
				return trumpfRank;
			}
		}
		throw new IllegalStateException("Mapping for Rank " + rank + " not found.");
	}
}
