 	/**
 *  Creation de table
 */
const newDatabaseButton = document.getElementById("new-db-btn");
newDatabaseButton.addEventListener("click", () => {
	const newDatabaseDialog = document.getElementById("new-db-form");
	const closeDialogBtn = newDatabaseDialog.getElementsByClassName("btn-close")[0];

	newDatabaseDialog.hidden = false;
	
	const handleCloseDialog = closeDialogBtn.addEventListener("click", () => {
		newDatabaseDialog.hidden = true;
		
		closeDialogBtn.removeEventListener("click", handleCloseDialog);
	});
});

/**
 *  Show tables and show newtable form
 */
const databases = document.getElementsByClassName("db");
const newDBTableDialog = document.getElementById("new-table-form");
for(let i = 0; i < databases.length; i++) {
	const database = databases[i];
	const tablesContainer = database.getElementsByClassName("db-table-container")[0];
	
	// affichage de la liste des tables
	const showDBTablesBtn = database.getElementsByClassName("show-db-table-btn")[0];
	showDBTablesBtn.addEventListener("click", () => {
		console.log(database.id);
		tablesContainer.hidden = !tablesContainer.hidden;
	});
	
	// affichage du dialog new table
	const newDBTableBtns = database.getElementsByClassName("new-db-table-btn")[0];	
	newDBTableBtns.addEventListener("click", () => {
			// ajout d'un champ referent le nom de la base de donnée qui vas contenir la table
			const dbNameReferencer = document.createElement("input");
			dbNameReferencer.type = 'text';
			dbNameReferencer.name = "db-nom";
			dbNameReferencer.setAttribute('value', database.id); 
			dbNameReferencer.hidden = true;
			newDBTableDialog.appendChild(dbNameReferencer); //
			
			newDBTableDialog.hidden = false;
			
			// fermeture du formulaure
			const closeDialogBtn = newDBTableDialog.getElementsByClassName("btn-close")[0];
			const handleCloseDialog = closeDialogBtn.addEventListener("click", () => {
				newDBTableDialog.hidden = true;
				/*newDBTableDialog.removeChild(dbNameInput);*/
				closeDialogBtn.removeEventListener("click", handleCloseDialog);
			});
	});
}

/*
 * New DB table colone manager 
 */
const newTableColoneContainer = document.getElementById("colone-container");
const addTableColoneBtn = document.getElementById("add-colone-btn");
addTableColoneBtn.addEventListener("click", () => {
    const newColone = `
        <div class="db-colone flex px-5 py-3 bg-[#F5F5F5]">
            <div>
                <label>Nom :</label>
                <input name="col-nom" class="border-b border-neutral-200" type="text" value="Colone1" required />
            </div><div>
                <label>Type de donn&eacutee :</label>
                <select name="col-type" class="border-b border-neutral-200" required>
                    <option value="INTEGER">INT</option>
                    <option value="VARCHAR(255)">VARCHAR(255)</option>
                </select>
            </div>
        </div>
	`;
    newTableColoneContainer.insertAdjacentHTML("beforeend", newColone);
});

/*
 * New DB table line
 */
/*const dbTableContainers = document.getElementsByClassName("db-table-container");
for(let i = 0; i < dbTableContainers.length; i++) {
	const tableContainer = dbTableContainers[i];
	const newTableLineBtns = tableContainer.getElementsByClassName("new-tb-line-btn");
	console.log(newTableLineBtns)
	
	for(let j=0; j < newTableLineBtns.length; j++) {
		const newTableLineBtn = newTableLineBtns[j];
		newTableLineBtn.addEventListener("click", () => {
				const newTableLigneForm = document.getElementById("add-table-line");
				
				newTableLigneForm.hidden = false;
				
				const closeFormBtn = newTableLigneForm.getElementsByClassName("btn-close")[0];
				const handleCloseForm = closeFormBtn.addEventListener("click", () => {
					newTableLigneForm.hidden = true;
					closeFormBtn.removeEventListener("click", handleCloseForm);
				});
		})
	}
}*/





