package cl.own.usi.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Object that holds the score of a user the score audit
 *
 * @author nicolas
 */
public class Scores {
	private final int score;
	private final UserScores topScores;
	private final UserScores beforeScores;
	private final UserScores afterScores;

	@JsonProperty(value = "score")
	public int getScore() {
		return score;
	}

	@JsonProperty(value = "top_scores")
	public UserScores getTopScores() {
		return topScores;
	}

	@JsonProperty(value = "before")
	public UserScores getBeforeScores() {
		return beforeScores;
	}

	@JsonProperty(value = "after")
	public UserScores getAfterScores() {
		return afterScores;
	}

	public Scores(int score, UserScores topScores, UserScores beforeScores,
			UserScores afterScores) {
		this.score = score;
		this.topScores = topScores;
		this.beforeScores = beforeScores;
		this.afterScores = afterScores;
	}
}
