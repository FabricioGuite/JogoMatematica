import javax.swing.*;


abstract class Jogo {

    protected JFrame frame;
    protected JLabel labelPergunta;
    protected JTextField campoResposta;
    protected JButton botaoResponder;

    protected abstract void gerarPergunta();


}
