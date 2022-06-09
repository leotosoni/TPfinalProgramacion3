package com.company.leotosoni;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jdk.jshell.execution.Util;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;

import static com.company.leotosoni.Utiles.*;

public class Main {
    public final static String usuarioPath = "users.json";
    public final static String transferenciaPath = "transferencia.json";
    public final static String blockchainPath = "blockchain.json";

    public static void main(String[] args) {
        inicioPantalla();
    }

    public static void registrarse() {

        BC();
        System.out.println("------SIGN UP------");
        Scanner scanner = new Scanner(System.in);
        System.out.println("INGRESE SU NOMBRE");
        String nombre = scanner.nextLine();
        System.out.println("INGRESE SU APELLIDO");
        String apellido = scanner.nextLine();
        System.out.println("EMAIL");
        String userName = scanner.nextLine();
        /// validador de emails
        while (!userName.contains("@") || !userName.contains(".")) {
            System.out.println("EL EMAIL INGRESADO NO ES CORRECTO");
            System.out.println("POR FAVOR INTENTELO NUEVAMENTE");
            userName = scanner.nextLine();
        }

        System.out.println("CONTRASENIA");
        String password = scanner.next();
        Usuario newUsuario = new Usuario(nombre, apellido, userName, password);

        System.out.println("SU CODIGO UNICO ES: " + newUsuario.getUuidCodigo().toString());
        System.out.println("(GUARDE ESTE CODIGO YA QUE SE MUESTRA POR UNICA VEZ)");

        guardarNuevoUsuarioEnArchivo(newUsuario);
        inicioPantalla();
    }

    public static void login() {
        BC();
        int intentos = 3;
        Scanner scanner = new Scanner(System.in);
        Usuario login = new Usuario();
        do {
            System.out.println("-------LOGIN-------");
            System.out.println("EMAIL: ");
            String email = scanner.next();
            System.out.println("PASSWORD: ");
            String password = scanner.next();

            login = buscarUsuarioPorMailContrasenia(email, password);

            if (login != null) {
                intentos = -1;
            } else {
                System.out.println("EMAIL O CONTRASENIA INCORRECTO");
                System.out.println("INTENTELO NUEVAMENTE");
                intentos--;
            }
        } while (intentos > 0);

        if (intentos == -1) {
            System.out.println("INGRESE SU CODIGO UNICO");
            String codigo = scanner.next();
            if (codigo.compareTo(login.getUuidCodigo().toString()) == 0) {
                System.out.println("CODIGO CORRECTO!!!");
                mainMenu(login);
            } else {
                System.out.println("CODIGO INCORRECTO...");
                inicioPantalla();
            }
        } else {
            inicioPantalla();
        }
    }


    public static void inicioPantalla() {
        BC();
        System.out.println("------BIENVENIDO!!!------");
        System.out.println("1. LOGIN");
        System.out.println("2. SIGN UP");
        System.out.println("3. SALIR");

        int option = validadorOpciones(1, 3);

        switch (option) {
            case 1 -> login();
            case 2 -> registrarse();
            case 3 -> hastaLuego();
        }
    }

    public static void mainMenu(Usuario usuario) {
        List<Usuario> listaUsuario = listaUsuariosDesdeJson();
        List<Transferencia> listaTransferencias = listaTransferenciaDesdeJson(transferenciaPath);
        List<Transferencia> listaBlockchain = listaTransferenciaDesdeJson(blockchainPath);

        BC();
        System.out.println("------MAIN MENU------");
        System.out.println("1. MOSTRAR LA LISTA DE USUARIOS");
        System.out.println("2. REALIZAR UNA TRANSFERENCIA NUEVA");
        System.out.println("3. CONSULTAR EL ESTADO DE LA CUENTA");
        System.out.println("4. TRANSFERENCIAS ORIGINADAS PENDIENTES");
        System.out.println("5. TRANSFERENCIAS DE TERCEROS PENDIENTES A VALIDAR");
        System.out.println("6. HISTORIAL DE TRANSACCIONES");
        System.out.println("7. SALIR");

        int option = validadorOpciones(1, 7);

        switch (option) {
            case 1 -> listaUsuarios(usuario, listaUsuario);
            case 2 -> realizarNuevaTransferencia(usuario, listaUsuario);
            case 3 -> estadoDeLACuenta(usuario);
            case 4 -> transferenciasDelUsuarioPendientes(usuario, listaUsuario, listaTransferencias);
            case 5 -> transferenciasPendientesAValidar(usuario, listaUsuario, listaTransferencias);
            case 6 -> historialTransferencias(usuario, listaUsuario, listaBlockchain);
            case 7 -> inicioPantalla();
        }
    }

