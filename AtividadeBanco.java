import java.util.Scanner;

public class AtividadeBanco {
    public static void main(String[] args) {
        Banco banco = new Banco();
        banco.menu();
    }
}

class Titular {
    private String nome;
    private String cpf;

    public Titular(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
}

class Conta {
    private static int proximoNumero = 1;
    private int numero;
    private Titular titular;
    private double saldo;

    public Conta(Titular titular) {
        this.numero = proximoNumero++;
        this.titular = titular;
        this.saldo = 0.0;
    }

    public int getNumero() {
        return numero;
    }

    public String getNomeTitular() {
        return titular.getNome();
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean sacar(double valor) {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }

    public boolean transferir(Conta destino, double valor) {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            destino.depositar(valor);
            return true;
        }
        return false;
    }
}

class Banco {
    private ArrayList<Conta> contas;
    private Scanner scanner;

    public Banco() {
        contas = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void menu() {
        while (true) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Criar uma nova conta");
            System.out.println("2. Exibir o saldo, ou extrato, de uma conta");
            System.out.println("3. Sacar um valor de uma conta");
            System.out.println("4. Depositar um valor em uma conta");
            System.out.println("5. Transferir valores de uma conta para outra");
            System.out.println("Escolha uma opção:");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    criarNovaConta();
                    break;
                case 2:
                    exibirExtrato();
                    break;
                case 3:
                    sacarValor();
                    break;
                case 4:
                    depositarValor();
                    break;
                case 5:
                    transferirValor();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void criarNovaConta() {
        scanner.nextLine(); // limpa o buffer
        System.out.println("Informe o nome do titular:");
        String nome = scanner.nextLine();
        System.out.println("Informe o CPF do titular:");
        String cpf = scanner.nextLine();
        Titular titular = new Titular(nome, cpf);
        Conta conta = new Conta(titular);
        contas.add(conta);
        System.out.println("Conta criada com sucesso. Número da conta: " + conta.getNumero());
    }

    private void exibirExtrato() {
        System.out.println("Informe o número da conta:");
        int numero = scanner.nextInt();
        Conta conta = encontrarConta(numero);
        if (conta != null) {
            System.out.println("Extrato da conta:");
            System.out.println("Número da conta: " + conta.getNumero());
            System.out.println("Nome do titular: " + conta.getNomeTitular());
            System.out.println("Saldo da conta: " + conta.getSaldo());
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private void sacarValor() {
        System.out.println("Informe o número da conta:");
        int numero = scanner.nextInt();
        Conta conta = encontrarConta(numero);
        if (conta != null) {
            System.out.println("Informe o valor a ser sacado:");
            double valor = scanner.nextDouble();
            if (conta.sacar(valor)) {
                System.out.println("Saque efetuado com sucesso.");
            } else {
                System.out.println("Saque não efetuado. Saldo insuficiente.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private void depositarValor() {
        System.out.println("Informe o número da conta:");
        int numero = scanner.nextInt();
        Conta conta = encontrarConta(numero);
        if (conta != null) {
            System.out.println("Informe o valor a ser depositado:");
            double valor = scanner.nextDouble();
            conta.depositar(valor);
            System.out.println("Depósito efetuado com sucesso.");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private void transferirValor() {
        System.out.println("Informe o número da conta de origem:");
        int numeroOrigem = scanner.nextInt();
        Conta contaOrigem = encontrarConta(numeroOrigem);
        if (contaOrigem != null) {
            System.out.println("Informe o número da conta de destino:");
            int numeroDestino = scanner.nextInt();
            Conta contaDestino = encontrarConta(numeroDestino);
            if (contaDestino != null) {
                System.out.println("Informe o valor a ser transferido:");
                double valor = scanner.nextDouble();
                if (contaOrigem.transferir(contaDestino, valor)) {
                    System.out.println("Transferência efetuada com sucesso.");
                } else {
                    System.out.println("Transferência não efetuada. Saldo insuficiente.");
                }
            } else {
                System.out.println("Conta de destino não encontrada.");
            }
        } else {
            System.out.println("Conta de origem não encontrada.");
        }
    }

    private Conta encontrarConta(int numero) {
        for (Conta conta : contas) {
            if (conta.getNumero() == numero) {
                return conta;
            }
        }
        return null;
    }
}