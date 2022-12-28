package com.roshka.bootcamp;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Objects;

@WebServlet("/agregar3")
public class agregar3 extends HttpServlet {
    Connection con;
    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/bootcamp_market",
                            "postgres", "rootpro");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String factura = req.getParameter("factura");
        if (Objects.equals(factura, "")){
            return;
        }
        try {

            PreparedStatement stmt = con.prepareStatement("INSERT INTO factura_tipo (id,nombre) VALUES (default, ?);");
            stmt.setString(1, factura);
            stmt.executeUpdate();
            stmt.close();
            resp.sendRedirect("./consulta3");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void destroy() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}