    public static void estadoDeLACuenta(Usuario usuario) {
        BC();
        System.out.println("ESTADO DE LA CUENTA DE " + usuario.getNombre().toUpperCase()
                + " " + usuario.getApellido().toUpperCase());
        System.out.println("USTED TIENE: " + usuario.getUtnCoins() + " UTN COINS DISPONIBLES");
        presioneTeclaParaContinuar();
        mainMenu(usuario);
    }

    public static void realizarNuevaTransferencia(Usuario usuario, List<Usuario> listaUsuario) {
        System.out.println("INGRESE EL CODIGO UNICO DEL USUARIO DESTINO: ");
        Scanner scanner = new Scanner(System.in);
        try {
            UUID codigo = UUID.fromString(scanner.nextLine());
            if (usuario.getUuidCodigo().equals(codigo)) {
                System.out.println("CODIGO INVALIDO...");
            } else {
                Usuario destino = findUserbyUTCcode(codigo, listaUsuario);
                if (destino == null) {
                    System.out.println("USUARIO NO ENCONTRADO ....");
                } else {
                    System.out.println("USUARIO ENCONTRADO!!!!");
                    System.out.println("|||| " + destino.getNombre().toUpperCase()
                            + " " + destino.getApellido().toUpperCase() + " ||||");

                    System.out.println("\nSALDO DISPONIBLE PARA TRANSFERIR: " + usuario.getUtnCoins());
                    System.out.println("INGRESE EL MONTO A TRANSFERIR: ");
                    double monto = scanner.nextDouble();

                    while (monto > usuario.getUtnCoins()) {
                        System.out.println("EL MONTO ES SUPERIOR A SU SALDO DISPONIBLE");
                        System.out.println("INTENTE NUEVAMENTE: ");
                        monto = scanner.nextDouble();
                    }
                    System.out.println("TRANSFERENCIA DE " + monto + " UTC COINS A " + destino.getNombre().toUpperCase());
                    System.out.println("CONFIRMA LA TRANSFERENCIA?");
                    System.out.println("1. SI");
                    System.out.println("2. NO");
                    int opcion = validadorOpciones(1, 2);

                    if (opcion == 1) {
                        Transferencia nueva = new Transferencia(usuario.getUuidCodigo(), destino.getUuidCodigo(), monto);
                        guardarTransferenciaEnJson(nueva);
                        System.out.println("TRANSFERENCIA CREADA EXITOSAMENTE");
                        System.out.println("CODIGO DE LA TRANSFERENCIA: " + nueva.getUniqueID());
                        modificarUTCoinsDeUsuario(usuario, monto);
                        usuario.setUtnCoins(usuario.getUtnCoins() - monto);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("CODIGO INVALIDO...");
            //e.printStackTrace();
        }
        presioneTeclaParaContinuar();
        mainMenu(usuario);
    }

    public static void listaUsuarios(Usuario usuario, List<Usuario> lista) {
        System.out.println("|NOMBRE|\t|APELLIDO|\t|CODIGO UNICO|");
        for (Usuario user : lista) {
            if (!user.equals(usuario)) {
                System.out.println(user.getNombre() + "\t\t"
                        + user.getApellido() + "\t\t"
                        + user.getUuidCodigo().toString());
            }
        }
        presioneTeclaParaContinuar();
        mainMenu(usuario);
    }

    public static Usuario findUserbyUTCcode(UUID codigo, List<Usuario> listaUsuario) {
        Usuario encontrado = null;
        for (Usuario user : listaUsuario) {
            if (user.getUuidCodigo().equals(codigo)) {
                encontrado = user;
                break;
            }
        }
        return encontrado;
    }

    public static Usuario buscarUsuarioPorMailContrasenia(String email, String contrasena) {
        Usuario encontrado = null;
        List<Usuario> listaUsuario = listaUsuariosDesdeJson();

        for (Usuario usuario : listaUsuario) {
            if (usuario.getEmail().equals(email) && usuario.getPassword().equals(contrasena)) {
                encontrado = usuario;
            }
        }
        return encontrado;
    }


    public static void transferenciasDelUsuarioPendientes(Usuario usuario, List<Usuario> listaUsuario, List<Transferencia> listaTransferencia) {

        if (listaTransferencia.isEmpty()) {
            System.out.println("---- NO HAY TRANFERENCIAS PENDIENTES ----");
        } else {
            for (Transferencia t : listaTransferencia) {
                if (t.getOrigen().equals(usuario.getUuidCodigo())) {
                    Usuario destino = findUserbyUTCcode(t.getDestino(), listaUsuario);
                    System.out.println(usuario.getNombre().toUpperCase() + " --> "
                            + destino.getNombre().toUpperCase()
                            + ": " + t.getMonto() + " UTNcoins");
                }
            }
        }

        presioneTeclaParaContinuar();
        mainMenu(usuario);
    }

    public static void transferenciasPendientesAValidar(Usuario usuario, List<Usuario> listaUsuario, List<Transferencia> listaTransferencias) {
        List<Transferencia> aValidar = new ArrayList<>();

        int posicion = 1;
        for (Transferencia t : listaTransferencias) {
            List<UUID> listArray = Arrays.asList(t.getValidaciones());
            if (!t.getOrigen().equals(usuario.getUuidCodigo())
                    && !t.getDestino().equals(usuario.getUuidCodigo())) {
                if (!listArray.toString().contains(usuario.getUuidCodigo().toString())) {
                    aValidar.add(t);
                    Usuario origen = findUserbyUTCcode(t.getOrigen(), listaUsuario);
                    Usuario destino = findUserbyUTCcode(t.getDestino(), listaUsuario);
                    System.out.println(posicion + ". " + origen.getNombre().toUpperCase() + " --> "
                            + destino.getNombre().toUpperCase()
                            + ": " + t.getMonto() + " UTNcoins");
                    posicion++;
                }
            }
        }

        if (!aValidar.isEmpty()) {
            System.out.println("DESEA VALIDAR ALGUNA TRANSACCION?");
            System.out.println("1. SI");
            System.out.println("2. NO");

            int resp = validadorOpciones(1, 2);
            if (resp == 1) {
                System.out.println("ELIJA UNA TRANSFERENCIA DE TERCEROS A VALIDAR");
                int opcion = validadorOpciones(1, aValidar.size());

                Transferencia t = aValidar.get(opcion - 1);
                listaTransferencias.remove(t);
                t.agregarUsuario(usuario.getUuidCodigo());

                if (t.getContador() < 4) {
                    listaTransferencias.add(t);
                } else {
                    t.setEstado(Estado.APROBADA);
                    guardarTransferenciaEnBlockchain(t);
                    Usuario destino = findUserbyUTCcode(t.getDestino(), listaUsuario);
                    modificarUTCoinsDeUsuario(destino, t.getMonto()*(-1));
                }
                guardarListaTransferenciasAJson(listaTransferencias);
                System.out.println("VALIDACION EXITOSA!!!!!!");
            }
        } else {
            System.out.println("NO HAY TRANSFERENCIAS POR VALIDAR....");
        }
        presioneTeclaParaContinuar();
        mainMenu(usuario);
    }


    public static void historialTransferencias(Usuario usuario, List<Usuario> listaUsuario, List<Transferencia> listaBlockchain) {
        List<Transferencia> blockchainPropioDelUsuario = new ArrayList<>();

        for (Transferencia t : listaBlockchain) {
            if (t.getOrigen().equals(usuario.getUuidCodigo())
                    || t.getDestino().equals(usuario.getUuidCodigo())) {
                blockchainPropioDelUsuario.add(t);
            }
        }
        if (blockchainPropioDelUsuario.isEmpty()) {
            System.out.println(".....HISTORIAL VACIO.....");
        } else {
            for (Transferencia tr : blockchainPropioDelUsuario) {
                Usuario destino = findUserbyUTCcode(tr.getDestino(), listaUsuario);
                System.out.println(usuario.getNombre().toUpperCase() + "\t "
                        + destino.getNombre().toUpperCase() + "\t "
                        + tr.getMonto());
            }

        }
            presioneTeclaParaContinuar();
            mainMenu(usuario);

    }

    /////// ARCHIVOS //////
    public static void guardarNuevoUsuarioEnArchivo(Usuario newUsuario) {
        File file = new File(usuarioPath);
        List<Usuario> lista = new ArrayList<>(listaUsuariosDesdeJson());
        Gson gson = new Gson();
        try {
            lista.add(newUsuario);
            Writer writer = Files.newBufferedWriter(Paths.get(usuarioPath));
            gson.toJson(lista, writer);
            writer.close();
        } catch (IOException e) {
            System.out.println("FILE CANNOT BE WRITTEN: " + e.getMessage());
        }
    }

    public static void guardarListaUsuarioAJson(List<Usuario> lista) {
        try {
            Gson gson = new Gson();
            Writer writer = Files.newBufferedWriter(Paths.get(usuarioPath));
            gson.toJson(lista, writer);
            writer.close();
        } catch (IOException e) {
            System.out.println("FILE CANNOT BE OPENED: " + e.getMessage());
        }
    }

    public static List<Usuario> listaUsuariosDesdeJson() {
        List<Usuario> lista = new ArrayList<>();
        File file = new File(usuarioPath);
        if (file.exists()) {
            try {
                Reader reader = Files.newBufferedReader(Paths.get(usuarioPath));
                Type collectionType = new TypeToken<Collection<Usuario>>() {
                }.getType();
                lista = new ArrayList<>(new Gson().fromJson(reader, collectionType));
                reader.close();
            } catch (IOException e) {
                System.out.println("FILE CANNOT BE OPENED: " + e.getMessage());
            }
        }
        return lista;
    }

    public static List<Transferencia> listaTransferenciaDesdeJson(String path) {
        List<Transferencia> lista = new ArrayList<>();
        File file = new File(path);
        if (file.exists()) {
            try {
                Reader reader = Files.newBufferedReader(Paths.get(path));
                Type collectionType = new TypeToken<Collection<Transferencia>>() {
                }.getType();
                lista = new ArrayList<>(new Gson().fromJson(reader, collectionType));
                reader.close();

            } catch (Exception e) {
                System.out.println("FILE CANNOT BE OPENED: " + e.getMessage());
            }
        }
        return lista;
    }

    public static void guardarTransferenciaEnJson(Transferencia nueva) {
        File file = new File(transferenciaPath);
        List<Transferencia> lista = new ArrayList<>(listaTransferenciaDesdeJson(transferenciaPath));
        Gson gson = new Gson();
        try {
            lista.add(nueva);
            Writer writer = Files.newBufferedWriter(Paths.get(transferenciaPath));
            gson.toJson(lista, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarListaTransferenciasAJson(List<Transferencia> lista) {
        List<Transferencia> listaTransferencia = new ArrayList<>(lista);
        File file = new File(transferenciaPath);
        if (file.exists()) {
            try {
                Gson gson = new Gson();
                Writer writer = Files.newBufferedWriter(Paths.get(transferenciaPath));
                gson.toJson(listaTransferencia, writer);
                writer.close();
            } catch (IOException e) {
                System.out.println("FILE CANNOT BE OPENED: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void modificarUTCoinsDeUsuario(Usuario usuario, double monto) {
        List<Usuario> lista = listaUsuariosDesdeJson();

        for (Usuario u : lista) {
            if (u.getUuidCodigo().equals(usuario.getUuidCodigo())) {
                u.setUtnCoins(u.getUtnCoins() - monto);
                break;
            }
        }
        guardarListaUsuarioAJson(lista);
    }

    public static void guardarTransferenciaEnBlockchain(Transferencia nueva) {
        File file = new File(blockchainPath);
        List<Transferencia> lista = new ArrayList<>(listaTransferenciaDesdeJson(blockchainPath));
        Gson gson = new Gson();
        try {
            lista.add(nueva);
            Writer writer = Files.newBufferedWriter(Paths.get(blockchainPath));
            gson.toJson(lista, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
