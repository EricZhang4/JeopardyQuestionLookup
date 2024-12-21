import java.io.File;



import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


import javafx.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.*;
import javafx.stage.*;

public class JeopardyQuestionGUI extends Application{

	
	
	private TextField showNumberBox;
	private ComboBox<JeopardyQuestion.Round> roundBox;
	private TextField categoryBox; 
	private TextField valueBox;
	private Button seeProgramButton, resetButton;
	private TextArea resultArea;
	
	private List<JeopardyQuestion> questionList; 
	private Map<JeopardyQuestion.Round, List<JeopardyQuestion>> questionsByRound;


	public void start(Stage primaryStage) {
		questionList = new ArrayList<>();
		questionsByRound = new HashMap<>();
		fillListAndMap(questionList, questionsByRound);

		Font font = Font.font("Helvetica", 20);
		
		resetButton = new Button("Reset Inputs and Output");
		resetButton.setOnAction(this::resetInputs);
		
		seeProgramButton = new Button("View Results");
		seeProgramButton.setOnAction(this::getResults);

		resultArea = new TextArea();
		
		
		VBox inputBox = new VBox();
		inputBox.setStyle("-fx-background-color: beige");
		inputBox.setAlignment(Pos.CENTER);
		inputBox.setPadding(new Insets(10, 10, 10, 10));
		inputBox.setSpacing(10);
		
		HBox showNumberHBox = new HBox();
		showNumberHBox.setAlignment(Pos.CENTER);
		showNumberHBox.setSpacing(10);
		Text showNumberText = new Text("Show number");
		showNumberText.setFont(font);
		showNumberBox = new TextField();
		showNumberHBox.getChildren().add(showNumberText);
		showNumberHBox.getChildren().add(showNumberBox);

		
		HBox roundHBox = new HBox();
		roundHBox.setAlignment(Pos.CENTER);
		roundHBox.setSpacing(10);
		roundBox = new ComboBox<JeopardyQuestion.Round>(FXCollections.observableArrayList(Arrays.asList(JeopardyQuestion.Round.values()))); 
		Text roundText = new Text("Round");
		roundText.setFont(font);
		
		roundHBox.getChildren().add(roundText);
		roundHBox.getChildren().add(roundBox);
		
		
		HBox categoryHBox = new HBox();
		categoryHBox.setAlignment(Pos.CENTER);
		categoryHBox.setSpacing(10);
		categoryBox = new TextField();
		//TODO consider making this an adaptive comboBox ^
		
		Text categoryText = new Text("Category");
		categoryText.setFont(font);
		
		categoryHBox.getChildren().add(categoryText);
		categoryHBox.getChildren().add(categoryBox);
		
		
		HBox valueHBox = new HBox();
		valueHBox.setAlignment(Pos.CENTER);
		valueHBox.setSpacing(10);
		valueBox = new TextField();
		//TODO consider making this an adaptive comboBox ^
		Text valueText = new Text("Value");
		valueText.setFont(font);
		
		valueHBox.getChildren().add(valueText);
		valueHBox.getChildren().add(valueBox);
		
		
		inputBox.getChildren().add(showNumberHBox);
		inputBox.getChildren().add(roundHBox);
		inputBox.getChildren().add(categoryHBox);
		inputBox.getChildren().add(valueHBox);
		
		VBox buttonBox = new VBox();
		buttonBox.setStyle("-fx-background-color: blue");
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(10);
		buttonBox.setPadding(new Insets(10, 10, 10, 10));		
		buttonBox.getChildren().add(seeProgramButton);
		buttonBox.getChildren().add(resultArea);
		buttonBox.getChildren().add(resetButton);
		
		
		VBox mainBox = new VBox();
		mainBox.getChildren().add(inputBox);
		mainBox.getChildren().add(buttonBox);
		
		
		Scene scene = new Scene(mainBox, 450, 450);
		
		primaryStage.setTitle("Jeopardy Question Lookup");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		

	}


	
	
	private void getResults(ActionEvent event) {
		resultArea.clear();
		String category = categoryBox.getText();
		JeopardyQuestion.Round round = roundBox.getValue();
		String value = valueBox.getText();
		
		int showNumber;
		
		if (showNumberBox.getText() == null || showNumberBox.getText() == "") {
			showNumber = 0;
		} else{ 
			showNumber = Integer.parseInt(showNumberBox.getText());
		}
		
		
		
//		System.out.println(questionList.size());
//		while(showNumber == 0) {
//			try {
//				showNumber = Integer.parseInt(showNumberBox.getText());
//			} catch (NumberFormatException ex) {
//				resultArea.appendText("ERROR: number format exception found");
//			}
//		}
		
		if(round == null && (category == null || category == "") && (value == null || value == "")  && showNumber == 0) {
			resultArea.appendText("ERROR: you must fill in all fields.");
		} else {
				//List<JeopardyQuestion> selectedQuestionList = new ArrayList<>();
				String questionInfo = "";
			
	//			JeopardyQuestion question = questionList.get(4192);
	//			System.out.println(showNumber == question.getShowNumber());
	//			System.out.println("category of data: " + question.getCategory().replaceAll("\"", "") + "inputted category: " + category);
	//			System.out.println("value of data:" + question.getValue().replaceAll("\"", "") + "inputted value: " + value);
	//			System.out.println("round of data:" + question.getRoundType() + "inputted round: " + round);
				
				//System.out.println(question);
				
			for(JeopardyQuestion question : questionList) {
				if( (showNumber == 0 || showNumber == question.getShowNumber()) && 
						(category==null || category == "" || question.getCategory().replaceAll("\"", "").equalsIgnoreCase(category)) &&
					(value==null ||	value == "" || question.getValue().replaceAll("\"", "").equalsIgnoreCase(value)) &&
					(round==null || question.getRoundType().equals(round))						
					) {
					questionInfo = question.toString();
					resultArea.appendText("\nQuestion found: " + questionInfo);
				}
			}
			if (questionInfo == "" ) {
				resultArea.appendText("Question not found, please try again.");
			}
		}
	}
	
	
	
	private void resetInputs(ActionEvent event) {
		valueBox.clear();
		categoryBox.clear();
		roundBox.setValue(null);
		showNumberBox.clear();
		resultArea.setText("");
	}
	
	public static void main(String[] args) {
		launch(args);

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
				String category = lineScan.next(); //gets category in uppercase
				String value = lineScan.next(); //TODO gets $ + the amount
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
