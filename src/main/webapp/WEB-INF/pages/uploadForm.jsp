<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css.css" />
<title>MI-VMW - Ultimate music recognizer | Jan Dufek and Jan Bouchner</title>
</head>
<body>
    <br />
	<br />
	<center>
                <h1>MI-VMW - Ultimate music recognizer</h1>
		<h2>by Jan Dufek &amp; Jan Bouchner</h2>
                <img src="${pageContext.request.contextPath}/note.jpeg" alt="note">
		<h3>Please select a music (mp3) to upload!</h3>
		<br />
		<form:form method="post" enctype="multipart/form-data"
			modelAttribute="uploadedFile" action="fileUpload.htm">
			<table>
				<tr>
					<td>Upload song:&nbsp;</td>
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
	</center>
</body>
</html>
