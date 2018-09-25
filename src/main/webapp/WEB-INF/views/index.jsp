<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib              uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<form action="${pageContext.request.contextPath}/" method="get">
    <!--Hidden fields-->
    <input type="hidden" id="page"        name="page" value="${page}" />
    <input type="hidden" id="deleteID"    name="deleteID"             />
    <input type="hidden" id="activateID"  name="activateID"           />
    <input type="hidden" id="editID"      name="editID"               />
    <input type="hidden" id="updateID"    name="updateID"             />
    <input type="hidden" id="filterField" name="filterField" value="${filter}" />
    <input type="hidden" id="addNewPart"  name="addNewPart"           />

    <!--MainForm-->
    <div class="mainBlocks">
		
		<!--AddNewPart-->
            <div style="float:left;">
                <!--button: add-->
                <c:if test="${empty addNewPart}">
                    <button onclick="document.getElementById('addNewPart').value = '1';">
                        add
						<!--img class="icons" src= "resources/img/add.png" /-->
                    </button>
                </c:if>
                <!--button: cancel_for_add-->
                <c:if test="${not empty addNewPart}">
                    <button onclick="document.getElementById('addNewPart').value = '';">
                        cancel
						<!--img class="icons" src= "resources/img/yellow_minus.png" /-->
                    </button>
                </c:if>
            </div>

        <!--form_for_add_new_part-->
        <c:if test="${not empty addNewPart}">
        <div class="form_for_add_new_part">
            name    <input type="text"      id="addTitle"   name="addTitle" style="width: 140px;" />
            enabled <input type="checkbox"  id="addEnabled" name="addEnabled" />
            amount  <input type="number"    id="addAmount"  name="addAmount" style="width: 40px;"/>
            <button><!--img class="icons" src="resources/img/save.png" /--> save </button>
        </div>
        </c:if>

        <!--Parts table-->
        <!--Head_of_table-->
        <table style="clear: both; margin: 40px 0 0 0;">
            <tr class="tableHeader">
                <td width="30  ">del         </td>
                <td width="300 ">Наименование</td>
                <!--Filter-->
                <td width="30"> Необходимость
                    <input type="hidden" id="newFilter" name="newFilter" />
                    <button type="submit"  onclick="document.getElementById('newFilter').value = '${1}';">
                        <c:choose>
                            <c:when test="${requestScope.filter eq 'NONE'}">
                                <!--img class="icons" src="resources/img/none.png" /-->
								Все
                            </c:when>
                            <c:when test="${requestScope.filter eq 'ACTIVE'}">
                                <!--img class="icons" src="resources/img/enabled.png" /-->
								Нужные
                            </c:when>
                            <c:when test="${requestScope.filter eq 'DISABLED'}">
                                <!--img class="icons" src="resources/img/disabled.png" /-->
								Остальные
                            </c:when>
                        </c:choose>
                    </button>
                </td>
                <td width="90">Количество               </td>
                <td width="30">edt                      </td>
            </tr>
            <!--Body_of_table-->
            <c:forEach var="part" varStatus="loopStatus" items="${parts}" >
                <tr style="<c:if test="${!part.isEnabled()}">color: silver;</c:if>
                        background-color: ${loopStatus.index % 2 == 0 ? '#F7F8E0;/>' : '#D8D8D8;/>'}">
                    <!--Delete button-->
                    <td align="left" class="whiteBG">
                        <button class="serviceButton" onclick="document.getElementById('deleteID').value = '${part.getId()}';">
                            <!--img class="icons" src="resources/img/delete.png" /-->
							x
                        </button>
                    </td>
                    <!--Title-->
                    <td align="left">
                                <c:if test="${empty editIDInt}">
                                    ${part.getTitle()}
                                </c:if>
                                <c:if test="${part.getId() eq editIDInt}">
                                    <input type="text" id="updateTitle"   name="updateTitle" value="${part.getTitle()}"  />
                                </c:if>
                    </td>
                    <!--Enabled status-->
                    <td align="center">
                        <c:if test="${empty editIDInt}">
                        <button type="submit" style="border: 0; ${loopStatus.index % 2 == 0 ? 'background-color: #F7F8E0;/>' : 'background-color: #D8D8D8;/>'}" onclick="document.getElementById('activateID').value = '${part.getId()}';">
                            <c:if test="${part.isEnabled()}">
                                <!--img class="icons" src="resources/img/enabled.png" /--> v </c:if>
                            <c:if test="${!part.isEnabled()}">
                                <!--img class="icons" src="resources/img/disabled.png" /--->  </c:if>
                        </button>
                        </c:if>
                        <c:if test="${part.getId() eq editIDInt}">
                            <input type="hidden" id="saveEnabled"   name="saveEnabled" value="${part.isEnabled()}" />
                        </c:if>
                    </td>
                    <!--Amount-->
                    <td align="center">
                        <c:if test="${empty editIDInt}">
                            ${part.getAmount()}
                        </c:if>
                        <c:if test="${part.getId() eq editIDInt}">
                            <input type="number" id="updateAmount"   name="updateAmount" value="${part.getAmount()}" style="width: 45px;" />
                        </c:if>
                    </td>
                    <!--Edit/Update button-->
                    <td align="right" class="whiteBG">
                        <c:if test="${empty editIDInt}">
                            <button class="serviceButton" onclick="document.getElementById('editID').value = '${part.getId()}';">
                                <!--img  class="icons" src="resources/img/edit.png" /-->
								edit
                            </button>
                        </c:if>
                        <c:if test="${part.getId() eq editIDInt}">
                            <button class="serviceButton" onclick="document.getElementById('updateID').value = '${part.getId()}';">
                                <!--img  class="icons" src="resources/img/save.png" /-->
								save
                            </button>
                        </c:if>
                    </td>
                </tr>

            </c:forEach>
        </table>
		
		
        <!--Search-->
        <div>
                <input type="text" id="searchTitle"   name="searchTitle"  value="${searchTitle}"/>
                <c:if test="${empty searchTitle}">
                <button>
                    <!--img class="smallIcons" src="resources/img/search.png" /-->
					search
                </button>
                </c:if>
                <c:if test="${not empty searchTitle}">
                <button onclick="document.getElementById('searchTitle').value = '';">
                    <!--img class="smallIcons" src="resources/img/cancel.png" /-->
					cancel
                </button>
                </c:if>

        </div>
        
		
    </div>

    <!--Pages-->
    <div class="mainBlocks">
        <c:forEach  begin="1" end="${pagesCalc}" varStatus="loop" >
            <c:set var="countB" value="${countB+1}"/>
            <button class="<c:if test="${countB eq page}">selected_</c:if>pageButton"
                    onclick="document.getElementById('page').value = '${countB}';" >
                    ${countB}
            </button>
        </c:forEach>
    </div>

    <!--Statistic-->
    <div class="mainBlocks">
        <p>Можно собрать: <b>${sborka}</b> </p>
    </div>

</form>

