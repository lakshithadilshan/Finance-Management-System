function validateForm() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const confirmPassword = document.getElementById("confirmpassword").value.trim();
    const email = document.getElementById("email").value.trim();

    // Regular Expressions for validation
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

    // Username validation
    if (username === "") {
        alert("Username is required.");
        return false;
    }

    // Password validation
    if (password === "") {
        alert("Password is required.");
        return false;
    }

    if (!passwordPattern.test(password)) {
        alert("Password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character.");
        return false;
    }

    // Confirm password validation
    if (confirmPassword === "") {
        alert("Please confirm your password.");
        return false;
    }

    if (password !== confirmPassword) {
        alert("Passwords do not match. Please re-enter.");
        return false;
    }

    // Email validation
    if (email === "") {
        alert("Email is required.");
        return false;
    }

    if (!emailPattern.test(email)) {
        alert("Please enter a valid email address.");
        return false;
    }

    return true; // Allow form submission
}
