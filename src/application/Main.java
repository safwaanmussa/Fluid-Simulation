package application;
	
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	Point2D gridWorldSize = new Point2D(840d, 840d);
	public int gridSizeX;
	public int gridSizeY;
	public float nodeRadius = 10f;
	
	public Node[][] gridArray = null;
	
	
	@Override
	public void start(Stage primaryStage) {
		
		float nodeDiameter = nodeRadius * 2;
		
		gridSizeX = (int) Math.round(gridWorldSize.getX() / nodeDiameter);
		gridSizeY = (int) Math.round(gridWorldSize.getY() / nodeDiameter);

		System.out.println(gridSizeX + gridSizeY);
		
		this.gridArray = new Node[gridSizeX][gridSizeY];
		
		
		try {
			
			GridPane root = new GridPane();
			
			root.setOnDragDetected(
					event -> {
						event.consume();
						root.startFullDrag();
					});
			
			for (int x = 0; x < gridSizeX; x++) {
				for ( int y = 0; y < gridSizeY; y++) {
					
					Particle node = new Particle();
					
					node.setOnMouseClicked(
							event -> {
								if (event.getButton() == MouseButton.PRIMARY) {
									System.out.println("pressed");
									event.consume();
									addDensity(node);
								}
							});
					
					node.setOnMouseDragEntered(
							event -> {
								if (event.getButton() == MouseButton.PRIMARY) {
									event.consume();
									addDensity(node);
								}
							});
					
					node.setStyle(getStyle(Color.BLACK));
					
					node.gridX = x;
					node.gridY = y;
					
					this.gridArray[x][y] = node;
					root.add(node, x, y);
					
				}
			}
			
			
			
			Scene scene = new Scene(root, gridWorldSize.getX(), gridWorldSize.getY(), Color.DARKSLATEGRAY);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	private void addDensity(Particle node) {
		
		for ( int i = -1; i <= 1; i++ ) {
			for (int j = -1; j <= 1; j++ ) {
				if (i == 0 && j == 0) {
					node.setStyle(getStyle(Color.WHITE));
					continue;
					}
				
				int checkX = node.gridX + i;
				int checkY = node.gridY + j;
				
				if (checkX >= 0 && checkX < gridSizeX && checkY >= 0 && checkY < gridSizeX)
					gridArray[checkX][checkY].setStyle(getStyle(Color.PINK));
				
			}
		}
		
	}
	
	private String getStyle(Color color) {
		
		String colorString = color.toString();
		colorString = colorString.replace("0x", "#");
		String style = "-fx-background-color: " + colorString + "; -fx-border-style: solid; -fx-border-width: 1;"
					+ " -fx-border-color: black; -fx-min-width: " + (nodeRadius * 2) + "; -fx-min-height:" + (nodeRadius * 2) + "; -fx-max-width:" + (nodeRadius * 2) + ";"
					+ " -fx-max-height: " + (nodeRadius * 2) + ";";

		return style;
	}

	public static void main(String[] args) {
		
		launch(args);
	}
}
