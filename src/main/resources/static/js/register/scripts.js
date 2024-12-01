document.getElementById("kecamatanList").addEventListener("change", function () {
    const kecamatanId = this.value;
    const kelurahanSelect = document.getElementById("kelurahanList");

    // Kosongkan dropdown Kelurahan
    kelurahanSelect.innerHTML = '<option value="" disabled selected>Pilih Kelurahan</option>';

    if (kecamatanId) {
        fetch(`/register/kelurahan/${kecamatanId}`)
            .then(response => response.json())
            .then(data => {
                data.forEach(kelurahan => {
                    const option = document.createElement("option");
                    option.value = kelurahan.id_kelurahan;
                    option.textContent = kelurahan.nama;
                    kelurahanSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error:', error));
    }
});