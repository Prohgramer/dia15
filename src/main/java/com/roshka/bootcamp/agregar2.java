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

@WebServlet("/agregar2")
public class agregar2 extends HttpServlet {
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

        String moneda = req.getParameter("moneda");
        if (Objects.equals(moneda, "")){
            return;
        }
        try {

            PreparedStatement stmt = con.prepareStatement("INSERT INTO moneda (id, nombre) VALUES (default, ?);");
            stmt.setString(1, moneda);
            stmt.executeUpdate();
            stmt.close();
            resp.sendRedirect("./consulta1");
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