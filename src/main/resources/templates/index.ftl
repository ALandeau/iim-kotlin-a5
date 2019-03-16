<#include "components/header.ftl" />
<section id="home">
    <#list articles as article>
        <div class="wrapper">
            <h2>
                <a href="/article/${article.id}">${article.title}</a>
            </h2>
            <#if session??>
                <form method="post" action="/article/delete/${article.id}">
                    <input class="delete" type="submit" value="X">
                </form>
            </#if>
        </div>
    </#list>
    <#if session?? >
        <div class="new-article">
            <a href="/article/new">Créer un nouvel article</a>
        </div>
    </#if>
</section>
<#include "components/footer.ftl" />
