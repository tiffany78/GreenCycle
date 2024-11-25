-- CREATE TABLE
CREATE TABLE Kecamatan (
    id_kecamatan SERIAL PRIMARY KEY,
    nama VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE Kelurahan (
    id_kelurahan SERIAL PRIMARY KEY,
    nama VARCHAR(50) UNIQUE NOT NULL,
    id_kecamatan INT NOT NULL,
    FOREIGN KEY (id_kecamatan) REFERENCES Kecamatan (id_kecamatan)
);

CREATE TABLE Sampah (
    id_sampah SERIAL PRIMARY KEY,
    nama VARCHAR(50) UNIQUE NOT NULL,
    unit VARCHAR(10) NOT NULL,
    harga DECIMAL(10, 2) NOT NULL,
    tanggal_perubahan DATE NOT NULL
);

CREATE TABLE Storage(
	id_sampah INT,
	kapasitas INT NOT NULL,
	FOREIGN KEY (id_sampah) REFERENCES Sampah (id_sampah)
);

CREATE TABLE Pengguna (
    id SERIAL PRIMARY KEY,
    nama VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    peran VARCHAR(10) NOT NULL,
    no_telp VARCHAR(12) UNIQUE NOT NULL,
    alamat VARCHAR(50) NOT NULL,
    id_kelurahan INT,
    FOREIGN KEY (id_kelurahan) REFERENCES Kelurahan (id_kelurahan)
);

CREATE TABLE TPA (
    id_TPA SERIAL PRIMARY KEY,
    nama VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE SetoranMember (
    id_transaksi SERIAL PRIMARY KEY,
    id_member INT,
    id_sampah INT,
    kuantitas_sampah INT NOT NULL,
    tgl_transaksi DATE NOT NULL,
    FOREIGN KEY (id_member) REFERENCES Pengguna (id),
    FOREIGN KEY (id_sampah) REFERENCES Sampah (id_sampah)
);

CREATE TABLE SetoranPusat (
    id_transaksi SERIAL PRIMARY KEY,
    id_TPA INT,
    id_sampah INT,
    kuantitas_sampah INT NOT NULL,
    tgl_transaksi DATE NOT NULL,
    FOREIGN KEY (id_TPA) REFERENCES TPA (id_TPA),
    FOREIGN KEY (id_sampah) REFERENCES Sampah (id_sampah)
);

-- INSERT DATA
INSERT INTO Kecamatan (nama)
VALUES
    ('Andir'),
    ('Astana Anyar'),
    ('Antapani'),
    ('Arcamanik'),
    ('Babakan Ciparay'),
    ('Bandung Kidul'),
    ('Bandung Kulon'),
    ('Bandung Wetan'),
    ('Batununggal'),
    ('Bojongloa Kaler');

INSERT INTO Kelurahan (id_kecamatan, nama)
VALUES
    (1, 'Campaka'),
    (1, 'Ciroyom'),
    (1, 'Garuda'),
    (2, 'Cibadak'),
    (2, 'Karanganyar'),
    (2, 'Karasak'),
    (3, 'Antapani Kidul'),
    (3, 'Antapani Kulon'),
    (3, 'Antapani Tengah'),
    (4, 'Cisaranten Endah'),
    (4, 'Cisaranten Kulon'),
    (4, 'Sukamiskin'),
    (5, 'Babakan'),
    (5, 'Babakan Ciparay'),
    (5, 'Cirangrang'),
    (6, 'Batununggal'),
    (6, 'Kujangsari'),
    (6, 'Mengger'),
    (7, 'Caringin'),
    (7, 'Cibuntu'),
    (7, 'Cijerah'),
    (8, 'Cihapit'),
    (8, 'Citarum'),
    (8, 'Tamansari'),
    (9, 'Binong'),
    (9, 'Cibangkong'),
    (9, 'Gumuruh'),
    (10, 'Babakan Asih'),
    (10, 'Babakan Tarogog'),
    (10, 'Kopo');

INSERT INTO Sampah (nama, unit, harga, tanggal_perubahan)
VALUES
    ('Botol plastik', 'pc', 500, '2024-05-24'),
    ('Kertas', 'kg', 1000, '2024-05-24'),
    ('Botol kaca', 'pc', 1000, '2024-05-24'),
    ('Pakaian', 'pc', 2500, '2024-05-24'),
    ('Kardus', 'kg', 1250, '2024-05-24');

INSERT INTO Storage (id_sampah, kapasitas)
VALUES
	(1, 5),
	(2, 7),
	(3, 5),
	(4, 1),
	(5, 2);

INSERT INTO Pengguna (nama, email, username, password, peran, no_telp, alamat, id_kelurahan)
VALUES
    ('Dodo', 'dodo@gmail.com', 'dodoadmin', 'admindodo', 'admin', '081378522999', 'Jl. Dodo Park no. 12', 1),
    ('Wombat', 'wombat@gmail.com', 'wombatadmin', 'adminwombat', 'admin', '081203212218', 'Jl. Wombat Jungle no. 1', 2),
    ('Cecep Priyatno', 'cecep@gmail.com', 'cecep', 'cecep123', 'member', '082201233142', 'Jl. Cibadak no. 1', 4),
    ('Putri Afifah', 'afifahputri@gmail.com', 'afifahputri', 'afput31', 'member', '081278229031', 'Jl. Antapani no. 25', 7),
    ('Ros Purnawati', 'roswati@gmail.com', 'ros', 'rospurna80', 'member', '087531202284', 'Jl. Arcamanik Endah no. 2', 10),
    ('Bambang Kusumo', 'bambangkusumo@gmail.com', 'bambangk', 'bambangkus03', 'member', '089272011320', 'Jl. Babakan Ciparay no. 200', 14);

INSERT INTO TPA (nama)
VALUES    
    ('Bersih Sejahtera'),
    ('EcoHaven'),
    ('Highland Recycling'),
    ('Bumi Asri'),
    ('Cahaya Timur');

INSERT INTO SetoranMember (id_member, id_sampah, kuantitas_sampah, tgl_transaksi)
VALUES
	(3, 1, 5, '2024-05-27'),
	(3, 2, 2, '2024-05-27'),
	(3, 3, 5, '2024-05-27'),
	(4, 4, 1, '2024-05-26'),
	(4, 2, 5, '2024-05-26'),
	(5, 5, 2, '2024-05-26');

INSERT INTO SetoranPusat (id_TPA, id_sampah, kuantitas_sampah, tgl_transaksi)
VALUES
	(1, 1, 5, '2024-05-29'),
	(2, 2, 5, '2024-05-29'),
	(3, 2, 2, '2024-05-29'),
	(4, 5, 2, '2024-05-29'),
	(5, 3, 5, '2024-05-29');