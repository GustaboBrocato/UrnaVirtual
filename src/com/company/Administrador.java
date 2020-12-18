package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import static com.company.Main.*;



public class Administrador {

    private String usuario = "Patron";
    private String contrasena = "12345";
    private static boolean estadoVotacion = false;
    private static boolean estadoInscripcion = false;


    public boolean isEstadoVotacion() {
        return estadoVotacion;
    }

    public void setEstadoVotacion(boolean estadoVotacion) {
        this.estadoVotacion = estadoVotacion;
    }

    public boolean isEstadoInscripcion() {
        return estadoInscripcion;
    }

    public void setEstadoInscripcion(boolean estadoInscripcion) {
        this.estadoInscripcion = estadoInscripcion;
    }

    public void menu(){
        Scanner r = new Scanner(System.in);
        int eleccion;
        System.out.println("Bienvenido al menu de Administrador");
        System.out.println("Escriba el indice de lo que desea hacer: ");
        System.out.println("1. Habilitar/Deshabilitar el sistema de votacion");
        System.out.println("2. Habilitar/Deshabilitar el sistema de inscripcion");
        System.out.println("3. Conteo de Votos");
        System.out.println("4. Pantalla Principal");
        eleccion = r.nextInt();
        switch(eleccion){
            case 1:
                votacion(r);
                break;
            case 2:
                inscripcion(r);
                break;
            case 3:
                conteoVotosMenu();
                break;
            case 4:
                menuPrincipal();
                break;
            default:
                System.out.println("Eleccion no es valida");
                menu();
        }
    }
    public void inscripcion(Scanner r){
        int eleccion;
        System.out.println("Panel de control del sistema de inscripcion");
        System.out.println("Ingrese el indice de la accion que desea realizar: ");
        System.out.println("1. Habilitar");
        System.out.println("2. Deshabilitar");
        eleccion = r.nextInt();
        System.out.println(eleccion);
        switch(eleccion){
            case 1:
                estadoInscripcion = true;
                estadoVotacion = false;
                break;
            case 2:
                estadoInscripcion = false;
                break;
            default:
                System.out.println("Eleccion no valida!");
                inscripcion(r);

        }
    }
    public void votacion(Scanner r){
        int eleccion;
        System.out.println("Panel de control del sistema de votacion");
        System.out.println("Ingrese el indice de la accion que desea realizar: ");
        System.out.println("1. Habilitar");
        System.out.println("2. Deshabilitar");
        eleccion = r.nextInt();
        switch(eleccion){
            case 1:
                estadoVotacion = true;
                estadoInscripcion=false;
                System.out.println("El sistema de votacion esta activado");
                break;
            case 2:
                estadoVotacion = false;
                System.out.println("El sistema de votacion esta desactivado");
            break;
            default:
                System.out.println("Eleccion no valida!");
                votacion(r);

        }
    }

