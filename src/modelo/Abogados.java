/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import vista.frmAbogados;

/**
 *
 * @author Estudiante
 */
public class Abogados {
    
    ////////////////////////1- Parametros
    private String UUID_Abogado;
    private String nombre;
    private int edad;
    private int peso;
    private String correo;
    
    
    ////////////////////////2- Metodos get y set
    public String getUUID_Abogado() {
        return UUID_Abogado;
    }

    public void setUUID_Abogado(String UUID_Abogado) {
        this.UUID_Abogado = UUID_Abogado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    ////////////////////////3- Métodos 
    public void Guardar() {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClaseConexion.getConexion();
        try {
            //Variable que contiene la Query a ejecutar
            String sql = "INSERT INTO tbAbogados(UUID_Abogado, Nombre_Abogado, Edad_Abogado, Peso_Abogado, Correo_Abogado) VALUES (?, ?, ?, ?, ?)";
            //Creamos el PreparedStatement que ejecutará la Query
            PreparedStatement addAbogado = conexion.prepareStatement(sql);
            //Establecer valores de la consulta SQL
            addAbogado.setString(1, UUID.randomUUID().toString());
            addAbogado.setString(2, getNombre());
            addAbogado.setInt(3, getEdad());
            addAbogado.setInt(4, getPeso());
            addAbogado.setString(5, getCorreo());
            
            //Ejecutar la consulta
            addAbogado.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("este es el error en el modelo:metodo guardar " + ex);
        }
    }

    public void Mostrar(JTable tabla) {
        //Creamos una variable de la clase de conexion
        Connection conexion = ClaseConexion.getConexion();
        //Definimos el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"UUID_Abogado", "Nombre_Abogado", "Edad_Abogado", "Peso_Abogado", "Correo_Abogado"});
        try {
            //Consulta a ejecutar
            String query = "SELECT * FROM tbAbogados";
            //Creamos un Statement
            Statement statement = conexion.createStatement();
            //Ejecutamos el Statement con la consulta y lo asignamos a una variable de tipo ResultSet
            ResultSet showAbogado = statement.executeQuery(query);
            //Recorremos el ResultSet
            while (showAbogado.next()) {
                //Llenamos el modelo por cada vez que recorremos el resultSet
                modelo.addRow(new Object[]{showAbogado.getString("UUID_Abogado"), 
                    showAbogado.getString("Nombre_Abogado"), 
                    showAbogado.getInt("Edad_Abogado"), 
                    showAbogado.getInt("Peso_Abogado"), 
                    showAbogado.getString("Correo_Abogado")});
            }
            //Asignamos el nuevo modelo lleno a la tabla
            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setWidth(0);
        } catch (Exception e) {
            System.out.println("Este es el error en el modelo, metodo mostrar " + e);
        }
    }

    public void Eliminar(JTable tabla) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClaseConexion.getConexion();

        //obtenemos que fila seleccionó el usuario
        int filaSeleccionada = tabla.getSelectedRow();
        //Obtenemos el id de la fila seleccionada

        String miId = tabla.getValueAt(filaSeleccionada, 0).toString();
        //borramos 
        try {
            String sql = "delete from tbAbogados where UUID_Abogado = ?";
            PreparedStatement deleteAbogado = conexion.prepareStatement(sql);
            deleteAbogado.setString(1, miId);
            deleteAbogado.executeUpdate();
        } catch (Exception e) {
            System.out.println("este es el error metodo de eliminar" + e);
        }
    }

    public void Actualizar(JTable tabla) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClaseConexion.getConexion();

        //obtenemos que fila seleccionó el usuario
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada != -1) {
            //Obtenemos el id de la fila seleccionada
            String miUUId = tabla.getValueAt(filaSeleccionada, 0).toString();

            try {
                //Ejecutamos la Query
                String sql = "update tbAbogados set Nombre_Abogado = ?, Edad_Abogado = ?, Peso_Abogado = ?, Correo_Abogado = ? where UUID_Abogado = ?";
                PreparedStatement updateUser = conexion.prepareStatement(sql);

                updateUser.setString(1, getNombre());
                updateUser.setInt(2, getEdad());
                updateUser.setInt(3, getPeso());
                updateUser.setString(4, getCorreo());
                updateUser.setString(5, miUUId);
                updateUser.executeUpdate();

            } catch (Exception e) {
                System.out.println("este es el error en el metodo de actualizar" + e);
            }
        } else {
            System.out.println("no");
        }
    }

    public void Buscar(JTable tabla, JTextField miTextField) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClaseConexion.getConexion();

        //Definimos el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"UUID_Abogado", "Nombre_Abogado", "Edad_Abogado", "Peso_Abogado", "Correo_Abogado"});
        try {
            String sql = "SELECT * FROM tbAbogados WHERE Nombre_Abogado LIKE ? || '%'";
            PreparedStatement searchAbogado = conexion.prepareStatement(sql);
            searchAbogado.setString(1, miTextField.getText());
            ResultSet rs = searchAbogado.executeQuery();

            while (rs.next()) {
                //Llenamos el modelo por cada vez que recorremos el resultSet
                modelo.addRow(new Object[]{rs.getString("UUID_Abogado"), rs.getString("Nombre_Abogado"), rs.getInt("Edad_Abogado"), rs.getInt("Peso_Abogado"), rs.getString("Correo_Abogado")});
            }

            
            //Asignamos el nuevo modelo lleno a la tabla
            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setWidth(0);
        } catch (Exception e) {
            System.out.println("Este es el error en el modelo, metodo de buscar " + e);
        }
    }

    public void limpiar(frmAbogados vista) {
        vista.txtNombre.setText("");
        vista.txtEdad.setText("");
        vista.txtPeso.setText("");
        vista.txtCorreo.setText("");
    }

    public void cargarDatosTabla(frmAbogados vista) {
        // Obtén la fila seleccionada 
        int filaSeleccionada = vista.jtbAbogado.getSelectedRow();

        // Debemos asegurarnos que haya una fila seleccionada antes de acceder a sus valores
        if (filaSeleccionada != -1) {
            String UUIDDeTb = vista.jtbAbogado.getValueAt(filaSeleccionada, 0).toString();
            String NombreDeTB = vista.jtbAbogado.getValueAt(filaSeleccionada, 1).toString();
            String EdadDeTb = vista.jtbAbogado.getValueAt(filaSeleccionada, 2).toString();
            String PesoDeTB = vista.jtbAbogado.getValueAt(filaSeleccionada, 3).toString();
            String CorreoDeTB = vista.jtbAbogado.getValueAt(filaSeleccionada, 4).toString();

            // Establece los valores en los campos de texto
            vista.txtNombre.setText(NombreDeTB);
            vista.txtEdad.setText(EdadDeTb);
            vista.txtPeso.setText(PesoDeTB);
            vista.txtCorreo.setText(CorreoDeTB);
        }
    }
    
}
