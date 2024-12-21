import java.time.LocalDate;
import java.time.*;

import java.util.*;

public class JeopardyQuestion implements Comparable<JeopardyQuestion>{

	public static enum Round {
		JEOPARDY, DOUBLE_JEOPARDY, FINAL_JEOPARDY, TIEBREAKER;
		
		public static Round getRound(String roundString) {
			for (Round round : Round.values()) {
				if (round.toString().equalsIgnoreCase(roundString) 
						|| (round.toString()+ "!").equalsIgnoreCase(roundString)) {
					return round;
				}
			}
			throw new IllegalArgumentException(roundString + " is not a valid String for Round.");
		}
		
		@Override
		public String toString() {
			
			String toPrint = super.toString().charAt(0) + super.toString().substring(1)
					.toLowerCase().replace('_', ' '); //changes enum to a more formatted round
			return toPrint;
		}
		
		
	}
	private int showNumber;
	private LocalDate airDate;
	private Round round;
	private String category;
	private String value;
	private String question;
	private String answer;
	
	public JeopardyQuestion(int showNumber, LocalDate airDate, JeopardyQuestion.Round round, String category, String value, 
			String question , String answer) {
		this.showNumber = showNumber;
		this.airDate = airDate;
		this.round = round;
		this.category = category;
		this.value = value;
		this.question = question;
		this.answer = answer;
	}

	public int getShowNumber() {
		return showNumber;
	}
	
	public LocalDate getAirDate() {
		return airDate;
	}

	public Round getRoundType() {
		return round;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setShowNumber(int showNumber) {
		this.showNumber = showNumber;
	}
	
	public void setAirDate(LocalDate airDate) {
		this.airDate = airDate;
	}
	
	public void setRoundType(Round round) {
		this.round = round;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Override
	public String toString() {
		return "Show number: " + showNumber + "\nAired on: " + airDate + "\nRound: " + round
				+ "\nCategory: " + category + "\nValue: " + value + "\nQuestion: " + question +
				"\nAnswer: " + answer;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JeopardyQuestion) {
			JeopardyQuestion other = (JeopardyQuestion) obj;
			return showNumber == other.showNumber &&
					airDate.equals(other.airDate) && 
					round.equals(other.round) &&
					category.equalsIgnoreCase(other.category) &&
					value == other.value &&
					question.equals(other.question) &&
					answer.equals(other.answer);
		} else {
			return false;
		}
	}
	
	@Override
	public int compareTo(JeopardyQuestion other) {
		//Compare by show number first, then if the show numbers are the same, compare by category
		//If both categories are the same, then compare by value
		int categoryCompare = category.compareTo(other.category);
		int showNumberCompare = Integer.compare(showNumber, other.showNumber);
		if(showNumberCompare != 0) {
			return showNumberCompare;
		} else if(categoryCompare != 0) {
			return categoryCompare;
		} else {
			return value.compareTo(other.value); //might want to fix this TODO
		}
	}
	
	

	

}
