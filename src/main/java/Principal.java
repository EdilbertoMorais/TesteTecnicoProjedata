import model.Funcionario;
import model.Pessoa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        List<Funcionario> ListaFuncionarios = new ArrayList<>();

        // 3.1 - Inserir funcionários utilizando a função add do ArrayList
        ListaFuncionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        ListaFuncionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        ListaFuncionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        ListaFuncionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        ListaFuncionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        ListaFuncionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        ListaFuncionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        ListaFuncionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        ListaFuncionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        ListaFuncionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        System.out.println("Lista completa de funcionários:");
        System.out.println("_________________________________________");
        imprimeListaFuncionarios(ListaFuncionarios);

        // 3.2 - Remover funcionário "João"
        ListaFuncionarios.removeIf(f -> f.getNome().equals("João"));

        // 3.3 - Imprimir funcionários
        //Imprimindo a lista após a remoção do Funcionário João.
        System.out.println("Lista após a remoção do Funcionário João:");
        System.out.println("_________________________________________");
        imprimeListaFuncionarios(ListaFuncionarios);

        // 3.4 - Aumento de 10%
        //Iterando sobre a lista de funcionários
        for (Funcionario f : ListaFuncionarios) {
            //Aumenta o salário do funcionário em 10% usando a função multiply
            f.setSalario(f.getSalario().multiply(new BigDecimal("1.10")));
        }

        // Imprimindo a lista de funcionários após o aumento
        System.out.println("Funcionários com aumento de 10%:");
        System.out.println("_________________________________________");
        imprimeListaFuncionarios(ListaFuncionarios);

        // 3.5 - Agrupar por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = ListaFuncionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 - Imprimir agrupados por função
        //inicia um loop for-each que itera sobre as entradas (chave-valor) do mapa
        // e declara uma variável entry do tipo Map.Entry que representará cada entrada do mapa durante a iteração
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            // Imprime a chave (função) e os funcionários correspondentes
            System.out.println("Função: " + entry.getKey());
            //Itera sobre a lista de funcionários correspondentes a essa função
            for (Funcionario funcionario : entry.getValue()) {
                //Imprime o nome do funcionário
                System.out.println("  - " + funcionario.getNome());
            }
        }

        // 3.8 - Aniversariantes de Outubro e Dezembro
        System.out.println("Aniversariantes em Outubro e Dezembro:");
        //Iterando sobre a lista de funcionários
        for (Funcionario f : ListaFuncionarios) {
            //Verificando se o mês do aniversário é 10 ou 12
            int mesAniversario = f.getDataNascimento().getMonthValue();
            //Imprimindo o nome do funcionário e o mês do aniversário caso seja 10 ou 12
            if (mesAniversario == 10 || mesAniversario == 12) {
                System.out.println("  - " + f.getNome() + " (" + mesAniversario + ")");
            }
        }

        // 3.9 - Funcionário mais velho
        //Iterando sobre a lista de funcionários
        model.Funcionario maisVelho = ListaFuncionarios.stream()
                //Encontra o funcionário com a data de nascimento mais antiga
                .min(Comparator.comparing(Pessoa::getDataNascimento)).orElse(null);
        if (maisVelho != null) {
            //Calculando a idade do funcionário mais velho
            int idade = LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear();
            //Imprimindo o nome e a idade do funcionário mais velho
            System.out.println("Funcionário mais velho: " + maisVelho.getNome() + " (" + idade + " anos)");
        }

        // 3.10 - Lista por ordem alfabética
        //Ordenando a lista de funcionários por ordem alfabética usando a função comparing
        List<Funcionario> funcionariosOrdenados = ListaFuncionarios.stream()
                .sorted(Comparator.comparing(Pessoa::getNome))
                .toList();
        System.out.println("Lista de funcionários por ordem alfabética:");
        //Iterando sobre a lista ordenada
        for (Funcionario f : funcionariosOrdenados) {
            //Imprimindo o nome do funcionário
            System.out.println("  - " + f.getNome());
        }

        // 3.11 - Total dos salários
        //Calculando o total dos salários
        BigDecimal totalSalarios = ListaFuncionarios.stream()
                //Soma os salários dos funcionários
                .map(Funcionario::getSalario)
                //Reduz o stream para um BigDecimal
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //Imprimindo o total dos salários com duas casas decimais e formatando para o padrão brasileiro
        System.out.println("Total dos salários: R$ " + totalSalarios.setScale(2, BigDecimal.ROUND_HALF_UP).toString().replace(".", ","));

        // 3.12 - Salários mínimos por funcionário
        //Cria uma variável para armazenar o salário mínimo
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("Salários mínimos por funcionário:");
        //Iterando sobre a lista de funcionários
        for (Funcionario f : ListaFuncionarios) {
            //Cria uma variável para armazenar a quantidade de salários mínimos e divide o salário do funcionário pelo salário mínimo
            BigDecimal quantidadeSalariosMinimos = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            //Imprimindo o nome do funcionário e a quantidade de salários mínimos
            System.out.println("  - " + f.getNome() + ": " + quantidadeSalariosMinimos);
        }
    }

    //Função para imprimir a lista de funcionários e reaproveitar o código
    private static void imprimeListaFuncionarios(List<Funcionario> ListaFuncionarios) {
        // Criando um objeto do tipo DateTimeFormatter e usando a função DateTimeFormatter.ofPattern para formatar a data no padrão desejado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //Iterando sobre a lista de funcionários
        for (Funcionario f : ListaFuncionarios) {
            //Imprimi o nome do funcionário
            System.out.println("Nome: " + f.getNome());
            //Imprimi a data de nascimento do funcionário formatada
            System.out.println("Data de Nascimento: " + f.getDataNascimento().format(formatter));
            //O f.getSalario() chama a função getSalario() do objeto Funcionario para obter o salário, que é um objeto BigDecimal
            //O setScale(2, BigDecimal.ROUND_HALF_UP) define a escala do salário para 2 casas decimais e arredonda o valor, se necessário
            //O .toString().replace(".", ",") converte o salário para uma string e substitui o ponto decimal por vírgula, formatando o valor para o padrão brasileiro.
            System.out.println("Salário: " + f.getSalario().setScale(2, BigDecimal.ROUND_HALF_UP).toString().replace(".", ","));
            //imprimi a função do funcionário
            System.out.println("Função: " + f.getFuncao());
            //Imprimi uma linha para melhorar a visibilidade no terminal
            System.out.println("--------------------");
        }
    }
}
