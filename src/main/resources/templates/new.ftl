<#include "components/header.ftl" />
    <section id="new-article" class="wrapper-form">
        <#if session?? >
            <form action="/article/new" method="post" class="basic-form">
                <label for="title">
                    Titre de l'article
                    <input name="title" type="text">
                </label><br />
                <label for="content">
                    Contenu de l'article
                    <textarea name="content"></textarea>
                </label><br />
                <input type="submit" value="Poster l'article" class="form-submit">
            </form>
        <#else >
            <p>Oups ! il n'y a rien sur cette page</p>
        </#if>
    </section>
<#include "components/footer.ftl" />