import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

class JogoMatematica extends Jogo {
    private String nick;
    
    
    private int numAcertos;    
    private RecordeDAO recordeDAO;
    private ArrayList<Integer> numeros;

    // Construtor da classe JogoMatematica
    public JogoMatematica(String nick) {
        super();
        this.nick = nick;
        numAcertos = 0;
        recordeDAO = new RecordeDAO("recorde.csv");
        numeros = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numeros.add(i);
        }

        frame = new JFrame("Jogo de Matemática");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1));
        frame.setLocationRelativeTo(null);

        // Criação dos componentes da interface
        labelPergunta = new JLabel();
        campoResposta = new JTextField();
        botaoResponder = new JButton("Responder");

        // Adiciona os componentes à janela do jogo
        frame.add(labelPergunta);
        frame.add(campoResposta);
        frame.add(botaoResponder);

        frame.setVisible(true);

        

        // Configura o listener para o botão de resposta
        botaoResponder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String respostaStr = campoResposta.getText();
                double resposta;
                try {
                    resposta = Double.parseDouble(respostaStr.replace(',', '.'));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Resposta inválida! Digite um número válido.");
                    return;
                }

                if (verificarResposta(resposta)) {
                    numAcertos++;
                    gerarPergunta();
                } else {
                    atualizarRecorde();
                    JOptionPane.showMessageDialog(null, "Resposta incorreta! Fim de jogo.");
                    frame.dispose();
                }
            }
        });
    }

    // Método para gerar uma pergunta aleatória
    protected void gerarPergunta() {
        Random rand = new Random();
        int num1 = numeros.get(rand.nextInt(numeros.size()));
        int num2 = numeros.get(rand.nextInt(numeros.size()));
        String operador = gerarOperador(rand);
        labelPergunta.setText(num1 + " " + operador + " " + num2 + " = ?");
    }

    // Método para gerar um operador aleatório
    private String gerarOperador(Random rand) {
        int operador = rand.nextInt(4);
        switch (operador) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            case 3:
                return "/";
            default:
                return "+";
        }
    }

    // Método para calcular o resultado de uma operação
    private double calcularResultado(int num1, int num2, String operador) {
        switch (operador) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return (double) num1 / num2;
            default:
                return num1 + num2;
        }
    }

    // Método para verificar se a resposta do usuário está correta
    private boolean verificarResposta(double resposta) {
        int num1 = Integer.parseInt(labelPergunta.getText().split(" ")[0]);
        int num2 = Integer.parseInt(labelPergunta.getText().split(" ")[2]);
        String operador = labelPergunta.getText().split(" ")[1];
        double resultado = calcularResultado(num1, num2, operador);
        return Math.abs(resposta - resultado) < 0.0001;
    }

    // Método para atualizar o recorde do jogador
    private void atualizarRecorde() {
        Recorde recordeAtual = recordeDAO.carregarRecorde();
        if (recordeAtual == null || numAcertos > recordeAtual.getQuantidadeAcertos()) {
            Recorde novoRecorde = new Recorde(nick, numAcertos);
            recordeDAO.salvarRecorde(novoRecorde);
        } else {
            Recorde recordeExistente = new Recorde(recordeAtual.getNick(), recordeAtual.getQuantidadeAcertos());
            recordeDAO.salvarRecorde(recordeExistente);
        }
    }
}
