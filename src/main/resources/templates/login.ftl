<#include "components/header.ftl" />
    <section id="login" class="wrapper-form">
        <form action="/login" method="post">
            <label for="username">
                Nom d'utilisateur
                <input name="username" type="text"/>
            </label>
            <label for="password">
                Mot de passe
                <input name="password" type="password"/>
            </label>
            <input type="submit" value="Connexion" class="form-submit" />
        </form>
        <#if erreur?? >
            <p class="erreur">${erreur}</p>
        </#if>
    </section>
<#include "components/footer.ftl" />