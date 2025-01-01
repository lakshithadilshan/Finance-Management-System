        function toggleTermDropdown() {
          const accountType = document.getElementById("account").value;
                  const termContainer = document.getElementById("term-container");
                  const termContainerLoan = document.getElementById("term-container_loan");

                  termContainer.style.display = "none";
                  termContainerLoan.style.display = "none";

                  if (accountType === "fd_account") {
                      termContainer.style.display = "block";
                  } else if (accountType === "loan_account") {
                      termContainerLoan.style.display = "block";
                  }
              }
