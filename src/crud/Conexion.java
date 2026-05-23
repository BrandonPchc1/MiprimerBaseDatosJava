/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author decab
 */
public class Conexion {
    Connection con = null;
    public Connection conectar(){
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/alumnos","root","");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error", "Error", 2);
        }
        return con;
    }
    public void leerDatos(DefaultTableModel modelo) {
    String consulta = "SELECT * FROM talumnos ORDER BY clave";

    // Limpiar tabla
    modelo.setRowCount(0);

    try (Connection conn = conectar();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(consulta)) {

       while (rs.next()) {
           Object[] fila = {
               rs.getInt("clave"),
               rs.getString("nombre"),
               rs.getInt("edad"),
               rs.getFloat("estatura")
           };
           modelo.addRow(fila);
       }

    } catch (SQLException e) {
       JOptionPane.showMessageDialog(null, "Error al leer la tabla: " + e.getMessage(), 
                                   "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void insertar(String nombre,int edad,Float estatura){
        String consulta = "Insert into talumnos(nombre,edad,estatura) values (?,?,?)";
        try (Connection con = conectar();
                PreparedStatement pstmt = con.prepareStatement(consulta)){
            pstmt.setString(1, nombre);
            pstmt.setInt(2, edad);
            pstmt.setFloat(3, estatura);
            int resultado = pstmt.executeUpdate();
            if (resultado > 0){
                JOptionPane.showMessageDialog(null, "Alumno insertado exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de insercion", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void actualizar(int clave,String nombre,int edad,Float estatura){
        String consulta = "update talumnos set nombre = ?, edad = ?,estatura =? where clave= ?";
        try (Connection con = conectar();
                PreparedStatement pstmt = con.prepareStatement(consulta)){
            pstmt.setString(1, nombre);
            pstmt.setInt(2, edad);
            pstmt.setFloat(3, estatura);
            pstmt.setInt(4, clave);
            int resultado = pstmt.executeUpdate();
            if (resultado > 0){
                JOptionPane.showMessageDialog(null, "Alumno modificado exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de modificacion", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void eliminar(int clave){
        String consulta = "delete from talumnos where clave=?";
        try (Connection con = conectar();
                PreparedStatement pstmt = con.prepareStatement(consulta)){
            pstmt.setInt(1, clave);
            
            int resultado = pstmt.executeUpdate();
            if (resultado > 0){
                JOptionPane.showMessageDialog(null, "Alumno eliminado exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de modificacion", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

