package comp228_mkp_lab5_exercise1;


/**
 * @author Minh Khoi Phan-301278135
 * @since 2024-11-20
 * @implNote Lab #5 Using JDBC
 */
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.FontWeight;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.*;
import java.sql.*;
import javax.swing.*;

public class GameForm extends Application{
	// Override the start method of the Application class

	@Override
	public void start(Stage primaryStage) throws ClassNotFoundException, SQLException {
		// Connection to database
		Connection conn = null;
		try {
			// Passing URL
			String url = "jdbc:mysql://localhost:3306/gamestore";
			String username = "root";
			String password = "NewPassword123";
			conn = DriverManager.getConnection(url,username,password);
			if(conn != null) {
				System.out.println("Database Successfully Connected");
			}
			// Create a statement
			Statement st = conn.createStatement();	
		// Application GUI
		// Design
			primaryStage.setTitle("COMP-228 Lab5 JDBC - Minh Khoi Phan");
			// Create the root container - Border Pane
			BorderPane rootBP = createRootBorderPane();
			// TOP CONTAINER
			HBox topContainer = new HBox(); //Instantiate HBox
			// Set properties
			topContainer.setPadding(new Insets(20, 0, 20, 0));
			// Add contents
			Label header = new Label("Game Store App");
			header.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
			topContainer.getChildren().add(header);	
			topContainer.setAlignment(Pos.BASELINE_CENTER); // Center alignment
			// LEFT CONTAINER
			// Instantiate a GridPane
			GridPane leftContainer = new GridPane();
			// Set properties
			leftContainer.setPadding(new Insets(5, 10, 5, 10));
			leftContainer.setVgap(10);
			leftContainer.setHgap(10);
			// Create labels
			Label lbPlayer = new Label("Player Registration");
			lbPlayer.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
			lbPlayer.setPadding(new Insets(10, 90, 5, 90));
			lbPlayer.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-border-width: 3;");
			GridPane.setHalignment(lbPlayer, HPos.CENTER);
			Label lbPlayerID = new Label("Player ID:");
			Label lbFirstName = new Label("First name:");
			Label lbLastName = new Label("Last name:");
			Label lbAddress = new Label("Address:");
			Label lbProvince = new Label("Province:");
			Label lbPostalCode = new Label("Postal Code:");
			Label lbPhoneNumber = new Label("Phone Number:");
			// Create TextFields
			TextField tfPlayerID = new TextField();
			TextField tfFirstName = new TextField();
			tfFirstName.setPrefWidth(250);
			TextField tfLastName = new TextField();
			TextField tfAddress = new TextField();
			TextField tfProvince = new TextField();
			TextField tfPostalCode = new TextField();
			TextField tfPhoneNumber = new TextField();
			// Add Buttons
			HBox playerControls = new HBox();
			playerControls.setSpacing(30);
			playerControls.setAlignment(Pos.CENTER);
			playerControls.setPadding(new Insets(8,0,5,0));
			Button btnDisplayPlayers = new Button("Display all");
			Button btnCreatePlayer = new Button("Add player");
			playerControls.getChildren().addAll(btnDisplayPlayers, btnCreatePlayer);
			// Populate contents
			leftContainer.add(lbPlayer, 0, 0, 2, 1);
			leftContainer.add(lbPlayerID, 0, 1);
			leftContainer.add(tfPlayerID, 1, 1);
			leftContainer.add(lbFirstName, 0, 2);
			leftContainer.add(tfFirstName, 1, 2);
			leftContainer.add(lbLastName, 0, 3);
			leftContainer.add(tfLastName, 1, 3);
			leftContainer.add(lbAddress, 0, 4);
			leftContainer.add(tfAddress, 1, 4);
			leftContainer.add(lbPostalCode, 0, 5);
			leftContainer.add(tfPostalCode, 1, 5);
			leftContainer.add(lbProvince, 0, 6);
			leftContainer.add(tfProvince, 1, 6);
			leftContainer.add(lbPhoneNumber, 0, 7);
			leftContainer.add(tfPhoneNumber, 1, 7);
			leftContainer.add(playerControls, 0, 8, 2, 1);
			// RIGHT CONTAINER
			// Instantiate a GridPane
			GridPane rightContainer = new GridPane();
			// Set properties
			rightContainer.setPadding(new Insets(5, 10, 5, 10));
			rightContainer.setVgap(10);
			rightContainer.setHgap(10);
			// Create labels
			Label lbGame = new Label("Game Details");
			lbGame.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
			lbGame.setPadding(new Insets(10, 85, 5, 85));
			lbGame.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-border-width: 3;");
			GridPane.setHalignment(lbGame, HPos.CENTER);
			Label lbGameID = new Label("Game ID:");
			Label lbGameTitle = new Label("Game Title:");
			Label lbProfile = new Label("Profile Information");
			lbProfile.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
			lbProfile.setPadding(new Insets(30, 65, 5, 65));
			lbProfile.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-border-width: 3;");
			GridPane.setHalignment(lbProfile, HPos.CENTER);
			Label lbProfileID = new Label("Select a profile:");
			Spinner<Integer> idSpinner = new Spinner<Integer>(1,10,1);
			idSpinner.setEditable(true);
			idSpinner.setPrefWidth(50);
			// Create TextFields
			TextField tfGameID = new TextField();
			TextField tfGameTitle = new TextField();
			tfGameTitle.setPrefWidth(200);
			// Add buttons
			HBox gameControls = new HBox();
			gameControls.setAlignment(Pos.CENTER);
			gameControls.setPadding(new Insets(8,0,5,0));
			Button btnAddGame = new Button("Add game");
			gameControls.getChildren().addAll(btnAddGame);
			HBox profileControls = new HBox();
			profileControls.setSpacing(25);
			profileControls.setAlignment(Pos.BASELINE_LEFT);
			profileControls.setPadding(new Insets(8,0,5,0));
			Button btnUpdatePlayer = new Button("Update player");
			Button btnViewPlayer = new Button("View played games");
			profileControls.getChildren().addAll(btnUpdatePlayer, btnViewPlayer);
			// Populate the contents
			rightContainer.add(lbGame, 0, 0, 2, 1);
			rightContainer.add(lbGameID, 0, 1);
			rightContainer.add(tfGameID, 1, 1);
			rightContainer.add(lbGameTitle, 0, 2);
			rightContainer.add(tfGameTitle, 1, 2);
			rightContainer.add(gameControls, 0, 3, 2, 1);
			rightContainer.add(lbProfile, 0, 4, 2, 1);
			rightContainer.add(lbProfileID, 0, 5);
			rightContainer.add(idSpinner, 1, 5);
			rightContainer.add(profileControls, 0, 6, 2, 1);
		// Populate the containers
			BorderPane.setMargin(leftContainer, new Insets(0,0,0,100));
			BorderPane.setMargin(rightContainer, new Insets(0,100,0,0));
			rootBP.setTop(topContainer);
			rootBP.setLeft(leftContainer);
			rootBP.setRight(rightContainer);
		// Create a scene
			Scene scene = new Scene(rootBP, 1000, 600);
		// Set scene
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(SQLException e) {
			System.out.println("Exception: "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
	}

	
	public static void main (String[] args)
	{
		launch();
	}
	// Root Container function
	private BorderPane createRootBorderPane() {
		// Instantiate a flow pane
		BorderPane bp = new BorderPane();
		// Set properties
		
		
		return bp;
	}
	// Top Container function
	private HBox createHBox() {
		// Instantiate an HBox
		HBox hbox = new HBox();
		// Set properties
		hbox.setPadding(new Insets(20, 0, 20, 0));
		// Add contents
		Label header = new Label("Student Registration Form");
		header.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		hbox.getChildren().add(header);	
		hbox.setAlignment(Pos.BASELINE_CENTER); // Center alignment
		return hbox;
	}
	// Left Container function
	private GridPane createGridPane() {
		// Instantiate a GridPane
		GridPane gpane = new GridPane();
		// Set properties
		gpane.setPadding(new Insets(5, 10, 5, 10));
		gpane.setVgap(8);
		gpane.setHgap(8);
		// Create labels
		Label lbName = new Label("Full name:");
		Label lbAddress = new Label("Address:");
		Label lbCity = new Label("City:");
		Label lbProvince = new Label("Province:");
		Label lbPostalCode = new Label("Postal Code:");
		Label lbPhoneNumber = new Label("Phone Number:");
		Label lbEmail = new Label("Email:");
		// Create TextFields
		TextField tfName = new TextField();
		tfName.setPrefWidth(250);
		TextField tfAddress = new TextField();
		TextField tfCity = new TextField();
		TextField tfProvince = new TextField();
		TextField tfPostalCode = new TextField();
		TextField tfPhoneNumber = new TextField();
		TextField tfEmail = new TextField();
		// Populate contents
		gpane.add(lbName, 0, 0);
		gpane.add(tfName, 1, 0);
		gpane.add(lbAddress, 0, 1);
		gpane.add(tfAddress, 1, 1);
		gpane.add(lbCity, 0, 2);
		gpane.add(tfCity, 1, 2);
		gpane.add(lbProvince, 0, 3);
		gpane.add(tfProvince, 1, 3);
		gpane.add(lbPostalCode, 0, 4);
		gpane.add(tfPostalCode, 1, 4);
		gpane.add(lbPhoneNumber, 0, 5);
		gpane.add(tfPhoneNumber, 1, 5);
		gpane.add(lbEmail, 0, 6);
		gpane.add(tfEmail, 1, 6);
		return gpane;
	}
	// Center container function
	private VBox createActivityVBox() {
		//Instantiate a tilePane
		VBox vb = new VBox();
		//Set properties
		vb.setPadding(new Insets(5,10,5,10));
		vb.setSpacing(20);
		//Add title
		Label title = new Label("Choose your college activities:");
		title.setFont(Font.font(14));
		vb.getChildren().add(title);
		//Add check box
		String str[] = { "Student Council", "Sports Club", "Volunteer Work", "Library Service"};
		for (int i=0; i<str.length; i++) {
			CheckBox cb = new CheckBox(str[i]);
			vb.getChildren().add(cb);
			cb.setIndeterminate(false);
		}
		return vb;
	}
	// Right container function
	private FlowPane createProgramFlowPane() {
		//Instantiate a FlowPane
		FlowPane fp = new FlowPane(Orientation.VERTICAL);
		//Set properties
		fp.setAlignment(Pos.BASELINE_LEFT);
		fp.setPadding(new Insets(5,10,5,10));
		fp.setVgap(5);
		//Add title
		Label title = new Label("Choose your program:");
		title.setFont(Font.font(14));
		//Add radio buttons
		HBox hp = new HBox();
		hp.setSpacing(30);
		ToggleGroup programGroup = new ToggleGroup();
		RadioButton program1 = new RadioButton("Computer Science");
		RadioButton program2 = new RadioButton("Business");
		program1.setToggleGroup(programGroup);
		program2.setToggleGroup(programGroup);
		hp.getChildren().addAll(program1,program2);
		//Add combo box
		String csCourses[]= { "Python" , "C#", "Java" };
		String businessCourses[]= { "Accounting", "Finance", "Risk Management" };
		ComboBox<String> cb = new ComboBox<String>(FXCollections.observableArrayList(csCourses));
		//Add list view
		ListView<String> listView = new ListView<String>();
		listView.setMaxHeight(150);
		listView.setMaxWidth(180);
		listView.getItems().add("Your selected course:");
		fp.getChildren().addAll(title,hp,cb,listView);
		return fp;
	}
	// Bottom container function
	private FlowPane createDisplayFlowPane() {
		//Instantiate a FlowPane
		FlowPane bottomContainer = new FlowPane(Orientation.VERTICAL);
		//Set properties
		bottomContainer.setAlignment(Pos.CENTER);
		bottomContainer.setPadding(new Insets(5,10,5,10));
		bottomContainer.setVgap(10);
		//Add display button
		Button btnDisplay = new Button("Display");
		//Add text area
		TextArea txtArea = new TextArea("Your information");
		bottomContainer.getChildren().addAll(btnDisplay,txtArea);
		return bottomContainer;
	}
}

