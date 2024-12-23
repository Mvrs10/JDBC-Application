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
			List<TextField> tfList = List.of(tfPlayerID, tfFirstName, tfLastName, tfAddress, tfPostalCode, tfProvince, tfPhoneNumber);
			// Add Buttons
			HBox playerControls = new HBox();
			playerControls.setSpacing(30);
			playerControls.setAlignment(Pos.CENTER);
			playerControls.setPadding(new Insets(8,0,5,0));
			Button btnDisplayPlayers = new Button("Display all");
			Button btnCreatePlayer = new Button("Add player");
			Button btnReset = new Button("Reset");
			playerControls.getChildren().addAll(btnDisplayPlayers, btnCreatePlayer, btnReset);

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
			int playerCount = countPlayer(); // Return the number of records
			Spinner<Integer> idSpinner = new Spinner<Integer>(1,playerCount,1);
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
			// RESET FUNCTIONALITY
			EventHandler<ActionEvent> resetFields = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					clearFields(tfList);
				}
			};
			btnReset.setOnAction(resetFields);
			// CREATE PLAYER FUNCTIONALITY
			EventHandler<ActionEvent> addPlayer = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					Connection conn = null;
					PreparedStatement pst = null;
					Integer playerID = Integer.valueOf(tfPlayerID.getText());
					String firstname = tfFirstName.getText();
					String lastname = tfLastName.getText();
					String address = tfAddress.getText();
					String province = tfProvince.getText();
					String postalcode = tfPostalCode.getText();
					String phonenumber = tfPhoneNumber.getText();
					try {
						conn = getDBConnection(conn);
						String query = "INSERT INTO player (player_id,first_name,last_name,address,postal_code,province,phone_number) VALUES (?,?,?,?,?,?,?)";
						pst = conn.prepareStatement(query);
						pst.setInt(1, playerID);
						pst.setString(2, firstname);
						pst.setString(3, lastname);
						pst.setString(4, address);
						pst.setString(5, postalcode);
						pst.setString(6, province);
						pst.setString(7, phonenumber);
						int res = pst.executeUpdate();
						if (res > 0) {
							idSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,playerCount+1,1));
							JOptionPane.showMessageDialog(null, res+" player inserted", "Player", 1);
						}						
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					finally {									
						try {
							closeDBConnection(conn,pst);
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
			};
			btnCreatePlayer.setOnAction(addPlayer);
			// DISPLAY ALL PLAYERS - JTABLE
			EventHandler<ActionEvent> displayAllPlayers = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					Connection conn = null;
					Statement st = null;
					ResultSet rs = null;
					try
					{
						conn = getDBConnection(conn);
						String query = "SELECT * FROM player";
						st = conn.createStatement();
						rs = st.executeQuery(query);
						List<String> colNames = List.of(lbPlayerID.getText().substring(0,lbPlayerID.getText().length()-1), lbFirstName.getText().substring(0,lbFirstName.getText().length()-1), lbLastName.getText().substring(0,lbLastName.getText().length()-1), lbAddress.getText().substring(0,lbAddress.getText().length()-1), lbPostalCode.getText().substring(0,lbPostalCode.getText().length()-1), lbProvince.getText().substring(0,lbProvince.getText().length()-1), lbPhoneNumber.getText().substring(0,lbPhoneNumber.getText().length()-1));
						List<String[]> data = new ArrayList<String[]>();
						while(rs.next())
						{
							data.add(new String[] {String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)});
						}
						makeTable("Player Table",data,colNames,800,200);
					}
					catch(SQLException ex) {
						ex.printStackTrace();
					}
					finally
					{
						try {
							closeDBConnection(conn,st,rs);
						}
						catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
			};
			btnDisplayPlayers.setOnAction(displayAllPlayers);
			//ADD GAME FUNCTIONALITY
			EventHandler<ActionEvent> addGame = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					Connection conn = null;
					PreparedStatement pst = null;
					Integer gameID = Integer.valueOf(tfGameID.getText());
					String gameTitle = tfGameTitle.getText();
					try {
						conn = getDBConnection(conn);
						String query = "INSERT INTO game (game_id,game_title) VALUES (?,?)";
						pst = conn.prepareStatement(query);
						pst.setInt(1, gameID);
						pst.setString(2, gameTitle);
						int res = pst.executeUpdate();
						if (res > 0) {
							tfGameID.clear();
							tfGameTitle.clear();
							JOptionPane.showMessageDialog(null, res+" game inserted", "Game", 1);
						}						
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					finally {									
						try {
							closeDBConnection(conn,pst);
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
			};
			btnAddGame.setOnAction(addGame);
			//SHOW PLAYER PROFILE USING SPINNER
			idSpinner.valueProperty().addListener(new ChangeListener<Integer>() {

				@Override
				public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
					Connection conn = null;
					PreparedStatement pst = null;
					ResultSet rs = null;
					try {
						conn = getDBConnection(conn);
						String query = "SELECT * FROM player WHERE player_id=?";
						pst = conn.prepareStatement(query);
						pst.setInt(1, idSpinner.getValue());
						rs = pst.executeQuery();
						rs.next();
						tfPlayerID.setDisable(true);
						tfPlayerID.setText(String.valueOf(idSpinner.getValue()));	
						tfFirstName.setText(rs.getString(2));
						tfLastName.setText(rs.getString(3));
						tfAddress.setText(rs.getString(4));
						tfPostalCode.setText(rs.getString(5));
						tfProvince.setText(rs.getString(6));
						tfPhoneNumber.setText(rs.getString(7));
					}
					catch (SQLException ex) {
						ex.printStackTrace();
					}
					finally {
						try {
							closeDBConnection(conn,pst,rs);
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
				
			});
			//UPDATE PLAYER FUNCTIONALITY
			EventHandler<ActionEvent> updatePlayer = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					Connection conn = null;
					PreparedStatement pst = null;
					Integer playerID = idSpinner.getValue();
					String firstname = tfFirstName.getText();
					String lastname = tfLastName.getText();
					String address = tfAddress.getText();
					String province = tfProvince.getText();
					String postalcode = tfPostalCode.getText();
					String phonenumber = tfPhoneNumber.getText();
					try {
						conn = getDBConnection(conn);
						String query = "UPDATE player SET player_id=?,first_name=?,last_name=?,address=?,postal_code=?,province=?,phone_number=? WHERE player_id=?";
						pst = conn.prepareStatement(query);
						pst.setInt(1, playerID);
						pst.setInt(8, playerID);
						pst.setString(2, firstname);
						pst.setString(3, lastname);
						pst.setString(4, address);
						pst.setString(5, postalcode);
						pst.setString(6, province);
						pst.setString(7, phonenumber);
						int res = pst.executeUpdate();
						if (res > 0) {
							JOptionPane.showMessageDialog(null, res+" records successfully updated!", "Player", 1);
						}	
					}
					catch (SQLException ex) {
						ex.printStackTrace();
					}
					finally {
						clearFields(tfList);
						try {
							closeDBConnection(conn,pst);
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
			};
			btnUpdatePlayer.setOnAction(updatePlayer);
			// VIEW OWNED GAME FUNCTIONALITY
			EventHandler<ActionEvent> displayOwnedGames = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					Connection conn = null;
					PreparedStatement pst = null;
					ResultSet rs = null;
					try
					{
						conn = getDBConnection(conn);
						String query = "SELECT ownedgame.game_id, game.game_title, ownedgame.playing_date, ownedgame.score, player.first_name, player.last_name\r\n"
								+ "FROM gamestore.player \r\n"
								+ "INNER JOIN gamestore.ownedgame ON gamestore.player.player_id = gamestore.ownedgame.player_id\r\n"
								+ "INNER JOIN gamestore.game ON gamestore.ownedgame.game_id = gamestore.game.game_id\r\n"
								+ "WHERE gamestore.player.player_id = ?;";
						pst = conn.prepareStatement(query);
						pst.setInt(1, idSpinner.getValue());
						rs = pst.executeQuery();
						List<String> colNames = List.of("Game ID", "Title", "Date", "Score");
						List<String[]> data = new ArrayList<String[]>();
						while(rs.next())
						{
							data.add(new String[] {String.valueOf(rs.getInt(1)), rs.getString(2), String.valueOf(rs.getDate(3)), String.valueOf(rs.getInt(4))});
						}
						makeTable(tfFirstName.getText()+tfLastName.getText()+"'s Games",data,colNames,400,100);
					}
					catch(SQLException ex) {
						ex.printStackTrace();
					}
					finally
					{
						try {
							closeDBConnection(conn,pst,rs);
						}
						catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
			};
			btnViewPlayer.setOnAction(displayOwnedGames);
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
	};
	// Database Connection function
	private Connection getDBConnection(Connection conn) throws SQLException {
		final String url = "jdbc:mysql://localhost:3306/gamestore";
		final String username = "root";
		final String password = "NewPassword123";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			// Passing URL
			conn = DriverManager.getConnection(url,username,password);
			System.out.println("Successfully connected to database!!!");
			return conn;
		}
		catch(SQLException e){
			System.out.println("Exception: "+e.getMessage());
			e.printStackTrace();
		}
		return conn;
	};
	// Database close
	private void closeDBConnection(Connection c, PreparedStatement ps) throws SQLException{
		ps.close();
		c.close();
		System.out.println("Connection close!");
	};
	private void closeDBConnection(Connection c, PreparedStatement ps, ResultSet rs) throws SQLException{
		rs.close();
		ps.close();
		c.close();
		System.out.println("Connection close!");
	}
	private void closeDBConnection(Connection c, Statement st, ResultSet rs) throws SQLException{
		rs.close();
		st.close();
		c.close();
		System.out.println("Connection close!");
	}
	// Clear fields
	private void clearFields(List<TextField> arr) {
		arr.get(0).setDisable(false);
		for (int i=0; i<arr.size();i++) {
			arr.get(i).clear();
		}		
	}
	// Make JTable
	private void makeTable(String title, List<String[]> dt, List<String> col, int width, int height) {
		//Create a frame
		JFrame playerFrame = new JFrame(title);
		playerFrame.setSize(width,height);
		String[][] data = dt.toArray(new String[dt.size()][]);
		String[] columnName = col.toArray(new String[col.size()]);
		//Create a J-table
		JTable playerTable = new JTable(data,columnName);
		JScrollPane scrollPane = new JScrollPane(playerTable);		
		playerFrame.add(scrollPane);
		playerFrame.setVisible(true);
	}
	// Get Player Counts
	private int countPlayer() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int playerCount = 10;
		try {
			conn = getDBConnection(conn);
			String query = "SELECT COUNT(*) FROM player;";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			rs.next();
			playerCount = rs.getInt(1);
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				closeDBConnection(conn,st,rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return playerCount;
	}
}

