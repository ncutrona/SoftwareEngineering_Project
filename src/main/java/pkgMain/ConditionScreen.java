package pkgMain;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.StringConverter;


/**
 * ConditionScreen class.
 * Handles formatting of conditions screen.
 * 
 * @author Aidan Chao
 * @author Nicholas Cutrona
 * @author Caleb Davis
 * @author Joey Loporto
 * @author Tommy Cheung
 */
public class ConditionScreen {

	final double TEXTFIELD_SIZE = 30;
	final double LABEL_SIZE = 35;
	final double PROMPTEXTLABEL_SIZE = 50; 
	
	private Image background = new Image(getClass().getResourceAsStream("/img/conditions.jpg"));
	private BackgroundImage backgroundimage = new BackgroundImage(background, 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundPosition.DEFAULT, 
            new BackgroundSize(Screen.getPrimary().getVisualBounds().getHeight(), Screen.getPrimary().getVisualBounds().getWidth(), true, true, true, true));
	
	Label budgetLabel, gardenLabel, dimensionLabel;
	TextField budget, gardenName, width, length;
	Button previous, next;
	private Text sun, moisture, soil;
	Slider sunSlider, moistSlider, soilSlider;
	private HBox input, sunBox, moistBox, soilBox;
	
	private BorderPane border;
	private FlowPane flow;
	
	
	/**
	 * Constructor for ConditionScreen object.
	 * Sets up sliders, text, input and screen.
	 */
	public ConditionScreen() {
		setText();
		setButtons();
		setSunSlider();
		setMoistSlider();
		setSoilSlider();
		setInputHBox();
		setScreen();
		
	}
	
	
	/**
	 * Returns the screen as a BorderPane
	 * 
	 * @return BorderPane The BorderPane for ConditionScreen
	 */
	public BorderPane getScreen() {
		return this.border;
	}
	
	
	/**
	 * Sets the text for the condition sliders.
	 */
	public void setText() {
		sun = new Text("Set Garden Sun Conditions:");
		sun.setFont(new Font(LABEL_SIZE));
		moisture = new Text("Set Garden Moisture Conditions:");
		moisture.setFont(new Font(LABEL_SIZE));
		soil = new Text("Set Garden Soil Conditions:");
		soil.setFont(new Font(LABEL_SIZE));
	}
	
	
	/**
	 * Sets up Screen.
	 * Sets up and align new border and flow panes.
	 */
	public void setScreen() {
		border = new BorderPane();
		flow = new FlowPane(Orientation.VERTICAL);
		flow.setBackground(new Background(backgroundimage));
		
		flow.getChildren().add(sun);
		flow.getChildren().add(sunBox);
		flow.getChildren().add(moisture);
		flow.getChildren().add(moistBox);
		flow.getChildren().add(soil);
		flow.getChildren().add(soilBox);
		
		border.setTop(input);
		input.setAlignment(Pos.TOP_CENTER);
		border.setCenter(flow);
		BorderPane.setAlignment(flow, Pos.CENTER);
		flow.setAlignment(Pos.CENTER);
		flow.setVgap(20);
	}
	
	
	/**
	 * Setup "go back" and "continue" buttons
	 */
	public void setButtons() {
		previous = new Button("Go Back");
		next = new Button("Continue");
		previous.setStyle("-fx-padding: 8 15 15 15; -fx-background-insets: "
				+ "0,0 0 5 0, 0 0 6 0, 0 0 7 0; -fx-background-radius: "
				+ "8; -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #ffb6c1 0%, #ffb6c1 100%),"
				+ "#ffb6c1,#ffb6c1,radial-gradient(center 50% 50%, radius 100%, #ffb6c1, #ffb6c1);"
				+ "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );-fx-font-weight: bold; -fx-font-size: 1.1em;");
		next.setStyle("-fx-padding: 8 15 15 15; -fx-background-insets: "
				+ "0,0 0 5 0, 0 0 6 0, 0 0 7 0; -fx-background-radius: "
				+ "8; -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #ffb6c1 0%, #ffb6c1 100%),"
				+ "#ffb6c1,#ffb6c1,radial-gradient(center 50% 50%, radius 100%, #ffb6c1, #ffb6c1);"
				+ "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );-fx-font-weight: bold; -fx-font-size: 1.1em;");
	}
	
	
	/**
	 * Setup for the sun slider.
	 * Uses string converter to set condition based on slider
	 */
	public void setSunSlider() {
		sunSlider = setSlider();
		sunSlider.setLabelFormatter(new StringConverter<Double>() {
			@Override
			public String toString(Double n) {
				if (n < 1.5) {
					return "Shade";
				}else if (n > 2.5) {
					return "Full";
				}
				return "Partial";
			}
			@Override
			public Double fromString(String s) {
				switch (s) {
				case "Shade":
					return 1d;
				case "Full":
					return 3d;	
				default:
					return 2d;
				}
			}
		});
		

		sunBox = setSliderHbox(sunSlider);
	}
	
	
	/**
	 * Setup for the soil slider.
	 * Uses string converter to set condition based on slider
	 */
	public void setSoilSlider() {
		soilSlider = setSlider();
		soilSlider.setLabelFormatter(new StringConverter<Double>() {
			@Override
			public String toString(Double n) {
				if (n < 1.5) {
					return "Sand";
				}else if (n > 2.5) {
					return "Clay";
				}
				return "Loam";

			}
			@Override
			public Double fromString(String s) {
				switch (s) {
				case "Sand":
					return 1d;
				case "Clay":
					return 3d;
				default:
					return 2d;
				}
			}
		});

		soilBox = setSliderHbox(soilSlider);
		soilBox.getChildren().addAll(next, previous);
	}
	
	
	/**
	 * Setup for the moisture slider.
	 * Uses string converter to set condition based on slider
	 */
	public void setMoistSlider() {
		moistSlider = setSlider();
		moistSlider.setLabelFormatter(new StringConverter<Double>() {
			@Override
			public String toString(Double n) {
				if (n < 1.5) {
					return "Dry";
				}
				if (n > 2.5) {
					return "Wet";
				}

				else {
					return "Moist";
				}
			}

			@Override
			public Double fromString(String s) {
				switch (s) {
				case "Dry":
					return 1d;
				case "Wet":
					return 3d;	
				default:
					return 2d;
				}
			}
		});
		
		
		moistBox = setSliderHbox(moistSlider);
	}
	
	
	/**
	 * Sets up the input box.
	 * Takes input for budget and garden name.
	 */
	public void setInputHBox() {
		budgetLabel = new Label("Budget $: ");
		budgetLabel.setFont(new Font(LABEL_SIZE));
		gardenLabel = new Label("Garden Name: ");
		gardenLabel.setFont(new Font(LABEL_SIZE));
		
		
		budget = new TextField();
		budget.setPromptText("Enter your Budget $");
		budget.setMaxHeight(PROMPTEXTLABEL_SIZE);
		
		gardenName = new TextField();
		gardenName.setPromptText("Enter Your Garden Name: ");
		gardenName.setMaxHeight(PROMPTEXTLABEL_SIZE);
		
		input =  new HBox(20);
		input.setPadding(new Insets(5, 5, 5, 5));
		input.setStyle("-fx-background-color: pink;");
		input.getChildren().addAll(budgetLabel, budget, gardenLabel, gardenName);
		
		
		dimensionLabel = new Label("Enter Dimension:" );
		dimensionLabel.setFont(new Font(LABEL_SIZE));
		//Text feet = new Text("X");
		Label feet = new Label("X");
		feet.setFont(new Font(LABEL_SIZE));
		length = new TextField();
		length.setPrefHeight(TEXTFIELD_SIZE);
		width = new TextField();
		width.setPrefHeight(TEXTFIELD_SIZE);
		length.setPromptText("Enter length here(feet)");
		length.setMaxHeight(PROMPTEXTLABEL_SIZE);
		width.setPromptText("Enter width here(feet)");
		width.setMaxHeight(PROMPTEXTLABEL_SIZE);
		input.getChildren().addAll(dimensionLabel, length, feet, width);
		
		//
	}
		
	/**
	 * Sets up new sliders horizontally.
	 * Adds passed in slider to new HBox.
	 * 
	 * @param slide slider to add
	 * @return HBox that contains the slider
	 */
	public HBox setSliderHbox(Slider slide) {
		HBox box = new HBox(20);
		box.setPadding(new Insets(5, 5, 5, 5));
		box.setStyle("-fx-background-color: transparent;");
		box.getChildren().addAll(slide);
		
		return box;
	}
	
	
	/**
	 * sets up a generic slider for conditions.
	 * Goes 1-3, same for each conditions slider.
	 * 
	 * @return new slider with formatting to be a conditions slider
	 */
	public Slider setSlider() {
		Slider slider = new Slider(0,3,0);
		slider.setMin(1);
		slider.setMax(3);
		slider.setValue(1);
		slider.setMinorTickCount(0);
		slider.setMajorTickUnit(1);
		slider.setSnapToTicks(true);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMinWidth(380);
		slider.setStyle("-fx-control-inner-background: palegreen;");
		
		
		return slider;
	}
}
