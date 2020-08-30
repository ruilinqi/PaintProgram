package paint;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * Title: Paint Editor - Group Project
 * @author Ruilin Qi
 */

public class PaintPane extends Pane {


	private boolean drawLine, drawRect, drawOval, drawpen, drawEraser, start;
	private int startx, starty, endx, endy;
	private int thewidth,theheight;
	private boolean draw;
	private Color color;
	private int count;

	private int tempx = startx;
	private int tempy = starty;
	
	public PaintPane(int width, int height) {

		super();
		this.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		this.setBackground(new Background(new BackgroundFill(Color.WHITE,  CornerRadii.EMPTY, Insets.EMPTY)));

		this.setWidth(width);
		this.setHeight(height);
		theheight=height;
		thewidth=width;
		drawRect = false;
		drawOval = false;
		drawLine = false;
		drawpen = false;
		drawEraser = false;
		start = false;
		count = 0;
		startx = 0;
		starty = 0;
		this.draw = true;

		// 1. Begin Drawing
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent e) {
				// TODO Auto-generated method stub
				if (!start) {
					startx = (int) e.getX();
					starty = (int) e.getY();
					if (drawRect) {
						Rectangle r = new Rectangle();
						if (draw) {
							r.setFill(Color.TRANSPARENT);
							r.setStroke(color);
						} else {
							r.setFill(color);
							r.setStroke(color);
						}
						getChildren().add(r);
					} else if (drawOval) {
						Ellipse ellipse = new Ellipse();
						if (draw) {
							ellipse.setFill(Color.TRANSPARENT);
							ellipse.setStroke(color);
						} else {
							ellipse.setFill(color);
							ellipse.setStroke(color);
						}
						getChildren().add(ellipse);
					} else if (drawLine) {
						Line line = new Line();
						line.setStroke(color);
						getChildren().add(line);

					} else if (drawpen) {

						Line line = new Line();
						line.setStroke(color);
						getChildren().add(line);
						tempx = startx;
						tempy = starty;
					} else if (drawEraser) {
						Line line = new Line();
						line.setStroke(Color.WHITE);
						getChildren().add(line);
						tempx = startx;
						tempy = starty;
					
					}
					start = true;
				}
			}

		});

		// 2. While start is true = Draw with origin point + current mouse position
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent e) {
				// TODO Auto-generated method stub
				
				if (start) {
					endx = (int) e.getX();
					endy = (int) e.getY();
					if (endx > 1 && endy > 1&&endx<thewidth &&endy<theheight) {
					
						if (drawRect) {
							Rectangle r = (Rectangle) getChildren().get(getChildren().size() - 1);
							r.setX(startx);
							r.setY(starty);
							r.setWidth(endx - startx);
							r.setHeight(endy - starty);
							if (r.getWidth() < 0) {
								r.setWidth(-r.getWidth());
								r.setX(r.getX() - r.getWidth());
							}
							if (r.getHeight() < 0) {
								r.setHeight(-r.getHeight());
								r.setY(r.getY() - r.getHeight());

							}
						} else if (drawOval) {
							Ellipse ellipse = (Ellipse) getChildren().get(getChildren().size() - 1);
							ellipse.setCenterX((startx + endx) / 2);
							ellipse.setCenterY((starty + endy) / 2);
							ellipse.setRadiusX((endx - startx) / 2);
							ellipse.setRadiusY((endy - starty) / 2);
							if (ellipse.getRadiusX() < 0) {
								ellipse.setRadiusX(-ellipse.getRadiusX());
							}
							if (ellipse.getRadiusY() < 0) {
								ellipse.setRadiusY(-ellipse.getRadiusY());
							}
						} else if (drawLine) {

							Line line = (Line) getChildren().get(getChildren().size() - 1);
							line.setStartX(startx);
							line.setStartY(starty);
							line.setEndX(endx);
							line.setEndY(endy);
						} else if (drawpen) {
							Line line = new Line();
							line.setStroke(color);
							getChildren().add(line);

							line.setStartX(tempx);
							line.setStartY(tempy);
							line.setEndX(endx);
							line.setEndY(endy);
							tempx = endx;
							tempy = endy;
						} else if (drawEraser) {
							Line line = new Line();
							line.setStrokeWidth(8);
							line.setStroke(Color.WHITE);
							getChildren().add(line);
							

							line.setStartX(tempx);
							line.setStartY(tempy);
							line.setEndX(endx);
							line.setEndY(endy);
							tempx = endx;
							tempy = endy;

						}

					}

				}
			}
		});

		// 3. Stop drawing by setting false
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent e) {
				// TODO Auto-generated method stub
				if (start) {
					if (!notChoicen()) {
						count ++;
						Paint.countLabel.setText("Count: "+count);
					}
					start = false;
				}
			}
		});

	}
	
	public int getcount() {
		return count;
	}
	public void resetCount() {
		this.count = 0;
		Paint.countLabel.setText("Count: "+count);
	}

	public void changeDraw(boolean draw) {
		this.draw = draw;
	}

	public void setColor(Color color) {
		
		this.color = color;
	}

	public void back() {
		if (this.getChildren().size() > 0) {
			this.getChildren().remove(this.getChildren().size() - 1);
		}
	}

	public void clear() {
		this.getChildren().clear();
	}
	
	public void setDraw(String string) {
		if(string == "rect") {
			drawRect = true;
			drawOval = false;
			drawLine = false;
			drawpen = false;
			drawEraser=false;
			
		}else if(string == "oval"){
			drawRect = false;
			drawOval = true;
			drawLine = false;
			drawpen = false;
			drawEraser=false;
			
		}else if (string == "line") {
			drawRect = false;
			drawOval = false;
			drawLine = true;
			drawpen = false;
			drawEraser=false;
		}else if(string =="eraser") {
			drawRect = false;
			drawOval = false;
			drawLine = false;
			drawpen = false;
			drawEraser = true;
			
		}else if(string == "pen") {
			drawEraser=false;
			drawRect = false;
			drawOval = false;
			drawLine = false;
			drawpen = true;
			
		}
	}


	public boolean notChoicen() {
		if (	drawRect == false && 
					drawOval == false &&
							drawLine == false &&
								drawpen == false &&
										drawEraser == false) {
			return true;	
		}else {
			return false;
		}
	}

}