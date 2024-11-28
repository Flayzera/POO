import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        ArrayList<Genero> generos = new ArrayList<>();
        ArrayList<Filme> filmes = new ArrayList<>();
        ArrayList<Ator> atores = new ArrayList<>();
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        ArrayList<Assento> assentos = new ArrayList<>();
        ArrayList<TipoAssento> tipoAssentos = new ArrayList<>();
        ArrayList<Sala> salas = new ArrayList<>();
        ArrayList<Sessao> sessoes = new ArrayList<>();
        ArrayList<Ingresso> ingressos = new ArrayList<>();
        ArrayList<SalaAssento> salasAssentos = new ArrayList<>();

        try {
            generos = Genero.listarGeneros();
            filmes = Filme.listarFilmes(generos);
            sessoes = Sessao.listarSessoes();
            ingressos = Ingresso.listarIngressos();
            salas = Sala.listarSalas();
            ////atores = Ator.listarAtores();
            ////funcionarios = Funcionario.listarFuncionarios();
            assentos = Assento.listarAssentos(tipoAssentos);
            tipoAssentos = TipoAssento.listarTipoAssentos();
            salasAssentos = SalaAssento.listarSalaAssentos(assentos, salas);
         
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }

        while (true) {
            System.out.println("Menu Principal:");
            System.out.println("1 - Menu de Filmes");
            System.out.println("2 - Menu de Ingressos");
            System.out.println("3 - Menu de Atores");
            System.out.println("4 - Menu de Funcionario");
            System.out.println("5 - Menu de Assento");
            System.out.println("6 - Menu de Sessao");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine(); 

            if (opcao == 0) break;

            switch (opcao) {
                case 1:
                    new MenuFilme(sc, filmes, generos).exibirMenu();
                    break;
                case 2:
                    new MenuIngresso(sc, ingressos, salasAssentos, sessoes, tipoAssentos, salas, assentos).exibirMenu();
                    break;
                case 3:
                    new MenuAtor(sc, atores).exibirMenu();
                    break;
                case 4:
                    new MenuFuncionario(sc, funcionarios).exibirMenu();
                    break;
                case 5:
                    new MenuAssento(sc, assentos, tipoAssentos).exibirMenu();
                    break;   
                case 6:
                    new MenuSessao(sc, sessoes).exibirMenu();
                    break;
                          
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        sc.close();
        System.out.println("Programa encerrado.");
    }

    // Menu de Filmesd
    static class MenuFilme {
        private Scanner sc;
        private ArrayList<Filme> filmes;
        private ArrayList<Genero> generos;

        public MenuFilme(Scanner sc, ArrayList<Filme> filmes, ArrayList<Genero> generos) {
            this.sc = sc;
            this.filmes = filmes;
            this.generos = generos;
        }

        public void exibirMenu() throws IOException {
            System.out.println("Menu de Filmes:");
            System.out.println("1 - Cadastrar Filme");
            System.out.println("2 - Editar Filme");
            System.out.println("3 - Listar Filmes");
            System.out.println("4 - Consultar Filme");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarFilme();
                    break;
                case 2:
                    editarFilme();
                    break;
                case 3:
                    listarFilmes();
                    break;
                case 4:
                    consultarFilme();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        public void cadastrarFilme() throws IOException {
            System.out.print("Digite o título do filme: ");
            String titulo = sc.nextLine();
        
            System.out.print("Digite o ID do gênero do filme: ");
            int idGenero = sc.nextInt();
            sc.nextLine(); 
            
            // Consultar o gênero existente a partir do ID digitado
            Genero generoExistente = Genero.consultarGenero(idGenero);

            // Caso nao for encontrado, perguntar se deseja criar um novo
            if (generoExistente == null) {
                System.out.println("Gênero não encontrado. Deseja criar um novo? (S/N)");
                String opcao = sc.nextLine();
            
                if (opcao.equalsIgnoreCase("S")) {
                    System.out.print("Digite a descrição do novo gênero: ");
                    String descGenero = sc.nextLine();

                    // Criar um novo gênero
                    generoExistente = new Genero(descGenero, "A");
                
                    // Inserir o novo gênero na lista de gêneros
                    Genero.inserirGenero(generoExistente);
                    System.out.println("Novo gênero cadastrado com sucesso!");
                } else {
                    System.out.println("Operação cancelada.");
                    return;
                }
            }

            Filme novoFilme = new Filme(generoExistente, titulo);

            // Verificar se o gênero foi atribuído ao filme
            if (novoFilme.getGenero() == null) {
                System.out.println("Erro: Gênero não atribuído ao filme.");
                return; 
            }

            //Cadastra o filme
            if (Filme.cadastrarFilme(novoFilme)) {
                filmes.add(novoFilme); 
                System.out.println("Filme cadastrado com sucesso!");
            } else {
                System.out.println("Erro ao cadastrar o filme.");
            }
        }
             
        private void editarFilme() throws IOException {
            System.out.print("Digite o id do filme que deseja editar: ");
            int idFilme = sc.nextInt();
            sc.nextLine(); // Limpar o buffer de leitura
        
            // Consultar o filme existente
            Filme filmeExistente = Filme.consultarFilme(idFilme, generos);
            if (filmeExistente == null) {
                System.out.println("Filme não encontrado.");
                return;
            }
        
            // Exibir as informações do filme atual
            System.out.println("Filme encontrado: " + filmeExistente);
            System.out.println("Título atual: " + filmeExistente.getTitulo());
            System.out.println("Gênero atual: " + (filmeExistente.getGenero() != null ? filmeExistente.getGenero().getDesc() : "Não atribuído"));
        
            // Alterar o título
            System.out.print("Digite o novo título do filme: ");
            String novoTitulo = sc.nextLine();
        
            // Manter o gênero atual ou permitir alteração
            Genero generoAtual = filmeExistente.getGenero();
            System.out.println("Deseja alterar o gênero? (S/N)");
            String opcaoGenero = sc.nextLine();
            if (opcaoGenero.equalsIgnoreCase("S")) {
                System.out.print("Digite o ID do novo gênero: ");
                int idGenero = sc.nextInt();
                sc.nextLine(); // Limpar o buffer
        
                Genero generoNovo = Genero.consultarGenero(idGenero);
                if (generoNovo != null) {
                    generoAtual = generoNovo; // Atualizar o gênero
                } else {
                    System.out.println("Gênero não encontrado. O gênero atual será mantido.");
                }
            }
        
            // Atualizar o título e gênero do filme
            filmeExistente.setTitulo(novoTitulo);
            filmeExistente.setGenero(generoAtual);
        
            // Salvar as alterações
            if (Filme.editarFilme(idFilme, novoTitulo, generos)) {
                System.out.println("Filme editado com sucesso!");
            } else {
                System.out.println("Erro ao editar o filme.");
            }
        }
        
        private void listarFilmes() {
            for (Filme f : filmes) {
                System.out.println(f);
            }
        }

        private void consultarFilme() {
            System.out.print("Digite o ID do filme que deseja consultar: ");
            int idFilme = sc.nextInt();
            sc.nextLine();
            Filme filme = Filme.consultarFilme(idFilme, generos);
            if (filme != null) {
                System.out.println(filme);
            } else {
                System.out.println("Filme não encontrado.");
            }
        }
    }

    // Menu de Atores
    static class MenuAtor {
        private Scanner sc;
        private ArrayList<Ator> atores;

        public MenuAtor(Scanner sc, ArrayList<Ator> atores) {
            this.sc = sc;
            this.atores = atores;
        }

        public void exibirMenu() throws IOException {
            System.out.println("Menu de Atores:");
            System.out.println("1 - Cadastrar Ator");
            System.out.println("2 - Editar Ator");
            System.out.println("3 - Listar Atores");
            System.out.println("4 - Consultar Ator");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarAtor();
                    break;
                case 2:
                    editarAtor();
                    break;    
                case 3:
                    listarAtores();
                    break;
                case 4:
                    consultarAtor();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        private void cadastrarAtor() throws IOException {
            System.out.print("Digite o registro do Ator: ");
            int registro = sc.nextInt();
            sc.nextLine();
            System.out.print("Digite o CPF do Ator: ");
            String cpf = sc.nextLine();
            System.out.print("Digite o nome do Ator: ");
            String nome = sc.nextLine();
            System.out.print("Digite o email do Ator: ");
            String email = sc.nextLine();
            Ator novoAtor = new Ator(cpf, nome, email, registro);
            Ator.cadastrarAtor(novoAtor);
            atores.add(novoAtor);
            System.out.println("Ator cadastrado com sucesso!");
        }

        private void editarAtor() throws IOException {
            System.out.print("Digite o registro do Ator que deseja editar: ");
            int registro = sc.nextInt();
            sc.nextLine(); 

            ArrayList<Ator> atores = Ator.listarAtores();
            boolean existe = false;
        
            for (Ator a : atores) {
                if (a.getRegistro() == registro) {
                    existe = true;
                    break;
                }
            }
        
            if (!existe) {
                System.out.println("Ator com registro " + registro + " não encontrado.");
                return; 
            }
        
            System.out.print("Digite o novo nome do Ator: ");
            String novoNome = sc.nextLine();
        
            boolean sucesso = Ator.editarNomeAtor(registro, novoNome);
        
            if (sucesso) {
                System.out.println("Ator editado com sucesso!");
            } else {
                System.out.println("Falha ao editar o ator.");
            }
        }
        
        private void listarAtores() {
            for (Ator a : atores) {
                System.out.println(a);
            }
        }

        private void consultarAtor() throws IOException {
            System.out.print("Digite o registro do ator que deseja consultar: ");
            int registroAtor = sc.nextInt();
            Ator ator = Ator.consultarAtor(registroAtor);
            if (ator != null) {
                System.out.println(ator);
            } else {
                System.out.println("Ator não encontrado.");
            }
        }
    }

    // Menu de Funcionários
    static class MenuFuncionario {
        private Scanner sc;
        private ArrayList<Funcionario> funcionarios;

        public MenuFuncionario(Scanner sc, ArrayList<Funcionario> funcionarios) {
            this.sc = sc;
            this.funcionarios = funcionarios;
        }

        public void exibirMenu() throws IOException {
            System.out.println("Menu de Funcionários:");
            System.out.println("1 - Cadastrar Funcionário");
            System.out.println("2 - Editar Funcionário");
            System.out.println("3 - Listar Funcionários");
            System.out.println("4 - Consultar Funcionário");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarFuncionario();
                    break;
                case 2:
                    editarFuncionario();
                    break;    
                case 3: 
                    listarFuncionarios();
                    break;
                case 4:
                    consultarFuncionario();
                    break;        
                default:
                    System.out.println("Opção inválida.");
            }
        }

        private void cadastrarFuncionario() throws IOException {
            System.out.print("Digite a matricula do funcionário: ");
            int matriculaFuncionario = sc.nextInt();
            sc.nextLine();
            System.out.println("Digite o CPF do funcionário: ");
            String cpf = sc.nextLine();
            System.out.println("Digite o nome do funcionário: ");
            String nome = sc.nextLine();
            System.out.println("Digite o email do funcionário: ");
            String email = sc.nextLine();
            
            System.out.print("Digite o horário de trabalho do funcionário (formato HH:mm): ");
            String horarioTrabalhoString = sc.nextLine();

            Date horarioTrabalho = null;

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                horarioTrabalho = sdf.parse(horarioTrabalhoString);
            } catch (Exception e) {
                System.out.println("Erro ao converter o horário de trabalho. Formato esperado: HH:mm");
            return; 
            }
            
            Funcionario novoFuncionario = new Funcionario(matriculaFuncionario, cpf, nome, email, horarioTrabalho);
            Funcionario.cadastrarFuncionario(novoFuncionario);
            funcionarios.add(novoFuncionario);
            System.out.println("Funcionário cadastrado com sucesso!");
        }

        private void editarFuncionario() throws IOException {
            System.out.print("Digite a matrícula do funcionário que deseja editar: ");
            int matriculaFuncionario = sc.nextInt();
            sc.nextLine(); 

            ArrayList<Funcionario> funcionarios = Funcionario.listarFuncionarios();
            boolean existe = false;
        
            for (Funcionario f : funcionarios) {
                if (f.getMatricula() == matriculaFuncionario) {
                    existe = true;
                    break;
                }
            }
        
            if (!existe) {
                System.out.println("Funcionário com matrícula " + matriculaFuncionario + " não encontrado.");
                return; 
            }
        
            System.out.print("Digite o novo nome do funcionário: ");
            String novoNome = sc.nextLine();
        
            boolean sucesso = Funcionario.editarFuncionario(matriculaFuncionario, novoNome);
        
            if (sucesso) {
                System.out.println("Funcionário editado com sucesso!");
            } else {
                System.out.println("Falha ao editar o funcionário.");
            }
        }

        private void listarFuncionarios() throws IOException {
            ArrayList<Funcionario> funcionarios = Funcionario.listarFuncionarios();
            for (Funcionario f : funcionarios) {
                System.out.println("Matrícula: " + f.getMatricula() + " - Nome: " + f.getNome() + " - Horário de Trabalho: " + f.getHorarioTrabalho());
            }
        }

        private void consultarFuncionario() throws IOException {
            System.out.print("Digite a matrícula do funcionário que deseja consultar: ");
            int matriculaFuncionario = sc.nextInt();
            Funcionario funcionario = Funcionario.consultarFuncionario(matriculaFuncionario);
            if (funcionario != null) {
                System.out.println("Matrícula: " + funcionario.getMatricula() + " - Nome: " + funcionario.getNome());
            } else {
                System.out.println("Funcionário não encontrado.");
            }
        }
    }

    static class MenuAssento {
        private Scanner sc;
        private ArrayList<Assento> assentos;
        private ArrayList<TipoAssento> tipoAssentos;

        public MenuAssento(Scanner sc, ArrayList<Assento> assentos, ArrayList<TipoAssento> tipoAssentos) {
            this.sc = sc;
            this.assentos = assentos;
            this.tipoAssentos = tipoAssentos;
        }

        public void exibirMenu() throws IOException {
            System.out.println("Menu de Assentos:");
            System.out.println("1 - Cadastrar Assento");
            System.out.println("2 - Editar Assento");
            System.out.println("3 - Listar Assentos");
            System.out.println("4 - Consultar Assento");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarAssento();
                    break;
                case 2:
                    editarAssento();
                    break;    
                case 3: 
                    listarAssentos();
                    break;
                case 4:
                    consultarAssento();
                    break;        
                default:
                    System.out.println("Opção inválida.");
            }
        }

        private void cadastrarAssento() throws IOException {
            System.out.print("Digite o id do assento: ");
            int idAssento = sc.nextInt();
            sc.nextLine();
            System.out.print("Digite o id do tipo de assento: ");
            int idTipoAssento = sc.nextInt();
            sc.nextLine();
            boolean tipoAssentoEncontrado = false;

            // Verificar se o tipo de assento existe
            for (TipoAssento tp : tipoAssentos) {
                if (tp.getIdTipoAssento() == idTipoAssento) {
                    tipoAssentoEncontrado = true;
                    Assento novoAssento = new Assento(idAssento, tp);
                    Assento.cadastrarAssento(novoAssento);
                    assentos.add(novoAssento);
                    System.out.println("Assento cadastrado com sucesso!");
                    break;
                }
            }
            if (!tipoAssentoEncontrado) {
                System.out.println("Tipo de assento não encontrado.");
            }
        }

        private void editarAssento() throws IOException {
            System.out.print("Digite o ID do assento que deseja editar: ");
            int idAssento = sc.nextInt();
            sc.nextLine();
        
            System.out.print("Digite o novo ID do tipo de assento: ");
            int novoTipoAssentoId = sc.nextInt();
            sc.nextLine();

            TipoAssento novoTipoAssento = null;
            for (TipoAssento tp : tipoAssentos) {
                if (tp.getIdTipoAssento() == novoTipoAssentoId) {
                    novoTipoAssento = tp;
                    break;
                }
            }

            if (novoTipoAssento == null) {
                System.out.println("Tipo de assento com ID " + novoTipoAssentoId + " não encontrado.");
                return;
            }
        

            boolean sucesso = Assento.editarAssento(idAssento, novoTipoAssento, tipoAssentos);
            if (sucesso) {
                System.out.println("Assento editado com sucesso!");
            } else {
                System.out.println("Assento com ID " + idAssento + " não encontrado.");
            }
        }

        private void listarAssentos() {
            for (Assento a : assentos) {
                System.out.println("ID: " + a.getIdAssento() + " - Tipo de Assento: " + a.getTipoAssento().getIdTipoAssento());
            }
        }
        
        private void consultarAssento() throws IOException {
            System.out.print("Digite o ID do assento que deseja consultar: ");
            int idAssento = sc.nextInt();
            sc.nextLine();
            Assento assento = Assento.consultarAssento(idAssento, tipoAssentos);
            if (assento != null) {
                System.out.println("ID: " + assento.getIdAssento() + " - Tipo de Assento: " + assento.getTipoAssento().getIdTipoAssento());
            } else {
                System.out.println("Assento não encontrado.");
            }
        }    
    }

    static class MenuSessao {
        private Scanner sc;
        private ArrayList<Sessao> sessoes;

        public MenuSessao(Scanner sc, ArrayList<Sessao> sessoes) {
            this.sc = sc;
            this.sessoes = sessoes;
        }
    
        public void exibirMenu() throws IOException {
            System.out.println("Menu de Sessões:");
            System.out.println("1 - Cadastrar Sessão");
            System.out.println("2 - Editar Sessão");
            System.out.println("3 - Listar Sessões");
            System.out.println("4 - Consultar Sessão");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();
    
            switch (opcao) {
                case 1:
                    cadastrarSessao();
                    break;
                case 2:
                    editarSessao();
                    break;    
                case 3: 
                    listarSessoes();
                    break;
                case 4:
                    consultarSessao();
                    break;        
                default:
                    System.out.println("Opção inválida.");
            }
        }

        private void cadastrarSessao() throws IOException {
            System.out.print("Digite a data e hora da sessão: ");
            int dataHoraSessao = sc.nextInt();
            sc.nextLine();
        
            System.out.print("Digite o ID da sala: ");
            int idSala = sc.nextInt();
            sc.nextLine();
        
            Sala salaExistente = Sala.consultarSala(idSala);
           
            if (salaExistente == null) {
                System.out.println("Sala não encontrada. Deseja criar uma nova sala? (S/N)");
                String opcao = sc.nextLine();
        
                if (opcao.equalsIgnoreCase("S")) {
                    salaExistente = new Sala().instanciarSala(sc);
                    Sala.cadastrarSala(salaExistente);
                    System.out.println("Nova sala cadastrada com sucesso!");
                    return;
                } else {
                    System.out.println("Operação cancelada.");
                    return;
                }
            }

            sc.nextLine();
        
            System.out.print("Digite o status da sessão: ");
            String statusSessao = sc.nextLine();
        
            Sessao novaSessao = new Sessao(dataHoraSessao, salaExistente, statusSessao);
            sessoes.add(novaSessao);
            Sessao.cadastrarSessao(novaSessao);
            System.out.println("Sessão cadastrada com sucesso!");
        }
        
        private void editarSessao() throws IOException {
            System.out.print("Digite o id da sessão que deseja editar: ");
            int idSessao = sc.nextInt();
            sc.nextLine();
            System.out.print("Digite o novo status da sessão: ");
            String novoStatus = sc.nextLine();
            Sessao.editarSessao(idSessao, novoStatus);
            System.out.println("Sessão editada com sucesso!");
        }

        private void listarSessoes() {
            for (Sessao s : sessoes) {
                System.out.println(s);
            }
        }

        private void consultarSessao() throws IOException {
            System.out.print("Digite o id da sessão que deseja consultar: ");
            int idSessao = sc.nextInt();
            sc.nextLine();
            Sessao sessao = Sessao.consultarSessao(idSessao);
            if (sessao != null) {
                System.out.println(sessao);
            } else {
                System.out.println("Sessão não encontrada.");
            }
        }   
    }
 
    static class MenuIngresso {
        private Scanner sc;
        private ArrayList<Ingresso> ingressos;
        private ArrayList<SalaAssento> salasAssentos;
        private ArrayList<Sessao> sessoes;
        private ArrayList<TipoAssento> tipoAssentos;
        private ArrayList<Sala> salas;
        private ArrayList<Assento> assentos;

        public MenuIngresso(Scanner sc, ArrayList<Ingresso> ingressos, ArrayList<SalaAssento> salasAssentos, ArrayList<Sessao> sessoes, ArrayList<TipoAssento> tipoAssentos, ArrayList<Sala> salas, ArrayList<Assento> assentos) {
            this.sc = sc;
            this.ingressos = ingressos;
            this.salasAssentos = salasAssentos;
            this.sessoes = sessoes;
            this.tipoAssentos = tipoAssentos;
            this.salas = salas;
            this.assentos = assentos;
        }

        public void exibirMenu() throws IOException {
            System.out.println("Menu de Ingressos:");
            System.out.println("1 - Cadastrar Ingresso");
            System.out.println("2 - Editar Ingresso");
            System.out.println("3 - Listar Ingressos");
            System.out.println("4 - Consultar Ingresso");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();
    
            switch (opcao) {
                case 1:
                    cadastrarIngresso();
                    break;
                case 2:
                    editarIngresso();
                    break;    
                case 3: 
                    listarIngressos();
                    break;
                case 4:
                    consultarIngresso();
                    break;        
                default:
                    System.out.println("Opção inválida.");
            }
        }

        private SalaAssento cadastrarNovoSalaAssento() throws IOException {

            System.out.print("Digite o ID da Sala: ");
            int idSala = sc.nextInt();
            sc.nextLine();

            Sala salaEncontrada = null;
            Assento assentoEncontrado = null;

            for (Sala sala : salas) {
                if (sala.getIdSala() == idSala) {
                    salaEncontrada = sala;
                    break;
                }
            }

            if(salaEncontrada == null){
                System.out.println("Sala não encontrada. Deseja criar uma nova sala? (S/N)");
                String opcao = sc.nextLine();
                if (opcao.equalsIgnoreCase("S")) {
                    salaEncontrada = new Sala().instanciarSala(sc);
                } else {
                    System.out.println("Operação cancelada.");
                    return null;
                }
            }

            System.out.print("Digite o ID do Assento: ");
            int idAssento = sc.nextInt();
            sc.nextLine();

            for (Assento assento : assentos){
                if (assento.getIdAssento() == idAssento){
                    assentoEncontrado = assento;
                    break;
                }
            }

            if(assentoEncontrado == null){
                System.out.println("Assento não encontrado. Deseja criar um novo assento? (S/N)");
                String opcao = sc.nextLine();
                if (opcao.equalsIgnoreCase("S")) {
                    assentoEncontrado = new Assento().instanciarAssento(tipoAssentos, sc);
                } else {
                    System.out.println("Operação cancelada.");
                    return null;
                }
            }
            SalaAssento salaAssento = new SalaAssento(assentoEncontrado, salaEncontrada);
            SalaAssento.cadastrarSalaAssento(salaAssento);
            salasAssentos.add(salaAssento);

            return salaAssento;
        }

        private void cadastrarIngresso() throws IOException {
            System.out.print("Digite o valor do ingresso: ");
            double valor = sc.nextDouble();
            sc.nextLine();
        
            System.out.print("Digite o ID da sala assento: ");
            int salaAssentoId = sc.nextInt();
            sc.nextLine();
            SalaAssento salaAssentoSelecionada = null;
        
            for (SalaAssento sala : salasAssentos) {
                if (sala.getIdSalaAssento() == salaAssentoId) {
                    salaAssentoSelecionada = sala;
                    break;
                }
            }
            
            if (salaAssentoSelecionada == null) {
                System.out.println("Sala Assento não encontrada. Deseja criar uma nova sala assento? (S/N)");
                String opcao = sc.nextLine();
                if (opcao.equalsIgnoreCase("S")) {
                    salaAssentoSelecionada = cadastrarNovoSalaAssento();
                } else {
                    System.out.println("Operação cancelada.");
                    return;
                }
            }
        
            System.out.print("Digite o ID da sessão: ");
            int idSessao = sc.nextInt();
            sc.nextLine();  
            Sessao sessaoSelecionada = null;
        
            
            for (Sessao sessao : sessoes) {
                if (sessao.getIdSessao() == idSessao) {
                    sessaoSelecionada = sessao;
                    break;
                }
            }
            
            if (sessaoSelecionada == null) {
                System.out.println("Sessão não encontrada. Deseja criar uma nova sessão? (S/N)");
                String opcao = sc.nextLine();
        
                if (opcao.equalsIgnoreCase("S")) {
                    sessaoSelecionada = new Sessao().instanciarSessao(salas, sc);
                } else {
                    System.out.println("Operação cancelada.");
                    return;
                }
                return;
            }
        
            
            Ingresso novoIngresso = new Ingresso(valor, salaAssentoSelecionada, sessaoSelecionada);
            
            
            Ingresso.cadastrarIngresso(novoIngresso);
        
            
            ingressos.add(novoIngresso);
            System.out.println("Ingresso cadastrado com sucesso! ID: " + novoIngresso.getId());
        }

        private void editarIngresso() throws IOException {
            System.out.print("Digite o id do ingresso que deseja editar: ");
            int idIngresso = sc.nextInt();
            sc.nextLine();
            System.out.print("Digite a nova sessao do ingresso: ");
            int novaSessao = sc.nextInt();
            sc.nextLine();
            Ingresso.editarIngresso(idIngresso, novaSessao);
            System.out.println("Ingresso editado com sucesso!");
        }
        
        private void listarIngressos() {
            for (Ingresso i : ingressos) {
                System.out.println("ID: " + i.getId() + " - Valor: " + i.getValorPago() + " - Sala Assento: " + i.getSalaAssento() + " - Sessão: " + i.getSessao());
            }
        }

        private void consultarIngresso() throws IOException {
            System.out.print("Digite o id do ingresso que deseja consultar: ");
            int idIngresso = sc.nextInt();
            sc.nextLine();
            Ingresso ingresso = Ingresso.consultarIngresso(idIngresso);
            if (ingresso != null) {
                System.out.println("ID: " + ingresso.getId() + " - Valor: " + ingresso.getValorPago() + " - Sala Assento: " + ingresso.getSalaAssento() + " - Sessão: " + ingresso.getSessao());
            } else {
                System.out.println("Ingresso não encontrado.");
            }
        }
            
    }
}
