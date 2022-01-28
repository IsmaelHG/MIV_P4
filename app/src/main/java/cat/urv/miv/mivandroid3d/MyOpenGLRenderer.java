package cat.urv.miv.mivandroid3d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class MyOpenGLRenderer implements Renderer {

	private Context context;
	private Object3D car, cube, streetlight, ground;
	private Light l0, l1;
	private SkyBox mySkyBox;

	public MyOpenGLRenderer(Context context){
		this.context = context;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Image Background color
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

		gl.glEnable(GL11.GL_TEXTURE_2D);

		//Enable Depth Testing
		gl.glEnable(GL10.GL_DEPTH_TEST);

		//Enable Smooth Shading
		gl.glShadeModel(GL10.GL_SMOOTH);

		//Enable Lights
		gl.glEnable(GL10.GL_LIGHTING);

		// Coche
        car = new Object3D(context, R.raw.car);

        // Cube
		cube = new Object3D(context, R.raw.cube);

		// Street Light
		streetlight = new Object3D(context, R.raw.streetlight);

		// Ground
		ground = new Object3D(context, R.raw.plane);
		//ground.setTexture(gl,context,R.drawable.rock_wall_n7_s_t6_k20_c_256x256);

		// test
		//earth = new Object3D(context, R.raw.earth);

		// Primera luz (simula la iluminacion de una farola)
		l0 = new Light(gl, GL10.GL_LIGHT0);
		l0.setPosition(new float[]{0.0f,0.0f,0.0f,1.0f});
		l0.setAmbientColor(new float[]{1.0f,1.0f,1.0f});
		l0.setDiffuseColor(new float[]{1.0f,1.0f,1.0f});
		l0.setSpotLight(new float[]{0.0f,-1.0f,0.0f}, 35,5.0f,2);
		l0.enable();

		// Segunda luz (ilumina con color rojo un cubo)
		l1 = new Light(gl, GL10.GL_LIGHT1);
		l1.setPosition(new float[]{0.0f,0.0f,0.0f,1.0f});
		l1.setAmbientColor(new float[]{1.0f,0.0f,0.0f});
		l1.setDiffuseColor(new float[]{1.0f,0.0f,0.0f});
		l1.setSpotLight(new float[]{0.0f,-1.0f,0.0f}, 25,5.0f,2);
		l1.enable();

		// Definimos la luz ambiental (50%)
		gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(new float[]{0.5f,0.5f,0.5f}));

		// Niebla
		FloatBuffer fog_color;
		ByteBuffer auxiliary;

		gl.glFogf(GL10.GL_FOG_MODE, GL10.GL_LINEAR);
		auxiliary = ByteBuffer.allocateDirect(4*4);
		auxiliary.order(ByteOrder.nativeOrder());
		fog_color = auxiliary.asFloatBuffer();
		fog_color.put(new float[] {0.2f, 0.2f, 0.2f, 1.0f});
		fog_color.position(0);
		gl.glFogfv(GL10.GL_FOG_COLOR, fog_color);
		gl.glFogf(GL10.GL_FOG_DENSITY, 0.01f);
		gl.glFogf(GL10.GL_FOG_START, 10f);
		gl.glFogf(GL10.GL_FOG_END, 100f);
		gl.glEnable(GL10.GL_FOG);


		// Inicializamos el sistema de camaras
		CameraManager.start(gl);

		// Ajustamos la posicion de las dos camaras
		CameraManager.setCamPosition(new Vertex4(0.0f, 2.0f, 0.0f, 1.0f),
				new Vertex4(0.0f, 0.0f, -1.0f, 0.0f),
				new Vertex4(0.0f, 1.0f, 0.0f, 0.0f),
				new Vertex4(1.0f, 0.0f, 0.0f, 0.0f),
				0);

		CameraManager.setCamPosition(new Vertex4(0.0f, 2.0f, 0.0f, 1.0f),
				new Vertex4(0.0f, 0.0f, -1.0f, 0.0f),
				new Vertex4(0.0f, 1.0f, 0.0f, 0.0f),
				new Vertex4(1.0f, 0.0f, 0.0f, 0.0f),
				1);

		// Creamos el skybox
		prepare_skybox(gl, context);

	}

	public void prepare_skybox(GL10 gl, Context context){
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

		// Cargamos la matriz de indentidad
		gl.glLoadIdentity();

		// La segunda camara se mueve constantemente
		CameraManager.moving_camera(1);

		// gluLookAt con las coordenadas de la camara actual
		// Estas coordenadas se guardan
		Vertex4[] cam_coords = CameraManager.look();

		// Dibujamos el skybox
		gl.glPushMatrix();
		gl.glScalef(80, 80, 80);
		mySkyBox.drawSkybox(gl);
		// Para que se puedan mostrar las texturas 2D
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glPopMatrix();

		// Las luces en openGL se mueven con el ModelView por lo que corregimos su posicion
		// para que se queden "estacionadas"
		l0.setPosition(new float[]{0.12f-cam_coords[0].get(0),3.7f-cam_coords[0].get(1),-10.7f-cam_coords[0].get(2),1.0f});
		l1.setPosition(new float[]{1.7f-cam_coords[0].get(0),1.7f-cam_coords[0].get(1),-10f-cam_coords[0].get(2),1.0f});

		// Dibujamos el coche
		gl.glPushMatrix();
		gl.glTranslatef(0,-0.7f,-10.0f);
		car.draw(gl);
		gl.glPopMatrix();

		// Dibujamos la farola
		gl.glPushMatrix();
		gl.glScalef(0.3f,0.3f,0.3f);
		gl.glTranslatef(-7.0f,-2.3f,-33.3f);
		streetlight.draw(gl);
		gl.glPopMatrix();

		// Dibujamos el cubo
		gl.glPushMatrix();
		gl.glTranslatef(1.5f,-0.28f,-9.0f);
		gl.glScalef(0.3f,0.3f,0.3f);
		cube.draw(gl);
		gl.glPopMatrix();

		// Dibujamos el suelo
		gl.glPushMatrix();
		gl.glTranslatef(0.0f,-0.6f,-10.0f);
		ground.draw(gl);
		gl.glPopMatrix();

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
