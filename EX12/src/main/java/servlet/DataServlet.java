/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DataServlet", urlPatterns = {"/DataServlet"})
public class DataServlet extends HttpServlet {

    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String sqlQuery = request.getParameter("query");
        String jdbcUrl = "jdbc:mysql://azure-server-murach-123.mysql.database.azure.com:3306/murach2?zeroDateTimeBehavior=CONVERT_TO_NULL";  // Cập nhật tên database
        String username = "ductho"; // Cập nhật username
        String password = "Tho0411@"; // Cập nhật password

        List<String[]> results = new ArrayList<>();
        String message = null;
        
        try {
            // Load driver và kết nối đến MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();
            boolean isResultSet = statement.execute(sqlQuery); // Thực thi câu lệnh SQL

            // Kiểm tra nếu là truy vấn SELECT
            if (isResultSet) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    results.add(new String[]{email, firstName, lastName});
                }
                resultSet.close();
            } else {
                // Nếu không phải truy vấn SELECT, thì nó là INSERT, UPDATE, DELETE
                int affectedRows = statement.getUpdateCount();
                message = "Truy vấn thành công! Số hàng bị ảnh hưởng: " + affectedRows;
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            message = "Lỗi khi thực thi truy vấn: " + e.getMessage();
        }

        // Đưa kết quả và thông báo vào request attributes
        request.setAttribute("results", results);
        request.setAttribute("message", message);

        // Quay lại trang index.jsp để hiển thị kết quả
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
