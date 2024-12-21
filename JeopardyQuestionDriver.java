
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.util.Map;
import java.util.Scanner;

public class JeopardyQuestionDriver {

	public static void main(String[] args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		JeopardyQuestion question1 = new JeopardyQuestion(3154, LocalDate.parse("2000-06-07", formatter), JeopardyQuestion.Round.JEOPARDY, 
				"TRAGEDIES", "200", "Skiing accidents recently claimed the lives of ", "Sonny Bono");
		System.out.println(question1);
		JeopardyQuestion question2 = new JeopardyQuestion(3154, LocalDate.parse("2000-06-07", formatter), JeopardyQuestion.Round.JEOPARDY, 
				"TRAGEDIES", "200", "Skiing accidents recently claimed the lives of ", "Sonny Bono");
		
		
		ArrayList<JeopardyQuestion> list = new ArrayList<>();
		Map<JeopardyQuestion.Round, List<JeopardyQuestion>> map = new HashMap<>();
		fillListAndMap(list, map);
		System.out.println("Filling successful");
		
	}

	public static void fillListAndMap(List<JeopardyQuestion> questionList, Map<JeopardyQuestion.Round, List<JeopardyQuestion>> questionsByRound) {
		try (Scanner fileScan = new Scanner(new FileReader(
  			 new File("JEOPARDY_CSV.csv")))) {
			String line = fileScan.nextLine(); // read the column headers
			while (fileScan.hasNext()) {
				line = fileScan.nextLine(); // one line of data

				// this code assumes perfectly formatted input data
				Scanner lineScan = new Scanner(line);
				lineScan.useDelimiter(",");
				
				int showNumber = Integer.parseInt(lineScan.next());
				LocalDate airDate = LocalDate.parse(lineScan.next().replace('/', '-')); //TODO possible to get a line of hashes, account for this
				JeopardyQuestion.Round round = JeopardyQuestion.Round.getRound(lineScan.next());
				String category = lineScan.next().toLowerCase(); //gets category in uppercase
				String value = lineScan.next();
				String question = lineScan.next();
				String answer = lineScan.next();
				
				JeopardyQuestion jeopardyQuestion = new JeopardyQuestion(showNumber, airDate, round, category, value, question, answer);
				questionList.add(jeopardyQuestion);
				
				if (questionsByRound.containsKey(jeopardyQuestion.getRoundType())) {
					questionsByRound.get(jeopardyQuestion.getRoundType()).add(jeopardyQuestion);
				} else {
					List<JeopardyQuestion> list = new ArrayList<>();
					list.add(jeopardyQuestion);
					questionsByRound.put(jeopardyQuestion.getRoundType(), list);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
	
