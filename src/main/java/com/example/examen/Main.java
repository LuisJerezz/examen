package com.example.examen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.examen.modelos.Horario;
import com.example.examen.modelos.Instalacion;
import com.example.examen.modelos.Reserva;
import com.example.examen.modelos.Usuario;

public class Main {


    public static void generaFicheroSql(List<Usuario> usuarios, List<Instalacion> instalaciones, List<Horario> horarios, List<Reserva> reservas) throws IOException{
        FileWriter fw = new FileWriter("C:\\Users\\aula18\\Documents\\MANANA\\ACCESO A DATOS\\tema02\\EXAMEN\\examen\\stack\\scripts\\initdb.sql");

        fw.write("CREATE DATABASAE IF NOT EXISTS `reservas`;\n");
        fw.write("USE `reservas`;\n");

        fw.write("CREATE TABLE Usuarios (\n");
        fw.write("    id INT PRIMARY KEY,\n");
        fw.write("    username VARCHAR(50),\n");
        fw.write("    password VARCHAR(65),\n");
        fw.write("    email VARCHAR(80)\n");
        fw.write(");\n\n");

        for (Usuario usuario : usuarios) {
            fw.write(String.format("INSERT INTO Usuarios (id, username, password, email) VALUES (%d, '%s', '%s', %d);\n",
                    usuario.getId(), usuario.getUsername(), usuario.getPassword(), usuario.getEmail()));
        }
        
        fw.write("\nCREATE TABLE Instalaciones (\n");
        fw.write("  id INT PRIMARY KEY,\n");
        fw.write("  nombre VARCHAR(50)\n");
        fw.write(");\n");

        for (Instalacion instalacion : instalaciones) {
            fw.write(String.format("INSERT INTO Instalaciones (id, nombre) VALUES (%d, '%s');\n",
                    instalacion.getId(), instalacion.getNombre()));
        }

        fw.write("\nCREATE TABLE Horarios (\n");
        fw.write("  id INT PRIMARY KEY,\n");
        fw.write("  instalacion INT,\n");
        fw.write("  inicio DATE,\n");
        fw.write("  fin DATE\n");
        fw.write(");\n");

        for (Horario horario : horarios){
            fw.write(String.format("INSERT INTO Horarios (id, instalacion, inicio, fin) VALUES (%d, '%s', '%s', '%s');\n",
                    horario.getId(), horario.getInstalacion(), horario.getInicio(), horario.getFin()));
        }

        fw.write("\nCREATE TABLE Reservas (\n");
        fw.write("  id INT PRIMARY KEY,\n");
        fw.write("  usuario INT,\n");
        fw.write("  horario INT,\n");
        fw.write("  fecha DATE\n");
        fw.write(");\n");

        for (Reserva reserva : reservas){
            fw.write(String.format("INSERT INTO Reservas (id, usuario, horario, fecha) VALUES (%d, %d, %d,'%s');\n",
                reserva.getId(), reserva.getUsuario(), reserva.getHorario(), reserva.getFecha()));
        }
    }

    public static List<Usuario> leerTablaUsuarios(XSSFSheet usuarioSheet){
        List<Usuario> usuarios = new ArrayList<>();
        for (Iterator it = usuarioSheet.iterator(); it.hasNext();) {
            Row row = (Row) it.next();
            if (row.getRowNum() == 0) continue;
            Cell cellId = row.getCell(0);
            Cell cellUsername = row.getCell(1);
            Cell cellPassword = row.getCell(2);
            Cell cellEmail = row.getCell(3);
            if (cellId != null && cellUsername != null && cellPassword != null && cellEmail != null) {
                int id = (int) cellId.getNumericCellValue();
                String username = cellUsername.getStringCellValue();
                String password = cellPassword.getStringCellValue();
                String email = cellEmail.getStringCellValue();
                usuarios.add(new Usuario(id, username, password, email));
            }
        }
        return usuarios;
    }


