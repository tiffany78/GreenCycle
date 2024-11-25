document.addEventListener('DOMContentLoaded', () => {
    // Add event listener to each "Edit" button
    const editButtons = document.querySelectorAll('.edit-button');
  
    editButtons.forEach(button => {
      button.addEventListener('click', (event) => {
        const setoranId = event.target.closest('button').getAttribute('data-id');  // Get setoran ID from button
        toggleSetoranDetails(event.target.closest('tr'), setoranId);  // Call the function to toggle details
      });
    });
  });
  
  // Function to toggle setoran details
  function toggleSetoranDetails(row, setoranId) {
    // Check if the details are already shown for this row
    const existingDetailsRow = row.nextElementSibling;
    if (existingDetailsRow && existingDetailsRow.classList.contains('setoran-details')) {
      // If details are already shown, remove the row
      existingDetailsRow.remove();
      return;
    }
  
    // Fetch the setoran details from the server
    fetch(`/admin/SetoranMember/SetoranMember/details/${setoranId}`)
      .then(response => response.json())
      .then(data => {
        // Log the data to verify what is being returned
        console.log(data);
  
        // Create a new row for the details
        const detailsRow = document.createElement('tr');
        detailsRow.classList.add('setoran-details');  // Add a class for easy identification
  
        // Populate the details row with data
        const detailsCell = document.createElement('td');
        detailsCell.colSpan = 5;  // Span across all columns (ID, Member, Subtotal, Tanggal, and Detail)
        detailsCell.innerHTML = `
          <div class="setoran-details-content">
            <strong>Setoran Details:</strong>
            <ul>
              ${data.map(detail => `
                <li><strong>${detail.jenis_sampah}:</strong> ${detail.jumlah} ${detail.satuan}</li>
              `).join('')}
            </ul>
          </div>
        `;
        detailsRow.appendChild(detailsCell);
  
        // Insert the details row right after the clicked row
        row.parentNode.insertBefore(detailsRow, row.nextElementSibling);
      })
      .catch(error => {
        console.error('Error fetching setoran details:', error);
      });
  }

  