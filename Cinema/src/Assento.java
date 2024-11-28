import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Assento {
  private int idAssento;
  private TipoAssento tipoAssento;

  //Método construtor

  public Assento() throws IOException {
    this.idAssento = Utils.obterProximoId("sala.txt");
  }

  public Assento(int idAssento, TipoAssento tipoAssento) {
    this.idAssento = idAssento;
    this.tipoAssento = tipoAssento;
  }

  //Getters & Setters

  public int getIdAssento() {
    return idAssento;
  }

  public TipoAssento getTipoAssento() {
    return tipoAssento;
  }

  public void setIdAssento(int idAssento) {
    this.idAssento = idAssento;
  }

  public void setTipoAssento(TipoAssento tipoAssento) {
    this.tipoAssento = tipoAssento;
  }

  public Assento instanciarAssento(ArrayList<TipoAssento> tipoAssentos, Scanner sc) throws IOException {
    System.out.print("Digite o ID do Assento: ");
    int idTipoAssento = sc.nextInt();
    sc.nextLine();

    TipoAssento tipoAssentoEncontrado = null;
    for (TipoAssento tipoAssento : tipoAssentos){
      if (tipoAssento.getIdTipoAssento() == idTipoAssento){
          tipoAssentoEncontrado = tipoAssento;
          break;
      }
    }

    if(tipoAssentoEncontrado == null){
      System.out.println("Tipo de assento não encontrado. Deseja criar um novo? (S/N)");
      String opcao = sc.nextLine();
      if (opcao.equalsIgnoreCase("S")) {
          tipoAssentoEncontrado = new TipoAssento().instanciarTipoAssento(sc);
      } else {
          System.out.println("Operação cancelada.");
          return null;
      }
    }

    this.tipoAssento = tipoAssentoEncontrado;
    return this;
  }

  public static boolean cadastrarAssento(Assento assento) throws IOException {
    boolean retorno = true;
    BufferedWriter bw = new BufferedWriter(new FileWriter("assento.txt", true));  

    bw.write(assento.idAssento + ";" + assento.tipoAssento.getIdTipoAssento());
    bw.newLine();
    bw.close();

    return retorno;
  }

  public static boolean editarAssento(int idAssento, TipoAssento novoTipo, ArrayList<TipoAssento> tipoAssentos) throws IOException {
    boolean retorno = false;

    ArrayList<Assento> assentos = listarAssentos(tipoAssentos);

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("assento.txt"))) {
      for (Assento a : assentos) {
        if (a.getIdAssento() == idAssento) {
          a.setTipoAssento(novoTipo);
          retorno = true;
        }
        bw.write(a.getIdAssento() + ";" + a.getTipoAssento().getIdTipoAssento());
        bw.newLine();
      }
    }

    if (!retorno) {
      System.out.println("Assento não encontrado.");
    }

    return retorno;
  }  

  public static ArrayList<Assento> listarAssentos(ArrayList<TipoAssento> tipoAssentos) throws IOException {
    ArrayList<Assento> assentos = new ArrayList<>();
    BufferedReader br = new BufferedReader(new FileReader("assento.txt"));
    
    String linha;

    while((linha = br.readLine()) != null) {
      String[] dados = linha.split(";");
      int idAssento = Integer.parseInt(dados[0]);
      TipoAssento tipoAssento = null;
      for (TipoAssento ta : tipoAssentos) {
        if (ta.getIdTipoAssento() == Integer.parseInt(dados[1])) {
          tipoAssento = ta;
          break;
        }
      }

      Assento assento = new Assento(idAssento, tipoAssento);
      assentos.add(assento);
    }
    
    br.close();
    return assentos;
  }  
  
  public static Assento consultarAssento(int idAssento, ArrayList<TipoAssento> tipoAssentos) throws IOException {
    Assento assento = null;
    BufferedReader br = new BufferedReader(new FileReader("assento.txt"));

    String linha;
    
    while((linha = br.readLine()) != null) {
      String[] dados = linha.split(";");
      int id = Integer.parseInt(dados[0]);
      if (id == idAssento) {
        TipoAssento tipoAssento = null;
        for (TipoAssento ta : tipoAssentos) {
          if (ta.getIdTipoAssento() == Integer.parseInt(dados[1])) {
            tipoAssento = ta;
            break;
          }
        }
        assento = new Assento(id, tipoAssento);
        break;
      }
    }

    br.close();

    return assento;
  }  
}
