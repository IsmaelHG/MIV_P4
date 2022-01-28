package cat.urv.miv.mivandroid3d;

import android.content.Context;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class SkyBox {

    private Texture[] textures = new Texture[6];
    private Square[] faces = new Square[6];
    private float [] texture_coordinates = new float[] {0, 1, 0, 0, 1, 0, 1, 1};

    public SkyBox(){};

    public void load_skybox(GL10 gl, Context context, int[] faces_textures) {

        //Generate and fill the texture with the image


        for (int i=0; i<6; i++){
            textures[i] = new Texture(gl, context, faces_textures[i]);

            faces[i] = new Square();
            faces[i].setTexture(textures[i], texture_coordinates);
        }
    }

    public void drawSkybox(GL10 gl){

        /* Face order:
        posx, negx, posy, negy, posz, negz
         */
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 1);
        faces[5].draw(gl);
        gl.glTranslatef(0, 0, -2);
        gl.glRotatef(180,0, 1, 0);
        faces[4].draw(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(-1, 0, 0);
        gl.glRotatef(-90,0, 1, 0);
        faces[0].draw(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(1, 0, 0);
        gl.glRotatef(90,0, 1, 0);
        faces[1].draw(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(0, 1, 0);
        gl.glRotatef(-90,1, 0, 0);
        gl.glRotatef(180,0, 0, 1);
        faces[2].draw(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(0, -1, 0);
        gl.glRotatef(90,1, 0, 0);
        gl.glRotatef(180,0, 0, 1);
        faces[3].draw(gl);
        gl.glPopMatrix();

    }

}
