<#-- @ftlvariable name="" type="fr.iim.iwm.a5.alex.landeau.kotlin.article.Article" -->
<#include "components/header.ftl" />
<section id="article">
    <div class="wrapper">
        <#if session??>
            <div class="delete">
                <form method="post" action="/article/delete/${article.id}">
                    <input class="delete" type="submit" value="X" />
                </form>
            </div>
        </#if>

        <div class="content">
            <h2>${article.title}</h2>
            <p>${article.text}</p>
        </div>

        <div class="comments">
            <#list article.comments as comment>
                <div class="wrapper-comment">
                    <#if session??>
                        <form method="post" action="/article/${article.id}/comment/${comment.id}/delete">
                            <input type="submit" value="X" />
                        </form>
                    </#if>
                    <p>${comment.text}</p>
                </div>
            </#list>

            <div class="split"></div>

            <form class="comment-create" method="post" action="/article/${article.id}">
                <textarea name="text"></textarea><br />
                <input type="submit" value="Envoyer">
            </form>
        </div>
    </div>
</section>
<#include "components/footer.ftl" />