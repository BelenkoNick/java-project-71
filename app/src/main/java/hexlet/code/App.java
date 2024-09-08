package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;


@Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<Integer> {

    private static final int SUCCESS_EXIT_CODE = 0;
    private static final int ERROR_EXIT_CODE = 1;

    @CommandLine.Parameters(index = "0", description = "path to first file")
    private String filepath1;

    @CommandLine.Parameters(index = "1", description = "path to second file")
    private String filepath2;

    // Опция для формата вывода
    @Option(names = { "-f", "--format" },
            description = "output format [default: ${DEFAULT-VALUE}]", defaultValue = "stylish")
    private String format;

    @Override
    public Integer call() {
        System.out.printf("Comparing '%s' with '%s' using format '%s'%n", filepath1, filepath2, format);
        try {
            System.out.println(Differ.generate(filepath1, filepath2, format));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ERROR_EXIT_CODE;
        }
        return SUCCESS_EXIT_CODE;
    }


    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
