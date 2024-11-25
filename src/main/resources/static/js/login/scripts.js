function showPopup(message) {
    const popup = document.getElementById("popup");
    const messageElement = document.getElementById("popup-message");
    messageElement.textContent = message;
    popup.style.display = "flex";
}

function closePopup() {
    const popup = document.getElementById("popup");
    popup.style.display = "none";
}

document.addEventListener("DOMContentLoaded", function () {
    const error = /*[[${error}]]*/ ''; // Thymeleaf mengisi value ini
    console.log("Error from backend:", error); // Debugging
    if (error.trim() !== '') {
        showPopup(error);
    }
});