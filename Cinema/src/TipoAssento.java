import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TipoAssento {
  private int idTipoAssento;
  private String descricaoAssento;
  private String statusAssento;

  //Método construtor 

  public TipoAssento(int idTipoAssento, String descricaoAssento, String statusAssento) {
    this.idTipoAssento = idTipoAssento;
    this.descricaoAssento = descricaoAssento;
    this.statusAssento = statusAssento;
  }

  public TipoAssento() throws IOException {
    this.idTipoAssento = Utils.obterProximoId("tipo-assento.txt");
  }

  //Getters & Setters

  public int getIdTipoAssento() {
    return idTipoAssento;
  }

  public String getDescricaoAssento() {
    return descricaoAssento;
  }

  public String getStatusAssento() {
    return statusAssento;
  }

  public void setDescricaoAssento(String descricaoAssento) {
    this.descricaoAssento = descricaoAssento;
  }

  public void setStatusAssento(String statusAssento) {
    this.statusAssento = statusAssento;
  }

  public void setIdTipoAssento(int idTipoAssento) {
    this.idTipoAssento = idTipoAssento;
  }  

  public TipoAssento instanciarTipoAssento(Scanner sc) throws IOException {
    System.out.print("Digite a descricao do assento: ");
    String descAssento = sc.nextLine();

    System.out.print("Digite o status do assento: ");
    String status = sc.nextLine();

    this.descricaoAssento = descAssento;
    this.statusAssento = status;

    return this;
  }

  public static boolean cadastrarTipoAssento(TipoAssento tipoAssento) throws IOException {
    boolean retorno = true;
    BufferedWriter bw = new BufferedWriter(new FileWriter("tipo-assento.txt", true));
     
    bw.write(tipoAssento.idTipoAssento + ";" + tipoAssento.descricaoAssento + ";" + tipoAssento.statusAssento);
    bw.newLine();
    bw.close();
      
    return retorno;
  } 

  public static boolean editarTipoAssento(int idTipoAssento, String novaDesc) throws IOException {
      boolean retorno = false;
  
      ArrayList<TipoAssento> listaTipoAssentos = listarTipoAssentos();
      
      try (BufferedWriter bw = new BufferedWriter(new FileWriter("tipo-assento.txt"))) {
        for (TipoAssento tp : listaTipoAssentos) {
          if (tp.idTipoAssento == idTipoAssento) {
            retorno = true;
            TipoAssento newTp = new TipoAssento(tp.idTipoAssento, novaDesc, tp.statusAssento);
            bw.write(newTp.idTipoAssento + ";" + newTp.descricaoAssento + ";" + newTp.statusAssento);
          } else {
            bw.write(tp.idTipoAssento + ";" + tp.descricaoAssento + ";" + tp.statusAssento);
          }
          bw.newLine();
        }
      } catch (IOException e) {
        retorno = false;
        e.printStackTrace();
      }
  
      if (!retorno) {
        System.out.println("Gênero não encontrado.");
      }
  
      return retorno;
    }

   public static ArrayList<TipoAssento> listarTipoAssentos() throws IOException {
    ArrayList<TipoAssento> listaTipoAssentos = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader("tipo-assento.txt"))) {
      String linha;
      while ((linha = br.readLine()) != null) {
        String[] dados = linha.split(";");
        int idTipoAssento = Integer.parseInt(dados[0]);
        String descAssento = dados[1];
        String statusAssento = dados[2];
        
        TipoAssento tp = new TipoAssento(idTipoAssento, descAssento, statusAssento);
        listaTipoAssentos.add(tp);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return listaTipoAssentos;
  }

  public static TipoAssento consultarTipoAssento(int idTipoAssento) throws IOException {
    TipoAssento tp = null;
    try (BufferedReader br = new BufferedReader(new FileReader("tipo-assento.txt"))) {
      String linha;
      while ((linha = br.readLine()) != null) {
        String[] dados = linha.split(";");
        int idTp = Integer.parseInt(dados[0]);
        String descAssento = dados[1];
        String statusAssento = dados[2];
        
        if (idTp == idTipoAssento) {
          tp = new TipoAssento(idTipoAssento, descAssento, statusAssento);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return tp;
  }
}
