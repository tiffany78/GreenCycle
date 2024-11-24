//menampilkan html tembah setoran
document.getElementById('').addEventListener('click', function() {
  window.location.href = '/admin/TSMember';  // Ganti dengan URL halaman yang sesuai
});

// Data sampah untuk contoh
const sampahDetails = {
  1: ['Plastik: 5 kg', 'Kertas: 2 kg', 'Botol: 10 pcs'],
  2: ['Kardus: 3 kg', 'Kaleng: 1 kg']
};

// Tunggu sampai DOM selesai dimuat
document.addEventListener("DOMContentLoaded", function () {
  // Tambahkan event listener ke semua tombol edit
  document.querySelectorAll('.edit-button').forEach(button => {
    button.addEventListener('click', function(e) {
      // Ambil ID data dari baris tabel
      const row = e.target.closest('tr'); // Ambil baris tabel terdekat dari tombol
      const id = row.children[0].textContent.trim(); // ID di kolom pertama

      // Ambil detail sampah berdasarkan ID
      const details = sampahDetails[id] || ['Data tidak tersedia'];

      // Menampilkan detail di bawah baris yang sesuai
      const detailRow = row.nextElementSibling; // Ambil baris detail setelah baris yang diklik
      const detailList = detailRow.querySelector('ul'); // Ambil list dari baris detail

      // Jika detail sudah muncul, sembunyikan
      if (detailRow.style.display === "table-row") {
        detailRow.style.display = "none";
      } else {
        // Tampilkan detail
        detailList.innerHTML = ''; // Bersihkan daftar sebelumnya
        details.forEach(item => {
          const li = document.createElement('li');
          li.textContent = item;
          detailList.appendChild(li);
        });
        detailRow.style.display = "table-row"; // Menampilkan baris detail
      }
    });
  });
});
