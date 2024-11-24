document.querySelectorAll('.valueSetoran').forEach(input => {
    input.addEventListener('input', function () {
        const inputValue = parseFloat(this.value);
        const kapasitasTd = this.parentElement.previousElementSibling;
        const kapasitasValue = parseFloat(kapasitasTd.textContent.trim());

        // Elemen pesan error
        const errorMessage = document.querySelector(".error-message");
        console.log(inputValue)
        console.log(kapasitasValue)

        if (isNaN(inputValue)) {
            errorMessage.classList.add("hidden");
        } else if (inputValue > kapasitasValue) {
            errorMessage.classList.remove("hidden");
        } else {
            errorMessage.classList.add("hidden");
        }
    });
});
