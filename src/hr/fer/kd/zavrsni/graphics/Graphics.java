package hr.fer.kd.zavrsni.graphics;

import hr.fer.kd.zavrsni.actions.PGRegularAction;
import hr.fer.kd.zavrsni.graphics.balls.GBallSet;
import hr.fer.kd.zavrsni.graphics.keyboard.GKeys;
import hr.fer.kd.zavrsni.graphics.painter.SimplePainter;
import hr.fer.kd.zavrsni.graphics.picker.IPickerListener;
import hr.fer.kd.zavrsni.graphics.picker.Picker;
import hr.fer.kd.zavrsni.graphics.pockets.GPocketSet;
import hr.fer.kd.zavrsni.graphics.shotsimulator.ShotSimulator;
import hr.fer.kd.zavrsni.graphics.table.GTable;
import hr.fer.kd.zavrsni.model.balls.Ball;
import hr.fer.kd.zavrsni.model.table.Table;

import java.util.List;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class Graphics {

	private static final BoundingSphere LIGHT_BOUNDS = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	private static final Color3f AMBIENT_COLOR = new Color3f(0.4f, 0.4f, 0.4f);
	private static final Color3f DIRECT_COLOR = new Color3f(0.9f, 0.9f, 0.9f);
	private static final Vector3f DIRECT_DIRECTION = new Vector3f(4.0f, -7.0f, -12.0f);

	private static final double GRAPHICS_TABLE_LENGTH = 3;

	private static final double FRONT_CLIPPING_DISTANCE = 0.001;

	private Canvas3D canvas;
	private SimpleUniverse universe;
	private BranchGroup contents;
	private TransformGroup transformGroup;

	private WorldTransform worldTransform;

	private GBallSet gballSet;
	private GPocketSet gpocketSet;
	private GTable gtable;

	private Picker picker;

	private IUndo undo;

	private IGraphicsPresentation presentation;

	public void drawWorld(List<Ball> balls, Table table, IPickerListener listener, IUndo undo,
			IGraphicsPresentation presentation) {
		this.presentation = presentation;
		this.undo = undo;

		presentation.newPreview("Odabir udarca");

		GraphicsUtil.setGF(GRAPHICS_TABLE_LENGTH / table.LENGTH);

		canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		universe = new SimpleUniverse(canvas);

		View v = universe.getViewer().getView();
		v.setFrontClipDistance(FRONT_CLIPPING_DISTANCE);

		transformGroup = new TransformGroup();

		gballSet = new GBallSet(balls);
		gpocketSet = new GPocketSet(table);
		gtable = new GTable(table);

		picker = new Picker(listener, canvas, gballSet, gtable.getPlate(), gpocketSet,
				table.BALL_RADIUS * GraphicsUtil.getGF(), presentation.getISpeedSupply());

		ShotSimulator.init(gballSet);

		initContents();

		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(contents);

		initGUI();
	}

	private void initGUI() {
		presentation.setCanvas(canvas);

		PGRegularAction action = new PGRegularAction("Pogled") {

			@Override
			public void actionPerformed() {
				worldTransform.changeTransform();
			}
		};
		presentation.addRegularAction(action, GKeys.V);

		picker.registerListeners(canvas);

		action = new PGRegularAction("Izbrisi linije") {

			@Override
			public void actionPerformed() {
				SimplePainter.painter.removeShapes();
			}
		};
		presentation.addRegularAction(action, GKeys.ESCAPE);

		action = new PGRegularAction("Prethodna situacija") {

			@Override
			public void actionPerformed() {
				undo.undo();
			}
		};
		presentation.addRegularAction(action, GKeys.CTRL_Z);
	}

	private void initContents() {
		contents = new BranchGroup();

		Background background = new Background(new Color3f(1,1,1));
		BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 1000);
		background.setApplicationBounds(sphere);
		contents.addChild(background);

		contents.addChild(transformGroup);
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		transformGroup.addChild(gtable);
		transformGroup.addChild(gballSet);
		transformGroup.addChild(gpocketSet);
		transformGroup.addChild(SimplePainter.painter);
		transformGroup.addChild(ShotSimulator.getSimulator());

		initLighting();

		worldTransform = new WorldTransform(transformGroup, canvas, picker);
		worldTransform.transformScene(1);
		worldTransform.changeTransform();
	}

	private void initLighting() {
		AmbientLight ambient = new AmbientLight(AMBIENT_COLOR);
		ambient.setInfluencingBounds(LIGHT_BOUNDS);
		transformGroup.addChild(ambient);

		DirectionalLight direct = new DirectionalLight(DIRECT_COLOR, DIRECT_DIRECTION);
		direct.setInfluencingBounds(LIGHT_BOUNDS);
		transformGroup.addChild(direct);
	}

}
