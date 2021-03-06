package com.company;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public boolean estadoV = false;

    public static void main(String[] args) {
        // write your code here
        menuPrincipal();


    }
    public static void menuPrincipal(){
        Scanner r = new Scanner(System.in);
        int eleccion;
        boolean repetir = true;
        while(repetir==true){
            System.out.println("Bienvenido al sistema de votacion virtual");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println(" ");
            System.out.println("Eleja el indice de la accion que desea realizar: ");
            System.out.println(" ");
            System.out.println("1. Votar");
            System.out.println("2. Inscripciones de candidatura");
            System.out.println("3. Administrador");
            eleccion = r.nextInt();
            switch(eleccion){
                case 1: votacionAlcaldia();
                    break;
                case 2: tipoCandidato();
                    break;
                case 3:
                    Administrador Ad = new Administrador();
                    Ad.login(r);
                    break;
                default:
                    System.out.println(" ");
                    System.out.println("***Su eleccion no es valida***");
            }

        }
    }

    public static void tipoCandidato(){
        Scanner r = new Scanner(System.in);
        Administrador patron = new Administrador();
        if(patron.isEstadoInscripcion()==false){
            System.out.println("El sistema de inscripcion esta deshabilitado");
            menuPrincipal();
        } else {
            System.out.println("\t\tBienvenido a las Inscripciones de Candidatura");
            System.out.println("\t\t---------------------------------------------");
            System.out.println(" ");
            int eleccion;
            boolean elegible;
            System.out.println("Porfavor a continuacion ingrese el indice del tipo de candidatura a la que aspira:");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("1. Alcalde");
            System.out.println("2. Diputadura");
            System.out.println("3. Presidencia");
            System.out.println(" ");
            eleccion = r.nextInt();
            switch (eleccion) {
                case 1:
                    elegible = elegibilidadAlcaldia();
                    if (elegible == true) {
                        inscripcionAlcaldia();
                    } else {
                        System.out.println("Usted no es elegible para la candidatura de la Alcaldia Municipal.");
                    }
                    break;
                case 2:
                    elegible = elegibilidadDiputadura();
                    if (elegible == true) {
                        inscripcionDiputadura();
                    } else {
                        System.out.println("Usted no es elegible para la candidatura de diputadura.");
                    }
                    break;
                case 3:
                    elegible = elegibilidadPresidencial();
                    if (elegible == true) {
                        inscripcionPresidencial();
                    } else {
                        System.out.println("Usted no es elegible para la candidatura presidencial.");
                    }
                    break;
                default:
                    System.out.println(" ");
            }
        }
    }

    public static void votacionAlcaldia() {
        Scanner r = new Scanner(System.in);
        boolean repetir = true,existe = true;
        int numeroCandidatos = 0, indiceDepartamento=0,voto,tipo=1;
        String municipalidadPostulada = "ninguna", cedula;
        String nombreArchivo = "";
        Administrador patron = new Administrador();
        if(patron.isEstadoVotacion()==false){
            System.out.println("El sistema de votacion no esta habilitado!");
            menuPrincipal();
        } else if(patron.isEstadoVotacion()==true){
            System.out.println("Sistema de voto virtual para la Alcaldia");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println(" ");
            while (indiceDepartamento < 1 || indiceDepartamento > 18) {
                System.out.println("En que departamento del pais reside? Elija el indice: ");
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
                    municipalidadPostulada = Departamentos(indiceDepartamento);
                } else {
                    System.out.println("***Su eleccion no es valida, Porfavor ingrese el indice de uno de los departamentos***");
                }
            }
            File archivo;
            FileWriter agregar;
            PrintWriter escribir;

            r.nextLine();
            System.out.println("Porfavor ingrese su numero de identidad: en el siguiente formato: (0000-1990-01234)");
            cedula = r.nextLine();
            existe = verificarListaVotante(r, cedula, tipo, municipalidadPostulada);
            if (existe == true) {
                System.out.println("Usted ya ha votado en la municipalidad " + municipalidadPostulada + ".");
                menuPrincipal();
            } else {
                AgregarListaVotante(r, tipo, municipalidadPostulada, cedula);
                try {
                    FileReader entrada = new FileReader("cantidadCandidatos" + municipalidadPostulada + ".txt");
                    BufferedReader br = new BufferedReader(entrada);
                    Scanner sc = new Scanner(entrada);
                    while (sc.hasNextLine()) {
                        numeroCandidatos = sc.nextInt();
                    }
                    entrada.close();
                } catch (IOException e) {
                    System.out.println("No se encontro el archivo");
                }
                String temp[] = new String[numeroCandidatos];
                String temp2[] = new String[numeroCandidatos];
                String temp3[] = new String[numeroCandidatos];
                String temp4[] = new String[numeroCandidatos];
                String nombres[] = new String[numeroCandidatos];
                String apellidos[] = new String[numeroCandidatos];
                String partidos[] = new String[numeroCandidatos];
                String archivos[] = new String[numeroCandidatos];

                try {
                    int contador = 0;
                    FileReader entrada = new FileReader("nombresAlcaldia" + municipalidadPostulada + ".txt");
                    BufferedReader br = new BufferedReader(entrada);
                    while (temp != null) {
                        temp[contador] = br.readLine();
                        nombres[contador] = temp[contador];
                        contador++;
                        if (contador == numeroCandidatos) {
                            temp = null;
                        }
                    }
                    entrada.close();

                } catch (IOException e) {
                    System.out.println("No se encontro el archivo");
                }
                try {
                    int contador = 0;
                    FileReader entrada = new FileReader("apellidosAlcaldia" + municipalidadPostulada + ".txt");
                    BufferedReader br = new BufferedReader(entrada);
                    while (temp2 != null) {
                        temp2[contador] = br.readLine();
                        apellidos[contador] = temp2[contador];
                        contador++;
                        if (contador == numeroCandidatos) {
                            temp2 = null;
                        }
                    }
                    entrada.close();

                } catch (IOException e) {
                    System.out.println("No se encontro el archivo");
                }
                try {
                    int contador = 0;
                    FileReader entrada = new FileReader("nombreArchivoAlcaldia" + municipalidadPostulada + ".txt");
                    BufferedReader br = new BufferedReader(entrada);
                    while (temp4 != null) {
                        temp4[contador] = br.readLine();
                        archivos[contador] = temp4[contador];
                        contador++;
                        if (contador == numeroCandidatos) {
                            temp4 = null;
                        }
                    }
                    entrada.close();

                } catch (IOException e) {
                    System.out.println("No se encontro el archivo");
                }
                try {
                    int contador = 0;
                    FileReader entrada = new FileReader("partidosAlcaldia" + municipalidadPostulada + ".txt");
                    BufferedReader br = new BufferedReader(entrada);
                    while (temp3 != null) {
                        temp3[contador] = br.readLine();
                        if (temp3[contador].equals("1")) {
                            partidos[contador] = "Partido Liberal";
                        } else if (temp3[contador].equals("2")) {
                            partidos[contador] = "Partido Nacional";
                        } else if (temp3[contador].equals("3")) {
                            partidos[contador] = "Partido Libre";
                        }
                        contador++;
                        if (contador == numeroCandidatos) {
                            temp3 = null;
                        }
                    }
                    entrada.close();

                } catch (IOException e) {
                    System.out.println("No se encontro el archivo");
                }
                while (repetir == true) {
                    System.out.println("A continuacion porfavor elija el indice del candidato que desea: ");
                    System.out.println("Los candidatos disponibles en la municipalidad de: " + municipalidadPostulada + " son: ");
                    System.out.println(" ");
                    if (numeroCandidatos == 1) {
                        System.out.println("1. " + nombres[0] + " " + apellidos[0] + " " + partidos[0]);
                        voto = r.nextInt();
                        repetir = confirmarVoto(r, voto, nombres, apellidos, partidos);
                        if (repetir == true) {
                            nombreArchivo = archivos[voto - 1];
                            castVote(tipo, municipalidadPostulada, nombreArchivo, indiceDepartamento);
                            repetir = false;
                        } else {
                            repetir = true;
                        }


                    } else if (numeroCandidatos == 2) {
                        System.out.println("1. " + nombres[0] + " " + apellidos[0] + " " + partidos[0]);
                        System.out.println("2. " + nombres[1] + " " + apellidos[1] + " " + partidos[1]);
                        voto = r.nextInt();
                        repetir = confirmarVoto(r, voto, nombres, apellidos, partidos);
                        if (repetir == true) {
                            nombreArchivo = archivos[voto - 1];
                            castVote(tipo, municipalidadPostulada, nombreArchivo, indiceDepartamento);
                            repetir = false;
                        } else {
                            repetir = true;
                        }

                    } else if (numeroCandidatos == 3) {
                        System.out.println("1. " + nombres[0] + " " + apellidos[0] + " " + partidos[0]);
                        System.out.println("2. " + nombres[1] + " " + apellidos[1] + " " + partidos[1]);
                        System.out.println("3. " + nombres[2] + " " + apellidos[2] + " " + partidos[2]);
                        voto = r.nextInt();
                        repetir = confirmarVoto(r, voto, nombres, apellidos, partidos);
                        if (repetir == true) {
                            nombreArchivo = archivos[voto - 1];
                            castVote(tipo, municipalidadPostulada, nombreArchivo, indiceDepartamento);
                            repetir = false;
                        } else {
                            repetir = true;
                        }

                    }
                }
            }
        }
        votacionDiputadura();
    }
    public static void votacionPresidencia(){
        Scanner r = new Scanner(System.in);
        String cedula, municipalidadPostulada = "ninguna";
        boolean repetir = true;
        boolean existe = true;
        int numeroCandidatos = 0,voto,tipo=2,indiceDepartamento=0;
        String tipoCandidatura = "Presidencia";
        String nombreArchivo = "";

        System.out.println("Sistema de voto virtual para la Presidencia");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(" ");

        File archivo;
        FileWriter agregar;
        PrintWriter escribir;

        System.out.println("Porfavor ingrese su numero de identidad: en el siguiente formato: (0000-1990-01234)");
        cedula = r.nextLine();
        existe=verificarListaVotante(r,cedula,tipo,municipalidadPostulada);
        if(existe==true){
            System.out.println("Usted ya ha votado para la presidencia.");
            menuPrincipal();
        }else {
            AgregarListaVotante(r, tipo, municipalidadPostulada, cedula);
        }
            try {
                FileReader entrada = new FileReader("cantidadCandidatos" + municipalidadPostulada + ".txt");
                BufferedReader br = new BufferedReader(entrada);
                Scanner sc = new Scanner(entrada);
                while (sc.hasNextLine()) {
                    numeroCandidatos = sc.nextInt();
                }
                entrada.close();
            }catch (IOException e){
                System.out.println("No se encontro el archivo");
            }
        try {
            FileReader entrada = new FileReader("cantidadCandidatos" + tipoCandidatura + ".txt");
            BufferedReader br = new BufferedReader(entrada);
            Scanner sc = new Scanner(entrada);
            while (sc.hasNextLine()) {
                numeroCandidatos = sc.nextInt();
            }
            entrada.close();
        }catch (IOException e){
            System.out.println("No se encontro el archivo");
        }
        String temp[]=new String[numeroCandidatos];
        String temp2[]=new String[numeroCandidatos];
        String temp3[]=new String[numeroCandidatos];
        String temp4[]=new String[numeroCandidatos];
        String nombres[]=new String[numeroCandidatos];
        String apellidos[]=new String[numeroCandidatos];
        String partidos[]=new String[numeroCandidatos];
        String archivos[]=new String[numeroCandidatos];

        try{
            int contador = 0;
            FileReader entrada = new FileReader("nombres"+tipoCandidatura+".txt");
            BufferedReader br = new BufferedReader(entrada);
            while(temp!=null){
                temp[contador] = br.readLine();
                nombres[contador]=temp[contador];
                contador++;
                if(contador==numeroCandidatos){
                    temp=null;
                }
            }
            entrada.close();

        }catch(IOException e){
            System.out.println("No se encontro el archivo");
        }
        try{
            int contador = 0;
            FileReader entrada = new FileReader("apellidos"+tipoCandidatura+".txt");
            BufferedReader br = new BufferedReader(entrada);
            while(temp2!=null){
                temp2[contador] = br.readLine();
                apellidos[contador]=temp2[contador];
                contador++;
                if(contador==numeroCandidatos){
                    temp2=null;
                }
            }
            entrada.close();

        }catch(IOException e){
            System.out.println("No se encontro el archivo");
        }
        try{
            int contador = 0;
            FileReader entrada = new FileReader("nombreArchivo"+tipoCandidatura+".txt");
            BufferedReader br = new BufferedReader(entrada);
            while(temp4!=null){
                temp4[contador] = br.readLine();
                archivos[contador]=temp4[contador];
                contador++;
                if(contador==numeroCandidatos){
                    temp4=null;
                }
            }
            entrada.close();

        }catch(IOException e){
            System.out.println("No se encontro el archivo");
        }
        try{
            int contador = 0;
            FileReader entrada = new FileReader("partidos"+tipoCandidatura+".txt");
            BufferedReader br = new BufferedReader(entrada);
            while(temp3!=null){
                temp3[contador] = br.readLine();
                if(temp3[contador].equals("1")){
                    partidos[contador]="Partido Liberal";
                }else if(temp3[contador].equals("2")){
                    partidos[contador]="Partido Nacional";
                }else if(temp3[contador].equals("3")){
                    partidos[contador]="Partido Libre";
                }
                contador++;
                if(contador==numeroCandidatos){
                    temp3=null;
                }
            }
            entrada.close();

        }catch(IOException e){
            System.out.println("No se encontro el archivo");
        }
        while(repetir == true){
            System.out.println("A continuacion porfavor elija el indice del candidato que desea: ");
            System.out.println("Los candidatos disponibles en la municipalidad de: " + tipoCandidatura + " son: ");
            System.out.println(" ");
            if (numeroCandidatos == 1) {
                System.out.println("1. " + nombres[0] + " " + apellidos[0] + " " + partidos[0]);
                voto = r.nextInt();
                repetir = confirmarVoto(r,voto,nombres,apellidos,partidos);
                if(repetir == true) {
                    nombreArchivo = archivos[voto - 1];
                    castVote(tipo,tipoCandidatura, nombreArchivo,indiceDepartamento);
                    repetir = false;
                }else{
                    repetir = true;
                }


            } else if (numeroCandidatos == 2) {
                System.out.println("1. " + nombres[0] + " " + apellidos[0] + " " + partidos[0]);
                System.out.println("2. " + nombres[1] + " " + apellidos[1] + " " + partidos[1]);
                voto = r.nextInt();
                repetir = confirmarVoto(r,voto,nombres,apellidos,partidos);
                if(repetir == true) {
                    nombreArchivo = archivos[voto - 1];
                    castVote(tipo,tipoCandidatura, nombreArchivo,indiceDepartamento);
                    repetir = false;
                }else{
                    repetir = true;
                }

            } else if (numeroCandidatos == 3) {
                System.out.println("1. " + nombres[0] + " " + apellidos[0] + " " + partidos[0]);
                System.out.println("2. " + nombres[1] + " " + apellidos[1] + " " + partidos[1]);
                System.out.println("3. " + nombres[2] + " " + apellidos[2] + " " + partidos[2]);
                voto = r.nextInt();
                repetir = confirmarVoto(r,voto,nombres,apellidos,partidos);
                if(repetir == true) {
                    nombreArchivo = archivos[voto - 1];
                    castVote(tipo,tipoCandidatura, nombreArchivo,indiceDepartamento);
                    repetir = false;
                }else{
                    repetir = true;
                }

            }
        }
    }
    public static void votacionDiputadura(){
        Scanner r = new Scanner(System.in);
        boolean terminar = false,existe = true;
        int numeroCandidatos = 0, indiceDepartamento=0,voto,tipo=3;
        String municipalidadPostulada = "ninguna", cedula;
        String nombreArchivo = "";
        System.out.println("Sistema de voto virtual para la Diputadura");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(" ");
        while (indiceDepartamento < 1 || indiceDepartamento > 18) {
            System.out.println("En que departamento del pais reside? Elija el indice: ");
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
            if (indiceDepartamento < 1 || indiceDepartamento > 18) {
                System.out.println("***Su eleccion no es valida, Porfavor ingrese el indice de uno de los departamentos***");
            }
        }
        File archivo;
        FileWriter agregar;
        PrintWriter escribir;

        r.nextLine();
        System.out.println("Porfavor ingrese su numero de identidad: en el siguiente formato: (0000-1990-01234)");
        cedula = r.nextLine();
        existe=verificarListaVotante(r,cedula,tipo,municipalidadPostulada);
        if(existe==true){
            System.out.println("Usted ya ha votado para la Diputadura");
            menuPrincipal();
        }else{
            try {
                FileReader entrada = new FileReader("cantidadCandidatos" + indiceDepartamento + ".txt");
                BufferedReader br = new BufferedReader(entrada);
                Scanner sc = new Scanner(entrada);
                while (sc.hasNextLine()) {
                    numeroCandidatos = sc.nextInt();
                }
                entrada.close();
            }catch (IOException e){
                System.out.println("No se encontro el archivo cantidadCandidatos");
            }
            ArrayList<String> infoCandidatos = new ArrayList<String>();
            ArrayList<String> nArchivos = new ArrayList<String>();

            try{
                FileReader entrada = new FileReader("candidatosDiputadura" + indiceDepartamento + ".txt");
                BufferedReader br = new BufferedReader(entrada);
                Scanner sc = new Scanner(entrada);
                while(sc.hasNextLine()){
                    infoCandidatos.add(sc.nextLine());
                }
                entrada.close();

            }catch(IOException e){
                System.out.println("No se encontro el archivo candidatoDiputadura");
            }
            String temp[] = new String[numeroCandidatos];
            String archivos[]=new String[numeroCandidatos];
            try{
                int contador = 0;
                FileReader entrada = new FileReader("nombreArchivoDiputadura"+indiceDepartamento+".txt");
                BufferedReader br = new BufferedReader(entrada);
                while(temp!=null){
                    temp[contador] = br.readLine();
                    archivos[contador]=temp[contador];
                    nArchivos.add(contador,archivos[contador]);
                    contador++;
                    if(contador==numeroCandidatos){
                        temp=null;
                    }
                }
                entrada.close();

            }catch(IOException e){
                System.out.println("No se encontro el archivo nombreArchivoDiputadura");
            }

            System.out.println("A continuacion porfavor elija el indice del candidato que desea: ");
            terminar = votoDiputados(r,indiceDepartamento,tipo,cantidadDiputados(indiceDepartamento),infoCandidatos,nArchivos);
            if(terminar == true){
                System.out.println("Sus votos han sido agregados.");
                AgregarListaVotante(r,tipo,municipalidadPostulada,cedula);
            } else{
                System.out.println("error");
                votacionDiputadura();
            }
        }
        votacionPresidencia();
    }

    public static boolean confirmarVoto(Scanner r,int voto,String[]nombres,String[]apellidos,String[]partidos){
        boolean confirmacion=false;
        int eleccion;
        System.out.println("Porfavor confirme su voto: "+nombres[voto-1]+" "+apellidos[voto-1]+" "+partidos[voto-1]);
        System.out.println("1. Confirmar\t\t\t2. Regresar");
        eleccion = r.nextInt();
        if(eleccion==1){
            confirmacion = true;
        }else{
            confirmacion = false;
        }
        return confirmacion;
    }
    public static void castVote(int tipo,String municipalidadPostulada,String nombreArchivo,int indiceDepartamento){
        int vototemp=0;
        File archivo;
        FileWriter agregar;
        PrintWriter escribir;
            switch(tipo) {
                case 1:
                try {
                    FileReader entrada = new FileReader("votosAlcaldia" + municipalidadPostulada + nombreArchivo + ".txt");
                    BufferedReader br = new BufferedReader(entrada);
                    Scanner sc = new Scanner(entrada);
                    while (sc.hasNextLine()) {
                        vototemp = sc.nextInt();
                    }
                    entrada.close();
                } catch (IOException e) {
                    System.out.println("No se encontro el archivo");
                }
                archivo = new File("votosAlcaldia" + municipalidadPostulada + nombreArchivo + ".txt");
                try {
                    escribir = new PrintWriter(archivo, "utf-8");
                    escribir.print(vototemp + 1);
                    escribir.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
                case 2:
                    try {
                        FileReader entrada = new FileReader("votos" + municipalidadPostulada + nombreArchivo + ".txt");
                        BufferedReader br = new BufferedReader(entrada);
                        Scanner sc = new Scanner(entrada);
                        while (sc.hasNextLine()) {
                            vototemp = sc.nextInt();
                        }
                        entrada.close();
                    } catch (IOException e) {
                        System.out.println("No se encontro el archivo");
                    }
                    archivo = new File("votos" + municipalidadPostulada + nombreArchivo + ".txt");
                    try {
                        escribir = new PrintWriter(archivo, "utf-8");
                        escribir.print(vototemp + 1);
                        escribir.close();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        FileReader entrada = new FileReader("votosDiputadura" + indiceDepartamento + nombreArchivo + ".txt");
                        BufferedReader br = new BufferedReader(entrada);
                        Scanner sc = new Scanner(entrada);
                        while (sc.hasNextLine()) {
                            vototemp = sc.nextInt();
                        }
                        entrada.close();
                    } catch (IOException e) {
                        System.out.println("No se encontro el archivo");
                    }
                    archivo = new File("votosDiputadura" + indiceDepartamento + nombreArchivo + ".txt");
                    try {
                        escribir = new PrintWriter(archivo, "utf-8");
                        escribir.print(vototemp + 1);
                        escribir.close();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("error");
                    System.exit(0);
            }
        System.out.println("Felicidades, su voto ha sido agregado");
        }

    public static void AgregarListaVotante(Scanner r,int tipo,String municipalidadPostulada,String cedula){


        File archivo;
        FileWriter agregar;
        PrintWriter escribir;
        switch(tipo){
            case 1:
                archivo = new File("listaVotos"+municipalidadPostulada+".txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(cedula+"\n");
                        escribir.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        agregar = new FileWriter(archivo,true);
                        escribir = new PrintWriter(agregar);
                        escribir.print(cedula+"\n");
                        escribir.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                break;
            case 2:
                archivo = new File("listaVotosPresidencia.txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(cedula+"\n");
                        escribir.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        agregar = new FileWriter(archivo,true);
                        escribir = new PrintWriter(agregar);
                        escribir.print(cedula+"\n");
                        escribir.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                break;
            case 3:
                archivo = new File("listaVotosDiputadura.txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(cedula+"\n");
                        escribir.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        agregar = new FileWriter(archivo,true);
                        escribir = new PrintWriter(agregar);
                        escribir.print(cedula+"\n");
                        escribir.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                break;
            default:
                System.out.println("error");
        }
    }
    public static boolean verificarListaVotante(Scanner r, String cedula, int tipo,String municipalidadPostulada) {
        boolean existe = false;
        ArrayList<String> listaCedula = new ArrayList<String>();
        switch (tipo) {
            case 1:

                try {

                    FileReader entrada = new FileReader("listaVotos" + municipalidadPostulada + ".txt");
                    BufferedReader br = new BufferedReader(entrada);
                    Scanner sc = new Scanner(entrada);
                    while (sc.hasNextLine()) {
                        listaCedula.add(sc.nextLine());

                    }
                    entrada.close();

                } catch (IOException e) {
                    System.out.println("No se encontro el archivo");
                    existe = false;
                    return existe;
                }
                if (listaCedula.contains(cedula) == true) {
                    existe = true;
                    return existe;
                } else {
                    existe = false;

                }
                break;
            case 2:

                try {
                    FileReader entrada = new FileReader("listaVotosPresidencia.txt");
                    BufferedReader br = new BufferedReader(entrada);
                    Scanner sc = new Scanner(entrada);
                    while (sc.hasNextLine()) {
                        listaCedula.add(sc.nextLine());

                    }
                    entrada.close();

                } catch (IOException e) {
                    System.out.println("No se encontro el archivo");
                    existe = false;
                    return existe;
                }
                if (listaCedula.contains(cedula) == true) {
                    existe = true;
                    return existe;
                } else {
                    existe = false;
                }
                break;
            case 3:
                try {
                    FileReader entrada = new FileReader("listaVotosDiputadura.txt");
                    BufferedReader br = new BufferedReader(entrada);
                    Scanner sc = new Scanner(entrada);
                    while (sc.hasNextLine()) {
                        listaCedula.add(sc.nextLine());
                    }
                    entrada.close();

                } catch (IOException e) {
                    System.out.println("No se encontro el archivo");
                    existe = false;
                    return existe;
                }
                if (listaCedula.contains(cedula) == true) {
                    existe = true;
                    return existe;
                } else {
                    existe = false;

                }
                break;

            default:
                System.out.println("error");

        }
        return existe;
    }

    public static boolean elegibilidadPresidencial(){
        Scanner r = new Scanner(System.in);
        boolean elegible = false;
        int nacimiento,derechos,edad,cargoEjercido,fuerzasArmadasJefes,fuerzasArmadasSuperior;
        System.out.println("Bienvenido al punto de elegibilidad Presidencial");
        System.out.println("------------------------------------------------");
        System.out.println(" ");
        System.out.println("A continuacion responda las preguntas para determinar si es elegible para ser candidato a la presidencia del pais");
        System.out.println(" ");
        System.out.println("Es usted Hondureño por nacimiento? ");
        System.out.println(" ");
        System.out.println("1. Si                       2. No");
        nacimiento = r.nextInt();
        System.out.println("Porfavor ingrese su edad: ");
        edad = r.nextInt();
        System.out.println("Esta usted en el goce de los derechos del ciudadano? ");
        System.out.println(" ");
        System.out.println("1. Si                                         2. No");
        derechos = r.nextInt();
        System.out.println("Ejerce usted alguno de los siguientes cargos: o\tLos Designados presidenciales; Secretarios y Subsecretarios de Estado, \nConsejeros del Consejo Nacional Electoral, Magistrados del Tribunal de Justicia Electoral, Magistrados y Jueces \ndel Poder Judicial; Presidentes, Vicepresidentes, Gerentes, Subgerentes, Directores, Subdirectores, Secretarios Ejecutivos \nde Instituciones Descentralizadas; Procurador(a) y Subprocurador(a) General de la República y Magistrados del Tribunal \nSuperior de Cuentas que hayan ejercido funciones durante los seis meses anteriores a la fecha de la elección del Presidente de la República ");
        System.out.println(" ");
        System.out.println("1. Si                                         2. No");
        cargoEjercido = r.nextInt();
        System.out.println("Es usted un Oficial Jefe o Oficial General de las Fuerzas Armadas? ");
        System.out.println(" ");
        System.out.println("1. Si                                       2. No");
        fuerzasArmadasJefes = r.nextInt();
        System.out.println("Es usted Jefe Superior de las Fuerzas Armadas o cuerpos de Policía o de Seguridad del Estado? ");
        System.out.println(" ");
        System.out.println("1. Si                                       2. No");
        fuerzasArmadasSuperior = r.nextInt();
        if(nacimiento == 1 && derechos == 1 && edad >= 29 && cargoEjercido == 2 && fuerzasArmadasJefes == 2 && fuerzasArmadasSuperior == 2){
            System.out.println("***Felicidades! Usted es elegible a la candidatura de presidencia!***");
            elegible = true;
        } else{
            System.out.println("*** Usted no es elegible a la candidatura de la presidencia ***");
        }
        return elegible;
    }
    public static boolean elegibilidadAlcaldia(){
        Scanner r = new Scanner(System.in);
        boolean elegible = false;
        int nacimiento, residente=3, edad, alfabeto, educacionMedia,zona,inabilidad;
        String titulo;
        int indiceMunicipio;

        System.out.println("Bienvenido al punto de elegibilidad para Alcaldia");
        System.out.println("-------------------------------------------------");
        System.out.println(" ");
        System.out.println("Es usted Hondureño por nacimiento?");
        System.out.println("1. Si                        2. No");
        nacimiento = r.nextInt();
        if(nacimiento == 1){
            System.out.println("Porfavor ingrese su Edad: ");
            edad = r.nextInt();
            System.out.println("Sabe Leer y Escribir? ");
            System.out.println("1. Si            2. No");
            alfabeto = r.nextInt();
            System.out.println("Culmino usted su Educacion Media? ");
            System.out.println("1. Si                        2. No");
            educacionMedia = r.nextInt();
            System.out.println("Ha residido usted al menos dos años en el municipio para el cual quiere lanzarse de candidato? ");
            System.out.println("1. Si                                                                      2. No");
            zona = r.nextInt();
            System.out.println("Posee usted algun tipo de inabilidad legal? ");
            System.out.println("1. Si                                  2. No");
            inabilidad = r.nextInt();



        }else{
            System.out.println("Ha usted residido en Honduras por mas de 5 años?");
            System.out.println("1. Si                                      2. No");
            residente = r.nextInt();
            System.out.println("Porfavor ingrese su Edad: ");
            edad = r.nextInt();
            System.out.println("Sabe Leer y Escribir? ");
            System.out.println("1. Si            2. No");
            alfabeto = r.nextInt();
            System.out.println("Culmino usted su Educacion Media? ");
            System.out.println("1. Si                        2. No");
            educacionMedia = r.nextInt();
            /*if(educacionMedia == 1){
                System.out.println("Cual es el Titulo obtenido? ");
                titulo = r.nextLine();
            }*/
            System.out.println("Ha residido usted al menos dos años en el municipio para el cual quiere lanzarse de candidato? ");
            System.out.println("1. Si                                                                      2. No");
            zona = r.nextInt();
            System.out.println("Posee usted algun tipo de inabilidad legal? ");
            System.out.println("1. Si                                  2. No");
            inabilidad = r.nextInt();

        }

        if(nacimiento == 1 || residente == 1 && edad > 17 && alfabeto == 1 && educacionMedia == 1 && zona == 1 && inabilidad == 2){
            System.out.println("***Felicidades usted es elegible para la Alcaldia!***");
            elegible = true;
        } else{
            System.out.println("*** Usted no es elegible a la candidatura de la alcaldia ***");
        }



        return elegible;
    }
    public static boolean elegibilidadDiputadura(){
        Scanner r = new Scanner(System.in);
        boolean elegible = false;
        int nacimiento, edad, seglar, residencia;

        System.out.println("Bienvenido al punto de elegibilidad para Diputadura");
        System.out.println("-------------------------------------------------");
        System.out.println(" ");
        System.out.println("Es usted Hondureño por nacimiento?");
        System.out.println("1. Si                        2. No");
        nacimiento = r.nextInt();
        System.out.println("Porfavor ingrese su Edad: ");
        edad = r.nextInt();
        System.out.println("Es usted del Estado Seglar? ");
        System.out.println("1. Si                  2. No");
        seglar = r.nextInt();
        System.out.println("Nacio usted en el departamento por el cual se postula o ha residido en el los ultimos cinco años? ");
        System.out.println("1. Si                  2. No");
        residencia = r.nextInt();

        if(nacimiento ==1&&edad>20&&seglar==1&&residencia==1){
            System.out.println("***Felicidades usted es elegible para la Diputadura!***");
            elegible = true;
        }else{
            System.out.println("*** Usted no es elegible a la candidatura de la diputadura ***");
        }

        return elegible;
    }

    public static void inscripcionAlcaldia(){
        Scanner r = new Scanner(System.in);
        boolean registrado = false;
        Alcalde nuevoAlcalde = new Alcalde();
        String tipoCandidatura = "Alcaldia",municipalidadPostulada="ninguna";
        int partidoPolitico = 0, indiceDepartamento = 0,numeroCandidatos=0,votos=0,tipo=1;

        System.out.println(" ");
        while (indiceDepartamento < 1 || indiceDepartamento > 18) {
            System.out.println("En que departamento del pais reside? Elija el indice: ");
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
        while(partidoPolitico<1||partidoPolitico>3){
            System.out.println("Ingrese el Partido Politico que representa: ");
            System.out.println("1. Partido Liberal\t\t2. Partido Nacional\t\t3. Partido Libertad y Refundacion(Libre)");
            partidoPolitico = r.nextInt();
            registrado=revisionPartido(tipo,partidoPolitico,municipalidadPostulada,indiceDepartamento);
            if(registrado==true){
                System.exit(0);
            }else if(registrado == false){
                System.out.println("A continuacion ingrese la siguiente informacion personal: ");
                System.out.println(" ");
                r.nextLine();
                System.out.println("Ingrese su primera inicial seguido por su apellido, ejemplo: (GBrocato): ");
                nuevoAlcalde.setNombreArchivo(r.nextLine());
                //nombreArchivo = r.nextLine();
                System.out.println("Ingrese su Primer Nombre: ");
                nuevoAlcalde.setPrimerNombre(r.nextLine());
                //primerNombre = r.nextLine();
                System.out.println("Ingrese su Segundo Nombre( escriba: nada - si no aplica): ");
                nuevoAlcalde.setSegundoNombre(r.nextLine());
                //segundoNombre = r.nextLine();
                System.out.println("Ingrese su Primer Apellido: ");
                nuevoAlcalde.setPrimerApellido(r.nextLine());
                //primerApellido = r.nextLine();
                System.out.println("Ingrese su Segundo Apellido: ");
                nuevoAlcalde.setSegundoApellido(r.nextLine());
                //segundoApellido = r.nextLine();
                System.out.println("Ingrese su Numero de Identificacion: ");
                nuevoAlcalde.setNumeroIdentificacion(r.nextLine());
                //numeroIdentificacion = r.nextLine();
                System.out.println("Ingrese su Fecha de Nacimiento: (dd/mm/aaaa)");
                nuevoAlcalde.setFechaNacimiento(r.nextLine());
                //fechaNacimiento = r.nextLine();
                System.out.println("Ingrese su Lugar de Nacimiento: ");
                nuevoAlcalde.setLugarNacimiento(r.nextLine());
                //lugarNacimiento = r.nextLine();


                File archivo;
                FileWriter agregar;
                PrintWriter escribir;
                archivo = new File(nuevoAlcalde.getNombreArchivo()+"Alcaldia.txt");
                if(!archivo.exists()){
                    System.out.println("Archivo del candidato"+nuevoAlcalde.getPrimerNombre()+" "+nuevoAlcalde.getPrimerApellido()+" a sido creado.");
                    try { //codigo donde pueden ocurrir errores(excepciones)
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.println(nuevoAlcalde.getNombreArchivo());
                        escribir.println(nuevoAlcalde.getPrimerNombre());
                        escribir.println(nuevoAlcalde.getSegundoNombre());
                        escribir.println(nuevoAlcalde.getPrimerApellido());
                        escribir.println(nuevoAlcalde.getSegundoApellido());
                        escribir.println(nuevoAlcalde.getNumeroIdentificacion());
                        escribir.println(nuevoAlcalde.getFechaNacimiento());
                        escribir.println(nuevoAlcalde.getLugarNacimiento());
                        escribir.println(partidoPolitico);
                        escribir.println(tipoCandidatura);
                        escribir.println(indiceDepartamento);
                        escribir.println(municipalidadPostulada);
                        escribir.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("El archivo del candidato"+nuevoAlcalde.getPrimerNombre()+" "+nuevoAlcalde.getPrimerApellido()+" ya existe.");
                }
                archivo = new File("cantidadCandidatos"+municipalidadPostulada+".txt");
                if(!archivo.exists()){

                    try{
                        System.out.println("El archivo de conteo de candidatos de la municipalidad: "+municipalidadPostulada+", ha sido creado.");
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(numeroCandidatos+1);
                        escribir.close();


                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        FileReader entrada = new FileReader("cantidadCandidatos"+municipalidadPostulada+".txt");
                        BufferedReader br = new BufferedReader(entrada);
                        Scanner sc = new Scanner(entrada);
                        while(sc.hasNextLine()){
                            numeroCandidatos = sc.nextInt();
                        }
                        entrada.close();
                        try{
                            escribir = new PrintWriter(archivo,"utf-8");
                            escribir.print(numeroCandidatos+1);
                            escribir.close();


                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }catch(IOException e){
                        System.out.println("No se encontro el archivo");
                    }
                }
                archivo = new File("nombresAlcaldia"+municipalidadPostulada+".txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(nuevoAlcalde.getPrimerNombre()+"\n");
                        escribir.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        agregar = new FileWriter(archivo,true);
                        escribir = new PrintWriter(agregar);
                        escribir.print(nuevoAlcalde.getPrimerNombre()+"\n");
                        escribir.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                archivo = new File("apellidosAlcaldia"+municipalidadPostulada+".txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(nuevoAlcalde.getPrimerApellido()+"\n");
                        escribir.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        agregar = new FileWriter(archivo,true);
                        escribir = new PrintWriter(agregar);
                        escribir.print(nuevoAlcalde.getPrimerApellido()+"\n");
                        escribir.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                archivo = new File("nombreArchivoAlcaldia"+municipalidadPostulada+".txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(nuevoAlcalde.getNombreArchivo()+"\n");
                        escribir.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        agregar = new FileWriter(archivo,true);
                        escribir = new PrintWriter(agregar);
                        escribir.print(nuevoAlcalde.getNombreArchivo()+"\n");
                        escribir.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                archivo = new File("partidosAlcaldia"+municipalidadPostulada+".txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(partidoPolitico+"\n");
                        escribir.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        agregar = new FileWriter(archivo,true);
                        escribir = new PrintWriter(agregar);
                        escribir.print(partidoPolitico+"\n");
                        escribir.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                archivo = new File("votosAlcaldia"+municipalidadPostulada+nuevoAlcalde.getNombreArchivo()+".txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(votos);
                        escribir.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("El archivo ya existe");
                }
            }
            if(partidoPolitico<1||partidoPolitico>3) {
                System.out.println("La eleccion es incorrecta, Porfavor ingrese un partido politico de la lista!");
            }
        }


    }
    public static void inscripcionPresidencial(){
        Scanner r = new Scanner(System.in);
        boolean registrado = false;
        Presidencia nuevoPresidente = new Presidencia();
        String tipoCandidatura = "Presidencia";
        int partidoPolitico=0,tipo = 2,numeroCandidatos=0,votos=0,indiceDepartamento=0;

        while(partidoPolitico<1||partidoPolitico>3){
            System.out.println("Ingrese el Partido Politico que representa: ");
            System.out.println("1. Partido Liberal\t\t2. Partido Nacional\t\t3. Partido Libertad y Refundacion(Libre)");
            partidoPolitico = r.nextInt();
            registrado=revisionPartido(tipo,partidoPolitico,tipoCandidatura,indiceDepartamento);
            if(registrado==true){
                System.exit(0);
            }else if(registrado == false){
                System.out.println(" ");
                System.out.println("A continuacion ingrese la siguiente informacion personal: ");
                System.out.println(" ");
                r.nextLine();
                System.out.println("Ingrese su primera inicial seguido por su apellido, ejemplo: (GBrocato): ");
                nuevoPresidente.setNombreArchivo(r.nextLine());
                System.out.println("Ingrese su Primer Nombre: ");
                nuevoPresidente.setPrimerNombre(r.nextLine());
                System.out.println("Ingrese su Segundo Nombre( escriba: nada - si no aplica): ");
                nuevoPresidente.setSegundoNombre(r.nextLine());
                System.out.println("Ingrese su Primer Apellido: ");
                nuevoPresidente.setPrimerApellido(r.nextLine());
                System.out.println("Ingrese su Segundo Apellido: ");
                nuevoPresidente.setSegundoApellido(r.nextLine());
                System.out.println("Ingrese su Numero de Identificacion: ");
                nuevoPresidente.setNumeroIdentificacion(r.nextLine());
                System.out.println("Ingrese su Fecha de Nacimiento: (dd/mm/aaaa)");
                nuevoPresidente.setFechaNacimiento(r.nextLine());
                System.out.println("Ingrese su Lugar de Nacimiento: ");
                nuevoPresidente.setLugarNacimiento(r.nextLine());


                File archivo;
                FileWriter agregar;
                PrintWriter escribir;
                archivo = new File(nuevoPresidente.getNombreArchivo()+"Presidencia.txt");
                if(!archivo.exists()){
                    System.out.println("Archivo del candidato "+nuevoPresidente.getPrimerNombre()+" "+nuevoPresidente.getPrimerApellido()+" a sido creado.");
                    try { //codigo donde pueden ocurrir errores(excepciones)
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.println(nuevoPresidente.getNombreArchivo());
                        escribir.println(nuevoPresidente.getPrimerNombre());
                        escribir.println(nuevoPresidente.getSegundoNombre());
                        escribir.println(nuevoPresidente.getPrimerApellido());
                        escribir.println(nuevoPresidente.getSegundoApellido());
                        escribir.println(nuevoPresidente.getNumeroIdentificacion());
                        escribir.println(nuevoPresidente.getFechaNacimiento());
                        escribir.println(nuevoPresidente.getLugarNacimiento());
                        escribir.println(partidoPolitico);
                        escribir.println(tipoCandidatura);
                        escribir.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("El archivo del candidato "+nuevoPresidente.getPrimerNombre()+" "+nuevoPresidente.getPrimerApellido()+" ya existe.");
                }

                archivo = new File("cantidadCandidatos"+tipoCandidatura+".txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(numeroCandidatos+1);
                        escribir.close();


                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        FileReader entrada = new FileReader("cantidadCandidatos"+tipoCandidatura+".txt");
                        BufferedReader br = new BufferedReader(entrada);
                        Scanner sc = new Scanner(entrada);
                        while(sc.hasNextLine()){
                            numeroCandidatos = sc.nextInt();
                        }
                        entrada.close();
                        try{
                            escribir = new PrintWriter(archivo,"utf-8");
                            escribir.print(numeroCandidatos+1);
                            escribir.close();


                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }catch(IOException e){
                        System.out.println("No se encontro el archivo");
                    }
                }
                archivo = new File("nombres"+tipoCandidatura+".txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(nuevoPresidente.getPrimerNombre()+"\n");
                        escribir.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        agregar = new FileWriter(archivo,true);
                        escribir = new PrintWriter(agregar);
                        escribir.print(nuevoPresidente.getPrimerNombre()+"\n");
                        escribir.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                archivo = new File("apellidos"+tipoCandidatura+".txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(nuevoPresidente.getPrimerApellido()+"\n");
                        escribir.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        agregar = new FileWriter(archivo,true);
                        escribir = new PrintWriter(agregar);
                        escribir.print(nuevoPresidente.getPrimerApellido()+"\n");
                        escribir.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                archivo = new File("nombreArchivo"+tipoCandidatura+".txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(nuevoPresidente.getNombreArchivo()+"\n");
                        escribir.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        agregar = new FileWriter(archivo,true);
                        escribir = new PrintWriter(agregar);
                        escribir.print(nuevoPresidente.getNombreArchivo()+"\n");
                        escribir.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                archivo = new File("partidos"+tipoCandidatura+".txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(partidoPolitico+"\n");
                        escribir.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        agregar = new FileWriter(archivo,true);
                        escribir = new PrintWriter(agregar);
                        escribir.print(partidoPolitico+"\n");
                        escribir.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                archivo = new File("votos"+tipoCandidatura+nuevoPresidente.getNombreArchivo()+".txt");
                if(!archivo.exists()){

                    try{
                        archivo.createNewFile();
                        escribir = new PrintWriter(archivo,"utf-8");
                        escribir.print(votos);
                        escribir.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("El archivo ya existe");
                }
            }
            if(partidoPolitico<1||partidoPolitico>3) {
                System.out.println("La eleccion es incorrecta, Porfavor ingrese un partido politico de la lista!");
            }
        }

    }
    public static void inscripcionDiputadura() {
        Scanner r = new Scanner(System.in);
        Diputadura nuevoDiputado = new Diputadura();
        String tipoCandidatura = "Diputadura", municipalidadPostulada = "ninguna";
        int partidoPolitico = 0, indiceDepartamento = 0, tipo = 3;
        int numeroCandidatos = 0, votos = 0;
        boolean registrado = true;

        System.out.println(" ");
        while (indiceDepartamento < 1 || indiceDepartamento > 18) {
            System.out.println("En que departamento del pais reside? Elija el indice: ");
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
            if (indiceDepartamento < 1 || indiceDepartamento > 18) {
                System.out.println("***Su eleccion no es valida, Porfavor ingrese el indice de uno de los departamentos***");
            }
        }
            while (partidoPolitico < 1 || partidoPolitico > 3) {
                System.out.println("Ingrese el Partido Politico que representa: ");
                System.out.println("1. Partido Liberal\t\t2. Partido Nacional\t\t3. Partido Libertad y Refundacion(Libre)");
                partidoPolitico = r.nextInt();
                registrado = revisionPartido(tipo, partidoPolitico, municipalidadPostulada, indiceDepartamento);
                if (registrado == true) {
                    System.exit(0);
                } else if (registrado == false) {
                    System.out.println("A continuacion ingrese la siguiente informacion personal: ");
                    System.out.println(" ");
                    r.nextLine();
                    System.out.println("Ingrese su primera inicial seguido por su apellido, ejemplo: (GBrocato): ");
                    nuevoDiputado.setNombreArchivo(r.nextLine());
                    //nombreArchivo = r.nextLine();
                    System.out.println("Ingrese su Primer Nombre: ");
                    nuevoDiputado.setPrimerNombre(r.nextLine());
                    //primerNombre = r.nextLine();
                    System.out.println("Ingrese su Segundo Nombre( escriba: nada - si no aplica): ");
                    nuevoDiputado.setSegundoNombre(r.nextLine());
                    //segundoNombre = r.nextLine();
                    System.out.println("Ingrese su Primer Apellido: ");
                    nuevoDiputado.setPrimerApellido(r.nextLine());
                    //primerApellido = r.nextLine();
                    System.out.println("Ingrese su Segundo Apellido: ");
                    nuevoDiputado.setSegundoApellido(r.nextLine());
                    //segundoApellido = r.nextLine();
                    System.out.println("Ingrese su Numero de Identificacion: ");
                    nuevoDiputado.setNumeroIdentificacion(r.nextLine());
                    //numeroIdentificacion = r.nextLine();
                    System.out.println("Ingrese su Fecha de Nacimiento: (dd/mm/aaaa)");
                    nuevoDiputado.setFechaNacimiento(r.nextLine());
                    //fechaNacimiento = r.nextLine();
                    System.out.println("Ingrese su Lugar de Nacimiento: ");
                    nuevoDiputado.setLugarNacimiento(r.nextLine());
                    //lugarNacimiento = r.nextLine();


                    File archivo;
                    FileWriter agregar;
                    PrintWriter escribir;
                    archivo = new File(nuevoDiputado.getNombreArchivo() + "Diputadura.txt");
                    if (!archivo.exists()) {
                        System.out.println("Archivo del candidato" + nuevoDiputado.getPrimerNombre() + " " + nuevoDiputado.getPrimerApellido() + " a sido creado.");
                        try { //codigo donde pueden ocurrir errores(excepciones)
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.println(nuevoDiputado.getNombreArchivo());
                            escribir.println(nuevoDiputado.getPrimerNombre());
                            escribir.println(nuevoDiputado.getSegundoNombre());
                            escribir.println(nuevoDiputado.getPrimerApellido());
                            escribir.println(nuevoDiputado.getSegundoApellido());
                            escribir.println(nuevoDiputado.getNumeroIdentificacion());
                            escribir.println(nuevoDiputado.getFechaNacimiento());
                            escribir.println(nuevoDiputado.getLugarNacimiento());
                            escribir.println(partidoPolitico);
                            escribir.println(tipoCandidatura);
                            escribir.println(indiceDepartamento);
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("El archivo del candidato" + nuevoDiputado.getPrimerNombre() + " " + nuevoDiputado.getPrimerApellido() + " ya existe.");
                    }
                    archivo = new File("cantidadCandidatos" + indiceDepartamento + ".txt");
                    if (!archivo.exists()) {

                        try {
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.print(numeroCandidatos + 1);
                            escribir.close();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            FileReader entrada = new FileReader("cantidadCandidatos" + indiceDepartamento + ".txt");
                            BufferedReader br = new BufferedReader(entrada);
                            Scanner sc = new Scanner(entrada);
                            while (sc.hasNextLine()) {
                                numeroCandidatos = sc.nextInt();
                            }
                            entrada.close();
                            try {
                                escribir = new PrintWriter(archivo, "utf-8");
                                escribir.print(numeroCandidatos + 1);
                                escribir.close();


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } catch (IOException e) {
                            System.out.println("No se encontro el archivo");
                        }
                    }
                    archivo = new File("candidatosDiputadura" + indiceDepartamento + ".txt");
                    if (!archivo.exists()) {

                        try {
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.print(nuevoDiputado.getPrimerNombre() + " " + nuevoDiputado.getPrimerApellido() + " " + nombrePartido(partidoPolitico) + "\n");
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            agregar = new FileWriter(archivo, true);
                            escribir = new PrintWriter(agregar);
                            escribir.print(nuevoDiputado.getPrimerNombre() + " " + nuevoDiputado.getPrimerApellido() + " " + nombrePartido(partidoPolitico) + "\n");
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    archivo = new File("nombreArchivoDiputadura" + indiceDepartamento + ".txt");
                    if (!archivo.exists()) {

                        try {
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.print(nuevoDiputado.getNombreArchivo() + "\n");
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            agregar = new FileWriter(archivo, true);
                            escribir = new PrintWriter(agregar);
                            escribir.print(nuevoDiputado.getNombreArchivo() + "\n");
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    archivo = new File("votosDiputadura" + indiceDepartamento + nuevoDiputado.getNombreArchivo() + ".txt");
                    if (!archivo.exists()) {

                        try {
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.print(votos);
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("El archivo ya existe");
                    }
                }
                if (partidoPolitico < 1 || partidoPolitico > 3) {
                    System.out.println("La eleccion es incorrecta, Porfavor ingrese un partido politico de la lista!");
                }
            }
        }

    public static boolean revisionPartido(int tipo,int partido,String municipalidadPostulada,int indiceDepartamento) {
        boolean registrado = false;
        //int tipo= 1 = Alcalde, 2 = Presidente
        File archivo;
        FileWriter agregar;
        PrintWriter escribir;
        boolean candidatoRegistrado = true;
        int numeroDiputados = 0,diputadosDepartamento = 0;
        switch (tipo) {
            case 1:
                if (partido == 1) {
                    archivo = new File("partidoLiberal" + municipalidadPostulada + ".txt");
                    if (!archivo.exists()) {

                        try {
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.print(candidatoRegistrado);
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        registrado = true;
                        System.out.println("Ya existe un candidato Liberal registrado en la municipalidad: " + municipalidadPostulada);
                    }
                } else if (partido == 2) {
                    archivo = new File("partidoNacional" + municipalidadPostulada + ".txt");
                    if (!archivo.exists()) {

                        try {
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.print(candidatoRegistrado);
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        registrado = true;
                        System.out.println("Ya existe un candidato Nacional registrado en la municipalidad: " + municipalidadPostulada);
                    }
                } else if (partido == 3) {
                    archivo = new File("partidoLibre" + municipalidadPostulada + ".txt");
                    if (!archivo.exists()) {

                        try {
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.print(candidatoRegistrado);
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        registrado = true;
                        System.out.println("Ya existe un candidato Libre registrado en la municipalidad: " + municipalidadPostulada);
                    }
                }
                break;
            case 2:
                if (partido == 1) {
                    archivo = new File("partidoLiberal" + municipalidadPostulada + ".txt");
                    if (!archivo.exists()) {

                        try {
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.print(candidatoRegistrado);
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        registrado = true;
                        System.out.println("Ya existe un candidato Liberal registrado en la municipalidad: " + municipalidadPostulada);
                    }
                } else if (partido == 2) {
                    archivo = new File("partidoNacional" + municipalidadPostulada + ".txt");
                    if (!archivo.exists()) {

                        try {
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.print(candidatoRegistrado);
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        registrado = true;
                        System.out.println("Ya existe un candidato Nacional registrado en la municipalidad: " + municipalidadPostulada);
                    }
                } else if (partido == 3) {
                    archivo = new File("partidoLibre" + municipalidadPostulada + ".txt");
                    if (!archivo.exists()) {

                        try {
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.print(candidatoRegistrado);
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        registrado = true;
                        System.out.println("Ya existe un candidato Libre registrado en la municipalidad: " + municipalidadPostulada);
                    }
                }
                break;
            case 3:
                diputadosDepartamento = cantidadDiputados(indiceDepartamento);
                if (partido == 1) {
                    archivo = new File("partidoLiberal" + indiceDepartamento + ".txt");
                    if (!archivo.exists()) {

                        try {
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.print(numeroDiputados+1);
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else{
                        try{
                            FileReader entrada = new FileReader("partidoLiberal" + indiceDepartamento + ".txt");
                            BufferedReader br = new BufferedReader(entrada);
                            Scanner sc = new Scanner(entrada);
                            while(sc.hasNextLine()){
                                numeroDiputados = sc.nextInt();
                            }
                            entrada.close();
                            if(numeroDiputados == diputadosDepartamento){
                                registrado = true;
                                System.out.println("Ya existe el numero limite de candidatos registrados para el departamento elegido");
                            } else{
                                try{
                                    escribir = new PrintWriter(archivo,"utf-8");
                                    escribir.print(numeroDiputados+1);
                                    escribir.close();


                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                        }catch(IOException e){
                            System.out.println("No se encontro el archivo");
                        }
                    }
                } else if (partido == 2) {
                    archivo = new File("partidoNacional" + indiceDepartamento + ".txt");
                    if (!archivo.exists()) {

                        try {
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.print(numeroDiputados+1);
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else{
                        try{
                            FileReader entrada = new FileReader("partidoNacional" + indiceDepartamento + ".txt");
                            BufferedReader br = new BufferedReader(entrada);
                            Scanner sc = new Scanner(entrada);
                            while(sc.hasNextLine()){
                                numeroDiputados = sc.nextInt();
                            }
                            entrada.close();
                            if(numeroDiputados == diputadosDepartamento){
                                registrado = true;
                                System.out.println("Ya existe el numero limite de candidatos registrados para el departamento elegido");
                            } else{
                                try{
                                    escribir = new PrintWriter(archivo,"utf-8");
                                    escribir.print(numeroDiputados+1);
                                    escribir.close();


                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                        }catch(IOException e){
                            System.out.println("No se encontro el archivo");
                        }
                    }
                }else if(partido == 3){
                    archivo = new File("partidoLibre" + indiceDepartamento + ".txt");
                    if (!archivo.exists()) {

                        try {
                            archivo.createNewFile();
                            escribir = new PrintWriter(archivo, "utf-8");
                            escribir.print(numeroDiputados+1);
                            escribir.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else{
                        try{
                            FileReader entrada = new FileReader("partidoLibre" + indiceDepartamento + ".txt");
                            BufferedReader br = new BufferedReader(entrada);
                            Scanner sc = new Scanner(entrada);
                            while(sc.hasNextLine()){
                                numeroDiputados = sc.nextInt();
                            }
                            entrada.close();
                            if(numeroDiputados == diputadosDepartamento){
                                registrado = true;
                                System.out.println("Ya existe el numero limite de candidatos registrados para el departamento elegido");
                            } else{
                                try{
                                    escribir = new PrintWriter(archivo,"utf-8");
                                    escribir.print(numeroDiputados+1);
                                    escribir.close();


                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                        }catch(IOException e){
                            System.out.println("No se encontro el archivo");
                        }
                    }
                }
                break;
            default:
                System.out.println("error!");
        }

        return registrado;
    }
    public static String Departamentos(int IndiceDepartamento){
        Scanner r = new Scanner(System.in);
        int indiceMunicipalidad;
        String municipalidadPostulada="ninguna";
        switch(IndiceDepartamento){
            case 1:
                System.out.println("Usted ha elegido el departamento de Atlantida");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. La Ceiba\t\t2. Tela");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "La Ceiba";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Tela";
                }
                break;
            case 2:
                System.out.println("Usted ha elegido el departamento de Colon");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. Trujillo\t\t2. Tocoa");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "Trujillo";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Tocoa";
                }
                break;
            case 3:
                System.out.println("Usted ha elegido el departamento de Comayagua");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. Comayagua\t\t2. Siguatepeque");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "Comayagua";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Siguatepeque";
                }
                break;
            case 4:
                System.out.println("Usted ha elegido el departamento de Copan");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. Santa Rosa de Copan\t\t2. Nueva Arcadia");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "Santa Rosa de Copan";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Nueva Arcadia";
                }
                break;
            case 5:
                System.out.println("Usted ha elegido el departamento de Cortes");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. San Pedro Sula\t\t2. Puerto Cortes");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "San Pedro Sula";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Puerto Cortes";
                }
                break;
            case 6:
                System.out.println("Usted ha elegido el departamento de Choluteca");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. Choluteca\t\t2. San Jose");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "Choluteca";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "San Jose";
                }
                break;
            case 7:
                System.out.println("Usted ha elegido el departamento de El Paraiso");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. El Paraiso\t\t2. Danli");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "El Paraiso";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Danli";
                }
                break;
            case 8:
                System.out.println("Usted ha elegido el departamento de Francisco Morazan");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. Distrito Central\t\t2. La Libertad");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "Distrito Central";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "La Libertad";
                }
                break;
            case 9:
                System.out.println("Usted ha elegido el departamento de Gracias a Dios");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. Puerto Lempira\t\t2. Ramon Villeda Morales");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "Puerto Lempira";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Ramon Villeda Morales";
                }
                break;
            case 10:
                System.out.println("Usted ha elegido el departamento de Intibuca");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. La Esperanza\t\t2. Intibuca");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "La Esperanza";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Intibuca";
                }
                break;
            case 11:
                System.out.println("Usted ha elegido el departamento de Islas de la Bahia");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. Roatan\t\t2. Utila");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "Roatan";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Utila";
                }
                break;
            case 12:
                System.out.println("Usted ha elegido el departamento de La Paz");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. La Paz\t\t2. Marcala");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "La Paz";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Marcala";
                }
                break;
            case 13:
                System.out.println("Usted ha elegido el departamento de Lempira");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. Gracias\t\t2. Belen");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "Gracias";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Belen";
                }
                break;
            case 14:
                System.out.println("Usted ha elegido el departamento de Ocotepeque");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. Ocotepeque\t\t2. Santa Fe");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "Ocotepeque";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Santa Fe";
                }
                break;
            case 15:
                System.out.println("Usted ha elegido el departamento de Olancho");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. Juticalpa\t\t2. Concordia");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "Juticalpa";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Concordia";
                }
                break;
            case 16:
                System.out.println("Usted ha elegido el departamento de Santa Barbara");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. Santa Barbara\t\t2. Santa Rita");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "Santa Barbara";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Santa Rita";
                }
                break;
            case 17:
                System.out.println("Usted ha elegido el departamento de Valle");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. Nacaome\t\t2. Amapala");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "Nacaome";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Amapala";
                }
                break;
            case 18:
                System.out.println("Usted ha elegido el departamento de Yoro");
                System.out.println("A continuacion ingrese el indice de la municipalidad en la cual reside: ");
                System.out.println("1. Yoro\t\t2. Olanchito");
                indiceMunicipalidad = r.nextInt();
                r.nextLine();
                if(indiceMunicipalidad<1||indiceMunicipalidad>2){
                    System.out.println("La eleccion no es valida! Intente de nuevo.");
                    System.out.println(" ");
                    Departamentos(IndiceDepartamento);
                } else if(indiceMunicipalidad == 1){
                    municipalidadPostulada = "Yoro";
                } else if(indiceMunicipalidad == 2){
                    municipalidadPostulada = "Olanchito";
                }
                break;
            default:
                System.out.println("Eleccion no valida.");


        }
        return municipalidadPostulada;
    }
    public static int cantidadDiputados(int IndiceDepartamento){
        int cantidadCandidatos=0;
        switch(IndiceDepartamento){
            case 1:
                cantidadCandidatos = 8;
                break;
            case 2:
                cantidadCandidatos = 4;
                break;
            case 3:
                cantidadCandidatos = 7;
                break;
            case 4:
                cantidadCandidatos = 7;
                break;
            case 5:
                cantidadCandidatos = 20;
                break;
            case 6:
                cantidadCandidatos = 9;
                break;
            case 7:
                cantidadCandidatos = 6;
                break;
            case 8:
                cantidadCandidatos = 23;
                break;
            case 9:
                cantidadCandidatos = 1;
                break;
            case 10:
                cantidadCandidatos = 5;
                break;
            case 11:
                cantidadCandidatos = 1;
                break;
            case 12:
                cantidadCandidatos = 3;
                break;
            case 13:
                cantidadCandidatos = 3;
                break;
            case 14:
                cantidadCandidatos = 2;
                break;
            case 15:
                cantidadCandidatos = 7;
                break;
            case 16:
                cantidadCandidatos = 9;
                break;
            case 17:
                cantidadCandidatos = 4;
                break;
            case 18:
                cantidadCandidatos = 9;
                break;
            default:
                System.out.println("Eleccion no valida.");


        }

        return cantidadCandidatos;
    }
    public static String nombrePartido(int partidoPolitico){
        String nombre="";
        if(partidoPolitico==1){
            nombre="Partido Liberal";
        }else if(partidoPolitico == 2){
            nombre="Partido Nacional";
        }else if(partidoPolitico == 3){
            nombre="Partido Libre";
        }

        return nombre;
    }
    public static String nombreDepartamento(int IndiceDepartamento) {
        String nombre = "";
        switch (IndiceDepartamento) {
            case 1:
                nombre = "Atlantida";
                break;
            case 2:
                nombre = "Colon";
                break;
            case 3:
                nombre = "Comayagua";
                break;
            case 4:
                nombre = "Copan";
                break;
            case 5:
                nombre = "Cortes";
                break;
            case 6:
                nombre = "Choluteca";
                break;
            case 7:
                nombre = "El Paraiso";
                break;
            case 8:
                nombre = "Francisco Morazan";
                break;
            case 9:
                nombre = "Gracias a Dios";
                break;
            case 10:
                nombre = "Intibuca";
                break;
            case 11:
                nombre = "Islas de la bahia";
                break;
            case 12:
                nombre = "La Paz";
                break;
            case 13:
                nombre = "Lempira";
                break;
            case 14:
                nombre = "Ocotepeque";
                break;
            case 15:
                nombre = "Olancho";
                break;
            case 16:
                nombre = "Santa Barbara";
                break;
            case 17:
                nombre = "Valle";
                break;
            case 18:
                nombre = "Yoro";
                break;
            default:
                System.out.println("Eleccion no valida.");


        }
        return nombre;
    }
    public static boolean votoDiputados(Scanner r,int indiceDepartamento,int tipo,int numeroCandidatos,ArrayList<String> listaDiputados,ArrayList<String>nArchivos){
        int votosDisponibles = numeroCandidatos;
        int voto, eleccion, ultimoNumero=0;
        String nombreArchivo,municipalidadPostulada="ninguna";

        boolean repetir = true;
        boolean votacion = false;
        while(repetir == true){
            System.out.println("Los candidatos disponibles en la Diputadura de su departamento son: ");
            System.out.println(" ");
            for(int x=0;x<listaDiputados.size();x++){
                System.out.println((x+1)+". "+listaDiputados.get(x));
                ultimoNumero = x+1;
            }
            System.out.println((ultimoNumero+1)+". Omitir votos");
            voto = r.nextInt();
            if(voto < 1 || voto > (listaDiputados.size()+1)){
                System.out.println("Su eleccion no es valida, intente de nuevo.");
                repetir = true;
            } else{
                if(voto == ultimoNumero+1){
                    System.out.println("A elegido la opcion de omitir el resto de sus votos");
                    System.out.println("1. Confirmar\t\t\t2. Regresar");
                    eleccion = r.nextInt();
                    if(eleccion==1){
                        votosDisponibles=0;
                        repetir = false;
                        votacion = true;
                    } else{
                        repetir = true;
                    }
                } else{
                    System.out.println("Su voto es: "+listaDiputados.get(voto-1));
                    System.out.println("1. Confirmar\t\t\t2. Regresar");
                    eleccion = r.nextInt();
                    if(eleccion == 1){
                        nombreArchivo = nArchivos.get(voto-1);
                        castVote(tipo,municipalidadPostulada,nombreArchivo,indiceDepartamento);
                        votosDisponibles--;
                        listaDiputados.remove(voto-1);
                        nArchivos.remove(voto-1);
                        if(votosDisponibles==0){
                            repetir=false;
                            votacion = true;
                        }

                    } else{
                        repetir = false;
                    }
                }

            }
        }


        return votacion;
    }
}