    public static List<Instalacion> leerTablaInstalaciones(XSSFSheet instalacionesSheet){
        List<Instalacion> instalaciones = new ArrayList<>();
        for (Iterator it = instalacionesSheet.iterator();
         it.hasNext();
         ) {
            Row row = (Row) it.next();
            if (row.getRowNum() == 0) continue;  
            Cell cellId = row.getCell(0);
            Cell cellNombre = row.getCell(1);
            if (cellId != null && cellNombre != null) {
                int id = (int) cellId.getNumericCellValue();
                String nombre = cellNombre.getStringCellValue();
                instalaciones.add(new Instalacion(id, nombre));
            }
        }
        return instalaciones;
    }

    public static List<Horario> leerTablaHorarios(XSSFSheet horariosSheet){
        List<Horario> horarios = new ArrayList<>();
        for (Iterator it = horariosSheet.iterator();
         it.hasNext();
         ) {
            Row row = (Row) it.next();
            if (row.getRowNum() == 0) continue;
            Cell cellId = row.getCell(0);
            Cell cellInstalacion = row.getCell(1);
            Cell cellInicio = row.getCell(2);
            Cell cellFin = row.getCell(3);
            if (cellId != null && cellInstalacion != null && cellInicio != null && cellFin != null) {
                int id = (int) cellId.getNumericCellValue();
                int instalacion = (int) cellInstalacion.getNumericCellValue();
                String inicio = cellInicio.getStringCellValue();
                String fin = cellFin.getStringCellValue();
                horarios.add(new Horario(id, instalacion, inicio, fin));
            }
        }
        return horarios;
    }

    private static List<Reserva> leerTablaReservas(XSSFSheet reservasSheet){
        List<Reserva> reservas = new ArrayList<>();
        for (Iterator it = reservasSheet.iterator();
         it.hasNext();
         ) {
            Row row = (Row) it.next();
            if (row.getRowNum() == 0) continue;
            Cell cellId = row.getCell(0);
            Cell cellUsuario = row.getCell(1);
            Cell cellHorario = row.getCell(2);
            Cell cellFecha = row.getCell(3);
            if (cellId != null && cellUsuario != null && cellHorario != null && cellFecha != null) {
                int id = (int) cellId.getNumericCellValue();
                int usuario = (int) cellUsuario.getNumericCellValue();
                int horario = (int) cellHorario.getNumericCellValue();
                String fecha = "";
                if (cellFecha.getCellType() == CellType.STRING) {
                    fecha = cellFecha.getStringCellValue();
                } else if (cellFecha.getCellType() == CellType.NUMERIC) {
                    fecha = cellFecha.getDateCellValue().toString(); 
                }
                reservas.add(new Reserva(id, usuario, horario, fecha));
            }
        }
        return reservas;
    }

    public static void ejecutarConsulta(String consultaSQL) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33310/reservas", "root", "9127836901");
             PreparedStatement stmt = conn.prepareStatement(consultaSQL)) {
            ResultSet rs = stmt.executeQuery(consultaSQL);

            
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File("C:\\Users\\aula18\\Documents\\MANANA\\ACCESO A DATOS\\tema02\\EXAMEN\\examen\\reservas.xlsx"));
            XSSFWorkbook workbook = null;
            try {
                workbook = new XSSFWorkbook(fis);
            } catch (IOException ex) {
                System.err.println("ERROR AL ACCEDER AL XLSX");
            }

            List<Usuario> usuarios = leerTablaUsuarios(workbook.getSheet("Usuario"));
            List<Instalacion> instalaciones = leerTablaInstalaciones(workbook.getSheet("Instalacion"));
            List<Horario> horarios = leerTablaHorarios(workbook.getSheet("Horario"));
            List<Reserva> reservas = leerTablaReservas(workbook.getSheet("Reserva"));
            
            generaFicheroSql(usuarios, instalaciones, horarios, reservas);

            workbook.close();
            fis.close();
            System.out.println("\nEl archivo SQL ha sido generado con éxito.\n");
        } catch (FileNotFoundException ex) {
            System.err.println("ERROR AL ENCONTRAR EN");
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
            }
        }

    }
}