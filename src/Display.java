package com.example.demo;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class to create the Display
 * Edited to add a receipt button, and edited the initialize method.
 */
public class Display extends Application {

    /**
     * DO NOT EDIT
     * Variables relating to the display
     */
    private Pane root;
    private VBox vBox;
    private Button deliveryButton;
    private SimpleStringProperty deliveryButtonText = new SimpleStringProperty("Start Deliveries");
    private VBox deliverableBox = new VBox();

    /**
     * Button for use in Task 8
     */
    private Button receiptButton;

    /**
     * DO NOT EDIT
     * Constants for the display
     */
    private final int WINDOW_SIZE_WIDTH = 1000;
    private final int WINDOW_SIZE_HEIGHT = 1200;
    private final int IMAGE_HW = 30;
    private final int PATH_OFFSET = 15;

    /**
     * DO NOT EDIT
     * Method to launch display and run program
     * @param args from command line
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * DO NOT EDIT
     * Method to set up stage
     * @param primaryStage stage to set up
     */
    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        root.setLayoutX(10);
        root.setLayoutY(10);
        root.setMaxSize(WINDOW_SIZE_WIDTH,WINDOW_SIZE_HEIGHT);

        HBox hBox = new HBox();
        vBox = new VBox();
        vBox.setSpacing(10);

        // Students implement this
        initialize();

        //these are pre-existing methods not to be changed
        addKey(); //key is added to vBox
        addDeliverableList(); //deliverableList is added to vBox


        Image map = new Image("map.png");
        ImageView mapImageView = new ImageView();
        mapImageView.setLayoutX(200);
        mapImageView.setFitHeight(1000);
        mapImageView.setFitWidth(680);
        mapImageView.setImage(map);

        //arrange the display
        hBox.getChildren().add(vBox);
        root.getChildren().add(mapImageView);

        addSortingOffices(); //map of facilities

        root.getChildren().add(hBox);

        //Set up and show scene
        Scene scene = new Scene(root, WINDOW_SIZE_WIDTH, WINDOW_SIZE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Delivery Service");
        primaryStage.show();
    }

    /**
     * DO NOT EDIT
     * Method to add a key to the screen which displays all types of deliverables and a button to show the next delivery.
     */
    private void addKey() {
        Label keyLabel = new Label("Key");
        Image plantImage = new Image("plant.png");
        ImageView plantImageView = new ImageView();
        plantImageView.setFitHeight(IMAGE_HW);
        plantImageView.setFitWidth(IMAGE_HW);
        plantImageView.setImage(plantImage);

        Image produceImage = new Image("produce.png");
        ImageView produceImageView = new ImageView();
        produceImageView.setFitHeight(IMAGE_HW);
        produceImageView.setFitWidth(IMAGE_HW);
        produceImageView.setImage(produceImage);

        Image nonPerishableImage = new Image("nonPerishable.png");
        ImageView nonPerishableImageView = new ImageView();
        nonPerishableImageView.setFitHeight(IMAGE_HW);
        nonPerishableImageView.setFitWidth(IMAGE_HW);
        nonPerishableImageView.setImage(nonPerishableImage);

        Label plantLabel = new Label("Plant");
        Label produceLabel = new Label("Produce");
        Label nonPerishableLabel = new Label("Non-Perishable");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(keyLabel, 1, 1);
        gridPane.add(plantImageView, 1, 2);
        gridPane.add(produceImageView, 1, 3);
        gridPane.add(nonPerishableImageView, 1, 4);
        gridPane.add(plantLabel, 2, 2);
        gridPane.add(produceLabel, 2, 3);
        gridPane.add(nonPerishableLabel, 2, 4);
        vBox.getChildren().add(gridPane);

        // Set up of delivery button.
        deliveryButton  = new Button();
        deliveryButton.textProperty().bind(deliveryButtonText);
        deliveryButton.setOnAction(e->nextDelivery());

        if(Data.getDeliverables()!=null && Data.getDeliverables().isEmpty()){
            updateDeliveryButtonText("No deliverables");
            deliveryButton.setDisable(true);
        }
        vBox.getChildren().add(deliveryButton);

        //students program this method
        addButtonTask(vBox);

        Label deliveryLabel = new Label();
        deliveryLabel.setText("Deliveries");
        vBox.getChildren().add(deliveryLabel);
    }

    /**
     * DO NOT EDIT
     * Method to update the buttonText
     * @param text to update button to
     */
    private void updateDeliveryButtonText(String text) {
        deliveryButtonText.set(text);
    }

