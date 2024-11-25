-- CREATE VIEW SetoranMemberView
CREATE VIEW setoran_member_view AS
SELECT 
    setoranmember.id_transaksi AS id,
    pengguna.id AS id_member,
    pengguna.nama AS nama,
    pengguna.username AS username,
    sampah.nama AS sampah,
    setoranmember.kuantitas_sampah AS kuantitas,
	sampah.unit AS unit,
    setoranmember.tgl_transaksi AS tanggal
FROM 
    setoranmember 
    JOIN sampah ON setoranmember.id_sampah = sampah.id_sampah 
    JOIN pengguna ON pengguna.id = setoranmember.id_member;

-- CREATE VIEW SetoranPusatView
CREATE VIEW setoran_pusat_view AS
SELECT 
    setoranpusat.id_transaksi AS id, 
    tpa.nama AS tpa,
    sampah.nama AS sampah, 
    setoranpusat.kuantitas_sampah AS kuantitas,
	sampah.unit AS unit,
    setoranpusat.tgl_transaksi AS tanggal
FROM 
    setoranpusat 
    JOIN tpa ON tpa.id_tpa = setoranpusat.id_tpa
    JOIN sampah ON sampah.id_sampah = setoranpusat.id_sampah;

-- CREATE VIEW STORAGE
CREATE VIEW storage_view AS
SELECT
	storage.id_sampah AS id_sampah,
	sampah.nama AS sampah,
	sampah.unit AS unit,
	storage.kapasitas AS kapasitas
FROM
	storage
	JOIN sampah ON sampah.id_sampah = storage.id_sampah;

-- CREATE LOGIN
CREATE VIEW login_view AS 
SELECT 
    pengguna.email AS email,
    pengguna.password AS password,
    pengguna.peran AS peran,
    pengguna.nama AS nama
FROM 
    pengguna