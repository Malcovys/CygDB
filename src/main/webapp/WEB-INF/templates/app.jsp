<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8" />
	    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
		<title>Cyg</title>
	</head>
	<body class="flex flex-row gap-[10px] bg-[#F5F5F5] mt-1">
	
		<div id="left-container" class="flex flex-col gap-y-5 py-[5px] w-65">
			<div class="w-full pl-5 border-b">
				<span class="font-semibold">Bases de données</span>
			</div>
			<div class="w-full flex items-center justify-center">
				<button id="new-db-btn" class="bg-black hover:bg-slate-700 text-white font-bold py-1 px-20 rounded-[30px] flex">
					<span>Nouveau</span>
				</button>
			</div>
			<div class="w-full bg-white py-3 px-2">
				<jstl:forEach var="database" items="${ databases }">
					<div id="${ database.nom }" class="db w-full">
						<div class="flex w-full gap-x-2 flex items-center">
							<button type="button" class="show-db-table-btn hover:bg-stone-50 rounded-xl p-1">
								<%@include file="/WEB-INF/statics/svgs/chevron_right.svg" %>
							</button>
							<span><%@include file="/WEB-INF/statics/svgs/database.svg" %></span>
							<span><jstl:out value="${ database.nom }" /></span>
							<div class="w-full flex justify-end">
								<button class="new-db-table-btn hover:bg-stone-50 rounded-xl p-1">
									<%@include file="/WEB-INF/statics/svgs/add_circle.svg" %>
								</button>
							</div>
						</div>
						<span class="db-table-container w-full" hidden=true>
							<jstl:forEach var="table" items="${ database.tables }">
								<div class="w-full" >
									<div class="flex w-full gap-x-2 pl-15 flex items-center">
										<span><%@include file="/WEB-INF/statics/svgs/table.svg" %></span>
										<span><jstl:out value="${ table.nom }" /></span>
										<div class="w-full flex justify-end">
											<a href="database/table?action=new_line&db_name=${ database.nom }&tb_name=${ table.nom }"
												class="new-tb-line-btn hover:bg-stone-50 rounded-xl p-1">
												<%@include file="/WEB-INF/statics/svgs/add_circle.svg" %>
											</a>
										</div>
									</div>
								</div>
							</jstl:forEach>
						</span>
					</div>
				</jstl:forEach>
			</div>
		</div>
		
		<div id="right-container" class="w-full">
			<div class="flex justify-between border-b">
				<div class="bg-white flex items-center px-1 border-t-4">
					<span><%@include file="/WEB-INF/statics/svgs/table.svg" %></span>
					<span>utilisateurs</span>
				</div>
				<button class="bg-black hover:bg-slate-700 text-white font-bold m-2 py-2 px-4 rounded-full">
					<span>Modifier</span>
				</button>
			</div>
			<table class="table-fixed border-collapse border">
				<thead>
				    <tr>
				      <th class="border">id</th>
				      <th class="border">nom</th>
				      <th class="border">prenom</th>
				      <th class="border">email</th>
				      <th class="border">password</th>
				    </tr>
			  	</thead>
			  	<tbody>
				    <tr class="bg-white hover:bg-stone-500">
				      <th class="border border-gray-500 px-2">1</th>
				      <td class="border border-gray-500 px-2">Leano</td>
				      <td class="border border-gray-500 px-2">Joanoro</td>
				      <td class="border border-gray-500 px-2">leanojpanoro@gmail.com</td>
				      <td class="border border-gray-500 px-2">123345652562223</td>
				    </tr>
				    <tr class="bg-white hover:bg-stone-500">
				      <th class="border border-gray-500 px-2">2</th>
				      <td class="border border-gray-500 px-2">Jeano</td>
				      <td class="border border-gray-500 px-2">patrik</td>
				      <td class="border border-gray-500 px-2">partik@gmail.com</td>
				      <td class="border border-gray-500 px-2">123345652562223</td>
				    </tr>
				 </tbody>
			</table>
			
			<jstl:if test="${ !empty error }">
				<p class="text-red-500"><jstl:out value="${ error }"/></p>
			</jstl:if>
		</div>
		
		<form id="new-db-form" method="post" action="database?action=new_db"
			 class="z-10 absolute h-full w-full flex justify-center items-center" hidden=true>
			<div class="flex flex-col gap-5 bg-white p-5 w-165 rounded shadow-lg p-5">
				<div class="flex flex-col gap-[35px] px-5">
					<div class="border-b flex justify-between">
						<span class="font-semibold">Nouvelle base de donnée</span>
						<button type="button" class="btn-close"><%@include file="/WEB-INF/statics/svgs/close.svg" %></button>
					</div>
					<div class="flex flex-col items-center gap-2 w-full">
						<span>Nom de la base de donnée</span>
						<input name="name" type="text" placeholder="exemple" class="w-full border border-neutral-200 rounded-md p-1" required/>
					</div>
				</div>
				<button type="submit" class="submit-btn w-full bg-black hover:bg-slate-700 text-white font-bold py-1 px-20 rounded-[30px]">
					<span>Créer</span>
				</button>
			</div>
		</form>
		
		<form id="new-table-form" method="post" action="database?action=new_tb"
			class="z-10 absolute h-full w-full flex justify-center items-center" hidden=true>
			<div class="flex flex-col gap-5 bg-white p-5 w-165 rounded shadow-lg p-5">
				<div class="flex flex-col gap-[35px] px-5">
					<div class="border-b flex justify-between">
						<span class="font-semibold">Nouvelle table</span>
						<button type="button" class="btn-close" ><%@include file="/WEB-INF/statics/svgs/close.svg" %></button>
					</div>
					<div class="flex flex-col items-center gap-2 w-full">
						<span>Nom de la table</span>
						<input name= "tb-nom" type="text" class="w-full border border-neutral-200 rounded-md p-1" required/>
					</div>
					<div class="flex flex-col gap-2">
						<div class="flex items-center justify-between border-b-2 border-neutral-200">
							<span>Colones</span>
							<button id="add-colone-btn" type="button" class="hover:bg-stone-50 rounded-xl p-1">
								<%@include file="/WEB-INF/statics/svgs/add_circle.svg" %>
							</button>
						</div>
						<div id="colone-container" class="flex flex-col gap-2">
						</div>
					</div>
				</div>
				<button type="submit" class="submit-btn w-full bg-black hover:bg-slate-700 text-white font-bold py-1 px-20 rounded-[30px]">
					<span>Créer</span>
				</button>
			</div>
		</form>
	</body>
	<script type="text/javascript"><%@include file="/WEB-INF/scripts/app.js" %></script>
</html>