    public void conteoVotosMenu(){
        Scanner r = new Scanner(System.in);
        int eleccion;
        if(estadoVotacion == true){
            System.out.println("El sistema de votacion esta habilitado!");
            System.out.println("Desea deshabilitar la votacion? ");
            System.out.println("1. Si\t\t\t2. No");
            eleccion = r.nextInt();
            if(eleccion==1){
                setEstadoVotacion(false);
            }else{
                menu();
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
                case 2:
                    conteoDiputadura(r);
                    break;
                case 3:
                    conteoPresidencia(r);
                    break;
            }

        }
    }
    public void conteoAlcaldia(Scanner r){
        System.out.println(" ");
        int indiceDepartamento=0;
        String municipalidadPostulada="",nombrePartido="";
        ArrayList<String> nombreArchivos = new ArrayList<String>();
        ArrayList<String> nombres = new ArrayList<String>();
        ArrayList<String> apellidos = new ArrayList<String>();
        ArrayList<String> partidos = new ArrayList<String>();
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
            FileReader entrada = new FileReader("partidosAlcaldia"+municipalidadPostulada+".txt");
            BufferedReader br = new BufferedReader(entrada);
            Scanner sc = new Scanner(entrada);
            while (sc.hasNextLine()) {
                partidos.add(sc.nextLine());
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

        nombreArchivos = burbujaString(1,nombreArchivos,nombres,apellidos,resultados,partidos);
        nombres = burbujaString(2,nombreArchivos,nombres,apellidos,resultados,partidos);
        apellidos = burbujaString(3,nombreArchivos,nombres,apellidos,resultados,partidos);
        partidos = burbujaString(4,nombreArchivos,nombres,apellidos,resultados,partidos);
        resultados = burbujaInt(1,resultados);

        if(resultados.get(0).equals(resultados.get(1))){
            System.out.println("El resultado ha sido un empate!");
            System.out.println(nombres.get(0)+" "+apellidos.get(0)+" "+nombrarPartido(partidos.get(0))+" -- Votos totales: "+resultados.get(0)+".");
            System.out.println(nombres.get(1)+" "+apellidos.get(1)+" "+nombrarPartido(partidos.get(1))+" -- Votos totales: "+resultados.get(1)+".");
        }else if(resultados.get(0).equals(resultados.get(2))){
            System.out.println("El resultado ha sido un empate!");
            System.out.println(nombres.get(0)+" "+apellidos.get(0)+" "+nombrarPartido(partidos.get(0))+" -- Votos totales: "+resultados.get(0)+".");
            System.out.println(nombres.get(1)+" "+apellidos.get(1)+" "+nombrarPartido(partidos.get(1))+" -- Votos totales: "+resultados.get(1)+".");
            System.out.println(nombres.get(2)+" "+apellidos.get(2)+" "+nombrarPartido(partidos.get(3))+" -- Votos totales: "+resultados.get(2)+".");
        } else{
            System.out.println("El ganador para la Alcaldia: "+municipalidadPostulada+" es:");
            System.out.println(nombres.get(0)+" "+apellidos.get(0)+" "+nombrarPartido(partidos.get(0))+" -- Votos totales: "+resultados.get(0)+".");
        }

    }
    public void conteoPresidencia(Scanner r){
        System.out.println(" ");

        String nombrePartido="";
        ArrayList<String> nombreArchivos = new ArrayList<String>();
        ArrayList<String> nombres = new ArrayList<String>();
        ArrayList<String> apellidos = new ArrayList<String>();
        ArrayList<String> partidos = new ArrayList<String>();
        ArrayList<Integer> resultados = new ArrayList<Integer>();

        try {
            FileReader entrada = new FileReader("nombreArchivoPresidencia.txt");
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
            FileReader entrada = new FileReader("nombresPresidencia.txt");
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
            FileReader entrada = new FileReader("apellidosPresidencia.txt");
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
            FileReader entrada = new FileReader("partidosPresidencia.txt");
            BufferedReader br = new BufferedReader(entrada);
            Scanner sc = new Scanner(entrada);
            while (sc.hasNextLine()) {
                partidos.add(sc.nextLine());
            }
            entrada.close();
        } catch (IOException e) {
            System.out.println("No se encontro el archivo");
        }

        for(int x = 0;x<nombreArchivos.size();x++){
            try {
                FileReader entrada = new FileReader("votosPresidencia"+nombreArchivos.get(x)+".txt");
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

        nombreArchivos = burbujaString(1,nombreArchivos,nombres,apellidos,resultados,partidos);
        nombres = burbujaString(2,nombreArchivos,nombres,apellidos,resultados,partidos);
        apellidos = burbujaString(3,nombreArchivos,nombres,apellidos,resultados,partidos);
        partidos = burbujaString(4,nombreArchivos,nombres,apellidos,resultados,partidos);
        resultados = burbujaInt(1,resultados);

        System.out.println(partidos.size());
        if(resultados.get(0).equals(resultados.get(1))){
            System.out.println("El resultado ha sido un empate!");
            System.out.println(nombres.get(0)+" "+apellidos.get(0)+" "+nombrarPartido(partidos.get(0))+" -- Votos totales: "+resultados.get(0)+".");
            System.out.println(nombres.get(1)+" "+apellidos.get(1)+" "+nombrarPartido(partidos.get(1))+" -- Votos totales: "+resultados.get(1)+".");
        }else if(resultados.get(0).equals(resultados.get(2))){
            System.out.println("El resultado ha sido un empate!");
            System.out.println(nombres.get(0)+" "+apellidos.get(0)+" "+nombrarPartido(partidos.get(0))+" -- Votos totales: "+resultados.get(0)+".");
            System.out.println(nombres.get(1)+" "+apellidos.get(1)+" "+nombrarPartido(partidos.get(1))+" -- Votos totales: "+resultados.get(1)+".");
            System.out.println(nombres.get(2)+" "+apellidos.get(2)+" "+nombrarPartido(partidos.get(2))+" -- Votos totales: "+resultados.get(2)+".");
        } else{
            System.out.println("El ganador para la Presidencia es: ");
            System.out.println(nombres.get(0)+" "+apellidos.get(0)+" "+nombrarPartido(partidos.get(0))+" -- Votos totales: "+resultados.get(0)+".");
        }

    }
    public void conteoDiputadura(Scanner r){
        System.out.println(" ");
        int indiceDepartamento=0,numeroDiputados = 0;
        String nombrePartido="";
        ArrayList<String> nombreArchivos = new ArrayList<String>();
        ArrayList<String> informacion = new ArrayList<String>();
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
        }
        try {
            FileReader entrada = new FileReader("nombreArchivoDiputadura"+indiceDepartamento+".txt");
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
            FileReader entrada = new FileReader("candidatosDiputadura"+indiceDepartamento+".txt");
            BufferedReader br = new BufferedReader(entrada);
            Scanner sc = new Scanner(entrada);
            while (sc.hasNextLine()) {
                informacion.add(sc.nextLine());
            }
            entrada.close();
        } catch (IOException e) {
            System.out.println("No se encontro el archivo");
        }


        for(int x = 0;x<informacion.size();x++){
            try {
                FileReader entrada = new FileReader("votosDiputadura"+indiceDepartamento+nombreArchivos.get(x)+".txt");
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

        nombreArchivos = burbujaStringDiputados(nombreArchivos,resultados);
        resultados = burbujaInt(1,resultados);

        numeroDiputados = cantidadDiputados(indiceDepartamento);
        System.out.println("Los candidatos ganadores en el departamento "+nombreDepartamento(indiceDepartamento)+" son:");
        for(int x = 0;x<=numeroDiputados-1;x++){
            System.out.println((x+1)+". "+informacion.get(x)+" con la cantidad de: "+resultados.get(x)+" de votos.");
        }
    }

    public String nombrarPartido(String partido){
        String nombrePartido="";

        if(partido.equals("1")==true) {
            nombrePartido = "Partido Liberal";
        } else if(partido.equals("2")==true) {
            nombrePartido = "Partido Nacional";
        } else if(partido.equals("3")==true){
                nombrePartido = "Partido Libre";
        }


        return nombrePartido;
    }

    public static ArrayList<String> burbujaStringDiputados(ArrayList<String> nombreArchivos, ArrayList<Integer> resultados){
        int auxResultados;
        String auxNombreArchivos;
        ArrayList<String>listaOrdenada = new ArrayList<String>();

        for(int i = 0; i < resultados.size()-1; i++)
        {
            for(int j = 0;j < resultados.size()-i-1;j++)
            {
                if(resultados.get(j+1) > resultados.get(j))
                {
                    auxResultados = resultados.get(j+1);
                    resultados.set(j+1,resultados.get(j));
                    resultados.set(j,auxResultados);

                    auxNombreArchivos = nombreArchivos.get(j+1);
                    nombreArchivos.set(j+1,nombreArchivos.get(j));
                    nombreArchivos.set(j,auxNombreArchivos);

                }
            }
        }
        listaOrdenada = nombreArchivos;
        return listaOrdenada;
    }
    public static ArrayList<Integer> burbujaInt(int tipo, ArrayList<Integer>resultados) {
        int auxResultados;
        ArrayList<Integer>listaOrdenada = new ArrayList<Integer>();

        for(int i = 0; i < resultados.size()-1; i++)
        {
            for(int j = 0;j < resultados.size()-i-1;j++)
            {
                if(resultados.get(j+1) > resultados.get(j))
                {
                    auxResultados = resultados.get(j+1);
                    resultados.set(j+1,resultados.get(j));
                    resultados.set(j,auxResultados);


                }
            }
        }
        listaOrdenada = resultados;


        return listaOrdenada;
    }
    public static ArrayList<String> burbujaString(int tipo,ArrayList<String>nombreArchivos, ArrayList<String> nombres, ArrayList<String> apellidos, ArrayList<Integer>resultados, ArrayList<String>partidos) {
        int auxResultados;
        String auxNombreArchivos, auxNombres, auxApellidos, auxPartido;
        ArrayList<String>listaOrdenada = new ArrayList<String>();

        for(int i = 0; i < resultados.size()-1; i++)
        {
            for(int j = 0;j < resultados.size()-i-1;j++)
            {
                if(resultados.get(j+1) > resultados.get(j))
                {
                    auxResultados = resultados.get(j+1);
                    resultados.set(j+1,resultados.get(j));
                    resultados.set(j,auxResultados);

                    auxNombreArchivos = nombreArchivos.get(j+1);
                    nombreArchivos.set(j+1,nombreArchivos.get(j));
                    nombreArchivos.set(j,auxNombreArchivos);

                    auxNombres = nombres.get(j+1);
                    nombres.set(j+1,nombres.get(j));
                    nombres.set(j,auxNombres);

                    auxApellidos = apellidos.get(j+1);
                    apellidos.set(j+1,apellidos.get(j));
                    apellidos.set(j,auxApellidos);

                    auxPartido = partidos.get(j+1);
                    partidos.set(j+1,partidos.get(j));
                    partidos.set(j,auxPartido);

                }
            }
        }
        switch(tipo){
            case 1:
                listaOrdenada = nombreArchivos;
                break;
            case 2:
                listaOrdenada = nombres;
                break;
            case 3:
                listaOrdenada = apellidos;
                break;
            case 4:
                listaOrdenada = partidos;

            default:
                System.out.println("error!");
        }


        return listaOrdenada;
    }

    public void login(Scanner r){
        String user, pass;
        int contador = 0,eleccion;
        System.out.println("Ingrese su usuario y contraseña");
        r.nextLine();
        System.out.println("Usuario: ");
        user = r.nextLine();
        System.out.println("Contraseña: ");
        pass = r.nextLine();
        if(user.equals(usuario)&&pass.equals(contrasena)){
            menu();
        } else{
            System.out.println("El usuario y/o contraseña son incorrectos!");
            contador++;
            if(contador>=3){
                System.out.println("Ha llegado al numero maximo de intentos");
                menuPrincipal();
            }else{
                System.out.println("Desea intentar de nuevo?");
                System.out.println("1. Si\t\t\t2. No");
                eleccion=r.nextInt();
                if(eleccion==1){
                    login(r);
                } else{
                    menuPrincipal();
                }

            }

        }

    }

}
