package cat.urv.miv.mivandroid3d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square {
	private float vertices[] = {-1.0f, -1.0f, 0.0f, //lower left
			-1.0f, 1.0f, 0.0f, //upper left
			1.0f, 1.0f, 0.0f, //upper right
			1.0f, -1.0f, 0.0f}; //lower right
	private short faces[] = {0, 1, 2, 0, 2, 3};

	// Our vertex buffer.
	private FloatBuffer vertexBuffer;

	// Our index buffer.
	private ShortBuffer indexBuffer;

	// Our color buffer
	private FloatBuffer colorBuffer;

	// Our texCoord buffer
	private FloatBuffer texCoordBuffer;

	private boolean colorEnabled = false;

	private boolean textureEnabled = false;

	private Texture texture;

	//private Animation animation;

	public Square() {
		//Move the vertices list into a buffer
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		//Move the faces list into a buffer
		ByteBuffer ibb = ByteBuffer.allocateDirect(faces.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(faces);
		indexBuffer.position(0);
	}

	public void setColor(float[] colors) {
		//Move the color list into a buffer
		ByteBuffer vbb = ByteBuffer.allocateDirect(colors.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		colorBuffer = vbb.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);

		colorEnabled = true;
	}

	public void setTexture(Texture tex, float[] texCoords) {
		this.texture = tex;

		/*System.out.println("Texture coordinates:");
		for (float f: texCoords){
			System.out.print(" "+f);
		}*/

		//Move the color list into a buffer
		ByteBuffer vbb = ByteBuffer.allocateDirect(texCoords.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		texCoordBuffer = vbb.asFloatBuffer();
		texCoordBuffer.put(texCoords);
		texCoordBuffer.position(0);

		textureEnabled = true;
	}

	public void draw(GL10 gl) {

		// Enabled the vertices buffer for writing and to be used during rendering.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		// enable Color Buffer
		if (colorEnabled) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		}

		// enable TextureCoord Buffer
		if (textureEnabled) {
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}


		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		//passar les dades del color buffer
		if (colorEnabled) {
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		}

		//passar les dades del color buffer
		if (textureEnabled) {
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoordBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.getTexture()[0]);
		}

		gl.glDrawElements(GL10.GL_TRIANGLES, faces.length,
				GL10.GL_UNSIGNED_SHORT, indexBuffer);

		//disable color buffer
		if (colorEnabled) {
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		}

		//disable color buffer
		if (textureEnabled) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}

		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

	}

}
