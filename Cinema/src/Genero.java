import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Genero {
  private int id;
  private String desc;
  private String status;

  //Método construtor
 
  public Genero(String desc, String status) throws IOException {
    this.id = Utils.obterProximoId("genero.txt");
    this.desc = desc;
    this.status = "A";
  }

  //Getters & Setters

  public int getId() {
    return id;
  }

  public String getDesc() {
    return desc;
  }

  public String getStatus() {
    return status;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public static boolean inserirGenero(Genero genero) throws IOException {
    boolean retorno = true;

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("genero.txt", true))) {
      bw.write(genero.getId() + ";" + genero.getDesc() + ";"  + genero.getStatus());
      bw.newLine();
    } catch (IOException e) {
      retorno = false;
      e.printStackTrace();
    }

    return retorno;
  }

  public static ArrayList<Genero> listarGeneros() throws IOException {
    ArrayList<Genero> generos = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader("genero.txt"))) {
      String linha;
      while ((linha = br.readLine()) != null) {
        String[] dados = linha.split(";");
        Genero genero = new Genero(dados[1], dados[2]);
        generos.add(genero);
        
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return generos;
  }

  public static Genero consultarGenero(int idGenero) {
    Genero genero = null;

    try (BufferedReader br = new BufferedReader(new FileReader("genero.txt"))) {
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] dados = linha.split(";");

            if (Integer.parseInt(dados[0]) == idGenero) {
                genero = new Genero(dados[1], dados[2]); 
                genero.setId(idGenero); 
                break;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return genero;
  }
 
  public static boolean editarGenero(Genero genero) throws IOException {
    boolean retorno = false;

    ArrayList<Genero> generos = listarGeneros();
    
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("genero.txt"))) {
      for (Genero g : generos) {
        if (g.getId() == genero.getId()) {
          retorno = true;
          bw.write(genero.getId() + ";" + genero.getDesc() + ";" + genero.getStatus());
        } else {
          bw.write(g.getId() + ";" + g.getDesc() + ";" + g.getStatus());
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
}  