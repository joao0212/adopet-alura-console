package br.com.alura;

public class HelpCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Adopet (1.0) - Aplicativo de linha de comando (CLI).\n");
        System.out.println("Realiza a importação em lote de um arquivos de pets.\n");
        System.out.println("Comando possíveis: \n");
        System.out.println("import - comando que realiza a importação do arquivo de pets.");
        System.out.println("list - comando que realiza a listagem de pets da API consumida.");
        System.out.println("show - comando que exibe no terminal o conteúdo do arquivo importado.");
    }
}
