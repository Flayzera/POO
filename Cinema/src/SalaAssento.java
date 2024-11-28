import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SalaAssento {
  private int idSalaAssento;
  private Assento assento;
  private Sala sala;

  //Método construtor

  public SalaAssento(Assento assento, Sala sala) throws IOException {
    this.idSalaAssento = Utils.obterProximoId("sala-assento.txt");
    this.assento = assento;
    this.sala = sala;
  }

  public SalaAssento(int idSalasAssento, Assento assento, Sala sala) {
    this.idSalaAssento = idSalasAssento;
    this.assento = assento;
    this.sala = sala;
  }

  //Getters & Setters

  public int getIdSalaAssento() {
    return idSalaAssento;
  }

  public Assento getAssento() {
    return assento;
  }

  public Sala getSala() {
    return sala;
  }

  public void setIdSalaAssento(int idSalasAssento) {
    this.idSalaAssento = idSalasAssento;
  }

  public void setAssento(Assento assento) {
    this.assento = assento;
  }

  public void setSala(Sala sala) {
    this.sala = sala;
  }

  public static boolean cadastrarSalaAssento(SalaAssento salaAssento) throws IOException {
    boolean retorno = true;
    BufferedWriter bw = new BufferedWriter(new FileWriter("sala-assento.txt", true));  

    bw.write(salaAssento.idSalaAssento + ";" + salaAssento.getAssento().getIdAssento() + ";" + salaAssento.getSala().getIdSala());
    bw.newLine();
    bw.close();

    return retorno;
  }

  public static boolean editarSalaAssento(int idSalaAssento, Assento novoAssento, ArrayList<Assento> assentos, ArrayList<Sala> salas) throws IOException {
    boolean encontrado = false;

    ArrayList<SalaAssento> salaAssentos = listarSalaAssentos(assentos, salas);

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("sessao.txt"))) {
      for (SalaAssento sa : salaAssentos) {
        if (sa.idSalaAssento == idSalaAssento) {
          sa.setAssento(novoAssento);
          encontrado = true;
        }
        bw.write(sa.idSalaAssento + ";" + novoAssento + ";" + sa.getSala());
        bw.newLine();
      }
    }

    if (!encontrado) {
      System.out.println("Sessão não encontrada.");
    }

    return encontrado;
  }

  public static ArrayList<SalaAssento> listarSalaAssentos(ArrayList<Assento> assentos, ArrayList<Sala> salas) throws IOException {
    ArrayList<SalaAssento> salaAssentos = new ArrayList<SalaAssento>();

    try (BufferedReader br = new BufferedReader(new FileReader("sala-assento.txt"))) {
      String linha;
      while ((linha = br.readLine()) != null) {
        String[] dados = linha.split(";");
        int idSalaAssento = Integer.parseInt(dados[0]);
        Assento assento = null;
        Sala sala = null;
        for(Assento a : assentos){
          if(a.getIdAssento() == Integer.parseInt(dados[1])){
            assento = a;
            for(Sala s : salas){
              if(s.getIdSala() == Integer.parseInt(dados[2])){
                sala = s;
              }
            }
          }
        }

        SalaAssento newSalaAssento = new SalaAssento(idSalaAssento, assento, sala);
        
        salaAssentos.add(newSalaAssento);
      }
    }

    return salaAssentos;
  }

  public static SalaAssento consultarSalaAssento(int idSalaAssento, ArrayList<Assento> assentos, ArrayList<Sala> salas) throws IOException {
    SalaAssento salaAssento = null;
    Sala sala = null;
    Assento assento = null;

    try (BufferedReader br = new BufferedReader(new FileReader("sala-assento.txt"))) {
      String linha;
      while ((linha = br.readLine()) != null) {
        String[] dados = linha.split(";");
        if (dados[0].equals(String.valueOf(idSalaAssento))) {
          for(Assento a : assentos){
            if(a.getIdAssento() == Integer.parseInt(dados[1])){
              assento = a;
              for(Sala s : salas){
                if(s.getIdSala() == Integer.parseInt(dados[2])){
                  sala = s;
                }
              }
            }
          }
         
          salaAssento = new SalaAssento(idSalaAssento, assento, sala);
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return salaAssento;
  }
 
}
