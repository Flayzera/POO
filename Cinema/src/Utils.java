import java.io.*;

public class Utils {
    public static int obterProximoId(String caminhoArquivo) throws IOException {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return 1; 
        }

        int maiorId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                int idAtual = Integer.parseInt(dados[0]);
                if (idAtual > maiorId) {
                    maiorId = idAtual;
                }
            }
        }

        return maiorId + 1; // Retorna o maior ID encontrado + 1
    }
}