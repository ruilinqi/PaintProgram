package paint;

import java.awt.TextField;
import java.util.concurrent.CountDownLatch;

import com.sun.xml.internal.ws.org.objectweb.asm.Label;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Title: Paint Editor - Group Project
 * @author Ruilin Qi
 */
public class Paint extends Application {
	


	//UI Elements
    private Button clear,back,quit,rect,oval,line,pen,eraser;
    public static javafx.scene.control.Label countLabel;
  
    private ComboBox<String> typeSelector;
 
    private String[] types;

    private PaintPane paintPane;
    private FlowPane controlPane;
    private FlowPane colorPane;
    private ColorPicker cp;

    @Override
    public void start(Stage primaryStage) {

    	//Setup 
        paintPane = new PaintPane(950, 550);
        
        controlPane = new FlowPane();
        controlPane.setMaxWidth(100);
        controlPane.setMaxHeight(600);
        controlPane.setVgap(20);
        controlPane.setAlignment(Pos.TOP_CENTER);
        
        colorPane = new FlowPane();
        colorPane.setMaxWidth(900);
        colorPane.setMaxHeight(50);
        colorPane.setHgap(5);
        colorPane.setPadding(new Insets(5, 5, 5, 5));

   
        Image imageRect = new Image(getClass().getResourceAsStream("rectangle.jpg"));
        rect = new Button("", new ImageView(imageRect));
        Image imageOval = new Image(getClass().getResourceAsStream("oval.png"));
        oval = new Button("", new ImageView(imageOval));
        Image imageLine = new Image(getClass().getResourceAsStream("line.png"));
        line = new Button("", new ImageView(imageLine));
        Image imagePen = new Image(getClass().getResourceAsStream("pen.jpg"));
        pen = new Button("", new ImageView(imagePen));
        Image imageEraser = new Image(getClass().getResourceAsStream("eraser.jpg"));
        eraser = new Button("", new ImageView(imageEraser));
        
        clear = new Button("Clear");
        back = new Button("Undo");
        quit = new Button("QUIT");
       
        countLabel = new javafx.scene.control.Label("Count: "+ paintPane.getcount());
        countLabel.setFont(new Font("Arial", 30));
        

 
        rect.setOnAction(buttonHandler);
        oval.setOnAction(buttonHandler);
        line.setOnAction(buttonHandler);
        back.setOnAction(buttonHandler);
        clear.setOnAction(buttonHandler);
        quit.setOnAction(buttonHandler);
        pen.setOnAction(buttonHandler);
        eraser.setOnAction(buttonHandler);

        cp = new ColorPicker(Color.BLACK);
        cp.setOnAction(colHandler);
        
        
        
        
        
        
        types = new String[]{"Draw", "Fill"};

        // --- Type Selector --- //
        typeSelector = new ComboBox<>();
        typeSelector.getItems().addAll(types);

        typeSelector.setValue(types[0]);
        typeSelector.setOnAction(typeHandler);
        

        

        colorPane.getChildren().addAll(typeSelector, cp,rect, oval, line ,pen,eraser,back, clear, quit);
        colorPane.getChildren().add(countLabel);
        
        
        
        BorderPane root = new BorderPane();
        paintPane.changeDraw(true);
        paintPane.setColor(cp.getValue());
        
        root.setCenter(paintPane);
        root.setTop(colorPane);
        rect.setStyle("outline:none; ");
        oval.setStyle("outline:none; ");
        line.setStyle("outline:none; ");
        pen.setStyle("outline:none; ");
        eraser.setStyle("outline:none; ");
  

        Scene scene = new Scene(root, 950, 600);
        
        
        
        primaryStage.setTitle("Paint Program");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    


    EventHandler<ActionEvent> typeHandler = new EventHandler<ActionEvent>(){
    	@Override
    	public void handle(ActionEvent e){
    	
    			if(typeSelector.getValue() == "Draw"){
    				paintPane.changeDraw(true);	
    			}else{
    				paintPane.changeDraw(false);
    			}
    	
    	}
    };
    

    EventHandler<ActionEvent> colHandler = new EventHandler<ActionEvent>(){
    	@Override
    	public void handle(ActionEvent e){
    		paintPane.setColor(cp.getValue());
    	}
    };


    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent e){
            if(e.getSource() == clear){
                paintPane.clear();
                paintPane.resetCount();
            }else if(e.getSource() == back){
            	
            	
                paintPane.back();
            }else if(e.getSource() == quit){
            	
                System.exit(0);
            }else if(e.getSource() == rect){
            	paintPane.setDraw("rect");
            }else if(e.getSource() == oval){
            	paintPane.setDraw("oval");

            }else if(e.getSource() == line){
            	paintPane.setDraw("line");

            }else if(e.getSource()==pen) {
            	paintPane.setDraw("pen");

            }else if(e.getSource()==eraser) {
            	paintPane.setDraw("eraser");

            }
        }
    };
    
    

    public static void main(String[] args){
        launch(args);
    }

}
