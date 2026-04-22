class LogarUsuario {
    constructor(email, senha) {
        this.email = email;
        this.senha = senha;
    }
}

document.querySelector("#form").addEventListener("submit", function(e) {
    e.preventDefault();

    const email = document.querySelector("input[name='email']").value;
    const senha = document.querySelector("input[name='senha']").value;

    const logarUsuario = new LogarUsuario(email, senha);

    fetch("http://localhost:8080/contausuario/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(logarUsuario)
    }).then(resp => {
        console.log(resp.status);
        return resp.json();  
    }).then(data => {
        console.log(data);
    })
    .catch(erro => {
        console.log(erro);
    })
})