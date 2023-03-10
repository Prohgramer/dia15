package com.roshka.bootcamp;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.*;
@WebServlet("/consulta2")
public class consulta2 extends HttpServlet{
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
                    .executeQuery("SELECT id,nombre FROM moneda");
            out.println("<html>");
            out.println("<body>");
            out.println("<h1>Moneda mas usada</h1>");
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int id=rs.getInt("id");
                out.println("<p>ID: moneda = " + id + "</p>");
                out.println("<p>NOMBRE = " + nombre + "</p>");


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
