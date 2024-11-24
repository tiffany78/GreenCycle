document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("form-edit-harga");
    const inputs = form.querySelectorAll("input[type='text']");
    const submitButton = form.querySelector("button.simpan");

    // Fungsi untuk memeriksa apakah semua input telah terisi
    function validateInputs() {
        let allFilled = true;
        inputs.forEach(input => {
            if (!input.value.trim()) {
                allFilled = false;
            }
        });
        submitButton.disabled = !allFilled; // Aktifkan atau nonaktifkan tombol
    }

    // Pasang event listener pada setiap input
    inputs.forEach(input => {
        input.addEventListener("input", validateInputs);
    });
});
