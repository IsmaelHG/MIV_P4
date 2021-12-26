package cat.urv.miv.mivandroid3d;

import javax.microedition.khronos.opengles.GL10;

/*
Global camera manager, makes transitions between
MainActivity and Renderer.
 */
public class CameraManager {

    public static int MAX_NUM_CAMERAS = 2;
    public static int FIRST_CAMERA = 0;
    public static float MOVE_SPEED = 0.08f;
    public static float MOVE_FREQUENCY = 20f;
    public static float ROTATE_ANGLE = 2f;
    private static Camera[] cameras;
    private static Camera current_camera;
    private static int current_camera_number = FIRST_CAMERA;
    private static boolean started_cameras = false;

    public static void start(GL10 gl) {
        cameras = new Camera [MAX_NUM_CAMERAS];
        for (int i=0; i<MAX_NUM_CAMERAS; i++) cameras[i] = new Camera(gl);
        current_camera = cameras[FIRST_CAMERA];
        started_cameras = true;


    }

    public static int switch_camera(){
        current_camera_number = (current_camera_number + 1)%MAX_NUM_CAMERAS;
        current_camera = cameras[current_camera_number];
        return current_camera_number;
    }

    public static void moveLeft(float inc) {
        if (started_cameras)
            current_camera.moveLeft(inc);
    }

    public static void moveRight(float inc) {
        if (started_cameras)
            current_camera.moveRight(inc);
    }

    public static void moveUp(float inc) {
        if (started_cameras)
            current_camera.moveUp(inc);
    }

    public static void moveDown(float inc) {
        if (started_cameras)
            current_camera.moveDown(inc);
    }

    public static void moveForward(float inc) {
        if (started_cameras)
            current_camera.moveForward(inc);
    }

    public static void moveBackward(float inc) {
        if (started_cameras)
            current_camera.moveBackward(inc);
    }

    public static void yaw(float angle) {
        if (started_cameras)
            current_camera.yaw(angle);

    }

    public static void inverse_yaw(float angle) {
        if (started_cameras)
            current_camera.inverse_yaw(angle);

    }

    public static void pitch(float angle) {
        if (started_cameras)
            current_camera.pitch(angle);
    }

    public static void inverse_pitch(float angle) {
        if (started_cameras)
            current_camera.inverse_pitch(angle);
    }

    public static void roll(float angle) {
        if (started_cameras)
            current_camera.roll(angle);
    }

    public static void inverse_roll(float angle) {
        if (started_cameras)
            current_camera.inverse_roll(angle);
    }

    public static int getCurrent_camera_number() {
        return current_camera_number;
    }

    public static void setCamPosition(Vertex4 pos, Vertex4 forward, Vertex4 up, Vertex4 side, int cam) {
        if (started_cameras)
            if ( (cam < MAX_NUM_CAMERAS) && (cam >= 0) ) {
                cameras[cam].setPosition(pos,forward,up,side);
            }
    }

    public static void look()
    {
        current_camera.look();
    }
}
