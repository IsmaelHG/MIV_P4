package cat.urv.miv.mivandroid3d;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

public class Camera {
	GL10 gl;
	Vertex4 pos;
	Vertex4 forward, up, side;
	
	public Camera(GL10 gl) {
		this.gl = gl;
		
		pos = new Vertex4(0.0f, 0.0f, 0.0f, 1.0f);
		forward = new Vertex4(0.0f, 0.0f, -1.0f, 0.0f);
		up = new Vertex4(0.0f, 1.0f, 0.0f, 0.0f);
		side = new Vertex4(1.0f, 0.0f, 0.0f, 0.0f);
	}

	public void setPosition(Vertex4 pos, Vertex4 forward, Vertex4 up, Vertex4 side) {
		this.pos = pos;
		this.forward = forward;
		this.up = up;
		this.side = side;
	}
	
	public void moveLeft(float inc) {
		pos = pos.add( side.normalize().mult(-inc) );
		System.out.println(pos);
		System.out.println(forward);
		System.out.println(up);
		System.out.println(side);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}

	public void moveRight(float inc) {
		pos = pos.add( side.normalize().mult(inc) );
	}

	public void moveUp(float inc) {
		pos = pos.add( up.normalize().mult(inc) );
	}

	public void moveDown(float inc) {
		pos = pos.add( up.normalize().mult(-inc) );
	}

	public void moveForward(float inc) {
		pos = pos.add(forward.normalize().mult(inc));
	}

	public void moveBackward(float inc) {
		pos = pos.add(forward.normalize().mult(-inc));
	}

	public void yaw(float angle) {
		rotate(-angle, up.get(0), up.get(1), up.get(2));
	}
	public void inverse_yaw(float angle) {
		rotate(angle, up.get(0), up.get(1), up.get(2));
	}

	public void pitch(float angle) {
		rotate(angle, side.get(0), side.get(1), side.get(2));
	}
	public void inverse_pitch(float angle) {
		rotate(-angle, side.get(0), side.get(1), side.get(2));
	}

	public void roll(float angle) {
		rotate(angle, forward.get(0), forward.get(1), forward.get(2));
	}
	public void inverse_roll(float angle) {
		rotate(-angle, forward.get(0), forward.get(1), forward.get(2));
	}

	private Vertex4 mulVectorMatrix (Vertex4 vector, float[][] matrix) {
		Vertex4 destination = new Vertex4();
		float value;
		for (int i = 0; i < 4; i++) {
			value = 0;
			for (int j = 0; j < 4; j++) {
				value += vector.get(j) * matrix[i][j];
			}
			destination.set(i, value);
		}
		return destination;
	}

	private void rotate(float angle, float x, float y, float z) {

		angle = (float) Math.toRadians(angle);
		float c = (float) Math.cos(angle);
		float s =(float) Math.sin(angle);

		float[][] rotationMatrix = { { x*x*(1-c)+c, x*y*(1-c)-z*s, x*z*(1-c)+y*s, 0},
				{ y*x*(1-c)+z*s, y*y*(1-c)+c, y*z*(1-c)-x*s, 0},
				{ x*z*(1-c)-y*s, y*z*(1-c)+x*s, z*z*(1-c)+c, 0},
				{ 0, 0, 0, 1}
		};

		forward = mulVectorMatrix(forward, rotationMatrix).normalize();
		side = mulVectorMatrix(side, rotationMatrix).normalize();
		up = mulVectorMatrix(up, rotationMatrix).normalize();

	}
	
	public void look()
	{
		Vertex4 center = pos.add(forward);

		GLU.gluLookAt(gl, pos.get(0), pos.get(1), pos.get(2), 
						  center.get(0), center.get(1), center.get(2), 
						  up.get(0), up.get(1), up.get(2));
	}
}
