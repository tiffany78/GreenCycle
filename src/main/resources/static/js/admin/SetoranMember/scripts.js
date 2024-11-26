document.addEventListener('DOMContentLoaded', () => {
  // Add event listener to each "Edit" button
  const editButtons = document.querySelectorAll('.edit-button');

  editButtons.forEach(button => {
      button.addEventListener('click', async (event) => {
          event.preventDefault(); // Prevent any default action
          
          // Get data-id attribute and split into setoranId and tanggal
          const dataId = button.getAttribute('data-id');
          const [setoranId, tanggal] = dataId.split("|");
          
          // Fetch details from server
          const url = `/admin/SetoranMember/details/${setoranId}/${tanggal}`;
          try {
              const response = await fetch(url);
              if (!response.ok) {
                  throw new Error(`HTTP error! Status: ${response.status}`);
              }
              
              const details = await response.json(); // Parse JSON response
              
              // Render the details into the table
              renderDetailsTable(details);
          } catch (error) {
              console.error('Error fetching details:', error);
          }
      });
  });

  // Submit form on Enter key press
  document.getElementById('filterInput').addEventListener('keydown', function (event) {
      if (event.key === 'Enter') {
          event.preventDefault();
          document.getElementById('filterForm').submit();
      }
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
    console.log(setoranId)
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

  