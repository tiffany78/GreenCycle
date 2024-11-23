document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".detail-button").forEach(button => {
        button.addEventListener("click", function () {
            const setoranId = button.getAttribute("data-id");
            const row = button.closest("tr");

            fetch(`/admin/SetoranTPA/details/${setoranId}`)
                .then(response => response.json())
                .then(data => {
                    const detailRows = row.parentNode.querySelectorAll(`.details-row[data-setoran-id="${setoranId}"]`);

                    //Tutup detail
                    if (detailRows.length > 0) {
                        detailRows.forEach(detailRow => {
                            detailRow.remove();
                        });
                    } 
                    //Buka detail
                    else {
                        const detailsTable = document.querySelector(".details-table");
                        const detailsBody = document.getElementById("details-body");

                        detailsBody.innerHTML = "";

                        data.forEach(sampahDetail => {
                            const detailRow = document.createElement("tr");
                            detailRow.classList.add("details-row");
                            detailRow.setAttribute("data-setoran-id", setoranId);  

                            const detailCell = document.createElement("td");
                            detailCell.colSpan = row.children.length;  

                            detailCell.innerHTML = `
                                <div class="detail-content">
                                    <p>${sampahDetail.jenis_sampah || 'N/A'} ${sampahDetail.jumlah || 'N/A'} ${sampahDetail.satuan || 'N/A'}</p>
                                </div>
                            `;

                            detailRow.appendChild(detailCell);
                            row.parentNode.insertBefore(detailRow, row.nextSibling);
                        });

                        detailsTable.style.display = "table";
                    }
                })
                
        });
    });
});
