package pkgMain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller extends Application{
	Model model;
	
	Stage window;
	Scene gardenScene, LoadScreenScene, ConditionsScene, SavedScene, InventoryScene;
	
	
	public static void main(String[] args) {
		/*
		 * Launch hands off execution to javafx to do various backend tasks
		 * such as setting up graphics and system specifics
		 */
		launch();
	}
	
	@Override
	public void start(Stage primaryStage){
		
		//Creating the Window
		window = primaryStage;
		//Creating a Model instance
		Model model = new Model();
		
		
		
			
		
		//GARDEN SCREEN CONTROLLER CODE **********************************************************************************************
		
		//Creating a GardenScreen Instance
		GardenScreen GardenScreenView = new GardenScreen();
		
		//Creating our Sorted Collection of Plants
    	ArrayList<Plant> plantsMaster = model.getPlants();
    	Collection<Plant> plantCollection = Plant.sortPlants(plantsMaster);
    	
    	//Creaeting our PlantImageData with the demoplants
    	HashMap<String, Image> plantImageData = new HashMap<String, Image>();
    	plantImageData = GardenScreenView.createPlantImages(model.demoPlantOne, model.demoPlantTwo, model.demoPlantThree);
    	
    	
    	
    	
    	
    	GardenScreenView.gardenFlow.setOnDragDropped(new EventHandler <DragEvent>(){
			public void handle(DragEvent event) {
				HashMap<String, Image> images = GardenScreenView.createPlantImages(model.demoPlantOne, model.demoPlantTwo, model.demoPlantThree);
				Dragboard db = event.getDragboard();
				if(db.hasString()) {
					String nodeId = db.getString();
					ImageView plant = new ImageView();
					plant.setImage(images.get(nodeId));
					plant.setPreserveRatio(true);
					plant.setFitHeight(100);
					plant.setId(nodeId);
					//System.out.println(tile.lookup("#" + nodeId)); //TILE LOOKUP IS CAUSING ISSUES. OTHERWISE CODE IS GOOD
					
					
					if(plant != null) {
						//System.out.println("WE REACH HERE!");
						HashMap<String, Plant> plants = model.createPlantData();
						GardenScreenView.gardenFlow.getChildren().add(GardenScreenView.newPlant(nodeId, GardenScreenView.createPlantImages(model.demoPlantOne, model.demoPlantTwo, model.demoPlantThree), model.createPlantData()));
						Plant plantNeeded = plants.get(nodeId);
						model.stateFinal.totalLepsSupported += plantNeeded.lepsSupported;
						model.stateFinal.gardenBudget -= plantNeeded.price;
						GardenScreenView.updateGardenDisplay(model.stateFinal.totalLepsSupported, model.stateFinal.gardenBudget);
						//NOT WORKING
					}
				}
				
				event.setDropCompleted(true);
				event.consume();
			}
		});
    	
    	
    	GardenScreenView.gardenFlow.setOnDragOver(new EventHandler <DragEvent>() {
			public void handle(DragEvent event) {
				
				if (event.getGestureSource() != GardenScreenView.gardenFlow && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY);
                }
				
				event.consume();
			}
		});
    	
    	
		
    	//Setting the GardenScene
    	Scene gardenScene = new Scene(GardenScreenView.createBorder(model.gardenFinal.getSun(), model.gardenFinal.getSoil(), model.gardenFinal.getMoisture(), plantCollection, GardenScreenView.createPlantImages(model.demoPlantOne, model.demoPlantTwo, model.demoPlantThree), model.createPlantData(), model.stateFinal.totalLepsSupported, model.stateFinal.gardenBudget), 800, 600);
 
    	
		//****************************************************************************************************************************
		
    	ConditionsScreen2 condScreen = new ConditionsScreen2();

    	condScreen.setSun.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if(!condScreen.slider.isValueChanging() && condScreen.slider.getValue() == 3d) {
    				model.gardenFinal.setSunConditions("full");
    				System.out.println(model.gardenFinal.getSun());

    			}

    			else if(!condScreen.slider.isValueChanging() && condScreen.slider.getValue() == 2d) {
    				model.gardenFinal.setSunConditions("partial");

    			}


    			else{
    				model.gardenFinal.setSunConditions("shade");
    			}
    			
    		}
    	});

    	condScreen.setMoisture.setOnAction(new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent e) {
    			

    			if(!condScreen.slider3.isValueChanging() && condScreen.slider3.getValue() == 3d) {
    				model.gardenFinal.setMoistureConditions("wet");

    			}

    			else if(!condScreen.slider3.isValueChanging() && condScreen.slider3.getValue() == 2d) {
    				model.gardenFinal.setMoistureConditions("moist");
    				
    			}


    			else{
    				model.gardenFinal.setMoistureConditions("dry");
    				
    			}
    		}
    	});

    	condScreen.setSoil.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if(!condScreen.slider2.isValueChanging() && condScreen.slider2.getValue() == 3d) {
    				model.gardenFinal.setSoilConditions("clay");

    			}

    			else if(!condScreen.slider2.isValueChanging() && condScreen.slider2.getValue() == 2d) {
    				model.gardenFinal.setSoilConditions("loam");
    				

    			}

    			else{
    				model.gardenFinal.setSoilConditions("sand");

    			}
    		}
    	});



    	condScreen.submit.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if ((condScreen.budget.getText() != null && !condScreen.budget.getText().isEmpty() && condScreen.gardenName.getText() != null && !condScreen.gardenName.getText().isEmpty())) {
    				try {
    					int intBudget = Integer.parseInt(condScreen.budget.getText());
    					condScreen.budget.setText("Your budget was set.");
    					condScreen.gardenName.setText("Your Garden Name was set.");

    					
    					//LOOK HERE WHEN WE ADD NAME TO SAVING!!!!!
    					model.gardenFinal.setBudget(intBudget);
    					model.stateFinal.gardenBudget = model.gardenFinal.getBudget();
    					model.stateFinal.setGardenName(condScreen.gardenName.getText());
    					GardenScreenView.budget.setText("Budget: $" + intBudget);
    				} catch(Exception except) {
    					condScreen.budget.setText("");
    					condScreen.budget.setPromptText("Enter a valid budget $");
    					condScreen.gardenName.setText("");
    					condScreen.gardenName.setPromptText("Enter Your Garden Name: ");
    				}

    			} else {
    				condScreen.budget.setPromptText("Enter a budget $");
    				condScreen.gardenName.setPromptText("Enter a Garden Name: ");
    			}
    			
    			System.out.println(model.gardenFinal.getBudget());
    		}
    	});

    	//Setting an action for the Clear button
    	condScreen.clear.setOnAction(new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent e) {
    			condScreen.budget.clear();
    			condScreen.budgetLabel.setText(null);
    			condScreen.gardenName.clear();
    			condScreen.gardenLabel.setText(null);
    		}
    	});
    	
    	
    	condScreen.Continue.setOnAction(e-> window.setScene(gardenScene));
    	
    	
		Scene ConditionsScene = new Scene(condScreen.createBorder(),800, 600);
    	
    	
		
		//LOAD SCREEN CODE *********************************************************
		LoadScreen loadScreen = new LoadScreen();
		
		loadScreen.startButton.setOnAction(e-> window.setScene(ConditionsScene));
		
		Scene LoadScreenScene = new Scene(loadScreen.createLoadBorder(),800, 600);
		
		condScreen.Previous.setOnAction(e-> window.setScene(LoadScreenScene));
		
		//**************************************************************************
		
		
		//SAVE SCREEN CODE *********************************************************
		SaveScreen saveScreen = new SaveScreen();
		
		loadScreen.startButton.setOnAction(e-> window.setScene(ConditionsScene));
		
		Scene SaveScreenScene = new Scene(saveScreen.createLoadingBorder(),800, 600);
		
		saveScreen.prevButton.setOnAction(e-> window.setScene(LoadScreenScene));
		
		//**************************************************************************
		
		loadScreen.loadButton.setOnAction(e-> window.setScene(SaveScreenScene));
		
		
		//SAVE SCREEN CODE *********************************************************
		InvScreen invScreen = new InvScreen();
		
		invScreen.PrevButtonInv.setOnAction(e-> window.setScene(gardenScene));
		
		Scene InventoryScene = new Scene(invScreen.createInvBorder(),800, 600);
		
		saveScreen.prevButton.setOnAction(e-> window.setScene(LoadScreenScene));
		
		//**************************************************************************
		
		//POPUP SCREEN CODE *********************************************************
		PopUpWindow popup = new PopUpWindow();	
		// Scene popupScene = new Scene(popup.display(), 250, 500);

