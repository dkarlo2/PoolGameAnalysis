package hr.fer.kd.zavrsni.ball_detection;

import hr.fer.kd.zavrsni.actions.PGRegularAction;
import hr.fer.kd.zavrsni.ball_detection.cv.CVKeys;
import hr.fer.kd.zavrsni.ball_detection.table.CVGetPointsAction;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public interface ICVPresentation {

	void newPreview(String name);

	void setImage(Mat image);

	void getInputPoints(Point[] suggestedPoints, CVGetPointsAction action, CVKeys key);

	void setSuggestedPoints(Point[] suggestedPoints);

	void addRegularAction(PGRegularAction action, CVKeys key);

	boolean confirmImageDialog(String message, Mat image, Point[] points);

	void drawTableOutline(Point[] points);

}
