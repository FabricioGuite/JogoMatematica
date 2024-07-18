import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class RecordeDAO {
    private String arquivo;

    // Construtor da classe RecordeDAO
    public RecordeDAO(String arquivo) {
        this.arquivo = arquivo;
    }

    // Carrega o recorde a partir do arquivo
    public Recorde carregarRecorde() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String line = reader.readLine();
            reader.close();
            if (line != null) {
                // Se houver uma linha no arquivo, divide os dados pelo caractere ","
                String[] dados = line.split(",");
                String nick = dados[0];
                int quantidadeAcertos = Integer.parseInt(dados[1]);
                return new Recorde(nick, quantidadeAcertos); // Retorna um novo objeto Recorde com os dados
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Salva o recorde no arquivo
    public void salvarRecorde(Recorde recorde) {
        try {
            FileWriter writer = new FileWriter(arquivo);
            writer.write(recorde.getNick() + "," + recorde.getQuantidadeAcertos() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
