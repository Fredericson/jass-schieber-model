package ch.jass.model;

public enum Card {

	SPADE_6(Color.SPADES, Rank.SIX), SPADE_7(Color.SPADES, Rank.SEVEN), SPADE_8(
			Color.SPADES, Rank.EIGHT), SPADE_9(Color.SPADES, Rank.NINE), SPADE_10(
			Color.SPADES, Rank.TEN), SPADE_J(Color.SPADES, Rank.JACK), SPADE_Q(
			Color.SPADES, Rank.QUEEN), SPADE_K(Color.SPADES, Rank.KING), SPADE_A(
			Color.SPADES, Rank.ACE),

	HEARTH_6(Color.HEARTS, Rank.SIX), HEARTH_7(Color.HEARTS, Rank.SEVEN), HEARTH_8(
			Color.HEARTS, Rank.EIGHT), HEARTH_9(Color.HEARTS, Rank.NINE), HEARTH_10(
			Color.HEARTS, Rank.TEN), HEARTH_J(Color.HEARTS, Rank.JACK), HEARTH_Q(
			Color.HEARTS, Rank.QUEEN), HEARTH_K(Color.HEARTS, Rank.KING), HEARTH_A(
			Color.HEARTS, Rank.ACE),

	DIAMOND_6(Color.DIAMONDS, Rank.SIX), DIAMOND_7(Color.DIAMONDS, Rank.SEVEN), DIAMOND_8(
			Color.DIAMONDS, Rank.EIGHT), DIAMOND_9(Color.DIAMONDS, Rank.NINE), DIAMOND_10(
			Color.DIAMONDS, Rank.TEN), DIAMOND_J(Color.DIAMONDS, Rank.JACK), DIAMOND_Q(
			Color.DIAMONDS, Rank.QUEEN), DIAMOND_K(Color.DIAMONDS, Rank.KING), DIAMOND_A(
			Color.DIAMONDS, Rank.ACE),

	CLUB_6(Color.CLUBS, Rank.SIX), CLUB_7(Color.CLUBS, Rank.SEVEN), CLUB_8(
			Color.CLUBS, Rank.EIGHT), CLUB_9(Color.CLUBS, Rank.NINE), CLUB_10(
			Color.CLUBS, Rank.TEN), CLUB_J(Color.CLUBS, Rank.JACK), CLUB_Q(
			Color.CLUBS, Rank.QUEEN), CLUB_K(Color.CLUBS, Rank.KING), CLUB_A(
			Color.CLUBS, Rank.ACE);

	private Card(final Color color, final Rank rank) {
		this.color = color;
		this.rank = rank;
	}

	public Color getColor() {
		return color;
	}

	public Rank getRank() {
		return rank;
	}

	public TrumpfRank getTrumpfRank() {
		return TrumpfRank.getTrumpfRank(rank);
	}

	private final Color color;
	private final Rank rank;

	public static Card getCard(final Color color, final TrumpfRank trumpfRank) {
		return getCard(color, trumpfRank.getRank());
	}

	public static Card getCard(final Color color, final Rank rank) {
		for (Card card : values()) {
			if (card.color.equals(color) && card.rank.equals(rank)) {
				return card;
			}
		}
		throw new IllegalStateException("No Card was found for color=" + color + ", trumpfRank=" + rank);
	}

}
