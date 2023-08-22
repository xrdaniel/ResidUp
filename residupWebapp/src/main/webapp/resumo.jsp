<%@page import="br.com.residup.models.Morador" %>
    <%@page import="br.com.residup.models.Ocorrencia" %>
        <%@page import="br.com.residup.models.Visitante" %>
            <%@page import="br.com.residup.models.Reserva" %>
            <% Morador morador = (Morador) request.getAttribute("morador");%>

                <%--<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>--%>
                    <%@page import="java.util.ArrayList" %>
                        <% @SuppressWarnings( "unchecked" ) ArrayList<Ocorrencia> listaOcorrencia = (ArrayList<Ocorrencia>) request.getAttribute("minhasOcorrencias");
                                ArrayList<Reserva> listaReserva = (ArrayList<Reserva>) request.getAttribute("minhasReservas");
                                        ArrayList<Visitante> listaVisitante = (ArrayList<Visitante>) request.getAttribute("meusVisitantes");

                                                %>
                                                <!DOCTYPE html>
                                                <html lang="en">

                                                <head>
                                                    <meta charset="UTF-8">
                                                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                                                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                                    <link href="css/perfil.css" rel="stylesheet" type="text/css">
                                                   <link href="../css/resumo.css" rel="stylesheet" type="text/css" />
                                                    <link rel="shortcut icon" href="imagens/LogoHeader.png" type="image/x-icon">

                                                    <title>Resumo</title>
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
                                                                    <a href="/listarResumo" class="active">PAGINA INICIAL</a>
                                                                    <a href="/reservas" >RESERVAR ÁREA</a>
                                                                    <a href="/visitantes">CONTROLE DE VISITANTES</a>
                                                                    <a href="/Ocorrencia">REGISTRO DE OCORRENCIAS</a>
                                                                </div>
                                                                  <div id="perfs">
                                                                     <button type="button" onclick="openModalteste()" id="openModal">Meu Perfil</button>
                                                                      <a href="/logout"><button id="sair" > Sair</button></a>
                                                                  </div>

                                                            </div>


                                                    <!-- Fim Header -->

                                                    <c:if test="${not empty listaOcorrencia}">

                                                        <div class="mo">
                                                            <div class="title">
                                                                <h3>Minhas Ocorrências</h3>
                                                            </div>
                                                            <div class="scrollable-div">

                                                                <%for (Ocorrencia ocorrencia : listaOcorrencia) {%>
                                                                    <div class="group">
                                                                        <label>
                                                                            <%=ocorrencia.getTitulo()%>
                                                                        </label>
                                                                        <label class="oc">
                                                                            <%=ocorrencia.getTexto()%>
                                                                        </label>
                                                                        <label class="or">
                                                                            <%=ocorrencia.getStatus()%>
                                                                        </label>
                                                                        <a href="/Ocorrencia" class="editar"><button class="ed" type='submit'>Acessar minha ocorrência</button></a>
                                                                    </div>
                                                                    <%}%>
                                                            </div>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${not empty listaVisitante}">
                                                        <div class="mv">
                                                            <div class="title">
                                                                <h3>Meus Visitantes</h3>
                                                            </div>
                                                            <div class="scrollable-div">
                                                                <%for (Visitante visitante : listaVisitante) {%>
                                                                    <div class="group">
                                                                        <input type="text" name="ID" required value="<%=visitante.getId()%>">
                                                                        <input class="nm" name="nome" disabled value="<%=visitante.getNome() + ' ' + visitante.getSobrenome()%>">
                                                                        <input class="x" name="doc" disabled value="<%=visitante.getDocumento()%>">
                                                                        <input class="oc" name="fone" disabled value="<%=visitante.getFone()%>">
                                                                        <a href="/visitantes"><button class="editarcad">Acessar meu visitante</button></a>
                                                                    </div>
                                                                    <%}%>
                                                            </div>
                                                        </div>
                                                        </div>
                                                        </div>
                                                    </c:if>


                                                    <c:if test="${not empty listaReserva}">
                                                        <div class="mr">
                                                            <div class="title">
                                                                <h3>Minhas Reservas</h3>
                                                            </div>
                                                            <div class="scrollable-div">
                                                                <%for (Reserva reserva : listaReserva) {%>
                                                                    <div class="group">
                                                                        <label>
                                                                            <%=reserva.getNomeArea()%>
                                                                        </label>
                                                                        <label class="oc">
                                                                            <%=reserva.getHoraReserva()%>
                                                                        </label>
                                                                        <label class="dt">
                                                                            <%=reserva.getDateReserva()%>
                                                                        </label>
                                                                        <a href="/reservas"><button class="editar" type='submit'>Acessar minha reserva</button></a>
                                                                    </div>
                                                                    <%}%>
                                                            </div>
                                                        </div>
                                                    </c:if>


                                                    <script src="scripts/redutortamtext.js"></script>
                                                     <script src="../scripts/perfil.js"></script>

                                                </body>

                                                </html>