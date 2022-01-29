package cat.urv.miv.mivandroid3d;

import android.content.Context;

import javax.microedition.khronos.opengles.GL10;

public class SkyBox {

    private final Square[] faces = new Square[6];

    public SkyBox(GL10 gl, Context context, int[] faces_textures) {

        for (int i = 0; i < 6; i++) {
            // Creamos las texturas para cada cara del skybox
            Texture[] skybox_textures = new Texture[6];
            skybox_textures[i] = new Texture(gl, context, faces_textures[i]);

            // Creamos las caras del cubo
            faces[i] = new Square();
            // Coordenadas UV + textura
            faces[i].setTexture(skybox_textures[i], new float[]{0, 1, 0, 0, 1, 0, 1, 1});
        }
    }

    public void drawSkybox(GL10 gl) {
        // Dibujamos las caras del skybox con sus correspondientes rotaciones

        // LEFT
        gl.glPushMatrix();
        gl.glTranslatef(-1, 0, 0);
        gl.glRotatef(-90, 0, 1, 0);
        faces[0].draw(gl);
        gl.glPopMatrix();

        // RIGHT
        gl.glPushMatrix();
        gl.glTranslatef(1, 0, 0);
        gl.glRotatef(90, 0, 1, 0);
        faces[1].draw(gl);
        gl.glPopMatrix();

        // UP
        gl.glPushMatrix();
        gl.glTranslatef(0, 1, 0);
        gl.glRotatef(-90, 1, 0, 0);
        gl.glRotatef(180, 0, 0, 1);
        faces[2].draw(gl);
        gl.glPopMatrix();

        // DOWN
        gl.glPushMatrix();
        gl.glTranslatef(0, -1, 0);
        gl.glRotatef(90, 1, 0, 0);
        gl.glRotatef(180, 0, 0, 1);
        faces[3].draw(gl);
        gl.glPopMatrix();

        // FRONT
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, -1);
        gl.glRotatef(180, 0, 1, 0);
        faces[4].draw(gl);
        gl.glPopMatrix();

        // BACK
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 1);
        faces[5].draw(gl);
        gl.glPopMatrix();

    }

}
