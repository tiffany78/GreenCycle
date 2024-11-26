document.querySelectorAll('.valueSetoran').forEach(input => {
    input.addEventListener('input', function () {
        const inputValue = parseFloat(this.value);
        const kapasitasTd = this.parentElement.previousElementSibling; // Kolom kapasitas
        const kapasitasValue = parseFloat(kapasitasTd.textContent.trim());

        // Elemen pesan error dalam baris yang sama
        const errorMessage = this.parentElement.nextElementSibling.querySelector(".error-message");

        if (isNaN(inputValue)) {
            errorMessage.classList.add("hidden"); // Sembunyikan error jika input kosong atau bukan angka
        } else if (inputValue > kapasitasValue || inputValue < 0) {
            errorMessage.classList.remove("hidden"); // Tampilkan error jika input melebihi kapasitas
        } else {
            errorMessage.classList.add("hidden"); // Sembunyikan error jika input valid
        }
    });
});
