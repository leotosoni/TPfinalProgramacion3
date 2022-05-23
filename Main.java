package com.company.leotosoni;

import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
    Usuario Leo = new Usuario("Leonardo", "Tosoni", "leotosoni", "leonardo1");
   /* Usuario Mario = new Usuario("Mario", "Lopez", "maritolopez", "marito123");
    Usuario Javier = new Usuario("Javier", "Betucci", "javito", "javibetucci");
    Usuario Ana = new Usuario("Ana", "Gonzalez", "anita123", "anitagonza");

    List<Transaccion> listTransPend = new ArrayList<>();
    List<Transaccion> listTransAprob = new ArrayList<>();

    listTransPend.add(Leo.nuevaTransferencia(Mario,30));
    listTransPend.add(Leo.nuevaTransferencia(Javier,40));

        System.out.println(Leo.mostrarTransaccionesPendientes(listTransPend));
        System.out.println("UTC COINS RESTANTES: "+ Leo.getUtnCoins());

        Mario.validarTransaccion(listTransPend.get(1));
        Ana.validarTransaccion(listTransPend.get(1));

        System.out.println(Leo.mostrarTransaccionesPendientes(listTransPend));
*/
        mainMenu(Leo);

    }

    public static Usuario registrarse() {

        System.out.println("------SIGN UP------");
        Scanner scanner = new Scanner(System.in);
        System.out.println("INGRESE SU NOMBRE");
        String nombre = scanner.nextLine();
        System.out.println("INGRESE SU APELLIDO");
        String apellido = scanner.nextLine();
        System.out.println("NOMBRE DE USUARIO");
        String userName = scanner.nextLine();
        System.out.println("CONTRASENIA");
        String password = scanner.nextLine();

        Usuario newUsuario = new Usuario(nombre, apellido, userName, password);

        System.out.println("SU CODIGO UNICO ES: " + newUsuario.getUuidCodigo().toString());
        return newUsuario;
    }

    public static void login() {
        /*int intentos = 3;

        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("-------LOGIN-------");
            System.out.println("USERNAME: ");
            String username = scanner.next();
            System.out.println("PASSWORD: ");
            String password = scanner.next();

            if (idExiste()) {
                intentos = 0;
            } else {
                System.out.println("USUARIO O CONTRASENIA INCORRECTO");
                System.out.println("INTENTELO NUEVAMENTE");
                intentos--;
            }
        } while (intentos > 0);

        if (intentos == 0) {
            mainMenu(buscarUsuarioPorId(idExiste));
        } else {
            inicioPantalla();
        }*/
    }


    public static void inicioPantalla() {
        System.out.println("------BIENVENIDO!!!------");
        System.out.println("1. LOGIN");
        System.out.println("2. SIGN UP");
        System.out.println("3. SALIR");

        Scanner scanner = new Scanner(System.in);
        System.out.println("INGRESE UNA OPCION");
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                login();
                break;
            case 2:
                registrarse();
                break;
            case 3:
                break;
        }

    }

    public static void mainMenu(Usuario usuario){

        System.out.println("------MAIN MENU------");
        System.out.println("1. MOSTRAR LA LISTA DE USUARIOS");
        System.out.println("2. REALIZAR UNA TRANSACCION NUEVA");
        System.out.println("3. CONSULTAR EL ESTADO DE LA CUENTA");
        System.out.println("4. TRANSACCIONES ORIGINADAS PENDIENTES");
        System.out.println("5. TRANSACCIONES PENDIENTES A VALIDAR");

        Scanner scanner = new Scanner(System.in);
        System.out.println("INGRESE UNA OPCION");
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                listaUsuarios(usuario);
                break;
            case 2:
                realizarNuevaTransaccion(usuario);
                break;
            case 3:
                estadoDeLACuenta(usuario);
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                inicioPantalla();
                break;
        }


    }


    public static void estadoDeLACuenta(Usuario usuario){
        System.out.println("ESTADO DE LA CUENTA");
        System.out.println("USTED TIENE: " + usuario.getUtnCoins() + " UTN COINS DISPONIBLES");

        mainMenu(usuario);
    }

    public static void realizarNuevaTransaccion(Usuario usuario){
        System.out.println("INGRESE EL CODIGO UNICO DEL USUARIO DESTINO: ");
        Scanner scanner = new Scanner(System.in);
        String codigoDestino = scanner.nextLine();

       // Usuario destino = findUserbyUTCcode(codigoDestino);
        Usuario destino = new Usuario("Mario","Lopez", "maritoL","mario123");
        if(destino == null){
            System.out.println("USUARIO NO ENCONTRADO ....");
        }
        else{
            System.out.println("SALDO DISPONIBLE: " + usuario.getUtnCoins());
            System.out.println("INGRESE EL MONTO A TRANSFERIR: ");
            double monto = scanner.nextDouble();

            while (monto>usuario.getUtnCoins()) {
                    System.out.println("EL MONTO ES SUPERIOR A SU SALDO DISPONIBLE");
                    System.out.println("INTENTE NUEVAMENTE: ");
                    monto = scanner.nextDouble();
                }

            System.out.println("TRANSFERENCIA DE " + monto + "  UTC COINS A " + destino.getNombre());
            System.out.println("CONFIRMA LA TRANSFERENCIA?");
            System.out.println("1. SI");
            System.out.println("2. NO");
            int opcion = scanner.nextInt();

            if(opcion == 1){
                Transaccion nueva = new Transaccion(usuario, destino, monto);

                System.out.println("TRANSFERENCIA CREADA EXITOSAMENTE");
                System.out.println("CODIGO DE LA TRANSFERENCIA: " + nueva.getUniqueID());
            }
        }
        mainMenu(usuario);
    }

    public static void listaUsuarios(Usuario usuario){

    }

   /* public static Usuario findUserbyUTCcode(String codigo){


    }*/
}
