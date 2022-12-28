package com.roshka.bootcamp;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.*;
@WebServlet("/consulta1")
public class consulta1 extends HttpServlet{
    Connection connection;
    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/bootcamp_market",
                            "postgres", "rootpro");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            Statement stmt = connection.createStatement();

            PrintWriter out = res.getWriter();
            ResultSet rs = stmt
                    .executeQuery("SELECT nombre,apellido,nro_cedula,telefono FROM cliente");
            out.println("<html>");
            out.println("<body>");
            out.println("<h1>Clientes</h1>");
            while (rs.next()) {

                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String cedula = rs.getString("nro_cedula");
                String telefono = rs.getString("telefono");
                out.println("<p>NOMBRE = " + nombre + "</p>");
                out.println("<p>APELLIDO = " + apellido + "</p>");
                out.println("<p>NRO. Cedula = " + cedula + "</p>");
                out.println("<p>telefono = " + telefono + "</p>");

            }
            out.println("</body>");
            out.println("</html>");
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }


    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
