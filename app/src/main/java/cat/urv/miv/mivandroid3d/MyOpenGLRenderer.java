package cat.urv.miv.mivandroid3d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import java.nio.FloatBuffer;


public class MyOpenGLRenderer implements Renderer {

	private Context context;
	private Object3D car, cube, cube2, streetlight, earth, ground;
	private Light l0, l1, l2;
	private SkyBox mySkyBox;

	// P4
	private GL10 gl;

	public MyOpenGLRenderer(Context context){
		this.context = context;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		this.gl = gl;

		// Image Background color
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

		gl.glEnable(GL11.GL_TEXTURE_2D);

		//Enable Depth Testing
		gl.glEnable(GL10.GL_DEPTH_TEST);

		//Enable Smooth Shading
		gl.glShadeModel(GL10.GL_SMOOTH);

		//Enable Lights
		gl.glEnable(GL10.GL_LIGHTING);

		// Monke
        car = new Object3D(context, R.raw.car);

        // Cube
		cube = new Object3D(context, R.raw.cube);

		// Cube2
		cube2 = new Object3D(context, R.raw.cube);

		// Street Light
		streetlight = new Object3D(context, R.raw.streetlight);

		// Ground
		ground = new Object3D(context, R.raw.plane);
		//ground.setTexture(gl,context,R.drawable.rock_wall_n7_s_t6_k20_c_256x256);

		// test
		//earth = new Object3D(context, R.raw.earth);

		// Light 0
		l0 = new Light(gl, GL10.GL_LIGHT0);
		l0.setPosition(new float[]{0.0f,0.0f,0.0f,1.0f});
		l0.setAmbientColor(new float[]{1.0f,1.0f,1.0f});
		l0.setDiffuseColor(new float[]{1.0f,1.0f,1.0f});
		gl.glLightfv(GL10.GL_LIGHT0,GL10.GL_SPOT_DIRECTION, FloatBuffer.wrap(new float[]{0.0f,-1.0f,0.0f}));
		gl.glLightf(GL10.GL_LIGHT0,GL10.GL_SPOT_CUTOFF,35);
		gl.glLightf(GL10.GL_LIGHT0,GL10.GL_SPOT_EXPONENT,5.0f);
		gl.glLightf(GL10.GL_LIGHT0,GL10.GL_LINEAR_ATTENUATION, 2);
		l0.enable();

		// Light 0
		l1 = new Light(gl, GL10.GL_LIGHT1);
		l1.setPosition(new float[]{0.0f,0.0f,0.0f,1.0f});
		l1.setAmbientColor(new float[]{1.0f,0.0f,0.0f});
		l1.setDiffuseColor(new float[]{1.0f,0.0f,0.0f});
		gl.glLightfv(GL10.GL_LIGHT1,GL10.GL_SPOT_DIRECTION, FloatBuffer.wrap(new float[]{0.0f,-1.0f,0.0f}));
		gl.glLightf(GL10.GL_LIGHT1,GL10.GL_SPOT_CUTOFF,25);
		gl.glLightf(GL10.GL_LIGHT1,GL10.GL_SPOT_EXPONENT,5.0f);
		gl.glLightf(GL10.GL_LIGHT1,GL10.GL_LINEAR_ATTENUATION, 2);
		l1.enable();

		// Camera
		CameraManager.start(gl);
		// Set second camera
		/**
		CameraManager.setCamPosition(new Vertex4(7.2f, 4.7f, -12.3f, 1.0f),
				new Vertex4(-0.75f, -0.2f, 0.62f, 0.0f),
				new Vertex4(-0.15f, 0.9f, 0.13f, 0.0f),
				new Vertex4(-0.65f, 0.0f, -0.75f, 0.0f),
				1);
		**/

		CameraManager.setCamPosition(new Vertex4(0.0f, 2.0f, 0.0f, 1.0f),
				new Vertex4(0.0f, 0.0f, -1.0f, 0.0f),
				new Vertex4(0.0f, 1.0f, 0.0f, 0.0f),
				new Vertex4(1.0f, 0.0f, 0.0f, 0.0f),
				1);

		CameraManager.setCamPosition(new Vertex4(0.0f, 2.0f, 0.0f, 1.0f),
				new Vertex4(0.0f, 0.0f, -1.0f, 0.0f),
				new Vertex4(0.0f, 1.0f, 0.0f, 0.0f),
				new Vertex4(1.0f, 0.0f, 0.0f, 0.0f),
				0);

		// Ambient Light
		gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(new float[]{0.5f,0.5f,0.5f}));

		// Start functionality switcher
		//StateManager.start(this, PS);
		prepare_skybox((GL11) gl, context);

	}

	public void prepare_skybox(GL11 gl, Context context){
		int[] skybox_textures = new int[6];
		skybox_textures[0] = R.drawable.posx;
		skybox_textures[1] = R.drawable.negx;
		skybox_textures[2] = R.drawable.posy;
		skybox_textures[3] = R.drawable.negy;
		skybox_textures[4] = R.drawable.posz;
		skybox_textures[5] = R.drawable.negz;
		mySkyBox = new SkyBox();
		mySkyBox.load_skybox(gl, context, skybox_textures);

	}

	public void onDrawFrame(GL10 gl) {

        // Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glLoadIdentity();

		// Camera
		CameraManager.moving_camera(1);
		Vertex4[] center = CameraManager.look();

		gl.glPushMatrix();
		gl.glScalef(80, 80, 80);
		mySkyBox.drawSkybox(gl);
		gl.glPopMatrix();

		// Lights
		l0.setPosition(new float[]{0.12f-center[0].get(0),3.7f-center[0].get(1),-10.7f-center[0].get(2),1.0f});
		l1.setPosition(new float[]{1.7f-center[0].get(0),1.7f-center[0].get(1),-10f-center[0].get(2),1.0f});


		//Draw the car
		gl.glPushMatrix();
		gl.glTranslatef(0,-0.7f,-10.0f);
		car.draw(gl);
		gl.glPopMatrix();

		// Draw the light
		gl.glPushMatrix();
		gl.glScalef(0.3f,0.3f,0.3f);
		gl.glTranslatef(-7.0f,-2.3f,-33.3f);
		streetlight.draw(gl);
		gl.glPopMatrix();

		//Draw Cube 1
		gl.glPushMatrix();
		gl.glTranslatef(1.5f,-0.28f,-9.0f);
		gl.glScalef(0.3f,0.3f,0.3f);
		cube.draw(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef(0.0f,-0.6f,-10.0f);
		ground.draw(gl);
		gl.glPopMatrix();

		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Define the Viewport
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 60.0f, (float) width / (float) height, 0.1f, 100.0f);

		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable( GL10.GL_BLEND );
		gl.glEnable( GL10.GL_BLEND );
	}

}
