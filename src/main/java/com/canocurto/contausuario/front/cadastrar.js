class CadastrarUsuario {
    constructor(nomeUsuario, email, senha) {
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
    }
    
}

document.querySelector("#form").addEventListener("submit", async function(e) {
    e.preventDefault();
   
    const nomeUsuario = document.querySelector("input[name='nomeUsuario']").value;
    const email = document.querySelector("input[name='email']").value;
    const senha = document.querySelector("input[name='senha']").value;
    
    const cadastrarUsuario = new CadastrarUsuario(nomeUsuario, email, senha);
    
    const resp = await fetch("http://localhost:8080/contausuario/cadastrar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(cadastrarUsuario)
    });

    const dados = await resp.json();

    if (!resp.ok) {
        console.log(dados);
        return;
    }


    window.location.href = "login.html";

    
});
    
    
