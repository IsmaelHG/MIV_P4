package cat.urv.miv.mivandroid3d;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.khronos.opengles.GL10;


public class Texture {

    private int[] textures=new int[1];
    private int width, height;


    public Texture(GL10 gl, Context context, int resource_id) {
        //Get the texture from the Android resource directory

        InputStream is = context.getResources().openRawResource(resource_id);
        Bitmap bitmap = null;

        try {
            //BitmapFactory is an Android graphics utility for images
            bitmap = BitmapFactory.decodeStream(is);

        } finally {
            //Always clear and close
            try {
                is.close();
                is = null;
            } catch (IOException e) {
            }
        }


        //Generate and fill the texture with the image
        gl.glGenTextures(1, textures, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_CLAMP_TO_EDGE, GL10.GL_REPEAT);

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGBA, bitmap, 0);

        width = bitmap.getWidth();
        height = bitmap.getHeight();

        //Clean up
        bitmap.recycle();
    }

    int[] getTexture() {
        return textures;
    }

    int getWidth(){
        return width;
    }

    int getHeight() {
        return height;
    }
}
