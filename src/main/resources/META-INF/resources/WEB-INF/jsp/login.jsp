<html>
    <head>
        <title>Login Page JSP</title>
    </head>
    <body>
        Welcome to the login page!
        <pre>${errorMessage}</pre>
        <form method="post">
            <label for="username">Name:</label> <input type="text" name="name" id="username">
            <label for="password">Password:</label> <input type="password" name="password" id="password">
            <input type="submit">
        </form>
    </body>
</html>