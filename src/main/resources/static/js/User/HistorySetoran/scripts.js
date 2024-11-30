document.addEventListener('DOMContentLoaded', () => {
    // Add event listener to each "Edit" button
    const editButtons = document.querySelectorAll('.edit-button');
  
    editButtons.forEach(button => {
      button.addEventListener('click', (event) => {
        const setoranId = button.getAttribute('data-id');  // Get setoran ID from button
        toggleSetoranDetails(event.target.closest('tr'), setoranId);  // Call the function to toggle details
      });
    });
    document.getElementById('filterInput').addEventListener('keydown', function (event) {
      if (event.key === 'Enter') {
          event.preventDefault();
          document.getElementById('filterForm').submit();
      }
  });
  });