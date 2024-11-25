const express = require('express');
const { Pool } = require('pg');
const app = express();
const port = 3000;

// Setup connection pool untuk PostgreSQL
const pool = new Pool({
  user: 'PostgreSQL 17', // ganti dengan username PostgreSQL Anda
  host: 'localhost',
  database: 'TUBES', // ganti dengan nama database Anda
  password: 'postgres', // ganti dengan password PostgreSQL Anda
  port: 5432,
});

// Middleware untuk melayani file statis seperti HTML dan CSS
app.use(express.static('public'));

// Route untuk mengambil data dan mengirimkannya ke HTML
// Route untuk mengambil data dan mengirimkannya ke HTML
app.get('/STMember', async (req, res) => {
  try {
    // Koneksi ke PostgreSQL dan ambil data
    const result = await pool.query(`
      SELECT sm.id_transaksi, p.nama AS member, s.nama AS sampah, sm.kuantitas_sampah, sm.tgl_transaksi
      FROM SetoranMember sm
      JOIN Pengguna p ON sm.id_member = p.id
      JOIN Sampah s ON sm.id_sampah = s.id_sampah;
    `);

    // Kirim data ke halaman HTML
    res.render('setoran-member', { setoranMember: result.rows });
  } catch (err) {
    console.error('Error fetching data from database:', err);
    res.status(500).send('Internal Server Error');
  }
});


// Jalankan server di port
app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
