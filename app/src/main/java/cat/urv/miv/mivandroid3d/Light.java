package cat.urv.miv.mivandroid3d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Light {
	ByteBuffer vtbb;
	FloatBuffer posicio;
	FloatBuffer ambient;
	FloatBuffer difuse;
	FloatBuffer specular;
	GL10 gl;
	int lightid;

	public Light(GL10 gl, int lightid) {
		this.gl = gl;
		this.lightid = lightid;

    }

	//To enable and disable the light
	public void enable() {gl.glEnable(lightid);}
	public void disable() {gl.glDisable(lightid);}

	//To position the light
	public void setPosition(float[] pos) {
		this.posicio = FloatBuffer.wrap(pos);
		this.setPosition();
	}

	public void setPosition() {
		gl.glLightfv(lightid,GL10.GL_POSITION, posicio);
	}



	//To set the light colors
	public void setAmbientColor(float[] color) {
		this.ambient = FloatBuffer.wrap(color);
		gl.glLightfv(lightid, GL10.GL_AMBIENT, ambient);
	}

	public void setDiffuseColor(float[] color) {
		this.difuse = FloatBuffer.wrap(color);
		gl.glLightfv(lightid, GL10.GL_DIFFUSE, difuse);
	}

	public void draw(){
		gl.glLightfv( lightid, gl.GL_POSITION, posicio );
		gl.glLightfv( lightid, gl.GL_AMBIENT, ambient );
		gl.glLightfv( lightid, gl.GL_DIFFUSE, difuse );
	}

}
