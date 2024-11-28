import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Funcionario extends Pessoa {
  private int matricula;
  private Date horarioTrabalho;

  public Funcionario(int matricula, String cpf, String nome, String email, Date horarioTrabalho) {
    super(cpf, nome, email);
    this.matricula = matricula;
    this.horarioTrabalho = horarioTrabalho;
  }

  public int getMatricula() {
    return matricula;
  }

  public void setMatricula(int matricula) {
    this.matricula = matricula;
  }

  public Date getHorarioTrabalho() {
    return horarioTrabalho;
  }

  public void setHorarioTrabalho(Date horarioTrabalho) {
    this.horarioTrabalho = horarioTrabalho;
  }

  public static boolean cadastrarFuncionario(Funcionario funcionario) throws IOException {
    boolean retorno = true;

    BufferedWriter bw = new BufferedWriter(new FileWriter("funcionario.txt", true));

    bw.write(funcionario.getMatricula() + ";" + funcionario.getCpf() + ";" + funcionario.getNome() + ";" + funcionario.getEmail() + ";" + funcionario.getHorarioTrabalho());
    bw.newLine();
    bw.close();

    return retorno;
  }

  public static boolean editarFuncionario(int matricula, String novoNome) throws IOException {
    boolean encontrado = false;

    ArrayList<Funcionario> funcionarios = listarFuncionarios();

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("funcionario.txt"))) {
        for (Funcionario f : funcionarios) {
            if (f.getMatricula() == matricula) {
                f.setNome(novoNome);
                encontrado = true;
            }

            bw.write(f.getMatricula() + ";" + f.getCpf() + ";" + f.getNome() + ";" + f.getEmail() + ";" + f.getHorarioTrabalho());
            bw.newLine();
        }
    }

    if (!encontrado) {
        System.out.println("Funcionário não encontrado.");
    }

    return encontrado;
  }

  public static ArrayList<Funcionario> listarFuncionarios() throws IOException {
    ArrayList<Funcionario> funcionarios = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader("funcionario.txt"))) {
      String linha;
      while ((linha = br.readLine()) != null) {
        String[] dados = linha.split(";");
        int matriculaFunc = Integer.parseInt(dados[0]);
        String cpfFunc = dados[1];
        String nomeFunc = dados[2];
        String emailFunc = dados[3];
        @SuppressWarnings("deprecation")
        Date horarioTrabalhoFunc = new Date(dados[4]);
        
        Funcionario funcionario = new Funcionario(matriculaFunc, cpfFunc, nomeFunc, emailFunc, horarioTrabalhoFunc);
        funcionarios.add(funcionario);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return funcionarios;
  }

  public static Funcionario consultarFuncionario(int matriculaFunc) throws IOException {
    Funcionario funcionario = null;
    try (BufferedReader br = new BufferedReader(new FileReader("funcionario.txt"))) {
      String linha;
      while ((linha = br.readLine()) != null) {
        String[] dados = linha.split(";");
        if (dados[0].equals(String.valueOf(matriculaFunc))) {
          String cpfFunc = dados[1];
          String nomeFunc = dados[2];
          String emailFunc = dados[3];
          @SuppressWarnings("deprecation")
          Date horarioTrabalhoFunc = new Date(dados[4]);

          funcionario = new Funcionario(matriculaFunc, cpfFunc, nomeFunc, emailFunc, horarioTrabalhoFunc);
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return funcionario;
  }
}

