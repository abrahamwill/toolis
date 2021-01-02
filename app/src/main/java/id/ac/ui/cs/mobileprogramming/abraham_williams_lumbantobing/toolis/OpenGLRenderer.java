package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private double redValue = 1f;
    private double greenValue = 1f;
    private double blueValue = 1f;
    private static final double FLASH_DURATION = 1000;

    private double redValueTriangle = 1f;
    private double greenValueTriangle = 1f;
    private double blueValueTriangle = 1f;

    private Shape mTriangle;
//    private Square   mSquare;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor((float) redValue, (float)  greenValue, (float) blueValue,1f);
        // initialize a triangle
        mTriangle = new Shape();
        // initialize a square
//        mSquare = new Square();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glClearColor((float) redValue, (float)  greenValue, (float) blueValue,1f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        mTriangle.draw();

        redValue = ((Math.sin(System.currentTimeMillis() * 2 * Math.PI / FLASH_DURATION * 0.2) + 0.5));
        greenValue = ((Math.sin(System.currentTimeMillis() * 2 * Math.PI / FLASH_DURATION * 0.4) + 0.5));
        blueValue = ((Math.sin(System.currentTimeMillis() * 2 * Math.PI / FLASH_DURATION * 0.7) + 0.5));

        redValueTriangle = ((Math.sin(System.currentTimeMillis() * 2 * Math.PI / FLASH_DURATION * 0.8) + 0.5));
        greenValueTriangle = ((Math.sin(System.currentTimeMillis() * 2 * Math.PI / FLASH_DURATION * 0.5) + 0.5));
        blueValueTriangle = ((Math.sin(System.currentTimeMillis() * 2 * Math.PI / FLASH_DURATION * 0.1) + 0.5));

        float color[] = {(float) redValueTriangle, (float)  greenValueTriangle, (float) blueValueTriangle, 1f};
        mTriangle.setColor(color);



    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}
