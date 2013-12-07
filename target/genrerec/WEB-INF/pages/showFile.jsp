<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>MI-VMW - Ultimate music recognizer | Jan Dufek and Jan Bouchner</title>
    </head>
    <body>
        <br />
        <br />
    <center>
        <h1>MI-VMW - Ultimate music recognizer</h1>
        <h2>by Jan Dufek &amp; Jan Bouchner</h2>
        <img src="${pageContext.request.contextPath}/note.jpeg" alt="note">
        <h3>
            Song <p style="color:green; display:inline">${model.message}</p> uploaded successfully! Lets look at recognized genre:
        </h3>

        <table border="1">
            <thead>
                <tr>
                    <th>Genre</th>
                    <th>Success rate (%)</th>
                </tr>
            </thead>
            <tbody>        
                <c:forEach items="${model.genres}" var="genre">
                    <tr>
                        <td>${genre.key}</td>
                        <td>${genre.value}</td>
                    </tr>           
                </c:forEach>     
            </tbody>
        </table>                      

        <br />

    </center>
</body>
</html>
