/**
 *  Creation de table
 */
const newDatabaseDialog = document.getElementById("new-db-dialog");
const newDatabaseButton = document.getElementById("new-db-btn");
const closeDialogBtn = newDatabaseDialog.getElementsByClassName("btn-close")[0];

newDatabaseButton.addEventListener("click", () => {
	newDatabaseDialog.hidden = false;
	
	const handleCloseDialog = closeDialogBtn.addEventListener("click", () => {
		newDatabaseDialog.hidden = true;
		
		closeDialogBtn.removeEventListener("click", handleCloseDialog);
	});
});
