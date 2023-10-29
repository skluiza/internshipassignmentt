// Variables
let authToken = null;

// Function to perform an API call (simulated here)
async function performApiCall(url, method, headers, body = null) {
    try {
        const response = await fetch(url, {
            method,
            headers,
            body: body ? JSON.stringify(body) : null,
        });

        if (response.status === 401) {
            alert("Invalid Authorization");
        } else if (response.status === 500) {
            alert("Invalid Command");
        } else if (response.status === 200) {
            return response.json();
        } else {
            alert("API request failed with status " + response.status);
        }
    } catch (error) {
        alert("An error occurred while making the API request: " + error.message);
    }
}

// Function to show/hide screens
function showScreen(screenId, mode = null) {
    const screens = document.querySelectorAll(".screen");
    screens.forEach(screen => (screen.style.display = "none"));

    const screen = document.getElementById(screenId);
    screen.style.display = "block;

    if (screenId === "customerDetailsScreen" && mode === "create") {
        // Clear input fields in the customer details screen for creating a new customer.
        clearCustomerDetailsForm();
    }
}

// Function to clear customer details form
function clearCustomerDetailsForm() {
    document.getElementById("first_name").value = "";
    document.getElementById("last_name").value = "";
    document.getElementById("email").value = "";
    document.getElementById("street").value = "";
    document.getElementById("address").value = "";
    document.getElementById("city").value = "";
    document.getElementById("state").value = "";
    document.getElementById("phone").value = "";
}

// Login Screen
document.getElementById("loginButton").addEventListener("click", async function () {
    const loginId = document.getElementById("login_id").value;
    const password = document.getElementById("password").value;

    // Simulated authentication (replace with actual API call)
    const authResponse = await performApiCall(
        "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp",
        "POST",
        { "Content-Type": "application/json" },
        { login_id: loginId, password: password }
    );

    if (authResponse && authResponse.token) {
        authToken = authResponse.token;
        showScreen("customerListScreen");
    } else {
        alert("Invalid credentials. Please try again.");
    }
});

// Customer List Screen
document.getElementById("createCustomerButton").addEventListener("click", function () {
    showScreen("customerDetailsScreen", "create");
});

document.getElementById("logoutButton").addEventListener("click", function () {
    authToken = null;
    showScreen("loginScreen");
});

// Customer Details Screen
document.getElementById("backToListButton").addEventListener("click", function () {
    showScreen("customerListScreen");
});

document.getElementById("updateCustomerButton").addEventListener("click", async function () {
    // Handle customer update logic (API call or local functionality).
    // Example: Update customer details and transition back to the Customer List screen.
    // Replace this with actual API calls.
    alert("Customer updated successfully!");
    showScreen("customerListScreen");
});

document.getElementById("deleteCustomerButton").addEventListener("click", async function () {
    // Handle customer deletion logic (API call or local functionality).
    // Example: Delete customer and transition back to the Customer List screen.
    // Replace this with actual API calls.
    alert("Customer deleted successfully!");
    showScreen("customerListScreen");
});

// Initial setup
showScreen("loginScreen");