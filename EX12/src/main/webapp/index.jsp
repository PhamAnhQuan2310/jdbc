<%-- 
    Document   : index
    Created on : Oct 8, 2024, 3:35:25 PM
    Author     : ANH QUAN
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="includes/header.html" %>

    <h1>Trang nhập lệnh truy vấn</h1>
    <form action="query" method="post">
        <p>SQL Query: </p><br>
        <textarea id="id" name="query" rows="20" cols="100"></textarea><br><br>
        <input type="submit" value="Thực hiện">
    </form>

    <%
        // Hiển thị kết quả sau khi truy vấn
        String message = (String) request.getAttribute("message");
        List<String[]> results = (List<String[]>) request.getAttribute("results");

        if (message != null) {
            out.println("<h2>" + message + "</h2>");
        }

        if (results != null && !results.isEmpty()) {
            out.println("<h2>Kết quả truy vấn</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>Email</th><th>First Name</th><th>Last Name</th></tr>");
            for (String[] row : results) {
                out.println("<tr><td>" + row[0] + "</td><td>" + row[1] + "</td><td>" + row[2] + "</td></tr>");
            }
            out.println("</table>");
        }
    %>
</body>
</html>
