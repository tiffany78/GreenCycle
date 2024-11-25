document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("form-tambah-sampah");
    const inputs = form.querySelectorAll("input[type='text']");
    const submitButton = form.querySelector("button.simpan");

    // Validasi input
    function validateInputs() {
        let allFilled = true;
        inputs.forEach(input => {
            if (!input.value.trim()) {
                allFilled = false;
            }
        });
        submitButton.disabled = !allFilled;
    }

    // Event listener untuk validasi
    inputs.forEach(input => {
        input.addEventListener("input", validateInputs);
    });

    // Pastikan submit tidak dicegah
    form.addEventListener("submit", function (event) {
        if (!submitButton.disabled) {
            console.log("Form submitted!"); // Debugging
        }
    });
});
