/*
    Linares, Herrero, Javier
*/

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Practica_2 {

    public static final int VK_UP = 0x26;   // Tecla flecha arriba
    public static final int VK_DOWN = 0x28; // Tecla flecha abajo
    public static final int VK_LEFT = 0x25; // Tecla flecha izquierda
    public static final int VK_RIGHT = 0x27; // Tecla flecha derecha
    public static final int VK_RETURN = 0x0D; // Tecla enter
    public static final int N = 0x4E;   // Tecla N
    public static final int R = 0x52; // Tecla R
    public static final int U = 0x55; // Tecla U
    public static final int S = 0x53; // Tecla S
    public static final int L = 0x4C; // Tecla L
    public static final int UNO = 0x31;   // Tecla 1
    public static final int DOS = 0x32; // Tecla 2
    public static final int TRES = 0x33; // Tecla 3
    public static final int CUATRO = 0x34; // Tecla 4
    public static final int CINCO = 0x35; // Tecla 5
    public static final int SEIS = 0x36;   // Tecla 6
    public static final int SIETE = 0x37; // Tecla 7
    public static final int OCHO = 0x38; // Tecla 8
    public static final int NUEVE = 0x39; // Tecla 9

    // Variables para mantener el estado de las teclas
    private static boolean upPressed = false;
    private static boolean downPressed = false;
    private static boolean leftPressed = false;
    private static boolean rightPressed = false;
    private static boolean enterPressed = false;
    private static boolean nPressed = false;
    private static boolean rPressed = false;
    private static boolean uPressed = false;
    private static boolean sPressed = false;
    private static boolean lPressed = false;
    private static boolean unoPressed = false;
    private static boolean dosPressed = false;
    private static boolean tresPressed = false;
    private static boolean cuatroPressed = false;
    private static boolean cincoPressed = false;
    private static boolean seisPressed = false;
    private static boolean sietePressed = false;
    private static boolean ochoPressed = false;
    private static boolean nuevePressed = false;

    public interface Kernel32 extends com.sun.jna.platform.win32.Kernel32 {
        Pruebas.Kernel32 INSTANCE = (Pruebas.Kernel32) Native.load("user32", User32.class);

        // Definir la función de Windows que lee un carácter de la consola
        boolean GetAsyncKeyState(int vKey);
    }

    // función para limpiar la consola
    public static void limpiapantalla(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //función para crear el tablero relleno de 0
    public static void creartablero(int[][] tablero){
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = 0;
            }
        }
    }
    //función para mostrar el tablero, las instrucciones y los datos actuales
    public static void mostrartablero(int[][] tablero,int posicionColumna,int posicionFila,int nivelInicio,int contadorGolpes,int nivelActual) {
        System.out.println();
        System.out.println("    Nuevo(N)-Recomenzar(R)-Deshacer(U)-Salir(S)");
        System.out.println();
        for (int i = 1; i < tablero.length-1; i++) {
            System.out.println("\t -------------------------");
            if(i==posicionFila && posicionColumna==1) {
                System.out.print("\t |");
            }else{
                System.out.print("\t | ");
            }
            for (int j = 1; j < tablero[i].length-1; j++) {
                if (i == posicionFila && j == posicionColumna) {
                    System.out.print("[" + tablero[i][j] + "]| ");
                }else if(i == posicionFila && j == posicionColumna-1) {
                    System.out.print(tablero[i][j] + " |");
                }else{
                    System.out.print(tablero[i][j] + " | ");
                }
            }
            System.out.println();
        }
        System.out.println("\t -------------------------");
        System.out.println();
        nivel(nivelInicio,nivelActual);
        System.out.println("    Golpes: "+contadorGolpes);
        System.out.println("    Instrucciones: ");
        System.out.println("        Mueva el cursor a una botón del tablero(con las flechas).");
        System.out.println("        Pulse \"return\"");
        System.out.println("        para decrementar el valor de ese boron en 1,");
        System.out.println("        y tambien los valores de sus 4 vecinos.");
        System.out.println("    Objetivo:");
        System.out.println("        Dejar todos los botones en '0'.");

    }
    //funcion para imprimir el nivel
    public static void nivel(int nivelInicio, int nivelActual){
        switch (nivelActual) {
            case 1 ->{
                System.out.println("    Nivel de juego(L): Prueba("+(nivelActual*3)+")");
            }
            case 2 ->{
                System.out.println("    Nivel de juego(L): Noob("+(nivelActual*3)+")");
            }
            case 3 ->{
                System.out.println("    Nivel de juego(L): Fasilito("+(nivelActual*3)+")");
            }
            case 4 ->{
                System.out.println("    Nivel de juego(L): Facil("+(nivelActual*3)+")");
            }
            case 5 ->{
                System.out.println("    Nivel de juego(L): Normal("+(nivelActual*3)+")");
            }
            case 6 ->{
                System.out.println("    Nivel de juego(L): Complicado("+(nivelActual*3)+")");
            }
            case 7 ->{
                System.out.println("    Nivel de juego(L): Dificil("+(nivelActual*3)+")");
            }
            case 8 ->{
                System.out.println("    Nivel de juego(L): Extremo("+(nivelActual*3)+")");
            }
            case 9 ->{
                System.out.println("    Nivel de juego(L): Fumadon("+(nivelActual*3)+")");
            }
            default ->{
                System.out.println("    Nivel de juego(L): Desconocido");
            }
        }
    }
    //función para la creacion del tablero según los niveles
    public static void golpearinverso(int[][] tablero,int nivel){
        if(nivel<=9 && nivel>=1){
            int golpesPorNivel= (nivel * 3);
            Random r = new Random();
            int fila, columna;

            for (int i = 0; i < golpesPorNivel; i++) {
                fila = r.nextInt(1,tablero.length-1);
                columna = r.nextInt(1,tablero.length-1);

                tablero[fila][columna] = (tablero[fila][columna])+1;
                if(tablero[fila][columna]>3){
                    tablero[fila][columna]=0;
                }
                tablero[fila+1][columna] = (tablero[fila+1][columna])+1;
                if(tablero[fila+1][columna]>3){
                    tablero[fila+1][columna]=0;
                }
                tablero[fila][columna+1] = (tablero[fila][columna+1])+1;
                if(tablero[fila][columna+1]>3){
                    tablero[fila][columna+1]=0;
                }
                tablero[fila-1][columna] = (tablero[fila-1][columna])+1;
                if(tablero[fila-1][columna]>3){
                    tablero[fila-1][columna]=0;
                }
                tablero[fila][columna-1] = (tablero[fila][columna-1])+1;
                if(tablero[fila][columna-1]>3){
                    tablero[fila][columna-1]=0;
                }
            }
        }else{
            System.out.println("El nivel mínimo es 1 y el nivel máximo es 9");
            if(nivel>9){
                nivel=9;
            }
            if(nivel<1){
                nivel=1;
            }
        }
    }
    //funcion para el golpeo del jugador
    public static void golpeo(int[][] tablero,int posicionColumna,int posicionFila,int[] columnasGuardadas,int[] filasGuardadas ){
        int fila = posicionFila;
        int columna = posicionColumna;

        tablero[fila][columna] = (tablero[fila][columna])-1;
        if(tablero[fila][columna]<0){
            tablero[fila][columna]=3;
        }
        tablero[fila+1][columna] = (tablero[fila+1][columna])-1;
        if(tablero[fila+1][columna]<0){
            tablero[fila+1][columna]=3;
        }
        tablero[fila][columna+1] = (tablero[fila][columna+1])-1;
        if(tablero[fila][columna+1]<0){
            tablero[fila][columna+1]=3;
        }
        tablero[fila-1][columna] = (tablero[fila-1][columna])-1;
        if(tablero[fila-1][columna]<0){
            tablero[fila-1][columna]=3;
        }
        tablero[fila][columna-1] = (tablero[fila][columna-1])-1;
        if(tablero[fila][columna-1]<0){
            tablero[fila][columna-1]=3;
        }


    }
    //funcion para deshacer el golpe anterior
    public static void deshacer(int[][] tablero,int posicionFila,int posicionColumna){
        //que conste que se que esto está dado la vuelta, pero me daba pereza cambiar lo de abajo otra vez
        int fila = posicionColumna;
        int columna = posicionFila;

        tablero[fila][columna] = (tablero[fila][columna])+1;
        if(tablero[fila][columna]>3){
            tablero[fila][columna]=0;
        }
        tablero[fila+1][columna] = (tablero[fila+1][columna])+1;
        if(tablero[fila+1][columna]>3){
            tablero[fila+1][columna]=0;
        }
        tablero[fila][columna+1] = (tablero[fila][columna+1])+1;
        if(tablero[fila][columna+1]>3){
            tablero[fila][columna+1]=0;
        }
        tablero[fila-1][columna] = (tablero[fila-1][columna])+1;
        if(tablero[fila-1][columna]>3){
            tablero[fila-1][columna]=0;
        }
        tablero[fila][columna-1] = (tablero[fila][columna-1])+1;
        if(tablero[fila][columna-1]>3){
            tablero[fila][columna-1]=0;
        }
    }
    //funcion para capturar el movimiento hacia arriba dentro del tablero con las flechas del teclado
    public static int movimientoArriba(int posicionFila) {
        int nuevaposicion ;
        if(posicionFila==1){
            nuevaposicion=6;
        }else{
            nuevaposicion=posicionFila-1;
        }
        return nuevaposicion;
    }
    //funcion para capturar el movimiento hacia arriba dentro del tablero con las flechas del teclado
    public static int movimientoAbajo(int posicionFila) {
        int nuevaposicion ;
        if(posicionFila==6){
            nuevaposicion=1;
        }else{
            nuevaposicion=posicionFila+1;
        }
        return nuevaposicion;
    }
    //funcion para capturar el movimiento hacia arriba dentro del tablero con las flechas del teclado
    public static int movimientoDerecha(int posicionColumna) {
        int nuevaposicion ;
        if(posicionColumna==6){
            nuevaposicion=1;
        }else{
            nuevaposicion=posicionColumna+1;
        }
        return nuevaposicion;
    }
    //funcion para capturar el movimiento hacia arriba dentro del tablero con las flechas del teclado
    public static int movimientoIzquierda(int posicionColumna) {
        int nuevaposicion ;
        if(posicionColumna==1){
            nuevaposicion=6;
        }else{
            nuevaposicion=posicionColumna-1;
        }
        return nuevaposicion;
    }
    //funcion para comprobar si se ha ganado
    public static boolean comprobarGanador(int [][] tablero,int nivel,int contadorGolpes){
        int cont=0;
        for (int i = 1; i < tablero.length-1; i++) {
            for (int j = 1; j < tablero[i].length-1; j++){
                if(tablero[i][j]==0){
                    cont++;
                }
            }
        }
        if(cont==36){
            return true;
        }else{
            return false;
        }
    }
    //funcion para imprimir si se ha ganado
    public static void imprimirGanador(int contadorGolpes,int nivel){
        if(contadorGolpes==(nivel*3)){
            System.out.println("Perfecto.Hecho en "+contadorGolpes+" golpes");
        }else if(contadorGolpes<(nivel*3)){
            System.out.println("Extraordinariamente bien: Hecho en "+contadorGolpes+" golpes");
        }else{
            System.out.println("Hecho en "+contadorGolpes+" golpes");
        }
    }
    //funcion para guardar el array en la opcion rehacer
    public static void tableroRehecho(int[][] tablero,int[][] tableroGuardado,int orden){
        if(orden==1){
            for(int i=0;i<tablero.length;i++){
                for(int j=0;j<tablero[i].length;j++){
                    tableroGuardado[i][j]=tablero[i][j];
                }
            }
        }else if(orden==2){
            for(int i=0;i<tablero.length;i++){
                for(int j=0;j<tablero[i].length;j++){
                    tablero[i][j]=tableroGuardado[i][j];
                }
            }
        }
    }
    //funcion para guardar los golpes que se han realizado para cuando se usa deshacer
    public static int[] aumentarArray(int[] ArrayFilaoColumna,int posicionFilaoColumna){
        int[] nuevoArray = new int[ArrayFilaoColumna.length + 1]; //creamos un nuevo array de 1 mas grande
        for (int i = 0; i < ArrayFilaoColumna.length; i++) {      //copiar elementos al nuevo array
            nuevoArray[i] = ArrayFilaoColumna[i];
        }
        nuevoArray[ArrayFilaoColumna.length] = posicionFilaoColumna; //añadimos el nuevo valor al final
        return nuevoArray;
    }
    //funcion para disminuir los arrays de golpes cuando se usa deshacer
    public static int[] disminuirArray(int[] ArrayFilaoColumna,int posicionFilaoColumna){
        int[] nuevoArray = new int[ArrayFilaoColumna.length - 1]; //creamos un nuevo array de 1 mas pequeño
        for (int i = 0; i < ArrayFilaoColumna.length -1 ; i++) {      //copiar elementos al nuevo array -1
            nuevoArray[i] = ArrayFilaoColumna[i];
        }
        return nuevoArray;


    }
    //funcion para iniciar el tablero
    public static void inicio(boolean continua, int[][] tablero,int nivelInicio, int orden,int[][] tableroGuardado,int posicionFila,int posicionColumna, int contadorGolpes,int nivelActual){
        if (!continua){
            creartablero(tablero);
            golpearinverso(tablero,nivelInicio);
        }
        if(!continua){
            orden = 1;
            tableroRehecho(tablero,tableroGuardado,orden);
        }
        mostrartablero(tablero,posicionColumna,posicionFila,nivelInicio,contadorGolpes,nivelActual);

    }
    //funcion principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] tablero = new int[8][8]; //creacion del tablero vacio
        int[] columnasGuardadas = new int[0]; //array donde guardar las columnas de donde se ha golpeado
        int[] filasGuardadas = new int[0]; //array donde guardar las filas de donde se ha golpeado
        int posicionColumna=1,posicionFila=1; //posicion donde está el cursor
        int nivelInicio=1; //nivel en el que inicia el juego
        int contadorGolpes = 0; //contador de golpes que lleva
        int nivelActual=nivelInicio;
        boolean Salir = false; //si se sale del programa
        boolean continua = false; //si se continua con el mismo tablero
        int[][] tableroGuardado = new int[8][8]; //tablero de reserva para el reinicio
        int orden = 1; //orden de guardado de tablero para el reinicio
        boolean inicia = true; //si se inicia un nuevo tablero

        do {

            if(inicia){
                inicio(continua, tablero, nivelInicio, orden, tableroGuardado, posicionFila, posicionColumna, contadorGolpes,nivelActual);
                inicia = false;
            }

            if ((User32.INSTANCE.GetAsyncKeyState(S) & 0x8000) != 0) { //para salir
                if (!sPressed) {

                    //System.out.println(Arrays.toString(columnasGuardadas));
                    Salir = true;

                    sPressed = true; // Marcamos que ya se ha presionado
                }
            } else {
                    sPressed = false;// Restablecemos el estado cuando la tecla se ha soltado

            }

            if ((User32.INSTANCE.GetAsyncKeyState(N) & 0x8000) != 0) { //para iniciar un tablero nuevo del mismo nivel
                if (!nPressed) {

                    int[] nuevo = new int[1];
                    columnasGuardadas = nuevo;
                    filasGuardadas = nuevo;
                    nivelActual = nivelInicio;

                    contadorGolpes = 0;
                    continua = false;
                    inicia = true;

                    limpiapantalla();
                    //mostrartablero(tablero, posicionColumna, posicionFila, nivelInicio, contadorGolpes,nivelActual);


                    nPressed = true; // Marcamos que ya se ha presionado
                }
            } else {
                    nPressed = false;// Restablecemos el estado cuando la tecla se ha soltado

            }

            if ((User32.INSTANCE.GetAsyncKeyState(R) & 0x8000) != 0) { //para reiniciar el tablero actual
                if (!rPressed) {

                    int[] nuevo = new int[1];
                    columnasGuardadas = nuevo;
                    filasGuardadas = nuevo;

                    orden = 2;
                    tableroRehecho(tablero, tableroGuardado, orden);
                    contadorGolpes = 0;
                    continua = true;
                    inicia = true;

                    limpiapantalla();
                    //mostrartablero(tablero, posicionColumna, posicionFila, nivelInicio, contadorGolpes,nivelActual);

                    rPressed = true; // Marcamos que ya se ha presionado

                }
            } else {
                    rPressed = false;// Restablecemos el estado cuando la tecla se ha soltado

            }

            if ((User32.INSTANCE.GetAsyncKeyState(L) & 0x8000) != 0) { //para cambiar el nivel e iniciar un tablero nuevo en ese nivel
                if (!lPressed) {
                    System.out.println("Nuevo nivel (del 1 al 9): ");
                    boolean pulsada = false;

                    do {
                        if ((User32.INSTANCE.GetAsyncKeyState(UNO) & 0x8000) != 0) { //para salir
                            if (!unoPressed) {
                                nivelActual = nivelInicio;
                                nivelInicio = 1;
                                pulsada = true;
                                unoPressed = true; // Marcamos que ya se ha presionado
                            }
                        } else {
                            unoPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                        }
                        if ((User32.INSTANCE.GetAsyncKeyState(DOS) & 0x8000) != 0) { //para salir
                            if (!dosPressed) {
                                nivelActual = nivelInicio;
                                nivelInicio = 2;
                                pulsada = true;
                                dosPressed = true; // Marcamos que ya se ha presionado
                            }
                        } else {
                            dosPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                        }
                        if ((User32.INSTANCE.GetAsyncKeyState(TRES) & 0x8000) != 0) { //para salir
                            if (!tresPressed) {
                                nivelActual = nivelInicio;
                                nivelInicio = 3;
                                pulsada = true;
                                tresPressed = true; // Marcamos que ya se ha presionado
                            }
                        } else {
                            tresPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                        }
                        if ((User32.INSTANCE.GetAsyncKeyState(CUATRO) & 0x8000) != 0) { //para salir
                            if (!cuatroPressed) {
                                nivelActual = nivelInicio;
                                nivelInicio = 4;
                                pulsada = true;
                                cuatroPressed = true; // Marcamos que ya se ha presionado
                            }
                        } else {
                            cuatroPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                        }
                        if ((User32.INSTANCE.GetAsyncKeyState(CINCO) & 0x8000) != 0) { //para salir
                            if (!cincoPressed) {
                                nivelActual = nivelInicio;
                                nivelInicio = 5;
                                pulsada = true;
                                cincoPressed = true; // Marcamos que ya se ha presionado
                            }
                        } else {
                            cincoPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                        }
                        if ((User32.INSTANCE.GetAsyncKeyState(SEIS) & 0x8000) != 0) { //para salir
                            if (!seisPressed) {
                                nivelActual = nivelInicio;
                                nivelInicio = 6;
                                pulsada = true;
                                seisPressed = true; // Marcamos que ya se ha presionado
                            }
                        } else {
                            seisPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                        }
                        if ((User32.INSTANCE.GetAsyncKeyState(SIETE) & 0x8000) != 0) { //para salir
                            if (!sietePressed) {
                                nivelActual = nivelInicio;
                                nivelInicio = 7;
                                pulsada = true;
                                sietePressed = true; // Marcamos que ya se ha presionado
                            }
                        } else {
                            sietePressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                        }
                        if ((User32.INSTANCE.GetAsyncKeyState(OCHO) & 0x8000) != 0) { //para salir
                            if (!ochoPressed) {
                                nivelActual = nivelInicio;
                                nivelInicio = 8;
                                pulsada = true;
                                ochoPressed = true; // Marcamos que ya se ha presionado
                            }
                        } else {
                            ochoPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                        }
                        if ((User32.INSTANCE.GetAsyncKeyState(NUEVE) & 0x8000) != 0) { //para salir
                            if (!nuevePressed) {
                                nivelActual = nivelInicio;
                                nivelInicio = 9;
                                pulsada = true;
                                nuevePressed = true; // Marcamos que ya se ha presionado
                            }
                        } else {
                            nuevePressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                        }
                    } while (!pulsada);

                    if(nivelActual==nivelInicio){
                        continua = true;
                        inicia = false;
                    }else{
                        int[] nuevo = new int[1];
                        columnasGuardadas = nuevo;
                        filasGuardadas = nuevo;
                        nivelActual = nivelInicio;

                        contadorGolpes = 0;
                        continua = false;
                        inicia = true;
                    }

                    limpiapantalla();
                    //mostrartablero(tablero,posicionColumna,posicionFila,nivelInicio,contadorGolpes,nivelActual);

                    lPressed = true; // Marcamos que ya se ha presionado
                }
            } else {
                    lPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
            }

            if ((User32.INSTANCE.GetAsyncKeyState(U) & 0x8000) != 0) { //para deshacer golpes anteriores
                if (!uPressed) {

                    if (!(contadorGolpes <= 0)) {

                        posicionColumna = columnasGuardadas[columnasGuardadas.length - 1];
                        posicionFila = filasGuardadas[filasGuardadas.length - 1];

                        deshacer(tablero, posicionColumna, posicionFila);
                        contadorGolpes--;

                        columnasGuardadas = disminuirArray(columnasGuardadas, posicionColumna);;
                        filasGuardadas = disminuirArray(filasGuardadas, posicionFila);

                    }

                    continua = true;
                    inicia = false;

                    limpiapantalla();
                    mostrartablero(tablero, posicionColumna, posicionFila, nivelInicio, contadorGolpes,nivelActual);

                    uPressed = true; // Marcamos que ya se ha presionado
                }
            } else {
                    uPressed = false;// Restablecemos el estado cuando la tecla se ha soltado

            }

            if ((User32.INSTANCE.GetAsyncKeyState(VK_RETURN) & 0x8000) != 0) { //para dar golpes
                if (!enterPressed) {

                    golpeo(tablero, posicionColumna, posicionFila, columnasGuardadas, filasGuardadas);
                    contadorGolpes++;
                    columnasGuardadas = aumentarArray(columnasGuardadas, posicionColumna);
                    filasGuardadas = aumentarArray(filasGuardadas, posicionFila);

                    continua = true;
                    inicia = false;
                    limpiapantalla();
                    mostrartablero(tablero, posicionColumna, posicionFila, nivelInicio, contadorGolpes,nivelActual);

                    enterPressed = true; // Marcamos que ya se ha presionado
                }
            } else {
                enterPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
            }


            if ((User32.INSTANCE.GetAsyncKeyState(VK_UP) & 0x8000) != 0) {// Verifica si la tecla de flecha arriba es presionada y aún no ha sido registrada
                if (!upPressed) {

                    posicionFila =movimientoArriba(posicionFila);

                    continua = true;
                    inicia = false;
                    limpiapantalla();
                    mostrartablero(tablero,posicionColumna,posicionFila,nivelInicio,contadorGolpes,nivelActual);

                    upPressed = true; // Marcamos que ya se ha presionado
                }
            } else {
                upPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
            }

            if ((User32.INSTANCE.GetAsyncKeyState(VK_DOWN) & 0x8000) != 0) {// Verifica si la tecla de flecha abajo es presionada y aún no ha sido registrada
                if (!downPressed) {

                    posicionFila =movimientoAbajo(posicionFila);

                    continua = true;
                    inicia = false;
                    limpiapantalla();
                    mostrartablero(tablero,posicionColumna,posicionFila,nivelInicio,contadorGolpes,nivelActual);

                    downPressed = true;
                }
            } else {
                downPressed = false;
            }

            if ((User32.INSTANCE.GetAsyncKeyState(VK_LEFT) & 0x8000) != 0) {// Verifica si la tecla de flecha izquierda es presionada y aún no ha sido registrada
                if (!leftPressed) {

                    posicionColumna =movimientoIzquierda(posicionColumna);

                    continua = true;
                    inicia = false;
                    limpiapantalla();
                    mostrartablero(tablero,posicionColumna,posicionFila,nivelInicio,contadorGolpes,nivelActual);

                    leftPressed = true;
                }
            } else {
                leftPressed = false;
            }

            if ((User32.INSTANCE.GetAsyncKeyState(VK_RIGHT) & 0x8000) != 0) {// Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                if (!rightPressed) {

                    posicionColumna =movimientoDerecha(posicionColumna);

                    continua = true;
                    inicia = false;
                    limpiapantalla();
                    mostrartablero(tablero,posicionColumna,posicionFila,nivelInicio,contadorGolpes,nivelActual);

                    rightPressed = true;
                }
            } else {
                rightPressed = false;
            }

            if (comprobarGanador(tablero, nivelInicio, contadorGolpes)) {
                System.out.println("");
                imprimirGanador(contadorGolpes, nivelInicio);
                continua = false;
                do{
                    if ((User32.INSTANCE.GetAsyncKeyState(VK_RETURN) & 0x8000) != 0) {
                        if (!enterPressed) {

                            int[] nuevo = new int[1];
                            columnasGuardadas = nuevo;
                            filasGuardadas = nuevo;
                            nivelActual = nivelInicio;

                            contadorGolpes = 0;
                            continua = false;
                            inicia = true;

                            limpiapantalla();

                            enterPressed = true; // Marcamos que ya se ha presionado
                            break;
                        }
                    } else {
                        enterPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                    }
                }while(true);
            }

        }while(!Salir);
    }
}

