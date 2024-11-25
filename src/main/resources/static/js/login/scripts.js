function showPopup(message) {
    const popup = document.getElementById("popup");
    const messageElement = document.getElementById("popup-message");
    messageElement.textContent = message; // Set error message
    popup.style.display = "flex"; // Show pop-up
}

function closePopup() {
    const popup = document.getElementById("popup");
    popup.style.display = "none"; // Hide pop-up
}

// Cek apakah ada pesan error dari backend
document.addEventListener("DOMContentLoaded", function () {
    const error = /*[[${error != null} ? '${error}' : '']]*/ '';
    if (error) {
        showPopup(error); // Panggil fungsi pop-up
    }
});