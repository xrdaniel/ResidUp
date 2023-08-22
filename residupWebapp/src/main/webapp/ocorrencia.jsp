<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="br.com.residup.models.Ocorrencia"%>
<%@page import="br.com.residup.models.Morador" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList"%>


<%
    ArrayList<Ocorrencia> lista = (ArrayList<Ocorrencia>) request.getAttribute("ocorrencias");
    Morador morador = (Morador) request.getAttribute("morador");

%>

<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="../css/ocorrenciamorador.css" rel="stylesheet" type="text/css"/>
         <link href="css/perfil.css" rel="stylesheet" type="text/css">
        <link rel="shortcut icon" href="imagens/LogoHeader.png" type="image/x-icon">

        <title>Ocorrências</title>
    </head>
    <body>
            <body class="bodyCorNova">
            <div class="header" id="header">
                <button onclick="toggleSidebar()" class="btn_icon_header">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-list" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5z"/>
                    </svg>
                </button>
                <div class="logo_header">
                    <img src="../imagens/img/LogoHeader.png" alt="Logo ResidUP" class="img_logo_header">
                </div>
                <div class="navigation_header" id="navigation_header">
                    <button onclick="toggleSidebar()" class="btn_icon_header">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                        <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                        </svg>
                    </button>
                    <a href="/listarResumo">PAGINA INICIAL</a>
                    <a href="/reservas" >RESERVAR ÁREA</a>
                    <a href="/visitantes">CONTROLE DE VISITANTES</a>
                    <a href="/Ocorrencia" class="active">REGISTRO DE OCORRENCIAS</a>
                </div>

                 <div id="perfs">
                     <button type="button" onclick="openModalteste()" id="openModal">Meu Perfil</button>
                     <a href="/logout"><button id="sair" > Sair</button></a>
                 </div>


            </div>



            <!--Fim da header-->
        <section class="container">
            <div class="NovaOcor">
                <div class="textNovaOcorrencia">
                <h1 class="h1NovaOcorrencia">Nova ocorrência</h1>
                </div>
                <form action="occurrenceInsert" method="post">
                    <div class="buttons">
                        <input type="text" placeholder="Título da ocorrência" name="titulo" required>
                        <button id="deletarOcorrencia" type="submit">
                           Salvar ocorrência
                        </button>
                    </div>
                    <div class="character">
                        <textarea name="texto" id="txtcharacter" cols="30" rows="10" required></textarea>
                    </div>
                </form>
            </div>
        </section>
        <section class="container">
            <div class="wrapper">
                <form class="filtro" action="Ocorrencia" method="GET">
                  <div class="text">
                    <h1>Minhas Ocorrências</h1>
                  </div>
                    <button type="submit" class="filtrar">Filtrar</button>

                  <select class="form-status" id="form-status" name="form-status">
                    <option value="Em aberto" <c:if test="${filtroOcorrencias.equalsIgnoreCase('Em aberto')}">selected</c:if>>Em aberto</option>
                    <option value="todos" <c:if test="${filtroOcorrencias.equalsIgnoreCase('Todos')}">selected</c:if>>Todos</option>
                    <option value="Em analise" <c:if test="${filtroOcorrencias.equalsIgnoreCase('Em analise')}">selected</c:if>>Em análise</option>
                    <option value="Em andamento" <c:if test="${filtroOcorrencias.equalsIgnoreCase('Em andamento')}">selected</c:if>>Em andamento</option>
                    <option value="Resolvido" <c:if test="${filtroOcorrencias.equalsIgnoreCase('Resolvido')}">selected</c:if>>Resolvido</option>
                  </select>
                </form>
                <c:forEach var="ocorrencia" items="${ocorrencias}">
                    <div class="group">
                        <label>${ocorrencia.getTitulo()}</label>
                        <label class="oc">${ocorrencia.getTexto()}</label>
                        <label class="or">${ocorrencia.getStatus()}</label>
                        <label class="editar" for="deletarOcorrencia">
                            <button id="deletarOcorrencia" class="ed" onclick="deletarOcorrencia(${ocorrencia.getId_ocorrencia()})">Excluir</button>
                        </label>
                    </div>

                </c:forEach>
            </div>
        </section>


        <!-- JavaScript Link -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.3/dist/sweetalert2.min.css "rel="stylesheet">
        <script src="scripts/scriptsOcorrencia.js"></script>
        <script src="scripts/redutortamtext.js"></script>


        <c:if test="${not empty mensagem}">
            <%-- Exibe o alerta somente se a mensagem não for nula --%>
            <script>
                <%= request.getAttribute("mensagem")%>
            </script>
        </c:if>
        <script src="scripts/script.js"></script>
         <script src="../scripts/perfil.js"></script>
    </body>
</html>


