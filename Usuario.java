class Usuario {
    private String nome;
    private String nick;
    private String senha;

    public Usuario(String nome, String nick, String senha) {
        this.nome = nome;
        this.nick = nick;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getNick() {
        return nick;
    }

    public String getSenha() {
        return senha;
    }
}