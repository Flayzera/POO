import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Sala {
  private int idSala;
  private int capacidadeSala;
  private String descricaoSala;
  private String statusSala;

  public Sala(int capacidadeSala, String descricaoSala, String statusSala) throws IOException {
    this.idSala = Utils.obterProximoId("sala.txt");
    this.capacidadeSala = capacidadeSala;
    this.descricaoSala = descricaoSala;
    this.statusSala = statusSala;
  }

  public Sala(int idSala, int capacidadeSala, String descricaoSala, String statusSala) throws IOException {
    this.idSala = idSala;
    this.capacidadeSala = capacidadeSala;
    this.descricaoSala = descricaoSala;
    this.statusSala = statusSala;
  }

  public Sala() throws IOException {
    this.idSala = Utils.obterProximoId("sala.txt");
  }

  public int getIdSala() {
    return idSala;
  }

  public void setIdSala(int idSala) {
    this.idSala = idSala;
  }

  public int getCapacidadeSala() {
    return capacidadeSala;
  }

  public void setCapacidadeSala(int capacidadeSala) {
    this.capacidadeSala = capacidadeSala;
  }

  public String getDescricaoSala() {
    return descricaoSala;
  }

  public void setDescricaoSala(String descricao) {
    this.descricaoSala = descricao;
  }

  public String getStatusSala() {
    return statusSala;
  }

  public void setStatus(String statusSala) {
    this.statusSala = statusSala;
  }


  public Sala instanciarSala(Scanner sc) throws IOException {
    System.out.print("Digite a capacidade da sala: ");
    int capacidade = sc.nextInt();
    sc.nextLine();

    System.out.print("Digite a descrição da sala: ");
    String descSala = sc.nextLine();

    System.out.print("Digite o status da sala: ");
    String statusSala = sc.nextLine();

    this.capacidadeSala = capacidade;
    this.descricaoSala = descSala;
    this.statusSala = statusSala;

    return this;
  }

  public static boolean cadastrarSala(Sala sala) throws IOException {
    boolean retorno = true;

    BufferedWriter bw = new BufferedWriter(new FileWriter("sala.txt", true));

    bw.write(sala.getIdSala() + ";" + sala.getCapacidadeSala() + ";" + sala.getDescricaoSala() + ";" + sala.getStatusSala());
    bw.newLine();
    bw.close();

    return retorno;
  }

  public static boolean editarSala(int idSala, String novaDescricao) throws IOException {
    boolean encontrado = false;

    ArrayList<Sala> salas = listarSalas();

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("sala.txt"))) {
        for (Sala s : salas) {
            if (s.getIdSala() == idSala) {
                s.setDescricaoSala(novaDescricao);
                encontrado = true;
            }

            bw.write(s.getIdSala() + ";" + s.getCapacidadeSala() + ";" + s.getDescricaoSala() + ";" + s.getStatusSala());
            bw.newLine();
        }
    }

    if (!encontrado) {
        System.out.println("Sala não encontrada.");
    }

    return encontrado;
  }

  public static ArrayList<Sala> listarSalas() throws IOException {
    ArrayList<Sala> salas = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader("sala.txt"))) {
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] dados = linha.split(";");
            Sala s = new Sala(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]), dados[2], dados[3]);
            salas.add(s);
        }
    }

    return salas;
  }

  public static Sala consultarSala(int idSala) throws IOException {
    Sala sala = null;
    try (BufferedReader br = new BufferedReader(new FileReader("sala.txt"))) {
      String linha;
      while ((linha = br.readLine()) != null) {
        String[] dados = linha.split(";");
        if (dados[0].equals(String.valueOf(idSala))) {
          int capacidadeSala = Integer.parseInt(dados[1]);
          String descSala = dados[2];
          String status = dados[3];
          
          sala = new Sala(idSala, capacidadeSala, descSala, status);
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sala;
  }
  @Override
  public String toString() {
    return "Sala [capacidadeSala=" + capacidadeSala + ", descricaoSala=" + descricaoSala + ", idSala=" + idSala
        + ", statusSala=" + statusSala + "]";
  }
}