    /**
     * DO NOT EDIT
     * This method lists the deliverables in the deliverable stack on the display according to their type.
     */
    private void addDeliverableList() {
        deliverableBox.setSpacing(5.00);
        if (Data.getDeliverables() != null) {
            LinkedList<Deliverable> deliverables = new LinkedList<>(Data.getDeliverables());
            Collections.reverse(deliverables);
            for (Deliverable deliverable : deliverables) {
                deliverableBox.getChildren().add(getImageView(deliverable));

            }
            // Create a ScrollPane and set the VBox as its content
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(deliverableBox);

            // Set the preferred size of the scroll pane
            scrollPane.setPrefSize(150, 500);
            vBox.getChildren().addAll(scrollPane);
        }
    }

    /**
     * DO NOT EDIT
     * This method adds the sorting offices from the database to the display.
     */
    private void addSortingOffices(){
        if(Data.getSortingOffices()!=null) {
            for (SortingOffice sortingOffice : Data.getSortingOffices()) {
                ImageView processingFacilityImageView = getProcessingFacilityImageView(sortingOffice);
                root.getChildren().add(processingFacilityImageView);
                for (SortingOffice neighbour : sortingOffice.getConnections()) {
                    Line path = new Line(sortingOffice.getX() + PATH_OFFSET, sortingOffice.getY() + PATH_OFFSET,
                            neighbour.getX() + PATH_OFFSET, neighbour.getY() + PATH_OFFSET);
                    path.setStroke(Color.RED);
                    root.getChildren().add(path);
                }
            }
        }
    }

    /**
     * Sets the image view based on the sortingOffice and it's values
     * @param sortingOffice to set image for
     * @return Image View with correct image being used
     */
    private ImageView getProcessingFacilityImageView(SortingOffice sortingOffice) {
        Image sortingOfficeImage;
        if(sortingOffice.isInternational()){
            sortingOfficeImage = new Image("sortingOfficeInt.png");
        } else {
            sortingOfficeImage = new Image("sortingOffice.png");
        }
        ImageView processingFacilityImageView = new ImageView();
        processingFacilityImageView.setFitHeight(IMAGE_HW+10);
        processingFacilityImageView.setFitWidth(IMAGE_HW+10);
        processingFacilityImageView.setImage(sortingOfficeImage);
        processingFacilityImageView.setLayoutX(sortingOffice.getX());
        processingFacilityImageView.setLayoutY(sortingOffice.getY());
        return processingFacilityImageView;
    }

    /**
     * DO NOT EDIT
     * This method is called with the delivery button is pressed. It delivers the deliverables from the deliverable
     * stack one by one until complete.
     */
    private void nextDelivery() {
        // Pop the first deliverable and start its delivery
        Deliverable deliverable = Data.getDeliverables().pop();
        deliverableBox.getChildren().remove(0);
        if(Data.getDeliverables().isEmpty()){
            updateDeliveryButtonText("No more deliverables");
            deliveryButton.setDisable(true);
        } else {
            updateDeliveryButtonText("Next delivery");
        }
        if (deliverable != null) {
            // Deliver the deliverable and start animation
            // After animation completes, check for next deliverable in the queue
            deliverDeliverable(deliverable, this::nextDelivery);
        }
    }

    /**
     * DO NOT EDIT
     * Method to create an image view for a deliverable
     * @param deliverable to create an image for
     * @return imageview for the deliverable
     */
    private ImageView getImageView(Deliverable deliverable){
        Image image = null;
        if(deliverable.getClass().getSimpleName().equals("Plant")){
            image = new Image("plant.png");
        } else if(deliverable.getClass().getSimpleName().equals("NonPerishable")) {
            image = new Image("nonPerishable.png");
        } else if (deliverable.getClass().getSimpleName().equals("Produce")){
            image = new Image("produce.png");
        }
        ImageView deliverableImageView = new ImageView();
        deliverableImageView.setFitHeight(IMAGE_HW);
        deliverableImageView.setFitWidth(IMAGE_HW);
        deliverableImageView.setImage(image);
        return deliverableImageView;
    }

    /**
     * DO NOT EDIT
     * This method takes in a deliverable object, ensures the correct image is output to the display, and calls getPath
     * to animate the image along the delivery path.
     * @param deliverable to be delivered on display
     * @param onFinish to track animation
     */
    private void deliverDeliverable(Deliverable deliverable, Runnable onFinish){
        ImageView deliverableImageView = getImageView(deliverable);
        List<SortingOffice> path = getPath(deliverable.getSender(), deliverable.getRecipient());
        if (!path.isEmpty()) {
            // Add ImageView to the root pane
            root.getChildren().add(deliverableImageView);
            for (int i=0; i<path.size()-1;i++) {
                deliverable.process(path.get(i));
                animateImageView(deliverableImageView, path, 0);
            }
            deliverable.process(deliverable.getRecipient());
            if(Data.getProcessedDeliverables()!=null){
                Data.getProcessedDeliverables().push(deliverable);
            }
        }
    }

