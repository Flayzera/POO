import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Ingresso {
  private int id;
  private double valorPago;
  private SalaAssento salaAssento;
  private Sessao sessao;  

  //Método construtor

  public Ingresso (double valorPago, SalaAssento salaAssento, Sessao sessao) throws IOException {
    this.id = Utils.obterProximoId("ingresso.txt");
    this.valorPago = valorPago;
    this.salaAssento = salaAssento;
    this.sessao = sessao;
  }

  //Getters & Setters

  public int getId() {
    return id;
  }

  public double getValorPago() {
    return valorPago;
  }

  public SalaAssento getSalaAssento() {
    return salaAssento;
  }

  public Sessao getSessao() {
    return sessao;
  }

  public void setValorPago(double valorPago) {
    this.valorPago = valorPago;
  }

  public void setSalaAssento(SalaAssento salaAssento) {
    this.salaAssento = salaAssento;
  }

  public void setSessao(Sessao sessao) {
    this.sessao = sessao;
  }

  public static boolean cadastrarIngresso(Ingresso ingresso) throws IOException {
    boolean retorno = true;
    BufferedWriter bw = new BufferedWriter(new FileWriter("ingresso.txt", true));  

    bw.write(ingresso.id + ";" + ingresso.valorPago + ";" + ingresso.salaAssento + ";" + ingresso.sessao);
    bw.newLine();
    bw.close();

    return retorno;
  }

  public static ArrayList<Ingresso> listarIngressos() throws IOException {
    ArrayList<Ingresso> ingressos = new ArrayList<>();
    ArrayList<Assento> assentos = new ArrayList<>();
    ArrayList<Sala> salas = new ArrayList<>();

    BufferedReader br = new BufferedReader(new FileReader("ingresso.txt"));
    String linha;

    while ((linha = br.readLine()) != null) {
      String[] dados = linha.split(";");
      double valorPago = Double.parseDouble(dados[1]);
      SalaAssento salaAssento = null;
      Sessao sessao = null;
      for (SalaAssento sa : SalaAssento.listarSalaAssentos(assentos, salas)) {
        if (sa.getIdSalaAssento() == Integer.parseInt(dados[2])) {
          salaAssento = sa;
          break;
        }
        for (Sessao s : Sessao.listarSessoes()) {
          if (s.getIdSessao() == Integer.parseInt(dados[3])) {
            sessao = s;
            break;
          }
        }
      }

      Ingresso ingresso = new Ingresso(valorPago, salaAssento, sessao);
      ingressos.add(ingresso);

    }

    br.close();

    return ingressos;
  }

  public static boolean editarIngresso(int idIngresso, int novaSessao) throws IOException {
    boolean encontrado = false;

    ArrayList<Ingresso> ingressos = listarIngressos();

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("ingresso.txt"))) {
      for (Ingresso i : ingressos) {
        if (i.id == idIngresso) {
          i.setSessao(Sessao.consultarSessao(novaSessao));
          encontrado = true;
        }
        bw.write(i.id + ";" + i.valorPago + ";" + i.salaAssento + ";" + i.sessao);
        bw.newLine();
      }
    }

    if (!encontrado) {
      System.out.println("Ingresso não encontrado.");
    }

    return encontrado;
  }

  public static Ingresso consultarIngresso(int idIngresso) throws IOException {
    ArrayList<Assento> assentos = new ArrayList<>();
    ArrayList<Sala> salas = new ArrayList<>();

    Ingresso ingresso = null;
    BufferedReader br = new BufferedReader(new FileReader("sessao.txt"));
    
    String linha;
    
    while ((linha = br.readLine()) != null) {
      String[] dados = linha.split(";");
      if (Integer.parseInt(dados[0]) == idIngresso) {
        double valorPago = Double.parseDouble(dados[1]);
        SalaAssento salaAssento = null;
        Sessao sessao = null;

        for (SalaAssento sa : SalaAssento.listarSalaAssentos(assentos, salas)) {
          if (sa.getIdSalaAssento() == Integer.parseInt(dados[2])) {
            salaAssento = sa;
            break;
          }
          for (Sessao s : Sessao.listarSessoes()) {
            if (s.getIdSessao() == Integer.parseInt(dados[3])) {
              sessao = s;
              break;
            }
          }
        }
        ingresso = new Ingresso(valorPago, salaAssento, sessao);
        break;
      }
    }

    br.close();

    return ingresso;
  }
}
