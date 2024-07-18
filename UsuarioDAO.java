import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class UsuarioDAO {
    private ArrayList<Usuario> usuarios;
    private String arquivo;

    // Construtor da classe UsuarioDAO
    public UsuarioDAO(String arquivo) {
        this.arquivo = arquivo;
        usuarios = new ArrayList<>();
        carregarUsuarios();
    }

    // Adiciona um usuário à lista de usuários
    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    // Salva a lista de usuários no arquivo
    public void salvarUsuarios() {
        try {
            FileWriter writer = new FileWriter(arquivo);
            for (Usuario usuario : usuarios) {
                // Escreve os dados do usuário no arquivo, separados por vírgula
                writer.write(usuario.getNome() + "," + usuario.getNick() + "," + usuario.getSenha() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carrega os usuários a partir do arquivo
    private void carregarUsuarios() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String line;
            while ((line = reader.readLine()) != null) {
                // Divide a linha em dados separados por vírgula
                String[] dados = line.split(",");
                String nome = dados[0];
                String nick = dados[1];
                String senha = dados[2];
                // Cria um novo objeto Usuario com os dados e o adiciona à lista de usuários
                Usuario usuario = new Usuario(nome, nick, senha);
                adicionarUsuario(usuario);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Verifica as credenciais de um usuário
    public boolean verificarCredenciais(String nick, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNick().equals(nick) && usuario.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

    // Verifica se um nick de usuário já existe
    public boolean verificarNickExistente(String nick) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }
}
