<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/static/css/app.css">
    <title>Alex Landeau - TP Kotlin</title>
</head>
<body>
    <header>
        <nav>
            <div class="left-item"><a href="/">Home</a></div>
            <div class="right-item">
                <#if session?? >
                    <p>Bonjour <span>${session.username}</span></p>
                    <p class="logout"><a href="/logout">Déconnexion</a></p>
                <#else>
                    <p><a href="/login">Connexion</a></p>
                </#if>
            </div>
        </nav>
    </header>