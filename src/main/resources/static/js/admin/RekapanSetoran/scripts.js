document.addEventListener("DOMContentLoaded", function () {
    //Search
    document.getElementById('filterInput').addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            document.getElementById('filterForm').submit();
        }
    });
});
