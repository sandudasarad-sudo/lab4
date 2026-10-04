package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/add")
public class addstudent extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String marks = req.getParameter("marks");

        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter out = resp.getWriter();

        String url = "jdbc:mysql://localhost:3306/db";
        String username = "root";
        String password = "dasarad";

        String sql = "INSERT INTO csed(name, marks) VALUES (?, ?)";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(
                    url, username, password);

                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setString(2, marks);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    out.println("<h2>Student Marks Added Successfully</h2>");
                    out.println("<p>Name: " + name + "</p>");
                    out.println("<p>Marks: " + marks + "</p>");
                } else {
                    out.println("<h2>Student Not Added</h2>");
                }
            }

        } catch (ClassNotFoundException e) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("<h2>MySQL JDBC Driver Not Found</h2>");

        } catch (SQLException e) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("<h2>Database Error: "
                    + e.getMessage() + "</h2>");
        }
    }
}