import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LoginCadastro extends JFrame {
    private JTextField campoNick;
    private JPasswordField campoSenha;
    private JButton botaoLogin;
    private JButton botaoCadastro;
    private UsuarioDAO usuarioDAO;

    // Construtor da classe LoginCadastro
    public LoginCadastro() {
        super("Login e Cadastro de Usuário");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);

        // Criação dos componentes da interface
        JLabel labelNick = new JLabel("Nick:");
        campoNick = new JTextField();
        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();
        botaoLogin = new JButton("Login");
        botaoCadastro = new JButton("Cadastro");

        // Adiciona os componentes à janela de login/cadastro
        add(labelNick);
        add(campoNick);
        add(labelSenha);
        add(campoSenha);
        add(botaoLogin);
        add(botaoCadastro);

        // Criação e inicialização do objeto UsuarioDAO
        usuarioDAO = new UsuarioDAO("usuarios.csv");

        // Configura o listener para o botão de login
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nick = campoNick.getText();
                String senha = new String(campoSenha.getPassword());
        
                // Verifica as credenciais do usuário
                if (usuarioDAO.verificarCredenciais(nick, senha)) {
                    JOptionPane.showMessageDialog(null, "Entrou!");
        
                    // Cria uma instância do JogoMatematica e inicia o jogo
                    Jogo jogo = new JogoMatematica(nick); // Passa o nick como argumento
                    jogo.gerarPergunta();
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciais inválidas!");
                }
            }
        });

        // Configura o listener para o botão de cadastro
        botaoCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = JOptionPane.showInputDialog(null, "Digite seu nome:");
                String nick = JOptionPane.showInputDialog(null, "Digite um nick:");
                String senha = JOptionPane.showInputDialog(null, "Digite uma senha:");

                // Verifica se o nick já existe no sistema
                if (usuarioDAO.verificarNickExistente(nick)) {
                    JOptionPane.showMessageDialog(null, "Nick já existe! Escolha outro nick.");
                } else {
                    // Cria um novo usuário e o adiciona ao arquivo de usuários
                    Usuario novoUsuario = new Usuario(nome, nick, senha);
                    usuarioDAO.adicionarUsuario(novoUsuario);
                    usuarioDAO.salvarUsuarios();
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                }
            }
        });
    }

    // Método main para executar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginCadastro loginCadastro = new LoginCadastro();
                loginCadastro.setVisible(true);
            }
        });
    }
}
