package com.company.leotosoni;

import java.util.Scanner;

public abstract class Utiles {

    public static void presioneTeclaParaContinuar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPRESIONE CUALQUIER TECLA PARA CONTINUAR: ");
        String tecla = scanner.next();
    }

    public static void BC() {
        System.out.println("|||||||| ■■■■■      ■■■■■ ||||||||");
        System.out.println("|||||||| ■   ■     ■■     ||||||||");
        System.out.println("|||||||| ■■■       ■      ||||||||");
        System.out.println("|||||||| ■   ■     ■■     ||||||||");
        System.out.println("|||||||| ■■■■■      ■■■■■ ||||||||");
    }

    public static void hastaLuego() {
        System.out.println("\n\n\t\t----- HASTA LA PROXIMA ..... -----");
    }

    public static int validadorOpciones(int min, int max) {
        int opcion = 0;
        int flag = 0;

        Scanner scanner = new Scanner(System.in);
        do {
            do {
                try {
                    System.out.println("INGRESE UNA OPCION");
                    opcion = Integer.parseInt(scanner.next());
                    flag = 0;
                } catch (Exception e) {
                    System.out.println("HA INGRESADO UNA OPCION INVALIDA");
                    flag = 1;
                }
            } while (flag == 1);

            if (opcion < min || opcion > max) {
                System.out.println("HA INGRESEADO UNA OPCION INCORRECTA");
                System.out.println("INTENTE NUEVAMENTE");
                flag = 1;
            }
        } while (flag == 1);

        return opcion;
    }
}
