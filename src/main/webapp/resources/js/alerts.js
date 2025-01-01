function showAlert(message, type) {
    // Create alert container if it doesn't exist
    let alertContainer = document.getElementById("alert-container");
    if (!alertContainer) {
        alertContainer = document.createElement("div");
        alertContainer.id = "alert-container";
        document.body.appendChild(alertContainer);
    }

    // Create alert box
    const alertBox = document.createElement("div");
    alertBox.className = `alert ${type}`;
    alertBox.innerText = message;

    // Add animation classes
    alertBox.classList.add("show");

    // Append alert box to container
    alertContainer.appendChild(alertBox);

    // Remove alert after 3 seconds
    setTimeout(() => {
        alertBox.classList.add("hide");
        alertBox.addEventListener("animationend", () => alertBox.remove());
    }, 3000);
}
