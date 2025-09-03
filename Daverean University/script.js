// "Database" using array (stored in localStorage)
let users = JSON.parse(localStorage.getItem("users")) || [];

// Registration
const regForm = document.getElementById("registrationForm");
if (regForm) {
  regForm.addEventListener("submit", function(e) {
    e.preventDefault();

    const fullName = document.getElementById("fullName").value.trim();
    const birthday = document.getElementById("birthday").value;
    const address = document.getElementById("address").value.trim();
    const school = document.getElementById("school").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    // check if email already exists
    if (users.some(u => u.email === email)) {
      alert("This email is already registered.");
      return;
    }

    // store new user
    const newUser = { fullName, birthday, address, school, email, password };
    users.push(newUser);
    localStorage.setItem("users", JSON.stringify(users));

    alert("Registration successful! You can now log in.");
    regForm.reset();
    window.location.href = "login.html";
  });
}

// Login
const loginForm = document.getElementById("loginForm");
if (loginForm) {
  loginForm.addEventListener("submit", function(e) {
    e.preventDefault();

    const loginEmail = document.getElementById("loginEmail").value.trim();
    const loginPassword = document.getElementById("loginPassword").value;

    const user = users.find(
      u => u.email === loginEmail && u.password === loginPassword
    );

    if (user) {
      alert(`Welcome, ${user.fullName}! You are now logged in.`);
      localStorage.setItem("currentUser", JSON.stringify(user)); // ✅ save session
      window.location.href = "index.html"; // go to profile page
    } else {
      alert("Invalid Email or password. Please try again.");
    }
  });
}

