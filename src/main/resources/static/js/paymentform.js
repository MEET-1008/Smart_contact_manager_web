document.addEventListener("DOMContentLoaded", function () {
    const popupForm = document.getElementById("popupForm");
    const openButtons = document.querySelectorAll(".open-btn");
    const closeBtn = document.querySelector(".close");
    const priceInput = document.getElementById("amount"); // Price input field
    let selectedPrice = 0; // Variable to store the selected price

    // Open popup form when any button is clicked
    openButtons.forEach(button => {
        button.addEventListener("click", function () {
            selectedPrice = this.getAttribute("data-price"); // Get the price from data attribute
            priceInput.value = `${selectedPrice}`; // Set the value of the price input field
            popupForm.style.display = "block"; // Show the popup
        });
    });

    // Close popup form when 'x' is clicked
    closeBtn.addEventListener("click", function () {
        popupForm.style.display = "none"; // Hide the popup
    });

    // Close popup when clicking outside of the form
    window.addEventListener("click", function (event) {
        if (event.target === popupForm) {
            popupForm.style.display = "none"; // Hide the popup
        }
    });

    // Handle form submission
    document.getElementById("paymentForm").addEventListener("submit", function (event) {
        event.preventDefault(); // Prevent actual form submission
        const name = document.getElementById("name").value;
        const email = document.getElementById("email").value;
        const mobile = document.getElementById("mobile").value;

        // Display form details in console along with the price
        console.log(`Name: ${name}`);
        console.log(`email: ${email}`);
        console.log(`mo number: ${mobile}`);
        console.log(`amount: $${selectedPrice}`); // Log the selected price

        // Close the popup after submission
        popupForm.style.display = "none";
    });
});
