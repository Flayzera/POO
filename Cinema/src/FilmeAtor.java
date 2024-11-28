import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FilmeAtor {
  private int idFilmeAtor;
  private Ator ator;
  private Filme filme;
  private String personagem;
  private boolean principal;

  public FilmeAtor(int idFilmeAtor, Ator ator, Filme filme, String personagem, boolean principal) {
    this.idFilmeAtor = idFilmeAtor;
    this.ator = ator;
    this.filme = filme;
    this.personagem = personagem;
    this.principal = principal;
  }

  public int getIdFilmeAtor() {
    return idFilmeAtor;
  }

  public Ator getAtor() {
    return ator;
  }

  public Filme getFilme() {
    return filme;
  }

  public String getPersonagem() {
    return personagem;
  }

  public boolean getPrincipal() {
    return principal;
  }

  public void setIdFilmeAtor(int idFilmeAtor) {
    this.idFilmeAtor = idFilmeAtor;
  }

  public void setAtor(Ator ator) {
    this.ator = ator;
  }

  public void setFilme(Filme filme) {
    this.filme = filme;
  }

  public void setPersonagem(String personagem) {
    this.personagem = personagem;
  }

  public void setPrincipal(boolean principal) {
    this.principal = principal;
  }

  public static boolean cadastrarFilmeAtor(FilmeAtor filmeAtor) throws IOException {
    boolean retorno = true;
    BufferedWriter bw = new BufferedWriter(new FileWriter("filme-ator.txt", true));
     
    bw.write(filmeAtor.getIdFilmeAtor() + ";" + filmeAtor.getAtor() + ";" + filmeAtor.getFilme() + ";" + filmeAtor.getPersonagem() + ";" + filmeAtor.getPrincipal());
    bw.newLine();
    bw.close();
      
    return retorno;
  } 

  public static boolean editarFilmeAtor(int idFilmeAtor, boolean isPrincipal) throws IOException {
      boolean retorno = false;
  
      ArrayList<FilmeAtor> filmesAtores = listarFilmesAtores();
      
      try (BufferedWriter bw = new BufferedWriter(new FileWriter("filme-ator.txt"))) {
        for (FilmeAtor fa : filmesAtores) {
          if (fa.getIdFilmeAtor() == idFilmeAtor) {
            retorno = true;
            FilmeAtor newFilmeAtor = new FilmeAtor(fa.getIdFilmeAtor(), fa.getAtor(), fa.getFilme(), fa.getPersonagem(), isPrincipal);
            bw.write(newFilmeAtor.getIdFilmeAtor() + ";" + newFilmeAtor.getAtor() + ";" + newFilmeAtor.getFilme() + ";" + newFilmeAtor.getPersonagem() + ";" + newFilmeAtor.getPrincipal());
          } else {
            bw.write(fa.getIdFilmeAtor() + ";" + fa.getAtor() + ";" + fa.getFilme() + ";" + fa.getPersonagem() + ";" + fa.getPrincipal());
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

  public static ArrayList<FilmeAtor> listarFilmesAtores() {
    ArrayList<FilmeAtor> filmesAtores = new ArrayList<FilmeAtor>();
    ArrayList<Genero> generos = new ArrayList<Genero>();
    try {
      BufferedReader br = new BufferedReader(new FileReader("filme-ator.txt"));
      String linha;
      while ((linha = br.readLine()) != null) {
        String[] dados = linha.split(";");
        int idFilmeAtor = Integer.parseInt(dados[0]);
        Ator ator = null;
        Filme filme = null;
        for (Ator a : Ator.listarAtores()) {
          if (a.getRegistro() == Integer.parseInt(dados[1])) {
            ator = a;
            break;
          }
        }
        for (Filme f : Filme.listarFilmes(generos)) {
          if (f.getId() == Integer.parseInt(dados[2])) {
            filme = f;
            break;
          }
        }
        String personagem = dados[3];
        boolean principal = Boolean.parseBoolean(dados[4]);

        FilmeAtor filmeAtor = new FilmeAtor(idFilmeAtor, ator, filme, personagem, principal);

        filmesAtores.add(filmeAtor);
      }
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return filmesAtores;
  }


}


