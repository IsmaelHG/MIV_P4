package cat.urv.miv.mivandroid3d;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;

public class MainActivity extends Activity {
    // Ultimo movimiento realizado
    public double lastupdate = System.currentTimeMillis();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new MyOpenGLRenderer(this));
        setContentView(view);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        double time = System.currentTimeMillis();
        if (time - lastupdate > CameraManager.MOVE_FREQUENCY) {
            // CONTROLES
            switch (keyCode) {
                // MOVIMIENTOS
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    CameraManager.moveLeft(CameraManager.SPEED);
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    CameraManager.moveRight(CameraManager.SPEED);
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    CameraManager.moveUp(CameraManager.SPEED);
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    CameraManager.moveDown(CameraManager.SPEED);
                    break;
                case KeyEvent.KEYCODE_W:
                    CameraManager.moveForward(CameraManager.SPEED);
                    break;
                case KeyEvent.KEYCODE_S:
                    CameraManager.moveBackward(CameraManager.SPEED);
                    break;

                // EJES DE ROTACION
                case KeyEvent.KEYCODE_Y:
                    CameraManager.yaw(CameraManager.ROTATE_ANGLE);
                    break;
                case KeyEvent.KEYCODE_H:
                    CameraManager.inverse_yaw(CameraManager.ROTATE_ANGLE);
                    break;
                case KeyEvent.KEYCODE_R:
                    CameraManager.roll(CameraManager.ROTATE_ANGLE);
                    break;
                case KeyEvent.KEYCODE_F:
                    CameraManager.inverse_roll(CameraManager.ROTATE_ANGLE);
                    break;
                case KeyEvent.KEYCODE_O:
                    CameraManager.pitch(CameraManager.ROTATE_ANGLE);
                    break;
                case KeyEvent.KEYCODE_L:
                    CameraManager.inverse_pitch(CameraManager.ROTATE_ANGLE);
                    break;

                // CAMBIO DE CAMARA
                case KeyEvent.KEYCODE_C:
                    CameraManager.switch_camera();
                    break;
                case KeyEvent.KEYCODE_1:
                    break;
            }
            return true;
        } else return false;

    }
}