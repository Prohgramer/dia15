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
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            ResultSet rs = stmt
                    .executeQuery("SELECT count(f.id),c.nombre,c.apellido,c.nro_cedula FROM cliente c join factura f on c.id = f.cliente_id GROUP BY c.nombre,c.apellido,c.nro_cedula ORDER BY count DESC");
            out.println("<html>");
            out.println("<body>");
            out.println("<h1>Cliente con mas factura</h1>");
            while (rs.next()) {
                int cantidad = rs.getInt("count");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String cedula = rs.getString("nro_cedula");

                out.println("<p>NOMBRE = " + nombre + "</p>");
                out.println("<p>APELLIDO = " + apellido + "</p>");
                out.println("<p>NRO. Cedula = " + cedula + "</p>");
                out.println("<p>CANTIDAD FACTURA = " + cantidad + "</p>");

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
