package cat.urv.miv.mivandroid3d;

import javax.microedition.khronos.opengles.GL10;

/*
Manejador del sistema de camaras

Permite cambiar entre las diversas camaras configuradas. Por defecto hay 2 camaras
 */
public class CameraManager {

    public static int MAX_NUM_CAMERAS = 2;
    public static int FIRST_CAMERA = 0;
    public static float MOVE_SPEED = 0.08f;
    public static float MOVE_FREQUENCY = 20f;
    public static float ROTATE_ANGLE = 2f;
    public static double[] last_modification;
    public static int[] curr_movement;
    public static boolean[] curr_dir;
    public static int MAX_MOVEMENT = 170;
    private static Camera[] cameras;
    private static Camera current_camera;
    private static int current_camera_number = FIRST_CAMERA;
    private static boolean started_cameras = false;

    public static void start(GL10 gl) {
        cameras = new Camera[MAX_NUM_CAMERAS];
        last_modification = new double[MAX_NUM_CAMERAS];
        curr_movement = new int[MAX_NUM_CAMERAS];
        curr_dir = new boolean[MAX_NUM_CAMERAS];
        for (int i = 0; i < MAX_NUM_CAMERAS; i++) {
            cameras[i] = new Camera(gl);
            last_modification[i] = System.currentTimeMillis();
            curr_movement[i] = 0;
            curr_dir[i] = true;
        }
        current_camera = cameras[FIRST_CAMERA];
        started_cameras = true;


    }

    // Metodo para crear un desplazamiento constante
    // La camara especificada por parametro hara un movimiento hacía atras/adelante
    public static void moving_camera(int cam_num) {
        // Comprobamos que la camara existe
        if (cam_num < MAX_NUM_CAMERAS) {
            // Capturamos el tiempo actual
            double current_modification = System.currentTimeMillis();
            // Si ha pasado un intervalo suficiente de tiempo, procedemos a realizar el movimiento en función de la dirección actual
            if (current_modification - last_modification[cam_num] > CameraManager.MOVE_FREQUENCY) {
                if (curr_dir[cam_num]) {
                    cameras[cam_num].moveForward(MOVE_SPEED);
                } else {
                    cameras[cam_num].moveBackward(MOVE_SPEED);
                }

                // Tras sobrepasar un cierto limite, la camara cambiara de dirección de movimiento
                if (curr_movement[cam_num] > MAX_MOVEMENT) {
                    curr_movement[cam_num] = 0;
                    curr_dir[cam_num] = !curr_dir[cam_num];
                } else {
                    curr_movement[cam_num]++;
                }

            }
        }

    }

    public static int switch_camera() {
        // Pasamos a usar la siguiente camara
        current_camera_number = (current_camera_number + 1) % MAX_NUM_CAMERAS;
        current_camera = cameras[current_camera_number];

        // Retornamos la camara actual
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

    public static void setCamPosition(Vertex4 pos, Vertex4 forward, Vertex4 up, Vertex4 side, int cam) {
        if (started_cameras)
            if ((cam < MAX_NUM_CAMERAS) && (cam >= 0)) {
                cameras[cam].setPosition(pos, forward, up, side);
            }
    }

    public static Vertex4[] look() {
        return current_camera.look();
    }
}
