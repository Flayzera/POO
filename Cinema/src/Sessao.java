import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Sessao {
  private int idSessao;
  private int dataHoraSessao;
  private Filme filme;
  private Sala sala;
  private Funcionario funcionario;
  private String status;

  public Sessao() throws IOException {
    this.idSessao = Utils.obterProximoId("sessao.txt");
  }

  public Sessao( int dataHoraSessao, Sala sala, String status) throws IOException {
    this.idSessao = Utils.obterProximoId("sessao.txt");
    this.dataHoraSessao = dataHoraSessao;
    this.sala = sala;
    this.status = status;
  }

  public Sessao(int idSessao, int dataHoraSessao, Sala sala, String status) throws IOException {
    this.idSessao = idSessao;
    this.dataHoraSessao = dataHoraSessao;
    this.sala = sala;
    this.status = status;
  }

  public int getIdSessao() {
    return idSessao;
  }

  public void setIdSessao(int idSessao) {
    this.idSessao = idSessao;
  }

  public int getDataHoraSessao() {
    return dataHoraSessao;
  }

  public void setDataHoraSessao(int dataHoraSessao) {
    this.dataHoraSessao = dataHoraSessao;
  }

  public Filme getFilme() {
    return filme;
  }

  public void setFilme(Filme filme) {
    this.filme = filme;
  }

  public Sala getSala() {
    return sala;
  }

  public void setSala(Sala sala) {
    this.sala = sala;
  }

  public Funcionario getFuncionario() {
    return funcionario;
  }

  public void setFuncionario(Funcionario funcionario) {
    this.funcionario = funcionario;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Sessao instanciarSessao(ArrayList<Sala> salas, Scanner sc) throws IOException {
    System.out.print("Digite a capacidade da sala: ");
    int dataHoraSessao = sc.nextInt();
    sc.nextLine();

    System.out.print("Digite o ID da Sala: ");
    int idSala = sc.nextInt();
    sc.nextLine();

    Sala salaEncontrada = null;
    for (Sala sala : salas){
      if (sala.getIdSala() == idSala){
          salaEncontrada = sala;
          break;
      }
    }

    if(salaEncontrada == null){
      System.out.println("Sala não encontrada. Deseja criar um nova sala? (S/N)");
      String opcao = sc.nextLine();
      if (opcao.equalsIgnoreCase("S")) {
          salaEncontrada = new Sala().instanciarSala(sc);
      } else {
          System.out.println("Operação cancelada.");
          return null;
      }
    }

    System.out.print("Digite o status da sala: ");
    String statusSala = sc.nextLine();

    this.dataHoraSessao = dataHoraSessao;
    this.sala = salaEncontrada;
    this.status = statusSala;

    return this;
  }

  public static boolean cadastrarSessao(Sessao sessao) throws IOException {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("sessao.txt", true))) {
        bw.write(sessao.getIdSessao() + ";" + sessao.getDataHoraSessao() + ";" +
                sessao.getSala().getIdSala() + ";" + sessao.getStatus());
        bw.newLine();
        return true;
    } catch (IOException e) {
        System.out.println("Erro ao salvar a sessão: " + e.getMessage());
        return false;
    }
  }

  public static boolean editarSessao(int idSessao, String novoStatus) throws IOException {
    boolean encontrado = false;

    ArrayList<Sessao> sessoes = listarSessoes();

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("sessao.txt"))) {
      for (Sessao s : sessoes) {
        if (s.getIdSessao() == idSessao) {
          s.setStatus(novoStatus);
          encontrado = true;
        }
        bw.write(s.getIdSessao() + ";" + s.getDataHoraSessao() + ";" + s.getFilme() + ";" + s.getSala() + ";" + s.getFuncionario() + ";" + s.getStatus());
        bw.newLine();
      }
    }

    if (!encontrado) {
      System.out.println("Sessão não encontrada.");
    }

    return encontrado;
  }

  public static ArrayList<Sessao> listarSessoes() throws IOException {
    ArrayList<Sessao> sessoes = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader("sessao.txt"))) {
      String linha;
      Sala sala = null;
      int dataHoraSessao = 0;
      String status = null;
      while ((linha = br.readLine()) != null) {
        String[] dados = linha.split(";");
        for (Sala s : Sala.listarSalas()) {
          if (s.getIdSala() == Integer.parseInt(dados[2])) {
            sala = s;
            dataHoraSessao = Integer.parseInt(dados[1]);
            status = dados[3];
            break;
          }
        }
        if (sala != null) {
            Sessao sessao = new Sessao(Integer.parseInt(dados[0]), dataHoraSessao, sala, status);
            sessoes.add(sessao);
        }
      }

    }
    return sessoes;
  }
  
  public static Sessao consultarSessao(int idSessao) throws IOException {
    Sessao sessao = null;

    BufferedReader br = new BufferedReader(new FileReader("sessao.txt"));
    String linha;
    while ((linha = br.readLine()) != null) {
      String[] dados = linha.split(";");
      if (Integer.parseInt(dados[0]) == idSessao) {
        int dataHoraSessao = Integer.parseInt(dados[1]);

        Sala sala = null;
        for (Sala s : Sala.listarSalas()) {
          if (s.getIdSala() == Integer.parseInt(dados[2])) {
            sala = s;
            break;
          }
        }

        String status = dados[3];
        sessao = new Sessao(dataHoraSessao, sala, status);
        break;
      }
    }

    br.close();

    return sessao;
  }
  
  @Override
  public String toString() {
    return "ID: " + idSessao + "\nData e Hora: " + dataHoraSessao + "\nSala: " + this.sala + "\nStatus: "  + status;
  }
}
