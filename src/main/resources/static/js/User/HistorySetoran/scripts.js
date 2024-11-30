document.addEventListener('DOMContentLoaded', () => {
  // Event listener untuk filter input (pengiriman form dengan Enter)
  document.getElementById('filterInput').addEventListener('keydown', function (event) {
    if (event.key === 'Enter') {
      event.preventDefault();
      document.getElementById('filterForm').submit(); // Kirim form
    }
  });

  // Event listener untuk perubahan pada filter tanggal (tanggal awal dan akhir)
  document.querySelectorAll('input[type="date"]').forEach(dateInput => {
    dateInput.addEventListener('change', function () {
      document.getElementById('filterForm').submit(); // Kirim form ketika tanggal dipilih
    });
  });
});
