<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>MI-VMW - Ultimate music recognizer | Jan Dufek and Jan Bouchner</title>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="http://code.highcharts.com/highcharts.js"></script>
        <script src="http://code.highcharts.com/modules/exporting.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js.js"></script>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css.css" />
    </head>
    <body>
        <br />
        <br />
    <center>
        <h1>MI-VMW - Ultimate music recognizer</h1>
        <h2>by Jan Dufek &amp; Jan Bouchner</h2>
        <img src="${pageContext.request.contextPath}/note.jpeg" alt="note">
        <h3>Please select a music (mp3) to upload!</h3>
        
        <h3>
            Song <p style="color:green; display:inline">${model.message}</p> uploaded successfully! Lets look at recognized genre:
        </h3>

        <table border="1" class="data">
            <tbody>        
                <c:forEach items="${model.genres}" var="genre">
                    <tr>
                        <td class="gName">${genre.key}</td>
                        <td class="val">${genre.value}</td>
                    </tr>           
                </c:forEach>     
            </tbody>
        </table>      
        
        <div id="graph" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

        <br />
        <form:form method="post" enctype="multipart/form-data"
                modelAttribute="uploadedFile" action="fileUpload.htm">
                <table>
                        <tr>
                                <td>Upload another song:&nbsp;</td>
                                <td><input type="file" name="file" />
                                </td>
                                <td style="color: red; font-style: italic;"><form:errors
                                                path="file" />
                                </td>
                        </tr>
                        <tr>
                                <td>&nbsp;</td>
                                <td><input type="submit" value="Upload" />
                                </td>
                                <td>&nbsp;</td>
                        </tr>
                </table>
        </form:form>
        <br />

    </center>
</body>
</html>
