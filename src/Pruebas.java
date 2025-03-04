import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;

public class Pruebas {

    //java -cp ".;libs/jna.jar;libs/jna-platform.jar" Practica_2

    //https://github.com/java-native-access/jna/tree/master/dist

    public static final int VK_UP = 0x26;   // Tecla flecha arriba
    public static final int VK_DOWN = 0x28; // Tecla flecha abajo
    public static final int VK_LEFT = 0x25; // Tecla flecha izquierda
    public static final int VK_RIGHT = 0x27; // Tecla flecha derecha

    // Variables para mantener el estado de las teclas
    private static boolean upPressed = false;
    private static boolean downPressed = false;
    private static boolean leftPressed = false;
    private static boolean rightPressed = false;

    public interface Kernel32 extends com.sun.jna.platform.win32.Kernel32 {
        Kernel32 INSTANCE = (Kernel32) Native.load("user32", User32.class);

        // Definir la función de Windows que lee un carácter de la consola
        boolean GetAsyncKeyState(int vKey);
    }

    public static void main(String[] args) {
        System.out.println("Presiona las teclas de flecha (arriba, abajo, izquierda, derecha).");

        // Bucle para detectar las pulsaciones de teclas
        while (true) {
            // Verifica si la tecla de flecha arriba es presionada y aún no ha sido registrada
            if ((User32.INSTANCE.GetAsyncKeyState(VK_UP) & 0x8000) != 0) {
                if (!upPressed) {
                    System.out.println("Flecha arriba detectada.");
                    upPressed = true; // Marcamos que ya se ha presionado
                }
            } else {
                upPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
            }

            // Verifica si la tecla de flecha abajo es presionada y aún no ha sido registrada
            if ((User32.INSTANCE.GetAsyncKeyState(VK_DOWN) & 0x8000) != 0) {
                if (!downPressed) {
                    System.out.println("Flecha abajo detectada.");
                    downPressed = true;
                }
            } else {
                downPressed = false;
            }

            // Verifica si la tecla de flecha izquierda es presionada y aún no ha sido registrada
            if ((User32.INSTANCE.GetAsyncKeyState(VK_LEFT) & 0x8000) != 0) {
                if (!leftPressed) {
                    System.out.println("Flecha izquierda detectada.");
                    leftPressed = true;
                }
            } else {
                leftPressed = false;
            }

            // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
            if ((User32.INSTANCE.GetAsyncKeyState(VK_RIGHT) & 0x8000) != 0) {
                if (!rightPressed) {
                    System.out.println("Flecha derecha detectada.");
                    rightPressed = true;
                }
            } else {
                rightPressed = false;
            }

            System.out.println("adf");

        }
    }
}

