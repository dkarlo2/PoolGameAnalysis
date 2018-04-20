package hr.fer.kd.zavrsni.ball_detection.table;

import hr.fer.kd.zavrsni.actions.PGRegularAction;
import hr.fer.kd.zavrsni.ball_detection.ICVPresentation;
import hr.fer.kd.zavrsni.ball_detection.cv.CVKeys;
import hr.fer.kd.zavrsni.ball_detection.cv.PerspectiveTransformer;
import hr.fer.kd.zavrsni.ball_detection.cv.TableDetector;
import hr.fer.kd.zavrsni.ball_detection.table.model.CVTable;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public class TableDetection implements ITableDetection {

	private ICVPresentation presentation;

	private TableDetector tableDetector;

	private CVGetPointsAction actionDone;

	private PGRegularAction actionReset;

	private Mat image;

	private ICVTableDetectedAction action;

	@Override
	public void detectTable(Mat image, ICVTableDetectedAction action, ICVPresentation presentation) {
		this.image = image;
		this.action = action;
		this.presentation = presentation;

		presentation.newPreview("Detekcija stola");
		presentation.setImage(image);

		tableDetector = new TableDetector(image);

		initActions();

		presentation.getInputPoints(tableDetector.getPoints(), actionDone, CVKeys.ENTER);
		presentation.addRegularAction(actionReset, CVKeys.R);
	}

	private void initActions() {
		actionDone = new CVGetPointsAction("Gotov") {

			@Override
			public void actionPerformed(Point[] points) {
				PerspectiveTransformer ptImage =
						new PerspectiveTransformer(image, points);
				PerspectiveTransformer ptImageTable =
						new PerspectiveTransformer(tableDetector.getImageTable(), points);

				Mat im = ptImage.getImage();

				if (presentation.confirmImageDialog("Potvrdite odabir", im, ptImage.getPoints())) {
					action.actionPerformed(new CVTable(
							ptImage.getImage(), ptImageTable.getImage(),
							ptImage.getPoints(), tableDetector.getColor()));
				}
			}

		};

		actionReset = new PGRegularAction("Ponovo odredi obrub stola") {

			@Override
			public void actionPerformed() {
				presentation.setSuggestedPoints(tableDetector.getPoints());
			}
		};
	}

}
