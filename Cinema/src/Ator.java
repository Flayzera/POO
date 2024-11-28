import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Ator extends Pessoa {
  private int registro;

  public Ator(String cpf, String nome, String email, int registro) {
    super(cpf, nome, email);
    this.registro = registro;
  }

  public int getRegistro() {
    return registro;
  }

  public void setRegistro(int registro) {
    this.registro = registro;
  }

  public static boolean cadastrarAtor(Ator ator) throws IOException {
    boolean retorno = true;

    BufferedWriter bw = new BufferedWriter(new FileWriter("ator.txt", true));

    bw.write(ator.getCpf() + ";" + ator.getNome() + ";" + ator.getEmail() + ";"+ ator.getRegistro());
    bw.newLine();
    bw.close();

    return retorno;
  }

  public static boolean editarNomeAtor(int registro, String novoNome) throws IOException {
    boolean encontrado = false;

    ArrayList<Ator> atores = listarAtores();

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("ator.txt"))) {
        for (Ator a : atores) {
            if (a.getRegistro() == registro) {
                a.setNome(novoNome);
                encontrado = true;
            }
            bw.write(a.getCpf() + ";" + a.getNome() + ";" + a.getEmail() + ";" + a.getRegistro());
            bw.newLine();
        }
    }

    if (!encontrado) {
        System.out.println("Ator não encontrado.");
    }

    return encontrado;
  }

  public static ArrayList<Ator> listarAtores() throws IOException {
    ArrayList<Ator> atores = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader("ator.txt"))) {
      String linha;
      while ((linha = br.readLine()) != null) {
        String[] dados = linha.split(";");
        String cpfAtor = dados[0];
        String nomeAtor = dados[1];
        String emailAtor = dados[2];
        int registroAtor = Integer.parseInt(dados[3]);
        
        Ator ator = new Ator(cpfAtor, nomeAtor, emailAtor, registroAtor);
        atores.add(ator);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return atores;
  }

  public static Ator consultarAtor(int registroAtor) throws IOException {
    Ator ator = null;
    try (BufferedReader br = new BufferedReader(new FileReader("ator.txt"))) {
      String linha;
      while ((linha = br.readLine()) != null) {
        String[] dados = linha.split(";");
        if (dados[3].equals(String.valueOf(registroAtor))) {
          String cpfAtor = dados[0];
          String nomeAtor = dados[1];
          String emailAtor = dados[2];
          int registro = Integer.parseInt(dados[3]);
          ator = new Ator(cpfAtor, nomeAtor, emailAtor, registro);
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ator;
  }

  public  String toString() {
    return "Registro: " + registro + "CPF: " + this.getCpf() + "\nNome: " + this.getNome() + "\nEmail: "  + this.getEmail() + "\nRegistro: " + this.getRegistro();
  }
}