//		popup.save.setOnAction(e-> window.setScene(gardenScene));
//		popup.restart.setOnAction(e-> window.setScene(LoadScreenScene));
//		popup.resume.setOnAction(e-> window.setScene(gardenScene));
		
		
		
		// Setting an action for the options button
    	GardenScreenView.optionsButton.setOnAction(new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent e) {
    			Stage popupStage = new Stage();
    	    	popup.restart.setOnAction(new EventHandler<ActionEvent>() {

    	    		@Override
    	    		public void handle(ActionEvent e) {
    	    			popupStage.close();
    	    			window.setScene(LoadScreenScene);
    	    		}
    	    	});
    			popupStage.initModality(Modality.APPLICATION_MODAL);
    			popup.save.setOnAction(k -> popupStage.close());
//    			popup.restart.setOnAction(g -> window.setScene(LoadScreenScene));
//    			popup.restart.setOnAction(k -> popupStage.close());
    			popup.resume.setOnAction(k -> popupStage.close());
    			popupStage.setTitle("Options");
    			popupStage.setMinWidth(250);
    			popupStage.setMinHeight(500);
    			Scene scene = new Scene(popup.display());
    			popupStage.setScene(scene);
    			popupStage.showAndWait();   			
    			
    		}
    	});
    	

		//**************************************************************************
		
		GardenScreenView.inventory.setOnAction(e-> window.setScene(InventoryScene));
    	
		window.setScene(LoadScreenScene);
		window.setTitle("GARDEN BUILDER v 0.01 ~ ALPHA");
		window.show();
    	
    	
	}
	

	
}