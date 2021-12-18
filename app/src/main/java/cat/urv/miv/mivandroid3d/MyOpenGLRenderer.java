package cat.urv.miv.mivandroid3d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;


public class MyOpenGLRenderer implements Renderer {

	private Context context;
	private Object3D sphere;
	private Light l1, l2, l3;

	// P4
	private GL10 gl;

	public MyOpenGLRenderer(Context context){
		this.context = context;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		this.gl = gl;

		// Image Background color
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

		//Enable Depth Testing
		gl.glEnable(GL10.GL_DEPTH_TEST);

		//Enable Smooth Shading
		gl.glShadeModel(GL10.GL_FLAT);

		//Enable Lights
		gl.glEnable(GL10.GL_LIGHTING);

        sphere = new Object3D(context, R.raw.earth);

        l1 = new Light(gl, GL10.GL_LIGHT0);
        l1.setPosition(new float[]{5.0f,0.0f,0.0f,1.0f});
        l1.setAmbientColor(new float[]{0.2f,0.2f,0.2f});
        l1.setDiffuseColor(new float[]{1.0f,0.0f,0.0f});
        l1.enable();

		// Camera
		CameraManager.start(gl);

		// Start functionality switcher
		//StateManager.start(this, PS);

	}

	public void onDrawFrame(GL10 gl) {

        // Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glLoadIdentity();

		// Camara set up
		CameraManager.look();

		//Draw the sphere
		gl.glTranslatef(0,0,-6.0f);
		sphere.draw(gl);
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
	}

}
