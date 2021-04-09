package pkgMain;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CodeBreakDown2 extends Application{
	
	
	GardenConditions garden = new GardenConditions(500, "full", "dry", "clay");
	GardenState state = new GardenState("Test Garden", "Arpil", 0, false, garden.getBudget());
	Plant demoPlantOne = new Plant(6, 3, "Acer negudo", "clay", "full", "dry", 100, 100, 1, 0 ,0);
	int gardenBudget = garden.getBudget();
	TilePane tile = new TilePane();
	TilePane tileTwo = new TilePane();
	FlowPane flow = new FlowPane();
	BorderPane border = new BorderPane();
	Text leps = new Text();
	Text budget = new Text();
	ImageView iv1;
	Canvas canvas; 
	Group root;
	
	public ImageView newPlant() {
		Image milkweed = new Image(getClass().getResourceAsStream("/img/commonMilkweed.png"));
		
		iv1 = new ImageView();
		iv1.setImage(milkweed);
		iv1.setPreserveRatio(true);
		iv1.setFitHeight(100);
		iv1.setId("Milkweed");
		
		iv1.setOnDragDetected(new EventHandler<MouseEvent>(){
			
			public void handle(MouseEvent event) {				
				Dragboard db = iv1.startDragAndDrop(TransferMode.COPY);
				ClipboardContent content = new ClipboardContent();
				content.putString(iv1.getId());
				db.setContent(content);
				event.consume();	
			}
		});
    	
		
		return iv1;
					
	}
	
    @Override
    public void start(Stage stage) {
    	
    	//Setting up the Images
    	newPlant();
    	
    	//DRAG AND DROP FEATURE *****************************************************
    	
    	flow.setOnDragDropped(new EventHandler <DragEvent>(){
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if(db.hasString()) {
					String nodeId = db.getString();
					ImageView plant = (ImageView) tile.lookup("#" + nodeId);
					
					if(plant != null) {
						flow.getChildren().add(newPlant());
						updateGardenDisplay();
					}
				}
				
				event.setDropCompleted(true);
				event.consume();
			}
		});
    	
    	flow.setOnDragOver(new EventHandler <DragEvent>() {
			public void handle(DragEvent event) {
				
				if (event.getGestureSource() != flow && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY);
                }
				
				event.consume();
			}
		});
    	
    	
    	//***************************************************************************
    	//Creating the Layout of Main Garden Screen
		border.setStyle("-fx-background-color: white;");
		flow.setPadding(new Insets(10, 10, 10, 10));
		flow.setStyle("-fx-background-color: Brown;");
		border.setCenter(flow); 
		tile.setPadding(new Insets(10, 10, 10, 10));
		tile.setStyle("-fx-background-color: Pink;");
		tile.getChildren().add(iv1);
		border.setLeft(tile);
		tileTwo.setPadding(new Insets(10, 10, 10, 10));
		tileTwo.setStyle("-fx-background-color: White;");
		
		//TRYING TO LET IT FREE DROP	
		canvas = new Canvas(flow.getHeight(), flow.getWidth());
		root = new Group();
		root.getChildren().add(canvas);
		flow.getChildren().add(root);
		
		
		//Text leps = new Text();
		leps.setText("Leps: " + state.totalLepsSupported);
		tileTwo.getChildren().add(leps);
		
		//Text budget = new Text();
		budget.setText("Budget: $" + state.gardenBudget);
		tileTwo.getChildren().add(budget);
		
		border.setTop(tileTwo);
	
		
    	Scene scene = new Scene(border, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    
    public void updateGardenDisplay() {
    	
    	ArrayList<Integer> updates = GardenState.placePlant(state, demoPlantOne);
    	
    	int newLeps = updates.get(0);
    	int newBudget = updates.get(1);
    	
		leps.setText("Leps: " + newLeps);
		//tileTwo.getChildren().add(leps);
    	
		
		budget.setText("Budget: $" + newBudget);
		//tileTwo.getChildren().add(budget);
    	
    	
    }

    public static void main(String[] args) {
        launch();
    }
	public void drag(MouseEvent event) {
		System.out.println("ic mouse");
		Node n = (Node)event.getSource();
		n.setTranslateX(n.getTranslateX() + event.getX());
		n.setTranslateY(n.getTranslateY() + event.getY());
		//call place plant
		updateGardenDisplay();
	}
}


