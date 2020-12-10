package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static com.company.Main.Departamentos;

public class Administrador {

    private String usuario = "Patron";
    private String contrasena = "12345";
    private boolean estadoVotacion = false;
    private boolean estadoInscripcion = true;

    public boolean isEstadoInscripcion() {
        return estadoInscripcion;
    }

    public void setEstadoInscripcion(boolean estadoInscripcion) {
        this.estadoInscripcion = estadoInscripcion;
    }

    public boolean votacion(int tipo){
        boolean estado = false;
        int eleccion;
        Scanner r = new Scanner(System.in);

        switch(tipo) {
            case 1:
                File archivo;
                FileWriter agregar;
                PrintWriter escribir;
                archivo = new File("estadoVotacion.txt");
                if (!archivo.exists()) {

                    try {
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo, "utf-8");
                        escribir.print(estado);
                        escribir.close();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        FileReader entrada = new FileReader("estadoVotacion.txt");
                        BufferedReader br = new BufferedReader(entrada);
                        Scanner sc = new Scanner(entrada);
                        while (sc.hasNextLine()) {
                            estado = sc.nextBoolean();
                        }
                        entrada.close();
                        if (estado == false) {
                            System.out.println("El sistema de votacion esta deshabilitado, desea habilitar el sistema de votacion?");
                            System.out.println("1. Habilitar\t\t\t2. No");
                            eleccion = r.nextInt();
                            if (eleccion == 1) {
                                estado = true;
                                setEstadoInscripcion(false);
                                try {
                                    escribir = new PrintWriter(archivo, "utf-8");
                                    escribir.print(estado);
                                    escribir.close();


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                System.out.println("El sistema de votacion esta abierto");
                            } else {
                                System.out.println("Habilitar un menu****");
                            }

                        } else if (estado == true) {
                            System.out.println("El sistema de votacion esta habilitado, desea deshabilitar el sistema de votacion?");
                            System.out.println("1. Deshabilitar\t\t\t2. No");
                            eleccion = r.nextInt();
                            if (eleccion == 1) {
                                estado = false;
                                setEstadoInscripcion(false);
                                try {
                                    escribir = new PrintWriter(archivo, "utf-8");
                                    escribir.print(estado);
                                    escribir.close();


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                System.out.println("El sistema de votacion esta cerrado");
                            } else {
                                System.out.println("Habilitar un menu****");
                            }
                        }


                    } catch (IOException e) {
                        System.out.println("No se encontro el archivo");
                    }
                }
                break;
            case 2:
                try {
                    FileReader entrada = new FileReader("estadoVotacion.txt");
                    BufferedReader br = new BufferedReader(entrada);
                    Scanner sc = new Scanner(entrada);
                    while (sc.hasNextLine()) {
                        estado = sc.nextBoolean();
                    }
                    entrada.close();
                } catch (IOException e) {
                    System.out.println("No se encontro el archivo");
                    votacion(1);
                }
                break;
            default:
                System.out.println("error");
        }
        return estado;
    }

    public void conteoVotosMenu(){
        Scanner r = new Scanner(System.in);
        int eleccion;
        estadoInscripcion = votacion(2);
        if(estadoVotacion == true){
            System.out.println("El sistema de votacion esta habilitado porfavor espere a que terminen las votaciones!");
            System.out.println("Desea acceder al menu de habilitacion/deshabilitacion de votacion? ");
            System.out.println("1. Si\t\t\t2. No");
            eleccion = r.nextInt();
            if(eleccion==1){
                votacion(1);
            }else{
                System.out.println("menu admin***");
            }
        }else if(estadoVotacion == false) {
            System.out.println("Bienvenido al sistema de conteo de votos");
            System.out.println("Que tipo de conteo desea realizar?");
            System.out.println("1. Alcaldia");
            System.out.println("2. Diputadura");
            System.out.println("3. Presidencia");
            eleccion = r.nextInt();
            switch(eleccion){
                case 1:
                    conteoAlcaldia(r);
                    break;
            }

        }
    }
    public void conteoAlcaldia(Scanner r){
        System.out.println(" ");
        int indiceDepartamento=0;
        String municipalidadPostulada="";
        ArrayList<String> nombreArchivos = new ArrayList<String>();
        ArrayList<String> nombres = new ArrayList<String>();
        ArrayList<String> apellidos = new ArrayList<String>();
        ArrayList<Integer> partidos = new ArrayList<Integer>();
        ArrayList<Integer> resultados = new ArrayList<Integer>();

        while (indiceDepartamento < 1 || indiceDepartamento > 18) {
            System.out.println("En que departamento quiere realizar el conteo? Elija el indice: ");
            System.out.println(" ");
            System.out.println("1. Atlantida\t\t2. Colon\t\t3. Comayagua\t\t4. Copan");
            System.out.println(" ");
            System.out.println("5. Cortes\t\t6. Choluteca\t\t7. El Paraiso\t\t8. Francisco Morazan");
            System.out.println(" ");
            System.out.println("9. Gracias a Dios\t\t10. Intibuca\t\t11. Islas de la Bahia\t\t12. La Paz");
            System.out.println(" ");
            System.out.println("13. Lempira\t\t14. Ocotepeque\t\t15. Olancho\t\t16. Santa Barbara");
            System.out.println(" ");
            System.out.println("17. Valle\t\t18. Yoro");
            indiceDepartamento = r.nextInt();
            if (indiceDepartamento > 0 || indiceDepartamento < 19) {
                municipalidadPostulada=Departamentos(indiceDepartamento);
            } else {
                System.out.println("***Su eleccion no es valida, Porfavor ingrese el indice de uno de los departamentos***");
            }
        }
        try {
            FileReader entrada = new FileReader("nombreArchivoAlcaldia"+municipalidadPostulada+".txt");
            BufferedReader br = new BufferedReader(entrada);
            Scanner sc = new Scanner(entrada);
            while (sc.hasNextLine()) {
                nombreArchivos.add(sc.nextLine());
            }
            entrada.close();
        } catch (IOException e) {
            System.out.println("No se encontro el archivo");
        }
        try {
            FileReader entrada = new FileReader("nombresAlcaldia"+municipalidadPostulada+".txt");
            BufferedReader br = new BufferedReader(entrada);
            Scanner sc = new Scanner(entrada);
            while (sc.hasNextLine()) {
                nombres.add(sc.nextLine());
            }
            entrada.close();
        } catch (IOException e) {
            System.out.println("No se encontro el archivo");
        }
        try {
            FileReader entrada = new FileReader("apellidosAlcaldia"+municipalidadPostulada+".txt");
            BufferedReader br = new BufferedReader(entrada);
            Scanner sc = new Scanner(entrada);
            while (sc.hasNextLine()) {
                apellidos.add(sc.nextLine());
            }
            entrada.close();
        } catch (IOException e) {
            System.out.println("No se encontro el archivo");
        }
        try {
            FileReader entrada = new FileReader("partidosArchivoAlcaldia"+municipalidadPostulada+".txt");
            BufferedReader br = new BufferedReader(entrada);
            Scanner sc = new Scanner(entrada);
            while (sc.hasNextLine()) {
                partidos.add(sc.nextInt());
            }
            entrada.close();
        } catch (IOException e) {
            System.out.println("No se encontro el archivo");
        }

        for(int x = 0;x<nombreArchivos.size();x++){
            try {
                FileReader entrada = new FileReader("votosAlcaldia"+municipalidadPostulada+nombreArchivos.get(x)+".txt");
                BufferedReader br = new BufferedReader(entrada);
                Scanner sc = new Scanner(entrada);
                while (sc.hasNextLine()) {
                    resultados.add(sc.nextInt());
                }
                entrada.close();
            } catch (IOException e) {
                System.out.println("No se encontro el archivo");
            }
        }





    }

}
