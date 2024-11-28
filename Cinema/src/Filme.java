import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Filme {
    private int id;
    private String titulo;
    private int classificacao;
    private Genero genero;

    // Método construtor
    public Filme(Genero genero, String titulo) throws IOException {
        this.id = Utils.obterProximoId("filme.txt");
        this.genero = genero;
        this.titulo = titulo;
    }

    // Getters & Setters
    public Filme(int idFilme, Genero idGenero) {
        this.id = idFilme;
        this.genero = idGenero;
    }

    public int getId() {
        return id;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public Genero getGenero() {
        return this.genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Método para cadastrar filme
    public static boolean cadastrarFilme(Filme filme) throws IOException {
        boolean retorno = true;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("filme.txt", true))) {
            // Verifica se o gênero é nulo
            if (filme.getGenero() != null) {
                bw.write(filme.getId() + ";" + filme.getGenero().getId() + ";" + filme.getTitulo());
            } else {
                // Caso o gênero seja nulo, podemos gravar um valor padrão (null)
                bw.write(filme.getId() + ";null;" + filme.getTitulo());
            }
            bw.newLine();
        }
        return retorno;
    }

    // Método para editar filme
    public static boolean editarFilme(int idFilme, String novoTitulo, ArrayList<Genero> generos) throws IOException {
        ArrayList<Filme> filmes = listarFilmes(generos);  // Recarrega todos os filmes
        boolean filmeEditado = false;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("filme.txt"))) {
            for (Filme filme : filmes) {
                if (filme.getId() == idFilme) {
                    filme.setTitulo(novoTitulo);  // Atualiza o título do filme
                    filmeEditado = true;
                }
                // Verifica se o gênero é nulo antes de gravar
                if (filme.getGenero() != null) {
                    bw.write(filme.getId() + ";" + filme.getGenero().getId() + ";" + filme.getTitulo());
                } else {
                    // Caso o gênero seja nulo, pode gravar um valor padrão
                    bw.write(filme.getId() + ";null;" + filme.getTitulo());
                }
                bw.newLine();
            }
        }

        return filmeEditado;
    }

    // Método para consultar um filme pelo ID
    public static Filme consultarFilme(int idFilme, ArrayList<Genero> generos) {
      Filme filme = null;
      try (BufferedReader br = new BufferedReader(new FileReader("filme.txt"))) {
          String linha;
          while ((linha = br.readLine()) != null) {
              String[] dados = linha.split(";");
              if (Integer.parseInt(dados[0]) == idFilme) { 
                  Genero idGenero = null;
                  if (!"null".equals(dados[1])) {  // Verifica se o gênero não é "null"
                      int idGeneroInt = Integer.parseInt(dados[1]);
                      for (Genero g : generos) {
                          if (g.getId() == idGeneroInt) {
                              idGenero = g;
                              break;
                          }
                      }
                  }
  
                  filme = new Filme(idGenero, dados[2]);
                  filme.setId(idFilme); 
                  break;
              }
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
  
      return filme;
  }
  
    public static ArrayList<Filme> listarFilmes(ArrayList<Genero> generos) throws IOException {
      ArrayList<Filme> filmes = new ArrayList<>();
  
      try (BufferedReader br = new BufferedReader(new FileReader("filme.txt"))) {
          String linha;
  
          while ((linha = br.readLine()) != null) {
              String[] dados = linha.split(";");
  
              if (dados.length == 3) {
                  int idFilme = Integer.parseInt(dados[0]);
                  String titulo = dados[2];
  
                  Genero idGenero = null;
                  for(Genero g : Genero.listarGeneros()) {
                      if (g.getId() == Integer.parseInt(dados[1])) {  
                        idGenero = g;
                        break;
                      }
                  }
                  Filme filme = new Filme(idGenero, titulo);
                  filme.setId(idFilme);
                  filmes.add(filme);
              }
          }
      }
  
      return filmes;
  }
  

    @Override
    public String toString() {
        return "ID: " + id + " - " + "Titulo: " + titulo;
    }
}