    /**
     * DO NOT EDIT
     * This method constructs a path from one sorting office to another, to find a route that a deliverable can take.
     * If no path exists then an empty list is returned. This route is not necessarily the shortest.
     * @param start sorting office
     * @param end sorting office
     * @return path as a list
     */
    public static List<SortingOffice> getPath(SortingOffice start, SortingOffice end) {
        ArrayList<SortingOffice> visited = new ArrayList<>();
        Stack<SortingOffice> stack = new Stack<>();
        Map<SortingOffice, SortingOffice> parentMap = new HashMap<>();

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            SortingOffice current = stack.pop();
            if (current == end) {
                List<SortingOffice> path = new ArrayList<>();
                while (end != start) {
                    path.add(end);
                    end = parentMap.get(end);
                }
                path.add(start); // Add start facility
                Collections.reverse(path);
                return path;
            }
            for (SortingOffice connection : current.getConnections()) {
                if (!visited.contains(connection)) {
                    stack.push(connection);
                    visited.add(connection);
                    parentMap.put(connection, current);
                }
            }
        }
        return Collections.emptyList(); // No path found
    }

    /**
     * DO NOT EDIT
     * This method is called recursively with an imageView and a path. An animation is then played until the imageView
     * has travelled along the entire path (using animateTransition method).
     * The index is used to track how many pairs of transitions there are in the path upon which the image can travel.
     * @param imageView the image to animate
     * @param path the path to travel along
     * @param index the index, to keep track of the pairs to travel from/to
     */
    private void animateImageView(ImageView imageView, List<SortingOffice> path, int index) {
        if (index < path.size() - 1) {
            SortingOffice currentFacility = path.get(index);
            SortingOffice nextFacility = path.get(index + 1);

            // Animate the transition from current facility to next facility
            animateTransition(imageView, currentFacility.getX(), currentFacility.getY(),
                    nextFacility.getX(), nextFacility.getY(), () -> {
                        // Recursively call animateImageView to animate the next segment
                        animateImageView(imageView, path, index + 1);
                    });
        } else {
            // Animation completed for the entire path
            root.getChildren().remove(imageView);
        }
    }

    /**
     * DO NOT EDIT
     * This animates the transition along a path.
     * @param imageView the image to animate
     * @param startX the X coord starting point of the animation
     * @param startY the Y coord starting point of the animation
     * @param endX the X coord ending point of the animation
     * @param endY the Y coord starting point of the animation
     * @param onFinished triggers the next animation if there is a longer path than 1 pair.
     */
    private void animateTransition(ImageView imageView, double startX, double startY,
                                   double endX, double endY, Runnable onFinished) {
        // Create TranslateTransition for the current segment
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(imageView);
        translateTransition.setDuration(Duration.seconds(3)); // Adjust duration as needed
        translateTransition.setFromX(startX);
        translateTransition.setFromY(startY);
        translateTransition.setToX(endX);
        translateTransition.setToY(endY);
        if (onFinished != null) {
            // Set onFinished event to trigger the next animation
            translateTransition.setOnFinished(event -> onFinished.run());
        }
        // Play the animation
        translateTransition.play();
    }

    /**
     * DO NOT EDIT ANY CODE ABOVE THIS COMMENT. There are some methods below which you need to complete.
     */

    /**
     * Method required for display to run.
     * Implements various methods from Data class.
     * Handles FileNotFound exception.
     */
    private void initialize() {
        try {
            Data.readSortingOffices();
            Data.readDeliverables();
            Data.addConnections();
        } catch (FileNotFoundException fnfe) {
            System.out.println("File hasn't been found");
        }

    }

    /**
     * Method required for display to run.
     * Adds and initializes the receipt button, which calls printReceipt method from the Data class.
     *
     * @param vBox to add button to.
     */
    private void addButtonTask(VBox vBox) {
        //Text displayed by the button
        SimpleStringProperty receiptButtonText = new SimpleStringProperty("Print receipts");

        receiptButton  = new Button();
        receiptButton.textProperty().bind(receiptButtonText);
        //Calls printReceipts method when clicked.
        receiptButton.setOnAction(e -> Data.printReceipts());

        //When all deliverables are processed, button can't be clicked.
        if (Data.getDeliverables() != null && Data.getDeliverables().isEmpty()) {
            receiptButtonText.set(("No receipts to print"));
            receiptButton.setDisable(true);
        }
        vBox.getChildren().add(receiptButton);
    }

}
