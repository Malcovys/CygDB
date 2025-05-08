<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
		<title>Cyg</title>
	</head>
	<body>
		<div id="add-table-line" class="h-screen w-full flex flex-col justify-center items-center">
			<form method="post" action="table?db_name=${ database_name }&tb_name=${ table.nom }"
				class="flex flex-col gap-5 bg-white p-5 w-165 rounded shadow-lg p-5">
				<div class="flex flex-col gap-[35px] px-5">
					<div class="border-b flex justify-between">
						<span class="font-semibold">Ajouter une nouvelle ligne</span>
					</div>
					<div class="flex justify-center w-full items-center text-lg">
						<span>Table selectionnée : </span>
						<span class="font-semibold">
						 	<jstl:out value="${ database_name }"/>.<jstl:out value="${ table.nom }"/>
						</span>
					</div>
					<div class="flex flex-col gap-2">
						<div class="items-center border-b-2 border-neutral-200">
							<span>Données</span>
						</div>
						<jstl:if test="${ !empty table }">
							<jstl:forEach var="i" begin="0" end="${fn:length(table.fields_type)-1}" step="1">
								<div class="flex px-5 py-3 bg-[#F5F5F5] gap-5">
									<label><jstl:out value="${ table.fields[i] }" /> (<jstl:out value="${ table.fields_type[i] }" />):</label>
									<jstl:choose>
									    <jstl:when test="${ table.fields_type[i] == 'int' }">
									    	<input name="value" class="border-b border-neutral-200" type="number" value="1" required>
									    </jstl:when>
									    <jstl:otherwise>
									    	<input name="value" class="border-b border-neutral-200" type="text" value="Colone1" required>
									    </jstl:otherwise>
									</jstl:choose>
								</div>
							</jstl:forEach>
						</jstl:if>
					</div>
				</div>
				<button type="submit" class="w-full bg-black hover:bg-slate-700 text-white font-bold py-1 px-20 rounded-[30px]">
					<span>Enregistrer</span>
				</button>
			</form>
			
			<jstl:if test="${ !empty error }">
				<p class="text-red-500"><jstl:out value="${ error }"/></p>
			</jstl:if>
			
		</div>	
	</body>
</html>