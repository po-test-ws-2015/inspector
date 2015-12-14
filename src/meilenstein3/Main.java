package meilenstein3;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import io.appium.java_client.AppiumDriver;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Startklasse fuer die Applikation, beinhaltet wesentliche Bestandteile für die Gui
 * @author Jakob Andersen
 *
 */
public class Main extends Application {

	private static AppiumDriver driver;

	private ListView<String> listLeft;
	private Rectangle lea;
	private WebElementInformationList cl;
	private CodeCreator cc;


	private static double w;
	private static double h;

	private ImageViewPane leftLog2;

	public static void main(String[] args) {
		driver = SetUp.create();
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		GridPane grid = new GridPane();
		
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(80);
		
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(20);
		
		grid.getColumnConstraints().addAll(column1, column2);
		
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(70);
		
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(30);
		
		grid.getRowConstraints().addAll(row1, row2);
		
		VBox topLeft = new VBox(10);
		VBox topRight = new VBox(10);
		VBox bottom = new VBox(10);
		
		// Top left
		listLeft = new ListView<String>();
		fillList(listLeft);
		topLeft.getChildren().add(listLeft);
		listLeft.prefHeightProperty().bind(topLeft.heightProperty());
		grid.add(topLeft, 0, 0);
		
		// Top right
		Button exportButton = new Button("Export View as CTA");
		
		exportButton.setOnMouseClicked(e -> {
			System.out.println("CTA Export started.");
			try {
				FileChooser fileChooser = new FileChooser();
				
				fileChooser.setTitle("Select save location");
				ExtensionFilter ctaExtension = new ExtensionFilter("CTA Files (*.cta)", "*.cta");
				fileChooser.getExtensionFilters().add(ctaExtension);
				fileChooser.setSelectedExtensionFilter(ctaExtension);
				
				File saveLocation = fileChooser.showSaveDialog(primaryStage);
				
				if(saveLocation != null) {
					Document document = ClassificationTreeGenerator.generate(cl);
					
					System.out.println("XML Document created.");
					
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(document);
					StreamResult result = new StreamResult(saveLocation);
					transformer.transform(source, result);
					
					System.out.println("XML Document saved.");
				} else {
					System.out.println("User cancelled CTA export.");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		topRight.getChildren().add(exportButton);
		
		topRight.setAlignment(Pos.BOTTOM_CENTER);
		
		grid.add(topRight, 1, 0);
		
		// Bottom
		StackPane bottomLabels = new StackPane();
		
		TextArea codeTextArea = new TextArea();
		this.cc = new CodeCreator(codeTextArea);
		bottomLabels.getChildren().add(codeTextArea);

		codeTextArea.prefHeightProperty().bind(bottom.heightProperty());
	
		bottom.getChildren().addAll(new Label("Test Editor Code"), bottomLabels);

		grid.add(bottom, 0, 1, 2, 1);
		
		openSecWindow(primaryStage); 

		primaryStage.setOnCloseRequest(arg0 -> {
			System.out.println("App close requested...");
			System.out.println("Quitting driver...");
			driver.quit();
			System.out.println("Driver quit.");
			System.out.println("Bye!");
			System.exit(1);
		});
		primaryStage.setScene(new Scene(grid, 800, 600));
		primaryStage.setTitle("Appium Inspector++ v2.0");
		primaryStage.show();
	}

	/**
	 * Sekundaerfenster - Aktuelle stand der Applikation
	 * @param primaryStage
	 */
	private void openSecWindow(Stage primaryStage) {
		Stage myDialog = new Stage();
		myDialog.setX(primaryStage.getX() + primaryStage.getWidth());

		Button resetButton = new Button("Reset");

		Image im = WebDriverUtility.takeScreenshot(driver);
		ImageView image2 = new ImageView(im);

		h = im.getHeight();
		w = im.getWidth();

		leftLog2 = new ImageViewPane(image2);
		leftLog2.mach().add(resetButton);

		Scene scene = new Scene(leftLog2);
		lea = new Rectangle();

		resetButton.setOnMouseClicked(e -> {
			WebDriverUtility.resetApp(driver);
			fillList(listLeft);
			Image image = WebDriverUtility.takeScreenshotInst(driver);
			image2.setImage(image);
		});

		leftLog2.widthProperty().addListener(
				(ChangeListener<Number>) (observableValue, oldSceneWidth,
						newSceneWidth) -> {
							double f = newSceneWidth.doubleValue() / w;

							lea.setWidth(lea.getWidth() * f);
							lea.setX(lea.getX() * f);
						});

		leftLog2.heightProperty().addListener(
				(ChangeListener<Number>) (observableValue, oldSceneHeight,
						newSceneHeight) -> {
							double f = newSceneHeight.doubleValue() / h;
							lea.setHeight(lea.getHeight() * f);
							lea.setY(lea.getY() * f);
						});

		listLeft.getSelectionModel()
		.selectedItemProperty()
		.addListener((ChangeListener<String>) (arg0, old, newEl) -> {

			if(leftLog2.mach().size() > 2)
				for (int i = 2; i < leftLog2.mach().size(); i++) {
					leftLog2.mach().remove(i);
				}

			if (newEl != null) {

				Rectangle ea = null;
				if (newEl.contains("Button")) {
					ea = cl.getRactangel(newEl, Color.RED);
				} else if (newEl.contains("Field")) {
					ea = cl.getRactangel(newEl, Color.GREEN);
				} else {
					ea = cl.getRactangel(newEl, Color.BLUE);
				}

				lea = new Rectangle();
				if (leftLog2.mach().size() > 2) {
					leftLog2.mach().remove(2);
				}
				lea = ea;
				leftLog2.mach().add(ea);

				lea.setOnMouseClicked(event -> {

					Pane g = new Pane();
					leftLog2.mach().add(g);

					Button b1 = new Button("click");
					Button b2 = new Button("send text");
					TextField b3 = new TextField();

					g.getChildren().addAll(b1, b2, b3);

					g.getChildren().get(0).resizeRelocate(lea.getX(), lea.getY(), 10, 20);
					g.getChildren().get(1).resizeRelocate(lea.getX() + 50, lea.getY(), 10, 20);
					g.getChildren().get(2).resizeRelocate(lea.getX(), lea.getY(), 10, 10);

					b3.setVisible(false);

					b1.setOnAction(e -> {
						WebDriverUtility.clickOn(newEl, driver);
						image2.setImage(WebDriverUtility .takeScreenshot(driver));
						cc.addClick(newEl);
						fillList(listLeft);
						listLeft.selectionModelProperty().get().clearSelection();
						leftLog2.mach().removeAll(b1, b2, b3);
						leftLog2.mach().removeAll(g);

					});

					b2.setOnAction(ee -> {
						b3.setVisible(true);
						b1.setVisible(false);
						b2.setVisible(false);
					});

					b3.setOnAction(e -> {
						cc.addSend(newEl, b3.getText());
						leftLog2.mach().removeAll(b1, b2, b3);
						leftLog2.mach().removeAll(g);
						WebDriverUtility.sendTo(newEl, driver, b3.getText());
						image2.setImage(WebDriverUtility .takeScreenshot(driver));
						fillList(listLeft);
					});
				});
			}
		});

		myDialog.setScene(scene);
		myDialog.show();

	}

	/**
	 * Liste der Webelemente wird neu am Stand des Emulatores aktualisiert. 
	 * @param listLeft
	 */
	private void fillList(ListView<String> listLeft) {
		cl = new WebElementInformationList(WebDriverUtility.getAllWebElements(driver));
		ObservableList<String> ol = FXCollections.observableList(cl.getStringList());
		listLeft.setItems(ol);
	}

}
